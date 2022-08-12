package com.example.mysqlapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InsertionActivity extends AppCompatActivity {

    private EditText editTextInsertID;
    private EditText editTextInsertName;
    private EditText editTextInsertSurname;
    private EditText editTextMajor;
    private EditText editTextGPA;

    private TextView textViewMessage;

    private Button buttonInsert;
    private Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        buttonInsert = findViewById(R.id.buttonInsert);
        buttonReturn = findViewById(R.id.buttonReturn3);
        
        editTextInsertID = findViewById(R.id.editText31);
        editTextInsertName = findViewById(R.id.editText32);
        editTextInsertSurname = findViewById(R.id.editText33);
        editTextMajor = findViewById(R.id.editText34);
        editTextGPA = findViewById(R.id.editText35);

        onButtonClick();

    }

    public void onButtonClick() {

        buttonInsert.setOnClickListener(v -> {

            buttonInsert.setEnabled(false);

            new Thread(() -> {

                String id = editTextInsertID.getText().toString();
                String name = editTextInsertName.getText().toString();
                String surname = editTextInsertSurname.getText().toString();
                String major = editTextMajor.getText().toString();
                String gpa = editTextGPA.getText().toString();

                boolean result = CommunicationHelper.getInstance().insertData(id, name, surname, major, gpa);

                buttonInsert.setEnabled(true);

            }).start();
        });

        buttonReturn.setOnClickListener(v -> {

            Intent myIntent = new Intent(this, AdminActivity.class);
            startActivity(myIntent);

        });
    }
}