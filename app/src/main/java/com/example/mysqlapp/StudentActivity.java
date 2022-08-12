package com.example.mysqlapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView searchCard, insertCard, fetchCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_student);

        searchCard = findViewById(R.id.search_card);
        fetchCard = findViewById(R.id.fetch_card);

        searchCard.setOnClickListener(this);
        fetchCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent myIntent;

        switch (view.getId()) {

            case R.id.search_card:
                myIntent = new Intent(this, SearchActivity.class);
                startActivity(myIntent);
                break;

            case R.id.fetch_card:
                myIntent = new Intent(this, FetchToTableActivity.class);
                startActivity(myIntent);
                break;

            default:
                break;

        }
    }
}