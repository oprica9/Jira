package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.Ticket;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Status;


public class ToDoAdapter extends ListAdapter<Ticket, ToDoAdapter.ViewHolder> {

    private final TicketAction listener;
    private final boolean admin;

    public ToDoAdapter(@NonNull DiffUtil.ItemCallback<Ticket> diffCallback, boolean admin, TicketAction ticketAction) {
        super(diffCallback);
        this.listener = ticketAction;
        this.admin = admin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (admin) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item_admin, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item, parent, false);
        }
        return new ViewHolder(view, parent.getContext(), admin);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = getItem(position);
        holder.ticket = ticket;
        holder.bind(ticket, listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final Context context;
        private Ticket ticket;
        private final boolean admin;
        private Button btnMov;
        private Button btnDel;

        public ViewHolder(@NonNull View itemView, Context context, boolean admin) {
            super(itemView);
            this.context = context;
            this.admin = admin;
            if(admin) {
                this.btnMov = itemView.findViewById(R.id.btnSendInProgress);
                this.btnDel = itemView.findViewById(R.id.btnDelete);
            } else {
                this.btnMov = itemView.findViewById(R.id.btnMov1);
            }

        }

        public void bind(Ticket ticket, TicketAction listener) {
            ImageView imageView = itemView.findViewById(R.id.ticketPictureIv);

            Glide
                    .with(context)
                    .load(getImage(ticket.getImg()))
                    .into(imageView);
            ((TextView) itemView.findViewById(R.id.titleTv)).setText(ticket.getTitle());
            ((TextView) itemView.findViewById(R.id.descTv)).setText(ticket.getDescription());
            btnMov.setOnClickListener(v -> listener.moveTicket(ticket, Status.IN_PROGRESS));
            if (admin) {
                btnDel.setOnClickListener(v -> listener.deleteTicket(ticket));
            }

        }

        public int getImage(String imageName) {
            return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        }

    }
    public interface TicketAction {
        int moveTicket(Ticket ticket, Status status);
        int deleteTicket(Ticket t);
    }


}
