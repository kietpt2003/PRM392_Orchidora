package com.example.prm391_orchidora.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tạo bảng OrchidList khi cơ sở dữ liệu được tạo
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS OrchidList(id NVARCHAR(200), quantity INTEGER, isSelected INTERGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Xử lý khi cơ sở dữ liệu cần nâng cấp
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS OrchidList");
        onCreate(sqLiteDatabase);
    }

    public void queryData(String sqlQuery){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sqlQuery);
    }

    public Cursor getData(String sqlQuery){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sqlQuery, null);
    }
}
