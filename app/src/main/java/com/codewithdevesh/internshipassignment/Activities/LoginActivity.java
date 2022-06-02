package com.codewithdevesh.internshipassignment.Activities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codewithdevesh.internshipassignment.R;
import com.codewithdevesh.internshipassignment.Utils.UserModel;
import com.codewithdevesh.internshipassignment.Utils.Utils;
import com.codewithdevesh.internshipassignment.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        Utils.setStatusBarColor(this,R.color.light_background);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            callActivity();
        }

        binding.ggLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        binding.gotoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }
        });
        binding.forgotPsswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
            }
        });
        binding.loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.pbLogin.setVisibility(View.VISIBLE);
                if(!validatePassword() | !validateEmail()){
                    binding.pbLogin.setVisibility(View.GONE);
                    return;
                }else{
                    isUser();
                }
            }
        });
    }

    private void isUser() {
        String email = binding.email.getEditText().getText().toString();
        String password = binding.password.getEditText().getText().toString();
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String uid = FirebaseAuth.getInstance().getUid();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    if(!uid.isEmpty()){
                        reference.child(uid).child("password").setValue(password);
                    }
                    binding.pbLogin.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                    finish();
                }else{
                    try{
                        throw  task.getException();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        binding.password.setError(e.getLocalizedMessage());
                        binding.pbLogin.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private boolean validateEmail() {
        String x = binding.email.getEditText().getText().toString();
        if(x.isEmpty()){
            binding.email.setError("Field cant be empty");
            return false;
        }else{
            binding.email.setError(null);
            binding.email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String x= binding.password.getEditText().getText().toString();
        if(x.isEmpty()){
            binding.password.setError("Field cant be empty");
            return false;
        }else{
            binding.password.setError(null);
            binding.password.setErrorEnabled(false);
            return true;
        }
    }

    private void signIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount>task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                task.getResult(ApiException.class);
                registerUserFromGoogle();
            }catch (ApiException e){
                e.printStackTrace();
                Snackbar.make(binding.cLayout,"Something Went Wrong",Snackbar.LENGTH_SHORT).show();
            }
        }
    }
    private void callActivity(){
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        }
    }

    @Override
    public void onBackPressed() {
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
            registerUser(personName,personEmail,id);
        }
    }
    private void registerUser(String name, String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            UserModel model = new UserModel(name,email,password);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                            if (firebaseUser != null) {
                                reference.child(firebaseUser.getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            binding.pbLogin.setVisibility(View.GONE);
                                            firebaseUser.sendEmailVerification();
                                            Snackbar.make(binding.cLayout,"Registered Successfully", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                                            finish();
                                        }else{
                                            Snackbar.make(binding.cLayout,"Failed to register user,Try again later", Toast.LENGTH_SHORT).show();
                                            binding.pbLogin.setVisibility(View.GONE);
                                        }

                                    }
                                });
                            }

                        }else{
                            try{
                                throw task.getException();
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(), e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                binding.password.setError(e.getLocalizedMessage());
                                binding.pbLogin.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }
}