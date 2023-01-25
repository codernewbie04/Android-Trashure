package com.akmalmf.trashure.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akmalmf.trashure.R;
import com.akmalmf.trashure.constants.Constants;
import com.akmalmf.trashure.json.ResponseAuth;
import com.akmalmf.trashure.utils.PrefManager;
import com.akmalmf.trashure.utils.Validate;
import com.akmalmf.trashure.utils.api.ServiceGenerator;
import com.akmalmf.trashure.utils.api.request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.akmalmf.trashure.constants.Constants.AUTH_PASS;
import static com.akmalmf.trashure.constants.Constants.AUTH_USER;

public class DaftarAct extends AppCompatActivity implements View.OnClickListener{
    EditText et_nama, et_nohp, et_email, et_password;
    Button btn_daftar, btn_google, btn_facebook;
    ImageView foto, gantifoto;
    Toolbar mActionBarToolbar;
    RelativeLayout rlprogress;
    ImageButton show_password;
    Boolean is_show = false;
    byte[] imageByteArray;
    Context mContext;
    Bitmap decoded;
    TextView masuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        startInit();
    }

    private void startInit() {
        initUI();
        initValue();
        initEvent();
    }

    private void initValue() {
    }

    private void initUI() {
        mContext = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.white));
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        btn_daftar      = findViewById(R.id.btn_daftar);
        btn_google      = findViewById(R.id.btn_google);
        btn_facebook    = findViewById(R.id.btn_facebook);

        et_password = findViewById(R.id.et_password);
        et_email    = findViewById(R.id.et_email);
        et_nama     = findViewById(R.id.et_nama);
        et_nohp     = findViewById(R.id.et_nohp);

        foto        = findViewById(R.id.foto);
        gantifoto   = findViewById(R.id.editfoto);

        mActionBarToolbar = findViewById(R.id.toolbar);

        masuk = findViewById(R.id.masuk);

        show_password = findViewById(R.id.show_password);

        rlprogress = findViewById(R.id.rlprogress);
    }

    private void initEvent() {
        btn_daftar.setOnClickListener(this);
        masuk.setOnClickListener(this);
        show_password.setOnClickListener(this);
        gantifoto.setOnClickListener(this);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.show_password:
                if(!is_show){
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    is_show = true;
                    show_password.setColorFilter(ContextCompat.getColor(mContext, R.color.greenPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                } else {
                    show_password.setColorFilter(ContextCompat.getColor(mContext, R.color.gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    is_show = false;
                }
                break;
            case R.id.editfoto:
                selectImage();
                break;
            case R.id.masuk:
                finish();
                break;
            case R.id.btn_daftar:
                if(validate_daftar())
                    daftar();
                break;
        }

    }

    private void selectImage() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }
    }

    private boolean check_ReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            Constants.permission_Read_data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

    public String getPath(Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 2) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                foto.setImageBitmap(rotatedBitmap);
                imageByteArray = baos.toByteArray();
                decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }

        }

    }

    private boolean validate_daftar() {
        return (!Validate.cekImage(imageByteArray, mContext)&&!Validate.cek(et_nama)&&!Validate.cek(et_email)&&!Validate.cekEmail(et_email)&&!Validate.cek(et_nohp)&&!Validate.cekNohp(et_nohp)&&!Validate.cek(et_password));
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        imageByteArray = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
    }

    private void daftar() {
        rlprogress.setVisibility(View.VISIBLE);
        request service = ServiceGenerator.createService(request.class, AUTH_USER, AUTH_PASS, null);
        service.register(et_nama.getText().toString(), et_email.getText().toString(), et_nohp.getText().toString(), et_password.getText().toString(), getStringImage(decoded)).enqueue(new Callback<ResponseAuth>() {
            @Override
            public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                rlprogress.setVisibility(View.GONE);
                if (response.code() == 200) {
                    new PrefManager(mContext).saveUserData(response.body().getData());
                    Intent goto_dashboard = new Intent(mContext, MainAct.class);
                    startActivity(goto_dashboard);
                    finishAffinity();
                } else {
                    Gson gson = new GsonBuilder().create();
                    ResponseAuth mError = new ResponseAuth();
                    try {
                        mError= gson.fromJson(response.errorBody().string(), ResponseAuth.class);
                        FancyToast.makeText(mContext,mError.getMsg(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    } catch (IOException e) {
                        Toast.makeText(mContext, "Invalid JSON", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAuth> call, Throwable t) {
                rlprogress.setVisibility(View.GONE);
                FancyToast.makeText(mContext, "No Connection!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
            }
        });
    }
}