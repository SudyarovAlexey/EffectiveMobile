package net.EffectiveMobile.TestTask.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Данные пользователя")
public class UserDto {

    @ApiModelProperty("id пользователя")
    private Long id;

    @ApiModelProperty("Имя пользователя")
    private String username;

    @ApiModelProperty("Пароль пользователя")
    private String password;

}
