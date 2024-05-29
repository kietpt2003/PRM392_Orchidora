package com.example.prm391_orchidora.Screens.Auth;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm391_orchidora.R;

public class SignUpScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup_layout);

        TextView alreadyHaveAcc = findViewById(R.id.alreadyHaveAcc);
        SpannableString spannableString = new SpannableString("Already have an account? Sign In");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false); // Vô hiệu hóa gạch chân
            }
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        // Set the ClickableSpan to the desired portion of the text ("Sign In")
        spannableString.setSpan(clickableSpan, 25, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Apply a ForegroundColorSpan to change the color of the "Sign In" text
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#BFA4DC")); // Change color as needed
        spannableString.setSpan(foregroundColorSpan, 25, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the styled text to your TextView
        alreadyHaveAcc.setText(spannableString);

        // Enable the LinkMovementMethod to make the clickable text actually clickable
        alreadyHaveAcc.setMovementMethod(LinkMovementMethod.getInstance());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
