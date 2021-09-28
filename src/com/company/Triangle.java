package com.company;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static java.lang.Math.*;

public class Triangle {

    protected List<Vector2d> points;
    protected String name = "Triangle";

    public Triangle()
    {
        points = new ArrayList<>();
    }
    public Triangle(Vector2d p1, Vector2d p2, Vector2d p3) throws Exception {
        points = new ArrayList<>();
        Construct(p1, p2, p3);
    }

    public void Construct (Vector2d p1, Vector2d p2, Vector2d p3) throws Exception
    {
        ArrayList<Vector2d> list = new ArrayList<>();
        list.add(p1); list.add(p2); list.add(p3);
        ConstructFromArrayList(list);
    }

    protected void ConstructFromArrayList(ArrayList<Vector2d> list) throws Exception
    {
        double esp = MyMath.GetEsp();
        double angleSumTheory = Math.PI * (list.size() - 2);
        Vector2d centroid = Vector2d.Centroid(list);

        ArrayList<Double> centroidAngles = new ArrayList<>();
        ArrayList<Integer> indexer = new ArrayList<>();
        for(int i = 0; i < list.size(); i++)
        {
            indexer.add(i);
            centroidAngles.add(list.get(i).Add(centroid.Refract()).Angle());
        }
        indexer.sort(new Comparator<Integer>() {
            @Override
            public int compare(final Integer o1, final Integer o2) {
                return Double.compare(centroidAngles.get(o1), centroidAngles.get(o2));
            }
        });
        ArrayList<Vector2d> adjusted = new ArrayList<>();
        for(Integer index : indexer)
        {
            adjusted.add(list.get(index).clone());
        }

        ArrayList<Vector2d> sides = new ArrayList<>();
        int size = adjusted.size();
        for(int i = 0; i < size; i++)
        {
            int next = MyMath.Ring(0,size - 1,i,1);
            sides.add(adjusted.get(next).Add(adjusted.get(i).Refract()));
        }

        ArrayList<Double> angles = new ArrayList<>();
        double angleSum = 0;
        for(int i = 0; i < size; i++)
        {
            Vector2d a1 = sides.get(i);
            Vector2d a2 = sides.get(MyMath.Ring(0,size - 1,i,-1)).Refract();
            angles.add(a1.Angle(a2));
            angleSum += angles.get(i);
        }

        Exception ex = new Exception("Incorrect " + name);
        if(Double.isNaN(angleSum) || abs(angleSumTheory - angleSum) > esp)
            throw ex;

        for(Double angleComp : angles)
        {
            if(abs(angleSumTheory - angleComp) < esp) throw ex;
        }

        points.clear();
        points.addAll(adjusted);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int size = points.size();

        for(int i = 0; i < size; i++)
        {
            sb.append((i+1));
            sb.append(": ");
            sb.append(points.get(i));
            if(i + 1 == size) continue;
            sb.append("\n");
        }
        return sb.toString();
    }

    public void Move(Vector2d direction)
    {
        for (Vector2d point : points)
        {
            point.Move(direction);
        }
    }
    
    public void Resize(Vector2d pivot, double factor)
    {
        for (Vector2d point : points)
        {
            Vector2d vec = point.Add(pivot.Refract());
            point.Set(vec.GetX() * factor + pivot.GetX(), vec.GetY() * factor + pivot.GetY());
        }
    }
    
    public void Rotate(Vector2d pivot, double angle)
    {
        for (Vector2d point : points) {
            Vector2d rotationVec = point.Add(pivot.Refract());
            rotationVec.Rotate(angle);
            Vector2d composite = pivot.Add(rotationVec);
            point.Set(composite.GetX(), composite.GetY());
        }
    }
}
