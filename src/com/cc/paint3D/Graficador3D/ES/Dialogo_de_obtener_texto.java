
package com.cc.paint3D.Graficador3D.ES;

import com.cc.paint3D.Graficador3D.ES.ES_Dialogo;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class Dialogo_de_obtener_texto extends ES_Dialogo
    implements ActionListener, KeyListener
{
	
	public static String APLICAR = "Aplicar";
    public static String CANCELAR = "Cancelar";
    public static String VACIO = "";
    public static JTextField tf;
		
    public Dialogo_de_obtener_texto(String s)
    {
        super(s);
        super.jp.setLayout(new FlowLayout());
        if(tf == null)
            tf = new JTextField(VACIO, 25);
        else
            tf.setText("");
        tf.setFont(ES_Utiles.FONT_DE_MENSAJES);
        super.jp.add(tf);
        tf.addKeyListener(this);
        tf.addActionListener(this);
        Boton_Libreria boton_libreria = new Boton_Libreria(APLICAR);
        boton_libreria.addActionListener(this);
        super.jp.add(boton_libreria);
        boton_libreria = new Boton_Libreria(CANCELAR);
        boton_libreria.addActionListener(this);
        super.jp.add(boton_libreria);
        dimensionar_y_mostrar();
        tf.requestFocus();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        cerrar_Dialogo();
        if(actionevent.getActionCommand() == CANCELAR)
            tf.setText("");
    }

    public static String preguntando(String s)
    {
        new Dialogo_de_obtener_texto(s);
        return tf.getText();
    }

    public void keyTyped(KeyEvent keyevent)
    {
        char c = keyevent.getKeyChar();
System.out.println("Obtener_texto:keyTyped:c= "+c);
        if(c == '\033')
            cerrar_Dialogo();
    }

    public void keyPressed(KeyEvent keyevent)
    {
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

}