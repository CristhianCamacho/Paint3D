
package com.cc.paint3D.Graficador3D.ES;

import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JLabel;


public class ES_etiqueta extends JLabel
{

    public ES_etiqueta(String s)
    {
        super(s);
        setFont(ES_Utiles.FONT_DE_MENSAJES);
        setForeground(Color.black);
    }
}