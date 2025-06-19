package com.example.assignment_writer.Result;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.assignment_writer.R;

public class WriterPage extends AppCompatActivity {
private ImageView back;
private Spinner spinner1,spinner2;
private EditText title,type,tone,word;
private TextView textView;
private CardView generate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_writer_page);

       back=findViewById(R.id.back);
       generate=findViewById(R.id.generate);
       textView=findViewById(R.id.type_text);
       spinner1=findViewById(R.id.spinner1);
       spinner2=findViewById(R.id.spinner2);

       title=findViewById(R.id.title);
       type=findViewById(R.id.type);
       tone=findViewById(R.id.tone);
       word=findViewById(R.id.word);



      Intent intent=getIntent();
      String text=intent.getStringExtra("type");
      textView.setText(text);

      setSpinner1();
      setSpinner2();
       generate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(title.getText().toString().isEmpty()){
                   title.setBackgroundResource(R.drawable.error_background);
               }
               if(type.getText().toString().isEmpty()){
                   type.setBackgroundResource(R.drawable.error_background);
               }
               if(tone.getText().toString().isEmpty()){
                   tone.setBackgroundResource(R.drawable.error_background);
               }

               else{
                   String text=title.getText().toString();
                   Intent intent1=new Intent(WriterPage.this, ResultActivity.class);
                   intent1.putExtra("topic_text",text);
                   startActivity(intent1);
               }
           }
       });

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });
    }

    private void setSpinner1() {
        String []spinner1String;
        spinner1=findViewById(R.id.spinner1);
        spinner1String=getResources().getStringArray(R.array.spinner1);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.textView,spinner1String);
        spinner1.setAdapter(adapter);
        spinner1.setPopupBackgroundResource(R.drawable.pop_up);
    }

    private void setSpinner2() {
        String []spinner2String;
        spinner2=findViewById(R.id.spinner2);
        spinner2String=getResources().getStringArray(R.array.level_name);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_item,R.id.textView,spinner2String);
        spinner2.setAdapter(adapter);
        spinner2.setPopupBackgroundResource(R.drawable.pop_up);
    }
}