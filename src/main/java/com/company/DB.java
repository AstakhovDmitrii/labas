package com.company;

import com.company.Models.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public class DB {

    private Optional<java.sql.Connection> connection = Optional.empty();
    public static String db_user = "users";
    public static String db_tickets = "tickets";

    public DB(String url, String user, String password) {
        try {
            connection =  Optional.ofNullable(DriverManager.getConnection(url, user, password));
        } catch (SQLException e) {
            Main.logger.error(e.getMessage());
            Main.logger.info("база данных не доступна");
        }
    }

    public synchronized void AddUser(user user) {
        String sql = "INSERT INTO "
                + db_user + "(name, password) "
                + "VALUES(?, ?)";

        connection.flatMap((Function<java.sql.Connection, Optional<?>>) connection -> {
            Optional<Integer> Id = Optional.empty();
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, user.getName());
                statement.setString(2, DigestUtils.md5Hex(user.getPassword()));

                int numberOfInsertedRows = statement.executeUpdate();

                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            Id = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

            } catch (SQLException ex) {
                Main.logger.error(ex.getMessage());
                Main.logger.info("возникла ошибка в запросе");
                return Optional.empty();
            }

            return Id;
        });
    }
    public synchronized user GetUser(String username, String password) {
        try {
            return connection.flatMap(conn -> {
                Optional<user> customer = Optional.empty();
                String sql = "SELECT * FROM " + db_user + " WHERE \"name\" = '" + username + "' AND \"password\" ='" + DigestUtils.md5Hex(password) + "'";

                try (PreparedStatement statement = conn.prepareStatement(sql);
                     ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");

                        return Optional.of(
                                new user(username, password, id));
                    }
                } catch (SQLException e) {
                    Main.logger.error(e.getMessage());
                    Main.logger.info("возникла ошибка в запросе");
                    return Optional.empty();
                }
                return customer;
            }).orElseThrow();
        }
        catch (NoSuchElementException e){
            return null;
        }
    }
    public synchronized Collection<user> GetAllUsers(){
        Collection<user> customers = new ArrayList<>();
        String sql = "SELECT * FROM " + db_user;

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");

                    user customer = new user(name, password, id);

                    customers.add(customer);
                }

            } catch (SQLException e) {
                Main.logger.error(e.getMessage());
                Main.logger.info("возникла ошибка в запросе");
            }
        });
        return customers;
    }
    public synchronized void DeleteUser(user user) {
        String sql = "DELETE FROM " + db_user + " WHERE id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, user.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                Main.logger.error(e.getMessage());
                Main.logger.info("возникла ошибка в запросе");
            }
        });
    }
    public synchronized void DeleteUser(int id) {
        String sql = "DELETE FROM " + db_user + " WHERE id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                Main.logger.error(e.getMessage());
                Main.logger.info("возникла ошибка в запросе");
            }
        });
    }
    public synchronized void DeleteAllUsers() {
            String sql = "DELETE FROM " + db_user;

            connection.ifPresent(conn -> {
                try {
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    Main.logger.error(e.getMessage());
                    Main.logger.info("возникла ошибка в запросе");
                }
            });
    }


    public synchronized void AddTicket(Ticket ticket, String key) {
        String sql = "INSERT INTO "
                + db_tickets + " (\"name\", \"creationDate\", \"ticketType\" , \"x\", \"y\", \"price\", \"height\", \"weight\", \"create_user\", \"key\") "
                + "VALUES(?, ?, ?, ?, ?, ? , ? ,? ,?,?)";

        connection.flatMap((Function<java.sql.Connection, Optional<?>>) connection -> {
            Optional<Integer> Id = Optional.empty();
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, ticket.getName());
                statement.setString(2, ticket.getCreationDate().toString());
                statement.setString(3, ticket.getType().toString());
                statement.setDouble(4, ticket.getCoordinates().getX());
                statement.setFloat(5, ticket.getCoordinates().getY());
                statement.setFloat(6, ticket.getPrice());
                statement.setInt(7 , (int)ticket.getPerson().getHeight());
                statement.setInt(8 , (int)ticket.getPerson().getWeight());

                statement.setInt(9, 1);
                statement.setString(10, key);


                int numberOfInsertedRows = statement.executeUpdate();

                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            Id = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

            } catch (SQLException e) {
                Main.logger.error(e.getMessage());
                Main.logger.info("возникла ошибка в запросе");
                return Optional.empty();
            }

            return Id;
        });
    }
    public synchronized void DeleteAllTickets() {
        String sql = "DELETE FROM " + db_tickets;

        connection.ifPresent(conn -> {
            try {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.executeUpdate();
            } catch (SQLException e) {
                Main.logger.error(e.getMessage());
                Main.logger.info("возникла ошибка в запросе");
            }
        });
    }
    public synchronized keyset GetTicket(int id) {
        return  connection.flatMap(conn -> {
            Optional<keyset> customer = Optional.empty();
            String sql = "SELECT * FROM " + db_tickets + " WHERE id = " + id;

            try (PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    ZonedDateTime Create = ZonedDateTime.parse(resultSet.getString("creationDate"));
                    TicketType ticketType = TicketType.valueOf(resultSet.getString("ticketType"));
                    double x = resultSet.getDouble("x");
                    float y = resultSet.getFloat("y");
                    float price = resultSet.getFloat("price");
                    Person person = new Person(resultSet.getLong("height"), resultSet.getLong("weight"));
                    int id_ = resultSet.getInt("id");
                    int crete = resultSet.getInt("create_user");
                    Ticket ticket = new Ticket(id_,name, new Coordinates(x, y), price, ticketType, person, crete);
                    ticket.setCreationDate(Create);
                    return Optional.of(new keyset(ticket, resultSet.getString("key")));
                }
            } catch (SQLException e) {
                Main.logger.error(e.getMessage());
                Main.logger.info("возникла ошибка в запросе");
                return Optional.empty();
            }
            return customer;
        }).orElseThrow();
    }
    public synchronized Collection<keyset> GetAllTicket(){
        Collection<keyset> tickets = new ArrayList<>();
        String sql = "SELECT * FROM " + db_tickets;

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    ZonedDateTime Create = ZonedDateTime.parse(resultSet.getString("creationDate"));
                    TicketType ticketType = TicketType.valueOf(resultSet.getString("ticketType"));
                    double x = resultSet.getDouble("x");
                    float y = resultSet.getFloat("y");
                    float price = resultSet.getFloat("price");
                    Person person = new Person(resultSet.getLong("height"), resultSet.getLong("weight"));
                    int id_ = resultSet.getInt("id");
                    int create = resultSet.getInt("create_user");
                    Ticket ticket = new Ticket(id_,name, new Coordinates(x, y), price, ticketType, person, create);
                    ticket.setCreationDate(Create);
                    keyset keyset = new keyset(ticket, resultSet.getString("key"));

                    tickets.add(keyset);
                }

            } catch (SQLException e) {
                Main.logger.error(e.getMessage());
                Main.logger.info("возникла ошибка в запросе");
            }
        });
        return tickets;
    }
}
