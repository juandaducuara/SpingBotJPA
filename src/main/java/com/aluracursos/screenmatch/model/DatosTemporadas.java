package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
//Mapeo de datos que vienen del JSON
//Anotacion para ignrar todas las otras propiedades
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosTemporadas(
        //@JsonAlias para saber como se llama el atributo que viene del JSON
        @JsonAlias("Season") Integer numero,
        //Se trae los episodios en lista de la serie ya que trae muchos mas datos
        @JsonAlias("Episodes") List<DatosEpisodio> episodios
) {
}
