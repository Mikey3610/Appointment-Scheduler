package model;

import java.sql.Timestamp;

public class Appointments {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private int contact;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private int customerId;
    private int userId;

    public Appointments(int appointmentId, String title, String description, String location, int contact, String type, Timestamp startDateTime, Timestamp endDateTime, int customerId, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
    }
}
