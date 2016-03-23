import java.util.ArrayList;

public class SPT {
	
	public DCEL polygon;
	public Graph graph;
	public ArrayList<Edge> edges;
	public ArrayList<Point> points;
	public SPT(Point p, ArrayList<Point> points) {
		
		this.points = points;
		graph = new Graph();
		polygon = new DCEL();
		edges = new ArrayList<Edge>();
		try {
			polygon.initialize(this.clonePoints(points));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		this.computeSPT(p, polygon);
		
	}
	
	private void computeSPT(Point p, DCEL polygon) {
		
		ArrayList<Graph.Edge> edges = new ArrayList<Graph.Edge>();
		Graph.Edge[] edgesArray = new Graph.Edge[1];
		double dist;
		for(int i = 0; i < polygon.vertices.size(); i++) {
			
			Point v1 = polygon.vertices.get(i);
			
			for(int j = 0; j < polygon.vertices.size(); j++) {
				
				Point v2 = polygon.vertices.get(j);
				if(v2.equals(v1))
					continue;
				
				if(v1.h.target.equals(v2) || v1.h.prev.twin.target.equals(v2)) {
					
					dist = computeDistance(v1, v2);
					edges.add(graph.new Edge(String.valueOf(i), String.valueOf(j), dist));
					continue;
					
				}
				if(isExterior(new Edge(v1, v2)))
					continue;
				
				else {
					
					boolean intersects = this.intersectsPolygon(v1, v2);
					
					if(!intersects) {
						dist = computeDistance(v1, v2);
						edges.add(graph.new Edge(String.valueOf(i), String.valueOf(j), dist));
						}
						
				}
				
			}
			
		}
		
		for(int i = 0; i < polygon.vertices.size(); i++) {
			
			Point v = polygon.vertices.get(i);
			
			boolean intersects = intersectsPolygon(p, v);
				
			if(!intersects) {
				dist = computeDistance(p, v);
				edges.add(graph.new Edge(String.valueOf(polygon.vertices.size()), String.valueOf(i), dist));
			}
				
		}

		
		graph.addEdges(edges.toArray(edgesArray));
		graph.dijkstra(String.valueOf(polygon.vertices.size()));
		ArrayList<String> path;
		
		for(int i = 0; i < polygon.vertices.size(); i++) {
			path = new ArrayList<String>();
			path = graph.getPath(String.valueOf(i), path);
			
			Point a = p;
			Point b;
			for(int j=1; j<path.size(); j++) {
				String s = path.get(j);
				b = this.points.get(Integer.valueOf(s));
				this.edges.add(new Edge(a, b));
				
				a = b;
			}
			
		}

	}
	
	private double computeDistance(Point a, Point b) {
		
		return Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y, 2));
	}

	private boolean intersectsPolygon(Point v1, Point v2) {
		
		Edge v1v2 = new Edge(v1, v2);
		
		boolean intersects = false;
		
		Face f = polygon.faces.get(1);
		Halfedge h = f.h;
		
		do {
			
			if(h.target.equals(v2) || h.twin.target.equals(v2)|| h.target.equals(v1) || h.twin.target.equals(v1)) {
				
				h = h.next;
				continue;
				
			}
				
			
			if(v1v2.strictlyIntersectsEdge(h.getEdge())) {
				
				intersects = true;
				break;
				
			}
			else if(v1v2.intersectsEdge(h.getEdge())) {
				
				Point intersection;
				Turn t = new Turn(v1, v2, h.target);
				if(t.value == 0)
					intersection = h.target;
				else
					intersection = h.twin.target;
				
				Point next = intersection.h.target;
				Point prev = intersection.h.prev.twin.target;
				
				if(v1v2.intersectsEdge(new Edge(prev, next))) {
					intersects = true;
					break;
				}
				
			}
			
			h = h.next;
			
		} while(!h.target.equals(f.h.target));
		
		return intersects;
		
	}
	
	private boolean isExterior(Edge e) {
		
		Point prevA = e.a.h.prev.twin.target;
		Point prevB = e.b.h.prev.twin.target;
		Point nextA = e.a.h.target;
		Point nextB = e.b.h.target;
		
		CrossProduct a = new CrossProduct(e.a, prevA, e.a, nextA);
		CrossProduct b = new CrossProduct(e.b, prevB, e.b, nextB);
		
		Edge pAnA = new Edge(prevA, nextA);
		Edge pBnB = new Edge(prevB, nextB);
		
		if(a.value > 0 && !e.intersectsEdge(pAnA))
			return false;
		if(b.value > 0 && !e.intersectsEdge(pBnB))
			return false;
		if(a.value < 0 && e.intersectsEdge(pAnA))
			return false;
		if(b.value < 0 && e.intersectsEdge(pBnB))
			return false;
		
		Turn pAAnA = new Turn(prevA, e.a, nextA);
		Turn pBBnB = new Turn(prevB, e.b, nextB);
		Turn pAAB = new Turn(prevA, e.a, e.b);
		Turn pBBA = new Turn(prevB, e.b, e.a);
		
		if(pAAnA.value == 0 && pAAB.value > 0)
			return false;
		
		if(pBBnB.value == 0 && pBBA.value > 0)
			return false;
		
		return true;
		
	}
	private ArrayList<Point> clonePoints(ArrayList<Point> points) throws CloneNotSupportedException {

		ArrayList<Point> clone = new ArrayList<Point>(points.size());
		for(Point item: points) clone.add(new Point(item.x, item.y, null));
		return clone;

	}
}
