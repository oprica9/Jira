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


public class InProgressAdapter extends ListAdapter<Ticket, InProgressAdapter.ViewHolder> {

    private final TicketAction listener;

    public InProgressAdapter(@NonNull DiffUtil.ItemCallback<Ticket> diffCallback, TicketAction ticketAction) {
        super(diffCallback);
        this.listener = ticketAction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item_2, parent, false);
        return new ViewHolder(view, parent.getContext());
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
        private Button btnSendDone;
        private Button btnSendToDo;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            this.btnSendDone = itemView.findViewById(R.id.btnSendDone);
            this.btnSendToDo = itemView.findViewById(R.id.btnSendToDo);

        }

        public void bind(Ticket ticket, TicketAction listener) {
            ImageView imageView = itemView.findViewById(R.id.ticketPictureIv);

            Glide
                    .with(context)
                    .load(getImage(ticket.getImg()))
                    .into(imageView);
            ((TextView) itemView.findViewById(R.id.titleTv)).setText(ticket.getTitle());
            ((TextView) itemView.findViewById(R.id.descTv)).setText(ticket.getDescription());
            btnSendDone.setOnClickListener(v -> listener.moveTicket(ticket, Status.DONE));
            btnSendToDo.setOnClickListener(v -> listener.moveTicket(ticket, Status.TO_DO));
        }

        public int getImage(String imageName) {
            return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        }

    }

    public interface TicketAction {
        int moveTicket(Ticket ticket, Status status);
    }


}
