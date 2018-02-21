package com.afpsoft.artbook;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_art,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.add_art){

            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
            intent.putExtra("info","new");
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview= findViewById(R.id.listView);

        ArrayList<String > artName= new ArrayList<>();
        ArrayList<Bitmap> artImage=new ArrayList<>();

        ArrayAdapter arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,artName);
        listview.setAdapter(arrayAdapter);

        try {

            Main2Activity.database=this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);
            Main2Activity.database.execSQL("CREATE TABLE IF NOT EXISTS arts (name VARCHAR,image BLOB)");

            Cursor cursor = Main2Activity.database.rawQuery("SELECT * FROM arts", null);

            int nameIx= cursor.getColumnIndex("name");
            int ImageIx=cursor.getColumnIndex("image");

            while (cursor!=null) {

                System.out.println("nameIX şudur ki" + nameIx);
                artName.add(cursor.getString(nameIx));

                byte [] byteArray= cursor.getBlob(ImageIx);
                Bitmap image= BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
                artImage.add(image);

                cursor.moveToNext();

                arrayAdapter.notifyDataSetChanged();


            }

        }catch (Exception e) {

            e.printStackTrace();
        }



    }


}
