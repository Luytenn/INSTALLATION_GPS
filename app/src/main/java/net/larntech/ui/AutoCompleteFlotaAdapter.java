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
import net.larntech.retrofit.response.CountryItem;
import net.larntech.retrofit.response.Flota;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteFlotaAdapter extends ArrayAdapter<Flota> {
    private List<Flota> flotaListFull;
    public AutoCompleteFlotaAdapter(@NonNull Context context, @NonNull List<Flota> flotaList) {
        super(context, 0, flotaList);
        flotaListFull = new ArrayList<>(flotaList);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return flotaFilter;
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

        Flota flota = getItem(position);
        if (flota != null) {
            textViewName.setText(flota.getDesFlota());

        }
        return convertView;
    }
    private Filter flotaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Flota> suggestions = new ArrayList<>();


            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(flotaListFull);
            } else {

                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Flota item : flotaListFull) {
                    if (item.getDesFlota().toLowerCase().contains(filterPattern)) {
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
            return ((Flota) resultValue).getDesFlota();
        }
    };
}