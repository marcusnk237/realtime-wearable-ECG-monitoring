package com.realtimewearable.SignalProcessing;
import static com.realtimewearable.Utils.Utils.floatArrayToBytes;
import android.content.Context;
import com.realtimewearable.ml.SignalEcgRedIr;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class SignalGenerator {
    private float [] ecg ;
    private float [] red ;
    private float [] ir ;

    public SignalGenerator(){
        this.ecg = new float[512];
        this.red = new float[512];
        this.ir  = new float[512];
    }

    public void signalGenerator(float[]signal, Context context) {
        try {

            SignalEcgRedIr model = SignalEcgRedIr.newInstance(context);

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 512}, DataType.FLOAT32);
            @NonNull ByteBuffer byteBuffer = ByteBuffer.wrap(floatArrayToBytes(signal));
            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            SignalEcgRedIr.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            TensorBuffer outputFeature1 = outputs.getOutputFeature1AsTensorBuffer();
            TensorBuffer outputFeature2 = outputs.getOutputFeature2AsTensorBuffer();

            ecg = outputFeature0.getFloatArray();
            red = outputFeature1.getFloatArray();
            ir = outputFeature2.getFloatArray();
            model.close();
            // Releases model resources if no longer used.

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public float[] getECG(){
        return ecg;
    }
    public float [] getRED(){
        return red;
    }
    public float [] getIR(){
        return ir;
    }
}