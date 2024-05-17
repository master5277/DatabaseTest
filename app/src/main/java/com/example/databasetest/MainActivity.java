package com.example.databasetest;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import DatabaseHelper.MyDataBaseHelper;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private MyDataBaseHelper dbHelper;//定义MyDataBaseHelper实例
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建数据库和表
        dbHelper = new MyDataBaseHelper(this,"BookStore.db",null,3);//创建实例
        Button createDataBase = (Button) findViewById(R.id.create_database);
        createDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();//没有对应名称的数据库就创建
            }
        });


        //插入数据到表中
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name","The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price",16.96);
                //插入第一条数据
                db.insert("Book",null,values);
                values.clear();
                //开始组装第二条数据
                values.put("name","The Lost Symbol");
                values.put("author","Dan Brown");
                values.put("pages",510);
                values.put("price",19.95);
                //插入第二条数据
                db.insert("BOOK",null,values);

            }
        });

        //更新数据
        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",10.99);
                db.update("BOOK",values,"name=?",new String[]{"The Da Vinci Code"});

            }
        });

        Button deleteButton =(Button) findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("BOOK","pages>?",new String[]{"500"});
            }
        });

        Button queryButton = (Button) findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //查询BOOK表中所有的数据
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if (cursor.moveToFirst())
                {
                    do {
                        //遍历Cursor对象，去除数据并打印
                        String name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
                        String author = cursor.getString(cursor.getColumnIndexOrThrow("AUTHOR"));
                        int pages = cursor.getInt(cursor.getColumnIndexOrThrow("PAGES"));
                        double price = cursor.getDouble(cursor.getColumnIndexOrThrow("PRICE"));
                        Log.d(TAG, "book name is "+name);
                        Log.d(TAG, "book author is "+author);
                        Log.d(TAG, "book pages is "+pages);
                        Log.d(TAG, "book prices is "+price);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}