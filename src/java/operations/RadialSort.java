package operations;
import java.util.ArrayList;
import java.util.List;

import dataStructures.Point;

public class RadialSort {

	public ArrayList<Point> result;

	public RadialSort(Point q, ArrayList<Point> points) {

		this.result = (ArrayList<Point>) cyclicOrder(q, points);

	}


	public RadialSort(double qx, double qy, ArrayList<Point> points) {

		this(new Point(qx, qy), points);

	}

	private List<Point> cyclicOrder(Point q, List<Point> arr) {

		if (!arr.isEmpty()) {
			Point pivot = arr.get(0); //This pivot can change to get faster results

			

			List<Point> less = new ArrayList<Point>();
			List<Point> pivotList = new ArrayList<Point>();
			List<Point> more = new ArrayList<Point>();

			// Partition
			for (Point i: arr) {
				int comparison = compare(q, i, pivot);
				if (comparison > 0)
					less.add(i);
				else if (comparison < 0)
					more.add(i);
				else
					pivotList.add(i);
			}

			// Recursively sort sublists
			less = cyclicOrder(q, less);
			more = cyclicOrder(q, more);

			// Concatenate results
			less.addAll(pivotList);
			less.addAll(more);
			return less;
		}
		return arr;
	}

	public static int compare(Point q, Point pivot, Point curr) {

		int result = 0;

		Turn t = new Turn(q, pivot, curr);
		
		if(t.value > 0) result = 1;
		else if (t.value < 0) result = -1;
		else result = comesBefore(q, pivot, curr);

		return result;

	}

	private static int comesBefore(Point q, Point a, Point b) {

		double xDiff = b.x - a.x - q.x;
		double yDiff = b.y - a.y -q.y;

		if(a.x == b.x && a.y == b.y) return 0;
		else if((xDiff > 0) || (xDiff == 0 && yDiff > 0)) return 1;
		else  return -1;

	}

}
