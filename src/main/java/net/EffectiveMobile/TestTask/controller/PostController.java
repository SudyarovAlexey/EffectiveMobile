package net.EffectiveMobile.TestTask.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import net.EffectiveMobile.TestTask.dto.PostDto;
import net.EffectiveMobile.TestTask.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/all/posts")
@AllArgsConstructor
@Api(tags = "Управление постами пользователей")
public class PostController {

    private final PostService postService;

    @ApiOperation("Создание поста пользователя")
    @PostMapping()
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @ApiOperation("Получение поста пользователя по id поста")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postService.getPostByPostId(postId), HttpStatus.OK);
    }

    @ApiOperation("Получение списка постов по id автора постов")
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<PostDto>> getAllPostsByAuthorId(@PathVariable("authorId") Long authorId) {
        return new ResponseEntity<>(postService.getAllPostsByAuthorId(authorId), HttpStatus.OK);
    }

    @ApiOperation("Удаление поста пользователя")
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostByPostId(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postService.deletePostByPostIdByAdmin(postId), HttpStatus.OK);
    }

    @ApiOperation("Обновление данных поста пользователя")
    @PatchMapping()
    public ResponseEntity<String> updatePostByPostId(@RequestBody @Valid PostDto postDto) {
        return new ResponseEntity<>(postService.updatePostByPostId(postDto), HttpStatus.OK);
    }

}
