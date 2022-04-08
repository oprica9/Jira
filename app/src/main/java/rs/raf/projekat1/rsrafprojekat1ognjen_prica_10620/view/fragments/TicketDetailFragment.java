package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments;

import static rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments.ToDoFragment.TICKET_DETAIL;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.Ticket;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Status;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel.TicketViewModel;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel.UserViewModel;
import timber.log.Timber;

public class TicketDetailFragment extends Fragment {

    public static final String EDIT_TICKET = "editTicket";
    public static final String EDIT_TICKET_FRAGMENT_TAG = "edit_ticket_fragment_tag";

    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvType;
    private TextView tvPriority;
    private TextView tvEstimation;
    private Button btnLoggedTime;
    private TextView tvDesc;
    private Button btnEdit;

    private TicketViewModel ticketViewModel;
    private UserViewModel userViewModel;
    private Ticket ticket;

    public TicketDetailFragment() {
        super(R.layout.fragment_ticket_detail);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ticketViewModel = new ViewModelProvider(requireActivity()).get(TicketViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        if(getArguments() != null) {
            ticketViewModel.setDetailTicket(getArguments().getInt(TICKET_DETAIL, -1));
            ticket = ticketViewModel.getTicket(getArguments().getInt(TICKET_DETAIL, -1));
        }
        init(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ticketViewModel.removeDetailsTicket();
    }

    private void init(View view) {
        initView(view);
        loadData();
        initListeners();
        initObservers();
    }

    private void initObservers() {
        ticketViewModel.getDetailTicket().observe(getViewLifecycleOwner(), ticket1 -> btnLoggedTime.setText(String.valueOf(ticket1.getLoggedTime())));
    }

    private void initView(View view) {
        imageView = view.findViewById(R.id.ticketTypePic);
        tvTitle = view.findViewById(R.id.ticketTitle);
        tvType = view.findViewById(R.id.ticketType2);
        tvPriority = view.findViewById(R.id.priority2);
        tvEstimation = view.findViewById(R.id.estimation2);
        btnLoggedTime = view.findViewById(R.id.btnLoggedTime);
        tvDesc = view.findViewById(R.id.ticketDesc2);
        btnEdit = view.findViewById(R.id.btnEdit);

        if(!userViewModel.isAdmin()) {
            btnEdit.setVisibility(View.INVISIBLE);
            btnEdit.setEnabled(false);
        } else {
            if (ticket.getStatus().equals(Status.DONE)) {
                btnEdit.setVisibility(View.INVISIBLE);
                btnEdit.setEnabled(false);
            }
        }

    }

    private void loadData() {
        if (ticket != null) {
            Glide
                    .with(this)
                    .load(getImage(ticket.getImg()))
                    .into(imageView);

            tvTitle.setText(ticket.getTitle());
            String type = ticket.getType().name().charAt(0) + ticket.getType().name().substring(1).toLowerCase();
            tvType.setText(type);
            String priority = ticket.getPriority().name().charAt(0) + ticket.getPriority().name().substring(1).toLowerCase();
            tvPriority.setText(priority);
            tvEstimation.setText(String.valueOf(ticket.getEstimation()));
            btnLoggedTime.setText(String.valueOf(ticket.getLoggedTime()));
            tvDesc.setText(ticket.getDescription());
        }
    }

    public int getImage(String imageName) {
        return requireContext().getResources().getIdentifier(imageName, "drawable", requireContext().getPackageName());
    }

    private void initListeners() {
        btnLoggedTime.setOnClickListener(v -> ticketViewModel.incrementLoggedTime(ticket));
        btnLoggedTime.setOnLongClickListener(v -> ticketViewModel.decrementLoggedTime(ticket));
        btnEdit.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putInt(EDIT_TICKET, ticket.getId());

            EditTicketFragment fragment = new EditTicketFragment();
            fragment.setArguments(args);

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fcvMain, fragment, EDIT_TICKET_FRAGMENT_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }
    
    
}