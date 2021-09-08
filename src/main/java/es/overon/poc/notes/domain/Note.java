package es.overon.poc.notes.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CREATE_MODIFY_NOTE")
    private Date createModifyDate;
    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    @Column(name = "BODY")
    private String body;


}
