package net.EffectiveMobile.TestTask.service;

import io.jsonwebtoken.lang.Assert;
import net.EffectiveMobile.TestTask.dto.PostDto;
import net.EffectiveMobile.TestTask.model.Post;
import net.EffectiveMobile.TestTask.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class PostServiceImplTest {

    @Mock
    PostService postService;
    @Mock
    PostRepository postRepository;

    @Test
    @org.junit.Test(expected = ConstraintViolationException.class)
    @DisplayName("Для передаваемого not null параметра, но не содержащий значения обязательных полей выбрасывается " +
            "исключение о невалидности данных и не происходит обращение в репозиторий для сохранения сущности")
    void createPost_ReturnsConstraintViolationException() {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postService.createPost(postDto);

        Post post = new Post();
        post.setId(1L);

        verify(postRepository, times(0)).saveAndFlush(post);
    }
}