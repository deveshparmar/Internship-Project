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
import com.codewithdevesh.internshipassignment.Utils.Utils;
import com.codewithdevesh.internshipassignment.databinding.ActivityResetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ActivityResetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        Utils.setStatusBarColor(this,R.color.light_background);
        auth = FirebaseAuth.getInstance();

        binding.resetBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = binding.emailSignup.getEditText().getText().toString();
                if(mail.isEmpty()){
                    binding.emailSignup.setError("Cant be empty");
                    binding.emailSignup.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    binding.emailSignup.setError("Provide valid mail");
                    binding.emailSignup.requestFocus();
                    return;
                }
                binding.pbReset.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ResetPasswordActivity.this, "Check your Email reset the password", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                            finish();
                        }else{
                            Toast.makeText(ResetPasswordActivity.this, "Some error occurred try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}