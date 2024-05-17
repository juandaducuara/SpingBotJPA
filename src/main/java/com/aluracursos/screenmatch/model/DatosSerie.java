package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//Mapeo de datos que vienen del JSON
//Anotacion para ignrar todas las otras propiedades
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        //@JsonAlias para saber como se llama el atributo que viene del JSON
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons")Integer totalTemporadas,
        @JsonAlias("imdbRating") String evaluacion,
        @JsonAlias("Poster")String poster,
        @JsonAlias("Genre") String genero,
        @JsonAlias("Actors")String actores,
        @JsonAlias("Plot")String sinopsis)
         {
}
