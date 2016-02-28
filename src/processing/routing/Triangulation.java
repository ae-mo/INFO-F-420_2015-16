import java.util.ArrayList;

public class Triangulation {

	public DCEL dcel;

	public Triangulation(DCEL dcel) {

		ArrayList<Point> reflexVertices= new ArrayList<Point>();
		ArrayList<Point> convexVertices=new ArrayList<Point>();
		ArrayList<Point> ears=new ArrayList<Point>();

		for(Point p: dcel.vertices) {

			Point next = p.h.prev.twin.target;
			Point prev = p.h.target;

			Turn t = new Turn(prev, p, next);
			if(t.value < 0) reflexVertices.add(p);
			else if (t.value > 0) {

				convexVertices.add(p);

			}
			
			p.h = p.h.prev.twin;

		}
		

//		Point pi = dcel.vertices.get(7);
//		Halfedge id = pi.h;
//		
//		for(int i = 0; i < dcel.vertices.size(); i++) {
//			
//			System.out.println(id.target.x + ", " + id.target.y);
//			id = id.next;
//			
//		}
		

		for(Point p: convexVertices) {

			if(testEar(reflexVertices, p)) {
				
				ears.add(p);
			}
				

		}


		//		System.out.println("\nReflex Vtcs");
		//		for(Point p: reflexVertices) {
		//
		//			System.out.println(p.x + ", " + p.y);
		//
		//		}
		//		System.out.println("\nConvex Vtcs");
		//		for(Point p: convexVertices) {
		//
		//			System.out.println(p.x + ", " + p.y);
		//
		//		}
		//		System.out.println("\near tips");
		//		for(Point p: ears) {
		//
		//			System.out.println(p.x + ", " + p.y);
		//
		//		}
		
		
		

		while(ears.size() > 1) {
			
			Point ear = ears.get(0);

			
			Point prev = ear.h.prev.prev.target;
			Point next = ear.h.target;
			Halfedge h = prev.h.prev;

			int prevTest = testConvexity(prev);
			int nextTest = testConvexity(next);
			
//			System.out.println("\nReflex Vtcs");
//			for(Point p: reflexVertices) {
//
//				System.out.println(p.x + ", " + p.y);
//
//			}
//			System.out.println("\nConvex Vtcs");
//			for(Point p: convexVertices) {
//
//				System.out.println(p.x + ", " + p.y);
//
//			}
//			System.out.println("\near tips");
//			for(Point p: ears) {
//
//				System.out.println(p.x + ", " + p.y);
//
//			}
			System.out.println("*****************************************");
			System.out.println("current ear: " + ear.x + ", " + ear.y);
			System.out.println("halfedge target: " + h.target.x + ", " + h.target.y);
			System.out.println("split point: " + next.x + ", " + next.y);


			dcel.splitFace(dcel.faces.size() -1 , h, next);
			ears.remove(0);
			convexVertices.remove(0);
			
			Halfedge h1 = dcel.halfedges.get(dcel.halfedges.size() - 2);
			next = h1.target;
			prev = h1.prev.target;
			next.h = h1.next;
			prev.h = h1;
			
			System.out.println("next next: " + next.h.target.x + ", " + next.h.target.y);
			System.out.println("next prev: " + next.h.prev.prev.target.x + ", " + next.h.prev.prev.target.y);
			int prevTest2 = testConvexity(prev);
			int nextTest2 = testConvexity(next);

			this.handleAdjacentVertex(reflexVertices, convexVertices, ears, next, nextTest, nextTest2);
			this.handleAdjacentVertex(reflexVertices, convexVertices, ears, prev, prevTest, prevTest2);


			//			System.out.println("hey");
			//			for(int i = 1; i < dcel.faces.size(); i++) {
			//
			//				Face f = dcel.faces.get(i);
			//
			//				System.out.println();
			//				System.out.println("****FACE #" + i +"****"); 
			//
			//				Halfedge hh = f.h;
			//				Point pp = hh.target;
			//
			//				for(int j = 0; j < 15; j++) {
			//
			//					System.out.println();
			//					System.out.println("Halfedge");
			//					System.out.println("target: " + hh.target.x + ", " + hh.target.y);
			//					System.out.println("twin target: " + hh.twin.target.x + ", " + hh.twin.target.y);
			//					System.out.println("next target: " + hh.next.target.x + ", " + hh.next.target.y);
			//					System.out.println("prev target: " + hh.prev.target.x + ", " + hh.prev.target.y);
			//
			//
			//					hh =hh.next;
			//				}
			//
			//
			//			}

			

		}



	}

	private int testConvexity(Point p) {

		Point prev = p.h.prev.prev.target;
		Point next = p.h.target;

		Turn t = new Turn(prev, p, next);

		if(t.value>0) return 1;
		if(t.value<0) return -1;
		else return 0;

	}

	private boolean testEar(ArrayList<Point> reflexVertices, Point p) {

		Point next = p.h.prev.twin.target;
		Point prev = p.h.target;

		Triangle tr = new Triangle(prev, p, next);

		boolean isEar = true;

		for(Point p1: reflexVertices) {

			if(p1 != prev && p1 != next) {

				if(tr.contains(p1)) {

					isEar = false;
					break;

				}

			}

		}

		return isEar;

	}

	private void handleAdjacentVertex(ArrayList<Point> reflexVertices, ArrayList<Point> convexVertices, ArrayList<Point> ears, Point p, int test1, int test2) {

//		System.out.println("handling: " + p.x + ", " + p.y);
		
		
		if(test1 < 0 && test2 >0) {

//			System.out.println("Now convex");
			reflexVertices.remove(p);
			convexVertices.add(0, p);
			if(testEar(reflexVertices, p)) {
//				System.out.println("ear!");
				ears.add(0, p);
			}
				
			

		}
		else if(test1 >0 && test2 > 0) {
			
//			System.out.println("was convex");
			if(!testEar(reflexVertices, p)) {
//				System.out.println("no more ear");
				ears.remove(p);
			}
				

		}
		
		

	}
	
	void fix(DCEL dcel, Halfedge h, Point p, Point prev, Point next) {
		
		if(h.face != dcel.faces.get(dcel.faces.size() -1)) {

			System.out.println("oh noes! 1");
			h = h.twin.next.twin;
			prev = h.target;
			next = h.next.next.target;

		}

		if(p.h.face != dcel.faces.get(dcel.faces.size() -1)) {

			System.out.println("oh noes! 2");
			h = p.h.twin.next.twin.prev;
			prev = h.target;
			next = h.next.next.target;

		}
		
	}



	//	private void makeMonotone(DCEL dcel) {
	//
	//		PriorityQueue<Event> queue = new PriorityQueue<Event>();
	//		RedBlackBST<Key, Edge> status = new RedBlackBST<Key, Edge>();
	//
	//		this.constructQueue(queue, dcel.vertices);
	//		
	//		Object[] q = queue.toArray();
	//		
	//		for(Object e: q) {
	//			
	//			Event ev = (Event) e;
	//			
	//			System.out.println(ev.p.x + ", " + ev.p.y);
	//			
	//		}
	//		
	//		while(queue.peek() != null) {
	//			
	//			
	//			
	//			Event e = queue.poll();
	//			
	//			switch (e.t) {
	//			
	//			case START:
	//				System.out.println("start");
	//				this.handleStartVertex(status, e);
	//				break;
	//				
	//			case END:
	//				System.out.println("end");
	//				this.handleEndVertex(status, dcel, e);
	//				break;
	//				
	//			case SPLIT:
	//				System.out.println("split");
	//				this.handleSplitVertex(status, dcel, e);
	//				break;
	//				
	//			case MERGE:
	//				System.out.println("merge");
	//				this.handleMergeVertex(status, dcel, e);
	//				break;
	//				
	//			case REGULAR:
	//				System.out.println("regular");
	//				this.handleRegularVertex(status, dcel, e);
	//				break;
	//			
	//			}
	//			
	//		}
	//
	//
	//	}
	//
	//	private void handleStartVertex(RedBlackBST<Key, Edge> status, Event ev) {
	//		
	//		Halfedge he = ev.p.h.prev.twin;
	//		he.helper = ev.p;
	//		Edge e = ev.p.h.prev.twin.getEdge();
	//
	//		Key k = new Key(e);
	//		
	//		status.put(k, e);
	//		
	//	}
	//	
	//	private void handleEndVertex(RedBlackBST<Key, Edge> status, DCEL dcel, Event ev) {
	//		
	//		Edge e = ev.p.h.twin.getEdge();
	//		
	//		if(decideType(e.helper) == Type.MERGE) {
	//			
	//			dcel.splitFace(dcel.faces.size() - 1, ev.p.h.twin, e.helper);
	//			
	//		}
	//		
	//		Key k = new Key(e);
	//		status.delete(k);
	//		
	//	}
	//	
	//	private void handleSplitVertex(RedBlackBST<Key, Edge> status, DCEL dcel, Event ev) {
	//		
	//		
	//		Halfedge hei = ev.p.h.prev.twin;
	//		hei.helper = ev.p;
	//		Edge ei = hei.getEdge();
	//		Key ki = new Key(ei);
	//		
	//		Edge e = new Edge(ev.p.h.target, ev.p);
	//		Key k = new Key(e);
	//		int rank = status.rank(k);
	//		
	//		Key kj = status.select(rank - 1);
	//		Edge ej = kj.e;
	//		dcel.splitFace(dcel.faces.size() - 1, ev.p.h.twin, ej.helper);
	//		ej.helper = ev.p;
	//		
	//		status.put(ki, ei);
	//		
	//	}
	//	
	//	private void handleMergeVertex(RedBlackBST<Key, Edge> status, DCEL dcel, Event ev) {
	//		
	//		Halfedge he= ev.p.h.twin;
	//		Edge e = he.getEdge();
	//		if(this.decideType(e.helper) == Type.MERGE)
	//			dcel.splitFace(dcel.faces.size() -1, he, e.helper);
	//		
	//		Key ke = new Key(e);
	//		status.delete(ke);
	//
	//		Edge es = new Edge(ev.p, ev.p.h.prev.twin.target);
	//		Key ks = new Key(es);
	//		int rank = status.rank(ks);
	//		Key kj = status.select(rank - 1);
	//		
	//		if(this.decideType(kj.e.helper) == Type.MERGE)
	//			dcel.splitFace(dcel.faces.size() -1, he, kj.e.helper);
	//		kj.e.helper = ev.p;
	//			
	//		
	//	}
	//	
	//	private void handleRegularVertex(RedBlackBST<Key, Edge> status, DCEL dcel, Event ev) {
	//		
	//		int count = 0;
	//		
	//		Iterable<Key> keys = status._keys();
	//		
	//		Edge ray = new Edge(ev.p.x, ev.p.y, ev.p.x + 1, ev.p.y);
	//		
	//		for(Key k: keys) {
	//			
	//			if(k.e.intersectsRay(ray))
	//				count++;
	//			
	//		}
	//		
	//		Key max = status._max();
	//		if(max.e.intersectsRay(ray))
	//			count++;
	//		 
	//		if(count % 2 == 0) {
	//			
	//			Halfedge hei_1 = ev.p.h.twin;
	//			Edge ei_1 = hei_1.getEdge();
	//			
	//			if(this.decideType(ei_1.helper) == Type.MERGE) {
	//				Halfedge hh = ei_1.helper.h.twin;
	//				dcel.splitFace(dcel.faces.size() - 1, hh, ev.p);
	//			}
	//			
	//			Key kei_1 = new Key(ei_1);
	//			status.delete(kei_1);
	//			
	//			Halfedge hei = ev.p.h.prev.twin;
	//			Edge ei = hei.getEdge();
	//			
	//			ei.helper = ev.p;
	//			Key kei = new Key(ei);
	//			status.put(kei, ei);
	//			
	//		}
	//		else {
	//			
	//			Halfedge hei = ev.p.h.prev.twin;
	//			Edge ei = hei.getEdge();
	//			Key kei = new Key(ei);
	//			
	//			int rank = status.rank(kei);
	//			
	//			Key kj = status.select(rank - 1);
	//			Edge ej = kj.e;
	//			
	//			if(this.decideType(ej.helper) == Type.MERGE) {
	//				
	//				Halfedge hei_1 = ev.p.h.twin;
	//				Edge ei_1 = hei_1.getEdge();
	//				
	//				dcel.splitFace(dcel.faces.size() - 1, hei_1, ej.helper);
	//				
	//			}
	//				
	//			ej.helper = ev.p;
	//		}
	//	}
	//
	//	private void constructQueue(PriorityQueue<Event> queue, ArrayList<Point> vertices) {
	//
	//		for(Point v: vertices) {
	//
	//			Event e = new Event(new Point(v.x, v.y, v.h), decideType(v));
	//
	//			queue.add(e);
	//
	//		}
	//
	//	}
	//
	//	private Type decideType(Point p) {
	//
	//		Point prev = p.h.target;
	//		Point next = p.h.prev.twin.target;
	//		
	//		System.out.println("prev: " + prev.x + ", " + prev.y);
	//		System.out.println("next: " + next.x + ", " + next.y);
	//		
	//		boolean nextBelow, prevBelow;
	//		
	//		if((prev.y  < p.y) || (prev.y  == p.y && prev.x > p.x)) prevBelow = true;
	//		else prevBelow = false;
	//		
	//		if((next.y  < p.y) || (next.y  == p.y && next.x > p.x)) nextBelow = true;
	//		else nextBelow = false;
	//		
	//		CrossProduct prod = new CrossProduct(p, prev, p, next);
	//		
	//		if(nextBelow && prevBelow && prod.value < 0) return Type.START;
	//		else if(nextBelow && prevBelow && prod.value > 0) return Type.SPLIT;
	//		else if(!nextBelow && !prevBelow && prod.value < 0) return Type.END;
	//		else if(!nextBelow && !prevBelow && prod.value > 0) return Type.MERGE;
	//		else return Type.REGULAR;
	//	}
	//
	//	class Event implements Comparable<Event> {
	//
	//		Point p;
	//		Type t;
	//
	//		Event(Point p, Type t) {
	//
	//			this.p = p;
	//			this.t= t;
	//
	//		}
	//
	//		@Override
	//		public int compareTo(Event e) {
	//
	//			if(this.p.y < e.p.y)
	//				return 1;
	//			else if(this.p.y > e.p.y)
	//				return -1;
	//			else {
	//
	//				if(this.p.x < e.p.x)
	//					return 1;
	//				else if(this.p.x > e.p.x)
	//					return -1;
	//
	//			}
	//
	//			return 0;
	//		}
	//	}
	//
	//	enum Type {
	//
	//		START, END, SPLIT, MERGE, REGULAR;
	//
	//	}
	//	
	//	class Key implements Comparable<Key> {
	//		
	//		Edge e;
	//		
	//		Key(Edge e) {
	//			
	//		
	//			this.e = e;
	//			
	//		}
	//
	//		@Override
	//		public int compareTo(Key o) {
	//			
	//			if(this.e.equals(o.e)) return 0;
	//			
	//			Edge current;
	//			Edge other;
	//			
	//			if(this.e.b.y > this.e.a.y)
	//				current = this.e;
	//			else
	//				current = new Edge(this.e.b, this.e.a);
	//			
	//			if(o.e.b.y > o.e.a.y)
	//				other = o.e;
	//			else
	//				other = new Edge(o.e.b, o.e.a);
	//			
	//			Turn ea= new Turn(current, other.a);
	//			Turn eb = new Turn(current, other.b);
	//			
	//			Triangle discr = new Triangle(current.a, other.b, other.a);
	//			
	//			if(discr.contains(current.b)) {
	//				
	//				if(ea.value >= 0) return 1;
	//				else return -1;
	//				
	//			}
	//			else {
	//				
	//				if(eb.value >= 0) return 1;
	//				else return -1;
	//				
	//			}
	//
	//		}}

}