package id.go.kominfo.andi.sqlitepractice.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import id.go.kominfo.andi.sqlitepractice.dao.FriendDao;
import id.go.kominfo.andi.sqlitepractice.model.Friend;

public class FriendDBOne extends SQLiteOpenHelper implements FriendDao {
    private static final String DBNAME = "kominfo.db";
    private static final int DBVERSION = 1;

    public FriendDBOne(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists Friend(" +
                "id integer primary key autoincrement," +
                "name text," +
                "address text," +
                "phone text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("drop table if exists friend");
            onCreate(db);
        }

    }

    @Override
    public void insert(Friend f) {
        String sql = "insert into friend values(?,?,?,?)";
        getWritableDatabase().execSQL(sql, new Object[]{
                null,
                f.getName(),
                f.getAddress(),
                f.getPhone()
        });
    }

    @Override
    public void update(Friend f) {
        String sql = "update friend set name=?, address=?, phone=? where id=?";
        getWritableDatabase().execSQL(sql, new Object[]{
                f.getName(),
                f.getAddress(),
                f.getPhone(),
                f.getId()
        });
    }

    @Override
    public void delete(int id) {
        String sql = "delete from friend where id=?";
        getWritableDatabase().execSQL(sql, new Object[]{id});
    }

    @Override
    public Friend getAFriend(int id) {
        Friend result = null;
        String sql = "select * from friend where id=?";
        Cursor cursor = getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            result = new Friend(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        }
        cursor.close();
        return result;
    }

    @Override
    public List<Friend> getAllFriend() {
        List<Friend> result = new ArrayList<>();
        String sql = "select * from friend";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            result.add(new Friend(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            ));
        }
        cursor.close();
        return result;
    }
}
