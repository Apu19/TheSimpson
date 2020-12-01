package cl.inacap.thesimpson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.inacap.thesimpson.adapter.Adapter;
import cl.inacap.thesimpson.dto.Personaje;

public class MainActivity extends AppCompatActivity {
    private Spinner frases;
    private String[] frasesItems;
    private ListView lvPer;
    private RequestQueue queue;
    private List<Personaje> personajes = new ArrayList<>();
    private Button btn;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.lvPer = findViewById(R.id.listForm);
        this.adapter = new Adapter(this, R.layout.list_personajes, this.personajes);
        this.lvPer.setAdapter(this.adapter);

        this.frases = findViewById(R.id.spnForm);
        ArrayAdapter<CharSequence> adapterSpn = ArrayAdapter.createFromResource(this, R.array.frases, android.R.layout.simple_spinner_item);
        adapterSpn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.frases.setAdapter(adapterSpn);
        this.frasesItems = getResources().getStringArray(R.array.frases);
        this.btn = findViewById(R.id.btnForm);
        this.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue = Volley.newRequestQueue(MainActivity.this);
                String cantElegida = frases.getSelectedItem().toString().trim();
                if (cantElegida.equalsIgnoreCase("Seleccionar")) {
                    Toast.makeText(MainActivity.this, "Debe seleccionar la cantidad de frases", Toast.LENGTH_LONG).show();
                } else {
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                            "https://thesimpsonsquoteapi.glitch.me/quotes?count=" + cantElegida
                            , null
                            , new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                personajes.clear();
                                Personaje[] personajesObt = new Gson().fromJson(response.toString(), Personaje[].class);
                                personajes.addAll(Arrays.asList(personajesObt));
                            } catch (Exception ex) {
                                personajes = null;
                            } finally {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            personajes = null;
                            adapter.notifyDataSetChanged();
                        }
                    });
                    queue.add(jsonArrayRequest);
                }
            }
        });

    }
}