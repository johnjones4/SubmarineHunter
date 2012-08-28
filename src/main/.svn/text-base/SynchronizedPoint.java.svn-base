package main;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

public class SynchronizedPoint implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private AtomicReference<Double> x;
	private AtomicReference<Double> y;
	
	public SynchronizedPoint(double x,double y)
	{
		this.x = new AtomicReference<Double>(new Double(x));
		this.y = new AtomicReference<Double>(new Double(y));
	}

	public double getX() {
		return x.get().doubleValue();
	}

	public double getY() {
		return y.get().doubleValue();
	}

	public void setLocation(double x, double y) {
		this.x.set(x);
		this.y.set(y);
	}

	public void affectLocation(double dx, double dy) {
		this.x.set(this.x.get() + dx);
		this.y.set(this.y.get() + dy);
	}
	
	public Object clone()
	{
		return new SynchronizedPoint(this.getX(),this.getY());
	}
	
	public String toString() {
		return "Point: (" + this.getX() + "," + this.getY() + ")"; 
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof SynchronizedPoint)
			return ((SynchronizedPoint)o).getX() == this.getX()
				&& ((SynchronizedPoint)o).getY() == this.getY();
		else
			return false;
	}
}
