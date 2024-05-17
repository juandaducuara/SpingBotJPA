package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//Mapeo de datos que vienen del JSON
//Anotacion para ignrar todas las otras propiedades
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEpisodio(
        //@JsonAlias para saber como se llama el atributo que viene del JSON
        @JsonAlias("Title") String titulo,
        @JsonAlias("Episode")Integer numeroEpisodio,
        @JsonAlias("imdbRating") String evaluacion,
        @JsonAlias("Released")String fechaDeLanzamiento
) {
}
