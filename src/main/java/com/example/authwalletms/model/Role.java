package com.example.authwalletms.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roleId;
    private String name;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
