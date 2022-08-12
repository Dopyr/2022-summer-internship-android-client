package com.example.mysqlapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private CommunicationHelper comm;

    private CardView searchCard, insertCard, fetchCard;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        comm = CommunicationHelper.getInstance();

        searchCard = findViewById(R.id.search_card);
        insertCard = findViewById(R.id.insert_card);
        fetchCard = findViewById(R.id.fetch_card);

        searchCard.setOnClickListener(this);
        insertCard.setOnClickListener(this);
        fetchCard.setOnClickListener(this);

    }


    public void onClick(View view) {

        Intent myIntent;

        switch (view.getId()) {
            case R.id.search_card:
                myIntent = new Intent(this, SearchActivity.class);
                startActivity(myIntent);
                break;

            case R.id.insert_card:
                myIntent = new Intent(this, InsertionActivity.class);
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
