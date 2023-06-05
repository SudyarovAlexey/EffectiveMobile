package net.EffectiveMobile.TestTask.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import net.EffectiveMobile.TestTask.dto.UserDto;
import net.EffectiveMobile.TestTask.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/all/users")
@AllArgsConstructor
@Api(tags = "Управление списком пользователей")
public class UserController {

    private final UserService userService;

    @ApiOperation("Регистрация пользователя")
    @PostMapping()
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.OK);
    }

    @ApiOperation("Получение данных пользователя по его id")
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "userId") Long userId) {
        return new ResponseEntity<>(userService.getUserByUserId(userId), HttpStatus.OK);
    }

    @ApiOperation("Удаление пользователя по его id")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserByUserId(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.deleteUserByUserId(userId), HttpStatus.OK);
    }
}
