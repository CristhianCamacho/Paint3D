
package com.cc.paint3D.Graficador3D.G3D;

import com.cc.paint3D.Graficador3D.Menus.JMI_Ajustador_de_real;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import java.io.*;
import javax.media.j3d.*;
import javax.swing.JPopupMenu;

public class Figura_caja extends Figura
{
	
    float ancho;
    float alto;
    float largo;
    Box caja;
	
    public Figura_caja()
    {
        ancho = 0.2F;
        alto = 0.2F;
        largo = 0.2F;
    }

    public void agregar_al_Menu(JPopupMenu jpopupmenu)
    {
        jpopupmenu.add(new JMI_Ajustador_de_real(Props.HEIGHT, "Alto"));
        jpopupmenu.add(new JMI_Ajustador_de_real(Props.WIDTH, "Ancho"));
        jpopupmenu.add(new JMI_Ajustador_de_real(Props.LENGTH, "Largo"));
    }

    public Node get_Node(Appearance appearance)
    {
        caja = new Box(ancho, alto, largo, 99, appearance);
                
        /*
        caja.setCapability(Box.ALLOW_CHILDREN_READ);
        caja.setCapability(Box.ALLOW_CHILDREN_WRITE);
        caja.setCapability(Box.ALLOW_CHILDREN_EXTEND);
        */        
        return caja;
    }

    public Object get_Prop(int i)
    {
        switch(i)
        {
        case Props.APPEARANCE: 
            return caja.getAppearance();

        case Props.HEIGHT: 
            return new Float(alto);

        case Props.WIDTH: 
            return new Float(ancho);

        case Props.LENGTH: 
            return new Float(largo);

        case Props.ESCALA_ESCENA_X: 
        
        case Props.TRANSPARENCIA: 
        case Props.X_DIVISIONES: 
        case Props.Y_DIVISIONES: 
        default:
            return super.get_Prop(i);
        }
    }
    
    public void leer_SubObjeto(DataInputStream datainputstream)
        throws IOException
    {
        ancho = datainputstream.readFloat();
        alto = datainputstream.readFloat();
        largo = datainputstream.readFloat();
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
            ancho = float2.floatValue();
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.LENGTH: 
            Float float3 = (Float)obj;
            largo = float3.floatValue();
            Datos_utiles.reemplazar_actual(this);
            break;
            
		case Props.APPEARANCE:      
            caja.setAppearance( (Appearance)obj );
            break;    

		
		
        case Props.X_DIVISIONES: 
        case Props.Y_DIVISIONES: 
        default:
            super.set_Prop(i, obj);
            break;
        }
    }

    public void escribir_SubObjeto(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeInt(CAJA);
        dataoutputstream.writeFloat(ancho);
        dataoutputstream.writeFloat(alto);
        dataoutputstream.writeFloat(largo);
    }

}