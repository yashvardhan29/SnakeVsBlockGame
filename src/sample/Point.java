package sample;

import java.io.Serializable;

public class Point implements Serializable {
    /**
     * x co-ordinate of point.
     */
    private double x;
    /**
     * y co-ordinate of point.
     */
    private double y;

    /**
     * Constructor for class.
     * @param x x co-ordinate of point.
     * @param y y co-ordinate of point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x.
     * @return x co-ordinate of point.
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for y.
     * @return y co-ordinate of point.
     */
    public double getY() {
        return y;
    }

    /**
     * Translates points according to velocity.
     * @param xvel Velocity in x direction.
     * @param yvel Velocity in y direction.
     * @param offset Used for increasing difficulty.
     * @return
     */
    public Point translate(double xvel,double yvel,int offset){
        if(xvel == 1){
            x += 2;
            wrap();
        }
        if(xvel == -1){
            x -= 2;
            wrap();
        }
        if(yvel == 1){
            y += 2 + offset;
            if(y >= 0 ) wrap();
        }
        if(yvel == -1){
            y -= 2 + offset;
            wrap();
        }

        return this;
    }

    /**
     * Ensures snake can't go out of grid.
     */
    public void wrap(){
        if(this.getX() > 500) this.setX(x-2);
        if(this.getX() < 0) this.setX(x+2);
        if(this.getY() > 1000) this.setY(y-2);
        if(this.getY() < 0) this.setY(y+2);
    }

    /**
     * Setter for x.
     * @param x x co-ordinate to be set.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Setter for y.
     * @param y y co-ordinate to be set.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * To string method of class.
     * @return Both co-ordinates of point.
     */
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Tests for equality between points.
     * @param o Point against which equality test is to be performed.
     * @return
     */
    @Override
    public boolean equals(Object o){
        if(o != null){
            Point p = (Point) o;
            if(p.getX() == this.getX() && p.getY() == this.getY()) return true;
            else return false;
        }
        else return false;
    }

}

