package net.larntech.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.larntech.R;
import net.larntech.retrofit.response.ColorVehiculo;
import net.larntech.retrofit.response.Marca;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteColorAdapter extends ArrayAdapter<ColorVehiculo> {
    private List<ColorVehiculo> colorListFull;
    public AutoCompleteColorAdapter(@NonNull Context context, @NonNull List<ColorVehiculo> colorList) {
        super(context, 0, colorList);
        colorListFull = new ArrayList<>(colorList);
        System.out.println("INSIDE AUTOCOMPLETE CLASS: "+ colorListFull);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return colorFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.marca_autocomplete_row, parent, false
            );
        }
        TextView textViewName = convertView.findViewById(R.id.marcaRow);
        ColorVehiculo color = getItem(position);
        if (color != null) {
            textViewName.setText(color.getDesColor());
        }
        return convertView;
    }

    private Filter colorFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            List<ColorVehiculo> suggestions = new ArrayList<>();


            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(colorListFull);
            } else {
                String filterPattern = constraint.toString();
                for (ColorVehiculo item : colorListFull) {

                    System.out.println("CELULAR INSIDE FOR " + item.getDesColor());
                    if (item.getDesColor().contains(filterPattern)) {
                        suggestions.add(item);
                    }

                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ColorVehiculo) resultValue).getDesColor();
        }
    };

}
