package com.wynnbet.playerservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "teamId")
    private Long teamId;

    @Column(name = "started_date")
    private LocalDateTime startedDate;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

}
