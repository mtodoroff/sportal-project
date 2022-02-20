package com.sportal.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="categories")
@Getter
@Setter
@NoArgsConstructor
public class Category extends BasePojo {

    @Column
    private String category;
    @OneToMany(mappedBy = "id")
    private Set<Article> articles;
}
