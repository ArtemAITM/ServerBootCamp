package com.example.server_bootcamp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayType3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }
}
class GameView extends View {
    private List<Shape> shapes;
    private Paint paint;
    float xusr1 = (float) getWidth() / 2;
    float x = 500;
    float y = 500;
    float xusr2 = (float) getWidth() / 2;
    float xVelocity = 10;
    float yVelocity = 10;
    float radius = 50;
    private int shapeCount = 0;
    boolean yourBall = true;

    public GameView(Context context) {
        super(context);
        init();
    }

    private void init() {
        shapes = new ArrayList<>();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        shapes.add(new Shape(200, 200, 100, Color.RED));
        shapes.add(new Shape(400, 400, 100, Color.GREEN));
        shapes.add(new Shape(300, 300, 100, Color.YELLOW));
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, radius, paint);
        paint.setColor(Color.BLACK);
        canvas.drawRect(xusr2 - 100, getHeight() - 110, xusr2 + 100, getHeight() - 90, paint);
        for (Shape shape : shapes) {
            paint.setColor(shape.getColor());
            canvas.drawRect(shape.getX(), shape.getY(), shape.getX() + shape.getSize(), shape.getY() + shape.getSize(), paint);
        }
        for (Shape shape : shapes) {
            if (isCollision(shape)) {
                if(shape.getColor() == Color.BLUE){
                    continue;
                }
                shapeCount++;
                shape.setColor(Color.BLUE);
            }
        }
        x += xVelocity;
        y += yVelocity;
        if (x + radius > getWidth() || x - radius < 0) {
            xVelocity = -xVelocity;
        }
        if (x >= xusr1 - 100 && x <= xusr1 + 100 && y - radius <= 110) {
            yVelocity = -yVelocity;
        }
        if (x >= xusr2 - 100 && x <= xusr2 + 100 && y + radius >= getHeight() - 110) {
            yVelocity = -yVelocity;
        }

        if (y + radius > getHeight() || y + radius < 0){
            Toast.makeText(this.getContext(), "Поражение", Toast.LENGTH_SHORT).show();
        }

        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText("Shapes: " + shapeCount, 50, 50, paint);
        invalidate();
    }
    private boolean isCollision(Shape shape) {
        double distance = Math.sqrt(Math.pow(x - shape.getX(), 2) + Math.pow(y - shape.getY(), 2));
        return distance < radius + shape.getSize() / 2;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (event.getY() < (float) getHeight() / 2) {
                xusr1 = event.getX();
            } else {
                xusr2 = event.getX();
            }
        }
        return true;
    }


    private class Shape {
        private float x;
        private float y;
        private float size;
        private int color;

        public Shape(float x, float y, float size, int color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getSize() {
            return size;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
}
