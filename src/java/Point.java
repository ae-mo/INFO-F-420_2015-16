public class Point {

  float x, y, z;

  Point(float x, float y) {
    this.x = x;
    this.y = y;
    this.z = 0;
  }


  Point(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  Point(Point a) {

    this.x = a.x;
    this.y = a.y;
    this.z = a.z;
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