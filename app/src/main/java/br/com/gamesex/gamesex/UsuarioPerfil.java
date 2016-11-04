package br.com.gamesex.gamesex;

import android.widget.EditText;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by jeffe on 10/10/2016.
 */
public class UsuarioPerfil {

    private String imgUser;
    private String nome;
    private String email;
    private String console;


    //private String confirmPasswordTxt;

    public UsuarioPerfil() {
    }

    public UsuarioPerfil(String email) {
        this.email = email;

    }

    public UsuarioPerfil(String nome, String email,ArrayList<Console> console){
        this.nome = nome;
        this.email = email;

    }


    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConsole() {        return console;    }

    public void setConsole(String console) {        this.console = console;    }

    public Map<String, Object> toUserMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("nome", nome);
        result.put("imagem", imgUser);

        return result;

    }

}

