
package com.cc.paint3D.Graficador3D.IO;

import com.cc.paint3D.Graficador3D.Barra;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.G3D.Archivo_generado;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos;
import com.cc.paint3D.Graficador3D.HTML_ayuda.Creador_de_HTML;
import com.cc.paint3D.Graficador3D.G3D.Datos_utiles;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.Paneles.Barra_de_herramientas;

import java.awt.Component;
import java.awt.Dimension;
import java.io.*;
import java.util.*;
import java.util.zip.*;


public class Distribuidor_de_Archivos
{
	
    static final String NOMBRE_TEMPORAL = "distribucion_temporal";
    static String GUARDANDO_MSG = "Guardando el applet actual.";
    public static Archivo_generado actual_archivo_generado;
    static String libreria_de_class[] = {
        "Graficador3D_applet.class", "M_KAction.class", "M_KAction_funcionamiento.class"
    };
    static Date dia_de_hoy;
    static ZipOutputStream zipOutputStream;
    static Hashtable zipped;
    static ZipFile zipFile;
    public static byte buffer_512[] = new byte[512];

    public Distribuidor_de_Archivos()
    {
    }

    public static void abrir_HTML()
    {
        File file = Manejador_de_archivos.get_buscador_de_Applet("Escoja la distribucion para Abrir");//"Select layout to open"
        if(file == null)
            return;
        Archivo_generado archivo_generado = Creador_de_HTML.get_primer_applet(file);
        if(archivo_generado != null)
            abrir_Distribucion(archivo_generado);
    }

    public static void guardar_todo(boolean flag)
    {
        if(!flag && actual_archivo_generado != null)
        {

            guardar(actual_archivo_generado);
        } else
        {
            File file = Manejador_de_archivos.get_applet_para_guardar(GUARDANDO_MSG);
            if(file == null)
                return;
            Archivo_generado archivo_generado = new Archivo_generado(file);
            if(guardar(archivo_generado))
                set_actual_Archivo_generado(archivo_generado);
        }
    }

    public static void set_actual_Archivo_generado(Archivo_generado archivo_generado)
    {
        actual_archivo_generado = archivo_generado;
        Barra.set_cabecera_de_ventana(archivo_generado);
    }

    public static boolean guardar(Archivo_generado archivo_generado)
    {
        archivo_generado.dim = Escena.canvas.getSize();
        boolean flag = false;

        dia_de_hoy = new Date();
        if(zipped == null)
            {
            	zipped = new Hashtable(20);            
            }
        else
            zipped.clear();
        File file = new File(Props_de_sistema.DIR_TEMPORAL, NOMBRE_TEMPORAL+".jar");//TMP_NAME "temp_layout.jar"
        try
        {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
        }
        catch(IOException ioexception)
        {
            new Dialogo_de_error("Error creating JAR file " + file.getName());
            return false;
        }
        if(!agregar_Manifest())
            return false;
        File file1 = new File(Props_de_sistema.DIR_TEMPORAL, archivo_generado.nombre_de_los_datos != null ? archivo_generado.nombre_de_los_datos + ".t" : archivo_generado.set_nombre_de_los_datos() + ".t");
        try
        {
            DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
            dataoutputstream.writeInt(1);

            Datos_utiles.escribir_todo(dataoutputstream);
            dataoutputstream.close();
        }
        catch(IOException ioexception1)
        {
            new Dialogo_de_error("Error '" + ioexception1.getMessage() + "' escribiendo los parametros.");
            return false;
        }
        if(!agregar_ZipFile(file1))
        {
            new Dialogo_de_error("No se pueden cargar los parametros del archivo.");
            return false;
        }
        Vector vector = Datos_utiles.files;
        int i = vector.size();
        for(int j = 0; j < i; j++)
        {
            File file2 = (File)vector.elementAt(j);
            if(!agregar_ZipFile(file2))
                return false;
        }

        i = libreria_de_class.length;
        archivo_generado.class_main = libreria_de_class[0];
        
        for(int k = 0; k < i; k++)
            if(!agregar_ZipSystemFile(libreria_de_class[k]))
            {
                new Dialogo_de_error("No de puede guardar " + libreria_de_class[k] + " en un archivo .JAR ");
                return false;
            }

        try
        {
            zipOutputStream.close();
        }
        catch(IOException ioexception2)
        {
            new Dialogo_de_error("Error cerrando el .JAR");
            return false;
        }

        if(!archivo_generado.nohtml && !Creador_de_HTML.crear_HTML(archivo_generado))
            return false;
        File file3 = new File(archivo_generado.file.getParent(), archivo_generado.nombre + ".jar");
        if(file3.exists())
            file3.delete();
        file.renameTo(file3);
        Archivos_recientes.agregar_Archivo_reciente(archivo_generado.file);
        return true;
    }

    public static boolean agregar_ZipSystemFile(String s)
    {
        if(zipped.containsKey(s))
            return true;
        zipped.put(s, "");
        try
        {
            InputStream inputstream = ES_Utiles.cls.getResourceAsStream("applets/" + s);
       
             
            zipOutputStream.putNextEntry(new ZipEntry(s));
            int i;
            while((i = inputstream.read(buffer_512)) != -1) 
                {                	
                	zipOutputStream.write(buffer_512, 0, i);
            	}
            inputstream.close();
        }
        catch(IOException ioexception)
        {
            new Dialogo_de_error("Error escribiendo el .JAR " + s);
            return false;
        }
        return true;
    }

    public static boolean agregar_ZipFile(File file)
    {
        if(!file.exists())
        {
            new Dialogo_de_error("No se puede encontrar el Archivo " + file.getAbsolutePath() + " para cargar ");
            return false;
        }
        String s = file.getName();
        if(zipped.containsKey(s))
        {
            return true;
        } else
        {
            zipped.put(s, "");
            return agregando_para_Zip(file);
        }
    }

    public static boolean agregando_para_Zip(File file)
    {
        if(!file.exists())
        {
            new Dialogo_de_error("No se puede encontrar el archivo " + file);
            return false;
        }
        String s = file.getName();
        try
        {
            FileInputStream fileinputstream = new FileInputStream(file);
            zipOutputStream.putNextEntry(new ZipEntry(s));
            int i;
            while((i = fileinputstream.read(buffer_512)) != -1) 
                zipOutputStream.write(buffer_512, 0, i);
            fileinputstream.close();
        }
        catch(IOException ioexception)
        {
            new Dialogo_de_error("Error escribiendo el .JAR " + s);
            return false;
        }
        return true;
    }

    public static boolean agregar_Manifest()
    {
        try
        {
            zipOutputStream.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
            String s = "Manifest-Version: 1.0\r\n";
            s+="Created-By: 1.5.0 (Sun Microsystems Inc.)\n";
			
            byte abyte0[] = s.getBytes();
            zipOutputStream.write(abyte0, 0, abyte0.length);
        }
        catch(IOException ioexception)
        {
            new Dialogo_de_error("Error escribiendo el manifest del .JAR ");
            return false;
        }
        return true;
    }

    public static void abrir_Distribucion(Archivo_generado archivo_generado)
    {
        if(leer_Distribucion(archivo_generado))
        {
            actual_archivo_generado = archivo_generado;
            Barra.set_cabecera_de_ventana(actual_archivo_generado);
        }
    }

    public static boolean leer_Distribucion(Archivo_generado archivo_generado)
    {
                
        File file = new File(archivo_generado.file.getParent(), archivo_generado.nombre + ".jar");
        if(!file.exists())
        {
            new Dialogo_de_error(file.getName() + " se necesita pero No se encontro.");
            return false;
        }
        try
        {
            zipFile = new ZipFile(file);
        }
        catch(IOException ioexception)
        {
            new Dialogo_de_error(file.getName() + " No se puede leer del archivo .JAR ");
            return false;
        }
        Enumeration enumeration = zipFile.entries();
        Object obj = null;
        String s = null;
        while(enumeration.hasMoreElements()) 
        {
            ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
            s = zipentry.getName();

            if(s.endsWith(".t"))
                break;
        }
        if(!s.endsWith(".t"))
        {
            new Dialogo_de_error("Los parametros del Applet no se encuentran en el .JAR ");
            return false;
        }
        String temp=s.substring(0, s.indexOf(".t"));

        archivo_generado.nombre_de_los_datos = temp;
        try
        {
            ZipEntry zipentry1 = zipFile.getEntry(s);
            DataInputStream datainputstream = new DataInputStream(zipFile.getInputStream(zipentry1));
        //lo primero un uno
            if(datainputstream.readInt() != 1)
            {
                new Dialogo_de_error("Version incompatible del applet - No se puede editar");
                datainputstream.close();
                return false;
            }

         
            Barra_de_herramientas.nueva_distribucion();
            int int_temp_1=datainputstream.readInt();      
            int int_temp_2=datainputstream.readInt();
  
            Dimension dimension = new Dimension(int_temp_1, int_temp_2);
            if(!dimension.equals(Escena.canvas.getSize()))
                Escena.setDimension(dimension);

            
           
            Lector_de_distribucion.leer_todo(datainputstream, dimension);
            
            datainputstream.close();
            zipFile.close();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            new Dialogo_de_error("Error leyendo los parametros del archivo .JAR ");//"Error reading parameter file from the JAR file."
            return false;
        }
        Archivos_recientes.agregar_Archivo_reciente(archivo_generado.file);
        return true;
    }

    public static File get_desde_Zip(String s)
    {
        ZipEntry zipentry = zipFile.getEntry(s);
        if(zipentry == null)
        {
            new Dialogo_de_error("La entrada " + s + " no se encuentra en el .JAR");
            return null;
        }
        File file = new File(Props_de_sistema.DIR_TEMPORAL, s);
        if(file.exists())
            return file;
        try
        {
            InputStream inputstream = zipFile.getInputStream(zipentry);
            FileOutputStream fileoutputstream = new FileOutputStream(file);
            int i;
            while((i = inputstream.read(buffer_512)) != -1) 
                fileoutputstream.write(buffer_512, 0, i);
            fileoutputstream.close();
            inputstream.close();
        }
        catch(IOException ioexception)
        {
            new Dialogo_de_error("Error leyendo " + file + " en el .JAR.");
            return null;
        }
        return file;
    }
    
}