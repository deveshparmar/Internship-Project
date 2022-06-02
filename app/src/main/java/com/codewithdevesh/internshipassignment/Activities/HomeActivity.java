package com.codewithdevesh.internshipassignment.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.MenuItem;


import com.codewithdevesh.internshipassignment.R;
import com.codewithdevesh.internshipassignment.Fragments.Tab1;
import com.codewithdevesh.internshipassignment.Fragments.Tab2;
import com.codewithdevesh.internshipassignment.Fragments.Tab3;
import com.codewithdevesh.internshipassignment.Utils.Utils;
import com.codewithdevesh.internshipassignment.databinding.ActivityHomeBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationBarView;


public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    final Tab1 tab1 = new Tab1();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        Utils.setStatusBarColor(this,R.color.light_background);
        getSupportFragmentManager().beginTransaction().replace(R.id.parentContainer,tab1).commit();
        binding.navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            final Tab2 tab2 = new Tab2();
            final Tab3 tab3 = new Tab3();
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tb1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.parentContainer,tab1).commit();
                        return true;
                    case R.id.tb2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.parentContainer,tab2).commit();
                        return true;
                    case R.id.tb3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.parentContainer,tab3).commit();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    private void registerUserFromGoogle(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient gsc = GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String id = acct.getId();
        }
    }
}