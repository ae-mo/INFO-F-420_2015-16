public class Point implements Cloneable{

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


  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (!Point.class.isAssignableFrom(obj.getClass())) {
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