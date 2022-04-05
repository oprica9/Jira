package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel.TicketViewModel;

public class TicketDetailFragment extends Fragment {

    private TextView tvTitle;
    private TextView tvType;
    private TextView tvPriority;
    private TextView tvEstimation;
    private Button btnLoggedTime;
    private TextView tvDesc;

    private TicketViewModel ticketViewModel;

    public TicketDetailFragment() {
        super(R.layout.fragment_ticket_detail);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketViewModel = new ViewModelProvider(requireActivity()).get(TicketViewModel.class);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initListeners();
    }

    private void initView(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        tvType = view.findViewById(R.id.ticketType2);
        tvPriority = view.findViewById(R.id.priority2);
        tvEstimation = view.findViewById(R.id.estimation2);
        btnLoggedTime = view.findViewById(R.id.btnLoggedTime);
        tvDesc = view.findViewById(R.id.ticketDesc2);
    }

    private void initListeners() {
    }
    
    
}