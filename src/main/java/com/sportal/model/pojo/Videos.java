package com.example.sportalproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Videos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String url;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Articles article_id;
}
