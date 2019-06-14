
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Barra;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.HTML_ayuda.Ventana_de_Ayuda;
import com.cc.paint3D.Graficador3D.HTML_ayuda.Ventana_de_Acerca_de;
import com.cc.paint3D.Graficador3D.Menus.Juego.Nemexis;

//import com.sun.j3d.utils.applet.MainFrame;

import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenu;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;

public class Menu_de_ayuda extends JMenu
    implements ActionListener
{
	public static String AYUDA = Graficador3D.DISPLAY_USER_MANUAL;
    public static String ACERCA_DE = Graficador3D.ABOUT;
    public static String JUEGO = Graficador3D.GAME;
        
    public static Menu_de_ayuda handle;

    public Menu_de_ayuda()
    {
        super(Graficador3D.HELP);
        handle = this;
        addSeparator();
        add(ES_Utiles.get_MI(AYUDA, this));
        addSeparator();
        add(ES_Utiles.get_MI(ACERCA_DE, this));
        addSeparator();
        add(ES_Utiles.get_MI(JUEGO, this));
                
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        if(s == AYUDA)
            new Ventana_de_Ayuda();
        else
        if(s == ACERCA_DE)
            {	
            	new Ventana_de_Acerca_de("ayuda/acerca_de.html");
            }
        else
        if(s == JUEGO)
        	{        		
        //		MainFrame mf= new MainFrame(new Nemexis(), 750, 550);
        		
        		
        final JFrame frame = new JFrame("Jugar un rato");
        final Nemexis nemexis = new Nemexis();
        nemexis.init();
        frame.setResizable(true);
        
        frame.setLocation(Barra.handle.getLocation().x,
        				  Barra.handle.getLocation().y + Barra.handle.getSize().height );
        
        Dimension dimension = Escena.canvas.getSize();
        Dimension dimension_pantalla=new Dimension(Barra.handle.getSize().width,dimension.height);
        
        nemexis.set_dimension_pantalla(dimension_pantalla);
        frame.setSize(dimension_pantalla);
        // frame.setSize(750,550);
        frame.add(nemexis);                   
		
        frame.addWindowListener(new WindowAdapter() {
                                 public void windowClosing(WindowEvent me) 
                                 { 
                                   nemexis.destroy();
                                   frame.setVisible(false);                                   
                                 } 
                                });

        //frame.pack();  //"pack()"= hace que los elementos SE AJUSTEN A SU TAMANYO PREFERIDO
        frame.setVisible(true);
        
        		// new Nemexis() ;        		
        	}                  
    }	

}