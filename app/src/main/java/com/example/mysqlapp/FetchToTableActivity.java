package com.example.mysqlapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class FetchToTableActivity extends AppCompatActivity {

    private Button buttonFetchData;
    private Button buttonReturn;
    private ListView listView;
    private User loginInfo;
    private Login user;
    private String[] params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);

        loginInfo = new User();

        buttonFetchData = findViewById(R.id.buttonFetch);
        buttonReturn = findViewById(R.id.buttonReturn4);

        listView = findViewById(R.id.listView);

        onButtonClick();
    }

    public void onButtonClick() {


        buttonFetchData.setOnClickListener(v -> {

            buttonFetchData.setEnabled(false);

            new Thread(() -> {
                params = CommunicationHelper.getInstance().fetchData();

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, android.R.id.text1, params);

                FetchToTableActivity.this.runOnUiThread(() -> {

                    listView.setAdapter(dataAdapter);
                    buttonFetchData.setEnabled(true);

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