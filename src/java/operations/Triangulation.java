package operations;

import java.util.ArrayList;


import dataStructures.*;
import operations.*;

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

		for(Point p: convexVertices) {

			if(testEar(reflexVertices, p)) {
				
				ears.add(p);
			}		

		}

		while(convexVertices.size() + reflexVertices.size() > 3) {
			
			Point ear = ears.get(0);

			Point prev = ear.h.prev.prev.target;
			Point next = ear.h.target;
			Halfedge h = ear.h.prev.prev;

			int prevTest = testConvexity(prev);
			int nextTest = testConvexity(next);
			
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

			int prevTest2 = testConvexity(prev);
			int nextTest2 = testConvexity(next);

			this.handleAdjacentVertex(reflexVertices, convexVertices, ears, next, nextTest, nextTest2);
			this.handleAdjacentVertex(reflexVertices, convexVertices, ears, prev, prevTest, prevTest2);			

		}



	}

	private int testConvexity(Point p) {

		Point prev = p.h.prev.prev.target;
		Point next = p.h.target;

		Turn t = new Turn(prev, p, next);

		if(t.value>0) return 1;
		else return -1;

	}

	private boolean testEar(ArrayList<Point> reflexVertices, Point p) {

		Point next = p.h.target;
		Point prev = p.h.prev.prev.target;

		Triangle tr = new Triangle(prev, p, next);

		boolean isEar = true;

		for(Point p1: reflexVertices) {

			if(p1 != prev && p1 != next) {

				if(tr.strictlyContains(p1)) {

					isEar = false;
					break;

				}

			}

		}

		return isEar;

	}

	private void handleAdjacentVertex(ArrayList<Point> reflexVertices, ArrayList<Point> convexVertices, ArrayList<Point> ears, Point p, int test1, int test2) {
				
		if(test1 < 0 && test2 >0) {
			reflexVertices.remove(p);
			convexVertices.add(0, p);
			if(testEar(reflexVertices, p)) {
				ears.add(0, p);
			}
			
		}
		else if(test1 >0) {
			if(!testEar(reflexVertices, p) && ears.contains(p)) {
				ears.remove(p);
			}
			else if (testEar(reflexVertices, p) && !ears.contains(p)) {
				
				ears.add(0, p);
				
			}
				
		}
		
	}
	
}