import java.util.ArrayList;
import java.util.LinkedList;

abstract class AttractionRegion {

	/**
	 * The complete algorithm. To be overridden in subclasses.
	 */
	public abstract void computeAttractionRegion();

	/**
	 * Sort the vertices of the polygon about an angle around the query beacon point.
	 * @param b
	 * @param vertices
	 * @return
	 */
	private ArrayList<Point> sortVertices(Point b, ArrayList<Point> vertices) {

		RadialSort sorted = new RadialSort(b, vertices);

		return sorted.result;

	}

	/**
	 * Compute the Ray vertices defined by the split vertices of the polygon with respect to the query beacon point.
	 * @param b
	 * @param vertices
	 * @param edges
	 */
	private void computeRayVertices(Point b, ArrayList<Point> vertices, ArrayList<Edge> edges) {

		RedBlackBST status = new RedBlackBST<Key, Edge>();

		LinkedList<Edge> queue = new LinkedList<Edge>();

		initializeStatus(status, b, vertices, edges, queue);

		for(int i=1; i < vertices.size(); i++) {

			updateStatus(status, b, vertices.get(i));

		}

	}

	private void computeRayVertex() {}

	private void updateStatus(RedBlackBST<Key, Edge> status, Point b, Point p) {
		
		Point next = p.h.target;
		Point prev = p.h.twin.next.target;
		
		Action actionNext = decideAction(b, p, next);
		Action actionPrev = decideAction(b, p, prev);
		
		if(actionNext == Action.ADD) 
			this.addEdge(status, b, p, next);
		else if(actionNext == Action.DELETE)
			this.deleteEdge(status, b, p, next);
		
		if(actionPrev == Action.ADD) 
			this.addEdge(status, b, p, prev);
		else if(actionPrev == Action.DELETE)
			this.deleteEdge(status, b, p, prev);
		
	}

	/**
	 * Initialize the status of the rotational sweep.
	 * @param b
	 * @param vertices
	 * @param edges
	 * @param status
	 * @param queue
	 */
	private void initializeStatus(RedBlackBST<Key, Edge> status, Point b, ArrayList<Point> vertices, ArrayList<Edge> edges, LinkedList<Edge> queue) {

		Point p1 = vertices.get(0);

		for(int i = 0; i < edges.size(); i++) {

			Edge e = edges.get(i);

			if(e.intersectsRay(b, p1)) { // add it to the status

				Turn bEab = new Turn(b, e.a, e.b);
				Turn bEba = new Turn(b, e.b, e.a);
				
				Turn bpEa = new Turn (b, p1, e.a);
				Turn bpEb = new Turn (b, p1, e.b);

				if((bpEa.value == 0 && bEab.value > 0) ||
						(bpEb.value == 0 && bEba.value > 0) ||
				        (bpEa.value != 0 && bpEb.value != 0)) {

					Key k = new Key(b, e.a, e);
					status.put(k, e);

				}


			}

		}

	}

	private Action decideAction(Point b, Point p, Point q) {
		
		Turn bpq = new Turn(b, p, q);
		
		if(bpq.value > 0)
			return Action.ADD;
		else if(bpq.value < 0)
			return Action.DELETE;
		
		return Action.NOTHING;

	}
	
	private void addEdge(RedBlackBST<Key, Edge> status, Point b, Point p, Point q) {
		
	}
	
	private void deleteEdge(RedBlackBST<Key, Edge> status, Point b, Point p, Point q) {}
	

}
