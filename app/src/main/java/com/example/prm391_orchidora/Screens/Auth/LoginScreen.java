package com.example.prm391_orchidora.Screens.Auth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Home.HomeScreen;

public class LoginScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_layout);

        TextView dontHaveAcc = findViewById(R.id.dontHaveAcc);
        SpannableString spannableString = new SpannableString("Don’t have account? Sign Up");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false); // Vô hiệu hóa gạch chân
            }
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, SignUpScreen.class);
                startActivity(intent);
            }
        };
        // Set the ClickableSpan to the desired portion of the text ("Sign Up")
        spannableString.setSpan(clickableSpan, 20, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Apply a ForegroundColorSpan to change the color of the "Sign Up" text
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#BFA4DC")); // Change color as needed
        spannableString.setSpan(foregroundColorSpan, 20, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the styled text to your TextView
        dontHaveAcc.setText(spannableString);

        // Enable the LinkMovementMethod to make the clickable text actually clickable
        dontHaveAcc.setMovementMethod(LinkMovementMethod.getInstance());

        Button signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
