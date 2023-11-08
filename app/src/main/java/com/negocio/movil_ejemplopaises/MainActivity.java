package com.negocio.movil_ejemplopaises;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.negocio.movil_ejemplopaises.entity.Pais;
import com.negocio.movil_ejemplopaises.service.ServicePais;
import com.negocio.movil_ejemplopaises.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Spinner spnRegion;
    ArrayAdapter<String> adaptadorRegion;
    ArrayList<String> regiones = new ArrayList<String>();

    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();

    ServicePais servicePais;
    List<Pais> paisesTotal = new ArrayList<Pais>();

    TextView txtCapital, txtMoneda, txtLanguaje, txtPoblacion;
    ImageView imgPais;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        txtCapital = findViewById(R.id.txtCapital);
        txtMoneda = findViewById(R.id.txtMoneda);
        txtLanguaje = findViewById(R.id.txtLenguaje);
        txtPoblacion = findViewById(R.id.txtPoblacion);
        imgPais = findViewById(R.id.imgPais);

        //PASO 1 Cargar las regiones
        adaptadorRegion = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, regiones);
        spnRegion = findViewById(R.id.spnRegion);
        spnRegion.setAdapter(adaptadorRegion);
        regiones.add(" [ Seleccione Región ]");
        regiones.add("Africa");
        regiones.add("Americas");
        regiones.add("Asia");
        regiones.add("Europe");
        regiones.add("Oceania");
        adaptadorRegion.notifyDataSetChanged();

        //PASO 2 Cargar el seleccione pais
        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnPais);
        spnPais.setAdapter(adaptadorPais);
        paises.add(" [ Seleccione País ]");
        adaptadorPais.notifyDataSetChanged();

        //PASO 3 Cargar los todos los paises en memoria
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        Call<List<Pais>> call  = servicePais.listaPais();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                    if (response.isSuccessful()){
                        paisesTotal = response.body();
                    }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {}
        });

        //PASO 4: Cargar los paises de un continente
        spnRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  paises.clear();
                  paises.add(" [ Seleccione País ]");
                  String region =  spnRegion.getSelectedItem().toString();
                  for (Pais obj:paisesTotal){
                      if (obj.getRegion().equalsIgnoreCase(region)){
                          paises.add(obj.getName());
                          adaptadorPais.notifyDataSetChanged();
                      }
                  }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //PASO 5 cargar los datos de pais
        spnPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  String name = spnPais.getSelectedItem().toString();
                  Optional<Pais> optPais  =  paisesTotal.stream().filter(x -> x.getName().equals(name)).findFirst();
                  if (optPais.isPresent()){
                      txtCapital.setText("Capital : " + optPais.get().getCapital());
                      txtPoblacion.setText("Población : " + optPais.get().getPopulation());

                      Glide.with(context).load(optPais.get().getFlags().getPng()).into(imgPais);
                  }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
}