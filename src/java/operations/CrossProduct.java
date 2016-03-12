package operations;
import dataStructures.Edge;
import dataStructures.Point;

public class CrossProduct {
	
	public double value;


	public CrossProduct(Point a1, Point a2, Point b1, Point b2) {

		value = (a2.x - a1.x)*(b2.y - b1.y) - (b2.x - b1.x)*(a2.y - a1.y);

	}

	public CrossProduct(Edge a, Edge b) {

		this(a.a, a.b, b.a, b.b);

	}


}
