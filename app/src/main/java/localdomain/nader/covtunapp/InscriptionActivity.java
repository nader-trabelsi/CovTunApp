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

public class InscriptionActivity extends AppCompatActivity {

    private Button butInscrire;
    EditText tel,nom,prenom,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        butInscrire = findViewById(R.id.buttonInscrire);
        tel = findViewById(R.id.editTel);
        prenom = findViewById(R.id.editPrenom);
        nom = findViewById(R.id.editNom);
        pass = findViewById(R.id.editPass);

        butInscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utilisateur u  =  new Utilisateur(
                        Integer.parseInt(tel.getText().toString()),
                        nom.getText().toString(),
                        prenom.getText().toString()
                );
                u.setPass(pass.getText().toString());

                PostUser pu = new PostUser(u);
                pu.execute(Common.getUtilisateursCollection());
                try {
                    pu.get(1000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                if(pu.dejaInscrit==false) {
                    Intent intent = new Intent(InscriptionActivity.this, OpsActivity.class);
                    intent.putExtra("idUtilisateur", u);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Vous êtes déjà inscrit!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class PostUser extends AsyncTask<String, String, String>{

        Utilisateur u ;
        public boolean dejaInscrit;

        public PostUser(Utilisateur u ){
            this.u = u;
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            HTTPDataHandler hh = new HTTPDataHandler();
            JSONObject jsonObj = new JSONObject();

            try {
                jsonObj.put("tel",u.getTel());
                jsonObj.put("nom",u.getNom());
                jsonObj.put("prenom",u.getPrenom());
                jsonObj.put("motdepasse",u.getPass());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json = jsonObj.toString();

            String resSet =  hh.getHTTPData(Common.queryUtilisateur(u));

            if(resSet == "" || Integer.parseInt(resSet)==0){
                this.dejaInscrit=false;
                hh.postHTTPData(urlString,json);
            }
            else {
                this.dejaInscrit=true;
            }
            return "";
        }
    }


}
