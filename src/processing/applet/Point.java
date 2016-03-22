
public class Point implements Cloneable{

	public static final double EPSILON = 0.01;
	public double x;
	public double y;
	public Halfedge h;

	public Point(double x, double y, Halfedge h) {
		this.x = x;
		this.y = y;
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