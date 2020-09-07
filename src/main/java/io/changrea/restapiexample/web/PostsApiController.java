package io.changrea.restapiexample.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import io.changrea.restapiexample.service.PostsService;
import io.changrea.restapiexample.web.dto.PostsResponseDto;
import io.changrea.restapiexample.web.dto.PostsSaveRequestDto;
import io.changrea.restapiexample.web.dto.PostsUpdateRequestDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "PostsApiController V1")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api")
public class PostsApiController {

    private final PostsService postsService;

    @ApiOperation(value = "Get All Posts", notes = "모든 포스트를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping("/posts")
    public ResponseEntity<CollectionModel<PostsResponseDto>> getAllPosts() {
        List<PostsResponseDto> posts = postsService.getAllPosts();
        Link link = linkTo(methodOn(PostsApiController.class)
                .getAllPosts()).withSelfRel();

        CollectionModel<PostsResponseDto> result = CollectionModel.of(posts).add(link);

        return ResponseEntity.ok().body(CollectionModel.of(result)
                .add(linkTo(methodOn(PostsApiController.class).getAllPosts()).withSelfRel())
        );
    }

    @ApiOperation(value = "Get Post for id", notes = "포스트를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping("/posts/{id}")
    public ResponseEntity<EntityModel<PostsResponseDto>> findById(@ApiParam(value = "Post ID", required = true, example = "1") @PathVariable Long id) {
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

    @ApiOperation(value = "Save Post", notes = "포스트를 저장합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @PostMapping("/posts")
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

    @ApiOperation(value = "Update Post for ID", notes = "포스트를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @PutMapping("/posts/{id}")
    public ResponseEntity<EntityModel<Long>> update(@ApiParam(value = "Post ID", required = true, example = "1") @PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
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

    @ApiOperation(value = "Delete Post for ID", notes = "포스트를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<EntityModel<Boolean>> delete(@ApiParam(value = "Post ID", required = true, example = "1") @PathVariable Long id) {
        boolean isRemoved = postsService.delete(id);
        Link selfLink = linkTo(methodOn(PostsApiController.class).delete(id)).withSelfRel();

        if(!isRemoved) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Boolean> result = EntityModel.of(true).add(selfLink);

        return ResponseEntity.ok().body(result);
    }
}
