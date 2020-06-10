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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AddFoodManually extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView datePickerTextView;
    DatePickerDialog.OnDateSetListener dateSetListener;
    ImageButton drawer_button;
    ArrayList<String> editItem;
    Button doneAdding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_manually);

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(new NavDrawer(this));
        //setting up client
        drawer_button = findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((DrawerLayout)findViewById(R.id.addfoodmanualdrawer)).openDrawer(Gravity.LEFT);
        });

        Intent i = getIntent();
        editItem = i.getStringArrayListExtra("shelfID");
        System.out.println("The id sent from shelf is: " + editItem);

        doneAdding = findViewById(R.id.doneAdding);

        doneAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveOn = new Intent(AddFoodManually.this, Shelf2.class);
                startActivity(moveOn);
            }
        });

        //declaring everything
        final TextView statusTextView = findViewById(R.id.statusTextView);
        datePickerTextView = findViewById(R.id.datePickerTextView);
        Button addFoodButton = findViewById(R.id.addFoodButton);
        final EditText foodNameEditText = findViewById(R.id.foodNameEditText);
        final Spinner quantitySpinner = findViewById(R.id.quantitySpinner), measurementSpinner = findViewById(R.id.measurementSpinner),
                foodGroupSpinner = findViewById(R.id.foodGroupSpinner), foodTypeSpinner = findViewById(R.id.foodTypeSpinner);
        final OkHttpClient myClient = new OkHttpClient();

        TextView addFoodTitle = findViewById(R.id.addFoodTitle);

        if(editItem!=null){
            addFoodTitle.setText("Edit Food");
            String name = editItem.get(1);
            foodNameEditText.setText(name);
            addFoodButton.setText("EDIT FOOD");
        }else{
            addFoodTitle.setText("Add Food");
        }

        //setting up the date picker
        datePickerTextView.setOnClickListener(view -> {
            //this gets today's date
            Calendar today = Calendar.getInstance();
            int year = today.get(Calendar.YEAR);
            int month = today.get(Calendar.MONTH);
            int day = today.get(Calendar.DAY_OF_MONTH);

            //choose theme here, also makes the date picker pop up
            DatePickerDialog dialog = new DatePickerDialog(AddFoodManually.this, android.R.style.Theme_Holo_Light, dateSetListener, year, month, day);
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
                if (position==1) {
                    foodTypeSpinner.setAdapter(fruitAdapter);
                }
                if (position==2) {
                    foodTypeSpinner.setAdapter(vegetableAdapter);
                }
                if (position==3) {
                    foodTypeSpinner.setAdapter(proteinAdapter);
                }
                if (position==4) {
                    foodTypeSpinner.setAdapter(grainAdapter);
                }
                if (position==5) {
                    foodTypeSpinner.setAdapter(dairyAdapter);
                }
                if (position==6) {
                    foodTypeSpinner.setAdapter(treatAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        addFoodButton.setOnClickListener(view -> {

            if (editItem == null) {

                //connecting to php
                String myUrl = "https://cgi.sice.indiana.edu/~team39/addFoodManuallyV4.php";

                //for the formbody that'll be posted
                String foodName = foodNameEditText.getText().toString();
                String foodQuantity = quantitySpinner.getSelectedItem().toString();
                String foodMeasurement = measurementSpinner.getSelectedItem().toString();
                String foodExpiration = datePickerTextView.getText().toString();

                String foodGroup = foodGroupSpinner.getSelectedItem().toString();
                String foodType = foodTypeSpinner.getSelectedItem().toString();
                String userid = readFile(0);
                String houseID = readFile(1);
                houseID = houseID.trim();
                userid = userid.trim();
                //building what i will post
                RequestBody myRequestBody = new FormBody.Builder()
                        .add("houseID", houseID)
                        .add("userid", userid)
                        .add("foodName", foodName)
                        .add("foodQuantity", foodQuantity)
                        .add("foodMeasurement", foodMeasurement)
                        .add("foodExpiration", foodExpiration)
                        .add("foodGroup", foodGroup)
                        .add("foodType", foodType)
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

                            AddFoodManually.this.runOnUiThread(() -> {
                                statusTextView.setText(myResponse);
                                if (statusTextView.getText().toString().contains("inserting NEW food") ||
                                        statusTextView.getText().toString().contains("inserting PRE-EXISTING food")) {
                                    Intent moveOn = new Intent(AddFoodManually.this, AddFoodManually.class);
                                    startActivity(moveOn);
                                }

                            });
                        }
                    });
                }
            }
            else {
                System.out.println("I see I'm an edit");
                String shelfId = editItem.get(0);
                String houseID = editItem.get(2);

                String myUrl = "https://cgi.sice.indiana.edu/~team39/updateShelf.php";

                //for the formbody that'll be posted
                String foodName = foodNameEditText.getText().toString();
                System.out.println("foodName is: " + foodName);
                String foodQuantity = quantitySpinner.getSelectedItem().toString();
                System.out.println("foodQuantity is: " + foodQuantity);
                String foodMeasurement = measurementSpinner.getSelectedItem().toString();
                System.out.println("foodMeasurement is: " + foodMeasurement);
                String foodExpiration = datePickerTextView.getText().toString();

                String strDate = "";

                if (!datePickerTextView.getText().equals("Select Date")) {
                    String[] exp = foodExpiration.split("-");
                    System.out.println(foodExpiration);
                    strDate = "" + exp[2] + "-" + exp[0] + "-" + exp[1];
                }
                System.out.println("Expiration is: " + strDate);
                String foodGroup = foodGroupSpinner.getSelectedItem().toString();
                System.out.println("foodMeasurement is: " + foodMeasurement);
                String foodType = foodTypeSpinner.getSelectedItem().toString();
                System.out.println("foodMeasurement is: " + foodMeasurement);
                String userid = readFile(0);
                houseID = houseID.trim();
                userid = userid.trim();
                System.out.println("houseID is: " + houseID);
                System.out.println("userid is: " + userid);
                System.out.println("shelfId is: " + shelfId);
                //building what i will post
                RequestBody myRequestBody = new FormBody.Builder()
                        .add("houseID", houseID)
                        .add("shelfID", shelfId)
                        .add("userid", userid)
                        .add("foodName", foodName)
                        .add("foodQuantity", foodQuantity)
                        .add("foodMeasurement", foodMeasurement)
                        .add("foodExpiration", strDate)
                        .add("foodGroup", foodGroup)
                        .add("foodType", foodType)
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
                            final String myResponse = "Editing your food now" + Objects.requireNonNull(response.body()).string();
                            System.out.println(myResponse);

                            AddFoodManually.this.runOnUiThread(() -> {
                                statusTextView.setText(myResponse);
                                openShelf();

                            });
                        }
                    });
                }

            }
        });
    }

    //goes back to the first page of add food
    public void openShelf() {
        Intent moveOn = new Intent(this, Shelf2.class);
        startActivity(moveOn);
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    //DO NOT DELETE THIS
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
