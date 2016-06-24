package baptistin.com.applicationsolo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by ERNSTLEMAILLOT on 18/04/2016.
 */
public class ConnexionActivity extends AppCompatActivity {

    private Button goinscription;
    private Button goseconnecter;
    private EditText emailET;
    private EditText passwordET;

    public String valueSuivant ="";
    public static final String INTENT_EMAIL = "baptistin.com.applicationsolo.INTENT_MESSAGE";
    public static final String INTENT_PASS = "baptistin.com.applicationsolo.INTENT_MESSAGE1";
    public static final String INTENT_NOM = "baptistin.com.applicationsolo.INTENT_MESSAGE2";
    public static final String INTENT_PRENOM = "baptistin.com.applicationsolo.INTENT_MESSAG3";
    public static final String INTENT_ADRESSE = "baptistin.com.applicationsolo.INTENT_MESSAGE4";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_main);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0); // 0 - for private mode
        if(pref.getString("email", null)==null){

            goinscription = (Button) findViewById(R.id.button2);
            goseconnecter = (Button) findViewById(R.id.login);

            goinscription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goConnexion();
                }
            });

            goseconnecter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailET = (EditText) findViewById(R.id.email);
                    passwordET = (EditText) findViewById(R.id.password);
                    try {
                        userExistAndLogin(emailET.getText().toString(), passwordET.getText().toString());
                    } catch (SQLException e) {
                        Log.d("ERROR", "ERROR");
                    }
                }
            });
        }
        else {
            Intent intent = new Intent(this, InscriptionValideActivity.class);
            startActivity(intent);
        }


    }
    private void goConnexion(){
        Intent intent = new Intent(this, InscriptionFormulaireActivity.class);
        startActivity(intent);
    }

    private void alertUser(){
        Context context = getApplicationContext();
        CharSequence text = "Utilisateur ou mot de pass incorrect.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    private void userExistAndLogin(String contenu_email, String contenu_motdepasse) throws java.sql.SQLException {
        TodoOpenDatabaseHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                TodoOpenDatabaseHelper.class);
        Dao<User, Long> todoDao = todoOpenDatabaseHelper.getDao();
        List<User> listUser = todoDao.queryForAll();
        for(User t : listUser){
            if (contenu_email.equals(t.getEmail())){
                if(contenu_motdepasse.equals(t.getMotdepasse())){
                    Log.d("verification :","utilisateur connu");

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("email", t.getEmail()); // Storing string
                    editor.putString("nom", t.getNom()); // Storing string
                    editor.putString("prenom", t.getPrenom()); // Storing string
                    editor.commit(); // commit changes

                    Intent intent = new Intent(this, InscriptionValideActivity.class);
                    intent.putExtra(INTENT_EMAIL, t.getEmail());
                    intent.putExtra(INTENT_ADRESSE, t.getEmail());
                    intent.putExtra(INTENT_NOM, t.getNom());
                    intent.putExtra(INTENT_PASS, t.getMotdepasse());
                    intent.putExtra(INTENT_PRENOM, t.getPrenom());
                    startActivity(intent);
                    valueSuivant = "OK";
                }
            }

        }
        if(!valueSuivant.equals("OK")){
            alertUser();
        }

    }
}
