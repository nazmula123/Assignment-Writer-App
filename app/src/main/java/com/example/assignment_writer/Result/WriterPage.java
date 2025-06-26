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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.assignment_writer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriterPage extends AppCompatActivity {
private ImageView back;
private Spinner spinner1,spinner2;
private EditText title,type,tone,word;
private TextView textView;
private CardView generate;
private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_writer_page);

        reference= FirebaseDatabase.getInstance().getReference("HistoryDatabase");

        FindId();

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
                   AddFirebaseHistory();
                   Intent intent1=new Intent(WriterPage.this, ResultActivity.class);
                   intent1.putExtra("topic_text",WriteText());
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
    private void FindId(){
        back=findViewById(R.id.back);
        generate=findViewById(R.id.generate);
        textView=findViewById(R.id.type_text);
        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);
        title=findViewById(R.id.title);
        type=findViewById(R.id.type);
        tone=findViewById(R.id.tone);
        word=findViewById(R.id.word);
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
private void AddFirebaseHistory(){
        AddHistoryRecyclerViewModel addHistoryRecyclerViewModel=new AddHistoryRecyclerViewModel("Science Project","12/05/2025","wfsdfs");

     reference.child(reference.push().getKey()).setValue(addHistoryRecyclerViewModel).addOnCompleteListener(task -> {
        if (task.isSuccessful()) {

            Toast.makeText(this, "Successful Added", Toast.LENGTH_SHORT).show();

        }
    });

}
    private String WriteText(){

        String  command_text="",type_text="",title_text="",tone_text="",word_text="",language_text="",level_text="";
        type_text=type.getText().toString();
        title_text=title.getText().toString();
        tone_text=tone.getText().toString();
        word_text=word.getText().toString();
        language_text=spinner1.getSelectedItem().toString();
        level_text=spinner2.getSelectedItem().toString();
        command_text = "Write a " + tone_text + " " + type_text + " titled '" + title_text +
                "' in " + language_text + " for " + level_text + " level students. The content should be around " +
                word_text + " words.";
        return command_text;
    }
}