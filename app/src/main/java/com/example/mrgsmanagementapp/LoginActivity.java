package com.example.mrgsmanagementapp;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
//import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout inputEmail,inputPassword;
    Button btnLogin;
    TextView forgotPassword,CreateNewAccount;
    ProgressDialog mLoadingBar;
    FirebaseAuth mAuth;
    CheckBox remember_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.btnLogin);
        forgotPassword=findViewById(R.id.forgotPassword);
        CreateNewAccount=findViewById(R.id.CreateNewAccount);
        remember_me=findViewById(R.id.remember_me);
        mLoadingBar=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

//      This part of the code is for checkbox
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember_me","");
        if(checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this, LogoutActivity.class);
            startActivity(intent);
//        }else if(checkbox.equals("false")) {
//            Toast.makeText(this, "Please Sign In!", Toast.LENGTH_SHORT).show();
        }

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, LogoutActivity.class);
            startActivity(intent);
        });

        remember_me.setOnCheckedChangeListener((compoundButton, isChecked) -> {

            if(compoundButton.isChecked()) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember_me","true");
                editor.apply();
//                Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();
            }else if (!compoundButton.isChecked()) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember_me","false");
                editor.apply();
//                Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
            }
        });

//      Checkbox Part Ends

//      Login Part starts
        CreateNewAccount.setOnClickListener(v -> {
            Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(v -> AtemptLogin());

    }

    private void AtemptLogin() {
        String email= Objects.requireNonNull(inputEmail.getEditText()).getText().toString();
        String password= Objects.requireNonNull(inputPassword.getEditText()).getText().toString();
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
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    mLoadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Login is Successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,LogoutActivity.class);
                    //Change mainactivity to setupactivity later
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    mLoadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void showError(TextInputLayout field, String text) {
        field.setError(text);
        field.requestFocus();
    }
//  Login Part Ends
}