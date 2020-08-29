package io.changrea.restapiexample.service;

import io.changrea.restapiexample.domain.posts.Posts;
import io.changrea.restapiexample.domain.posts.PostsRepository;
import io.changrea.restapiexample.web.dto.PostsResponseDto;
import io.changrea.restapiexample.web.dto.PostsSaveRequestDto;
import io.changrea.restapiexample.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    private final ModelMapper modelMapper;

    public List<PostsResponseDto> getAllPosts() {
        List<Posts> result = postsRepository.findAll();

        return result.stream()
                .map(posts -> modelMapper.map(posts, PostsResponseDto.class)).collect(Collectors.toList());
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public Boolean delete(Long id) {
        try {
            postsRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
