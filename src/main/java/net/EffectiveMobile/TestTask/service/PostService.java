package net.EffectiveMobile.TestTask.service;

import net.EffectiveMobile.TestTask.dto.PostDto;

import java.util.List;

public interface PostService {

    /**
     * Создает новый пост
     * @param postDto - данные для создания поста
     * @return - пост, полученный из БД после его сохранения
     */
    PostDto createPost(PostDto postDto);

    /**
     * Возвращает пост по его id
     * @param postId - id поста
     * @return - пост
     */
    PostDto getPostByPostId(Long postId);

    /**
     * Возвращает список всех постов по id автора поста
     * @param authorId - id автора поста
     * @return - список постов
     */
    List<PostDto> getAllPostsByAuthorId(Long authorId);

    /**
     * Обновление данных поста по его id автором поста
     * @param postDto - данные поста для обновления
     * @return - строковое подтверждение обновления
     */
    String updatePostByPostId(PostDto postDto);

    /**
     * Удаление поста по его id автором поста
     * @param postId - id поста, который нужно удалить
     * @return - строковое подтверждение обновления
     */
    String deletePostByPostId(Long postId);

    /**
     * Обновляет данных поста по его id без проверки авторства (для администратора)
     * @param postDto - данные поста для обновления
     * @return - строковое подтверждение обновления
     */
    String updatePostByPostIdByAdmin(PostDto postDto);

    /**
     * Удаление поста по заданному id.
     * @param postId - id поста, который нужно удалить
     * @return - сообщение (String), подтверждающее удаление. Для работы внешнего приложения через API
     * данного приложения. Т.к. статус 200 будет в любом случае.
     */
    String deletePostByPostIdByAdmin(Long postId);
}
