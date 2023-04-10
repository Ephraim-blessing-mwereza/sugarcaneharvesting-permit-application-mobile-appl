package com.example.login_register_firebase2.farmer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.login_register_firebase2.R;
import com.example.login_register_firebase2.admin.IssuePermitAdapter;
import com.example.login_register_firebase2.admin.IssuePermitModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class IssuedPermit extends AppCompatActivity {
    private ImageView backArrow;
    private Toolbar toolbar;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private IssuedPermitAdapter adapter;
    private FirebaseFirestore mFirestore;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private CollectionReference issuedPermitRef;
    private ArrayList<IssuedPermitModel> permitsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_permit);
        // killing both action and status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // Find views
        backArrow = findViewById(R.id.backArrow);
        toolbar = findViewById(R.id.toolBar3);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.issuedPermitRecyclerView);

        // Set the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the permits list
        permitsList = new ArrayList<>();

        // Create an instance of the adapter and pass it the data from Firestore
        adapter = new IssuedPermitAdapter(this, permitsList);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(adapter);

        // Initialize Firestore
        mFirestore = FirebaseFirestore.getInstance();
        issuedPermitRef = mFirestore.collection("Issued Permits");

        // Show progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data ....");
        progressDialog.show();

        // Start listening to Firestore for changes
        EventChangeListener();


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
    private void EventChangeListener() {
        // Assuming "loginEmail" is the email address used for login
        String loginEmail = user.getEmail();

        // Query to retrieve the document for the logged in user
        Query query = issuedPermitRef.whereEqualTo("userEmail", loginEmail);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    progressDialog.dismiss();
                    Log.e("Firestore Error", error.getMessage());
                    return;
                }

                permitsList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    // Get the data from Firestore document
                    String fullName = doc.getString("fullName");
                    String userEmail = doc.getString("userEmail");
                    String phoneNumber = doc.getString("phoneNumber");
                    String fieldNumber = doc.getString("fieldNumber");
                    String subLocation = doc.getString("subLocation");
                    String area = doc.getString("area");
                    String caneAge = doc.getString("caneAge");
                    String cropRotation = doc.getString("cropRotation");
                    String estimatedTonnes = doc.getString("estimatedTonnes");
                    String acreAge = doc.getString("acreAge");
                    String bankName = doc.getString("bankName");
                    String bankAccountNumber = doc.getString("bankAccountNumber");
                    String selectedDate = doc.getString("selectedDate");
                    String issuedDate = doc.getString("allocated_harvesting_date");
                    String trucksIssued = doc.getString("trucksIssued");

                    // Add the retrieved data to the permits list
                    permitsList.add(new IssuedPermitModel(fullName,userEmail,phoneNumber,fieldNumber,subLocation,area,caneAge,cropRotation,estimatedTonnes,acreAge,
                            bankName,bankAccountNumber,selectedDate,issuedDate,trucksIssued));
                }

                // Notify the adapter of the data set changed
                adapter.notifyDataSetChanged();

                // Hide progress dialog
                progressDialog.dismiss();
            }
        });
    }

}