package com.example.calendarioesposita.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.calendarioesposita.Helpers.ReminderDatabaseHelper;

public class ReminderDAO {

    private SQLiteDatabase database;
    private ReminderDatabaseHelper dbHelper;

    public ReminderDAO(Context context) {
        dbHelper = new ReminderDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Método para guardar un recordatorio en la base de datos
    public void saveReminder(String description, String date) {
        ContentValues values = new ContentValues();
        values.put(ReminderDatabaseHelper.COLUMN_DESCRIPTION, description);
        values.put(ReminderDatabaseHelper.COLUMN_DATE, date);

        database.insert(ReminderDatabaseHelper.TABLE_REMINDERS, null, values);
    }

    // Método para obtener todos los recordatorios
    public Cursor getAllReminders() {
        return database.query(ReminderDatabaseHelper.TABLE_REMINDERS, null, null, null, null, null, null);
    }
}
