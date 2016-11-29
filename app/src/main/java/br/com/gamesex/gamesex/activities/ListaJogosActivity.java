package br.com.gamesex.gamesex.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.gamesex.gamesex.ListGamesRecyclerViewAdapter;
import br.com.gamesex.gamesex.R;
import br.com.gamesex.gamesex.model.Games;


public class ListaJogosActivity extends AppCompatActivity {

    private ListGamesRecyclerViewAdapter reyclerViewAdapterGame;
    private List<Games> gamesLst = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference gamesRef;
    private Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_games);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSair = (Button) findViewById(R.id.disconnect_button);

        getRecycler();

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chamarLogin = new Intent(ListaJogosActivity.this, LoginActivity.class);
                startActivity(chamarLogin);
                sair();

            }
        });


    }


    private void getGames(){

        database = FirebaseDatabase.getInstance();
        gamesRef = database.getReference("Games");

        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gamesLst.clear();
                for(DataSnapshot c: dataSnapshot.getChildren()){
                    System.out.println(c.getValue());
                    Games games = c.getValue(Games.class);
                    gamesLst.add(games);
                }
                reyclerViewAdapterGame.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ListaJogosActivity.this, "Não há jogos cadastrados!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getRecycler(){

        reyclerViewAdapterGame = new ListGamesRecyclerViewAdapter(this, gamesLst);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ricycler_view_list_games);
        recyclerView.setAdapter(reyclerViewAdapterGame);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        getGames();
    }


 public void sair(){

     finish();
     firebaseAuth.signOut();

 }

    /*@Override
    protected void onResume() {
        super.onResume();
        firebaseAuth = FirebaseAuth.getInstance();
        btnSair = (Button) findViewById(R.id.disconnect_button);

        getRecycler();

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chamarLogin = new Intent(ListaJogosActivity.this, LoginActivity.class);
                startActivity(chamarLogin);
                sair();

            }
        });*/



}
