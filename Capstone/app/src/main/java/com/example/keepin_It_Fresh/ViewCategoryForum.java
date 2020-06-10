package com.example.keepin_It_Fresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

public class ViewCategoryForum extends Fragment {

    private JSONArray data;
    private LinearLayout iconContainer;
    private ImageButton drawer_button;


    public ViewCategoryForum() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_category_forum, container, false);
        iconContainer = view.findViewById(R.id.recipe_icon_container_llayout);
        drawer_button = view.findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((ForumPage)getActivity()).getNd().openDrawer(Gravity.LEFT);
        });
        getCategories();
        return view;
    }

    private void getCategories() {

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject rootJson = new JSONObject();

        JSONObject myjson = new JSONObject();
        try {
            myjson.put("action", "selectrecipecat");
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            rootJson.put("input", myjson);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(rootJson);
        @SuppressLint("InflateParams") JsonObjectRequest MyJSONRequest = new JsonObjectRequest("http://cgi.sice.indiana.edu/~team39/api.php",
                rootJson,
                response -> {
                    try {
                        data = (JSONArray)response.get("data");
                        LayoutInflater li = getLayoutInflater();
                        LinearLayout current, container1, container2, insert1, insert2;
                        TextView item1, item2;
                        for(int i = 0; i < data.length()-2; i+=2){
                            final String name1 = ((JSONObject)data.get(i)).get("name").toString();
                            final String id1 = ((JSONObject)data.get(i)).get("rcid").toString();
                            final String name2 = ((JSONObject)data.get(1+i)).get("name").toString();
                            final String id2 = ((JSONObject)data.get(1+i)).get("rcid").toString();
                            current = (LinearLayout)li.inflate(R.layout.two_column_icon_container, null);
                            if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
                                current.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                            } else {
                                current.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                            }
                            container1 = current.findViewById(R.id.two_column_icon_container_1);
                            container2 = current.findViewById(R.id.two_column_icon_container_2);
                            insert1 = (LinearLayout)li.inflate(R.layout.icon_category_container, null);
                            insert1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                            insert2 = (LinearLayout)li.inflate(R.layout.icon_category_container, null);
                            insert2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                            item1 = insert1.findViewById(R.id.forum_category_name_container);
                            item2 = insert2.findViewById(R.id.forum_category_name_container);
                            item1.setText(name1);
                            item2.setText(name2);
                            insert1.setOnClickListener(v -> {

                                FragmentTransaction ft;
                                FragmentManager fm = ((ForumPage)getActivity()).getFm();
                                ft = fm.beginTransaction();
                                Fragment myFragment = new ViewRecipeByCategoryForum();
                                Bundle args = new Bundle();
                                args.putString("recipeCategory", name1);
                                args.putString("recipeCatID", id1);
                                myFragment.setArguments(args);
                                ft.replace(R.id.forum_fragment_container, myFragment);
                                ft.commit();

                            });
                            insert2.setOnClickListener(v -> {

                                FragmentTransaction ft;
                                FragmentManager fm = ((ForumPage)getActivity()).getFm();
                                ft = fm.beginTransaction();
                                Fragment myFragment = new ViewRecipeByCategoryForum();
                                Bundle args = new Bundle();
                                args.putString("recipeCategory", name2);
                                args.putString("recipeCatID", id2);
                                myFragment.setArguments(args);
                                ft.replace(R.id.forum_fragment_container, myFragment);
                                ft.commit();

                            });
                            container1.addView(insert1, 0);
                            container2.addView(insert2, 0);
                            iconContainer.addView(current);
                        }
                        final String name1 = ((JSONObject)data.get(data.length()-1)).get("name").toString();
                        final String id1 = ((JSONObject)data.get(data.length()-1)).get("rcid").toString();
                        current = (LinearLayout)li.inflate(R.layout.two_column_icon_container, null);
                        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
                            current.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                        } else {
                            current.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                        }
                        container1 = current.findViewById(R.id.two_column_icon_container_1);
                        insert1 = (LinearLayout)li.inflate(R.layout.icon_category_container, null);
                        insert1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                        item1 = insert1.findViewById(R.id.forum_category_name_container);
                        item1.setText(name1);
                        container1.addView(insert1, 0);
                        container2 = current.findViewById(R.id.two_column_icon_container_2);
                        insert2 = (LinearLayout)li.inflate(R.layout.icon_category_container, null);
                        insert2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                        item2 = insert2.findViewById(R.id.forum_category_name_container);
                        insert1.setOnClickListener(v -> {

                            FragmentTransaction ft;
                            FragmentManager fm = ((ForumPage)getActivity()).getFm();
                            ft = fm.beginTransaction();
                            Fragment myFragment = new ViewRecipeByCategoryForum();
                            Bundle args = new Bundle();
                            args.putString("recipeCategory", name1);
                            args.putString("recipeCatID", id1);
                            myFragment.setArguments(args);
                            ft.replace(R.id.forum_fragment_container, myFragment);
                            ft.commit();

                        });

                        if(data.length()%2==0){
                            final String name2 = ((JSONObject)data.get(data.length()-2)).get("name").toString();
                            final String id2 = ((JSONObject)data.get(data.length()-2)).get("rcid").toString();
                            item2.setText(name2);
                            insert2.setOnClickListener(v -> {

                                FragmentTransaction ft;
                                FragmentManager fm = ((ForumPage)getActivity()).getFm();
                                ft = fm.beginTransaction();
                                Fragment myFragment = new ViewRecipeByCategoryForum();
                                Bundle args = new Bundle();
                                args.putString("recipeCategory", name2);
                                args.putString("recipeCatID", id2);
                                myFragment.setArguments(args);
                                ft.replace(R.id.forum_fragment_container, myFragment);
                                ft.commit();
                            });
                        } else {
                            item2.setText("");
                        }
                        container2.addView(insert2, 0);
                        iconContainer.addView(current);

                    } catch (Exception e){
                        System.out.println(response);
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        requestQueue.add(MyJSONRequest);

    }

}
