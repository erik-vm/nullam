package com.example.nullam.company;

import com.example.nullam.event.Event;
import com.example.nullam.paymentmethod.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "company")
@Table(name = "company")
@Data
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    @JsonIgnore
    private Long id;
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(name = "company_reg_number", nullable = false, unique = true, length = 8, updatable = false)
    private String companyRegNumber;
    @Column(name = "participants")
    private Integer participants;
    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "company_event_participants", joinColumns = @JoinColumn(name = "company_id", foreignKey = @ForeignKey(
            name = "participants_company_id_fk")), inverseJoinColumns = @JoinColumn(name = "event_id", foreignKey = @ForeignKey(
                    name = "participants_event_id_fk"
    )))
    private List<Event> events = new ArrayList<>();

    public Company(Long id, String name, String companyRegNumber,Integer participants, PaymentMethod paymentMethod, String description) {
        this.id = id;
        this.name = name;
        this.companyRegNumber = companyRegNumber;
        this.participants = participants;
        this.paymentMethod = paymentMethod;
        this.description = description;
    }

    public void registerToEvent(Event event){
        if (!this.events.contains(event)) {
            this.events.add(event);
            event.getCompanyParticipants().add(this);
        }
    }
}
