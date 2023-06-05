package net.EffectiveMobile.TestTask.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import net.EffectiveMobile.TestTask.dto.AuthenticationRequestDto;
import net.EffectiveMobile.TestTask.mapper.UserMapper;
import net.EffectiveMobile.TestTask.model.User;
import net.EffectiveMobile.TestTask.security.jwt.JwtTokenProvider;
import net.EffectiveMobile.TestTask.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/all")
@AllArgsConstructor
@Api(tags = "Аутентификация и авторизация")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserMapper userMapper;

    @ApiOperation("Авторизация")
    @PostMapping("/auth")
    public ResponseEntity authorization(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            User user = getUser(username);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,
                            requestDto.getPassword()));

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", jwtTokenProvider.createToken(username, user.getRoles()));

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @NotNull
    private User getUser(String username) {
        User user = userService.getUserEntityByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with username: %s not found", username));
        }
        return user;
    }
}
