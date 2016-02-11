public class Edge {

  Point a, b;
  
  Edge(Point a, Point b) {
  
    this.a = a;
    this.b = b;
    
  }
  
  Edge(float x1, float y1, float x2, float y2) {
  
    this.a = new Point(x1, y1);
    this.b = new Point(x2, y2);
    
  }

}