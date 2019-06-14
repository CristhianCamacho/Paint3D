
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Fijable;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos_temporales;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.G3D.Figura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.swing.*;
import javax.vecmath.Color3f;

import javax.media.j3d.*;
import javax.vecmath.*;

public class Menu_de_escala_de_componente extends JMenu implements Fijable, Props
{
	String ESCALA_X;
    String ESCALA_Y;
    String ESCALA_Z;

    public Menu_de_escala_de_componente()
    {
    	super(Graficador3D.SCALE);
        ESCALA_X = Graficador3D.ADJUST_SCALE_X;
        ESCALA_Y = Graficador3D.ADJUST_SCALE_Y;
        ESCALA_Z = Graficador3D.ADJUST_SCALE_Z;
        
        add(new JMI_Ajustador_de_real(Props.ESCALA_COMPONENTE_X, Graficador3D.ADJUST_SCALE_X, this));
        add(new JMI_Ajustador_de_real(Props.ESCALA_COMPONENTE_Y, Graficador3D.ADJUST_SCALE_Y, this));
        add(new JMI_Ajustador_de_real(Props.ESCALA_COMPONENTE_Z, Graficador3D.ADJUST_SCALE_Z, this));
                   
    }

    public Object get_Prop(int i)
    {
        if(i == Props.ESCALA_COMPONENTE_X)
        {
            
            double e_x= ((Figura)Escena.get_actual()).escala_x;            
            
            return new Float(e_x);
        } else
        if(i == Props.ESCALA_COMPONENTE_Y)
        {
            
            double e_y= ((Figura)Escena.get_actual()).escala_y;            
            
            return new Float(e_y);
        } else
        if(i == Props.ESCALA_COMPONENTE_Z)
        {
            
            double e_z= ((Figura)Escena.get_actual()).escala_z;
                        
            return new Float(e_z);
        } else
        {
            return null;
        }
    }

	public void set_Prop(int i, Object obj)
    {
    	Float float1 = (Float)obj;
    	
    	Tipo_componente tipo_componente = Escena.get_actual();
        if(tipo_componente == null)
        {		
        	   new Dialogo_de_error("No hay componente seleccionado");
        	   return;
        }    	
    	
        if(i == Props.ESCALA_COMPONENTE_X)
        {            
            Vector3d v3d=new Vector3d(float1,
            						 ((Figura)Escena.get_actual()).escala_y,
            						 ((Figura)Escena.get_actual()).escala_z);
                        
            Transform3D transform3d = new Transform3D();            
            transform3d.setScale(v3d);
            
            ((Figura)Escena.get_actual()).escala_x = float1.floatValue();            
            ((Figura)Escena.get_actual()).setTransform(transform3d);                       
        }
        if(i == Props.ESCALA_COMPONENTE_Y)
        {
            Vector3d v3d=new Vector3d( ((Figura)Escena.get_actual()).escala_x,
            						   float1,
            						   ((Figura)Escena.get_actual()).escala_z );
                        
            Transform3D transform3d = new Transform3D();            
            transform3d.setScale(v3d);
            
            ((Figura)Escena.get_actual()).escala_y = float1.floatValue();            
            ((Figura)Escena.get_actual()).setTransform(transform3d);             
        }
        if(i == Props.ESCALA_COMPONENTE_Z)
        {
           Vector3d v3d=new Vector3d( ((Figura)Escena.get_actual()).escala_x,
            						  ((Figura)Escena.get_actual()).escala_y,
            						  float1 );
                        
            Transform3D transform3d = new Transform3D();            
            transform3d.setScale(v3d);
            
            ((Figura)Escena.get_actual()).escala_z = float1.floatValue();            
            ((Figura)Escena.get_actual()).setTransform(transform3d);               
        }
    }
	    
    
}