package antimomandorino.u5w2d3.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter

@AllArgsConstructor
@ToString
public class AutorePayload {

    private String nome;
    private String cognome;
    private String mail;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDiNascita;
}
