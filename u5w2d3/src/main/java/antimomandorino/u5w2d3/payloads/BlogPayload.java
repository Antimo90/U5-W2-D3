package antimomandorino.u5w2d3.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter

@AllArgsConstructor
@ToString
public class BlogPayload {

    private String categoria;
    private String titolo;
    private String contenuto;
    private int tempoDiLettura;
    private UUID autoreId;

}
