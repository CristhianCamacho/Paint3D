
package com.cc.paint3D.Graficador3D.Paneles;

import com.cc.paint3D.Graficador3D.interfaces.Panel_de_opciones_multiples_Listener;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Panel_de_opciones_multiples extends JPanel
    implements ActionListener
{
	public JRadioButton jrb[];
    public Panel_de_opciones_multiples_Listener oml;
    public int actual;
    public int ultimo;
	
    public Panel_de_opciones_multiples(String s, String as[], Panel_de_opciones_multiples_Listener panel_de_opciones_multiples_Listener)
    {
        this(s, as, panel_de_opciones_multiples_Listener, 0);
    }

    public Panel_de_opciones_multiples(String s, String as[], Panel_de_opciones_multiples_Listener panel_de_opciones_multiples_Listener, int i)
    {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), s));
        oml = panel_de_opciones_multiples_Listener;
        setLayout(new FlowLayout(1, 5, 5));
        ButtonGroup buttongroup = new ButtonGroup();
        ultimo = as.length;
        jrb = new JRadioButton[ultimo];
        ultimo--;
        for(int j = 0; j <= ultimo; j++)
        {
            JRadioButton jradiobutton = new JRadioButton(as[j]);
            jrb[j] = jradiobutton;
            jradiobutton.addActionListener(this);
            buttongroup.add(jradiobutton);
            add(jradiobutton);
        }

        jrb[i].setSelected(true);
        actual = i;
    }

    public Panel_de_opciones_multiples(String s, String as[])
    {
        this(s, as, null);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        for(int i = 0; i <= ultimo; i++)
        {
            if(!jrb[i].isSelected())
                continue;
            actual = i;
            break;
        }

        if(oml != null)
            oml.cambio_opcion(this);
    }

    public void set_opcion(int i)
    {
        if(i > ultimo || actual == ultimo)
        {
            return;
        } else
        {
            actual = i;
            jrb[actual].setSelected(true);
            return;
        }
    }

    public int get_opcion()
    {
        return actual;
    }

    
}