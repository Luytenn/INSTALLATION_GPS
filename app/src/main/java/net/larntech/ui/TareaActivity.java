package net.larntech.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.larntech.R;
import net.larntech.common.Constantes;
import net.larntech.common.LoadingDialog;
import net.larntech.common.SharedPreferencesManager;
import net.larntech.retrofit.client.AuthTareasClient;
import net.larntech.retrofit.TareasAdapter;
import net.larntech.retrofit.client.FlotaClient;
import net.larntech.retrofit.response.Flota;
import net.larntech.retrofit.response.TecnicoTareas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareaActivity extends AppCompatActivity implements TareasAdapter.ClickedItem {

    LoadingDialog loadingDialog = new LoadingDialog(TareaActivity.this);
    Toolbar toolbar;
    RecyclerView recyclerView;
    TareasAdapter tareasAdapter = new TareasAdapter();
    TextView username;
    public static List<String> listaFlota = new ArrayList<>();
    public static HashMap<Integer,String> mapFlota=new HashMap<Integer,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);




        getSupportActionBar().hide();

        getFlota();

        username =  (TextView)findViewById(R.id.usuarioView);

        username.setText(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USERNAME));


        toolbar = findViewById(R.id.toolbar);

        loadingDialog.startLoadingDialog();

        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        tareasAdapter = new TareasAdapter(this::ClickedUser);

        getAllUsers();




    }

    private void getFlota(){

        Call<List<Flota>> flotaList = FlotaClient.getFlotaService().getFlota();
        flotaList.enqueue(new Callback<List<Flota>>() {
            @Override
            public void onResponse(Call<List<Flota>> call, Response<List<Flota>> response) {
                if(response.isSuccessful()){

                    System.out.println("SUCCESS DROPDOWN FLOTA");
                    for(Flota temp: response.body()){

                        String descripcion = temp.getDesFlota();
                        int idFlota = temp.getIdFlota();
                        listaFlota.add(temp.getDesFlota());
                        mapFlota.put(idFlota,descripcion);


                        System.out.println("DESCRIPCION FLOTA : " +  descripcion);
                        System.out.println("ID FLOTA : " + idFlota);



                    }


                }
            }

            @Override
            public void onFailure(Call<List<Flota>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });



    }

    public void getAllUsers() {

        Call<List<TecnicoTareas>> tareaList = AuthTareasClient.getAuthTareasService().getTareas();

        tareaList.enqueue(new Callback<List<TecnicoTareas>>() {
            @Override
            public void onResponse(Call<List<TecnicoTareas>> call, Response<List<TecnicoTareas>> response) {

                if (response.isSuccessful()) {
                    Log.e("success", response.body().toString());
                    List<TecnicoTareas> tareas =  response.body();
                    for(TecnicoTareas temp: tareas){

                        System.out.println("ESTADO : " +temp.getEstado());


                    }
                    tareasAdapter.setData(tareas);
                    recyclerView.setAdapter(tareasAdapter);

                    loadingDialog.dismissDialog();

                }
            }

            @Override
            public void onFailure(Call<List<TecnicoTareas>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                Toast.makeText(TareaActivity.this, "No se ha posido listar las tardes, consulta mas tarde", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
            }
        });

    }

    @Override
    public void ClickedUser(TecnicoTareas userResponse) {

        startActivity(new Intent(this,DetalleTareas.class).putExtra("data", userResponse));
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_TELEFONO,userResponse.getCelular());
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID_TAREA,String.valueOf(userResponse.getId()));




    }
}