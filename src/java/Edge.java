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

		Turn abEa = new Turn(this, e.a);
		Turn ea = new Turn(e, a);
		Turn eb = new Turn(e, b);

		return true;
	}

	public boolean intersectsRay(Point a, Point b) {
		
		return this.intersectsRay(new Edge(a, b));
	}
	
	public boolean intersectsRay(float ax, float ay, float bx, float by) {
		
		return this.intersectsRay(new Point(ax, ay), new Point(bx, by));
	}

}