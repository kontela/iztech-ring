package com.iztech.ringtracker.bus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bus {

    @Id
    private Long id;

    private int capacity;

    @JsonIgnore
    @OneToMany(mappedBy = "bus")
    private Collection<Location> locations = new ArrayList<>();

}
