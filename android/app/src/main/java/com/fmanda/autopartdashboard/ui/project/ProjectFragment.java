package com.fmanda.autopartdashboard.ui.project;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fmanda.autopartdashboard.R;
import com.fmanda.autopartdashboard.adapter.ProjectAdapter;
import com.fmanda.autopartdashboard.controller.ControllerProject;
import com.fmanda.autopartdashboard.model.ModelProject;
import com.fmanda.autopartdashboard.ui.home.HomeViewModel;

import java.util.ArrayList;

public class ProjectFragment extends Fragment {

    private ProjectViewModel mViewModel;
    private RecyclerView rvProject;
    ProjectAdapter projectAdapter;

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        projectAdapter = new ProjectAdapter(getContext(), mViewModel.projects);

        View root = inflater.inflate(R.layout.fragment_project, container, false);


        rvProject = root.findViewById(R.id.rvProject);
        rvProject.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rvProject.setAdapter(projectAdapter);
        loadProjects();
        return root;
    }

    private void loadProjects(){
        ControllerProject controllerProject = new ControllerProject(getContext());
        mViewModel.projects.clear();
        mViewModel.projects.addAll(controllerProject.getProjects());
        projectAdapter.notifyDataSetChanged();

    }

}
