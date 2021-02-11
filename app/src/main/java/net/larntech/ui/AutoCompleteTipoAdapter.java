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
import net.larntech.retrofit.response.TipoVehiculo;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteTipoAdapter extends ArrayAdapter<TipoVehiculo> {
    private List<TipoVehiculo> tipoVehiculoListFull;

    public AutoCompleteTipoAdapter(@NonNull Context context, @NonNull List<TipoVehiculo> tipoVehiculoList) {
        super(context, 0, tipoVehiculoList);
        tipoVehiculoListFull = new ArrayList<>(tipoVehiculoList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return tipoVehiculoFilter;
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
        TipoVehiculo tipoVehiculo = getItem(position);
        if (tipoVehiculo != null) {
            textViewName.setText(tipoVehiculo.getDesTipo());
        }
        return convertView;
    }

    private Filter tipoVehiculoFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            List<TipoVehiculo> suggestions = new ArrayList<>();


            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(tipoVehiculoListFull);
            } else {
                String filterPattern = constraint.toString();
                for (TipoVehiculo item : tipoVehiculoListFull) {
                    if (item.getDesTipo().contains(filterPattern)) {
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
            return ((TipoVehiculo) resultValue).getDesTipo();
        }
    };

}