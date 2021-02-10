package com.example.shivaniprac_hardyinfotech.API;

import com.example.shivaniprac_hardyinfotech.Model.DataModel;

import retrofit2.http.GET;
import rx.Observable;

public interface ApiInterface {

    @GET("members.php")
    public Observable<DataModel> get_members();

    @GET("disease.php")
    public Observable<DataModel> get_disease();

}