package com.example.dictionary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ProgrammingViewHolder>
{
    private List<SubData> subData;
    private Context main_context;

    AdapterRecyclerView(List<SubData> subData, Context main_context)
    {
        this.main_context = main_context;
        this.subData = subData;
    }

    @Override
    public ProgrammingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ProgrammingViewHolder holder, int position)
    {
        String POS = subData.get(position).partOfSpeech;
        String definition1 = subData.get(position).subSubData.get(0).definition;
        String example1 = subData.get(position).subSubData.get(0).example;
        List<String> synonyms = subData.get(position).subSubData.get(0).synonyms;

        Log.i("DictAPI", subData.get(position).partOfSpeech);
        Log.i("DictAPI", subData.get(position).subSubData.get(0).definition);

        holder.pos.setText(POS);
        holder.definition1.setText(definition1);
        holder.example1.setText(example1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(main_context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.synonym_rview.setLayoutManager(linearLayoutManager);
        holder.synonym_rview.setAdapter(new AdapterSynonymRecyclerView(synonyms));
    }

    @Override
    public int getItemCount() {
        return subData.size();
    }

    static class ProgrammingViewHolder extends RecyclerView.ViewHolder{

        TextView pos, definition1, example1, synonym1;
        RecyclerView synonym_rview;

        ProgrammingViewHolder(View itemView) {
            super(itemView);
            pos         = itemView.findViewById(R.id.pos);
            definition1 = itemView.findViewById(R.id.definition1);
            example1    = itemView.findViewById(R.id.example1);
            synonym_rview  = itemView.findViewById(R.id.synonym_rview);
        }
    }
}

