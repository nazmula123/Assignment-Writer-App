package com.example.assignment_writer.Result;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment_writer.Edit.EditActivity;
import com.example.assignment_writer.R;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Executors;


public class ResultActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView edit_text, back,pdfGenerate,copy,share,refresh;
    TextView gpt_text;
    String project_type="";
    String type="";
    String language="";
     DatabaseReference reference;
       ProgressBar progressBar;
    private static final int CREATE_PDF_REQUEST_CODE = 1;
    private String textToConvert = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        reference= FirebaseDatabase.getInstance().getReference("DownloadDb");

        FindId();
        TextGenerate();
        setCopied();
        setShare();
         setRefresh();
        pdfGenerate.setOnClickListener(this);
        back.setOnClickListener(this);
        edit_text.setOnClickListener(this);
    }

    private void FindId(){
        edit_text = findViewById(R.id.edit_text);
        back = findViewById(R.id.back);
        gpt_text = findViewById(R.id.gpt_text);
        pdfGenerate=findViewById(R.id.generate_pdf);
        copy=findViewById(R.id.copy);
        share=findViewById(R.id.share);
        refresh=findViewById(R.id.refresh);
        progressBar=findViewById(R.id.progress);
    }
    private void setCopied(){

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=gpt_text.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("Copied Text",text);
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(ResultActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setShare(){
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = gpt_text.getText().toString();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(shareIntent, "Share text using"));
            }
        });
    }
    private void setRefresh(){
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextGenerate();
            }
        });
    }
    public void TextGenerate(){

        //progressBar.setVisibility(View.VISIBLE);

        Intent intent=getIntent();

        String inputText =intent.getStringExtra("topic_text");

        if (inputText.isEmpty()) {
            Toast.makeText(this, "Please enter text before calling the API.", Toast.LENGTH_SHORT).show();
            return;
        }
        GenerativeModel gm = new GenerativeModel("gemini-2.0-flash", "AIzaSyD1oBWt5yx6Xzq-_mTyHq3Zez1bpuFYI6w");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content co = new Content.Builder()
                .addText(inputText)
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(co);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {

               // progressBar.setVisibility(View.GONE);

                if (result != null && result.getText() != null) {
                    String resultText = result.getText();

                    runOnUiThread(() -> {

                        gpt_text.setText(resultText);
                    });
                } else {
                    runOnUiThread(() -> {

                        gpt_text.setText("Empty response received!");
                        Toast.makeText(ResultActivity.this, "Empty response from API.", Toast.LENGTH_SHORT).show();
                    });
                }
            }
            @Override
            public void onFailure(@NonNull Throwable t) {
                runOnUiThread(() -> {
                    gpt_text.setText("Error: " + t.getMessage());

                    Toast.makeText(ResultActivity.this, "Failed to call API.", Toast.LENGTH_SHORT).show();
                });
                Log.e("API_ERROR", "Error calling API", t);
            }
        }, Executors.newSingleThreadExecutor());
    }
    @Override
    public void onClick(View v) {


        if (v.getId()==R.id.generate_pdf){
            textToConvert = gpt_text.getText().toString();
            AddDownloadFirebase(textToConvert);
            if (!textToConvert.isEmpty()) {
                createPdfFile(textToConvert);
            } else {
                Toast.makeText(this, "Please enter some text.", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId()==R.id.edit_text) {

            Intent intent1=getIntent();
            project_type=intent1.getStringExtra("project_type");
            type=intent1.getStringExtra("type");
            language=intent1.getStringExtra("language");

            String text=gpt_text.getText().toString();
            Intent intent=new Intent(ResultActivity.this, EditActivity.class);
            intent.putExtra("text_edit",text);
            intent.putExtra("project_type",project_type);
            intent.putExtra("type",type);
            intent.putExtra("language",language);

            Toast.makeText(this, ""+project_type+type+language, Toast.LENGTH_SHORT).show();

            startActivity(intent);
        }
        else if(v.getId()==R.id.back){
            onBackPressed();
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

        Intent intent=getIntent();
        String project_type= intent.getStringExtra("project_type");
        String type=intent.getStringExtra("type");
        String language=intent.getStringExtra("language");
        Date now = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        String formattedDate = dateFormat.format(now);

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        String formattedTime = timeFormat.format(now);


        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("projectType",project_type);
        userMap.put("type","Type : "+type);
        userMap.put("title",gpt_text.getText().toString());
        userMap.put("character","Number of Character : "+gpt_text.length());
        userMap.put("language","Language : "+language);
        userMap.put("date",formattedDate);
        userMap.put("time",formattedTime);

        reference.child(reference.push().getKey()).setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onBackPressed();
            }
        });
    }
}