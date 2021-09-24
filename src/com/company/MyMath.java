package com.company;

public class MyMath {
    private static final double ESP = 0.0001;
    public static double GetEsp() {return ESP;}

    public static int Ring(int start, int end, int current, int increment)
    {
        current += increment;
        while(current < start) current += (end - start) + 1;
        while(current > end) current += start - (end + 1);
        return current;
    }
}
