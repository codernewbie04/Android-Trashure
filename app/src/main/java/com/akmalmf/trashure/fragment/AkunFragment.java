package com.akmalmf.trashure.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.akmalmf.trashure.R;
import com.akmalmf.trashure.activity.LoginAct;
import com.akmalmf.trashure.activity.MainAct;
import com.akmalmf.trashure.constants.Constants;
import com.akmalmf.trashure.utils.PicassoTrustAll;
import com.akmalmf.trashure.utils.PrefManager;
import com.akmalmf.trashure.utils.Utility;

public class AkunFragment extends Fragment implements View.OnClickListener{
    TextView txt_berat, txt_saldo, txt_level, txt_nama, txt_nohp, txt_email;
    Button btn_keluar;
    PrefManager userData;
    ImageView foto;
    Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_akun, container, false);
        initStart(root);
        return root;
    }

    private void initStart(View root) {
        initUI(root);
        initValue();
        initEvent();
    }

    private void initUI(View v) {
        mContext = v.getContext();
        userData = new PrefManager(mContext);
        foto = v.findViewById(R.id.foto);
        btn_keluar = v.findViewById(R.id.btn_keluar);
        txt_nama  = v.findViewById(R.id.txt_nama);
        txt_berat = v.findViewById(R.id.txt_berat);
        txt_saldo = v.findViewById(R.id.txt_saldo);
        txt_level = v.findViewById(R.id.txt_level);
        txt_nohp  = v.findViewById(R.id.txt_nohp);
        txt_email = v.findViewById(R.id.txt_email);
    }

    private void initValue() {
        PicassoTrustAll.getInstance(mContext)
                .load(Constants.IMAGESUSER + userData.getFoto())
                .placeholder(R.drawable.image_placeholder)
                .resize(250, 250)
                .into(foto);
        txt_level.setText(userData.getLevel());
        txt_nama.setText(userData.getNama());
        txt_nohp.setText(userData.getNohp());
        txt_email.setText(userData.getEmail());
        Utility.currencyTXT(txt_saldo, userData.getSaldo(), mContext);
    }

    private void initEvent() {
        btn_keluar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_keluar:
                new AlertDialog.Builder(mContext, R.style.DialogStyle)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle(getString(R.string.app_name))
                        .setMessage("Kamu yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear().apply();
                                Intent goto_login = new Intent(mContext, LoginAct.class);
                                startActivity(goto_login);
                                getActivity().finishAffinity();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }
}
