
package com.cc.paint3D.Graficador3D.interfaces;

import com.cc.paint3D.Graficador3D.Paneles.Panel_de_flechas;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_mas_menos;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

public class Ajustador_de_vector
    implements Ajustador
{
	float angulo;
    float sin;
    float cos;
    float msin;
    float mcos;
    Fijable fijable;
    int prop;
    Vector3f v;
    float x;
    float y;
    float z;

    public Ajustador_de_vector(int i, Vector3f vector3f, String s, Fijable fijabl)
    {
        angulo = 0.3926991F;
        sin = (float)Math.sin(angulo);
        cos = (float)Math.cos(angulo);
        msin = (float)Math.sin(-angulo);
        mcos = (float)Math.cos(-angulo);
        fijable = fijabl;
        prop = i;
        v = vector3f;
        Panel_de_flechas.panel_On(this, 0);
        Panel_de_mas_menos.set_Texto(s);
        
    }

    public String alterar_valor(int i)
    {
        switch(i)
        {
        case Panel_de_flechas.ARRIBA:
            y = cos * ((Tuple3f) (v)).y + sin * ((Tuple3f) (v)).z;
            z = -sin * ((Tuple3f) (v)).y + cos * ((Tuple3f) (v)).z;
            x = ((Tuple3f) (v)).x;
            break;

        case Panel_de_flechas.ABAJO:
            y = mcos * ((Tuple3f) (v)).y + msin * ((Tuple3f) (v)).z;
            z = -msin * ((Tuple3f) (v)).y + mcos * ((Tuple3f) (v)).z;
            x = ((Tuple3f) (v)).x;
            break;

        case Panel_de_flechas.DERECHA:
            x = cos * ((Tuple3f) (v)).x - sin * ((Tuple3f) (v)).z;
            z = sin * ((Tuple3f) (v)).x + cos * ((Tuple3f) (v)).z;
            break;

        case Panel_de_flechas.IZQUIERDA:
            x = mcos * ((Tuple3f) (v)).x - msin * ((Tuple3f) (v)).z;
            z = msin * ((Tuple3f) (v)).x + mcos * ((Tuple3f) (v)).z;
            break;
        }
        v.x = x;
        v.y = y;
        v.z = z;
        fijable.set_Prop(prop, v);
        return null;
    }

    public void set_valor()
    {
    }
    
    public void set_valor(int i)
    {
    	angulo=(float)i;	
    }
    
}