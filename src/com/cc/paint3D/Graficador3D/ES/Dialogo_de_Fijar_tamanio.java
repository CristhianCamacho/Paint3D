
package com.cc.paint3D.Graficador3D.ES;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.Paneles.MasMenos;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_masmenos;
import com.cc.paint3D.Graficador3D.Paneles.Panel_Principal;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class Dialogo_de_Fijar_tamanio extends ES_Dialogo
    implements Ajustador_de_masmenos, ActionListener
{

	String CONTINUAR;
    String CANCELAR;
    int inc;
    Dimension dim;
    JTextField cols;
    JTextField fils;
    int fn;
    int cn;
    MasMenos fp;
    MasMenos cp;
    JRadioButton rb1;
    JRadioButton rb10;
    JRadioButton rb100;
    

    public Dialogo_de_Fijar_tamanio()
    {
        super("Fijar el tama�o exacto de la vista");
        CONTINUAR = "Aplicar tama�o";
        CANCELAR = "Cancelar";
        inc = 1;
        super.jp.setLayout(new BorderLayout());
        dim = Panel_Principal.handle.getSize();
        cn = dim.width;
        fn = dim.height;
        fils = new JTextField(3);
        fils.setEditable(false);
        fils.setBackground(Color.white);
        fils.setFont(ES_Utiles.FONT_DE_MENSAJES);
        fils.setText(String.valueOf(fn));
        cols = new JTextField(3);
        cols.setEditable(false);
        cols.setBackground(Color.white);
        cols.setFont(ES_Utiles.FONT_DE_MENSAJES);
        cols.setText(String.valueOf(cn));
        JPanel jpanel = new JPanel();
        jpanel.add(cols);
        jpanel.add(cp = new MasMenos(this));
        jpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), Graficador3D.HEIGHT));
        super.jp.add(jpanel, "North");
        jpanel = new JPanel();
        jpanel.add(fils);
        jpanel.add(fp = new MasMenos(this));
        jpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), Graficador3D.WIDTH));
        super.jp.add(jpanel, "Center");
        jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        
        JPanel jpanel1 = new JPanel();
        ButtonGroup buttongroup = new ButtonGroup();
        jpanel1.add(rb1 = new JRadioButton("Saltar 1"));
        
        rb1.addActionListener(this);
        rb1.setSelected(true);
        buttongroup.add(rb1);
        
        jpanel1.add(rb10 = new JRadioButton("Saltar 10"));
        rb10.addActionListener(this);
        buttongroup.add(rb10);
        
        jpanel1.add(rb100 = new JRadioButton("Saltar 100"));
        rb100.addActionListener(this);
        buttongroup.add(rb100);        
        
        jpanel.add(jpanel1, "North");
        JPanel jpanel2 = new JPanel();
        Boton_Libreria boton_libreria;
        jpanel2.add(boton_libreria = new Boton_Libreria(CONTINUAR));
        boton_libreria.addActionListener(this);
        jpanel2.add(boton_libreria = new Boton_Libreria(CANCELAR));
        boton_libreria.addActionListener(this);
        jpanel.add(jpanel2, "South");
        jpanel.setBorder(BorderFactory.createEtchedBorder());
        super.jp.add(jpanel, "South");
        dimensionar_y_mostrar();
    }

    public void ajustar(MasMenos masmenos, int i)
    {
        if(masmenos == fp)
        {
            fn += i;
            if(fn != 0)
                fils.setText(String.valueOf(fn));
            else
                fn++;
        } else
        {
            cn += i;
            if(cn != 0)
                cols.setText(String.valueOf(cn));
            else
                cn++;
        }
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getActionCommand() == CONTINUAR)
        {
            if(cn == dim.width && fn == dim.height)
                return;
            cerrar_Dialogo();
            Panel_Principal.handle.setSize(cn, fn);
            Escena.handle.pack();
        } else
        if(actionevent.getActionCommand() == CANCELAR)
            cerrar_Dialogo();
        else
        if(actionevent.getSource() == rb1)
        {
            fp.set_incremento(1);
            cp.set_incremento(1);
        } else
        if(actionevent.getSource() == rb10)
        {
            fp.set_incremento(10);
            cp.set_incremento(10);
        } else
        if(actionevent.getSource() == rb100)
        {
            fp.set_incremento(100);
            cp.set_incremento(100);
        }
    }

    
}