package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel.TicketViewModel;


public class NewTicketFragment extends Fragment {

    private Spinner spinType;
    private Spinner spinPriority;
    private EditText etEst;
    private EditText etTitle;
    private EditText etDesc;
    private Button btnAddTicket;

    private TicketViewModel ticketViewModel;

    public NewTicketFragment() {
        super(R.layout.fragment_new_ticket);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        ticketViewModel = new ViewModelProvider(requireActivity()).get(TicketViewModel.class);
        initView(view);
        initListeners(view);
    }

    private void initView(View view) {
        spinType = view.findViewById(R.id.spinType);
        spinType.setAdapter(ticketViewModel.getTypeAdapter(requireContext()));
        spinPriority = view.findViewById(R.id.spinPriority);
        spinPriority.setAdapter(ticketViewModel.getPriorityAdapter(requireContext()));
        etEst = view.findViewById(R.id.etEst);
        etTitle = view.findViewById(R.id.etTitle);
        etDesc = view.findViewById(R.id.etDesc);
        btnAddTicket = view.findViewById(R.id.btnAddTicket);
    }

    private void initListeners(View view) {
        btnAddTicket.setOnClickListener(v -> {
            String type = spinType.getSelectedItem().toString();
            String priority = spinPriority.getSelectedItem().toString();
            String est = etEst.getText().toString();
            String title = etTitle.getText().toString();
            String desc = etDesc.getText().toString();

            // pass values to viewModel
            int res = ticketViewModel.addTicket(type, priority, est, title, desc);
            if(res < 0) {
                Toast.makeText(requireContext(), "Something went wrong. Check if data entered is correct.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Success! Ticket has id " + res, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
