package net.EffectiveMobile.TestTask.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.EffectiveMobile.TestTask.dto.PostDto;
import net.EffectiveMobile.TestTask.dto.UserDto;
import net.EffectiveMobile.TestTask.handlerException.ResourceFoundException;
import net.EffectiveMobile.TestTask.handlerException.ResourceNotFoundException;
import net.EffectiveMobile.TestTask.handlerException.ResourceUpdatingException;
import net.EffectiveMobile.TestTask.mapper.UserMapper;
import net.EffectiveMobile.TestTask.model.Status;
import net.EffectiveMobile.TestTask.model.User;
import net.EffectiveMobile.TestTask.repository.RoleRepository;
import net.EffectiveMobile.TestTask.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Возможность регистрации необходима только роли "USER". Админа указываем явно в БД
     *
     * @param userDto - данные пользователя
     * @return - данные пользователя после сохранеия в БД
     */
    @Override
    public UserDto registerUser(UserDto userDto) {
        checkUserIsExists(userDto);

        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.findByRoleName("ROLE_USER")));
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.saveAndFlush(user);
        log.info("user: {} successfully registered", registeredUser);
        return userMapper.toDto(userRepository.getUserByUsername(userDto.getUsername()));
    }

    private void checkUserIsExists(UserDto userDto) {
        User user = userRepository.getUserById(userDto.getId());
        if (user != null) {
            throw new ResourceFoundException(String.format("Пользователь с id %s уже создан", userDto.getId()));
        }
    }

    @Override
    public UserDto getUserByUserName(String username) {
        checkUserIsPresentByName(username);
        UserDto userDto = userMapper.toDto(userRepository.getUserByUsername(username));
        log.info("findByUsername - user: {} found by username: {}", userDto, username);
        return userDto;
    }

    @Override
    public User getUserEntityByUserName(String username) {
        checkUserIsPresentByName(username);
        User user = userRepository.getUserByUsername(username);
        log.info("findByUsername - user: {} found by username: {}", user, username);
        return user;
    }

    private void checkUserIsPresentByName(String username) {
        User postToUpdate = userRepository.getUserByUsername(username);
        if (postToUpdate == null) {
            throw new ResourceNotFoundException(String.format("Пользователь с id %s не найден", username));
        }
    }

    @Override
    public UserDto getUserByUserId(Long id) {
        checkUserIsPresentById(id);
        UserDto userDto = userMapper.toDto(userRepository.getUserById(id));
        log.info("findByUsername - user: {} found by username: {}", userDto, id);
        return userDto;
    }

    private void checkUserIsPresentById(Long id) {
        User postToUpdate = userRepository.getUserById(id);
        if (postToUpdate == null) {
            throw new ResourceNotFoundException(String.format("Пользователь с id %s не найден", id));
        }
    }

    /**
     * Удаление удаление текущего пользователя
     *
     * @param userId - id пользователя для удаления
     * @return - строковое сообщение об обновлении
     */
    @Override
    public String deleteUserByUserId(Long userId) {
        checkUserIsPresentById(userId);
        checkSelfDelete(userMapper.toDto(userRepository.getUserById(userId)));
        userRepository.deleteById(userId);
        log.info("delete - user with id: {} successfully deleted", userId);
        return String.format("Пользователь с id %s удален", userId);
    }

    private void checkSelfDelete(UserDto userDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserName;
        if (principal instanceof UserDetails) {
            currentUserName = ((UserDetails) principal).getUsername();

        } else {
            currentUserName = principal.toString();
        }
        if (!currentUserName.equals(userDto.getUsername())) {
            throw new ResourceUpdatingException("Попытка удалить другого пользователя");
        }
    }
}
