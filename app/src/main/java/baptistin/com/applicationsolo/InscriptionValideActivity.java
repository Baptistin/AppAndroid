package baptistin.com.applicationsolo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * Created by ERNSTLEMAILLOT on 18/04/2016.
 */
public class InscriptionValideActivity extends AppCompatActivity {

    private Button modifyPassword;
    public TextView passTV;
    public EditText mdpNew;
    public TextView mdpLabel;
    public Button buttonNew;
    private Button validationForm;
    private Button deconnexion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription_valide);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        TextView emailTV = (TextView) findViewById(R.id.email);
        TextView adresseTV = (TextView) findViewById(R.id.adresse);
        TextView nomTV = (TextView) findViewById(R.id.nom);
        TextView prenomTV = (TextView) findViewById(R.id.prenom);

        emailTV.setText(pref.getString("email", null)); // getting String);
        adresseTV.setText(pref.getString("email", null));
        nomTV.setText(pref.getString("nom", null));
        prenomTV.setText(pref.getString("prenom", null));
        modifyPassword = (Button) findViewById(R.id.mdpmodify);
        validationForm = (Button) findViewById(R.id.validation);
        deconnexion = (Button) findViewById(R.id.deconnexion);

        modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertUserNewPassword();
                editeMdp();
            }
        });

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deconnexiontosession();
            }
        });

        validationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMap();
                //goConnexion();
            }
        });
    }
    private void deconnexiontosession(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this, ConnexionActivity.class);
        startActivity(intent);
    }
    private void goMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    private void alertUserNewPassword(){
        Context context = getApplicationContext();
        CharSequence text = "Veuillez saisir un nouveau mot de passe";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void alertUserNewPasswordSave(){
        Context context = getApplicationContext();
        CharSequence text = "Nouveau mot de passe enregistré.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    private void editeMdp() {
        mdpLabel = (TextView) findViewById(R.id.password1);
        mdpNew = (EditText) findViewById(R.id.password12);
        buttonNew = (Button) findViewById(R.id.password123);

        mdpLabel.setVisibility(View.VISIBLE);
        mdpNew.setVisibility(View.VISIBLE);
        buttonNew.setVisibility(View.VISIBLE);


        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mdpLabel.setVisibility(View.INVISIBLE);
                mdpNew.setVisibility(View.INVISIBLE);
                buttonNew.setVisibility(View.INVISIBLE);
                alertUserNewPasswordSave();
            }
        });



    }

}
