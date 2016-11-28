package br.com.gamesex.gamesex.model;

/**
 * Created by jeffe on 24/11/2016.
 */
public class Games {

    private String nomegames;
    private String categoria;
    private String valor;
    private String console;
    private String gamesimg;


    public String getNomegames() {
        return nomegames;
    }

    public void setNomegames(String nomegames) {
        this.nomegames = nomegames;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getGamesimg() {
        return gamesimg;
    }

    public void setGamesimg(String gameimg) {
        this.gamesimg = gameimg;
    }
}
