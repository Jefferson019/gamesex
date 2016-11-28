package br.com.gamesex.gamesex;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.gamesex.gamesex.interfaces.RecycleOnItemClickListener;
import br.com.gamesex.gamesex.model.Console;

/**
 * Created by jeffe on 31/10/2016.
 */
public class ConsoleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "msgLog" ;
    public TextView listConsole;
    public Console consoles;
    RecycleOnItemClickListener mItemClickListener;

    public ConsoleViewHolder(View itemView){
        super(itemView);

        this.listConsole = (TextView) itemView.findViewById(R.id.consoleNameLabel);

        itemView.setOnClickListener(this);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        params.setMargins(35, 0, 0, 0);
        itemView.setLayoutParams(params);


    }
    public void onItemClick(View view, int position){


    }

    public void SetOnItemClickListener(RecycleOnItemClickListener mItemClickListener){

        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(view, "Item clicado", Toast.LENGTH_SHORT).show();
       mItemClickListener.onItemClickListener(getAdapterPosition());

       // Log.d(TAG, "onClick" +getPosition() + " " + mItemClickListener);
}


}
