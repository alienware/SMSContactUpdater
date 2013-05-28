package com.example.SMS_Contact_Updater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Tanay
 * Date: 20/5/13
 * Time: 8:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SMSReceiver extends BroadcastReceiver{

    final String TAG = "SMSReceiver";
    private String getContactNameFromPartialName (Context context, String partialContactName) {

        String contactName = null;
        //int i=0;
        Uri lookUpURI = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(partialContactName));
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        String selection =  null;
        String[] selectionArgs = null;
        String orderBy = null;
        Cursor contactLookupCursor = context.getContentResolver().query(lookUpURI, projection, selection, selectionArgs, orderBy);
        if(contactLookupCursor.moveToFirst()) {
            do {
                int contactNameIndex = contactLookupCursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME);
                contactName = contactLookupCursor.getString(contactNameIndex);
                //i++;
            }   while(contactLookupCursor.moveToNext());
        }
        contactLookupCursor.close();

        return contactName;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();



        if (bundle != null) {
            Object[] pdusObj = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdusObj.length];

            // getting SMS information from Pdu.
            for (int i = 0; i < pdusObj.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
            }

            String newContactNumber = null;
            String body = null;
            String contactName = null;

            for (SmsMessage currentMessage : messages) {
                newContactNumber = currentMessage.getDisplayOriginatingAddress();
                body = currentMessage.getDisplayMessageBody();
            }

            contactName = getContactNameFromPartialName(context, body);

            Intent i = new Intent(context,MyActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("newContactNumber", newContactNumber);
            i.putExtra("contactName", contactName);
            context.startActivity(i);


        }

    }
}
