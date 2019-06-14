
package com.cc.paint3D.Graficador3D.IO;

import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;

import java.io.*;
import java.util.Hashtable;
import java.util.Properties;


public class Prefs
{
	
	public static String POSICION_EN_EL_APPLET= "posicion_en_el_applet";
	public static String HTML_CON_COLOR_DE_BACKGROUND= "html_con_color_de_background";
	
	public static String TRUE_TXT = "T";
    public static String FALSE_TXT = "F";

    public static Properties lista_de_Properties;
    public static File PREFS;

    static 
    {
        PREFS = new File(Props_de_sistema.DIR_DE_PROPS, "prefs.g3d");
    }
	
    public Prefs()
    {
    }

    public static String get(String s)
    {
        if(lista_de_Properties == null)
        {
            lista_de_Properties = new Properties();
            if(!PREFS.exists())
            {
                lista_de_Properties.put(HTML_CON_COLOR_DE_BACKGROUND, TRUE_TXT);
                lista_de_Properties.put("browser", "appletviewer");
                lista_de_Properties.put(POSICION_EN_EL_APPLET, FALSE_TXT);
                if(!escribir_Prefs())
                    return null;
            } else
            {
                try
                {
                    lista_de_Properties.load(new FileInputStream(PREFS));
                }
                catch(Exception exception)
                {
                    new Dialogo_de_error("no se encontro el archivo con las preferencias del sistema");
                    return null;
                }
            }
        }
        return (String)lista_de_Properties.get(s);
    }

    public static boolean escribir_Prefs()
    {
        try
        {
            lista_de_Properties.store(new FileOutputStream(PREFS), "preferencias del AppletPainter");
        }
        catch(Exception exception)
        {
            new Dialogo_de_error("Error al escribir el archivo de preferencias");
            return false;
        }
        return true;
    }

    public static void set(String s, String s1)
    {
        lista_de_Properties.put(s, s1);
    }

    public static void setBoolean(String s, boolean flag)
    {
        if(flag)
            lista_de_Properties.put(s, TRUE_TXT);
        else
            lista_de_Properties.put(s, FALSE_TXT);
    }

    public static boolean getBoolean(String s)
    {
        return get(s).equals(TRUE_TXT);
    }


    
}