package antimomandorino.u5w2d3.controllers;

import antimomandorino.u5w2d3.entities.Blog;
import antimomandorino.u5w2d3.payloads.BlogPayload;
import antimomandorino.u5w2d3.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/blogPosts")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public Page<Blog> getBlogs(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return this.blogService.findAll(page, size, sortBy);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Blog createBlog(@RequestBody BlogPayload body) {
        return this.blogService.saveBlog(body);
    }

    @GetMapping("/{blogId}")
    public Blog getBlogByID(@PathVariable UUID blogId) {
        return this.blogService.findById(blogId);
    }

    @PutMapping("/{blogId}")
    public Blog getBlogByIdAndUpdate(@PathVariable UUID blogId, @RequestBody BlogPayload body) {
        return this.blogService.findByIdAndUpdate(blogId, body);
    }

    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getBlogByIdAndDelete(@PathVariable UUID blogId) {
        this.blogService.findByIdAndDelete(blogId);
    }
}
