package com.realtimewearable.BluetoothLE;
import static com.realtimewearable.SignalProcessing.SignalProcessing.*;
import static com.realtimewearable.Utils.Utils.Minmaxnormalize;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.realtimewearable.R;
import com.realtimewearable.SignalProcessing.SignalGenerator;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeviceControlActivity extends Activity {

    private final Handler plotHandler = new Handler();
    private Runnable plotTimer;


    private final static String TAG = DeviceControlActivity.class.getSimpleName();
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private String[] results;
    private LineGraphSeries<DataPoint> ecgseries,ppgirseries;
    private GraphView ecg_plot,ppg_plot;
    Viewport  ecgviewport,ppg_viewport;

    private double xECGData = 0;
    private  double xPPGData=0;
    public static final double F_Data = 128;
    private Float  heart_rate;

    private int spo2_value;



    private String ECG_flag = "A0";
    int ecg_counter=0;

    float [] history_raw_ecg = new float[512];
    float [] red = new float[512];
    float [] ecg = new float[512];
    float [] ir = new float[512];
    float [] raw_ecg = new float[512];
    private float [] features = new float[4];
    private TextView mConnectionState,mTemperatureField,mSpo2Field,mHeartRateField,mECGField,mHealthField;
    private String mDeviceName,mDeviceAddress;
    private BluetoothLEService mBluetoothLeService;
    private int idx = 0;
    private int ct_idx = 0;
    private List ecgfirebase = new ArrayList<>(512);
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    Menu optionsMenu;
    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLEService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLEService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);
                mConnectionState.setTextColor(Color.parseColor("#00FF00"));
                invalidateOptionsMenu();
            } else if (BluetoothLEService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                mConnectionState.setTextColor(Color.parseColor("#FF0000"));
                invalidateOptionsMenu();
                //clearUI();
            } else if (BluetoothLEService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
                getData();
            } else if (BluetoothLEService.ACTION_DATA_AVAILABLE.equals(action)) {
                //plot_Flag=true;
                readSensorData(intent.getStringExtra(BluetoothLEService.EXTRA_DATA),intent.getStringExtra(BluetoothLEService.FLAG_DATA));

            }
        }
    };

    private void getData() {
        if (mGattCharacteristics != null) {
            mBluetoothLeService.setCharacteristicNotification(
                    mGattCharacteristics.get(2).get(0), true);
            mBluetoothLeService.readCharacteristic(mGattCharacteristics.get(2).get(0));
        }
    }

    private void ECGplotPoints(int z){
        ecgseries.appendData(new DataPoint((xECGData / (F_Data)), (double) ecg[z]), false, 512);
        xECGData = xECGData + 1;
        if (xECGData>4*F_Data){
            ecgviewport.scrollToEnd();
        }
    }

    private void PPGplotPoints(int z){
        ppgirseries.appendData(new DataPoint((xPPGData / (F_Data)), (double) ir[z]), false, 512);
        xPPGData = xPPGData + 1;
        if (xPPGData>4*F_Data){
            ppg_viewport.scrollToEnd();
        }
    }

    void getPhysiologicalDatas(){

        history_raw_ecg =  Minmaxnormalize(raw_ecg);
        SignalGenerator gen =  new SignalGenerator();
        gen.signalGenerator((history_raw_ecg), getApplicationContext());
        red = gen.getRED();
        ir = gen.getIR();
        ecg = (gen.getECG());

        for (int z=0;z<512;z++){
            //double q=data[z];
            ecgseries.appendData(new DataPoint(((xECGData)/F_Data), Double.parseDouble(String.valueOf(ecg[z]))), true, 512);
            ppgirseries.appendData(new DataPoint(((xECGData)/F_Data), Double.parseDouble(String.valueOf(ir[z]))), true, 512);
            xECGData=xECGData+1;
        }

        spo2_value = SpO2Values(red,ir);
        heart_rate = Float.valueOf(HeartRateEstimated(ecg));
        mSpo2Field.setText(String.valueOf(spo2_value));
        mHeartRateField.setText(String.valueOf(heart_rate));
    }



    void ECGpointPlots(){
        ECGplotPoints((int) (xECGData%F_Data));
    }

    void PPGpointPlots(){PPGplotPoints((int) (xPPGData%F_Data));}


    @SuppressLint("NewApi")
    private void readSensorData(String data, String flag){
        results = data.split("\\|", -1);
        Log.d(TAG, String.valueOf(results.length));
        if (flag.contains(ECG_flag)) {

            for (int x =0; x<results.length;x=x+1) {
                raw_ecg[ecg_counter]= (Float.parseFloat((results[x])));
                ecg_counter=ecg_counter+1;
            }
            if ((ecg_counter%(4*F_Data)==0) && (ecg_counter>0)){
                ecg_counter=0;
                getPhysiologicalDatas();
            }
        }

    }

    private void PlotDatas ( float[] ecg, float [] ir){

    }
    private void clearUI() {
        mTemperatureField.setText(R.string.no_data);
        mSpo2Field.setText(R.string.no_data);
        mHeartRateField.setText(R.string.no_data);
        mECGField.setText(R.string.no_data);
        mHealthField.setText(R.string.no_data);
        ecg_plot.removeAllSeries();
        ppg_plot.removeAllSeries();


    }

    public void initPlots(){
        ecgseries = new LineGraphSeries<DataPoint>();
        ppgirseries = new LineGraphSeries<DataPoint>();

        ecgseries.setTitle("ECG Signal");
        ppgirseries.setTitle("PPG Signal");


        ecg_plot.getLegendRenderer().setVisible(true);
        ecg_plot.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        ppg_plot.getLegendRenderer().setVisible(true);
        ppg_plot.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);


        ecg_plot.addSeries(ecgseries);
        ecgseries.setColor(Color.GREEN);


        ppg_plot.addSeries(ppgirseries);
        ppgirseries.setColor(Color.RED);


        ecgviewport = ecg_plot.getViewport();
        ecgviewport.setMinY(0);
        ecgviewport.setMinX(0);
        ecgviewport.setMaxX(4);
        ecgviewport.setMaxY(1.2);
        ecgviewport.setXAxisBoundsManual(true);
        ecgviewport.setYAxisBoundsManual(true);
        ecgviewport.setScrollable(true);
        ecgviewport.setScrollableY(true);
        ecgviewport.setScalable(true);
        ecgviewport.setScalableY(true);
        ecg_plot.getGridLabelRenderer().setNumHorizontalLabels(4);
        ecg_plot.getGridLabelRenderer().setNumVerticalLabels(2);
        ecg_plot.getGridLabelRenderer().setHumanRounding(false);
        ecg_plot.getGridLabelRenderer().setHorizontalAxisTitle("Time (s)");
        ecg_plot.getGridLabelRenderer().setVerticalAxisTitle("Amplitude (mV)");
        ecg_plot.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                return ""+((int) value);
            }
        });

        ppg_viewport = ppg_plot.getViewport();
        ppg_viewport.setMinY(0);
        ppg_viewport.setMinX(0);
        ppg_viewport.setMaxX(4);
        ppg_viewport.setMaxY(1.2);
        ppg_viewport.setXAxisBoundsManual(true);
        ppg_viewport.setYAxisBoundsManual(true);
        ppg_viewport.setScrollable(true);
        ppg_viewport.setScrollableY(true);
        ppg_viewport.setScalable(true);
        ppg_viewport.setScalableY(true);
        ppg_plot.getGridLabelRenderer().setHumanRounding(false);
        ppg_plot.getGridLabelRenderer().setNumHorizontalLabels(4);
        ppg_plot.getGridLabelRenderer().setNumVerticalLabels(2);
        ppg_plot.getGridLabelRenderer().setHorizontalAxisTitle("Time (s)");
        ppg_plot.getGridLabelRenderer().setVerticalAxisTitle("Amplitude ");
        ppg_plot.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                return ""+((int) value);
            }
        });


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_viz);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        final Intent intent = getIntent();

        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        // Sets up UI references.
        mConnectionState = findViewById(R.id.connection_state);
        mSpo2Field = findViewById(R.id.spo2_value);

        mHeartRateField = findViewById(R.id.hr_value);

        ecg_plot = findViewById(R.id.ecg_plot);
        ppg_plot = findViewById(R.id.ppg_plot);

        initPlots();

        getActionBar().setTitle(mDeviceName);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent gattServiceIntent = new Intent(this, BluetoothLEService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

    }


    private DataPoint[] PPGgenerateData() {
        int count = 512;
        DataPoint[] values = new DataPoint[count];

        for (int i=0; i<count; i++) {
            double x = (double)(i/F_Data);
            double y = Double.parseDouble(String.valueOf(ir[i]));
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }
    private DataPoint[] ECGgenerateData() {
        int count = 512;
        DataPoint[] values = new DataPoint[count];

        for (int i=0; i<count; i++) {
            double x = (i)/(F_Data);
            double y = ecg[i];
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

        plotTimer = new Runnable() {

            @Override
            public void run() {
                plotHandler.postDelayed(this, 1);

            }
        };
        plotHandler.postDelayed(plotTimer, 4000);


        //getHealth();
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
        plotHandler.removeCallbacks(plotTimer);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wearable_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        optionsMenu = menu;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_connect) {
            mBluetoothLeService.connect(mDeviceAddress);
            return true;
        }
        if (item.getItemId() == R.id.menu_disconnect) {
            mBluetoothLeService.disconnect();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<>();

        // Loops through available GATT Services.
        String LIST_UUID = "UUID";
        String LIST_NAME = "NAME";
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();

            currentServiceData.put(
                    LIST_NAME, GattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, GattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }

        SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
                this,
                gattServiceData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 },
                gattCharacteristicData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 }
        );
    }
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLEService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLEService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLEService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLEService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

}


