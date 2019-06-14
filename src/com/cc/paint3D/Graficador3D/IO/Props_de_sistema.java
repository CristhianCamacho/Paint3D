
package com.cc.paint3D.Graficador3D.IO;

import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.Barra;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_ayuda;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_opciones_de_escala;
import com.cc.paint3D.Graficador3D.Paneles.Panel_Principal;

import java.awt.*;
import java.io.*;

public class Props_de_sistema
{
	public static Font TOOLTIP_FONT = new Font("SansSerif", 1, 13);
    public static DataInputStream dis;
    public static File DIR_ACTUAL;
    public static File DIR_DE_PROPS;
    public static File ARCHIVO_PROPS;    
    public static File DIR_TEMPORAL;
    public static File DIR_DE_APPLETS;    
    public static int PROPS_VERSION = 2;
        
    public Props_de_sistema()
    {
    }

    public static void escribir_Props()
    {
        try
        {
            DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(ARCHIVO_PROPS));
            
            dataoutputstream.writeInt(PROPS_VERSION);
            
            Dimension dimension = Escena.canvas.getSize();
            
            dataoutputstream.writeInt(dimension.width);
            dataoutputstream.writeInt(dimension.height);
            
            
            //dataoutputstream.writeUTF(Manejador_de_archivos.directorio_escojido.getAbsolutePath());
//System.out.println("Props_de_sistema:escribir_Props:Manejador_de_archivos.directorio_escojido.getAbsolutePath()="+Manejador_de_archivos.directorio_escojido.getAbsolutePath() );            
            //dataoutputstream.writeUTF(Manejador_de_archivos.directorio_applet.getAbsolutePath());
                     
            Point point = Barra.handle.getLocation();
            
            dataoutputstream.writeInt(point.x);
            dataoutputstream.writeInt(point.y);
            
            point = Escena.handle.getLocation();
            
            dataoutputstream.writeInt(point.x);
            dataoutputstream.writeInt(point.y);
            
            dataoutputstream.close();
        }
        catch(IOException ioexception) { }
    }

    public static void set_Directorios()
    {
        DIR_ACTUAL = new File(System.getProperty("user.dir"));
        
        DIR_DE_PROPS = new File(DIR_ACTUAL, "props");
        DIR_DE_APPLETS = new File(DIR_ACTUAL, "applets_creados");
        if(!DIR_DE_PROPS.exists())
            DIR_DE_PROPS.mkdir();
        ARCHIVO_PROPS = new File(DIR_DE_PROPS, "propiedades.g3d");
        DIR_TEMPORAL = new File(DIR_DE_PROPS, "temp");
        if(!DIR_TEMPORAL.exists())
            DIR_TEMPORAL.mkdir();
        if(!DIR_DE_APPLETS.exists())
            DIR_DE_APPLETS.mkdir();
    }

    public static boolean existe()
    {
        if(ARCHIVO_PROPS.exists())
        {
            try
            {
                dis = new DataInputStream(new FileInputStream(ARCHIVO_PROPS));
            }
            catch(IOException ioexception) { }
            return true;
        } else
        {
            return false;
        }
    }

    public static boolean set_Propiedades()
    {
        try
        {
            if(dis.readInt() != PROPS_VERSION)
            {
                dis.close();
                ARCHIVO_PROPS.delete();
                return false;
            }

            System.out.println("Props_de_sistema: LD Library Path:" + System.getProperty("java.library.path"));
            System.out.println("Props_de_sistema: Path:" + System.getProperty("path"));

            Panel_Principal.prefdim = new Dimension(dis.readInt(), dis.readInt());
            
            String dir_escojido=System.getProperty("user.dir");
            String dir_applet=dir_escojido+"/"+"applets_creados";
            
            Manejador_de_archivos.directorio_escojido = new File(dir_escojido/*dis.readUTF()*/);
            Manejador_de_archivos.directorio_applet = new File(dir_applet/*dis.readUTF()*/);
                                    
        }
        catch(IOException ioexception) { }
        return true;
    }

    public static Point get_location_Barra()
    {
        int i = 0;
        int j = 0;
        try
        {
            i = dis.readInt();
            j = dis.readInt();
        }
        catch(IOException ioexception) { }
        return new Point(i, j);
    }

    public static Point get_location_Escena()
    {
        int i = 0;
        int j = 0;
        try
        {
            i = dis.readInt();
            j = dis.readInt();
            dis.close();
        }
        catch(IOException ioexception) { }
        return new Point(i, j);
    }

    

}