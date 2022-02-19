package com.sportal.model.dto.article;

import com.sportal.model.pojo.Category;
import com.sportal.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class AddArticleDTO {

    @Column
    private String title;
    @Column
    private String content;

}
