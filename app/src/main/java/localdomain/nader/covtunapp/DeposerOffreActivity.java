package localdomain.nader.covtunapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import localdomain.nader.covtunapp.mongoDb.Covoiturage;
import localdomain.nader.covtunapp.mongoDb.Trajet;
import localdomain.nader.covtunapp.mongoDb.Utilisateur;
import localdomain.nader.covtunapp.mongoDb.Voiture;
import localdomain.nader.covtunmockup.R;
import localdomain.nader.covtunmockup.mongoDb.*;
import localdomain.nader.covtunapp.mongoDb.mLabDataAPI.Common;
import localdomain.nader.covtunapp.mongoDb.mLabDataAPI.HTTPDataHandler;

public class DeposerOffreActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText marque, modele, nbPlaces, age, vd, va, pd, pa, dateDep, heureDep, prix;
    Button butPublier;
    Switch fumeurAutorise;
    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposer_offre);
        marque = findViewById(R.id.editTextMarque);
        modele = findViewById(R.id.editTextModele);
        nbPlaces = findViewById(R.id.editTextnbPlaces);
        age = findViewById(R.id.editTextAge);

        vd = findViewById(R.id.editTextVD);
        va = findViewById(R.id.editTextVA);
        pd = findViewById(R.id.editTextPD);
        pa = findViewById(R.id.editTextPA);

        dateDep = findViewById(R.id.editTextDD);

        dateDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year =(calendar.get(Calendar.YEAR));
                int month=(calendar.get(Calendar.MONTH));
                int day = (calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog = new DatePickerDialog(DeposerOffreActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dateDep.setText(i2+"/"+i1+"/"+i);
                    }
                },year,month,day );
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        heureDep = findViewById(R.id.editTextHD);
        prix = findViewById(R.id.editTextPrix);

        fumeurAutorise = findViewById(R.id.switchFumeur);

        butPublier = findViewById(R.id.butPublier);

        butPublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(marque.getText().toString())){
                    marque.setError("Veuillez remplir tous les champs");
                    return;
                }

                if(TextUtils.isEmpty(modele.getText().toString())){
                    modele.setError("Veuillez remplir tous les champs");
                    return;
                }

                if(TextUtils.isEmpty(nbPlaces.getText().toString())){
                    nbPlaces.setError("Veuillez remplir tous les champs");
                    return;
                }

                if(TextUtils.isEmpty(age.getText().toString())){
                    age.setError("Veuillez remplir tous les champs");
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

                if(TextUtils.isEmpty(dateDep.getText().toString())){
                    dateDep.setError("Veuillez remplir tous les champs");
                    return;
                }

                if(TextUtils.isEmpty(heureDep.getText().toString())){
                    heureDep.setError("Veuillez remplir tous les champs");
                    return;
                }

                if(TextUtils.isEmpty(prix.getText().toString())){
                    prix.setError("Veuillez remplir tous les champs");
                    return;
                }


                Covoiturage cov = new Covoiturage(
                        heureDep.getText().toString(),
                        dateDep.getText().toString(),
                        Double.parseDouble(prix.getText().toString()),
                        0,
                        fumeurAutorise.isChecked(),
                        new Trajet(vd.getText().toString(),
                                va.getText().toString(),
                                pd.getText().toString(),
                                pa.getText().toString()),
                        new Voiture(Integer.parseInt(nbPlaces.getText().toString()),
                                modele.getText().toString(),
                                marque.getText().toString(),
                                Double.parseDouble(age.getText().toString())),
                        (Utilisateur) getIntent().getSerializableExtra("idUtilisateur")
                );

                PostCovoiturage pc = new PostCovoiturage(cov);
                pc.execute(Common.getCovoituragesCollection());
                try {
                    pc.get(1000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "L'offre a été publiée avec succés", Toast.LENGTH_SHORT).show();


            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Déposer une offre");


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
        getMenuInflater().inflate(R.menu.deposer_offre, menu);
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

    class PostCovoiturage extends AsyncTask<String, String, String> {

        Covoiturage cov;

        public PostCovoiturage(Covoiturage cov) {
            this.cov = cov;
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            HTTPDataHandler hh = new HTTPDataHandler();


            try {
                JSONObject u = new JSONObject();
                u.put("tel",cov.getPublieePar().getTel());
                u.put("nom", cov.getPublieePar().getNom());
                u.put("prenom", cov.getPublieePar().getPrenom());

                JSONObject t = new JSONObject();
                t.put("villeDepart", cov.getTrajet().getVilleDepart());
                t.put("villeArrivee", cov.getTrajet().getVilleArrivee());
                t.put("pointDepart", cov.getTrajet().getPointDepart());
                t.put("pointArrivee", cov.getTrajet().getPointArrivee());

                JSONObject v = new JSONObject();
                v.put("marque", cov.getVoiture().getMarque());
                v.put("modele", cov.getVoiture().getModele());
                v.put("nbPlaces", cov.getVoiture().getNbPlaces());
                v.put("ageEnAnnee", cov.getVoiture().getAge());

                JSONObject covJson = new JSONObject();
                covJson.put("heureDepart", cov.getHeureDepart());
                covJson.put("dateDepart", cov.getDateDepart());
                covJson.put("prix", cov.getPrix());
                covJson.put("nbReservations", cov.getNbReservations());
                covJson.put("fumeurAutorise", cov.isFumeurAutorisee());
                covJson.put("Trajet", t);
                covJson.put("Voiture", v);
                covJson.put("PublieePar", u);


                String json = covJson.toString();

                hh.postHTTPData(urlString, json);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "";

        }
    }
}
