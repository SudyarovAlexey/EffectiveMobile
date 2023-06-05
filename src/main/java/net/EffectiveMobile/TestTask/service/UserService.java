package net.EffectiveMobile.TestTask.service;

import net.EffectiveMobile.TestTask.dto.UserDto;
import net.EffectiveMobile.TestTask.model.User;

public interface UserService {

    /**
     * Регистрация нового пользователя - сохранение в БД
     *
     * @param userDto - данные пользователя
     * @return - данные пользователя, полученные из БД после сохранения
     */
    UserDto registerUser(UserDto userDto);

    /**
     * Возвращает данные пользователя по его имени (является уникаьным)
     *
     * @param username - имя пользователя
     * @return - дынные пользователя
     */
    UserDto getUserByUserName(String username);

    /**
     * Возвращает сущность  пользователя из БД по его имени (является уникаьным)
     *
     * @param username - имя пользователя
     * @return - дынные пользователя
     */
    User getUserEntityByUserName(String username);

    /**
     * Возвращает данные пользователя по его id
     *
     * @param userId - id пользователя
     * @return - дынные пользователя
     */
    UserDto getUserByUserId(Long userId);

    /**
     * Удаление пользователя по его id
     *
     * @param userId - id поста, который нужно удалить
     * @return - строковое подтверждение обновления
     */
    String deleteUserByUserId(Long userId);
}
