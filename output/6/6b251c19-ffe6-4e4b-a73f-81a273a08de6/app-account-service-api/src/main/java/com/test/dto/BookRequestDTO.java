package com.test.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "BookRequestDTO Description")
public class BookRequestDTO {

    @ApiModelProperty(value = "Data Exmaple for BookRequestDTO", required = true, example = "name1")
    private String name1;
	
}
