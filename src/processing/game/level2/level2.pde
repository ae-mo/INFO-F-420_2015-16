// Mouse click to see only the small circles
// ellipse, sin, cos, line, rotation, circle
Shape b1, b2, b3, b4, b5;
boolean seeSmallOnly;
 
void setup() {
  size(1068, 600);
  ellipseMode(RADIUS);
  seeSmallOnly = false;
  b1 = new Shape(.5, -.5);
  b2 = new Shape(.8, -.8);
  b3 = new Shape(.8, -.8);
  b4 = new Shape(.8, -.8);
  b5 = new Shape(.8, -.8);
  frameRate(40);
}
 
void draw() {
  background(0);
  b1.display(width/2, height/2);
  b2.display(b1.connectPt1.x, b1.connectPt1.y);
  b3.display(b1.connectPt2.x, b1.connectPt2.y);
  b4.display(b1.connectPt3.x, b1.connectPt3.y);
  b5.display(b1.connectPt4.x, b1.connectPt4.y);
}
 
void mousePressed() {
  seeSmallOnly = !seeSmallOnly;
}
 
class Shape {
  PVector  connectPt1, connectPt2, connectPt3, connectPt4;
  float x, y, ang1, ang2, ang3, ang4, incr1, incr2, rad;
 
  Shape(float incr1In, float incr2In) {
    incr1 = incr1In;
    incr2 = incr2In;
    connectPt1 = new PVector();
    connectPt2 = new PVector();
    connectPt3 = new PVector();
    connectPt4 = new PVector();
    rad =  100;
    ang1 = ang4 =0;
    ang2 = ang3 = 180;
  }
 
  void display(float xin, float yin) {
    update( xin, yin);
    drawShape();
  }
 
  void update(float xin2, float yin2) {
    x = xin2;
    y = yin2;
 
    connectPt1.x = x + cos(radians(ang1))*rad;
    connectPt1.y = y + sin(radians(ang1))*rad;
    ang1 += incr1;
 
    connectPt2.x = x + cos(radians(ang2))*rad;
    connectPt2.y = y + sin(radians(ang2))*rad;
    ang2 += incr2;
 
    connectPt3.x = x + cos(radians(ang3))*rad;
    connectPt3.y = y + sin(radians(ang3))*rad;
    ang3 += incr1;
 
    connectPt4.x = x + cos(radians(ang4))*rad;
    connectPt4.y = y + sin(radians(ang4))*rad;
    ang4 += incr2;
  }
 
  void drawShape() {
    if (!seeSmallOnly) {
      stroke(#FCA03D);
      strokeWeight(3);
      fill(#FCA03D);
      line(connectPt1.x, connectPt1.y, connectPt3.x, connectPt3.y);
      line(connectPt2.x, connectPt2.y, connectPt4.x, connectPt4.y);
      noFill();
      ellipse(width/2, height/2, 2*rad, 2*rad);
      ellipse(x, y, rad, rad);
    }
 
    fill(#FCA03D);
    stroke(0);
    strokeWeight(1.5);
    ellipse(width/2, height/2, 12, 12);
    ellipse(connectPt1.x, connectPt1.y, 12, 12);
    ellipse(connectPt2.x, connectPt2.y, 12, 12);
    ellipse(connectPt3.x, connectPt3.y, 12, 12);
    ellipse(connectPt4.x, connectPt4.y, 12, 12);
  }
}
