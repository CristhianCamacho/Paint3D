
package com.cc.paint3D.Graficador3D.G3D;

import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.Menus.JMI_Ajustador_de_real;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_Entero;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Primitive;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.media.j3d.Appearance;
import javax.media.j3d.Node;
import java.io.*;
import javax.swing.JPopupMenu;

public class Figura_cono extends Figura
{
	Cone cono;
    
    float alto;
    float radio;
    int xdivisiones;
    int ydivisiones;

    public Figura_cono()
    {
        alto = 0.5F;
        radio = 0.5F;
        
        xdivisiones = 40;
        ydivisiones = 40;        
    }
    
    public void agregar_al_Menu(JPopupMenu jpopupmenu)
    {
        jpopupmenu.add(new JMI_Ajustador_de_real(Props.HEIGHT, "Alto"));
        jpopupmenu.add(new JMI_Ajustador_de_real(Props.WIDTH, "Radio"));
        jpopupmenu.add(new Ajustador_de_Entero(Props.X_DIVISIONES, "divisiones en X", 1, 1000));
        jpopupmenu.add(new Ajustador_de_Entero(Props.Y_DIVISIONES, "divisiones en Y", 1, 1000));
    }

    public Node get_Node(Appearance appearance)
    {
        cono = new Cone(radio, alto, 99, xdivisiones, ydivisiones, appearance);
        return cono;
    }

    public Object get_Prop(int i)
    {
    	switch(i)
        {        
        case Props.APPEARANCE: 
            return cono.getAppearance();
            
         case Props.HEIGHT: 
            return new Float(alto);

        case Props.WIDTH: 
            return new Float(radio);

        case Props.X_DIVISIONES: 
            return new Integer(xdivisiones);

        case Props.Y_DIVISIONES: 
            return new Integer(ydivisiones);

        case Props.ESCALA_ESCENA_X: 
        
        case Props.TRANSPARENCIA:    
        }
        return super.get_Prop(i);
    }
    
    public void leer_SubObjeto(DataInputStream datainputstream)
        throws IOException
    {
        radio = datainputstream.readFloat();
        alto = datainputstream.readFloat();
        xdivisiones = datainputstream.readInt();
        ydivisiones = datainputstream.readInt();
        addChild(get_Node(Color_utiles.iniciar_Appearance()));
    }
    
    public void set_Prop(int i, Object obj)
    {
    	switch(i)
        {
        case Props.HEIGHT: 
            Float float1 = (Float)obj;
            alto = float1.floatValue();
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.WIDTH: 
            Float float2 = (Float)obj;
            radio = float2.floatValue();
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.X_DIVISIONES: 
            Integer integer = (Integer)obj;
            xdivisiones = integer.intValue();
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.Y_DIVISIONES: 
            Integer integer1 = (Integer)obj;
            ydivisiones = integer1.intValue();
            Datos_utiles.reemplazar_actual(this);
            break;
        	
        	case Props.APPEARANCE:      
            cono.setAppearance( (Appearance)obj );
            break; 
		
        	default:
            super.set_Prop(i, obj);
            break;
        }
    } 

    public void escribir_SubObjeto(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeInt(CONO);
        dataoutputstream.writeFloat(radio);
        dataoutputstream.writeFloat(alto);
        dataoutputstream.writeInt(xdivisiones);
        dataoutputstream.writeInt(ydivisiones);
    }

    
}