package com.example.keepin_It_Fresh;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class RecipeUse1 extends Fragment {

    String id;
    TextView sufficientText, insufficientText , missingText;
    Button useRecipeConfirm, useRecipeDeny;
    ImageButton drawer_button;
    GlobalVars vars = GlobalVars.getInstance();

    public RecipeUse1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_use1, container, false);

        Bundle extras = getArguments();
        drawer_button = view.findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((RecipePage)getActivity()).getNd().openDrawer(Gravity.LEFT);
        });
        sufficientText = view.findViewById(R.id.sufficient_text);
        insufficientText = view.findViewById(R.id.insufficient_text);
        useRecipeConfirm = view.findViewById(R.id.use_recipe_confirm);
        useRecipeDeny = view.findViewById(R.id.use_recipe_deny);
        missingText = view.findViewById(R.id.missing_text);
        id = Objects.requireNonNull(extras).getString("id");
        System.out.println(id);
        getResponse(id);
        useRecipeDeny.setOnClickListener(v -> {
            FragmentTransaction ft;
            FragmentManager fm = ((RecipePage)getActivity()).getFm();
            ft = fm.beginTransaction();
            ft.replace(R.id.recipe_fragment_container, ((RecipePage)getActivity()).getCore());
            ft.commit();
        });
        useRecipeConfirm.setOnClickListener(v -> {
            FragmentTransaction ft;
            Fragment myFragment = new RecipeUse2();
            Bundle args = new Bundle();
            args.putString("id", id);
            myFragment.setArguments(args);
            FragmentManager fm = ((RecipePage)getActivity()).getFm();
            ft = fm.beginTransaction();
            ft.replace(R.id.recipe_fragment_container, myFragment);
            ft.commit();

        });

        return view;
    }



    public void getResponse(String id) {
        final String myid = id;
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest myStringRequest = new StringRequest(Request.Method.POST,
                "http://cgi.sice.indiana.edu/~team39/recipeCheckV2.php",
                response -> {
                System.out.println("The response is "+response);
                    try {
                        JSONParser parser = new JSONParser();
                        JSONObject returnJson = (JSONObject) parser.parse(response);
                        JSONArray sufficient = (JSONArray) returnJson.get("sufficient");
                        JSONArray insufficient = (JSONArray) returnJson.get("insufficient");
                        JSONArray missing = (JSONArray) returnJson.get("missing");
                        StringBuilder bigStr;

                        if (Objects.requireNonNull(sufficient).size() > 0) {
                            bigStr = new StringBuilder("You have enough of:\n");
                            String name, userQty, userMeas, recipeQty, recipeMeas;
                            for (Object o : sufficient) {
                                JSONObject current = (JSONObject) o;
                                name = Objects.requireNonNull(current.get("name")).toString();
                                userQty = Objects.requireNonNull(current.get("userQty")).toString();
                                userMeas = Objects.requireNonNull(current.get("userMeas")).toString();
                                recipeQty = Objects.requireNonNull(current.get("recipeQty")).toString();
                                recipeMeas = Objects.requireNonNull(current.get("recipeMeas")).toString();
                                bigStr.append(name).append(":  ").append(userQty).append(userMeas).append("/").append(recipeQty).append(recipeMeas).append("\n");
                            }
                            sufficientText.setText(bigStr.toString());
                        }
                        if (Objects.requireNonNull(insufficient).size() > 0) {

                            bigStr = new StringBuilder("You don't have enough of:\n");
                            String name, userQty, userMeas, recipeQty, recipeMeas;
                            for (Object o : insufficient) {
                                JSONObject current = (JSONObject) o;
                                name = Objects.requireNonNull(current.get("name")).toString();
                                userQty = Objects.requireNonNull(current.get("userQty")).toString();
                                userMeas = Objects.requireNonNull(current.get("userMeas")).toString();
                                recipeQty = Objects.requireNonNull(current.get("recipeQty")).toString();
                                recipeMeas = Objects.requireNonNull(current.get("recipeMeas")).toString();
                                bigStr.append(name).append(":  ").append(userQty).append(userMeas).append("/").append(recipeQty).append(recipeMeas).append("\n");
                            }
                            insufficientText.setText(bigStr.toString());
                        }
                        if (Objects.requireNonNull(missing).size() > 0) {
                            bigStr = new StringBuilder("You don't have any of:\n");
                            String name, recipeQty, recipeMeas;
                            for (Object o : missing) {
                                JSONObject current = (JSONObject) o;
                                name = Objects.requireNonNull(current.get("name")).toString();
                                recipeQty = Objects.requireNonNull(current.get("recipeQty")).toString();
                                recipeMeas = Objects.requireNonNull(current.get("recipeMeas")).toString();
                                bigStr.append(name).append(": Required: ").append(recipeQty).append(recipeMeas).append("\n");
                            }
                            missingText.setText(bigStr.toString());
                            System.out.println("The request runs");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                //String userid = readFile();
                data.put("recipeid", myid);
                data.put("userid", vars.userid);
                return data;
            }
        };
        System.out.println("the request is sent");
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
