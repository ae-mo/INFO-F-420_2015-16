var dcel = new DCEL();
var lastIndex = -1;
var flag = 0;
var arr = [];

function setup() {
  // uncomment this line to make the canvas the full size of the window
  createCanvas(1000, 1000);

  var e1 = new HalfEdge();
  var e2 = new HalfEdge();
  var e3 = new HalfEdge();
  var e4 = new HalfEdge();
  var e5 = new HalfEdge();
  var e6 = new HalfEdge();
  var e7 = new HalfEdge();
  var e8 = new HalfEdge();

  var f1 = new Face (e1, 1);  // outer face
  var f2 = new Face (e2);

  var p1 = new Point(500, -200, e1);   // Starting with an square and two faces to avoid problems.
  var p2 = new Point(600, -200, e3);
  var p3 = new Point(600, -300, e5);
  var p4 = new Point(500, -300, e7);

  e1.target = p2;
  e1.twin = e2;
  e1.next = e3;
  e1.previous = e7;
  e1.face = f1;

  e2.target = p1;
  e2.twin = e1;
  e2.next = e8;
  e2.previous = e4;
  e2.face = f2;

  e3.target = p3;
  e3.twin = e4;
  e3.next = e5;
  e3.previous = e1;
  e3.face = f1;

  e4.target = p2;
  e4.twin = e3;
  e4.next = e2;
  e4.previous = e6;
  e4.face = f2;

  e5.target = p4;
  e5.twin = e6;
  e5.next = e7;
  e5.previous = e3;
  e5.face = f1;

  e6.target = p3;
  e6.twin = e5;
  e6.next = e4;
  e6.previous = e8;
  e6.face = f2;

  e7.target = p1;
  e7.twin = e8;
  e7.next = e1;
  e7.previous = e5;
  e7.face = f1;

  e8.target = p4;
  e8.twin = e7;
  e8.next = e6;
  e8.previous = e2;
  e8.face = f2;

  dcel.ver.push(p1);
  dcel.ver.push(p2);
  dcel.ver.push(p3);
  dcel.ver.push(p4);

  dcel.hed.push(e1);
  dcel.hed.push(e2);
  dcel.hed.push(e3);
  dcel.hed.push(e4);
  dcel.hed.push(e5);
  dcel.hed.push(e6);
  dcel.hed.push(e7);
  dcel.hed.push(e8);


  dcel.fac.push(f1);
  dcel.fac.push(f2);

  ellipse(p1.x, -p1.y, 6, 6);    // DRAWING
  line( p1.x, -p1.y, p2.x, -p2.y );
  ellipse(p2.x, -p2.y, 6, 6);    
  line( p2.x, -p2.y, p3.x, -p3.y );
  ellipse(p3.x, -p3.y, 6, 6);    
  line( p3.x, -p3.y, p4.x, -p4.y );
  ellipse(p4.x, -p4.y, 6, 6);    
  line( p4.x, -p4.y, p1.x, -p1.y );
}

function draw() {
  // draw stuff here

}

function mousePressed() {   // First point clicked MUST BE one vertex of the square, if not the graph could be not connected.

	if (flag==0)   // MODE DRAWING GRAPH
	{
		var v = new Point( mouseX, -mouseY);
	    
	    var pack = checkExistence(v);   // We check if the point is new or not.

	    if( pack.found == 1 )   // The clicked point was already in the list (add edge between exiting points, we suppose the edge does not intersect anothers).
	    {
	    	v = dcel.ver[pack.i];
	  		var u = dcel.ver[lastIndex];

	  		if ( existingEdge(u,v) == 0 && lastIndex != -1 ) // If the edge selected does not exist yet...
	  		{
	  			var h = calculateH(u,v);

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
		  		h1.target = v;
		  		h2.target = u;



				h2.next = h.next;
		 		h2.next.previous = h2;
		  		h1.previous = h;
		  		h.next = h1;

		  		var i = h2;
		  		var end = 0;

		  		while (end == 0) {
		  			i.face = f2;
		  			if ( i.target == v )
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
		  			if ( i.target == u )
		  				end = 1;
		  			else
		  				i = i.next;
		  		}

		  
		  		dcel.delete_face(f);
		  		dcel.fac.push(f1);
		  		dcel.fac.push(f2);

		  		dcel.hed.push(h1);
		  		dcel.hed.push(h2);                                 


	    		line( v.x, -v.y, u.x, -u.y );     // DRAWING
	    	}

	    	lastIndex = pack.i; 

	    }
	  	else {   // The clicked point is new (add edge to the emptiness).

	  		
		  		var u = dcel.ver[lastIndex];

		  		var h = calculateH(u,v);

				var h1 = new HalfEdge();
		  		var h2 = new HalfEdge();
		  		v.edge = h2;
		  		h1.twin = h2;
		  		h2.twin = h1;
		  		h1.target = v;
		  		h2.target = u;
		  		h1.face = h.face;
		  		h2.face = h.face;
		  		h1.next = h2;
		  		h2.next = h.next;
		  		h1.previous = h;
		  		h2.previous = h1;
		 		h.next = h1;
		  		h2.next.previous = h2;	  

		  		dcel.hed.push(h1);
		  		dcel.hed.push(h2);                                                           

		  		dcel.ver.push(v);

		  		ellipse(v.x, -v.y, 6, 6);    // DRAWING
	  			line( v.x, -v.y, u.x, -u.y );
		  	
	/*	  	else {  // First point
		  		dcel.ver.push(v);

		  		ellipse(v.x, -v.y, 6, 6);    // DRAWING
		  	}
	*/
		  		lastIndex = dcel.ver.length -1;

	  	}
	 }


	 else {   // MODE INSIDE QUERY

	 	var found = 0;   // We will just look at the points of each face we stored earlier.
	 	var i=0;
	 	var p = new Point( mouseX, -mouseY);
	 	var v;
	 	var indexOut = 0;

	 	 while ( i<dcel.fac.length && found == 0)  {

	 	 	var face = dcel.fac[i];
			if ( face.out != 1 ) {     // If it is NOT the outer face...
			 	 	v = arr[i];
			 	 	if ( insideOutside(v, p) == 1 )  // We check if it is inside
			 	 		found = 1;
			 	 	else
			 	 		i = i+1;
			}
			else {
				indexOut = i;
				i = i+1;
			}
	 	 }

	 	 if ( found == 0)  // Outer point
	 	 	v = arr[indexOut];

	 	 for (var j=0; j<=v.length-1 ;j++) {  // We color the points of the face with red.
	 	 	fill('red');
	 	 	ellipse( v[j].x, -v[j].y, 6, 6);
	 	 }
	 }

  return false;
};

function Point (x,y, e)
{
	this.x = x;
	this.y = y;
	if (e===undefined)
		this.edge = null;
	else
		this.edge = e;
};
Point.prototype.getInfo = function() {
	return this.x + ' ' + this.y;
};


function Face (e, out)
{
	if (e===undefined)
		this.edge = null;
	else
		this.edge = e;

	if (out===undefined)
		this.out = 0;
	else
		this.out = out;
};

function HalfEdge (p, e1, e2, e3, f)
{
	if (p===undefined)
		this.target = null;
	else
		this.target = p;
	if (e1===undefined)
		this.twin = null;
	else
		this.twin = e1;
	if (e2===undefined)
		this.next = null;
	else
		this.next = e2;
	if (e3===undefined)
		this.previous = null;
	else
		this.previous = e3;
	if (f===undefined)
		this.face = null;
	else
		this.face = f;
};

function DCEL ()
{
	this.ver = [];
	this.hed = [];
	this.fac = [];
};
DCEL.prototype.number_edges = function(p) {  // We get the number of edges leaving the point.
	var n = 1;

	var first = p.edge;
	var current = first.twin.next;

	while (current != first)
	{
		n++;
		current = current.twin.next;
	}

	return n;
};
DCEL.prototype.array_faces = function(p) {  // We get an array of faces toucing the point (vertex).
	var array = [];

	var first = p.edge;
	array.push(first.face);

	var current = first.twin.next;

	while (current != first)
	{
		array.push(current.face);
		current = current.twin.next;
	}

	return array;
};
DCEL.prototype.delete_point = function(p) { 

	var pos = 0;
	for (var i = 0; i < this.ver.length; i++) 
	{
		if ( this.ver[i] == p ) {
			pos = i;
			break;
		}
	}

	this.ver.splice(pos, 1);
}

DCEL.prototype.delete_halfedge = function(h) { 

	var pos = 0;
	for (var i = 0; i < this.hed.length; i++) 
	{
		if ( this.hed[i] == h ) {
			pos = i;
			break;
		}
	}

	this.hed.splice(pos, 1);
}

DCEL.prototype.delete_face = function(f) { 

	var pos = 0;
	for (var i = 0; i < this.fac.length; i++) 
	{
		if ( this.fac[i] == f ) {
			pos = i;
			break;
		}
	}

	this.fac.splice(pos, 1);
}

function Pack (i, f)
{
	this.i = i;
	this.found = f;
};
function checkExistence(p)   // If we have clicked in an existing point.
{
	var found = 0;
	var i = 0;

	while (i <= dcel.ver.length-1 && found==0 )
	{
		if( (p.x - dcel.ver[i].x)*(p.x - dcel.ver[i].x) + (p.y - dcel.ver[i].y)*(p.y - dcel.ver[i].y) <= 7*7 )    // Euclidean distance so we click in the little circle representing the point.
			found = 1;
		else
			i = i+1;
	}

	var pack = new Pack(i,found);
	return pack;
}; 


function existingEdge( p1, p2)   // If an edge between two points already exists.
{
	var found = 0;
	var i = 0;

	while (i <= dcel.hed.length-1 && found==0 )
	{
		if ( dcel.hed[i].target == p1 && dcel.hed[i].twin.target == p2)
			found = 1;
		else
			i = i+1;
	}

	return found;
};

function calculateH (u,v)
{
	var a = u.edge;
	var b = a.twin.next;
	var found = 0;
	var h;

	while ( b != u.edge  &&  found == 0)   // We will finish with this loop if 'a' is to one side of the u-v line and 'b' to the other.
	{
		if ( side(u,v,b.target) <0  &&  side(u,v,a.target) >0 )
			found = 1;
		else {
			a = b;
			b = a.twin.next;
		} 
	}

	if (  ( found == 0 && side(u,v,b.target) <0  &&  side(u,v,a.target) >0 ) ||  a==b )   // Last case of the ieration (includes a=b)
			found = 1;

	if ( found == 1 )   // General case.
		h = a.twin;

	else {              // The two edges (h and h.next) are in the same side of the line u-v.

		a = u.edge;
	    b = a.twin.next;
		found = 0;
		var x1, x2, y1, y2;

		while ( b != u.edge  &&  found == 0)
		{
			x1 = a.target.x - a.twin.target.x;
			y1 = a.target.y - a.twin.target.y;
			x2 = b.target.x - b.twin.target.x;
			y2 = b.target.y - b.twin.target.y;  

			if ( more180( x1, y1, x2, y2 ) == 1)  // Angle between a and b  in clockwise order bigger than 180ª.
				found = 1;
			else {
				a = b;
				b = a.twin.next;
			}
		}

		if (found == 0) {
			x1 = a.target.x - a.twin.target.x; 
			y1 = a.target.y - a.twin.target.y;
			x2 = b.target.x - b.twin.target.x;
			y2 = b.target.y - b.twin.target.y;      

			if ( more180( x1, y1, x2, y2 ) == 1)  // Angle between a and b  in clockwise order bigger than 180ª.
				found = 1;
		}

		if ( found == 1 )
			h = a.twin;
		else
			h = null;  // Theoretically unreachable.
	}

	console.log(2);
	return h;
}


function more180( x1, y1, x2, y2 )  // We suppose a (x1,y1) to be the the first vector.
{

	if ( side ( new Point(0,0), new Point(x1,y1), new Point(x2,y2) ) > 0 )
		return 1;
	else
		return 0;
	
}


function side ( a, b, c )  // Orientation determinant.
{
	return b.x*c.y - a.x*c.y + a.x*b.y - c.x*b.y + c.x*a.y - b.x*a.y;
};


