package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.Ticket;


public class TicketDiffItemCallback extends DiffUtil.ItemCallback<Ticket> {
    @Override
    public boolean areItemsTheSame(@NonNull Ticket oldItem, @NonNull Ticket newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Ticket oldItem, @NonNull Ticket newItem) {
        return oldItem.getDescription().equals(newItem.getDescription())
                && oldItem.getEstimation() == newItem.getEstimation()
                && oldItem.getLoggedTime() == newItem.getLoggedTime()
                && oldItem.getPriority().equals(newItem.getPriority())
                && oldItem.getType().equals(newItem.getType())
                && oldItem.getTitle().equals(newItem.getTitle())
                && oldItem.getStatus().equals(newItem.getStatus());
    }
}
