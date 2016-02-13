
public class Halfedge {

	Point target;
	Face face;
	Halfedge twin;
	Halfedge next;
	Halfedge prev;

	Halfedge(Point target, Face face, Halfedge twin, Halfedge next, Halfedge prev) {

		this.target = target;
		this.face = face;
		this.twin = twin;
		this.next = next;
		this.prev = prev;
	}

	Halfedge() {
	}

	public Edge getEdge() {
		
		Point a = new Point(this.twin.target.x, this.twin.target.y);
		Point b = new Point(this.target.x, this.target.y);

		return new Edge(a, b);

	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		if (!Halfedge.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Halfedge other = (Halfedge) obj;

		if(this.target.equals(other.target) && this.getEdge().equals(other.getEdge()))
			return true;
		
		return false;
	}


}
