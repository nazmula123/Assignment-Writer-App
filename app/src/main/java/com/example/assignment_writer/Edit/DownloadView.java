package com.example.assignment_writer.Edit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment_writer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DownloadView extends AppCompatActivity {

    ImageView back;
    TextView textView;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_download_view);

        Intent intent=getIntent();
       String key=intent.getStringExtra("key");

        reference= FirebaseDatabase.getInstance().getReference("DownloadDb").child(key);
        back=findViewById(R.id.back);
        textView=findViewById(R.id.text);

        setFirebaseDatabase();
    }
    private void setBack(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void setFirebaseDatabase(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String text=snapshot.child("title").getValue(String.class);
                textView.setText(text);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(DownloadView.this, "network problem", Toast.LENGTH_SHORT).show();
            }
        });

    }
}