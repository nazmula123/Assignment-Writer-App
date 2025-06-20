package com.example.assignment_writer.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment_writer.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassword extends AppCompatActivity {

    TextInputEditText user_email,new_password,confirm_password;
    CardView forget_password;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        reference= FirebaseDatabase.getInstance().getReference("Users");

        FindId();

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RestPassword();
            }
        });

    }
private void FindId(){

    user_email=findViewById(R.id.email);
    new_password=findViewById(R.id.password);
    confirm_password=findViewById(R.id.confirm_password);
    forget_password=findViewById(R.id.forget_password);
}
    private void RestPassword() {

        View dialogView = LayoutInflater.from(ForgetPassword.this).inflate(R.layout.dialog_box, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();


        HashString HashUtil = new HashString();

        String email = user_email.getText().toString();
        String password = new_password.getText().toString();
        String cPassword = confirm_password.getText().toString();

        String newHashedPass = HashUtil.sha256(password);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnap : snapshot.getChildren()) {
                    String dbEmail = userSnap.child("email").getValue(String.class);
                    if (email.equals(dbEmail)) {
                        userSnap.getRef().child("password").setValue(newHashedPass);
                        dialog.dismiss();
                        Toast.makeText(ForgetPassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                dialog.dismiss();
                Toast.makeText(ForgetPassword.this, "User not found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}