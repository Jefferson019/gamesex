package br.com.gamesex.gamesex.Activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.gamesex.gamesex.Console;
import br.com.gamesex.gamesex.ConsoleRecycleViewAdapter;
import br.com.gamesex.gamesex.Interface.RecycleOnItemClickListener;
import br.com.gamesex.gamesex.R;
import br.com.gamesex.gamesex.UsuarioPerfil;


public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {

        final static  String TAGPERFIL  = "CadastroActivity";
        final private int GALERY = 1;
        final private int CAPTURE_IMAGE = 2;
        private EditText emailReceber;
        private EditText editTextNomeP;
        private ImageView imageViewUser;

        private ProgressDialog progressDialog;

        private Button btnAvancar;
        private Button btnSair;

        private FirebaseAuth mAuth;
        private FirebaseDatabase database;

        private DatabaseReference mDatabase;

        private ConsoleRecycleViewAdapter recycleConsoleViewAdapter;
        private List<Console> listarConsole = new ArrayList<>();

        private UsuarioPerfil userPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        progressDialog = new ProgressDialog(this);

        recycleConsoleViewAdapter = new ConsoleRecycleViewAdapter(this, listarConsole);
        recycleConsoleViewAdapter.setRecycleOnItemClickListener((RecycleOnItemClickListener) this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_ricycler_view);
        recyclerView.setHasFixedSize(true);

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
        imageViewUser = (ImageView) findViewById(R.id.imageUser);

        imageViewUser.setOnClickListener(this);

        Intent recebeEmail = getIntent();
        Bundle paramsEmail = recebeEmail.getExtras();

        String recebrEmail = this.getIntent().getStringExtra("email");



        emailReceber.setText(recebrEmail.toString());


        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                final String email = emailReceber.getText().toString().trim();
                final String nome = editTextNomeP.getText().toString().trim();
                final String imageUserSet = imageViewUser.getResources().toString();

                if(TextUtils.isEmpty(nome)){

                    Log.d(TAGPERFIL, "createaccountPerfil"+ nome);
                    editTextNomeP.setError("O Campo nome é necessário");
                }

                UsuarioPerfil user = new UsuarioPerfil();
                user.setEmail(email);
                user.setNome(nome);
                user.setImgUser(imageUserSet);

                database = FirebaseDatabase.getInstance();
                mDatabase = database.getReference("Users");

                DatabaseReference recordUser=  mDatabase.push();
                recordUser.updateChildren(user.toUserMap());


                Intent vaiMain = new Intent(getApplicationContext(), SelecionarActivity.class);
                startActivity(vaiMain);

                NotificationCompat.Builder nCadastro = new NotificationCompat.Builder(PerfilActivity.this);
                nCadastro.setSmallIcon(R.mipmap.ic_app_gamesex);
                nCadastro.setContentTitle("Games-Exchange GEx");
                nCadastro.setContentText("Seja bem vindo a GamesEx, A rede social do seu game!");

                Intent notifBoasVIndas = new Intent(PerfilActivity.this, SelecionarActivity.class);
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

    private void selecionarImagem(){

        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
        builder.setMessage("Selecionar a imagem do Perfil:")
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //camera intent
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE);
                    }
                })
                .setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), GALERY);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == CAPTURE_IMAGE) {
            if (resultCode == RESULT_OK) {
                Glide.with(PerfilActivity.this)
                        .load(data.getData())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .centerCrop()
                        .error(R.drawable.person)
                        //.placeholder(R.drawable.default_user_gray)
                        .into(new BitmapImageViewTarget(imageViewUser) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                 RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(PerfilActivity.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                imageViewUser.setImageDrawable(circularBitmapDrawable);

                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                resource.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream .toByteArray();
                                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                            }

                        });


                }
            }
        if (requestCode == GALERY){
            if (resultCode == RESULT_OK) {
                Glide.with(PerfilActivity.this)
                        .load(data.getData())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .centerCrop()
                        .error(R.drawable.person)
                        //.placeholder(R.drawable.default_user_gray)
                        .into(new BitmapImageViewTarget(imageViewUser) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(PerfilActivity.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                imageViewUser.setImageDrawable(circularBitmapDrawable);

                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                resource.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream .toByteArray();
                                String encodedGalery = Base64.encodeToString(byteArray, Base64.DEFAULT);

                            }


                        });

            }
        }
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


    @Override
    public void onClick(View v) {
        selecionarImagem();
    }

    private void sair(){
        finish();
        mAuth.signOut();

    }
}
