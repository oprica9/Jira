package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Priority;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.TicketType;

public class TicketDto {

    private TicketType type;
    private Priority priority;
    private int est;
    private String title;
    private String desc;

    public TicketDto() {
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getEst() {
        return est;
    }

    public void setEst(int est) {
        this.est = est;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
