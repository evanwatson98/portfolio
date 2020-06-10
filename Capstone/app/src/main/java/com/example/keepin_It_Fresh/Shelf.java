package com.example.keepin_It_Fresh;

import androidx.appcompat.app.AppCompatActivity;

public class Shelf extends AppCompatActivity {

}

//
//    private static final String fileStorage = "shelfContent.txt";
//
//    EditText qty;
//    EditText exp;
//    EditText item;
//
//    Button saveButton;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shelf_page2);
//        setupViews();
//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                save(v);
//                startActivity(new Intent(Shelf.this, Shelf2.class));
//            }
//
//            });
//    }
//
//    public void save(View v) {
//        String textQ = qty.getText().toString();
//        String textE = exp.getText().toString();
//        String textI = item.getText().toString();
//        FileOutputStream fos = null;
//            try {
//        fos = openFileOutput(fileStorage, MODE_PRIVATE);
//        fos.write(textE.getBytes());
//        fos.write(textQ.getBytes());
//        fos.write(textI.getBytes());
//
//        Toast.makeText(this, "Saved to" + getFilesDir() + "/" + fileStorage, Toast.LENGTH_LONG).show();
//    } catch (
//    FileNotFoundException e) {
//        e.printStackTrace();
//    } catch (IOException e) {
//        e.printStackTrace();
//    } finally{
//        if(fos != null){
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//
//    public void setupViews () {
//        saveButton = (Button) findViewById(R.id.save);
//
//        qty = (EditText) findViewById(R.id.qtyInput);
//        exp = (EditText) findViewById(R.id.expDate);
//        item = (EditText) findViewById(R.id.itemInput);
//
//    }
//}
