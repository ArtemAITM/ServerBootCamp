package com.example.server_bootcamp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ClientMainActivity extends AppCompatActivity {

    private EditText nameRoom;
    public static String nameRoomString;
    public static DatabaseReference mDataBase;
    public float post;
    String key;
    float user_id = Math.round(Math.random() * 10000);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_type2);

        nameRoom = findViewById(R.id.room_code);
        ImageButton button = findViewById(R.id.for_code);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, PlayType1.class);
            nameRoomString = nameRoom.getText().toString();
            key = nameRoomString;
            startActivity(intent);
            mDataBase = FirebaseDatabase.getInstance().getReference();
            sendKeyAndCheckForMatch(key);
        });
    }
    private void sendKeyAndCheckForMatch(String key) {
        mDataBase.child(nameRoomString).child("key").setValue(nameRoomString).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    checkUsersWithKeyAndNavigate(key);
                } else {
                }
            }
        });
    }
    private void checkUsersWithKeyAndNavigate(String key) {
        Query query = mDataBase.orderByChild("key").equalTo(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int count = 0;
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        if (!userSnapshot.getKey().equals(nameRoomString)) {
                            count++;
                        }
                    }

                    if (count > 0) {
                        Intent intent = new Intent(ClientMainActivity.this, PlayType1.class);
                        startActivity(intent);
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}