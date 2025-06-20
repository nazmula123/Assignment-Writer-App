package com.example.assignment_writer.Result;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.assignment_writer.Edit.EditActivity;
import com.example.assignment_writer.Edit.PdfGenerate;
import com.example.assignment_writer.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executors;


public class ResultActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView edit_text, back,pdfGenerate;
    TextView gpt_text;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout layout;
    private static final int CREATE_PDF_REQUEST_CODE = 1;
    private String textToConvert = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        FindId();

        pdfGenerate.setOnClickListener(this);
        TextGenerate();
        back.setOnClickListener(this);
        edit_text.setOnClickListener(this);
        shimmerFrameLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.background));
    }

    private void FindId(){
        edit_text = findViewById(R.id.edit_text);
        back = findViewById(R.id.back);
        gpt_text = findViewById(R.id.gpt_text);
        shimmerFrameLayout=findViewById(R.id.shimmer_layout);
        layout=findViewById(R.id.layout);
        pdfGenerate=findViewById(R.id.generate_pdf);
    }

    public void TextGenerate(){

        Intent intent=getIntent();

        String inputText =intent.getStringExtra("topic_text");

        if (inputText.isEmpty()) {
            Toast.makeText(this, "Please enter text before calling the API.", Toast.LENGTH_SHORT).show();
            return;
        }

        GenerativeModel gm = new GenerativeModel("gemini-2.0-flash", "AIzaSyDDkoARvFjd0v6wNq5RWoXYRHS5hc9SwcY");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content co = new Content.Builder()
                .addText(inputText)
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(co);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                if (result != null && result.getText() != null) {
                    String resultText = result.getText();


                    runOnUiThread(() -> {
                        gpt_text.setText(resultText);
                        shimmerFrameLayout.stopShimmer();
                        layout.setVisibility(View.VISIBLE);
                        Toast.makeText(ResultActivity.this, "Response received!", Toast.LENGTH_SHORT).show();
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
                    shimmerFrameLayout.stopShimmer();
                    layout.setVisibility(View.VISIBLE);
                    Toast.makeText(ResultActivity.this, "Failed to call API.", Toast.LENGTH_SHORT).show();
                });
                Log.e("API_ERROR", "Error calling API", t);
            }
        }, Executors.newSingleThreadExecutor());
    }
    @Override
    public void onClick(View v) {

        PdfGenerate pdf=new PdfGenerate();
        if (v.getId()==R.id.generate_pdf){
            textToConvert = gpt_text.getText().toString();
            if (!textToConvert.isEmpty()) {
                pdf.createPdfFile(gpt_text.getText().toString());
            } else {
                Toast.makeText(this, "Please enter some text.", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId()==R.id.edit_text) {

            String text=gpt_text.getText().toString();
            Intent intent=new Intent(ResultActivity.this, EditActivity.class);
            intent.putExtra("text_edit",text);
            startActivity(intent);
        }
        else if(v.getId()==R.id.back){
            onBackPressed();
        }
    }
}