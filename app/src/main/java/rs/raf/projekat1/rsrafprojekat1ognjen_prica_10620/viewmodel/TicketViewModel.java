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

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.Ticket;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Priority;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Status;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.TicketType;

public class TicketViewModel extends ViewModel {

    public static int counter = 0;

    private MutableLiveData<List<Ticket>> toDoList = new MutableLiveData<>();
    private MutableLiveData<List<Ticket>> inProgressList = new MutableLiveData<>();

    public LiveData<List<Ticket>> getToDoList() {
        return toDoList;
    }

    public LiveData<List<Ticket>> getInProgressList() {
        return inProgressList;
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

        List<Ticket> temp = null;
        if(toDoList.getValue() == null){
            temp = new ArrayList<>();
        } else {
            temp = new ArrayList<>(toDoList.getValue());
        }
        temp.add(t);
        toDoList.setValue(temp);

        return t.getId();
    }

    public int moveTicket(Ticket ticket, Status status) {
        // moving to in progress
        List<Ticket> toDoTemp;
        if (status.equals(Status.IN_PROGRESS)) {
            if(toDoList.getValue() == null){
                throw new NullPointerException("LISTEN MAN I DONT KNOW WHAT HAPPENED");
            } else {
                toDoTemp = new ArrayList<>(toDoList.getValue());
            }

            toDoTemp.remove(ticket);
            toDoList.setValue(toDoTemp);

            List<Ticket> inProgTemp;
            if(inProgressList.getValue() == null){
                inProgTemp = new ArrayList<>();
                // throw new NullPointerException("LISTEN MAN I DONT KNOW WHAT HAPPENED");
            } else {
                inProgTemp = new ArrayList<>(inProgressList.getValue());
            }
            inProgTemp.add(ticket);
            inProgressList.setValue(inProgTemp);

            return ticket.getId();
        }


        return -1;
    }

    public SpinnerAdapter getTypeAdapter(Context context) {
        List<String> a = new ArrayList<>();
        for (TicketType t: TicketType.values()) {
            a.add(t.name().charAt(0) + t.name().substring(1).toLowerCase());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public SpinnerAdapter getPriorityAdapter(Context context) {
        List<String> a = new ArrayList<>();
        for (Priority t: Priority.values()) {
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
