
package com.cc.paint3D.Graficador3D.G3D;

import com.cc.paint3D.Graficador3D.Menus.JMI_Ajustador_de_real;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_Entero;
import com.cc.paint3D.Graficador3D.interfaces.Props;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import java.io.*;
import javax.media.j3d.*;
import javax.swing.JPopupMenu;

public class Figura_esfera extends Figura
{
	float radio;
    int divisiones;
    Sphere esfera;
	
    public Figura_esfera()
    {
        radio = 0.2F;
        divisiones = 40;
    }

    public void agregar_al_Menu(JPopupMenu jpopupmenu)
    {
        jpopupmenu.add(new JMI_Ajustador_de_real(Props.HEIGHT, "Radio"));
        jpopupmenu.add(new Ajustador_de_Entero(Props.X_DIVISIONES, "Divisiones", 5, 1000));
    }

    public Node get_Node(Appearance appearance)
    {
        esfera = new Sphere(radio, 99, divisiones, appearance);
        return esfera;
    }

    public Object get_Prop(int i)
    {
        switch(i)
        {
        case Props.APPEARANCE: 
            return esfera.getAppearance();

        case Props.NOMBRE: 
            return "Esfera";

        case Props.HEIGHT: 
            return new Float(radio);

        case Props.X_DIVISIONES: 
            return new Integer(divisiones);
        }
        return super.get_Prop(i);
    }

    public void leer_SubObjeto(DataInputStream datainputstream)
        throws IOException
    {
        radio = datainputstream.readFloat();
        divisiones = datainputstream.readInt();
        addChild(get_Node(Color_utiles.iniciar_Appearance()));
    }

    public void set_Prop(int i, Object obj)
    {
        switch(i)
        {
        case Props.HEIGHT: 
            Float float1 = (Float)obj;
            radio = float1.floatValue();
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.X_DIVISIONES: 
            Integer integer = (Integer)obj;
            divisiones = integer.intValue();
            Datos_utiles.reemplazar_actual(this);
            break;
		
		case Props.APPEARANCE:      
            esfera.setAppearance( (Appearance)obj );
            break;		
        		
        case Props.WIDTH: 
        default:
            super.set_Prop(i, obj);
            break;
        }
    }

    public void escribir_SubObjeto(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeInt(ESFERA);
        dataoutputstream.writeFloat(radio);
        dataoutputstream.writeInt(divisiones);
    }

    
}