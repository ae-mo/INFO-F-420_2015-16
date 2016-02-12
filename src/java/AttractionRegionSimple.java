import java.util.ArrayList;

class AttractionRegionSimple extends AttractionRegion {

	DCEL dcel;
	Point b;
	ArrayList<Point> rayVertices;
	
	public AttractionRegionSimple(Point b, DCEL dcel) {
		
		this.dcel = dcel;
		this.b = b;
		this.rayVertices = this.computeAttractionRegion();
		
	}
	
	@Override
	public ArrayList<Point> computeAttractionRegion() {
		
		ArrayList<Point> sortedVertices = this.sortVertices(b, this.dcel.vertices);
		
		return this.computeRayVertices(b, sortedVertices);

	}

}
