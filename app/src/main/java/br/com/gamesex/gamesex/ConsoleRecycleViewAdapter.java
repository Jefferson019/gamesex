package br.com.gamesex.gamesex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jeffe on 31/10/2016.
 */
public class ConsoleRecycleViewAdapter extends RecyclerView.Adapter<ConsoleViewHolder> {

    private Context context;
    private List<Console> listConsoles;


    public ConsoleRecycleViewAdapter(Context context, List<Console> pListConsole) {
        this.context = context;
        this.listConsoles = pListConsole;
    }


    @Override
    public ConsoleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_console, parent, false);
         return new ConsoleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsoleViewHolder holder, int position) {

        Console console = listConsoles.get(position);

        holder.listConsole.setText(console.getNome());

    }

    @Override
    public int getItemCount() {
        return listConsoles.size();
    }

}
