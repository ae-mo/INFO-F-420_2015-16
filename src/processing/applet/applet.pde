
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
}

void draw() {
  if (keyPressed)  
    test21();
}

void mouseClicked() {
  if (mouseButton == LEFT) {

    Point p = new Point(mouseX, mouseY, null);
    points.add(p);
    text(p.x + ", " + p.y, (float) p.x, (float)p.y);

    if (points.size() > 1) {

      Point p1 = points.get(points.size()-2);
      Point p2 = points.get(points.size()-1);

      line((float)p1.x, (float) p1.y, (float) p2.x, (float) p2.y);
    }
  } else if (mouseButton == RIGHT) {

    b = new Point(mouseX, mouseY, null);

    point((float)b.x, (float)b.y);
    text(b.x + ", " + b.y, (float) b.x, (float)b.y);
  }
}


void test21() {
  
  System.out.println("Started");

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