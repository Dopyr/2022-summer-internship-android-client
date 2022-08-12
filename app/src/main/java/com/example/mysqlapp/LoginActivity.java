package com.example.mysqlapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private CommunicationHelper comm;
    private Button buttonToEnter;
    private EditText idEditText;
    private EditText passwordEditText;
    private User loginInfo;
    private Login user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ConstraintLayout constraintLayout = findViewById(R.id.loginMenu);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        comm = CommunicationHelper.getInstance();
        buttonToEnter = findViewById(R.id.buttonLogin);

        idEditText = findViewById(R.id.editText01);
        passwordEditText = findViewById(R.id.editTextPass01);

        buttonOnClick();

    }

    public void buttonOnClick() {


        buttonToEnter.setOnClickListener(v -> {

            new Thread(() -> {

                String idToEnter = idEditText.getText().toString();
                String passwordToEnter = passwordEditText.getText().toString();

                passwordToEnter = md5(passwordToEnter);

                loginInfo = CommunicationHelper.getInstance().loginToSql(idToEnter, passwordToEnter);

                Boolean isAdmin = loginInfo.isAdmin();
                user = new Login(Integer.parseInt(idToEnter), isAdmin);

                LoginActivity.this.runOnUiThread(() -> {

                    if (user.isAdmin()) {

                        Intent myIntent = new Intent(this, AdminActivity.class);
                        startActivity(myIntent);

                    } else if (loginInfo.isAdmin() == false) {

                        Intent myIntent = new Intent(this, StudentActivity.class);
                        startActivity(myIntent);
                    }
                });

            }).start();

        });

    }

    public static String md5(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String myHash = bytesToHex(digest);

            return myHash;

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

            return "";
        }
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}