package br.com.gamesex.gamesex.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import br.com.gamesex.gamesex.R;


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
