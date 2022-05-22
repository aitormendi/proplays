package com.tutorial.crud.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutorial.crud.shared.PowerException;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CustomErrorDTO {

  @ApiModelProperty(example = "UNAUTHORIZED")
  private String error;

  @ApiModelProperty(example = "8b55b402-6e60-44c4-ab0d-2be2fbb60845")
  private String code;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss z")
  @ApiModelProperty(example = "2020-08-15 10:42:48 UTC")
  private Date timestamp;

  public CustomErrorDTO(PowerException powerException) {
    this.error = powerException.getFullMessage();
    this.code = powerException.getId();
    this.timestamp = powerException.getTimestamp();
  }
}
