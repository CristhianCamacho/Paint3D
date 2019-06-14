
package com.cc.paint3D.Graficador3D.G3D;

import com.cc.paint3D.Graficador3D.IO.Tipo_de_texto;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.G3D.Datos_utiles;

import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Color;
import java.io.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Color_utiles
{

    public Color_utiles()
    {
    }

    public static int get_alineamiento_de_texto3D(int i)
    {
        switch(i)
        {
        case 0: 
            return Text3D.PATH_RIGHT;//1

        case 1: 
            return Text3D.PATH_DOWN;//3

        case 2: 
            return Text3D.PATH_UP;//2

        case 3: 
            return Text3D.PATH_LEFT;//0
        }
        return Text3D.PATH_RIGHT;
    }

    public static int get_atributos_de_TEXTURAS(int i)
    {
        switch(i)
        {
        case 1: 
            return Datos_utiles.TEXTURAS[i-1];//2

        case 2: 
            return Datos_utiles.TEXTURAS[i-1];//4

        case 3: 
            return Datos_utiles.TEXTURAS[i-1];//5

        case 4: 
            return Datos_utiles.TEXTURAS[i-1];//3
        }
        return 0;
    }

    public static int get_Transparencia(Object obj, Appearance appearance)
    {
        Integer integer = (Integer)obj;
        int i = integer.intValue();
        float f = (float)i / 100F;
        TransparencyAttributes transparencyattributes = new TransparencyAttributes(TransparencyAttributes.BLENDED, f);
        transparencyattributes.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
        appearance.setTransparencyAttributes(transparencyattributes);
        return i;
    }

    public static Appearance iniciar_Appearance()
    {
        Appearance appearance = new Appearance();
        appearance.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
        appearance.setCapability(Appearance.ALLOW_MATERIAL_READ);
        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        appearance.setCapability(Appearance.ALLOW_TEXTURE_READ);
        appearance.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
        appearance.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
        appearance.setCapability(Appearance.ALLOW_TEXTURE_ATTRIBUTES_WRITE);
        appearance.setCapability(Appearance.ALLOW_TEXTURE_ATTRIBUTES_READ);
        
        appearance.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_READ);
        appearance.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
        appearance.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        
        
        Material material = new Material();
        material.setCapability(Material.ALLOW_COMPONENT_READ);
        material.setCapability(Material.ALLOW_COMPONENT_WRITE);
        appearance.setMaterial(material);
        return appearance;
    }

    public static Color leer_Color(DataInputStream datainputstream)
        throws IOException
    {
        int i = datainputstream.readInt();
        if(i == 0)
            return null;
        else
            return new Color(i);
    }
	//Figura_luz
    public static Point3f leer_Point3f(DataInputStream datainputstream)
        throws IOException
    {
        return new Point3f(datainputstream.readFloat(), datainputstream.readFloat(), datainputstream.readFloat());
    }
	//Color color=Color.red;
	//color.hashCode()
    /*public static String leer_Texto(DataInputStream datainputstream)
        throws IOException
    {
        String s = datainputstream.readUTF();
        if(s.equals(""))
            return null;
        else
            return s;
    }*/
    
    
	//Figura_texto2D
    public static void leer_Transform3D(DataInputStream datainputstream, TransformGroup transformgroup)
        throws IOException
    {
        float af[] = new float[16];
        for(int i = 0; i < 16; i++)
            af[i] = datainputstream.readFloat();

        Transform3D transform3d = new Transform3D(af);
        transformgroup.setTransform(transform3d);
    }
	//Figura_luz
    public static Vector3f leer_Vector3f(DataInputStream datainputstream)
        throws IOException
    {
        return new Vector3f(datainputstream.readFloat(), datainputstream.readFloat(), datainputstream.readFloat());
    }
	//Figura, Figura_texto3D	
    public static Tipo_de_texto set_TEXTURAS(Tipo_de_texto tipo_de_texto, Appearance appearance)
    {
        if(tipo_de_texto == null)
        {
            appearance.setTexture(null);
            return null;
        }
        java.awt.image.BufferedImage bufferedimage = ES_Utiles.get_BufferedImage(tipo_de_texto.path);
        if(bufferedimage == null)
        {
            return null;
        } else
        {
            TextureLoader textureloader = new TextureLoader(bufferedimage);
            javax.media.j3d.Texture texture = textureloader.getTexture();
            TextureAttributes textureattributes = new TextureAttributes();
            textureattributes.setTextureMode(get_atributos_de_TEXTURAS(tipo_de_texto.tipo));
            appearance.setTexture(texture);
            appearance.setTextureAttributes(textureattributes);
            return tipo_de_texto;
        }
    }
	//Figura , Figura_texto2D
    public static void escribir_Color(DataOutputStream dataoutputstream, Color color)
        throws IOException
    {
        if(color == null)
            dataoutputstream.writeInt(0);
        else
            dataoutputstream.writeInt(color.getRGB());
    }
	//Figura_luz
    public static void escribir_Point3f(DataOutputStream dataoutputstream, Point3f point3f)
        throws IOException
    {
        dataoutputstream.writeFloat(((Tuple3f) (point3f)).x);
        dataoutputstream.writeFloat(((Tuple3f) (point3f)).y);
        dataoutputstream.writeFloat(((Tuple3f) (point3f)).z);
    }
/*
    public static void escribir_Texto(DataOutputStream dataoutputstream, String s)
        throws IOException
    {
        if(s == null)
            dataoutputstream.writeUTF("");
        else
            dataoutputstream.writeUTF(s);
    }
*/
	//Figura, Figura_texto2D, Figura_luz
    public static void escribir_Transform3D(DataOutputStream dataoutputstream, TransformGroup transformgroup)
        throws IOException
    {
        Transform3D transform3d = new Transform3D();
        transformgroup.getTransform(transform3d);
        float af[] = new float[16];
        transform3d.get(af);
        for(int i = 0; i < 16; i++)
            dataoutputstream.writeFloat(af[i]);

    }
	//Figura_luz
    public static void escribir_Vector3f(DataOutputStream dataoutputstream, Vector3f vector3f)
        throws IOException
    {
        dataoutputstream.writeFloat(((Tuple3f) (vector3f)).x);
        dataoutputstream.writeFloat(((Tuple3f) (vector3f)).y);
        dataoutputstream.writeFloat(((Tuple3f) (vector3f)).z);
    }
}