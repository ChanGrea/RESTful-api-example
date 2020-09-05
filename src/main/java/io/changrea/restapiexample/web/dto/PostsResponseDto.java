package io.changrea.restapiexample.web.dto;

import io.changrea.restapiexample.domain.posts.Posts;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Relation(collectionRelation = "posts")
@NoArgsConstructor
public class PostsResponseDto extends RepresentationModel<PostsResponseDto> {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
