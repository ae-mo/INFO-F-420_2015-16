import java.util.ArrayList;

abstract class AttractionRegion {

	/**
	 * The complete algorithm. To be overridden in subclasses.
	 */
	public abstract ArrayList<Point> computeAttractionRegion();

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
	protected ArrayList<Point> computeRayVertices(Point b, ArrayList<Point> vertices) {

		ArrayList<Point> rayVertices = new ArrayList<Point>();
		
		RedBlackBST<Key, Edge> status = new RedBlackBST<Key, Edge>();

		this.initializeStatus(status, b, vertices);

		for(int i=1; i < vertices.size(); i++) {

			Point p = vertices.get(i);
			
			this.updateStatus(status, b, p);
			
			Edge min = status._min().e;
			Edge max = status._max().e;
			
			System.out.println();
			System.out.println("min edge: " + min.a.x + ", " + min.a.y + "; "+ min.b.x + ", " + min.b.y );
			System.out.println("max edge: " + max.a.x + ", " + max.a.y + "; "+ max.b.x + ", " + max.b.y );
			
			if(this.isSplitVertex(b, p))  {
				
				Point vtx = this.computeRayVertex(status, b, p);
				
				if(vtx != null)
					rayVertices.add(vtx);
			}

		}

		return rayVertices;
	}

	/**
	 * Given a split vertex, computes the corresponding ray vertex.
	 * @param status
	 * @param b
	 * @param p
	 * @return
	 */
	private Point computeRayVertex(RedBlackBST<Key, Edge> status, Point b, Point p) {
		
		Point next = p.h.target;
		
		Key k = new Key(b, p, new Edge(p, next));
		
		int rank = status.rank(k);
		
		if(rank >= status._size() || status._size() == 1) return null;
		
		//System.out.println("Rank: " + rank);
		//System.out.println("Size: " + status._size());
		
		Key k2 = status.select(rank + 1);
		
		return this.computeLineLineIntersection(b.x, b.y, p.x, p.y, k2.e.a.x, k2.e.a.y, k2.e.b.x, k2.e.b.y);
	}

	private void updateStatus(RedBlackBST<Key, Edge> status, Point b, Point p) {
		
		Point prev = p.h.target;
		Point next = p.h.prev.twin.target;
		
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
	private void initializeStatus(RedBlackBST<Key, Edge> status, Point b, ArrayList<Point> vertices) {

		Point p1 = vertices.get(0);
		
		Halfedge h = p1.h;
		Edge e = h.getEdge();

		while(!h.target.equals(p1)) {

			if(e.intersectsRay(b, p1)) { // add it to the status

				Turn bEab = new Turn(b, e.a, e.b);
				Turn bEba = new Turn(b, e.b, e.a);
				
				Turn bpEa = new Turn (b, p1, e.a);
				Turn bpEb = new Turn (b, p1, e.b);

				if((bpEa.value == 0 && bEab.value > 0) ||
						(bpEb.value == 0 && bEba.value > 0) ||
				        (bpEa.value != 0 && bpEb.value != 0)) {

					Key k = new Key(b, e.a, new Edge(e.a, e.b));
					status.put(k, e);

				}


			}
			
			h = h.next;
			e = h.getEdge();

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
		
		Key k = createKey(b, p, q, 1);
		
		status.put(k, new Edge(p, q));
		
	}
	
	private void deleteEdge(RedBlackBST<Key, Edge> status, Point b, Point p, Point q) {
		
		Key k = createKey(b, p, q, 2);
		
		status.delete(k);
		
	}
	
	private Key createKey(Point b, Point p, Point q, int first) {
		
		if(first == 2) {
			
			Point temp = p;
			p = q;
			q = temp;
			
		}
			
		
		return new Key(b, p, new Edge(p, q));
		
	}
	
	private boolean isSplitVertex(Point b, Point p) {
		
		Point prev = p.h.target;
		Point next = p.h.prev.twin.target;
		
		System.out.println();
		System.out.println("prev: " + prev.x + ", "+prev.y);
		System.out.println("p: " + p.x + ", "+p.y);
		System.out.println("next: " + next.x + ", "+next.y);
		
		Turn prevPNext = new Turn(prev, p, next);
		
		if(prevPNext.value >= 0)
			return false;
		
		CrossProduct bp_pNext = new CrossProduct(b, p, p, next);
		CrossProduct bp_prevP = new CrossProduct(b, p, prev, p);
		
		DotProduct bp_pNext_d = new DotProduct(b, p, p, next);
		DotProduct bp_prevP_d = new DotProduct(b, p, prev, p);
		
		if(bp_pNext.value < 0 && bp_prevP.value < 0) {
			
			if(bp_pNext_d.value < 0 && bp_prevP_d.value > 0)
				return true;
			
		}
		else if(bp_pNext.value > 0 && bp_prevP.value < 0) {
			
			if(bp_prevP_d.value > 0)
				return true;
			
		}
		else if(bp_pNext.value < 0 && bp_prevP.value > 0) {
			
			if(bp_pNext_d.value < 0)
				return true;
			
		}
		
		return false;
		
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

}
