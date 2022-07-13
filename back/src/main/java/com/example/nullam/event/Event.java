package com.example.nullam.event;

import com.example.nullam.company.Company;
import com.example.nullam.person.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "event")
@Table(name = "event")
@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    @JsonIgnore
    private Long id;
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(name = "time", nullable = false)
    private LocalDateTime time;
    @Column(name = "location", nullable = false, columnDefinition = "TEXT")
    private String location;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @JsonIgnore
    @ManyToMany(mappedBy = "events")
    private List<Person> personParticipants = new ArrayList<>();
    @JsonIgnore
    @ManyToMany(mappedBy = "events")
    private List<Company> companyParticipants = new ArrayList<>();

    public Event(Long id, String name, LocalDateTime time, String location, String description) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.location = location;
        this.description = description;
    }

}
