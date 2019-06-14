
package com.cc.paint3D.Graficador3D.interfaces;

import com.cc.paint3D.Graficador3D.Paneles.MasMenos;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.Color;
import java.awt.Container;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class Caja_de_real_masmenos extends JPanel
    implements Ajustador_de_masmenos
{
	public float min;
    public float max;
    public float actual;
    public JTextField tf;
	
    public Caja_de_real_masmenos(String s, float f, float f1, float f2)
    {
        min = f1;
        max = f2;
        actual = f;
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
        int j = Math.round(actual * 10F);
        if(i < 0)
            j--;
        else
            j++;
        if(j >= 0)
        {
            actual = j;
            actual = actual / 10F;
            tf.setText(String.valueOf(actual));
        }
    }
    
}