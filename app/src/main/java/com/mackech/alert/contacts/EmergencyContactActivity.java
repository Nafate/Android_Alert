package com.mackech.alert.contacts;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import com.mackech.alert.R;
import com.mackech.alert.models.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EmergencyContactActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private ContactsAdapter mContactsAdapter;
    private ArrayList<Contact> mContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rv_contacts);

        mContacts = new ArrayList<>();
        mContactsAdapter = new ContactsAdapter(this,mContacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvContacts.setAdapter(mContactsAdapter);

        getSupportLoaderManager().initLoader(0,null,this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        while(cursor.moveToNext()){
            mContacts.add(Contact.fromCursor(this,cursor));
        }

        Collections.sort(mContacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                return contact1.getName().compareTo(contact2.getName());
            }
        });

        mContactsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
