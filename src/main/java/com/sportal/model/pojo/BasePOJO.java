package com.sportal.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BasePOJO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
}
