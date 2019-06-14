
package com.cc.paint3D.Graficador3D.interfaces;

import com.cc.paint3D.Graficador3D.Paneles.Panel_de_mas_menos;

public class Ajustador_de_cambio_de_real
    implements Ajustador
{
	String txt;
    StringBuffer sb;
    float actual;
    int prop;
    Fijable fijable;
	
    public Ajustador_de_cambio_de_real(int i, Fijable fijabl, String s)
    {
        prop = i;
        txt = s;
        fijable = fijabl;
        Object obj = fijable.get_Prop(i);
        actual = ((Float)obj).floatValue();
        sb = new StringBuffer(30);
        Panel_de_mas_menos.panel_On(this);
        Panel_de_mas_menos.set_Texto(get_linea());        
    }

    public String alterar_valor(int i)
    {
        int j = Math.round(actual * 10F);
        if(i < 0)
            j--;
        else
            j++;
        if(j >= 0)
        {
            actual = j;
            actual = actual / 10F;
        }
        return get_linea();
    }

    public void set_valor()
    {
        fijable.set_Prop(prop, new Float(actual));
    }
    
    public void set_valor(int i)
    {
    	actual=(float)i;	
    }

    public String get_linea()
    {
        sb.setLength(0);
        sb.append(' ').append(txt).append("  ").append(actual);
        return sb.toString();
    }
    
}