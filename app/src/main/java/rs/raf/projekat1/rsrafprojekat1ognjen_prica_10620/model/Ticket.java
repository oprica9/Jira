package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Priority;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Status;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.TicketType;

public class Ticket implements Parcelable {

    private int id;
    private String title;
    private TicketType type;
    private Priority priority;
    private int estimation;
    private int loggedTime;
    private String description;
    private Status status;
    private String img;

    public Ticket(int id, int estimation, String title, String description, TicketType type, Priority priority) {
        this.id = id;
        this.estimation = estimation;
        this.title = title;
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.loggedTime = 0;
        this.status = Status.TO_DO;
    }

    protected Ticket(Parcel in) {
        id = in.readInt();
        title = in.readString();
        estimation = in.readInt();
        loggedTime = in.readInt();
        description = in.readString();
        img = in.readString();
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getEstimation() {
        return estimation;
    }

    public void setEstimation(int estimation) {
        this.estimation = estimation;
    }

    public int getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(int loggedTime) {
        this.loggedTime = loggedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", priority=" + priority +
                ", estimation=" + estimation +
                ", loggedTime=" + loggedTime +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", img='" + img + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
    }
}
