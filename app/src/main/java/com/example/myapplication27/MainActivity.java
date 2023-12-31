package com.example.myapplication27;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText textid, textnombre, textedad;

    Button Btn_Buscar, Btn_Agregar, Btn_Eliminar, Btn_Modificar;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textid = findViewById(R.id.indenti);
        textnombre = findViewById(R.id.nombre);
        textedad = findViewById(R.id.edad);

        Btn_Buscar = findViewById(R.id.btnbuscar);
        Btn_Agregar = findViewById(R.id.btninsertar);
        Btn_Eliminar = findViewById(R.id.btneliminar);
        Btn_Modificar = findViewById(R.id.btnactualizar);

        requestQueue = Volley.newRequestQueue(this); // Mueve la inicialización de RequestQueue aquí

        Btn_Modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actualizar("http://172.16.0.59/prue/actualizar.php");
            }
        });
        Btn_Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eliminar("http://172.16.0.59/prue/Eliminar.php?id=" + textid.getText().toString());
            }
        });
        Btn_Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agregar("http://172.16.0.59/prue/insertar.php");
            }
        });

        Btn_Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buscar("http://172.16.0.59/prue/buscar.php?id=" + textid.getText().toString());
            }
        });


    }

    private void Buscar(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    if (response.length() > 0) {
                        JSONObject jsonObject = response.getJSONObject(0); // Obtén solo el primer objeto del JSONArray si hay elementos
                        textnombre.setText(jsonObject.getString("nombre"));
                        textedad.setText(jsonObject.getString("edad"));
                        Toast.makeText(MainActivity.this, "Datos encontrados", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error al realizar la consulta", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }


    public void Agregar(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Operación realizada", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", textid.getText().toString());
                parametros.put("nombre", textnombre.getText().toString());
                parametros.put("edad", textedad.getText().toString());

                return parametros;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void Eliminar(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Registro eliminado", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    private void Actualizar(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Operación realizada", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", textid.getText().toString());
                parametros.put("nombre", textnombre.getText().toString());
                parametros.put("edad", textedad.getText().toString());

                return parametros;
            }
        };

        requestQueue.add(stringRequest);
    }
}

