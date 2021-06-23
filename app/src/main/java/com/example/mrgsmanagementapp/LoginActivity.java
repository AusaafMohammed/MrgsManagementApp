package com.example.mrgsmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout inputEmail,inputPassword;
    Button btnLogin;
    TextView forgotPassword,CreateNewAccount;
    ProgressDialog mLoadingBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.btnLogin);
        forgotPassword=findViewById(R.id.forgotPassword);
        CreateNewAccount=findViewById(R.id.CreateNewAccount);
        mLoadingBar=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        CreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtemptLogin();
            }
        });

    }

    private void AtemptLogin() {
        String email=inputEmail.getEditText().getText().toString();
        String password=inputPassword.getEditText().getText().toString();
        if (email.isEmpty() || !email.contains("@students.mrgs.school.nz"))
        {
            showError(inputEmail,"Email is not Valid!");

        }else if(password.isEmpty() || password.length()<5)
        {
            showError(inputPassword,"Password must be greater than 5 digits!");
        }
        else
        {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Please Wait, Loading soon!");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        mLoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Login is Successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        //Change mainactivity to setupactivity later
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        mLoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void showError(TextInputLayout field, String text) {
        field.setError(text);
        field.requestFocus();
    }
}