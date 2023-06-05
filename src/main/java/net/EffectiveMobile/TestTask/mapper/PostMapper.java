package net.EffectiveMobile.TestTask.mapper;

import net.EffectiveMobile.TestTask.dto.PostDto;
import net.EffectiveMobile.TestTask.model.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto toDto(Post post);

    List<PostDto> toListDto(List<Post> post);

    Post toEntity(PostDto postDto);

}
