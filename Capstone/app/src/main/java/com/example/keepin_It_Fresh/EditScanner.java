package com.example.keepin_It_Fresh;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Objects;

public class EditScanner extends AppCompatActivity {

    /*
    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    public static final String EXTRA_SUBJECT = "com.example.application.example.EXTRA_SUBJECT";
    TextView datePickerTextView;
    */
    DatePickerDialog.OnDateSetListener dateSetListener;


    public static final String EXTRA_TITLE = "com.example.application.example.EXTRA_TITLE";
    EditText foodNameEditText;
    TextView upcEdit;
    ImageButton drawer_button;
    TextView messageItem;

    /*
    TextView extra;
    TextView extra2;
    TextView extra3;
    Button foodAdd;
    TextView imag;
    ImageButton edit;
    ImageView imageF;
    Spinner foodGroupSpinner;
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_notfound);
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(new NavDrawer(this));
        messageItem = findViewById(R.id.messageItem);
        foodNameEditText = findViewById(R.id.foodNameEditText);
        drawer_button = findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((DrawerLayout) findViewById(R.id.editfoodnotfoundnavdrawer)).openDrawer(Gravity.LEFT);
        });
        upcEdit = findViewById(R.id.upcEdit);


        Intent intent = getIntent();
        final String foodName = intent.getStringExtra(Scanner.EXTRA_TITLE);
        final String foodUpc = intent.getStringExtra(Scanner.EXTRA_TEXT);
        final String foodMessage = intent.getStringExtra(Scanner.EXTRA_SUBJECT);

        foodNameEditText.setText(foodName);
        upcEdit.setText(foodUpc);
        messageItem.setText(foodMessage);
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
        final Spinner foodGroupSpinner = findViewById(R.id.foodGroupSpinner);
        final Spinner foodTypeSpinner = findViewById(R.id.foodTypeSpinner);

        //setting up the date picker
        datePickerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this gets today's date
                Calendar today = Calendar.getInstance();
                int year = today.get(Calendar.YEAR);
                int month = today.get(Calendar.MONTH);
                int day = today.get(Calendar.DAY_OF_MONTH);

                //choose theme here, also makes the date picker pop up
                DatePickerDialog dialog = new DatePickerDialog(EditScanner.this, android.R.style.Theme_Holo_Light, dateSetListener, year, month, day);
                dialog.show();
            }
        });

        //this makes it so that the textview changes so it fits the date selected
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //month starts at zero so you have to add one
                month = month + 1;
                String dayString = Integer.toString(day);
                String monthString = Integer.toString(month);
                if (dayString.length() == 1) {
                    dayString = "0" + dayString;
                }
                if (monthString.length() == 1) {
                    monthString = "0" + monthString;
                }
                String expirationDate = monthString + "-" + dayString + "-" + year;
                datePickerTextView.setText(expirationDate);
            }
        };

        //for measurement spinner
        ArrayAdapter<CharSequence> measurementAdapter = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurementSpinner.setAdapter(measurementAdapter);

        //for quantity spinner
        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(this, R.array.quantity, android.R.layout.simple_spinner_item);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(quantityAdapter);

        //for food group spinner
        ArrayAdapter<CharSequence> foodGroupAdapter = ArrayAdapter.createFromResource(this, R.array.foodGroups, android.R.layout.simple_spinner_item);
        foodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodGroupSpinner.setAdapter(foodGroupAdapter);

        //setting up different array adapters for the different food types from food groups
        final ArrayAdapter<CharSequence> emptyAdapter = ArrayAdapter.createFromResource(this, R.array.empty, android.R.layout.simple_spinner_item);
        emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter displayed when the user hasn't chosen a food group yet
        foodTypeSpinner.setAdapter(emptyAdapter);

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

        //this changes the foodtype spinner based off the food group selected
        foodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 1) {
                    foodTypeSpinner.setAdapter(fruitAdapter);
                }
                if (position == 2) {
                    foodTypeSpinner.setAdapter(vegetableAdapter);
                }
                if (position == 3) {
                    foodTypeSpinner.setAdapter(proteinAdapter);
                }
                if (position == 4) {
                    foodTypeSpinner.setAdapter(grainAdapter);
                }
                if (position == 5) {
                    foodTypeSpinner.setAdapter(dairyAdapter);
                }
                if (position == 6) {
                    foodTypeSpinner.setAdapter(treatAdapter);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        addFoodButton.setOnClickListener(view -> {

            //connecting to php
            String myUrl = "https://cgi.sice.indiana.edu/~team39/editFoodScannerTEST.php";

            //for the formbody that'll be posted
            String foodName1 = foodNameEditText.getText().toString();
            String foodQuantity = quantitySpinner.getSelectedItem().toString();
            String foodMeasurement = measurementSpinner.getSelectedItem().toString();
            String foodExpiration = datePickerTextView.getText().toString();
            String foodGroup = foodGroupSpinner.getSelectedItem().toString();
            String foodType = foodTypeSpinner.getSelectedItem().toString();
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
                    .add("foodGroup", foodGroup)
                    .add("foodType", foodType)
                    .add("upc", foodUpc)
                    .build();

            final Request myRequest = new Request.Builder()
                    .url(myUrl)
                    .post(myRequestBody)
                    .build();

            //if all the entries aren't filled
            if (foodNameEditText.getText().toString().equals("") ||
                    quantitySpinner.getSelectedItem().toString().equals("") ||
                    measurementSpinner.getSelectedItem().toString().equals("Select Measurement") ||
                    foodGroupSpinner.getSelectedItem().toString().equals("Select Food Group") ||
                    foodTypeSpinner.getSelectedItem().toString().equals("Select Type") ||
                    foodTypeSpinner.getSelectedItem().toString().equals("Select Food Group First") ||
                    datePickerTextView.getText().equals("Select Date")) {
                String statusFail = "Please fill in all entries";
                statusFail += "\n Food Name:" + foodNameEditText.getText();
                statusFail += "\n Quantity:" + quantitySpinner.getSelectedItem().toString();
                statusFail += "\n Measurement:" + measurementSpinner.getSelectedItem().toString();
                statusFail += "\n Expiration:" + datePickerTextView.getText();
                statusFail += "\n Food Group:" + foodGroupSpinner.getSelectedItem().toString();
                statusFail += "\n Food Type:" + foodTypeSpinner.getSelectedItem().toString();
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

                        EditScanner.this.runOnUiThread(() -> {
                            statusTextView.setText(myResponse);
                            if (statusTextView.getText().toString().contains("inserting NEW food") ||
                                    statusTextView.getText().toString().contains("inserting PRE-EXISTING food")) {
                                openShelf();
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

    public void onNothingSelected(AdapterView<?> adapterView) {

    }

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
    }

}