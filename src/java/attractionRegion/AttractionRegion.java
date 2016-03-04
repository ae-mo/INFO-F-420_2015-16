package attractionRegion;
import java.util.ArrayList;

import dataStructures.DCEL;
import dataStructures.Edge;
import dataStructures.Halfedge;
import dataStructures.Point;
import dataStructures.RedBlackBST;
import operations.CrossProduct;
import operations.DotProduct;
import operations.RadialSort;
import operations.Turn;

public class AttractionRegion {

	DCEL dcel;
	Point b;

	public AttractionRegion(Point b, DCEL dcel) {

		this.dcel = dcel;
		this.b = b;
		this.computeAttractionRegion();

	}

	public void computeAttractionRegion() {

		ArrayList<Point> sortedVertices = this.sortVertices(b, this.dcel.vertices);

		this.computeRayVertices(b, sortedVertices, this.dcel);

	}

	/**
	 * Sort the vertices of the polygon about an angle around the query beacon point.
	 * @param b
	 * @param vertices
	 * @return
	 */
	protected ArrayList<Point> sortVertices(Point b, ArrayList<Point> vertices) {

		RadialSort sorted = new RadialSort(b, vertices);

		return sorted.result;

	}

	/**
	 * Compute the Ray vertices defined by the split vertices of the polygon with respect to the query beacon point.
	 * @param b
	 * @param vertices
	 * @param edges
	 */
	protected void computeRayVertices(Point b, ArrayList<Point> vertices, DCEL dcel) {

		for(Point p: dcel.vertices) {
			p.h = p.h.prev.twin;
		}

		RedBlackBST<Key, Edge> status = new RedBlackBST<Key, Edge>();

		this.initializeStatus(status, dcel, b, vertices);

		for(int i=1; i < vertices.size(); i++) {

			Point p = vertices.get(i);

			int type = this.isSplitVertex(b, p);

			this.updateStatus(status, b, p, type);

			if(type > 0)  {
				System.out.println("Split vertex found!: " + p.x + ", " + p.y);
				this.computeRayVertex(status, dcel, b, p, type);

			}

		}

	}

	/**
	 * Given a split vertex, computes the corresponding ray vertex.
	 * @param status
	 * @param b
	 * @param p
	 * @return
	 */
	private void computeRayVertex(RedBlackBST<Key, Edge> status, DCEL dcel, Point b, Point p, int type) {

		Point next = p.h.target;
		Point prev = p.h.prev.prev.target;

		System.out.println("Size: " + status._size());

		Point p2;
		
		switch(type) {
		
		case 1:
			p2 = prev;
			break;
			
		case 2:
			p2 = prev;
			break;
			
		case 3:
			p2 = next;
			break;
			
			default:
				p2 = next;
		
		}
		
		Key k = new Key(b, new Edge(p, p2));
		
		int rank = status.rank(k);
		
		if(status.contains(k)) rank++;
		
		Key k2 = status.select(rank);

		Point rayVertex = this.computeLineLineIntersection(b.x, b.y, p.x, p.y, k2.e.a.x, k2.e.a.y, k2.e.b.x, k2.e.b.y);


		Edge old = k2.e;

		Turn pEab = new Turn (p, old.a, old.b);
		if(pEab.value > 0) p2 = old.b; 
		else p2 = old.a; 

		Key _new = this.createKey(b, rayVertex, p2, 1);

		System.out.println();
		System.out.println("UPDATING KEY");

		status.delete(k2);
		System.out.println("REPLACING WITH NEW KEY");
		status.put(_new, _new.e);

		System.out.println("END OF UPDATE");

		System.out.println("Halfedge to divide: " + old.b.h.target.x + ", " +  old.b.h.target.y+ "; "+ old.b.h.twin.target.x + ", " + old.b.h.twin.target.y);
		int[] newH = dcel.splitEdge(p2.h.prev, rayVertex);
		System.out.println("Ray vertex halfedge's NEW target: " + rayVertex.h.target.x + ", " + + rayVertex.h.target.y);

		System.out.println();
		System.out.println("Splitting using halfedge: " + p.h.target.x + ", " +  p.h.target.y+ "; "+ p.h.twin.target.x + ", " + p.h.twin.target.y);
		System.out.println("ray vertex: " + rayVertex.x + ", " + rayVertex.y);

		dcel.splitFace(p.h.face, p.h.prev, dcel.vertices.size()-1);

	}

	private void updateStatus(RedBlackBST<Key, Edge> status, Point b, Point p, int type) {

		Point prev = p.h.prev.prev.target;
		Point next = p.h.target;

		Action actionNext = decideAction(b, p, next);
		Action actionPrev = decideAction(b, p, prev);


		if(actionNext == Action.DELETE)
			this.deleteEdge(status, b, p, next);
		if(actionPrev == Action.DELETE)
			this.deleteEdge(status, b, p, prev);

		if(actionNext == Action.ADD) 
			this.addEdge(status, b, p, next);
		if(actionPrev == Action.ADD) 
			this.addEdge(status, b, p, prev);


	}


	/**
	 * Initialize the status of the rotational sweep.
	 * @param b
	 * @param vertices
	 * @param edges
	 * @param status
	 * @param queue
	 */
	private void initializeStatus(RedBlackBST<Key, Edge> status, DCEL dcel, Point b, ArrayList<Point> vertices) {

		Point p1 = vertices.get(0);

		Halfedge h = p1.h;
		Edge e = h.getEdge();

		System.out.println();
		System.out.println("*******INITIALIZATION***********");

		do {

			System.out.println();
			System.out.println("Ray: " + b.x + ", " + b.y+ "; "+p1.x + ", " + p1.y);
			System.out.println("Edge: " + e.a.x + ", " + e.a.y+ "; "+e.b.x + ", " + e.b.y);

			Point p2 = this.computePointOnLine(b,  p1, 1.1);

			if(e.intersectsRay(p1, p2)) { // add it to the status

				Turn bEab = new Turn(b, e.a, e.b);
				Turn bEba = new Turn(b, e.b, e.a);

				Turn bpEa = new Turn (b, p1, e.a);
				Turn bpEb = new Turn (b, p1, e.b);

				if((bpEa.value == 0 && bEab.value > 0) ||
						(bpEb.value == 0 && bEba.value > 0) ||
						(bpEa.value != 0 && bpEb.value != 0)) {

					Key k = new Key(b, new Edge(e.a, e.b));
					status.put(k, e);
					System.out.println("Added");

				}


			}

			h = h.next;
			e = h.getEdge();

		} while(!h.twin.target.equals(p1));

		int type = this.isSplitVertex(b, p1);

		if(type > 0)  {
			System.out.println("Split vertex found!: " + p1.x + ", " + p1.y);
			this.computeRayVertex(status, dcel, b, p1, type);

		}


	}

	private Action decideAction(Point b, Point p, Point q) {

		Turn bpq = new Turn(b, p, q);

		System.out.println();
		System.out.println("Deciding for Edge: " + p.x + ", " + p.y+ "; "+q.x + ", " + q.y);

		if(bpq.value > 0) {
			System.out.println("added");
			return Action.ADD;
		}

		else if(bpq.value < 0) {
			System.out.println("deleted");
			return Action.DELETE;
		}


		return Action.NOTHING;

	}

	private void addEdge(RedBlackBST<Key, Edge> status, Point b, Point p, Point q) {

		Key k = createKey(b, p, q, 1);
		System.out.println();
		System.out.println("ADDITION");
		System.out.println("Size: " + status._size());
		status.put(k, new Edge(p, q));
		System.out.println("Size: " + status._size());

	}

	private void deleteEdge(RedBlackBST<Key, Edge> status, Point b, Point p, Point q) {

		Key k = createKey(b, p, q, 2);
		System.out.println("DELETION");
		System.out.println("Size: " + status._size());
		status.delete(k);
		System.out.println("Size: " + status._size());

	}

	private Key createKey(Point b, Point p, Point q, int first) {

		if(first == 2) {

			Point temp = p;
			p = q;
			q = temp;

		}


		return new Key(b, new Edge(p, q));

	}

	private int isSplitVertex(Point b, Point p) {

		Point prev = p.h.prev.prev.target;
		Point next = p.h.target;


		Turn prevPNext = new Turn(prev, p, next);

		if(prevPNext.value >= 0)
			return -1;

		CrossProduct bp_pNext = new CrossProduct(b, p, p, next);
		CrossProduct bp_prevP = new CrossProduct(b, p, prev, p);

		DotProduct bp_pNext_d = new DotProduct(b, p, p, next);
		DotProduct bp_prevP_d = new DotProduct(b, p, prev, p);

		if(bp_pNext.value < 0 && bp_prevP.value < 0) {

			if(bp_pNext_d.value < 0 && bp_prevP_d.value > 0)
				return 1;

		}
		else if(bp_pNext.value > 0 && bp_prevP.value < 0) {

			if(bp_prevP_d.value > 0)
				return 2;

		}
		else if(bp_pNext.value < 0 && bp_prevP.value > 0) {

			if(bp_pNext_d.value < 0)
				return 3;

		}

		return -1;

	}

	private Point computeLineLineIntersection(double x1,  double y1,  double x2,  double y2,  double x3,  double y3,  double x4,  double y4) {

		double den, xNum, yNum;
		double x, y;

		den = (x1 -x2)*(y3 - y4) - (y1 - y2)*(x3 - x4);

		if(den == 0) return null;

		xNum = (x1*y2-y1*x2)*(x3 - x4) - (x1-x2)*(x3*y4-y3*x4);
		yNum = (x1*y2-y1*x2)*(y3 - y4) - (y1-y2)*(x3*y4-y3*x4);

		x = xNum / den;
		y = yNum / den;

		return new Point(x, y);

	}

	private Point computePointOnLine(Point a, Point b, double t) {

		double x = a.x +t*(b.x-a.x);
		double y = a.y +t*(b.y-a.y);

		return new Point(x, y);

	}

}
