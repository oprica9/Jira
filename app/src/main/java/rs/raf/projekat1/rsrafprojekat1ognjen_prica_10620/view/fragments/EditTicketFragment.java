package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments;

import static rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments.TicketDetailFragment.EDIT_TICKET;
import static rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments.ToDoFragment.TICKET_DETAIL;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.Ticket;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Priority;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.TicketType;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel.TicketViewModel;


public class EditTicketFragment extends Fragment {

    private Spinner spinType;
    private Spinner spinPriority;
    private EditText etEst;
    private EditText etTitle;
    private EditText etDesc;
    private Button btnSaveTicket;

    private TicketViewModel ticketViewModel;

    private Ticket ticket;

    public EditTicketFragment() {
        super(R.layout.fragment_edit_ticket);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketViewModel = new ViewModelProvider(requireActivity()).get(TicketViewModel.class);
        if(getArguments() != null) {
            ticket = ticketViewModel.getTicket(getArguments().getInt(EDIT_TICKET, -1));
        }
        init(view);
    }

    private void init(View view) {
        initView(view);
        loadData();
        initListeners(view);
    }

    private void initView(View view) {
        spinType = view.findViewById(R.id.spinType2);
        spinType.setAdapter(ticketViewModel.getTypeAdapter(requireContext()));
        spinPriority = view.findViewById(R.id.spinPriority2);
        spinPriority.setAdapter(ticketViewModel.getPriorityAdapter(requireContext()));
        etEst = view.findViewById(R.id.etEst2);
        etTitle = view.findViewById(R.id.etTitle2);
        etDesc = view.findViewById(R.id.etDesc2);
        btnSaveTicket = view.findViewById(R.id.btnSaveTicket);
    }

    private void loadData() {
        if (ticket != null) {
            int c1 = 0, c2 = 0;
            for (TicketType t: TicketType.values()) {
                if(t.equals(ticket.getType()))
                    break;
                c1++;
            }
            for (Priority p: Priority.values()) {
                if(p.equals(ticket.getPriority()))
                    break;
                c2++;
            }
            spinType.setSelection(c1);
            spinPriority.setSelection(c2);
            etEst.setText(String.valueOf(ticket.getEstimation()));
            etTitle.setText(String.valueOf(ticket.getTitle()));
            etDesc.setText(ticket.getDescription());
        }
    }

    private void initListeners(View view) {
        btnSaveTicket.setOnClickListener(v -> {
            String type = spinType.getSelectedItem().toString();
            String priority = spinPriority.getSelectedItem().toString();
            String est = etEst.getText().toString();
            String title = etTitle.getText().toString();
            String desc = etDesc.getText().toString();

            // pass values to viewModel
            if (getArguments() != null) {
                int res = ticketViewModel.editTicket(ticket, type, priority, est, title, desc);
                if(res < 0) {
                    Toast.makeText(requireContext(), "Something went wrong. Check if data entered is correct.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Success! Ticket edited id " + res, Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.remove(this);
                    transaction.commit();
                    requireActivity().getSupportFragmentManager().popBackStackImmediate();
                }
            }
        });
    }
}
