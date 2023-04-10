package com.example.login_register_firebase2.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.login_register_firebase2.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class IssuedPermitDocument extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private DocumentReference mDocumentRef;
    private ListenerRegistration mDocumentListener;
    private  TextView fullName, userEmail, phoneNumber, fieldNumber, subLocation, area, caneAge,cropRotation,estimatedTonnes,acreAge,
            bankName,bankAccountNumber,selectedDate, trailersIssued, allocatedHarvestingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_permit_document);


        // Get references to the TextViews in the layout
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
        trailersIssued = findViewById(R.id.trailersNumber);
        allocatedHarvestingDate = findViewById(R.id.harvestingDate);

        IssuePermitModel model = getIntent().getParcelableExtra("model");

        // Initialize Firestore
        mFirestore = FirebaseFirestore.getInstance();

        // Get the document ID from the intent extras
        String documentId = getIntent().getStringExtra("document_id");

        // Get a reference to the document
        mDocumentRef = mFirestore.collection("Issued Permits").document(documentId);

        // Register a listener to get updates for the document
        mDocumentListener = mDocumentRef.addSnapshotListener(this, new com.google.firebase.firestore.EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("IssuedPermitDocument", "Listen failed.", e);
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Document exists and has data
                    IssuePermitModel permit = documentSnapshot.toObject(IssuePermitModel.class);
                    //mTextView.setText(permit.toString());
                    // Populate the TextViews with the corresponding values from the IssuePermitModel object
                    fullName.setText(permit.getFullName());
                    userEmail.setText(permit.getUserEmail());
                    phoneNumber.setText(permit.getPhoneNumber());
                    fieldNumber.setText(permit.getFieldNumber());
                    subLocation.setText(permit.getSubLocation());
                    area.setText(permit.getArea());
                    caneAge.setText(permit.getCaneAge());
                    cropRotation.setText(permit.getCropRotation());
                    estimatedTonnes.setText(permit.getEstimatedTonnes());
                    acreAge.setText(permit.getAcreAge());
                    bankName.setText(permit.getBankName());
                    bankAccountNumber.setText(permit.getBankAccountNumber());
                    selectedDate.setText(permit.getSelectedDate());
                    trailersIssued.setText(permit.getTrucksIssued());
                    allocatedHarvestingDate.setText(permit.getAllocated_harvesting_date());
                } else {
                    // Document does not exist or has been deleted
                    Log.d("IssuedPermitDocument", "Document does not exist or has been deleted");
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Stop listening for updates when the activity is stopped
        if (mDocumentListener != null) {
            mDocumentListener.remove();
            mDocumentListener = null;
        }
    }
}
