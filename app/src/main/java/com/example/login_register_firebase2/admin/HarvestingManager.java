package com.example.login_register_firebase2.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.login_register_firebase2.intro.Login;
import com.example.login_register_firebase2.R;
import com.example.login_register_firebase2.farmer.IssuedPermit;
import com.example.login_register_firebase2.intro.SplashScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class HarvestingManager extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar toolbar;
    private FirebaseAuth auth;
    private TextView userEmail,slogan;
    private FirebaseUser user;
    private FirebaseFirestore hStore;
    private static int SPLASH_SCREEN = 5000;
    Animation  bottomAnim;

    private View headerView, profileView;
    private NavigationView navigationView;
    private String userId;
    private ImageButton editImageButton;

    private DrawerLayout drawerLayout;
    FirebaseStorage storage;
    StorageReference storageRef;
    private static final int REQUEST_IMAGE_PICKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvesting_manager);

        // killing both action and status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = FirebaseAuth.getInstance();
        //logout = findViewById(R.id.ivLogOut);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        // find the toolbar view and set it as action bar
        toolbar = findViewById(R.id.main_toolbar2);
        //setSupportActionBar(toolbar);

        //Animations
        bottomAnim = AnimationUtils.loadAnimation(HarvestingManager.this, R.anim.bottom_animation);

        //Hooks
        slogan = findViewById(R.id.tvSlogan);

        //set animations
        slogan.setAnimation(bottomAnim);


        //setting up the navigation drawer
        navigationView = findViewById(R.id.navigationView2);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_layout_admin);


        headerView = navigationView.getHeaderView(0);
        userEmail = headerView.findViewById(R.id.textViewEmail);
        user = auth.getCurrentUser();
        hStore = FirebaseFirestore.getInstance();
        userId = user.getUid();
        editImageButton = headerView.findViewById(R.id.nav_header_edit_image_button);

        // Add a click listener to the navigation drawer icon
        ImageView navDrawerIcon = findViewById(R.id.toolbar_logo2);
        navDrawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }  else {
            userEmail.setText(user.getEmail());
        }

        if (editImageButton != null){
            editImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open an Image Picker activity
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_IMAGE_PICKER);
                }
            });
        }


        if (user != null) {
            userEmail.setText(user.getEmail());
            hStore.collection("Users").document(userId).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String userName = documentSnapshot.getString("FullName");

                            //set the user's name to navigation header
                            TextView navHeaderName = headerView.findViewById(R.id.textViewName);
                            navHeaderName.setText(userName);

                            // set the user's profile picture
                            String profilePictureUrl = documentSnapshot.getString("profile_picture_url");
                            if (profilePictureUrl != null) {
                                ImageView navHeaderImage = headerView.findViewById(R.id.nav_header_image);
                                ImageView profileHeaderImage = findViewById(R.id.nav_header_image2);
                                Glide.with(HarvestingManager.this).load(profilePictureUrl).into(navHeaderImage);
                                Glide.with(HarvestingManager.this).load(profilePictureUrl).into(profileHeaderImage);
                            }
                        }
                    });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                // Do something with the bitmap
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Update the profile picture ImageView with the selected image
            ImageView navHeaderImage = headerView.findViewById(R.id.nav_header_image);
            navHeaderImage.setImageBitmap(bitmap);

            // Update the profile picture ImageView with the selected image
            ImageView profileHeaderImage = findViewById(R.id.nav_header_image2);
            profileHeaderImage.setImageBitmap(bitmap);

            // Upload the selected image to Firebase Storage and update the user's profile picture URL in Firebase Firestore
            uploadImage(imageUri);
        }
    }

    private void uploadImage(Uri imageUri) {
        // Create a reference to the user's profile picture in Firebase Storage
        String userId = auth.getCurrentUser().getUid();
        StorageReference imageRef = storageRef.child("profile_pictures/" + userId + ".jpeg");

        // Upload the image to Firebase Storage
        UploadTask uploadTask = imageRef.putFile(imageUri);
        uploadTask.addOnCompleteListener(this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    // Get the download URL of the uploaded image
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Update the user's profile picture URL in Firebase Firestore
                            String imageUrl = uri.toString();
                            hStore.collection("Users").document(userId)
                                    .update("profile_picture_url", imageUrl)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(HarvestingManager.this, "Profile picture updated", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                } else {
                    Toast.makeText(HarvestingManager.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut(); // Logout user
            Intent intent = new Intent(HarvestingManager.this, Login.class);
            startActivity(intent); // Go back to login screen
            finish(); // Close MainActivity
        }

        if (id == R.id.nav_issue_permit) {
            Intent intent = new Intent(HarvestingManager.this, IssuePermit.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.nav_issued_permits){
            Intent intent = new Intent(HarvestingManager.this, IssuedPermits.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.nav_about_us){
            Intent intent = new Intent(HarvestingManager.this,HarvestingManager.class);
            startActivity(intent);
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout_admin);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_drawer_menu2, menu);
        return true;
    }
}