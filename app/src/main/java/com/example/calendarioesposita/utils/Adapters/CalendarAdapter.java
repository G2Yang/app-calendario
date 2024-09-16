package com.example.calendarioesposita.utils.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarioesposita.R;
import com.example.calendarioesposita.utils.Classes.DayItem;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private List<DayItem> daysList;

    public CalendarAdapter(List<DayItem> daysList) {
        this.daysList = daysList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_day_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayItem dayItem = daysList.get(position);
        holder.dayText.setText(dayItem.getDay());

        // Cambiar el color dependiendo de si el día es del mes actual o no
        if (dayItem.isCurrentMonth()) {
            holder.dayText.setBackgroundColor(Color.LTGRAY); // Color para los días del mes actual
        } else {
            holder.dayText.setBackgroundColor(Color.GRAY); // Color gris oscuro para los días del mes anterior o siguiente
        }
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    // Método para actualizar la lista de días y notificar al adaptador
    public void updateDays(List<DayItem> newDays) {
        this.daysList = newDays;
        notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.dayText);
        }
    }
}
