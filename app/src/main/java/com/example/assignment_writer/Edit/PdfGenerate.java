package com.example.assignment_writer.Edit;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;

public class PdfGenerate extends AppCompatActivity {

        private static final int CREATE_PDF_REQUEST_CODE = 1;
        private String textToConvert;
        public void createPdfFile(String text) {
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_TITLE, "my_text.pdf");
            startActivityForResult(intent, CREATE_PDF_REQUEST_CODE);
            textToConvert=text;
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
    }

