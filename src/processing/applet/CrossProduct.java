

public class CrossProduct {
	
	public double value;
	private final double EPSILON = 0.1;

	public CrossProduct(Point a1, Point a2, Point b1, Point b2) {

		value = (a2.x - a1.x)*(b2.y - b1.y) - (b2.x - b1.x)*(a2.y - a1.y);
		
		if(value < EPSILON && value > -EPSILON)
			value = 0;
	}

	public CrossProduct(Edge a, Edge b) {

		this(a.a, a.b, b.a, b.b);

	}


}