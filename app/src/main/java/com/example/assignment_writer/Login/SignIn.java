package com.example.assignment_writer.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.assignment_writer.HomePage.HomeActivity;
import com.example.assignment_writer.HomePage.MainActivity;
import com.example.assignment_writer.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    String user_email,user_password;
    private CardView signIn;
    private TextView forget,signup;
    private TextInputEditText email,password;
    private DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        userRef= FirebaseDatabase.getInstance().getReference("Users");

        signIn=findViewById(R.id.sing_in);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
       forget=findViewById(R.id.forget_password);
       signup=findViewById(R.id.signUp);


       SetClick();

       signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().isEmpty()){
                    email.setBackgroundResource(R.drawable.error_background);

                }
                if(password.getText().toString().isEmpty()){
                    password.setBackgroundResource(R.drawable.error_background);
                }
                else {
                              loginUser();
                }
            }
        });
    }
    private void loginUser() {


        View dialogView = LayoutInflater.from(SignIn.this).inflate(R.layout.dialog_box, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();


        HashString HashUtil=new HashString();

        user_email = email.getText().toString().trim();
        user_password = password.getText().toString().trim();

        String hashedInputPass = HashUtil.sha256(user_password);
        userRef.addValueEventListener(new ValueEventListener() {
            int s=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot userSnap : snapshot.getChildren()) {

                    String dbEmail = userSnap.child("email").getValue(String.class);
                    String dbPass = userSnap.child("password").getValue(String.class);

                    if (user_email.equals(dbEmail) && hashedInputPass.equals(dbPass)) {
                        s=1;
                        break;
                    }
                }
                if (s==1) {
                    Toast.makeText(SignIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    Toast.makeText(SignIn.this, "Invalid User", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(SignIn.this, " "+"serverProblem", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SetClick(){

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,ForgetPassword.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });
    }
}