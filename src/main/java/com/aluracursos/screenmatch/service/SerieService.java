package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
//Se utiliza para definir una clase como un servicio, que es un componente de la capa de negocio de la aplicación
@Service
public class SerieService {
    //Inyeccion de depencias de SerieService de donde se transforman los datos
    //Inyecte automáticamente las dependencias requeridas por un componente, sin necesidad de instanciar manualmente los objetos.
    @Autowired
    private SerieRepository repository;
    //Se retornan en DTO ya que se quieren los datos ya transformados
    public List<SerieDTO> obtenerTodasLasSeries(){
        return convierteDatos(repository.findAll());
    }
    public List<SerieDTO> obtenerTop5() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }
    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convierteDatos(repository.lanzamientosMasRecientes());
    }
    public List<SerieDTO> convierteDatos(List<Serie> serie){
        return serie.stream()
                .map(s->new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(),
                        s.getEvaluacion(),s.getPoster(),
                        s.getGenero(),s.getActores(),s.getSinopsis()))
                .collect(Collectors.toList());
    }
    //Se retornan en DTO ya que se quieren los datos ya transformados
    public SerieDTO obtenerPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(),
                    s.getEvaluacion(),s.getPoster(),
                    s.getGenero(),s.getActores(),s.getSinopsis());
        }
        return null;

    }

    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e->new EpisodioDTO(e.getTemporada(),
                            e.getTitulo(),e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTemporadasPorNumero(Long id, Long numeroTemporada){
        return repository.obtenerTemporadasPorNumero(id,numeroTemporada).stream()
                .map(e->new EpisodioDTO(e.getTemporada(),
                        e.getTitulo(),e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }
    //Se retornan en DTO ya que se quieren los datos ya transformados
    //Se traen los datos .fromEspañol para traer el genero en el idioma
    public List<SerieDTO> obtenerSeriesPorCategoria(String nombreGenero){
        Categoria categoria = Categoria.fromEspanol(nombreGenero);
        return convierteDatos(repository.findByGenero(categoria));
    }
}
