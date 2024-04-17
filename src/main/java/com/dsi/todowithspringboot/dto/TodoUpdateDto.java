package com.dsi.todowithspringboot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class TodoUpdateDto {
    @Length(min=3, max = 255)
    @NotBlank
    private String title;

    @Length(max = 65535)
    private String details;

    private Boolean isStarred;

    private Boolean isCompleted;
}
