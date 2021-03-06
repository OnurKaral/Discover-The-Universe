package com.example.discovertheuniverse.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.discovertheuniverse.FavoritesActivity;
import com.example.discovertheuniverse.ProfileActivity;
import com.example.discovertheuniverse.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BottomSheetDialogFragment {

    public BlankFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment,container,false);

        final NavigationView navigationView = view.findViewById(R.id.navigationid);
       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               switch (item.getItemId()) {
                   case R.id.asteroids_neows:
                       Intent intent = new Intent(getActivity().getApplication(), asteroids_neo.class);
                       startActivity(intent);
                       break;

                   case R.id.profile:
                       Intent intent2 = new Intent(getActivity().getApplication(), ProfileActivity.class);
                       startActivity(intent2);
                       break;

                   case R.id.infopage:
                       Intent intent4 = new Intent(getActivity().getApplication(), Info.class);
                       startActivity(intent4);
                       break;

                   case R.id.favorites:
                       Intent intent5 = new Intent(getActivity().getApplication(), FavoritesActivity.class);
                       startActivity(intent5);
               }
               return false;
           }
       });
       return view;

    }
}
