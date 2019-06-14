
package com.cc.paint3D.Graficador3D.Paneles;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador;
import com.cc.paint3D.Graficador3D.Botones.Boton_Flash;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.*;


public class Panel_de_flechas extends JPanel
    implements ActionListener
{
	static final int TODO = 0;
    static final int ANGULO = 1;
    static final int VERTICAL = 2;
    public static final int IZQUIERDA = 0;
    public static final int ARRIBA = 1;
    public static final int ABAJO = 2;
    public static final int DERECHA = 3;    
    static Boton_Flash izquierda;
    static Boton_Flash derecha;
    static Boton_Flash arriba;
    static Boton_Flash abajo;    
    static Ajustador ajustador;

    public Panel_de_flechas()
    {
        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new GridLayout(3, 3));//3 filas * 3 columnas
        add(ES_Utiles.get_Boton_Flash(Graficador3D.QUARTER_ICON, this));
        add(arriba = ES_Utiles.get_Boton_Flash(Graficador3D.UP_ICON, this));
        add(ES_Utiles.get_Boton_Flash(Graficador3D.QUARTER_ICON, this));
        
        add(izquierda = ES_Utiles.get_Boton_Flash(Graficador3D.LEFT_ICON, this));
        add(ES_Utiles.get_Boton_Flash(Graficador3D.QUARTER_ICON,this));
        add(derecha = ES_Utiles.get_Boton_Flash(Graficador3D.RIGHT_ICON, this));
    	
    	add(ES_Utiles.get_Boton_Flash(Graficador3D.QUARTER_ICON,this));
    	add(abajo = ES_Utiles.get_Boton_Flash(Graficador3D.DOWN_ICON, this));
        add(ES_Utiles.get_Boton_Flash(Graficador3D.QUARTER_ICON,this));
    }

    public static void panel_Off()
    {
        izquierda.setEnabled(false);
        derecha.setEnabled(false);
        arriba.setEnabled(false);
        abajo.setEnabled(false);        
        ajustador = null;
    }

    public static void panel_On(Ajustador ajus, int i)
    {
        Panel_de_mas_menos.panel_Off();
        ajustador = ajus;
        Panel_de_mas_menos.etiqueta_On();
        switch(i)
        {
        case TODO: 
            flechas();
            break;

        case ANGULO: 
            flechas();
            break;

        case VERTICAL: 
            vertical();
            break;
        }
    }

    public static void flechas()
    {
        izquierda.setEnabled(true);
        derecha.setEnabled(true);
        abajo.setEnabled(true);
        arriba.setEnabled(true);
    }
	
    public static void vertical()
    {
        abajo.setEnabled(true);
        arriba.setEnabled(true);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Object obj = actionevent.getSource();
        if(obj == izquierda)
            ajustador.alterar_valor(IZQUIERDA);
        else
        if(obj == derecha)
            ajustador.alterar_valor(DERECHA);
        else
        if(obj == arriba)
            ajustador.alterar_valor(ARRIBA);
        else
        if(obj == abajo)
            ajustador.alterar_valor(ABAJO);
        
    }

    
}