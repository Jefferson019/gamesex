package br.com.gamesex.gamesex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.gamesex.gamesex.interfaces.RecycleOnItemClickListener;
import br.com.gamesex.gamesex.model.Console;

/**
 * Created by jeffe on 31/10/2016.
 */
public class ConsoleRecycleViewAdapter extends RecyclerView.Adapter<ConsoleViewHolder> {

    private Context context;
    private List<Console> listConsoles;
    RecycleOnItemClickListener recycleOnItemClickListener;

    public ConsoleRecycleViewAdapter(Context context, List<Console> pListConsole) {
        this.context = context;
        this.listConsoles = pListConsole;
    }

    public void SetRecycleOnItemClickListener(final RecycleOnItemClickListener recycleOnItemClickListener){

         this.recycleOnItemClickListener = recycleOnItemClickListener;
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
        holder.consoles = console;
        holder.mItemClickListener = recycleOnItemClickListener;

    }

    @Override
    public int getItemCount() {
        return listConsoles.size();
    }

}
