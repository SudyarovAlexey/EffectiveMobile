package net.EffectiveMobile.TestTask.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel(description = "Данные роли")
public class RoleDto {

    @ApiModelProperty("id роли")
    private Long id;

    @ApiModelProperty("Наименование роли")
    private String roleName;

    @ApiModelProperty("Статус роли")
    private String status;

    @ApiModelProperty("Пользователь")
    private List<String> users;
}


