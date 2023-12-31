package com.example.userService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "microUsers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String uid;
    private String name;
    private String email;
    @Transient  //database won't store this field
    private List<Rating> ratings;
}
