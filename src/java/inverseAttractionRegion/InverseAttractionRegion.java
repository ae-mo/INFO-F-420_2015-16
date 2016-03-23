package inverseAttractionRegion;

import java.util.ArrayList;

import attractionRegion.AttractionRegion;
import dataStructures.DCEL;
import dataStructures.Edge;
import dataStructures.Face;
import dataStructures.Halfedge;
import dataStructures.LineArrangement;
import dataStructures.Point;
import dataStructures.Triangle;
import operations.CrossProduct;
import operations.SPT;
import operations.Turn;

public class InverseAttractionRegion {

	public double SCREEN_X, SCREEN_Y;
	public ArrayList<Face> faces;

	public InverseAttractionRegion(ArrayList<Point> points, Point p) {

		this.faces = new ArrayList<Face>();
		SCREEN_X = 1068;
		SCREEN_Y = 600;
		try {
			this.computeInverseAttractionRegion(points, p);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

	}

	private void computeInverseAttractionRegion(ArrayList<Point> points, Point p) throws CloneNotSupportedException {

		DCEL polygon = new DCEL();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		SPT spt;

		polygon.initialize(this.clonePoints(points));

		Face f = polygon.faces.get(1);
		Halfedge h = f.h;
		
		ArrayList<Point> arrangementPoints = this.clonePoints(points);

		for(int i = 0; i < arrangementPoints.size(); i++) {
			
			edges.add(new Edge(arrangementPoints.get(i), arrangementPoints.get((i+1) % arrangementPoints.size())));
			
		}
		
		spt = new SPT(p, arrangementPoints);
		edges.addAll(spt.edges);
		
		Point prev, next;
		Turn c;
		
		for(int i = 0; i < arrangementPoints.size(); i++) {
			
			Point v = arrangementPoints.get(i);
			
			prev = i-1 >= 0 ? arrangementPoints.get(i-1) :
				arrangementPoints.get((arrangementPoints.size()-i+1) % arrangementPoints.size());
			next = arrangementPoints.get((i+1) % arrangementPoints.size());
			c = new Turn(prev, v, next);
			
			if(c.value < 0) {
				
				edges.add(findPerpendicularEdge(new Edge(v, prev), arrangementPoints));
				edges.add(findPerpendicularEdge(new Edge(v, next), arrangementPoints));
				
			}
			
		}
		
		LineArrangement Ap = new LineArrangement(edges);
		AttractionRegion attr;
		
		for(int i = 1; i < Ap.faces.size(); i ++) {
			
			f = Ap.faces.get(i);
			Point candidate = this.findPointInConvexFace(f);
			
			if(candidate != null && polygon.faces.get(1).contains(candidate)) {
				
				attr = new AttractionRegion(candidate, this.clonePoints(points));
				
				if(attr.face.contains(p))
					this.faces.add(f);
			}
			
		}

	}

	private ArrayList<Point> clonePoints(ArrayList<Point> points) throws CloneNotSupportedException {

		ArrayList<Point> clone = new ArrayList<Point>(points.size());
		for(Point item: points) clone.add(new Point(item.x, item.y, null));
		return clone;

	}
	
	private Edge findPerpendicularEdge(Edge e, ArrayList<Point> points) {
		
		double newX = e.a.x -(e.b.y - e.a.y);
		double newY = e.a.y +(e.b.x - e.a.x);
		
		Point result = new Point(newX, newY, null);
		
		Point duplicate = this.findDuplicate(result, points);
		
		if(duplicate != null)
			return new Edge(e.a, duplicate);
		
		return new Edge(e.a, result);
	}
	
	private Point findPointInConvexFace(Face f) {
		
		Halfedge h = f.h;
		Point prev, next;
		Turn t;
		
		do {
			
			prev = h.prev.twin.target;
			next = h.target;
			t = new Turn(prev, h.twin.target, next);
			
			if(t.value > 0) {
				
				Triangle tr = new Triangle(prev, h.twin.target, next);
				
				return tr.findCentroid();
			}
			
			h = h.next;
		} while (f.h.target != h.target);
		
		return null;
	}
	
	private Point findDuplicate(Point p, ArrayList<Point> points) {
		
		for(Point t: points)
			if(p.equals(t))
				return t;
		
		return null;
	}

}
