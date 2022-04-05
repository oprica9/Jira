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

import java.util.function.Consumer;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.Ticket;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Status;


public class InProgressAdapter extends ListAdapter<Ticket, InProgressAdapter.ViewHolder> {

    private final Consumer<Ticket> moveL;
    private final Consumer<Ticket> moveR;
    private final Consumer<Ticket> detail;

    public InProgressAdapter(@NonNull DiffUtil.ItemCallback<Ticket> diffCallback, Consumer<Ticket> moveL, Consumer<Ticket> moveR, Consumer<Ticket> detail) {
        super(diffCallback);
        this.moveL = moveL;
        this.moveR = moveR;
        this.detail = detail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item_2, parent, false);
        return new ViewHolder(
                view,
                parent.getContext(),
                position -> {
                    Ticket ticket = getItem(position);
                    moveL.accept(ticket);
                },
                position -> {
                    Ticket ticket = getItem(position);
                    moveR.accept(ticket);
                },
                position -> {
                    Ticket ticket = getItem(position);
                    detail.accept(ticket);
                });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = getItem(position);
        holder.bind(ticket);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> moveL, Consumer<Integer> moveR, Consumer<Integer> detail) {
            super(itemView);
            this.context = context;

            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                    detail.accept(getBindingAdapterPosition());
            });

            itemView.findViewById(R.id.btnSendDone).setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                    moveR.accept(getBindingAdapterPosition());
            });

            itemView.findViewById(R.id.btnSendToDo).setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION)
                    moveL.accept(getBindingAdapterPosition());
            });
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
