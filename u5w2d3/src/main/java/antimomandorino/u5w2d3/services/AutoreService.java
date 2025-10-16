package antimomandorino.u5w2d3.services;

import antimomandorino.u5w2d3.entities.Autore;
import antimomandorino.u5w2d3.exceptions.BadRequestException;
import antimomandorino.u5w2d3.exceptions.NotFoundException;
import antimomandorino.u5w2d3.payloads.AutorePayload;
import antimomandorino.u5w2d3.repositorys.AutoreRepository;
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
public class AutoreService {

    @Autowired
    private AutoreRepository autoreRepository;


    public Page<Autore> findAll(Integer pageNumber, Integer pageSize, String sortBy) {
        if (pageSize > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.autoreRepository.findAll(pageable);
    }

    public Autore saveAutore(AutorePayload payload) {
        this.autoreRepository.findByMail(payload.getMail()).ifPresent(autore -> {
                    throw new BadRequestException("L'email " + autore.getMail() + " è già in uso!");
                }
        );
        Autore newAutore = new Autore(payload.getNome(), payload.getCognome(), payload.getMail(), payload.getDataDiNascita());
        this.autoreRepository.save(newAutore);
        log.info("L'autore " + newAutore.getNome() + " è stato aggiunto con successo");
        return newAutore;
    }

    public Autore findById(UUID autoreId) {
        return this.autoreRepository.findById(autoreId).orElseThrow(() -> new NotFoundException(autoreId));
    }

    public Autore findByIdAndUpdate(UUID autoreId, AutorePayload payload) {
        Autore found = this.findById(autoreId);

        if (!found.getMail().equals(payload.getMail())) {
            this.autoreRepository.findByMail(payload.getMail()).ifPresent(autore -> {
                throw new BadRequestException("L'email " + autore.getMail() + " è già in uso!");
            });
        }

        found.setNome(payload.getNome());
        found.setCognome(payload.getCognome());
        found.setMail(payload.getMail());
        found.setDataDiNascita(payload.getDataDiNascita());

        Autore modifiedAutore = this.autoreRepository.save(found);

        log.info("L'utente con id " + modifiedAutore.getAutoreId() + " è stato modificato correttamente");
        return modifiedAutore;
    }

    public void findByIdAndDelete(UUID autoreId) {
        Autore found = this.findById(autoreId);
        this.autoreRepository.delete(found);
    }

}
