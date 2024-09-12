package com.example.demo.model.security;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String permission;

    @ManyToMany(mappedBy = "authorities")
    Set<Role> roles = new HashSet<>();
}
