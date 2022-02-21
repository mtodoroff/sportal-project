package com.sportal.model.dto.imageDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@NoArgsConstructor
public class DeleteImageRequestDTO {
    private long id;
}
