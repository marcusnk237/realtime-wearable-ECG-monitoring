package com.realtimewearable.SignalProcessing;
import static org.apache.commons.lang3.ObjectUtils.min;

import java.util.Arrays;

public class SignalProcessing {
    private static int F_ECG=128;
    private static Float ADCRANGE= 1023f;
    private static Float AD8232_V =3.3f;
    private static Float AD8232GAIN = 550f;
    private static Float MV  = 1000f;
    public static float Value2Millis(float x){

        return (MV*AD8232_V*x)/(ADCRANGE*AD8232GAIN);
    }
    public static float []  getSample (float[] signal , int start , int end) {

        float [] sample = new float[end-start];
        for (int i = start; i< end;i++){
            sample[i-start] = signal[i];
        }
        return sample;

    }
    public static int getIMax(float array[]) {
        if (array.length == 0) {
            return -1; // array contains no elements
        }
        float max = array[0];
        int pos = 0;

        for(int i=1; i<array.length; i++) {
            if (max < array[i]) {
                pos = i;
                max = array[i];
            }
        }
        return pos;
    }
    private static int [] getPeaks(float[]signal){
        int n = signal.length/F_ECG;
        int [] peaks = new int[n];
        int idx=0;
        for (int x=0;x<n;x++){
            float[] signal_sample = getSample (signal , idx, idx+F_ECG);
            peaks[x] = (getIMax(signal_sample)+idx)/F_ECG;
            idx = idx + F_ECG;
        }
        return  peaks;
    }
    public static int HeartRateEstimated (float[]signal){
        int[] peaks = getPeaks(signal);
        float interval = peaks[peaks.length-1] - peaks[0];
        return (int) (60*(peaks.length)/interval);

    }
    public static float ACSignal(float[] signal) {
        Arrays.sort(signal);
        return signal[signal.length - 1] - signal[0];
    }
    public static float Ratio_of_Ratios(float[] red, float[] ir) {
        return ACSignal(red) / ACSignal(ir);
    }
    public static int SpO2Values(float[] red, float[] ir) {
        int spo2 = (int) (11.78 * (Math.pow((double) Ratio_of_Ratios(red, ir), 3)) - 55.92 * (Math.pow((double) Ratio_of_Ratios(red, ir), 2)) + 28.84 * ((double) Ratio_of_Ratios(red, ir)) + 97.12);
        if (spo2 <= 0) {
            return 0;
        }
        else {
            return min(spo2, 100);
            }
    }


}