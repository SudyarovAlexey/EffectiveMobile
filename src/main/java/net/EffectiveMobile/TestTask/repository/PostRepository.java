package net.EffectiveMobile.TestTask.repository;

import net.EffectiveMobile.TestTask.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post getPostById(Long postId);

    Post getByTitle(String title);

    List<Post> getAllByAuthorId(Long authorId);

    @Modifying
    @Query(value = "update posts set title = :title, content = :content, link_image = :linkImage where id = :id",
            nativeQuery = true)
    Integer updatePostByPostId(Long id, String title, String content, String linkImage);

}
