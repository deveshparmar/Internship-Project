package com.codewithdevesh.internshipassignment.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.codewithdevesh.internshipassignment.R;
import com.codewithdevesh.internshipassignment.Utils.UserModel;
import com.codewithdevesh.internshipassignment.Utils.Utils;
import com.codewithdevesh.internshipassignment.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityRegisterBinding binding;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        Utils.setStatusBarColor(this,R.color.light_background);
        binding.gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
                finish();
            }
        });
        binding.signupBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.pbSignup.setVisibility(View.VISIBLE);
                String email = binding.emailSignup.getEditText().getText().toString();
                String password = binding.passwordSignup.getEditText().getText().toString();
                String name = binding.nameSignup.getEditText().getText().toString();
                if(!validateEmail() | !validatePassword()|!validateName()){
                    binding.pbSignup.setVisibility(View.GONE);
                    return;
                }
                registerUser(name,email,password);
            }
        });
    }

    private void registerUser(String name, String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            UserModel model = new UserModel(name,email,password);
                            reference = FirebaseDatabase.getInstance().getReference("Users");
                            if (firebaseUser != null) {
                                reference.child(firebaseUser.getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            binding.pbSignup.setVisibility(View.GONE);
                                            firebaseUser.sendEmailVerification();
                                            Snackbar.make(binding.clLayout,"Registered Successfully", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                                            finish();
                                        }else{
                                            Snackbar.make(binding.clLayout,"Failed to register user,Try again later", Toast.LENGTH_SHORT).show();
                                            binding.pbSignup.setVisibility(View.GONE);
                                        }

                                    }
                                });
                            }

                        }else{
                            try{
                                throw task.getException();
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(), e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                binding.passwordSignup.setError(e.getLocalizedMessage());
                                binding.pbSignup.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    private boolean validateName() {
        String x = binding.nameSignup.getEditText().getText().toString();
        if(x.isEmpty()){
            binding.nameSignup.setError("Field cant be empty");
            return false;
        }else{
            binding.nameSignup.setError(null);
            binding.nameSignup.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String x = binding.emailSignup.getEditText().getText().toString();
        if(x.isEmpty()){
            binding.emailSignup.setError("Field cant be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(x).matches()){
            binding.emailSignup.setError("Invalid Email");
            return false;
        }
        else{
            binding.emailSignup.setError(null);
            binding.emailSignup.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String x= binding.passwordSignup.getEditText().getText().toString();
        if(x.isEmpty()){
            binding.passwordSignup.setError("Field cant be empty");
            return false;
        }else if(x.length()<6){
            binding.passwordSignup.setError("Password too short");
            return false;
        }
        else{
            binding.passwordSignup.setError(null);
            binding.passwordSignup.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}