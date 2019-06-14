
package com.cc.paint3D.Graficador3D.ES;

import com.cc.paint3D.Graficador3D.ES.ES_Dialogo;
import com.cc.paint3D.Graficador3D.ES.ES_etiqueta;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.EventObject;
import javax.swing.*;

public class Dialogo_de_pregunta extends ES_Dialogo
    implements KeyListener, ActionListener
{
	
	public static Boton_Libreria resultado;
    public static Boton_Libreria c1;
    public static Boton_Libreria c2;
	
    public Dialogo_de_pregunta(String s, String s1, String s2)
    {
        super(" Una pregunta de G3D");
        super.jp.setBackground(Color.cyan.brighter());
        super.jp.setLayout(new FlowLayout());
        ES_etiqueta eslabel = new ES_etiqueta("  " + s + "  ");
        eslabel.setForeground(Color.black);
        eslabel.setIcon(new ImageIcon(ES_Utiles.get_URL_images("qm.gif")));
        super.jp.add(eslabel);
        c1 = new Boton_Libreria(s1);
        super.jp.add(c1);
        c1.addActionListener(this);
        c2 = new Boton_Libreria(s2);
        super.jp.add(c2);
        c2.addActionListener(this);
        dimensionar_y_mostrar();
        c1.addKeyListener(this);
        c1.requestFocus();
    }

    public static boolean preguntando(String s, String s1, String s2)
    {
        new Dialogo_de_pregunta(s, s1, s2);
        return resultado == c1;
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyPressed(KeyEvent keyevent)
    {
        if((Boton_Libreria)keyevent.getSource() == c1)
            System.out.println("ch1");
        if(keyevent.getKeyCode() == 10)
            cerrar_Dialogo();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        cerrar_Dialogo();
        resultado = (Boton_Libreria)actionevent.getSource();
    }

    
}