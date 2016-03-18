package operations;

import java.util.ArrayList;

import dataStructures.DCEL;
import dataStructures.Edge;
import dataStructures.Face;
import dataStructures.Graph;
import dataStructures.Halfedge;
import dataStructures.Point;

public class SPT {
	
	public DCEL polygon;
	public Graph graph;
	
	public SPT(Point p, ArrayList<Point> points) {
		
		graph = new Graph();
		polygon = new DCEL();
		polygon.initialize(points);
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
				
				Point next1 = v1.h.target;
				Point prev1 = v1.h.prev.twin.target;
				Point next2 = v2.h.target;
				Point prev2 = v2.h.prev.twin.target;
				
				Turn v1v2n2 = new Turn(v1, v2, next2);
				Turn v1v2p2 = new Turn(v1, v2, prev2);
				Turn v2v1n1 = new Turn(v2, v1, next1);
				Turn v2v1p1 = new Turn(v2, v1, prev1);
				
				if(v1.h.target.equals(v2) || v1.h.prev.twin.target.equals(v2)) {
					
					dist = computeDistance(v1, v2);
					edges.add(graph.new Edge(String.valueOf(i), String.valueOf(j), dist));
					continue;
					
				}
				if(!((v1v2n2.value >= 0 && v1v2p2.value <= 0) || (v2v1n1.value >= 0 && v2v1p1.value <= 0) || 
						((v1v2n2.value >= 0 && v1v2p2.value >= 0) && (v2v1n1.value >= 0 && v2v1p1.value >= 0)) || 
						((v1v2n2.value <= 0 && v1v2p2.value <= 0) && (v2v1n1.value <= 0 && v2v1p1.value <= 0))))
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
		ArrayList<String> path = new ArrayList<String>();
		
		graph.printAllPaths();

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
			
		} while(h.target != f.h.target);
		
		return intersects;
		
	}
}
