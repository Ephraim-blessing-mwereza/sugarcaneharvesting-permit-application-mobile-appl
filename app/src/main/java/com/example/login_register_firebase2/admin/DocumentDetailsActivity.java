package com.example.login_register_firebase2.admin;

import static android.content.ContentValues.TAG;
import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login_register_firebase2.farmer.AppliedPermitModel;
import com.example.login_register_firebase2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DocumentDetailsActivity extends AppCompatActivity {

    CollectionReference issuedPermitRef;
    DocumentReference docRef;
    FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    TextView fullName, userEmail, phoneNumber, fieldNumber, subLocation, area, caneAge,cropRotation,estimatedTonnes,acreAge,
    bankName,bankAccountNumber,selectedDate;
    String response, story1, story2, story3,story4, fieldNumber2;
    TextInputEditText trailerNumbers;
    DatePicker datePicker;
    Button btnIssuePermit;
    private Toolbar toolbar;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // killing both action and status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_document_details);
        backArrow = findViewById(R.id.backArrow);
        // find the toolbar view and set it as action bar
        toolbar = findViewById(R.id.toolBar3);
        setSupportActionBar(toolbar);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IssuePermit.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        AppliedPermitModel selectedAppliedPermitModel = intent.getParcelableExtra("selectedAppliedPermitModel");

        fullName = findViewById(R.id.fullName);
        userEmail = findViewById(R.id.userEmail);
        phoneNumber = findViewById(R.id.phoneNumber);
        fieldNumber = findViewById(R.id.fieldNumber);
        subLocation = findViewById(R.id.subLocation);
        area =findViewById(R.id.area);
        caneAge = findViewById(R.id.caneAge);
        cropRotation = findViewById(R.id.cropRotation);
        estimatedTonnes = findViewById(R.id.estimatedTonnes);
        acreAge = findViewById(R.id.acreAge);
        bankName = findViewById(R.id.bankName);
        bankAccountNumber = findViewById(R.id.bankAccountNumber);
        selectedDate = findViewById(R.id.selectedDate);
        trailerNumbers = findViewById(R.id.editTextTrailers);

        btnIssuePermit = findViewById(R.id.btn_issue_permit);





        //AppliedPermitModel appliedPermitModel = getIntent().getParcelableExtra("appliedPermitModel");
        if (selectedAppliedPermitModel != null) {
            fullName.setText(selectedAppliedPermitModel.getFullName());
            userEmail.setText(selectedAppliedPermitModel.getUserEmail());
            phoneNumber.setText(String.valueOf(selectedAppliedPermitModel.getPhoneNumber()));
            fieldNumber.setText(String.valueOf(selectedAppliedPermitModel.getFieldNumber()));
            subLocation.setText(selectedAppliedPermitModel.getSubLocation());
            area.setText(selectedAppliedPermitModel.getArea());
            cropRotation.setText(selectedAppliedPermitModel.getCropRotation());
            caneAge.setText(String.valueOf(selectedAppliedPermitModel.getCaneAge()));
            estimatedTonnes.setText(String.valueOf(selectedAppliedPermitModel.getEstimatedTonnes()));
            acreAge.setText(String.valueOf(selectedAppliedPermitModel.getAcreAge()));
            bankName.setText(selectedAppliedPermitModel.getBankName());
            bankAccountNumber.setText(String.valueOf(selectedAppliedPermitModel.getBankAccountNumber()));
            selectedDate.setText(String.valueOf(selectedAppliedPermitModel.getSelectedDate()));
        } else {
            Log.e("DocumentDetailsActivity", "selectedAppliedPermitModel is null");
        }

        btnIssuePermit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // response from the admin
                response = ((TextView) findViewById(R.id.tvResponse)).getText().toString();
                story1 = ((TextView) findViewById(R.id.tvStory1)).getText().toString();
                fieldNumber2 = ((TextView) findViewById(R.id.fieldNumber2)).getText().toString();
                story2 = ((TextView) findViewById(R.id.textViewStory2)).getText().toString();
                story3 = ((TextView) findViewById(R.id.tvStory3)).getText().toString();
                String editTextTrailers = String.valueOf(trailerNumbers.getText());
                story4 = ((TextView) findViewById(R.id.textViewStory4)).getText().toString();
                btnIssuePermit = findViewById(R.id.btn_issue_permit);



                //Get selected date from the date picker
                datePicker = findViewById(R.id.datePicker1);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1; // Months start from 0
                int year = datePicker.getYear();
                String approvedDate = day + "/" + month + "/" + year;

                // Validate input fields
                if (isEmpty(editTextTrailers) ) {
                    // showToast("Please fill in all required fields.");
                    Toast.makeText(DocumentDetailsActivity.this, "Please fill in all required fields", Toast.LENGTH_LONG).show();
                    return;
                }


                // create  new firestore doc
                issuedPermitRef = mStore.collection("Issued Permits");
                docRef = issuedPermitRef.document();

                //create a map of the data to be added to the new document
                Map<String, Object> issuedPermitInfo = new HashMap<>();
                issuedPermitInfo.put("fullName", selectedAppliedPermitModel.getFullName());
                issuedPermitInfo.put("userEmail", selectedAppliedPermitModel.getUserEmail());
                issuedPermitInfo.put("phoneNumber", selectedAppliedPermitModel.getPhoneNumber());
                issuedPermitInfo.put("fieldNumber", selectedAppliedPermitModel.getFieldNumber());
                issuedPermitInfo.put("subLocation", selectedAppliedPermitModel.getSubLocation());
                issuedPermitInfo.put("area", selectedAppliedPermitModel.getArea());
                issuedPermitInfo.put("cropRotation", selectedAppliedPermitModel.getCropRotation());
                issuedPermitInfo.put("caneAge", selectedAppliedPermitModel.getCaneAge());
                issuedPermitInfo.put("estimatedTonnes", selectedAppliedPermitModel.getEstimatedTonnes());
                issuedPermitInfo.put("acreAge", selectedAppliedPermitModel.getAcreAge());
                issuedPermitInfo.put("bankName", selectedAppliedPermitModel.getBankName());
                issuedPermitInfo.put("bankAccountNumber", selectedAppliedPermitModel.getBankAccountNumber());
                issuedPermitInfo.put("selectedDate", selectedAppliedPermitModel.getSelectedDate());
                issuedPermitInfo.put("trucksIssued ", editTextTrailers);
                issuedPermitInfo.put("allocated_harvesting_date", approvedDate);

                //save the map to a new document
                docRef.set(issuedPermitInfo).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "Permit issued successfully");
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Error issuing permit to user", e);
                            }
                        });
            }
        });


    }
}