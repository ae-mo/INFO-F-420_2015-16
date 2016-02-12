import java.util.ArrayList;

public class Tests {

	public static void main(String[] args) {

		test16();

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

		System.out.println();

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

	public static void test15() {

		DCEL dcel = new DCEL();
		ArrayList<Point> points = new ArrayList<Point>();
		
		Point p1 = new Point(5.48363, 2.08953);
		Point p2 = new Point(10.89538, 5.94496);
		
		int he = dcel.initialize(p1, p2);
		
		Halfedge h = dcel.halfedges.get(he);
		
		System.out.println("Halfedge");
		System.out.println("target: " + h.target.x + ", " + h.target.y);
		System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
		System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
		System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
		
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
			
			System.out.println();
			System.out.println("Halfedge");
			System.out.println("target: " + h.target.x + ", " + h.target.y);
			System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
			System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
			System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
			
		}
		
		dcel.splitFace(0, he, 0);
		h = dcel.halfedges.get(dcel.halfedges.size() - 2);
		
		System.out.println();
		System.out.println("Last halfedge");
		System.out.println("target: " + h.target.x + ", " + h.target.y);
		System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
		System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
		System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);
		
		

	}
	
	public static void test16() {

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
		
		dcel.splitFace(0, he, 0);
		h = dcel.halfedges.get(dcel.halfedges.size() - 2);
		
		Point b = new Point(6.82772, 7.07683);
		
		AttractionRegionSimple attr = new AttractionRegionSimple(b, dcel);
		
		System.out.println();
		System.out.println("Ray vertices");
		
		for(Point p: attr.rayVertices)
			System.out.println(p.x + ", " + p.y);
		

	}

}
