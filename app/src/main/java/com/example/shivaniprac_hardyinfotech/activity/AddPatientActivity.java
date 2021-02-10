package com.example.shivaniprac_hardyinfotech.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivaniprac_hardyinfotech.DatabaseHelper;
import com.example.shivaniprac_hardyinfotech.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;

public class AddPatientActivity extends AppCompatActivity {

    @BindView(R.id.edt_fname)
    EditText edt_fname;
    @BindView(R.id.edt_num)
    EditText edt_num;
    @BindView(R.id.edt_add)
    EditText edt_add;
    @BindView(R.id.edt_dob)
    EditText edt_dob;
    @BindView(R.id.edt_weight)
    EditText edt_weight;
    @BindView(R.id.rd1)
    RadioButton rd1;
    @BindView(R.id.rd2)
    RadioButton rd2;
    @BindView(R.id.chk1)
    CheckBox chk1;
    @BindView(R.id.chk2)
    CheckBox chk2;
    @BindView(R.id.chk3)
    CheckBox chk3;
    @BindView(R.id.chk4)
    CheckBox chk4;
    @BindView(R.id.chk5)
    CheckBox chk5;
    @BindView(R.id.chk6)
    CheckBox chk6;
    @BindView(R.id.chk7)
    CheckBox chk7;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.img_pic)
    ImageView img_pic;
    @BindView(R.id.txt_name)
    TextView txt_name;

    DatePickerDialog datePickerDialog;
    Uri mCapturedImageURI;
    String mediaPath = "", filePath1;
    Integer CAPTURE_IMAGE = 3;
    private String id, name, number, email, weight, gender, dob, diseas;
    private DatabaseHelper db;
    List<String> selection = new ArrayList<>();
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        ButterKnife.bind(this);

        checkAndRequestPermissions();
        db = new DatabaseHelper(this);


        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);

        if (isEditMode) {
            //update data
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            dob = intent.getStringExtra("dob");
            email = intent.getStringExtra("email");
            number = intent.getStringExtra("contact");
            gender = intent.getStringExtra("gender");
            mediaPath = intent.getStringExtra("image");
            weight = intent.getStringExtra("weight");
            diseas = intent.getStringExtra("diases");

            edt_fname.setText(name);
            edt_dob.setText(dob);
            edt_add.setText(email);
            edt_num.setText(number);
            edt_weight.setText(weight);
            if (gender.equals("Male")) {
                rd1.setChecked(true);
            } else {
                rd2.setChecked(true);
            }
            if (!mediaPath.toString().equals("null")) {
                image.setImageURI(Uri.parse(mediaPath));
            }
        } else {
            //add data
        }


        img_pic.setOnClickListener(view -> {
            selectImage();
        });

        rd1.setOnClickListener(view -> {
            gender = rd1.getText().toString();
        });
        rd2.setOnClickListener(view -> {
            gender = rd2.getText().toString();
        });

        edt_dob.setOnClickListener(view -> {

            // calender class's instance and get current date , month and year from calender
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            datePickerDialog = new DatePickerDialog(AddPatientActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            edt_dob.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);
                            //txtdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                            //for day
                           /* SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                            Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                            txt_day.setText(simpledateformat.format(date));

                            SimpleDateFormat simpledateformat1 = new SimpleDateFormat("EEE"); // for pass in api
                            Date date1 = new Date(year, monthOfYear, dayOfMonth - 1);
                            txtday = simpledateformat1.format(date1);*/
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        btn_submit.setOnClickListener(view -> {

            name = "" + edt_fname.getText().toString().trim();
            number = "" + edt_num.getText().toString().trim();
            dob = "" + edt_dob.getText().toString().trim();
            email = "" + edt_add.getText().toString().trim();
            weight = "" + edt_weight.getText().toString().trim();

            String final_items = "";
            for (String Selection : selection) {
                final_items = TextUtils.join(",", selection);
            }
            Log.e("finial_items", final_items);
            txt_name.setText(final_items);

            if (isEditMode) {

                db.updateInfo(
                        "" + id,
                        "" + name,
                        "" + number,
                        "" + email,
                        "" + dob,
                        "" + gender,
                        "" + mediaPath,
                        "" + diseas,
                        "" + weight
                );
                Toast.makeText(this, "Updated...", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                long id = db.insertInfo(
                        "" + name,
                        "" + number,
                        "" + email,
                        "" + dob,
                        "" + gender,
                        "" + mediaPath,
                        "" + final_items,
                        "" + weight
                );
                Toast.makeText(this, "Record Added to id" + id, Toast.LENGTH_SHORT).show();
                Log.e("image path ", mediaPath);
            }
        });
    }

    public void selectItem(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.chk1:
                if (checked) {
                    selection.add(chk1.getText().toString());
                } else {
                    selection.remove(chk1.getText().toString());
                }
                break;
            case R.id.chk2:
                if (checked) {
                    selection.add(chk2.getText().toString());
                } else {
                    selection.remove(chk2.getText().toString());
                }
                break;
            case R.id.chk3:
                if (checked) {
                    selection.add(chk3.getText().toString());
                } else {
                    selection.remove(chk3.getText().toString());
                }
                break;
            case R.id.chk4:
                if (checked) {
                    selection.add(chk4.getText().toString());
                } else {
                    selection.remove(chk4.getText().toString());
                }
                break;
            case R.id.chk5:
                if (checked) {
                    selection.add(chk5.getText().toString());
                } else {
                    selection.remove(chk5.getText().toString());
                }
                break;
            case R.id.chk6:
                if (checked) {
                    selection.add(chk6.getText().toString());
                } else {
                    selection.remove(chk6.getText().toString());
                }
                break;
            case R.id.chk7:
                if (checked) {
                    selection.add(chk7.getText().toString());
                } else {
                    selection.remove(chk7.getText().toString());
                }
                break;
        }

    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPatientActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        String fileName = "temp.jpg";
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, fileName);
                        mCapturedImageURI = getContentResolver()
                                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        values);
                        takePictureIntent
                                .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE);

                    }
                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = null;
            if (selectedImage != null) {
                cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            }
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //filePath1 = cursor.getString(column_index);
            mediaPath = cursor.getString(columnIndex);
            // mediaPath=compressImage(mediaPath1);
            Log.e("TAG", "onActivityResult: 1--" + mediaPath);
            image.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            cursor.close();
        } else if (requestCode == CAPTURE_IMAGE) {
            ImageCropFunctionCustom(mCapturedImageURI);

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                // Uri uri = result.getUri();
                Compressor compressedImageFile = new Compressor(this);
                compressedImageFile.setQuality(60);
                try {
                    File file = compressedImageFile.compressToFile(new File(result.getUri().getPath()));
                    Uri uri = Uri.fromFile(file);

                    Bundle bundle = data.getExtras();
                    assert bundle != null;
                    filePath1 = uri.getPath();
                    mediaPath = compressImage(filePath1);
                    if (filePath1 != null) {
                        image.setImageBitmap(BitmapFactory.decodeFile(filePath1));
                    } else {
                        if (uri != null) {
                            filePath1 = uri.getPath();
                            mediaPath = compressImage(filePath1);
                            image.setImageResource(0);
                            image.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                            Log.e("TAG", "onActivityResult: 2--" + mediaPath);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ImageCropFunctionCustom(Uri uri) {
        Intent intent = CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private boolean checkAndRequestPermissions() {

        int camera = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
        int writeStorage = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        int readStorage = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");

        List<String> listPermissionNeeded = new ArrayList<>();

        if (writeStorage != 0) {
            listPermissionNeeded.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        if (readStorage != 0) {
            listPermissionNeeded.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (camera != 0) {

            listPermissionNeeded.add("android.permission.CAMERA");
        }
        if (listPermissionNeeded.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), 100);
        return false;
    }


}
