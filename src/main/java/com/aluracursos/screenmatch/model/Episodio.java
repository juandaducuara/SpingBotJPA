package com.aluracursos.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
//Anotacion para saber que va a ser una entidad dentr de la base de datos
@Entity
//Anotacion utilizada para saber que es una table, esta tiene que llevar su respectivo nombre
@Table(name = "episodios")

public class Episodio {
    //Anotacin para reconocer que tiene un id y que se generara los valores IDENTITY autoincrementable
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private  Integer temporada;
    private  String titulo;
    private Integer numeroEpisodio;
    private Double evaluacion;
    private LocalDate fechaDeLanzamiento;
    // Anoracion para relacionar con tabla episodios
    @ManyToOne
    private Serie serie;

    public Episodio(){}

    public Episodio(Integer numero, DatosEpisodio d) {
        this.temporada = numero;
        this.titulo = d.titulo();
        this.numeroEpisodio = d.numeroEpisodio();
        try{
            this.evaluacion =Double.valueOf(d.evaluacion());
        }catch (NumberFormatException e){
            this.evaluacion = 0.0 ;
        }
        try {
            this.fechaDeLanzamiento = LocalDate.parse(d.fechaDeLanzamiento());
        }catch (DateTimeParseException e){
            this.fechaDeLanzamiento = null;
        }

    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public LocalDate getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    @Override
    public String toString() {
        return  "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", evaluacion=" + evaluacion +
                ", fechaDeLanzamiento=" + fechaDeLanzamiento;
    }
}
