
package com.cc.paint3D.Graficador3D.G3D;

import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos_temporales;
import com.cc.paint3D.Graficador3D.IO.Informacion_de_BH;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Selector;
import com.cc.paint3D.Graficador3D.Menus.Llamando_al_Menu_de_seleccion;
import com.cc.paint3D.Graficador3D.Menus.Ajustador_de_color;
import com.cc.paint3D.Graficador3D.Menus.Ajustador_de_texto;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_fuentes;

import com.sun.j3d.utils.geometry.Text2D;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import javax.media.j3d.*;
import javax.swing.JPopupMenu;

public class Figura_texto2D extends TransformGroup
    implements Tipo_componente, Props, Selector
{

	BranchGroup bg;
    String texto;
    String nombre;
    Text2D text2Dobj;
    String nombre_de_font;
    int estilo_de_font;
    int tamanio_de_font;
    Color color;
    int lados;
    int pos;
    Informacion_de_BH bh;

    public Figura_texto2D()
    {
        nombre_de_font = "Helvetica";
        estilo_de_font = Font.BOLD;
        tamanio_de_font = 50;
        color = Color.white;
        lados = 1;
        texto = "";
        setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        bg = new BranchGroup();
        bg.setCapability(BranchGroup.ALLOW_DETACH);
        bg.addChild(this);
    }

    public void agregar_este_a(TransformGroup transformgroup)
    {
        transformgroup.addChild(bg);
    }

    public void llamada(Llamando_al_Menu_de_seleccion llamando_al_Menu_de_seleccion)
    {
        lados = llamando_al_Menu_de_seleccion.get_estado();
        Datos_utiles.reemplazar_actual(this);
    }
    
    public void separar_este()
    {
        bg.detach();
    }

    public void final_agregar_nodo(Object obj)
    {
        texto = (String)obj;
        addChild(text2Dobj = get_texto_2D());
    }

    public Object get_Prop(int i)
    {
        switch(i)
        {
        case Props.NECESITA_TEXTO: 
            return new Integer(4);

        case Props.NOMBRE: 
            return nombre;

        case Props.APPEARANCE: 
            return text2Dobj.getAppearance();

        case Props.POS: 
            return new Integer(pos);

        case Props.COLOR: 
            return color;

        case Props.FONT_NOMBRE: 
            return nombre_de_font;

        case Props.FONT_ESTILO: 
            return new Integer(estilo_de_font);

        case Props.FONT_TAMANIO:
            return new Integer(tamanio_de_font);

        case Props.BEHAVIOUR: 
            return bh;

        case Props.TEXTURA: 
        case Props.ESCALA_ESCENA_X: 
        
        case Props.TRANSPARENCIA: 
        case Props.HEIGHT: 
        case Props.WIDTH: 
        case Props.X_DIVISIONES: 
        case Props.Y_DIVISIONES: 
        case Props.LENGTH: 
        case Props.DENSIDAD: 
        case Props.ALINEAMIENTO: 
        case Props.MATERIAL: 
        default:
            return null;
        }
    }

    Text2D get_texto_2D()
    {
        Text2D text2d = new Text2D(texto, Manejador_de_archivos_temporales.getColor3f(color), nombre_de_font, tamanio_de_font, estilo_de_font);
        
        text2d.getGeometry().setCapability(Geometry.ALLOW_INTERSECT);
        text2d.setCapability(Text2D.ALLOW_APPEARANCE_READ);
        text2d.setCapability(Text2D.ALLOW_APPEARANCE_WRITE);
        
        if(lados == 1)
        {
            PolygonAttributes polygonattributes = new PolygonAttributes();
            polygonattributes.setCullFace(PolygonAttributes.CULL_NONE);
            polygonattributes.setBackFaceNormalFlip(true);
            text2d.getAppearance().setPolygonAttributes(polygonattributes);
        }
        return text2d;
    }

    public void leer_Objeto(DataInputStream datainputstream)
        throws IOException
    {
        Color_utiles.leer_Transform3D(datainputstream, this);
        texto = datainputstream.readUTF();
        color = Color_utiles.leer_Color(datainputstream);
        nombre_de_font = datainputstream.readUTF();
        tamanio_de_font = datainputstream.readInt();
        estilo_de_font = datainputstream.readInt();
        lados = datainputstream.readInt();
        addChild(text2Dobj = get_texto_2D());
        if(datainputstream.readBoolean())
            bh = Informacion_de_BH.leer_BH(datainputstream);
    }

    public void set_Menu(JPopupMenu jpopupmenu)
    {
        jpopupmenu.add(new Ajustador_de_color());
        jpopupmenu.addSeparator();
        jpopupmenu.add(new Ajustador_de_texto());
        jpopupmenu.add(new Menu_de_fuentes());
        Llamando_al_Menu_de_seleccion llamando_al_Menu_de_seleccion = new Llamando_al_Menu_de_seleccion("Double cara", "No", "Si", this);
        llamando_al_Menu_de_seleccion.set_estado(lados);
        jpopupmenu.add(llamando_al_Menu_de_seleccion);
    }

    public void set_Prop(int i, Object obj)
    {
        switch(i)
        {
        case Props.NOMBRE: 
            nombre = (String)obj;
            break;

        case Props.POS: 
            Integer integer = (Integer)obj;
            pos = integer.intValue();
            break;

        case Props.COLOR: 
            color = (Color)obj;
            Datos_utiles.reemplazar_actual(this);            

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
            Integer integer1 = (Integer)obj;
            tamanio_de_font = integer1.intValue();
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.BEHAVIOUR: 
            bh = (Informacion_de_BH)obj;
            break;
            
        case Props.APPEARANCE:      
            text2Dobj.setAppearance( (Appearance)obj );
            break;            
        }
    }
	
    public void escribir_Objeto(DataOutputStream dataoutputstream)
        throws IOException
    {
    	dataoutputstream.writeUTF(getClass().getName().substring("com.cc.paint3D.Graficador3D.G3D.".length() ));
        dataoutputstream.writeUTF(getClass().getName());
        
        dataoutputstream.writeInt(Props.TEXTURA);
        Color_utiles.escribir_Transform3D(dataoutputstream, this);
        dataoutputstream.writeUTF(texto);
        Color_utiles.escribir_Color(dataoutputstream, color);
        dataoutputstream.writeUTF(nombre_de_font);
        dataoutputstream.writeInt(tamanio_de_font);
        dataoutputstream.writeInt(estilo_de_font);
        dataoutputstream.writeInt(lados);
        if(bh == null)
            dataoutputstream.writeBoolean(false);
        else
            bh.escribir_BH(dataoutputstream);
    }
/*
    public void escribir_SubObjeto(DataOutputStream dataoutputstream)
        throws IOException
    {
    }    
    public void leer_SubObjeto(DataInputStream datainputstream)
        throws IOException
    {
    }
    public Node get_Node(Appearance appearance)
    {
        Shape3D s3d=null; //= get_Shape3D(appearance);
        return s3d;
    }
    public void agregar_al_Menu(JPopupMenu jpopupmenu)
    {
        //jpopupmenu.add(new Ajustador_de_texto());
        //jpopupmenu.add(new Menu_de_fuentes());
        //jpopupmenu.add(new Menu_de_alineamiento3D());
    }
*/    	
}