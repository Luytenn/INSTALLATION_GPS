package net.larntech.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.larntech.R;
import net.larntech.common.Constantes;
import net.larntech.common.SharedPreferencesManager;
import net.larntech.retrofit.client.AuthTareasClient;
import net.larntech.retrofit.request.RequestGrabarTarea;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroOperacion extends AppCompatActivity {

    Spinner spinnerPosicionGps;
    Spinner spinnerTipoTrabajo;
    TextView planText;
    TextView clienteText;
    TextView imeiText;
    Button btnOperacion;

    int TipoTrabajoPosicion;
    int PosicionGps;
    int idTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_operacion);
        getSupportActionBar().setTitle("Datos");

        events();

        llenarCampos();

        comboBoxPosicionGPS();

        comboBoxTipoTrabajo();


        grabarTarea();


    }

    private void llenarCampos(){

        String cliente = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_CLIENTE);
        String imei = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_IMEI_GPS);
        String plan = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_PLAN);

        if(ListadoTareas.estado){

            imeiText.setText(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_IMEI_GPS));

        }else{
            imeiText.setText(imei);
        }

        clienteText.setText(cliente);
        planText.setText(plan);


    }

    private void grabarTarea(){

        btnOperacion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                btnOperacion.setEnabled(false);
                int color = Color.parseColor("#5356fe");
                btnOperacion.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));

                RequestGrabarTarea grabaTarea = new RequestGrabarTarea();

                grabaTarea.setPosicion_gps(PosicionGps);
                grabaTarea.setTipo_trabajo(TipoTrabajoPosicion);

                Call<RequestGrabarTarea> grabarTarea = AuthTareasClient.getAuthTareasService().grabarTarea(grabaTarea,idTarea);

                grabarTarea.enqueue(new Callback<RequestGrabarTarea>() {
                    @Override
                    public void onResponse(Call<RequestGrabarTarea> call, Response<RequestGrabarTarea> response) {

                        if(response.isSuccessful()){

                            btnOperacion.setEnabled(true);
                            int color = Color.parseColor("#536DFE");
                            btnOperacion.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));

                            Toast.makeText(RegistroOperacion.this, "Se Registro Correctamente", Toast.LENGTH_SHORT).show();

                            Intent intnt = new Intent(getApplicationContext(), ListadoTareas.class);
                            startActivity(intnt);


                        }else{

                            btnOperacion.setEnabled(true);
                            int color = Color.parseColor("#536DFE");
                            btnOperacion.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                            Toast.makeText(RegistroOperacion .this, "Algo fue mal, revise sus datos de acceso, o la red de sus datos móviles" , Toast.LENGTH_SHORT).show();


                        }

                    }

                    @Override
                    public void onFailure(Call<RequestGrabarTarea> call, Throwable t) {
                        Log.e("failure", t.getLocalizedMessage());
                        btnOperacion.setEnabled(true);
                        int color = Color.parseColor("#536DFE");
                        btnOperacion.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                        Toast.makeText(RegistroOperacion .this, "Algo fue mal, revise sus datos de acceso, o la red de sus datos móviles" , Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });


    }

    private void comboBoxTipoTrabajo(){

        List<String> tipoTrabajoList = new ArrayList<>();
        tipoTrabajoList.add(0, "INSTALACION GPS");
        tipoTrabajoList.add("MANTENIMIENTO GPS");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,tipoTrabajoList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTipoTrabajo.setAdapter(dataAdapter);


        spinnerTipoTrabajo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int posicion =parent.getSelectedItemPosition();

                if(posicion == 0){
                    TipoTrabajoPosicion = 1;
                }else{
                    TipoTrabajoPosicion = 2;
                }

                System.out.println("TIPO TRABAJO POSICION:  " + TipoTrabajoPosicion );

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void comboBoxPosicionGPS(){

        List<String> posicionList = new ArrayList<>();
        posicionList.add(0, "Detrás del tablero");
        posicionList.add("Detrás de la radio");
        posicionList.add("Copiloto");
        posicionList.add("Cinturón, palanca de cambios y asiento");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,posicionList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPosicionGps.setAdapter(dataAdapter);


        spinnerPosicionGps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int posicion =parent.getSelectedItemPosition();


                switch (posicion){
                    case 0:
                        PosicionGps = 1;
                        break;
                    case 1:
                        PosicionGps = 2;
                        break;
                    case 2:
                        PosicionGps = 3;
                        break;
                    default:
                        PosicionGps = 4;
                }


                System.out.println("POSICION DEL GPS:   " + PosicionGps);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void events(){

        spinnerPosicionGps = findViewById(R.id.spinnerPosicionGps);
        spinnerTipoTrabajo = findViewById(R.id.spinnerTipoTrabajo);
        planText = findViewById(R.id.planView);
        clienteText = findViewById(R.id.placaView);
        imeiText = findViewById(R.id.colorView);
        btnOperacion = findViewById(R.id.btnGrabarOperacion);

        idTarea = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_TAREA));

    }


}