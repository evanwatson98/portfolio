package com.example.keepin_It_Fresh;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

public class ShelfFilterLayout extends AppCompatActivity {

    ArrayList<String> choices = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelf_filter_box);
        //setupViews();
        /////////////////////////////////////////////////////////
        ///////Spinner for Sort////////////////////////////////
        /////////////////////////////////////////////////////////
        Spinner sortSpinner = findViewById(R.id.spinnerSortL);
        Button submitShelfFilter = findViewById(R.id.submitShelfFilterL);


        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(ShelfFilterLayout.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.shelfSortOptions));
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        choices.add(0,"-1");
        choices.add(1,"-2");


        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    //putExtra
                    choices.set(0,"0");
                }else if(position == 1){
                    choices.set(0,"1");
                } else if(position == 2){
                    choices.set(0,"2");
                } else if(position == 3){
                    choices.set(0,"3");
                } else if(position == 4){
                    choices.set(0,"4");
                }else if(position == 5){
                    choices.set(0,"5");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        /////////////////////////////////////////////////////////
        ///////Spinner for Filter////////////////////////////////
        /////////////////////////////////////////////////////////
        Spinner sortSpinner2 = (Spinner) findViewById(R.id.spinnerFilterL);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(ShelfFilterLayout.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.shelfFilterOptions));
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner2.setAdapter(filterAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    //putExtra
                    choices.set(1,"0");
                }else if(position == 1){
                    choices.set(1,"1");
                } else if(position == 2){
                    choices.set(1,"2");
                } else if(position == 3){
                    choices.set(1,"3");
                } else if(position == 4){
                    choices.set(1,"4");
                }
                else if(position == 5){
                    choices.set(1,"5");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        ///////////////////////////////////////////////////////////
        //Send information to Shelf2, so you know what to sort by//
        ///////////////////////////////////////////////////////////
        submitShelfFilter.setOnClickListener(view -> {
//                Intent i = new Intent(getApplicationContext(),Shelf2.class);
//                i.putExtra("choices",choices);
//                startActivity(i);

        });
    }
}
