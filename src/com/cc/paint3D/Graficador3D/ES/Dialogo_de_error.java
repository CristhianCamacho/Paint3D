
package com.cc.paint3D.Graficador3D.ES;

import com.cc.paint3D.Graficador3D.ES.ES_Dialogo;
import com.cc.paint3D.Graficador3D.ES.ES_etiqueta;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Dialogo_de_error extends ES_Dialogo
    implements KeyListener
{

    public Dialogo_de_error(String s)
    {
        super(" Un mensaje de Error del programa.");
        
        super.jp.setBackground(Color.yellow);
        
        super.jp.setLayout(new FlowLayout());
        ES_etiqueta eslabel = new ES_etiqueta("  " + s + "  ");
        eslabel.setIcon(new ImageIcon(ES_Utiles.get_URL_images("noway.jpg")));
        super.jp.add(eslabel);
        Boton_Libreria boton_libreria = new Boton_Libreria("Continuar");
        super.jp.add(boton_libreria);
        boton_libreria.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                ver_constantes();
                cerrar_Dialogo();
            }

        });
        dimensionar_y_mostrar();
        boton_libreria.addKeyListener(this);
        boton_libreria.requestFocus();
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyPressed(KeyEvent keyevent)
    {
        
        if(keyevent.getKeyCode() == 10)
            cerrar_Dialogo();
    }
    
    public void ver_constantes()
    {                
    }
}