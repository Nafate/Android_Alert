package com.mackech.alert.contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mackech.alert.MemoryServices;
import com.mackech.alert.R;
import com.mackech.alert.models.Contact;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Cesar on 12/20/16.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactHolder> {

    private Context mContext;
    private ArrayList<Contact> mContacts;

    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        mContext = context;
        mContacts = contacts;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(LayoutInflater.from(mContext).inflate(R.layout.item_contact,parent,false));
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        holder.bind(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvPhones;
        private CheckBox cbEmergencyContact;

        public ContactHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tv_name);
            tvPhones = (TextView) v.findViewById(R.id.tv_phones);
            cbEmergencyContact = (CheckBox) v.findViewById(R.id.cb_emergency_contact);
        }

        public void bind(final Contact contact) {
            tvName.setText(contact.getName());
            StringBuilder sb = new StringBuilder();
            for(String phone : contact.getPhones())
                sb.append(phone).append("\n");
            tvPhones.setText(sb.toString());


            cbEmergencyContact.setOnCheckedChangeListener(null);
            if(contact.getPhones() != null && contact.getPhones().size() > 0){
                Set<String> emergencyContacts = MemoryServices.getEmergencyContacts(mContext);
                cbEmergencyContact.setChecked(emergencyContacts.contains(contact.getPhones().get(0)));
            }else{
                cbEmergencyContact.setChecked(false);
            }

            cbEmergencyContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                    if(contact.getPhones() != null && contact.getPhones().size()>0){
                        Set<String> emergencyContacts = MemoryServices.getEmergencyContacts(mContext);
                        if(checked){
                            emergencyContacts.add(contact.getPhones().get(0));
                        }else{
                            emergencyContacts.remove(contact.getPhones().get(0));
                        }
                        MemoryServices.setEmergencyContacts(mContext,emergencyContacts);
                    }

                }
            });

        }
    }
}
