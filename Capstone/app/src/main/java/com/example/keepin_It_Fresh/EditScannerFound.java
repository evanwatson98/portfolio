package com.example.keepin_It_Fresh;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.drawerlayout.widget.DrawerLayout;
import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Objects;

import com.google.android.material.navigation.NavigationView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class EditScannerFound extends AppCompatActivity {
    /*
    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    public static final String EXTRA_SUBJECT = "com.example.application.example.EXTRA_SUBJECT";
    TextView datePickerTextView;
    */
    DatePickerDialog.OnDateSetListener dateSetListener;


    public static final String EXTRA_TITLE = "com.example.application.example.EXTRA_TITLE";

    public static final String EXTRA_SPLIT_NAME = "com.example.application.example.EXTRA_SPLIT_NAME";
    EditText foodNameEditText;
    EditText group;
    EditText type;
    TextView upcEdit;
    ImageButton drawer_button;
    TextView messageItem;
    ImageView testing;

    /*
    TextView imag;
    TextView extra;
    TextView extra2;
    TextView extra3;
    Button foodAdd;
    ImageButton edit;
    ImageView imageF;
    Spinner foodGroupSpinner;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_found);
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(new NavDrawer(this));
        messageItem = findViewById(R.id.messageItem);
        foodNameEditText = findViewById(R.id.foodNameEditText);
        group = findViewById(R.id.foodGroupEditText);
        type = findViewById(R.id.foodTypeEditText);
        drawer_button = findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((DrawerLayout)findViewById(R.id.editfoodfoundnavdrawer)).openDrawer(Gravity.LEFT);
        });
        testing = findViewById(R.id.testingURL);

        upcEdit = findViewById(R.id.upcEdit);


        Intent intent = getIntent();

        final String foodName = intent.getStringExtra(Scanner.EXTRA_TITLE);
        final String foodUpc = intent.getStringExtra(Scanner.EXTRA_TEXT);
        final String foodMessage = intent.getStringExtra(Scanner.EXTRA_SUBJECT);
        final String foodGroup = intent.getStringExtra(Scanner.EXTRA_COMPONENT_NAME);
        final String foodType = intent.getStringExtra(Scanner.EXTRA_EMAIL);
        final String imageURL = intent.getStringExtra(Scanner.EXTRA_SPLIT_NAME);
        //String url = "http://cgi.sice.indiana.edu/~jplazony/corn_pop.jpg";
        Glide
                .with(this)
                .load(imageURL)
                .into(testing);

        foodNameEditText.setText(foodName);
        upcEdit.setText(foodUpc);
        messageItem.setText(foodMessage);
        group.setText(foodGroup);

        type.setText(foodType);
//        int duration = Toast.LENGTH_LONG;
//        Toast toast = Toast.makeText(this, imageURL, duration);
//        toast.show();
        //imag.setText(foodImage);


        //setting up client
        final OkHttpClient myClient = new OkHttpClient();

        //declaring everything
        Button addFoodButton = findViewById(R.id.addFoodButton);
        final TextView statusTextView = findViewById(R.id.statusTextView);
        final TextView datePickerTextView = findViewById(R.id.datePickerTextView);
        final EditText foodNameEditText = findViewById(R.id.foodNameEditText);
        final TextView upcEdit = findViewById(R.id.upcEdit);
        final Spinner quantitySpinner = findViewById(R.id.quantitySpinner);
        final Spinner measurementSpinner = findViewById(R.id.measurementSpinner);
        final EditText foodGroupSpinner = findViewById(R.id.foodGroupEditText);
        final EditText foodTypeSpinner = findViewById(R.id.foodTypeEditText);

        //setting up the date picker
        datePickerTextView.setOnClickListener(view -> {
            //this gets today's date
            Calendar today = Calendar.getInstance();
            int year = today.get(Calendar.YEAR);
            int month = today.get(Calendar.MONTH);
            int day = today.get(Calendar.DAY_OF_MONTH);

            //choose theme here, also makes the date picker pop up
            DatePickerDialog dialog = new DatePickerDialog(EditScannerFound.this, android.R.style.Theme_Holo_Light, dateSetListener, year, month, day);
            dialog.show();
        });

        //this makes it so that the textview changes so it fits the date selected
        dateSetListener = (datePicker, year, month, day) -> {
            //month starts at zero so you have to add one
            month = month + 1;
            String dayString = Integer.toString(day);
            String monthString = Integer.toString(month);
            if (dayString.length() == 1){
                dayString = "0" + dayString;
            }
            if (monthString.length() == 1){
                monthString = "0" + monthString;
            }
            String expirationDate = monthString + "-" + dayString + "-" + year;
            datePickerTextView.setText(expirationDate);
        };

        //for measurement spinner
        ArrayAdapter<CharSequence> measurementAdapter = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurementSpinner.setAdapter(measurementAdapter);

        //for quantity spinner
        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(this, R.array.quantity, android.R.layout.simple_spinner_item);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(quantityAdapter);





        final ArrayAdapter<CharSequence> fruitAdapter = ArrayAdapter.createFromResource(this, R.array.fruit, android.R.layout.simple_spinner_item);
        fruitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<CharSequence> vegetableAdapter = ArrayAdapter.createFromResource(this, R.array.vegetable, android.R.layout.simple_spinner_item);
        vegetableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<CharSequence> proteinAdapter = ArrayAdapter.createFromResource(this, R.array.protein, android.R.layout.simple_spinner_item);
        proteinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<CharSequence> grainAdapter = ArrayAdapter.createFromResource(this, R.array.grain, android.R.layout.simple_spinner_item);
        grainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<CharSequence> dairyAdapter = ArrayAdapter.createFromResource(this, R.array.dairy, android.R.layout.simple_spinner_item);
        dairyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<CharSequence> treatAdapter = ArrayAdapter.createFromResource(this, R.array.treat, android.R.layout.simple_spinner_item);
        treatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);







        addFoodButton.setOnClickListener(view -> {

            //connecting to php
            String myUrl = "https://cgi.sice.indiana.edu/~team39/editFoodScannerTEST.php";

            //for the formbody that'll be posted+
            String foodName1 = foodNameEditText.getText().toString();
            String foodQuantity = quantitySpinner.getSelectedItem().toString();
            String foodMeasurement = measurementSpinner.getSelectedItem().toString();
            String foodExpiration = datePickerTextView.getText().toString();
            String foodGroup1 = foodGroupSpinner.getText().toString();
            String foodType1 = foodTypeSpinner.getText().toString();
            String userid = readFile(0).trim();
            String houseID = readFile(1).trim();
            //building what i will post
            RequestBody myRequestBody = new FormBody.Builder()
                    .add("houseID", houseID)
                    .add("userid", userid)
                    .add("foodName", foodName1)
                    .add("foodQuantity", foodQuantity)
                    .add("foodMeasurement", foodMeasurement)
                    .add("foodExpiration", foodExpiration)
                    .add("foodGroup", foodGroup1)
                    .add("foodType", foodType1)
                    .add("upc", foodUpc)
                    .build();

            final Request myRequest = new Request.Builder()
                    .url(myUrl)
                    .post(myRequestBody)
                    .build();

            //if all the entries aren't filled
            if (foodNameEditText.getText().toString().equals("")||
                    quantitySpinner.getSelectedItem().toString().equals("")||
                    measurementSpinner.getSelectedItem().toString().equals("Select Measurement")||
                    foodGroupSpinner.getText().toString().equals("Select Food Group")||
                    foodTypeSpinner.getText().toString().equals("Select Type")||
                    foodTypeSpinner.getText().toString().equals("Select Food Group First")||
                    datePickerTextView.getText().equals("Select Date")) {
                String statusFail = "Please fill in all entries";
                statusFail += "\n Food Name:" + foodNameEditText.getText();
                statusFail += "\n Quantity:" + quantitySpinner.getSelectedItem().toString();
                statusFail += "\n Measurement:" + measurementSpinner.getSelectedItem().toString();
                statusFail += "\n Expiration:" + datePickerTextView.getText();
                statusFail += "\n Food Group:" + foodGroupSpinner.getText().toString();
                statusFail += "\n Food Type:" + foodTypeSpinner.getText().toString();
                statusTextView.setText(statusFail);
            }
            //if all the entries are filled
            else {
                myClient.newCall(myRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String myResponse = "Adding your food now" + Objects.requireNonNull(response.body()).string();

                        EditScannerFound.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                statusTextView.setText(myResponse);
                                if (statusTextView.getText().toString().contains("inserting NEW food")||
                                        statusTextView.getText().toString().contains("inserting PRE-EXISTING food")) {
                                    openShelf();
                                }

                            }
                        });
                    }
                });
            }
        });
    }

    //goes back to the first page of add food
    public void openShelf() {
        Intent moveOn = new Intent(this, Shelf2.class);
        startActivity(moveOn);
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    //DO NOT DELETE THIS
    /*
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    */

    public String readFile(int option){
        if(option == 0) {
            try {
                String fileName = "userInformation.txt";
                FileInputStream fileInputStream = openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuffer = new StringBuilder();

                String lines;
                while ((lines = bufferedReader.readLine()) != null) {
                    stringBuffer.append(lines).append("\n");
                }
                return stringBuffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else{
            try {
                String fileName = "houseID.txt";
                FileInputStream fileInputStream = openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuffer = new StringBuilder();

                String lines;
                while ((lines = bufferedReader.readLine()) != null) {
                    stringBuffer.append(lines).append("\n");
                }
                return stringBuffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
}}