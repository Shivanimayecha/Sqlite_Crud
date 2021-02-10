package com.example.shivaniprac_hardyinfotech.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shivaniprac_hardyinfotech.Model.DataModel;
import com.example.shivaniprac_hardyinfotech.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.MyViewHolder> {

    Context mContext;
    List<DataModel.DiseaseDatum> arraylist;

    public DiseaseAdapter(Context mContext, List<DataModel.DiseaseDatum> arraylist) {
        this.mContext = mContext;
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public DiseaseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diseas_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseAdapter.MyViewHolder holder, int position) {

        holder.txt_name.setText(arraylist.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name)
        TextView txt_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
