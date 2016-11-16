package br.com.gamesex.gamesex.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.gamesex.gamesex.R;
import br.com.gamesex.gamesex.UsuarioPerfil;

public class CadastroActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final String TAG = "CadastroActivity";

    private Button butonCriarConta;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private EditText editTextConfSenha;
    private Button btnVoltar;

    private ProgressDialog progressDialog;

    //define objeto firebese auth
    private FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //inicializa objeto firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!= null){

            finish();

            startActivity(new Intent(getApplicationContext(), PerfilActivity.class ));
        }

        progressDialog = new ProgressDialog(this);

        butonCriarConta = (Button) findViewById(R.id.email_create_account_button);
        btnVoltar = (Button) findViewById(R.id.btn_cancelar);


        editTextEmail = (EditText) findViewById(R.id.field_email);
        editTextSenha = (EditText) findViewById(R.id.field_password);
        editTextConfSenha = (EditText) findViewById(R.id.edit_ConfirmPassword);


        butonCriarConta.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]
    }




    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextSenha.getText().toString().trim();
        String confirPassword = editTextConfSenha.getText().toString();

        if(TextUtils.isEmpty(email)){
            // Email vazio
            editTextEmail.setError("O campo email é necessário");
            Log.d(TAG, "createAccount:"+ email);

            return;
        }
        if(TextUtils.isEmpty(password )){
            // Senha vazio
            editTextSenha.setError("O campo senha é necessário");
            Log.w(TAG, "createPassword:"+ password);

            return;
        }

        if(!TextUtils.equals(confirPassword, password)){
            // Senha vazio
            Toast.makeText(this, "As senhas são diferentes",Toast.LENGTH_SHORT).show();

            return;
        }

        if(TextUtils.isEmpty(confirPassword )){
            // Senha vazio
            Toast.makeText(this, "Campo senha é obrigatório!",Toast.LENGTH_SHORT).show();

            return;
        }

        progressDialog.setMessage("Registrando, Aguarde...");
        progressDialog.show();
        final UsuarioPerfil user= new UsuarioPerfil();
        user.setEmail(email);

        firebaseAuth.createUserWithEmailAndPassword(email,password )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            if(firebaseAuth.getCurrentUser()!= null){

                                finish();

                                user.setEmail(email);

                                Intent chamarPerfil = new Intent(getApplicationContext(), PerfilActivity.class);
                                Bundle enviaEmail = new Bundle();

                                enviaEmail.putString("email", email);
                                chamarPerfil.putExtras(enviaEmail);
                                startActivity(chamarPerfil);

                            }

                        }
                        else {

                            Toast.makeText(CadastroActivity.this,"Falha ao registrar", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }



    private void updateUI(FirebaseUser user) {

        if (user != null) {

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.edit_ConfirmPassword).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.INVISIBLE);

        }

    }


    @Override
    public void onClick(View view) {

        if (view == butonCriarConta) {
            // criarContaInter();
            registerUser();
        }

        if(view == btnVoltar){
            finish();
            Intent inteCancelar = new Intent(CadastroActivity.this, LoginActivity.class);
            startActivity(inteCancelar);

        }




    }
}
