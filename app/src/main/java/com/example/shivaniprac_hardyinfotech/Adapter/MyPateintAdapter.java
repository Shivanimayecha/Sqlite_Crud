package com.example.shivaniprac_hardyinfotech.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shivaniprac_hardyinfotech.DatabaseHelper;
import com.example.shivaniprac_hardyinfotech.Model.PatientModel;
import com.example.shivaniprac_hardyinfotech.R;
import com.example.shivaniprac_hardyinfotech.activity.AddPatientActivity;
import com.example.shivaniprac_hardyinfotech.activity.MyPatientActivity;
import com.example.shivaniprac_hardyinfotech.activity.ViewPatientRecord;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPateintAdapter extends RecyclerView.Adapter<MyPateintAdapter.ViewHolder> {

    Context mContext;
    ArrayList<PatientModel> arrayList;
    DatabaseHelper dbHelper;

    public MyPateintAdapter(Context mContext, ArrayList<PatientModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        dbHelper = new DatabaseHelper(mContext);
    }

    @NonNull
    @Override
    public MyPateintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypatient_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPateintAdapter.ViewHolder holder, int position) {

        PatientModel model = arrayList.get(position);
        String id = model.getId();
        String name = model.getName();
        String email = model.getEmail();
        String contact = model.getContact();
        String dob = model.getDob();
        String weight = model.getWeight();
        String image = model.getImage();
        String gender = model.getGender();
        String deises = model.getDeiseas();

        holder.txt_name.setText(name);
        holder.txt_email.setText(email);
        holder.txt_contact.setText(contact);
        holder.txt_dob.setText(dob);
        if (image.equals("null")) {
            holder.img_pic.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.img_pic.setImageURI(Uri.parse(image));
        }
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ViewPatientRecord.class);
            intent.putExtra("RECORD_ID", id);
            mContext.startActivity(intent);
        });
        holder.img_more.setOnClickListener(view ->
        {
            showMoreDialog(
                    "" + position,
                    "" + id,
                    "" + name,
                    "" + dob,
                    "" + email,
                    "" + contact,
                    "" + gender,
                    "" + image,
                    "" + weight,
                    "" + deises);
        });
    }

    private void showMoreDialog(String pos, String id, String name, String dob, String email, String contact, String gender, String image, String weight, String diases) {

        String[] options = {"Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (i == 0) {
                    Intent intent = new Intent(mContext, AddPatientActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("dob", dob);
                    intent.putExtra("email", email);
                    intent.putExtra("contact", contact);
                    intent.putExtra("gender", gender);
                    intent.putExtra("image", image);
                    intent.putExtra("weight", weight);
                    intent.putExtra("diases", diases);
                    intent.putExtra("isEditMode", true);
                    mContext.startActivity(intent);
                } else if (i == 1) {
                    dbHelper.deleteData(id);
                    ((MyPatientActivity) mContext).onResume();
                }
            }
        });
        builder.create().show();

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name)
        TextView txt_name;
        @BindView(R.id.txt_dob)
        TextView txt_dob;
        @BindView(R.id.txt_email)
        TextView txt_email;
        @BindView(R.id.txt_contact)
        TextView txt_contact;
        @BindView(R.id.img_pic)
        ImageView img_pic;
        @BindView(R.id.img_more)
        ImageView img_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
