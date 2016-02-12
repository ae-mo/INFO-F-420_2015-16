/*
 * Represents a polygon's edge.
 * 
 */

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