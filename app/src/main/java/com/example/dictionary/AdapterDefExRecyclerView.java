package com.example.dictionary;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterDefExRecyclerView extends RecyclerView.Adapter<AdapterDefExRecyclerView.ProgrammingViewHolderDERV>
{
    AdapterDefExRecyclerView()
    {

    }

    @NonNull
    @Override
    public ProgrammingViewHolderDERV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolderDERV holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ProgrammingViewHolderDERV extends RecyclerView.ViewHolder{

        TextView pos, definition1, example1, synonym1;
        RecyclerView synonym_rview;

        ProgrammingViewHolderDERV(View itemView) {
            super(itemView);
            pos         = itemView.findViewById(R.id.pos);
            definition1 = itemView.findViewById(R.id.definition1);
            example1    = itemView.findViewById(R.id.example1);
            synonym_rview  = itemView.findViewById(R.id.synonym_rview);
        }
    }
}
