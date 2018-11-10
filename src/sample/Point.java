package sample;

import java.io.Serializable;

public class Point implements Serializable {
    private int x;
    private int y;

    //Constructor
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Getter for x
    public int getX() {
        return x;
    }

    //Getter for y
    public int getY() {
        return y;
    }

    //Changes hlocation according to the velocity.
    public Point translate(int xvel,int yvel){
        if(xvel == 1){
            x += 10;
            wrap();
        }
        if(xvel == -1){
            x -= 10;
            wrap();
        }
        if(yvel == 1){
            y += 10;
            wrap();
        }
        if(yvel == -1){
            y -= 10;
            wrap();
        }

        return this;
    }

    // Ensures new hlocation is within grid.
    public void wrap(){
        if(this.getX() > 500) this.setX(0);
        if(this.getX() < 0) this.setX(500);
        if(this.getY() > 1000) this.setY(0);
        if(this.getY() < 0) this.setY(1000);
    }

    //Setter for x
    public void setX(int x) {
        this.x = x;
    }

    //Setter for y
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

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

