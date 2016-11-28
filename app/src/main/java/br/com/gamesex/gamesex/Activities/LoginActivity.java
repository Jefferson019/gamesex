package br.com.gamesex.gamesex.activities;

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

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {

        private static final String TAG = "LoginActivity";

        private Button butonCriarConta;
        private EditText editTextEmail;
        private EditText editTextSenha;
        private Button btnSignin;

        private ProgressDialog progressDialog;

        //define objeto firebese auth
        private FirebaseAuth firebaseAuth;

        private FirebaseAuth.AuthStateListener mAuthListener;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            //inicializa objeto firebase auth
            firebaseAuth = FirebaseAuth.getInstance();

            if(firebaseAuth.getCurrentUser()!= null){

                finish();

                startActivity(new Intent(getApplicationContext(), ListaJogosActivity.class ));
            }

            progressDialog = new ProgressDialog(this);

           butonCriarConta = (Button) findViewById(R.id.email_create_account_button);
            btnSignin = (Button) findViewById(R.id.email_sign_in_button);


            editTextEmail = (EditText) findViewById(R.id.field_email);
            editTextSenha = (EditText) findViewById(R.id.field_password);

            butonCriarConta.setOnClickListener(this);
            btnSignin.setOnClickListener(this);

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


    private void loginUser(){
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextSenha.getText().toString().trim();

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


        progressDialog.setMessage("Carregando...");
        progressDialog.show();

        //criando novo usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            if(firebaseAuth.getCurrentUser()!= null){
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                startActivity(new Intent(getApplicationContext(), ListaJogosActivity.class ));
                            }
                        }

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Falha ao entrar, verifique seu email e senha!", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }


                });
        // [END sign_in_with_email]
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.disconnect_button).setVisibility(View.VISIBLE);
        } else {

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.edit_ConfirmPassword).setVisibility(View.VISIBLE);
            findViewById(R.id.disconnect_button).setVisibility(View.INVISIBLE);

        }

    }


        @Override
        public void onClick(View view) {

            if (view == butonCriarConta) {
               // criarContaInter();
                    Intent chamarCadastrar = new Intent(LoginActivity.this, CadastroActivity.class);
                    startActivity(chamarCadastrar);
            }
            if (view == btnSignin) {

                loginUser();
            }



        }
    }
