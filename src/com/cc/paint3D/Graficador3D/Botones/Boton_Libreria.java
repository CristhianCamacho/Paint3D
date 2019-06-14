
package com.cc.paint3D.Graficador3D.Botones;

import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;


public class Boton_Libreria extends JButton
{
	Color normal;

    public Boton_Libreria(String s, String s1)
    {
        super(s);
        setFont(ES_Utiles.FONT_DE_MENSAJES);
        setOpaque(true);
        normal = getBackground();
        if(s1 != null)
            setIcon(new ImageIcon(ES_Utiles.get_URL_images(s1)));
        addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent mouseevent)
            {
                if(isEnabled())
                {
                    setBackground(normal.darker());
                    repaint();
                }
            }

            public void mouseExited(MouseEvent mouseevent)
            {
                if(isEnabled())
                {
                    setBackground(normal);
                    repaint();
                }
            }

        });
    }

    public Boton_Libreria(String s)
    {
        this(s, "cy_dot.gif");
    }

    public Boton_Libreria(String s, MouseListener mouselistener)
    {
        this(s);
        addMouseListener(mouselistener);
    }
    
    public Boton_Libreria(String s, ActionListener actionlistener)
    {
        this(s);
        addActionListener(actionlistener);
    }

    public void setEnabled(boolean flag)
    {
        if(!flag)
            setBackground(normal);
        super.setEnabled(flag);
    }

   
}