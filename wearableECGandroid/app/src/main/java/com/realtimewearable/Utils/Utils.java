package com.realtimewearable.Utils;
import static java.lang.Math.round;

public class Utils {
    public static byte[] floatToBytes(float d) {
        int i = Float.floatToRawIntBits(d);
        return intToBytes(i);
    }

    public static byte[] intToBytes(int v) {
        byte[] r = new byte[4];
        for (int i = 0; i < 4; i++) {
            r[i] = (byte) ((v >>> (i * 8)) & 0xFF);
        }
        return r;
    }
    public static float [] doubleToFloat(double [] s){
        float [] d = new float [s.length];
        for (int i=0;i<s.length;i++){
            d[i] = (float) s[i];
        }
        return d;
    }

    public static double [] floatToDouble(float [] s){
        double [] d = new double [s.length];
        for (int i=0;i<s.length;i++){
            d[i] = s[i];
        }
        return d;
    }
    public static byte[] floatArrayToBytes(float[] d) {
        byte[] r = new byte[d.length * 4];
        for (int i = 0; i < d.length; i++) {
            byte[] s = floatToBytes(d[i]);
            for (int j = 0; j < 4; j++)
                r[4 * i + j] = s[j];/* w  w  w .ja v  a  2 s  .c om*/
        }
        return r;
    }
    static float minValue(float arr[]){
        float min = arr[0];
        for(int i = 1; i<arr.length; i++)
            if(arr[i]<min) min = arr[i];
        return min;
    }

    // Method to find maximum value from the data set
    static float maxValue(float arr[]){
        float max = arr[0];
        for(int i = 1; i<arr.length; i++)
            if(arr[i]>max) max = arr[i];
        return max;
    }
    public static float [] Minmaxnormalize (float[] data){
        float [] results =  new float[data.length];

        for (int i=0;i<data.length;i++){
            results[i] = ( data[i] - minValue(data) )/( maxValue(data) - minValue(data) );

        }
        return results;

    }


}
