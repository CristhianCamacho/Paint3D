
package com.cc.paint3D.Graficador3D.Paneles;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;

import com.cc.paint3D.Graficador3D.Menus.Menu_dinamico;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.media.j3d.Canvas3D;

public class Panel_Principal extends Canvas3D
{

	public static Cursor CROSSHAIR = new Cursor(Cursor.CROSSHAIR_CURSOR);
	public static Cursor HAND = new Cursor(Cursor.HAND_CURSOR);
    public static Dimension prefdim;
    
    public static BufferedImage bufferedImage;
    
    public static Panel_Principal handle;
    public static Menu_dinamico menu_dinamico;
    public static boolean mostrar_menu_dinamico=false;

    public Panel_Principal(GraphicsConfiguration graphicsconfiguration)
    {
        super(graphicsconfiguration);
        handle = this;
                
        menu_dinamico=new Menu_dinamico();
                    
    }

    public void createBI()
    {
        Dimension dimension = getSize();
        bufferedImage = new BufferedImage(dimension.width, dimension.height, 1);
    }

    public Dimension getPreferredSize()
    {
        if(prefdim == null)
            return super.getPreferredSize();
        else
            return prefdim;
    }
	
    public void processMouseEvent(MouseEvent mouseevent)
    {	
        switch(mouseevent.getID())
        {
        case MouseEvent.MOUSE_PRESSED:
            
            Escena.seleccionar_Componente(mouseevent.getX(), mouseevent.getY());
            break;                   
        }
        
                
        if(mostrar_menu_dinamico)
        {
        	
        if(mouseevent.getButton()==MouseEvent.BUTTON3)
        	if(mouseevent.getID()==mouseevent.MOUSE_PRESSED)
        {

         menu_dinamico.mostrar( (int)( Graficador3D.escena.getLocation().getX()+mouseevent.getX() ),								  
         						(int)(	Graficador3D.escena.getLocation().getY()+mouseevent.getY() ) );
        
        }
        if( (mouseevent.getButton()==MouseEvent.BUTTON1) || (mouseevent.getButton()==MouseEvent.BUTTON2) )
        	//if(mouseevent.getID()==mouseevent.MOUSE_PRESSED)        
        {	
        menu_dinamico.ocultar();       
        }
    	
    	}
                
        super.processMouseEvent(mouseevent);
    }   

}