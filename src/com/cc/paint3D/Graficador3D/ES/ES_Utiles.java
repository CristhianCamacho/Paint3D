
package com.cc.paint3D.Graficador3D.ES;

import com.cc.paint3D.Graficador3D.Barra;
import com.cc.paint3D.Graficador3D.Botones.Boton_Flash;
import com.cc.paint3D.Graficador3D.IO.Distribuidor_de_Archivos;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.StringTokenizer;
import javax.swing.AbstractButton;
import javax.swing.JMenuItem;


public class ES_Utiles
{
	public static Dimension DIM = new Dimension(28,28 );
    public static Font FONT_DE_MENSAJES = new Font("dialog", Font.BOLD, 12);
    public static String PRODUCTO = "Paint3D ";//"Simulador de trayectorias";//" GRAFICADOR 3D ";
    public static String AUTOR = " Cristhian Camacho";//" Cristhian Camacho Rodriguez";
    public static int particula_actual=0;
    public static MediaTracker tracker;
    public static Class cls;

    public ES_Utiles()
    {
    }

    public static JMenuItem get_MI(String s, ActionListener actionlistener)
    {
        JMenuItem jmenuitem = new JMenuItem(s);
        jmenuitem.addActionListener(actionlistener);
        return jmenuitem;
    }

    public static Boton_Flash get_Boton_Flash(String s, ActionListener actionlistener)
    {
        Boton_Flash boton_flash = new Boton_Flash(get_System_Image(s), null, DIM, actionlistener, null);
        boton_flash.setRepeat(true);
        boton_flash.setEnabled(false);
        return boton_flash;
    }

    public static int get_Pos_Menu_de_fuentes(String as[], String s)
    {
        int tamanio = as.length;
        int pos = 0;
        
        for(pos = 0; pos < tamanio; pos++)
            if(s.equals(as[pos]))
                break;

        if(pos == tamanio)
            return -1;
        else
            return pos;
    }

    public static int get_Pos_Menu_de_fuentes(int ai[], Integer integer)
    {
        int tamanio = ai.length;
        int pos = 0;
        int valor = integer.intValue();
        for(pos = 0; pos < tamanio; pos++)
            if(valor == ai[pos])
                break;

        if(pos == tamanio)
            return -1;
        else
            return pos;
    }

    public static boolean guardar_String_como_File(String s, File file)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "\r\n");
        if(file.exists())
            file.delete();
        try
        {
            BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
            for(; stringtokenizer.hasMoreTokens(); bufferedwriter.write("\r\n", 0, 2))
            {
                String s1;
                bufferedwriter.write(s1 = stringtokenizer.nextToken(), 0, s1.length());
            }

            bufferedwriter.close();
        }
        catch(IOException ioexception)
        {
            new Dialogo_de_error("Error escribiendo el archivo" + file.getName());
            return false;
        }
        return true;
    }

    public static String get_SystemFile_como_String(String s)
    {
        if(cls == null)
            cls = Barra.handle.getClass();
        char c = '\u0100';
        byte abyte0[] = new byte[1];
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(c);
        byte byte0 = 13;
        try
        {
            InputStream inputstream = cls.getResourceAsStream(s);
            if(inputstream == null)
                return null;
            while(inputstream.read(abyte0, 0, 1) > -1) 
                if(abyte0[0] != byte0)
                    bytearrayoutputstream.write(abyte0, 0, 1);
            inputstream.close();
        }
        catch(IOException ioexception)
        {
            return null;
        }
        return bytearrayoutputstream.toString();
    }
	//Creador_de_HTML, Ver_codigo_HTML
    public static String get_File_como_String(File file)
    {
        if(!file.exists())
            return null;
        int i = (int)file.length();
        byte abyte0[] = new byte[i];
        try
        {
            BufferedInputStream bufferedinputstream = new BufferedInputStream(new FileInputStream(file));
            bufferedinputstream.read(abyte0, 0, i);
            bufferedinputstream.close();
        }
        catch(IOException ioexception)
        {
            return null;
        }
        return new String(abyte0, 0, i);
    }
	
	//Boton_Libreria, Dialogo_de_error, Dialogo_de_pregunta	
    public static URL get_URL_images(String s)
    {
            
        if(cls == null)
            cls = Barra.handle.getClass();
        URL url = cls.getResource("images/" + s);
        if(url == null)
            return null;
        else
            return url;
    }

    public static Image get_System_Image(String s)//la llama Barra
    {
        return cargar_Image(Toolkit.getDefaultToolkit().getImage(get_URL_images(s)));
    }
	//Menu_de_fondo, Datos_utiles, Color_utiles
    public static BufferedImage get_BufferedImage(File file)
    {
        if(!file.exists())
            return null;
        String s = file.getName();
        BufferedImage bufferedimage = null;
        if(s.endsWith(".jpg") || s.endsWith(".JPG"))
        {
            try
            {
                FileInputStream fileinputstream = new FileInputStream(file);
                JPEGImageDecoder jpegimagedecoder = JPEGCodec.createJPEGDecoder(fileinputstream);
                bufferedimage = jpegimagedecoder.decodeAsBufferedImage();
                fileinputstream.close();
            }
            catch(Exception exception)
            {
                new Dialogo_de_error("Error leyendo el Archivo .JPEG ");
                return null;
            }
        } else
        {
            Image image = get_Image(file);
            bufferedimage = new BufferedImage(image.getWidth(null), image.getHeight(null), 1);
            Graphics2D graphics2d = bufferedimage.createGraphics();
            graphics2d.drawImage(image, 0, 0, null);
        }
        return bufferedimage;
    }

    public static Image get_Image(File file)
    {
        return cargar_Image(Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath()));
    }
	//Boton_Flash, this osea este
    public static Image cargar_Image(Image image)
    {
        try
        {
            if(tracker == null)
                tracker = new MediaTracker(Barra.handle);
            tracker.addImage(image, 0);
            tracker.waitForID(0);
        }
        catch(Exception exception)
        {
            return null;
        }
        if(image.getWidth(null) <= 0)
            return null;
        else
            return image;
    }
	/*
    public static void BubbleSortTexto(String as[], int i)
    {
        int l = i - 1;
        boolean flag = false;
        do
        {
            for(int j = 0; j < l; j++)
            {
                int k = j + 1;
                if(as[j].compareTo(as[k]) > 0)
                {
                    String s = as[k];
                    as[k] = as[j];
                    as[j] = s;
                    flag = true;
                }
            }

            if(flag)
            {
                flag = false;
                l--;
            } else
            {
                return;
            }
        } while(true);
    }
	*/
    
}