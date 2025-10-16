package antimomandorino.u5w2d3.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Autore {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID autoreId;
    private String nome;
    private String cognome;
    private String mail;
    @Column(name = "data_di_nascita")
    private LocalDate dataDiNascita;
    private String avatar;


    public Autore(String nome, String cognome, String mail, LocalDate dataDiNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
        this.dataDiNascita = dataDiNascita;
        String nomecompleto = this.nome + "+" + this.cognome;
        this.avatar = "https://ui-avatars.com/api/?name=" + nomecompleto;
    }
}
