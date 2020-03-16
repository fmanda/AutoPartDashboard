package com.fmanda.autopartdashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.model.ModelProject;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private Context context;
    private List<ModelProject> projects;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;

    public ProjectAdapter(Context context, List<ModelProject> projects) {
        this.context = context;
        this.projects = projects;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_project_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.modelproject = projects.get(i);
        viewHolder.txtKode.setText(viewHolder.modelproject.getProjectcode());
        viewHolder.txtNama.setText(viewHolder.modelproject.getProjectname());

        ProjectAdapterDetail projectAdapterDetail = new ProjectAdapterDetail(context, projects);
        viewHolder.rvDetail.setLayoutManager(new GridLayoutManager(context, 1));
        viewHolder.rvDetail.setAdapter(projectAdapterDetail);

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelProject modelproject;
        public TextView txtNama;
        public TextView txtKode;
        public RecyclerView rvDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            txtKode = itemView.findViewById(R.id.txtKode);
            txtNama = itemView.findViewById(R.id.txtNama);
            rvDetail = itemView.findViewById(R.id.rvDetail);
        }
        //
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onClick(modelproject);
            }
        }


    }

    public interface ItemClickListener {
        void onClick(ModelProject modelProject);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


}

class ProjectAdapterDetail extends RecyclerView.Adapter<ProjectAdapterDetail.ViewHolder> {
    private Context context;
    private List<ModelProject> projects;
    private LayoutInflater mInflater;

    public ProjectAdapterDetail(Context context, List<ModelProject> projects) {
        this.context = context;
        this.projects = projects;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ProjectAdapterDetail.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_projectdetail_layout, viewGroup, false);
        ProjectAdapterDetail.ViewHolder viewHolder = new ProjectAdapterDetail.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProjectAdapterDetail.ViewHolder viewHolder, int i) {
        viewHolder.modelproject = projects.get(i);
        viewHolder.txtKode.setText(viewHolder.modelproject.getProjectcode());
        viewHolder.txtNama.setText(viewHolder.modelproject.getProjectname());
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelProject modelproject;
        public TextView txtNama;
        public TextView txtKode;

        public ViewHolder(View itemView) {
            super(itemView);
            txtKode = itemView.findViewById(R.id.txtKode);
            txtNama = itemView.findViewById(R.id.txtNama);
        }

        @Override
        public void onClick(View v) {

        }
    }


}