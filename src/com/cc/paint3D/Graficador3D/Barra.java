package com.cc.paint3D.Graficador3D;

import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.G3D.Archivo_generado;
import com.cc.paint3D.Graficador3D.Menus.Barra_de_Menus;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_mas_menos;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_flechas;
import com.cc.paint3D.Graficador3D.Paneles.Barra_de_herramientas;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_texto_estado;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.*;

//import com.l2fprod.gui.plaf.skin.Skin;
//import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;

public class Barra extends JFrame
    implements WindowListener
{
	public Barra_de_herramientas barra_de_herramientas;
	public static Barra handle;


    public Barra()
    {
        handle = this;        
                
        Barra_de_Menus barra_de_Menus;
        setJMenuBar(barra_de_Menus=new Barra_de_Menus(this));
        
        setUndecorated(true);
        
        //setLookAndFeel(barra_de_Menus.MASCARA_DEFECTO);
        setIconImage(ES_Utiles.get_System_Image(Graficador3D.SYS_ICON));
        
        
    	//getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
               
        Container container = getContentPane();
        container.setLayout(new FlowLayout(0, 0, 0));
               
        
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        jpanel.add(barra_de_herramientas=new Barra_de_herramientas(), "North");
        
        JPanel jpanel1 = new JPanel();
        jpanel1.setLayout(new FlowLayout(0, 0, 0));
        
        jpanel1.add(new Panel_de_texto_estado());
        jpanel.add(jpanel1,"South");
                
        container.add(jpanel);
        
        container.add(new Panel_de_flechas());
        container.add(new Panel_de_mas_menos());
        
        addWindowListener(this);
        pack();
        
        JFrame a =new JFrame();
        
        //setResizable(false);
    }

    public static void set_cabecera_de_ventana(Archivo_generado archivo_generado)
    {
        StringBuffer stringbuffer = new StringBuffer(50);
        stringbuffer.append(" Grafico: "+" '").append(archivo_generado.nombre).append("'");

        stringbuffer.append(" - ").append(ES_Utiles.PRODUCTO);
        handle.setTitle(stringbuffer.toString());
    }

    public static void set_sin_titulo()
    {
        Manejador_de_archivos.file_actual = null;
        handle.setTitle("Sin titulo - " + ES_Utiles.PRODUCTO);
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
        Graficador3D.cerrar_Programa();
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowOpened(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }
    
   public void setLookAndFeel(String SKIN){
		try{
			//Skin skin = SkinLookAndFeel.loadThemePack(SKIN);
			//SkinLookAndFeel.setSkin(skin);
			//SkinLookAndFeel.enable();
			
		}catch(Exception e){ 
			System.err.println(e);
		}
		
		SwingUtilities.updateComponentTreeUI(this);
		
		//if(Escena.handle!=null)
		//Escena.handle.setLookAndFeel(SKIN);
	}
    
}