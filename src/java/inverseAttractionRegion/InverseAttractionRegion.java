package inverseAttractionRegion;

import java.util.ArrayList;

import dataStructures.DCEL;
import dataStructures.Edge;
import dataStructures.Face;
import dataStructures.Halfedge;
import dataStructures.Point;
import operations.SPT;

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
		DCEL arrangement = new DCEL();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		SPT spt;

		polygon.initialize(this.clonePoints(points));
		spt = new SPT(p, this.clonePoints(points));

		edges.addAll(spt.edges);

		Face f = polygon.faces.get(1);
		Halfedge h = f.h;

		do {

			edges.add(h.getEdge());
		} while (h.target != f.h.target);
		
		

	}

	private ArrayList<Point> clonePoints(ArrayList<Point> points) throws CloneNotSupportedException {

		ArrayList<Point> clone = new ArrayList<Point>(points.size());
		for(Point item: points) clone.add((Point) item.clone());
		return clone;

	}

}
