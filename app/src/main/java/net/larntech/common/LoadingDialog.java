package net.larntech.common;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Window;

import net.larntech.R;

public class LoadingDialog {
    
    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity myActivity){
        this.activity = myActivity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);

        LayoutInflater inflater =this.activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        
    }

    public void dismissDialog(){
        dialog.dismiss();
    }

}
