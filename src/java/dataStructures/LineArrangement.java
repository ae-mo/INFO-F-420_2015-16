package dataStructures;

import java.util.ArrayList;

import operations.Turn;

public class LineArrangement {

	public static final double EPSILON = 0.01;
	public double SCREEN_X, SCREEN_Y;
	public ArrayList<Face> faces;

	public LineArrangement(ArrayList<Edge> edges) {

		SCREEN_X = 1068;
		SCREEN_Y = 600;

		edges = this.removeDuplicateLines(edges);
		this.computeLineArrangement(edges);

	}

	private void computeLineArrangement(ArrayList<Edge> edges) {

		DCEL arrangement = new DCEL();

		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(0, 0, null));
		points.add(new Point(this.SCREEN_X, 0, null));
		points.add(new Point(this.SCREEN_X, this.SCREEN_Y, null));
		points.add(new Point(0, this.SCREEN_Y, null));

		arrangement.initialize(points);

		int size;
		ArrayList<Halfedge> splitHalfedges;
		ArrayList<Point> edgeIntersections, vertexIntersections;
		Face f;
		Halfedge h;
		Edge e;
		Point v;

		for(Edge l: edges) {

			size = arrangement.faces.size();

			for(int i = 1; i < size; i++) {
				
				splitHalfedges = new ArrayList<Halfedge>();
				edgeIntersections = new ArrayList<Point>();
				vertexIntersections = new ArrayList<Point>();

				f = arrangement.faces.get(i);
				h = f.h;

				do {

					e = h.getEdge();
					
					if(e.strictlyIntersectsLine(l)) {
						
						v = this.computeLineLineIntersection(l.a.x, l.a.y, l.b.x, l.b.y, e.a.x, e.a.y, e.b.x, e.b.y);
						splitHalfedges.add(h);
						edgeIntersections.add(v);
					}
					else if(e.intersectsLine(l)) {
						
						Turn lAlBeA = new Turn(l.a, l.b, e.a);
						Turn lAlBeB = new Turn(l.a, l.b, e.b);
						
						if(lAlBeA.value == 0)
							v = e.a;
						else
							v = e.b;
						
						Halfedge h1 = this.findHalfedgeInFace(v, f);
						
						Point prev = h1.prev.twin.target;
						Point next = h1.target;
						
						Point v1 = !l.a.equals(v) ? l.a : l.b;
						
						Turn v1vp = new Turn(v1, v, prev);
						Turn v1vn = new Turn(v1, v, next);
						
						if((v1vp.value > 0 && v1vn.value < 0) || (v1vp.value < 0 && v1vn.value > 0))
							vertexIntersections.add(v);
						
					}
					
					h = h.next;

				} while(!f.h.target.equals(h.target));
				
				vertexIntersections = this.removeDuplicatePoints(vertexIntersections);
				
				if(edgeIntersections.size() > 0 || vertexIntersections.size() > 0) {
					
					if(edgeIntersections.size() == 2) {
						
						f=arrangement.splitEdge(splitHalfedges.get(0), edgeIntersections.get(0));
						arrangement.splitEdge(splitHalfedges.get(1), edgeIntersections.get(1));
						
						h = this.findHalfedgeInFace(edgeIntersections.get(0), f);
						h = h.prev;
						
						arrangement.splitFace2(i, h, edgeIntersections.get(1));
						
					}
					else if(edgeIntersections.size() == 1) {
						
						f= arrangement.splitEdge(splitHalfedges.get(0), edgeIntersections.get(0));
						h = this.findHalfedgeInFace(edgeIntersections.get(0), f);
						h = h.prev;
						
						arrangement.splitFace2(i, h, vertexIntersections.get(0));

						
					}
					else if(edgeIntersections.size() == 0) {
						
						h = this.findHalfedgeInFace(vertexIntersections.get(0), f);
						h = h.prev;
						
						arrangement.splitFace2(i, h, vertexIntersections.get(1));

						
					}					
				}

			}

		}
		
		this.faces = arrangement.faces;

	}

	private ArrayList<Edge> removeDuplicateLines(ArrayList<Edge> edges) {

		ArrayList<Edge> uniqueEdges = new ArrayList<Edge>(); 
		boolean duplicate;
		Edge current, test;
		Turn a1b1b2, a1b1a2;

		for(int i=0; i < edges.size(); i++) {

			duplicate = false;
			current = edges.get(i);

			for(int j = i+1; j< edges.size(); j++) {

				test = edges.get(j);
				a1b1b2 = new Turn(current.a, current.b, test.b);
				a1b1a2 = new Turn(current.a, current.b, test.a);

				if(a1b1b2.value == 0 && a1b1a2.value == 0) {

					duplicate = true;
					break;

				}

			}

			if(!duplicate)
				uniqueEdges.add(current);

		}

		return uniqueEdges;

	}
	
	private ArrayList<Point> removeDuplicatePoints(ArrayList<Point> points) {
		
		ArrayList<Point> uniquePoints = new ArrayList<Point>(); 
		boolean duplicate;
		Point current, test;
		
		for(int i=0; i < points.size(); i++) {

			duplicate = false;
			current = points.get(i);

			for(int j = i+1; j< points.size(); j++) {

				test = points.get(j);

				if(current.equals(test)) {

					duplicate = true;
					break;

				}

			}

			if(!duplicate)
				uniquePoints.add(current);

		}

		return uniquePoints;

		
	}
	
	private Point computeLineLineIntersection(double x1,  double y1,  double x2,  double y2,  double x3,  double y3,  double x4,  double y4) {

		double den, xNum, yNum;
		double x, y;

		den = (x1 -x2)*(y3 - y4) - (y1 - y2)*(x3 - x4);

		if(Math.abs(den) <= this.EPSILON) return null;
		
		if(Math.abs(x1-x2)<= this.EPSILON) {
			
			y = (y4 - y3)/(x4 - x3) * (x1 - x3) + y3;
			
			return new Point(x1, y, null);
			
		}
		
		if(Math.abs(x3-x4)<= this.EPSILON) {
			
			y = (y2 - y1)/(x2 - x1) * (x3 - x1) + y1;
			
			return new Point(x3, y, null);
			
		}

		xNum = (x1*y2-y1*x2)*(x3 - x4) - (x1-x2)*(x3*y4-y3*x4);
		yNum = (x1*y2-y1*x2)*(y3 - y4) - (y1-y2)*(x3*y4-y3*x4);

		x = xNum / den;
		y = yNum / den;

		return new Point(x, y, null);

	}
	
	private Halfedge findHalfedgeInFace(Point p, Face f) {
		
		Halfedge h = p.h;
		
		do {
			
			Halfedge h1 = h;
			
			do {
				
				if(h1.face.equals(f))
					return h;
				
				h1 = h1.next;
				
			} while(h1.target != h.target);
			
			h = h.prev.twin;
			
		} while (!p.h.target.equals(h.target));
		
		return null;
		
	}
}
