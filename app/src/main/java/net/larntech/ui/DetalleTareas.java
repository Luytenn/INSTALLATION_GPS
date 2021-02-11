package net.larntech.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.larntech.R;
import net.larntech.common.Constantes;
import net.larntech.common.SharedPreferencesManager;
import net.larntech.retrofit.response.TecnicoTareas;

public class DetalleTareas extends AppCompatActivity {

    TextView placaText,vinText,colorText,clienteText,tipoSoltText,conceText,direcText;
    TecnicoTareas userResponse;
    Button button2;
    boolean estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tareas);

        getSupportActionBar().setTitle("Reporte");
        

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
                                    startActivity(new Intent(DetalleTareas.this,DetalleGps.class));
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
                                    startActivity(new Intent(DetalleTareas.this, DetalleGps.class));
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

    private void obtenerViews(){

        clienteText = findViewById(R.id.cliente);
        placaText  = findViewById(R.id.clienteView);
        vinText  = findViewById(R.id.tipoSolView);
        colorText  = findViewById(R.id.imeiView);
        tipoSoltText = findViewById(R.id.tipoSolicitud);
        conceText = findViewById(R.id.concecionario);
        direcText = findViewById(R.id.direccion);

    }

    private void llenarCasillas(){

        Intent intent = getIntent();
        if(intent.getExtras() !=null){
            userResponse = (TecnicoTareas) intent.getSerializableExtra("data");

            String placa = userResponse.getPlaca();
            String vin = userResponse.getNumero_vin();
            String color = userResponse.getColor();
            String cliente = userResponse.getCliente();
            String tipoSol = userResponse.getTipoSolicitud();
            String concecionario = userResponse.getConcecionario();
            String direccion = userResponse.getDireccion();
            estado = userResponse.getEstado();

            if(estado){

                button2.setText("Editar Vehiculo");

            }

            placaText.setText(placa);
            vinText.setText(vin);
            colorText.setText(color);
            clienteText.setText(cliente);
            tipoSoltText.setText(tipoSol);
            conceText.setText(concecionario);
            direcText.setText(direccion);

            SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CLIENTE,cliente);


        }

    }


}