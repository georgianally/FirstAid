package com.example.firstaid.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ReportDB.db";
    public static final String LOCATIONS_TABLE_NAME = "savedLocations";
    public static final String LOCATIONS_COLUMN_ID = "id";
    public static final String LOCATIONS_COLUMN_NAME = "name";
    public static final String COLUMN_NAME_START = "start";
    public static final String COLUMN_NAME_FINISH = "finish";
    public static final String COLUMN_NAME_LOCATION = "location";
    public static final String COLUMN_NAME_SAFE = "safe";
    public static final String COLUMN_NAME_DANGER = "danger";
    public static final String COLUMN_NAME_RESPONSIVE = "responsive";
    public static final String COLUMN_NAME_BLEED = "bleed";
    public static final String COLUMN_NAME_CPR = "cpr";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table savedLocations " +
                "(id INTEGER PRIMARY KEY autoincrement, name text, start text, finish text, location text, safe text, danger text, responsive text, bleed text, cpr text)"
        );
        //dummyData();
    }

    private void dummyData() {
        insertLocation("Test","12:55", "13:55", "Hythe, Kent", "Yes", "No", "Yes", "No", "No");
        insertLocation("Test2", "11:55", "13:55","Folkestone, Kent", "Yes", "No", "Yes", "No", "No");
        insertLocation("Test3", "11:55", "13:55","Folkestone, Kent", "Yes", "No", "Yes", "No", "No");
        insertLocation("Test4", "11:55", "13:55","Folkestone, Kent", "Yes", "No", "Yes", "No", "No");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS savedLocations");
        onCreate(db);
    }

    public boolean insertLocation(String name, String start, String finish, String location, String safe, String danger, String responsive, String bleed, String cpr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("start", start);
        contentValues.put("finish", finish);
        contentValues.put("location", location);
        contentValues.put("safe", safe);
        contentValues.put("danger", danger);
        contentValues.put("responsive", responsive);
        contentValues.put("bleed", bleed);
        contentValues.put("cpr", cpr);
        db.insert("savedLocations", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from savedLocations where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, LOCATIONS_TABLE_NAME);
        return numRows;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ LOCATIONS_TABLE_NAME);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name ='" + LOCATIONS_TABLE_NAME + "'");
        dummyData();
        db.close();
    }

    public ArrayList<String> getSavedLocations() {
        return getStrings(LOCATIONS_COLUMN_NAME);
    }

    public ArrayList<String> getStart() {
        return getStrings(COLUMN_NAME_START);
    }

    public ArrayList<String> getFinish() {
        return getStrings(COLUMN_NAME_FINISH);
    }

    public ArrayList<String> getLocation() {
        return getStrings(COLUMN_NAME_LOCATION);
    }

    public ArrayList<String> getSafe() {
        return getStrings(COLUMN_NAME_SAFE);
    }

    public ArrayList<String> getDanger() {
        return getStrings(COLUMN_NAME_DANGER);
    }

    public ArrayList<String> getResponsive() {
        return getStrings(COLUMN_NAME_RESPONSIVE);
    }
    public ArrayList<String> getBleed() {
        return getStrings(COLUMN_NAME_BLEED);
    }
    public ArrayList<String> getCPR() {
        return getStrings(COLUMN_NAME_CPR);
    }

    private ArrayList<String> getStrings(String columnName) {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from savedLocations", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(columnName)));
            res.moveToNext();
        }
        return array_list;
    }
}

