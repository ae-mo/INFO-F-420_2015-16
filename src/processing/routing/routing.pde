
int r, g, b;
ArrayList<Point> points;
ArrayList<Point> m;
PrintWriter polyPoints;
PrintWriter beaconPoints;

void setup() {

  size(800, 800); 

  r = 0;
  g = 255;
  b = 0;

  background(r, g, b);

  points = new ArrayList<Point>();
  m = new ArrayList<Point>();
  polyPoints = createWriter("points.txt");
  beaconPoints = createWriter("beacons.txt");
}

void draw() {
  if (keyPressed) {
    polyPoints.flush();
    polyPoints.close();
    beaconPoints.flush();
    beaconPoints.close();
    
    Point a = points.get(0);
    Point b = points.get(points.size() -1);
    line((float)a.x, (float) a.y, (float) b.x, (float) b.y);
    test21();
  }
}

void mouseClicked() {

  if (mouseButton == LEFT) {
    Point p = new Point(mouseX, mouseY);
    points.add(new Point(mouseX, mouseY));
    polyPoints.println("points.add(new Point("+ p.x + ", " + p.y + "));");
    fill(0, 0, 0);
    text(p.x + ", " + p.y, (float) p.x, (float)p.y);
    if (points.size() > 1) {

      Point p1 = points.get(points.size()-2);
      Point p2 = points.get(points.size()-1);

      line((float)p1.x, (float) p1.y, (float) p2.x, (float) p2.y);
    }
  } else if (mouseButton == RIGHT) {

    Point b =new Point(mouseX, mouseY); 
    m.add(b);
    beaconPoints.println("m.add(new Point("+ b.x + ", " + b.y + "));");
    point((float)b.x, (float)b.y);
    fill(0, 0, 255);
    text(b.x + ", " + b.y, (float) b.x, (float)b.y);
  }
}


void test21() {

  Point start = m.get(0);
  Point end = m.get(m.size()-1);
  m.remove(0);
  m.remove(m.size()-1);

  MinimumBeaconPath mbp = new MinimumBeaconPath(points, m, start, end); 

  fill(255, 0, 0);
  if (mbp.beacons == null) {
    text("NOT ROUTED!", 30, 30);
    return;
  }


  int i = 1;
  textSize(30);
  for (Point p : mbp.beacons) {

    fill(255, 0, 0);
    text(i, (float) p.x - 20, (float)p.y);
    i++;
  }
}