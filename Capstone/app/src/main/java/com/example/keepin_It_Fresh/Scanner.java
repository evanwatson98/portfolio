package com.example.keepin_It_Fresh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class Scanner extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    public static final String EXTRA_SUBJECT = "com.example.application.example.EXTRA_SUBJECT";
    public static final String EXTRA_EMAIL = "com.example.application.example.EXTRA_EMAIL";
    public static final String EXTRA_TITLE = "com.example.application.example.EXTRA_TITLE";
    public static final String EXTRA_COMPONENT_NAME = "com.example.application.example.EXTRA_COMPONENT_NAME";
    public static final String EXTRA_SPLIT_NAME = "com.example.application.example.EXTRA_SPLIT_NAME";

    private ZXingScannerView scannerView;
    TextView value;
    TextView name;
    TextView group;
    TextView type;
    TextView message;
    TextView image;

    /*
    TextView imagePath;
    TextView upc;
    Button Edit;
    Button addFood;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_page);
        //addFood = findViewById(R.id.addFood);
        name = findViewById(R.id.foodName);
        group = findViewById(R.id.foodGroup);
        type = findViewById(R.id.foodType);
        image = findViewById(R.id.imageURL);
        //upc = findViewById(R.id.foodUPC);
        OkHttpClient client = new OkHttpClient();
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScannerResultHandler());
        OkHttpClient postIUServer;
        setContentView(scannerView);
        scannerView.startCamera();


    }

    OkHttpClient client = new OkHttpClient();

    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler {
        @Override
        public void handleResult(Result result) {
            String resultCode = result.getText();
            setContentView(R.layout.activity_scan_page);
            scannerView.stopCamera();
            value = findViewById(R.id.Num);
            value.setText(resultCode);

            String upcCode = value.getText().toString();
            String url = "https://cgi.sice.indiana.edu/~team39/scannerAddFood.php";
            RequestBody assignParameters = new FormBody.Builder()
                    .add("upc", upcCode)
                    .build();

            Request request = new Request.Builder()
                    .post(assignParameters)
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {

                        final String myResponse = Objects.requireNonNull(response.body()).string();
                        final String[] messageSplit = myResponse.split("--");

                        Scanner.this.runOnUiThread(() -> {
                            name = findViewById(R.id.foodName);
                            group = findViewById(R.id.foodGroup);
                            //upc = findViewById(R.id.foodUPC);
                            message = findViewById(R.id.foundMessage);
                            image = findViewById(R.id.imageURL);
                            message.setText(messageSplit[0]);
                            name.setText(messageSplit[1]);
                            group.setText(messageSplit[2]);
                            type.setText(messageSplit[3]);
                            image.setText(messageSplit[4].trim());


                            String foodName = name.getText().toString();

                            String foodUpc = value.getText().toString();
                            group = findViewById(R.id.foodGroup);
                            String foodGroup = group.getText().toString();
                            message = findViewById(R.id.foundMessage);
                            String foodMessage = message.getText().toString();
                            String foodType = type.getText().toString();
                            String imageURL = image.getText().toString();

                            if (foodMessage.contains("Item Found!")) {
                                Intent intent = new Intent(Scanner.this, EditScannerFound.class);
                                intent.putExtra(EXTRA_TITLE, foodName);
                                intent.putExtra(EXTRA_TEXT, foodUpc);
                                intent.putExtra(EXTRA_SUBJECT, foodMessage);
                                intent.putExtra(EXTRA_EMAIL, foodType);
                                intent.putExtra(EXTRA_COMPONENT_NAME, foodGroup);
                                intent.putExtra(EXTRA_SPLIT_NAME, imageURL);
                                //intent.putExtra(EXTRA_TEXT, String.valueOf(name));
                                //intent.putExtra(EXTRA_TEXT, String.valueOf(message));
                                startActivity(intent);
                            }
                            if (foodMessage.contains("Not Found. Edit Below.")) {
                                Intent intent = new Intent(Scanner.this, EditScanner.class);
                                intent.putExtra(EXTRA_TITLE, foodName);
                                intent.putExtra(EXTRA_TEXT, foodUpc);
                                intent.putExtra(EXTRA_SUBJECT, foodMessage);

                                startActivity(intent);
                            }
                        });
                    }
                }
            });

        }}
    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }


}
