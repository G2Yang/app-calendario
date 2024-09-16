package com.example.calendarioesposita.utils.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarioesposita.R;

import java.util.List;

public class DaysOfWeekAdapter extends RecyclerView.Adapter<DaysOfWeekAdapter.DayViewHolder> {

    private List<String> daysOfWeek;

    public DaysOfWeekAdapter(List<String> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout de cada ítem
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_of_week, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        // Enlazar el día correspondiente al TextView
        String day = daysOfWeek.get(position);
        holder.dayTextView.setText(day);
    }

    @Override
    public int getItemCount() {
        // Devolver el tamaño de la lista
        return daysOfWeek.size();
    }

    // ViewHolder interno para el RecyclerView
    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayTextView;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
        }
    }
}
