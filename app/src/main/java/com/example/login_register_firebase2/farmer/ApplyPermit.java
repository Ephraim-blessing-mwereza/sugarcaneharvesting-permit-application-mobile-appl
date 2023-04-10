package com.example.login_register_firebase2.farmer;


import static android.content.ContentValues.TAG;
import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.login_register_firebase2.R;
import com.example.login_register_firebase2.admin.HarvestingManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class ApplyPermit extends AppCompatActivity  {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    String fullName,userEmail, phoneNumber, userID;

    CollectionReference appliedPermitRef = mStore.collection("applied permit");
    CollectionReference usersRef = fStore.collection("Users");
    Button applyPermitButton;
    private ImageView backArrow;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_permit);
        //getSupportFragmentManager().beginTransaction().replace(R.id., new RegisterFragment()).commit();

        // killing both action and status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Find views
        backArrow = findViewById(R.id.backArrow);
        toolbar = findViewById(R.id.toolBar3);
        setSupportActionBar(toolbar);

        DatePicker datePicker = findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 10);
        datePicker.setMinDate(calendar.getTimeInMillis());
        applyPermitButton = findViewById(R.id.btn_apply_permit);

        applyPermitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code to execute when button is clicked
                applyPermit();
            }
        });

        // Set the click listener for the back arrow button
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private void applyPermit(){


        // Get the currently logged-in user
        if (user != null) {
            userID = user.getUid();
            //fullName = user.getDisplayName();
            userEmail = user.getEmail();
            //phoneNumber = user.getPhoneNumber();


            usersRef.document(userID).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                             fullName = documentSnapshot.getString("FullName");
                             phoneNumber =documentSnapshot.getString("PhoneNumber");


            // Get the field values from the form
            String fieldNumber = ((TextInputEditText) findViewById(R.id.fieldNumber)).getText().toString();
            String subLocation = ((TextInputEditText) findViewById(R.id.subLocation)).getText().toString();
            String area = ((TextInputEditText) findViewById(R.id.area)).getText().toString();
            String caneAge = ((TextInputEditText) findViewById(R.id.caneAge)).getText().toString();
            String cropRotation = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
            String estimatedTonnes = ((TextInputEditText) findViewById(R.id.estimatedTonnes)).getText().toString();
            String acreAge = ((TextInputEditText) findViewById(R.id.acreAge)).getText().toString();
            String bankName = ((Spinner) findViewById(R.id.spinnerBankName)).getSelectedItem().toString();
            String bankAccountNumber = ((TextInputEditText) findViewById(R.id.bankAccountNumber)).getText().toString();

            // Validate input fields
            if (isEmpty(fieldNumber) || isEmpty(subLocation) || isEmpty(area) || isEmpty(caneAge) || isEmpty(cropRotation) || isEmpty(estimatedTonnes) || isEmpty(acreAge) || isEmpty(bankName) || isEmpty(bankAccountNumber)) {
               // showToast("Please fill in all required fields.");
                Toast.makeText(ApplyPermit.this, "Please fill in all required fields", Toast.LENGTH_LONG).show();
                return;
            }


            // Get the selected date from the DatePicker
            DatePicker datePicker = findViewById(R.id.datePicker);
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1; // Months start from 0
            int year = datePicker.getYear();
            String selectedDate = day + "/" + month + "/" + year;

            // Populate the crop rotation spinner
            Spinner spinner = findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ApplyPermit.this, R.array.crop_rotation_types, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            // Populate the bank name spinner
            Spinner spinnerBankName = findViewById(R.id.spinnerBankName);
            ArrayAdapter<CharSequence> adapterBankName = ArrayAdapter.createFromResource(ApplyPermit.this, R.array.bank_name, android.R.layout.simple_spinner_item);
            adapterBankName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerBankName.setAdapter(adapterBankName);


            PermitApplication permitApplication = new PermitApplication(userID,fullName,userEmail,phoneNumber,fieldNumber,subLocation,area,caneAge,cropRotation,estimatedTonnes,acreAge,
                    bankName,bankAccountNumber,selectedDate);

// Add the new object to the "applied permit" collection
            appliedPermitRef.add(permitApplication)
                    .addOnSuccessListener(documentReference -> {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        // Show a success message to the user
                        Toast.makeText(ApplyPermit.this, "Permit application submitted successfully", Toast.LENGTH_SHORT).show();

                        // Reset the input fields

                        ((TextInputEditText) findViewById(R.id.fieldNumber)).setText("");
                        ((TextInputEditText) findViewById(R.id.subLocation)).setText("");
                        ((TextInputEditText) findViewById(R.id.area)).setText("");
                        ((TextInputEditText) findViewById(R.id.caneAge)).setText("");
                        ((Spinner) findViewById(R.id.spinner)).setSelection(0); // Set to default selection
                        ((TextInputEditText) findViewById(R.id.estimatedTonnes)).setText("");
                        ((TextInputEditText) findViewById(R.id.acreAge)).setText("");
                        ((Spinner) findViewById(R.id.spinnerBankName)).setSelection(0); // Set to default selection
                        ((TextInputEditText) findViewById(R.id.bankAccountNumber)).setText("");
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Error adding document", e);
                        // Show an error message to the user
                        Toast.makeText(ApplyPermit.this, "Error submitting permit application", Toast.LENGTH_SHORT).show();
                    });
               }
            });
        }
    }
}