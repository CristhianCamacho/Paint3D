package com.cc.paint3D.Graficador3D.IO.Patron;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.lang.Thread;


public class Eventos_I implements InternalFrameListener,MouseListener,MouseMotionListener,KeyListener,ListSelectionListener
{	
	Panel_patron panel_patron;
	
	public Eventos_I(Panel_patron panel)
	{
		panel_patron=panel;
		//panel_patron.pintar_puntos();				
	}
	public void actionPerformed(ActionEvent e)
	{		
	}
	
	public void internalFrameDeactivated(InternalFrameEvent ife)
	{		
	}
	
	public void internalFrameActivated(InternalFrameEvent ife)
	{
	}
	
	public void internalFrameDeiconified(InternalFrameEvent ife)
	{		
	}
	
	public void internalFrameIconified(InternalFrameEvent ife)
	{		
	}
	
	public void internalFrameClosed(InternalFrameEvent ife)
	{		
	}
	
	public void internalFrameOpened(InternalFrameEvent ife)
	{
	}
	
	public void internalFrameOpening(InternalFrameEvent ife)
	{
	}
	
	public void internalFrameClosing(InternalFrameEvent ife)
	{
	}
	
	//MouseListener
	public void mouseClicked(MouseEvent e)
	{
		System.out.println ( "mouseClicked Eventos_I" );
		System.out.println ( " "+e.getPoint().getX()+" ; "+e.getPoint().getY());
		
		//panel_patron.pintar_puntos();//para actualizar los puntos pintados 		
		
		if(panel_patron.jta_estado.getText().equalsIgnoreCase("mover_puntos"))
		{
			panel_patron.panel_de_dibujo.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		};
		
		panel_patron.dibujar_punto(e.getPoint().getX(),e.getPoint().getY());//si agregamos puntos 
	}
	
	public void mouseEntered(MouseEvent e)
	{
		//System.out.println ( "mouse entro" );
		panel_patron.pintar_puntos(panel_patron.v_puntos);
	}
	public void mouseExited(MouseEvent e)
	{
		//System.out.println ( "mouse salio" );
	}
    public void mousePressed(MouseEvent e)
    {
    	System.out.println ( "mousePressed Eventos_I" );		
    	
    }
    public void mouseReleased(MouseEvent e)
    {
    	//System.out.println ( e.getSource() );
    	if(panel_patron.jta_estado.getText().equalsIgnoreCase("mover_puntos"))
		{
			panel_patron.panel_de_dibujo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		};
    }
    
	public void mouseMoved(MouseEvent e)
	{
		//panel_patron.pintar_puntos();
	}
	public void mouseDragged(MouseEvent e)
	{	
		System.out.println("mouseDragged Eventos_I");
		
		if(panel_patron.jta_estado.getText().equalsIgnoreCase("mover_puntos"))
		{
			panel_patron.mover_punto(e.getPoint().getX(),e.getPoint().getY());
			panel_patron.panel_de_dibujo.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
		if(panel_patron.jta_estado.getText().equalsIgnoreCase("colocar_puntos") || 
		   panel_patron.jta_estado.getText().equalsIgnoreCase("mas_puntos")     ||
		   panel_patron.jta_estado.getText().equalsIgnoreCase("borrar_puntos")     )			
    	panel_patron.dibujar_punto(e.getPoint().getX(),e.getPoint().getY());
		
		//System.out.println ( e.getSource() );
	}
	public void keyPressed(KeyEvent e)
	{
	}
	public void keyReleased(KeyEvent e){System.out.println ( e );}
	public void keyTyped(KeyEvent e){System.out.println ( e );}
	public void valueChanged(ListSelectionEvent e)
	{
	}
	
}