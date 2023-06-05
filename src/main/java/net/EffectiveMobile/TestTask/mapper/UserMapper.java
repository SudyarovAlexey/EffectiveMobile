package net.EffectiveMobile.TestTask.mapper;

import net.EffectiveMobile.TestTask.dto.UserDto;
import net.EffectiveMobile.TestTask.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto userDto);

    UserDto toDto(User user);

}


