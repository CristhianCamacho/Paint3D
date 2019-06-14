
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador;
import com.cc.paint3D.Graficador3D.interfaces.Fijable;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_mas_menos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JMenuItem;

public class JMI_Ajustador_de_real extends JMenuItem
    implements ActionListener, Ajustador
{
	String txt;
    StringBuffer sb;
    float actual;
    int prop;
    Fijable fijable;
	
    public JMI_Ajustador_de_real(int i, String s)
    {
        this(i, s, null);
    }

    public JMI_Ajustador_de_real(int i, String s, Fijable fijabl)
    {
        super(s);
        prop = i;
        txt = s;
        fijable = fijabl;
        addActionListener(this);
        
    }

    public String alterar_valor(int i)
    {
        int j = Math.round(actual * 10F);
        if(i < 0)
            j--;
        else
            j++;
        if(j >= 1)
        {
            actual = j;
            actual = actual / 10F;
        }
        return get_linea();
    }

    public void set_valor()
    {
        if(fijable == null)
            Escena.get_actual().set_Prop(prop, new Float(actual));
        else
            fijable.set_Prop(prop, new Float(actual));
    }
    
    public void set_valor(int i)
    {
    	actual=i;	
    }

    public String get_linea()
    {
        sb.setLength(0);
        sb.append(' ').append(txt).append("  ").append(actual);
        return sb.toString();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        
        
        Object obj = fijable != null ? fijable.get_Prop(prop) : Escena.get_actual().get_Prop(prop);
                
        actual = ((Float)obj).floatValue();
        sb = new StringBuffer(30);
        Panel_de_mas_menos.panel_On(this);
        Panel_de_mas_menos.set_Texto(get_linea());
    }

    
}