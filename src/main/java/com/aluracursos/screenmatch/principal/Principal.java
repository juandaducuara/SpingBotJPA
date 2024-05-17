package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.*;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE="http://www.omdbapi.com/?t=";
    private final String API_KEY="&apikey=c1a866d4";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repository;
    private List<Serie> series;
    Optional<Serie> serieBuscada;

    public Principal(SerieRepository repository) {
        this.repository=repository;
    }

    public void muestraMenu(){
        var opcion =-1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series
                    2 - Buscar episodios
                    3 - Mostrar series buscadas 
                    4 - Buscar series por titulo  
                    5 - Top 5 mejores series
                    6 - Buscar series por categoria
                    7 - Filtrar series por temporadas y evaluación
                    8 - Buscar episodios por titulo
                    9 - Buscar TOP 5 episodios por serie
                                                         
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriesPorCategoria();
                    break;
                case 7:
                    filtrarSeriesPorTemporadaYEvaluacion();
                    break;
                case 8:
                    buscarEpisodioPorTitulo();
                    break;
                case 9:
                    top5EpisodiosPorSerie();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicacion");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }
    }

    private DatosSerie getDatosSerie(){
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+API_KEY);
        System.out.println(json);
        return conversor.obtenerDatos(json,DatosSerie.class);
    }
    private void buscarEpisodioPorSerie(){
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie de la cual quieres ver los episodios");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serie = series.stream()
                .filter(s->s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();
        if (serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i < serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumoAPI.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DatosTemporadas datosTemporadas = conversor.obtenerDatos(json,DatosTemporadas.class);
                temporadas.add(datosTemporadas);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d->d.episodios().stream()
                            .map(e->new Episodio(d.numero(),e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repository.save(serieEncontrada);
        }
    }
    private void  buscarSerieWeb(){
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        repository.save(serie);
        //datosSeries.add(datos);
        System.out.println(datos);
    }
    private void mostrarSeriesBuscadas() {
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
    private void buscarSeriePorTitulo(){
        System.out.println("Escribe el nombre de la serie de la cual quieres ver los serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        serieBuscada = repository.findByTituloContainsIgnoreCase(nombreSerie);
        if (serieBuscada.isPresent()){
            System.out.println("La serie buscada es : " + serieBuscada.get());
        }else {
            System.out.println("Serie no encontrada");
        }
    }
    private void buscarTop5Series(){
        List<Serie> topSeries = repository.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s->
                System.out.println(
                        "Serie nombre: " + s.getTitulo() +
                                " Evaluación: "+s.getEvaluacion()));
    }
    private void buscarSeriesPorCategoria(){
        System.out.println("Buscar genero de la categoria de la serie deseada");
        var genero = teclado.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repository.findByGenero(categoria);
        System.out.println("las series con genero " + categoria + "son: ");
        seriesPorCategoria.forEach(System.out::println);
    }
    private void filtrarSeriesPorTemporadaYEvaluacion(){
        System.out.println("¿Filtrar séries con cuántas temporadas? ");
        var totalTemporadas = teclado.nextInt();
        teclado.nextLine();
        System.out.println("¿Con evaluación apartir de cuál valor? ");
        var evaluacion = teclado.nextDouble();
        teclado.nextLine();
        List<Serie> filtroSeries = repository.seriesPorTemporadaYEvaluacion(totalTemporadas,evaluacion);
        System.out.println("*** Series filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - evaluacion: " + s.getEvaluacion()));
    }
    private void buscarEpisodioPorTitulo(){
        System.out.println("Escribe el nombre del episodio que deseas buscar");
        var nombreEpisodio = teclado.nextLine();
        List<Episodio> episodiosEncontrados = repository.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(e->
                System.out.printf("Serie: %s Temporada %s Episodio %s Evaluacion %s\n",
                        e.getSerie().getTitulo(),e.getTemporada(),e.getNumeroEpisodio(),e.getEvaluacion()));
    }
    private void top5EpisodiosPorSerie(){
        buscarSeriePorTitulo();
        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repository.top5Episodios(serie);
            topEpisodios.forEach(e->
                    System.out.printf("Serie: %s - Titulo episodio: %s - Temporada %s - Episodio %s - Evaluacion %s\n",
                            e.getSerie().getTitulo(),e.getTitulo(),e.getTemporada(),e.getNumeroEpisodio(),e.getEvaluacion()));

        }
    }
}
