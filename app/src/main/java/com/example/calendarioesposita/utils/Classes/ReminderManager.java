package com.example.calendarioesposita.utils.Classes;

import android.content.Context;
import android.database.Cursor;

import com.example.calendarioesposita.DAO.ReminderDAO;

import java.util.Calendar;

public class ReminderManager {

    private Context context;
    private ReminderDAO reminderDAO;

    public ReminderManager(Context context) {
        this.context = context;
        reminderDAO = new ReminderDAO(context);
        reminderDAO.open();
    }

    public void saveReminder(String description, String date) {

        // Guardar en la base de datos
        reminderDAO.saveReminder(description, date);

        // Programar la notificación
        scheduleNotification(description, date);
    }

    public Cursor getAllReminders(){
        return reminderDAO.getAllReminders();
    }

    private void scheduleNotification(String description, String date) {
        // Aquí deberías agregar el código para programar una notificación.
        // Puede usar AlarmManager, NotificationManager, etc.
    }

    public void close() {
        reminderDAO.close();
    }
}
