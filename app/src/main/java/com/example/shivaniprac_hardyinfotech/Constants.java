package com.example.shivaniprac_hardyinfotech;

public class Constants {

    public static final String DB_NAME = "PATIENT_DB";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "PATIENT_TABLE";


    public static final String C_ID = "ID";
    public static final String C_FULL_NAME = "FULL_NAME";
    public static final String C_CONTACT_NUMBER = "CONTACT_NUMBER";
    public static final String C_EMAIL_ADD = "EMAIL_ADD";
    public static final String C_DOB = "DOB";
    public static final String C_GENDER = "GENDER";
    public static final String C_WEIGHT = "WEIGHT";
    public static final String C_IMAGE = "IAMGE";
    public static final String C_DISEASE = "DISEASE";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_FULL_NAME + " TEXT,"
            + C_CONTACT_NUMBER + " TEXT,"
            + C_EMAIL_ADD + " TEXT,"
            + C_DOB + " TEXT,"
            + C_GENDER + " TEXT,"
            + C_WEIGHT + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_DISEASE + " TEXT"
            + ");";


}
