package rs.raf.projekat1.ognjen_prica_10620.view.recycler.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.function.Consumer;

import ognjen_prica_10620.R;
import rs.raf.projekat1.ognjen_prica_10620.model.Ticket;

public class ToDoAdapter extends ListAdapter<Ticket, ToDoAdapter.ViewHolder> {

    private final boolean admin;
    private final Consumer<Ticket> moveTicket;
    private final Consumer<Ticket> deleteTicket;
    private final Consumer<Ticket> detailTicket;

    public ToDoAdapter(@NonNull DiffUtil.ItemCallback<Ticket> diffCallback, boolean admin, Consumer<Ticket> moveTicket, Consumer<Ticket> deleteTicket, Consumer<Ticket> detailTicket) {
        super(diffCallback);
        this.admin = admin;
        this.moveTicket = moveTicket;
        this.deleteTicket = deleteTicket;
        this.detailTicket = detailTicket;
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
        return new ViewHolder(
                view,
                parent.getContext(),
                admin,
                position -> {
                    Ticket ticket = getItem(position);
                    moveTicket.accept(ticket);
                },
                position -> {
                    Ticket ticket = getItem(position);
                    deleteTicket.accept(ticket);
                },
                position -> {
                    Ticket ticket = getItem(position);
                    detailTicket.accept(ticket);
                });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = getItem(position);
        holder.bind(ticket);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context, boolean admin, Consumer<Integer> moveItem, Consumer<Integer> deleteItem, Consumer<Integer> detail) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                    detail.accept(getBindingAdapterPosition());
            });
            if (admin) {

                itemView.findViewById(R.id.btnSendInProgress).setOnClickListener(v -> {
                    if (getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                        moveItem.accept(getBindingAdapterPosition());
                });

                itemView.findViewById(R.id.btnDelete).setOnClickListener(v -> {
                    if (getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                        deleteItem.accept(getBindingAdapterPosition());
                });


            } else {
                itemView.findViewById(R.id.btnMov1).setOnClickListener(v -> {
                    if (getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                        moveItem.accept(getBindingAdapterPosition());
                });
            }
        }

        public void bind(Ticket ticket) {
            ImageView imageView = itemView.findViewById(R.id.ticketPictureIv);
            Glide
                    .with(context)
                    .load(getImage(ticket.getImg()))
                    .into(imageView);
            ((TextView) itemView.findViewById(R.id.titleTv)).setText(ticket.getTitle());
            ((TextView) itemView.findViewById(R.id.descTv)).setText(ticket.getDescription());
        }

        public int getImage(String imageName) {
            return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        }
    }

}
