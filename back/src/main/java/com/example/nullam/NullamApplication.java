package com.example.nullam;

import com.example.nullam.company.Company;
import com.example.nullam.company.CompanyRepository;
import com.example.nullam.company.CompanyService;
import com.example.nullam.event.Event;
import com.example.nullam.event.EventRepository;
import com.example.nullam.event.EventService;
import com.example.nullam.paymentmethod.PaymentMethod;
import com.example.nullam.person.Person;
import com.example.nullam.person.PersonRepository;
import com.example.nullam.person.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class NullamApplication {

    public static void main(String[] args) {
        SpringApplication.run(NullamApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PersonRepository personRepository, CompanyRepository companyRepository, EventRepository eventRepository) {
        return args -> {
            Event medKoolitus = new Event(1L, "Meditsiini koolitus", LocalDateTime.of(2016, 2, 14, 17, 0),
                    "Tartu ERM", "Koolitus");

            Event suvePaevad = new Event(2L, "Suvepäevad", LocalDateTime.of(2022, 8, 14, 13, 0),
                    "Võru Tamula rand", "Suvepäevad");

            Event lasteKaitsepaev = new Event(3L, "Lastekaitsepäev", LocalDateTime.of(2018, 6, 1, 12, 30),
                    "Tallinna loomaaed", "Lastekaitsepäev");

            Event eesti105 = new Event(4L, "Eesti Vabariigi aastapäev 105", LocalDateTime.of(2023, 2, 24, 20, 0),
                    "Rahvusooper Estonia", "Vabariigi sünnipäev");

            eventRepository.saveAll(Arrays.asList(medKoolitus, suvePaevad, lasteKaitsepaev, eesti105));

            Person mihkel = new Person(1L, "Mihkel", "Amman",
                    "37810014694", PaymentMethod.TRANSFER, "Student");

            Person uku = new Person(2L, "Uku", "Leele",
                    "37810014944", PaymentMethod.CASH, "Sponsor");

            personRepository.saveAll(Arrays.asList(mihkel, uku));

            mihkel.registerToEvent(medKoolitus);
            mihkel.registerToEvent(eesti105);
            mihkel.registerToEvent(lasteKaitsepaev);
            uku.registerToEvent(suvePaevad);
            uku.registerToEvent(eesti105);
            uku.registerToEvent(lasteKaitsepaev);

            personRepository.saveAll(Arrays.asList(mihkel, uku));

            Company nullam = new Company(1L, "Nullam OÜ", "70000622", 32,
                    PaymentMethod.TRANSFER, "Medical company");

            companyRepository.save(nullam);


            nullam.registerToEvent(medKoolitus);
            nullam.registerToEvent(lasteKaitsepaev);
            nullam.registerToEvent(suvePaevad);

            companyRepository.save(nullam);


        };
    }

}
