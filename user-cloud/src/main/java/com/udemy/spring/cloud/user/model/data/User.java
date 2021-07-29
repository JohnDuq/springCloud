package com.udemy.spring.cloud.user.model.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idUser;
    @Column(unique = true, length = 20)
    private String username;
    @Column(length = 60)
    private String password;
    @Column
    private String status;
    @Column
    private String name;
    @Column
    private String lastName;
    @Column(unique = true, length = 100)
    private String email;

}
