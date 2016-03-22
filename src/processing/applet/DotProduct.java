
public class DotProduct {

	public double value;
	private final double EPSILON = 0.1;

	public DotProduct(Point a1, Point a2, Point b1, Point b2) {

		value = (a1.x - a2.x)*(b1.x - b2.x) + (a1.y - a2.y)*(b1.y - b2.y);
		
		if(value < EPSILON && value > -EPSILON)
			value = 0;
		
	}

	public DotProduct(Edge a, Edge b) {

		this(a.a, a.b, b.a, b.b);

	}

	public DotProduct(Point a1, Point a2, Edge b) {

		this(a1, a2, b.a, b.b);

	}

}