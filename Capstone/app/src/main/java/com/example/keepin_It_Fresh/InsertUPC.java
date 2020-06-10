package com.example.keepin_It_Fresh;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class InsertUPC extends AppCompatActivity {
    Context context = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upc_post_request);
        Button post = findViewById(R.id.post);
        final TextView outputPost = findViewById(R.id.outputPost);

        post.setOnClickListener(v -> {

            OkHttpClient client = new OkHttpClient();

            String url = "http://cgi.soic.indiana.edu/~evwatson/capstone/insertUPC.php";

            try {
                final InputStream is = context.getAssets().open("fooddata.csv");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String myLine;
                int total = 0;
                int max_row_number = 100;
                reader.readLine();
                //ArrayList<String> inserts = new ArrayList<>();
                String name = "";
                String foodGroup = "";
                String upc = "";
                String measurements = "";
                String allergens = "";
                String dietary = "";
                //ArrayList<String> recipes = new ArrayList<String>();

                while (total <= max_row_number) {
                    try{Thread.sleep(1500);}catch(InterruptedException e){System.out.println(e);}
                    total++;
                    String[] row = reader.readLine().replaceAll("[\n]", "").split(",");
                    name += row[2];
                    foodGroup += row[4];
                    upc += row[0];
                    measurements += row[3];
                    allergens += row[6];
                    dietary += row[5];

                    RequestBody reqBody = new FormBody.Builder()
                            .add("name", name)
                            .add("foodGroup", foodGroup)
                            .add("upc", upc)
                            .add("measurements", measurements)
                            .add("allergens", allergens)
                            .add("dietary", dietary)

                            .build();

                    Request request = new Request.Builder()
                            .url(url)
                            .post(reqBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                        }


                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            if (response.isSuccessful()) {
                                final String resp = Objects.requireNonNull(response.body()).string();
                                outputPost.setText("SUCESSFUL" + resp);

                                InsertUPC.this.runOnUiThread(() -> {

                                });
                            } else {
                                outputPost.setText("not running");
                            }
                        }
                    });
                    name = "";
                    foodGroup = "";
                    upc = "";
                    measurements = "";
                    allergens = "";
                    dietary = "";
                }
            }catch (IOException e) {
                outputPost.setText(e.getMessage());
            }
        });

    }
}
