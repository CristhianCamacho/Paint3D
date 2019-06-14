
package com.cc.paint3D.Graficador3D.IO;

import com.cc.paint3D.Graficador3D.ES.Dialogo_de_busqueda_de_Archivos;

import java.io.File;


public class Manejador_de_archivos
{
	public static final int JPEG = 0;
    public static final int GIF = 1;
    //public static final int BMP = 2;
    //public static int PNG = 2;
    public static File directorio_escojido;
    public static File directorio_applet;
    public static File file_actual;//
    
	
    public Manejador_de_archivos()
    {
    }

    public static File get_buscador_de_archivos(String s)
    {
        File file = Dialogo_de_busqueda_de_Archivos.preguntar(directorio_escojido, s, JPEG, "nombre del Archivo", null);
        if(file != null)
            directorio_escojido = file.getParentFile();
        return file;
    }

    public static File get_buscador_de_imagen(String s)
    {
        String as[] = {
            ".jpg", ".gif"
        };
        File file = Dialogo_de_busqueda_de_Archivos.preguntar(directorio_escojido, s, GIF, "nombre del Archivo de la imagen", as);
        if(file != null)
            directorio_escojido = file.getParentFile();
        return file;
    }

    public static File get_buscador_de_Applet(String s)
    {
        String as[] = {
            ".html"
        };
        File file = Dialogo_de_busqueda_de_Archivos.preguntar(directorio_applet, s, JPEG, "nombre del Archivo HTML", as);
        if(file != null)
            directorio_applet = file.getParentFile();
        return file;
    }

    public static File check_extension(File file, String s)
    {
        String s1 = file.getName();
        int i = s1.indexOf('.');
        int j = s1.length() - 1;
        if(i == j)
            return new File(file.getParent(), s1 + s);
        if(i > -1)
            return file;
        else
            return new File(file.getParent(), s1 + '.' + s);
    }
    
    public static File get_applet_para_guardar(String s)
    {
        return get_Archivo_para_guardar(s, directorio_applet);
    }

    public static File get_Archivo_para_guardar(String s, File file)
    {
        String as[] = {
            ".html"
        };
        File file1 = Dialogo_de_busqueda_de_Archivos.preguntar(file, s, Dialogo_de_busqueda_de_Archivos.GUARDAR, "nombre del Archivo HTML", as);//"HTML file name"
        if(file1 == null)
        {
            return null;
        } else
        {
            file = file1.getParentFile();
            return check_extension(file1, "html");
        }
    }

    
}