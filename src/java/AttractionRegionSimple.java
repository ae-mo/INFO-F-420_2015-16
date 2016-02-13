import java.util.ArrayList;

class AttractionRegionSimple extends AttractionRegion {

	DCEL dcel;
	Point b;

	public AttractionRegionSimple(Point b, DCEL dcel) {

		this.dcel = dcel;
		this.b = b;
		this.computeAttractionRegion();

	}

	@Override
	public void computeAttractionRegion() {

		ArrayList<Point> sortedVertices = this.sortVertices(b, this.dcel.vertices);

		this.computeRayVertices(b, sortedVertices, this.dcel);

	}

}
