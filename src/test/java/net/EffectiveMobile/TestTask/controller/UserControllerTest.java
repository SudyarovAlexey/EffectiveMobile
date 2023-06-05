package net.EffectiveMobile.TestTask.controller;

import net.EffectiveMobile.TestTask.dto.UserDto;
import net.EffectiveMobile.TestTask.model.User;
import net.EffectiveMobile.TestTask.repository.UserRepository;
import net.EffectiveMobile.TestTask.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserControllerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserService userService;

    @Test
    @org.junit.Test(expected = ConstraintViolationException.class)
    @DisplayName("Для передаваемого not null параметра, но не содержащий значения обязательных полей выбрасывается " +
            "исключение о невалидности данных и не происходит обращение в репозиторий для сохранения сущности")
    void registerUser_ReturnsConstraintViolationException() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userService.registerUser(userDto);

        User user = new User();
        user.setId(1L);

        verify(userRepository, times(0)).saveAndFlush(user);
    }

}