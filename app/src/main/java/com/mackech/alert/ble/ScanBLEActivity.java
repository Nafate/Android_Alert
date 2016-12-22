package com.mackech.alert.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mackech.alert.MainActivity;
import com.mackech.alert.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScanBleActivity extends AppCompatActivity implements BleDeviceAdapter.OnDeviceClickListener{

    private BleDeviceAdapter mBleDeviceAdapter;
    private ArrayList<BluetoothDevice> mDevices;
    private BluetoothLeScanner mBleScanner;

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            if(!mDevices.contains(device)) {
                mDevices.add(device);
                mBleDeviceAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {

        }

        @Override
        public void onScanFailed(int errorCode) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        RecyclerView rvScanResults = (RecyclerView) findViewById(R.id.rv_scan_results);

        mDevices = new ArrayList<>();
        mBleDeviceAdapter = new BleDeviceAdapter(this,mDevices,this);

        rvScanResults.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvScanResults.setAdapter(mBleDeviceAdapter);

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        BluetoothAdapter bluetoothAdapter = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (bluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ScanSettings scanSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setReportDelay(0)
                .build();

        ArrayList<ScanFilter> filters = new ArrayList<>();

        ScanFilter filter = new ScanFilter.Builder()
                .setServiceUuid(new ParcelUuid(UUID.fromString(GattAttributes.AUTOMATION_IO_SERVICE)))
                .build();

//        filters.add(filter);

        mBleScanner = bluetoothAdapter.getBluetoothLeScanner();
        mBleScanner.startScan(filters,scanSettings,mScanCallback);

    }

    @Override
    protected void onStop() {
        mBleScanner.stopScan(mScanCallback);
        super.onStop();
    }

    @Override
    public void onDeviceClicked(BluetoothDevice device) {
        startActivity(new Intent(this, MainActivity.class)
                .putExtra("BLE_DEVICE",device));
    }

}
