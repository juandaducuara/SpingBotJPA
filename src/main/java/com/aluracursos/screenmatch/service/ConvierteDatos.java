package com.aluracursos.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//Clase para mapear datos para manejarlos con la dependecia ObjetManajer
public class ConvierteDatos implements IConvierteDatos{
    //Se implemeta IConvierte datos que trae un metodo generico que sirve para cualquiera clase serie o episodios
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
