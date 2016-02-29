public class Halfedge {

	public Point target;
	public Face face;
	public Halfedge twin;
	public Halfedge next;
	public Halfedge prev;

	public Point helper;
	
	public Halfedge(Point target, Face face, Halfedge twin, Halfedge next, Halfedge prev) {

		this.target = target;
		this.face = face;
		this.twin = twin;
		this.next = next;
		this.prev = prev;
	}

	public Halfedge() {
	}

	public Edge getEdge() {
		
		Point a = new Point(this.twin.target.x, this.twin.target.y, this.twin.target.h);
		Point b = new Point(this.target.x, this.target.y, this.target.h);

		Edge e= new Edge(a, b);
		
		e.helper = this.helper;
		
		return e;

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