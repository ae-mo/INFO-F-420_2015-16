package operations;

import java.util.ArrayList;
import java.util.PriorityQueue;

import dataStructures.*;
import operations.*;

public class Triangulation {

	public DCEL dcel;
	
	public Triangulation(DCEL dcel) {
		
		makeMonotone(dcel);
		
	}

	private void makeMonotone(DCEL dcel) {

		PriorityQueue<Event> queue = new PriorityQueue<Event>();
		RedBlackBST<Key, Edge> status = new RedBlackBST<Key, Edge>();

		this.constructQueue(queue, dcel.vertices);
		
		while(queue.peek() != null) {
			
			Event e = queue.poll();
			
			switch (e.t) {
			
			case START:
				this.handleStartVertex(status, e);
				break;
				
			case END:
				this.handleEndVertex(status, dcel, e);
				break;
				
			case SPLIT:
				this.handleSplitVertex(status, dcel, e);
				break;
				
			case MERGE:
				this.handleMergeVertex(status, dcel, e);
				break;
				
			case REGULAR:
				this.handleRegularVertex(status, dcel, e);
				break;
			
			}
			
		}


	}

	private void handleStartVertex(RedBlackBST<Key, Edge> status, Event ev) {
		
		Halfedge he = ev.p.h.prev.twin;
		he.helper = ev.p;
		Edge e = ev.p.h.prev.twin.getEdge();

		Key k = new Key(e);
		
		status.put(k, e);
		
	}
	
	private void handleEndVertex(RedBlackBST<Key, Edge> status, DCEL dcel, Event ev) {
		
		Edge e = ev.p.h.twin.getEdge();
		
		if(decideType(e.helper) == Type.MERGE) {
			
			dcel.splitFace(dcel.faces.size() - 1, ev.p.h.twin, e.helper);
			
		}
		
		Key k = new Key(e);
		status.delete(k);
		
	}
	
	private void handleSplitVertex(RedBlackBST<Key, Edge> status, DCEL dcel, Event ev) {
		
		
		Halfedge hei = ev.p.h.prev.twin;
		hei.helper = ev.p;
		Edge ei = hei.getEdge();
		Key ki = new Key(ei);
		
		Edge e = new Edge(ev.p.h.target, ev.p);
		Key k = new Key(e);
		int rank = status.rank(k);
		
		Key kj = status.select(rank - 1);
		Edge ej = kj.e;
		dcel.splitFace(dcel.faces.size() - 1, ev.p.h.twin, ej.helper);
		ej.helper = ev.p;
		
		status.put(ki, ei);
		
	}
	
	private void handleMergeVertex(RedBlackBST<Key, Edge> status, DCEL dcel, Event ev) {
		
		Halfedge he= ev.p.h.twin;
		Edge e = he.getEdge();
		if(this.decideType(e.helper) == Type.MERGE)
			dcel.splitFace(dcel.faces.size() -1, he, e.helper);
		
		Key ke = new Key(e);
		status.delete(ke);

		Edge es = new Edge(ev.p, ev.p.h.prev.twin.target);
		Key ks = new Key(es);
		int rank = status.rank(ks);
		Key kj = status.select(rank - 1);
		
		if(this.decideType(kj.e.helper) == Type.MERGE)
			dcel.splitFace(dcel.faces.size() -1, he, kj.e.helper);
		kj.e.helper = ev.p;
			
		
	}
	
	private void handleRegularVertex(RedBlackBST<Key, Edge> status, DCEL dcel, Event ev) {
		
		int count = 0;
		
		Iterable<Key> keys = status._keys();
		
		Edge ray = new Edge(ev.p.x, ev.p.y, ev.p.x + 1, ev.p.y);
		
		for(Key k: keys) {
			
			if(k.e.intersectsRay(ray))
				count++;
			
		}
		
		Key max = status._max();
		if(max.e.intersectsRay(ray))
			count++;
		 
		if(count % 2 == 0) {
			
			Halfedge hei_1 = ev.p.h.twin;
			Edge ei_1 = hei_1.getEdge();
			
			if(this.decideType(ei_1.helper) == Type.MERGE) {
				Halfedge hh = ei_1.helper.h.twin;
				dcel.splitFace(dcel.faces.size() - 1, hh, ev.p);
			}
			
			Key kei_1 = new Key(ei_1);
			status.delete(kei_1);
			
			Halfedge hei = ev.p.h.prev.twin;
			Edge ei = hei.getEdge();
			
			ei.helper = ev.p;
			Key kei = new Key(ei);
			status.put(kei, ei);
			
		}
		else {
			
			Halfedge hei = ev.p.h.prev.twin;
			Edge ei = hei.getEdge();
			Key kei = new Key(ei);
			
			int rank = status.rank(kei);
			
			Key kj = status.select(rank - 1);
			Edge ej = kj.e;
			
			if(this.decideType(ej.helper) == Type.MERGE) {
				
				Halfedge hei_1 = ev.p.h.twin;
				Edge ei_1 = hei_1.getEdge();
				
				dcel.splitFace(dcel.faces.size() - 1, hei_1, ej.helper);
				
			}
				
			ej.helper = ev.p;
		}
	}

	private void constructQueue(PriorityQueue<Event> queue, ArrayList<Point> vertices) {

		for(Point v: vertices) {

			Event e = new Event(new Point(v.x, v.y, v.h), decideType(v));

			queue.add(e);

		}

	}

	private Type decideType(Point p) {

		return Type.START;
	}

	class Event implements Comparable<Event> {

		Point p;
		Type t;

		Event(Point p, Type t) {

			this.p = p;
			this.t= t;

		}

		@Override
		public int compareTo(Event e) {

			if(this.p.y < e.p.y)
				return -1;
			else if(this.p.y > e.p.y)
				return 1;
			else {

				if(this.p.x < e.p.x)
					return -1;
				else if(this.p.x < e.p.x)
					return 1;

			}

			return 0;
		}
	}

	enum Type {

		START, END, SPLIT, MERGE, REGULAR;

	}
	
	class Key implements Comparable<Key> {
		
		Edge e;
		
		Key(Edge e) {
			
		
			this.e = e;
			
		}

		@Override
		public int compareTo(Key o) {
			
			if(this.e.equals(o.e)) return 0;
			
			Edge current;
			Edge other;
			
			if(this.e.b.y > this.e.a.y)
				current = this.e;
			else
				current = new Edge(this.e.b, this.e.a);
			
			if(o.e.b.y > o.e.a.y)
				other = o.e;
			else
				other = new Edge(o.e.b, o.e.a);
			
			Turn ea= new Turn(current, other.a);
			Turn eb = new Turn(current, other.b);
			
			Triangle discr = new Triangle(current.a, other.b, other.a);
			
			if(discr.contains(current.b)) {
				
				if(ea.value >= 0) return 1;
				else return -1;
				
			}
			else {
				
				if(eb.value >= 0) return 1;
				else return -1;
				
			}

		}}

}
