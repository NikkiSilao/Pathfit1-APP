package com.example.pathfit1finalapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;
import android.content.res.ColorStateList;
import android.animation.ValueAnimator;

public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private Runnable colorChangeRunnable;
    private int startColor;
    private int endColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove the status bar and make the app full screen
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_main);

        startColor = ContextCompat.getColor(this, R.color.button_color_original);
        endColor = ContextCompat.getColor(this, R.color.button_color_changed);

        // Initialize button color change runnable
        initColorChangeRunnable();

        // Start button color change animation
        handler.postDelayed(colorChangeRunnable, 0);

        // Set onClickListener for Get Started button
        Button getStartedButton = findViewById(R.id.button);
        getStartedButton.setOnClickListener(v -> {
            // Navigate to HomeActivity when button is clicked
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Finish current activity
        });

        // Apply system bars insets to main layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets; // Return the insets without consuming them
        });

        // Load the Inter font from resources
        Typeface typeface = ResourcesCompat.getFont(this, R.font.interbold);

        // Apply the font to the TextViews
        TextView textViewPart1 = findViewById(R.id.text_view_part1);
        TextView textViewPart2 = findViewById(R.id.text_view_part2);
        textViewPart1.setTypeface(typeface);
        textViewPart2.setTypeface(typeface);

        // Handle back button press
        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        dispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            // Finish the activity
                            finish();
                        })
                        .setNegativeButton("No", (dialog, id) -> {
                            // Do nothing
                        })
                        .show();
            }
        });
    }

    // Method to initialize button color change runnable
    private void initColorChangeRunnable() {
        colorChangeRunnable = new Runnable() {
            boolean originalColor = true;

            @Override
            public void run() {
                Button getStartedButton = findViewById(R.id.button);

                // Create a ValueAnimator for smooth color transition
                ValueAnimator colorAnimation = ValueAnimator.ofArgb(
                        originalColor ? startColor : endColor,
                        originalColor ? endColor : startColor
                );
                colorAnimation.setDuration(1000); // Animation duration in milliseconds

                // Update button's background tint on animation update
                colorAnimation.addUpdateListener(animation -> {
                    int animatedValue = (int) animation.getAnimatedValue();
                    getStartedButton.setBackgroundTintList(ColorStateList.valueOf(animatedValue));
                });

                colorAnimation.start(); // Start the animation

                originalColor = !originalColor; // Toggle originalColor flag
                handler.postDelayed(this, 3000); // Repeat every 3 seconds
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(colorChangeRunnable); // Stop color change runnable when activity is destroyed
    }
}
