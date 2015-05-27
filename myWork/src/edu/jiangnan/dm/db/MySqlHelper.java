package edu.jiangnan.dm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import edu.jiangnan.dm.Constants;

public class MySqlHelper extends SQLiteOpenHelper {

	public MySqlHelper(Context context, String name, CursorFactory factory,
					   int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table "+ Constants.USERINFO_TABLE_NAME + "(_id integer primary key autoincrement,"
				+ TUserInfoColumn.USER_NAME +" varchar(20),"
				+ TUserInfoColumn.USER_SEX + " varchar(20),"
				+ TUserInfoColumn.USER_NATION + " varchar(20),"
				+ TUserInfoColumn.USER_NATIONALITY + " varchar(20),"
				+ TUserInfoColumn.USER_BIRTHDAY + " varchar(20),"
				+ TUserInfoColumn.USER_IDCARD_NUM + " varchar(20),"
				+ TUserInfoColumn.USER_TELEPHONE + " varchar(20),"
				+ TUserInfoColumn.USER_EMAIL + " varchar(50),"
				+ TUserInfoColumn.USER_FAMILY_ADDRESS + " varchar(50),"
				+ TUserInfoColumn.USER_CURRENT_ADDRESS + " varchar(50),"
				+ TUserInfoColumn.USER_JOB + " varchar(50),"
				+ TUserInfoColumn.USER_ORGANIZATION + " varchar(50),"
				+ TUserInfoColumn.USER_ORGAN_ADDRESS + " varchar(50),"
				+ TUserInfoColumn.USER_POLITICAL_STATUE + " varchar(20),"
				+ TUserInfoColumn.USER_IMAGE + " blob)");
		db.execSQL("create table "+ Constants.CONTACT_TABLE_NAME + "(_id integer primary key autoincrement,"
				+ TContactColumn.NAME +" varchar(20),"
				+ TContactColumn.JOB +" varchar(20),"
				+ TContactColumn.TELEPHONE +" varchar(20),"
				+ TContactColumn.EMAIL +" varchar(50),"
				+ TContactColumn.ORGANIZATION +" varchar(50),"
				+ TContactColumn.ADDRESS +" varchar(20),"
				+ TContactColumn.CARDID +" varchar(20),"
				+ TContactColumn.QRCODE + " blob)");

		System.out.println("onCreate tables");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// db.execSQL("drop table if exists student");
		System.out.println("onUpgrade ������" + oldVersion + "--" + newVersion);

	}

}
