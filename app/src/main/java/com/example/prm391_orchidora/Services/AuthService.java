package com.example.prm391_orchidora.Services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.prm391_orchidora.Screens.Auth.SignUpScreen;
import com.example.prm391_orchidora.Screens.Home.HomeScreen;

public class AuthService {
    public static void SignIn(Button signInBtn, Context loginScreen) {
        signInBtn.setOnClickListener(view -> {
            Intent intent = new Intent(loginScreen, HomeScreen.class);
            loginScreen.startActivity(intent);
        });
    }

    public static void NavigatorFunction(String navString, int start, TextView textView, boolean isBack, Context context) {
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
}
