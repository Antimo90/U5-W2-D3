package antimomandorino.u5w2d3.services;

import antimomandorino.u5w2d3.entities.Autore;
import antimomandorino.u5w2d3.entities.Blog;
import antimomandorino.u5w2d3.exceptions.BadRequestException;
import antimomandorino.u5w2d3.exceptions.NotFoundException;
import antimomandorino.u5w2d3.payloads.BlogPayload;
import antimomandorino.u5w2d3.repositorys.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AutoreService autoreService;

    public Page<Blog> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.blogRepository.findAll(pageable);
    }

    public Blog saveBlog(BlogPayload payload) {
        this.blogRepository.findByTitolo(payload.getTitolo()).ifPresent(blog -> {
                    throw new BadRequestException("Il titolo " + blog.getTitolo() + " è già in uso!");
                }
        );


        Autore autore = autoreService.findById(payload.getAutoreId());

        Blog newBlog = new Blog(payload.getCategoria(), payload.getTitolo(), payload.getContenuto(), payload.getTempoDiLettura(), autore);
        this.blogRepository.save(newBlog);
        log.info("Il blog " + newBlog.getTitolo() + " è stato aggiunto al database!");
        return newBlog;
    }

    public Blog findById(UUID blogId) {
        return this.blogRepository.findById(blogId).orElseThrow(() -> new NotFoundException(blogId));
    }

    public Blog findByIdAndUpdate(UUID blogId, BlogPayload payload) {
        Blog found = this.findById(blogId);

        if (!found.getTitolo().equals(payload.getTitolo())) {
            this.blogRepository.findByTitolo(payload.getTitolo()).ifPresent(blog -> {
                throw new BadRequestException("Il titolo " + blog.getTitolo() + " è già in uso!");
            });
        }
        ;
        Autore autore = autoreService.findById(payload.getAutoreId());

        found.setCategoria(payload.getCategoria());
        found.setTitolo(payload.getTitolo());
        found.setContenuto(payload.getContenuto());
        found.setTempoDiLettura(payload.getTempoDiLettura());
        found.setAutore(autore);

        Blog modifiedBlog = this.blogRepository.save(found);

        return modifiedBlog;
    }

    public void findByIdAndDelete(UUID blogId) {
        Blog found = this.findById(blogId);

        this.blogRepository.delete(found);
    }
}
