package com.aluracursos.screenmatch.controller;

import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//Simplificacion de implementacion de controladores
@RestController
//Anotracion utilizada para mapear toda la clase /serie/@GetMapping
@RequestMapping("/series")
public class SerieController {
    //Inyeccion de depencias de SerieService de donde se transforman los datos
    //Inyecte automáticamente las dependencias requeridas por un componente, sin necesidad de instanciar manualmente los objetos.
    @Autowired
    private SerieService servicio;
    //@GetMapping mapeo de URL
    @GetMapping()
    //Se retornan de tipo DTO Los DTO representan los datos que se transfieren entre las
    //diferentes capas de la aplicación, como la capa de servicio y la capa de controlador.
    public List<SerieDTO> obtenerTodasLasSeries(){
        return  servicio.obtenerTodasLasSeries();
    }
    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5(){
        return servicio.obtenerTop5();
    }
    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return servicio.obtenerLanzamientosMasRecientes();
    }
    //Anotacion con {} llaves para saber que es una URL dinamica
    //@PathVarible para saber que es una varible de URL
    @GetMapping("/{id}")
    public SerieDTO obtenerPorId(@PathVariable Long id){
        return servicio.obtenerPorId(id);
    }
    @GetMapping("/{id}/temporadas/todas")
    public  List<EpisodioDTO> obtenerTodasLasTempradas(@PathVariable Long id){
        return servicio.obtenerTodasLasTemporadas(id);
    }
    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obtenerTemporadasPorNumero(@PathVariable Long id,
                                                        @PathVariable Long numeroTemporada){
        return servicio.obtenerTemporadasPorNumero(id,numeroTemporada);
    }
    @GetMapping("/categoria/{nombreGenero}")
    public List<SerieDTO> obtenerSeriesPorCartegoria(@PathVariable String nombreGenero){
        return servicio.obtenerSeriesPorCategoria(nombreGenero);
    }

}
