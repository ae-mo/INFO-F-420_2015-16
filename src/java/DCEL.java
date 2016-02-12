import java.util.ArrayList;

class DCEL {

	ArrayList<Face> faces;
	ArrayList<Point> vertices;
	ArrayList<Halfedge> halfedges;
	Face outer;
	private boolean initialized;

	DCEL() {

		this.initialized = false;
		this.faces = new ArrayList<Face>();
		this.vertices = new ArrayList<Point>();
		this.halfedges = new ArrayList<Halfedge>();
		outer = new Face();

		this.faces.add(outer);
	}

	/**
	 * Hooks a new vertex to the desired face.
	 * @param f
	 * @param h
	 * @param v
	 */
	public int addVertexAt(int face, int halfedge, Point v) {

		Face f = this.faces.get(face);
		Halfedge h = this.halfedges.get(halfedge);

		Point u = h.target;

		Halfedge h1 = new Halfedge();
		Halfedge h2 = new Halfedge();

		v.h = h2;
		h1.twin = h2;
		h2.twin = h1;
		h1.target = v;
		h2.target = u;
		h1.face = f;
		h2.face = f;
		h1.next = h2;
		h2.next = h.next;
		h1.prev = h;
		h2.prev = h1;
		h.next = h1;
		h2.next.prev = h2;

		this.vertices.add(v);
		this.halfedges.add(h1);
		this.halfedges.add(h2);

		return (this.vertices.size() - 1);

	}

	/**
	 * Splits a face into two new faces, creating a new edge between two of its vertices.
	 * @param f
	 * @param h
	 * @param v
	 * @return
	 */
	public int[] splitFace(int face, int halfedge, int vertex) {

		Halfedge h = this.halfedges.get(halfedge);
		Point v = this.vertices.get(vertex);
		int[] newFaces = new int[2];

		Point u = h.target;

		Face f1 = new Face();
		Face f2 = new Face();

		Halfedge h1 = new Halfedge();
		Halfedge h2 = new Halfedge();

		f1.h = h1;
		f2.h = h2;
		h1.twin = h2;
		h2.twin = h1;
		h1.target = v;
		h2.target = u;
		h2.next = h.next;
		h2.next.prev = h2;
		h1.prev = h;
		h.next = h1;

		Halfedge i = h2;
		i.face = f2;

		while (i.target.x != v.x || i.target.y != v.y) {

			i = i.next;
			i.face = f2;
		}

		h1.next = i.next;
		h1.next.prev = h1;
		i.next = h2;
		h2.prev = i;
		i = h1;

		do {

			i.face = f1;
			i = i.next;
		} while (i.target.x != u.x || i.target.y != u.y);

		this.faces.remove(face);
		this.faces.add(f1);
		newFaces[0] = this.faces.size() - 1;
		this.faces.add(f2);
		newFaces[1] = this.faces.size() - 1;

		this.halfedges.add(h1);
		this.halfedges.add(h2);

		return newFaces;
	}

	public int initialize(Point p1, Point p2) {
		
		if(initialized) return -1;
		
		Halfedge h1 = new Halfedge();
		Halfedge h2 = new Halfedge();
		Face f = this.outer;

		p1.h = h1;
		p2.h = h2;

		h1.target = p2;
		h1.face = f;
		h1.twin = h2;
		h1.next = h2;
		h1.prev = h2;

		h2.target = p1;
		h2.face = f;
		h2.twin = h1;
		h2.next = h1;
		h2.prev = h1;

		f.h = h1;
		
		this.vertices.add(p1);
		this.vertices.add(p2);
		this.halfedges.add(h1);
		this.halfedges.add(h2);
		
		this.initialized = true;
		
		return (this.halfedges.size() - 2);

	}
}
