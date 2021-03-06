package com.project.between.signInAndUp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.between.AnniversaryActivity;
import com.project.between.R;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    Button start_btn;
    Calendar calendar;
    TextView textViewBirthday, textViewFirstday;
    EditText name_edit;
    RadioGroup gender;
    RadioButton radiomale, radiofemale;
    int year, month, day;

    String birthday, firstday;

    String tempkey;



    FirebaseDatabase database;
    DatabaseReference userRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        getIntentFromPhoneConnectActivity();

        init();


        textViewBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        textViewBirthday.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                        birthday= textViewBirthday.getText().toString();
                    }
                }, year, month, day).show();
            }
        });

        textViewFirstday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        textViewFirstday.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                        firstday = textViewFirstday.getText().toString();
                    }
                }, year, month, day).show();
            }
        });

    }

    public void movetohome(View view) {
        int selectedId = gender.getCheckedRadioButtonId();
        String gender = selectedId == R.id.radiomale ? "male" : "female";
        String name = name_edit.getText().toString();
        userRef.child(tempkey).child("name").setValue(name);
        userRef.child(tempkey).child("birthday").setValue(birthday);
        userRef.child(tempkey).child("firstday").setValue(firstday);
        userRef.child(tempkey).child("gender").setValue(gender);
        Intent intent = new Intent(ProfileActivity.this, AnniversaryActivity.class);
        startActivity(intent);

    }


    public void getIntentFromPhoneConnectActivity() {
        Intent intent = getIntent();
        tempkey = intent.getExtras().getString("tempkey2");
    }

    public void init() {
        textViewBirthday = findViewById(R.id.textViewBirthday);
        textViewFirstday = findViewById(R.id.textViewFirstday);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        name_edit = findViewById(R.id.name_edit);
        start_btn = findViewById(R.id.start_btn);
        gender = findViewById(R.id.gender);
    }
}
