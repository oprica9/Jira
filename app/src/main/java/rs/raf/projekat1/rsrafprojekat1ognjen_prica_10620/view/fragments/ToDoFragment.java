package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.Ticket;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Status;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.recycler.adapter.InProgressAdapter;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.recycler.adapter.ToDoAdapter;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.recycler.differ.TicketDiffItemCallback;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel.TicketViewModel;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel.UserViewModel;


public class ToDoFragment extends Fragment {

    private RecyclerView recyclerView;

    private UserViewModel userViewModel;
    private TicketViewModel ticketViewModel;
    private ToDoAdapter toDoAdapter;

    private EditText etSearch;

    public ToDoFragment() {
        super(R.layout.fragment_to_do);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketViewModel = new ViewModelProvider(requireActivity()).get(TicketViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initObservers();
        initRecycler();
        initListeners();
    }

    private void initRecycler() {
        toDoAdapter = new ToDoAdapter(new TicketDiffItemCallback(), userViewModel.isAdmin(), new ToDoAdapter.TicketAction() {
            @Override
            public int moveTicket(Ticket ticket, Status status) {
                return ticketViewModel.moveTicket(ticket, status);
            }

            @Override
            public int deleteTicket(Ticket t) {
                return ticketViewModel.deleteTicket(t);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(toDoAdapter);
    }

    private void initListeners() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable.toString());
                ticketViewModel.filterTickets(editable.toString(), Status.TO_DO);
            }
        });
    }

    private void initObservers() {
        ticketViewModel.getToDoList().observe(getViewLifecycleOwner(), tickets -> {
            toDoAdapter.submitList(tickets);
        });
    }

    private void initView(View view) {
        etSearch = view.findViewById(R.id.etSearch1);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

}