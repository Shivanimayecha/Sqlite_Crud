package com.example.shivaniprac_hardyinfotech.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shivaniprac_hardyinfotech.Adapter.DiseaseAdapter;
import com.example.shivaniprac_hardyinfotech.Adapter.MyPateintAdapter;
import com.example.shivaniprac_hardyinfotech.DatabaseHelper;
import com.example.shivaniprac_hardyinfotech.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPatientActivity extends AppCompatActivity {


    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    RecyclerView.LayoutManager layoutManager;
    MyPateintAdapter adapter;
    DatabaseHelper dbHelper;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patient);
        ButterKnife.bind(this);
        dbHelper = new DatabaseHelper(this);
        actionBar = getSupportActionBar();
        actionBar.setTitle("All Records");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        rv_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(layoutManager);
        loadData();
    }

    private void loadData() {

        adapter = new MyPateintAdapter(this, dbHelper.getAllRecords());
        rv_list.setAdapter(adapter);

        actionBar.setSubtitle("Total :" + dbHelper.getRecordsCount());
    }

    private void searchRecord(String query) {
        adapter = new MyPateintAdapter(this, dbHelper.searchRecords(query));
        rv_list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecord(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecord(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_deleteall) {
            deleteDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyPatientActivity.this);
        builder.setMessage("Are you sure want to delete all records ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbHelper.deleteAllData();
                onResume();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}