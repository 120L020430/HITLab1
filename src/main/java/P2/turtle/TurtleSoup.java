/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {

        // throw new RuntimeException("implement me!");
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {

        //throw new RuntimeException("implement me!");
        return (sides - 2) * 180.0 / sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        //throw new RuntimeException("implement me!");
        return (int) Math.round(360 / (180 - angle));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        //throw new RuntimeException("implement me!");
        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(180 - calculateRegularPolygonAngle(sides));
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        //throw new RuntimeException("implement me!");
        if (targetX == currentX && targetY == currentY) return 0;
        double de = Math.atan2(targetY - currentY, targetX - currentX) / Math.PI * 180;
        de = 90 - currentBearing - de;
        if (de < 0)
            de += 360;
        return de;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        //throw new RuntimeException("implement me!");
        List<Double> result = new ArrayList<>();
        double t = 0;
        for (int i = 1; i < xCoords.size(); i++) {
            t = calculateBearingToPoint(t, xCoords.get(i - 1), yCoords.get(i - 1),
                    xCoords.get(i), yCoords.get(i));
            result.add(t);
        }
        return result;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        //throw new RuntimeException("implement me!");
        if (points.size() < 3)//The number is less than three directly returned
            return points;
        Point[] spots = points.toArray(new Point[points.size()]);
        int temp = 0; //Saves the current bottom-right point
        for (int i = 1; i < spots.length; i++) {
            temp = spots[i].x() < spots[temp].x() ? i : temp;
            if (spots[i].x() == spots[temp].x()) {
                temp = spots[i].y() < spots[temp].y() ? i : temp;
            }
        }
        int bpoint = temp;
        Set<Point> result = new HashSet<Point>();
        result.add(spots[temp]);
        double mindegree = 0, mindegree1 = 360;
        int npoint = temp == 0 ? 1 : 0;
        while (bpoint != npoint) {
            for (int i = 0; i < spots.length; i++) {
                if (temp != i) {
                    double temp1 = calculateBearingToPoint(mindegree, (int)spots[temp].x(), (int)spots[temp].y(),
                            (int)spots[i].x(), (int)spots[i].y());
                    if (temp1 < mindegree1) {
                        npoint = i;
                        mindegree1 = temp1;
                    }
                }
            }
            mindegree += mindegree1;
            if (mindegree > 360) mindegree -= 360;
            result.add(spots[npoint]);
            temp = npoint;
            mindegree1 = 360;
        }
        return result;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {

        //throw new RuntimeException("implement me!");
        PenColor[] colors = new PenColor[6];
        colors[0] = PenColor.RED;
        colors[1] = PenColor.ORANGE;
        colors[2] = PenColor.YELLOW;
        colors[3] = PenColor.GREEN;
        colors[4] = PenColor.BLUE;
        colors[5] = PenColor.MAGENTA;
        for (int i = 1; i <= 30; i++) {
            for (int j = 0; j < 6; j++) {
                turtle.color(colors[j]);
                drawRegularPolygon(turtle, 3, i * 8);
                turtle.turn(60);
            }
        }
        for (int i = 30; i > 0; i--) {
            for (int j = 0; j < 6; j++) {
                turtle.color(colors[j]);
                drawRegularPolygon(turtle, 6, i * 4);
                turtle.turn(60);
            }
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawSquare(turtle, 40);
        turtle.draw();

        DrawableTurtle turtle1 = new DrawableTurtle();
        drawRegularPolygon(turtle1, 6, 40);
        turtle1.draw();

        DrawableTurtle turtle2 = new DrawableTurtle();
        drawPersonalArt(turtle2);
        // draw the window
        turtle2.draw();
    }

}
