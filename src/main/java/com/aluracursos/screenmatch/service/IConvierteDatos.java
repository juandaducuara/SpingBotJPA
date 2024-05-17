package com.aluracursos.screenmatch.service;
//Deserializar un objeto JSON en una instancia de una clase específica
public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
