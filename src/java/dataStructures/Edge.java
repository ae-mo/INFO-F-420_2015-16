package dataStructures;
import operations.Turn;
import operations.CrossProduct;
import operations.DotProduct;

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

		this.a = new Point(x, y, null);
		this.b = new Point(d, e, null);

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

			CrossProduct aEaB = new CrossProduct(this.a, this.b, this.a, e.a);
			DotProduct EaEbP = new DotProduct(this.a, this.b, new Point(0, 0, null), new Point(-(e.b.y-e.a.y), (e.b.x-e.a.x), null));

			double t1 = aEaB.value/EaEbP.value;

			if(t1 >= 0) return true;

			//			Triangle abEb = new Triangle(a, b, e.b);
			//
			//			boolean strictlyContains = abEb.strictlyContains(e.a);
			//
			//			if(strictlyContains) return false;
			//			else return true;

		}

		return false;
	}

	public boolean intersectsRay(Point a, Point b) {

		return this.intersectsRay(new Edge(a, b));
	}

	public boolean intersectsRay(float ax, float ay, float bx, float by) {

		return this.intersectsRay(new Point(ax, ay, null), new Point(bx, by, null));
	}

	public boolean intersectsEdge(Edge e) {

		Turn a1b1b2 = new Turn(this.a, this.b, e.b);
		Turn a1b1a2 = new Turn(this.a, this.b, e.a);
		Turn a2b2a1 = new Turn(e.a, e.b, this.a);
		Turn a2b2b1 = new Turn(e.a, e.b, this.b);

		if(((a1b1b2.value >= 0 && a1b1a2.value <= 0)||(a1b1b2.value <= 0 && a1b1a2.value >= 0))
				&&((a2b2a1.value >= 0 && a2b2b1.value <= 0)||(a2b2a1.value <= 0 && a2b2b1.value >= 0))) {
			
			if(a1b1b2.value == 0 && a1b1a2.value == 0) {
				if(this.isInBoundingBox(this.a, e) || this.isInBoundingBox(this.b, e))
					return true;
				else return false;
			}
			else return true;

		}
			
		return false;
	}

	public boolean strictlyIntersectsEdge(Edge e) {

		Turn a1b1b2 = new Turn(this.a, this.b, e.b);
		Turn a1b1a2 = new Turn(this.a, this.b, e.a);
		Turn a2b2a1 = new Turn(e.a, e.b, this.a);
		Turn a2b2b1 = new Turn(e.a, e.b, this.b);

		if(((a1b1b2.value > 0 && a1b1a2.value < 0)||(a1b1b2.value < 0 && a1b1a2.value > 0))
				&&((a2b2a1.value > 0 && a2b2b1.value < 0)||(a2b2a1.value < 0 && a2b2b1.value > 0)))
			return true;

		return false;
	}

	public void flip() {

		Point temp = this.a;
		this.a = this.b;
		this.b = temp;

	}

	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		final Edge other = (Edge) obj;

		if((this.a.equals(other.a) && this.b.equals(other.b)) ||
				(this.a.equals(other.b) && this.b.equals(other.a)))
			return true;

		return false;
	}

	public boolean isInBoundingBox(Point p, Edge d) {

		Point a, b;

		if(d.b.y >= d.b.y) {
			b = d.b;
			a = d.a;
		}
		else {
			b = d.a;
			a = d.b;
		}

		if(p.y <= b.y && p.y >= a.y) {

			if(b.x >= a.x) {

				if(p.x <= b.x && p.x >= a.x)
					return true;
				return false;

			}
			else {

				if(p.x >= b.x && p.x <= a.x)
					return true;
				return false;

			}
		}

		return false;

	}


}