
package com.cc.paint3D.Graficador3D.G3D;

import com.cc.paint3D.Graficador3D.Menus.Ajustador_de_texto;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_fuentes;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_alineamiento3D;
import com.cc.paint3D.Graficador3D.IO.Tipo_de_texto;
import com.cc.paint3D.Graficador3D.interfaces.Props;

import java.awt.Font;
import java.io.*;
import javax.media.j3d.*;
import javax.swing.JPopupMenu;
import javax.vecmath.Point3f;

public class Figura_texto3D extends Figura
{
	String texto;
    Text3D text3Dobj;
    Shape3D s3d;
    int alineamiento;
    String nombre_de_font;
    int estilo_de_font;
    int tamanio_de_font;

    public Figura_texto3D()
    {
        nombre_de_font = "TimesRoman";
        estilo_de_font = Font.PLAIN;
        tamanio_de_font = 1;
    }

    public void agregar_este_a(TransformGroup transformgroup)
    {
        transformgroup.addChild(super.bg);
    }

    public void agregar_al_Menu(JPopupMenu jpopupmenu)
    {
        jpopupmenu.add(new Ajustador_de_texto());
        jpopupmenu.add(new Menu_de_fuentes());
        jpopupmenu.add(new Menu_de_alineamiento3D());
    }

    public void final_agregar_nodo(Object obj)
    {
        texto = (String)obj;
        super.final_agregar_nodo(obj);
    }

    public Node get_Node(Appearance appearance)
    {
        s3d = get_Shape3D(appearance);
        return s3d;
    }

    public Object get_Prop(int i)
    {
        switch(i)
        {
        case Props.NECESITA_TEXTO: 
            return new Integer(4);

        case Props.APPEARANCE: 
            return s3d.getAppearance();

        case Props.FONT_NOMBRE: 
            return nombre_de_font;

        case Props.FONT_ESTILO: 
            return new Integer(estilo_de_font);

        case Props.FONT_TAMANIO:
            return new Integer(tamanio_de_font);

        case Props.TEXTURA: 
            return super.textura;

        case Props.ALINEAMIENTO: 
            return new Integer(alineamiento);

        case Props.POS: 
        case Props.ESCALA_ESCENA_X: 
        
        case Props.TRANSPARENCIA: 
        case Props.HEIGHT: 
        case Props.WIDTH: 
        case Props.X_DIVISIONES: 
        case Props.Y_DIVISIONES: 
        case Props.LENGTH: 
        case Props.DENSIDAD: 
        case Props.BEHAVIOUR: 
        case Props.NOMBRE: 
        case Props.MATERIAL: 
        default:
            return super.get_Prop(i);
        }
    }

    public Shape3D get_Shape3D(Appearance appearance)
    {
        appearance.setCapability(Appearance.ALLOW_TEXGEN_READ);
        appearance.setCapability(Appearance.ALLOW_TEXGEN_WRITE);
        FontExtrusion fontextrusion = new FontExtrusion();
        Font3D font3d = new Font3D(new Font(nombre_de_font, estilo_de_font, tamanio_de_font), fontextrusion);
        text3Dobj = new Text3D(font3d, texto, new Point3f(0.0F, 0.0F, 0.0F), 0, Color_utiles.get_alineamiento_de_texto3D(alineamiento));
        
        
        text3Dobj.setCapability(Text3D.ALLOW_STRING_WRITE);
        text3Dobj.setCapability(Text3D.ALLOW_INTERSECT);
        text3Dobj.setCapability(Text3D.ALLOW_ALIGNMENT_READ);

        s3d = new Shape3D();
        s3d.setGeometry(text3Dobj);
        s3d.setAppearance(appearance);
        s3d.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
        s3d.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        return s3d;
    }

    public void leer_SubObjeto(DataInputStream datainputstream)
        throws IOException
    {
        nombre_de_font = datainputstream.readUTF();
        estilo_de_font = datainputstream.readInt();
        tamanio_de_font = datainputstream.readInt();
        texto = datainputstream.readUTF();
        datainputstream.readInt();
        alineamiento = datainputstream.readInt();
        addChild(get_Node(Color_utiles.iniciar_Appearance()));
    }

    public void set_Prop(int i, Object obj)
    {
        switch(i)
        {
        case Props.TEXTURA: 
            if(obj != null)
            {
                TexCoordGeneration texcoordgeneration = new TexCoordGeneration();
                texcoordgeneration.setEnable(true);
                s3d.getAppearance().setTexCoordGeneration(texcoordgeneration);
            }
            super.textura = Color_utiles.set_TEXTURAS((Tipo_de_texto)obj, s3d.getAppearance());
            break;

        case Props.TRANSPARENCIA: 
            super.transparencia = Color_utiles.get_Transparencia(obj, s3d.getAppearance());
            break;

        case Props.TEXTO: 
            texto = (String)obj;
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.FONT_NOMBRE: 
            nombre_de_font = (String)obj;
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.FONT_ESTILO: 
            estilo_de_font = ((Integer)obj).intValue();
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.FONT_TAMANIO:
            Integer integer = (Integer)obj;
            tamanio_de_font = integer.intValue();
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.ALINEAMIENTO: 
            Integer integer1 = (Integer)obj;
            alineamiento = integer1.intValue();
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.COLOR: 
        case Props.POS: 
        case Props.APPEARANCE: 
        
        case Props.ESCALA_ESCENA_X: 
        case Props.HEIGHT: 
        case Props.WIDTH: 
        case Props.X_DIVISIONES: 
        case Props.Y_DIVISIONES: 
        case Props.LENGTH: 
        case Props.DENSIDAD: 
        default:
            super.set_Prop(i, obj);
            break;
        }
    }

    public void escribir_SubObjeto(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeInt(TEXTO_3D);
        dataoutputstream.writeUTF(nombre_de_font);
        dataoutputstream.writeInt(estilo_de_font);
        dataoutputstream.writeInt(tamanio_de_font);
        dataoutputstream.writeUTF(texto);
        dataoutputstream.writeInt(Color_utiles.get_alineamiento_de_texto3D(alineamiento));
        dataoutputstream.writeInt(alineamiento);
    }

    
}