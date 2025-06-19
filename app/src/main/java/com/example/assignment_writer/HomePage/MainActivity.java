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
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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

        home.setOnClickListener(this);
        download.setOnClickListener(this);
        history.setOnClickListener(this);
        account.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeActivity).commit();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.home){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeActivity).commit();
        }
        else if(v.getId()== R.id.download){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,downloadActivity).commit();
        }
        else if(v.getId()==R.id.history)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,historyActivity).commit();
        }
        else if(v.getId()==R.id.account){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,profile).commit();
        }
    }
}