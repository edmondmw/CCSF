package com.example.edmondwang.drawingshapeshw5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Edmond Wang on 10/18/2016.
 */
public class DrawCircle extends View
{
    private class Circle
    {
        int x;
        int y;
        int radius;
        int color;

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

    int screenWidth;
    int screenHeight;
    Paint p;
    Random rand;
    ArrayList<Circle> circleArrayList;

    public DrawCircle(Context con, AttributeSet attr)
    {
        super(con, attr);
        p = new Paint();
        WindowManager wm = (WindowManager) con.getSystemService
                (Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        screenWidth = point.x;
        screenHeight = point.y;
        rand = new Random();
        circleArrayList = new ArrayList<Circle>();
    }

    @Override
    public void onDraw(Canvas c)
    {
        super.onDraw(c);

        //loop through array list and draw the circles
        for(int i = 0; i < circleArrayList.size(); ++i)
        {
            Circle aCircle = circleArrayList.get(i);
            p.setColor(aCircle.color);
            p.setStyle(Paint.Style.FILL);
            c.drawCircle(aCircle.x, aCircle.y, aCircle.radius, p);
        }
    }

    public void generateCircle()
    {
        Circle aCircle = new Circle(screenWidth, screenHeight,
                rand);
        circleArrayList.add(aCircle);
    }
}
