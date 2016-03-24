package routing;

import java.util.ArrayList;
import java.util.HashMap;

import attractionRegion.AttractionRegion;
import dataStructures.Face;
import dataStructures.Graph;
import dataStructures.Graph.Edge;
import dataStructures.Graph.Vertex;
import dataStructures.Point;

public class MinimumBeaconPath {

	public ArrayList<Point> points;
	public ArrayList<Point> beacons;
	public ArrayList<Point> candidateBeacons;
	public Point start;
	public Point end;
	public Graph g;



	public MinimumBeaconPath(ArrayList<Point> points, ArrayList<Point> candidateBeacons,
			Point start, Point end) {

		g = new Graph();
		this.points = points;
		this.beacons = new ArrayList<Point>();
		this.candidateBeacons = candidateBeacons;
		this.start = start;
		this.end = end;

		try {
			this.computeMinimumBeaconPath();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void computeMinimumBeaconPath() throws CloneNotSupportedException {

		ArrayList<Face> attractionRegions = new ArrayList<Face>();
		ArrayList<Point> beacons = new ArrayList<Point>(this.candidateBeacons);
		beacons.add(start);
		beacons.add(end);
		ArrayList<Point> clonedPoints;
		AttractionRegion attr;

		for(Point c: beacons) {

			clonedPoints = this.clonePoints(this.points);
			attr = new AttractionRegion(c, clonedPoints);
			attractionRegions.add(attr.face);

		}

		for(int c = 0; c < beacons.size(); c++) {

			Point b;
			Point b2;
			double dist;
			Face f = attractionRegions.get(c);
			for(int d = 0; d < beacons.size(); d++) {

				b = beacons.get(d);
				if(d!=c) {

					if(f.contains(b)) {

						b2 = beacons.get(c);
						dist = Math.sqrt(Math.pow(b2.x-b.x, 2) + Math.pow(b2.y-b.y, 2));
						g.addEdge(String.valueOf(d), String.valueOf(c), dist);

					}

				}

			}

		}

		Graph.Edge[] graph = new Graph.Edge[1];
		g.addEdges(g.edges.toArray(graph));
		int start = beacons.size() - 2;
		int end = beacons.size() - 1;

		if(!g.graph.containsKey(String.valueOf(start))) {

			this.beacons = null;
			return;
		}

		if(!g.graph.containsKey(String.valueOf(end))) {

			this.beacons = null;
			return;
		}

		g.dijkstra(String.valueOf(start));
		ArrayList<String> path = new ArrayList<String>();
		path = g.getPath(String.valueOf(end), path);

		if(path == null)
			this.beacons = null;
		else {

			int point;
			for(String d: path) {

				point = Integer.parseInt(d);
				this.beacons.add(beacons.get(point));

			}

		}

	}

	private ArrayList<Point> clonePoints(ArrayList<Point> points) throws CloneNotSupportedException {

		ArrayList<Point> clone = new ArrayList<Point>(points.size());
		for(Point item: points) clone.add((Point) item.clone());
		return clone;

	}

}
