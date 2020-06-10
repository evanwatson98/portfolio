package com.example.keepin_It_Fresh;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AdvertiserForumPage extends AppCompatActivity {

    LinearLayout topAdvertiserRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_advertiser);
//        setupViews();
//
//        topAdvertiserRecipes =findViewById(R.id.topAdvertiserRecipes);
//
//        while(int i = 0; i < placement.size(); i++) {
//            LayoutInflater li = getLayoutInflater();
//            ConstraintLayout newItemContainer;
//            newItemContainer = (ConstraintLayout) li.inflate(R.layout.avertiser_top_recipe_box, null);
//            topAdvertiserRecipes.addView(newItemContainer);
//
//            TextView recipePlacement = newItemContainer.findViewById(R.id.recipePlacement);
//            recipePlacement.setText();
//
//            TextView recipName = newItemContainer.findViewById(R.id.recipeName);
//            recipName.setText();
//
//            TextView recipeCreator = newItemContainer.findViewById(R.id.recipeCreator);
//            recipeCreator.setText();
//
//
//        }
    }

    public void setupViews () {
        topAdvertiserRecipes =  findViewById(R.id.topAdvertiserRecipes);
    }
}
