package es.overon.poc.notes.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String firstName;

    @Column(name = "PASS")
    private String passw;



    public User(String firstName, String passw) {
        this.firstName = firstName;
        this.passw = passw;

    }

    public User() {

    }
}
