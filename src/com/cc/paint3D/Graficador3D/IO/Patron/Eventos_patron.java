package com.cc.paint3D.Graficador3D.IO.Patron;


import java.util.Vector;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class Eventos_patron implements MouseListener,MouseMotionListener
{	
	Panel_patron panel_patron;
	
	boolean estamos_en_Panel_patron_inicial=false;
	
		
	public Eventos_patron(Panel_patron panel)
	{
		panel_patron=panel;		
	}
		
	//MouseListener
	public synchronized void mouseClicked(MouseEvent e)
	{
		if( e.getSource() instanceof JButton )
		{
			JButton temp=(JButton)e.getSource();
			String aux=temp.getText();
								
			
			if(aux.equals("colocar_puntos"))
			{				
				panel_patron.borrar_puntos=false;
				panel_patron.mas_puntos=true;
				panel_patron.mover_puntos=false;
				panel_patron.borrar_todo=false;
				
				panel_patron.jta_estado.setForeground(panel_patron.color_punto_normal);	
				panel_patron.panel_de_dibujo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));											
			}
			
			if(aux.equals("mover_puntos"))
			{				
				panel_patron.borrar_puntos=false;	
				panel_patron.mover_puntos=true;
				panel_patron.mas_puntos=false;
				panel_patron.borrar_todo=false;
				
				panel_patron.jta_estado.setForeground(panel_patron.color_punto_seleccionado);
				panel_patron.panel_de_dibujo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));													
			}
			
			if(aux.equals("Persec."))
			{				
				panel_patron.borrar_puntos=false;panel_patron.mas_puntos=true;
				panel_patron.mover_puntos=false;panel_patron.borrar_todo=false;
								
				panel_patron.trayectorias_de_persecucion_simetrica();
				//panel_patron.panel_de_dibujo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));					
			}
			
			if(aux.equals("asim."))
			{
				panel_patron.borrar_puntos=false;panel_patron.mas_puntos=true;
				panel_patron.mover_puntos=false;panel_patron.borrar_todo=false;
				
				panel_patron.trayectorias_de_persecucion_asimetrica();
											
			}
			
			if(aux.equals("Aceptar"))
			{				
				if(panel_patron.v_puntos_temporal.size()!=0)			
				panel_patron.v_puntos=panel_patron.v_puntos_temporal;
				
				panel_patron.cerrar_Dialogo();				
			}
			
						
			if(aux.equals("borrar_todo"))
			{				
				panel_patron.borrar_puntos=false;
				panel_patron.mas_puntos=false;	
				panel_patron.mover_puntos=false;
				panel_patron.borrar_todo=true;
				
				panel_patron.jta_estado.setForeground(Color.BLACK);
				
				panel_patron.dibujar_punto(0,0);
				panel_patron.panel_de_dibujo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));		
			}
			if(aux.equals("borrar_puntos"))
			{
				panel_patron.borrar_puntos=true;
				panel_patron.mas_puntos=false;	
				panel_patron.mover_puntos=false;
				panel_patron.borrar_todo=false;	
				
				panel_patron.jta_estado.setForeground(panel_patron.color_lineas);
				
				panel_patron.panel_de_dibujo.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));		
				
			}
			
			panel_patron.fijar_texto(aux);
					
			
		}
	}
	
	public void mouseEntered(MouseEvent e)
	{
	
	}
	public void mouseExited(MouseEvent e)
	{		
	}
    public void mousePressed(MouseEvent e)
    {
	}
    
    public void mouseReleased(MouseEvent e)
    {
    }
    
	public void mouseMoved( MouseEvent e )
	{
	}
	public void mouseDragged( MouseEvent e )
	{
	} 
	public void keyReleased( KeyEvent e )
	{
	} 
		
}