package br.com.fatec.fatec_eng3.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Points {

    private Long idRoom;

    private Integer pointPlayerOne;

    private Integer pointPlayerTwo;

}
