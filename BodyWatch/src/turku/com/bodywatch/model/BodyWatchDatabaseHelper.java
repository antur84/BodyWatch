package turku.com.bodywatch.model;

import java.util.LinkedList;
import java.util.List;

import turku.com.bodywatch.model.domainobjects.BodyFat;
import turku.com.bodywatch.model.domainobjects.BodyWater;
import turku.com.bodywatch.model.domainobjects.BodyWeight;
import turku.com.bodywatch.model.domainobjects.BoneWeight;
import turku.com.bodywatch.model.domainobjects.MuscleRatio;
import turku.com.bodywatch.model.domainobjects.WeightEntry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BodyWatchDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "BodywatchDB";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_WEIGHTENTRY = "weightEntry";
	private static final String KEY_ID = "id";
	private static final String KEY_BODY_WEIGHT = "bodyWeight";
	private static final String KEY_BODY_FAT = "bodyFat";
	private static final String KEY_BODY_WATER = "bodyWater";
	private static final String KEY_MUSCLE_RATIO = "muscleRatio";
	private static final String KEY_BONE_WEIGHT = "boneWeight";

	BodyWatchDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_WEIGHT_ENTRY_TABLE = "CREATE TABLE " + TABLE_WEIGHTENTRY
				+ " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_BODY_WEIGHT + " INTEGER, " + KEY_BODY_FAT + " INTEGER, "
				+ KEY_BODY_WATER + " INTEGER, " + KEY_MUSCLE_RATIO
				+ " INTEGER, " + KEY_BONE_WEIGHT + " INTEGER " + ")";

		db.execSQL(CREATE_WEIGHT_ENTRY_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO need implementation if any model changes
	}

	public void InsertWeight(WeightEntry weightEntry) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_BODY_WEIGHT, weightEntry.getBodyWeight().toString());
		values.put(KEY_BODY_FAT, weightEntry.getBodyFat().toString());
		values.put(KEY_BODY_WATER, weightEntry.getBodyWater().toString());
		values.put(KEY_MUSCLE_RATIO, weightEntry.getMuscleRatio().toString());
		values.put(KEY_BONE_WEIGHT, weightEntry.getBoneWeight().toString());

		db.insert(TABLE_WEIGHTENTRY, null, values);
		db.close();
		
		Log.d("InsertWeight()", values.toString());
	}

	public List<WeightEntry> getAllWeightEntries() {
		List<WeightEntry> weightEntries = new LinkedList<WeightEntry>();   
		String query = "SELECT  * FROM " + TABLE_WEIGHTENTRY;
	 
	       SQLiteDatabase db = this.getWritableDatabase();
	       Cursor cursor = db.rawQuery(query, null);
	
	       WeightEntry we = null;
	       if (cursor.moveToFirst()) {
	           do {
	               we = new WeightEntry(
	            		   Integer.parseInt(cursor.getString(0)),
	            		   new BodyWeight(cursor.getLong(1)),
	            		   new BodyFat(cursor.getLong(2)),
	            		   new BodyWater(cursor.getLong(3)),
	            		   new MuscleRatio(cursor.getLong(4)),
	            		   new BoneWeight(cursor.getLong(5)));
	 
	            		   weightEntries.add(we);
	           } while (cursor.moveToNext());
	       }
	 
	       Log.d("getAllBooks()", weightEntries.toString());
	 
	       // return books
	       return weightEntries;
	}
}
