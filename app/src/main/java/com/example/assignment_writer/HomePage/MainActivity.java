package com.example.assignment_writer.HomePage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment_writer.Download.downloadActivity;
import com.example.assignment_writer.History.BlankFragment;
import com.example.assignment_writer.Profile.ProfileActivity;
import com.example.assignment_writer.R;
public class MainActivity extends AppCompatActivity{

    private ImageView home,download,history,account;
     HomeActivity homeActivity=new HomeActivity();
     ProfileActivity profile=new ProfileActivity();
     downloadActivity downloadActivity=new downloadActivity();
     BlankFragment historyActivity=new BlankFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        home=findViewById(R.id.home);
        download=findViewById(R.id.download);
        history=findViewById(R.id.history);
        account=findViewById(R.id.account);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeActivity).commit();

        setHomeActivity();
        setDownloadActivity();
        setHistoryActivity();
        setProfileActivity();
    }
    private void setHomeActivity(){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeActivity).commit();
                home.setImageResource(R.drawable.home_select);
                download.setImageResource(R.drawable.download_b);
                history.setImageResource(R.drawable.history_b);
                account.setImageResource(R.drawable.user);
            }
        });
    }
    private void setDownloadActivity(){

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,downloadActivity).commit();
                home.setImageResource(R.drawable.home);
                download.setImageResource(R.drawable.download_c);
                history.setImageResource(R.drawable.history_b);
                account.setImageResource(R.drawable.user);
            }
        });
    }

    private void setHistoryActivity(){
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,historyActivity).commit();
                history.setImageResource(R.drawable.home);
                download.setImageResource(R.drawable.download_b);
                history.setImageResource(R.drawable.history_c);
                account.setImageResource(R.drawable.user);
            }
        });
    }

    private void setProfileActivity(){
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,profile).commit();
                history.setImageResource(R.drawable.home);
                download.setImageResource(R.drawable.download_b);
                history.setImageResource(R.drawable.history_b);
                account.setImageResource(R.drawable.user_c);
            }
        });
    }

}