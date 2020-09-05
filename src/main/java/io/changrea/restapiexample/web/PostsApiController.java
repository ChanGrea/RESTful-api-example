package io.changrea.restapiexample.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import io.changrea.restapiexample.service.PostsService;
import io.changrea.restapiexample.web.dto.PostsResponseDto;
import io.changrea.restapiexample.web.dto.PostsSaveRequestDto;
import io.changrea.restapiexample.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @GetMapping("/api/v1/posts")
    public ResponseEntity<CollectionModel<PostsResponseDto>> getAllPosts() {
        List<PostsResponseDto> posts = postsService.getAllPosts();
        Link link = linkTo(methodOn(PostsApiController.class)
                .getAllPosts()).withSelfRel();

        CollectionModel<PostsResponseDto> result = CollectionModel.of(posts).add(link);

        return ResponseEntity.ok().body(CollectionModel.of(result)
                .add(linkTo(methodOn(PostsApiController.class).getAllPosts()).withSelfRel())
        );
    }

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<EntityModel<PostsResponseDto>> findById(@PathVariable Long id) {
        PostsResponseDto post = postsService.findById(id);
        Link selfLink = linkTo(methodOn(PostsApiController.class).findById(id)).withSelfRel();
        Link updateLink = linkTo(methodOn(PostsApiController.class)).withRel("update");
        Link deleteLink = linkTo(methodOn(PostsApiController.class)).withRel("delete");

        EntityModel<PostsResponseDto> result = EntityModel.of(post)
                .add(selfLink)
                .add(updateLink)
                .add(deleteLink);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/api/v1/posts")
    public ResponseEntity<EntityModel<Long>> save(@RequestBody PostsSaveRequestDto requestDto) {
        Long id = postsService.save(requestDto);
        Link selfLink = linkTo(methodOn(PostsApiController.class).save(requestDto)).withSelfRel();
        Link getLink = linkTo(methodOn(PostsApiController.class)).withRel("get");
        Link updateLink = linkTo(methodOn(PostsApiController.class)).withRel("update");
        Link deleteLink = linkTo(methodOn(PostsApiController.class)).withRel("delete");

        EntityModel<Long> result = EntityModel.of(id)
                .add(selfLink)
                .add(getLink)
                .add(updateLink)
                .add(deleteLink);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<EntityModel<Long>> update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        Long updatedId = postsService.update(id, requestDto);
        Link selfLink = linkTo(methodOn(PostsApiController.class).update(id, requestDto)).withSelfRel();
        Link getLink = linkTo(methodOn(PostsApiController.class)).withRel("get");
        Link deleteLink = linkTo(methodOn(PostsApiController.class)).withRel("delete");

        EntityModel<Long> result = EntityModel.of(updatedId)
                .add(selfLink)
                .add(getLink)
                .add(deleteLink);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<EntityModel<Boolean>> delete(@PathVariable Long id) {
        boolean isRemoved = postsService.delete(id);
        Link selfLink = linkTo(methodOn(PostsApiController.class).delete(id)).withSelfRel();

        if(!isRemoved) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Boolean> result = EntityModel.of(true).add(selfLink);

        return ResponseEntity.ok().body(result);
    }
}
