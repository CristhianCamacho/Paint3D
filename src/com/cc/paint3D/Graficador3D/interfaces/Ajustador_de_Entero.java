
package com.cc.paint3D.Graficador3D.interfaces;

import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_mas_menos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JMenuItem;

public class Ajustador_de_Entero extends JMenuItem//cilindros y conos
    implements ActionListener, Ajustador
{
	
	String txt;
    StringBuffer sb;
    int actual;
    int prop;
    int min;
    int max;

    public Ajustador_de_Entero(int i, String s, int j, int k)
    {
        super(s);
        prop = i;
        txt = s;
        min = j;
        max = k;
        addActionListener(this);
        
    }

    public String alterar_valor(int i)
    {
        if(i < 0)
        {
            if(actual > min)
                actual--;
        } else
        if(actual < max)
            actual++;
        return get_linea();
    }

    public void set_valor()
    {
        Escena.get_actual().set_Prop(prop, new Integer(actual));
    }
    
    public void set_valor(int i)
    {
    	actual=i;	
    }

    String get_linea()
    {
        sb.setLength(0);
        sb.append(' ').append(txt).append("  ").append(actual);
        return sb.toString();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Object obj = Escena.get_actual().get_Prop(prop);
        actual = ((Integer)obj).intValue();
        sb = new StringBuffer(30);
        Panel_de_mas_menos.panel_On(this);
        Panel_de_mas_menos.set_Texto(get_linea());
    }
    
}