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
import org.springframework.http.HttpStatus;
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
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        boolean isRemoved = postsService.delete(id);

        if(!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
