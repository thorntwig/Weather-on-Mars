package com.janne.weatheronmars.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.janne.weatheronmars.Model.Sol;
import com.janne.weatheronmars.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SolListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SolAdapter adapter;

    private Callbacks callbacks;

    private List<Sol> sols;

    public interface Callbacks {
        void onSolSelected(Sol sol);
    }
    public static SolListFragment newInstance(List<Sol> sols) {
        Bundle args = new Bundle();
        args.putSerializable("solskey" , (Serializable) sols);

        SolListFragment fragment = new SolListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks  = (Callbacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sols = (List<Sol>) getArguments().getSerializable("solskey");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sol_list,container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.sol_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    public void updateUI(){

        if(adapter == null) {
            adapter = new SolAdapter(sols);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setSols(sols);
            adapter.notifyDataSetChanged();
        }
    }


    private class SolHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Sol sol;

        private TextView titleTextView;

        public SolHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_sol,parent,false));
            itemView.setOnClickListener(this);

            titleTextView = (TextView) itemView.findViewById(R.id.sol_number);
            titleTextView.setText("test");
        }

        @Override
        public void onClick(View view) {

            Log.i("clicked" ,sol.getNumber() + " ");
            callbacks.onSolSelected(sol);
        }
        public void bind(Sol sol) {
            this.sol = sol;
            titleTextView.setText(sol.getNumber() + "");
        }
    }
    private class SolAdapter extends RecyclerView.Adapter<SolHolder> {

        private List<Sol> sols;

        public SolAdapter(List<Sol> sols){
            this.sols = sols;
        }

        @NonNull
        @Override
        public SolHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SolHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SolHolder holder, int position) {
            Sol sol = sols.get(position);
            holder.bind(sol);
        }

        @Override
        public int getItemCount() {
            if(sols == null) {
                return 0;
            }
            return sols.size();
        }
        public void setSols(List<Sol> sols) {
            this.sols = sols;
        }
    }

}
