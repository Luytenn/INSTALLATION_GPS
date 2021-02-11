package net.larntech.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.larntech.R;
import net.larntech.retrofit.response.GPSVehiculo;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteChipAdapter extends ArrayAdapter<GPSVehiculo> implements Filterable {
    private List<GPSVehiculo> countryListFull;
    public AutoCompleteChipAdapter(@NonNull Context context, @NonNull List<GPSVehiculo> countryList) {
        super(context, 0, countryList);
        countryListFull = new ArrayList<>(countryList);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
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
        GPSVehiculo chip = getItem(position);
        if (chip != null) {
            textViewName.setText(chip.getImeiGps());
        }
        return convertView;
    }
    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            List<GPSVehiculo> suggestions = new ArrayList<>();


            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(countryListFull);
            } else {
                String filterPattern = constraint.toString();
                for (GPSVehiculo item : countryListFull) {

                    if (item.getImeiGps().contains(filterPattern)) {
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
            return ((GPSVehiculo) resultValue).getImeiGps();
        }
    };
}
