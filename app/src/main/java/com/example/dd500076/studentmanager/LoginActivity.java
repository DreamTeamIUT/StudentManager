package com.example.dd500076.studentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class LoginActivity extends SuperActivity {

    private String idEtud;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idEtud = getIntent().getStringExtra("idEtu");

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText login = (EditText) findViewById(R.id.text_edit_login);
                EditText password = (EditText) findViewById(R.id.edit_txt_password);

                String l = login.getText().toString();
                String p = password.getText().toString();

                if (l.equals("") || p.equals("")){
                    Snackbar snackbar = Snackbar.make(view,"Erreur dans la saise du mot de passe et du login", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else {
                    APIManager.getInstance(LoginActivity.this).connect(l, p);
                }
            }
        });
    }

    @Override
    public void onConnect(boolean connected, String token) {
        if(connected){
            Intent i = new Intent();
            i.putExtra("idEtu", idEtud);
            setResult(RESULT_OK, i);
            finish();
        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.fab), "Erreur dans la connexion au serveur distant", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

}
