package net.EffectiveMobile.TestTask.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post extends BaseEntity {

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "title")
    @NotBlank(message = "Title is mandatory")
    @Max(value = 100)
    private String title;

    @Column(name = "content")
    @NotBlank(message = "Content is mandatory")
    @Max(value = 255)
    private String content;

    @Column(name = "link_image")
    @Max(value = 255)
    private String linkImage;

}
