package antimomandorino.u5w2d3.controllers;

import antimomandorino.u5w2d3.entities.Autore;
import antimomandorino.u5w2d3.payloads.AutorePayload;
import antimomandorino.u5w2d3.services.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AutoreController {

    @Autowired
    private AutoreService autoreService;

    @GetMapping
    public Page<Autore> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.autoreService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Autore createAutore(@RequestBody AutorePayload body) {
        return this.autoreService.saveAutore(body);
    }

    @GetMapping("/{autoreId}")
    public Autore getAutoreById(@PathVariable UUID autoreId) {
        return this.autoreService.findById(autoreId);
    }

    @PutMapping("/{autoreId}")
    public Autore getAutoreByIdAndUpdate(@PathVariable UUID autoreId, @RequestBody AutorePayload body) {
        return this.autoreService.findByIdAndUpdate(autoreId, body);
    }

    @DeleteMapping("/{autoreId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getAutoreByIdAndDelete(@PathVariable UUID autoreId) {
        this.autoreService.findByIdAndDelete(autoreId);
    }

}
