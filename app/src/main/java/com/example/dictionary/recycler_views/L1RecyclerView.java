package com.example.dictionary.recycler_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;

import java.util.List;

public class L1RecyclerView extends RecyclerView.Adapter<L1RecyclerView.ProgrammingViewHolder>
{
    private List<L2DictData> l2DictData;
    private Context main_context;

    public L1RecyclerView(List<L2DictData> l2DictData, Context main_context)
    {
        this.main_context = main_context;
        this.l2DictData = l2DictData;
    }

    @Override
    public ProgrammingViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(main_context);
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ProgrammingViewHolder holder, int position)
    {
        holder.pos.setText(l2DictData.get(position).partOfSpeech);
        holder.def_ex_rview.setLayoutManager(new LinearLayoutManager(main_context));
        holder.def_ex_rview.setAdapter(new L2RecyclerView(l2DictData.get(position).l3DictData, main_context));
    }

    @Override
    public int getItemCount() {
        return l2DictData.size();
    }

    static class ProgrammingViewHolder extends RecyclerView.ViewHolder
    {
        RecyclerView def_ex_rview;
        TextView pos;

        ProgrammingViewHolder(View itemView)
        {
            super(itemView);
            def_ex_rview = itemView.findViewById(R.id.def_ex_rview);
            pos = itemView.findViewById(R.id.pos);
        }
    }
}

