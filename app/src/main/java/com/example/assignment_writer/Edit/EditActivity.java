package com.example.assignment_writer.Edit;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.assignment_writer.R;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private EditText text;
    private CardView cardView;
    private ImageView bold_text,italic,underline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);

        back = findViewById(R.id.back);
        text = findViewById(R.id.text);
        bold_text = findViewById(R.id.text_bold);
        italic=findViewById(R.id.italic);
        underline=findViewById(R.id.underline);
        cardView=findViewById(R.id.save_text);

        back.setOnClickListener(this);
        bold_text.setOnClickListener(this);
        italic.setOnClickListener(this);
        underline.setOnClickListener(this);
        cardView.setOnClickListener(this);
        setText();
    }

    private void setText() {
        Intent intent = getIntent();
        String inputText = intent.getStringExtra("text_edit");
        if (inputText != null) {
            text.setText(inputText);
        } else {
            text.setText("No text found");
        }
    }
    @Override
    public void onClick(View v) {
        int selStart = text.getSelectionStart();
        int selEnd = text.getSelectionEnd();

        TextEditGenerate textEditGenerate = new TextEditGenerate(getApplicationContext());
        String myselect = text.getText().toString().substring(selStart, selEnd);

        if (v.getId() == R.id.back) {
            onBackPressed();
        } else if (v.getId() == R.id.text_bold) {
            if (selStart == selEnd) {
                Toast.makeText(this, "Please select some text to bold.", Toast.LENGTH_SHORT).show();
                return;
            }
            SpannableString string = textEditGenerate.boldText(text.getText().toString(), myselect);
            text.setText(string);
        }

        else if (v.getId() == R.id.italic) {
            if (selStart == selEnd) {
                Toast.makeText(this, "Please select some text to italic.", Toast.LENGTH_SHORT).show();
                return;
            }
            SpannableString string = textEditGenerate.Italic(text.getText().toString(), myselect);
            text.setText(string);
        }
        else if (v.getId() == R.id.underline) {
            if (selStart == selEnd) {
                Toast.makeText(this, "Please select some text to underline.", Toast.LENGTH_SHORT).show();
                return;
            }
            SpannableString string = textEditGenerate.Underline(text.getText().toString(), myselect);
            text.setText(string);
        }

        else if(v.getId()==R.id.save_text){

            PdfGenerate pdfGenerate=new PdfGenerate();
            pdfGenerate.createPdfFile(text.getText().toString());

        }
    }
}
