package com.ranferi.ssrsi.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ranferi.ssrsi.model.User;

public class SharedPrefManager {

    // Shared preferences file name
    private static final String PREF_NAME = "MY_TAG";
    private static final String PASS_TAG= "my_pass_tag";

    private static SharedPrefManager instance;
    private static Context sContext;

    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedprefretrofit";

    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_NAME = "keyusername";
    private static final String KEY_USER_LAST_NAME = "keyuserlastname";
    private static final String KEY_USER_MAIDEN_NAME = "keyusermaidenname";
    private static final String KEY_USER_USER = "keyuseruser";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_GENDER = "keyusergender";

    private SharedPrefManager(Context context) {
        sContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(User user) {
        SharedPreferences sharedPreferences = sContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_LAST_NAME, user.getLastName());
        editor.putString(KEY_USER_MAIDEN_NAME, user.getMothersMaidenName());
        editor.putString(KEY_USER_USER, user.getUser());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        // editor.putString(KEY_USER_GENDER, user.getGender());
        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = sContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null)
            return true;
        return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = sContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Log.d("TT", "Estás en getUser,SharedPrefManager " + " last name: " + sharedPreferences.getString(KEY_USER_LAST_NAME, null));
        return new User(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_NAME, null),
                sharedPreferences.getString(KEY_USER_LAST_NAME, null),
                sharedPreferences.getString(KEY_USER_MAIDEN_NAME, null),
                sharedPreferences.getString(KEY_USER_USER, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null)
        );

        /* sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_NAME, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_GENDER, null)*/
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = sContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public void Setpassword(String password) {
        SharedPreferences sharedPreferences = sContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PASS_TAG, password);
        editor.apply();
    }
    public String getpassword() {
        SharedPreferences sharedPreferences = sContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASS_TAG, null);
    }
}
