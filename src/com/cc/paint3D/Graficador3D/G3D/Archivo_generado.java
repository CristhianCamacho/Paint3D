
package com.cc.paint3D.Graficador3D.G3D;

import java.awt.Dimension;
import java.io.File;
import java.util.Date;

public class Archivo_generado
{
	public static final String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static int contador = 0;
    public String nombre;
    public File file;
    public Dimension dim;
    public String class_main;
    public String nombre_de_los_datos;
    public boolean nohtml;
	
    public Archivo_generado()
    {
    }

    public Archivo_generado(File file1)
    {
        file = file1;
        String s = file1.getName();
        nombre = s.substring(0, s.indexOf("."));
    }

    public Archivo_generado(Archivo_generado archivo_generado)
    {
        file = archivo_generado.file;

    }

    public String set_nombre_de_los_datos()
    {
        nombre_de_los_datos = get_nombre_de_los_datos();
        return nombre_de_los_datos;
    }

    public static String get_nombre_de_los_datos()
    {
        String s = String.valueOf((new Date()).getTime());
        s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(contador) + s.substring(2);
        contador++;
        if(contador == 26)
            contador = 0;
        return s;
    }

    
}