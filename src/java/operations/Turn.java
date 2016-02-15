package operations;
import dataStructures.Edge;
import dataStructures.Point;

public class Turn {
	
	public double value;
	private final double EPSILON = 0.00000000001;
	
	public Turn(Point a, Point b, Point c) {
		
		double result = b.x*c.y - a.x*c.y + a.x*b.y - b.y*c.x + a.y*c.x - a.y*b.x;
		
		if(result < EPSILON && result > -EPSILON)
			result = 0;
		
		this.value = result;
		
	}
	
	public Turn(Edge e, Point c) {
		
		this.value = e.b.x*c.y - e.a.x*c.y + e.a.x*e.b.y - e.b.y*c.x + e.a.y*c.x - e.a.y*e.b.x;
		
	}

}
