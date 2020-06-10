package com.example.keepin_It_Fresh;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class ViewMainForum extends Fragment {

    int pos = 0;

    int maxScroll = 2;

    ImageButton rightArrow;
    ImageButton leftArrow;

    private ImageButton drawer_button;


    ImageButton kitchenPic;


    TextView kitchenTime;
    TextView kitchenDescription;
    TextView title;
    LinearLayout layout;
    Button tempBtn;



    //    ArrayList<String> time;
//    ArrayList<String> desc;
    String[] time = {"45-60","60-70","30-40"};
    String[] desc = {"A dish that brings you to the belly of the sea with a fresh and healthy taste",
            "This dish has been lurking deep beneath the abyss of the ocean, waiting to be discovered by you",
            "A SNAPPING meal. This plate is as delicious as it is expensive."};
    String[] recipeName = {"Lemon-zest cod",
            "Squid-ink noodles",
            "Lobster tail cuisine"};

    LinearLayout kitchenPicLayout;

    //Button goToAdvertiserRecipes;



    public ViewMainForum() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_view_main_forum, container, false);

        layout = view.findViewById(R.id.recipeForumHomeLayout);

        rightArrow = view.findViewById(R.id.rightArrow);
        leftArrow = view.findViewById(R.id.leftArrow);
        kitchenTime = view.findViewById(R.id.kitchenTime);
        kitchenDescription = view.findViewById(R.id.kitchenDescription);
        title = view.findViewById(R.id.forumTitle);

        tempBtn = view.findViewById(R.id.view_all_categories_temp);
        tempBtn.setOnClickListener(v -> {

            FragmentTransaction ft;
            FragmentManager fm = ((ForumPage)getActivity()).getFm();
            ft = fm.beginTransaction();
            Fragment myFragment = new ViewCategoryForum();
            ft.replace(R.id.forum_fragment_container, myFragment);
            ft.commit();
        });


        kitchenPicLayout = view.findViewById(R.id.kitchenPicLayout);

//        time = new ArrayList<>();
//        time.add("45-60");
//        time.add("60-70");
//        time.add("30-40");

//        desc = new ArrayList<>();
//        time.add("A dish that brings you to the belly of the sea with a fresh and healthy taste");
//        time.add("This dish has been lurking deep beneath the abyss of the ocean, waiting to be discovered by you");
//        time.add("A SNAPPING meal. This plate is as delicious as it is expensive. ");


        makeCategories();

        buildKitchen(kitchenPicLayout, desc, time);


        rightArrow.setOnClickListener(v -> {
            kitchenPicLayout.removeAllViews();
            if(pos != maxScroll){
                pos++;
            }else {
                pos = 0;
            }
            buildKitchen(kitchenPicLayout, desc, time);
        });

        leftArrow.setOnClickListener(v -> {
            kitchenPicLayout.removeAllViews();
            if(pos != 0){
                pos--;
            }else {
                pos = 2;
            }

            buildKitchen(kitchenPicLayout, desc, time);
        });

        kitchenPic.setOnClickListener(v -> {
            /*Intent i = new Intent(getActivity(),ForumViewRecipe.class);
//                i.putExtra("choices",recipeid);
            startActivity(i);*/
        });

        drawer_button = view.findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((ForumPage)getActivity()).getNd().openDrawer(Gravity.LEFT);
        });

        return view;
    }

    private void makeCategories(){

        ImageButton btn1 = new ImageButton(getActivity());
        btn1.setLayoutParams(new LinearLayout.LayoutParams(700, 400));
        btn1.setImageResource(R.drawable.category1);
        btn1.setAdjustViewBounds(true);
        btn1.setBackgroundColor(0000);
        btn1.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);

        ImageButton btn2 = new ImageButton(getActivity());
        btn2.setLayoutParams(new LinearLayout.LayoutParams(1000, 400));
        btn2.setImageResource(R.drawable.category2);
        btn2.setAdjustViewBounds(true);
        btn2.setBackgroundColor(0000);
        btn2.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);

        layout.addView(btn1);
        layout.addView(btn2);


//        int category = 1;
//        String file = "category";

//        while (category <= 2) {
//            String fileC = file + category;
    }


    private void buildKitchen(LinearLayout kitchen, String[] desc, String[] time){
        kitchenPic = new ImageButton(getActivity());
//        TransitionManager.beginDelayedTransition(kitchen);
        if(pos == 0){
            kitchenPic.setImageResource(R.drawable.fishlemon);
            kitchenPic.setAdjustViewBounds(true);
            kitchenPic.setBackgroundColor(0000);
            kitchenPic.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);

            kitchenTime.setText("Time: " +time[0].toString() + " min");
            title.setText(recipeName[0]);
            kitchenDescription.setText(desc[0].toString());

        }else if(pos == 1){
            kitchenPic.setImageResource(R.drawable.squidink);
            kitchenPic.setAdjustViewBounds(true);
            kitchenPic.setBackgroundColor(0000);
            kitchenPic.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);

            kitchenTime.setText("Time: " + time[1].toString() + " min");
            kitchenDescription.setText(desc[1].toString());
            title.setText(recipeName[1]);

        }else if(pos == 2){
            kitchenPic.setImageResource(R.drawable.lobstertail);
            kitchenPic.setAdjustViewBounds(true);
            kitchenPic.setBackgroundColor(0000);
            kitchenPic.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);

            kitchenTime.setText("Time: " +time[2].toString()+" min");
            kitchenDescription.setText(desc[2].toString());
            title.setText(recipeName[2]);
        }
        kitchenPicLayout.addView(kitchenPic);
    }


}
