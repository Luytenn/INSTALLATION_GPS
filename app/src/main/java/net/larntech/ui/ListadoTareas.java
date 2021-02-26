package net.larntech.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.larntech.R;
import net.larntech.common.Constantes;
import net.larntech.common.LoadingDialog;
import net.larntech.common.SharedPreferencesManager;
import net.larntech.retrofit.client.AuthTareasClient;
import net.larntech.retrofit.TareasAdapter;
import net.larntech.retrofit.client.FlotaClient;
import net.larntech.retrofit.client.VehiculoClient;
import net.larntech.retrofit.response.Flota;
import net.larntech.retrofit.response.TareaCompleta;
import net.larntech.retrofit.response.TecnicoTareas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoTareas extends AppCompatActivity implements TareasAdapter.ClickedItem {

    LoadingDialog loadingDialog = new LoadingDialog(ListadoTareas.this);
    Toolbar toolbar;
    RecyclerView recyclerView;
    TareasAdapter tareasAdapter = new TareasAdapter();
    TextView username;
    Button buttonSalir;
    public static boolean estado = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);


        getSupportActionBar().hide();



        events();

        getTareas();

        cerrarSesion();


    }


    public void cerrarSesion(){

        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clearing all data from Shared Preferences
                SharedPreferencesManager.getSharedPreferences().edit().clear();
                SharedPreferencesManager.getSharedPreferences().edit().commit();

                // After logout redirect user to Loing Activity
                Intent i = new Intent(ListadoTareas.this, Login.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Login Activity
                startActivity(i);
            }
        });



    }

    private void events(){

        username =  (TextView)findViewById(R.id.usuarioView);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        buttonSalir = findViewById(R.id.btnSalir);

        username.setText(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USERNAME));

        loadingDialog.startLoadingDialog();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        tareasAdapter = new TareasAdapter(this::ClickedUser);


    }




    public void getTareas() {

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

                }else{

                    Toast.makeText(ListadoTareas.this, "No se ha podido listar las tareas, intentelo de nuevo", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ListadoTareas.this, Login.class);
                    startActivity(i);

                }
            }

            @Override
            public void onFailure(Call<List<TecnicoTareas>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                Toast.makeText(ListadoTareas.this, "No se ha podido listar las tareas, intentelo de nuevo", Toast.LENGTH_LONG).show();
                loadingDialog.dismissDialog();
                Intent i = new Intent(ListadoTareas.this, Login.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void ClickedUser(TecnicoTareas userResponse) {

        startActivity(new Intent(this,DetalleTareas.class).putExtra("data", userResponse));
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_TELEFONO,userResponse.getCelular());
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID_TAREA,String.valueOf(userResponse.getId()));
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_COLOR, userResponse.getColor());
        estado = userResponse.getEstado();
        System.out.println("ESTAO DE LA TAREA : " + estado);

    }
}