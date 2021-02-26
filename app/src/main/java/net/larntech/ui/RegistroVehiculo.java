package net.larntech.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import net.larntech.R;
import net.larntech.common.Constantes;
import net.larntech.common.DataBaseHelper;
import net.larntech.common.LoadingDialog;
import net.larntech.common.SharedPreferencesManager;
import net.larntech.retrofit.client.AuthChipClient;
import net.larntech.retrofit.client.MarcaClient;
import net.larntech.retrofit.client.ModeloClient;
import net.larntech.retrofit.client.PlanClient;
import net.larntech.retrofit.client.TipoVehiculoClient;
import net.larntech.retrofit.client.VehiculoClient;
import net.larntech.retrofit.request.RequestVehiculo;
import net.larntech.retrofit.response.Flota;
import net.larntech.retrofit.response.GPSVehiculo;
import net.larntech.retrofit.response.Marca;
import net.larntech.retrofit.response.ModeloVehiculo;
import net.larntech.retrofit.response.TipoVehiculo;
import net.larntech.retrofit.response.Ubicacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroVehiculo extends AppCompatActivity {

    LoadingDialog loadingDialog = new LoadingDialog(RegistroVehiculo.this);
    AwesomeValidation awesomeValidation;
    ImageView btnLocal;
    Button btnEnviar;
    Button btnSiguiente;
    Spinner spinnerCommand;
    String imeiGps;
    TextView modeloGps;
    TextView nombreText;
    TextView estadoText;
    TextView numCreditoText;
    Spinner spinnerSutran;
    Spinner spinnerSelectOperacion;
    TextView direccionText;
    TextView bloqueText;
    TextView ultimaTransText;
    Spinner spinnerMarca;
    AutoCompleteTextView telefonoText;
    AutoCompleteTextView imeiText;
    Spinner spinnerTipoVeh;
    Spinner spinnerFlota;
    Spinner spinnerModelo;
    List<GPSVehiculo> chipPossible = new ArrayList<>();
    List<GPSVehiculo> listaTelefono = new ArrayList<>();
    List<String> listaMarca = new ArrayList<>();
    List<String> listaModelo = new ArrayList<>();
    List<RequestVehiculo> listaVehiculo = new ArrayList<>();
    List<String> listaTipo = new ArrayList<>();
    Ubicacion ubicacion = new Ubicacion();
    RequestVehiculo comando = new RequestVehiculo();
    HashMap<Integer,String> mapMarca = new HashMap<>();
    HashMap<Integer,String> mapTipo = new HashMap<>();
    HashMap<Integer,String> mapModelo = new HashMap<>();
    HashMap<Integer,String> mapMarcaPosition = new HashMap<>();
    String telefono;
    String marca="";
    String modelo;
    String flota;
    String mensaje;
    int estado;
    int idMarca;
    int idTipo;
    int idChip;
    int idModelo;
    int idTarea;
    int idFlota;
    int vehiculoId;
    int idGps;
    int position;
    int positionTipo;
    int positionFlota;
    int positionModelo;
    Integer idVehiculo;
    String tipoVehiculo;
    String nombreVehiculo;
    String estVehiculo;
    String numCredito;
    String flagSutran;
    String direccion;
    int selectOperacion;
    int flagBloqueo;
    boolean imeiSelected = false;
    boolean telefSelected = false;
    String ultimaTrans;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_gps);
        getSupportActionBar().setTitle("Datos");



        events();

        comboBoxSutran();

        llenarCasillas();

        comboBoxFlota();

        comboBoxComando();

        dropdownIMEI();

        dropdownTelefono();

        llenarComboBoxMarca();

        llenarComboBoxModelo();

        llenarComboBoxTipoVehiculo();


        registrarVehiculoGPS();

        enviarComando();





    }

    private void getPlan(){

        idTarea = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_TAREA));
        System.out.println("ID DE LA TAREA : " + idTarea);
        Call<String> obtenerPlan = PlanClient.getPlanService().obtenerPlan(idTarea);

        obtenerPlan.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){

                    System.out.println("PROCESO SATISFACTORIO");

                    String Plan = response.body();

                    System.out.println("PLAN  : " + Plan);

                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_PLAN, Plan);

                     //retrieve data if user only write de imei without selecting one
                    //getTelefono();


                }else{
                    loadingDialog.dismissDialog();
                    Toast.makeText(RegistroVehiculo.this, "Algo fue mal, revise la red de sus datos móviles", Toast.LENGTH_LONG).show();
                    return;
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                loadingDialog.dismissDialog();
                Toast.makeText(RegistroVehiculo.this, "Algo fue mal, revise la red de sus datos móviles", Toast.LENGTH_LONG).show();
                return;
            }
        });


    }

    private void comboBoxComando(){

        List<String> comandoList = new ArrayList<>();
        comandoList.add(0, "DESBLOQUEO");
        comandoList.add("BLOQUEO");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(RegistroVehiculo.this, android.R.layout.simple_spinner_item,comandoList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCommand.setAdapter(dataAdapter);

        spinnerCommand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int posicion =parent.getSelectedItemPosition();

                if(posicion == 0){
                    flagBloqueo =posicion;
                }else{
                    flagBloqueo  = posicion;
                }

                System.out.println("BLOQUEO VALOR " + flagBloqueo);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



    private void comboBoxSutran(){

        //Setting the ArrayAdapter data on the Spinner

        List<String> sutranList = new ArrayList<>();
        sutranList.add(0, "NO");
        sutranList.add("SI");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,sutranList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSutran.setAdapter(dataAdapter);

        spinnerSutran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int posicion =parent.getSelectedItemPosition();

                if(posicion == 0){
                    flagSutran ="N";
                }else{
                    flagSutran  = "S";
                }

                System.out.println("SUTRAN VALOR " + flagSutran);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void comboBoxFlota(){

        if(ListadoTareas.estado){
            idFlota = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_FLOTA));

            int incrementable = 0;

            for(Map.Entry<Integer, String> entry : DetalleTareas.mapFlota.entrySet()){

                if(idFlota == entry.getKey()){

                    positionFlota = incrementable;
                    System.out.println("Posicion Flota found it : " + positionFlota);

                }

                incrementable++;

            }
        }


        ArrayAdapter<Flota> dataAdapter;
        dataAdapter = new ArrayAdapter(RegistroVehiculo.this, android.R.layout.simple_spinner_item, (List) DetalleTareas.listaFlota);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFlota.setAdapter(dataAdapter);

        if(ListadoTareas.estado){
            spinnerFlota.setSelection(positionFlota);
        }


        spinnerFlota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = spinnerFlota.getSelectedItem().toString();

                for(Map.Entry m : DetalleTareas.mapFlota.entrySet()){

                    if(m.getValue().equals(text)){

                        idFlota = (int) m.getKey();

                    }

                }

                System.out.println(" FLOTA SELECCIONADA " + text);
                System.out.println(" ID FLOTA : " + idFlota);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void dropdownIMEI() {

        imeiText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //retrieveData(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

                getGps(); //this will call your method every time the user stops typing, if you want to call it for each letter, call it in onTextChanged
                imeiSelected = false;
            }
        });


        imeiText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                imeiSelected = true;

                GPSVehiculo imei = (GPSVehiculo)parent.getItemAtPosition(position);
                idGps  = imei.getIdGps();
                imeiGps = imei.getImeiGps();
                String modelo = imei.getDes_tipo();
                modeloGps.setText(modelo);

                System.out.println("ID GPS REAL : " + imei.getImeiGps());
                System.out.println("IMEI DEL GPS : " + imei.getImeiGps());

                SharedPreferencesManager.setSomeStringValue(Constantes.PREF_IMEI_GPS, imeiGps);


            }
        });


    }


    private void getGps() {


        imeiGps = imeiText.getText().toString();


        System.out.println("IMEI DEL GPS : " + imeiGps);

        Call<List<GPSVehiculo>> chipList = AuthChipClient.getChipService().getChips(imeiGps);
        chipList.enqueue(new Callback<List<GPSVehiculo>>() {
            @Override
            public void onResponse(Call<List<GPSVehiculo>> call, Response<List<GPSVehiculo>> response) {


                if (response.isSuccessful()) {
                    Log.e("success", response.body().toString());
                    chipPossible = response.body();
                    for(GPSVehiculo GPSVehiculo : chipPossible){
                        System.out.println("LISTANDO : " + GPSVehiculo.getImeiGps());
                    }


                    AutoCompleteChipAdapter adapter = new AutoCompleteChipAdapter(RegistroVehiculo.this, chipPossible);
                    imeiText.setAdapter(adapter);



                }else{

                    System.out.println("No ha podida listar el los numeros de imei");


                }
            }

            @Override
            public void onFailure(Call<List<GPSVehiculo>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });

    }


    private void obtnerVehiculoxId(){

        Call<Integer> obtenerVehiculoId= VehiculoClient.getVehiculoService().obtenerVehiculoxId(idTarea);
        System.out.println("METODO OBTENER VEHICULO X ID, ID TAREA  :" + idTarea);

        obtenerVehiculoId.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {


                if(response.isSuccessful()){

                    idVehiculo = new Integer(response.body());
                    vehiculoId = idVehiculo.intValue();
                    System.out.println("ID VEHICULO 1 : " + vehiculoId);


                    Timer m = new Timer();

                    m.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            long t= System.currentTimeMillis();
                            long end = t+60000;
                            while(System.currentTimeMillis() < end) {
                                obtenerUltimaUbicacion();

                                System.out.println("ESPERANDO DATOS CADA 3 SEGUNDOS");

                                if(ubicacion.getUltima_ubicacion()!=null){

                                    System.out.println("La direccion del vehiculo existe");
                                    loadingDialog.dismissDialog();

                                    break;
                                }

                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }




                            loadingDialog.dismissDialog();


                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    ultimaTransText.setText(ubicacion.getUltima_transmision());
                                    direccionText.setText(ubicacion.getUltima_ubicacion());
                                    bloqueText.setText(ubicacion.getFlag_bloqueado());
                                    if(ubicacion.getUltima_ubicacion()!=null){
                                        btnSiguiente.setEnabled(true);
                                        btnEnviar.setEnabled(true);
                                    }


                                }
                            });

                        }
                    },1000);

                }else{
                    Toast.makeText(RegistroVehiculo.this, "Algo fue mal, Intentelo de nuevo", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismissDialog();
                }


            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("failure get VehxID", t.getLocalizedMessage());
                Toast.makeText(RegistroVehiculo.this, "Algo fue mal, Intentelo de nuevo", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();

            }
        });



    }

    private void obtenerUltimaUbicacion() {

        System.out.println("ID DEL VEHICULO 2:" + vehiculoId);
        idTarea = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_TAREA));
        Call<Ubicacion> call = VehiculoClient.getVehiculoService().obtenerUbicacion(vehiculoId);
        call.enqueue(new Callback<Ubicacion>() {
            @Override
            public void onResponse(Call<Ubicacion> call, Response<Ubicacion> response) {

                if (response.isSuccessful()) {


                    ubicacion  = response.body();
                    System.out.println("ULTIMA TRANSMISION: " + ubicacion.getUltima_transmision());
                    System.out.println("SUCESS, OBTENER ULTIMA TRANSMISION");

                }
            }

            @Override
            public void onFailure(Call<Ubicacion> call, Throwable t) {
                Log.e("failure Obtener UlTrans", t.getLocalizedMessage());
            }
        });





    }

    private void registrarVehiculoGPS(){

        btnLocal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                nombreVehiculo = nombreText.getText().toString();
                numCredito = numCreditoText.getText().toString();



                if(awesomeValidation.validate()==true && imeiSelected==true && telefSelected == true) {

                    getPlan();

                    idTarea = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_TAREA));
                    System.out.println("ID TAREA " + idTarea);
                    System.out.println("ID MODELO: "+ idModelo + "  ID_MARCA: " + idMarca+" ID_CHIP: " + idChip+" ID_GPS: "+ idGps+
                            "  ID TIPO " + idTipo+ "  NomVehiculo: " + nombreVehiculo + "  NumCredito  " +
                            numCredito + "  FlagSutran : " + flagSutran +  "ID FLOTA: "+ idFlota);

                    RequestVehiculo vehiculo = new RequestVehiculo();

                    vehiculo.setIdModelo(idModelo);
                    vehiculo.setIdMarca(idMarca);
                    vehiculo.setIdChip(idChip);
                    vehiculo.setIdGps(idGps);
                    vehiculo.setIdTipo(idTipo);
                    vehiculo.setNomVehiculo(nombreVehiculo);
                    vehiculo.setNumCredito(numCredito);
                    vehiculo.setFlagSutran(flagSutran);
                    vehiculo.setId_flota(idFlota);


                    loadingDialog.startLoadingDialog();


                    Call<RequestVehiculo> call = VehiculoClient.getVehiculoService().registrarVehiculo(vehiculo,idTarea);

                    call.enqueue(new Callback<RequestVehiculo>() {
                        @Override
                        public void onResponse(Call<RequestVehiculo> call, Response<RequestVehiculo> response) {

                            if (response.isSuccessful()) {

                                obtnerVehiculoxId();

                                System.out.println("SUCESS");

                            }else{
                                loadingDialog.dismissDialog();
                                Toast.makeText(RegistroVehiculo.this, "Algo fue mal, intentelo de nuevo", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<RequestVehiculo> call, Throwable t) {
                            Log.e("failure Vehiculo", t.getLocalizedMessage());
                            loadingDialog.dismissDialog();
                            Toast.makeText(RegistroVehiculo.this, "Algo fue mal, revise la red de sus datos móviles" , Toast.LENGTH_SHORT).show();

                        }
                    });


                }else {
                    Toast.makeText(RegistroVehiculo.this, "Seleccione el IMEI y el Teléfono", Toast.LENGTH_LONG).show();
                    return;
                }


            }
        });




    }


    private void enviarComando(){

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroOperacion.class);
                startActivity(intent);

            }
        });


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnEnviar.setEnabled(false);
                int color = Color.parseColor("#5356fe");
                btnEnviar.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));

                System.out.println("INGRESO ENVIAR COMANDO CLICK");
                Call<RequestVehiculo> call = VehiculoClient.getVehiculoService().enviarComando(idVehiculo,flagBloqueo);

                call.enqueue(new Callback<RequestVehiculo>() {
                    @Override
                    public void onResponse(Call<RequestVehiculo> call, Response<RequestVehiculo> response) {
                        if (response.isSuccessful()) {
                            System.out.println("ENVIAR COMANDO INGRESO SUCCESS");

                            comando = response.body();
                            estado = comando.getEstado();
                             mensaje = comando.getMensaje();
                            System.out.println("COMANDO ENVIADO : " + mensaje);


                            if(estado==0){
                                btnEnviar.setEnabled(true);
                                Toast.makeText(RegistroVehiculo.this, mensaje , Toast.LENGTH_SHORT).show();
                                int color = Color.parseColor("#536DFE");
                                btnEnviar.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                            }else{
                                btnEnviar.setEnabled(true);
                                int color = Color.parseColor("#536DFE");
                                btnEnviar.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                                Toast.makeText(RegistroVehiculo.this, mensaje , Toast.LENGTH_SHORT).show();
                                Intent activity = new Intent(getApplicationContext(), RegistroOperacion.class);
                                startActivity(activity);
                            }




                        }else{
                            btnEnviar.setEnabled(true);
                            int color = Color.parseColor("#536DFE");
                            btnEnviar.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                            Toast.makeText(RegistroVehiculo.this, "Algo fue mal, intentelo de nuevo", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestVehiculo> call, Throwable t) {
                        Log.e("failure send command", t.getLocalizedMessage());
                        Toast.makeText(RegistroVehiculo.this, "Algo fue mal, revise sus datos de acceso, o la red de sus datos móviles" , Toast.LENGTH_SHORT).show();
                        btnEnviar.setEnabled(true);
                        int color = Color.parseColor("#536DFE");
                        btnEnviar.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                    }
                });

            }
        });



    }

    private void dropdownTelefono(){


        telefonoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                getTelefono();
                telefSelected = false;

            }
        });

        telefonoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                telefSelected = true;

                GPSVehiculo telefonoSIM = (GPSVehiculo)parent.getItemAtPosition(position);
                idChip  = Integer.parseInt(telefonoSIM.getId_chip());
                System.out.println("ID CHIP REAL : " + telefonoSIM.getId_chip());
                System.out.println("DESCRIPCION CHIP: " + telefonoSIM.getDes_chip());
                System.out.println("TELEF-SIMCARD: " +telefono);


            }
        });

    }

    private void events() {



        spinnerFlota = findViewById(R.id.spinnerFlota);
        btnLocal = findViewById(R.id.btnLocalizacion);
        btnEnviar = findViewById(R.id.buttonEnviar);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        nombreText = (TextView)findViewById(R.id.nombreView);
        numCreditoText = findViewById(R.id.numCreditoView);
        spinnerSutran = findViewById(R.id.spinnerSutran);
        direccionText = findViewById(R.id.direccionView);
        bloqueText = findViewById(R.id.bloqueoView);
        ultimaTransText = findViewById(R.id.ultimaTransView);
        spinnerTipoVeh = findViewById(R.id.spinnerTipoVehiculo);
        spinnerMarca = findViewById(R.id.spinnerMarca);
        spinnerModelo = findViewById(R.id.spinnerModelo);
        imeiText = findViewById(R.id.imeiAutoComplete);
        modeloGps = findViewById(R.id.vinView);
        telefonoText = findViewById(R.id.telefAutoComplete);
        spinnerCommand = findViewById(R.id.spinnerComando);

        telefonoText.setThreshold(1);
        btnSiguiente.setEnabled(false);
        btnEnviar.setEnabled(false);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(RegistroVehiculo.this, R.id.nombreView, RegexTemplate.NOT_EMPTY,R.string.invalid_nombreVeh);
        awesomeValidation.addValidation(RegistroVehiculo.this, R.id.numCreditoView, "^[0-9]{5,10}$",R.string.invalid_numCredito);
        awesomeValidation.addValidation(RegistroVehiculo.this, R.id.telefAutoComplete, "^[0-9A-Z\\s-]{9,}$",R.string.invalid_telfonoSIM);
        awesomeValidation.addValidation(RegistroVehiculo.this, R.id.imeiAutoComplete, "^[0-9]{10,}$",R.string.invalid_imei);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);



    }


    private void llenarCasillas() {




        if(ListadoTareas.estado){

            String nombre =  SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NOM_VEH);
            String numCredit = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NUM_CRE);
            String imei = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_IMEI_GPS);
            String modelo = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_MODEL_GPS);
            String telefonoSIM = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_TELEFONO2);
            String SutranFlag = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NUM_SUTRAN);
            String ultDireccion = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ULT_DIRECCION);
            String ultTrans = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ULT_TRANS);
            int flagBloqueo = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_FLAG_BLOQUEO));
            idGps = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_GPS));
            idChip = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_CHIP));


            imeiSelected = true;
            telefSelected = true;
            nombreText.setText(nombre);
            numCreditoText.setText(numCredit);
            imeiText.setText(imei);
            modeloGps.setText(modelo);
            telefonoText.setText(telefonoSIM);
            direccionText.setText(ultDireccion);
            ultimaTransText.setText(ultTrans);


            if(flagBloqueo == 0){
                bloqueText.setText("NO");
            }else{
                bloqueText.setText("SI");
            }

            if(SutranFlag.equals("N")){
                System.out.println("INGRESO CONDICION ES N");
                spinnerSutran.setSelection(0);
            }else{
                System.out.println("INGRESO CONDICION ES S");
                spinnerSutran.setSelection(1);
            }



        }



    }

    private void llenarComboBoxTipoVehiculo(){




        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(RegistroVehiculo.this);

        List<TipoVehiculo> tempList = new ArrayList<>();

        tempList = dataBaseHelper.getTipoVehiculo();

        int incrementable = 0;

        for(TipoVehiculo tipo: tempList ){

            listaTipo.add(tipo.getDesTipo());
            mapTipo.put(tipo.getIdTipo(),tipo.getDesTipo());
            System.out.println("TIPO DEVEHICULO DES : " + tipo.getDesTipo());

                if(ListadoTareas.estado){
                    idTipo = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_TIPO));
                    if(idTipo == tipo.getIdTipo()){

                        positionTipo = incrementable;
                        System.out.println("POSICION TIPO  SELECT ES " + positionTipo);

                    }

                    incrementable++;

                }

        }



        ArrayAdapter<TipoVehiculo> dataAdapter;
        dataAdapter = new ArrayAdapter(RegistroVehiculo.this, android.R.layout.simple_spinner_item, listaTipo);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTipoVeh.setAdapter(dataAdapter);

        if(ListadoTareas.estado){

            spinnerTipoVeh.setSelection(positionTipo);

        }

        spinnerTipoVeh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String desTipoVeh = spinnerTipoVeh.getSelectedItem().toString();
                System.out.println("TIPO VEHICULO SELECCIONADO : " + desTipoVeh);


                for(Map.Entry<Integer,String> map: mapTipo.entrySet()){

                    if(map.getValue().equals(desTipoVeh)){

                        idTipo = map.getKey();
                        System.out.println("ID TIPO VEHICULO  ES : " + idTipo);

                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void insertarTipoVehiculoLocal(){

        Call<List<TipoVehiculo>> tipoVehiculoList = TipoVehiculoClient.getTipoVehiculoService().getTipoVechiulo("");
        tipoVehiculoList.enqueue(new Callback<List<TipoVehiculo>>() {
            @Override
            public void onResponse(Call<List<TipoVehiculo>> call, Response<List<TipoVehiculo>> response) {

                if (response.isSuccessful()) {
                    List<TipoVehiculo> listaTipo = new ArrayList<>();
                    listaTipo =  response.body();
                    System.out.println("LISTADO DE TIPO VEHICULO  : " + listaTipo);

                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(RegistroVehiculo.this);
                    TipoVehiculo tipoVeh = new TipoVehiculo();
                    boolean success  = false;

                    for(TipoVehiculo tipo: listaTipo){

                        tipoVeh.setDesTipo (tipo.getDesTipo());
                        tipoVeh.setIdTipo(tipo.getIdTipo());
                        System.out.println("DES TIPO VEHICULO : " + tipo.getDesTipo());

                        success = dataBaseHelper.addTipo(tipoVeh);

                    }

                    Toast.makeText(RegistroVehiculo.this, "Success " + success, Toast.LENGTH_SHORT).show();



                }
            }

            @Override
            public void onFailure(Call<List<TipoVehiculo>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });






    }


    private void getModelo(){
/*
        modelo = modeloText.getText().toString();

        Call<List<ModeloVehiculo>> telefonoList = ModeloClient.getModeloService().getModelo();
        telefonoList.enqueue(new Callback<List<ModeloVehiculo>>() {
            @Override
            public void onResponse(Call<List<ModeloVehiculo>> call, Response<List<ModeloVehiculo>> response) {

                if (response.isSuccessful()) {
                    Log.e("success", response.body().toString());
                    System.out.println("ENTRO AL METODO SUCCESS");
                    listaModelo =  response.body();
                    System.out.println("LISTADO DE MODELO  : " + listaModelo);
                    AutoCompleteModeloAdapter adapter = new AutoCompleteModeloAdapter(DetalleGps.this, listaModelo);
                    modeloText.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<ModeloVehiculo>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });

*/
    }

    private void borrarRegistrosLocales(){

        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(RegistroVehiculo.this);

        dataBaseHelper.deleteRows();

    }

    private void insertarModeloLocal(){

        Call<List<ModeloVehiculo>> modeloList = ModeloClient.getModeloService().getModelo();
        modeloList.enqueue(new Callback<List<ModeloVehiculo>>() {
            @Override
            public void onResponse(Call<List<ModeloVehiculo>> call, Response<List<ModeloVehiculo>> response) {

                if(response.isSuccessful()){
                    List<ModeloVehiculo> listaModelo = new ArrayList<>();
                    listaModelo = response.body();
                    System.out.println("INGRESO RESPONSE MODELO LOCAL");

                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(RegistroVehiculo.this);
                    ModeloVehiculo modelo = new ModeloVehiculo();
                    boolean success =false;

                    for(ModeloVehiculo model : listaModelo){

                        modelo.setIdModelo(model.getIdModelo());
                        modelo.setDesModelo(model.getDesModelo());
                        modelo.setId_marca(model.getId_marca());

                        success = dataBaseHelper.addModelo(modelo);
                    }

                    Toast.makeText(RegistroVehiculo.this, "SUCCESS : " + success, Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(Call<List<ModeloVehiculo>> call, Throwable t) {

            }
        });

    }

    private void insertarMarcaLocal(){

        Call<List<Marca>> marcaList = MarcaClient.getMarcaService().getMarca();
        marcaList.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {

                if (response.isSuccessful()) {
                    List<Marca> listaMarca = new ArrayList<>();
                    listaMarca =  response.body();


                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(RegistroVehiculo.this);
                    Marca marca = new Marca();
                    boolean success  = false;

                    if(!dataBaseHelper.validacionRowsMarca()){

                        for(Marca m: listaMarca){

                            marca.setDesMarca(m.getDesMarca());
                            marca.setIdMarca(m.getIdMarca());
                            System.out.println("DES MARCA: " + m.getDesMarca());

                            success = dataBaseHelper.addMarca(marca);


                        Toast.makeText(RegistroVehiculo.this, "Success " + success, Toast.LENGTH_SHORT).show();

                    }
                    }





                }
            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });



    }

    private void llenarComboBoxModelo(){


        listaModelo.clear();

        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(RegistroVehiculo.this);



        List<ModeloVehiculo> tempList = new ArrayList<>();

        tempList = dataBaseHelper.getModeloVehiculo(idMarca);

        int incrementable = 0;

        for(ModeloVehiculo m: tempList ){


            listaModelo.add(m.getDesModelo());
            mapModelo.put(m.getIdModelo(),m.getDesModelo());
            System.out.println("MODELO DESCRIPCION : " + m.getDesModelo());

            if(ListadoTareas.estado){
                idModelo = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_MODELO));
                if(idModelo == m.getIdModelo()){

                    position = incrementable;
                    System.out.println("POSICION MODELO SELECT ES " + position);

                }

                incrementable++;

            }

        }


        ArrayAdapter<ModeloVehiculo> dataAdapter;
        dataAdapter = new ArrayAdapter(RegistroVehiculo.this, android.R.layout.simple_spinner_item, listaModelo);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerModelo.setAdapter(dataAdapter);

        if(ListadoTareas.estado){
            spinnerModelo.setSelection(position);
        }


        spinnerModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String desModelo = spinnerModelo.getSelectedItem().toString();
                System.out.println("MODELO  SELECCIONADA : " + desModelo);


                for(Map.Entry<Integer,String> map: mapModelo.entrySet()){

                    if(map.getValue().equals(desModelo)){

                        idModelo = map.getKey();
                        System.out.println("ID DEL MODELO ES : " +idModelo);

                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void llenarComboBoxMarca(){




        DataBaseHelper dataBaseHelper = new DataBaseHelper(RegistroVehiculo.this);

        List<Marca> tempList = new ArrayList<>();

        tempList = dataBaseHelper.getMarca();

        int incrementable = 0;

        for(Marca m: tempList ){


            listaMarca.add(m.getDesMarca());
            mapMarca.put(m.getIdMarca(),m.getDesMarca());
            System.out.println("MARCA : " + m.getDesMarca());

            if(ListadoTareas.estado){
                idMarca = Integer.parseInt(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_MARCA));
                if(idMarca == m.getIdMarca()){

                    position = incrementable;
                    System.out.println("POSICION DEL SELECT ES " + position);

                }

                incrementable++;

            }

        }



        ArrayAdapter<Marca> dataAdapter;
        dataAdapter = new ArrayAdapter(RegistroVehiculo.this, android.R.layout.simple_spinner_item, listaMarca);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMarca.setAdapter(dataAdapter);

        if(ListadoTareas.estado){

            spinnerMarca.setSelection(position);

        }


        spinnerMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String desMarca = spinnerMarca.getSelectedItem().toString();
                System.out.println("MARCA SELECCIONADA : " + desMarca);

                for(Map.Entry<Integer,String> map: mapMarca.entrySet()){

                    if(map.getValue().equals(desMarca)){

                        idMarca = map.getKey();
                        System.out.println("ID DE LA MARCA ES : " +idMarca);

                    }


                }


                llenarComboBoxModelo();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }



    private void getTelefono(){

        telefono = telefonoText.getText().toString(); //Retrieve the whole data incluiding telef+ SIMCARD

        System.out.println("CELULAR  : " + telefono);

        Call<List<GPSVehiculo>> telefonoList = AuthChipClient.getChipService().getTelefono(telefono);
        telefonoList.enqueue(new Callback<List<GPSVehiculo>>() {
            @Override
            public void onResponse(Call<List<GPSVehiculo>> call, Response<List<GPSVehiculo>> response) {

                if (response.isSuccessful()) {
                    Log.e("success", response.body().toString());
                    System.out.println("ENTRO AL METODO SUCCESS");
                    listaTelefono =  response.body();
                    System.out.println("LISTADO DE TELEFONO  : " + listaTelefono);
                    AutoCompleteTelefAdapter adapter = new AutoCompleteTelefAdapter(RegistroVehiculo.this, listaTelefono);
                    telefonoText.setAdapter(adapter);

                }else{
                    loadingDialog.dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<List<GPSVehiculo>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                loadingDialog.dismissDialog();
            }
        });


    }

}

