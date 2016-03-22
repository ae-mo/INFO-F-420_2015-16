
import java.util.ArrayList;

public class Face {

	public double SCREEN_X = 1608;
	public Halfedge h;

	public Face(Halfedge h) {

		this.h = h;
	}


	Face() {
	}

	public boolean contains(Point point)
	{

		Edge ray = new Edge(point, new Point(SCREEN_X + 10, point.y, null));
		int intersection = 0;

		ArrayList<Edge> edges = new ArrayList<Edge>();
		Halfedge h = this.h;
		Edge e;
		Turn t;
		
		do {
			
			e = new Edge(h.twin.target, h.target);
			t = new Turn(point, e.a, e.b);
			if(t.value != 0)
				edges.add(e);
			
			h = h.next;
			
		} while(h.target != this.h.target);
		
		h = this.h;
		
		for(int i = 0; i < edges.size(); i++) {
			
			e = edges.get(i);
			
			if(ray.strictlyIntersectsEdge(e))
				intersection++;
			
			else if(ray.intersectsEdge(e) && e.a.y == point.y)
				continue;
			
			else if(ray.intersectsEdge(e) && e.b.y == point.y) {
				
				Edge e1 = edges.get((i+1)% edges.size());
				
				Turn peBeA = new Turn(point, e.b, e.a);
				Turn pe1Ae1B = new Turn(point, e1.a, e1.b);
				
				if((peBeA.value > 0 && pe1Ae1B.value < 0) || 
						(peBeA.value < 0 && pe1Ae1B.value > 0))
					intersection++;
				
			}
			
		}

		/*
		 * If the number of intersections is odd, then the point is inside the polygon
		 */
		if (intersection % 2 != 0)
		{
			return true;
		}

		return false;
	}

	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		final Face other = (Face) obj;

		if (!this.h.equals(other.h)) {
			return false;
		}
		return true;

	}

}