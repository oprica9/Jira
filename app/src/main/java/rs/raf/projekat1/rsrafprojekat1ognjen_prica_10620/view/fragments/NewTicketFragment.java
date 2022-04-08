package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.TicketType;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.spinner.SpinnerAdapter;
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
        // ne uzimam od enuma zbog prevoda, too late now to do it right...
        spinType = view.findViewById(R.id.spinType);
        List<String> a = new ArrayList<>();
        a.add(requireContext().getString(R.string.bug));
        a.add(requireContext().getString(R.string.enhancement));
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item, a);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // spinType.setAdapter(ticketViewModel.getTypeAdapter(requireContext()));
        spinType.setAdapter(spinnerAdapter);
        a = new ArrayList<>();
        a.add(requireContext().getString(R.string.highest));
        a.add(requireContext().getString(R.string.high));
        a.add(requireContext().getString(R.string.medium));
        a.add(requireContext().getString(R.string.low));
        a.add(requireContext().getString(R.string.lowest));
        spinnerAdapter = new SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item, a);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPriority = view.findViewById(R.id.spinPriority);
        // spinPriority.setAdapter(ticketViewModel.getPriorityAdapter(requireContext()));
        spinPriority.setAdapter(spinnerAdapter);
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
