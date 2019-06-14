
package com.cc.paint3D.Graficador3D.interfaces;

import com.cc.paint3D.Graficador3D.Paneles.Panel_de_flechas;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_mas_menos;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

public class Ajustador_de_rotacion
    implements Ajustador
{
	
	double angulo;
    double limite;
    TransformGroup tg;
    Transform3D t3dx;
    Transform3D t3dy;
    double x;
    double y;

    public Ajustador_de_rotacion(TransformGroup transformgroup, String s)
    {
        angulo = 0.39269908169872414D;
        limite = 6.2831853071795862D - angulo / 2D;
        tg = transformgroup;
        Panel_de_flechas.panel_On(this, 0);
        Panel_de_mas_menos.set_Texto(s);
        t3dx = new Transform3D();
        t3dy = new Transform3D();
        set_angulo();
    }

    public String alterar_valor(int i)
    {
        tg.getTransform(t3dx);
        switch(i)
        {
        default:
            break;

        case Panel_de_flechas.ARRIBA:
            x -= angulo;
            if(x < -limite)
                x = 0.0D;
            break;

        case Panel_de_flechas.ABAJO:
            x += angulo;
            if(x > limite)
                x = 0.0D;
            break;

        case Panel_de_flechas.DERECHA:
            y += angulo;
            if(y > limite)
                y = 0.0D;
            break;

        case Panel_de_flechas.IZQUIERDA:
            y -= angulo;
            if(y < -limite)
                y = 0.0D;
            break;
        }
        set_angulo();
        return null;
    }

    void set_angulo()
    {
        t3dx.rotX(x);
        t3dy.rotY(y);
        t3dx.mul(t3dy);
        tg.setTransform(t3dx);
    }

    public void set_valor()
    {
    }
    
    public void set_valor(int i)
    {
    	angulo=(double)i;	
    }
    
}