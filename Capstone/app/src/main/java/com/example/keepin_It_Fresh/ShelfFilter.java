package com.example.keepin_It_Fresh;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

public class ShelfFilter extends AppCompatActivity {

    ImageButton drawer_button;

    ArrayList<String> choices = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf_filter_page);
        //setupViews();
        /////////////////////////////////////////////////////////
        ///////Spinner for Sort////////////////////////////////
        /////////////////////////////////////////////////////////
        drawer_button = findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((DrawerLayout)findViewById(R.id.shelffilternavdrawer)).openDrawer(Gravity.LEFT);
        });
        Spinner sortSpinner = findViewById(R.id.spinnerSort);
        Button submitShelfFilter = findViewById(R.id.submitShelfFilter);

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(ShelfFilter.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.shelfSortOptions));
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
        Spinner filterSpinner = findViewById(R.id.spinnerFilter);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(ShelfFilter.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.shelfFilterOptions));
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(filterAdapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);


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
            Intent i = new Intent(getApplicationContext(),Shelf2.class);
            i.putExtra("choices",choices);
            startActivity(i);
        });
    }
}
