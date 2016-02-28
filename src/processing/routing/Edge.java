/*
 * Represents a polygon's edge.
 * 
 */

public class Edge {

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

			Triangle abEb = new Triangle(a, b, e.b);

			boolean strictlyContains = abEb.strictlyContains(e.a);

			if(strictlyContains) return false;
			else return true;

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
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
	        return false;
	    }
	    if (!Edge.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
	    final Edge other = (Edge) obj;
	    
	   if((this.a.equals(other.a) && this.b.equals(other.b)) ||
			   (this.a.equals(other.b) && this.b.equals(other.a)))
		   return true;
	   
	   return false;
	}


}