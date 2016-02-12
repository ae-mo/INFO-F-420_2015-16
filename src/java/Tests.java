import java.util.ArrayList;

public class Tests {

	public static void main(String[] args) {

		test12();

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
		
		Point beacon = new Point(12, -2);
		Point a = new Point(13.29273, -4.18535);
		Point b = new Point(20.91224, -0.36486);
		
		Edge e = new Edge(a, b);
		Key k = new Key(beacon, a, e);
		
		status.put(k, e);
		
		
		
	}

}
