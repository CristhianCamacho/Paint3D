
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.G3D.Figura;
import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Seleccion_multiple;
//import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.IO.Informacion_de_BH;
import com.cc.paint3D.Graficador3D.IO.Informacion_de_traslacion_puntos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Menu_de_propiedades_de_comportamiento extends JPopupMenu
    implements ActionListener, Seleccion_multiple, Props
{
	public static String CAMBIAR_ROTACION = Graficador3D.ALTER_ROTATOR_BEHAVIOR_PARAM;
	public static String CAMBIAR_TRASLACION = Graficador3D.ALTER_TRANSLATOR_BEHAVIOR_PARAM;
	public static String CAMBIAR_TRASLACION_PUNTOS = Graficador3D.ALTER_TRANSLATOR_POINTS_BEHAVIOR_PARAM;
		
    public static String BH_rotacion[] = {
        Graficador3D.NONE, Graficador3D.ROTATE
    };
    
    public static String BH_traslacion[] = {
        Graficador3D.NONE, Graficador3D.TRANSLATE
    };
    
    public static String BH_traslacion_puntos[] = {
        Graficador3D.NONE, Graficador3D.TRANSLATE_POINTS
    };
        
    public static Menu_de_propiedades_de_comportamiento handle = new Menu_de_propiedades_de_comportamiento();
    public static Menu_de_seleccion_multiple menu_de_rotacion_bh;
    public static Menu_de_seleccion_multiple menu_de_traslacion_bh;
    public static Menu_de_seleccion_multiple menu_de_traslacion_puntos_bh;
    public static JMenuItem cambiar_rotacion;
	public static JMenuItem cambiar_traslacion;
	public static JMenuItem cambiar_traslacion_puntos;
		
    public Menu_de_propiedades_de_comportamiento()
    {
        add(menu_de_rotacion_bh = new Menu_de_seleccion_multiple(Graficador3D.SET_ROTATOR_BEHAVIOR, BH_rotacion, this));
        add(cambiar_rotacion = new JMenuItem(CAMBIAR_ROTACION));
        cambiar_rotacion.addActionListener(this);
        cambiar_rotacion.setEnabled(false);
        
        add(menu_de_traslacion_bh = new Menu_de_seleccion_multiple(Graficador3D.SET_TRANSLATOR_BEHAVIOR, BH_traslacion, this));
        add(cambiar_traslacion = new JMenuItem(CAMBIAR_TRASLACION));
        cambiar_traslacion.addActionListener(this);
        cambiar_traslacion.setEnabled(false);
        
        add(menu_de_traslacion_puntos_bh = new Menu_de_seleccion_multiple(Graficador3D.SET_TRANSLATOR_POINT_BEHAVIOR, BH_traslacion_puntos, this));
        add(cambiar_traslacion_puntos = new JMenuItem(CAMBIAR_TRASLACION_PUNTOS));
        cambiar_traslacion_puntos.addActionListener(this);
        cambiar_traslacion_puntos.setEnabled(false);
        
    }

    public static void set()
    {
        Informacion_de_BH informacion_de_BH = (Informacion_de_BH)Escena.get_actual().get_Prop(Props.BEHAVIOUR);
if(informacion_de_BH != null)        
System.out.println("Menu_de_propiedades_de_comportamiento:set:Informacion_de_BH.tipo= "+informacion_de_BH.tipo);
        
        if(informacion_de_BH == null)
        {
            //cambiar_rotacion.setEnabled(false);
            //cambiar_traslacion.setEnabled(false);
            menu_de_rotacion_bh.set_estado(0);
            menu_de_traslacion_bh.set_estado(0);
            menu_de_traslacion_puntos_bh.set_estado(0);
        } 
        else
        {
            if(informacion_de_BH.tipo==Informacion_de_BH.ROTACION)            
            menu_de_rotacion_bh.set_estado(1);
            else            
            if(informacion_de_BH.tipo==Informacion_de_BH.TRASLACION)
            menu_de_traslacion_bh.set_estado(1);
            else
            if(informacion_de_BH.tipo==Informacion_de_BH.TRASLACION_PUNTOS)
            menu_de_traslacion_puntos_bh.set_estado(1);            
        }
        
        if(menu_de_rotacion_bh.get_estado()==0)
        {
        	cambiar_rotacion.setEnabled(false);
        }
        if(menu_de_rotacion_bh.get_estado()==1)
        {
        	cambiar_rotacion.setEnabled(true);
        }        
        if(menu_de_traslacion_bh.get_estado()==0)
        {
        	cambiar_traslacion.setEnabled(false);
        }
        if(menu_de_traslacion_bh.get_estado()==1)
        {
        	cambiar_traslacion.setEnabled(true);
        }
        if(menu_de_traslacion_puntos_bh.get_estado()==0)
        {
        	cambiar_traslacion_puntos.setEnabled(false);
        }
        if(menu_de_traslacion_puntos_bh.get_estado()==1)
        {
        	cambiar_traslacion_puntos.setEnabled(true);
        }
                
    }

    public void llamada_multiple(Menu_de_seleccion_multiple menu_de_seleccion_multiple, int i)
    {
        //set();
        
        Informacion_de_BH informacion_de_BH=(Informacion_de_BH)Escena.get_actual().get_Prop(Props.BEHAVIOUR);
if(informacion_de_BH != null)        
System.out.println("Menu_de_propiedades_de_comportamiento:set:Informacion_de_BH.tipo= "+informacion_de_BH.tipo);        
        if(i != 0)
        {	if( menu_de_seleccion_multiple==menu_de_rotacion_bh )
        	{
            	menu_de_traslacion_puntos_bh.set_estado(0);
            	menu_de_traslacion_bh.set_estado(0);
            	
            	informacion_de_BH = Informacion_de_BH.get_BH(Informacion_de_BH.ROTACION,i);
            	//Informacion_de_BH informacion_de_BH_actual=(Informacion_de_BH)Escena.get_actual().get_Prop(Props.BEHAVIOUR);
            	//if(informacion_de_BH_actual != null)
            	//informacion_de_BH_actual.tipo=Informacion_de_BH.ROTACION;            	
            	//informacion_de_BH_actual.set_tipo(Informacion_de_BH.ROTACION);
            	//informacion_de_BH.tipo=Informacion_de_BH.ROTACION;
            	if(informacion_de_BH == null)
            	{
//System.out.println("Menu_de_propiedares_de_comportamiento:llamada_multiple:menu_de_rotacion_bh:i= "+i);        	
                	menu_de_rotacion_bh.set_estado_anterior();
                 	return;
            	}
            	//cambiar_rotacion.setEnabled(true);
        		else
        		if(informacion_de_BH.tipo==Informacion_de_BH.ROTACION)
        		{
//            	cambiar_rotacion.setEnabled(true);
        		}
        		
        	}else	    	
        	if(menu_de_seleccion_multiple==menu_de_traslacion_bh)
        	{
        		menu_de_traslacion_puntos_bh.set_estado(0);
            	menu_de_rotacion_bh.set_estado(0);            	
        		
System.out.println("Menu_de_propiedades_de_comportamiento:llamada_multiple:menu_de_traslacion_bh:i= "+i);        	
            	informacion_de_BH = Informacion_de_BH.get_BH(Informacion_de_BH.TRASLACION,i);
            	//Informacion_de_BH informacion_de_BH_actual=(Informacion_de_BH)Escena.get_actual().get_Prop(Props.BEHAVIOUR);
            	if(informacion_de_BH != null)
            	{
System.out.println("Menu_de_propiedades_de_comportamiento:llamada_multiple:menu_de_traslacion_bh:informacion_de_BH.tipo= "+informacion_de_BH.tipo);               		
            		informacion_de_BH.tipo=Informacion_de_BH.TRASLACION;            		
            	}
            	//informacion_de_BH_actual.set_tipo(Informacion_de_BH.TRASLACION);
            	
            	//if(informacion_de_BH!=null)
            	//informacion_de_BH.tipo=Informacion_de_BH.TRASLACION;
            	
//System.out.println("Menu_de_propiedares_de_comportamiento:llamada_multiple:informacion_de_BH.tipo= "+informacion_de_BH.tipo);                	
            	if(informacion_de_BH == null)
            	{
                	menu_de_traslacion_bh.set_estado_anterior();                
                	return;
            	}
            	//cambiar_traslacion.setEnabled(true);
        		else
        		if(informacion_de_BH.tipo==Informacion_de_BH.TRASLACION)
        		{
            	//cambiar_traslacion.setEnabled(true);
            	;
        		}
        		
        	}else
        	if(menu_de_seleccion_multiple==menu_de_traslacion_puntos_bh)
        	{
        		menu_de_traslacion_bh.set_estado(0);
            	menu_de_rotacion_bh.set_estado(0);
            	        		
System.out.println("Menu_de_propiedares_de_comportamiento:llamada_multiple:menu_de_traslacion_puntos_bh:i= "+i);        	
            	informacion_de_BH = Informacion_de_BH.get_BH(Informacion_de_BH.TRASLACION_PUNTOS,i);
            	//Informacion_de_BH informacion_de_BH_actual=(Informacion_de_BH)Escena.get_actual().get_Prop(Props.BEHAVIOUR);
            	if(informacion_de_BH != null)
            	{
            		informacion_de_BH.tipo=Informacion_de_BH.TRASLACION_PUNTOS;            		
            	}
            	//informacion_de_BH_actual.set_tipo(Informacion_de_BH.TRASLACION_PUNTOS);
            	
            	//if(informacion_de_BH!=null)
            	//informacion_de_BH.tipo=Informacion_de_BH.TRASLACION_PUNTOS;
            	
//System.out.println("Menu_de_propiedares_de_comportamiento:llamada_multiple:informacion_de_BH.tipo= "+informacion_de_BH.tipo);                	
            	if(informacion_de_BH == null)
            	{
                	menu_de_traslacion_puntos_bh.set_estado_anterior();                
                	return;
            	}
            	//cambiar_traslacion.setEnabled(true);
        		//else
        		//{
            	//cambiar_traslacion.setEnabled(true);
        		//}
//if( ! ((String)((Tipo_componente) Escena.get_actual().get_Prop(Props.NOMBRE) ).equalsIgnoreCase("Figura_texto2D")) );
((Figura)Escena.get_actual()).dibujar_lineas(((Informacion_de_traslacion_puntos)(informacion_de_BH)).puntos);
        		//Escena.handle.dibujar_lineas(((Informacion_de_traslacion_puntos)(informacion_de_BH)).puntos);

        	}
         		
        }
        else if(i==0) 
        {
System.out.println("Menu_de_propiedares_de_comportamiento:llamada_multiple:i=0 ");                	
        	Escena.get_actual().set_Prop(Props.BEHAVIOUR, null);              
    		if(menu_de_seleccion_multiple==menu_de_traslacion_puntos_bh || 
    		   menu_de_seleccion_multiple==menu_de_rotacion_bh ||
    		   menu_de_seleccion_multiple==menu_de_traslacion_bh)
    		{
    		menu_de_seleccion_multiple.set_estado(0);
    		informacion_de_BH=null;	 
    		}
System.out.println("Menu_de_propiedares_de_comportamiento:llamada_multiple:menu_de_seleccion_multiple.get_estado() "+menu_de_seleccion_multiple.get_estado());    		   		
    	}
        Escena.get_actual().set_Prop(Props.BEHAVIOUR, informacion_de_BH);
        	 
        	 	    	
System.out.println("Menu_de_propiedares_de_comportamiento:llamada_multiple:i= "+i);
if(informacion_de_BH!=null) System.out.println("Menu_de_propiedares_de_comportamiento:llamada_multiple:informacion_de_BH.tipo= "+informacion_de_BH.tipo);    
        
        
        set();               
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
System.out.println("Menu_de_propiedades_de_comportamiento:actionPerformed:s= "+s);  
        
        if(s == CAMBIAR_ROTACION )
        {
            Informacion_de_BH informacion_de_BH = (Informacion_de_BH)Escena.get_actual().get_Prop(Props.BEHAVIOUR);
System.out.println("Menu_de_propiedades_de_comportamiento:informacion_de_BH.tipo= "+informacion_de_BH.tipo);            
            //informacion_de_BH.tipo=Informacion_de_BH.ROTACION;
            informacion_de_BH.cambiar_rotacion();
        }        
        if(s == CAMBIAR_TRASLACION)
        {
            Informacion_de_BH informacion_de_BH = (Informacion_de_BH)Escena.get_actual().get_Prop(Props.BEHAVIOUR);
System.out.println("Menu_de_propiedades_de_comportamiento:informacion_de_BH.tipo= "+informacion_de_BH.tipo);            
            //informacion_de_BH.tipo=Informacion_de_BH.TRASLACION;
            informacion_de_BH.cambiar_traslacion();
        }
        
        if(s == CAMBIAR_TRASLACION_PUNTOS)
        {
            Informacion_de_BH informacion_de_BH = (Informacion_de_BH)Escena.get_actual().get_Prop(Props.BEHAVIOUR);
System.out.println("Menu_de_propiedades_de_comportamiento:informacion_de_BH.tipo= "+informacion_de_BH.tipo);            
            //informacion_de_BH.tipo=Informacion_de_BH.TRASLACION;
            informacion_de_BH.cambiar_traslacion();
            
((Figura)Escena.get_actual()).dibujar_lineas(((Informacion_de_traslacion_puntos)(informacion_de_BH)).puntos);
        }
        
        
        /*
        if(menu_de_rotacion_bh.get_estado()==0)
        {
        	cambiar_rotacion.setEnabled(false);
        }
        if(menu_de_traslacion_bh.get_estado()==0)
        {
        	cambiar_traslacion.setEnabled(false);
        }
        */
                
    }

    
}