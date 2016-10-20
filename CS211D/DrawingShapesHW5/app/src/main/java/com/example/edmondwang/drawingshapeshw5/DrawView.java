package com.example.edmondwang.drawingshapeshw5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Edmond Wang on 10/18/2016.
 */
public class DrawView extends View
{
    int width;
    int height;
    int selection;
    Paint p;
    Random rand;
    ArrayList<Circle> circleArrayList;
    ArrayList<Triangle> triangleArrayList;
    ArrayList<Rectangle> rectangleArrayList;

    public DrawView(Context con, AttributeSet attr)
    {
        super(con, attr);
        p = new Paint();
        p.setStyle(Paint.Style.FILL);
//        WindowManager wm = (WindowManager) con.getSystemService
//                (Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        Point point = new Point();
//        display.getSize(point);
//        width = point.x;
//        height = point.y;
        rand = new Random();
        selection = 0;
    }

    //**********************onWindowFocusChanged()****************
    //Used to set the height and width of the view after it has
    // been created
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        // the height will be set at this point
        height = getMeasuredHeight();
        width = getMeasuredWidth();
    }

    //*********************setSelection()*************************
    public void setSelection(int s)
    {
        selection = s;
        switch (selection)
        {
            case 1:
                circleArrayList = new ArrayList<>();
                break;
            case 2:
                triangleArrayList = new ArrayList<>();
                break;
            case 3:
                rectangleArrayList = new ArrayList<>();
                break;
            default:
                break;
        }
    }
    //**********************clearScreen()*************************
    public void clearScreen()
    {
        switch(selection)
        {
            case 1:
                circleArrayList.clear();
                break;
            case 2:
                triangleArrayList.clear();
                break;
            case 3:
                rectangleArrayList.clear();
                break;
            default:
                break;
        }
        invalidate();
    }

    //GENERATE FUNCTIONS
    //*********************generateCircle()***********************
    public void generateCircle()
    {
        Circle aCircle = new Circle(width, height, rand);
        circleArrayList.add(aCircle);
    }
    //*********************generateTriangle()*********************
    public void generateTriangle()
    {
        Triangle tri = new Triangle(width, height, rand);
        triangleArrayList.add(tri);
    }
    //********************generateRectangle()*********************
    public void generateRectangle()
    {
        Rectangle rect = new Rectangle(width, height, rand);
        rectangleArrayList.add(rect);
    }

    //DRAW FUNCTIONS
    //********************onDraw()********************************
    @Override
    public void onDraw(Canvas c)
    {
        super.onDraw(c);

        switch(selection)
        {
            case 1:
                drawCircles(c);
                break;
            case 2:
                drawTriangles(c);
                break;
            case 3:
                drawRectangles(c);
                break;
            default:
                break;
        }
        invalidate();
    }
    //********************drawCircles()***************************
    private void drawCircles(Canvas c)
    {
        for (Circle aCircle:circleArrayList)
        {
            p.setColor(aCircle.color);
            c.drawCircle(aCircle.x, aCircle.y, aCircle.radius, p);
        }
    }
    //******************drawTriangles()***************************
    private void drawTriangles(Canvas c)
    {
        for (Triangle tri:triangleArrayList)
        {
            p.setColor(tri.color);
            Path path = new Path();
            path.moveTo(tri.x1,tri.y1);
            path.lineTo(tri.x2, tri.y2);
            path.lineTo(tri.x3,tri.y3);
            path.lineTo(tri.x1,tri.y1);
            c.drawPath(path, p);
        }
    }
    //*****************drawRectangles()***************************
    private void drawRectangles(Canvas c)
    {
        for(Rectangle rect:rectangleArrayList)
        {
            p.setColor(rect.color);
            c.drawRect(rect.x1,rect.y1,rect.x2,rect.y2, p);
        }
    }

    //SHAPE CLASSES
    //**************************Circle Class**********************
    //Class for each circle being drawn
    private class Circle
    {
        int x, y, radius, color;
        public Circle(int w, int h, Random rand)
        {
            color = Color.rgb(rand.nextInt(256), rand
                    .nextInt(256), rand.nextInt(256));
            //set max to w since width is usually the smaller of
            // the 2
            radius = rand.nextInt(w/2);
            //between radius and w - radius
            x = rand.nextInt(w-2*radius ) + radius;
            y = rand.nextInt(h-2*radius) + radius;
        }
    }
    //**********************Triangle Class************************
    private class Triangle
    {
        int x1,y1,x2,y2,x3,y3, color;
        public Triangle(int w, int h, Random rand)
        {
            color = Color.rgb(rand.nextInt(256), rand
                    .nextInt(256), rand.nextInt(256));
            x1 = rand.nextInt(w+1);
            y1 = rand.nextInt(h+1);
            x2 = rand.nextInt(w+1);
            y2 = rand.nextInt(h+1);
            x3 = rand.nextInt(w+1);
            y3 = rand.nextInt(h+1);
        }
    }
    //*********************Rectangle Class************************
    private class Rectangle
    {
        int x1,y1,x2,y2,color;
        public Rectangle(int w, int h, Random rand)
        {
            x1 = rand.nextInt(w+1);
            y1 = rand.nextInt(h+1);
            x2 = rand.nextInt(w+1);
            y2 = rand.nextInt(h+1);
            //Need to make sure x1 and y1 are the lower bound
            while(x2 <= x1)
            {
                x1 = rand.nextInt(w+1);
                x2 = rand.nextInt(w+1);
            }
            while(y2 <= y1)
            {
                y1 = rand.nextInt(h+1);
                y2 = rand.nextInt(h+1);
            }
            color = Color.rgb(rand.nextInt(256), rand
                    .nextInt(256), rand.nextInt(256));
        }
    }
}

