package br.com.gamesex.gamesex;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by jeffe on 31/10/2016.
 */
public class ConsoleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView listConsole;
    public Console consoles;
    OnItemClickListener mItemClickListener;

    public ConsoleViewHolder(View itemView){
        super(itemView);

        this.listConsole = (TextView) itemView.findViewById(R.id.consoleNameLabel);

        itemView.setOnClickListener(this);

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        params.setMargins(35, 0, 0, 0);
        itemView.setLayoutParams(params);


    }
    public void onItemClick(View view, int position){}

    public interface OnItemClickListener{

        public void onItemClick(View v, int position);
    }
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener){

        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void onClick(View view) {

        if(mItemClickListener !=null){

            mItemClickListener.onItemClick(view, getPosition());
        }
    }


}
