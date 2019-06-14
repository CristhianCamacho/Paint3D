
package com.cc.paint3D.Graficador3D.G3D;

import com.cc.paint3D.Graficador3D.Menus.JMI_Ajustador_de_real;
import com.cc.paint3D.Graficador3D.interfaces.Props;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import java.io.*;
import javax.media.j3d.*;
import javax.swing.JPopupMenu;

public class Figura_cubo extends Figura
{
	
    float lado;
    Box cubo;
	
    public Figura_cubo()
    {
        lado = 0.5F;
    }

    public void agregar_al_Menu(JPopupMenu jpopupmenu)
    {
        jpopupmenu.add(new JMI_Ajustador_de_real(Props.WIDTH, "Dimension"));
    }

    public Node get_Node(Appearance appearance)
    {
        cubo = new Box(lado, lado, lado, 99, appearance);
        return cubo;
    }

    public Object get_Prop(int i)
    {
        switch(i)
        {
        case Props.APPEARANCE: 
            return cubo.getAppearance();

        case Props.WIDTH: 
            return new Float(lado);

        case Props.ESCALA_ESCENA_X: 
        
        case Props.TRANSPARENCIA: 
        case Props.HEIGHT: 
        default:
            return super.get_Prop(i);
        }
    }

    public void leer_SubObjeto(DataInputStream datainputstream)
        throws IOException
    {
        lado = datainputstream.readFloat();
        addChild(get_Node(Color_utiles.iniciar_Appearance()));
    }

    public void set_Prop(int i, Object obj)
    {
        switch(i)
        {
        case Props.WIDTH: 
            Float float1 = (Float)obj;
            lado = float1.floatValue();
            Datos_utiles.reemplazar_actual(this);
            break;
        
        case Props.APPEARANCE:      
            cubo.setAppearance( (Appearance)obj );
            break;    

        default:
            super.set_Prop(i, obj);
            break;
        }
    }

    public void escribir_SubObjeto(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeInt(CUBO);
        dataoutputstream.writeFloat(lado);
    }

}