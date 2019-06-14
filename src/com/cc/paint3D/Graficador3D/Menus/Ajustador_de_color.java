
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Ajustador_de_color extends JMenuItem
    implements ActionListener, Props//texto 2D
{

    public Ajustador_de_color()
    {
        super(Graficador3D.SET_COLOR);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Tipo_componente tipo_componente = Escena.get_actual();
        Color color = JColorChooser.showDialog(Escena.handle, Graficador3D.CHOOSE_A_COLOR, (Color)tipo_componente.get_Prop(Props.COLOR));
        if(color == null)
        {
            return;
        } else
        {
            tipo_componente.set_Prop(Props.COLOR, color);
            return;
        }
    }
}