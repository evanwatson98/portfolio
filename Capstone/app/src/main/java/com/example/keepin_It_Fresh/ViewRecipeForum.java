package com.example.keepin_It_Fresh;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;


public class ViewRecipeForum extends Fragment {

    private TextView nameView, instructionsView;
    private EditText ratingEntry;
    private LinearLayout fullStars, currentStars;
    private ImageView recipeImage;
    private Button ratingButton;
    private ImageButton drawer_button;
    GlobalVars vars = GlobalVars.getInstance();

    public ViewRecipeForum() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_recipe_forum, container, false);

        Bundle extras = getArguments();
        drawer_button = view.findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((ForumPage)getActivity()).getNd().openDrawer(Gravity.LEFT);
        });
        instructionsView = view.findViewById(R.id.forum_recipe_view_instructions);
        nameView = view.findViewById(R.id.forum_recipe_view_name);
        ratingEntry = view.findViewById(R.id.recipeRatingInput);
        currentStars = view.findViewById(R.id.currentStars);
        fullStars = view.findViewById(R.id.fullstars);
        recipeImage = view.findViewById(R.id.currentRecipeImage);
        ratingButton = view.findViewById(R.id.recipeRatingButton);
        LinearLayout.LayoutParams currentStarParams = (LinearLayout.LayoutParams) currentStars.getLayoutParams();
        LinearLayout.LayoutParams currentSpaceParams = (LinearLayout.LayoutParams) view.findViewById(R.id.currentstars_altspace).getLayoutParams();
        double currentStarWidth = Double.parseDouble(Objects.requireNonNull(extras.get("recipeRating")).toString());
        currentSpaceParams.weight = 1-(float)currentStarWidth/5;
        currentStarParams.weight = (float)currentStarWidth/5;
        currentStars.setLayoutParams(currentStarParams);
        view.findViewById(R.id.currentstars_altspace).setLayoutParams(currentSpaceParams);
        nameView.setText(Objects.requireNonNull(extras.get("recipeName")).toString());
        final String id = Objects.requireNonNull(extras.get("id")).toString();
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        try {
            JSONObject rootJson = new JSONObject();
            JSONObject myjson = new JSONObject();
            try {
                myjson.put("action", "getrecipe");
                myjson.put("data", id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                rootJson.put("input", myjson);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(rootJson);
            JsonObjectRequest MyJSONRequest = new JsonObjectRequest("http://cgi.sice.indiana.edu/~team39/api.php",
                    rootJson,
                    response -> {
                        try {
                            System.out.println(response);
                            JSONObject recipe = ((JSONObject)((JSONArray)response.get("data")).get(0));
                            JSONArray tools = ((JSONArray)((JSONArray)response.get("data")).get(1));
                            JSONArray ingrediants = ((JSONArray)((JSONArray)response.get("data")).get(2));
                            String instructions = recipe.get("instructions").toString();
                            String url = recipe.get("url").toString();
                            instructions += "\n\nCooking Time\n";
                            instructions += recipe.get("cooking_time");
                            instructions += "\n\nRecommended Tools:\n";
                            for(int i = 0; i < tools.length(); i++){
                                instructions+= "-"+((JSONObject)tools.get(i)).get("name") + "\n";
                            }
                            instructions += "\n\nIngredients:\n\n";
                            for(int i = 0; i < ingrediants.length(); i++){
                                instructions += ((JSONObject)ingrediants.get(i)).getString("name") + "\n";
                                instructions += ((JSONObject)ingrediants.get(i)).getString("quantity") + " ";
                                instructions += ((JSONObject)ingrediants.get(i)).getString("measurement") + "\n\n";
                            }
                            instructionsView.setText(instructions);
                            Glide
                                    .with(this)
                                    .load(url)
                                    .into(recipeImage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }, Throwable::printStackTrace);

            requestQueue.add(MyJSONRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ratingEntry.setOnKeyListener((v, keyCode, event) -> {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) fullStars.getLayoutParams();
            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) view.findViewById(R.id.fullstars_altspace).getLayoutParams();
            double newWidth;
            if(ratingEntry.getText().toString().equals("")){
                newWidth = 0;
            } else if (ratingEntry.getText().toString().equals(".")){
                newWidth = 0;
            } else {
                newWidth = Double.parseDouble(ratingEntry.getText().toString());
            }
            params.weight = (float)newWidth/5;
            params2.weight = 1 - (float)newWidth/5;
            fullStars.setLayoutParams(params);
            view.findViewById(R.id.fullstars_altspace).setLayoutParams(params2);
            return false;
        });
        ratingButton.setOnClickListener(v -> {
            JSONObject rootJson = new JSONObject();
            JSONObject myjson = new JSONObject();
            JSONObject dataJson = new JSONObject();
            try {
                dataJson.put("userid",vars.userid);
                dataJson.put("recipeid",id);
                String rating = ratingEntry.getText().toString();
                double ratingValue = Double.parseDouble(rating);
                if(ratingValue > 5){
                    rating = "5";
                } else if(ratingValue < 0){
                    rating = "0";
                }
                dataJson.put("rating",rating);
                myjson.put("action", "raterecipe");
                myjson.put("data", dataJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                rootJson.put("input", myjson);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(rootJson);
            JsonObjectRequest MyJSONRequest = new JsonObjectRequest("http://cgi.sice.indiana.edu/~team39/api.php",
                    rootJson,
                    System.out::println, Throwable::printStackTrace);

            requestQueue.add(MyJSONRequest);
        });

        return view;
    }

  
}
