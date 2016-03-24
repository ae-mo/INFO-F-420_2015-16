PrintWriter polyPoints;
PrintWriter beaconPoints;
int r, g, bl;
ArrayList<Point> points;
Point b;    
void setup() {

  size(1068, 600);

  r = 0;
  g = 255;
  bl = 0;

  background(r, g, bl);

  points = new ArrayList<Point>();
  polyPoints = createWriter("points.txt");
  beaconPoints = createWriter("beacons.txt");
}

void draw() {
  if (keyPressed)  {
  
    polyPoints.flush();
    polyPoints.close();
    beaconPoints.flush();
    beaconPoints.close();
    test21();
  }
    
}

void mouseClicked() {
  if (mouseButton == LEFT) {

    Point p = new Point(mouseX, mouseY, null);
    points.add(p);
    polyPoints.println("points.add(new Point("+ p.x + ", " + p.y + ", null));");
    text(p.x + ", " + p.y, (float) p.x, (float)p.y);

    if (points.size() > 1) {

      Point p1 = points.get(points.size()-2);
      Point p2 = points.get(points.size()-1);

      line((float)p1.x, (float) p1.y, (float) p2.x, (float) p2.y);
    }
  } else if (mouseButton == RIGHT) {

    b = new Point(mouseX, mouseY, null);
    
    beaconPoints.println("m.add(new Point("+ b.x + ", " + b.y + ", null));");
    point((float)b.x, (float)b.y);
    text(b.x + ", " + b.y, (float) b.x, (float)b.y);
  }
}


void test21() {
  
  System.out.println("Started");
  
  points.add(new Point(329.0, 308.0, null));
    points.add(new Point(348.0, 178.0, null));
    points.add(new Point(475.0, 171.0, null));
    points.add(new Point(400.0, 123.0, null));
    points.add(new Point(315.0, 147.0, null));
    points.add(new Point(321.0, 95.0, null));
    points.add(new Point(474.0, 107.0, null));
    points.add(new Point(432.0, 74.0, null));
    points.add(new Point(592.0, 62.0, null));
    points.add(new Point(576.0, 145.0, null));
    points.add(new Point(527.0, 139.0, null));
    points.add(new Point(596.0, 229.0, null));
    points.add(new Point(704.0, 122.0, null));
    points.add(new Point(621.0, 74.0, null));
    points.add(new Point(875.0, 109.0, null));
    points.add(new Point(813.0, 310.0, null));
    points.add(new Point(768.0, 222.0, null));
    points.add(new Point(593.0, 288.0, null));
    points.add(new Point(546.0, 381.0, null));
    points.add(new Point(493.0, 259.0, null));
    points.add(new Point(527.0, 258.0, null));
    points.add(new Point(413.0, 206.0, null));
    
    b = new Point(514.0, 172.0, null);
  
  line((float)points.get(0).x, (float)points.get(0).y, (float) points.get(points.size()-1).x, (float)points.get(points.size()-1).y);
  
  InverseAttractionRegion inv = new InverseAttractionRegion(points, b);

  System.out.println();
  System.out.println("****FACES OF THE ARRANGEMENT****");
  PShape s;

  Halfedge h;
  for (int i = 0; i < inv.faces.size(); i++) {

    Face f = inv.faces.get(i);

    System.out.println();
    System.out.println("****FACE #" + i +"****"); 

    h = f.h;
    Point pp;

    do {
      
      pp = h.target;
      System.out.println();
      System.out.println("Halfedge");
      System.out.println("target: " + h.target.x + ", " + h.target.y);
      line((float)h.target.x, (float)h.target.y, (float) h.prev.target.x, (float)h.prev.target.y);
      h =h.next;
    } while(!f.h.target.equals(h.target));
    
    s = getShape(f);
    shape(s, 0, 0);

  }
  

  text(b.x + ", " + b.y, (float) b.x, (float)b.y);  
}

PShape getShape(Face f) {

  Halfedge h = f.h;
  PShape s = createShape();
  s.beginShape();
  
  float x, y;
  do {
  
    x = (float) h.target.x;
    y = (float) h.target.y;
    
    s.vertex(x, y);
    
    h = h.next;
    
  } while(h.target != f.h.target);
  
  s.endShape();
  
  return s;
  
}