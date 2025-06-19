package com.example.assignment_writer.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.assignment_writer.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    TextInputEditText user_email,username,user_full_name,user_phone,user_password,user_confirm_password;
    String email,user_name,full_name,phone,password,confirm_password;
    CardView signUp;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

      reference=FirebaseDatabase.getInstance().getReference("Users");

        user_email=findViewById(R.id.user_email);
        username=findViewById(R.id.user_name);
        user_full_name=findViewById(R.id.user_full_name);
        user_phone=findViewById(R.id.user_phone);
        user_password=findViewById(R.id.user_password);
        user_confirm_password=findViewById(R.id.confirm_password);
        signUp=findViewById(R.id.signUp);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFromUser();
            }
        });
    }

    private void SignupFromUser() {


        isChecked checked = new isChecked();
        HashString hashString = new HashString();

        email = user_email.getText().toString();
        user_name = username.getText().toString();
        full_name = user_full_name.getText().toString();
        phone = user_phone.getText().toString();
        password = user_password.getText().toString();
        confirm_password = user_confirm_password.getText().toString();

        if (checked.validEmail(email) == false || user_email.getText().toString().isEmpty()) {
            user_email.setBackgroundResource(R.drawable.error_background);
            return;

        }
        if (checked.validPassword(password) == false || user_password.getText().toString().isEmpty()) {
            user_password.setBackgroundResource(R.drawable.error_background);
            return;
        }

        if (!password.equals(confirm_password)) {
            user_confirm_password.setBackgroundResource(R.drawable.error_background);
        }

        else {

            View dialogView = LayoutInflater.from(SignUp.this).inflate(R.layout.dialog_box, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialog.show();


            String sPassword = hashString.sha256(password);

            HashMap<String, String> userMap = new HashMap<>();
            userMap.put("email", email);
            userMap.put("userName", user_name);
            userMap.put("fullName", full_name);
            userMap.put("phone", phone);
            userMap.put("password", sPassword);

            reference.child(reference.push().getKey()).setValue(userMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }
    }

}