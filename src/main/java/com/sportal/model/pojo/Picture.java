package com.sportal.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "pictures")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Picture extends BasePojo{


    @Column
    private String url;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="article_id")
    private Article article_id;
}
