package com.example.nullam.person;

import com.example.nullam.event.Event;
import com.example.nullam.paymentmethod.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity (name = "person")
@Table (name = "person")
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    @JsonIgnore
    private Long id;
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;
    @Column(name = "personal_id_code", nullable = false, unique = true, length = 11, updatable = false)
    private String personalIdCode;
    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "person_event_participants", joinColumns = @JoinColumn(name = "person_id", foreignKey = @ForeignKey(
            name = "participants_person_id_fk")), inverseJoinColumns = @JoinColumn(name = "event_id", foreignKey = @ForeignKey(
            name = "participants_event_id_fk"
    )))
    private List<Event> events = new ArrayList<>();

    public Person(Long id, String firstName, String lastName, String personalIdCode, PaymentMethod paymentMethod, String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalIdCode = personalIdCode;
        this.paymentMethod = paymentMethod;
        this.description = description;
    }


    public void registerToEvent(Event event){
        if (!this.events.contains(event)) {
            this.events.add(event);
            event.setTotalParticipants(event.getTotalParticipants() + 1);
            event.getPersonParticipants().add(this);
        }
    }
}
