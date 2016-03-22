import java.util.ArrayList;

import attractionRegion.AttractionRegion;
import attractionRegion.Key;
import dataStructures.DCEL;
import dataStructures.Edge;
import dataStructures.Face;
import dataStructures.Halfedge;
import dataStructures.LineArrangement;
import dataStructures.Point;
import dataStructures.RedBlackBST;
import inverseAttractionRegion.InverseAttractionRegion;
import operations.RadialSort;
import operations.SPT;
import operations.Turn;
import routing.MinimumBeaconPath;

public class Tests {

	public static void main(String[] args) {

		test24();

	}


	public static void test20() {

		ArrayList<Point> points = new ArrayList<Point>();

		points.add(new Point(259, 87, null));
		points.add(new Point(204, 127, null));
		points.add(new Point(311, 112, null));
		points.add(new Point(307, 64, null));
		points.add(new Point(346, 113, null));
		points.add(new Point(380, 110, null));
		points.add(new Point(345, 68, null));
		points.add(new Point(423, 120, null));
		points.add(new Point(452, 102, null));
		points.add(new Point(453, 117, null));
		points.add(new Point(504, 76, null));
		points.add(new Point(470, 35, null));
		points.add(new Point(561, 94, null));
		points.add(new Point(473, 134, null));
		points.add(new Point(591, 102, null));
		points.add(new Point(538, 141, null));
		points.add(new Point(657, 115, null));
		points.add(new Point(623, 49, null));
		points.add(new Point(693, 117, null));
		points.add(new Point(732, 81, null));
		points.add(new Point(747, 39, null));
		points.add(new Point(757, 121, null));
		points.add(new Point(719, 129, null));
		points.add(new Point(748, 167, null));
		points.add(new Point(818, 152, null));
		points.add(new Point(792, 69, null));
		points.add(new Point(855, 162, null));
		points.add(new Point(793, 197, null));
		points.add(new Point(827, 238, null));
		points.add(new Point(740, 191, null));
		points.add(new Point(763, 249, null));
		points.add(new Point(693, 154, null));
		points.add(new Point(694, 234, null));
		points.add(new Point(669, 136, null));
		points.add(new Point(647, 224, null));
		points.add(new Point(624, 149, null));
		points.add(new Point(613, 224, null));
		points.add(new Point(583, 161, null));
		points.add(new Point(560, 207, null));
		points.add(new Point(536, 157, null));
		points.add(new Point(490, 196, null));
		points.add(new Point(514, 142, null));
		points.add(new Point(436, 176, null));
		points.add(new Point(447, 133, null));
		points.add(new Point(384, 156, null));
		points.add(new Point(428, 222, null));
		points.add(new Point(458, 195, null));
		points.add(new Point(486, 223, null));
		points.add(new Point(464, 269, null));
		points.add(new Point(382, 268, null));
		points.add(new Point(421, 246, null));
		points.add(new Point(376, 207, null));
		points.add(new Point(314, 200, null));
		points.add(new Point(353, 163, null));
		points.add(new Point(313, 152, null));
		points.add(new Point(261, 217, null));
		points.add(new Point(278, 261, null));
		points.add(new Point(305, 217, null));
		points.add(new Point(368, 226, null));
		points.add(new Point(318, 263, null));
		points.add(new Point(386, 243, null));
		points.add(new Point(334, 299, null));
		points.add(new Point(409, 298, null));
		points.add(new Point(424, 283, null));
		points.add(new Point(462, 313, null));
		points.add(new Point(527, 300, null));
		points.add(new Point(505, 251, null));
		points.add(new Point(579, 303, null));
		points.add(new Point(528, 207, null));
		points.add(new Point(614, 292, null));
		points.add(new Point(645, 244, null));
		points.add(new Point(641, 292, null));
		points.add(new Point(675, 210, null));
		points.add(new Point(674, 300, null));
		points.add(new Point(709, 205, null));
		points.add(new Point(720, 320, null));
		points.add(new Point(789, 282, null));
		points.add(new Point(754, 206, null));
		points.add(new Point(826, 257, null));
		points.add(new Point(857, 227, null));
		points.add(new Point(825, 199, null));
		points.add(new Point(881, 178, null));
		points.add(new Point(901, 258, null));
		points.add(new Point(806, 282, null));
		points.add(new Point(879, 346, null));
		points.add(new Point(767, 319, null));
		points.add(new Point(818, 382, null));
		points.add(new Point(715, 348, null));
		points.add(new Point(772, 395, null));
		points.add(new Point(669, 360, null));
		points.add(new Point(699, 315, null));
		points.add(new Point(611, 328, null));
		points.add(new Point(665, 395, null));
		points.add(new Point(562, 375, null));
		points.add(new Point(593, 349, null));
		points.add(new Point(544, 320, null));
		points.add(new Point(494, 331, null));
		points.add(new Point(553, 403, null));
		points.add(new Point(431, 329, null));
		points.add(new Point(421, 395, null));
		points.add(new Point(344, 322, null));
		points.add(new Point(351, 377, null));
		points.add(new Point(296, 277, null));
		points.add(new Point(243, 311, null));
		points.add(new Point(235, 249, null));
		points.add(new Point(232, 180, null));
		points.add(new Point(138, 211, null));
		points.add(new Point(154, 351, null));
		points.add(new Point(105, 181, null));


		Point b = new Point(349, 133, null);

		AttractionRegion attr = new AttractionRegion(b, points);

		System.out.println();
		System.out.println("****FACES OF THE ARRANGEMENT****");

		Halfedge h;
		for(int i = 0; i < attr.dcel.faces.size(); i++) {

			Face f = attr.dcel.faces.get(i);

			System.out.println();
			System.out.println("****FACE #" + i +"****"); 

			h = f.h;
			Point pp = h.target;

			for(int j = 0; j < 15; j++) {

				System.out.println();
				System.out.println("Halfedge");
				System.out.println("target: " + h.target.x + ", " + h.target.y);

				h =h.next;
			}


		}

		System.out.println();
		System.out.println("****ATTRACTION REGION****");

		h = attr.face.h;
		Point pp = h.target;

		for(int j = 0; j < 30; j++) {

			System.out.println();
			System.out.println("Halfedge");
			System.out.println("target: " + h.target.x + ", " + h.target.y);

			h =h.next;
		}


	}

	public static void test21() {

		ArrayList<Point> points = new ArrayList<Point>();

		points.add(new Point(9.56, 9.02, null));
		points.add(new Point(6.06, 10.84, null));
		points.add(new Point(0.58, 9.02, null));
		points.add(new Point(0.84, 6.98, null));
		points.add(new Point(3.16, 7.56, null));
		points.add(new Point(2.12, 8.46, null));
		points.add(new Point(3.54, 9.02, null));
		points.add(new Point(4.7, 7.12, null));
		points.add(new Point(2.96, 5.82, null));
		points.add(new Point(4.32, 4.54, null));
		points.add(new Point(6.8, 5.76, null));
		points.add(new Point(5, 6, null));
		points.add(new Point(6.4, 7.12, null));
		points.add(new Point(4.86, 9.02, null));
		points.add(new Point(7.52, 9.02, null));

		Point p = new Point(1.76, 9.02, null);
		
		SPT spt = new SPT(p, points);
	}
	
	public static void test22() {

		ArrayList<Point> points = new ArrayList<Point>();

		points.add(new Point(8.64, 10.84, null));
		points.add(new Point(6.06, 10.84, null));
		points.add(new Point(0.58, 9.02, null));
		points.add(new Point(0.84, 6.98, null));
		points.add(new Point(3.16, 7.56, null));
		points.add(new Point(2.12, 8.46, null));
		points.add(new Point(3.54, 9.02, null));
		points.add(new Point(4.7, 7.12, null));
		points.add(new Point(2.96, 5.82, null));
		points.add(new Point(4.32, 4.54, null));
		points.add(new Point(6.8, 5.76, null));
		points.add(new Point(5, 6, null));
		points.add(new Point(6.4, 7.12, null));
		points.add(new Point(4.86, 9.02, null));
		points.add(new Point(7.52, 9.02, null));
		
		DCEL dcel = new DCEL();
		
		dcel.initialize(points);

		Point p = new Point(1.76, 9.02, null);
		
		Face f = dcel.faces.get(1);
		
		System.out.println(f.contains(p));
		
	}

	public static void test23() {

		ArrayList<Edge> edges = new ArrayList<Edge>();

		edges.add(new Edge(new Point(500.8226, 232.94935, null), new Point(556.53559, 415.35253, null)));
		edges.add(new Edge(new Point(250.74011, 530.70506, null), new Point(785.02147, 466.83217, null)));
		edges.add(new Edge(new Point(503.32655, 562.16485, null), new Point(705.11, 375.26, null)));
		edges.add(new Edge(new Point(186.88927, 251.38034, null), new Point(692.06215, 248.52036, null)));
		edges.add(new Edge(new Point(765.30282, 415.35253, null), new Point(410.11, 188.35, null)));
		
		LineArrangement la = new LineArrangement(edges);
		
		System.out.println();
		System.out.println("****FACES OF THE ARRANGEMENT****");

		Halfedge h;
		for(int i = 0; i < la.faces.size(); i++) {

			Face f = la.faces.get(i);

			System.out.println();
			System.out.println("****FACE #" + i +"****"); 

			h = f.h;
			Point pp;

			do {
				
				pp = h.target;
				System.out.println();
				System.out.println("Halfedge");
				System.out.println("target: " + h.target.x + ", " + h.target.y);

				h =h.next;
			} while(f.h.target != h.target);


		}

		
	}
	
	public static void test24() {

		ArrayList<Point> points = new ArrayList<Point>();

		points.add(new Point(286, 130, null));
		points.add(new Point(515, 74, null));
		points.add(new Point(514, 190, null));
		points.add(new Point(350, 239, null));

		Point p = new Point(426, 166, null);
		
		InverseAttractionRegion inv = new InverseAttractionRegion(points, p);
		
		System.out.println();
		System.out.println("****FACES OF THE ARRANGEMENT****");

		Halfedge h;
		for(int i = 0; i < inv.faces.size(); i++) {

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

				h =h.next;
			} while(f.h.target != h.target);


		}

		
	}
	
	public static void test25() {
		
		Turn t = new Turn(new Point(286, 130, null), new Point(515, 74, null), new Point(514, 190, null));
		
		System.out.println(t.value);
		
	}
}