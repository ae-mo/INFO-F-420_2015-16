
int r, g, b;
    ArrayList<Point> points;
void setup() {

  size(400, 400);
  textSize(32); 

  r = 255;
  g = 255;
  b = 0;

  background(r, g, b);
  
  points = new ArrayList<Point>();

}

void draw() {
  if(keyPressed)  
      test21();
}

void mouseClicked() {
  
  points.add(new Point(mouseX, mouseY));
  
  if(points.size() > 1) {
  
    Point p1 = points.get(points.size()-2);
    Point p2 = points.get(points.size()-1);
    
    line((float)p1.x,(float) p1.y,(float) p2.x,(float) p2.y);
    
  }
  
}


void test21() {
 System.out.println("hey");
    DCEL dcel = new DCEL();

    Point p1 = points.get(0);
    Point p2 =  points.get(1);

    int he = dcel.initialize(p1, p2);

    Halfedge h = dcel.halfedges.get(he);

  for(int i=2; i < points.size(); i++) {

      p2 = points.get(i);
      he = dcel.addVertexAt(0, he, p2);
      h = dcel.halfedges.get(he);
    }



    dcel.splitFace(0, dcel.halfedges.get(he), 0);
    p1.h = p1.h.prev.twin;
    
    for(int i = 0; i < dcel.faces.size(); i++) {

      Face f = dcel.faces.get(i);

      System.out.println();
      System.out.println("****FACE #" + i +"****"); 

      h = f.h;
      Point pp = h.target;

      for(int j = 0; j < 15; j++) {

        System.out.println();
        System.out.println("Halfedge");
        System.out.println("target: " + h.target.x + ", " + h.target.y);
        System.out.println("twin target: " + h.twin.target.x + ", " + h.twin.target.y);
        System.out.println("next target: " + h.next.target.x + ", " + h.next.target.y);
        System.out.println("prev target: " + h.prev.target.x + ", " + h.prev.target.y);

        h =h.next;
      }


    }

  
    Triangulation tr = new Triangulation(dcel);

    System.out.println();
    System.out.println("****FACES OF THE ARRANGEMENT****");

    for(int i = 1; i < dcel.faces.size(); i++) {

      Face f = dcel.faces.get(i);

      System.out.println();
      System.out.println("****FACE #" + i +"****"); 

      h = f.h;
      Point pp = h.target;

      for(int j = 0; j < 3; j++) {

        line((float)h.target.x, (float)h.target.y,(float) h.twin.target.x, (float)h.twin.target.y);

        h =h.next;
      }


    }


  }