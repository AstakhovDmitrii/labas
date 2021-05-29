
package com.company.Models;

import com.company.Main;

import java.time.ZonedDateTime;

public class Ticket implements Comparable<Ticket>{
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null

    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float price; //Значение поля должно быть больше 0
    private TicketType type; //Поле может быть null
    private Person person; //Поле не может быть null
    public int create;

    public int getCreate() {
        return create;
    }

    public void setCreate(int create) {
        this.create = create;
    }

    public Ticket() {
        creationDate = ZonedDateTime.now();
        coordinates = new Coordinates();
        person = new Person();
        id = Main.ids;
        Main.ids++;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "\n\tid=" + id +
                ",\n\tname='" + name + '\'' +
                ",\n\tcoordinates=" + coordinates +
                ",\n\tcreationDate=" + creationDate +
                ",\n\tprice=" + price +
                ",\n\ttype=" + type +
                ",\n\tperson=" + person +
                "\n}";
    }


    public Ticket(Integer id, String name, Coordinates coordinates, float price, TicketType type, Person person, int create) {
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        this.creationDate = ZonedDateTime.now();
        setPrice(price);
        setType(type);
        setPerson(person);
        this.create = create;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int compareTo(Ticket ticket) {
        return ticket.getName().length() - name.length();
    }
}