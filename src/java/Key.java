
public class Key implements Comparable<Key>{
	
	Point beacon;
	Point p;
	Edge e;
	
	public Key(Point beacon, Point p, Edge e) {
		
		this.beacon = beacon;
		this.p = p;
		this.e = e;
		
		if(!e.a.equals(p))
			this.e.flip();
		
	}
	
	public Key(Point beacon, Point p, float ax, float ay, float bx, float by) {
		
		this(beacon, p, new Edge(ax, ay, bx, by));
		
	}
	
	public Key(float bx, float by, float px, float py, Edge e) {
		
		this(new Point(bx, by),  new Point(px, py), e);
	}
	
	public Key(float bex, float bey, float px, float py, float ax, float ay, float bx, float by) {
		
		this(new Point(bex, bey), new Point(px, py), new Edge(ax, ay, bx, by));
		
	}
	
	
	public int compareTo(Key _key) {
		
		int result = 0;
		
		if(this.equals(_key)) return 0;
		
		boolean intersectsBeA = _key.e.intersectsRay(this.beacon, this.e.a);
		boolean intersectsBeB = _key.e.intersectsRay(this.beacon, this.e.b);
		
		
		if(intersectsBeA && intersectsBeB) {
			
			boolean intersectsAB = _key.e.intersectsRay(this.e);
			
			if(intersectsAB) {
				Turn ea = new Turn(this.e, _key.e.a);
				if(ea.value > 0 ) result = 1;
				else result = -1;
			}
			else {
				Turn eb = new Turn(this.e, _key.e.b);
				if(eb.value > 0 ) result = 1;
				else result = -1;
			}
			
		}
		else if(!intersectsBeB) {
			
			Turn eb = new Turn(this.e, _key.e.b);
			if(eb.value > 0 ) result = 1;
			else result = -1;
			
		}
		else if(!intersectsBeA) {
			Turn ea = new Turn(this.e, _key.e.a);
			if(ea.value > 0 ) result = 1;
			else result = -1;
		}
		
		//System.out.println("Comparison: " + result);
		
		return result;
	}
	
	private void flipEdge() {
		
		Point temp = this.e.a;
		e.a = e.b;
		e.b = temp;
		
	}
	
	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		if (!Key.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Key other = (Key) obj;

		if(this.beacon.equals(other.beacon) && this.p.equals(other.p) && this.e.equals(other.e)) 
			return true;
		
		return false;
	}


}
