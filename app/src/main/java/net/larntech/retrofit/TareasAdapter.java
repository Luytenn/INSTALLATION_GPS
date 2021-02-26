package net.larntech.retrofit;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.larntech.R;
import net.larntech.common.Constantes;
import net.larntech.common.SharedPreferencesManager;
import net.larntech.retrofit.response.TecnicoTareas;

import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareasAdapterVH>{

    private List<TecnicoTareas> userResponseList;
    private Context context;
    private ClickedItem clickedItem;

    public TareasAdapter(){

    }

    public TareasAdapter(ClickedItem clickedItem) {
        System.out.println("Ingreso metodo");
        this.clickedItem = clickedItem;
    }

    public void setData(List<TecnicoTareas> userResponseList) {
        System.out.println("Ingreso" + userResponseList);
        this.userResponseList = userResponseList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TareasAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TareasAdapter.TareasAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_users,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TareasAdapterVH holder, int position) {
        TecnicoTareas userResponse = userResponseList.get(position);

        holder.setIsRecyclable(false);


        boolean estado = userResponse.getEstado();
        String placa = userResponse.getPlaca();
        String vin = userResponse.getNumero_vin();
        String conceci = userResponse.getConcecionario();
        String direccion = userResponse.getDireccion();
        String marca = userResponse.getMarca();
        String modelo = userResponse.getModelo();
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_DES_MARCA,marca);
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_DES_MODELO,modelo);

        holder.placa.setText(placa);
        holder.vin.setText(vin);
        holder.conceci.setText(conceci);
        holder.direccion.setText(direccion);
        holder.marca.setText(marca);
        holder.modelo.setText(modelo);

        if(estado){
            //holder.itemRecycler.setBackgroundColor(Color.parseColor("#96dfff"));
            holder.itemRecycler.setBackgroundColor(Color.parseColor("#66177CCE"));

            System.out.println("Boolean Estado : " + estado);
        }

        holder.imageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedUser(userResponse);
            }
        });

    }

    public interface ClickedItem{
        public void ClickedUser(TecnicoTareas userResponse);
    }



    @Override
    public int getItemCount() {
        return userResponseList.size();
    }

    public class TareasAdapterVH extends RecyclerView.ViewHolder {


        TextView placa;
        TextView vin;
        TextView conceci;
        TextView direccion;
        ImageView imageMore;
        TextView marca;
        TextView modelo;
        RelativeLayout itemRecycler;




        public TareasAdapterVH(@NonNull View itemView) {
            super(itemView);

            itemRecycler = itemView.findViewById(R.id.ItemRecycler);
            placa = itemView.findViewById(R.id.placaView);
            vin = itemView.findViewById(R.id.vinView);
            conceci = itemView.findViewById(R.id.concecionario);
            direccion = itemView.findViewById(R.id.direccion);
            marca = itemView.findViewById(R.id.marcaView);
            modelo = itemView.findViewById(R.id.modeloText);
            imageMore = itemView.findViewById(R.id.imageMore);




        }
    }
}
