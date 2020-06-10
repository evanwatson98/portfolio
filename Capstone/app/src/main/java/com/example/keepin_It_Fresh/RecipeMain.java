package com.example.keepin_It_Fresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class RecipeMain extends Fragment {

    private ScrollView myScrollerView;
    private ImageButton  editButton, sortButton, drawer_button;
    private CheckBox spicyCheckbox, glutenCheckbox, dairyCheckbox, nutsCheckbox, fishCheckbox,
            sugarfreeCheckbox, halalCheckbox, kosherCheckbox,
            veganCheckbox,vegetarianCheckbox;
    private RadioGroup sortChoice, filterChoice;
    private RadioButton ratingChoice, dontCareChoice, noneOfChoice, onlyChoice;
    private LinearLayout sortFilterContainer;
    private FrameLayout contentContainer;
    private FloatingActionButton addRecipeButton;
    private ViewGroup recipeLayout;
    private String sortKey = "name", filterKey = "dc";
    private boolean isEditMode = false;
    final JSONArray recipes = new JSONArray();
    final ArrayList<String> recipeNames = new ArrayList<String>(), chosenFlags = new ArrayList<>();
    public GlobalVars vars;


    Spinner nationalitySelect;

    public RecipeMain() {
        // Required empty public constructor
    }

    public boolean hasAttr2(JSONObject item, String attr){
        try {
            switch (attr) {
                case "spicy":
                    return item.get("spicy").equals("1");
                case "gluten":
                    return item.get("glutenfree").equals("1");
                case "sugarfree":
                    return item.get("sugarfree").equals("1");
                case "halal":
                    return item.get("halal").equals("1");
                case "kosher":
                    return item.get("kosher").equals("1");
                case "dairy":
                    return item.get("dairy").equals("1");
                case "nuts":
                    return item.get("nuts").equals("1");
                case "fish":
                    return item.get("fish").equals("1");
                default:
                    return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasAttr(JSONObject item, String key){
        //System.out.println(item);
        //System.out.println(key);
        switch(key) {
            case "dc":
                return true;
            case "only":
                boolean truth = true;
                for(String attr : chosenFlags){
                    truth = truth && hasAttr2(item, attr);
                }
                return truth;
            case "none":
                truth = true;
                for(String attr : chosenFlags) {
                    truth = truth && !hasAttr2(item, attr);
                }
                return truth;
            default:
                return false;
        }
    }

    public void displayModules2(String filterkey){
        recipeLayout.removeAllViews();
        try {
            for (int i = 0; i < recipes.length(); i++) {
                if(hasAttr((JSONObject)recipes.get(i), filterkey)){
                    String name = ((JSONObject) recipes.get(i)).get("name").toString();
                    String instructions = ((JSONObject) recipes.get(i)).get("instructions").toString();
                    String id = ((JSONObject) recipes.get(i)).get("recipeid").toString();
                    String url;
                    try {
                        url = ((JSONObject) recipes.get(i)).get("url").toString();
                    } catch (JSONException e){
                        url = "";
                    }
                    if (name.length() > 25) {
                        name = name.substring(0, 25) + "...";
                    }
                    makeRecipeContainer(name, instructions, id, url);
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void displayModules(String sortkey, String filterkey){
        recipeLayout.removeAllViews();
        try {
            ArrayList<JSONObject> sorter = new ArrayList<>();
            for (int i = 0; i < recipes.length(); i++) {
                sorter.add((JSONObject) recipes.get(i));
            }
            //System.out.println(recipes);
            sorter.sort((p1, p2)-> {
                switch (sortkey) {
                    case "name":
                        try {
                            return p1.get("name").toString().compareTo(p2.get("name").toString());
                        } catch (Exception e){
                            e.printStackTrace();
                            return 0;
                        }
                    case "rating":
                        try{
                            return (int)Math.min(Double.parseDouble(p1.get("rating").toString()), Double.parseDouble(p2.get("rating").toString()));
                        } catch (Exception e){
                            e.printStackTrace();
                            return 0;
                        }
                    case "ingrediants":
                        try{
                            return p2.get("ingredientscount").toString().compareTo(p1.get("ingredientscount").toString());
                        } catch (Exception e){
                            e.printStackTrace();
                            return 0;
                        }
                    default:
                        return 0;
                }
            });
            //System.out.println(sorter);
            for (int i = sorter.size()-1; i >=0 ; i--) {
                if(hasAttr(sorter.get(i), filterkey)){
                    String name = sorter.get(i).get("name").toString();
                    String instructions = sorter.get(i).get("instructions").toString();
                    String id = sorter.get(i).get("recipeid").toString();
                    String url;
                    try {
                        url = ((JSONObject) sorter.get(i)).get("url").toString();
                    } catch (JSONException e){
                        url = "";
                    }
                    if (name.length() > 25) {
                        name = name.substring(0, 25) + "...";
                    }
                    makeRecipeContainer(name, instructions, id, url);
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void deleteEditRecipe(String id){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject rootJson = new JSONObject();
        JSONObject dataJson = new JSONObject();
        JSONObject myjson = new JSONObject();
        try {
            myjson.put("action", "deleterecipe");
            dataJson.put("recipeid", id);
            dataJson.put("userid", vars.userid);
            myjson.put("data", dataJson);
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            rootJson.put("input", myjson);
        } catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println(rootJson);
        JsonObjectRequest MyJSONRequest = new JsonObjectRequest("http://cgi.sice.indiana.edu/~team39/api.php", rootJson, response -> {
            try {

                selectRecipe(vars.userid);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    displayModules(sortKey,filterKey);
                } else {
                    displayModules2(filterKey);
                }
            } catch (Exception e){
                //System.out.println("oof");
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(MyJSONRequest);
    }

    @SuppressLint("InflateParams")
    public void makeRecipeContainer(String name, String instructions, String id, String url){
        LinearLayout recipeContainer;
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String tempid = id;
        LayoutInflater li = getLayoutInflater();
        if(isEditMode) recipeContainer = (LinearLayout) li.inflate(R.layout.recipe_box_template_edit, null);
        else recipeContainer = (LinearLayout) li.inflate(R.layout.recipe_box_template, null);
        TextView recipe_cont_lab = recipeContainer.findViewById(R.id.recipe_container_textview);
        recipe_cont_lab.setText(name);
        ImageView recipeImage = recipeContainer.findViewById(R.id.recipe_image_main);
        if(!url.equals("")){
            try {
                ImageRequest request = new ImageRequest(url, recipeImage::setImageBitmap, 0, 0, null, volleyError -> {recipeImage.setVisibility(View.GONE);});
                requestQueue.add(request);
            } catch (Exception e){recipeImage.setVisibility(View.GONE);}
        } else {

        }
        TextView recipe_cont_instr = recipeContainer.findViewById(R.id.instructions_textview_recipe_box);
        recipe_cont_instr.setText(instructions);
        ImageButton toggleButton = recipeContainer.findViewById(R.id.recipe_toggle_button);
        Button use_recipe_button = recipeContainer.findViewById(R.id.use_button);
        use_recipe_button.setOnClickListener(view -> {

            FragmentTransaction ft;
            FragmentManager fm = ((RecipePage)getActivity()).getFm();
            Fragment myFragment = new RecipeUse1();
            Bundle args = new Bundle();
            args.putString("id", tempid);
            myFragment.setArguments(args);
            ft = fm.beginTransaction();
            ft.replace(R.id.recipe_fragment_container, myFragment);
            ft.commit();
        });
        final LinearLayout expandedRecipeBox = recipeContainer.findViewById(R.id.expanded_recipe_box);
        toggleButton.setOnClickListener(view -> {
            if(expandedRecipeBox.getVisibility()==View.VISIBLE){
                expandedRecipeBox.setVisibility(View.GONE);
            } else {
                expandedRecipeBox.setVisibility(View.VISIBLE);
            }
        });
        if(isEditMode){
            ImageButton recipeContDelete = recipeContainer.findViewById(R.id.recipe_cont_delete);
            ImageButton recipeContEdit = recipeContainer.findViewById(R.id.recipe_cont_edit);
            recipeContDelete.setOnClickListener(view -> {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you would like to delete this recipe?\nThis action cannot be undone");
                builder.setTitle("Are you sure?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", (d, which) -> d.cancel());
                builder.setPositiveButton("Yes", (d, which) -> deleteEditRecipe(tempid));
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });
            recipeContEdit.setOnClickListener(view -> {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you would like to edit this recipe?");
                builder.setTitle("Are you sure?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", (d, which) -> d.cancel());
                builder.setPositiveButton("Yes", (d, which) -> {

                    FragmentTransaction ft;
                    FragmentManager fm = ((RecipePage)getActivity()).getFm();
                    ft = fm.beginTransaction();
                    Fragment myFragment = new RecipeAdd();
                    Bundle args = new Bundle();
                    args.putString("id", tempid);
                    myFragment.setArguments(args);
                    ft.replace(R.id.recipe_fragment_container, myFragment);
                    ft.commit();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });

        }
        recipeLayout.addView(recipeContainer, 0);
    }

    public void toggleCheckChange(String item, boolean isChecked){
        if(isChecked){
            chosenFlags.add(item);
        } else {
            chosenFlags.remove(item);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            displayModules(sortKey,filterKey);
        } else {
            displayModules2(filterKey);
        }
    }

    private void selectRecipe(String userid) {

        for(int i = 0; i < recipes.length(); i++){
            recipes.remove(0);
        }

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject rootJson = new JSONObject();

        JSONObject myjson = new JSONObject();
        try {
            myjson.put("action", "selectrecipe");
            myjson.put("data", userid);
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
                        System.out.println(response);

                        JSONArray items = (JSONArray)response.get("data");
                        for(int i = 0; i < items.length(); i++){
                            recipes.put(i, (items.get(i)));
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            displayModules(sortKey,filterKey);
                        } else {
                            displayModules2(filterKey);
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        requestQueue.add(MyJSONRequest);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_main, container, false);
        drawer_button = view.findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((RecipePage)getActivity()).getNd().openDrawer(Gravity.LEFT);
        });
        myScrollerView = view.findViewById(R.id.scrollview);
        nationalitySelect = view.findViewById(R.id.nationality_select);
        editButton = view.findViewById(R.id.editButton); sortButton = view.findViewById(R.id.sortButton);
        spicyCheckbox = view.findViewById(R.id.spicy_checkbox_2); glutenCheckbox = view.findViewById(R.id.gluten_checkbox_2); dairyCheckbox = view.findViewById(R.id.checkbox_diary_2);
        sugarfreeCheckbox= view.findViewById(R.id.sugarfree_checkbox_2); halalCheckbox = view.findViewById(R.id.halal_checkbox_2); kosherCheckbox = view.findViewById(R.id.kosher_checkbox_2);
        veganCheckbox = view.findViewById(R.id.vegan_checkbox_2); vegetarianCheckbox = view.findViewById(R.id.vegetarian_checkbox_2);
        sortChoice = view.findViewById(R.id.rating_choice); filterChoice = view.findViewById(R.id.filter_choice);
        ratingChoice = view.findViewById(R.id.sort_recipe_rating_choice); dontCareChoice = view.findViewById(R.id.filter_recipe_dont_care);
        sortFilterContainer = view.findViewById(R.id.filter_sort_container); contentContainer = view.findViewById(R.id.contentContainer);
        addRecipeButton = view.findViewById(R.id.add_recipe_button);
        recipeLayout = view.findViewById(R.id.recipeContainer);

        vars = GlobalVars.getInstance();
        selectRecipe(vars.userid);
        addRecipeButton.setOnClickListener(v -> {

            FragmentTransaction ft;
            FragmentManager fm = ((RecipePage)getActivity()).getFm();
            ft = fm.beginTransaction();
            ft.replace(R.id.recipe_fragment_container, new RecipeAdd());
            ft.commit();
        });
        spicyCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> toggleCheckChange("spicy", isChecked));
        glutenCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> toggleCheckChange("gluten", isChecked));
        dairyCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> toggleCheckChange("dairy", isChecked));
        sugarfreeCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> toggleCheckChange("sugarfree", isChecked));
        halalCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> toggleCheckChange("halal", isChecked));
        kosherCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> toggleCheckChange("kosher", isChecked));
        veganCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> toggleCheckChange("vegan", isChecked));
        vegetarianCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> toggleCheckChange("vegetarian", isChecked));
        editButton.setOnClickListener(v -> {
            isEditMode = !isEditMode;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                displayModules(sortKey,filterKey);
            } else {
                displayModules2(filterKey);
            }
        });
        sortButton.setOnClickListener(v -> {
            if(sortFilterContainer.getVisibility() == View.GONE){
                sortFilterContainer.setVisibility(View.VISIBLE);
                contentContainer.setVisibility(View.INVISIBLE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentContainer.getLayoutParams();
                params.weight = 0.2f;
                contentContainer.setLayoutParams(params);
            } else {
                sortFilterContainer.setVisibility(View.GONE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentContainer.getLayoutParams();
                params.weight = 0.8f;
                contentContainer.setLayoutParams(params);
                contentContainer.setVisibility(View.VISIBLE);
            }
        });
        ratingChoice.setChecked(true);
        dontCareChoice.setChecked(true);
        sortChoice.setOnCheckedChangeListener((group, checkedId) -> {
            //System.out.println("Im running checkid is "+ checkedId);
            switch (checkedId){
                case R.id.sort_recipe_rating_choice:
                    sortKey = "rating";
                    break;
                case R.id.sort_recipe_name_choice:
                    sortKey = "name";
                    break;
                case R.id.sort_recipe_ingrediants_choice:
                    sortKey = "ingrediants";
                    break;
                default:
                    break;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                displayModules(sortKey,filterKey);
            } else {
                displayModules2(filterKey);
            }
        });
        filterChoice.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case R.id.filter_recipe_dont_care:
                    filterKey = "dc";
                    break;
                case R.id.filter_recipe_only:
                    filterKey = "only";
                    break;
                case R.id.filter_recipe_none_of:
                    filterKey = "none";
                    break;
                default:
                    break;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                displayModules(sortKey,filterKey);
            } else {
                displayModules2(filterKey);
            }
        });
        return view;
    }


}
