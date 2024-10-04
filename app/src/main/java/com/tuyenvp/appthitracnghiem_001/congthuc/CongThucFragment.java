package com.tuyenvp.appthitracnghiem_001.congthuc;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.tuyenvp.appthitracnghiem_001.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class CongThucFragment extends Fragment {

    ListView lvCongThuc;
    CongThucController congThucController;
    CongThucAdapter congThucAdapter;
    EditText edtSeach;
    ImageButton imgChude;
    String chude="";

    public CongThucFragment() {
        // Required empty public constructor
    }

    public void linkView() {
        lvCongThuc = (ListView) getActivity().findViewById(R.id.lvCongthuc);
        edtSeach = (EditText) getActivity().findViewById(R.id.edtSearch);
        imgChude = (ImageButton) getActivity().findViewById(R.id.imgChude);
        congThucController = new CongThucController(getActivity());
        listCursor(congThucController.getCongThuc(chude,edtSeach.getText().toString()));
    }

    public void listCursor(Cursor cursor) {
        congThucAdapter = new CongThucAdapter(getActivity(), cursor, true);
        lvCongThuc.setAdapter(congThucAdapter);
        congThucAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cong_thuc, container, false);
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        linkView();
        edtSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listCursor(congThucController.getCongThuc(chude,edtSeach.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imgChude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

//        lvCongThuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent= new Intent(getActivity(),ListViewItem.class);
//                intent.putExtra("congthuc",congThucController.get1CongThucTheoViTri(position));
//            }
//        });
    }
    public void showMenu(View v){
        PopupMenu popupMenu= new PopupMenu(getActivity(),v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ite_all:
                        chude="";
                        listCursor(congThucController.getCongThucTheoChuDe(chude));
                        break;
                    case R.id.ite_chude1:
                        chude="tienganh";
                        listCursor(congThucController.getCongThucTheoChuDe(chude));
                        break;
                    case R.id.ite_chude6:
                        chude="python";
                        listCursor(congThucController.getCongThucTheoChuDe(chude));
                        break;
                    case R.id.ite_chude2:
                        chude="java";
                        listCursor(congThucController.getCongThucTheoChuDe(chude));
                        break;


//                    case R.id.ite_all:
//                        break;
//                    case R.id.ite_all:
//                        break;
                }
                return false;
            }
        });
        popupMenu.inflate(R.menu.menu_congthuc);
        setForceShowIcon(popupMenu);
        popupMenu.show();
    }

    //Hiện thị icon trên popupMenu Field
    //import java.lang.reflect.Field;
    //import java.lang.reflect.Method;
    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}