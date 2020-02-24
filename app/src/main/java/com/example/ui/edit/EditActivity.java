package com.example.ui.edit;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.R;
import com.example.data.constants.Constants;
import com.example.data.database.AppDatabase;
import com.example.data.database.AppExecutors;
import com.example.data.model.PersonModel;

public class EditActivity extends AppCompatActivity {
    EditText name, email, pincode, city, phoneNumber;
    Button button;
    int mPersonId;
    Intent intent;
    private AppDatabase mDb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            button.setText("Update");

            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, -1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    PersonModel person = mDb.personDao().loadPersonById(mPersonId);
                    populateUI(person);
                }
            });


        }

    }

    private void populateUI(PersonModel person) {

        if (person == null) {
            return;
        }

        name.setText(person.getName());
        email.setText(person.getEmail());
        phoneNumber.setText(person.getNumber());
        pincode.setText(person.getPincode());
        city.setText(person.getCity());
    }

    private void initViews() {
        name = findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        pincode = findViewById(R.id.edit_pincode);
        city = findViewById(R.id.edit_city);
        phoneNumber = findViewById(R.id.edit_number);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {

        if (name.getText().toString().trim()==null || name.getText().toString().equalsIgnoreCase("")) {

            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
        }
        else  if (email.getText().toString().trim()==null || email.getText().toString().equalsIgnoreCase("")) {

            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
        }
        else  if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {

            Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
        }
        else  if (phoneNumber.getText().toString().equalsIgnoreCase("") || phoneNumber.getText().length()>10){
            Toast.makeText(this, "Enter valid mobile", Toast.LENGTH_SHORT).show();
        }
        else
        {

            final PersonModel person = new PersonModel(
                    name.getText().toString(),
                    email.getText().toString(),
                    phoneNumber.getText().toString(),
                    pincode.getText().toString(),
                    city.getText().toString());

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (!intent.hasExtra(Constants.UPDATE_Person_Id)) {
                        mDb.personDao().insertPerson(person);
                    } else {
                        person.setId(mPersonId);
                        mDb.personDao().updatePerson(person);
                    }
                    finish();
                }
            });
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
