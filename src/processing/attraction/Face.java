public class Face {

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
    
    do {
      
      Edge e = h.getEdge();
      
      if(e.intersectsRay(new Edge(p.x, p.y, p.x+1, p.y)))
        count++;
      
      h = h.next;
      
    } while(h.target != this.h.target);
    
    if(count % 2 == 0) return false;
    return true;
    
  }
}