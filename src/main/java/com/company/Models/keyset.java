package com.company.Models;

public class keyset {
    public Ticket ticket;
    public String key;

    public keyset(Ticket ticket, String key) {
        this.ticket = ticket;
        this.key = key;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
