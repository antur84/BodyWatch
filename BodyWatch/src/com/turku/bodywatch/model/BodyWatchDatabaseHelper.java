package com.turku.bodywatch.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.turku.bodywatch.model.domainobjects.BodyFat;
import com.turku.bodywatch.model.domainobjects.BodyWater;
import com.turku.bodywatch.model.domainobjects.BodyWeight;
import com.turku.bodywatch.model.domainobjects.BoneWeight;
import com.turku.bodywatch.model.domainobjects.MuscleRatio;
import com.turku.bodywatch.model.domainobjects.RegistrationDate;
import com.turku.bodywatch.model.domainobjects.WeightEntry;

import android.annotation.SuppressLint;
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
	private static final String KEY_REGISTRATION_DATE = "registrationDate";

	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sqLiteDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	BodyWatchDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_WEIGHT_ENTRY_TABLE = "CREATE TABLE " + TABLE_WEIGHTENTRY
				+ " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_BODY_WEIGHT + " REAL, " + KEY_BODY_FAT + " REAL, "
				+ KEY_BODY_WATER + " REAL, " + KEY_MUSCLE_RATIO
				+ " REAL, " + KEY_BONE_WEIGHT + " REAL, "
				+ KEY_REGISTRATION_DATE + " TEXT NOT NULL" + ");";

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHTENTRY);
		 db.execSQL(CREATE_WEIGHT_ENTRY_TABLE); 
	
		 /*db.execSQL("CREATE TABLE TEST (ID INT PRIMARY KEY NOT NULL);");*/
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHTENTRY);
		onCreate(db);
	}

	public void insertWeightEntry(WeightEntry weightEntry) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_BODY_WEIGHT, weightEntry.getBodyWeight().getValue());
		values.put(KEY_BODY_FAT, weightEntry.getBodyFat().getValue());
		values.put(KEY_BODY_WATER, weightEntry.getBodyWater().getValue());
		values.put(KEY_MUSCLE_RATIO, weightEntry.getMuscleRatio().getValue());
		values.put(KEY_BONE_WEIGHT, weightEntry.getBoneWeight().getValue());
		values.put(KEY_REGISTRATION_DATE,
				sqLiteDateFormat.format(weightEntry.getRegistrationDate().getValue()));

		db.insert(TABLE_WEIGHTENTRY, null, values);
		db.close();

		Log.d("InsertWeight()", values.toString());
	}

	public List<WeightEntry> getAllWeightEntries() {
		List<WeightEntry> weightEntries = new LinkedList<WeightEntry>();
		String query = "SELECT * FROM " + TABLE_WEIGHTENTRY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		WeightEntry we = null;
		Date registrationDate;

		if (cursor.moveToFirst()) {
			do {
				try {
					registrationDate = cursor.isNull(6) ? null
							: sqLiteDateFormat.parse(cursor.getString(6));
				} catch (ParseException e) {
					Log.d(BodyWatchDatabaseHelper.class.toString(),
							"getAllWeightEntries()", e);
					registrationDate = null;
				}

				we = new WeightEntry(Integer.parseInt(cursor.getString(0)),
						new BodyWeight(cursor.getDouble(1)), new BodyFat(
								cursor.getDouble(2)), new BodyWater(
								cursor.getDouble(3)), new MuscleRatio(
								cursor.getDouble(4)), new BoneWeight(
								cursor.getDouble(5)), new RegistrationDate(
								registrationDate));

				weightEntries.add(we);
			} while (cursor.moveToNext());
		}

		Log.d("getAllBooks()", weightEntries.toString());

		// return books
		return weightEntries;
	}

	public void deleteWeightEntry(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_WEIGHTENTRY, KEY_ID+"="+id, null);
		Log.d("deleteWeightEntry()", id.toString());
	}
}
