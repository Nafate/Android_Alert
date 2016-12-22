package com.mackech.alert.models;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by Cesar on 12/20/16.
 */

public class Contact {

    private String id;
    private String name;
    private ArrayList<String> phones;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }

    public static Contact fromCursor(Context context, Cursor cursor){

        Contact contact = new Contact();

        contact.setId(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
        contact.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

        ArrayList<String> phones = new ArrayList<>();
        if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0){

            Cursor phoneCursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[] { contact.getId() },
                    null);

            while (phoneCursor.moveToNext()) {
                phones.add(phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            }
            phoneCursor.close();

        }
        contact.setPhones(phones);

        return contact;
    }
}
