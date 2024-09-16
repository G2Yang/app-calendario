package com.example.calendarioesposita.utils.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarioesposita.Helpers.ReminderDatabaseHelper;
import com.example.calendarioesposita.R;

import java.util.Calendar;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private Context context;
    private Cursor cursor;

    public ReminderAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            // Obteniendo los datos del cursor
            String description = cursor.getString(cursor.getColumnIndexOrThrow(ReminderDatabaseHelper.COLUMN_DESCRIPTION));
            long dateMillis = cursor.getLong(cursor.getColumnIndexOrThrow(ReminderDatabaseHelper.COLUMN_DATE));

            // Formatear la fecha para mostrarla
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateMillis);
            String dateText = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm", calendar).toString();

            // Configurando los datos en el ViewHolder
            holder.descriptionTextView.setText(description);
            holder.dateTextView.setText(dateText);
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ReminderViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView dateTextView;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
