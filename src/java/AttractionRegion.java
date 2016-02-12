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

		initializeStatus(b, vertices, edges, status, queue);

	}

	private void computeRayVertex() {}

	private void updateStatus() {}

	/**
	 * Initialize the status of the rotational sweep.
	 * @param b
	 * @param vertices
	 * @param edges
	 * @param status
	 * @param queue
	 */
	private void initializeStatus(Point b, ArrayList<Point> vertices, ArrayList<Edge> edges, RedBlackBST<Key, Edge> status, LinkedList<Edge> queue) {

		Point p1 = vertices.get(0);

		for(Edge e: edges) {

			if(e.intersectsRay(b, p1)); // add it to the status

		}

	}

}
