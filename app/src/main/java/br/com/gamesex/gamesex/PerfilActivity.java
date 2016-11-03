package br.com.gamesex.gamesex;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PerfilActivity extends AppCompatActivity {

        private static final String TAGPERFIL  = "CadastroActivity";
        private EditText emailReceber;
        private EditText editTextNomeP;

        private ProgressDialog progressDialog;

        private Button btnAvancar;
        private Button btnSair;

        private FirebaseAuth mAuth;
        private FirebaseDatabase database;

        private DatabaseReference mDatabase;

        private ConsoleRecycleViewAdapter recycleConsoleViewAdapter;
        private List<Console> listarConsole = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        progressDialog = new ProgressDialog(this);

        recycleConsoleViewAdapter = new ConsoleRecycleViewAdapter(this, listarConsole);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_ricycler_view);
        recyclerView.setHasFixedSize(true);
        /*recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));*/
        recyclerView.setAdapter(recycleConsoleViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        firebaseListarConsole();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        btnAvancar = (Button) findViewById(R.id.btnCadastrar);
        btnSair = (Button) findViewById(R.id.btnSairCancelar);
        emailReceber = (EditText) findViewById(R.id.editEmail);
        editTextNomeP = (EditText) findViewById(R.id.edit_Txt_Nome);



        Intent recebeEmail = getIntent();
        Bundle paramsEmail = recebeEmail.getExtras();

        String recebrEmail = this.getIntent().getStringExtra("email");

        emailReceber.setText(recebrEmail.toString());


        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                final String email = emailReceber.getText().toString().trim();
                final String nome = editTextNomeP.getText().toString().toString();

                if(TextUtils.isEmpty(nome)){

                    Log.w(TAGPERFIL, "createaccountPerfil"+ nome);
                    editTextNomeP.setError("O Campo nome é necessário");
                }

                UsuarioPerfil user = new UsuarioPerfil();
                user.setEmail(email);
                user.setNome(nome);

                database = FirebaseDatabase.getInstance();
                mDatabase = database.getReference("Users");

                DatabaseReference recordUser=  mDatabase.push();
                recordUser.updateChildren(user.toUserMap());


                Intent vaiMain = new Intent(getApplicationContext(), SelecionarActivity.class);
                startActivity(vaiMain);


                NotificationCompat.Builder nCadastro = new NotificationCompat.Builder(PerfilActivity.this);
                nCadastro.setSmallIcon(R.mipmap.ic_app_gamesex);
                nCadastro.setContentText("Seja bem vindo a GamesEx, A rede social do seu game!");

                Intent notifBoasVIndas = new Intent(getApplicationContext(), SelecionarActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());

                stackBuilder.addNextIntentWithParentStack(notifBoasVIndas);

                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                nCadastro.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(PerfilActivity.NOTIFICATION_SERVICE);

                mNotificationManager.notify(0, nCadastro.build());

            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sair();
            }
        });

    }

    private void pegarImagem(){



    }

    private void firebaseListarConsole() {

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Consoles");

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot c : dataSnapshot.getChildren()){
                    Console console = c.getValue(Console.class);

                    listarConsole.add(console);
                }

                recycleConsoleViewAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PerfilActivity.this, "Não foi localizados Consoles!", Toast.LENGTH_SHORT).show();
            }

        });

    }


    private void sair(){
        finish();
        mAuth.signOut();

    }
}
