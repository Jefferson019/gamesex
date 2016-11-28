package br.com.gamesex.gamesex;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.gamesex.gamesex.model.Games;

/**
 * Created by jeffe on 24/11/2016.
 */
public class GamesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView gameNomeLabel;
    public TextView gameCategoriaLabel;
    public TextView gameValorLabel;
    public TextView gameConsoleLabel;
    public ImageView gamesimg;
    public Games gameHolder;

    public GamesViewHolder(View itemView) {
        super(itemView);

        this.gameNomeLabel = (TextView) itemView.findViewById(R.id.txtNomeGame);
        this.gameCategoriaLabel = (TextView) itemView.findViewById(R.id.txtCategoria);
        this.gameConsoleLabel = (TextView) itemView.findViewById(R.id.txtConsole);
        this.gameValorLabel = (TextView) itemView.findViewById(R.id.txtValorTroca);
        this.gamesimg = (ImageView) itemView.findViewById(R.id.imgGames);
    }

    @Override
    public void onClick(View view) {


    }
}
