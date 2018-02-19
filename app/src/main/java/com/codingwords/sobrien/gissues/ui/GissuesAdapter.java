package com.codingwords.sobrien.gissues.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingwords.sobrien.gissues.R;
import com.codingwords.sobrien.gissues.entity.Issue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/19/2018.
 */

public class GissuesAdapter extends RecyclerView.Adapter<GissuesAdapter.Holder> {

    private List<Issue> gissuesList;
    private final LayoutInflater inflator;

    public GissuesAdapter(LayoutInflater inflator) {
        this.inflator = inflator;
        gissuesList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.mtvTitle.setText(gissuesList.get(position).getTitle());
        String id = gissuesList.get(position).getNumber().toString();
        holder.mtvId.setText(id);
        holder.mtvCreatedBy.setText(gissuesList.get(position).getUser().getLogin());
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflator.inflate(R.layout.gissue, parent, false));
    }

    @Override
    public int getItemCount(){
        return gissuesList.size();
    }
    public void addGissues(List<Issue> issues) {
        gissuesList.clear();
        gissuesList.addAll(issues);
        notifyDataSetChanged();
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView mtvTitle;
        TextView mtvId;
        TextView mtvCreatedBy;

        public Holder(View v) {
            super(v);
            mtvTitle = (TextView) v.findViewById(R.id.title);
            mtvId = (TextView) v.findViewById(R.id.gissue_id);
            mtvCreatedBy = (TextView) v.findViewById(R.id.created_by);
        }
    }

    public void clearGissues(){
            gissuesList.clear();
            notifyDataSetChanged();
    }
}
