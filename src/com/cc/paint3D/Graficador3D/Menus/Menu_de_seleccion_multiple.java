
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.interfaces.Seleccion_multiple;
import com.cc.paint3D.Graficador3D.Botones.RBMI;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventObject;
import javax.swing.*;


public class Menu_de_seleccion_multiple extends JMenu
    implements ItemListener
{
	public RBMI items[];
    public ButtonGroup bg;
    public boolean ignorar=false;
    public Seleccion_multiple selector;
    public RBMI seleccionado;
    public int anterior;

    public Menu_de_seleccion_multiple(String s, String as[], Seleccion_multiple seleccion_multiple)
    {
        super(s);
        bg = new ButtonGroup();
        selector = seleccion_multiple;
        int i = as.length;
        items = new RBMI[i];
        for(int j = 0; j < i; j++)
        {
            RBMI rbmi;
            add(rbmi = new RBMI(j, as[j]));
            rbmi.addItemListener(this);
            bg.add(rbmi);
            items[j] = rbmi;
        }

        seleccionado = items[0];
        ignorar = true;
        seleccionado.setSelected(true);
    }
	//Menu_de_textura, Menu_de_alineamiento3D
    public void set_Seleccion_multiple(Seleccion_multiple seleccion_multiple)
    {
        selector = seleccion_multiple;
    }
	//ItemListener
    public void itemStateChanged(ItemEvent itemevent)
    {
System.out.println("Menu_de_seleccion_multiple:itemStateChanged: ");
        
        RBMI rbmi = (RBMI)itemevent.getSource();
        
        if(!rbmi.isSelected())
            return;
        
        if(ignorar)
        {
            ignorar = false;
            
        }
        
        if(rbmi != seleccionado)
        {
            anterior = seleccionado.pos;
            seleccionado = rbmi;
            if(selector != null)
        	selector.llamada_multiple(this, rbmi.pos);           
        }
        
    }

    public void set_estado_anterior()
    {
        if(seleccionado.pos == anterior)
        {
            return;
        } else
        {
            set_estado(anterior);
            return;
        }
    }

    public void set_estado(int i)
    {
        
        if(seleccionado != null)
        {
            ignorar = true;
            seleccionado.setSelected(false);
        }
        
        seleccionado = items[i];
        ignorar = true;
        seleccionado.setSelected(true);
    }

    public int get_estado()
    {
        return seleccionado.pos;
    }
    
}