package net.larntech.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.larntech.R;
import net.larntech.common.Constantes;
import net.larntech.common.SharedPreferencesManager;
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

public class DetalleTareas extends AppCompatActivity {

    TextView placaText,vinText,colorText,clienteText,conceText,direcText;
    TextView marcaText;
    TextView modeloText;
    TecnicoTareas userResponse;
    Button button2;
    boolean estado;
    public static List<String> listaFlota = new ArrayList<>();
    public static HashMap<Integer,String> mapFlota=new HashMap<Integer,String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tareas);

        getSupportActionBar().setTitle("Reporte");

        getFlota();

        getTareaCompleta();

        button2 = (Button) findViewById(R.id.btnGrabarOperacion);

        //Alert Dialog
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(estado!=false){
                    new AlertDialog.Builder(DetalleTareas.this)
                            .setTitle("Editar Vehiculo")
                            .setMessage("¿Está seguro de querer editar el Vehiculo.?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(listaFlota.isEmpty()){
                                        System.out.println("LA FLOTA ESTA VACIA");
                                        Toast.makeText(DetalleTareas.this, "No se ha podido cargar los datos, Revise su conexion de internet", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(DetalleTareas.this, ListadoTareas.class));
                                    }else{
                                        startActivity(new Intent(DetalleTareas.this, RegistroVehiculo.class));
                                    }

                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }else {
                    new AlertDialog.Builder(DetalleTareas.this)
                            .setTitle("Iniciar Operación")
                            .setMessage("¿Está seguro de generar la operación.?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(listaFlota.isEmpty()){
                                        System.out.println("LA FLOTA ESTA VACIA");
                                        Toast.makeText(DetalleTareas.this, "No se ha podido cargar los datos, Revise su conexion de internet", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(DetalleTareas.this, ListadoTareas.class));
                                    }else{
                                        startActivity(new Intent(DetalleTareas.this, RegistroVehiculo.class));
                                    }
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            }
        });



        obtenerViews();

        llenarCasillas();


    }

    public void getTareaCompleta(){

        if(ListadoTareas.estado){

            int idTarea = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_TAREA));

            Call<TareaCompleta> tareaList = VehiculoClient.getVehiculoService().getTareaEditar(idTarea);

            tareaList.enqueue(new Callback<TareaCompleta>() {
                @Override
                public void onResponse(Call<TareaCompleta> call, Response<TareaCompleta> response) {


                    if(response.isSuccessful()){

                        TareaCompleta tarea  = response.body();
                        String nombreVehiculo = tarea.getNom_vehiculo();
                        String numCredito = tarea.getNum_credito();
                        String imei = tarea.getGps().getImeiGps();
                        String modeloGps = tarea.getModelo();
                        String telefSIM = tarea.getChip().getDes_chip();
                        String sutran = tarea.getFlag_sutran();
                        String ultDireccion = tarea.getUltima_direccion();
                        String ultTrans = tarea.getUltima_transmision();
                        int flagBloqueo = tarea.getFlag_bloqueo();
                        String idChip = tarea.getChip().getId_chip();
                        int idFlota = tarea.getId_flota();
                        int idMarca = tarea.getId_marca();
                        int idModelo = tarea.getId_modelo();
                        int idTipo = tarea.getId_tipo();
                        int idGps = tarea.getGps().getIdGps();

                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_NOM_VEH, nombreVehiculo);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_NUM_CRE, numCredito);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_IMEI_GPS, imei);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_MODEL_GPS, modeloGps);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_TELEFONO2, telefSIM);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_NUM_SUTRAN, sutran);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ULT_DIRECCION, ultDireccion);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ULT_TRANS, ultTrans);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_FLAG_BLOQUEO, String.valueOf(flagBloqueo));
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID_GPS, String.valueOf(idGps));
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID_CHIP, idChip);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID_MARCA, String.valueOf(idMarca));
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID_MODELO, String.valueOf(idModelo)) ;
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID_TIPO, String.valueOf(idTipo));
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID_FLOTA,String.valueOf(idFlota));

                        System.out.println("Nombre Vehiculo : " + nombreVehiculo);
                        System.out.println("Ultima direccion : " +  ultDireccion);
                        System.out.println("BLOQUEO :" + flagBloqueo);
                        System.out.println("ID GPS :" + flagBloqueo);
                        System.out.println("ID CHIP:" + idChip );
                        System.out.println("SUGRAN : " + sutran);
                        System.out.println("ID MARCA: " + idMarca);
                        System.out.println("ID TIPO: " + idTipo);
                        System.out.println("ID FLOTA : " + idFlota);

                    }else{

                        Toast.makeText(DetalleTareas.this, "Error al cargar los datos, Revise su conexión de datos móviles", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(DetalleTareas.this, ListadoTareas.class));

                    }

                }

                @Override
                public void onFailure(Call<TareaCompleta> call, Throwable t) {
                    Toast.makeText(DetalleTareas.this, "Error al cargar los datos, Revise su conexión de datos móviles", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(DetalleTareas.this, ListadoTareas.class));
                }
            });

        }




    }


    private void getFlota(){

        listaFlota.clear();

        for(int i=0; i<listaFlota.size(); i++){

            System.out.println("Inicio Flota: " + listaFlota.get(i));
        }

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


                }else{
                    Toast.makeText(DetalleTareas .this, "Algo fue mal, Intentelo de nuevo" , Toast.LENGTH_LONG).show();
                    Intent i = new Intent(DetalleTareas.this, ListadoTareas.class);
                    startActivity(i);
                }

            }

            @Override
            public void onFailure(Call<List<Flota>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                Toast.makeText(DetalleTareas .this, "Algo fue mal, Intentelo de nuevo" , Toast.LENGTH_LONG).show();
                Intent i = new Intent(DetalleTareas.this, ListadoTareas.class);
                startActivity(i);

            }
        });



    }

    private void obtenerViews(){

        clienteText = findViewById(R.id.cliente);
        placaText  = findViewById(R.id.placaView);
        vinText  = findViewById(R.id.vinView);
        colorText  = findViewById(R.id.colorView);
        conceText = findViewById(R.id.concecionario);
        direcText = findViewById(R.id.direccion);
        marcaText = findViewById(R.id.marcaVehView);
        modeloText = findViewById(R.id.modeloVehView);


    }

    private void llenarCasillas(){

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            userResponse = (TecnicoTareas) intent.getSerializableExtra("data");

            String placa = userResponse.getPlaca();
            String vin = userResponse.getNumero_vin();
            String color = userResponse.getColor();
            String cliente = userResponse.getCliente();
            String concecionario = userResponse.getConcecionario();
            String direccion = userResponse.getDireccion();
            String marca = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_DES_MARCA);
            String modelo = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_DES_MODELO);
            estado = userResponse.getEstado();

            if(estado){

                button2.setText("Editar Vehiculo");

            }

            placaText.setText(placa);
            vinText.setText(vin);
            colorText.setText(color);
            clienteText.setText(cliente);
            conceText.setText(concecionario);
            direcText.setText(direccion);
            marcaText.setText(marca);
            modeloText.setText(modelo);
            SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CLIENTE,cliente);


        }

    }


}