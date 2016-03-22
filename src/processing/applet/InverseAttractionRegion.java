
import java.util.ArrayList;

public class InverseAttractionRegion {

  public double SCREEN_X, SCREEN_Y;
  public ArrayList<Face> faces;

  public InverseAttractionRegion(ArrayList<Point> points, Point p) {

    this.faces = new ArrayList<Face>();
    SCREEN_X = 1068;
    SCREEN_Y = 600;
    try {
      this.computeInverseAttractionRegion(points, p);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }

  }

  private void computeInverseAttractionRegion(ArrayList<Point> points, Point p) throws CloneNotSupportedException {

    DCEL polygon = new DCEL();
    ArrayList<Edge> edges = new ArrayList<Edge>();
    SPT spt;

    polygon.initialize(this.clonePoints(points));
    spt = new SPT(p, this.clonePoints(points));

    edges.addAll(spt.edges);

    Face f = polygon.faces.get(1);
    Halfedge h = f.h;

    do {

      edges.add(new Edge(new Point(h.twin.target.x, h.twin.target.y, null), new Point(h.target.x, h.target.y, null)));
      h = h.next;
    } while (h.target != f.h.target);
    
    Point prev, next;
    Turn c;
    
    for(Point v: polygon.vertices) {
      
      prev = v.h.prev.twin.target;
      next = v.h.target;
      c = new Turn(prev, v, next);
      
      if(c.value < 0) {
        
        edges.add(findPerpendicularEdge(new Edge(v, prev)));
        edges.add(findPerpendicularEdge(new Edge(v, next)));
        
      }
      
    }
    
    LineArrangement Ap = new LineArrangement(edges);
    AttractionRegion attr;
    
    for(int i = 1; i < Ap.faces.size(); i ++) {
      
      f = Ap.faces.get(i);
      Point candidate = this.findPointInConvexFace(f);
      
      if(polygon.faces.get(1).contains(candidate)) {
        
        attr = new AttractionRegion(candidate, this.clonePoints(points));
        
        if(attr.face.contains(p))
          this.faces.add(f);
      }
      
    }

  }

  private ArrayList<Point> clonePoints(ArrayList<Point> points) throws CloneNotSupportedException {

    ArrayList<Point> clone = new ArrayList<Point>(points.size());
    for(Point item: points) clone.add((Point) item.clone());
    return clone;

  }
  
  private Edge findPerpendicularEdge(Edge e) {
    
    double newX = e.a.x -(e.b.y - e.a.y);
    double newY = e.a.y +(e.b.x - e.a.x);
    
    return new Edge(new Point(e.a.x, e.a.y, null), new Point(newX, newY, null));
  }
  
  private Point findPointInConvexFace(Face f) {
    
    Halfedge h = f.h;
    Point prev, next;
    Turn t;
    
    do {
      
      prev = h.prev.twin.target;
      next = h.target;
      t = new Turn(prev, h.twin.target, next);
      
      if(t.value > 0) {
        
        Triangle tr = new Triangle(prev, h.twin.target, next);
        
        return tr.findCentroid();
      }
      
      h = h.next;
    } while (f.h.target != h.target);
    
    return null;
  }

}