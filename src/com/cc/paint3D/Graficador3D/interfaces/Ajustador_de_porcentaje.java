
package com.cc.paint3D.Graficador3D.interfaces;

import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_mas_menos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JMenuItem;

public class Ajustador_de_porcentaje extends JMenuItem
    implements ActionListener, Ajustador
{
	String txt;
    StringBuffer sb;
    int actual;
    int prop;
    Fijable fijable;

    public Ajustador_de_porcentaje(int i, String s)
    {
        this(i, s, null);
    }

    public Ajustador_de_porcentaje(int i, String s, Fijable fijabl)
    {
        super(s);
        prop = i;
        txt = s;
        fijable = fijabl;
        addActionListener(this); 
        
        //if(Panel_de_mas_menos.handle!=null)
        try
        {
        	try
            {Panel_de_mas_menos.handle.borrar_jslistener();
        	}catch(Exception e){ /*System.out.println("Error:Panel_de_mas_menos.handle==null"+e);*/ }
        
           	Panel_de_mas_menos.handle.agregar_jslistener(100,0,1,10);
        	//System.out.println("Ajustador_de_porcentaje:Ajustador_de_porcentaje:"); 
        	Panel_de_mas_menos.handle.updateUI();       	
        }catch(Exception e){ /*System.out.println("Error:Panel_de_mas_menos.handle==null"+e);*/ }
             
    }

    public String alterar_valor(int i)
    {
        if(i < 0)
        {
            if(actual > 0)
                actual--;
        } else
        if(actual < 100)
            actual++;
        return get_linea();
    }

    public void set_valor()
    {
        if(fijable != null)
            fijable.set_Prop(prop, new Integer(actual));
        else
            Escena.get_actual().set_Prop(prop, new Integer(actual));
    }
    
    public void set_valor(int i)
    {
    	actual=i;	
    }   

    public String get_linea()
    {
        sb.setLength(0);
        sb.append(' ').append(txt).append("  ").append(actual).append("%");
        return sb.toString();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Object obj = null;
        if(fijable != null)
            obj = fijable.get_Prop(prop);
        else
            obj = Escena.get_actual().get_Prop(prop);
        actual = ((Integer)obj).intValue();
        sb = new StringBuffer(30);
        Panel_de_mas_menos.panel_On(this);
        Panel_de_mas_menos.set_Texto(get_linea());
    }
    
}