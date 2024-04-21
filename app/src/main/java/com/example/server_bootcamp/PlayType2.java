package com.example.server_bootcamp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kotlin.random.Random;

public class PlayType2 extends AppCompatActivity {
    //1524
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BouncingBallView(this));
    }

    private static class BouncingBallView extends View {
        private float x = 500;
        private float y = 500;
        private float xusr1 = getWidth() / 2;
        private float xusr2 = getWidth() / 2;
        private float xVelocity = 10;
        private float yVelocity = 10;
        private final float radius = 25;
        private final Paint paint = new Paint();
        private ArrayList<Square> squares = new ArrayList<>();
        private int squareSize = 50;
        public BouncingBallView(Context context) {
            super(context);
            paint.setColor(Color.RED);
            paint.setTextSize(100f);
            for (int i = 0; i < 5; i++) {
                int x = 100 * (i + 1); // Случайная координата X
                int y = 100 * (i + 1);
                squares.add(new Square(x, y, squareSize));
            }
        }

        @SuppressLint("DrawAllocation")
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint.setColor(Color.RED);
            canvas.drawCircle(x, y, radius, paint);
            paint.setColor(Color.BLACK);
            canvas.drawRect(xusr1 - 100, 110, xusr1 + 100, 90, paint);
            canvas.drawRect(xusr2 - 100, getHeight() - 110, xusr2 + 100, getHeight() - 90, paint);
            paint.setColor(Color.GREEN);
            for (Square square : squares) {
                canvas.drawRect(square.getX(), square.getY(), square.getX() + square.getSize(), square.getY() + square.getSize(), paint);
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
            if(squares.isEmpty()){
                Toast.makeText(this.getContext(), "Win", Toast.LENGTH_SHORT).show();
                paint.setColor(Color.BLACK);
                canvas.drawText("Победа", (float) getWidth() / 4, (float) getHeight() / 2, paint);
            }else{
                checkCollisions();
            }
            if (y + radius > getHeight() || y + radius < 0) {
                Toast.makeText(this.getContext(), "Поражение", Toast.LENGTH_SHORT).show();
                paint.setColor(Color.BLACK);
                canvas.drawText("Поражение", (float) getWidth() / 4, (float) getHeight() / 2, paint);
            }
            invalidate();

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
        private void checkCollisions() {
            Iterator<Square> iterator = squares.iterator();
            while (iterator.hasNext()) {
                Square square = iterator.next();
                if (x + radius >= square.getX() && x - radius <= square.getX() + square.getSize() &&
                        y + radius >= square.getY() && y - radius <= square.getY() + square.getSize()) {
                    yVelocity = -yVelocity;
                    iterator.remove();
                }
            }
        }
    }
}
class Square {
    private int x;
    private int y;
    private int size;

    public Square(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }
}

