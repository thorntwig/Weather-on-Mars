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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.janne.weatheronmars.Model.Sol;
import com.janne.weatheronmars.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.List;

public class SolListFragment extends Fragment {

    private static final String SOLS_LIST_KEY = "sols_list_key";

    private RecyclerView recyclerView;
    private SolAdapter adapter;

    private Callbacks callbacks;

    private List<Sol> sols;
    private int adapterPosition;

    public interface Callbacks {
        void onSolSelected(List<Sol> sols, int key);
    }

    private SolListFragment() {

    }

    public static SolListFragment newInstance(List<Sol> sols) {
        Bundle args = new Bundle();
        args.putSerializable(SOLS_LIST_KEY, (Serializable) sols);

        SolListFragment fragment = new SolListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        sols = (List<Sol>) getArguments().getSerializable(SOLS_LIST_KEY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sol_list, container, false);


        recyclerView = view.findViewById(R.id.sol_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SolAdapter(sols);
        recyclerView.setAdapter(adapter);
        return view;
    }


    private class SolHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Sol sol;

        private TextView titleTextView;

        public SolHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_sol, parent, false));
            itemView.setOnClickListener(this);

            titleTextView = itemView.findViewById(R.id.sol_number);
        }

        @Override
        public void onClick(View view) {

        }

        public void bind(Sol sol) {
            this.sol = sol;
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
            String date = formatter.format(sol.getDate());
            titleTextView.setText(date);
        }
    }

    private class SolAdapter extends RecyclerView.Adapter<SolHolder> {

        private List<Sol> sols;

        public SolAdapter(List<Sol> sols) {
            this.sols = sols;
        }

        @NonNull
        @Override
        public SolHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SolHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull final SolHolder holder, final int position) {
            Sol sol = sols.get(position);
            holder.bind(sol);
            holder.itemView.setSelected(false);
            if (adapterPosition == holder.getAdapterPosition()) {
                holder.itemView.setSelected(true);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (adapterPosition != position) {
                        notifyItemChanged(adapterPosition);
                        adapterPosition = position;
                        view.setSelected(true);
                        callbacks.onSolSelected(sols, position);
                    }
                }

            });

        }

        @Override
        public int getItemCount() {
            if (sols == null) {
                return 0;
            }
            return sols.size();
        }

    }

}
