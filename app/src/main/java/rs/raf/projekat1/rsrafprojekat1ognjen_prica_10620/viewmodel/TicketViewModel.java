package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.Ticket;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Priority;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Status;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.TicketType;

public class TicketViewModel extends ViewModel {

    public static int counter = 0;

    private MutableLiveData<List<Ticket>> toDoList = new MutableLiveData<>();
    private MutableLiveData<List<Ticket>> inProgressList = new MutableLiveData<>();
    private MutableLiveData<List<Ticket>> doneList = new MutableLiveData<>();

    // makeshift database
    private List<Ticket> todo = new ArrayList<>();
    private List<Ticket> inProgress = new ArrayList<>();
    private List<Ticket> done = new ArrayList<>();

    public LiveData<List<Ticket>> getToDoList() {
        return toDoList;
    }
    public LiveData<List<Ticket>> getInProgressList() {
        return inProgressList;
    }
    public LiveData<List<Ticket>> getDoneList() {
        return doneList;
    }

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
        List<Ticket> temp;
        if (status.equals(Status.IN_PROGRESS)) {
            if (toDoList.getValue() == null) {
                throw new NullPointerException("LISTEN MAN I DONT KNOW WHAT HAPPENED");
            } else {
                temp = new ArrayList<>(toDoList.getValue());
            }

            temp.remove(ticket);
            ticket.setStatus(status);
            toDoList.setValue(temp);

            List<Ticket> inProgTemp;
            if (inProgressList.getValue() == null) {
                inProgTemp = new ArrayList<>();
                // throw new NullPointerException("LISTEN MAN I DONT KNOW WHAT HAPPENED");
            } else {
                inProgTemp = new ArrayList<>(inProgressList.getValue());
            }
            inProgTemp.add(ticket);
            inProgressList.setValue(inProgTemp);

            return ticket.getId();

        }
        // moving to done
        else if (status.equals(Status.DONE)) {
            if (inProgressList.getValue() == null) {
                throw new NullPointerException("LISTEN MAN I DONT KNOW WHAT HAPPENED");
            } else {
                temp = new ArrayList<>(inProgressList.getValue());
            }

            temp.remove(ticket);
            ticket.setStatus(status);
            inProgressList.setValue(temp);

            List<Ticket> doneTemp;
            if (doneList.getValue() == null) {
                doneTemp = new ArrayList<>();
                // throw new NullPointerException("LISTEN MAN I DONT KNOW WHAT HAPPENED");
            } else {
                doneTemp = new ArrayList<>(doneList.getValue());
            }
            doneTemp.add(ticket);
            doneList.setValue(doneTemp);

            return ticket.getId();
        } else if (status.equals(Status.TO_DO)) {
            if (inProgressList.getValue() == null) {
                throw new NullPointerException("LISTEN MAN I DONT KNOW WHAT HAPPENED");
            } else {
                temp = new ArrayList<>(inProgressList.getValue());
            }

            temp.remove(ticket);
            ticket.setStatus(status);
            inProgressList.setValue(temp);

            List<Ticket> toDoTemp;
            if (toDoList.getValue() == null) {
                toDoTemp = new ArrayList<>();
                // throw new NullPointerException("LISTEN MAN I DONT KNOW WHAT HAPPENED");
            } else {
                toDoTemp = new ArrayList<>(toDoList.getValue());
            }
            toDoTemp.add(ticket);
            toDoList.setValue(toDoTemp);

            return ticket.getId();
        }


        return -1;
    }

    public int deleteTicket(Ticket t) {
        List<Ticket> temp = null;
        if (toDoList.getValue() == null) {
            throw new NullPointerException("I DUNNO MAN");
        } else {
            temp = new ArrayList<>(toDoList.getValue());
            temp.remove(t);
            toDoList.setValue(temp);
            return t.getId();
        }
    }

    public void filterTickets(String filter, Status status) {

        List<Ticket> tickets = null;
        if (status.equals(Status.TO_DO))
            tickets = toDoList.getValue();
        else if (status.equals(Status.IN_PROGRESS))
            tickets = inProgressList.getValue();
        else if (status.equals(Status.DONE))
            tickets = doneList.getValue();

        List<Ticket> filteredList;
        if (tickets != null) {
            filteredList = tickets.stream().filter(ticket -> ticket.getTitle().toLowerCase().startsWith(filter.toLowerCase())).collect(Collectors.toList());

        } else {
            filteredList = new ArrayList<>();
        }

        if (status.equals(Status.TO_DO)) {
            toDoList.setValue(filteredList);
        }
        else if (status.equals(Status.IN_PROGRESS)) {
            inProgressList.setValue(filteredList);
        }
        else if (status.equals(Status.DONE)) {
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
}
