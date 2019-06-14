
package com.cc.paint3D.Graficador3D.Paneles;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador;
import com.cc.paint3D.Graficador3D.Botones.Boton_Flash;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;

import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos_temporales;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.JTextComponent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Panel_de_mas_menos extends JPanel
    implements ActionListener, ChangeListener
{
	public static JSlider jslider;
	
	static String todas_las_letras="abcdefghijklmn�opqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ% ";
    static JTextField etiqueta;
    static Boton_Flash mas;
    static Boton_Flash menos;
    static Boton_Libreria fijar;
    
    static Ajustador ajustador;
       
    public static Panel_de_mas_menos handle;
    
    static Color color_background_selected=Color.darkGray;
    static Color color_background_normal=Color.lightGray;
    
    static Color color_foreground_selected=Color.red;
    static Color color_foreground_normal=Color.darkGray;
	
    public Panel_de_mas_menos()
    {
        super(false);
        handle = this;
        setLayout(new GridLayout(2, 1));
        etiqueta = new JTextField() {

            public boolean isFocusTraversable()
            {
                return false;
            }

        };
        
        etiqueta.setColumns(12);
        etiqueta.setEditable(false);
        
        etiqueta.setForeground(color_foreground_selected);
        etiqueta.setBackground(color_background_selected);
        
        etiqueta.setOpaque(true);
        etiqueta.setFont(Manejador_de_archivos_temporales.MSG_FONT);
        etiqueta.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        
        JPanel jpanel = new JPanel();
        jpanel.add(etiqueta); 
               
        jpanel.setBorder(BorderFactory.createEtchedBorder());
        jpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 3));
        
        jpanel.add(menos = ES_Utiles.get_Boton_Flash(Graficador3D.MINUS_ICON, this));
        jpanel.add(mas = ES_Utiles.get_Boton_Flash(Graficador3D.PLUS_ICON, this));
        jpanel.add(fijar = new Boton_Libreria("Fijar",this));
        
        fijar.setIcon(null);
        fijar.setFont(new Font("Arial",Font.BOLD,10));
        fijar.setPreferredSize(new Dimension(60, 20));
        fijar.setEnabled(false);
        
        add(jpanel);
        
        agregar_jslistener(10,0,1,5);
    }
    
    public void agregar_jslistener(int maximo , int inicial , int marcas_pequenias , int marcas_grandes )
    {
    	jslider=new JSlider();
        jslider=new JSlider(JSlider.HORIZONTAL,0,maximo,0);
        jslider.addChangeListener(this);
        										
		jslider.setMajorTickSpacing(marcas_grandes);		
		jslider.setMinorTickSpacing(marcas_pequenias);
		
		jslider.setPaintTicks(true);
		
		jslider.setBorder(new BevelBorder(BevelBorder.RAISED));		
		jslider.setEnabled(false);
		
		add("South",jslider);
    }
    
    public void borrar_jslistener()
    {
    	remove(jslider);
    	jslider=null;
	}

    public void actionPerformed(ActionEvent actionevent)
    {
        Object obj = actionevent.getSource();
        if(obj == mas)
            {
            	String temp=ajustador.alterar_valor(1);
            	jslider.setValue( (int)( dar_numero(temp) ) );		
            	etiqueta.setText(temp);
            }
        else
        if(obj == menos)
            {
            	String temp=ajustador.alterar_valor(-1);
            	jslider.setValue( (int)( dar_numero(temp) ) );	
            	etiqueta.setText(temp);
            }
        else
            ajustador.set_valor();
    }
    
    public void stateChanged(ChangeEvent e)
	{
	System.out.println(e.getSource());
	JSlider source = (JSlider)e.getSource();
											
		if(!source.getValueIsAdjusting())
		{
			if( etiqueta!=null && ajustador!=null && jslider!=null )
			{
				etiqueta.setText( dar_texto_sin_numero(ajustador.alterar_valor(0))+source.getValue() );	
				ajustador.set_valor( jslider.getValue() );				
				
				if( jslider.getValue()>=(jslider.getMaximum()-20) )
				{        		
        		int valor_nuevo=10*((int)(jslider.getValue()/10))+20;
        		jslider.setMaximum(valor_nuevo);
        		}
        		else
        			if( jslider.getValue()<(jslider.getMaximum()-10) )
					{        		
        				int valor_nuevo=10*((int)(jslider.getValue()/10))+10;
        				if(valor_nuevo>=0)
        				jslider.setMaximum(valor_nuevo);
        			}
        												
			}
		}
	}

    public static void off()
    {
        panel_Off();
        Panel_de_flechas.panel_Off();
        etiqueta_Off();
    }

    public static void etiqueta_Off()
    {
        etiqueta.setBackground(color_background_normal);
        etiqueta.setText("");
    }

    public static void etiqueta_On()
    {
        etiqueta.setBackground(color_background_selected);
    }

    public static void panel_Off()
    {
        if(!mas.isEnabled())
        {
            return;
        } else
        {
            mas.setEnabled(false);
            menos.setEnabled(false);
            fijar.setEnabled(false);
            jslider.setEnabled(false);
            ajustador = null;
            return;
        }
    }

    public static void panel_On(Ajustador ajust)
    {
        Panel_de_flechas.panel_Off();
        ajustador = ajust;
        etiqueta_On();
        mas.setEnabled(true);
        menos.setEnabled(true);
        fijar.setEnabled(true);
        jslider.setEnabled(true);
    }

    public static void set_Texto(String s)
    {
        etiqueta.setText(s);
    }
    
    public static double dar_numero(String s)
    {
    	String s_salida="";
    	
    	for( int i=s.length() ; i>1 ; i-- )
    	{
    		if( ! todas_las_letras.contains(s.substring(i-1,i)) )
    		{
    			s_salida=s.substring(i-1,i)+s_salida;
    		} 
    	}	
    	
    	return Double.parseDouble(s_salida);	
    }
    
    public static String dar_texto_sin_numero(String s)
    {
    	String s_salida="";
    	
    	for( int i=s.length() ; i>1 ; i-- )
    	{
    		if( todas_las_letras.contains(s.substring(i-1,i)) )
    		{
    			s_salida=s.substring(i-1,i)+s_salida;
    		} 
    	}	
    	
    	return s_salida;	
    }
     
}