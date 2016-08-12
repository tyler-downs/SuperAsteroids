package edu.byu.cs.superasteroids.database;

import android.graphics.PointF;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Tyler on 7/16/2016.
 */
public class PointFStringConverter {
    //Used frequently in getting data to/from the database into model objects

    /**
     * Converts a point if to a string with format "digit,digit"
     * @param point the point
     * @return the string representation
     */
    public static String pointfToString(PointF point)
    {
        return String.valueOf((int) point.x) + "," + String.valueOf((int) point.y);
    }

    /**
     * Converts a string of format "digit,digit" to PointF
     * @param input the input string
     * @return PointF representation of the values in the string
     */
    public static PointF stringToPointF(String input)
    {
        Scanner scanner = new Scanner(input);
        scanner.useDelimiter(",");
        float x = scanner.nextFloat();
        float y = scanner.nextFloat();
        return new PointF(x, y);

    }
}
