package com.simple.mcghealth.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.adapters.UserAdapter;
import com.simple.mcghealth.entities.User;
import com.simple.mcghealth.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class AddInfoMainActivity extends AppCompatActivity {

    private EditText editTextFullName;
    private EditText editTextBirthDay;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private Button btnContinnue;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_main);

        inituil();


        btnContinnue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();


            }


        });
    }

    private void inituil() {
        editTextFullName = findViewById(R.id.txtName);
        editTextBirthDay = findViewById(R.id.txtBirthday);
        editTextHeight = findViewById(R.id.txtHeight);
        editTextWeight = findViewById(R.id.txtWeight);
        btnContinnue = findViewById(R.id.btncontinue);
    }

    private void addUser() {
        String username = editTextFullName.getText().toString().trim();
        String birthday = editTextBirthDay.getText().toString().trim();
        String height = editTextHeight.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(birthday) || TextUtils.isEmpty(height) || TextUtils.isEmpty(weight)) {
            editTextFullName.setError("Không được trống");
            editTextBirthDay.setError("Không được trống");
            editTextWeight.setError("Không được trống");
            editTextHeight.setError("Không được trống");
            return;
        }
        User user = new User(username, birthday, Integer.parseInt(height), Integer.parseInt(weight));
        // try {
        appDatabase = AppDatabase.getInstance(getApplicationContext());
        List<User> i = appDatabase.userDao().getAll();
        appDatabase.userDao().insertUser(user);
        List<User> j = appDatabase.userDao().getAll();
        if (j.size() > i.size()) {
            Toast.makeText(this, "Add user succese", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddInfoMainActivity.this, MainActivity.class);
            startActivity(intent);
        }

        //  }catch (Exception x){
        //      Toast.makeText(this,"Erro "+ x.getMessage(), Toast.LENGTH_SHORT).show();
        //   }


    }
}