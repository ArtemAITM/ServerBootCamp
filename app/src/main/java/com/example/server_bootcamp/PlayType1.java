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

import androidx.appcompat.app.AppCompatActivity;

public class PlayType1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BouncingBallView(this));
    }

    private static class BouncingBallView extends View {
        private float x = 100;
        private float y = 100;
        private float xusr1 = getWidth() / 2;
        private float xusr2 = getWidth() / 2;
        private float xVelocity = 10;
        private float yVelocity = 10;
        private final float radius = 25;
        private final Paint paint = new Paint();

        public BouncingBallView(Context context) {
            super(context);
            paint.setColor(Color.RED);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint.setColor(Color.RED);
            canvas.drawCircle(x, y, radius, paint);
            paint.setColor(Color.BLACK);
            canvas.drawRect(xusr1 - 100, 110, xusr1 + 100, 90, paint);
            canvas.drawRect(xusr2 - 100, getHeight() - 110, xusr2 + 100, getHeight() - 90, paint);
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

            if (y + radius > getHeight()){
                Toast.makeText(this.getContext(), "Победил user2", Toast.LENGTH_SHORT).show();
            } else if (y + radius < 0) {
                Toast.makeText(this.getContext(), "Победил user1", Toast.LENGTH_SHORT).show();
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
    }
}


