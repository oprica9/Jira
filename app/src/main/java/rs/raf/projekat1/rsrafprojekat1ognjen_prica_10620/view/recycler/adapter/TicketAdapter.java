package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.Ticket;

public class TicketAdapter extends ListAdapter<Ticket, TicketAdapter.ViewHolder> {

    public TicketAdapter(@NonNull DiffUtil.ItemCallback<Ticket> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = getItem(position);
        holder.bind(ticket);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            itemView.findViewById(R.id.btnMov1).setOnClickListener(v -> {
                Toast.makeText(context, "MOVE IT", Toast.LENGTH_SHORT).show();
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
