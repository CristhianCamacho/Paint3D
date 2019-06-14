
package com.cc.paint3D.Graficador3D.interfaces;

import com.cc.paint3D.Graficador3D.Paneles.Panel_de_mas_menos;

public class Ajustador_de_angulo
    implements Ajustador
{

    public Ajustador_de_angulo(int i, String s)
    {
        this(i, s, null);
    }

    public Ajustador_de_angulo(int i, String s, Fijable fijabl)
    {
        PI = 3.141593F;
        prop = i;
        txt = s;
        fijable = fijabl;
        float f = ((Float)fijable.get_Prop(i)).floatValue();
        actual = (int)((f * 180) / PI);
        sb = new StringBuffer(30);
        Panel_de_mas_menos.panel_On(this);
        Panel_de_mas_menos.set_Texto(getLine());
                
    }

    public String alterar_valor(int i)
    {
        int j = actual + i;
        if(j >= 0 && j < 181)
            actual = j;
        return getLine();
    }

    public void set_valor()
    {
        float f = actual;
        float f1 = (f * PI) / 180F;
        if(f1 > PI)
            f1 = PI;
        fijable.set_Prop(prop, new Float(f1));
    }
    
    public void set_valor(int i)
    {
    	actual=i;	
    }

    public String getLine()
    {
        sb.setLength(0);
        sb.append(' ').append(txt).append("  ").append(actual).append(" grados");//dgrs
        return sb.toString();
    }

    String txt;
    StringBuffer sb;
    int actual;
    int prop;
    Fijable fijable;
    float PI;
}