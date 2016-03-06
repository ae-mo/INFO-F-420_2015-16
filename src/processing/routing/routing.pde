
int r, g, b;
ArrayList<Point> points;
ArrayList<Point> m;

void setup() {

  size(800, 800);
  textSize(32); 

  r = 255;
  g = 255;
  b = 0;

  background(r, g, b);

  points = new ArrayList<Point>();
  m = new ArrayList<Point>();
}

void draw() {
  if (keyPressed) {
    Point a = points.get(0);
    Point b = points.get(points.size() -1);
    line((float)a.x, (float) a.y, (float) b.x, (float) b.y);
    test21();
  }
}

void mouseClicked() {

  if (mouseButton == LEFT) {
    points.add(new Point(mouseX, mouseY));

    if (points.size() > 1) {

      Point p1 = points.get(points.size()-2);
      Point p2 = points.get(points.size()-1);

      line((float)p1.x, (float) p1.y, (float) p2.x, (float) p2.y);
    }
  } else if (mouseButton == RIGHT) {

    Point b =new Point(mouseX, mouseY); 
    m.add(b);

    point((float)b.x, (float)b.y);
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
  for (Point p : mbp.beacons) {

    fill(255, 0, 0);
    text(i, (float) p.x - 20, (float)p.y);
    i++;
  }
}