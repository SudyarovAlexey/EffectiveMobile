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

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
@Api(tags = "Управление постами пользователей (для администратора)")
public class PostControllerAdmin {

    private final PostService postService;

    @ApiOperation("Удаление поста пользователя")
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostByPostIdByAdmin(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postService.deletePostByPostIdByAdmin(postId), HttpStatus.OK);
    }

    @ApiOperation("Обновление данных поста пользователя")
    @PostMapping("/")
    public ResponseEntity<String> updatePostByPostIdByAdmin(@RequestBody @Valid PostDto postDto) {
        return new ResponseEntity<>(postService.updatePostByPostIdByAdmin(postDto), HttpStatus.OK);
    }

}
