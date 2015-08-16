package it.jaschke.alexandria.CameraPreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {

    public static final int REQUEST_CODE = 1001;

    private String TAG = "ScannerActivity";

    private ZXingScannerView mZXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mZXingScannerView = new ZXingScannerView(this);
        setContentView(mZXingScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mZXingScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mZXingScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mZXingScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        // Do something with the result here
        Context context = getApplicationContext();
        CharSequence text = result.getText();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent intent = new Intent();
        intent.putExtra("EAN", result.getText());
        setResult(1001, intent);
        finish();
//        Log.v(TAG, result.getText()); // Prints scan results
//        Log.v(TAG, result.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
    }
}
