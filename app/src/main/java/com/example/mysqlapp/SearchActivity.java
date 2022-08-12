package com.example.mysqlapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    private Button buttonSearch;
    private Button buttonReturn;
    private TextView textViewWrite;
    private EditText editTextSearch;
    private User loginInfo;
    private Login user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        loginInfo = new User();
        user = new Login();

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonReturn = findViewById(R.id.buttonReturn2);

        textViewWrite = findViewById(R.id.textView21);

        editTextSearch = findViewById(R.id.editText21);

        onButtonClick();
    }

    public void onButtonClick() {

        buttonSearch.setOnClickListener(v -> {

            buttonSearch.setEnabled(false);
            textViewWrite.setText("");

            new Thread(() -> {

                String idToSearch = editTextSearch.getText().toString();

                String result = CommunicationHelper.getInstance().searchData(idToSearch);

                SearchActivity.this.runOnUiThread(() -> {

                    editTextSearch.setText("");
                    textViewWrite.setText(result);
                    buttonSearch.setEnabled(true);
                });

            }).start();
        });

        buttonReturn.setOnClickListener(v -> {

            Intent myIntent;
            if (user.isAdmin()) {

                myIntent = new Intent(this, AdminActivity.class);

            } else {

                myIntent = new Intent(this, StudentActivity.class);

            }
            startActivity(myIntent);

        });
    }
}