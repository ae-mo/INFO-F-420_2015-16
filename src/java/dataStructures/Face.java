package dataStructures;

import operations.Turn;

public class Face {

	public Point n;
	public Halfedge h;

	public Face(Halfedge h) {

		this.h = h;
	}


	Face() {
	}

	public boolean contains(Point p) {

		Halfedge h = this.h;
		int count = 0;
		boolean intersectsPrevious = false;
		do {

			Edge e = h.getEdge();

			if(e.intersectsRay(new Edge(p.x, p.y, p.x+1, p.y))) {
				
				if(!intersectsPrevious) {
					intersectsPrevious = true;
					count++;
				}
				else {
					
					Turn pP1eA = new Turn(p, new Point(p.x+1, p.y), e.a);
					if(pP1eA.value != 0)
						count++;
					
				}
					

			}
			else if(intersectsPrevious)
				intersectsPrevious = false;

			h = h.next;

		} while(h.target != this.h.target);

		if(count % 2 == 0) return false;
		return true;

	}
}

