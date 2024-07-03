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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.prm391_orchidora.Controller.AuthController;
import com.example.prm391_orchidora.Models.Account.AccountResponse;
import com.example.prm391_orchidora.Models.Account.CreateAccountRequest;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Home.HomeScreen;
import com.example.prm391_orchidora.Utils.TokenManager;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpScreen extends AppCompatActivity implements AuthController.RegisterCallBack {
    private AuthController authController;
    private TextInputEditText name;
    private TextInputEditText email;
    private TextInputEditText phoneNumber;
    private TextInputEditText address;
    private TextInputEditText password;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup_layout);

        signUpBtn = findViewById(R.id.signUpBtn);

        authController = new AuthController(this);

        signUpBtn.setOnClickListener(v -> {
            name = findViewById(R.id.editTextName);
            email = findViewById(R.id.editTextEmail);
            phoneNumber = findViewById(R.id.editTextPhoneNumber);
            address = findViewById(R.id.editTextAddress);
            password = findViewById(R.id.editTextPasswordSignUp);
            CreateAccountRequest requestBody = new CreateAccountRequest(
                    email.getText().toString(),
                    password.getText().toString(),
                    name.getText().toString(),
                    phoneNumber.getText().toString(),
                    address.getText().toString()
            );
            authController.fetchRegister(requestBody);
        });

        TextView alreadyHaveAcc = findViewById(R.id.alreadyHaveAcc);
        NavigatorFunction("Already have an account? Sign In", 25, alreadyHaveAcc, true, this);

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
                if (!isBack) {
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
    public void onRegisterSuccess(AccountResponse accountResponse) {
        TokenManager.saveToken(SignUpScreen.this, accountResponse.getId());
        Intent intent = new Intent(SignUpScreen.this, HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SignUpScreen.this.startActivity(intent);
        // Optionally finish the LoginScreen activity
        finish();
    }

    @Override
    public void onRegisterError(ErrorResponse errorResponse) {
        Toast.makeText(this,errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
