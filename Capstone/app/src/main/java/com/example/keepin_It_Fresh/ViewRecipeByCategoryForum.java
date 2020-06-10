package com.example.keepin_It_Fresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ViewRecipeByCategoryForum extends Fragment {


    private JSONArray data;
    private LinearLayout iconContainer;
    private int idinquestion;
    private TextView label;
    private ImageButton drawer_button;

    public ViewRecipeByCategoryForum() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_recipe_by_category_forum, container, false);
        Bundle extras = getArguments();
        drawer_button = view.findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((ForumPage)getActivity()).getNd().openDrawer(Gravity.LEFT);
        });
        iconContainer = view.findViewById(R.id.recipe_icon_container_llayout);
        label = view.findViewById(R.id.top_label);
        idinquestion = Integer.parseInt(Objects.requireNonNull(Objects.requireNonNull(extras).get("recipeCatID")).toString());
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
            label.setText(TextUtils.join("\n",Objects.requireNonNull(extras.get("recipeCategory")).toString().split("")));
        } else {
            label.setText(Objects.requireNonNull(extras.get("recipeCategory")).toString());
        }

        selectRecipeCategory();

        return view;
    }

    @SuppressLint("InflateParams")
    private LinearLayout unpackJson(JSONArray input, LayoutInflater li, int position, boolean both, boolean last) throws JSONException {

        LinearLayout current, container1, container2, insert1, insert2, stars;
        TextView iconTime, nameLabel;
        ImageView recipeImage;
        final String name = ((JSONObject) (input.get(position))).get("name").toString();
        final String id = ((JSONObject) (input.get(position))).get("recipeid").toString();
        final String cookingtime = ((JSONObject) (input.get(position))).get("cooking_time").toString();
        final String rating = ((JSONObject) (input.get(position))).get("rating").toString();
        final String url = ((JSONObject) (input.get(position))).get("url").toString();
        current = (LinearLayout) li.inflate(R.layout.two_column_icon_container, null);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            current.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        } else {
            current.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        }
        container1 = current.findViewById(R.id.two_column_icon_container_1);
        insert1 = (LinearLayout) li.inflate(R.layout.forum_recipe_icon_container, null);
        insert1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        recipeImage = insert1.findViewById(R.id.forum_recipe_image);
        Glide
                .with(this)
                .load(url)
                .into(recipeImage);
        iconTime = insert1.findViewById(R.id.recipe_icon_time);
        iconTime.setText(cookingtime);
        nameLabel = insert1.findViewById(R.id.recipe_name_icon_container_label);
        nameLabel.setText(name.length() > 25 ? name.substring(0, 25) + "..." : name);

        stars = insert1.findViewById(R.id.fullstars);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) stars.getLayoutParams();
        params = (LinearLayout.LayoutParams) stars.getLayoutParams();
        float rat;
        try {
            rat = (float) Double.parseDouble(rating);
        } catch (Exception e){
            rat = 0f;
        }
        params.weight = rat;
        Space alt = insert1.findViewById(R.id.altstars);
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) alt.getLayoutParams();
        params2.weight = 1 -rat/5;
        stars.setLayoutParams(params);
        alt.setLayoutParams(params2);

        insert1.setOnClickListener(v -> {

            FragmentTransaction ft;
            FragmentManager fm = ((ForumPage)getActivity()).getFm();
            ft = fm.beginTransaction();
            Fragment myFragment = new ViewRecipeForum();
            Bundle args = new Bundle();
            args.putString("recipeName", name);
            args.putString("recipeRating", rating);
            args.putString("id", id);
            myFragment.setArguments(args);
            ft.replace(R.id.forum_fragment_container, myFragment);
            ft.commit();

        });
        container1.addView(insert1, 0);

        if(both) {
            System.out.println("IM RUNNING");
            final String name2 = ((JSONObject)(input.get(last?position-1:position+1))).get("name").toString();
            final String id2 = ((JSONObject)(input.get(last?position-1:position+1))).get("recipeid").toString();
            final String cookingtime2 = ((JSONObject)(input.get(last?position-1:position+1))).get("cooking_time").toString();
            final String rating2 = ((JSONObject)(input.get(last?position-1:position+1))).get("rating").toString();
            final String url2 = ((JSONObject)(input.get(last?position-1:position+1))).get("url").toString();
            insert2 = (LinearLayout) li.inflate(R.layout.forum_recipe_icon_container, null);
            insert2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            iconTime = insert2.findViewById(R.id.recipe_icon_time);
            iconTime.setText(cookingtime2);
            nameLabel = insert2.findViewById(R.id.recipe_name_icon_container_label);
            nameLabel.setText(name2.length() > 25? name2.substring(0, 25) + "...":name2);
            recipeImage = insert2.findViewById(R.id.forum_recipe_image);
            Glide
                    .with(this)
                    .load(url2)
                    .into(recipeImage);
            container2 = current.findViewById(R.id.two_column_icon_container_2);
            stars = insert2.findViewById(R.id.fullstars);
            params = (LinearLayout.LayoutParams) stars.getLayoutParams();
            float rat2;
            try {
                rat2 = (float)Double.parseDouble(rating2);
            } catch (Exception e){
                rat2 = 0f;
            }
            params.weight = rat2/5;
            alt = insert2.findViewById(R.id.altstars);
            params2 = (LinearLayout.LayoutParams) alt.getLayoutParams();
            params2.weight = 1 - rat2/5;
            stars.setLayoutParams(params);
            alt.setLayoutParams(params2);
            insert2.setOnClickListener(v -> {
                FragmentTransaction ft;
                FragmentManager fm = ((ForumPage)getActivity()).getFm();
                ft = fm.beginTransaction();
                Fragment myFragment = new ViewRecipeForum();
                Bundle args = new Bundle();
                args.putString("recipeName", name2);
                args.putString("recipeRating", rating2);
                args.putString("id", id2);
                myFragment.setArguments(args);
                ft.replace(R.id.forum_fragment_container, myFragment);
                ft.commit();
            });
            container2.addView(insert2, 0);
        }
        return current;
    }

    private void selectRecipeCategory() {

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject rootJson = new JSONObject();

        JSONObject myjson = new JSONObject();
        try {
            myjson.put("action", "selectrecipebycat");
            myjson.put("data", idinquestion);
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            rootJson.put("input", myjson);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(rootJson);
        JsonObjectRequest MyJSONRequest = new JsonObjectRequest("http://cgi.sice.indiana.edu/~team39/api.php",
                rootJson,
                response -> {
                    try {
                        data = (JSONArray) response.get("data");
                        System.out.println(data);
                        LayoutInflater li = getLayoutInflater();
                        for (int i = 0; i < data.length() - 2; i += 2) {

                            iconContainer.addView(unpackJson(data, li, i, true, false));
                        }
                        System.out.println(data.length());
                        if(data.length() > 0) {
                            iconContainer.addView(unpackJson(data, li, data.length()-1, data.length() % 2==0, true));
                        }

                    } catch (Exception e){
                        System.out.println(response);
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        requestQueue.add(MyJSONRequest);

    }



}
