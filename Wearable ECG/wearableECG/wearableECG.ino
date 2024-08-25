/*
 *  Import libraries :
 *   - Wire : Access to micro-controller register
 *   - ArduinoBLE : Arduino Bluetooth Low Energy Library
 */
#include <Wire.h>
#include <Arduino.h>
#include <ArduinoBLE.h>
/*
 *  UUIDs values and device name
 *  UUID_SERVICE : Device service
 *  UUID_DATA : Device characteristic
 *  DEVICE_NAME : Wearable device name
 */

#define UUID_WEARABLE_SERVICE "19B10000-E8F2-537E-4F6C-D104768A1214"  // SERVICE UUID
#define DEVICE_NAME "WearableDevice" // DEVICE NAME
float final_ecg_value;



/*
 *   int variables for byte conversion:
 *   - ecg_int : ECG sample
 */
int ecg_int; 

/*
 *   Timer variables :
 *   - current_time : current time of signal acquistion
 *   - old_time : previous time of signal acquisition
 */
unsigned long current_time,old_time;
/*
 *  Bytes array for Bluetooth Low Energy data transmission:
 *  - ecgDATA : byte array for ECG samples
 */
byte ECGDATA[129];

/*
 *  Bluetooth Low Energy Initialization
 *  - Create BLE service 
 *  - Create BLE characteristics for each datas
 */
BLEService wearableService(UUID_WEARABLE_SERVICE); // Create service
BLECharacteristic dataWearable("1AAB", BLERead | BLENotify |BLEWrite , 129); // Create data characteristic
/*
 *  Setup microcontroller :
 *  - Set BLE connection
 */

void setup() {
  old_time = 0; // Initialize previous time
  pinMode(6, INPUT); // ECG Electrode LO+
  pinMode(5, INPUT); // ECG Electrode LO-
  idx=1;
  /*
   * Check if BLE sensor is detected
   */
  if (!BLE.begin()) {
    while (1); 
  }
  /*
   * Set byte array flags and index
   */
  ECGDATA[0] = 0xA0;
  /*
   * Set Our device as a BLE Server
   */
  BLE.setLocalName(DEVICE_NAME);  // Set local name
  BLE.setAdvertisedService(wearableService); // Set advertise service
  wearableService.addCharacteristic(dataWearable); // Add data characteristic
  BLE.addService(wearableService); // Add service
  BLE.advertise();   // Set advertise
}


/*
 * Get ECG sample and store to the ECG byte
 */

void getECG(){ 

    final_ecg_value = analogRead(A0); // Get analogic value
    ecg_int = (int)(final_ecg_value *100); // Multiply value by 100 for byte conversion
    ECGDATA[idx++] = highByte(ecg_int); // High Byte conversion
    ECGDATA[idx++] = lowByte(ecg_int); // High Byte conversion

    if ((cnt%64==0)){
      dataWearable.writeValue(ECGDATA, sizeof(ECGDATA)); // Send ECG byte array
      idx=1;
    }
}

/*
 * Read and send ECG values
 */

void readSensors(){
  current_time = micros(); // Get current time
  if (10*(current_time-old_time) >= 78125) {
    getValues();
    old_time=current_time; 
  }
}

/*
 * Main program of the microcontroller 
 * The program will read sensors datas and send it through BLE
 */
void loop(){
   BLEDevice central = BLE.central(); // initialize BLE
   if (central) { 
    while (central.connected()){ // While the device is connected
       readSensors();
    }
    
  }
}