package com.example.DiscoverTheUniverse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BottomSheetDialogFragment {

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment,container,false);

       NavigationView navigationView =view.findViewById(R.id.navigationid);
       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               //RESERVE
               switch (item.getItemId()) {
                   case R.id.asteroids_neows:
                       Intent intent = new Intent(getActivity().getApplication(), asteroids_neows.class);
                       startActivity(intent);
                       break;
                   case R.id.epic:
                       Intent intent2 = new Intent(getActivity().getApplication(), EpicActivity.class);
                       startActivity(intent2);
                       break;
                   case R.id.settingpage:
                       Intent intent3 = new Intent(getActivity().getApplication(), SettingsActivity.class);
                       startActivity(intent3);
                   case R.id.infopage:
                       Intent intent4 = new Intent(getActivity().getApplication(), Info.class);
                       startActivity(intent4);


               }

               return false;
           }
       });
       return view;

    }
}
