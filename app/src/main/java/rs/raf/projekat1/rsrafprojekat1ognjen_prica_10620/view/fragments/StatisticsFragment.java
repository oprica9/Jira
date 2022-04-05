package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.Ticket;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Status;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.TicketType;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel.TicketViewModel;


public class StatisticsFragment extends Fragment {

    private TextView toDoSumTv;
    private TextView toDoEnhTv;
    private TextView toDoBugTv;
    private TextView inProgSumTv;
    private TextView inProgEnhTv;
    private TextView inProgBugTv;
    private TextView doneSumTv;
    private TextView doneEnhTv;
    private TextView doneBugTv;

    private TicketViewModel ticketViewModel;

    public StatisticsFragment() {
        super(R.layout.fragment_statistics);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        ticketViewModel = new ViewModelProvider(requireActivity()).get(TicketViewModel.class);
        initView(view);
        loadData();
        initObservers();
    }

    private void initObservers() {
        ticketViewModel.getToDoList().observe(getViewLifecycleOwner(), t -> loadData());
        ticketViewModel.getInProgressList().observe(getViewLifecycleOwner(), t -> loadData());
        ticketViewModel.getDoneList().observe(getViewLifecycleOwner(), t -> loadData());
    }

    private void initView(View view) {
        toDoSumTv = view.findViewById(R.id.toDoSumTv);
        toDoEnhTv = view.findViewById(R.id.toDoEnhTv);
        toDoBugTv = view.findViewById(R.id.toDoBugTv);
        inProgSumTv = view.findViewById(R.id.inProgSumTv);
        inProgEnhTv = view.findViewById(R.id.inProgEnhTv);
        inProgBugTv = view.findViewById(R.id.inProgBugTv);
        doneSumTv = view.findViewById(R.id.doneSumTv);
        doneEnhTv = view.findViewById(R.id.doneEnhTv);
        doneBugTv = view.findViewById(R.id.doneBugTv);
    }

    private void loadData() {

        List<Ticket> ticketsToDo = ticketViewModel.getToDoList().getValue();
        List<Ticket> ticketsInProgress = ticketViewModel.getInProgressList().getValue();
        List<Ticket> ticketsDone = ticketViewModel.getDoneList().getValue();

        int toDoEnh = 0, inProgEnh = 0, doneEnh = 0;
        int toDoBug = 0, inProgBug = 0, doneBug = 0;


        if (ticketsToDo != null)
        for (Ticket t : ticketsToDo) {
            if (t.getStatus().equals(Status.TO_DO)) {
                if (t.getType().equals(TicketType.ENHANCEMENT)) {
                    toDoEnh++;
                } else if (t.getType().equals(TicketType.BUG)) {
                    toDoBug++;
                }
            }
        }

        if (ticketsInProgress != null)
        for (Ticket t : ticketsInProgress) {
            if (t.getStatus().equals(Status.IN_PROGRESS)) {
                if (t.getType().equals(TicketType.ENHANCEMENT)) {
                    inProgEnh++;
                } else if (t.getType().equals(TicketType.BUG)) {
                    inProgBug++;
                }
            }
        }

        if (ticketsDone != null)
        for (Ticket t : ticketsDone) {
            if (t.getStatus().equals(Status.DONE)) {
                if (t.getType().equals(TicketType.ENHANCEMENT)) {
                    doneEnh++;
                } else if (t.getType().equals(TicketType.BUG)) {
                    doneBug++;
                }
            }
        }


        toDoSumTv.setText(String.valueOf(toDoEnh + toDoBug));
        toDoEnhTv.setText(String.valueOf(toDoEnh));
        toDoBugTv.setText(String.valueOf(toDoBug));

        inProgSumTv.setText(String.valueOf(inProgEnh + inProgBug));
        inProgEnhTv.setText(String.valueOf(inProgEnh));
        inProgBugTv.setText(String.valueOf(inProgBug));

        doneSumTv.setText(String.valueOf(doneEnh + doneBug));
        doneEnhTv.setText(String.valueOf(doneEnh));
        doneBugTv.setText(String.valueOf(doneBug));
    }

}
