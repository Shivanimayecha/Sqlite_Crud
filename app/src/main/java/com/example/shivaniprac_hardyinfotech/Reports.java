package com.example.shivaniprac_hardyinfotech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.shivaniprac_hardyinfotech.API.ApiInterface;
import com.example.shivaniprac_hardyinfotech.API.ApiServiceCreator;
import com.example.shivaniprac_hardyinfotech.Adapter.DiseaseAdapter;
import com.example.shivaniprac_hardyinfotech.Model.DataModel;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Reports extends AppCompatActivity {

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    ApiInterface apiservice;
    RecyclerView.LayoutManager layoutManager;
    DiseaseAdapter adapter;
    int statusCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        ButterKnife.bind(this);
        apiservice = ApiServiceCreator.createService("latest");

        call_dieasesApi();
        call_memberApi();
    }

    private void call_dieasesApi() {

        rv_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(layoutManager);
        Observable<DataModel> responseObservable = apiservice.get_disease();
        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DataModel>>() {
                    @Override
                    public Observable<? extends DataModel> call(Throwable throwable) {
                        if (throwable instanceof retrofit2.HttpException) {
                            retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                            statusCode = ex.code();
                            Log.e("error", ex.getLocalizedMessage());
                        } else if (throwable instanceof SocketTimeoutException) {
                            statusCode = 1000;
                        }
                        return Observable.empty();
                    }
                })
                .subscribe(new Observer<DataModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(DataModel model) {

                        adapter = new DiseaseAdapter(getApplication(), model.getDiseaseData());
                        rv_list.setAdapter(adapter);

                    }
                });
    }

    private void call_memberApi() {

        Observable<DataModel> responseObservable = apiservice.get_members();
        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DataModel>>() {
                    @Override
                    public Observable<? extends DataModel> call(Throwable throwable) {
                        if (throwable instanceof retrofit2.HttpException) {
                            retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                            statusCode = ex.code();
                            Log.e("error", ex.getLocalizedMessage());
                        } else if (throwable instanceof SocketTimeoutException) {
                            statusCode = 1000;
                        }
                        return Observable.empty();
                    }
                })
                .subscribe(new Observer<DataModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(DataModel model) {

                        Log.e("TAG", "onNext: member data--" + new Gson().toJson(model.getMemberData()));
                       /* for (int i = 0; i < model.getMemberData().size(); i++) {

                        }*/
                    }
                });

    }
}
