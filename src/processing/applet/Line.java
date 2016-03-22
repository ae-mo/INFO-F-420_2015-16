

public class Line
{
	private final Point _start;
	private final Point _end;
	private double _a = Double.NaN;
	private double _b = Double.NaN;
	private boolean _vertical = false;

	public Line(Point start, Point end)
	{
		_start = start;
		_end = end;

		if (_end.x - _start.x != 0)
		{
			_a = ((_end.y - _start.y) / (_end.x - _start.x));
			_b = _start.y - _a * _start.x;
		}

		else
		{
			_vertical = true;
		}
	}

	/**
	 * Indicate whereas the point lays on the line.
	 * 
	 * @param point
	 *            - The point to check
	 * @return <code>True</code> if the point lays on the line, otherwise return <code>False</code>
	 */
	public boolean isInside(Point point)
	{
		Turn t = new Turn(this._start, this._end, point);

		if (t.value == 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * Indicate whereas the line is vertical. <br>
	 * For example, line like x=1 is vertical, in other words parallel to axis Y. <br>
	 * In this case the A is (+/-)infinite.
	 * 
	 * @return <code>True</code> if the line is vertical, otherwise return <code>False</code>
	 */
	public boolean isVertical()
	{
		return _vertical;
	}

	/**
	 * y = <b>A</b>x + B
	 * 
	 * @return The <b>A</b>
	 */
	public double getA()
	{
		return _a;
	}

	/**
	 * y = Ax + <b>B</b>
	 * 
	 * @return The <b>B</b>
	 */
	public double getB()
	{
		return _b;
	}

	/**
	 * Get start point
	 * 
	 * @return The start point
	 */
	public Point getStart()
	{
		return _start;
	}

	/**
	 * Get end point
	 * 
	 * @return The end point
	 */
	public Point getEnd()
	{
		return _end;
	}
	
	public Point computeLineLineIntersection(double x1,  double y1,  double x2,  double y2,  double x3,  double y3,  double x4,  double y4) {

		double den, xNum, yNum;
		double x, y;

		den = (x1 -x2)*(y3 - y4) - (y1 - y2)*(x3 - x4);

		if(den == 0) return null;

		xNum = (x1*y2-y1*x2)*(x3 - x4) - (x1-x2)*(x3*y4-y3*x4);
		yNum = (x1*y2-y1*x2)*(y3 - y4) - (y1-y2)*(x3*y4-y3*x4);

		x = xNum / den;
		y = yNum / den;

		return new Point(x, y, null);

	}

	@Override
	public String toString()
	{
		return String.format("%s-%s", _start.toString(), _end.toString());
	}
}