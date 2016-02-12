public class Point {

  double x, y, z;
  Halfedge h;

  Point(double x, double y) {
    this.x = x;
    this.y = y;
    this.z = 0;
  }


  Point(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  Point(Point a) {

    this.x = a.x;
    this.y = a.y;
    this.z = a.z;
  }
  
  Point(double x, double y, Halfedge h) {
	    this.x = x;
	    this.y = y;
	    this.h = h;
	  }

	  Point(Point a, Halfedge h) {

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
    
    if (this.x != other.x || this.y != other.y) {
        return false;
    }
    return true;
	
}

  
}