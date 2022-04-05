package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.Ticket;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Priority;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Status;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.TicketType;

public class TicketViewModel extends ViewModel {

    public static int counter = 0;

    private final MutableLiveData<List<Ticket>> toDoList;
    private final MutableLiveData<List<Ticket>> inProgressList;
    private final MutableLiveData<List<Ticket>> doneList;

    // dumpster-fire below
    private final MutableLiveData<Ticket> detailTicket;

    // makeshift database
    private final List<Ticket> todo = new ArrayList<>();
    private final List<Ticket> inProgress = new ArrayList<>();
    private final List<Ticket> done = new ArrayList<>();

    public TicketViewModel() {
        this.toDoList = new MutableLiveData<>();
        this.inProgressList = new MutableLiveData<>();
        this.doneList = new MutableLiveData<>();
        this.detailTicket = new MutableLiveData<>();
    }

    public LiveData<List<Ticket>> getToDoList() {
        return toDoList;
    }

    public LiveData<List<Ticket>> getInProgressList() {
        return inProgressList;
    }

    public LiveData<List<Ticket>> getDoneList() {
        return doneList;
    }

    // todo handle if filter is active

    public int addTicket(String type, String priority, String est, String title, String desc) {

        if (!validate(type, priority, est, title, desc)) {
            System.out.println("DATA INVALID");
            return -1;
        }

        TicketType tempType = TicketType.valueOf(type.toUpperCase());
        Priority tempPriority = Priority.valueOf(priority.toUpperCase());

        Ticket t = new Ticket(
                counter++,
                Integer.parseInt(est),
                title,
                desc,
                tempType,
                tempPriority
        );
        if (tempType.equals(TicketType.BUG)) {
            t.setImg("img_bug");
        } else {
            t.setImg("img_enh");
        }

        todo.add(t);
        toDoList.setValue(new ArrayList<>(todo));

        return t.getId();
    }

    public int moveTicket(Ticket ticket, Status status) {
        // moving to in progress
        if (status.equals(Status.IN_PROGRESS)) {

            todo.remove(ticket);
            ticket.setStatus(status);
            toDoList.setValue(new ArrayList<>(todo));

            inProgress.add(ticket);
            inProgressList.setValue(new ArrayList<>(inProgress));

            return ticket.getId();

        }
        // moving to done
        else if (status.equals(Status.DONE)) {

            inProgress.remove(ticket);
            ticket.setStatus(status);
            inProgressList.setValue(new ArrayList<>(inProgress));

            done.add(ticket);
            doneList.setValue(new ArrayList<>(done));

            return ticket.getId();

        } else if (status.equals(Status.TO_DO)) {

            inProgress.remove(ticket);
            ticket.setStatus(status);
            inProgressList.setValue(new ArrayList<>(inProgress));

            todo.add(ticket);
            toDoList.setValue(new ArrayList<>(todo));

            return ticket.getId();
        }

        return -1;
    }

    public int deleteTicket(Ticket t) {
        Optional<Ticket> ticketOptional = todo.stream().filter(ticket -> ticket.equals(t)).findFirst();
        if (ticketOptional.isPresent()) {
            todo.remove(ticketOptional.get());
            toDoList.setValue(new ArrayList<>(todo));
        }
        return t.getId();
    }

    public void filterTickets(String filter, Status status) {

        List<Ticket> tickets = null;
        if (status.equals(Status.TO_DO))
            tickets = todo;
        else if (status.equals(Status.IN_PROGRESS))
            tickets = inProgress;
        else if (status.equals(Status.DONE))
            tickets = done;

        List<Ticket> filteredList;
        if (tickets != null) {
            filteredList = tickets.stream().filter(ticket -> ticket.getTitle().toLowerCase().startsWith(filter.toLowerCase())).collect(Collectors.toList());

        } else {
            filteredList = new ArrayList<>();
        }

        if (status.equals(Status.TO_DO)) {
            toDoList.setValue(filteredList);
        } else if (status.equals(Status.IN_PROGRESS)) {
            inProgressList.setValue(filteredList);
        } else if (status.equals(Status.DONE)) {
            doneList.setValue(filteredList);
        }

    }

    // misc

    public SpinnerAdapter getTypeAdapter(Context context) {
        List<String> a = new ArrayList<>();
        for (TicketType t : TicketType.values()) {
            a.add(t.name().charAt(0) + t.name().substring(1).toLowerCase());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public SpinnerAdapter getPriorityAdapter(Context context) {
        List<String> a = new ArrayList<>();
        for (Priority t : Priority.values()) {
            a.add(t.name().charAt(0) + t.name().substring(1).toLowerCase());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    // private

    private boolean validate(String type, String priority, String est, String title, String desc) {
        if (type != null && priority != null
                && est != null && title != null
                && desc != null
                && !est.equals("") && !title.equals("") && !desc.equals("")) {
            try {
                Integer.parseInt(est);
                return true;
            } catch (NumberFormatException e) {
                System.out.println("NUMBER EXCEPTION");
                return false;
            }
        }
        return false;
    }

    public Ticket getTicket(int ticketId) {
        Optional<Ticket> ticketOptional = todo.stream().filter(ticket -> ticket.getId() == ticketId).findFirst();

        if (ticketOptional.isPresent()) {
            return ticketOptional.get();
        }
        ticketOptional = inProgress.stream().filter(ticket -> ticket.getId() == ticketId).findFirst();
        if (ticketOptional.isPresent()) {
            return ticketOptional.get();
        }
        ticketOptional = done.stream().filter(ticket -> ticket.getId() == ticketId).findFirst();
        return ticketOptional.orElse(null);
    }

    public void incrementLoggedTime(Ticket t) {
        Optional<Ticket> ticketOptional;
        switch (t.getStatus()) {
            case TO_DO: ticketOptional = todo.stream().filter(ticket -> ticket.equals(t)).findFirst(); break;
            case IN_PROGRESS: ticketOptional = inProgress.stream().filter(ticket -> ticket.equals(t)).findFirst(); break;
            case DONE: ticketOptional = done.stream().filter(ticket -> ticket.equals(t)).findFirst(); break;
            default: ticketOptional = Optional.empty();
        }
        ticketOptional.ifPresent(ticket -> ticket.setLoggedTime(ticket.getLoggedTime() + 1));
        ticketOptional.ifPresent(detailTicket::setValue);
    }

    public boolean decrementLoggedTime(Ticket t) {
        Optional<Ticket> ticketOptional;
        switch (t.getStatus()) {
            case TO_DO: ticketOptional = todo.stream().filter(ticket -> ticket.equals(t)).findFirst(); break;
            case IN_PROGRESS: ticketOptional = inProgress.stream().filter(ticket -> ticket.equals(t)).findFirst(); break;
            case DONE: ticketOptional = done.stream().filter(ticket -> ticket.equals(t)).findFirst(); break;
            default: ticketOptional = Optional.empty();
        }
        if (t.getLoggedTime() > 0) {
            ticketOptional.ifPresent(ticket -> ticket.setLoggedTime(ticket.getLoggedTime() - 1));
            ticketOptional.ifPresent(detailTicket::setValue);
        }
            return true;
    }

    public LiveData<Ticket> getDetailTicket() {
        return detailTicket;
    }

    public void setDetailTicket(int ticketId) {
        Optional<Ticket> ticketOptional = todo.stream().filter(ticket -> ticket.getId() == ticketId).findFirst();
        ticketOptional.ifPresent(detailTicket::setValue);

        ticketOptional = inProgress.stream().filter(ticket -> ticket.getId() == ticketId).findFirst();
        ticketOptional.ifPresent(detailTicket::setValue);

        ticketOptional = done.stream().filter(ticket -> ticket.getId() == ticketId).findFirst();
        ticketOptional.ifPresent(detailTicket::setValue);
    }

    public void removeDetailsTicket() {
        detailTicket.setValue(null);
    }

    public int editTicket(Ticket ticket, String type, String priority, String est, String title, String desc) {
        if (!validate(type, priority, est, title, desc)) {
            System.out.println("DATA INVALID");
            return -1;
        }

        TicketType tempType = TicketType.valueOf(type.toUpperCase());
        Priority tempPriority = Priority.valueOf(priority.toUpperCase());

        ticket.setPriority(tempPriority);
        ticket.setType(tempType);
        ticket.setEstimation(Integer.parseInt(est));
        ticket.setTitle(title);
        ticket.setDescription(desc);

        if (tempType.equals(TicketType.BUG)) {
            ticket.setImg("img_bug");
        } else {
            ticket.setImg("img_enh");
        }

        if (ticket.getStatus().equals(Status.TO_DO)) {
            for (Ticket t: todo) {
                if (t.getId() == ticket.getId()) {
                    t.setPriority(ticket.getPriority());
                    t.setType(ticket.getType());
                    t.setEstimation(ticket.getEstimation());
                    t.setTitle(ticket.getTitle());
                    t.setDescription(ticket.getDescription());
                    toDoList.setValue(new ArrayList<>(todo));
                    break;
                }
            }
        } else if (ticket.getStatus().equals(Status.IN_PROGRESS)) {
            for (Ticket t: inProgress) {
                if (t.getId() == ticket.getId()) {
                    t.setPriority(ticket.getPriority());
                    t.setType(ticket.getType());
                    t.setEstimation(ticket.getEstimation());
                    t.setTitle(ticket.getTitle());
                    t.setDescription(ticket.getDescription());
                    inProgressList.setValue(new ArrayList<>(inProgress));
                    break;
                }
            }
        } else if (ticket.getStatus().equals(Status.DONE)) {
            for (Ticket t: done) {
                if (t.getId() == ticket.getId()) {
                    t.setPriority(ticket.getPriority());
                    t.setType(ticket.getType());
                    t.setEstimation(ticket.getEstimation());
                    t.setTitle(ticket.getTitle());
                    t.setDescription(ticket.getDescription());
                    doneList.setValue(new ArrayList<>(done));
                    break;
                }
            }
        }
        return ticket.getId();
    }
}
