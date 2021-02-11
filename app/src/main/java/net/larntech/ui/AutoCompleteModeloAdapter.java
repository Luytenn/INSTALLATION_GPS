package net.larntech.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.larntech.R;
import net.larntech.retrofit.response.ModeloVehiculo;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteModeloAdapter extends ArrayAdapter<ModeloVehiculo> {
    private List<ModeloVehiculo> modeloListFull;

    public AutoCompleteModeloAdapter(@NonNull Context context, @NonNull List<ModeloVehiculo> modeloList) {
        super(context, 0, modeloList);
        modeloListFull = new ArrayList<>(modeloList);
        System.out.println("INSIDE AUTOCOMPLETE CLASS: " + modeloListFull);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return modeloFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.telefono_autocomplete_row, parent, false
            );
        }
        TextView textViewName = convertView.findViewById(R.id.telefonoRow);
        ModeloVehiculo modelo = getItem(position);
        if (modelo != null) {
            textViewName.setText(modelo.getDesModelo());
        }
        return convertView;
    }

    private Filter modeloFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            List<ModeloVehiculo> suggestions = new ArrayList<>();


            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(modeloListFull);
            } else {
                System.out.println("ENTOR ELSE " + modeloListFull);
                String filterPattern = constraint.toString();
                for (ModeloVehiculo item : modeloListFull) {

                    System.out.println("CELULAR INSIDE FOR " + item.getDesModelo());
                    if (item.getDesModelo().contains(filterPattern)) {
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
            return ((ModeloVehiculo) resultValue).getDesModelo();
        }
    };

}