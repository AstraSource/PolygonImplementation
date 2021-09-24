package com.company;

import java.util.ArrayList;

public class Rectangle extends Triangle {

    protected String name = "Rectangle";

    public Rectangle(Vector2d p1, Vector2d p2, Vector2d p3, Vector2d p4) throws Exception {
        points = new ArrayList<>();

        Construct(p1, p2, p3, p4);
    }

    public void Construct (Vector2d p1, Vector2d p2, Vector2d p3, Vector2d p4) throws Exception {
        ArrayList<Vector2d> list = new ArrayList<>();
        list.add(p1); list.add(p2); list.add(p3); list.add(p4);
        ConstructFromArrayList(list);
    }
}
