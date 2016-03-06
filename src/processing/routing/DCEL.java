import java.util.ArrayList;

public class DCEL {

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
    
    if(initialized) return;
    if(points.size() < 3) throw new IllegalArgumentException("Provide at least 3 vertices!");
    
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

    for(int i = 2; i < points.size(); i++) {

      p2 =  points.get(i);
      he = this.addVertexAt(0, he, p2);
    }

    this.splitFace(0, this.halfedges.get(he), 0);
    p1.h = p1.h.prev.twin;
    
    for(Point v: this.vertices)
      v.h = v.h.prev.twin;
    
    this.initialized = true;


  }
}