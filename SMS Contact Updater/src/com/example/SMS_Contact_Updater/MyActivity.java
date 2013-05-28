package com.example.SMS_Contact_Updater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    final String TAG = "MyActivity";

    private void updateContact() {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI).withSelection(where, params).withValue(ContactsContract.CommonDataKinds.Phone.DATA, phone).build());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String contactName = getIntent().getExtras().getString("contactName");
        String newContactNumber = getIntent().getExtras().getString("newContactNumber");

        try {
            if(!contactName.equals(null)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
                builder.setMessage("Do you want to update "+contactName+"'s contact with "+newContactNumber+" ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }   catch (NullPointerException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
            builder.setMessage("Do you wish to create a new contact for "+newContactNumber+"?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }


    }
}
