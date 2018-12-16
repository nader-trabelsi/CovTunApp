package localdomain.nader.covtunapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import localdomain.nader.covtunmockup.R;
import localdomain.nader.covtunapp.mongoDb.Utilisateur;
import localdomain.nader.covtunapp.mongoDb.mLabDataAPI.Common;
import localdomain.nader.covtunapp.mongoDb.mLabDataAPI.HTTPDataHandler;

public class MainActivity extends AppCompatActivity {

    private Button butConnecter;
    private Button butInscri;
    EditText tel,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        butConnecter = findViewById(R.id.buttonConnecter);
        tel = findViewById(R.id.editTel);
        pass = findViewById(R.id.editPass);

        butConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilisateur u  =  new Utilisateur(
                        Integer.parseInt(tel.getText().toString()),
                        pass.getText().toString()
                );

                CheckUser cu = new CheckUser(u);
                cu.execute(Common.queryUtilisateurWithPass(u));
                try {
                    cu.get(1000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                if(cu.existe){
                    Intent intent = new Intent(MainActivity.this, OpsActivity.class);
                    intent.putExtra("idUtilisateur", cu.u);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Veuillez vérifier votre n° de tel et mot de passe", Toast.LENGTH_SHORT).show();
                }


            }
        });

        butInscri = findViewById(R.id.buttonGoInscri);
        butInscri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        
    }

    class CheckUser extends AsyncTask<String, String, String> {

        Utilisateur u ;
        public boolean existe;

        public CheckUser(Utilisateur u ){
            this.u = u;
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            HTTPDataHandler hh = new HTTPDataHandler();
            String resSet =  hh.getHTTPData(urlString);

            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(resSet);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(jsonObj==null){
                existe = false;
            } else {
                existe = true;
                try {
                    u.setNom(jsonObj.getString("nom"));
                    u.setPrenom(jsonObj.getString("prenom"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return "";
        }
    }


}
