package com.company;

import java.util.ArrayList;

import static java.lang.Math.*;

public class Vector2d {

    private double x;
    private double y;

    public Vector2d () {}
    public Vector2d (double x, double y)
    {
        Set(x,y);
    }

    public double GetX() {return this.x;}
    public double GetY() {return this.y;}
    public void Set(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public static Vector2d Centroid(ArrayList<Vector2d> list)
    {
        Vector2d centroid = new Vector2d();
        int size = list.size();
        double xcomp = 0;
        double ycomp = 0;

        for (Vector2d point : list) {
            xcomp += point.GetX();
            ycomp += point.GetY();
        }

        xcomp /= size;
        ycomp /= size;

        centroid.Set(xcomp,ycomp);
        return centroid;
    }

    public double Dot(Vector2d vec)
    {
        return (this.x * vec.x + this.y * vec.y);
    }

    public double Angle()
    {
        double angle = acos(this.x / this.Magnitude());
        if(this.y < 0) angle = 2 * PI - angle;
        if(Double.isNaN(angle)) angle = 0;
        return angle;
    }

    public double Angle(Vector2d vec)
    {
        double dot = this.Dot(vec);
        double angle = acos(dot / (this.Magnitude() * vec.Magnitude()));
        if(Double.isNaN(angle)) angle = 0;
        return angle;
    }

    public double Magnitude()
    {
        return sqrt(pow(this.x,2) + pow(this.y,2));
    }

    @Override
    public String toString()
    {
        return "X: " + String.format("%.2f",this.x) + " Y: " + String.format("%.2f",this.y);
    }

    public void Normalize()
    {
        double length = Magnitude();
        x /= length;
        y /= length;
    }

    public void Move(Vector2d vec)
    {
        this.x += vec.x;
        this.y += vec.y;
    }

    public void Rotate(double angle)
    {
        double curAngle = this.Angle();
        double length = this.Magnitude();
        curAngle += angle;
        this.Set(length * cos(curAngle), length * sin(curAngle));
    }

    @Override
    public Vector2d clone() {
        return new Vector2d(this.x, this.y);
    }

    public Vector2d Refract()
    {
        return new Vector2d(-this.x, -this.y);
    }

    public Vector2d Add(Vector2d vec)
    {
        return new Vector2d(this.x + vec.x, this.y + vec.y);
    }
}
