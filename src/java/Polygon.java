import java.util.ArrayList;

import dataStructures.Edge;
import dataStructures.Point;

class Polygon {

	ArrayList<Point> vertices;
	ArrayList<Edge> edges;
	ArrayList<Polygon> holes;
	
	public Polygon() {
		
		this.vertices = new ArrayList<Point>();
		this.edges = new ArrayList<Edge>();
		this.holes = new ArrayList<Polygon>();
	}
	
}
