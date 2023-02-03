package com.test.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "BookResponseDTO Description")
public class BookResponseDTO {

    @ApiModelProperty(value = "Data Exmaple for BookResponseDTO", required = true, example = "name1")
    private String name1;
}
