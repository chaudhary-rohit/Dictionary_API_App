package com.example.dictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterSynonymRecyclerView extends RecyclerView.Adapter<AdapterSynonymRecyclerView.ProgrammingViewHolderSRV>

{
    private List<String> synonyms;

    AdapterSynonymRecyclerView(List<String> synonyms)
    {
        this.synonyms = synonyms;
    }

    @NonNull
    @Override
    public ProgrammingViewHolderSRV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.synonym_item_layout, parent, false);
        return new ProgrammingViewHolderSRV(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolderSRV holder, int position)
    {
        String synonym = synonyms.get(position);
        holder.synonymitem.setText(synonym);
    }

    @Override
    public int getItemCount() {
        return synonyms.size();
    }

    static class ProgrammingViewHolderSRV extends RecyclerView.ViewHolder{

        TextView synonymitem;

        ProgrammingViewHolderSRV(View itemView) {
            super(itemView);
            synonymitem  = itemView.findViewById(R.id.synonym_item);
        }
    }
}

