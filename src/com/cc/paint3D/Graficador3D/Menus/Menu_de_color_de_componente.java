
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos_temporales;
import com.cc.paint3D.Graficador3D.G3D.Datos_utiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.swing.*;
import javax.vecmath.Color3f;

import javax.media.j3d.*;
import javax.vecmath.*;

import java.awt.Color;

public class Menu_de_color_de_componente extends JMenu
    implements ActionListener, Props
{
	String DIFUSA;
    String ESPECULAR;
    String EMISIVA;
    String AMBIENTE;
    String ENMALLADO;
	
    public Menu_de_color_de_componente()
    {
    	super(Graficador3D.SET_COLOR);
        DIFUSA = Graficador3D.DIFFUSE;
        ESPECULAR = Graficador3D.SPECULAR;
        EMISIVA = Graficador3D.EMISSIVE;
        AMBIENTE = Graficador3D.AMBIENT;
        ENMALLADO = Graficador3D.GRID;
        JMenuItem jmenuitem = new JMenuItem(DIFUSA);
        jmenuitem.addActionListener(this);
        add(jmenuitem);
        jmenuitem = new JMenuItem(ESPECULAR);
        jmenuitem.addActionListener(this);
        add(jmenuitem);
        jmenuitem = new JMenuItem(EMISIVA);
        jmenuitem.addActionListener(this);
        add(jmenuitem);
        jmenuitem = new JMenuItem(AMBIENTE);
        jmenuitem.addActionListener(this);
        add(jmenuitem);
        
        jmenuitem = new JMenuItem("Con "+ENMALLADO);
        jmenuitem.addActionListener(this);
        add(jmenuitem);
        
        jmenuitem = new JMenuItem("Sin "+ENMALLADO);
        jmenuitem.addActionListener(this);
        add(jmenuitem);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Tipo_componente tipo_componente = Escena.get_actual();
        if(tipo_componente == null)
            return;
        String s = actionevent.getActionCommand();
        Color3f color3f = new Color3f();
        Appearance appearance = (Appearance)tipo_componente.get_Prop(Props.APPEARANCE);
                
        Material material = appearance.getMaterial();
        if(material==null) material=new Material();
        //if(polygonAttributes_anteriores!=null) appearance.setPolygonAttributes(null);
        
        if(s == DIFUSA)
        {
            material.getDiffuseColor(color3f);
            java.awt.Color color = JColorChooser.showDialog(Escena.handle, "color difuso", Manejador_de_archivos_temporales.getColor(color3f));
            if(color == null)
                return;
            tipo_componente.set_Prop(Props.DIFUSO, color);
        }
        if(s == ESPECULAR)
        {
            material.getSpecularColor(color3f);
            java.awt.Color color1 = JColorChooser.showDialog(Escena.handle, "color especular", Manejador_de_archivos_temporales.getColor(color3f));
            if(color1 == null)
                return;
            tipo_componente.set_Prop(Props.ESPECULAR, color1);
        }
        if(s == EMISIVA)
        {
            material.getEmissiveColor(color3f);
            java.awt.Color color2 = JColorChooser.showDialog(Escena.handle, "color emisivo (que el objeto emite)", Manejador_de_archivos_temporales.getColor(color3f));
            if(color2 == null)
                return;
            tipo_componente.set_Prop(Props.EMISIVO, color2);
        }
        if(s == AMBIENTE)
        {
            material.getAmbientColor(color3f);
            java.awt.Color color3 = JColorChooser.showDialog(Escena.handle, "color ambiente", Manejador_de_archivos_temporales.getColor(color3f));
            if(color3 == null)
                return;
            tipo_componente.set_Prop(Props.AMBIENTE, color3);
        }
        if(s == "Con "+ENMALLADO)
        {
        	//System.out.println("Menu_de_color_de_componente:actionPerformed:enmallado");
        	       	
        	//appearance = (Appearance)tipo_componente.get_Prop(Props.APPEARANCE);//;new Appearance();
        	
        	//Color color_enmallado=JColorChooser.showDialog(Escena.handle, "color ambiente", Manejador_de_archivos_temporales.getColor(color3f));
        	//Color3f objColor = Datos_utiles.get_Color3f( color_enmallado.getRGB() );//new Color3f(1.0f, 0.4f, 0.0f);
			//ColoringAttributes ca = new ColoringAttributes();
			//ca.setColor(objColor);
			//appearance.setColoringAttributes(ca);			
			       	
        	if(!b_enmallado)
        	{
        	PolygonAttributes pa = new PolygonAttributes();
			pa.setPolygonMode(pa.POLYGON_LINE);
			pa.setCullFace(PolygonAttributes.CULL_NONE);
						
			appearance.setPolygonAttributes(pa);
			b_enmallado=!b_enmallado;
			}
			
			tipo_componente.set_Prop(Props.ENMALLADO, b_enmallado);			
    	}
    	if(s == "Sin "+ENMALLADO)
        {
        	if(b_enmallado)
        	{
        	PolygonAttributes pa = new PolygonAttributes();
			pa.setPolygonMode(pa.POLYGON_FILL);
			pa.setCullFace(PolygonAttributes.CULL_NONE);
						
			appearance.setPolygonAttributes(pa);
			b_enmallado=!b_enmallado;
			}
			
			tipo_componente.set_Prop(Props.ENMALLADO, b_enmallado);	
			//tipo_componente.set_Prop(Props.APPEARANCE,appearance);
    	}
    }
	boolean b_enmallado=false;
    //PolygonAttributes polygonAttributes_anteriores;
}