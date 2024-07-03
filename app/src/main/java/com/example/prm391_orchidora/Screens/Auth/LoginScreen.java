package com.example.prm391_orchidora.Screens.Auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm391_orchidora.Controller.AuthController;
import com.example.prm391_orchidora.Models.Account.AccountResponse;
import com.example.prm391_orchidora.Models.Auth.LoginRequest;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Home.HomeScreen;
import com.example.prm391_orchidora.Screens.Home.MngHomeScreen;
import com.example.prm391_orchidora.Utils.TokenManager;
import com.google.android.material.textfield.TextInputEditText;

public class LoginScreen extends AppCompatActivity implements AuthController.LoginCallBack {
    private AuthController authController;
    private TextInputEditText editTextEmailAddress;
    private TextInputEditText editTextPassword;
    Button signInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_layout);

        authController = new AuthController(this);

        signInBtn = findViewById(R.id.signInBtn);

        signInBtn.setOnClickListener(view -> {
            editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
            editTextPassword = findViewById(R.id.editTextPassword);

            String email = editTextEmailAddress.getText().toString();
            String password = editTextPassword.getText().toString();
            LoginRequest requestBody = new LoginRequest(email, password);
            authController.fetchAuth(requestBody);
        });

        TextView dontHaveAcc = findViewById(R.id.dontHaveAcc);
        NavigatorFunction("Don’t have account? Sign Up",20, dontHaveAcc, false, LoginScreen.this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void NavigatorFunction(String navString, int start, TextView textView, boolean isBack, Context context) {
        SpannableString spannableString = new SpannableString(navString);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false); // Vô hiệu hóa gạch chân
            }
            @Override
            public void onClick(View view) {
                if(!isBack) {
                    Intent intent = new Intent(context, SignUpScreen.class);
                    context.startActivity(intent);
                } else {
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }

            }
        };
        // Set the ClickableSpan to the desired portion of the text ("Sign Up")
        spannableString.setSpan(clickableSpan, start, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Apply a ForegroundColorSpan to change the color of the "Sign Up" text
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#817C7C")); // Change color as needed
        spannableString.setSpan(foregroundColorSpan, start, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the styled text to your TextView
        textView.setText(spannableString);

        // Enable the LinkMovementMethod to make the clickable text actually clickable
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onLoginSuccess(AccountResponse accountResponse) {
        TokenManager.saveToken(LoginScreen.this, accountResponse.getId());
        Intent intent;
        if (accountResponse.getRole().equals("USER")) {
            intent = new Intent(LoginScreen.this, HomeScreen.class);
        } else {
            intent = new Intent(LoginScreen.this, MngHomeScreen.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        LoginScreen.this.startActivity(intent);
        // Optionally finish the LoginScreen activity
        finish();
    }

    @Override
    public void onLoginError(ErrorResponse errorResponse) {
        Toast.makeText(this,errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
