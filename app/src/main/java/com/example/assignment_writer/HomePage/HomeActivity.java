package com.example.assignment_writer.HomePage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.assignment_writer.Login.SignIn;
import com.example.assignment_writer.R;
import com.example.assignment_writer.Result.WriterPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends Fragment implements View.OnClickListener{
    private CardView signIn;
    private CircleImageView profileImage;
    SharedPreferences sharedPreferences;
    LinearLayout blog,email,grammar,cv,assignment,history,application,letter,readability,essay,mcq;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home_activity, container, false);

        sharedPreferences= getActivity().getSharedPreferences("MyPrefs",MODE_PRIVATE);

             FindId(view);
             setClick();
             setProfileImage();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SignIn.class));
            }
        });

    return view;
    }
    private void FindId(View view){
        blog=view.findViewById(R.id.blog_part);
        email=view.findViewById(R.id.gmail_part);
        grammar=view.findViewById(R.id.grammar_part);
        cv=view.findViewById(R.id.cv_part);
        assignment=view.findViewById(R.id.assignment);
        history=view.findViewById(R.id.history);
        application=view.findViewById(R.id.application);
        letter=view.findViewById(R.id.letter);
        readability=view.findViewById(R.id.readability);
        essay=view.findViewById(R.id.essay);
        mcq=view.findViewById(R.id.mcq);
        signIn=view.findViewById(R.id.sing_in);
        profileImage=view.findViewById(R.id.profileImage);
    }
    private void setClick(){
        blog.setOnClickListener(this);
        email.setOnClickListener(this);
        grammar.setOnClickListener(this);
        cv.setOnClickListener(this);
        assignment.setOnClickListener(this);
        history.setOnClickListener(this);
        application.setOnClickListener(this);
        letter.setOnClickListener(this);
        readability.setOnClickListener(this);
        essay.setOnClickListener(this);
        mcq.setOnClickListener(this);
    }
    private void setProfileImage(){
        String key=sharedPreferences.getString("profileKey",null);
        Boolean permission=sharedPreferences.getBoolean("profilePermission",false);

        if(permission==true) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(key);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String imageUrl=snapshot.child("Photos").getValue(String.class);

                    Picasso.get().load(imageUrl).into(profileImage);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Load Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.blog_part){
            Intent intent=new Intent(getContext(), WriterPage.class);
            intent.putExtra("type","Blog Writer");
            startActivity(intent);
        }

        else if(v.getId()==R.id.gmail_part){
            Intent intent=new Intent(getContext(), WriterPage.class);
            intent.putExtra("type","Email Generator");
            startActivity(intent);
        }
        else if(v.getId()==R.id.grammar_part) {
            Intent intent = new Intent(getContext(), WriterPage.class);
            intent.putExtra("type", "Grammar Checker");
            startActivity(intent);
        }
        else if(v.getId()==R.id.cv_part){
            Intent intent = new Intent(getContext(), WriterPage.class);
            intent.putExtra("type", "Resume/CV Writer");
            startActivity(intent);
        }
        else if(v.getId()==R.id.assignment){
            Intent intent = new Intent(getContext(), WriterPage.class);
            intent.putExtra("type", "Assignment Writer");
            startActivity(intent);
        }
        else if(v.getId()==R.id.history){
            Intent intent = new Intent(getContext(), WriterPage.class);
            intent.putExtra("type", "History Writer");
            startActivity(intent);
        }

        else if(v.getId()==R.id.application){
            Intent intent = new Intent(getContext(), WriterPage.class);
            intent.putExtra("type", "Application Writer");
            startActivity(intent);
        }
        else if(v.getId()==R.id.letter){
            Intent intent = new Intent(getContext(), WriterPage.class);
            intent.putExtra("type", "Letter Writer");
            startActivity(intent);
        }
        else if(v.getId()==R.id.readability){
            Intent intent = new Intent(getContext(), WriterPage.class);
            intent.putExtra("type", "Readability Analysis");
            startActivity(intent);
        }
        else if(v.getId()==R.id.essay){
            Intent intent = new Intent(getContext(), WriterPage.class);
            intent.putExtra("type", "Essay Writer");
            startActivity(intent);
        }

        else if(v.getId()==R.id.mcq){
            Intent intent = new Intent(getContext(), WriterPage.class);
            intent.putExtra("type", "MCQ Solver");
            startActivity(intent);
        }
    }


}