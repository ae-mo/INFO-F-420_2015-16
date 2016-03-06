import java.util.ArrayList;

public class MinimumBeaconPath {

  public ArrayList<Point> points;
  public ArrayList<Point> beacons;
  public ArrayList<Point> candidateBeacons;
  public Point start;
  public Point end;



  public MinimumBeaconPath(ArrayList<Point> points, ArrayList<Point> candidateBeacons,
      Point start, Point end) {

    this.points = points;
    this.beacons = new ArrayList<Point>();
    this.candidateBeacons = candidateBeacons;
    this.start = start;
    this.end = end;

    try {
      this.computeMinimumBeaconPath();
    } catch (CloneNotSupportedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void computeMinimumBeaconPath() throws CloneNotSupportedException {

    ArrayList<Face> attractionRegions = new ArrayList<Face>();
    ArrayList<Point> beacons = new ArrayList<Point>(this.candidateBeacons);
    beacons.add(start);
    beacons.add(end);
    ArrayList<Point> clonedPoints;
    AttractionRegion attr;
    ArrayList<Graph.Edge> edges = new ArrayList<Graph.Edge>();

    for(Point c: beacons) {

      clonedPoints = this.clonePoints(this.points);
      attr = new AttractionRegion(c, clonedPoints);
      attractionRegions.add(attr.face);

    }
    
    System.out.println("ENDED ATTRACTION REGIONS");

    for(int c = 0; c < beacons.size(); c++) {

      Point b;
      Point b2;
      double dist;
      Face f = attractionRegions.get(c);
      for(int d = 0; d < beacons.size(); d++) {

        b = beacons.get(d);
        if(d!=c) {

          if(f.contains(b)) {

            b2 = beacons.get(c);
            dist = Math.sqrt(Math.pow(b2.x-b.x, 2) + Math.pow(b2.y-b.y, 2));
            edges.add(new Graph.Edge(String.valueOf(d), String.valueOf(c), dist));

          }

        }

      }

    }

    Graph.Edge[] graph = new Graph.Edge[1];
    Graph g = new Graph(edges.toArray(graph));
    int start = beacons.size() - 2;
    int end = beacons.size() - 1;
    g.dijkstra(String.valueOf(start));
    ArrayList<String> path = new ArrayList<String>();
    path = g.getPath(String.valueOf(end), path);

    if(path == null)
      this.beacons = null;
    else {
      
      int point;
      for(String d: path) {

        point = Integer.parseInt(d);
        this.beacons.add(beacons.get(point));

      }


    }

  }

  private ArrayList<Point> clonePoints(ArrayList<Point> points) throws CloneNotSupportedException {

    ArrayList<Point> clone = new ArrayList<Point>(points.size());
    for(Point item: points) clone.add((Point) item.clone());
    return clone;

  }

}