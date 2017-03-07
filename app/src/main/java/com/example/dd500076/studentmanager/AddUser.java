package com.example.dd500076.studentmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddUser extends SuperActivity {

    private static final int REQUEST_CODE = 15;

    /*
    public AddUser(){
        super();
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser(view);
            }
        });
    }

    public void addUser(View v){
        EditText nomEtu = (EditText) findViewById(R.id.nomEtu);
        EditText prenomEtu = (EditText) findViewById(R.id.prenomEtu);
        EditText anneeEtu = (EditText) findViewById(R.id.anneeEtu);
        EditText formationEtu = (EditText) findViewById(R.id.formationEtu);

        String nom = nomEtu.getText().toString();
        String prenom = prenomEtu.getText().toString();
        String annee = anneeEtu.getText().toString();
        String formation = formationEtu.getText().toString();

        if (nom.equals("") || prenom.equals("") || formation.equals("") || annee.equals("")){
            Snackbar snackbar = Snackbar.make(v,"Erreur dans la saise des informations", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        if (APIManager.getInstance(this).isConnected())
            APIManager.getInstance(this).addStudent(nom, prenom , formation, annee);
        else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }

    }

    @Override
    public void onAddStudent(boolean added) {
        Log.d("fghjkjddfgh", "onAddStudent: gggggg");
        if (added)
        {
            Log.d("TAG", "Création Réussie");
            setResult(RESULT_OK, null);
            finish();
        }
        else
        {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.fab),"Erreur dans l'ajout", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == REQUEST_CODE && resultCode == RESULT_OK){
           addUser(findViewById(R.id.fab));
        }
    }
}
