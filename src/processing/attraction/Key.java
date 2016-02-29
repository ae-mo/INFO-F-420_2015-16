public class Key implements Comparable<Key>{

	public Point beacon;
	public Point p;
	public Edge e;

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

			if(intersectsAB && !this.e.a.equals(_key.e.a)) { 

				System.out.println("heyheyhey");
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
		
		System.out.println();
		System.out.println(" this beacon:" + this.beacon.x + ", " + this.beacon.y );
		System.out.println(" this ref:" + this.p.x + ", " + this.p.y );
		System.out.println(" this edge:" + this.e.a.x + ", " + this.e.a.y + "; " + this.e.b.x + ", " + this.e.b.y);
		
		System.out.println(" other beacon:" + other.beacon.x + ", " + other.beacon.y );
		System.out.println(" other ref:" + other.p.x + ", " + other.p.y );
		System.out.println(" other edge:" + other.e.a.x + ", " + other.e.a.y + "; " + other.e.b.x + ", " + other.e.b.y);

		if(this.e.equals(other.e)) 
			return true;

		return false;
	}


}