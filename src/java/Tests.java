import java.util.ArrayList;

import attractionRegion.AttractionRegion;
import attractionRegion.Key;
import dataStructures.DCEL;
import dataStructures.Edge;
import dataStructures.Face;
import dataStructures.Halfedge;
import dataStructures.Point;
import dataStructures.RedBlackBST;
import operations.RadialSort;
import operations.Triangulation;

public class Tests {

	public static void main(String[] args) {

		test20();

	}

	public static void test1() {

		Point q = new Point(2.64f, 3.1f);
		Point q1 = new Point(7.12f, 3.22f);
		Point q2 = new Point(5f, 6f);

		System.out.println(RadialSort.compare(q, q1, q2));

	}

	public static void test2() {

		Point q = new Point(1f, 2f);
		Point q1 = new Point(3f, 2f);
		Point q2 = new Point(5f, 2f);

		System.out.println(RadialSort.compare(q, q1, q1));

	}

	public static void test3() {

		Point q = new Point(2.64f, 3.1f);

		ArrayList<Point> points = new ArrayList<Point>();

		points.add(new Point(3.08f, 0.74f));
		points.add(new Point(-9.13f, 8.7f));
		points.add(new Point(5f, 6f));
		points.add(new Point(-1.418f, 1.12f));
		points.add(new Point(0.98f, 5.27f));
		points.add(new Point(7.12f, 3.22f));
		points.add(new Point(-1.31f, 1.18f));
		points.add(new Point(-4.49f, 4.1f));

		RadialSort sort = new RadialSort(q, points);


		System.out.println();
		for(Point p: sort.result)
			System.out.println(p.x + ", " + p.y);


	}

	public static void test4() {

		Point b = new Point(9.82928, -2.23581);

		Point p = new Point(19.47275, -5.43088);
		Edge e = new Edge(new Point(19.47275, -5.43088), new Point(19.28932, 1.57012));
		Point p1 = new Point(20.7262, 4.1076);
		Edge q = new Edge(new Point(20.7262, 4.1076), new Point(22.34652, -7.47921));

		Key key = new Key(b, p, e);
		Key key2 = new Key(b, p1, q);

		System.out.println(key.compareTo(key2));

	}

	public static void test5() {

		Point b = new Point(9.82928, -2.23581);

		Point p = new Point(19.47275, -5.43088);
		Edge e = new Edge(new Point(19.47275, -5.43088), new Point(19.28932, 1.57012));
		Point p1 = new Point(17.36328, -7.66264);
		Edge q = new Edge(p1, new Point(15.74296, 3.03758));

		Key key = new Key(b, p, e);
		Key key2 = new Key(b, p1, q);

		System.out.println(key.compareTo(key2));

	}


	public static void test6() {

		Point b = new Point(9.82928, -2.23581);

		Point p = new Point(19.47275, -5.43088);
		Edge e = new Edge(new Point(19.47275, -5.43088), new Point(19.28932, 1.57012));
		Point p1 = new Point(22.49938, -1.97624);
		Edge q = new Edge(p1, new Point(25.4343, -0.99793));

		Key key = new Key(b, p, e);
		Key key2 = new Key(b, p1, q);

		System.out.println(key.compareTo(key2));

	}

	public static void test7() {

		Point b = new Point(9.82928, -2.23581);

		Point p = new Point(19.47275, -5.43088);
		Edge e = new Edge(new Point(19.47275, -5.43088), new Point(19.28932, 1.57012));
		Point p1 = new Point(22.49938, -1.97624);
		Edge q = new Edge(new Point(25.4343, -0.99793), p1);

		Key key = new Key(b, p, e);
		Key key2 = new Key(b, p1, q);

		System.out.println(key.compareTo(key2));

	}

	public static void test8() {

		Point b = new Point(9.82928, -2.23581);

		Point p = new Point(19.47275, -5.43088);
		Edge e = new Edge(new Point(19.47275, -5.43088), new Point(19.28932, 1.57012));
		Point p1 = new Point(24.07306, -0.19516);
		Edge q = new Edge(p1, new Point(25.28144, 3.92417));

		Key key = new Key(b, p, e);
		Key key2 = new Key(b, p1, q);

		System.out.println(key.compareTo(key2));

	}

	public static void test9() {

		Point b = new Point(9.82928, -2.23581);

		Point p = new Point(19.47275, -5.43088);
		Edge e = new Edge(new Point(19.47275, -5.43088), new Point(19.28932, 1.57012));
		Point p1 = new Point(26.16803, -8.94667);
		Edge q = new Edge(p1, new Point(25.49545, -3.35198));

		Key key = new Key(b, p, e);
		Key key2 = new Key(b, p1, q);

		System.out.println(key.compareTo(key2));

	}

	public static void test10() {

		Point b = new Point(9.82928, -2.23581);

		Point p = new Point(19.47275, -5.43088);
		Edge e = new Edge(new Point(19.47275, -5.43088), new Point(19.28932, 1.57012));
		Point p1 = new Point(18.37215, -2.3431);
		Edge q = new Edge(p1, new Point(18.00529, -1.15079));

		Key key = new Key(b, p, e);
		Key key2 = new Key(b, p1, q);

		System.out.println(key.compareTo(key2));

	}

	public static void test11() {

		Point b = new Point(9.82928, -2.23581);

		Point p = new Point(19.47275, -5.43088);
		Edge e = new Edge(new Point(19.47275, -5.43088), new Point(19.28932, 1.57012));
		Point p1 = new Point(12.77747, -8.51866);
		Edge q = new Edge(p1, new Point(14.09206, -2.95455));

		Key key = new Key(b, p, e);
		Key key2 = new Key(b, p1, q);

		System.out.println(key.compareTo(key2));

	}

	public static void test12() {

		Point b = new Point(9.82928, -2.23581);

		Point p = new Point(19.47275, -5.43088);
		Edge e = new Edge(new Point(19.47275, -5.43088), new Point(19.28932, 1.57012));
		Point p1 = new Point(14.18378, -0.75336);
		Edge q = new Edge(p1, new Point(11.15571, 3.27396));

		Key key = new Key(b, p, e);
		Key key2 = new Key(b, p1, q);

		System.out.println(key.compareTo(key2));

	}

	public static void test13() {

		RedBlackBST<Key, Edge> status = new RedBlackBST<Key, Edge>();

		Point beacon = new Point(6.82772, 7.07683);
		Point a = new Point(10.89538, 5.94496);
		Point b = new Point(11.99188, 12.66544);

		Point a1 = new Point(15.63509, 1.77119);
		Point b1 = new Point(18.95995, 10.86152);

		Point a2 = new Point(23.69966, 1.59434);
		Point b2 = new Point(26.10488, 13.6912);

		Edge e = new Edge(a, b);
		Key k = new Key(beacon, a, e);

		Edge e1 = new Edge(a1, b1);
		Key k1 = new Key(beacon, a1, e1);

		Edge e2 = new Edge(a2, b2);
		Key k2 = new Key(beacon, a2, e2);

		status.put(k, e);
		status.put(k1, e1);
		status.put(k2, e2);

		Key k11 = status._max();
		System.out.println(k11.e.a.x + ", " + k11.e.a.y + "; " + k11.e.b.x + ", " + k11.e.b.y);

		for(Key key: status._keys())
			System.out.println(key.e.a.x + ", " + key.e.a.y + "; " + key.e.b.x + ", " + key.e.b.y);

	}

	public static void test14() {

		RedBlackBST<Key, Edge> status = new RedBlackBST<Key, Edge>();

		Point beacon = new Point(22.14334, 11.39209);
		Point a = new Point(18.95995, 10.86152);
		Point b = new Point(15.63509, 1.77119);

		Point a1 = new Point(11.99188, 12.66544);
		Point b1 = new Point(10.89538, 5.94496);

		Point a2 = new Point(3.53823, 11.03838);
		Point b2 = new Point(5.48363, 2.08953);

		Edge e = new Edge(a, b);
		Key k = new Key(beacon, a, e);

		Edge e1 = new Edge(a1, b1);
		Key k1 = new Key(beacon, a1, e1);

		Edge e2 = new Edge(a2, b2);
		Key k2 = new Key(beacon, a2, e2);

		status.put(k, e);
		status.put(k1, e1);
		status.put(k2, e2);

		Key k11 = status._max();
		System.out.println(k11.e.a.x + ", " + k11.e.a.y + "; " + k11.e.b.x + ", " + k11.e.b.y);

		System.out.println();

		for(Key key: status._keys())
			System.out.println(key.e.a.x + ", " + key.e.a.y + "; " + key.e.b.x + ", " + key.e.b.y);

	}

	//	public static void test15() {
	//
	//		DCEL dcel = new DCEL();
	//		ArrayList<Point> points = new ArrayList<Point>();
	//
	//		Point p1 = new Point(5.48363, 2.08953);
	//		Point p2 = new Point(10.89538, 5.94496);
	//
	//		int he = dcel.initialize(p1, p2);
	//
	//		Halfedge h = dcel.halfedges.get(he);
	//
	//		System.out.println("Halfedge");
	//		System.out.println("target: " + h.target.x + ", " + h.target.y);
	//		System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
	//		System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
	//		System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
	//
	//		points.add(new Point(11.99188, 12.66544));
	//		points.add(new Point(18.88921, 10.82615));
	//		points.add(new Point(15.63509, 1.77119));
	//		points.add(new Point(23.66429, 1.59434));
	//		points.add(new Point(25.60969, 15.81345));
	//		points.add(new Point(10.04648, 18.39553));
	//		points.add(new Point(3.50286, 11.07375));
	//
	//		for(Point p: points) {
	//
	//			p2 =  p;
	//			he = dcel.addVertexAt(0, he, p2);
	//			h = dcel.halfedges.get(he);
	//
	//			System.out.println();
	//			System.out.println("Halfedge");
	//			System.out.println("target: " + h.target.x + ", " + h.target.y);
	//			System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
	//			System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
	//			System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
	//
	//		}
	//
	//		Point v = dcel.vertices.get(0);
	//		Halfedge vh = dcel.halfedges.get(he);
	//
	//		System.out.println();
	//
	//		System.out.println();
	//		System.out.println("point: " + v.x + ", " + v.y);
	//		System.out.println(" hook halfedge");
	//		System.out.println("target: " + vh.target.x + ", " + vh.target.y);
	//		System.out.println("twin target: " + vh.twin.target.x + ", " + vh.twin.target.y);
	//		System.out.println("next target: " + vh.next.target.x + ", " + vh.next.target.y);
	//		System.out.println("prev target: " + vh.prev.target.x + ", " + vh.prev.target.y);
	//
	//		dcel.splitFace(0, he, 0);
	//
	//		p1.h = p1.h.prev.twin;
	//		h = p1.h;
	//
	//		while(!h.target.equals(p1)) {
	//
	//			System.out.println();
	//			System.out.println("Halfedge after split");
	//			System.out.println("target: " + h.target.x + ", " + h.target.y);
	//			System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
	//			System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
	//			System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
	//
	//			h =h.next;
	//		}
	//
	//		for(Point p: dcel.vertices) {
	//
	//			Halfedge hp = p.h;
	//
	//			System.out.println();
	//			System.out.println("vertex:" + p.x + ", " + p.y);
	//			System.out.println("Halfedge");
	//			System.out.println("target: " + hp.target.x + ", " + hp.target.y);
	//			System.out.println("twin target: " + hp.twin.target.x + ", " + hp.twin.target.y);
	//			System.out.println("next target: " + hp.next.target.x + ", " + hp.next.target.y);
	//			System.out.println("prev target: " + hp.prev.target.x + ", " + hp.prev.target.y);
	//		}
	//
	//	}

	//	public static void test16() {
	//
	//		DCEL dcel = new DCEL();
	//		ArrayList<Point> points = new ArrayList<Point>();
	//
	//		Point p1 = new Point(5.48363, 2.08953);
	//		Point p2 = new Point(10.89538, 5.94496);
	//
	//		int he = dcel.initialize(p1, p2);
	//
	//		Halfedge h = dcel.halfedges.get(he);
	//
	//		points.add(new Point(11.99188, 12.66544));
	//		points.add(new Point(18.88921, 10.82615));
	//		points.add(new Point(15.63509, 1.77119));
	//		points.add(new Point(23.66429, 1.59434));
	//		points.add(new Point(25.60969, 15.81345));
	//		points.add(new Point(10.04648, 18.39553));
	//		points.add(new Point(3.50286, 11.07375));
	//
	//
	//
	//		for(Point p: points) {
	//
	//			p2 =  p;
	//			he = dcel.addVertexAt(0, he, p2);
	//
	//
	//			h = dcel.halfedges.get(he);
	//
	//
	//		}
	//
	//
	//
	//		dcel.splitFace(0, he, 0);
	//		p1.h = p1.h.prev.twin;
	//
	//		h = dcel.halfedges.get(dcel.halfedges.size() - 2);
	//
	//		Point b = new Point(18.60624, 3.07992);
	//
	//		AttractionRegionSimple attr = new AttractionRegionSimple(b, dcel);
	//
	//		System.out.println();
	//		System.out.println("Ray vertices");
	//
	//		for(Point p: attr.rayVertices)
	//			System.out.println(p.x + ", " + p.y);
	//
	//
	//	}

	//	public static void test17() {
	//
	//		DCEL dcel = new DCEL();
	//		ArrayList<Point> points = new ArrayList<Point>();
	//
	//		Point p1 = new Point(5.48363, 2.08953);
	//		Point p2 = new Point(10.89538, 5.94496);
	//
	//		int he = dcel.initialize(p1, p2);
	//
	//		Halfedge h = dcel.halfedges.get(he);
	//
	//		System.out.println("Halfedge");
	//		System.out.println("target: " + h.target.x + ", " + h.target.y);
	//		System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
	//		System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
	//		System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
	//
	//		points.add(new Point(11.99188, 12.66544));
	//		points.add(new Point(18.88921, 10.82615));
	//		points.add(new Point(15.63509, 1.77119));
	//		points.add(new Point(23.66429, 1.59434));
	//		points.add(new Point(25.60969, 15.81345));
	//		points.add(new Point(10.04648, 18.39553));
	//		points.add(new Point(3.50286, 11.07375));
	//
	//		int toSplit = 0;
	//
	//		for(Point p: points) {
	//
	//			p2 =  p;
	//			he = dcel.addVertexAt(0, he, p2);
	//			if(p2.equals(new Point(3.50286, 11.07375)))
	//				toSplit = he;
	//			h = dcel.halfedges.get(he);
	//
	//			System.out.println();
	//			System.out.println("Halfedge");
	//			System.out.println("target: " + h.target.x + ", " + h.target.y);
	//			System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
	//			System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
	//			System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
	//
	//		}
	//
	//		Point v = dcel.vertices.get(0);
	//		Halfedge vh = dcel.halfedges.get(he);
	//
	//		System.out.println();
	//
	//		System.out.println();
	//		System.out.println("point: " + v.x + ", " + v.y);
	//		System.out.println(" hook halfedge");
	//		System.out.println("target: " + vh.target.x + ", " + vh.target.y);
	//		System.out.println("twin target: " + vh.twin.target.x + ", " + vh.twin.target.y);
	//		System.out.println("next target: " + vh.next.target.x + ", " + vh.next.target.y);
	//		System.out.println("prev target: " + vh.prev.target.x + ", " + vh.prev.target.y);
	//
	//		dcel.splitFace(0, he, 0);
	//
	//		p1.h = p1.h.prev.twin;
	//		h = p1.h;
	//
	//		dcel.splitEdge(dcel.halfedges.get(toSplit).twin, new Point(8.913035115839046, 17.127297113012066));
	//
	//		while(!h.target.equals(p1)) {
	//
	//			System.out.println();
	//			System.out.println("Halfedge after split");
	//			System.out.println("target: " + h.target.x + ", " + h.target.y);
	//			System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
	//			System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
	//			System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
	//
	//			h =h.next;
	//		}
	//
	//		//		for(Point p: dcel.vertices) {
	//		//			
	//		//			Halfedge hp = p.h;
	//		//			
	//		//			System.out.println();
	//		//			System.out.println("vertex:" + p.x + ", " + p.y);
	//		//			System.out.println("Halfedge");
	//		//			System.out.println("target: " + hp.target.x + ", " + hp.target.y);
	//		//			System.out.println("twin target: " + hp.twin.target.x + ", " + hp.twin.target.y);
	//		//			System.out.println("next target: " + hp.next.target.x + ", " + hp.next.target.y);
	//		//			System.out.println("prev target: " + hp.prev.target.x + ", " + hp.prev.target.y);
	//		//		}
	//
	//	}
	//
	//	public static void test18() {
	//
	//		DCEL dcel = new DCEL();
	//		ArrayList<Point> points = new ArrayList<Point>();
	//
	//		Point p1 = new Point(5.48363, 2.08953);
	//		Point p2 = new Point(10.89538, 5.94496);
	//
	//		int he = dcel.initialize(p1, p2);
	//		int splitH = he;
	//
	//		Halfedge h = dcel.halfedges.get(he);
	//
	//		System.out.println("Halfedge");
	//		System.out.println("target: " + h.target.x + ", " + h.target.y);
	//		System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
	//		System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
	//		System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
	//
	//		points.add(new Point(11.99188, 12.66544));
	//		points.add(new Point(18.88921, 10.82615));
	//		points.add(new Point(15.63509, 1.77119));
	//		points.add(new Point(23.66429, 1.59434));
	//		points.add(new Point(25.60969, 15.81345));
	//		points.add(new Point(10.04648, 18.39553));
	//		points.add(new Point(3.50286, 11.07375));
	//
	//		int toSplit = 0;
	//
	//		for(Point p: points) {
	//
	//			p2 =  p;
	//			he = dcel.addVertexAt(0, he, p2);
	//			if(p2.equals(new Point(3.50286, 11.07375)))
	//				toSplit = he;
	//			h = dcel.halfedges.get(he);
	//
	//			System.out.println();
	//			System.out.println("Halfedge");
	//			System.out.println("target: " + h.target.x + ", " + h.target.y);
	//			System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
	//			System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
	//			System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
	//
	//		}
	//
	//		Point v = dcel.vertices.get(0);
	//		Halfedge vh = dcel.halfedges.get(he);
	//
	//		System.out.println();
	//
	//		System.out.println();
	//		System.out.println("point: " + v.x + ", " + v.y);
	//		System.out.println(" hook halfedge");
	//		System.out.println("target: " + vh.target.x + ", " + vh.target.y);
	//		System.out.println("twin target: " + vh.twin.target.x + ", " + vh.twin.target.y);
	//		System.out.println("next target: " + vh.next.target.x + ", " + vh.next.target.y);
	//		System.out.println("prev target: " + vh.prev.target.x + ", " + vh.prev.target.y);
	//
	//		int[] newFaces = dcel.splitFace(0, he, 0);
	//
	//		p1.h = p1.h.prev.twin;
	//		h = p1.h;
	//
	//		dcel.splitEdge(dcel.halfedges.get(toSplit).twin, new Point(8.913035115839046, 17.127297113012066));
	//
	//		while(!h.target.equals(p1)) {
	//
	//			System.out.println();
	//			System.out.println("Halfedge after split");
	//			System.out.println("target: " + h.target.x + ", " + h.target.y);
	//			System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
	//			System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
	//			System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
	//
	//			h =h.next;
	//		}
	//
	//
	//		int[] newFaces2 = dcel.splitFace(newFaces[0], splitH, dcel.vertices.size() - 1);
	//
	//		h = dcel.faces.get(newFaces2[0]).h; 
	//
	//		Point pp = h.target;
	//
	//		for(int i = 0; i < 5; i++) {
	//
	//			System.out.println();
	//			System.out.println("Halfedge after SECOND split");
	//			System.out.println("target: " + h.target.x + ", " + h.target.y);
	//			System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
	//			System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
	//			System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
	//
	//			h =h.next;
	//		}
	//
	//	}

	public static void test19() {

		DCEL dcel = new DCEL();
		ArrayList<Point> points = new ArrayList<Point>();

		Point p1 = new Point(5.48363, 2.08953);
		Point p2 = new Point(10.89538, 5.94496);

		int he = dcel.initialize(p1, p2);

		Halfedge h = dcel.halfedges.get(he);

		points.add(new Point(11.99188, 12.66544));
		points.add(new Point(18.88921, 10.82615));
		points.add(new Point(15.63509, 1.77119));
		points.add(new Point(23.66429, 1.59434));
		points.add(new Point(25.60969, 15.81345));
		points.add(new Point(10.04648, 18.39553));
		points.add(new Point(3.50286, 11.07375));



		for(Point p: points) {

			p2 =  p;
			he = dcel.addVertexAt(0, he, p2);


			h = dcel.halfedges.get(he);


		}



		dcel.splitFace(0, dcel.halfedges.get(he), 0);
		p1.h = p1.h.prev.twin;

		for(int i = 0; i < dcel.faces.size(); i++) {

			Face f = dcel.faces.get(i);

			System.out.println();
			System.out.println("****FACE #" + i +"****"); 

			h = f.h;
			Point pp = h.target;

			for(int j = 0; j < 10; j++) {

				System.out.println();
				System.out.println("Halfedge");
				System.out.println("target: " + h.target.x + ", " + h.target.y);
				System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
				System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
				System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);

				h =h.next;
			}


		}

		Point b = new Point(18.60624, 3.07992);

		AttractionRegion attr = new AttractionRegion(b, dcel);

		System.out.println();
		System.out.println("****FACES OF THE ARRANGEMENT****");

		for(int i = 0; i < dcel.faces.size(); i++) {

			Face f = dcel.faces.get(i);

			System.out.println();
			System.out.println("****FACE #" + i +"****"); 

			h = f.h;
			Point pp = h.target;

			for(int j = 0; j < 10; j++) {

				System.out.println();
				System.out.println("Halfedge");
				System.out.println("target: " + h.target.x + ", " + h.target.y);
				System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
				System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
				System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);

				h =h.next;
			}


		}


	}

	public static void test20() {

		DCEL dcel = new DCEL();
		ArrayList<Point> points = new ArrayList<Point>();

		Point p1 = new Point(525, 87);
		Point p2 = new Point(645, 197);

		int he = dcel.initialize(p1, p2);

		Halfedge h = dcel.halfedges.get(he);

		points.add(new Point(476, 330));
		points.add(new Point(511, 220));
		points.add(new Point (369, 271));
		points.add(new Point(460, 426));
		points.add(new Point(687, 223));
		points.add(new Point(581, 542));
		points.add(new Point(258, 489));
		points.add(new Point(319, 212));
		
		

		for(Point p: points) {

			p2 =  p;
			he = dcel.addVertexAt(0, he, p2);


			h = dcel.halfedges.get(he);


		}



		dcel.splitFace(0, dcel.halfedges.get(he), 0);
		p1.h = p1.h.prev.twin;

		Point b = new Point (436, 210);

		AttractionRegion attr = new AttractionRegion(b, dcel);

		System.out.println();
		System.out.println("****FACES OF THE ARRANGEMENT****");

		for(int i = 0; i < dcel.faces.size(); i++) {

			Face f = dcel.faces.get(i);

			System.out.println();
			System.out.println("****FACE #" + i +"****"); 

			h = f.h;
			Point pp = h.target;

			for(int j = 0; j < 15; j++) {

				System.out.println();
				System.out.println("Halfedge");
				System.out.println("target: " + h.target.x + ", " + h.target.y);

				h =h.next;
			}


		}


	}

	public static void test21() {

		DCEL dcel = new DCEL();
		ArrayList<Point> points = new ArrayList<Point>();

		Point p1 = new Point(6.76, 4.3);
		Point p2 = new Point(3.24, 5.52);

		int he = dcel.initialize(p1, p2);

		Halfedge h = dcel.halfedges.get(he);

		points.add(new Point(1.8, 1.54));
		points.add(new Point(6.46, 0.4));


		for(Point p: points) {

			p2 =  p;
			he = dcel.addVertexAt(0, he, p2);


			h = dcel.halfedges.get(he);


		}



		dcel.splitFace(0, dcel.halfedges.get(he), 0);
		p1.h = p1.h.prev.twin;

		for(int i = 0; i < dcel.faces.size(); i++) {

			Face f = dcel.faces.get(i);

			System.out.println();
			System.out.println("****FACE #" + i +"****"); 

			h = f.h;
			Point pp = h.target;

			for(int j = 0; j < 15; j++) {

				System.out.println();
				System.out.println("Halfedge");
				System.out.println("target: " + h.target.x + ", " + h.target.y);
				System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
				System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
				System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);

				h =h.next;
			}


		}


		Triangulation tr = new Triangulation(dcel);

		System.out.println();
		System.out.println("****FACES OF THE ARRANGEMENT****");

		for(int i = 0; i < dcel.faces.size(); i++) {

			Face f = dcel.faces.get(i);

			System.out.println();
			System.out.println("****FACE #" + i +"****"); 

			h = f.h;
			Point pp = h.target;

			for(int j = 0; j < 15; j++) {

				System.out.println();
				System.out.println("Halfedge");
				System.out.println("target: " + h.target.x + ", " + h.target.y);
				System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
				System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
				System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);

				h =h.next;
			}


		}


	}

	public static void test22() {

		DCEL dcel = new DCEL();
		ArrayList<Point> points = new ArrayList<Point>();

		Point p1 = new Point(87.0655, 323.47837);
		Point p2 = new Point (23.66695, 338.79016);

		int he = dcel.initialize(p1, p2);

		Halfedge h = dcel.halfedges.get(he);

		points.add(new Point(62.58054, 220.8894));
		points.add(new Point(15.79678, 211.70233));
		points.add(new Point(26.72757, 95.33276));
		points.add(new Point(125.54187, 187.20347));
		points.add(new Point(80.50703, 76.95861));
		points.add(new Point(204.68076, 70.8339));
		points.add(new Point(262.3953, 167.29815));
		points.add(new Point(149.5896, 236.20119));
		points.add(new Point(208.61584, 276.01183));


		for(Point p: points) {

			p2 =  p;
			he = dcel.addVertexAt(0, he, p2);

			h = dcel.halfedges.get(he);


		}

		dcel.splitFace(0, dcel.halfedges.get(he), 0);
		System.out.println("p1 target:" + p1.h.target.x + ", " + p1.h.target.y);
		p1.h = p1.h.prev.twin;
		System.out.println("p1 target:" + p1.h.target.x + ", " + p1.h.target.y);
		
		
		

//		for(int i = 0; i < dcel.faces.size(); i++) {
//
//			Face f = dcel.faces.get(i);
//
//			System.out.println();
//			System.out.println("****FACE #" + i +"****"); 
//
//			h = f.h;
//			Point pp = h.target;
//
//			for(int j = 0; j < 15; j++) {
//
//				System.out.println();
//				System.out.println("Halfedge");
//				System.out.println("target: " + h.target.x + ", " + h.target.y);
//				System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
//				System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
//				System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
//
//				h =h.next;
//			}
//
//
//		}


		Triangulation tr = new Triangulation(dcel);

		System.out.println();
		System.out.println("****FACES OF THE ARRANGEMENT****");

		for(int i = 1; i < dcel.faces.size(); i++) {

			Face f = dcel.faces.get(i);

			System.out.println();
			System.out.println("****FACE #" + i +"****"); 

			h = f.h;
			Point pp = h.target;

			for(int j = 0; j < 3; j++) {

				System.out.println();
				System.out.println("Halfedge");
				System.out.println("target: " + h.target.x + ", " + h.target.y);
				System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
				System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
				System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);


				h =h.next;
			}


		}


	}

}
