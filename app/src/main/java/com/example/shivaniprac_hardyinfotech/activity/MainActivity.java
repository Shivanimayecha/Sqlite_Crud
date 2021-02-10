package com.example.shivaniprac_hardyinfotech.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shivaniprac_hardyinfotech.R;
import com.example.shivaniprac_hardyinfotech.Reports;
import com.example.shivaniprac_hardyinfotech.activity.AddPatientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.card_addpatient)
    CardView card_addpatient;
    @BindView(R.id.card_mypatient)
    CardView card_mypatient;
    @BindView(R.id.card_reports)
    CardView card_reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        card_addpatient.setOnClickListener(this);
        card_mypatient.setOnClickListener(this);
        card_reports.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()) {
            case R.id.card_addpatient:
                intent = new Intent(getApplicationContext(), AddPatientActivity.class);
                intent.putExtra("isEditMode", false);
                startActivity(intent);
                break;
            case R.id.card_mypatient:
                intent = new Intent(getApplicationContext(), MyPatientActivity.class);
                startActivity(intent);
                break;
            case R.id.card_reports:
                if (isNetworkConnected()) {
                    intent = new Intent(getApplicationContext(), Reports.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
