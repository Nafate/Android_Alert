package com.mackech.alert.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mackech.alert.R;

import java.util.ArrayList;

/**
 * Created by Cesar on 12/21/16.
 */

public class BleDeviceAdapter extends RecyclerView.Adapter<BleDeviceAdapter.DeviceHolder> {

    private Context mContext;
    private ArrayList<BluetoothDevice> mDevices;
    private OnDeviceClickListener mListener;

    public BleDeviceAdapter(Context context, ArrayList<BluetoothDevice> devices,OnDeviceClickListener listener) {
        this.mContext = context;
        this.mDevices = devices;
        this.mListener = listener;
    }

    @Override
    public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ble_device,parent,false));
    }

    @Override
    public void onBindViewHolder(DeviceHolder holder, int position) {
        holder.bind(mDevices.get(0));
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    public class DeviceHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private TextView tvAddress;

        public DeviceHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tv_name);
            tvAddress = (TextView) v.findViewById(R.id.tv_address);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null)
                        mListener.onDeviceClicked(mDevices.get(getAdapterPosition()));
                }
            });

        }

        public void bind(BluetoothDevice device){
            tvName.setText(device.getName() != null ? device.getName() : "Unknown");
            tvAddress.setText(device.getAddress());
        }
    }

    public interface OnDeviceClickListener{
        void onDeviceClicked(BluetoothDevice device);
    }
}
