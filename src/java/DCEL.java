import java.util.ArrayList;

class DCEL {

	ArrayList<Face> faces;
	ArrayList<Point> vertices;
	ArrayList<Halfedge> halfedges;
	Face outer;

	DCEL() {

		this.faces = new ArrayList<Face>();
		this.vertices = new ArrayList<Point>();
		this.halfedges = new ArrayList<Halfedge>();
		outer = new Face();
	}

	/**
	 * Hooks a new vertex to the desired face.
	 * @param f
	 * @param h
	 * @param v
	 */
	public static void addVertexAt(Face f, Halfedge h, Point v) {

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

	}

	/**
	 * Splits a face into two new faces, creating a new edge between two of its vertices.
	 * @param f
	 * @param h
	 * @param v
	 * @return
	 */
	public static ArrayList<Face> splitFace(Face f, Halfedge h, Point v) {

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

		ArrayList<Face> newFaces = new ArrayList<Face>();
		newFaces.add(f1);
		newFaces.add(f2);

		return newFaces;
	}
}
