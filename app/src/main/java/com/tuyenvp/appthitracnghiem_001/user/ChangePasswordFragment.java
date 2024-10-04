package com.tuyenvp.appthitracnghiem_001.user;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tuyenvp.appthitracnghiem_001.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePasswordFragment extends Fragment {
    EditText edtPasswordNew, edtConfirmPasswordNew;
    Button btnUpdatePassWord;
    View view;
    ProgressDialog progressDialog;


    public ChangePasswordFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_password, container, false);
        initUi();
        initListenner();
        return view;

    }

    private void initListenner() {
        btnUpdatePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordNew = edtPasswordNew.getText().toString().trim();
                String passwordConfirm = edtConfirmPasswordNew.getText().toString().trim();
                progressDialog.show();

                if (passwordNew.equals(passwordConfirm)) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updatePassword(passwordNew)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        showDialog();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), passwordConfirm + passwordNew, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void showDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_reautheticate);

        Button btnClose, btnXacThuc;
        EditText edtEmailCurrent, edtPasswordCurent;

        btnClose = dialog.findViewById(R.id.btnClose);
        btnXacThuc = dialog.findViewById(R.id.btnXacThuc);
        edtEmailCurrent = dialog.findViewById(R.id.edtEmailCurrent);
        edtPasswordCurent = dialog.findViewById(R.id.edtPasswordCurrent);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnXacThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailCurrent = edtEmailCurrent.getText().toString().trim();
                String passwordCurent = edtPasswordCurent.getText().toString().trim();
                Toast.makeText(getActivity(), emailCurrent+passwordCurent, Toast.LENGTH_SHORT);


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                AuthCredential credential = EmailAuthProvider
                        .getCredential(emailCurrent, passwordCurent);

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Xác thực thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
            }
        });
        dialog.show();
    }


    private void initUi() {
        edtPasswordNew = view.findViewById(R.id.edtNewPassWord);
        edtConfirmPasswordNew = view.findViewById(R.id.edtConfirmNewPassWord);
        btnUpdatePassWord = view.findViewById(R.id.btnUpdatePassword);
        progressDialog = new ProgressDialog(getActivity());
    }


}