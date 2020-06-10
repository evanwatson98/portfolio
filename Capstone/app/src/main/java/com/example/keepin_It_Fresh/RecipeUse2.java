package com.example.keepin_It_Fresh;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.*;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class RecipeUse2 extends Fragment {


    String id;
    LinearLayout recipeBoxRoot;
    GlobalVars vars = GlobalVars.getInstance();
    ImageButton drawer_button;

    public RecipeUse2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_use2, container, false);
        Bundle extras =  getArguments();
        id = Objects.requireNonNull(extras.get("id")).toString();
        drawer_button = view.findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((RecipePage)getActivity()).getNd().openDrawer(Gravity.LEFT);
        });
        recipeBoxRoot = view.findViewById(R.id.recipe_2_box_root);
        getResponse(id);
        Button finish_button = view.findViewById(R.id.use_recipe_finish);
        finish_button.setOnClickListener(v -> dataCollect());

        // Inflate the layout for this fragment
        return view;
    }

    public void dataCollect()  {

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());


        org.json.JSONObject rootData = new org.json.JSONObject();
        try {
            org.json.JSONObject returnData = new org.json.JSONObject();
            org.json.JSONArray ingrediants = new org.json.JSONArray();
            returnData.put("userid", vars.userid);
            for(int i = 0; i < recipeBoxRoot.getChildCount(); i++){

                JSONObject currentIngrediant = new JSONObject();
                LinearLayout currentLayout = (LinearLayout) recipeBoxRoot.getChildAt(i);
                TextView nameLabel = currentLayout.findViewById(R.id.recipe_2_confirm_name);
                currentIngrediant.put("name", nameLabel.getText().toString());
                TextView qtyLabel = currentLayout.findViewById(R.id.recipe_box_confirm_amount);
                currentIngrediant.put("qty", qtyLabel.getText().toString());
                Spinner unitSpinner = currentLayout.findViewById(R.id.use_recipe_unit_spinner);
                currentIngrediant.put("unit", unitSpinner.getSelectedItem().toString());
                TextView idContainer = currentLayout.findViewById(R.id.idContainer);
                currentIngrediant.put("id", idContainer.getText().toString());
                ingrediants.put(i, currentIngrediant);

            }
            returnData.put("ingrediants", ingrediants);
            rootData.put("input", returnData);
        } catch (JSONException e){
            e.printStackTrace();
        }

        String jsonString = rootData.toString();

        final OkHttpClient myClient = new OkHttpClient();

        RequestBody myRequestBody = new FormBody.Builder()
                .add("json", jsonString)
                .build();

        String myUrl = "http://cgi.sice.indiana.edu/~team39/useRecipe4.php";

        final okhttp3.Request myRequest = new okhttp3.Request.Builder()
                .url(myUrl)
                .post(myRequestBody)
                .build();

        //final TextView statusTextView = findViewById(R.id.daTester);

        myClient.newCall(myRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                System.out.println ("this is the response from recipe use 2:");
                System.out.println (response.body().string());
                getActivity().runOnUiThread(() -> {
                    FragmentTransaction ft;
                    FragmentManager fm = ((RecipePage)getActivity()).getFm();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.recipe_fragment_container, ((RecipePage)getActivity()).getCore());
                    ft.commit();
                });
            }
        });
    }

    public void getResponse(String id){
        final String myid = id;
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest myStringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                "http://cgi.sice.indiana.edu/~team39/recipeCheck.php",
                response -> {
                    try {
                        JSONParser parser = new JSONParser();
                        JSONObject returnJson = (JSONObject)parser.parse(response);
                        JSONArray sufficient = (JSONArray)returnJson.get("sufficient");
                        JSONArray insufficient = (JSONArray)returnJson.get("insufficient");

                        LinearLayout currentLayout;
                        TextView nameLabel, idContainer;

                        Button incrementButton, decrementButton;
                        Spinner unitSpinner;
                        System.out.println(returnJson);

                        if(Objects.requireNonNull(sufficient).size() > 0){

                            String fid, name, recipeQty, userMeas, recipeMeas;
                            for (Object o : sufficient) {
                                LayoutInflater li = getLayoutInflater();
                                JSONObject current = (JSONObject) o;
                                currentLayout = (LinearLayout) li.inflate(R.layout.use_recipe_confirmation_box_template, null);
                                incrementButton = currentLayout.findViewById(R.id.use_recipe_confirm_increment);
                                decrementButton = currentLayout.findViewById(R.id.use_recipe_confirm_decrement);
                                fid = Objects.requireNonNull(current.get("id")).toString();
                                idContainer = currentLayout.findViewById(R.id.idContainer);
                                idContainer.setText(fid);
                                name = Objects.requireNonNull(current.get("name")).toString();
                                nameLabel = currentLayout.findViewById(R.id.recipe_2_confirm_name);
                                nameLabel.setText(name);
                                recipeQty = Objects.requireNonNull(current.get("recipeQty")).toString();
                                final String userQty = Objects.requireNonNull(current.get("userQty")).toString();
                                final EditText amountLabel = currentLayout.findViewById(R.id.recipe_box_confirm_amount);
                                amountLabel.setText(recipeQty);
                                recipeMeas = Objects.requireNonNull(current.get("recipeMeas")).toString();

                                //parts relevant to conversion

                                unitSpinner = currentLayout.findViewById(R.id.use_recipe_unit_spinner);

                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                                        R.array.unit_select, android.R.layout.simple_spinner_item);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                                        R.array.unit_only, android.R.layout.simple_spinner_item);
                                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                int index = 0;

                                userMeas = current.get("userMeas").toString();
                                if (userMeas.equals("Units/Servings")) {
                                    unitSpinner.setAdapter(adapter2);
                                    index = adapter2.getPosition(recipeMeas);
                                    unitSpinner.setSelection(index);
                                }

                                else {
                                    unitSpinner.setAdapter(adapter);
                                    index = adapter.getPosition(recipeMeas);
                                    unitSpinner.setSelection(index);
                                }

                                //done
                                incrementButton.setOnClickListener(v -> {
                                    double amount = Double.parseDouble(amountLabel.getText().toString());
                                    if (amount < Double.parseDouble(userQty)) {
                                        amountLabel.setText(String.format("%.2f",amount + 0.1));
                                    } else {
                                        amountLabel.setText(userQty);
                                    }
                                });
                                decrementButton.setOnClickListener(v -> {
                                    double amount = Double.parseDouble(amountLabel.getText().toString());
                                    if (amount > 0) {
                                        amountLabel.setText(String.format("%.2f", amount - 0.1));
                                    } else {
                                        amountLabel.setText("0");
                                    }
                                });
                                recipeBoxRoot.addView(currentLayout, 0);
                            }

                        }
                        if(Objects.requireNonNull(insufficient).size() > 0){
                            String fid, name, userMeas, recipeQty, recipeMeas;
                            for (Object o : insufficient) {
                                LayoutInflater li = getLayoutInflater();
                                JSONObject current = (JSONObject) o;

                                currentLayout = (LinearLayout) li.inflate(R.layout.use_recipe_confirmation_box_template, null);
                                incrementButton = currentLayout.findViewById(R.id.use_recipe_confirm_increment);
                                decrementButton = currentLayout.findViewById(R.id.use_recipe_confirm_decrement);
                                fid = Objects.requireNonNull(current.get("id")).toString();
                                idContainer = currentLayout.findViewById(R.id.idContainer);
                                idContainer.setText(fid);
                                name = Objects.requireNonNull(current.get("name")).toString();
                                nameLabel = currentLayout.findViewById(R.id.recipe_2_confirm_name);
                                nameLabel.setText(name);

                                final String userQty = Objects.requireNonNull(current.get("userQty")).toString();
                                //userMeas = current.get("userMeas").toString();
                                final EditText amountLabel = currentLayout.findViewById(R.id.recipe_box_confirm_amount);
                                amountLabel.setText(userQty);

                                //started changing here

                                recipeMeas = Objects.requireNonNull(current.get("recipeMeas")).toString();

                                unitSpinner = currentLayout.findViewById(R.id.use_recipe_unit_spinner);

                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                                        R.array.unit_select, android.R.layout.simple_spinner_item);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                                        R.array.unit_only, android.R.layout.simple_spinner_item);
                                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                int index = 0;

                                userMeas = current.get("userMeas").toString();
                                if (userMeas.equals("Units/Servings")) {
                                    unitSpinner.setAdapter(adapter2);
                                    index = adapter2.getPosition(recipeMeas);
                                    unitSpinner.setSelection(index);
                                }

                                else {
                                    unitSpinner.setAdapter(adapter);
                                    index = adapter.getPosition(recipeMeas);
                                    unitSpinner.setSelection(index);
                                }

                                //stopped changes here

                                recipeQty = Objects.requireNonNull(current.get("recipeQty")).toString();
                                incrementButton.setOnClickListener(v -> {
                                    double amount = Double.parseDouble(amountLabel.getText().toString());
                                    if (amount < Double.parseDouble(userQty)) {
                                        amountLabel.setText(Double.toString(amount + 0.1));
                                    } else {
                                        amountLabel.setText(userQty);
                                    }
                                });
                                decrementButton.setOnClickListener(v -> {
                                    double amount = Double.parseDouble(amountLabel.getText().toString());
                                    if (amount > 0) {
                                        amountLabel.setText(Double.toString(amount - 0.1));
                                    } else {
                                        amountLabel.setText("0");
                                    }
                                });
                                recipeBoxRoot.addView(currentLayout, 0);
                            }

                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace) { protected Map<String, String> getParams() {
            Map<String, String> data = new HashMap<>();

            data.put("recipeid", myid);
            data.put("userid", vars.userid);
            return data;
        }
        };

        requestQueue.add(myStringRequest);
    }

    private String readFile() {
        try {
            String fileName = "userInformation.txt";
            FileInputStream fileInputStream = getActivity().openFileInput(fileName);
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