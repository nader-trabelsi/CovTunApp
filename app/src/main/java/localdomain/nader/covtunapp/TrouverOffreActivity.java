package localdomain.nader.covtunapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import localdomain.nader.covtunapp.mongoDb.Covoiturage;
import localdomain.nader.covtunapp.mongoDb.Trajet;
import localdomain.nader.covtunapp.mongoDb.Utilisateur;
import localdomain.nader.covtunapp.mongoDb.mLabDataAPI.Common;
import localdomain.nader.covtunapp.mongoDb.mLabDataAPI.HTTPDataHandler;
import localdomain.nader.covtunmockup.R;
import localdomain.nader.covtunapp.mongoDb.Voiture;

public class TrouverOffreActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button butRechercher;
    private EditText vd,pd,va,pa,date;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouver_offre);

        butRechercher = findViewById(R.id.buttonRechercher);
        vd = findViewById(R.id.editTextVDsearch);
        va = findViewById(R.id.editTextVAsearch);
        pd = findViewById(R.id.editTextPDsearch);
        pa = findViewById(R.id.editTextPAsearch);
        date = findViewById(R.id.editTextDateDep);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year =(calendar.get(Calendar.YEAR));
                int month=(calendar.get(Calendar.MONTH));
                int day = (calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog = new DatePickerDialog(TrouverOffreActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.setText(i2+"/"+i1+"/"+i);
                    }
                },year,month,day );
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        recyclerView = findViewById(R.id.searchResultView);

        adapter = new RecyclerViewAdapter(getApplicationContext(),new ArrayList<Covoiturage>(),(Utilisateur) getIntent().getSerializableExtra("idUtilisateur"));
        layoutManager  = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        butRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(date.getText().toString())){
                    date.setError("Veuillez remplir tous les champs");
                    return;
                }
                if(TextUtils.isEmpty(vd.getText().toString())){
                    vd.setError("Veuillez remplir tous les champs");
                    return;
                }
                if(TextUtils.isEmpty(va.getText().toString())){
                    va.setError("Veuillez remplir tous les champs");
                    return;
                }
                if(TextUtils.isEmpty(pd.getText().toString())){
                    pd.setError("Veuillez remplir tous les champs");
                    return;
                }
                if(TextUtils.isEmpty(pa.getText().toString())){
                    pa.setError("Veuillez remplir tous les champs");
                    return;
                }

                Covoiturage tmpCov = new Covoiturage(
                        date.getText().toString(),
                        new Trajet(vd.getText().toString(),
                                va.getText().toString(),
                                pd.getText().toString(),
                                pa.getText().toString()
                        )
                );

                GetCovoiturages gc = new GetCovoiturages();

                gc.execute(Common.queryCovoiturages(tmpCov));

                try {
                    gc.get(1000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }

                adapter.setData(gc.liste);


            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trouver une offre");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trouver_offre, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_deconnecter) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class GetCovoiturages extends AsyncTask<String, String, String> {

        public ArrayList<Covoiturage> liste = new ArrayList<Covoiturage>();


        @Override
        protected String doInBackground(String... strings) {
            String urlString, resSet;

            urlString = strings[0];
            HTTPDataHandler hh = new HTTPDataHandler();
             resSet =  hh.getHTTPData(urlString);

             ajouterEnsemble(resSet);

            return "";
        }

        private void ajouterEnsemble(String resSet){
            try {
                JSONArray jsonArray = new JSONArray(resSet);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject explrObject = jsonArray.getJSONObject(i);
                    Trajet t = new Trajet(
                            explrObject.getJSONObject("Trajet").getString("villeDepart"),
                            explrObject.getJSONObject("Trajet").getString("villeArrivee"),
                            explrObject.getJSONObject("Trajet").getString("pointDepart"),
                            explrObject.getJSONObject("Trajet").getString("pointArrivee")
                    );

                    Voiture v = new Voiture(
                            explrObject.getJSONObject("Voiture").getInt("nbPlaces"),
                            explrObject.getJSONObject("Voiture").getString("marque"),
                            explrObject.getJSONObject("Voiture").getString("modele"),
                            explrObject.getJSONObject("Voiture").getDouble("ageEnAnnee")
                    );

                    Utilisateur u = new Utilisateur(
                            explrObject.getJSONObject("PublieePar").getInt("tel"),
                            explrObject.getJSONObject("PublieePar").getString("nom"),
                            explrObject.getJSONObject("PublieePar").getString("prenom")
                    );



                    Covoiturage c = new Covoiturage(
                            explrObject.getString("heureDepart"),
                            explrObject.getString("dateDepart"),
                            explrObject.getDouble("prix"),
                            explrObject.getInt("nbReservations"),
                            explrObject.getBoolean("fumeurAutorise"),
                            t,v,u
                    );

                    liste.add(c);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
