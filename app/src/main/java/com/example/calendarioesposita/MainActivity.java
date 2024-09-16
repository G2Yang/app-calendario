package com.example.calendarioesposita;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarioesposita.utils.Adapters.CalendarAdapter;
import com.example.calendarioesposita.utils.Adapters.DaysOfWeekAdapter;
import com.example.calendarioesposita.utils.Adapters.ReminderAdapter;
import com.example.calendarioesposita.utils.Classes.DayItem;
import com.example.calendarioesposita.utils.Classes.ReminderManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends ComponentActivity {
    private Calendar calendar; // Variable para manejar el mes actual
    private CalendarAdapter adapter; // Adapter del RecyclerView para actualizar el calendario
    private RecyclerView calendarRecyclerView; // RecyclerView de los días del mes
    private Button anteriorBt,siguenteBt, createRutineBt, showRutines;
    private TextView monthNameTextView;
    private ReminderManager reminderManager;
    private ReminderAdapter reminderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos el calendario
        calendar = Calendar.getInstance();
        reminderManager = new ReminderManager(this);

        // Instancia los botones dandoles la referencia y les inicia el listener del click
        instanceButtonElements();
        setOnClickListeners();

        // Inicializa el TextView para mostrar el nombre del mes
        monthNameTextView = findViewById(R.id.weekName);

        // Instancia y le indica como gestionar el layout
        calendarRecyclerView = findViewById(R.id.calendarView);
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));

        // Instancia y le indica como gestionar el layout
        RecyclerView calendarRecuclerViewOfWeek = findViewById(R.id.daysOfWeekView);
        calendarRecuclerViewOfWeek.setLayoutManager(new GridLayoutManager(this, 7));

        // Generar lista de los dias de la semana
        List<String> daysOfWeek = generateDaysOfWeek();
        DaysOfWeekAdapter weekAdapter = new DaysOfWeekAdapter(daysOfWeek);
        calendarRecuclerViewOfWeek.setAdapter(weekAdapter);

        // Generar lista de días del mes actual
        List<DayItem> daysOfMonth = generateDaysOfMonth();
        adapter = new CalendarAdapter(daysOfMonth);
        calendarRecyclerView.setAdapter(adapter);

        // Inicializar el nombre del mes
        updateMonthName();
    }

    private void updateMonthName() {
        // Obtener el nombre del mes actual
        String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, getResources().getConfiguration().locale);

        // Establecer el nombre del mes en el TextView
        monthNameTextView.setText(monthName);
    }

    private void instanceButtonElements() {
        // set Buttons
         anteriorBt = findViewById(R.id.anteriorBt);
         siguenteBt = findViewById(R.id.siguenteBt);
         createRutineBt = findViewById(R.id.createRutineBt);
        showRutines = findViewById(R.id.showRutines);
    }

    private void setOnClickListeners(){
        // Listener para el botón "anterior"
        anteriorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrementar el mes
                calendar.add(Calendar.MONTH, -1);
                // Regenerar la lista de días y actualizar el RecyclerView
                updateCalendar();
            }
        });

        // Listener para el botón "siguiente"
        siguenteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Incrementar el mes
                calendar.add(Calendar.MONTH, 1);
                // Regenerar la lista de días y actualizar el RecyclerView
                updateCalendar();
            }
        });

        createRutineBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Infla el diseño del diálogo
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_form, null);

                // Crea el diálogo
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Ingresar información")
                        .setView(dialogView)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Obtiene los valores ingresados
                                EditText editTextSubject = dialogView.findViewById(R.id.editTextSubject);
                                DatePicker datePicker = dialogView.findViewById(R.id.editTextDate);

                                // Obtener el texto del EditText (asignatura)
                                String subject = editTextSubject.getText().toString();

                                // Obtener los valores del DatePicker
                                int day = datePicker.getDayOfMonth();
                                int month = datePicker.getMonth() + 1; // Nota: Los meses comienzan en 0, por lo que enero es 0.
                                int year = datePicker.getYear();

                                // Formatear la fecha como una cadena (opcional)
                                String date = day + "/" + month + "/" + year;

                                Toast.makeText(view.getContext(), "Asunto: " + subject + ", Día: " + date, Toast.LENGTH_LONG).show();

                                reminderManager.saveReminder(sunject, date);
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });


        showRutines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Infla el diseño del diálogo
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_see_reminder, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Todos los reminders")
                        .setView(dialogView)
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                // Obtener todos los recordatorios de la base de datos
                Cursor cursor = reminderManager.getAllReminders();

                // Inicializar el RecyclerView dentro del dialogView
                RecyclerView recyclerView = dialogView.findViewById(R.id.dataRecycler);

                // Configurar el adaptador y conectarlo al RecyclerView
                reminderAdapter = new ReminderAdapter(view.getContext(), cursor);

                recyclerView.setAdapter(reminderAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext())); // Asegúrate de configurar un LayoutManager

                // Mostrar el diálogo
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    // Método para actualizar el calendario
    private void updateCalendar() {
        List<DayItem> daysOfMonth = generateDaysOfMonth();
        adapter.updateDays(daysOfMonth); // Método en el adapter para actualizar los datos
        adapter.notifyDataSetChanged();  // Notificar al adapter que los datos cambiaron

        // Actualizar el nombre del mes
        updateMonthName();
    }

    private List<DayItem> generateDaysOfMonth() {
        List<DayItem> daysList = new ArrayList<>();

        // Obtener el primer día del mes actual
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 1 es domingo, 2 es lunes, etc.
        int daysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Ajuste: Si el primer día del mes no es lunes, se deben añadir los días del mes anterior
        int offset = (firstDayOfWeek == Calendar.MONDAY) ? 0 : (firstDayOfWeek - Calendar.MONDAY + 7) % 7;

        // Obtener el calendario para el mes anterior
        Calendar previousMonthCalendar = (Calendar) calendar.clone();
        previousMonthCalendar.add(Calendar.MONTH, -1);
        int daysInPreviousMonth = previousMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Añadir los días del mes anterior (en gris oscuro)
        for (int i = offset - 1; i >= 0; i--) {
            daysList.add(new DayItem(String.valueOf(daysInPreviousMonth - i), false)); // falso indica que no es del mes actual
        }

        // Añadir los días del mes actual
        for (int day = 1; day <= daysInCurrentMonth; day++) {
            daysList.add(new DayItem(String.valueOf(day), true)); // verdadero indica que es del mes actual
        }

        // Comprobar cuántos días faltan para completar la última semana y agregar los días del mes siguiente
        int remainingDays = 42 - daysList.size(); // Asegurarse de que el total sea de 6 semanas (42 días en total)

        // Si hay menos de 42 días, añadir los días del mes siguiente
        for (int day = 1; day <= remainingDays; day++) {
            daysList.add(new DayItem(String.valueOf(day), false)); // falso indica que no es del mes actual
        }

        return daysList;
    }

    private List<String> generateDaysOfWeek() {
        List<String> days = new ArrayList<>();

        // Agregar los nombres de los días de la semana
        days.add("Do"); // Domingo
        days.add("Lu"); // Lunes
        days.add("Ma"); // Martes
        days.add("Mi"); // Miércoles
        days.add("Ju"); // Jueves
        days.add("Vi"); // Viernes
        days.add("Sa"); // Sábado

        return days;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reminderManager.close();
    }
}