package com.example.pathfit1finalapp;

import android.os.Bundle;

import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Load the Inter font from the resources
        Typeface typeface = ResourcesCompat.getFont(this, R.font.interbold);

        // Apply the font to the TextView
        TextView textView = findViewById(R.id.my_text_view);
        textView.setTypeface(typeface);
    }
}