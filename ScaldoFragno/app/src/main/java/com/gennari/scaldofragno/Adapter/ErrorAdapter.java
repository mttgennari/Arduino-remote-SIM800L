package com.gennari.scaldofragno.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gennari.scaldofragno.R;
import com.gennari.scaldofragno.data.ErrorLog;

import java.util.ArrayList;
import java.util.List;

public class ErrorAdapter extends RecyclerView.Adapter<ErrorAdapter.ErrorViewHolder> {
    private ErrorLog[] logs;

    public ErrorAdapter(ErrorLog[] logs){
        this.logs = logs;
    }

    public void setLogs(ErrorLog[] logs) {
        this.logs = logs;
    }

    @NonNull
    @Override
    public ErrorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_error, parent, false);
        ErrorViewHolder vh = new ErrorViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ErrorViewHolder holder, int position) {
        holder.txtErrDate.setText(logs[position].getData());
        holder.txtErrDesc.setText(logs[position].getErrDesc());
        holder.txtErrCode.setText(String.format("%03d", logs[position].getErrCode()));
    }

    @Override
    public int getItemCount() {
        return logs.length;
    }

    public class ErrorViewHolder extends RecyclerView.ViewHolder{
        public TextView txtErrDate, txtErrCode, txtErrDesc;
        public ErrorViewHolder(View itemView) {
            super(itemView);
            txtErrDate = itemView.findViewById(R.id.txtErrData);
            txtErrDesc = itemView.findViewById(R.id.txtErrDesc);
            txtErrCode = itemView.findViewById(R.id.txtErrCode);
        }
    }
}
