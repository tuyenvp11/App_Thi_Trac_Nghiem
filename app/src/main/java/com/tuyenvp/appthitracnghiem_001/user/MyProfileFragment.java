package com.tuyenvp.appthitracnghiem_001.user;

import static com.tuyenvp.appthitracnghiem_001.user.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tuyenvp.appthitracnghiem_001.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MyProfileFragment extends Fragment {

    private View mView;
    private ImageView imvAvatar;
    private EditText edtName, edtEmail;
    private Button btnUpdate;
    private Uri mUri;
    private MainActivity mMainActivity;
    ProgressDialog progressDialog;

    public MyProfileFragment() {

    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        initUI();
        mMainActivity =(MainActivity)  getActivity();
        setUserInfomation();
        initListener();
        return mView;
    }

    private void initListener() {
        imvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermisstion();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();// lấy user đang đăng nhập
                progressDialog.show();
                if(user==null){
                    return;
                }
                String fullName=edtName.getText().toString().trim();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(fullName)
                        .setPhotoUri(mUri)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(),"Cập nhật thành công",0).show();
                                    mMainActivity.showUserInformation();
                                }
                            }
                        });
            }
        });
    }

    private void onClickRequestPermisstion() {
        if(mMainActivity==null){
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mMainActivity.openGallary();
            return;
        }
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mMainActivity.openGallary();
        }else {
            String [] permisstion ={Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permisstion,MY_REQUEST_CODE);
        }
    }

    private void setUserInfomation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        edtName.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        //Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.user_default_2).into(imvAvatar);
    }

    private void initUI() {
        imvAvatar =  mView.findViewById(R.id.imvAvatarUpdate);
        edtName = mView.findViewById(R.id.edtFullname);
        edtEmail = mView.findViewById(R.id.edtEmail);
        btnUpdate = mView.findViewById(R.id.btnUpdate);
        progressDialog=new ProgressDialog(getActivity());
    }

    public void setBitmapImageView(Bitmap bitmapImageView){
        imvAvatar.setImageBitmap(bitmapImageView);
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }
}