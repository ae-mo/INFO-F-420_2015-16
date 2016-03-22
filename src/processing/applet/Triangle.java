public class Triangle {

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