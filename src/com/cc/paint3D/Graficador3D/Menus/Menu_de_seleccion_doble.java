
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Botones.RBMI;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventObject;
import javax.swing.*;


public class Menu_de_seleccion_doble extends JMenu
    implements ItemListener
{
	public static final int OPCION1 = 0;
    public static final String OPCION1_TXT = "0";
    public static final int OPCION2 = 1;
    public static final String OPCION2_TXT = "1";
    RBMI op1;
    RBMI op2;
    int estado=0;
    ButtonGroup bg;
    boolean ignorar;
	
    public Menu_de_seleccion_doble(String s, String s1, String s2)
    {
        super(s);
        bg = new ButtonGroup();
        add(op1 = new RBMI(0, s1));
        op1.addItemListener(this);
        bg.add(op1);
        add(op2 = new RBMI(1, s2));
        op2.addItemListener(this);
        bg.add(op2);
        set_estado(OPCION1);
    }
	//ItemListener
    public void itemStateChanged(ItemEvent itemevent)
    {
        RBMI rbmi = (RBMI)itemevent.getSource();
        if(!rbmi.isSelected())
            return;
        if(ignorar)
        {
            ignorar = false;
            return;
        }
        if(rbmi.pos != estado)
        {
            estado = rbmi.pos;
            hacer_llamada_de_cambio();
        }
    }
	//Figura_texto2D
    public void set_estado(int i)
    {
        estado = i;
        ignorar = true;
        if(i == OPCION1)
            op1.setSelected(true);
        else
            op2.setSelected(true);
    }

    public void set_estado(boolean boo)
    {
        if(boo)
            set_estado(1);
        else
            set_estado(0);
    }

    /*public void set_estado_de_TXT(String s)
    {
        if(s.equals(OPCION1_TXT))
        {
            estado = OPCION1;
            op1.setSelected(true);
        } else
        {
            estado = OPCION2;
            op2.setSelected(true);
        }
    }*/

    /*public void reverse()
    {
        if(estado == OPCION1)
            setState(OPCION2);
        else
            setState(OPCION1);
    }
*/
 /*   public String getStateText()
    {
        if(estado == OPCION1)
            return OPCION1_TXT;
        else
            return OPCION2_TXT;
    }
*/
    public int get_estado()
    {
        return estado;
    }

    public void hacer_llamada_de_cambio()
    {
    	System.out.println("Menu_de_seleccion_doble:hacer_llamada_de_cambio:estado= "+estado);
    	
    	if( estado == OPCION1)
    	{
//    		Menu_de_ayuda.ver_logo.setState(OPCION1);
//    		Graficador3D.barra.barra_de_herramientas.borrar_logo();
//    		Graficador3D.barra.barra_de_herramientas.agregar_logo();
    	}	
    	else
    	if( estado == OPCION2)
    	{
//    		Menu_de_ayuda.ver_logo.setState(OPCION2);
    	}
    	
    	
    	
    }

    
}