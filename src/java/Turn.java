
public class Turn {
	
	float value;
	
	public Turn(Point a, Point b, Point c) {
		
		this.value = b.x*c.y - a.x*c.y + a.x*b.y - b.y*c.x + a.y*c.x - a.y*b.x;
		
	}
	
	public Turn(Edge e, Point c) {
		
		this.value = e.b.x*c.y - e.a.x*c.y + e.a.x*e.b.y - e.b.y*c.x + e.a.y*c.x - e.a.y*e.b.x;
		
	}

}
