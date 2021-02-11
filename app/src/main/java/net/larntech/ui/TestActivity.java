package net.larntech.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import net.larntech.R;
import net.larntech.retrofit.response.CountryItem;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private List<CountryItem> countryList;
    private int countKeywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);



        fillCountryList();



        AutoCompleteTextView editText = findViewById(R.id.actv);




        AutoCompleteCountryAdapter  adapter = new AutoCompleteCountryAdapter(TestActivity.this, countryList);
        editText.setThreshold(6);
        editText.setAdapter(adapter);










    }

    private void fillCountryList(){

        countryList = new ArrayList<>();
        countryList.add(new CountryItem("990251677"));
        countryList.add(new CountryItem("990251456"));
        countryList.add(new CountryItem("990251963"));
        countryList.add(new CountryItem("990251744"));
        countryList.add(new CountryItem("990251343"));

    }
}