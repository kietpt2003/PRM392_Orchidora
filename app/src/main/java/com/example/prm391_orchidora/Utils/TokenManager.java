package com.example.prm391_orchidora.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.prm391_orchidora.Screens.Category.ManageCategoryScreen;

public class TokenManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String KEY_ACCESS_TOKEN = "token";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.apply();
    }
    public String getToken(Context context) {
        return getSharedPreferences(context).getString(KEY_ACCESS_TOKEN, null);
    }

    public void clearToken(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_ACCESS_TOKEN);
        editor.apply();
        String token = getToken(context);
        if (token == null) {
            Log.d("TokenManager nè", "Token has been successfully cleared.");
        } else {
            Log.d("TokenManager nè", "Failed to clear the token.");
        }
    }
}
