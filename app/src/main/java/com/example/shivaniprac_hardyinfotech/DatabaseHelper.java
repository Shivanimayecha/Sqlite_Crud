package com.example.shivaniprac_hardyinfotech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.shivaniprac_hardyinfotech.Model.PatientModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public long insertInfo(String name, String contact, String email, String dob, String gender, String image, String diseas, String weight) {

        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_FULL_NAME, name);
        values.put(Constants.C_CONTACT_NUMBER, contact);
        values.put(Constants.C_EMAIL_ADD, email);
        values.put(Constants.C_DOB, dob);
        values.put(Constants.C_GENDER, gender);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_DISEASE, diseas);
        values.put(Constants.C_WEIGHT, weight);

        long id = sdb.insert(Constants.TABLE_NAME, null, values);
        sdb.close();
        return id;
    }

    //update existing record
    public void updateInfo(String id, String name, String contact, String email, String dob, String gender, String image, String diseas, String weight) {

        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_FULL_NAME, name);
        values.put(Constants.C_CONTACT_NUMBER, contact);
        values.put(Constants.C_EMAIL_ADD, email);
        values.put(Constants.C_DOB, dob);
        values.put(Constants.C_GENDER, gender);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_DISEASE, diseas);
        values.put(Constants.C_WEIGHT, weight);

        sdb.update(Constants.TABLE_NAME, values, Constants.C_ID + " = ?", new String[]{id});
        sdb.close();
    }

    //get Records
    public ArrayList<PatientModel> getAllRecords() {

        ArrayList<PatientModel> recordList = new ArrayList<>();
        String SelectQry = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQry, null);

        if (cursor.moveToFirst()) {
            do {
                PatientModel model = new PatientModel(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_FULL_NAME)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_DOB)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_WEIGHT)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_EMAIL_ADD)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_DISEASE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_CONTACT_NUMBER)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_GENDER))
                );
                recordList.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordList;
    }

    //search records
    public ArrayList<PatientModel> searchRecords(String query) {

        ArrayList<PatientModel> recordList = new ArrayList<>();
        String SelectQry = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_FULL_NAME + " LIKE '%" + query + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SelectQry, null);

        if (cursor.moveToFirst()) {
            do {
                PatientModel model = new PatientModel(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_FULL_NAME)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_DOB)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_WEIGHT)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_EMAIL_ADD)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_DISEASE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_CONTACT_NUMBER)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_GENDER))
                );
                recordList.add(model);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordList;
    }

    //get number of records
    public int getRecordsCount() {
        String countQry = " SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQry, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //delete data using id
    public void deleteData(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.C_ID + " = ?", new String[]{id});
        db.close();
    }

    //delete all data from table
    public void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + Constants.TABLE_NAME);
        db.close();
    }
}
