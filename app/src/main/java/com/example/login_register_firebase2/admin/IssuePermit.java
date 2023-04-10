package com.example.login_register_firebase2.admin;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
//import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.login_register_firebase2.farmer.AppliedPermitAdapter;
import com.example.login_register_firebase2.farmer.AppliedPermitModel;
import com.example.login_register_firebase2.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
//import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;


    public class IssuePermit extends AppCompatActivity {
        FirebaseFirestore mStore;
        CollectionReference appliedPermitRef;
        RecyclerView recyclerView;
        ProgressDialog progressDialog;
        private Toolbar toolbar;
        ImageView backArrow;
        ArrayList<AppliedPermitModel> appliedPermitModels;
        AppliedPermitAdapter appliedPermitAdapter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // killing both action and status bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            setContentView(R.layout.activity_issue_permit);

            // find the toolbar view and set it as action bar
            toolbar = findViewById(R.id.toolBar3);
            backArrow = findViewById(R.id.backArrow);
            setSupportActionBar(toolbar);

            mStore = FirebaseFirestore.getInstance();
            appliedPermitRef = mStore.collection("applied permit");

            recyclerView = findViewById(R.id.issuePermitRecyclerView);
            recyclerView.setHasFixedSize(true);

            // Set the layout manager
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            appliedPermitModels = new ArrayList<>();

            // Create an instance of the adapter and pass it the data from Firestore
            appliedPermitAdapter = new AppliedPermitAdapter(IssuePermit.this,appliedPermitModels);

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Fetching Data ....");
            progressDialog.show();

            EventChangeListener();

            // Set the adapter for the RecyclerView
            recyclerView.setAdapter(appliedPermitAdapter);
            backArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), HarvestingManager.class);
                    startActivity(intent);
                    finish();
                }
            });

        }

        @SuppressLint("NotifyDataSetChanged")
        private void EventChangeListener(){
            appliedPermitRef.orderBy("fullName", Query.Direction.ASCENDING)
                    .addSnapshotListener((value, error) -> {

                        if (error != null) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.e("FireStore error",error.getMessage());
                            return;
                        }

                        assert value != null;
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                appliedPermitModels.add(documentChange.getDocument().toObject(AppliedPermitModel.class));
                            }
                        }

                        appliedPermitAdapter.notifyDataSetChanged();

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    });
        }

    }


