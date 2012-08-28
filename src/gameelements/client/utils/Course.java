package gameelements.client.utils;

import gameelements.GameElement;

public class Course {
	public static int HELM_OFFSET = 270;
	public static double UPDATE = 1000;
    private int speed;
    private int helm;
    private int plane;
    private int maxSpeed;
    
    public Course(int max)
    {
    	speed = 0;
    	helm = HELM_OFFSET;
    	plane = 0;
    	maxSpeed = max;
    }

    public void setSpeed(int s){
    	if (s >= 0 && s <= maxSpeed)
    		speed = s;
    }

    public int getSpeed(){
        return speed;
    }
    
    public void incrementSpeed(int i)
    {
    	this.setSpeed(this.getSpeed()+i);
    }

    public void setHelm(int d){
    	if (helm < 0)
    		helm = 360 + d%360;
    	else
    		helm = d%360;
    }

    public int getHelm(){
        return helm;
    }
    
    public int getHelmPrint() {
        return helm;
    }
    
    public void incrementHelm(int i) {
    	this.setHelm(this.getHelm()+i);
    }

    public void setPlane(int p){
    	if (p > -90 && p < 90)
    		plane = p;
    }

    public int getPlane(){
        return plane;
    }
    
    public void incrementPlane(int i) {
    	this.setPlane(this.getPlane()+i);
    }

    public Course clone()
    {
    	Course course = new Course(maxSpeed);
    	course.helm = this.helm;
    	course.speed = this.speed;
    	course.plane = this.plane;
    	return course;
    }
    
    public void affectCourse(GameElement element, double timeSinceLast)
    {
    	double a = timeSinceLast/UPDATE;
    	double dy = ((getSpeed()*a)*Math.sin(Math.toRadians((double)getHelm())));
    	double dx = Math.sqrt(Math.pow((getSpeed()*a), 2)-Math.pow(dy,2));
		double dz = ((getSpeed()*a)*Math.sin(Math.toRadians((double)getPlane())));
		if (getHelm() > 90 && getHelm() < 270)
			dx = -dx;
		element.getLocation().affectLocation(dx, dy);
		element.setDepth(element.getDepth()+dz);
    }
}
