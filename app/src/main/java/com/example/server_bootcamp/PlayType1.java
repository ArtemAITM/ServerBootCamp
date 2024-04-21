package com.example.server_bootcamp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.C;

public class PlayType1 extends AppCompatActivity {
    //1524
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BouncingBallView(this));
    }

    private static class BouncingBallView extends View {

        public String nameRoom = ClientMainActivity.nameRoomString;
        private DatabaseReference mDataBase;

        private float x = 100;
        private float y = 100;
        private float xusr2 = (float) getWidth() / 2;
        private float xVelocity = 10;
        private float yVelocity = 10;
        private final float radius = 25;
        private final Paint paint = new Paint();
        Canvas canvas;

        public BouncingBallView(Context context) {
            super(context);
            paint.setColor(Color.RED);

            mDataBase = FirebaseDatabase.getInstance().getReference();

            mDataBase.child(nameRoom).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("12","12");
                    Float post = snapshot.child("x").getValue(Float.class);
                    Float post2 = snapshot.child("vel").getValue(Float.class);
                    //проверка на равенство данных
                    x = post;
                    y = 0;
                    xVelocity = post2;
                    yVelocity = -Math.abs(yVelocity);
                    draw(canvas);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        @Override
        public void draw(@NonNull Canvas canv) {
            this.canvas = canv;
            super.draw(canvas);
            paint.setColor(Color.RED);
            canvas.drawCircle(x, y, radius, paint);
            paint.setColor(Color.BLACK);
            canvas.drawRect(xusr2 - 100, getHeight() - 110, xusr2 + 100, getHeight() - 90, paint);
            x += xVelocity;
            y += yVelocity;
            if (x + radius > getWidth() || x - radius < 0) {
                xVelocity = -xVelocity;
            }
            if (x >= xusr2 - 100 && x <= xusr2 + 100 && y + radius >= getHeight() - 110) {
                yVelocity = -yVelocity;
            }

            if (y + radius <0){
                mDataBase.removeValue();
                mDataBase.child(nameRoom).child("x").setValue(x);
                mDataBase.child(nameRoom).child("vel").setValue(xVelocity);
                y = getHeight() + 30;
                yVelocity = 0;
                xVelocity = 0;
            }
            invalidate();
        }
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                xusr2 = event.getX();
            }
            return true;
        }
    }
}