
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Fijable;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.G3D.Figura;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_Fijar_tamanio;

import com.cc.paint3D.Graficador3D.IO.Props_de_sistema;
import com.cc.paint3D.Graficador3D.IO.Archivos_recientes;
import com.cc.paint3D.Graficador3D.IO.Prefs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JMenu;

import javax.vecmath.Vector3d;

public class Menu_de_opciones_de_escala extends JMenu
    implements ActionListener, Fijable, Props
{

	String EXACTO;
	public String CARGAR_VALORES_POR_DEFECTO;
    
    public Menu_de_opciones_de_escala()
    {
        super(Graficador3D.DISPLAY);
        EXACTO = Graficador3D.SET_EXACT_VIEW_DIMENSION;
        CARGAR_VALORES_POR_DEFECTO = Graficador3D.RELOAD_ALL_DEFAULTS;
        
        add(ES_Utiles.get_MI(EXACTO, this));
        
        add(new JMI_Ajustador_de_real(Props.ESCALA_ESCENA_X, Graficador3D.ADJUST_SCALE_X, this));
        add(new JMI_Ajustador_de_real(Props.ESCALA_ESCENA_Y, Graficador3D.ADJUST_SCALE_Y, this));
        add(new JMI_Ajustador_de_real(Props.ESCALA_ESCENA_Z, Graficador3D.ADJUST_SCALE_Z, this));
        
        addSeparator();
        add(ES_Utiles.get_MI(CARGAR_VALORES_POR_DEFECTO, this));
        
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        if(s == EXACTO)
            new Dialogo_de_Fijar_tamanio();
        else
        	if(s == CARGAR_VALORES_POR_DEFECTO)
        	{
            if(Props_de_sistema.ARCHIVO_PROPS.exists())
                Props_de_sistema.ARCHIVO_PROPS.delete();
            if(Archivos_recientes.LISTA_DE_FILES_RECIENTES.exists())
                Archivos_recientes.LISTA_DE_FILES_RECIENTES.delete();
            if(Prefs.PREFS.exists())
                Prefs.PREFS.delete();
            System.exit(0);
        	};
    }

    public Object get_Prop(int i)
    {
        if(i == Props.ESCALA_ESCENA_X)
        {            
            double e_x= Escena.scale_actual_x;
            
            return new Float(e_x);
        } else
        if(i == Props.ESCALA_ESCENA_Y)
        {
            double e_y= Escena.scale_actual_y;
            
            return new Float(e_y);
        } else
        if(i == Props.ESCALA_ESCENA_Z)
        {
            double e_z= Escena.scale_actual_z;
            
            return new Float(e_z);
        } else
        {
            return null;
        }
    }

    public void set_Prop(int i, Object obj)
    {
        if(i == Props.ESCALA_ESCENA_X)
        {
            Float float1 = (Float)obj;
            
            Vector3d v3d=new Vector3d(float1,
            						 Escena.scale_actual_y,
            						 Escena.scale_actual_z);
            
            Transform3D transform3d = new Transform3D();
            transform3d.setScale(v3d);
            
            Escena.scale_actual_x = float1.floatValue();
            
            Escena.tg.setTransform(transform3d);
        }
        if(i == Props.ESCALA_ESCENA_Y)
        {
            Float float1 = (Float)obj;
            Vector3d v3d=new Vector3d(Escena.scale_actual_x,
            						 float1,
            						 Escena.scale_actual_z);
                        
            Transform3D transform3d = new Transform3D();
            
            transform3d.setScale(v3d);
            
            Escena.scale_actual_y = float1.floatValue();
            
            Escena.tg.setTransform(transform3d);
                    
        }
        if(i == Props.ESCALA_ESCENA_Z)
        {
            Float float1 = (Float)obj;
            
            Vector3d v3d=new Vector3d(Escena.scale_actual_x,
            						  Escena.scale_actual_y,
            						  float1);
                        
            Transform3D transform3d = new Transform3D();
            
            transform3d.setScale(v3d);
            
            Escena.scale_actual_z = float1.floatValue();
            
            Escena.tg.setTransform(transform3d);
                        
        }
    }

    
}