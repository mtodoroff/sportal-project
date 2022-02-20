package com.sportal.model.pojo;

import com.sportal.model.pojo.enums.RoleName;
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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String category;
    @OneToMany(mappedBy = "id")
    private Set<Article> articles;

    public Category(String category) {
        this.category = category;
    }
}
