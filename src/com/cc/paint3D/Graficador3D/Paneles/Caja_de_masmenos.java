
package com.cc.paint3D.Graficador3D.Paneles;

import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_masmenos;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_Caja_masmenos;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.Color;
import java.awt.Container;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class Caja_de_masmenos extends JPanel
    implements Ajustador_de_masmenos
{
	public int min;
    public int max;
    public int actual;
    public Ajustador_de_Caja_masmenos ajustador_de_Caja_masmenos;
    public JTextField tf;

    public Caja_de_masmenos(String s, Ajustador_de_Caja_masmenos ajustador, int i, int j, int k)
    {
        ajustador_de_Caja_masmenos = ajustador;
        actual = i;
        min = j;
        max = k;
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), s));
        tf = new JTextField(4);
        tf.setEditable(false);
        tf.setBackground(Color.white);
        tf.setFont(ES_Utiles.FONT_DE_MENSAJES);
        tf.setText(String.valueOf(actual));
        add(tf);
        MasMenos masmenos = new MasMenos();
        add(masmenos);
        masmenos.setAdjuster(this);
    }

    public void ajustar(MasMenos masmenos, int i)
    {
        int j = actual + i;
        if(j >= min && j <= max)
        {
            actual = j;
            tf.setText(String.valueOf(actual));
            if(ajustador_de_Caja_masmenos != null)
                ajustador_de_Caja_masmenos.ajustar(this, actual);
        }
    }

    
}