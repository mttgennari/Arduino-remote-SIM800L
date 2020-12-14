package com.gennari.scaldofragno.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gennari.scaldofragno.R;

/*public class ProgressDialog {
    Context context;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    View dialogView;

    public ProgressDialog(Context context){
        this.context = context;

        builder = new AlertDialog.Builder(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null, false);
        builder.setView(dialogView);
        alertDialog = builder.create();
    }

    public void setTitle(String title){
        ((TextView)dialogView.findViewById(R.id.title)).setText(title);
    }

    public void setMessage(String message){
        ((TextView)dialogView.findViewById(R.id.message)).setText(message);
    }

    public void show(){
        alertDialog.show();
    }

    public void dismiss(){
        alertDialog.dismiss();
    }*/

public class ProgressDialog extends Dialog{

    public ProgressDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_progress);
    }

    public void setTitle(String title){
        ((TextView)findViewById(R.id.title)).setText(title);
    }

    public void setMessage(String message){
        ((TextView)findViewById(R.id.message)).setText(message);
    }
}
