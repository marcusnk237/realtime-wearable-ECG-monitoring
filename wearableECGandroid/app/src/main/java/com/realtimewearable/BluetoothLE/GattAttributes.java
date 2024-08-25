package com.realtimewearable.BluetoothLE;
import java.util.HashMap;
public class GattAttributes {
    private static HashMap<String, String> attributes = new HashMap<>();
    public static String SENSOR_MEASUREMENT = "00001aab-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Dummy Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        attributes.put("19b10000-e8f2-537e-4f6c-d104768a1214", "Wearable device Service");
        // Sample Characteristics.
        attributes.put(SENSOR_MEASUREMENT, "Wearable datas measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
