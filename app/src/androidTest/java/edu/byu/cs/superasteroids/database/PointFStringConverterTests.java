package edu.byu.cs.superasteroids.database;

import android.graphics.Point;
import android.graphics.PointF;
import android.test.AndroidTestCase;

/**
 * Created by Tyler on 7/16/2016.
 */
public class PointFStringConverterTests extends AndroidTestCase {

    /**
     * Tests to see if the String to PointF function works properly
     */
    public void testStringToPointF_1digit()
    {
        String string = "2,4";
        PointF stringP = PointFStringConverter.stringToPointF(string);
        PointF answer = new PointF(2, 4);
        assertEquals(stringP, answer);
    }

    public void testStringToPointF_2digit()
    {
        String string = "22,43";
        PointF stringP = PointFStringConverter.stringToPointF(string);
        PointF answer = new PointF(22, 43);
        assertEquals(stringP, answer);
    }

    public void testPointFToString()
    {
        PointF point = new PointF(2, 4);
        String pointFString = PointFStringConverter.pointfToString(point);
        String answer = "2,4";
        assertEquals(pointFString, answer);
    }
}
