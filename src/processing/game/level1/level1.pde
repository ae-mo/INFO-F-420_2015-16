import java.io.*;
import java.util.*;
import java.lang.Comparable;

int r, g, bl;
ArrayList<Point> points;
Point b;

void setup() {

  size(1068, 600);
  
  r = 0;
  g = 0;
  bl = 0;

  points = new ArrayList<Point>();
  points.add(new Point(314.94845, 284.82678));
  points.add(new Point(468.96991, 159.82386));
  points.add(new Point(703.35039, 393.46027));
  points.add(new Point(406.46845, 513.25474));
  points.add(new Point(329.82975, 420.99068));
  points.add(new Point(545.60861, 414.29409));
  b = new Point(433.25479, 287.80304);
   drawShape(points, 0, 0, 0, 0, 200, 0, 10);
  
  console.log("hey");
  
}

void draw() {

 
  
}

void showHint() {

    AttractionRegion attr = new AttractionRegion(b, points);

    drawShape(attr.face, 0, 255, 0, 0, 200, 0, 0);
}


void drawShape(Face f, int r1, int g1, int b1, int r2, int g2, int b2, int stroke) {

  Halfedge h = f.h;
  
  fill(color(r1, g1, b1));

  strokeWeight(stroke);
  
  beginShape();

  float x, y;
  do {

    x = (float) h.target.x;
    y = (float) h.target.y;

    vertex(x, y);
    h = h.next;
  } while (h.target != f.h.target);

  endShape(CLOSE);
  strokeWeight(1);

}

void drawShape(ArrayList<Point> points, int r1, int g1, int b1, int r2, int g2, int b2, int stroke) {

  fill(color(r1, g1, b1));
  strokeWeight(stroke);
  
  beginShape();

  for (Point p : points) {
    vertex((float) p.x, (float) p.y);
  }
  endShape(CLOSE);

 strokeWeight(1);
 
  
}

void clear() {
}

/**
 * Attraction Region
 **/
 
 class AttractionRegion {

  ArrayList<Point> points;
  public DCEL dcel;
  public Face face;
  Point b;

  public AttractionRegion(Point b, ArrayList<Point> points) {

    this.points = points;
    this.dcel = new DCEL();
    this.dcel.initialize(this.points);
    this.b = b;
    this.computeAttractionRegion();

  }

  public void computeAttractionRegion() {

    ArrayList<Point> sortedVertices = this.sortVertices(b, this.dcel.vertices);

    this.computeRayVertices(b, sortedVertices, this.dcel);
    this.computeAttractionFace(b, this.dcel);

  }
  
  private void computeAttractionFace(Point b, DCEL dcel) {

    Face f;
    for(int i = 1; i < this.dcel.faces.size(); i++) {
      
      f = this.dcel.faces.get(i);
      if(f.contains(b))  {
        this.face = f;
        break;
      }
        
      
    }
    
  }

  /**
   * Sort the vertices of the polygon about an angle around the query beacon point.
   * @param b
   * @param vertices
   * @return
   */
  protected ArrayList<Point> sortVertices(Point b, ArrayList<Point> vertices) {

    RadialSort sorted = new RadialSort(b, vertices);

    return sorted.result;

  }

  /**
   * Compute the Ray vertices defined by the split vertices of the polygon with respect to the query beacon point.
   * @param b
   * @param vertices
   * @param edges
   */
  protected void computeRayVertices(Point b, ArrayList<Point> vertices, DCEL dcel) {

    RedBlackBST<Key, Edge> status = new RedBlackBST<Key, Edge>();

    this.initializeStatus(status, dcel, b, vertices);

    for(int i=1; i < vertices.size(); i++) {

      Point p = vertices.get(i);

      int type = this.isSplitVertex(b, p);

      this.updateStatus(status, b, p, type);

      if(type > 0)  {
        System.out.println("Split vertex found!: " + p.x + ", " + p.y);
        this.computeRayVertex(status, dcel, b, p, type);

      }

    }

  }

  /**
   * Given a split vertex, computes the corresponding ray vertex.
   * @param status
   * @param b
   * @param p
   * @return
   */
  private void computeRayVertex(RedBlackBST<Key, Edge> status, DCEL dcel, Point b, Point p, int type) {

    Point next = p.h.target;
    Point prev = p.h.prev.prev.target;

    System.out.println("Size: " + status._size());

    Point p2;
    
    switch(type) {
    
    case 1:
      p2 = prev;
      break;
      
    case 2:
      p2 = prev;
      break;
      
    case 3:
      p2 = next;
      break;
      
      default:
        p2 = next;
    
    }
    
    Key k = new Key(b, new Edge(p, p2));
    
    int rank = status.rank(k);
    
    if(status.contains(k)) rank++;
    
    Key k2 = status.select(rank);

    Point rayVertex = this.computeLineLineIntersection(b.x, b.y, p.x, p.y, k2.e.a.x, k2.e.a.y, k2.e.b.x, k2.e.b.y);


    Edge old = k2.e;

    Turn pEab = new Turn (p, old.a, old.b);
    if(pEab.value > 0) p2 = old.b; 
    else p2 = old.a; 

    Key _new = this.createKey(b, rayVertex, p2, 1);

    System.out.println();
    System.out.println("UPDATING KEY");

    status.delete(k2);
    System.out.println("REPLACING WITH NEW KEY");
    status.put(_new, _new.e);

    System.out.println("END OF UPDATE");

    System.out.println("Halfedge to divide: " + old.b.h.target.x + ", " +  old.b.h.target.y+ "; "+ old.b.h.twin.target.x + ", " + old.b.h.twin.target.y);
    int[] newH = dcel.splitEdge(p2.h.prev, rayVertex);
    System.out.println("Ray vertex halfedge's NEW target: " + rayVertex.h.target.x + ", " + + rayVertex.h.target.y);

    System.out.println();
    System.out.println("Splitting using halfedge: " + p.h.target.x + ", " +  p.h.target.y+ "; "+ p.h.twin.target.x + ", " + p.h.twin.target.y);
    System.out.println("ray vertex: " + rayVertex.x + ", " + rayVertex.y);

    dcel.splitFace(p.h.face, p.h.prev, dcel.vertices.size()-1);

  }

  private void updateStatus(RedBlackBST<Key, Edge> status, Point b, Point p, int type) {

    Point prev = p.h.prev.prev.target;
    Point next = p.h.target;

    int actionNext = decideAction(b, p, next);
    int actionPrev = decideAction(b, p, prev);


    if(actionNext == 2)
      this.deleteEdge(status, b, p, next);
    if(actionPrev == 2)
      this.deleteEdge(status, b, p, prev);

    if(actionNext == 1) 
      this.addEdge(status, b, p, next);
    if(actionPrev == 1) 
      this.addEdge(status, b, p, prev);


  }


  /**
   * Initialize the status of the rotational sweep.
   * @param b
   * @param vertices
   * @param edges
   * @param status
   * @param queue
   */
  private void initializeStatus(RedBlackBST<Key, Edge> status, DCEL dcel, Point b, ArrayList<Point> vertices) {

    Point p1 = vertices.get(0);

    Halfedge h = p1.h;
    Edge e = h.getEdge();

    System.out.println();
    System.out.println("*******INITIALIZATION***********");

    do {

      System.out.println();
      System.out.println("Ray: " + b.x + ", " + b.y+ "; "+p1.x + ", " + p1.y);
      System.out.println("Edge: " + e.a.x + ", " + e.a.y+ "; "+e.b.x + ", " + e.b.y);

      Point p2 = this.computePointOnLine(b,  p1, 1.1);

      if(e.intersectsRay(p1, p2)) { // add it to the status

        Turn bEab = new Turn(b, e.a, e.b);
        Turn bEba = new Turn(b, e.b, e.a);

        Turn bpEa = new Turn (b, p1, e.a);
        Turn bpEb = new Turn (b, p1, e.b);

        if((bpEa.value == 0 && bEab.value > 0) ||
            (bpEb.value == 0 && bEba.value > 0) ||
            (bpEa.value != 0 && bpEb.value != 0)) {

          Key k = new Key(b, new Edge(e.a, e.b));
          status.put(k, e);
          System.out.println("Added");

        }


      }

      h = h.next;
      e = h.getEdge();

    } while(!h.twin.target.equals(p1));

    int type = this.isSplitVertex(b, p1);

    if(type > 0)  {
      System.out.println("Split vertex found!: " + p1.x + ", " + p1.y);
      this.computeRayVertex(status, dcel, b, p1, type);

    }


  }

  private int decideAction(Point b, Point p, Point q) {

    Turn bpq = new Turn(b, p, q);

    System.out.println();
    System.out.println("Deciding for Edge: " + p.x + ", " + p.y+ "; "+q.x + ", " + q.y);

    if(bpq.value > 0) {
      System.out.println("added");
      return 1;
    }

    else if(bpq.value < 0) {
      System.out.println("deleted");
      return 2;
    }


    return 0;

  }

  private void addEdge(RedBlackBST<Key, Edge> status, Point b, Point p, Point q) {

    Key k = createKey(b, p, q, 1);
    System.out.println();
    System.out.println("ADDITION");
    System.out.println("Size: " + status._size());
    status.put(k, new Edge(p, q));
    System.out.println("Size: " + status._size());

  }

  private void deleteEdge(RedBlackBST<Key, Edge> status, Point b, Point p, Point q) {

    Key k = createKey(b, p, q, 2);
    System.out.println("DELETION");
    System.out.println("Size: " + status._size());
    status.delete(k);
    System.out.println("Size: " + status._size());

  }

  private Key createKey(Point b, Point p, Point q, int first) {

    if(first == 2) {

      Point temp = p;
      p = q;
      q = temp;

    }


    return new Key(b, new Edge(p, q));

  }

  private int isSplitVertex(Point b, Point p) {

    Point prev = p.h.prev.prev.target;
    Point next = p.h.target;


    Turn prevPNext = new Turn(prev, p, next);

    if(prevPNext.value >= 0)
      return -1;

    CrossProduct bp_pNext = new CrossProduct(b, p, p, next);
    CrossProduct bp_prevP = new CrossProduct(b, p, prev, p);

    DotProduct bp_pNext_d = new DotProduct(b, p, p, next);
    DotProduct bp_prevP_d = new DotProduct(b, p, prev, p);

    if(bp_pNext.value < 0 && bp_prevP.value < 0) {

      if(bp_pNext_d.value < 0 && bp_prevP_d.value > 0)
        return 1;

    }
    else if(bp_pNext.value > 0 && bp_prevP.value < 0) {

      if(bp_prevP_d.value > 0)
        return 2;

    }
    else if(bp_pNext.value < 0 && bp_prevP.value > 0) {

      if(bp_pNext_d.value < 0)
        return 3;

    }

    return -1;

  }

  private Point computeLineLineIntersection(double x1,  double y1,  double x2,  double y2,  double x3,  double y3,  double x4,  double y4) {

    double den, xNum, yNum;
    double x, y;

    den = (x1 -x2)*(y3 - y4) - (y1 - y2)*(x3 - x4);

    if(den == 0) return null;

    xNum = (x1*y2-y1*x2)*(x3 - x4) - (x1-x2)*(x3*y4-y3*x4);
    yNum = (x1*y2-y1*x2)*(y3 - y4) - (y1-y2)*(x3*y4-y3*x4);

    x = xNum / den;
    y = yNum / den;

    return new Point(x, y);

  }

  private Point computePointOnLine(Point a, Point b, double t) {

    double x = a.x +t*(b.x-a.x);
    double y = a.y +t*(b.y-a.y);

    return new Point(x, y);

  }

}


class Key implements Comparable<Key>{

  public Point beacon;
  public Edge e;

  public Key(Point beacon, Edge e) {

    Turn bEab = new Turn(beacon, e.a, e.b);
    
    if(bEab.value <= 0) {
      
      Point temp = e.a;
      e.a = e.b;
      e.b = temp;
      
    }
    
    this.beacon = beacon;
    this.e = e;

  }

  public Key(Point beacon, float ax, float ay, float bx, float by) {

    this(beacon, new Edge(ax, ay, bx, by));

  }

  public Key(float bx, float by, Edge e) {

    this(new Point(bx, by), e);
  }

  public Key(float bex, float bey, float ax, float ay, float bx, float by) {

    this(new Point(bex, bey), new Edge(ax, ay, bx, by));

  }


  public int compareTo(Key _key) {

    int result = 0;

    if(this.equals(_key)) return 0;

    boolean intersectsBeA = _key.e.intersectsRay(this.beacon, this.e.a);
    boolean intersectsBeB = _key.e.intersectsRay(this.beacon, this.e.b);


    if(intersectsBeA && intersectsBeB) {

      Triangle tr = new Triangle(this.e.b, _key.e.b, _key.e.a);
      
      if(tr.contains(this.e.a)) {
        
        Turn eb = new Turn(this.e, _key.e.b);
        if(eb.value > 0 ) result = 1;
        else result = -1;
        
      }
      else {
        
        Turn ea = new Turn(this.e, _key.e.a);
        if(ea.value > 0 ) result = 1;
        else result = -1;
        
      }
    }
    else if(intersectsBeA) {
      Turn eb = new Turn(this.e, _key.e.b);
      if(eb.value > 0 ) result = 1;
      else result = -1;
    }
    else {

      Turn ea = new Turn(this.e, _key.e.a);
      if(ea.value > 0 ) result = 1;
      else result = -1;

    }

    return result;
  }

  
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (!Key.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    final Key other = (Key) obj;

    if(this.e.equals(other.e)) 
      return true;

    return false;
  }


}



/**
 * Operations
 **/

class Turn {

  public double value;
  private final double EPSILON = 0.00000000001;

  public Turn(Point a, Point b, Point c) {

    double result = b.x*c.y - a.x*c.y + a.x*b.y - b.y*c.x + a.y*c.x - a.y*b.x;

    if (result < EPSILON && result > -EPSILON)
      result = 0;

    this.value = result;
  }

  public Turn(Edge e, Point c) {

    this.value = e.b.x*c.y - e.a.x*c.y + e.a.x*e.b.y - e.b.y*c.x + e.a.y*c.x - e.a.y*e.b.x;
  }
}


class Triangulation {

  public DCEL dcel;

  public Triangulation(DCEL dcel) {

    ArrayList<Point> reflexVertices= new ArrayList<Point>();
    ArrayList<Point> convexVertices=new ArrayList<Point>();
    ArrayList<Point> ears=new ArrayList<Point>();

    for (Point p : dcel.vertices) {

      Point next = p.h.prev.twin.target;
      Point prev = p.h.target;

      Turn t = new Turn(prev, p, next);
      if (t.value < 0) reflexVertices.add(p);
      else if (t.value > 0) {

        convexVertices.add(p);
      }
    }

    for (Point p : convexVertices) {

      if (testEar(reflexVertices, p)) {

        ears.add(p);
      }
    }

    while (convexVertices.size() + reflexVertices.size() > 3) {

      Point ear = ears.get(0);

      Point prev = ear.h.prev.prev.target;
      Point next = ear.h.target;
      Halfedge h = ear.h.prev.prev;

      int prevTest = testConvexity(prev);
      int nextTest = testConvexity(next);

      System.out.println("*****************************************");
      System.out.println("current ear: " + ear.x + ", " + ear.y);
      System.out.println("halfedge target: " + h.target.x + ", " + h.target.y);
      System.out.println("split point: " + next.x + ", " + next.y);


      dcel.splitFace(dcel.faces.size() -1, h, next);
      ears.remove(0);
      convexVertices.remove(0);

      Halfedge h1 = dcel.halfedges.get(dcel.halfedges.size() - 2);
      next = h1.target;
      prev = h1.prev.target;
      next.h = h1.next;
      prev.h = h1;

      int prevTest2 = testConvexity(prev);
      int nextTest2 = testConvexity(next);

      this.handleAdjacentVertex(reflexVertices, convexVertices, ears, next, nextTest, nextTest2);
      this.handleAdjacentVertex(reflexVertices, convexVertices, ears, prev, prevTest, prevTest2);
    }
  }

  private int testConvexity(Point p) {

    Point prev = p.h.prev.prev.target;
    Point next = p.h.target;

    Turn t = new Turn(prev, p, next);

    if (t.value>0) return 1;
    else return -1;
  }

  private boolean testEar(ArrayList<Point> reflexVertices, Point p) {

    Point next = p.h.target;
    Point prev = p.h.prev.prev.target;

    Triangle tr = new Triangle(prev, p, next);

    boolean isEar = true;

    for (Point p1 : reflexVertices) {

      if (p1 != prev && p1 != next) {

        if (tr.strictlyContains(p1)) {

          isEar = false;
          break;
        }
      }
    }

    return isEar;
  }

  private void handleAdjacentVertex(ArrayList<Point> reflexVertices, ArrayList<Point> convexVertices, ArrayList<Point> ears, Point p, int test1, int test2) {

    if (test1 < 0 && test2 >0) {
      reflexVertices.remove(p);
      convexVertices.add(0, p);
      if (testEar(reflexVertices, p)) {
        ears.add(0, p);
      }
    } else if (test1 >0) {
      if (!testEar(reflexVertices, p) && ears.contains(p)) {
        ears.remove(p);
      } else if (testEar(reflexVertices, p) && !ears.contains(p)) {

        ears.add(0, p);
      }
    }
  }
}

class RadialSort {

  public ArrayList<Point> result;

  public RadialSort(Point q, ArrayList<Point> points) {

    this.result = (ArrayList<Point>) cyclicOrder(q, points);
  }


  public RadialSort(double qx, double qy, ArrayList<Point> points) {

    this(new Point(qx, qy), points);
  }

  private List<Point> cyclicOrder(Point q, List<Point> arr) {

    if (!arr.isEmpty()) {
      Point pivot = arr.get(0); //This pivot can change to get faster results



      List<Point> less = new ArrayList<Point>();
      List<Point> pivotList = new ArrayList<Point>();
      List<Point> more = new ArrayList<Point>();

      // Partition
      for (Point i : arr) {
        int comparison = compare(q, i, pivot);
        if (comparison > 0)
          less.add(i);
        else if (comparison < 0)
          more.add(i);
        else
          pivotList.add(i);
      }

      // Recursively sort sublists
      less = cyclicOrder(q, less);
      more = cyclicOrder(q, more);

      // Concatenate results
      less.addAll(pivotList);
      less.addAll(more);
      return less;
    }
    return arr;
  }

  public int compare(Point q, Point pivot, Point curr) {

    int result = 0;

    Turn t = new Turn(q, pivot, curr);

    if (t.value > 0) result = 1;
    else if (t.value < 0) result = -1;
    else result = comesBefore(q, pivot, curr);

    return result;
  }

  private int comesBefore(Point q, Point a, Point b) {

    double xDiff = b.x - a.x - q.x;
    double yDiff = b.y - a.y -q.y;

    if (a.x == b.x && a.y == b.y) return 0;
    else if ((xDiff > 0) || (xDiff == 0 && yDiff > 0)) return 1;
    else  return -1;
  }
}


class DotProduct {

  public double value;


  public DotProduct(Point a1, Point a2, Point b1, Point b2) {

    value = (a1.x - a2.x)*(b1.x - b2.x) + (a1.y - a2.y)*(b1.y - b2.y);
  }

  public DotProduct(Edge a, Edge b) {

    this(a.a, a.b, b.a, b.b);
  }

  public DotProduct(Point a1, Point a2, Edge b) {

    this(a1, a2, b.a, b.b);
  }

  public DotProduct(Edge a, Point b1, Point b2) {

    this(a.a, a.b, b1, b2);
  }
}


class CrossProduct {

  public double value;


  public CrossProduct(Point a1, Point a2, Point b1, Point b2) {

    value = (a2.x - a1.x)*(b2.y - b1.y) - (b2.x - b1.x)*(a2.y - a1.y);
  }

  public CrossProduct(Edge a, Edge b) {

    this(a.a, a.b, b.a, b.b);
  }

  public CrossProduct(Point a1, Point a2, Edge b) {

    this(a1, a2, b.a, b.b);
  }

  public CrossProduct(Edge a, Point b1, Point b2) {

    this(a.a, a.b, b1, b2);
  }
}


/**
 * Data Structures
 **/

class Graph {
  private final Map<String, Vertex> graph; // mapping of vertex names to Vertex objects, built from a set of Edges

  /** One edge of the graph (only used by Graph constructor) */
  public class Edge {
    public final String v1, v2;
    public final double dist;
    public Edge(String v1, String v2, double dist) {
      this.v1 = v1;
      this.v2 = v2;
      this.dist = dist;
    }
  }

  /** One vertex of the graph, complete with mappings to neighbouring vertices */
  public class Vertex implements Comparable<Vertex> {
    public final String name;
    public double dist = Double.MAX_VALUE; // MAX_VALUE assumed to be infinity
    public Vertex previous = null;
    public final Map<Vertex, Double> neighbours = new HashMap<Vertex, Double>();

    public Vertex(String name) {
      this.name = name;
    }

    private void printPath() {
      if (this == this.previous) {
        System.out.printf("%s", this.name);
      } else if (this.previous == null) {
        System.out.printf("%s(unreached)", this.name);
      } else {
        this.previous.printPath();
        System.out.printf(" -> %s(%d)", this.name, this.dist);
      }
    }
    private ArrayList<String> getPath(ArrayList<String> path) {
      if (this == this.previous) {
        path.add(this.name);
      } else if (this.previous == null) {
        return null;
      } else {
        this.previous.getPath(path);
        path.add(this.name);
      }

      return path;
    }

    public int compareTo(Vertex other) {
      return Double.compare(dist, other.dist);
    }
  }

  /** Builds a graph from a set of edges */
  public Graph(Edge[] edges) {
    graph = new HashMap<String, Vertex>(edges.length);

    //one pass to find all vertices
    for (Edge e : edges) {
      if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
      if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
    }

    //another pass to set neighbouring vertices
    for (Edge e : edges) {
      graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
      //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
    }
  }

  /** Runs dijkstra using a specified source vertex */
  public void dijkstra(String startName) {
    if (!graph.containsKey(startName)) {
      System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
      return;
    }
    final Vertex source = graph.get(startName);
    NavigableSet<Vertex> q = new TreeSet<Vertex>();

    // set-up vertices
    for (Vertex v : graph.values()) {
      v.previous = v == source ? source : null;
      v.dist = v == source ? 0 : Double.MAX_VALUE;
      q.add(v);
    }

    dijkstra(q);
  }

  /** Implementation of dijkstra's algorithm using a binary heap. */
  private void dijkstra(final NavigableSet<Vertex> q) {      
    Vertex u, v;
    while (!q.isEmpty()) {

      u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
      if (u.dist == Double.MAX_VALUE) break; // we can ignore u (and any other remaining vertices) since they are unreachable

      //look at distances to each neighbour
      for (Map.Entry<Vertex, Double> a : u.neighbours.entrySet()) {
        v = a.getKey(); //the neighbour in this iteration

        final double alternateDist = u.dist + a.getValue();
        if (alternateDist < v.dist) { // shorter path to neighbour found
          q.remove(v);
          v.dist = alternateDist;
          v.previous = u;
          q.add(v);
        }
      }
    }
  }

  /** Prints a path from the source to the specified vertex */
  public void printPath(String endName) {
    if (!graph.containsKey(endName)) {
      System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
      return;
    }

    graph.get(endName).printPath();
    System.out.println();
  }

  /** Returns a path from the source to the specified vertex */
  public ArrayList<String> getPath(String endName, ArrayList<String> path) {
    if (!graph.containsKey(endName)) {
      System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
      return null;
    }

    return graph.get(endName).getPath(path);
  }

  /** Prints the path from the source to every vertex (output order is not guaranteed) */
  public void printAllPaths() {
    for (Vertex v : graph.values()) {
      v.printPath();
      System.out.println();
    }
  }
}

class Triangle {

  public Point a;
  public Point b;
  public Point c;

  public Triangle(Point a, Point b, Point c) {

    this.a = a;
    this.b = b;
    this.c = c;
  }

  public Triangle(float ax, float ay, float bx, float by, float cx, float cy) {

    this(new Point(ax, ay), new Point(bx, by), new Point(cx, cy));
  }

  public boolean contains(Point q) {

    Turn turnAQC = new Turn(this.a, q, this.c);
    Turn turnCQB = new Turn(this.c, q, this.b);
    Turn turnBQA = new Turn(this.b, q, this.a);

    if ((turnAQC.value >= 0 && turnCQB.value >= 0 && turnBQA.value >= 0) ||
      (turnAQC.value <= 0 && turnCQB.value <= 0 && turnBQA.value <= 0))
      return true;
    else return false;
  }

  public boolean contains(float qx, float qy) {

    return contains(new Point(qx, qy));
  }

  public boolean strictlyContains(Point q) {

    Turn turnAQC = new Turn(this.a, q, this.c);
    Turn turnCQB = new Turn(this.c, q, this.b);
    Turn turnBQA = new Turn(this.b, q, this.a);

    if ((turnAQC.value > 0 && turnCQB.value > 0 && turnBQA.value > 0) ||
      (turnAQC.value < 0 && turnCQB.value < 0 && turnBQA.value < 0))
      return true;
    else return false;
  }

  public boolean strictlyContains(float qx, float qy) {

    return strictlyContains(new Point(qx, qy));
  }
}


class RedBlackBST<Key extends Comparable<Key>, Value> {

  private static final boolean RED   = true;
  private static final boolean BLACK = false;
  Node lastInserted;

  private Node root;     // root of the BST

  // BST helper node data type
  final class Node {
    private Key _key;           // key
    Value val;         // associated data
    Node left, right;  // links to left and right subtrees
    private boolean colored;     // color of parent link
    private int N;             // subtree count

    public Node(Key _key, Value val, boolean colored, int N) {
      this._key = _key;
      this.val = val;
      this.colored = colored;
      this.N = N;
    }
  }

  /**
   * Initializes an empty symbol table.
   */
  public RedBlackBST() {

    this.lastInserted = null;
  }

  /***************************************************************************
   *  Node helper methods.
   ***************************************************************************/
  // is node x red; false if x is null ?
  private boolean isRed(Node x) {
    if (x == null) return false;
    return x.colored == RED;
  }

  // number of node in subtree rooted at x; 0 if x is null
  private int _size(Node x) {
    if (x == null) return 0;
    return x.N;
  } 


  /**
   * Returns the number of key-value pairs in this symbol table.
   * @return the number of key-value pairs in this symbol table
   */
  public int _size() {
    return _size(root);
  }

  /**
   * Is this symbol table empty?
   * @return <tt>true</tt> if this symbol table is empty and <tt>false</tt> otherwise
   */
  public boolean isEmpty() {
    return root == null;
  }


  /***************************************************************************
   *  Standard BST search.
   ***************************************************************************/

  /**
   * Returns the value associated with the given key.
   * @param _key the key
   * @return the value associated with the given key if the key is in the symbol table
   *     and <tt>null</tt> if the key is not in the symbol table
   * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
   */
  public Value getValue(Key _key) {
    if (_key == null) throw new NullPointerException("argument to getValue() is null");
    return getValue(root, _key);
  }

  // value associated with the given key in subtree rooted at x; null if no such key
  private Value getValue(Node x, Key _key) {
    while (x != null) {
      int cmp = _key.compareTo(x._key);
      System.out.println("Comparison: " + cmp);
      if      (cmp < 0) x = x.left;
      else if (cmp > 0) x = x.right;
      else              return x.val;
    }
    return null;
  }

  /**
   * Does this symbol table contain the given key?
   * @param key the key
   * @return <tt>true</tt> if this symbol table contains <tt>_key</tt> and
   *     <tt>false</tt> otherwise
   * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
   */
  public boolean contains(Key _key) {
    return getValue(_key) != null;
  }

  /***************************************************************************
   *  Red-black tree insertion.
   ***************************************************************************/

  /**
   * Inserts the specified key-value pair into the symbol table, overwriting the old 
   * value with the new value if the symbol table already contains the specified key.
   * Deletes the specified key (and its associated value) from this symbol table
   * if the specified value is <tt>null</tt>.
   *
   * @param _key the key
   * @param val the value
   * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
   */
  public void put(Key _key, Value val) {
    if (_key == null) throw new NullPointerException("first argument to put() is null");
    if (val == null) {
      delete(_key);
      return;
    }

    root = put(root, _key, val);
    root.colored = BLACK;
    // assert check();
  }

  // insert the key-value pair in the subtree rooted at h
  private Node put(Node h, Key _key, Value val) { 
    if (h == null) {

      this.lastInserted = new Node(_key, val, RED, 1);
      return lastInserted;
    }

    int cmp = _key.compareTo(h._key);
    if      (cmp < 0) h.left  = put(h.left, _key, val); 
    else if (cmp > 0) h.right = put(h.right, _key, val); 
    else              h.val   = val;

    // fix-up any right-leaning links
    if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
    if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
    if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
    h.N = _size(h.left) + _size(h.right) + 1;

    return h;
  }

  /***************************************************************************
   *  Red-black tree deletion.
   ***************************************************************************/

  /**
   * Removes the smallest key and associated value from the symbol table.
   * @throws NoSuchElementException if the symbol table is empty
   */
  public void deleteMin() {
    if (isEmpty()) throw new NoSuchElementException("BST underflow");

    // if both children of root are black, set root to red
    if (!isRed(root.left) && !isRed(root.right))
      root.colored = RED;

    root = deleteMin(root);
    if (!isEmpty()) root.colored = BLACK;
    // assert check();
  }

  // delete the key-value pair with the minimum key rooted at h
  private Node deleteMin(Node h) { 
    if (h.left == null)
      return null;

    if (!isRed(h.left) && !isRed(h.left.left))
      h = moveRedLeft(h);

    h.left = deleteMin(h.left);
    return balance(h);
  }


  /**
   * Removes the largest key and associated value from the symbol table.
   * @throws NoSuchElementException if the symbol table is empty
   */
  public void deleteMax() {
    if (isEmpty()) throw new NoSuchElementException("BST underflow");

    // if both children of root are black, set root to red
    if (!isRed(root.left) && !isRed(root.right))
      root.colored = RED;

    root = deleteMax(root);
    if (!isEmpty()) root.colored = BLACK;
    // assert check();
  }

  // delete the key-value pair with the maximum key rooted at h
  private Node deleteMax(Node h) { 
    if (isRed(h.left))
      h = rotateRight(h);

    if (h.right == null)
      return null;

    if (!isRed(h.right) && !isRed(h.right.left))
      h = moveRedRight(h);

    h.right = deleteMax(h.right);

    return balance(h);
  }

  /**
   * Removes the specified key and its associated value from this symbol table     
   * (if the key is in this symbol table).    
   *
   * @param  _key the key
   * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
   */
  public void delete(Key _key) { 
    if (_key == null) throw new NullPointerException("argument to delete() is null");
    if (!contains(_key)) return;
    System.out.println("ohyes");
    // if both children of root are black, set root to red
    if (!isRed(root.left) && !isRed(root.right))
      root.colored = RED;

    root = delete(root, _key);
    if (!isEmpty()) root.colored = BLACK;
    // assert check();
  }

  // delete the key-value pair with the given key rooted at h
  private Node delete(Node h, Key _key) { 
    // assert getValue(h, key) != null;

    if (_key.compareTo(h._key) < 0) {
      if (!isRed(h.left) && !isRed(h.left.left))
        h = moveRedLeft(h);
      h.left = delete(h.left, _key);
    } else {
      if (isRed(h.left))
        h = rotateRight(h);
      if (_key.compareTo(h._key) == 0 && (h.right == null))
        return null;
      if (!isRed(h.right) && !isRed(h.right.left))
        h = moveRedRight(h);
      if (_key.compareTo(h._key) == 0) {
        Node x = _min(h.right);
        h._key = x._key;
        h.val = x.val;
        // h.val = getValue(h.right, _min(h.right)._key);
        // h._key = _min(h.right)._key;
        h.right = deleteMin(h.right);
      } else h.right = delete(h.right, _key);
    }
    return balance(h);
  }

  /***************************************************************************
   *  Red-black tree helper functions.
   ***************************************************************************/

  // make a left-leaning link lean to the right
  private Node rotateRight(Node h) {
    // assert (h != null) && isRed(h.left);
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    x.colored = x.right.colored;
    x.right.colored = RED;
    x.N = h.N;
    h.N = _size(h.left) + _size(h.right) + 1;
    return x;
  }

  // make a right-leaning link lean to the left
  private Node rotateLeft(Node h) {
    // assert (h != null) && isRed(h.right);
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    x.colored = x.left.colored;
    x.left.colored = RED;
    x.N = h.N;
    h.N = _size(h.left) + _size(h.right) + 1;
    return x;
  }

  // flip the colors of a node and its two children
  private void flipColors(Node h) {
    // h must have opposite color of its two children
    // assert (h != null) && (h.left != null) && (h.right != null);
    // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
    //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
    h.colored = !h.colored;
    h.left.colored = !h.left.colored;
    h.right.colored = !h.right.colored;
  }

  // Assuming that h is red and both h.left and h.left.left
  // are black, make h.left or one of its children red.
  private Node moveRedLeft(Node h) {
    // assert (h != null);
    // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

    flipColors(h);
    if (isRed(h.right.left)) { 
      h.right = rotateRight(h.right);
      h = rotateLeft(h);
      flipColors(h);
    }
    return h;
  }

  // Assuming that h is red and both h.right and h.right.left
  // are black, make h.right or one of its children red.
  private Node moveRedRight(Node h) {
    // assert (h != null);
    // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
    flipColors(h);
    if (isRed(h.left.left)) { 
      h = rotateRight(h);
      flipColors(h);
    }
    return h;
  }

  // restore red-black tree invariant
  private Node balance(Node h) {
    // assert (h != null);

    if (isRed(h.right))                      h = rotateLeft(h);
    if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
    if (isRed(h.left) && isRed(h.right))     flipColors(h);

    h.N = _size(h.left) + _size(h.right) + 1;
    return h;
  }


  /***************************************************************************
   *  Utility functions.
   ***************************************************************************/

  /**
   * Returns the height of the BST (for debugging).
   * @return the height of the BST (a 1-node tree has height 0)
   */
  public int height() {
    return height(root);
  }
  private int height(Node x) {
    if (x == null) return -1;
    return 1 + Math.max(height(x.left), height(x.right));
  }

  /***************************************************************************
   *  Ordered symbol table methods.
   ***************************************************************************/

  /**
   * Returns the smallest key in the symbol table.
   * @return the smallest key in the symbol table
   * @throws NoSuchElementException if the symbol table is empty
   */
  public Key _min() {
    if (isEmpty()) throw new NoSuchElementException("called _min() with empty symbol table");
    return _min(root)._key;
  } 

  // the smallest key in subtree rooted at x; null if no such key
  public Node _min(Node x) { 
    // assert x != null;
    if (x.left == null) return x; 
    else                return _min(x.left);
  } 

  /**
   * Returns the largest key in the symbol table.
   * @return the largest key in the symbol table
   * @throws NoSuchElementException if the symbol table is empty
   */
  public Key _max() {
    if (isEmpty()) throw new NoSuchElementException("called _max() with empty symbol table");
    return _max(root)._key;
  } 

  // the largest key in the subtree rooted at x; null if no such key
  private Node _max(Node x) { 
    // assert x != null;
    if (x.right == null) return x; 
    else                 return _max(x.right);
  } 


  /**
   * Returns the largest key in the symbol table less than or equal to <tt>_key</tt>.
   * @param _key the key
   * @return the largest key in the symbol table less than or equal to <tt>_key</tt>
   * @throws NoSuchElementException if there is no such key
   * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
   */
  public Key _floor(Key _key) {
    if (_key == null) throw new NullPointerException("argument to _floor() is null");
    if (isEmpty()) throw new NoSuchElementException("called _floor() with empty symbol table");
    Node x = _floor(root, _key);
    if (x == null) return null;
    else           return x._key;
  }    

  // the largest key in the subtree rooted at x less than or equal to the given key
  private Node _floor(Node x, Key _key) {
    if (x == null) return null;
    int cmp = _key.compareTo(x._key);
    if (cmp == 0) return x;
    if (cmp < 0)  return _floor(x.left, _key);
    Node t = _floor(x.right, _key);
    if (t != null) return t; 
    else           return x;
  }

  /**
   * Returns the smallest key in the symbol table greater than or equal to <tt>_key</tt>.
   * @param _key the key
   * @return the smallest key in the symbol table greater than or equal to <tt>_key</tt>
   * @throws NoSuchElementException if there is no such key
   * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
   */
  public Key ceiling(Key _key) {
    if (_key == null) throw new NullPointerException("argument to ceiling() is null");
    if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty symbol table");
    Node x = ceiling(root, _key);
    if (x == null) return null;
    else           return x._key;
  }

  // the smallest key in the subtree rooted at x greater than or equal to the given key
  private Node ceiling(Node x, Key _key) {  
    if (x == null) return null;
    int cmp = _key.compareTo(x._key);
    if (cmp == 0) return x;
    if (cmp > 0)  return ceiling(x.right, _key);
    Node t = ceiling(x.left, _key);
    if (t != null) return t; 
    else           return x;
  }

  /**
   * Return the kth smallest key in the symbol table.
   * @param k the order statistic
   * @return the kth smallest key in the symbol table
   * @throws IllegalArgumentException unless <tt>k</tt> is between 0 and
   *     <em>N</em> &minus; 1
   */
  public Key select(int k) {
    if (k < 0 || k >= _size()) throw new IllegalArgumentException();
    Node x = select(root, k);
    return x._key;
  }

  // the key of rank k in the subtree rooted at x
  private Node select(Node x, int k) {
    // assert x != null;
    // assert k >= 0 && k < _size(x);
    int t = _size(x.left); 
    if      (t > k) return select(x.left, k); 
    else if (t < k) return select(x.right, k-t-1); 
    else            return x;
  } 

  /**
   * Return the number of keys in the symbol table strictly less than <tt>_key</tt>.
   * @param _key the key
   * @return the number of keys in the symbol table strictly less than <tt>_key</tt>
   * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
   */
  public int rank(Key _key) {
    if (_key == null) throw new NullPointerException("argument to rank() is null");
    return rank(_key, root);
  } 

  // number of keys less than key in the subtree rooted at x
  private int rank(Key _key, Node x) {
    if (x == null) return 0; 
    int cmp = _key.compareTo(x._key); 
    if      (cmp < 0) return rank(_key, x.left); 
    else if (cmp > 0) return 1 + _size(x.left) + rank(_key, x.right); 
    else              return _size(x.left);
  } 

  /***************************************************************************
   *  Range count and range search.
   ***************************************************************************/

  /**
   * Returns all keys in the symbol table as an <tt>Iterable</tt>.
   * To iterate over all of the keys in the symbol table named <tt>st</tt>,
   * use the foreach notation: <tt>for (Key _key : st._keys())</tt>.
   * @return all keys in the sybol table as an <tt>Iterable</tt>
   */
  public Iterable<Key> _keys() {
    if (isEmpty()) return new LinkedList<Key>();
    return _keys(_min(), _max());
  }

  /**
   * Returns all keys in the symbol table in the given range,
   * as an <tt>Iterable</tt>.
   * @return all keys in the sybol table between <tt>lo</tt> 
   *    (inclusive) and <tt>hi</tt> (exclusive) as an <tt>Iterable</tt>
   * @throws NullPointerException if either <tt>lo</tt> or <tt>hi</tt>
   *    is <tt>null</tt>
   */
  public Iterable<Key> _keys(Key lo, Key hi) {
    if (lo == null) throw new NullPointerException("first argument to keys() is null");
    if (hi == null) throw new NullPointerException("second argument to keys() is null");

    LinkedList<Key> queue = new LinkedList<Key>();
    // if (isEmpty() || lo.compareTo(hi) > 0) return queue;
    _keys(root, queue, lo, hi);
    return queue;
  } 

  // add the keys between lo and hi in the subtree rooted at x
  // to the queue
  private void _keys(Node x, LinkedList<Key> queue, Key lo, Key hi) { 
    if (x == null) return; 
    int cmplo = lo.compareTo(x._key); 
    int cmphi = hi.compareTo(x._key); 
    if (cmplo < 0) _keys(x.left, queue, lo, hi); 
    if (cmplo <= 0 && cmphi >= 0) queue.add(x._key); 
    if (cmphi > 0) _keys(x.right, queue, lo, hi);
  } 

  /**
   * Returns the number of keys in the symbol table in the given range.
   * @return the number of keys in the sybol table between <tt>lo</tt> 
   *    (inclusive) and <tt>hi</tt> (exclusive)
   * @throws NullPointerException if either <tt>lo</tt> or <tt>hi</tt>
   *    is <tt>null</tt>
   */
  public int _size(Key lo, Key hi) {
    if (lo == null) throw new NullPointerException("first argument to _size() is null");
    if (hi == null) throw new NullPointerException("second argument to _size() is null");

    if (lo.compareTo(hi) > 0) return 0;
    if (contains(hi)) return rank(hi) - rank(lo) + 1;
    else              return rank(hi) - rank(lo);
  }


  /***************************************************************************
   *  Check integrity of red-black tree data structure.
   ***************************************************************************/
  private boolean check() {
    if (!isBST())            System.out.println("Not in symmetric order");
    if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
    if (!isRankConsistent()) System.out.println("Ranks not consistent");
    if (!is23())             System.out.println("Not a 2-3 tree");
    if (!isBalanced())       System.out.println("Not balanced");
    return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
  }

  // does this binary tree satisfy symmetric order?
  // Note: this test also ensures that data structure is a binary tree since order is strict
  private boolean isBST() {
    return isBST(root, null, null);
  }

  // is the tree rooted at x a BST with all keys strictly between min and max
  // (if min or max is null, treat as empty constraint)
  // Credit: Bob Dondero's elegant solution
  private boolean isBST(Node x, Key min, Key max) {
    if (x == null) return true;
    if (min != null && x._key.compareTo(min) <= 0) return false;
    if (max != null && x._key.compareTo(max) >= 0) return false;
    return isBST(x.left, min, x._key) && isBST(x.right, x._key, max);
  } 

  // are the _size fields correct?
  private boolean isSizeConsistent() { 
    return isSizeConsistent(root);
  }
  private boolean isSizeConsistent(Node x) {
    if (x == null) return true;
    if (x.N != _size(x.left) + _size(x.right) + 1) return false;
    return isSizeConsistent(x.left) && isSizeConsistent(x.right);
  } 

  // check that ranks are consistent
  private boolean isRankConsistent() {
    for (int i = 0; i < _size(); i++)
      if (i != rank(select(i))) return false;
    for (Key _key : _keys())
      if (_key.compareTo(select(rank(_key))) != 0) return false;
    return true;
  }

  // Does the tree have no red right links, and at most one (left)
  // red links in a row on any path?
  private boolean is23() { 
    return is23(root);
  }
  private boolean is23(Node x) {
    if (x == null) return true;
    if (isRed(x.right)) return false;
    if (x != root && isRed(x) && isRed(x.left))
      return false;
    return is23(x.left) && is23(x.right);
  } 

  // do all paths from root to leaf have same number of black edges?
  private boolean isBalanced() { 
    int black = 0;     // number of black links on path from root to min
    Node x = root;
    while (x != null) {
      if (!isRed(x)) black++;
      x = x.left;
    }
    return isBalanced(root, black);
  }

  // does every path from the root to a leaf have the given number of black links?
  private boolean isBalanced(Node x, int black) {
    if (x == null) return black == 0;
    if (!isRed(x)) black--;
    return isBalanced(x.left, black) && isBalanced(x.right, black);
  }
}

class DCEL {

  public ArrayList<Face> faces;
  public ArrayList<Point> vertices;
  public ArrayList<Halfedge> halfedges;
  public Face outer;
  private boolean initialized;

  public DCEL() {

    this.initialized = false;
    this.faces = new ArrayList<Face>();
    this.vertices = new ArrayList<Point>();
    this.halfedges = new ArrayList<Halfedge>();
    outer = new Face();

    this.faces.add(outer);
  }

  /**
   * Hooks a new vertex to the desired face.
   * @param f
   * @param h
   * @param v
   */
  public int addVertexAt(int face, int halfedge, Point v) {

    Face f = this.faces.get(face);
    Halfedge h = this.halfedges.get(halfedge);

    Point u = h.target;

    Halfedge h1 = new Halfedge();
    Halfedge h2 = new Halfedge();

    v.h = h2;
    h1.twin = h2;
    h2.twin = h1;
    h1.target = v;
    h2.target = u;
    h1.face = f;
    h2.face = f;
    h1.next = h2;
    h2.next = h.next;
    h1.prev = h;
    h2.prev = h1;
    h.next = h1;
    h2.next.prev = h2;

    this.vertices.add(v);
    this.halfedges.add(h1);
    this.halfedges.add(h2);

    return (this.halfedges.size() - 2);
  }

  public int[] splitFace(int face, Halfedge h, int vertex) {

    Face f = this.faces.get(face);
    Point v = this.vertices.get(vertex);

    return this.splitFace(f, h, v);
  }

  public int[] splitFace(int face, Halfedge h, Point v) {

    Face f = this.faces.get(face);

    return this.splitFace(f, h, v);
  }

  public int[] splitFace(Face f, Halfedge h, int vertex) {

    Point v = this.vertices.get(vertex);

    return this.splitFace(f, h, v);
  }

  /**
   * Splits a face into two new faces, creating a new edge between two of its vertices.
   * @param f
   * @param h
   * @param v
   * @return
   */
  public int[] splitFace(Face f, Halfedge h, Point v) {

    int[] newFaces = new int[2];

    Point u = h.target;

    Face f1 = new Face();
    Face f2 = new Face();

    Halfedge h1 = new Halfedge();
    Halfedge h2 = new Halfedge();

    f1.h = h1;
    f2.h = h2;
    h1.twin = h2;
    h2.twin = h1;
    h1.target = v;
    h2.target = u;
    h2.next = h.next;
    h2.next.prev = h2;
    h1.prev = h;
    h.next = h1;

    Halfedge i = h2;
    i.face = f2;

    while (!i.target.equals(v)) {

      i = i.next;
      i.face = f2;
    }


    h1.next = i.next;
    h1.next.prev = h1;
    i.next = h2;
    h2.prev = i;
    i = h1;

    do {

      i.face = f1;
      i = i.next;
    } while (!i.target.equals(u));



    this.faces.remove(f);

    this.faces.add(f2);
    this.faces.add(f1);
    newFaces[0] = this.faces.size() - 1;
    newFaces[1] = this.faces.size() - 2;

    this.halfedges.add(h1);
    this.halfedges.add(h2);

    return newFaces;
  }

  /**
   * Splits and edge, represented by a halfedge, into two new edges, incident to a given point.
   * @param halfedge
   * @param w
   * @return
   */
  public int[] splitEdge(Halfedge h, Point w) {

    int[] halfedges = new int[2];

    Face f1 = h.face;
    Face f2 = h.twin.face;

    Halfedge h1 = new Halfedge();
    Halfedge h2 = new Halfedge();

    Halfedge k1 = new Halfedge();
    Halfedge k2 = new Halfedge();

    h1.face = f1;
    h1.next = h2;
    h1.prev = h.prev;
    h1.twin = k2;
    h1.target = w;

    h2.face = f1;
    h2.next = h.next;
    h2.prev = h1;
    h2.twin = k1;
    h2.target = h.target;

    k1.face = f2;
    k1.next = k2;
    k1.prev = h.twin.prev;
    k1.twin = h2;
    k1.target = w;

    k2.face = f2;
    k2.next = h.twin.next;
    k2.prev = k1;
    k2.twin = h1;
    k2.target = h.twin.target;

    h1.prev.next = h1;
    k1.prev.next = k1;

    h2.next.prev = h2;
    k2.next.prev = k2;

    w.h = h2;

    // copy k2 into h.twin
    h.twin.next = k2.next;
    h.twin.prev = k2.prev;
    h.twin.twin = k2.twin;
    h.twin.target = k2.target;

    // copy h1 into h
    h.next = h2.next;
    h.prev = h2.prev;
    h.twin = h2.twin;
    h.target = h2.target;

    this.halfedges.add(h1);
    this.halfedges.add(k1);

    int size = this.halfedges.size();

    halfedges[0] = size - 2;
    halfedges[1] = size - 1;

    this.vertices.add(w);

    return halfedges;
  }

  /**
   * Initializes the dcel with a single edge.
   * @param p1
   * @param p2
   * @return
   */
  public void initialize(ArrayList<Point> points) {

    if (initialized) return;
    if (points.size() < 3) throw new IllegalArgumentException("Provide at least 3 vertices!");

    Point p1 = points.get(0);
    Point p2 = points.get(1);

    Halfedge h1 = new Halfedge();
    Halfedge h2 = new Halfedge();
    Face f = this.outer;

    p1.h = h1;
    p2.h = h2;

    h1.target = p2;
    h1.face = f;
    h1.twin = h2;
    h1.next = h2;
    h1.prev = h2;

    h2.target = p1;
    h2.face = f;
    h2.twin = h1;
    h2.next = h1;
    h2.prev = h1;

    f.h = h1;

    this.vertices.add(p1);
    this.vertices.add(p2);
    this.halfedges.add(h1);
    this.halfedges.add(h2);

    int he = this.halfedges.size() - 2;

    for (int i = 2; i < points.size(); i++) {

      p2 =  points.get(i);
      he = this.addVertexAt(0, he, p2);
    }

    this.splitFace(0, this.halfedges.get(he), 0);
    p1.h = p1.h.prev.twin;

    for (Point v : this.vertices)
      v.h = v.h.prev.twin;

    this.initialized = true;
  }
}


class Edge {

  public Point a;
  public Point b;
  public Point helper;

  public Edge(Point a, Point b) {

    this.a = a;
    this.b = b;

  }

  public Edge(double x, double y, double d, double e) {

    this.a = new Point(x, y);
    this.b = new Point(d, e);

  }
  
  public boolean intersectsLine(Edge e) {
    
    Turn ea = new Turn(e, this.a);
    Turn eb = new Turn(e, this.b);
    
    if((eb.value >= 0 && ea.value <= 0) ||
        (eb.value <= 0 && ea.value >= 0)) 
      return true;
    return false;
    
  }

  public boolean intersectsRay(Edge e) {

    Turn ea = new Turn(e, this.a);
    Turn eb = new Turn(e, this.b);

    if((eb.value >= 0 && ea.value <= 0) ||
        (eb.value <= 0 && ea.value >= 0)) {
      
      CrossProduct aEaB = new CrossProduct(this.a, this.b, this.a, e.a);
      DotProduct EaEbP = new DotProduct(this.a, this.b, new Point(0, 0), new Point(-(e.b.y-e.a.y), (e.b.x-e.a.x)));
      
      double t1 = aEaB.value/EaEbP.value;
      
      if(t1 >= 0) return true;
      
//      Triangle abEb = new Triangle(a, b, e.b);
//
//      boolean strictlyContains = abEb.strictlyContains(e.a);
//
//      if(strictlyContains) return false;
//      else return true;

    }

    return false;
  }

  public boolean intersectsRay(Point a, Point b) {

    return this.intersectsRay(new Edge(a, b));
  }

  public boolean intersectsRay(float ax, float ay, float bx, float by) {

    return this.intersectsRay(new Point(ax, ay), new Point(bx, by));
  }
  
  public void flip() {
    
    Point temp = this.a;
    this.a = this.b;
    this.b = temp;
    
  }
  
  public boolean equals(Object obj) {
    
    if (obj == null) {
          return false;
      }
      final Edge other = (Edge) obj;
      
     if((this.a.equals(other.a) && this.b.equals(other.b)) ||
         (this.a.equals(other.b) && this.b.equals(other.a)))
       return true;
     
     return false;
  }


}

class Face {

  public Point n;
  public Halfedge h;

  public Face(Halfedge h) {

    this.h = h;
  }


  Face() {
  }

  public boolean contains(Point p) {

    Halfedge h = this.h;
    int count = 0;
    boolean intersectsPrevious = false;
    do {

      Edge e = h.getEdge();

      if (e.intersectsRay(new Edge(p.x, p.y, p.x+1, p.y))) {

        if (!intersectsPrevious) {
          intersectsPrevious = true;
          count++;
        } else {

          Turn pP1eA = new Turn(p, new Point(p.x+1, p.y), e.a);
          if (pP1eA.value != 0)
            count++;
        }
      } else if (intersectsPrevious)
        intersectsPrevious = false;

      h = h.next;
    } while (h.target != this.h.target);

    if (count % 2 == 0) return false;
    return true;
  }
}



class Halfedge {

  public Point target;
  public Face face;
  public Halfedge twin;
  public Halfedge next;
  public Halfedge prev;

  public Point helper;
  
  public Halfedge(Point target, Face face, Halfedge twin, Halfedge next, Halfedge prev) {

    this.target = target;
    this.face = face;
    this.twin = twin;
    this.next = next;
    this.prev = prev;
  }

  public Halfedge() {
  }

  public Edge getEdge() {
    
    Point a = new Point(this.twin.target.x, this.twin.target.y, this.twin.target.h);
    Point b = new Point(this.target.x, this.target.y, this.target.h);

    Edge e= new Edge(a, b);
    
    e.helper = this.helper;
    
    return e;

  }

  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    final Halfedge other = (Halfedge) obj;

    if(this.target.equals(other.target) && this.getEdge().equals(other.getEdge()))
      return true;
    
    return false;
  }


}



class Point implements Cloneable{

  public static final double EPSILON = 0.00000000001;
  public double x;
  public double y;
  public double z;
  public Halfedge h;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
    this.z = 0;
  }


  public Point(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Point(Point a) {

    this.x = a.x;
    this.y = a.y;
    this.z = a.z;
  }

  public Point(double x, double y, Halfedge h) {
    this.x = x;
    this.y = y;
    this.h = h;
  }

  public Point(Point a, Halfedge h) {

    this.x = a.x;
    this.y = a.y;
    this.h = h;
  }


  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    final Point other = (Point) obj;

    if ((Math.abs(this.x - other.x) > Point.EPSILON) || Math.abs(this.y - other.y) > Point.EPSILON) {
      return false;
    }
    return true;

  }
  
  public Object clone() throws CloneNotSupportedException {
    
    return super.clone();
    
  }


}