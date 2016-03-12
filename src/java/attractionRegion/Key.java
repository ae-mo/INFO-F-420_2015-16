package attractionRegion;
import dataStructures.Edge;
import dataStructures.Point;
import dataStructures.Triangle;
import operations.Turn;

public class Key implements Comparable<Key>{

	public Point beacon;
	public Edge e;

	public Key(Point beacon, Edge e) {

		Turn bEab = new Turn(beacon, e.a, e.b);
		
		if(bEab.value <= 0) {
			
			Point temp = e.a;
			e.a = e.b;
			e.b = temp;
			
		}
		
		this.beacon = beacon;
		this.e = e;

	}

	public Key(Point beacon, float ax, float ay, float bx, float by) {

		this(beacon, new Edge(ax, ay, bx, by));

	}

	public Key(float bx, float by, Edge e) {

		this(new Point(bx, by, null), e);
	}

	public Key(float bex, float bey, float ax, float ay, float bx, float by) {

		this(new Point(bex, bey, null), new Edge(ax, ay, bx, by));

	}


	public int compareTo(Key _key) {

		int result = 0;

		if(this.equals(_key)) return 0;

		boolean intersectsBeA = _key.e.intersectsRay(this.beacon, this.e.a);
		boolean intersectsBeB = _key.e.intersectsRay(this.beacon, this.e.b);


		if(intersectsBeA && intersectsBeB) {

			Triangle tr = new Triangle(this.e.b, _key.e.b, _key.e.a);
			
			if(tr.contains(this.e.a)) {
				
				Turn eb = new Turn(this.e, _key.e.b);
				if(eb.value > 0 ) result = 1;
				else result = -1;
				
			}
			else {
				
				Turn ea = new Turn(this.e, _key.e.a);
				if(ea.value > 0 ) result = 1;
				else result = -1;
				
			}
		}
		else if(intersectsBeA) {
			Turn eb = new Turn(this.e, _key.e.b);
			if(eb.value > 0 ) result = 1;
			else result = -1;
		}
		else {

			Turn ea = new Turn(this.e, _key.e.a);
			if(ea.value > 0 ) result = 1;
			else result = -1;

		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		final Key other = (Key) obj;

		if(this.e.equals(other.e)) 
			return true;

		return false;
	}


}
