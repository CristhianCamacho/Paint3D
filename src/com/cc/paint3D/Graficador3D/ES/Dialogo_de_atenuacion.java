
package com.cc.paint3D.Graficador3D.ES;

import com.cc.paint3D.Graficador3D.G3D.Figura_luz;
import com.cc.paint3D.Graficador3D.interfaces.Caja_de_real_masmenos;
import com.cc.paint3D.Graficador3D.interfaces.Caja_de_real_masmenos;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_mas_menos;
import com.cc.paint3D.Graficador3D.G3D.Figura_luz;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.media.j3d.PointLight;
import javax.swing.*;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;

public class Dialogo_de_atenuacion extends ES_Dialogo implements ActionListener
{
	String FIJAR;
    String CANCELAR;
    PointLight pl;
    Caja_de_real_masmenos con;
    Caja_de_real_masmenos x;
    Caja_de_real_masmenos x2;

    public Dialogo_de_atenuacion(Figura_luz figura_luz)
    {
        super("Ajustar atenuacion");
        FIJAR = "Fijar";
        CANCELAR = "Cancelar";
        super.jp.setLayout(new GridLayout(5, 3));
        Panel_de_mas_menos.off();
        pl = (PointLight)figura_luz.light;
        Point3f point3f = new Point3f();
        pl.getAttenuation(point3f);
        super.jp.add(con = new Caja_de_real_masmenos("Valor de la Constante", ((Tuple3f) (point3f)).x, 0.0F, 100F));//"Constant value"
        super.jp.add(x = new Caja_de_real_masmenos("multiplicador en x ", ((Tuple3f) (point3f)).y, 0.0F, 100F));//"x multiplier"
        super.jp.add(x2 = new Caja_de_real_masmenos("multiplicador en x2 ", ((Tuple3f) (point3f)).z, 0.0F, 100F));//"x2 multiplier"
        JPanel jpanel = new JPanel(false);
        jpanel.setBorder(BorderFactory.createEtchedBorder());
        Boton_Libreria boton_libreria;
        jpanel.add(boton_libreria = new Boton_Libreria(FIJAR));
        boton_libreria.addActionListener(this);
        jpanel.add(boton_libreria = new Boton_Libreria(CANCELAR));
        boton_libreria.addActionListener(this);
        super.jp.add(jpanel);
        dimensionar_y_mostrar();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        cerrar_Dialogo();
        if(actionevent.getActionCommand() == FIJAR)
            pl.setAttenuation(new Point3f(con.actual, x.actual, x2.actual));
    }

    
}