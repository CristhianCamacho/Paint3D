
package com.cc.paint3D.Graficador3D.HTML_ayuda;

import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.G3D.Archivo_generado;
import com.cc.paint3D.Graficador3D.IO.Prefs;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_de_fondo;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Creador_de_HTML
{
	
	
    static String APPLET_INICIO= "<applet code=\"";    
    static String APPLET_FIN = "</applet>";
    static String ARCHIVE_INICIO = "\" archive=\"";
    static String EDITADO_INICIO = "<!-- Editado por";
    static String EDITADO_FIN = " -->";
    static String BODY = "</body>";    
    static StringBuffer sb = new StringBuffer(512);
	
    public Creador_de_HTML()
    {
    }

    public static Archivo_generado get_primer_applet(File file)
    {
        Archivo_generado archivo_generado = null;
        if(!file.exists())
        {
            new Dialogo_de_error("No se puede encontrar el archivo "+"'" + file.getAbsolutePath() + "'");//"Unable to find file "
            return null;
        }
        String s = ES_Utiles.get_File_como_String(file);
        
        int j = 0;
        int i = 0;
        do
        {

            j = s.indexOf(APPLET_INICIO, j);
            if(j < 0)
                break;
            j++;
            i++;
        } while(true);
        archivo_generado = new Archivo_generado();

        int k = s.indexOf(APPLET_INICIO, 0);
        k += APPLET_INICIO.length();
        int l = s.indexOf('"', k);
        archivo_generado.class_main = s.substring(k, l);
        get_siguiente_nombre(archivo_generado, s, 0);
        if(archivo_generado.nombre == null)
        {
            return null;
        } else
        {
            archivo_generado.file = file;
            return archivo_generado;
        }
    }

    public static void get_siguiente_nombre(Archivo_generado archivo_generado, String s, int i)
    {
        i = s.indexOf(ARCHIVE_INICIO, i);
        if(i < 0)
        {
            new Dialogo_de_error("El HTML esta Mal - no se encontro el nombre del applet.");//"The HTML is corrupted - no applet name found."
            return;
        } else
        {
            i += ARCHIVE_INICIO.length();
            int j = s.indexOf('.', i);
            archivo_generado.nombre = s.substring(i, j);
            return;
        }
    }

    public static void agregar_Applet_y_guardar_HTML(Archivo_generado archivo_generado)
    {
        
        agregar_cadena_Applet(archivo_generado);

        sb.append("\n");
        
        ES_Utiles.guardar_String_como_File(sb.toString(), archivo_generado.file);
    }

    public static void agregar_cadena_Applet(Archivo_generado archivo_generado)
    {
        sb.append(APPLET_INICIO);
        sb.append(archivo_generado.class_main);
        sb.append(ARCHIVE_INICIO).append(archivo_generado.nombre).append(".jar").append("\" width=");
        sb.append(archivo_generado.dim.width).append(" height=").append(archivo_generado.dim.height);
        sb.append(">\n<param name=\"datos\" value=\"").append(archivo_generado.nombre_de_los_datos).append("\">\n").append(APPLET_FIN);
    }

    public static void agregar_Editado_por()
    {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("MMM ddd, yyy");
        sb.append(EDITADO_INICIO).append(ES_Utiles.PRODUCTO).append(" Fecha:(mes,dia,a�o) ").append(simpledateformat.format(new Date())).append(EDITADO_FIN);
    }

    public static String get_Hex(int i)
    {
        String s = Integer.toHexString(i);
        if(s.length() == 1)
            s = "0" + s;
        return s;
    }

    public static boolean crear_HTML(Archivo_generado archivo_generado)
    {

       boolean centro = Prefs.getBoolean(Prefs.POSICION_EN_EL_APPLET);
        sb.setLength(0);
        agregar_Editado_por();
        sb.append("\n<html>\n<head>\n<title>\n</title>\n</head>\n<body");
        if(Prefs.getBoolean(Prefs.HTML_CON_COLOR_DE_BACKGROUND))
        {
            Color color = null;
            if(Menu_de_color_de_fondo.bg == null)
                color = Color.black;
            else
                color = Menu_de_color_de_fondo.color_de_fondo;
            sb.append(" bgcolor=\"#");
            sb.append(get_Hex(color.getRed()));
            sb.append(get_Hex(color.getGreen()));
            sb.append(get_Hex(color.getBlue()));
            sb.append("\"");
        }
        sb.append(">\n");
        if(centro)
            sb.append("<center>\n");

        {                        
            agregar_Applet_y_guardar_HTML(archivo_generado);
        }
        
        if(centro)
            sb.append("\n</center>");
        sb.append("\n").append(BODY).append("\n</html>");
        return ES_Utiles.guardar_String_como_File(sb.toString(), archivo_generado.file);
    }
	
    
}