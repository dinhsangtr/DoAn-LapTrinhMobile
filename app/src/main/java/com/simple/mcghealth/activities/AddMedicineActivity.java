package com.simple.mcghealth.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.entities.Medicine;
import com.simple.mcghealth.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMedicineActivity extends AppCompatActivity {
    int REQUEST_CODE_CAMERA = 1;
    int REQUEST_CODE_FOLDER = 2;

    private Button btnExit, btnSave;
    private EditText edtDrugName, edtNote;
    private ImageView imageViewGallery, imageViewCamera, imgReview;
    private Toolbar toolbar;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        mappings();
        actionToolbar();

        imageViewCamera.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        });

        imageViewGallery.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_FOLDER);
        });

        btnSave.setOnClickListener(view -> {
            String drugName = edtDrugName.getText().toString().trim();
            String note = edtNote.getText().toString().trim();

            if (TextUtils.isEmpty(drugName)){
                edtDrugName.setError("Tên thuốc không để trống!");
            }else{
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgReview.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] image = byteArrayOutputStream.toByteArray();
                image = CommonUtils.imagemTratada(image);

                db = AppDatabase.getInstance(this.getApplicationContext());

                Medicine medicine = new Medicine();
                medicine.setName(drugName);
                medicine.setNote(note);
                medicine.setImage(image);

                db.medicineDao().insertMedicine(medicine);

                Toast.makeText(this, "Insert" + drugName + "-sl: "+ db.medicineDao().getAllMedicines().size(), Toast.LENGTH_SHORT).show();
                CommonUtils.hideSoftKeyBoard(this);
                CommonUtils.displayAlertDialog(this, "Thêm thành công", null);

                edtDrugName.setText("");
                edtNote.setText("");
                imgReview.setImageResource(R.drawable.null_100px);
            }

        });

        btnExit.setOnClickListener(view -> {
            finish();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgReview.setImageBitmap(bitmap);
        }

        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = (Bitmap) BitmapFactory.decodeStream(inputStream);
                imgReview.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void mappings() {
        btnExit = (Button) findViewById(R.id.btnExit);
        btnSave = (Button) findViewById(R.id.btnSave);
        edtDrugName = (EditText) findViewById(R.id.edtDrugName);
        edtNote = (EditText) findViewById(R.id.edtNote);
        imageViewGallery = (ImageView) findViewById(R.id.imageViewGallery);
        imageViewCamera = (ImageView) findViewById(R.id.imageViewCamera);
        imgReview = (ImageView) findViewById(R.id.imgReview);
        toolbar = (Toolbar) findViewById(R.id.toolbarAddDrug);
    }

    private void actionToolbar() {
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}