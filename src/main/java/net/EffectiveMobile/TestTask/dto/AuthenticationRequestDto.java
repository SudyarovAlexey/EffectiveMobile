package net.EffectiveMobile.TestTask.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Данные для авторизации и аутентификации")
public class AuthenticationRequestDto {

    @ApiModelProperty("Имя пользователя")
    private String username;

    @ApiModelProperty("Пароль пользователя")
    private String password;

}
