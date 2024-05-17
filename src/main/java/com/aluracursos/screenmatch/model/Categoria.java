package com.aluracursos.screenmatch.model;
//Clase que sirve para mapear todas las categorias o varibles de tipoo Enum
public enum Categoria {
    ACCION("Action","Acción"),
    ROMANCE("Romance","Romance"),
    COMEDIA("Comedy","Comedia"),
    DRAMA("Drama","Drama"),
    CRIMEN("Crime","Crimen");

    private String categoriaOmdb;
    private String categoriaEspanol;

    Categoria(String categoriaOmdb,String categoriaEspanol){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    public static  Categoria fromString(String text){
        for (Categoria categoria:Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("NInguna categoria encontrada: " + text);
    }
    //Trae series a español ya que fueron tratadas  en las lineas 4,5...8
    public static  Categoria fromEspanol(String text){
        for (Categoria categoria:Categoria.values()){
            if (categoria.categoriaEspanol.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("NInguna categoria encontrada: " + text);
    }
}
