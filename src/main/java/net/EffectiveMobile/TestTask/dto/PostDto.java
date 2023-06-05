package net.EffectiveMobile.TestTask.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Данные поста")
public class PostDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("id автора поста")
    private Long authorId;

    @ApiModelProperty("Название поста")
    private String title;

    @ApiModelProperty("Содержимое поста")
    private String content;

    @ApiModelProperty("Ссылка на рисунок поста")
    private String linkImage;

}
