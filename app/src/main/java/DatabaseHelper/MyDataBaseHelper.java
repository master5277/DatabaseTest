package DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.databasetest.R;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "CREATE TABLE BOOK("
            +"ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"AUTHOR TEXT,"
            +"PRICE REAL,"
            +"PAGES INTEGER,"
            +"NAME TEXT);";
    public static final String CREATE_CATEGORY = "CREATE TABLE CATEGORY("
            +"ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"CATEGORY_NAME TEXT,"
            +"CATEGORY_CODE INTEGER);";
    private Context mContext;

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);//数据库创建的同时，执行SQL语句创建数据表BOOK
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "Create Succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists BOOK");
        db.execSQL("drop table if exists CATEGORY");
        onCreate(db);
    }
}