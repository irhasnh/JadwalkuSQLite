package id.rackspira.jadwalku.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.rackspira.jadwalku.Model.Jadwal;

/*
 * Created by kikiosha on 3/5/18.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "DbHelper";

    private static final String DATABASE_NAME = "jadwalku";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_INPUT = "tabel_jadwal";

    private static final String ID = "id";
    private static final String NAMA_MAKUL = "nama_makul";
    private static final String DOSEN = "dosen";
    private static final String RUANG = "ruang";
    private static final String JAM_MULAI = "jam_mulai";
    private static final String JAM_SELESAI = "jam_selesai";
    private static final String HARI = "hari";

    private static DbHelper dbHelper;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryCreateTabelJadwal = "create table " +
                TABLE_INPUT +
                " (" +
                ID + " integer primary key autoincrement not null," +
                NAMA_MAKUL + " text," +
                DOSEN + " text," +
                RUANG + " text," +
                JAM_MULAI + " text," +
                JAM_SELESAI + " text," +
                HARI + " text" +
                ");";

        sqLiteDatabase.execSQL(queryCreateTabelJadwal);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int lama, int baru) {
        if (lama != baru){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INPUT);
            onCreate(sqLiteDatabase);
        }
    }

    public static synchronized DbHelper getInstance(Context context){
        if (dbHelper == null){
            dbHelper = new DbHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    private DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertJadwal(Jadwal jadwal){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(NAMA_MAKUL, jadwal.getNamaMakul());
            values.put(DOSEN, jadwal.getDosen());
            values.put(RUANG, jadwal.getRuang());
            values.put(JAM_MULAI, jadwal.getJamMulai());
            values.put(JAM_SELESAI, jadwal.getJamSelesai());
            values.put(HARI, jadwal.getHari());

            sqLiteDatabase.insertOrThrow(TABLE_INPUT, null, values);
            sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLException e){
            e.printStackTrace();
            Log.d(TAG, "Gagal Untuk Menambah"+e);
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public List<Jadwal> getJadwal(String hari){
        List<Jadwal> jadwalList = new ArrayList<>();
        String JADWAL_QUERY = " SELECT * FROM "+ TABLE_INPUT + " WHERE " + HARI + " = '" + hari + "'";

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(JADWAL_QUERY, null);

        try{
            if (cursor.moveToFirst()){
                do {
                    Jadwal jadwal = new Jadwal(
                            cursor.getString(cursor.getColumnIndex(ID)),
                            cursor.getString(cursor.getColumnIndex(NAMA_MAKUL)),
                            cursor.getString(cursor.getColumnIndex(DOSEN)),
                            cursor.getString(cursor.getColumnIndex(RUANG)),
                            cursor.getString(cursor.getColumnIndex(JAM_MULAI)),
                            cursor.getString(cursor.getColumnIndex(JAM_SELESAI)),
                            cursor.getString(cursor.getColumnIndex(HARI)));

                    Log.d(ID, cursor.getString(cursor.getColumnIndex(ID)));
                    jadwalList.add(jadwal);
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException e){
            Log.d(TAG, "Gagal Mendapat Data");
        } finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return jadwalList;
    }

    public void deleteJadwal(String id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        try{
            sqLiteDatabase.beginTransaction();
            sqLiteDatabase.execSQL("DELETE FROM " + TABLE_INPUT + " WHERE " + ID + " = '" + id + "'");
            sqLiteDatabase.setTransactionSuccessful();
            Log.d(TAG, "Berhasil Menghapus Data");
        } catch (SQLException e){
            e.printStackTrace();
            Log.d(TAG, "Gagal Menghapus Data");
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void updateData(Jadwal jadwal){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        try{
            sqLiteDatabase.beginTransaction();
            sqLiteDatabase.execSQL("UPDATE " + TABLE_INPUT + " SET " +
                    NAMA_MAKUL + " = '" + jadwal.getNamaMakul() + "', " +
                    DOSEN + " = '" + jadwal.getDosen() + "', " +
                    RUANG + " = '" + jadwal.getRuang() + "', " +
                    JAM_MULAI + " = '" + jadwal.getJamMulai() + "', " +
                    JAM_SELESAI + " = '" + jadwal.getJamSelesai() + "', " +
                    HARI + " = '" + jadwal.getHari() + "' WHERE " +
                    ID + " = '" + jadwal.getId() + "'");
            sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLException e){
            e.printStackTrace();
            Log.d(TAG, "Gagal Update Data");
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }
}
