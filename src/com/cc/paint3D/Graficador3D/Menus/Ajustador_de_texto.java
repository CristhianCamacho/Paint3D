
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_obtener_texto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JMenuItem;

public class Ajustador_de_texto extends JMenuItem
    implements ActionListener, Props
{

    public Ajustador_de_texto()
    {
        super(Graficador3D.SET_TEXT);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = Dialogo_de_obtener_texto.preguntando(Graficador3D.ENTER_TEXT);
        if(s.equals(""))
        {
            return;
        } else
        {
            Escena.get_actual().set_Prop(Props.TEXTO, s);
            return;
        }
    }
}