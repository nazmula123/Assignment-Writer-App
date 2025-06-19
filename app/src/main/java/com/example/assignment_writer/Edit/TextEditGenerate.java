package com.example.assignment_writer.Edit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.Toast;

public class TextEditGenerate {
    private Context context;
    TextEditGenerate(Context context){
        this.context=context;
    }
    public SpannableString boldText(String text, String myselect) {
        String fullText = text;

        int start = fullText.indexOf(myselect);
        int end = start + myselect.length();

        SpannableString spannable = new SpannableString(fullText);

        if (start >= 0) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            Toast.makeText(context, "Text not found!", Toast.LENGTH_SHORT).show();
        }
       return spannable;
    }
    public SpannableString Italic(String text, String myselect) {
        String fullText = text;

        int start = fullText.indexOf(myselect);
        int end = start + myselect.length();

        SpannableString spannable = new SpannableString(fullText);

        if (start >= 0) {
            spannable.setSpan(new StyleSpan(Typeface.ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            Toast.makeText(context, "Text not found!", Toast.LENGTH_SHORT).show();
        }
        return spannable;
    }

    public SpannableString Underline(String text, String myselect) {
        String fullText = text;
        int start = fullText.indexOf(myselect);
        int end = start + myselect.length();

        SpannableString spannable = new SpannableString(fullText);

        if (start >= 0) {
            spannable.setSpan(new UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            Toast.makeText(context, "Text not found!", Toast.LENGTH_SHORT).show();
        }
        return spannable;
    }
}
