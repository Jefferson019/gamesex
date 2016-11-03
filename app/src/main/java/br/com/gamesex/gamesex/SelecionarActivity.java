package br.com.gamesex.gamesex;

import android.content.ClipData;
import android.content.Intent;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class SelecionarActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSair = (Button) findViewById(R.id.disconnect_button);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chamarLogin = new Intent(SelecionarActivity.this, LoginActivity.class);
                startActivity(chamarLogin);
                sair();


            }
        });


    }



 public void sair(){

     finish();
     firebaseAuth.signOut();

 }

}
