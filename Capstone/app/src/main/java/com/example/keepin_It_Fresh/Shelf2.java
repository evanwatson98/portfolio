package com.example.keepin_It_Fresh;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Shelf2 extends AppCompatActivity {
    ImageButton edit;
    ImageButton shelfFilter;
    ImageButton searchShelfButton;

    EditText searchShelf;
    private ImageButton addfoodbutton;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<String> expiration = new ArrayList<>();
    ArrayList<String> measurement = new ArrayList<>();
    ArrayList<String> imageURL = new ArrayList<>();
    //ArrayList<Integer> shelfBoxes = new ArrayList<Integer>();
    ArrayList<String> itemID = new ArrayList<>();
    ArrayList<String> deleteQueue = new ArrayList<>();


    ArrayList<String> delteItemID = new ArrayList<>();

    ArrayList<String> nameSorted = new ArrayList<>();
    ArrayList<String> quantitySorted = new ArrayList<>();
    ArrayList<String> expirationSorted = new ArrayList<>();
    ArrayList<String> measurementSorted = new ArrayList<>();
    ArrayList<String> imageURLSorted = new ArrayList<>();
    ArrayList<String> itemIDSorted = new ArrayList<>();

    String search;

    String houseID;

    String resp;

    LinearLayout itemBox;
    LinearLayout shelfConfirmation;

    Boolean isSearch = false;


    TextView setExp;
    TextView setQty;
    TextView itemName;
    ImageView shelfImage;
    CheckBox editCheckBox;
    ImageButton drawer_button;
    Button editSubmit;
    Button removeSubmit;
    Button cancelEdit;
    Boolean isEdit = false;
    JSONArray data;

    /*
    TextView test;
    LinearLayout filterBox;
    TextView hideBox;
    Spinner filt;
    Spinner sort;
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf_page);
        setupViews();
        FirebaseMessaging.getInstance().subscribeToTopic(GlobalVars.getInstance().username+"keepinitfreshtest");
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(new NavDrawer(this));
        drawer_button = findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((DrawerLayout)findViewById(R.id.shelfnavdrawer)).openDrawer(Gravity.LEFT);
        });

        makeShelfContainer();

        Bundle extras = getIntent().getExtras();
        try {
            if(extras.get("text").equals("")){
                throw new Exception();
            }
            final AlertDialog.Builder builder = new AlertDialog.Builder(Shelf2.this);
            builder.setMessage((String)extras.get("text"));
            builder.setTitle("Expiration Notice!");
            builder.setCancelable(true);
            builder.setNegativeButton("Ok", (d, which) -> d.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e){

        }

        edit.setOnClickListener(view -> {

            if(!isEdit) {
                isEdit = true;
                for(int i = 0; i < itemBox.getChildCount(); i++) {
                    ConstraintLayout currentlayout = (ConstraintLayout) itemBox.getChildAt(i);
                    itemName = currentlayout.findViewById(R.id.itemName);
                    CheckBox newCheckbox = currentlayout.findViewById(R.id.editCheckBox);

                    if(!itemName.getText().equals("")) {
                        newCheckbox.setVisibility(View.VISIBLE);
                    }
                }

                shelfConfirmation = findViewById(R.id.shelfContent);

                LayoutInflater li = getLayoutInflater();

                LinearLayout newItemContainer;
                newItemContainer = (LinearLayout) li.inflate(R.layout.edit_shelf_box, null);

                shelfConfirmation.addView(newItemContainer, 1);

                removeSubmit =  newItemContainer.findViewById(R.id.removeSubmit);
                editSubmit =  newItemContainer.findViewById(R.id.editSubmit);
                cancelEdit =  newItemContainer.findViewById(R.id.cancelEdit);
                ImageButton addfoodbutton2 = findViewById(R.id.add_food_button2);
                removeSubmit.setVisibility(View.VISIBLE);
                cancelEdit.setVisibility(View.VISIBLE);

                addfoodbutton2.setOnClickListener(view1 -> {
                    Intent intent = new Intent(view.getContext(), FoodAddNavigation.class);
                    startActivity(intent);
                });

                removeSubmit.setOnClickListener(view1 -> {
                    System.out.println("Clicked on remove");
                    System.out.println("The id for food: " + itemIDSorted.toString());
                    editItem(1);

//                    itemBox.removeAllViews();
//                    makeShelfContainer();
                    for (int i = 0; i < itemIDSorted.size(); i++) {
                        for(String id : deleteQueue){
                            if(itemIDSorted.get(i).equals(id)){
                                itemBox.removeViewAt(i);
                                LayoutInflater li2 = getLayoutInflater();
                                ConstraintLayout newItemContainer2;
                                newItemContainer2 = (ConstraintLayout) li.inflate(R.layout.shelf_food_box, null);
                                itemBox.addView(newItemContainer2, i);
                                editCheckBox = newItemContainer2.findViewById(R.id.editCheckBox);
                                editCheckBox.setVisibility(View.INVISIBLE);

                                itemName = newItemContainer2.findViewById(R.id.itemName);
                                itemName.setVisibility(View.INVISIBLE);

                                setQty = newItemContainer2.findViewById(R.id.setQty);
                                setQty.setVisibility(View.INVISIBLE);

                                setExp = newItemContainer2.findViewById(R.id.setExp);
                                setExp.setVisibility(View.INVISIBLE);

                                shelfImage = newItemContainer2.findViewById(R.id.shelfImage);
                                shelfImage.setVisibility(View.INVISIBLE);

                                TextView exp = newItemContainer2.findViewById(R.id.exp);
                                exp.setVisibility(View.INVISIBLE);
                                TextView qty = newItemContainer2.findViewById(R.id.qty);
                                qty.setVisibility(View.INVISIBLE);

                        }

                        }

                    }
                });

                editSubmit.setOnClickListener(view1 -> {

                    editItem(2);
                });
            }
            else{
                isEdit = false;
                for(int i = 0; i < itemBox.getChildCount(); i++) {
                    ConstraintLayout currentlayout = (ConstraintLayout) itemBox.getChildAt(i);
                    CheckBox newCheckbox = currentlayout.findViewById(R.id.editCheckBox);
                    newCheckbox.setChecked(false);
                    newCheckbox.setVisibility(View.INVISIBLE);
                }
                shelfConfirmation.removeViewAt(1);

            }

            cancelEdit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    isEdit = false;
                    for(int i = 0; i < itemBox.getChildCount(); i++) {
                        ConstraintLayout currentlayout = (ConstraintLayout) itemBox.getChildAt(i);
                        CheckBox newCheckbox = currentlayout.findViewById(R.id.editCheckBox);
                        newCheckbox.setChecked(false);
                        newCheckbox.setVisibility(View.INVISIBLE);
                    }
                    shelfConfirmation.removeViewAt(1);
                }
            });

        });


        shelfFilter.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ShelfFilter.class);
            startActivity(intent);
//                buildFilter();
        });

        searchShelfButton.setOnClickListener(view -> {
            search = searchShelf.getText().toString();

            isSearch = true;

            if(!search.equals("")) {

                isSearch = false;
                makeContainer();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            makeContainer();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });



    }
    public void setupViews () {
        edit = findViewById(R.id.editPencil);
        shelfFilter =  findViewById(R.id.shelfFilter);
        editSubmit = findViewById(R.id.editSubmit);
        removeSubmit =  findViewById(R.id.removeSubmit);
        cancelEdit = findViewById(R.id.cancelEdit);
        removeSubmit = findViewById(R.id.removeSubmit);
        editSubmit = findViewById(R.id.editSubmit);
        cancelEdit = findViewById(R.id.cancelEdit);
        searchShelf = findViewById(R.id.searchShelf);
        searchShelfButton = findViewById(R.id.searchShelfButton);
    }

    public void makeShelfContainer(){
        itemBox = findViewById(R.id.shelfBoxParent);

        ///////         Grab the user's Shelf Informstion ////
        //////////////////////////////////////////////////////
        OkHttpClient client = new OkHttpClient();

        String url = "http://cgi.soic.indiana.edu/~team39/selectUserShelf.php";

        String userid = readFile(0);

        houseID = readFile(1);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject rootJson = new JSONObject();
        JSONObject dataJson = new JSONObject();
        JSONObject myjson = new JSONObject();
        try {
            System.out.println("HouseID " +houseID);
            dataJson.put("houseId", houseID.replace("\n",""));
            dataJson.put("userId", userid.replace("\n",""));
            myjson.put("data", dataJson);
            rootJson.put("input", myjson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest MyJSONRequest = new JsonObjectRequest("http://cgi.sice.indiana.edu/~team39/selectIngrediants.php",
                rootJson,
                response -> {
                    //org.json.JSONArray myArray;
                    try {
                        System.out.println(response);
                        data = (JSONArray) response.get("data");
                        makeContainer();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, Throwable::printStackTrace);
        requestQueue.add(MyJSONRequest);

    }

    public void makeContainer() {


            itemBox.removeAllViews();
            itemIDSorted.clear();
            try {
                ArrayList<JSONObject> sorter = new ArrayList<>();

                if(!isSearch){
                    for (int i = 0; i < data.length(); i++) {
                        sorter.add((JSONObject) data.get(i));
                    }
                }
                else{
                    for(int i = 0; i < data.length(); i++){
                        JSONObject current = (JSONObject) data.get(i);
                        if(((String)current.get("name")).contains(search.trim())){
                            sorter.add(current);
                        }
                    }
                }
                Intent i = getIntent();
                ArrayList<String> choices = i.getStringArrayListExtra("choices");
                String sort;
                //String filt;
                try {
                    //filt = choices.get(1);
                    sort = choices.get(0);
                } catch (Exception e){
                    System.out.println("Choices doesnt work");
                    sort = "";
                    //filt = "";
                } finally {
                    System.out.println("Choices works");
                }

                final String sort2 = sort;
                sorter.sort((p1, p2) -> {
                    switch (sort2) {
                        case "2":
                            return -1;
                        case "3":

                            return 0;

                        case "4":
                            try {
                                System.out.println("Sorting by date");
                                String date1 = p1.get("expiration").toString();
                                String date2 = p2.get("expiration").toString();
                                //System.out.print(date1);
                                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                                //System.out.println(dateformat.parse(date1));
                                //System.out.println("Date1: "+date1+"\nDate2: "+date2);
                                return dateformat.parse(date2).compareTo(dateformat.parse(date1));

                            } catch (Exception e) {
                                e.printStackTrace();
                                return 0;
                            }
                        default:
                            return 0;
                    }
                });

                for (int j = 0; j < sorter.size(); j++) {

                    JSONObject current = sorter.get(j);
                    LayoutInflater li = getLayoutInflater();
                    ConstraintLayout newItemContainer;
                    newItemContainer = (ConstraintLayout) li.inflate(R.layout.shelf_food_box, null);
                    itemBox.addView(newItemContainer, 0);
                    String name = (String) current.get("name");
                    //String foodGroup = (String) current.get("foodGroup");
                    String shelfID = (String) current.get("shelfid");
                    //System.out.println("ShelfID is : " + shelfID);

                    String expiration = (String) current.get("expiration");
                    String quantity = (String) current.get("quantity");
                    //String measurement = (String) current.get("measurement");
                    String imageurl = (current.get("imageurl").toString());

                    nameSorted.add(name);
                    quantitySorted.add(quantity);
                    expirationSorted.add(expiration);
//                    ArrayList<String> measurementSorted = new ArrayList<>();
                    imageURLSorted.add(imageurl);
                    itemIDSorted.add(shelfID);
          //          System.out.println("The id being added is: " + shelfID);
            //        System.out.println("itemID sorted: " + itemIDSorted.toString());

                    shelfImage = newItemContainer.findViewById(R.id.shelfImage);
                    try {

                        if (!imageurl.equals("")) {
                            Glide.with(this).load(imageurl).into(shelfImage);
                        }
                    } catch (Exception e) {
                        System.out.println("image url is: " + imageurl);
                    }

                    if (!expiration.equals("")) {
                        setExp = newItemContainer.findViewById(R.id.setExp);
                        setExp.setText(expiration);
                    }
                    if (!quantity.equals("")) {
                        setQty = newItemContainer.findViewById(R.id.setQty);
                        setQty.setText(quantity);
                    }
                    if (!name.equals("")) {
                        itemName = newItemContainer.findViewById(R.id.itemName);
                        itemName.setText(name);
                    }
                    editCheckBox = newItemContainer.findViewById(R.id.editCheckBox);
                    editCheckBox.setVisibility(View.INVISIBLE);
                }
                int b = sorter.size();
                while(b <= 3 ) {
                    LayoutInflater li = getLayoutInflater();
                    ConstraintLayout newItemContainer;
                    newItemContainer = (ConstraintLayout) li.inflate(R.layout.shelf_food_box, null);
                    itemBox.addView(newItemContainer, b);
                    editCheckBox = newItemContainer.findViewById(R.id.editCheckBox);
                    editCheckBox.setVisibility(View.INVISIBLE);

                    itemName = newItemContainer.findViewById(R.id.itemName);
                    itemName.setVisibility(View.INVISIBLE);

                    setQty = newItemContainer.findViewById(R.id.setQty);
                    setQty.setVisibility(View.INVISIBLE);

                    setExp = newItemContainer.findViewById(R.id.setExp);
                    setExp.setVisibility(View.INVISIBLE);

                    shelfImage = newItemContainer.findViewById(R.id.shelfImage);
                    shelfImage.setVisibility(View.INVISIBLE);

                    TextView exp = newItemContainer.findViewById(R.id.exp);
                    exp.setVisibility(View.INVISIBLE);
                    TextView qty = newItemContainer.findViewById(R.id.qty);
                    qty.setVisibility(View.INVISIBLE);

                    b++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        Collections.reverse(itemIDSorted);
        Collections.reverse(nameSorted);
        System.out.println("itemID sorted reversed: " + itemIDSorted.toString());
    }

    public void editItem(int choice){

        if(choice == 1) {

            for (int i = 0; i < itemBox.getChildCount(); i++) {
                ConstraintLayout currentlayout = (ConstraintLayout) itemBox.getChildAt(i);
                CheckBox newCheckbox = currentlayout.findViewById(R.id.editCheckBox);
                if (newCheckbox.isChecked()) {
                    System.out.println("Item to delete: " + itemIDSorted.get(i));
                    deleteQueue.add(itemIDSorted.get(i));
                    delteItemID.add(itemIDSorted.get(i));
//                    deleteQueue.add()
                }
            }

            for (String dID : delteItemID) {
                OkHttpClient client = new OkHttpClient();

                String url = "http://cgi.sice.indiana.edu/~team39/removeUserShelf.php";
                String houseID = readFile(1);
                System.out.println("Deleting: " + dID + ", " + houseID);
                System.out.println("houseID" + houseID);
                System.out.println("shelfID" + dID);

                RequestBody reqBody = new FormBody.Builder()
                        .add("houseid", houseID.trim())
                        .add("shelfID", dID.trim())
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(reqBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        if (response.isSuccessful()) {
                            resp = Objects.requireNonNull(response.body()).string();
                            System.out.println("Deleting response: " + resp);

                            Shelf2.this.runOnUiThread(() -> {

                            });
                        }

                    }
                });

            }
            delteItemID.clear();
        }else{
            System.out.println("Edit Submit button was pressed:  ");

            ArrayList<String> editID = new ArrayList<>();
            for (int i = 0; i < itemBox.getChildCount(); i++) {
                ConstraintLayout currentlayout = (ConstraintLayout) itemBox.getChildAt(i);
                CheckBox newCheckbox = currentlayout.findViewById(R.id.editCheckBox);

                if (newCheckbox.isChecked()) {
                    System.out.println("Item to edit: " + itemIDSorted.get(i));
                    editID.add(itemIDSorted.get(i));
                    editID.add(nameSorted.get(i));
                }
            }
            TextView shelfEditResponse = findViewById(R.id.shelfEditResponse);
            if(editID.isEmpty()){
                System.out.println("Item list is empty: " + editID.toString());

                shelfEditResponse.setText("No boxes to edit");
            }else if(editID.size() > 2){
                System.out.println("Item list has too much: " + editID.toString());

                shelfEditResponse.setText("Please select 1 item to edit at a time");
            }else{
                System.out.println("Item list is 1 " + editID.toString());
                editID.add(houseID.trim());
                Intent i = new Intent(getApplicationContext(),AddFoodManually.class);
                System.out.println("Item sending to addFoodManually " + editID);
                i.putExtra("shelfID",editID);
                startActivity(i);


            }
            editID.clear();
        }
    }

    public String readFile(int option){
        if(option == 0) {
            try {
                String fileName = "userInformation.txt";
                FileInputStream fileInputStream = openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();

                String lines;
                while ((lines = bufferedReader.readLine()) != null) {
                    stringBuffer.append(lines + "\n");
                }

                return stringBuffer.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
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

//    public void getChoices(){
//        Intent i = getIntent();
//        ArrayList<String> choices = i.getStringArrayListExtra("choices");
//
//        String sort = "";
//        String filter = "";
//
//        if(choices != null) {
//
//            sort += choices.get(0);
//            filter += choices.get(1);
//        }
//
//        nameSorted.clear();
//        quantitySorted.clear();
//        expirationSorted.clear();
//        measurementSorted.clear();
//        itemIDSorted.clear();
//        imageURLSorted.clear();
//
//        int c = 0;
//        while (c < name.size()) {
//            nameSorted.add(name.get(c));
//            quantitySorted.add(quantity.get(c));
//            expirationSorted.add(expiration.get(c));
//            measurementSorted.add(measurement.get(c));
//            itemIDSorted.add(itemID.get(itemID.size()-c-1));
//            if(imageURL.size()>c) {
//                imageURLSorted.add(imageURL.get(c));
//            }
//
//            c++;
//        }
//
//        if(sort.equals("2")){
//            int count = 0;
//            nameSorted.clear();
//            quantitySorted.clear();
//            expirationSorted.clear();
//            measurementSorted.clear();
//            itemIDSorted.clear();
//            imageURLSorted.clear();
//
//            while(count < expiration.size()){
//                nameSorted.add(name.get(name.size()-count-1));
//                quantitySorted.add(quantity.get(name.size()-count-1));
//                expirationSorted.add(expiration.get(name.size()-count-1));
//                measurementSorted.add(measurement.get(name.size()-count-1));
//                itemIDSorted.add(itemID.get(count));
//                if(imageURL.size()> name.size() - count - 1) {
//                    imageURLSorted.add(imageURL.get(name.size() - count - 1));
//                }
//
//                count++;
//            }
//
//        }else if(sort.equals("3")){
//
//        }else if(sort.equals("4")) {
//            int count = 0;
//            nameSorted.clear();
//            quantitySorted.clear();
//            expirationSorted.clear();
//            measurementSorted.clear();
//            itemIDSorted.clear();
//            imageURLSorted.clear();
//
//            ArrayList<String> day = new ArrayList<>();
//
//            for(String date:expiration){
//                String[] sp = date.split("-");
//                day.add(sp[0] + sp[1] + sp[2]);
//            }
//
//            Collections.sort(day);
//            ArrayList<String> orderedDays = new ArrayList<>();
//
//            for(String d:day){
//                    orderedDays.add("" + d.charAt(0) + d.charAt(1) + d.charAt(2) + d.charAt(3) + "-" + d.charAt(4) + d.charAt(5) + "-" + d.charAt(6) + d.charAt(7));
//            }
//
//            int co = 0;
//
//            ArrayList<String> orderedBoxQ = new ArrayList<>();
//            ArrayList<String> orderedBoxE = new ArrayList<>();
//            ArrayList<String> orderedBoxN = new ArrayList<>();
//            ArrayList<String> orderedBoxID = new ArrayList<>();
//            ArrayList<String> orderedBoxI = new ArrayList<>();
//
//            for(String da : orderedDays){
//                co = 0;
//                for(String nd : expiration){
//
//                    if(da.equals(nd)){
//
//                        orderedBoxN.add(name.get(co));
//                        orderedBoxQ.add(quantity.get(co));
//                        orderedBoxE.add(da);
//
//                        if(imageURL.size() < co) {
//                            orderedBoxI.add(imageURL.get(co));
//                        }
//
//                        orderedBoxID.add(itemID.get(itemID.size() - co - 1));
//                    }
//                    co++;
//                }
//                co = 0;
//            }
//            while(co < expiration.size()){
//                nameSorted.add(orderedBoxN.get(orderedBoxN.size()-co-1));
//                quantitySorted.add(orderedBoxQ.get(orderedBoxN.size()-co-1));
//                expirationSorted.add(orderedBoxE.get(orderedBoxN.size()-co-1));
//
//                if(orderedBoxI.size() > orderedBoxN.size()-co-1) {
//                    imageURLSorted.add(orderedBoxI.get(orderedBoxN.size() - co - 1));
//                }
//                itemIDSorted.add(orderedBoxID.get(orderedBoxN.size()-co-1));
//
//                co++;
//            }
//        }
//
//    }

//    private void buildFilter() {
//
//        findViewById(R.id.shelfScroll).setVisibility(View.GONE);
//        findViewById(R.id.shelfBackground).setVisibility(View.GONE);
//
//        filterBox = (LinearLayout) findViewById(R.id.filterLayout);
//        //            System.out.print(exp[0]);
//        LayoutInflater li = getLayoutInflater();
//        ConstraintLayout newItemContainer;
//        newItemContainer = (ConstraintLayout) li.inflate(R.layout.shelf_filter_box, null);
//        filterBox.addView(newItemContainer, 0);
//    }
}

