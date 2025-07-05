package com.example.assignment_writer.Edit;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.assignment_writer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private EditText text;
    private CardView cardView;
    DatabaseReference reference;
    String project_type="";
    String type="";
    String language="";
    String title;
    private static final int CREATE_PDF_REQUEST_CODE = 1;
    private String textToConvert = "";
    private ImageView bold_text,italic,underline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);

        reference= FirebaseDatabase.getInstance().getReference("DownloadDb");

          FindId();

        back.setOnClickListener(this);
        bold_text.setOnClickListener(this);
        italic.setOnClickListener(this);
        underline.setOnClickListener(this);
        cardView.setOnClickListener(this);
        setText();
    }
private void FindId(){

    back = findViewById(R.id.back);
    text = findViewById(R.id.text);
    bold_text = findViewById(R.id.text_bold);
    italic=findViewById(R.id.italic);
    underline=findViewById(R.id.underline);
    cardView=findViewById(R.id.save_text);
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

            Intent intent=getIntent();
            title=intent.getStringExtra("text_edit");
            textToConvert = title;
            AddDownloadFirebase(textToConvert);
            if (!textToConvert.isEmpty()) {
                createPdfFile(textToConvert);
            } else {
                Toast.makeText(this, "Please enter some text.", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_PDF_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                writeTextToPdf(uri, textToConvert);
            }
        }
    }
    public void createPdfFile(String text) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "my_text.pdf");
        startActivityForResult(intent, CREATE_PDF_REQUEST_CODE);
        textToConvert = text;
    }
    private void writeTextToPdf(Uri uri, String text) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        paint.setTextSize(16);
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        int x = 10, y = 25;

        for (String line : text.split("\n")) {
            canvas.drawText(line, x, y, paint);
            y += paint.getTextSize() + 10;
        }
        pdfDocument.finishPage(page);

        try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
            pdfDocument.writeTo(outputStream);
            Toast.makeText(this, "PDF saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error saving PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        pdfDocument.close();
    }
    private void AddDownloadFirebase(String textToConvert) {


        Date now = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        String formattedDate = dateFormat.format(now);

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        String formattedTime = timeFormat.format(now);

        Intent intent=getIntent();
        project_type=intent.getStringExtra("project_type");
        type=intent.getStringExtra("type");
        language=intent.getStringExtra("language");

        HashMap<String, String> user = new HashMap<>();

        user.put("projectType",project_type);
        user.put("type","Type : "+type);
        user.put("title",title);
        user.put("character","Number of Character : "+title.length());
        user.put("language","Language : "+language);
        user.put("date",formattedDate);
        user.put("time",formattedTime);

        reference.child(reference.push().getKey()).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
