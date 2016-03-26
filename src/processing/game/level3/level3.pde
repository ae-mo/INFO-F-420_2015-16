/* @pjs font="../../fonts/Charybdis.ttf"; */

ArrayList<Point> points, beacons, defaultBeacons;
Point start, end;
Point target, previousTarget;
AttractionRegion attr;
DCEL polygon;
boolean started, hint, hintShown, ready;
float startMillis, currentMillis;
int count, r, g, b, MAX_SECS, MIN_BEACONS;
int strokePoly;
int SHIFT_X, SHIFT_Y;
int nextTarget; 
PImage bg;
void setup() {

  size(1068, 600);
  myFont = createFont("../../fonts/Charybdis.ttf");
  textFont(myFont);
  MIN_BEACONS = 8;
  MAX_SECS = 5;
  SHIFT_X = 0;
  SHIFT_Y = 0;
  strokePoly = 2;
  started = false;
  hint = false;
  hintShown = false;
  ready = false;
  nextTarget = 1;
  lose = win = false;
  noLoop();
  
  r= g = b= 255;
  
  points = new ArrayList<Point>();
  defaultBeacons = new ArrayList<Point>();
  
  points.add(new Point(259, 87, null));
  points.add(new Point(204, 127, null));
  points.add(new Point(311, 112, null));
  points.add(new Point(307, 64, null));
  points.add(new Point(346, 113, null));
  points.add(new Point(380, 110, null));
  points.add(new Point(345, 68, null));
  points.add(new Point(423, 120, null));
  points.add(new Point(452, 102, null));
  points.add(new Point(453, 117, null));
  points.add(new Point(504, 76, null));
  points.add(new Point(470, 35, null));
  points.add(new Point(561, 94, null));
  points.add(new Point(473, 134, null));
  points.add(new Point(591, 102, null));
  points.add(new Point(538, 141, null));
  points.add(new Point(657, 115, null));
  points.add(new Point(623, 49, null));
  points.add(new Point(693, 117, null));
  points.add(new Point(732, 81, null));
  points.add(new Point(747, 39, null));
  points.add(new Point(757, 121, null));
  points.add(new Point(719, 129, null));
  points.add(new Point(748, 167, null));
  points.add(new Point(818, 152, null));
  points.add(new Point(792, 69, null));
  points.add(new Point(855, 162, null));
  points.add(new Point(793, 197, null));
  points.add(new Point(827, 238, null));
  points.add(new Point(740, 191, null));
  points.add(new Point(763, 249, null));
  points.add(new Point(693, 154, null));
  points.add(new Point(694, 234, null));
  points.add(new Point(669, 136, null));
  points.add(new Point(647, 224, null));
  points.add(new Point(624, 149, null));
  points.add(new Point(613, 224, null));
  points.add(new Point(583, 161, null));
  points.add(new Point(560, 207, null));
  points.add(new Point(536, 157, null));
  points.add(new Point(490, 196, null));
  points.add(new Point(514, 142, null));
  points.add(new Point(436, 176, null));
  points.add(new Point(447, 133, null));
  points.add(new Point(384, 156, null));
  points.add(new Point(428, 222, null));
  points.add(new Point(458, 195, null));
  points.add(new Point(486, 223, null));
  points.add(new Point(464, 269, null));
  points.add(new Point(382, 268, null));
  points.add(new Point(421, 246, null));
  points.add(new Point(376, 207, null));
  points.add(new Point(314, 200, null));
  points.add(new Point(353, 163, null));
  points.add(new Point(313, 152, null));
  points.add(new Point(261, 217, null));
  points.add(new Point(278, 261, null));
  points.add(new Point(305, 217, null));
  points.add(new Point(368, 226, null));
  points.add(new Point(318, 263, null));
  points.add(new Point(386, 243, null));
  points.add(new Point(334, 299, null));
  points.add(new Point(409, 298, null));
  points.add(new Point(424, 283, null));
  points.add(new Point(462, 313, null));
  points.add(new Point(527, 300, null));
  points.add(new Point(505, 251, null));
  points.add(new Point(579, 303, null));
  points.add(new Point(528, 207, null));
  points.add(new Point(614, 292, null));
  points.add(new Point(645, 244, null));
  points.add(new Point(641, 292, null));
  points.add(new Point(675, 210, null));
  points.add(new Point(674, 300, null));
  points.add(new Point(709, 205, null));
  points.add(new Point(720, 320, null));
  points.add(new Point(789, 282, null));
  points.add(new Point(754, 206, null));
  points.add(new Point(826, 257, null));
  points.add(new Point(857, 227, null));
  points.add(new Point(825, 199, null));
  points.add(new Point(881, 178, null));
  points.add(new Point(901, 258, null));
  points.add(new Point(806, 282, null));
  points.add(new Point(879, 346, null));
  points.add(new Point(767, 319, null));
  points.add(new Point(818, 382, null));
  points.add(new Point(715, 348, null));
  points.add(new Point(772, 395, null));
  points.add(new Point(669, 360, null));
  points.add(new Point(699, 315, null));
  points.add(new Point(611, 328, null));
  points.add(new Point(665, 395, null));
  points.add(new Point(562, 375, null));
  points.add(new Point(593, 349, null));
  points.add(new Point(544, 320, null));
  points.add(new Point(494, 331, null));
  points.add(new Point(553, 403, null));
  points.add(new Point(431, 329, null));
  points.add(new Point(421, 395, null));
  points.add(new Point(344, 322, null));
  points.add(new Point(351, 377, null));
  points.add(new Point(296, 277, null));
  points.add(new Point(243, 311, null));
  points.add(new Point(235, 249, null));
  points.add(new Point(232, 180, null));
  points.add(new Point(138, 211, null));
  points.add(new Point(154, 351, null));
  points.add(new Point(105, 181, null));


  defaultBeacons.add(new Point(826, 257, null));
defaultBeacons.add(new Point(789, 282, null));
defaultBeacons.add(new Point(806, 282, null));
 defaultBeacons.add(new Point(720, 320, null));
 defaultBeacons.add(new Point(674, 300, null));
 defaultBeacons.add(new Point(699, 315, null));
 defaultBeacons.add(new Point(544, 320, null));
 defaultBeacons.add(new Point(462, 313, null));
 defaultBeacons.add(new Point(494, 331, null));
 defaultBeacons.add(new Point(431, 329, null));
 defaultBeacons.add(new Point(334, 299, null));
 defaultBeacons.add(new Point(278, 261, null));
 defaultBeacons.add(new Point(204, 127, null));
 defaultBeacons.add(new Point(384, 156, null));
 defaultBeacons.add(new Point(447, 133, null));
 defaultBeacons.add(new Point(473, 134, null));
 defaultBeacons.add(new Point(538, 141, null));
 defaultBeacons.add(new Point(624, 149, null));
 defaultBeacons.add(new Point(669, 136, null));
 defaultBeacons.add(new Point(693, 154, null));
 defaultBeacons.add(new Point(740, 191, null));
  
  start = new Point(768, 223, null);
  end = new Point(785, 210, null);
  start.x = start.x + SHIFT_X;
  start.y = start.y + SHIFT_Y;
  end.x = end.x + SHIFT_X;
  end.y = end.y + SHIFT_Y;
  
  for(Point p: points) {
	  
	  p.x = p.x + SHIFT_X;
	  p.y = p.y + SHIFT_Y;
	  
  }
  
  
  for(Point p: defaultBeacons) {
	  
	  p.x = p.x + SHIFT_X;
	  p.y = p.y + SHIFT_Y;
	  
  }
  
  polygon = new DCEL();
  polygon.initialize(clonePoints(points));
  
}

void draw() {

	if(!started) {
		
		background(59, 185, 255);
		showText("Can you do better?", 270, 70, 255, 0, 0, 64);
		drawShapeFromPoints(points, 255, 255, 0, 255, 200, 0, strokePoly);
		drawPoint(start, 255, 0, 0, 5);
		drawPoint(end, 0, 255, 100, 5);
		drawPoints(defaultBeacons, 127, 70, 44, 5);
		
	}
		
	if(hintShown && !ready) {
		currentMillis = millis();
		float diff = currentMillis - startMillis;
		if(diff > 1000*count) {
			
			background(59, 185, 255);
			drawShapeFromPoints(points, 255, 255, 0, 255, 200, 0, strokePoly);
			showHint(null);
			drawPoint(start, 255, 0, 0, 5);
			drawPoint(end, 255, 255, 100, 5);
			drawPoints(beacons, 0, 0, 255, 5);
			showText( MAX_SECS-count, 50, 70, 255, 0, 0, 64);
			count++;
	
		}
		
		if(count > MAX_SECS) {
			
			hintShown = false;
			background(59, 185, 255);
			drawShapeFromPoints(points, 255, 255, 0, 255, 200, 0, strokePoly);
			drawPoint(start, 255, 0, 0, 5);
			drawPoint(end, 0, 255, 100, 5);
			drawPoints(beacons, 0, 0, 255, 5);
			
		}
					
	}
	else if (ready) {
		
		currentMillis = millis();
		float diff = currentMillis - startMillis;
		
		if(diff > 2000*count) { 
			AttractionRegion currentAttr = new AttractionRegion(target, points);
			console.log("new target:" + target.x + ", " + target.y);
			background(59, 185, 255);
			drawShapeFromPoints(points, 255, 255, 0, 255, 200, 0, strokePoly);
			drawShapeFromFace(currentAttr.face, 100, 255, 100, 0, 200, 0, 0, 0);
			drawPoint(previousTarget, 255, 0, 0, 5);
			drawPoint(target, 255, 255, 100, 5);
			
			if(!currentAttr.face.contains(previousTarget)) {
				
				playLose();
				showText("YOU LOSE!", 150, 246, 255, 0, 0, 200);
				showText("Not routed!", 390, 300, 255, 0, 0, 64);
				started = false;
				ready = false;
				restart();
				noLoop();
				return;
				
			}
			
			if(nextTarget < beacons.size()) {
				previousTarget = target;
				target = beacons.get(nextTarget);
				nextTarget++;
			}
			else {
				
				int beaconsUsed = beacons.size() - 1;

				if(beaconsUsed > defaultBeacons.size()) {
					playLose();
					showText("YOU LOSE!", 150, 246, 255, 0, 0, 200);
					int excess = beacons.size() - 1 - defaultBeacons.size();
					showText("Beacons in excess: " + excess , 270, 300, 255, 0, 0, 64);
					
				}
				else {
				
					playWin();
					if(beaconsUsed > (defaultBeacons.size() - MIN_BEACONS)/2 +MIN_BEACONS)
						showText("GOOD!", 315, 246, 0, 140, 0, 200);
					else if(beaconsUsed <= (defaultBeacons.size() - MIN_BEACONS)/2 +MIN_BEACONS && beaconsUsed > MIN_BEACONS)
						showText("VERY GOOD!", 110, 246, 0, 140, 0, 200);
					else
						showText("EXCELLENT!", 110, 246, 0, 140, 0, 200);
						
					enableNext();
					
				} 
				
				noLoop();
				started = false;
				ready = false;
				restart();
					
			}
			count++;
		}
		
	}
}

void mouseClicked() {
	
	if(!hintShown && !ready && started) {
		
				if(!hint) {
					Point b = new Point(mouseX, mouseY, null);
					if(polygon.faces.get(1).contains(b)) {
						if(beacons.size() == 0 && getHints() > 0)
							toggleHints();
						beacons.add(b);
						drawPoint(b, 0, 0, 255, 5);
					}
				}
				else {
					
					Point b = findNearest(mouseX, mouseY);
					
					if(b != null) {
						
						showHint(b);
						startMillis = millis();
						count = 0;
						
					}
					
				}
			}	
	
}

Point findNearest(float x, float y) {
	
	for(Point b: beacons) {
		
		if(Math.abs(b.x - x) < 15 && Math.abs(b.y - y) < 15)
			return b;
		
	}
	
	return null;
	
}

ArrayList<Point> clonePoints(ArrayList<Point> points) throws CloneNotSupportedException {

		ArrayList<Point> clone = new ArrayList<Point>(points.size());
		for(Point item: points) clone.add(new Point(item.x, item.y, null));
		return clone;

	}

void showText(String inputText, float x, float y, int r1, int g1, int b1, int size) {
	
	fill(r1, g1, b1);
	textSize(size);
	text(inputText, x, y);
	
}

void showHint(Point b) {
	
	hintShown = true;
	hint = false;
	
	if(b != null)
		attr = new AttractionRegion(b, points);

    drawShapeFromFace(attr.face, 0, 255, 0, 0, 200, 0, 0, 0);
}

int getNumberOfHints() {
	
	return NR_HINTS;
	
}

void toggleHint() {
	
	hint = true;
	showText("Click on a beacon!", 270, 70, 255, 0, 0, 64);
}

void toggleReady() {
	
	ready = true;
	count = 1;
	previousTarget = start;
	nextTarget = 1;
	beacons.add(end);
	target = beacons.get(0);
	startMillis = millis();
}

void toggleStart() {
	
	beacons = new ArrayList<Point>();
	started = true;
	background(59, 185, 255);
	drawShapeFromPoints(points, 255, 255, 0, 255, 200, 0, strokePoly);
	drawPoint(start, 255, 0, 0, 5);
	drawPoint(end, 0, 255, 100, 5);
	loop();
	
}

void drawPoints(ArrayList<Point> points, int r1, int g1, int b1, int size) {
	
	for(Point p: points)
		drawPoint(p, r1, g1, b1, size);
	
}

void drawPoint(Point p, int r1, int g1, int b1, int size) {
	
	strokeWeight(size);
	stroke(r1, g1, b1);
	point((float)p.x,(float)p.y);
	strokeWeight(1);

}

void drawShapeFromFace(Face f, int r1, int g1, int b1, int r2, int g2, int b2, int strokeSize) {

  Halfedge h = f.h;
  fill(color(r1, g1, b1));
  stroke(color(r2, g2, b2));
  strokeWeight(strokeSize);
  
  beginShape();

  float x, y;
  do {

    x = (float) h.target.x;
    y = (float) h.target.y;

    vertex(x, y);
    h = h.next;
  } while (h.target != f.h.target);

  endShape(CLOSE);
  strokeWeight(1);

}

void drawShapeFromPoints(ArrayList<Point> points, int r1, int g1, int b1, int r2, int g2, int b2, int strokeSize) {

  fill(color(r1, g1, b1));
  stroke(color(r2, g2, b2));
  strokeWeight(strokeSize);
  
  beginShape();

  for (Point p : points) {
    vertex((float) p.x, (float) p.y);
  }
  endShape(CLOSE);

 strokeWeight(1);
 
  
}

/*******************************************
*********** Attraction Region **************
*******************************************/

/**
* Attraction Region
* (Implemented following the guidelines in REF)
**/
class AttractionRegion {

	public static final double EPSILON = 0.01;
	ArrayList<Point> points;
	public DCEL dcel;
	public Face face;
	Point b;

	public AttractionRegion(Point b, ArrayList<Point> points) {

		this.points = points;
		this.dcel = new DCEL();
		this.dcel.initialize(this.points);
		this.b = b;
		this.computeAttractionRegion();

	}

	public void computeAttractionRegion() {

		ArrayList<Point> sortedVertices = this.sortVertices(b, this.dcel.vertices);

		this.computeRayVertices(b, sortedVertices, this.dcel);
		this.computeAttractionFace(b, this.dcel);

	}
	
	private void computeAttractionFace(Point b, DCEL dcel) {

		Face f;
		for(int i = 1; i < this.dcel.faces.size(); i++) {
			
			f = this.dcel.faces.get(i);
			if(f.contains(b))  {
				this.face = f;
				break;
			}
				
			
		}
		
	}

	/**
	 * Sort the vertices of the polygon about an angle around the query beacon point.
	 * @param b
	 * @param vertices
	 * @return
	 */
	protected ArrayList<Point> sortVertices(Point b, ArrayList<Point> vertices) {

		RadialSort sorted = new RadialSort(b, vertices);

		return sorted.result;

	}

	/**
	 * Compute the Ray vertices defined by the split vertices of the polygon with respect to the query beacon point.
	 * @param b
	 * @param vertices
	 * @param edges
	 */
	protected void computeRayVertices(Point b, ArrayList<Point> vertices, DCEL dcel) {

		RedBlackBST<Key, Edge> status = new RedBlackBST<Key, Edge>();

		this.initializeStatus(status, dcel, b, vertices);

		for(int i=1; i < vertices.size(); i++) {

			Point p = vertices.get(i);

			int type = this.isSplitVertex(b, p);

			this.updateStatus(status, b, p, type);

			if(type > 0)  {
				
				this.computeRayVertex(status, dcel, b, p, type);

			}

		}

	}

	/**
	 * Given a split vertex, computes the corresponding ray vertex.
	 * @param status
	 * @param b
	 * @param p
	 * @return
	 */
	private void computeRayVertex(RedBlackBST<Key, Edge> status, DCEL dcel, Point b, Point p, int type) {

		Point next = p.h.target;
		Point prev = p.h.prev.prev.target;

		

		Point p2;
		
		switch(type) {
		
		case 1:
			p2 = prev;
			break;
			
		case 2:
			p2 = prev;
			break;
			
		case 3:
			p2 = next;
			break;
			
			default:
				p2 = next;
		
		}
		
		Key k = new Key(b, new Edge(p, p2));
		
		int rank = status.rank(k);
		
		if(status.contains(k)) rank++;
		
		Key k2 = status.select(rank);

		Point rayVertex = this.computeLineLineIntersection(b.x, b.y, p.x, p.y, k2.e.a.x, k2.e.a.y, k2.e.b.x, k2.e.b.y);


		Edge old = k2.e;

		Turn pEab = new Turn (p, old.a, old.b);
		if(pEab.value > 0) p2 = old.b; 
		else p2 = old.a; 

		Key _new = this.createKey(b, rayVertex, p2, 1);

		
		

		status.delete(k2);
		
		status.put(_new, _new.e);

		

		
		dcel.splitEdge(p2.h.prev, rayVertex);
		

		
		
		

		dcel.splitFace(p.h.face, p.h.prev, dcel.vertices.get(dcel.vertices.size()-1));

	}

	private void updateStatus(RedBlackBST<Key, Edge> status, Point b, Point p, int type) {

		Point prev = p.h.prev.prev.target;
		Point next = p.h.target;

		int actionNext = decideAction(b, p, next);
		int actionPrev = decideAction(b, p, prev);


		if(actionNext == 2)
			this.deleteEdge(status, b, p, next);
		if(actionPrev == 2)
			this.deleteEdge(status, b, p, prev);

		if(actionNext == 1) 
			this.addEdge(status, b, p, next);
		if(actionPrev == 1) 
			this.addEdge(status, b, p, prev);


	}


	/**
	 * Initialize the status of the rotational sweep.
	 * @param b
	 * @param vertices
	 * @param edges
	 * @param status
	 * @param queue
	 */
	private void initializeStatus(RedBlackBST<Key, Edge> status, DCEL dcel, Point b, ArrayList<Point> vertices) {

		Point p1 = vertices.get(0);

		Halfedge h = p1.h;
		Edge e = h.getEdge();

		
		

		do {

			
			
			

			if(e.intersectsRay(b, p1)) { // add it to the status

				Turn bEab = new Turn(b, e.a, e.b);
				Turn bEba = new Turn(b, e.b, e.a);

				Turn bpEa = new Turn (b, p1, e.a);
				Turn bpEb = new Turn (b, p1, e.b);

				if((bpEa.value == 0 && bEab.value > 0) ||
						(bpEb.value == 0 && bEba.value > 0) ||
						(bpEa.value != 0 && bpEb.value != 0)) {

					Key k = new Key(b, new Edge(e.a, e.b));
					status.put(k, e);
					

				}


			}

			h = h.next;
			e = h.getEdge();

		} while(!h.twin.target.equals(p1));

		int type = this.isSplitVertex(b, p1);

		if(type > 0)  {
			
			this.computeRayVertex(status, dcel, b, p1, type);

		}


	}

	private int decideAction(Point b, Point p, Point q) {

		Turn bpq = new Turn(b, p, q);

		
		

		if(bpq.value > 0) {
			
			return 1;
		}

		else if(bpq.value < 0) {
			
			return 2;
		}


		return 0;

	}

	private void addEdge(RedBlackBST<Key, Edge> status, Point b, Point p, Point q) {

		Key k = createKey(b, p, q, 1);
		
		
		
		status.put(k, new Edge(p, q));
		

	}

	private void deleteEdge(RedBlackBST<Key, Edge> status, Point b, Point p, Point q) {

		Key k = createKey(b, p, q, 2);
		
		
		status.delete(k);
		

	}

	private Key createKey(Point b, Point p, Point q, int first) {

		if(first == 2) {

			Point temp = p;
			p = q;
			q = temp;

		}


		return new Key(b, new Edge(p, q));

	}

	private int isSplitVertex(Point b, Point p) {

		Point prev = p.h.prev.prev.target;
		Point next = p.h.target;


		Turn prevPNext = new Turn(prev, p, next);

		if(prevPNext.value >= 0)
			return -1;

		CrossProduct bp_pNext = new CrossProduct(b, p, p, next);
		CrossProduct bp_prevP = new CrossProduct(b, p, prev, p);

		DotProduct bp_pNext_d = new DotProduct(b, p, p, next);
		DotProduct bp_prevP_d = new DotProduct(b, p, prev, p);

		if(bp_pNext.value < 0 && bp_prevP.value < 0) {

			if(bp_pNext_d.value < 0 && bp_prevP_d.value > 0)
				return 1;

		}
		else if(bp_pNext.value > 0 && bp_prevP.value < 0) {

			if(bp_prevP_d.value > 0)
				return 2;

		}
		else if(bp_pNext.value < 0 && bp_prevP.value > 0) {

			if(bp_pNext_d.value < 0)
				return 3;

		}

		return -1;

	}

	private Point computeLineLineIntersection(double x1,  double y1,  double x2,  double y2,  double x3,  double y3,  double x4,  double y4) {

		double den, xNum, yNum;
		double x, y;

		den = (x1 -x2)*(y3 - y4) - (y1 - y2)*(x3 - x4);

		if((Math.abs(den) <= this.EPSILON)) return null;
		
		if(Math.abs(x1-x2)<= this.EPSILON) {
			
			y = (y4 - y3)/(x4 - x3) * (x1 - x3) + y3;
			
			return new Point(x1, y, null);
			
		}
		
		if(Math.abs(x3-x4)<= this.EPSILON) {
			
			y = (y2 - y1)/(x2 - x1) * (x3 - x1) + y1;
			
			return new Point(x3, y, null);
			
		}

		xNum = (x1*y2-y1*x2)*(x3 - x4) - (x1-x2)*(x3*y4-y3*x4);
		yNum = (x1*y2-y1*x2)*(y3 - y4) - (y1-y2)*(x3*y4-y3*x4);

		x = xNum / den;
		y = yNum / den;

		return new Point(x, y, null);

	}

	private Point computePointOnLine(Point a, Point b, double t) {

		double x = a.x +t*(b.x-a.x);
		double y = a.y +t*(b.y-a.y);

		return new Point(x, y, null);

	}

}


/**
* Key (for the binary search tree)
* (Adapted from: 
*      http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
* )
**/
class Key implements Comparable<Key>{

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


/*******************************************
*************** Operations *****************
*******************************************/

/**
* Shortest Path Tree
* (Thanks to Professor Stefan Langerman for outlining the alorithm!)
**/
class SPT {
	
	public DCEL polygon;
	public ArrayList<Edge> edges;
	public ArrayList<Point> points;
	public SPT(Point p, ArrayList<Point> points) {
		
		this.points = points;
		graph = new Graph();
		polygon = new DCEL();
		edges = new ArrayList<Edge>();
		try {
			polygon.initialize(this.clonePoints(points));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		this.computeSPT(p, polygon);
		
	}
	
	private void computeSPT(Point p, DCEL polygon) {
		
		
		var map = {};
		double dist;
		for(int i = 0; i < polygon.vertices.size(); i++) {
			
			Point v1 = polygon.vertices.get(i);
			
			for(int j = 0; j < polygon.vertices.size(); j++) {
				
				Point v2 = polygon.vertices.get(j);
				if(v2.equals(v1))
					continue;
				
				if(v1.h.target.equals(v2) || v1.h.prev.twin.target.equals(v2)) {
					
					dist = computeDistance(v1, v2);
					if(map[i.toString()] == null)
						map[i.toString()] = {};
					map[i.toString()][j.toString()] = dist;
					continue;
					
				}
				if(isExterior(new Edge(v1, v2)))
					continue;
				
				else {
					
					boolean intersects = this.intersectsPolygon(v1, v2);
					
					if(!intersects) {
						dist = computeDistance(v1, v2);
						if(map[i.toString()] == null)
							map[i.toString()] = {};
						map[i.toString()][j.toString()] = dist;

						}
						
				}
				
			}
			
		}
		
		for(int i = 0; i < polygon.vertices.size(); i++) {
			
			Point v = polygon.vertices.get(i);
			
			boolean intersects = intersectsPolygon(p, v);
				
			if(!intersects) {
				dist = computeDistance(p, v);
				if(map[polygon.vertices.size().toString()] == null)
						map[polygon.vertices.size().toString()] = {};
					
				map[polygon.vertices.size().toString()][i.toString()] = dist;
			}
				
		}


		var graph = new Graph(map);
		
		for(int i = 0; i < polygon.vertices.size(); i++) {
			
			var path = graph.findShortestPath(polygon.vertices.size().toString, i.toString);
			
			Point a = p;
			Point b;
			for(int j=1; j<path.length; j++) {
				var s = path[j];
				b = this.points.get(parseInt(s));
				this.edges.add(new Edge(a, b));
				
				a = b;
			}
			
		}

	}
	
	private double computeDistance(Point a, Point b) {
		
		return Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y, 2));
	}

	private boolean intersectsPolygon(Point v1, Point v2) {
		
		Edge v1v2 = new Edge(v1, v2);
		
		boolean intersects = false;
		
		Face f = polygon.faces.get(1);
		Halfedge h = f.h;
		
		do {
			
			if(h.target.equals(v2) || h.twin.target.equals(v2)|| h.target.equals(v1) || h.twin.target.equals(v1)) {
				
				h = h.next;
				continue;
				
			}
				
			
			if(v1v2.strictlyIntersectsEdge(h.getEdge())) {
				
				intersects = true;
				break;
				
			}
			else if(v1v2.intersectsEdge(h.getEdge())) {
				
				Point intersection;
				Turn t = new Turn(v1, v2, h.target);
				if(t.value == 0)
					intersection = h.target;
				else
					intersection = h.twin.target;
				
				Point next = intersection.h.target;
				Point prev = intersection.h.prev.twin.target;
				
				if(v1v2.intersectsEdge(new Edge(prev, next))) {
					intersects = true;
					break;
				}
				
			}
			
			h = h.next;
			
		} while(!h.target.equals(f.h.target));
		
		return intersects;
		
	}
	
	private boolean isExterior(Edge e) {
		
		Point prevA = e.a.h.prev.twin.target;
		Point prevB = e.b.h.prev.twin.target;
		Point nextA = e.a.h.target;
		Point nextB = e.b.h.target;
		
		CrossProduct a = new CrossProduct(e.a, prevA, e.a, nextA);
		CrossProduct b = new CrossProduct(e.b, prevB, e.b, nextB);
		
		Edge pAnA = new Edge(prevA, nextA);
		Edge pBnB = new Edge(prevB, nextB);
		
		if(a.value > 0 && !e.intersectsEdge(pAnA))
			return false;
		if(b.value > 0 && !e.intersectsEdge(pBnB))
			return false;
		if(a.value < 0 && e.intersectsEdge(pAnA))
			return false;
		if(b.value < 0 && e.intersectsEdge(pBnB))
			return false;
		
		Turn pAAnA = new Turn(prevA, e.a, nextA);
		Turn pBBnB = new Turn(prevB, e.b, nextB);
		Turn pAAB = new Turn(prevA, e.a, e.b);
		Turn pBBA = new Turn(prevB, e.b, e.a);
		
		if(pAAnA.value == 0 && pAAB.value > 0)
			return false;
		
		if(pBBnB.value == 0 && pBBA.value > 0)
			return false;
		
		return true;
		
	}
	private ArrayList<Point> clonePoints(ArrayList<Point> points) throws CloneNotSupportedException {

		ArrayList<Point> clone = new ArrayList<Point>(points.size());
		for(Point item: points) clone.add(new Point(item.x, item.y, null));
		return clone;

	}
}



/**
* Radial Sort
* (cyclicOrder method is adapted from URL)
**/
class RadialSort {

	public ArrayList<Point> result;

	public RadialSort(Point q, ArrayList<Point> points) {

		this.result = (ArrayList<Point>) cyclicOrder(q, points);

	}


	public RadialSort(double qx, double qy, ArrayList<Point> points) {

		this(new Point(qx, qy, null), points);

	}

	private List<Point> cyclicOrder(Point q, List<Point> arr) {

		if (!arr.isEmpty()) {
			Point pivot = arr.get(0); //This pivot can change to get faster results

			

			List<Point> less = new ArrayList<Point>();
			List<Point> pivotList = new ArrayList<Point>();
			List<Point> more = new ArrayList<Point>();

			// Partition
			for (Point i: arr) {
				int comparison = compare(q, i, pivot);
				if (comparison > 0)
					less.add(i);
				else if (comparison < 0)
					more.add(i);
				else
					pivotList.add(i);
			}

			// Recursively sort sublists
			less = cyclicOrder(q, less);
			more = cyclicOrder(q, more);

			// Concatenate results
			less.addAll(pivotList);
			less.addAll(more);
			return less;
		}
		return arr;
	}

	public static int compare(Point q, Point pivot, Point curr) {

		int result = 0;

		Turn t = new Turn(q, pivot, curr);
		
		if(t.value > 0) result = 1;
		else if (t.value < 0) result = -1;
		else result = comesBefore(q, pivot, curr);

		return result;

	}

	private static int comesBefore(Point q, Point a, Point b) {

		double xDiff = b.x - a.x - q.x;
		double yDiff = b.y - a.y -q.y;

		if(a.x == b.x && a.y == b.y) return 0;
		else if((xDiff > 0) || (xDiff == 0 && yDiff > 0)) return 1;
		else  return -1;

	}

}


/**
* Cross Product
**/
class CrossProduct {
	
	public double value;
	private final double EPSILON = 0.1;

	public CrossProduct(Point a1, Point a2, Point b1, Point b2) {

		value = (a2.x - a1.x)*(b2.y - b1.y) - (b2.x - b1.x)*(a2.y - a1.y);
		
		if(value < EPSILON && value > -EPSILON)
			value = 0;
	}

	public CrossProduct(Edge a, Edge b) {

		this(a.a, a.b, b.a, b.b);

	}


}


/**
* Dot Product
**/
class DotProduct {

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


/**
* Turn
**/
class Turn {
	
	public double value;
	private final double EPSILON = 0.1;
	
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


/*******************************************
*********** Data Structures ****************
*******************************************/

/**
* Line Arrangement
**/
class LineArrangement {

	public static final double EPSILON = 0.01;
	public double SCREEN_X, SCREEN_Y;
	public ArrayList<Face> faces;

	public LineArrangement(ArrayList<Edge> edges) {

		SCREEN_X = 1068;
		SCREEN_Y = 600;

		edges = this.removeDuplicateLines(edges);
		this.computeLineArrangement(edges);

	}

	private void computeLineArrangement(ArrayList<Edge> edges) {

		DCEL arrangement = new DCEL();

		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(0, 0, null));
		points.add(new Point(this.SCREEN_X, 0, null));
		points.add(new Point(this.SCREEN_X, this.SCREEN_Y, null));
		points.add(new Point(0, this.SCREEN_Y, null));

		arrangement.initialize(points);

		int size;
		ArrayList<Halfedge> splitHalfedges;
		ArrayList<Point> edgeIntersections, vertexIntersections;
		Face f;
		Halfedge h;
		Edge e;
		Point v;

		for(Edge l: edges) {
			
			size = arrangement.faces.size();

			for(int i = 1; i < size; i++) {

				splitHalfedges = new ArrayList<Halfedge>();
				edgeIntersections = new ArrayList<Point>();
				vertexIntersections = new ArrayList<Point>();

				f = arrangement.faces.get(i);
				h = f.h;

				do {

					e = h.getEdge();

					if(e.strictlyIntersectsLine(l)) {

						v = this.computeLineLineIntersection(l.a.x, l.a.y, l.b.x, l.b.y, e.a.x, e.a.y, e.b.x, e.b.y);

						Point dup = this.findDuplicate(v, arrangement.vertices);

						if(dup != null)
							v = dup;

						splitHalfedges.add(h);
						edgeIntersections.add(v);
					}
					else if(e.intersectsLine(l)) {

						Turn lAlBeA = new Turn(l.a, l.b, e.a);
						Turn lAlBeB = new Turn(l.a, l.b, e.b);

						if(lAlBeA.value == 0)
							v = e.a;
						else
							v = e.b;

						Point dup = this.findDuplicate(v, arrangement.vertices);

						if(dup != null)
							v = dup;

						Halfedge h1 = this.findHalfedgeInFace(v, f);
						
						if (h1 == null)
							continue;
						
						Point prev = h1.prev.twin.target;
						Point next = h1.target;

						Point v1 = !l.a.equals(v) ? l.a : l.b;

						Turn v1vp = new Turn(v1, v, prev);
						Turn v1vn = new Turn(v1, v, next);

						if((v1vp.value > 0 && v1vn.value < 0) || (v1vp.value < 0 && v1vn.value > 0))
							vertexIntersections.add(v);

					}

					h = h.next;

				} while(!f.h.target.equals(h.target));

				vertexIntersections = this.removeDuplicatePoints(vertexIntersections);

				if(edgeIntersections.size() > 0 || vertexIntersections.size() > 0) {

					if(edgeIntersections.size() == 2 && vertexIntersections.size() == 0) {

						f=arrangement.splitEdge(splitHalfedges.get(0), edgeIntersections.get(0));
						arrangement.splitEdge(splitHalfedges.get(1), edgeIntersections.get(1));

						h = this.findHalfedgeInFace(edgeIntersections.get(0), f);

						if (h == null)
							continue;
						h = h.prev;

						arrangement.splitFace2(i, h, edgeIntersections.get(1));

					}
					else if(edgeIntersections.size() == 1 && vertexIntersections.size() == 1) {

						f= arrangement.splitEdge(splitHalfedges.get(0), edgeIntersections.get(0));
						h = this.findHalfedgeInFace(edgeIntersections.get(0), f);
						if (h == null)
							continue;
						h = h.prev;

						arrangement.splitFace2(i, h, vertexIntersections.get(0));


					}
					else if(edgeIntersections.size() == 0 && vertexIntersections.size() == 2) {

						h = this.findHalfedgeInFace(vertexIntersections.get(0), f);
						if (h == null)
							continue;
						h = h.prev;

						arrangement.splitFace2(i, h, vertexIntersections.get(1));


					}					
				}

			}

		}

		this.faces = arrangement.faces;

	}

	private ArrayList<Edge> removeDuplicateLines(ArrayList<Edge> edges) {

		ArrayList<Edge> uniqueEdges = new ArrayList<Edge>(); 
		boolean duplicate;
		Edge current, test;
		Turn a1b1b2, a1b1a2;

		for(int i=0; i < edges.size(); i++) {

			duplicate = false;
			current = edges.get(i);

			for(int j = i+1; j< edges.size(); j++) {

				test = edges.get(j);
				a1b1b2 = new Turn(current.a, current.b, test.b);
				a1b1a2 = new Turn(current.a, current.b, test.a);

				if(a1b1b2.value == 0 && a1b1a2.value == 0) {

					duplicate = true;
					break;

				}

			}

			if(!duplicate)
				uniqueEdges.add(current);

		}

		return uniqueEdges;

	}

	private ArrayList<Point> removeDuplicatePoints(ArrayList<Point> points) {

		ArrayList<Point> uniquePoints = new ArrayList<Point>(); 
		boolean duplicate;
		Point current, test;

		for(int i=0; i < points.size(); i++) {

			duplicate = false;
			current = points.get(i);

			for(int j = i+1; j< points.size(); j++) {

				test = points.get(j);

				if(current.equals(test)) {

					duplicate = true;
					break;

				}

			}

			if(!duplicate)
				uniquePoints.add(current);

		}

		return uniquePoints;


	}

	private Point computeLineLineIntersection(double x1,  double y1,  double x2,  double y2,  double x3,  double y3,  double x4,  double y4) {

		double den, xNum, yNum;
		double x, y;

		den = (x1 -x2)*(y3 - y4) - (y1 - y2)*(x3 - x4);

		if(Math.abs(den) <= this.EPSILON) return null;

		if(Math.abs(x1-x2)<= this.EPSILON) {

			y = (y4 - y3)/(x4 - x3) * (x1 - x3) + y3;

			return new Point(x1, y, null);

		}

		if(Math.abs(x3-x4)<= this.EPSILON) {

			y = (y2 - y1)/(x2 - x1) * (x3 - x1) + y1;

			return new Point(x3, y, null);

		}

		xNum = (x1*y2-y1*x2)*(x3 - x4) - (x1-x2)*(x3*y4-y3*x4);
		yNum = (x1*y2-y1*x2)*(y3 - y4) - (y1-y2)*(x3*y4-y3*x4);

		x = xNum / den;
		y = yNum / den;

		return new Point(x, y, null);

	}

	private Halfedge findHalfedgeInFace(Point p, Face f) {

		Halfedge h = p.h;

		do {

			Halfedge h1 = h;

			do {

				if(h1.equals(f.h))
					return h;

				h1 = h1.next;

			} while(h1.target != h.target);

			h = h.prev.twin;

		} while (!p.h.target.equals(h.target));

		return null;

	}

	private Point findDuplicate(Point p, ArrayList<Point> points) {

		for(Point t: points)
			if(p.equals(t))
				return t;

		return null;
	}
}


/**
* Red Black Binary Search Tree
* (Adapted from: 
*      http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
* )
**/

/**
 *  The <tt>BST</tt> class represents an ordered symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>_size</em>, and <em>is-empty</em> methods.
 *  It also provides ordered methods for finding the <em>minimum</em>,
 *  <em>maximum</em>, <em>floor</em>, and <em>ceiling</em>.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be <tt>null</tt>&mdash;setting the
 *  value associated with a key to <tt>null</tt> is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a left-leaning red-black BST. It requires that
 *  the key type implements the <tt>Comparable</tt> interface and calls the
 *  <tt>compareTo()</tt> and method to compare two keys. It does not call either
 *  <tt>equals()</tt> or <tt>hashCode()</tt>.
 *  The <em>put</em>, <em>contains</em>, <em>remove</em>, <em>minimum</em>,
 *  <em>maximum</em>, <em>ceiling</em>, and <em>floor</em> operations each take
 *  logarithmic time in the worst case, if the tree becomes unbalanced.
 *  The <em>_size</em>, and <em>is-empty</em> operations take constant time.
 *  Construction takes constant time.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/33balanced">Section 3.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For other implementations, see {@link ST}, {@link BinarySearchST},
 *  {@link SequentialSearchST}, {@link BST},
 *  {@link SeparateChainingHashST}, and {@link LinearProbingHashST},
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

class RedBlackBST<Key extends Comparable<Key>, Value> {

	private static final boolean RED   = true;
	private static final boolean BLACK = false;
	Node lastInserted;

	private Node root;     // root of the BST

	// BST helper node data type
	final class Node {
		private Key _key;           // key
		Value val;         // associated data
		Node left, right;  // links to left and right subtrees
		private boolean colored;     // color of parent link
		private int N;             // subtree count

		public Node(Key _key, Value val, boolean colored, int N) {
			this._key = _key;
			this.val = val;
			this.colored = colored;
			this.N = N;
		}
	}

	/**
	 * Initializes an empty symbol table.
	 */
	public RedBlackBST() {

		this.lastInserted = null;
	}

	/***************************************************************************
	 *  Node helper methods.
	 ***************************************************************************/
	// is node x red; false if x is null ?
	private boolean isRed(Node x) {
		if (x == null) return false;
		return x.colored == RED;
	}

	// number of node in subtree rooted at x; 0 if x is null
	private int _size(Node x) {
		if (x == null) return 0;
		return x.N;
	} 


	/**
	 * Returns the number of key-value pairs in this symbol table.
	 * @return the number of key-value pairs in this symbol table
	 */
	public int _size() {
		return _size(root);
	}

	/**
	 * Is this symbol table empty?
	 * @return <tt>true</tt> if this symbol table is empty and <tt>false</tt> otherwise
	 */
	public boolean isEmpty() {
		return root == null;
	}


	/***************************************************************************
	 *  Standard BST search.
	 ***************************************************************************/

	/**
	 * Returns the value associated with the given key.
	 * @param _key the key
	 * @return the value associated with the given key if the key is in the symbol table
	 *     and <tt>null</tt> if the key is not in the symbol table
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public Value getValue(Key _key) {
		if (_key == null) throw new NullPointerException("argument to getValue() is null");
		return getValue(root, _key);
	}

	// value associated with the given key in subtree rooted at x; null if no such key
	private Value getValue(Node x, Key _key) {
		while (x != null) {
			int cmp = _key.compareTo(x._key);
			
			if      (cmp < 0) x = x.left;
			else if (cmp > 0) x = x.right;
			else              return x.val;
		}
		return null;
	}

	/**
	 * Does this symbol table contain the given key?
	 * @param key the key
	 * @return <tt>true</tt> if this symbol table contains <tt>_key</tt> and
	 *     <tt>false</tt> otherwise
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public boolean contains(Key _key) {
		return getValue(_key) != null;
	}

	/***************************************************************************
	 *  Red-black tree insertion.
	 ***************************************************************************/

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 * Deletes the specified key (and its associated value) from this symbol table
	 * if the specified value is <tt>null</tt>.
	 *
	 * @param _key the key
	 * @param val the value
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public void put(Key _key, Value val) {
		if (_key == null) throw new NullPointerException("first argument to put() is null");
		if (val == null) {
			delete(_key);
			return;
		}

		root = put(root, _key, val);
		root.colored = BLACK;
		// assert check();
	}

	// insert the key-value pair in the subtree rooted at h
	private Node put(Node h, Key _key, Value val) { 
		if (h == null) {

			this.lastInserted = new Node(_key, val, RED, 1);
			return lastInserted;

		}

		int cmp = _key.compareTo(h._key);
		if      (cmp < 0) h.left  = put(h.left,  _key, val); 
		else if (cmp > 0) h.right = put(h.right, _key, val); 
		else              h.val   = val;

		// fix-up any right-leaning links
		if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
		if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
		h.N = _size(h.left) + _size(h.right) + 1;

		return h;
	}

	/***************************************************************************
	 *  Red-black tree deletion.
	 ***************************************************************************/

	/**
	 * Removes the smallest key and associated value from the symbol table.
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public void deleteMin() {
		if (isEmpty()) throw new NoSuchElementException("BST underflow");

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.colored = RED;

		root = deleteMin(root);
		if (!isEmpty()) root.colored = BLACK;
		// assert check();
	}

	// delete the key-value pair with the minimum key rooted at h
	private Node deleteMin(Node h) { 
		if (h.left == null)
			return null;

		if (!isRed(h.left) && !isRed(h.left.left))
			h = moveRedLeft(h);

		h.left = deleteMin(h.left);
		return balance(h);
	}


	/**
	 * Removes the largest key and associated value from the symbol table.
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public void deleteMax() {
		if (isEmpty()) throw new NoSuchElementException("BST underflow");

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.colored = RED;

		root = deleteMax(root);
		if (!isEmpty()) root.colored = BLACK;
		// assert check();
	}

	// delete the key-value pair with the maximum key rooted at h
	private Node deleteMax(Node h) { 
		if (isRed(h.left))
			h = rotateRight(h);

		if (h.right == null)
			return null;

		if (!isRed(h.right) && !isRed(h.right.left))
			h = moveRedRight(h);

		h.right = deleteMax(h.right);

		return balance(h);
	}

	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).    
	 *
	 * @param  _key the key
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public void delete(Key _key) { 
		if (_key == null) throw new NullPointerException("argument to delete() is null");
		if (!contains(_key)) return;
		
		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.colored = RED;

		root = delete(root, _key);
		if (!isEmpty()) root.colored = BLACK;
		// assert check();
	}

	// delete the key-value pair with the given key rooted at h
	private Node delete(Node h, Key _key) { 
		// assert getValue(h, key) != null;

		if (_key.compareTo(h._key) < 0)  {
			if (!isRed(h.left) && !isRed(h.left.left))
				h = moveRedLeft(h);
			h.left = delete(h.left, _key);
		}
		else {
			if (isRed(h.left))
				h = rotateRight(h);
			if (_key.compareTo(h._key) == 0 && (h.right == null))
				return null;
			if (!isRed(h.right) && !isRed(h.right.left))
				h = moveRedRight(h);
			if (_key.compareTo(h._key) == 0) {
				Node x = _min(h.right);
				h._key = x._key;
				h.val = x.val;
				// h.val = getValue(h.right, _min(h.right)._key);
				// h._key = _min(h.right)._key;
				h.right = deleteMin(h.right);
			}
			else h.right = delete(h.right, _key);
		}
		return balance(h);
	}

	/***************************************************************************
	 *  Red-black tree helper functions.
	 ***************************************************************************/

	// make a left-leaning link lean to the right
	private Node rotateRight(Node h) {
		// assert (h != null) && isRed(h.left);
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.colored = x.right.colored;
		x.right.colored = RED;
		x.N = h.N;
		h.N = _size(h.left) + _size(h.right) + 1;
		return x;
	}

	// make a right-leaning link lean to the left
	private Node rotateLeft(Node h) {
		// assert (h != null) && isRed(h.right);
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.colored = x.left.colored;
		x.left.colored = RED;
		x.N = h.N;
		h.N = _size(h.left) + _size(h.right) + 1;
		return x;
	}

	// flip the colors of a node and its two children
	private void flipColors(Node h) {
		// h must have opposite color of its two children
		// assert (h != null) && (h.left != null) && (h.right != null);
		// assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
		//    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
		h.colored = !h.colored;
		h.left.colored = !h.left.colored;
		h.right.colored = !h.right.colored;
	}

	// Assuming that h is red and both h.left and h.left.left
	// are black, make h.left or one of its children red.
	private Node moveRedLeft(Node h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

		flipColors(h);
		if (isRed(h.right.left)) { 
			h.right = rotateRight(h.right);
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}

	// Assuming that h is red and both h.right and h.right.left
	// are black, make h.right or one of its children red.
	private Node moveRedRight(Node h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
		flipColors(h);
		if (isRed(h.left.left)) { 
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}

	// restore red-black tree invariant
	private Node balance(Node h) {
		// assert (h != null);

		if (isRed(h.right))                      h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right))     flipColors(h);

		h.N = _size(h.left) + _size(h.right) + 1;
		return h;
	}


	/***************************************************************************
	 *  Utility functions.
	 ***************************************************************************/

	/**
	 * Returns the height of the BST (for debugging).
	 * @return the height of the BST (a 1-node tree has height 0)
	 */
	public int height() {
		return height(root);
	}
	private int height(Node x) {
		if (x == null) return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}

	/***************************************************************************
	 *  Ordered symbol table methods.
	 ***************************************************************************/

	/**
	 * Returns the smallest key in the symbol table.
	 * @return the smallest key in the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public Key _min() {
		if (isEmpty()) throw new NoSuchElementException("called _min() with empty symbol table");
		return _min(root)._key;
	} 

	// the smallest key in subtree rooted at x; null if no such key
	public Node _min(Node x) { 
		// assert x != null;
		if (x.left == null) return x; 
		else                return _min(x.left); 
	} 

	/**
	 * Returns the largest key in the symbol table.
	 * @return the largest key in the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public Key _max() {
		if (isEmpty()) throw new NoSuchElementException("called _max() with empty symbol table");
		return _max(root)._key;
	} 

	// the largest key in the subtree rooted at x; null if no such key
	private Node _max(Node x) { 
		// assert x != null;
		if (x.right == null) return x; 
		else                 return _max(x.right); 
	} 


	/**
	 * Returns the largest key in the symbol table less than or equal to <tt>_key</tt>.
	 * @param _key the key
	 * @return the largest key in the symbol table less than or equal to <tt>_key</tt>
	 * @throws NoSuchElementException if there is no such key
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public Key _floor(Key _key) {
		if (_key == null) throw new NullPointerException("argument to _floor() is null");
		if (isEmpty()) throw new NoSuchElementException("called _floor() with empty symbol table");
		Node x = _floor(root, _key);
		if (x == null) return null;
		else           return x._key;
	}    

	// the largest key in the subtree rooted at x less than or equal to the given key
	private Node _floor(Node x, Key _key) {
		if (x == null) return null;
		int cmp = _key.compareTo(x._key);
		if (cmp == 0) return x;
		if (cmp < 0)  return _floor(x.left, _key);
		Node t = _floor(x.right, _key);
		if (t != null) return t; 
		else           return x;
	}

	/**
	 * Returns the smallest key in the symbol table greater than or equal to <tt>_key</tt>.
	 * @param _key the key
	 * @return the smallest key in the symbol table greater than or equal to <tt>_key</tt>
	 * @throws NoSuchElementException if there is no such key
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public Key ceiling(Key _key) {
		if (_key == null) throw new NullPointerException("argument to ceiling() is null");
		if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty symbol table");
		Node x = ceiling(root, _key);
		if (x == null) return null;
		else           return x._key;  
	}

	// the smallest key in the subtree rooted at x greater than or equal to the given key
	private Node ceiling(Node x, Key _key) {  
		if (x == null) return null;
		int cmp = _key.compareTo(x._key);
		if (cmp == 0) return x;
		if (cmp > 0)  return ceiling(x.right, _key);
		Node t = ceiling(x.left, _key);
		if (t != null) return t; 
		else           return x;
	}

	/**
	 * Return the kth smallest key in the symbol table.
	 * @param k the order statistic
	 * @return the kth smallest key in the symbol table
	 * @throws IllegalArgumentException unless <tt>k</tt> is between 0 and
	 *     <em>N</em> &minus; 1
	 */
	public Key select(int k) {
		if (k < 0 || k >= _size()) throw new IllegalArgumentException();
		Node x = select(root, k);
		return x._key;
	}

	// the key of rank k in the subtree rooted at x
	private Node select(Node x, int k) {
		// assert x != null;
		// assert k >= 0 && k < _size(x);
		int t = _size(x.left); 
		if      (t > k) return select(x.left,  k); 
		else if (t < k) return select(x.right, k-t-1); 
		else            return x; 
	} 

	/**
	 * Return the number of keys in the symbol table strictly less than <tt>_key</tt>.
	 * @param _key the key
	 * @return the number of keys in the symbol table strictly less than <tt>_key</tt>
	 * @throws NullPointerException if <tt>_key</tt> is <tt>null</tt>
	 */
	public int rank(Key _key) {
		if (_key == null) throw new NullPointerException("argument to rank() is null");
		return rank(_key, root);
	} 

	// number of keys less than key in the subtree rooted at x
	private int rank(Key _key, Node x) {
		if (x == null) return 0; 
		int cmp = _key.compareTo(x._key); 
		if      (cmp < 0) return rank(_key, x.left); 
		else if (cmp > 0) return 1 + _size(x.left) + rank(_key, x.right); 
		else              return _size(x.left); 
	} 

	/***************************************************************************
	 *  Range count and range search.
	 ***************************************************************************/

	/**
	 * Returns all keys in the symbol table as an <tt>Iterable</tt>.
	 * To iterate over all of the keys in the symbol table named <tt>st</tt>,
	 * use the foreach notation: <tt>for (Key _key : st._keys())</tt>.
	 * @return all keys in the sybol table as an <tt>Iterable</tt>
	 */
	public Iterable<Key> _keys() {
		if (isEmpty()) return new LinkedList<Key>();
		return _keys(_min(), _max());
	}

	/**
	 * Returns all keys in the symbol table in the given range,
	 * as an <tt>Iterable</tt>.
	 * @return all keys in the sybol table between <tt>lo</tt> 
	 *    (inclusive) and <tt>hi</tt> (exclusive) as an <tt>Iterable</tt>
	 * @throws NullPointerException if either <tt>lo</tt> or <tt>hi</tt>
	 *    is <tt>null</tt>
	 */
	public Iterable<Key> _keys(Key lo, Key hi) {
		if (lo == null) throw new NullPointerException("first argument to keys() is null");
		if (hi == null) throw new NullPointerException("second argument to keys() is null");

		LinkedList<Key> queue = new LinkedList<Key>();
		// if (isEmpty() || lo.compareTo(hi) > 0) return queue;
		_keys(root, queue, lo, hi);
		return queue;
	} 

	// add the keys between lo and hi in the subtree rooted at x
	// to the queue
	private void _keys(Node x, LinkedList<Key> queue, Key lo, Key hi) { 
		if (x == null) return; 
		int cmplo = lo.compareTo(x._key); 
		int cmphi = hi.compareTo(x._key); 
		if (cmplo < 0) _keys(x.left, queue, lo, hi); 
		if (cmplo <= 0 && cmphi >= 0) queue.add(x._key); 
		if (cmphi > 0) _keys(x.right, queue, lo, hi); 
	} 

	/**
	 * Returns the number of keys in the symbol table in the given range.
	 * @return the number of keys in the sybol table between <tt>lo</tt> 
	 *    (inclusive) and <tt>hi</tt> (exclusive)
	 * @throws NullPointerException if either <tt>lo</tt> or <tt>hi</tt>
	 *    is <tt>null</tt>
	 */
	public int _size(Key lo, Key hi) {
		if (lo == null) throw new NullPointerException("first argument to _size() is null");
		if (hi == null) throw new NullPointerException("second argument to _size() is null");

		if (lo.compareTo(hi) > 0) return 0;
		if (contains(hi)) return rank(hi) - rank(lo) + 1;
		else              return rank(hi) - rank(lo);
	}


	/***************************************************************************
	 *  Check integrity of red-black tree data structure.
	 ***************************************************************************/
	private boolean check() {
		if (!isBST())            console.log("Not in symmetric order");
		if (!isSizeConsistent()) console.log("Subtree counts not consistent");
		if (!isRankConsistent()) console.log("Ranks not consistent");
		if (!is23())             console.log("Not a 2-3 tree");
		if (!isBalanced())       console.log("Not balanced");
		return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
	}

	// does this binary tree satisfy symmetric order?
	// Note: this test also ensures that data structure is a binary tree since order is strict
	private boolean isBST() {
		return isBST(root, null, null);
	}

	// is the tree rooted at x a BST with all keys strictly between min and max
	// (if min or max is null, treat as empty constraint)
	// Credit: Bob Dondero's elegant solution
	private boolean isBST(Node x, Key min, Key max) {
		if (x == null) return true;
		if (min != null && x._key.compareTo(min) <= 0) return false;
		if (max != null && x._key.compareTo(max) >= 0) return false;
		return isBST(x.left, min, x._key) && isBST(x.right, x._key, max);
	} 

	// are the _size fields correct?
	private boolean isSizeConsistent() { return isSizeConsistent(root); }
	private boolean isSizeConsistent(Node x) {
		if (x == null) return true;
		if (x.N != _size(x.left) + _size(x.right) + 1) return false;
		return isSizeConsistent(x.left) && isSizeConsistent(x.right);
	} 

	// check that ranks are consistent
	private boolean isRankConsistent() {
		for (int i = 0; i < _size(); i++)
			if (i != rank(select(i))) return false;
		for (Key _key : _keys())
			if (_key.compareTo(select(rank(_key))) != 0) return false;
		return true;
	}

	// Does the tree have no red right links, and at most one (left)
	// red links in a row on any path?
	private boolean is23() { return is23(root); }
	private boolean is23(Node x) {
		if (x == null) return true;
		if (isRed(x.right)) return false;
		if (x != root && isRed(x) && isRed(x.left))
			return false;
		return is23(x.left) && is23(x.right);
	} 

	// do all paths from root to leaf have same number of black edges?
	private boolean isBalanced() { 
		int black = 0;     // number of black links on path from root to min
		Node x = root;
		while (x != null) {
			if (!isRed(x)) black++;
			x = x.left;
		}
		return isBalanced(root, black);
	}

	// does every path from the root to a leaf have the given number of black links?
	private boolean isBalanced(Node x, int black) {
		if (x == null) return black == 0;
		if (!isRed(x)) black--;
		return isBalanced(x.left, black) && isBalanced(x.right, black);
	} 

}

/**
* Triangle
*/
class Triangle {

	public Point a;
	public Point b;
	public Point c;

	public Triangle(Point a, Point b, Point c) {

		this.a = a;
		this.b = b;
		this.c = c;

	}

	public Triangle(float ax, float ay, float bx, float by, float cx, float cy) {

		this(new Point(ax, ay, null), new Point(bx, by, null), new Point(cx, cy, null));

	}

	public boolean contains(Point q) {

		Turn turnAQC = new Turn(this.a, q, this.c);
		Turn turnCQB = new Turn(this.c, q, this.b);
		Turn turnBQA = new Turn(this.b, q, this.a);

		if ((turnAQC.value >= 0 && turnCQB.value >= 0 && turnBQA.value >= 0) ||
				(turnAQC.value <= 0 && turnCQB.value <= 0 && turnBQA.value <= 0))
			return true;
		else return false;

	}

	public boolean contains(float qx, float qy) {

		return contains(new Point(qx, qy, null));

	}

	public boolean strictlyContains(Point q) {

		Turn turnAQC = new Turn(this.a, q, this.c);
		Turn turnCQB = new Turn(this.c, q, this.b);
		Turn turnBQA = new Turn(this.b, q, this.a);

		if ((turnAQC.value > 0 && turnCQB.value > 0 && turnBQA.value > 0) ||
				(turnAQC.value < 0 && turnCQB.value < 0 && turnBQA.value < 0))
			return true;
		else return false;


	}

	public boolean strictlyContains(float qx, float qy) {

		return strictlyContains(new Point(qx, qy, null));

	}
	
	public Point findCentroid() {
		
		double x = (a.x+b.x+c.x)/(double)3;
		double y = (a.y+b.y+c.y)/(double)3;
		
		return new Point(x, y, null);
		
	}

}


/**
* DCEL
*/
class DCEL {

	public ArrayList<Face> faces;
	public ArrayList<Point> vertices;
	public ArrayList<Halfedge> halfedges;
	public Face outer;
	private boolean initialized;

	public DCEL() {

		this.initialized = false;
		this.faces = new ArrayList<Face>();
		this.vertices = new ArrayList<Point>();
		this.halfedges = new ArrayList<Halfedge>();
		outer = new Face();

		this.faces.add(outer);
	}

	/**
	 * Hooks a new vertex to the desired face.
	 * @param f
	 * @param h
	 * @param v
	 */
	public int addVertexAt(int face, int halfedge, Point v) {

		Face f = this.faces.get(face);
		Halfedge h = this.halfedges.get(halfedge);

		Point u = h.target;

		Halfedge h1 = new Halfedge();
		Halfedge h2 = new Halfedge();

		v.h = h2;
		h1.twin = h2;
		h2.twin = h1;
		h1.target = v;
		h2.target = u;
		h1.face = f;
		h2.face = f;
		h1.next = h2;
		h2.next = h.next;
		h1.prev = h;
		h2.prev = h1;
		h.next = h1;
		h2.next.prev = h2;

		this.vertices.add(v);
		this.halfedges.add(h1);
		this.halfedges.add(h2);

		return (this.halfedges.size() - 2);

	}


	/**
	 * Splits a face into two new faces, creating a new edge between two of its vertices.
	 * @param f
	 * @param h
	 * @param v
	 * @return
	 */
	public int[] splitFace(Face f, Halfedge h, Point v) {

		int[] newFaces = new int[2];

		Point u = h.target;

		Face f1 = new Face();
		Face f2 = new Face();

		Halfedge h1 = new Halfedge();
		Halfedge h2 = new Halfedge();

		f1.h = h1;
		f2.h = h2;
		h1.twin = h2;
		h2.twin = h1;
		h1.target = v;
		h2.target = u;
		h2.next = h.next;
		h2.next.prev = h2;
		h1.prev = h;
		h.next = h1;

		Halfedge i = h2;
		
		while (true) {

			i.face = f2;
			if(i.target.equals(v)) break;
			i = i.next;
		}


		h1.next = i.next;
		h1.next.prev = h1;
		i.next = h2;
		h2.prev = i;
		i = h1;

		do {

			i.face = f1;
			i = i.next;
		} while (!i.target.equals(u));

		i.face = f1;

		this.faces.remove(f);

		this.faces.add(f2);
		this.faces.add(f1);
		newFaces[0] = this.faces.size() - 1;
		newFaces[1] = this.faces.size() - 2;

		this.halfedges.add(h1);
		this.halfedges.add(h2);

		return newFaces;
	}

	/**
	 * Splits a face into two new faces, creating a new edge between two of its vertices.
	 * @param f
	 * @param h
	 * @param v
	 */
	public void splitFace2(int face, Halfedge h, Point v) {

		Point u = h.target;

		Face f1 = new Face();
		Face f2 = new Face();

		Halfedge h1 = new Halfedge();
		Halfedge h2 = new Halfedge();

		f1.h = h1;
		f2.h = h2;
		h1.twin = h2;
		h2.twin = h1;
		h1.target = v;
		h2.target = u;
		h2.next = h.next;
		h2.next.prev = h2;
		h1.prev = h;
		h.next = h1;

		Halfedge i = h2;


		while (true) {

			i.face = f2;
			if(i.target.equals(v)) break;
			i = i.next;
		}


		h1.next = i.next;
		h1.next.prev = h1;
		i.next = h2;
		h2.prev = i;
		i = h1;

		do {

			i.face = f1;
			i = i.next;
		} while (!i.target.equals(u));
		
		i.face = f1;

		this.faces.set(face, f1);

		this.faces.add(f2);

		this.halfedges.add(h1);
		this.halfedges.add(h2);


	}

	/**
	 * Splits and edge, represented by a halfedge, into two new edges, incident to a given point.
	 * @param halfedge
	 * @param w
	 * @return
	 */
	public Face splitEdge(Halfedge h, Point w) {


		Face f1 = h.face;
		Face f2 = h.twin.face;

		Halfedge h1 = new Halfedge();
		Halfedge h2 = h;

		Halfedge k1 = new Halfedge();
		Halfedge k2 = h.twin;

		h1.face = f1;
		h1.next = h2;
		h1.prev = h.prev;
		h1.twin = k2;
		h1.target = w;

		k1.face = f2;
		k1.next = k2;
		k1.prev = h.twin.prev;
		k1.twin = h2;
		k1.target = w;

		h1.prev.next = h1;
		h1.prev.target.h = h1;
		k1.prev.next = k1;
		k1.prev.target.h = h2.next;

		h2.next.prev = h2;
		k2.next.prev = k2;


		//h2.face = f1;
		//h2.next = h.next;
		h2.prev = h1;
		h2.twin = k1;
		//h2.target = h.target;


		//k2.face = f2;
		//k2.next = h.twin.next;
		k2.prev = k1;
		k2.twin = h1;
		//k2.target = h.twin.target;


		w.h = h2;

		this.halfedges.add(h1);
		this.halfedges.add(k1);

		this.vertices.add(w);

		return f1;

	}

	/**
	 * Initializes the dcel with a single edge.
	 * @param p1
	 * @param p2
	 * @return
	 */
	public void initialize(ArrayList<Point> points) {

		if(initialized) return;
		if(points.size() < 3) throw new IllegalArgumentException("Provide at least 3 vertices!");

		Point p1 = points.get(0);
		Point p2 = points.get(1);

		Halfedge h1 = new Halfedge();
		Halfedge h2 = new Halfedge();
		Face f = this.outer;

		p1.h = h1;
		p2.h = h2;

		h1.target = p2;
		h1.face = f;
		h1.twin = h2;
		h1.next = h2;
		h1.prev = h2;

		h2.target = p1;
		h2.face = f;
		h2.twin = h1;
		h2.next = h1;
		h2.prev = h1;

		f.h = h1;

		this.vertices.add(p1);
		this.vertices.add(p2);
		this.halfedges.add(h1);
		this.halfedges.add(h2);

		int he = this.halfedges.size() - 2;

		for(int i = 2; i < points.size(); i++) {

			p2 =  points.get(i);
			he = this.addVertexAt(0, he, p2);
		}

		this.splitFace(this.faces.get(0), this.halfedges.get(he), this.vertices.get(0));
		p1.h = p1.h.prev.twin;

		for(Point v: this.vertices)
			v.h = v.h.prev.twin;

		this.initialized = true;

	}
}


/**
* Face
*/
class Face {

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

/**
* Halfedge
*/

class Halfedge {

	public Point target;
	public Face face;
	public Halfedge twin;
	public Halfedge next;
	public Halfedge prev;

	public Point helper;
	
	public Halfedge(Point target, Face face, Halfedge twin, Halfedge next, Halfedge prev) {

		this.target = target;
		this.face = face;
		this.twin = twin;
		this.next = next;
		this.prev = prev;
	}

	public Halfedge() {
	}

	public Edge getEdge() {
		
		Point a = new Point(this.twin.target.x, this.twin.target.y, this.twin.target.h);
		Point b = new Point(this.target.x, this.target.y, this.target.h);

		Edge e= new Edge(a, b);
		
		e.helper = this.helper;
		
		return e;

	}

	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		final Halfedge other = (Halfedge) obj;

		if(this.target.equals(other.target) && this.getEdge().equals(other.getEdge()))
			return true;
		
		return false;
	}


}


/**
* Edge
*/

class Edge {

	public Point a;
	public Point b;
	public Point helper;

	public Edge(Point a, Point b) {

		this.a = a;
		this.b = b;

	}

	public Edge(double x, double y, double d, double e) {

		this.a = new Point(x, y, null);
		this.b = new Point(d, e, null);

	}

	public boolean intersectsRay(Edge e) {

		Turn ea = new Turn(e, this.a);
		Turn eb = new Turn(e, this.b);

		if((eb.value >= 0 && ea.value <= 0) ||
				(eb.value <= 0 && ea.value >= 0)) {

			CrossProduct aEaB = new CrossProduct(this.a, this.b, this.a, e.a);
			DotProduct EaEbP = new DotProduct(this.a, this.b, new Point(0, 0, null), new Point(-(e.b.y-e.a.y), (e.b.x-e.a.x), null));

			double t1 = aEaB.value/EaEbP.value;

			if(t1 >= 0) return true;

		}

		return false;
	}

	public boolean intersectsRay(Point a, Point b) {

		return this.intersectsRay(new Edge(a, b));
	}

	public boolean intersectsRay(float ax, float ay, float bx, float by) {

		return this.intersectsRay(new Point(ax, ay, null), new Point(bx, by, null));
	}

	public boolean intersectsEdge(Edge e) {

		Turn a1b1b2 = new Turn(this.a, this.b, e.b);
		Turn a1b1a2 = new Turn(this.a, this.b, e.a);
		Turn a2b2a1 = new Turn(e.a, e.b, this.a);
		Turn a2b2b1 = new Turn(e.a, e.b, this.b);

		if(((a1b1b2.value >= 0 && a1b1a2.value <= 0)||(a1b1b2.value <= 0 && a1b1a2.value >= 0))
				&&((a2b2a1.value >= 0 && a2b2b1.value <= 0)||(a2b2a1.value <= 0 && a2b2b1.value >= 0))) {
			
			if(a1b1b2.value == 0 && a1b1a2.value == 0) {
				if(this.isInBoundingBox(this.a, e) || this.isInBoundingBox(this.b, e))
					return true;
				else return false;
			}
			else return true;

		}
			
		return false;
	}
	
	public boolean strictlyIntersectsLine(Edge e) {
		
		Turn a1b1b2 = new Turn(e.a, e.b, this.b);
		Turn a1b1a2 = new Turn(e.a, e.b, this.a);
		
		if((a1b1b2.value > 0 && a1b1a2.value < 0)||(a1b1b2.value < 0 && a1b1a2.value > 0))
			return true;
		
		return false;
		
	}
	public boolean intersectsLine(Edge e) {

		Turn a1b1b2 = new Turn(e.a, e.b, this.b);
		Turn a1b1a2 = new Turn(e.a, e.b, this.a);
		
		if((a1b1b2.value >= 0 && a1b1a2.value <= 0)||(a1b1b2.value <= 0 && a1b1a2.value >= 0))
			return true;
		
		return false;
		
		
	}

	public boolean strictlyIntersectsEdge(Edge e) {

		Turn a1b1b2 = new Turn(this.a, this.b, e.b);
		Turn a1b1a2 = new Turn(this.a, this.b, e.a);
		Turn a2b2a1 = new Turn(e.a, e.b, this.a);
		Turn a2b2b1 = new Turn(e.a, e.b, this.b);

		if(((a1b1b2.value > 0 && a1b1a2.value < 0)||(a1b1b2.value < 0 && a1b1a2.value > 0))
				&&((a2b2a1.value > 0 && a2b2b1.value < 0)||(a2b2a1.value < 0 && a2b2b1.value > 0)))
			return true;

		return false;
	}

	public void flip() {

		Point temp = this.a;
		this.a = this.b;
		this.b = temp;

	}

	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		final Edge other = (Edge) obj;

		if((this.a.equals(other.a) && this.b.equals(other.b)) ||
				(this.a.equals(other.b) && this.b.equals(other.a)))
			return true;

		return false;
	}

	public boolean isInBoundingBox(Point p, Edge d) {

		Point a, b;

		if(d.b.y >= d.b.y) {
			b = d.b;
			a = d.a;
		}
		else {
			b = d.a;
			a = d.b;
		}

		if(p.y <= b.y && p.y >= a.y) {

			if(b.x >= a.x) {

				if(p.x <= b.x && p.x >= a.x)
					return true;
				return false;

			}
			else {

				if(p.x >= b.x && p.x <= a.x)
					return true;
				return false;

			}
		}

		return false;

	}


}

/**
* Point
*/
class Point implements Cloneable{

	public static final double EPSILON = 0.01;
	public double x;
	public double y;
	public Halfedge h;

	public Point(double x, double y, Halfedge h) {
		this.x = x;
		this.y = y;
		this.h = h;
	}

	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		final Point other = (Point) obj;

		if ((Math.abs(this.x - other.x) > Point.EPSILON) || Math.abs(this.y - other.y) > Point.EPSILON) {
			return false;
		}
		return true;

	}
	
	public Object clone() throws CloneNotSupportedException {
		
		return super.clone();
		
	}


}