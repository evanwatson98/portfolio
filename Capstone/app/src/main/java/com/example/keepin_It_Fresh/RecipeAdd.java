package com.example.keepin_It_Fresh;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

import static android.app.Activity.RESULT_OK;
import static com.example.keepin_It_Fresh.R.layout.recipe_add_page_4;

class NoIngrediantsException extends Exception{
    public NoIngrediantsException(String s){
        super(s);
    }
}

class IngrediantMissingException extends Exception{
    public IngrediantMissingException(String s){
        super(s);
    }
}

class QuantityMissingException extends Exception{
    public QuantityMissingException(String s){
        super(s);
    }
}





public class  RecipeAdd extends Fragment {

    LayoutInflater li;
    @SuppressLint("InflateParams")
    EditText name_edit, instructions_edit, tool_input, hourDigit1, hourDigit2,
            minuteDigit1, minuteDigit2, secondDigit1, secondDigit2;
    Button finish_button, upload_button;
    ImageButton drawer_button, next_button_add_recipe, back_button_add_recipe, undo_button_add_recipe, add_tool_button, add_ingrediant_button, settingsBtn;
    LinearLayout tool_container, ingrediant_container, page1Layout, add_recipe_root, mainLayout, page2Layout
            , page3Layout, page4Layout, page5Layout;
    ViewGroup ingrediantbox;
    CheckBox spicy_checkbox, gluten_checkbox, halal_checkbox, kosher_checkbox, sugarfree_checkbox, fish_checkbox, nuts_checkbox,
            dairy_checkbox, vegan_checkbox, vegetarian_checkbox, publicCheckBox;
    ImageView recipeImage;
    ArrayList<String> toolsList = new ArrayList<>(), ingrediantsList, ingrediantsRefined = new ArrayList<>();
    Spinner nationalitySelect, categorySelect;
    GlobalVars vars = GlobalVars.getInstance();
    MultiAutoCompleteTextView ingrediants_searcher;
    HashMap<String, Integer> catmap = new HashMap<>();
    HashMap<Integer, String> rCatmap = new HashMap<>();
    JSONObject data;
    String id;
    String image="";
    public int currentPage;
    public boolean isEdit;
    final HashMap<String, Integer> tempMap = new HashMap<>();
    final HashMap<String, String> unitmap = new HashMap<>(), qtymap = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_add, container, false);
        String[] cats = {"Tacos", "Burgers", "Curries", "Teas", "Pastas", "Pastries", "Cookies", "Deserts", "Rices", "Gumbos", "Soups", "Salads", "Sandwiches", "Wraps", "Burritos", "Quick Meals"};
        for(int i = 0; i < cats.length; i++){
            catmap.put(cats[i], i);
            rCatmap.put(i, cats[i]);

        }
        li = getLayoutInflater();
        drawer_button = view.findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((RecipePage)getActivity()).getNd().openDrawer(Gravity.LEFT);
        });
        add_recipe_root = view.findViewById(R.id.add_recipe_page_container); page1Layout = (LinearLayout) li.inflate(R.layout.recipe_add_page_1, null); page2Layout = (LinearLayout) li.inflate(R.layout.recipe_add_page_2, null);
        page3Layout = (LinearLayout) li.inflate(R.layout.recipe_add_page_3, null); page4Layout = (LinearLayout) li.inflate(recipe_add_page_4, null); page5Layout = (LinearLayout) li.inflate(R.layout.recipe_add_page_5, null);
        name_edit = page1Layout.findViewById(R.id.edit_name); instructions_edit = page1Layout.findViewById(R.id.instructions_edit); tool_input = page3Layout.findViewById(R.id.toolInput); hourDigit1 = page3Layout.findViewById(R.id.hourDigit1); hourDigit2 = page3Layout.findViewById(R.id.hourDigit2);
        minuteDigit1 = page3Layout.findViewById(R.id.minuteDigit1); minuteDigit2 = page3Layout.findViewById(R.id.minuteDigit2); secondDigit1 = page3Layout.findViewById(R.id.secondDigit1); secondDigit2 = page3Layout.findViewById(R.id.secondDigit2);
        finish_button = view.findViewById(R.id.finish_button); upload_button = page5Layout.findViewById(R.id.upload_button);
        next_button_add_recipe = view.findViewById(R.id.next_button_add_recipe); back_button_add_recipe = view.findViewById(R.id.back_button_add_recipe); undo_button_add_recipe = view.findViewById(R.id.undo_button_add_recipe); add_tool_button = page3Layout.findViewById(R.id.addToolButton); add_ingrediant_button = page4Layout.findViewById(R.id.recipe_add_ingrediant_button);
        tool_container = page3Layout.findViewById(R.id.recipe_add_tool_container); ingrediant_container = page4Layout.findViewById(R.id.recipe_add_ingrediant_container);
        ingrediantbox = page5Layout.findViewById(R.id.ingrediant_prompt_container);
        recipeImage = page5Layout.findViewById(R.id.recipeImage);
        spicy_checkbox = page2Layout.findViewById(R.id.spicy_checkBox); gluten_checkbox = page2Layout.findViewById(R.id.gluten_checkBox); halal_checkbox = page2Layout.findViewById(R.id.halal_checkBox); kosher_checkbox = page2Layout.findViewById(R.id.kosher_checkBox);
        sugarfree_checkbox = page2Layout.findViewById(R.id.sugarfree_checkBox); fish_checkbox = page2Layout.findViewById(R.id.fish_checkBox); nuts_checkbox = page2Layout.findViewById(R.id.nuts_checkBox); dairy_checkbox  = page2Layout.findViewById(R.id.dairy_checkBox);
        vegan_checkbox = page2Layout.findViewById(R.id.vegan_checkBox); vegetarian_checkbox = page2Layout.findViewById(R.id.vegetarian_checkBox); publicCheckBox = page5Layout.findViewById(R.id.isPublicCheck);
        nationalitySelect = page2Layout.findViewById(R.id.nationality_select); categorySelect = page2Layout.findViewById(R.id.category_select);
        ingrediants_searcher = page4Layout.findViewById(R.id.ingrediants_searcher);
        ArrayAdapter<CharSequence> natSeladapter = ArrayAdapter.createFromResource(getActivity(), R.array.nationality_select, android.R.layout.simple_spinner_item);
        natSeladapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ArrayAdapter<CharSequence> catSeladapter = ArrayAdapter.createFromResource(getActivity(), R.array.category_select, android.R.layout.simple_spinner_item);
        catSeladapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySelect.setAdapter(catSeladapter);
        nationalitySelect.setAdapter(natSeladapter);
        upload_button.setOnClickListener(v->{
            try {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            } catch (Exception e){
                e.printStackTrace();
                //System.out.println("User chose not to upload");
            }
        });
        add_tool_button.setOnClickListener(v -> {
            removeTools();
            toolsList.add(tool_input.getText().toString());
            for(String item : toolsList){
                addTool(item, true);
            }
        });
        next_button_add_recipe.setOnClickListener(v -> { if(currentPage < 5){ currentPage++; setPage(currentPage); } });
        back_button_add_recipe.setOnClickListener(v -> { if(currentPage > 1){ currentPage--; setPage(currentPage); }});
        undo_button_add_recipe.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("").setMessage("Are you sure you want to abandon this recipe?").setIcon(
                    android.R.drawable.ic_dialog_alert).setPositiveButton("Yes", (dialog, whichButton) -> {
                        FragmentTransaction ft;
                        FragmentManager fm = ((RecipePage)getActivity()).getFm();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.recipe_fragment_container, ((RecipePage)getActivity()).getCore());
                        ft.commit();
            }).setNegativeButton("No", null).show();
        });
        ingrediants_searcher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    System.out.println("I'm running");
                    final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    JSONObject rootJson = new JSONObject();
                    JSONObject myjson = new JSONObject();
                    try {
                        String[] lastIngrediant = ingrediants_searcher.getText().toString().split(", ");
                        int lastIngrediantLength = lastIngrediant.length;
                        myjson.put("action", "selectfoodlike");
                        myjson.put("data", lastIngrediant[lastIngrediantLength-1]);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        rootJson.put("input", myjson);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    //System.out.println(rootJson);
                    JsonObjectRequest MyJSONRequest = new JsonObjectRequest("http://cgi.sice.indiana.edu/~team39/api.php",
                            rootJson,
                            response -> {

                                try {

                                    ArrayList<String> ingrediantsFound = new ArrayList<>();
                                    JSONArray names = (JSONArray)response.get("data");
                                    for(int i = 0; i < names.length(); i++){
                                        //System.out.println(names.get(i));
                                        ingrediantsFound.add(((JSONObject)names.get(i)).get("name").toString());
                                        tempMap.put(((JSONObject)names.get(i)).get("name").toString(),Integer.parseInt(((JSONObject)names.get(i)).get("shelfID").toString()));
                                    }
                                    //System.out.println("running");
                                    ingrediantsList = ingrediantsFound;
                                    String[] tempStrings = new String[ingrediantsFound.size()];
                                    for(int i = 0; i < tempStrings.length; i++){
                                        tempStrings[i] = ingrediantsFound.get(i);
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                            android.R.layout.simple_dropdown_item_1line, tempStrings);
                                    ingrediants_searcher.setAdapter(adapter);
                                    ingrediants_searcher.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                                } catch (JSONException e){
                                    //System.out.println(response);
                                    e.printStackTrace();
                                }
                            }, Throwable::printStackTrace);
                    requestQueue.add(MyJSONRequest);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });
        add_ingrediant_button.setOnClickListener(v -> {
            //removeIngrediants();
            String[] ingrediants = ingrediants_searcher.getText().toString().split(", ");
            for(String item : ingrediants){
                ingrediantsRefined.add(item);
                addTool(item, false);
            }
        });
        finish_button.setOnClickListener(v -> {

            try{
                JSONObject input = collectData();
                input.get("input");
                handleNewRecipe(input);
            } catch (IngrediantMissingException e) {
                String item1 = e.getMessage();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("");
                LinearLayout addFoodAddRecipe = (LinearLayout) getLayoutInflater().inflate(R.layout.add_food_add_recipe, null);
                TextView adarPage1 = addFoodAddRecipe.findViewById(R.id.add_food_addrecipe_page1);
                adarPage1.setText("Error: One of the ingrediants you entered, "+item1+", wasn't found in our database\n" +
                        "Please enter the necessary information.");
                builder.setCancelable(false);
                TextView datePickerTextView = addFoodAddRecipe.findViewById(R.id.datePickerTextView);
                datePickerTextView.setOnClickListener(view1 -> {
                    Calendar today = Calendar.getInstance();
                    int year = today.get(Calendar.YEAR);
                    int month = today.get(Calendar.MONTH);
                    int day = today.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light,
                            (DatePickerDialog.OnDateSetListener) (datePicker, year1, month1, day1) -> {
                                month1 = month1 + 1;
                                String dayString = Integer.toString(day1);
                                String monthString = Integer.toString(month1);
                                if (dayString.length() == 1){
                                    dayString = "0" + dayString;
                                }
                                if (monthString.length() == 1){
                                    monthString = "0" + monthString;
                                }
                                String expirationDate = monthString + "-" + dayString + "-" + year1;
                                datePickerTextView.setText(expirationDate);
                            }, year, month, day);
                    dialog.show();
                });
                TextView redText = addFoodAddRecipe.findViewById(R.id.redText);
                Spinner quantitySpinner = addFoodAddRecipe.findViewById(R.id.quantitySpinner),
                        measurementSpinner = addFoodAddRecipe.findViewById(R.id.measurementSpinner),
                        foodGroupSpinner = addFoodAddRecipe.findViewById(R.id.foodGroupSpinner),
                        foodTypeSpinner = addFoodAddRecipe.findViewById(R.id.foodTypeSpinner);
                ArrayAdapter<CharSequence> measurementAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.measurements, android.R.layout.simple_spinner_item);
                measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); measurementSpinner.setAdapter(measurementAdapter);
                ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.quantity, android.R.layout.simple_spinner_item);
                quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); quantitySpinner.setAdapter(quantityAdapter);
                ArrayAdapter<CharSequence> foodGroupAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.foodGroups, android.R.layout.simple_spinner_item);
                foodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); foodGroupSpinner.setAdapter(foodGroupAdapter);
                ArrayAdapter<CharSequence> emptyAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.empty, android.R.layout.simple_spinner_item);
                emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); foodTypeSpinner.setAdapter(emptyAdapter);
                final ArrayAdapter<CharSequence> fruitAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.fruit, android.R.layout.simple_spinner_item); fruitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                final ArrayAdapter<CharSequence> vegetableAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.vegetable, android.R.layout.simple_spinner_item); vegetableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                final ArrayAdapter<CharSequence> proteinAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.protein, android.R.layout.simple_spinner_item); proteinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                final ArrayAdapter<CharSequence> grainAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.grain, android.R.layout.simple_spinner_item); grainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                final ArrayAdapter<CharSequence> dairyAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.dairy, android.R.layout.simple_spinner_item); dairyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                final ArrayAdapter<CharSequence> treatAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.treat, android.R.layout.simple_spinner_item); treatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                foodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        switch(position){
                            case 1: foodTypeSpinner.setAdapter(fruitAdapter); break;
                            case 2: foodTypeSpinner.setAdapter(vegetableAdapter); break;
                            case 3: foodTypeSpinner.setAdapter(proteinAdapter); break;
                            case 4: foodTypeSpinner.setAdapter(grainAdapter); break;
                            case 5: foodTypeSpinner.setAdapter(dairyAdapter); break;
                            case 6: foodTypeSpinner.setAdapter(treatAdapter); break;
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}

                });
                builder.setPositiveButton("Finish", (dialog, which)->{});
                builder.setView(addFoodAddRecipe);
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v3 -> {
                    String myUrl = "https://cgi.sice.indiana.edu/~team39/addFoodManuallyV4.php";
                    String foodName = item1;
                    String foodQuantity = quantitySpinner.getSelectedItem().toString();
                    String foodMeasurement = measurementSpinner.getSelectedItem().toString();
                    String foodExpiration = datePickerTextView.getText().toString();
                    String foodGroup = foodGroupSpinner.getSelectedItem().toString();
                    String foodType = foodTypeSpinner.getSelectedItem().toString();
                    String userid = GlobalVars.getInstance().userid;
                    RequestBody myRequestBody = new FormBody.Builder()
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
                    if(quantitySpinner.getSelectedItem().toString().equals("")){
                        redText.setText("Please select the quantity");
                    } else if(measurementSpinner.getSelectedItem().toString().equals("Select Measurement")){
                        redText.setText("Please select the appropriate measurement");
                    } else if(foodGroupSpinner.getSelectedItem().toString().equals("Select Food Group")){
                        redText.setText("Please select the appropriate food group");
                    } else if(foodTypeSpinner.getSelectedItem().toString().equals("Select Type")){
                        redText.setText("Please select the appropriate type");
                    } else if(foodTypeSpinner.getSelectedItem().toString().equals("Select Food Group First")){
                        redText.setText("Please select the appropriate food group");
                    } else if(datePickerTextView.getText().equals("Select Date")) {
                        redText.setText("Please select the expiration date");
                    } else {
                        final OkHttpClient myClient = new OkHttpClient();
                        myClient.newCall(myRequest).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e1) { e1.printStackTrace(); }
                            @Override
                            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                                String newId = response.body().string().split("::ID:")[1];
                                ingrediantsRefined.add(item1);
                                tempMap.put(item1, Integer.parseInt(newId.trim()));
                                dialog.cancel();
                            }
                        });
                    }
                });

            } catch(QuantityMissingException e) {
                String item1 = e.getMessage();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("").setMessage("Error: Must enter quantity for "+item1).setIcon(
                        android.R.drawable.ic_dialog_alert).setNegativeButton("Ok", null).show();
            } catch(NoIngrediantsException e){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("").setMessage("Error: No Ingrediants were entered").setIcon(
                        android.R.drawable.ic_dialog_alert).setNegativeButton("Ok", null).show();
            } catch(JSONException e){
                e.printStackTrace();
            }
        });
        Bundle extras = getArguments();
        try {
            id = Objects.requireNonNull(extras).get("id").toString();
            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JSONObject rootJson = new JSONObject();
            JSONObject myjson = new JSONObject();
            try {
                myjson.put("action", "getrecipe");
                myjson.put("data",id);
            } catch (Exception e){
                e.printStackTrace();
            }
            try {
                rootJson.put("input", myjson);
            } catch (Exception e){
                e.printStackTrace();
            }
            //System.out.println(rootJson);
            JsonObjectRequest MyJSONRequest = new JsonObjectRequest("http://cgi.sice.indiana.edu/~team39/api.php",
                    rootJson,
                    response -> {
                        try {
                            data = response;
                            //System.out.println(data);
                            JSONArray myDataRefined = (JSONArray)response.get("data");
                            data = (JSONObject)myDataRefined.get(0);
                            try {
                                JSONArray tools = (JSONArray) myDataRefined.get(1);
                                for (int i = 0; i < tools.length(); i++) {
                                    addTool(((JSONObject) tools.get(i)).get("name").toString(), true);
                                    toolsList.add(((JSONObject) tools.get(i)).get("name").toString());
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                            try {
                                JSONArray ingrediants = (JSONArray) myDataRefined.get(2);
                                //System.out.println(ingrediants);
                                for (int i = 0; i < ingrediants.length(); i++) {
                                    ingrediantsRefined.add(((JSONObject) ingrediants.get(i)).get("name").toString());
                                    unitmap.put(((JSONObject) ingrediants.get(i)).get("name").toString(),
                                            ((JSONObject) ingrediants.get(i)).get("measurement").toString());
                                    qtymap.put(((JSONObject) ingrediants.get(i)).get("name").toString(),
                                            ((JSONObject) ingrediants.get(i)).get("quantity").toString());
                                    tempMap.put(((JSONObject) ingrediants.get(i)).get("name").toString(),
                                            Integer.parseInt(((JSONObject) ingrediants.get(i)).get("shelfID").toString()));
                                }
                                showIngrediantsPrompts();
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                            try {
                                JSONArray ingrediants = (JSONArray) myDataRefined.get(2);
                                for (int i = 0; i < ingrediants.length(); i++) {
                                    addTool(((JSONObject) ingrediants.get(i)).get("name").toString(), false);
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                            name_edit.setText(data.get("name").toString());
                            instructions_edit.setText(data.get("instructions").toString());
                            try {
                                if (data.get("spicy").toString().equals("1")){ spicy_checkbox.setChecked(true);
                                } else { spicy_checkbox.setChecked(false); }
                            } catch (Exception e){
                                spicy_checkbox.setChecked(false);
                            }
                            try {
                                if (data.get("glutenfree").toString().equals("1")){ gluten_checkbox.setChecked(true);
                                } else { gluten_checkbox.setChecked(false); }
                            } catch (Exception e){
                                gluten_checkbox.setChecked(false);
                            }
                            try {
                                if (data.get("dairy").toString().equals("1")){ dairy_checkbox.setChecked(true);
                                } else { dairy_checkbox.setChecked(false); }
                            } catch (Exception e){
                                dairy_checkbox.setChecked(false);
                            }
                            try {
                                if (data.get("halal").toString().equals("1")){ halal_checkbox.setChecked(true);
                                } else { halal_checkbox.setChecked(false); }
                            } catch (Exception e){
                                halal_checkbox.setChecked(false);
                            }
                            try {
                                if (data.get("kosher").toString().equals("1")){ kosher_checkbox.setChecked(true);
                                } else { kosher_checkbox.setChecked(false); }
                            } catch (Exception e){
                                kosher_checkbox.setChecked(false);
                            }
                            try {
                                if (data.get("fish").toString().equals("1")){ fish_checkbox.setChecked(true);
                                } else { fish_checkbox.setChecked(false); }
                            } catch (Exception e){
                                fish_checkbox.setChecked(false);
                            }
                            try {
                                if (data.get("sugarfree").toString().equals("1")){ sugarfree_checkbox.setChecked(true);
                                } else { sugarfree_checkbox.setChecked(false); }
                            } catch (Exception e){
                                sugarfree_checkbox.setChecked(false);
                            }
                            try {
                                if (data.get("nuts").toString().equals("1")){ nuts_checkbox.setChecked(true);
                                } else { nuts_checkbox.setChecked(false); }
                            } catch (Exception e){
                                nuts_checkbox.setChecked(false);
                            }
                            try {
                                if (data.get("vegan").toString().equals("1")){ vegan_checkbox.setChecked(true);
                                } else { vegan_checkbox.setChecked(false); }
                            } catch (Exception e){
                                vegan_checkbox.setChecked(false);
                            }
                            try {
                                if (data.get("vegetarian").toString().equals("1")){ vegetarian_checkbox.setChecked(true);
                                } else { vegetarian_checkbox.setChecked(false); }
                            } catch (Exception e){
                                vegetarian_checkbox.setChecked(false);
                            }
                            try {
                                String time = data.get("cooking_time").toString();
                                if(!(time==null)&&time.length()==8) {
                                    hourDigit1.setText(time.substring(0, 1));
                                    hourDigit2.setText(time.substring(1, 2));
                                    minuteDigit1.setText(time.substring(3, 4));
                                    minuteDigit2.setText(time.substring(4, 5));
                                    secondDigit1.setText(time.substring(6, 7));
                                    secondDigit2.setText(time.substring(7, 8));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try{
                                String rcid = ((JSONObject)myDataRefined.get(3)).get("rcid").toString();
                                System.out.println("rcid is "+rcid);
                                categorySelect.setSelection(Integer.parseInt(rcid)-1);
                            } catch (Exception e){
                            }
                            try {
                                String isPublicRes = data.get("isPublic").toString();
                                if(isPublicRes.equals("1")){
                                    publicCheckBox.setChecked(true);
                                }
                            } catch (Exception e){
                                //e.printStackTrace();
                            }
                            try{
                                String url = data.get("url").toString();
                                ImageRequest request = new ImageRequest(url, bitmap -> {
                                    recipeImage.setImageBitmap(bitmap);
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

                                    image = encodedImage;

                                }, 0, 0, null, Throwable::printStackTrace);
                                requestQueue.add(request);


                            } catch (Exception e){
                                //e.printStackTrace();
                            }

                        } catch (Exception e){
                            //System.out.println("oof");
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace);

            requestQueue.add(MyJSONRequest);
            //System.out.println(data);
            isEdit = true;
        } catch (Exception e){
            isEdit = false;
        }
        currentPage = 1;
        setPage(currentPage);

        return view;
    }

    @SuppressLint("InflateParams")
    public void showIngrediantsPrompts() {
        ingrediantbox.removeAllViews();
        for(String s : ingrediantsRefined){
            LayoutInflater li = getLayoutInflater();
            LinearLayout ingrediantContainer;
            ingrediantContainer = (LinearLayout)li.inflate(R.layout.add_recipe_ingrediant_box, null);
            TextView ing_cont_lab = ingrediantContainer.findViewById(R.id.recipe_ingrediant_template_label);
            ing_cont_lab.setText(s);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.unit_select, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            EditText qtyLabel = ingrediantContainer.findViewById(R.id.add_recipe_ingrediant_qty);
            Spinner unitSelect = ingrediantContainer.findViewById(R.id.unitSelect);
            int choice = 0;
            if(unitmap.containsKey(s)){
                //System.out.println("ADAPTERCOUNT" + adapter.getCount());
                for(int i = 0; i < adapter.getCount(); i++) {
                    //System.out.println("cURRENTIteM " + adapter.getItem(i));
                    if (Objects.equals(unitmap.get(s), Objects.requireNonNull(adapter.getItem(i)).toString())) {
                        choice = i;
                        //System.out.println(adapter.getItem(i));
                        break;
                    }
                }
            }
            if(qtymap.containsKey(s)){
                qtyLabel.setText(qtymap.get(s));
            }
            unitSelect.setAdapter(adapter);
            unitSelect.setSelection(choice);
            ingrediantbox.addView(ingrediantContainer, 0);
        }
    }

    public void removeTools(){
        tool_container.removeAllViews();
    }

    public void removeIngrediants() { ingrediant_container.removeAllViews(); }

    @SuppressLint("InflateParams")
    public void addTool(String s, boolean isTool){
        final String tempString = s;
        final boolean tempBool = isTool;
        LayoutInflater li = getLayoutInflater();
        LinearLayout toolContainer;
        toolContainer = (LinearLayout)li.inflate(R.layout.tool_box_template, null);
        if(isTool) {
            tool_container.addView(toolContainer, 0);
        } else {
            ingrediant_container.addView(toolContainer, 0);
        }
        TextView tool_cont_lab = toolContainer.findViewById(R.id.tool_container_label);
        tool_cont_lab.setText(s);
        ImageButton delete_button = toolContainer.findViewById(R.id.remove_tool_button);
        delete_button.setOnClickListener(view -> {

            if(tempBool) {
                toolsList.remove(tempString);
                removeTools();
                for (String item : toolsList) {
                    addTool(item, true);
                }
            } else {
                ingrediantsRefined.remove(tempString);
                removeIngrediants();
                for (String item : ingrediantsRefined){
                    addTool(item, false);
                }
            }
        });
    }

    public JSONObject collectData() throws IngrediantMissingException, QuantityMissingException, NoIngrediantsException {
        JSONObject myRoot = new JSONObject();
        try {
            String name = name_edit.getText().toString();
            String bigString = "";
            String instructions = instructions_edit.getText().toString();
            String nationality = nationalitySelect.getSelectedItem().toString();
            int rcid = categorySelect.getSelectedItemPosition();
            int spicy = spicy_checkbox.isChecked()? 1 : 0;
            int gluten = gluten_checkbox.isChecked()? 1 : 0;
            int halal = halal_checkbox.isChecked()? 1 : 0;
            int kosher = kosher_checkbox.isChecked()? 1 : 0;
            int sugarfree = sugarfree_checkbox.isChecked()? 1 : 0;
            int fish = fish_checkbox.isChecked()? 1 : 0;
            int nuts = nuts_checkbox.isChecked()? 1 : 0;
            int dairy = dairy_checkbox.isChecked()? 1 : 0;
            int vegan = vegan_checkbox.isChecked()? 1 : 0;
            int vegetarian = vegetarian_checkbox.isChecked()? 1 : 0;
            if (name.equals("")) {
                myRoot.put("input", "Error: No name is inserted");
            } else if (instructions.equals("")) {
                myRoot.put("input", "Error: No instructions are inserted");
            } else {
                JSONObject subInput = new JSONObject();
                JSONObject newData = new JSONObject();
                JSONObject myRecipe = new JSONObject();
                if(isEdit){
                    if(data.get("creator").toString().equals(vars.userid)) {
                        System.out.println("IM RUNNING");
                        subInput.put("action", "editowner");
                    } else {
                        subInput.put("action","editother");
                    }
                } else {
                    subInput.put("action", "insertrecipe");
                    myRecipe.put("creator", vars.userid);
                }

                myRecipe.put("Name", name);
                if(isEdit){
                    myRecipe.put("oldid", id);
                }
                if(!toolsList.isEmpty()){
                    int i = 0;
                    JSONArray myTools = new JSONArray();
                    for(String tool : toolsList){
                        JSONObject minijson = new JSONObject();
                        minijson.put("name", tool);
                        myTools.put(i, minijson);
                        i++;
                    }
                    myRecipe.put("tools", myTools);
                }
                String timeString = "";
                String hourString1 = hourDigit1.getText().toString();
                String hourString2 = hourDigit2.getText().toString();
                String minuteString1 = minuteDigit1.getText().toString();
                String minuteString2 = minuteDigit2.getText().toString();
                String secondString1 = secondDigit1.getText().toString();
                String secondString2 = secondDigit2.getText().toString();
                if(hourString1.equals("")){ timeString += "0"; } else {
                    timeString += hourString1; }
                if(hourString2.equals("")){ timeString += "0"; } else {
                    timeString += hourString2; }
                timeString += ":";
                if(minuteString1.equals("")){ timeString += "0"; } else {
                    timeString += minuteString1; }
                if(minuteString2.equals("")){ timeString += "0"; } else {
                    timeString += minuteString2; }
                timeString += ":";
                if(secondString1.equals("")){ timeString += "0"; } else {
                    timeString += secondString1; }
                if(secondString2.equals("")){ timeString += "0"; } else {
                    timeString += secondString2; }
                if(!timeString.equals("00:00:00")){ myRecipe.put("cookingtime", timeString); }
                bigString += instructions;
                myRecipe.put("isPublic", publicCheckBox.isChecked()? "1": "0");
                myRecipe.put("Instructions", bigString);
                myRecipe.put("Nationality", nationality);
                myRecipe.put("Spicy", spicy);
                myRecipe.put("Gluten",  gluten);
                myRecipe.put("Halal", halal);
                myRecipe.put("Kosher", kosher);
                myRecipe.put("Sugarfree", sugarfree);
                myRecipe.put("Fish", fish);
                myRecipe.put("Nuts", nuts);
                myRecipe.put("Dairy", dairy);
                myRecipe.put("Vegan", vegan);
                myRecipe.put("Vegetarian", vegetarian);
                myRecipe.put("CookingTime", timeString);
                myRecipe.put("image", image);
                myRecipe.put("rcid", rcid+1);
                newData.put("recipe", myRecipe);
                JSONArray ingrediants = new JSONArray();
                boolean broken = false;
                if(ingrediantbox.getChildCount()==0){
                    throw new NoIngrediantsException("");
                }
                for(int i = 0; i < ingrediantbox.getChildCount(); i++){
                    JSONObject currentIngrediant = new JSONObject();
                    View current = ingrediantbox.getChildAt(i);
                    TextView ingrediantNameTV = current.findViewById(R.id.recipe_ingrediant_template_label);
                    String ingrediantName = ingrediantNameTV.getText().toString();
                    EditText ingrediantQtyET = current.findViewById(R.id.add_recipe_ingrediant_qty);
                    double ingrediantQty;
                    try {
                        ingrediantQty = Double.parseDouble(ingrediantQtyET.getText().toString());
                    } catch (NumberFormatException e){
                        throw new QuantityMissingException(ingrediantName);
                    }
                    Spinner unitSpinner = current.findViewById(R.id.unitSelect);
                    String unitChoice = unitSpinner.getSelectedItem().toString();
                    try{if(tempMap.get(ingrediantName)==null){
                        throw new IngrediantMissingException(ingrediantName); }
                    }catch (Exception e){
                        throw new IngrediantMissingException(ingrediantName);
                    }
                    currentIngrediant.put("id", tempMap.get(ingrediantName));
                    currentIngrediant.put("name", ingrediantName);
                    currentIngrediant.put("qty", ingrediantQty);
                    currentIngrediant.put("unit", unitChoice);
                    if(ingrediantQty == 0){
                        myRoot.put("input", ingrediantName + " can't have a quantity of zero");
                        broken = true;
                    }
                    ingrediants.put(i, currentIngrediant);
                }
                //System.out.println("I RUN AT THE END"+ ingrediants);
                newData.put("ingrediants", ingrediants);
                newData.put("userid",vars.userid);
                subInput.put("data", newData);
                if(!broken){ myRoot.put("input", subInput); }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return myRoot;
    }

    public void handleNewRecipe(JSONObject input){
        //System.out.println(input);
        try{
            if(input.get("input") instanceof String){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("").setMessage(input.get("input").toString()).setIcon(
                        android.R.drawable.ic_dialog_alert).setNegativeButton("Ok", null).show();
            } else {
                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                JsonObjectRequest MyJSONRequest = new JsonObjectRequest("http://cgi.sice.indiana.edu/~team39/api.php",
                        input,
                        response -> {
                            System.out.println(response);
                            Intent intent = new Intent(getActivity(), RecipePage.class);
                            startActivity(intent);
                        }, Throwable::printStackTrace);
                requestQueue.add(MyJSONRequest);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            //recipeImage.setImageURI(selectedImage);
            Glide
                    .with(this)
                    .load(selectedImage)
                    .into(recipeImage);
            try {
                InputStream imageStream = getContext().getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                imageStream.close();
                image = encodedImage;
            } catch (Exception e){
                e.printStackTrace();
                //System.out.println("I done goof");
            }
        }
    }

    @SuppressLint("InflateParams")
    public void setPage(int myPage){
        int removeNum = add_recipe_root.getChildCount();
        back_button_add_recipe.setVisibility(View.VISIBLE);
        next_button_add_recipe.setVisibility(View.VISIBLE);
        finish_button.setVisibility(View.GONE);
        switch(myPage){

            case 1:
                back_button_add_recipe.setVisibility(View.INVISIBLE);
                for(int i = 0; i < removeNum; i++){ add_recipe_root.removeViewAt(i); }
                add_recipe_root.addView(page1Layout);
                break;
            case 2:
                for(int i = 0; i < removeNum; i++){ add_recipe_root.removeViewAt(i); }
                add_recipe_root.addView(page2Layout);
                break;
            case 3:
                for(int i = 0; i < removeNum; i++){ add_recipe_root.removeViewAt(i); }
                add_recipe_root.addView(page3Layout);
                break;
            case 4:
                for(int i = 0; i < removeNum; i++){ add_recipe_root.removeViewAt(i); }
                add_recipe_root.addView(page4Layout);
                break;
            case 5:
                next_button_add_recipe.setVisibility(View.GONE);
                finish_button.setVisibility(View.VISIBLE);
                showIngrediantsPrompts();
                for(int i = 0; i < removeNum; i++){ add_recipe_root.removeViewAt(i); }
                add_recipe_root.addView(page5Layout);
                break;

        }
        mainLayout = (LinearLayout) li.inflate(R.layout.recipe_add_page_1, null);
    }

}
