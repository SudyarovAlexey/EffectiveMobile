package net.EffectiveMobile.TestTask.service;

import lombok.AllArgsConstructor;
import net.EffectiveMobile.TestTask.dto.PostDto;
import net.EffectiveMobile.TestTask.handlerException.ResourceFoundException;
import net.EffectiveMobile.TestTask.handlerException.ResourceIsEmptyException;
import net.EffectiveMobile.TestTask.handlerException.ResourceNotFoundException;
import net.EffectiveMobile.TestTask.handlerException.ResourceUpdatingException;
import net.EffectiveMobile.TestTask.mapper.PostMapper;
import net.EffectiveMobile.TestTask.model.Post;
import net.EffectiveMobile.TestTask.repository.PostRepository;
import net.EffectiveMobile.TestTask.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    /**
     * Создание поста
     *
     * @param postDto - содержимое поста, передаваемые пользователем
     * @return - объект из БД после сохранения postDto
     */
    @Override
    public PostDto createPost(PostDto postDto) {
        checkPostIsNotExists(postDto);
        postRepository.saveAndFlush(postMapper.toEntity(postDto));
        return postMapper.toDto(postRepository.getByTitle(postDto.getTitle()));
    }

    private void checkPostIsNotExists(PostDto postDto) {
        Post post = postRepository.getPostById(postDto.getId());
        if (post != null) {
            throw new ResourceFoundException(String.format("Пост с id %s уже создан", postDto.getId()));
        }
    }

    @Override
    public PostDto getPostByPostId(Long postId) {
        checkPostIsPresent(postId);
        return postMapper.toDto(postRepository.getPostById(postId));
    }

    /**
     * В рамках данной задачи можно возвращать просто пустой список без выбрасывания исключения. Данное решение
     * для случая с внешним подключеним к API - не будет вопросов при коде 200 и пустом списке
     *
     * @param authorId - id автора поставов
     * @return - список постов (dto)
     */
    @Override
    public List<PostDto> getAllPostsByAuthorId(Long authorId) {
        List<Post> postList = new ArrayList<>(postRepository.getAllByAuthorId(authorId));
        if (postList.isEmpty()) {
            throw new ResourceIsEmptyException(String.format("Автор с id %s не создал постов " +
                    "или такого автора не существует", authorId));
        }
        return postMapper.toListDto(postList);
    }

    /**
     * Обновление собственного поста - проводится проверка авторства поста
     *
     * @param postDto - данные поста для обновления
     * @return - строковое сообщение об обновлении
     */
    @Transactional
    @Override
    public String updatePostByPostId(PostDto postDto) {
        checkPostIsPresent(postDto.getId());
        checkPostAuthor(postDto);
        postUpdated(postDto);
        return String.format("Пост с id %s обновлен", postDto.getId());
    }

    private void postUpdated(PostDto postDto) {
        Integer isUpdated = postRepository.updatePostByPostId(
                postDto.getId(),
                postDto.getTitle(),
                postDto.getContent(),
                postDto.getLinkImage());
        if (isUpdated != 1) {
            throw new ResourceUpdatingException(String.format("Данные поста id %s не обновлены", postDto.getId()));
        }
    }

    private void checkPostIsPresent(Long postId) {
        Post post = postRepository.getPostById(postId);
        if (post == null) {
            throw new ResourceNotFoundException(String.format("Пост с id %s не найден", postId));
        }
    }

    private void checkPostAuthor(PostDto postDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserName;
        if (principal instanceof UserDetails) {
            currentUserName = ((UserDetails) principal).getUsername();

        } else {
            currentUserName = principal.toString();
        }
        if (!currentUserName.equals(getAuthorName(postDto))) {
            throw new ResourceUpdatingException("Попытка правки поста чужого авторства");
        }
    }

    private String getAuthorName(PostDto postDto) {
        return userRepository.getById(postDto.getAuthorId()).getUsername();
    }

    /**
     * Удаление собственного поста - проводится проверка авторства поста
     *
     * @param postId - данные поста для удаления
     * @return - строковое сообщение об обновлении
     */
    @Override
    public String deletePostByPostId(Long postId) {
        checkPostIsPresent(postId);
        checkPostAuthor(postMapper.toDto(postRepository.getPostById(postId)));
        postRepository.deleteById(postId);
        return String.format("Пост с id %s удален", postId);
    }

    /**
     * Обновление поста админом - проверка авторства поста не проводится
     *
     * @param postDto - данные поста для обновления
     * @return - строковое сообщение об обновлении
     */
    @Transactional
    @Override
    public String updatePostByPostIdByAdmin(PostDto postDto) {
        checkPostIsPresent(postDto.getId());
        postUpdated(postDto);
        return String.format("Пост с id %s обновлен", postDto.getId());
    }

    /**
     * Удаление поста админом - проверка авторства поста не проводится
     *
     * @param postId - данные поста для обновления
     * @return - строковое сообщение об обновлении
     */
    @Override
    public String deletePostByPostIdByAdmin(Long postId) {
        checkPostIsPresent(postId);
        postRepository.deleteById(postId);
        return String.format("Пост с id %s удален", postId);
    }
}