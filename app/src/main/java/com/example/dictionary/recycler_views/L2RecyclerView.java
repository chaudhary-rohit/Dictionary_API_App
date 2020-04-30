package com.example.dictionary.recycler_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;

import java.util.List;

public class L2RecyclerView extends RecyclerView.Adapter<L2RecyclerView.ProgrammingViewHolderDERV>
{
    List<L3DictData> l3DictData;
    Context main_context;

    L2RecyclerView(List<L3DictData> l3DictData, Context main_context)
    {
        this.l3DictData = l3DictData;
        this.main_context = main_context;
    }

    @NonNull
    @Override
    public ProgrammingViewHolderDERV onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.def_ex_syn_item_layout, parent, false);
        return new L2RecyclerView.ProgrammingViewHolderDERV(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolderDERV holder, int position)
    {
        holder.definition.setText(l3DictData.get(position).definition);
        holder.example.setText(l3DictData.get(position).example);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(main_context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        holder.synonym_rview.setLayoutManager(linearLayoutManager);
        holder.synonym_rview.setAdapter(new L3RecyclerView(l3DictData.get(position).synonyms));
    }

    @Override
    public int getItemCount()
    {
        return l3DictData.size();
    }

    static class ProgrammingViewHolderDERV extends RecyclerView.ViewHolder
    {
        TextView definition, example;
        RecyclerView synonym_rview;

        ProgrammingViewHolderDERV(View itemView) {
            super(itemView);
            definition     = itemView.findViewById(R.id.definition);
            example        = itemView.findViewById(R.id.example);
            synonym_rview  = itemView.findViewById(R.id.synonym_rview);
        }
    }
}
