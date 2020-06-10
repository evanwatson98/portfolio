package com.example.keepin_It_Fresh;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ExampleFragment extends Fragment {

    //We need the constructor and onCreateView

    //Declarations of instance variables go here

    public ExampleFragment() {
        // Required empty public constructor
    }

    //All other functions go here

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_example, container, false);
        //ALL ONCREATE CODE GOES HERE
        Bundle extras = getArguments();
        //getActivity() is our new context variable

        /*

        Moving from one fragment to another

        FragmentTransaction ft;
        FragmentManager fm = ((CAST AS THE CURRENT ACTIVITY)getActivity()).getFm();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new NewFragment());
        ft.commit();


        FragmentTransaction ft;
        FragmentManager fm = ((RecipePage)getActivity()).getFm();
        ft = fm.beginTransaction();
        Fragment myFragment = new RecipeAdd();
            Bundle args = new Bundle();
            args.putString("id", tempid);
            myFragment.setArguments(args);
        ft.replace(R.id.recipe_fragment_container, myFragment);
        ft.commit();


         */



        //view.findViewById
        //put a view.findViewById

        //
        return view;
    }


}
