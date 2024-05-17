package com.aluracursos.screenmatch.dto;
//Se pasan varibales a tratar
import com.aluracursos.screenmatch.model.Categoria;

public record SerieDTO(
        Long id,
         String titulo,
         Integer totalTemporadas,
         Double evaluacion,
         String poster,
         Categoria genero,
         String actores,
         String sinopsis
) {
}
