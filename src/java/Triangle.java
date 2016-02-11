
class Triangle {
	
	Point a;
	Point b;
	Point c;
	
	public Triangle(Point a, Point b, Point c) {
		
		this.a = a;
		this.b = b;
		this.c = c;
		
	}
	
	public Triangle(float ax, float ay, float bx, float by, float cx, float cy) {
		
		this(new Point(ax, ay), new Point(bx, by), new Point(cx, cy));
		
	}
	
	public boolean contains(Point q) {
		
		return false;
		
	}
	
	public boolean contains(float qx, float qy) {
		
		return contains(new Point(qx, qy));
		
	}
	
	public boolean strictlyContains(Point q) {
		
		return false;
		
	}
	
	public boolean strictlyContains(float qx, float qy) {
		
		return strictlyContains(new Point(qx, qy));
		
	}

}
