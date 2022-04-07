package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments;

import static rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments.ToDoFragment.TICKET_DETAIL;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Status;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.recycler.adapter.InProgressAdapter;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.recycler.differ.TicketDiffItemCallback;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel.TicketViewModel;

public class InProgressFragment extends Fragment {

    private RecyclerView recyclerView;

    private TicketViewModel ticketViewModel;
    private InProgressAdapter ticketAdapter;
    private EditText etSearch;

    public InProgressFragment() {
        super(R.layout.fragment_in_progress);
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
        initListeners();
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
                ticketViewModel.filterTickets(editable.toString(), Status.IN_PROGRESS);
            }
        });
    }

    private void initRecycler() {
        ticketAdapter = new InProgressAdapter(
                new TicketDiffItemCallback(),
                moveLTicket -> ticketViewModel.moveTicket(moveLTicket, Status.TO_DO),
                moveRTicket -> ticketViewModel.moveTicket(moveRTicket, Status.DONE),
                detailTicket -> {
                    // todo open ticket details
                    Bundle args = new Bundle();
                    args.putInt(TICKET_DETAIL, detailTicket.getId());

                    TicketDetailFragment fragment = new TicketDetailFragment();
                    fragment.setArguments(args);

                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fcvMain, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(ticketAdapter);
    }

    private void initObservers() {
        ticketViewModel.getInProgressList().observe(getViewLifecycleOwner(), tickets -> ticketAdapter.submitList(tickets));
    }

    private void initView(View view) {
        etSearch = view.findViewById(R.id.etSearch2);
        recyclerView = view.findViewById(R.id.recyclerView2);
    }
}