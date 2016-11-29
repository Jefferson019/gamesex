package br.com.gamesex.gamesex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import br.com.gamesex.gamesex.model.Games;

/**
 * Created by jeffe on 24/11/2016.
 */
public class ListGamesRecyclerViewAdapter extends RecyclerView.Adapter<GamesViewHolder>  {

    private Context contexto;
    private List<Games> listGames;

    public ListGamesRecyclerViewAdapter(Context contexto, List<Games> listGames) {
        this.contexto = contexto;
        this.listGames = listGames;
    }

    @Override
    public GamesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(contexto).inflate(R.layout.activity_games, parent, false);
        return new GamesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GamesViewHolder holder, int position) {
        Games games = listGames.get(position);


        holder.gameNomeLabel.setText(games.getNomegames());
        holder.gameCategoriaLabel.setText(games.getCategoria());
        holder.gameConsoleLabel.setText(games.getConsole());
        holder.gameValorLabel.setText(games.getValor());

        byte[] imgGameByteArray = Base64.decode(games.getGamesimg(), Base64.DEFAULT);

        Glide.with(contexto)
                .load(imgGameByteArray)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new SimpleTarget<Bitmap>(100, 100){

                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            holder.gamesimg.setImageBitmap(resource);

                        }

        });
        holder.gameHolder = games;
    }


    @Override
    public int getItemCount() {
        return listGames.size();
    }
}
