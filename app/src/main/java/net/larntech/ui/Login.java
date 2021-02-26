package net.larntech.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.larntech.R;
import net.larntech.common.Constantes;
import net.larntech.common.DataBaseHelper;
import net.larntech.common.SharedPreferencesManager;
import net.larntech.retrofit.client.LoginClient;
import net.larntech.retrofit.client.MarcaClient;
import net.larntech.retrofit.client.ModeloClient;
import net.larntech.retrofit.client.TipoVehiculoClient;
import net.larntech.retrofit.response.Marca;
import net.larntech.retrofit.response.ModeloVehiculo;
import net.larntech.retrofit.response.TipoVehiculo;
import net.larntech.retrofit.service.LoginService;
import net.larntech.retrofit.response.ResponseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {


    Button btnLogin;
    TextView tvGoSignUp;
    EditText etUsuario, etPassword;
    LoginClient loginClient;
    LoginService loginService;
    List<ModeloVehiculo> listaModelo = new ArrayList<>();
    List<Marca> listaMarca = new ArrayList<>();
    List<TipoVehiculo> listaTipo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        insertarTipoVehiculoLocal();
        insertarMarcaLocal();
        insertarModeloLocal();


        getSupportActionBar().hide();


        retrofitInit();
        findViews();
        events();





    }

    private void retrofitInit() {
        loginClient = LoginClient.getInstance();
        loginService = loginClient.getLoginService();
    }

    private void findViews() {
        btnLogin = findViewById(R.id.buttonLogin);
        tvGoSignUp = findViewById(R.id.textViewGoSignUp);
        etUsuario = findViewById(R.id.editTextUsuario);
        etPassword = findViewById(R.id.editTextPassword);
    }

    private void events() {
        btnLogin.setOnClickListener(this);
        tvGoSignUp.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.buttonLogin:
                goToLogin();

                break;
            case R.id.textViewGoSignUp:

                break;
        }
    }



    private void goToLogin() {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(Login.this);

        boolean marca = dataBaseHelper.validacionRowsMarca();
        boolean tipo = dataBaseHelper.validacionRowsTipo();
        boolean modelo = dataBaseHelper.validacionRowsModelo();

        if(marca!=false && tipo!=false && modelo!=false) {

            String usuario = etUsuario.getText().toString();
            String password = etPassword.getText().toString();
            String grant_type = "password";

            if (usuario.isEmpty()) {
                etUsuario.setError("El usuario es requerido");

            } else if (password.isEmpty()) {
                etPassword.setError("La contraseña es requerida");

            } else {

                btnLogin.setEnabled(false);
                int color = Color.parseColor("#5356fe");
                btnLogin.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));

                Call<ResponseAuth> call = loginService.doLogin(usuario, password, grant_type);


                call.enqueue(new Callback<ResponseAuth>() {
                    @Override
                    public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                        if (response.isSuccessful()) {

                            SharedPreferencesManager.setSomeStringValue(Constantes.PREF_TOKEN, response.body().getAccess_token());
                            SharedPreferencesManager.setSomeStringValue(Constantes.PREF_USERNAME, usuario);

                            System.out.println(response.body().getAccess_token());


                            Toast.makeText(Login.this, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show();
                            btnLogin.setEnabled(true);
                            int color = Color.parseColor("#536DFE");
                            btnLogin.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                            Intent i = new Intent(Login.this, ListadoTareas.class);
                            startActivity(i);
                            // Destruimos este Activity para que no se pueda volver.
                            finish();


                        } else {
                            etUsuario.setError("El usuario o contraseña son incorrectas");
                            etPassword.setError("El usuario o contraseña son incorrectas");
                            etPassword.setFocusableInTouchMode(true);
                            etPassword.requestFocus();
                            //Toast.makeText(Login.this, "Algo fue mal, revise sus datos de acceso, o la red de sus datos móviles", Toast.LENGTH_LONG).show();
                            btnLogin.setEnabled(true);
                            int color = Color.parseColor("#536DFE");
                            btnLogin.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAuth> call, Throwable t) {
                        Toast.makeText(Login.this, "Problemas de conexión. Inténtelo de nuevo", Toast.LENGTH_LONG).show();
                        btnLogin.setEnabled(true);
                        int color = Color.parseColor("#536DFE");
                        btnLogin.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));

                    }
                });

            }

        }else{
                btnLogin.setEnabled(true);
                int color = Color.parseColor("#536DFE");
                btnLogin.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                System.out.println("No sevuev cargaron los datos correctamente");
                Intent i = new Intent(Login.this, Login.class);
                Toast.makeText(Login.this, "Error al cargar los datos, reinicie la aplicación", Toast.LENGTH_LONG).show();

            }
    }

    private void insertarMarcaLocal(){

        Call<List<Marca>> marcaList = MarcaClient.getMarcaService().getMarca();
        marcaList.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {

                if (response.isSuccessful()) {
                    listaMarca =  response.body();


                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(Login.this);
                    Marca marca = new Marca();
                    boolean success  = false;

                    if(!dataBaseHelper.validacionRowsMarca()){
                        for(Marca m: listaMarca){

                            marca.setDesMarca(m.getDesMarca());
                            marca.setIdMarca(m.getIdMarca());
                            System.out.println("DES MARCA: " + m.getDesMarca());

                            success = dataBaseHelper.addMarca(marca);

                        }

                        //Toast.makeText(Login.this, "Success " + success, Toast.LENGTH_SHORT).show();

                    }




                }else{
                    Toast.makeText(Login.this, "Vuelva a Ingresar Nuevamente", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });



    }


    private void insertarModeloLocal(){

        Call<List<ModeloVehiculo>> modeloList = ModeloClient.getModeloService().getModelo();
        modeloList.enqueue(new Callback<List<ModeloVehiculo>>() {
            @Override
            public void onResponse(Call<List<ModeloVehiculo>> call, Response<List<ModeloVehiculo>> response) {

                if(response.isSuccessful()) {
                    listaModelo = response.body();
                    System.out.println("INGRESO RESPONSE MODELO LOCAL");

                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(Login.this);
                    ModeloVehiculo modelo = new ModeloVehiculo();
                    boolean success = false;

                    if(!dataBaseHelper.validacionRowsModelo()){

                        for (ModeloVehiculo model : listaModelo) {

                            modelo.setIdModelo(model.getIdModelo());
                            modelo.setDesModelo(model.getDesModelo());
                            modelo.setId_marca(model.getId_marca());

                            success = dataBaseHelper.addModelo(modelo);
                        }

                        //Toast.makeText(Login.this, "SUCCESS : " + success, Toast.LENGTH_SHORT).show();


                    }


                   }else{

                        Toast.makeText(Login.this, "Vuelva a Ingresar Nuevamente", Toast.LENGTH_SHORT).show();

                    }

                }


            @Override
            public void onFailure(Call<List<ModeloVehiculo>> call, Throwable t) {

            }
        });

    }

    private void insertarTipoVehiculoLocal(){

        Call<List<TipoVehiculo>> tipoVehiculoList = TipoVehiculoClient.getTipoVehiculoService().getTipoVechiulo("");
        tipoVehiculoList.enqueue(new Callback<List<TipoVehiculo>>() {
            @Override
            public void onResponse(Call<List<TipoVehiculo>> call, Response<List<TipoVehiculo>> response) {

                if (response.isSuccessful()) {

                    listaTipo =  response.body();
                    System.out.println("LISTADO DE TIPO VEHICULO  : " + listaTipo);

                    DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(Login.this);
                    TipoVehiculo tipoVeh = new TipoVehiculo();
                    boolean success  = false;

                    if(!dataBaseHelper.validacionRowsTipo()){

                        for(TipoVehiculo tipo: listaTipo){

                            tipoVeh.setDesTipo (tipo.getDesTipo());
                            tipoVeh.setIdTipo(tipo.getIdTipo());
                            System.out.println("DES TIPO VEHICULO : " + tipo.getDesTipo());

                            success = dataBaseHelper.addTipo(tipoVeh);

                        }

                        //Toast.makeText(Login.this, "Success " + success, Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(Login.this, "Vuelva a Ingresar Nuevamente", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<TipoVehiculo>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });


    }


    private void borrarRegistrosLocales(){

        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(Login.this);

        dataBaseHelper.deleteRows();

    }


}