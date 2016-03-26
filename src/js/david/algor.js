var v = [];
var dcel = new DCEL();

var beas = [];  // Array of beacons.
var index_beacons = 0;
var myVar;

/// Points inside
var flag_points = 0;
var v_points = [];
var face1;
var face2;
var p1_found = 0;
var p2_found = 0;
var beacons1 = [];
var beacons2 = [];
var path_found = 0;


function setup() {
  // uncomment this line to make the canvas the full size of the window
  
  var cnv = createCanvas(1000, 500);
   // positions canvas 50px to the right and 100px
   // below upper left corner of the window
   cnv.position(300, 300);
  
  background('rgba(225,255,0, 0.50)');
}

function draw() {
  // draw stuff heres

}

function mousePressed() {

	if ( flag_points == 0 )
	{
		var p = new Point( mouseX, -mouseY);
		v.push(p);
		ellipse(mouseX, mouseY, 3, 3);

		if (v.length > 1) {
			line( v[v.length-1].x, -v[v.length-1].y, v[v.length-2].x, -v[v.length-2].y );
		}
	}
	else {
		if ( flag_points < 3 ) {

		var p = new Point( mouseX, -mouseY);  // Here we register the query points (intended to be TWO)
		v_points.push(p);

		if (flag_points == 1)
			fill('red');
		else
			fill('green');

		rect(mouseX, mouseY, 5, 5);
		flag_points++;

		}

	}
    
  // prevent default
  return false;
}
/*
function Point (x,y)
{
	this.x = x;
	this.y = y;
};  
Point.prototype.getInfo = function() {
	return this.x + ' ' + this.y;
};
*/
function side ( a, b, c )  // Orientation determinant.
{
	return b.x*c.y - a.x*c.y + a.x*b.y - c.x*b.y + c.x*a.y - b.x*a.y;
};

function showSide (a)   // Shows the type orientation given by a real.
{
    if (a>0) 
		console.log( "Left" );     // All the outputs will be done using "console.log"
	else if (a<0) 
		console.log( "Right" );
	else
		console.log( "Line" );
};

function insideTr ( A, B, C, d)  // Tells us if 'd' is inside ABC triangle, outside or in the border.								
	{
		var l1 = side( A, B, d);
		var l2 = side( B, C, d);
		var l3 = side( C, A, d);
	
		if ( (l1 >= 0 && l2 >= 0 && l3 >= 0)  ||  (l1 <= 0 && l2 <= 0 && l3 <= 0) ) {
			if ( l1 == 0 || l2 == 0 || l3 == 0 ) 
				return 0;
			else
				return -1; // Inside
		}
		else 
			return 1; //Outside
};

function radialSort( v, po )  
{
	var a;
	var aux = new Point( po.x, po.y+1);
	var v1 = [];
	var v2 = [];
	
	for (var i = 0; i <= v.length-1; i++) 
	{
		a = side ( po, aux, v[i] );
		 if (a<0) 
			v1.push(v[i]);
		else if (a>0) 
			v2.push(v[i]);
		else
			if (v[i].y > po.y)
				v1.push(v[i]);
			else
				v2.push(v[i]);				
	}

	v1.sort(function(a, b){return side (po, a, b)});
	v2.sort(function(a, b){return side (po, a, b)});
	
	return v1.concat(v2);
};

function showVector(v)   // Show vector function for Points.
{
	for (var i = 0; i < v.length; i++) 
		console.log(v[i].getInfo() + ".");
};

function connectPoints(v)   // Connect points in a radially sorted array.
{
	for (var i = 0; i < v.length-1; i++) 
		line( v[i].x, -v[i].y, v[i+1].x, -v[i+1].y );
	
	line( v[v.length-1].x, -v[v.length-1].y, v[0].x, -v[0].y );
};

function keyPressed() { 
	if (keyCode === ENTER && v.length >= 3 ) {   // When Enter is pressed, then we finalize the input of the simple polygon.
		
		line( v[v.length-1].x, -v[v.length-1].y, v[0].x, -v[0].y );  // We draw the last line.

		console.log("This is a polygon with " + v.length + " vertices.");
	
		// Initialize the dcel.


		var v_halfedges = [];

		for (var i = 0; i < 2*v.length; i++)	
			v_halfedges[i] = new HalfEdge();

		// From here on, we will consider even index halfedges to be in the INNER FACE and odd index to be in the OUTER FACE.

		for (var i = 0; i < v.length; i++)   // We initizalize the points.
			if ( i%2 == 1 )
				v[i].edge = v_halfedges[2*i-2];
			else
				v[i].edge = v_halfedges[2*i+1];

		var inner = new Face (v_halfedges[0]);
	  	var outer = new Face (v_halfedges[1], 1); // outer face

	  	// even
		for (var i = 2; i < 2*v.length -2; i=i+2)
		{
			v_halfedges[i].target = v[i/2];
			v_halfedges[i].twin = v_halfedges[i+1];
			v_halfedges[i].next = v_halfedges[i-2];
			v_halfedges[i].previous = v_halfedges[i+2];
			v_halfedges[i].face = inner;

		}

		v_halfedges[0].target = v[0];          // Special case.
		v_halfedges[0].twin = v_halfedges[1];
		v_halfedges[0].next = v_halfedges[2*v.length -2];
		v_halfedges[0].previous = v_halfedges[i+2];
		v_halfedges[0].face = inner;

		v_halfedges[2*v.length -2].target = v[v.length -1];          // Special case.
		v_halfedges[2*v.length -2].twin = v_halfedges[2*v.length -1];
		v_halfedges[2*v.length -2].next = v_halfedges[2*v.length -4];
		v_halfedges[2*v.length -2].previous = v_halfedges[0];
		v_halfedges[2*v.length -2].face = inner;


		// odd
		for (var i = 3; i < 2*v.length -1; i=i+2)
		{
			v_halfedges[i].target = v[Math.floor(i/2) +1];
			v_halfedges[i].twin = v_halfedges[i-1];
			v_halfedges[i].next = v_halfedges[i+2];
			v_halfedges[i].previous = v_halfedges[i-2];
			v_halfedges[i].face = outer;
		}

		v_halfedges[1].target = v[1];          // Special case.
		v_halfedges[1].twin = v_halfedges[0];
		v_halfedges[1].next = v_halfedges[3];
		v_halfedges[1].previous = v_halfedges[2*v.length -1];
		v_halfedges[1].face = outer;

		v_halfedges[2*v.length -1].target = v[0];          // Special case.
		v_halfedges[2*v.length -1].twin = v_halfedges[2*v.length -2];
		v_halfedges[2*v.length -1].next = v_halfedges[1];
		v_halfedges[2*v.length -1].previous = v_halfedges[2*v.length -3];
		v_halfedges[2*v.length -1].face = outer;


		// We finally add everything:
		// Vertices.
		for (var i = 0; i < v.length; i++)
			dcel.ver.push(v[i]);

		//Faces.
		dcel.fac.push(outer);
	  	dcel.fac.push(inner);

		// Halfedges.
		for (var i = 0; i < 2*v.length; i++)	
			dcel.hed.push(v_halfedges[i]);

		for (var i = 0; i < 2*v.length; i++)	
			if ( v_halfedges[i].target == null )
				console.log("Empty target in halfedge " + i);

		console.log("DCEL initialized successfully.");

		alert("Now introduce two points to route between. Then press 's' to start seeing the beacons.");
		
		flag_points = 1;
	}

}; 

function keyTyped() {

	if (key === 's') {

		triangulate(v);   // Here we triangulate the polygon.

		console.log("Polygon triangulated successfully.");

		/// Now we search for the faces where the query points are.

		if ( flag_points == 3 )  // If we have introduced TWO points
		{
			p1 = v_points[0];
			p2 = v_points[1];

			if ( v.length == 3 )  // If we have only one face
			{
				face1 = dcel.fac[1];  // Inner face was pushed secondly.
				face2 = dcel.fac[1];
			}
			else {

				for (var i = 0; i < dcel.fac.length; i++)
				{
					var f = dcel.fac[i];
					if ( f.out != 1 ) {
						if ( insideTr(  f.edge.previous.target, f.edge.target, f.edge.next.target, p1 ) <= 0 )
						{
							face1 = f;
							break;
						}
					}
				}

				for (var i = 0; i < dcel.fac.length; i++)
				{
					var f = dcel.fac[i];
					if ( f.out != 1 ) {
						if ( insideTr(  f.edge.previous.target, f.edge.target, f.edge.next.target, p2 ) <= 0 )
						{
							face2 = f;
							break;
						}
					}
				}

			}
		}


		copy_dcel = new DCEL();  // We made a copy to preserve the dcel.

		for (var i = 0; i < dcel.ver.length; i++)  
			copy_dcel.ver[i] = dcel.ver[i];

		for (var i = 0; i < dcel.hed.length; i++)  
			copy_dcel.hed[i] = dcel.hed[i];

		for (var i = 0; i < dcel.fac.length; i++)  
			copy_dcel.fac[i] = dcel.fac[i];

		beacons(copy_dcel);

		console.log("Beacons computed successfully.");

		//Here we arrange the sequence of beacons to route.
		if ( beacons1[ beacons1.length -1] == beacons2[ beacons2.length -1])  // If the boundary beacon is repeated, we will remove it.
		{
			beacons2.pop();
		}
		for (var i = 0; i < beacons2.length; i++)
			beacons1[beacons1.length] = beacons2[beacons2.length -1-i];


		myVar = setTimeout(show_beacons, 800);

	}
};

function show_beacons() {

	fill('red');
	ellipse( beas[index_beacons].x, -beas[index_beacons].y, 7, 7);
	index_beacons++;

	if ( index_beacons == beas.length ) {
		console.log("There are " + beas.length + " beacons.");
		clearTimeout(myVar);

		var r = confirm("Now we will show you the beacons activated to route!");  /// Now we will show the sequence.

		if (r == true) {
			index_beacons = 0;
		    myVar = setTimeout(show_sequence, 600);

		} else {
		    ////// Nothing
		}
	}
	else 
		myVar = setTimeout(show_beacons, 800);

};

function show_sequence() {

	fill('blue');
	ellipse( beacons1[index_beacons].x, -beacons1[index_beacons].y, 7, 7);
/*
	if ( index_beacons == 0)
		line( v_points[0].x, -v_points[0].y, beacons1[0].x, -beacons1[0].y );
	else
		line( beacons1[index_beacons-1].x, -beacons1[index_beacons-1].y, beacons1[index_beacons].x, -beacons1[index_beacons].y );
*/
	index_beacons++;

	if ( index_beacons == beacons1.length ) {

		// line( beacons1[beacons1.length -1].x, -beacons1[beacons1.length -1].y, v_points[1].x, -v_points[1].y );

		console.log("There are " + beacons1.length + " beacons in the sequence (it may not be minimal).");
		clearTimeout(myVar);
	}
	else 
		myVar = setTimeout(show_sequence, 600);

};


function beacons(dcel) {

	if (dcel.fac.length == 2 ) { // Base case (1 triangle and the outer face)

		if ( existence_beacon( dcel.ver[0] ) == 0 )
			beas[beas.length] = dcel.ver[0];  // We place the beacon on any vertex.

		if ( path_found == 0 )  // If we have not found a path yet, this must be the union.
		{
			if ( beacons1.length == 0  || (beacons1.length > 0  &&  beacons1[beacons1.length -1] != beas[beas.length -1]) )  // If it is not repeated.
			{
				beacons1[beacons1.length] = beas[beas.length -1];
				path_found = 1;
			}
		}

	}
	else if (dcel.fac.length == 3 ) // Base case (2 triangles and the outer face)
	{

		// We have to place the beacon in the middle edge to that the two triangles are visible.

		if ( dcel.number_edges(dcel.ver[0]) == 2 ) { // Not in the middle edge point (two incident edges).
			if ( existence_beacon( dcel.ver[0].edge.target ) == 0 )
				beas[beas.length] = dcel.ver[0].edge.target;  // We place the beacon on the next vertex, which will be in the middle edge.
		}
		else {
			if ( existence_beacon( dcel.ver[0] ) == 0 )
				beas[beas.length] = dcel.ver[0];  // We place the beacon on that vertex.
		}

		if ( path_found == 0 )  // If we have not found a path yet, this must be the union.
		{
			if (beacons1.length == 0  || (beacons1.length > 0  &&  beacons1[beacons1.length -1] != beas[beas.length -1]) )  // If it is not repeated.
			{
				beacons1[beacons1.length] = beas[beas.length -1];
				path_found = 1;
			}
		}

	}

	else {  // Main case

		for (var i = 0; i < dcel.ver.length; i++)
		{
			var main_vertex = dcel.ver[i];

			main_vertex = dcel.ver[i];


			if ( dcel.number_edges(main_vertex) == 2 )  // Here we have a leaf, we must check if it is a lowest leaf.
			{
				if ( main_vertex.edge.face.out == 0 )  // We must choose the right halfedge coming from the vertex (the inner one).
					var opposite_edge = main_vertex.edge.next;  // It was the one (main_vertex.edge).
				else
					var opposite_edge = main_vertex.edge.twin.next.next;  // the one was (main_vertex.edge.twin.next).

				opposite_edge = opposite_edge.twin; // Opposite halfedge towards P.

				if ( opposite_edge.next.twin.face.out == 1 )  // Case 1: P' to the left
				{
					// We now know it is a lowest leaf and that we are in case 1.


					update_paths(dcel, opposite_edge.previous.target, opposite_edge.twin.face, opposite_edge.previous.face);


					// We place the beacon, if it is not repeated.
					if ( existence_beacon(opposite_edge.previous.target) == 0 )
						beas[beas.length] = opposite_edge.previous.target;

					//We remove the two triangles from the dcel.
					delete_triangle(dcel, opposite_edge.twin);
					delete_triangle(dcel, opposite_edge.previous);

					//oppsite edge now will be null.

					//We call recursively.
					beacons(dcel);

					break;  // We are done.

				}
				else if ( opposite_edge.previous.twin.face.out == 1 )  // Case 1: P' to the right
				{
					// We now know it is a lowest leaf and that we are in case 1.


					update_paths(dcel, opposite_edge.target, opposite_edge.twin.face, opposite_edge.next.face);


					//We place the beacon, if it is not repeated.
					if ( existence_beacon(opposite_edge.target) == 0 )
						beas[beas.length] = opposite_edge.target;

					//We remove the two triangles from the dcel.
					delete_triangle(dcel, opposite_edge.twin);
					delete_triangle(dcel, opposite_edge.next);

					//We call recursively.
					beacons(dcel);

					break;  // We are done.
				}
				else if ( opposite_edge.next.twin.next.twin.face.out == 1  &&  opposite_edge.next.twin.previous.twin.face.out == 1 ) {
					// We now know it is a lowest leaf and that we are in case 2, with P' to the right.


					update_paths(dcel, opposite_edge.target, opposite_edge.twin.face, opposite_edge.next.twin.face);


					//We place the beacon. This beacon will be unique.
					/////////////////////////////////////////////////////////////////

					beas[beas.length] = opposite_edge.target;

					//We remove two triangles from the dcel.
					delete_triangle(dcel, opposite_edge.twin);
					delete_triangle(dcel, opposite_edge.next.twin);

					//We call recursively.
					beacons(dcel);

					break;  // We are done.
				}
				else if ( opposite_edge.previous.twin.next.twin.face.out == 1  &&  opposite_edge.previous.twin.previous.twin.face.out == 1 ) {
					// We now know it is a lowest leaf and that we are in case 2, with P' to the left.


					update_paths(dcel, opposite_edge.previous.target, opposite_edge.twin.face, opposite_edge.previous.twin.face);


					//We place the beacon. This beacon will be unique.
					/////////////////////////////////////////////////////////////////

					beas[beas.length] = opposite_edge.previous.target;

					//We remove two triangles from the dcel.
					delete_triangle(dcel, opposite_edge.twin);
					delete_triangle(dcel, opposite_edge.previous.twin);

					//We call recursively.
					beacons(dcel);

					break;  // We are done.
				}
				else {
					// We do nothing as it was not a lowest leaf. We will continue searching for one. The checking has been done in constant time.
				}



			}
		}


	}



};

function update_paths(dcel, future_beacon, triangle1, triangle2) {

		//////////////////////// Query points treatment.

		if ( path_found == 0 )
		{
				if ( p1_found == 0 && (face1 == triangle1  ||  face1 == triangle2) )  
				// If p1 is in any of the triangles, we will start saving the beacons on the way to p2.
				{
					beacons1[beacons1.length] = future_beacon;
					p1_found = 1;
				}

				if ( p2_found == 0 && (face2 == triangle1  ||  face2 == triangle2) )  
				// If p2 is in any of the triangles, we will start saving the beacons on the way to p1.
				{
					beacons2[beacons2.length] = future_beacon;
					p2_found = 1;

					/// Check if both points were in the union of these two triangles.
					if ( beacons2[beacons2.length -1] == beacons1[beacons1.length -1] )
					{
						path_found = 1;
					}
				}

				var faces_influenced;
				var faces_influenced_by_other;

				if ( p1_found == 1 )
				{
					// We check if any of the triangles are influenced by the last beacon of our path.
					faces_influenced = dcel.array_faces(beacons1[beacons1.length -1]);

					if ( contains(faces_influenced, triangle1) || contains(faces_influenced, triangle2) )
					/// Here we have found the path.
					{
						if ( beacons1[beacons1.length -1] != future_beacon ) // It may be repeated.
							beacons1[beacons1.length] = future_beacon;

						// Know we must check if our paths have merged!
						if ( p2_found == 1 )
						{
							faces_influenced_by_other = dcel.array_faces(beacons2[beacons2.length -1]);

							if ( contains(faces_influenced_by_other, triangle1) || contains(faces_influenced_by_other, triangle2) )
							/// Here we have found the union.
							{
								path_found = 1;
							}
						}

					}
				}

				if ( p2_found == 1 )
				{
					// We check if any of the triangles are influenced by the last beacon of our path.
					faces_influenced = dcel.array_faces(beacons2[beacons2.length -1]);

					if ( contains(faces_influenced, triangle1) || contains(faces_influenced, triangle2) )
					/// Here we have found the path.
					{
						if ( beacons2[beacons2.length -1] != future_beacon ) // It may be repeated.
							beacons2[beacons2.length] = future_beacon;

						// Know we must check if our paths have merged!
						if ( p1_found == 1 )
						{
							faces_influenced_by_other = dcel.array_faces(beacons1[beacons1.length -1]);

							if ( contains(faces_influenced_by_other, triangle1) || contains(faces_influenced_by_other, triangle2) )
							/// Here we have found the union.
							{
								path_found = 1;
							}
						}

					}
				}

		}
};

function existence_beacon(p) {

	for (var i = 0; i < beas.length; i++)
	{
		if ( beas[i] == p)
			return 1;
	}

	return 0;
};

function delete_triangle(dcel, h) {  // We will delete the triangle still stuck to P with the edge h (inner halfedge).

	var future_h_next = h.next.twin.next;
	var future_h_previous = h.previous.twin.previous;

	dcel.delete_face(h.face);

	dcel.delete_point(h.next.target);

	/*////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	stroke('rgb(100%,100%,100%)');
	line( h.next.next.target.x, -h.next.next.target.y, h.next.next.twin.target.x, -h.next.next.twin.target.y );
	line( h.next.target.x, -h.next.target.y, h.next.twin.target.x, -h.next.twin.target.y );
	stroke('#222222');
	*/

	dcel.delete_halfedge(h.next.next.twin);
	dcel.delete_halfedge(h.next.next);
	dcel.delete_halfedge(h.next.twin);
	dcel.delete_halfedge(h.next);

	// We have to update the border.

	// The two points.
	h.target.edge = h.twin;
	h.twin.target.edge = h;

	// The now outer halfedge (target and twin fields don't change)
	h.next = future_h_next;
	h.previous = future_h_previous;
	h.face = future_h_next.face; // this will be the outer face.

	// The previous and next of h
	h.next.previous = h;
	h.previous.next = h;
};


function triangulate(v) {

	var v2 = [];

	for (var i = 0; i < v.length; i++)  // We made a copy to preserve v.
		v2[i] = v[i];

	while ( v2.length > 3 )
	{

			var index_ear = 0;
			var ear = 0;

			for (var i = 1; i < v2.length -1; i++)  // For each vertex
			{
				if ( side( v2[i-1], v2[i], v2[i+1]) < 0 )  // If convex vertex...
				{
					ear = 1;
					for (var j = 0; j < v2.length; j++)
					{
						if ( insideTr( v2[i-1], v2[i], v2[i+1], v2[j] ) == -1 )  // If there is a point inside...
						{
							ear = 0;
							break;
						}
					}

					if (ear == 1)  // We found the ear...
					{
						index_ear = i;
						break;
					}
				}
			}

			if ( ear == 0 ) {  // If we have not found an ear yet, we consider the special cases (fisrt).

				if ( side( v2[v2.length -1], v2[0], v2[1]) < 0 )  // If convex vertex...
				{
					ear = 1;
					for (var j = 2; j < v2.length -1; j++)
					{
						if ( insideTr( v2[v2.length-1], v2[0], v2[1], v2[j] ) == -1 )  // If there is a point inside...
						{
							ear = 0;
							break;
						}
					}

					if (ear == 1)  // We found the ear...
					{
						index_ear = 0;
					}
				}
			}

			// We need not consider the last case because we know (two ears theorem), that any polygon will have at least 2 ears.

			// Now we have to remove the vertex from v2 and draw the line.

			if ( index_ear != 0) {

				var a = v2[index_ear-1];  //v
				var b = v2[index_ear+1];                            
			}
			else {

				var a = v2[v2.length-1];  //v
				var b = v2[1];
			}

			stroke('rgb(80%,80%,80%)');
			line( a.x, -a.y, b.x, -b.y );     // DRAWING
			stroke('#222222');

			// We add the edge to the DCEL.
			var h = calculateH(b,a);

	 		var f = h.face;
	 		var h1 = new HalfEdge();
	  		var h2 = new HalfEdge();

	 		if (h.face.out == 1) {  //Case outer face
		    	var f1 = new Face(h1, 1);
		  		var f2 = new Face(h2);
		  	}
		  	else {
		  		var f1 = new Face(h1);
		  		var f2 = new Face(h2);
		  	}

		  	h1.twin = h2;
	  		h2.twin = h1;
	  		h1.target = a;
	  		h2.target = b;



			h2.next = h.next;
	 		h2.next.previous = h2;
	  		h1.previous = h;
	  		h.next = h1;

	  		var i = h2;
	  		var end = 0;

	  		while (end == 0) {
	  			i.face = f2;
	  			if ( i.target == a )
	  				end = 1;
	  			else {
	  				i = i.next;
	  			}
	  		}

	  		h1.next = i.next;
	  		h1.next.previous = h1;
	  		i.next = h2;
	  		h2.previous = i;
	  		i = h1;
	  		end = 0;

	  		while (end == 0) {
	  			i.face = f1;
	  			if ( i.target == b )
	  				end = 1;
	  			else
	  				i = i.next;
	  		}

	  
	  		dcel.delete_face(f);
	  		dcel.fac.push(f1);
	  		dcel.fac.push(f2);

	  		dcel.hed.push(h1);
	  		dcel.hed.push(h2);                                 


			v2.splice(index_ear, 1);

	}

};

function contains(a, obj) {
    for (var i = 0; i < a.length; i++) {
        if (a[i] === obj) {
            return true;
        }
    }
    return false;
}


