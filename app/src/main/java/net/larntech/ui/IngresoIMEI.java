package net.larntech.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import net.larntech.R;
import net.larntech.common.Constantes;
import net.larntech.common.LoadingDialog;
import net.larntech.common.SharedPreferencesManager;
import net.larntech.retrofit.client.AuthChipClient;
import net.larntech.retrofit.response.GPSVehiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngresoIMEI extends AppCompatActivity {

    AutoCompleteTextView editText;
    List<GPSVehiculo> chipPossible = new ArrayList<>();
    String imeiGps;
    Button buttonOne;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_i_m_e_i);
        getSupportActionBar().setTitle("Datos");

        events();


        editText.setThreshold(5);

        dropdownIMEI();



    }



    private void dropdownIMEI() {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //retrieveData(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                getChips(); //this will call your method every time the user stops typing, if you want to call it for each letter, call it in onTextChanged

            }
        });



    }

    private void getChips() {


        imeiGps = editText.getText().toString();


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


                        AutoCompleteChipAdapter adapter = new AutoCompleteChipAdapter(IngresoIMEI.this, chipPossible);
                        editText.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<List<GPSVehiculo>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });

    }

    private void events() {

        buttonOne = findViewById(R.id.btnSeleccionarImei);
        editText = findViewById(R.id.numeroIMEI);
        //enfocar y mostrar keyboard
        editText.requestFocus();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(IngresoIMEI.this, R.id.numeroIMEI, "^[0-9]{15}$",R.string.invalid_numeroIMEI);


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);



        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(awesomeValidation.validate()){

                chipPossible.clear();

                getChips();

                final LoadingDialog loadingDialog = new LoadingDialog(IngresoIMEI.this);

                loadingDialog.startLoadingDialog();


                Timer d = new Timer();

                d.schedule(new TimerTask() {
                    @Override
                    public void run() {


                        String imeiGpsCompleto = chipPossible.get(0).getImeiGps();
                        System.out.println("ONCLICK IMEI :" + imeiGpsCompleto);
                        String modeloGps = chipPossible.get(0).getDes_tipo();
                        String idGps = chipPossible.get(0).getIdGps().toString();
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID_GPS,idGps);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_IMEI_GPS, imeiGpsCompleto);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_MODEL_GPS, modeloGps);
                        System.out.println("ID GPS : " + idGps);
                        loadingDialog.dismissDialog();
                        Intent activity2Intent = new Intent(getApplicationContext(), RegistroVehiculo.class);
                        startActivity(activity2Intent);
                    }
                },5000);


                }else{

                    return;


                }

            }
        });





    }


}