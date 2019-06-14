
package com.cc.paint3D.Graficador3D.IO;

import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.G3D.Datos_utiles;
import com.cc.paint3D.Graficador3D.IO.Distribuidor_de_Archivos;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_iluminacion;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_de_niebla;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_ambiente;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_fondo;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_de_fondo;


import java.awt.Color;
import java.awt.Dimension;
import java.io.*;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3d;


public class Lector_de_distribucion
    implements Props
{

    public Lector_de_distribucion()
    {
    }

    public static void leer_todo(DataInputStream datainputstream, Dimension dimension)
        throws IOException
    {        
        Escena.scale_actual_x=datainputstream.readFloat();
        Escena.scale_actual_y=datainputstream.readFloat();
        Escena.scale_actual_z=datainputstream.readFloat();
        
        Transform3D transform3d = new Transform3D();
        transform3d.setScale( new Vector3d(Escena.scale_actual_x,
        								   Escena.scale_actual_y,
        								   Escena.scale_actual_z) );
        
        Escena.tg.setTransform(transform3d);
        
        
        
                
        javax.media.j3d.BoundingSphere boundingsphere = Datos_utiles.boundingSphere;
        int i = 0;
        i = datainputstream.readInt();
        if(i != 0)
            Menu_de_color_de_fondo.agregar_Color(new Color(i));
        i = datainputstream.readInt();
        if(i != 0)
            {	//sin esto no se ve la imagen de fondo del HTML cuando se ejecuta
            	Menu_de_fondo.menu_de_seleccion_multiple_image.set_estado(i);           
               	Menu_de_fondo.agregar_Background(i, get_File_actual(datainputstream.readUTF()));
        	}
        i = datainputstream.readInt();
        if(i != 0)
            Menu_de_color_ambiente.agregar_Ambient(new Color(i));
        i = datainputstream.readInt();
        if(i != 0)
        {
            Menu_de_color_de_niebla.agregar_niebla(new Color(i));
            Menu_de_color_de_niebla.handle.set_Prop(Props.DENSIDAD, new Integer(datainputstream.readInt()));
        }
        i = datainputstream.readInt();
        for(int j = 0; j < i; j++)
            Menu_de_iluminacion.agregar_luz(datainputstream);

        i = datainputstream.readInt();
        for(int k = 0; k < i; k++)
        {
            Object obj = null;
            try
            {
                String este=datainputstream.readUTF();

                obj = Class.forName("com.cc.paint3D.Graficador3D.G3D." + este).newInstance();
            }
            catch(Exception exception)
            {
                return;
            }
            Tipo_componente tipo_componente = (Tipo_componente)obj;
            tipo_componente.set_Prop(Props.NOMBRE, datainputstream.readUTF());
            
            datainputstream.readInt();
            
            tipo_componente.leer_Objeto(datainputstream);
            Escena.reemplazar_componente(k, tipo_componente);
            tipo_componente.set_Prop(Props.POS, new Integer(k));
        }

    }

    public static File get_File_actual(String s)
    {
        File file = new File(s);
        return Distribuidor_de_Archivos.get_desde_Zip(file.getName());
    }
}