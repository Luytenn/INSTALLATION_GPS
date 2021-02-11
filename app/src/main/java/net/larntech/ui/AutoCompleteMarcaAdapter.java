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
import net.larntech.retrofit.response.Marca;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteMarcaAdapter extends ArrayAdapter<Marca> {
    private List<Marca> marcaListFull;
    public AutoCompleteMarcaAdapter(@NonNull Context context, @NonNull List<Marca> marcaList) {
        super(context, 0, marcaList);
        marcaListFull = new ArrayList<>(marcaList);
        System.out.println("INSIDE AUTOCOMPLETE CLASS: "+ marcaList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return marcaFilter;
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
        Marca marca = getItem(position);
        if (marca != null) {
            textViewName.setText(marca.getDesMarca());
        }
        return convertView;
    }

    private Filter marcaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            List<Marca> suggestions = new ArrayList<>();


            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(marcaListFull);
            } else {
                System.out.println("ENTOR ELSE "+ marcaListFull);
                String filterPattern = constraint.toString();
                for (Marca item : marcaListFull) {

                    System.out.println("CELULAR INSIDE FOR " + item.getDesMarca());
                    if (item.getDesMarca().contains(filterPattern)) {
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
            return ((Marca) resultValue).getDesMarca();
        }
    };

}