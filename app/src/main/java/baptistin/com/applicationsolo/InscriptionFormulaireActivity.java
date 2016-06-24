package baptistin.com.applicationsolo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.util.Date;
import java.util.List;

public class InscriptionFormulaireActivity extends AppCompatActivity {

    final Context context = this;
    private EditText emailET;
    private EditText passwordET;
    private EditText nomET;
    private EditText prenomET;
    private EditText adresseET;

    public static final String INTENT_EMAIL = "baptistin.com.applicationsolo.INTENT_MESSAGE";
    public static final String INTENT_PASS = "baptistin.com.applicationsolo.INTENT_MESSAGE1";
    public static final String INTENT_NOM = "baptistin.com.applicationsolo.INTENT_MESSAGE2";
    public static final String INTENT_PRENOM = "baptistin.com.applicationsolo.INTENT_MESSAG3";
    public static final String INTENT_ADRESSE = "baptistin.com.applicationsolo.INTENT_MESSAGE4";
    private Button signIN;
    private Button retourInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
        signIN = (Button) findViewById(R.id.login);
        nomET = (EditText) findViewById(R.id.nom);
        prenomET =(EditText) findViewById(R.id.prenom);
        adresseET = (EditText) findViewById(R.id.adresse);
        retourInscription = (Button) findViewById(R.id.retour);

        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signIN();
                }
                catch (java.sql.SQLException e){
                    e.printStackTrace();
                }
            }
        });

        retourInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    goInscription();
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void goInscription(){
        Intent intent = new Intent(this, ConnexionActivity.class);
        startActivity(intent);
    }
    private void signIN() throws java.sql.SQLException{

        String emailString = emailET.getText().toString();
        String passwordString = passwordET.getText().toString();
        String adresseString = adresseET.getText().toString();
        String nomString = nomET.getText().toString();
        String prenomString = prenomET.getText().toString();
        Boolean userKnow = null;

        reinitError();

        boolean error = false;
        if(passwordString.isEmpty() && passwordString.length() < 3){
            passwordET.setError("Le champ 'mot de passe' ne doit pas être vide et plus grand que 3 caractères");
            error = true;
        }
        if(emailString.isEmpty()){
            emailET.setError("Le champ 'email' ne doit pas être vide");
            error = true;
        }
        if(adresseString.isEmpty()){
            adresseET.setError("Le champ 'adresse' ne doit pas être vide");
            error = true;
        }
        if(nomString.isEmpty()){
            nomET.setError("Le champ 'nom' ne doit pas être vide");
            error = true;
        }
        if(prenomString.isEmpty()){
            prenomET.setError("Le champ 'prenom' ne doit pas être vide");
            error = true;
        }

        userKnow = userNotExist(emailString);

        Log.d("Value of boolean :", String.valueOf(userKnow));

        Date a = new Date();
        if(!error && userKnow){
            testOutOrmLiteDatabase(nomString, prenomString,emailString,emailString,passwordString,a);
            Intent intent = new Intent(this, ConnexionActivity.class);
            startActivity(intent);
            inscriptionValideConnexion();

        }else if(!userKnow) {
            alertUser();
        }

    }

    private void reinitError() {
        passwordET.setError(null);
        emailET.setError(null);

    }
    private boolean userNotExist(String contenu_email) throws java.sql.SQLException {
        TodoOpenDatabaseHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                TodoOpenDatabaseHelper.class);
        Dao<User, Long> todoDao = todoOpenDatabaseHelper.getDao();
        List<User> listUser = todoDao.queryForAll();
        for(User t : listUser){
            if (contenu_email.equals(t.getEmail())){
                Log.d("verification :", contenu_email);
                return false;
            }
        }
        return true;
    }


    private void testOutOrmLiteDatabase(String nom,String prenom, String email, String login , String mdp,Date datenai) throws java.sql.SQLException {
        TodoOpenDatabaseHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                TodoOpenDatabaseHelper.class);

        Dao<User, Long> todoDao = todoOpenDatabaseHelper.getDao();

        todoDao.create(new User(nom, prenom, email, login, mdp, datenai));
    }

    private void alertUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle("Inscription impossible");

        alertDialogBuilder
                .setMessage("Cet utilisateur est déjà enregistré.")
                .setCancelable(false)
                .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
    private void inscriptionValideConnexion(){
        Context context = getApplicationContext();
        CharSequence text = "Inscription réussi";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
