package com.example.shivaniprac_hardyinfotech.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shivaniprac_hardyinfotech.Constants;
import com.example.shivaniprac_hardyinfotech.DatabaseHelper;
import com.example.shivaniprac_hardyinfotech.Model.PatientModel;
import com.example.shivaniprac_hardyinfotech.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPatientRecord extends AppCompatActivity {

    @BindView(R.id.txt_name)
    TextView txt_name;
    @BindView(R.id.txt_dob)
    TextView txt_dob;
    @BindView(R.id.txt_email)
    TextView txt_email;
    @BindView(R.id.txt_contact)
    TextView txt_contact;
    @BindView(R.id.txt_weight)
    TextView txt_weight;
    @BindView(R.id.txt_gender)
    TextView txt_gender;
    @BindView(R.id.txt_deises)
    TextView txt_deises;
    @BindView(R.id.img_pic)
    ImageView img_pic;
    String recordId;

    private ActionBar actionBar;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_record);
        ButterKnife.bind(this);
        dbHelper = new DatabaseHelper(this);
        actionBar = this.getSupportActionBar();
        actionBar.setTitle("Patient Record");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        recordId = getIntent().getStringExtra("RECORD_ID");
        showRecordDetails();
    }

    private void showRecordDetails() {

        String selectQry = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + recordId + "\"";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQry, null);
        if (cursor.moveToFirst()) {
            do {
                String id = "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID));
                String name = "" + cursor.getString(cursor.getColumnIndex(Constants.C_FULL_NAME));
                String dob = "" + cursor.getString(cursor.getColumnIndex(Constants.C_DOB));
                String weight = "" + cursor.getString(cursor.getColumnIndex(Constants.C_WEIGHT));
                String image = "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE));
                String email = "" + cursor.getString(cursor.getColumnIndex(Constants.C_EMAIL_ADD));
                String disease = "" + cursor.getString(cursor.getColumnIndex(Constants.C_DISEASE));
                String contact = "" + cursor.getString(cursor.getColumnIndex(Constants.C_CONTACT_NUMBER));
                String gender = "" + cursor.getString(cursor.getColumnIndex(Constants.C_GENDER));

                txt_name.setText(name);
                txt_dob.setText(dob);
                txt_email.setText(email);
                txt_contact.setText(contact);
                txt_gender.setText(gender);
                txt_weight.setText(weight);
                txt_deises.setText(disease);
                if (image.equals("null")) {
                    img_pic.setImageResource(R.mipmap.ic_launcher);
                } else {
                    img_pic.setImageURI(Uri.parse(image));
                }
            } while (cursor.moveToNext());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}