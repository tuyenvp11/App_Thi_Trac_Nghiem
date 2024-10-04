package com.tuyenvp.appthitracnghiem_001.user;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tuyenvp.appthitracnghiem_001.R;
import com.tuyenvp.appthitracnghiem_001.congthuc.CongThucFragment;
import com.tuyenvp.appthitracnghiem_001.monhoc.TiengAnhFragment;

import com.tuyenvp.appthitracnghiem_001.question.DBHelper;
import com.tuyenvp.appthitracnghiem_001.score.ScoreFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int FRAGMENT_TA = 1;
    public static final int FRAGMENT_SCORE = 2;
    public static final int FRAGMENT_CONGTHUC = 3;
    public static final int FRAGMENT_MYPROFILE = 4;
    public static final int FRAGMENT_CHANGE_PASSWORD = 5;
    public static final int MY_REQUEST_CODE = 10;


    public int mCurrentFragment = FRAGMENT_TA;
    public NavigationView mNavigationView;

    DrawerLayout mDrawerLayout;
    private ImageView imgAvatar;
    private TextView txtName, txtEmail;
    final private MyProfileFragment myProfileFragment = new MyProfileFragment();

    final private ActivityResultLauncher<Intent> mActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent == null) {
                            return;
                        }
                        Uri uri = intent.getData();
                        myProfileFragment.setUri(uri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            myProfileFragment.setBitmapImageView(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initUi();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new TiengAnhFragment());
        mNavigationView.getMenu().findItem(R.id.nav_montienganh).setChecked(true);
        DBHelper db = new DBHelper(this);
//        db.deleteDataBase();
        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showUserInformation();

    }

    private void initUi() {
        mNavigationView = findViewById(R.id.navigation_view);
        imgAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.imvAvatar);
        txtName = mNavigationView.getHeaderView(0).findViewById(R.id.txtName);
        txtEmail = mNavigationView.getHeaderView(0).findViewById(R.id.txtEmail);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_montienganh) {
            if (mCurrentFragment != FRAGMENT_TA) {
                replaceFragment(new TiengAnhFragment());
                mCurrentFragment = FRAGMENT_TA;
                mNavigationView.getMenu().findItem(R.id.nav_montienganh).setChecked(true);
            }
        } else if (id == R.id.nav_xemdiem) {
            if (mCurrentFragment != FRAGMENT_SCORE) {
                replaceFragment(new ScoreFragment());
                mCurrentFragment = FRAGMENT_SCORE;
                mNavigationView.getMenu().findItem(R.id.nav_xemdiem).setChecked(true);
            }
        } else if (id == R.id.nav_congthuc) {
            if (mCurrentFragment != FRAGMENT_CONGTHUC) {
                replaceFragment(new CongThucFragment());
                mCurrentFragment = FRAGMENT_CONGTHUC;
                mNavigationView.getMenu().findItem(R.id.nav_congthuc).setChecked(true);
            }
        } else if (id == R.id.nav_sign_out) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_my_profile) {
            if (mCurrentFragment != FRAGMENT_MYPROFILE) {
                replaceFragment(myProfileFragment);
                mCurrentFragment = FRAGMENT_MYPROFILE;
                mNavigationView.getMenu().findItem(R.id.nav_my_profile).setChecked(true);
                mNavigationView.getMenu().findItem(R.id.nav_my_profile).setCheckable(true);
            }
        }else if (id == R.id.nav_change_password) {
            if (mCurrentFragment != FRAGMENT_CHANGE_PASSWORD) {
                replaceFragment(new ChangePasswordFragment());
                mCurrentFragment = FRAGMENT_CHANGE_PASSWORD;
                mNavigationView.getMenu().findItem(R.id.nav_change_password).setChecked(true);
                mNavigationView.getMenu().findItem(R.id.nav_change_password).setCheckable(true);

            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    public void showUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        } else {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri avatarUri = user.getPhotoUrl();
            if (name == null) {
                txtName.setVisibility(View.GONE);
            } else {
                txtName.setVisibility(View.VISIBLE);
                txtName.setText(name);

            }
            txtEmail.setText(email);
            //Glide.with(this).load(avatarUri).error(R.drawable.user_default_2).into(imgAvatar);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallary();
            }
        }
    }

    public void openGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

}