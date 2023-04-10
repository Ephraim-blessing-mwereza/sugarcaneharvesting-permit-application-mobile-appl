package com.example.login_register_firebase2.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register_firebase2.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class IssuedPermits extends AppCompatActivity {
    private IssuePermitAdapter adapter;
    private FirebaseFirestore mFirestore;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CollectionReference issuedPermitRef;
    private ArrayList<IssuePermitModel> permitsList;
    private ProgressDialog progressDialog;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide both the navigation bar and the status bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_issued_permits);

        // Find views
        backArrow = findViewById(R.id.backArrow);
        toolbar = findViewById(R.id.toolBar3);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.myRecyclerView);

        // Set the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the permits list
        permitsList = new ArrayList<>();

        // Create an instance of the adapter and pass it the data from Firestore
        adapter = new IssuePermitAdapter(this, permitsList);

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
                Intent intent = new Intent(getApplicationContext(), HarvestingManager.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void EventChangeListener() {
        issuedPermitRef
                .orderBy("allocated_harvesting_date", Query.Direction.DESCENDING)
                .limit(50)
                .addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.e("IssuedPermits", "Error getting issued permits: " + error.getMessage());
                            return;
                        }

                        // Process the document changes and add new permits to the list
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                permitsList.add(dc.getDocument().toObject(IssuePermitModel.class));
                            }
                        }

                        // Notify the adapter of the data changes
                        adapter.notifyDataSetChanged();

                        // Dismiss the progress dialog
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}
