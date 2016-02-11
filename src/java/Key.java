
public class Key implements Comparable<Key>{
	
	Edge e;
	
	public Key(Edge e) {
		
		this.e = e;
		
	}
	
	public Key(float x1, float y1, float x2, float y2) {
		
		this.e = new Edge(x1, y1, x2, y2);
		
	}
	
	public int compareTo(Key key) {
		
		return 0;
		
	}

}
