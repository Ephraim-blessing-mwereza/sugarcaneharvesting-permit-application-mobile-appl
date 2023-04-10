package com.example.login_register_firebase2.intro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
import com.example.login_register_firebase2.R;
import com.example.login_register_firebase2.admin.HarvestingManager;
import com.example.login_register_firebase2.farmer.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
//import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword,editTextFullName, editTextPhone;
    Button buttonReg;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    TextView textView;
    boolean valid = true;
    CheckBox isFarmerBox, isHarvestingManagerBox;
    //TextInputLayout textInputPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // killing both action and status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextFullName = findViewById(R.id.registerName);
        editTextPhone = findViewById(R.id.registerPhoneNumber);
        buttonReg = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);



        isFarmerBox = findViewById(R.id.isFarmer);
        isHarvestingManagerBox = findViewById(R.id.isHarvestingManager);


        // check box logics

        isFarmerBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    isHarvestingManagerBox.setChecked(false);
                }
            }
        });

        isHarvestingManagerBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    isFarmerBox.setChecked(false);
                }
            }
        });



        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // checkbox validation
                if (!(isFarmerBox.isChecked()  || isHarvestingManagerBox.isChecked())){
                    // not sure to use the next 1, 3 and 4rth line of this category
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Register.this, "Select the Account Type!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Register.class));
                    finish();
                }

                progressBar.setVisibility(View.VISIBLE);
                String email, password, registerName, registerPhoneNumber;
                registerName = String.valueOf(editTextFullName.getText());
                registerPhoneNumber = String.valueOf(editTextPhone.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());



                if (TextUtils.isEmpty(registerName)){
                    Toast.makeText(Register.this, "Enter Full Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(registerPhoneNumber)){
                    Toast.makeText(Register.this, "Enter Phone Number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this, "Enter Email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this, "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (valid) {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressBar.setVisibility(View.GONE);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Register.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                    DocumentReference df = fStore.collection("Users").document(user.getUid());

                                    //storing the data
                                    Map<String, Object> userInfo = new HashMap<>();
                                    userInfo.put("FullName", registerName);
                                    userInfo.put("UserEmail", email);
                                    userInfo.put("PhoneNumber", registerPhoneNumber);


                                    // specify the access level
                                    /* userInfo.put("isUser","1");  // if I had to abstract then I would use that

                                     */
                                    if (isHarvestingManagerBox.isChecked()) {
                                        userInfo.put("isHarvestingManager", "1");
                                    }
                                    if (isFarmerBox.isChecked()) {
                                        userInfo.put("isFarmer", "1");
                                    }
                                    df.set(userInfo);

                                    // sending either the harvesting Manager or Farmer to their respective pages

                                    if (isHarvestingManagerBox.isChecked()) {
                                        startActivity(new Intent(getApplicationContext(), HarvestingManager.class));
                                        finish();
                                    }

                                    if (isFarmerBox.isChecked()) {
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Register.this, "Failed to create the account", Toast.LENGTH_SHORT).show();


                                }
                            });

                }


            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}