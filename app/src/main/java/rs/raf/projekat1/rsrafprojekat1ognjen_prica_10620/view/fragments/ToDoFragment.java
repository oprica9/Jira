package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.recycler.adapter.TicketAdapter;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.recycler.differ.TicketDiffItemCallback;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel.TicketViewModel;


public class ToDoFragment extends Fragment {

    private RecyclerView recyclerView;

    private TicketViewModel ticketViewModel;
    private TicketAdapter ticketAdapter;

    public ToDoFragment() {
        super(R.layout.fragment_to_do);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketViewModel = new ViewModelProvider(requireActivity()).get(TicketViewModel.class);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initObservers();
        initRecycler();
    }

    private void initRecycler() {
        ticketAdapter = new TicketAdapter(new TicketDiffItemCallback());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(ticketAdapter);
    }

    private void initObservers() {
        ticketViewModel.getToDoList().observe(getViewLifecycleOwner(), tickets -> {
            ticketAdapter.submitList(tickets);
        });
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }
}