
package com.cc.paint3D.Graficador3D.HTML_ayuda;

import com.cc.paint3D.Graficador3D.ES.ES_Dialogo;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class Ventana_de_Acerca_de extends ES_Dialogo
    implements ActionListener
{
	
	public static final String CONTINUAR = "Continuar";
	
    public Ventana_de_Acerca_de(String s)
    {
        super("Acerca " + ES_Utiles.PRODUCTO);
        super.jp.setLayout(new BorderLayout());
        java.net.URL url = getClass().getResource(s);
        JEditorPane jeditorpane;
    
        try
        {
            jeditorpane = new JEditorPane(url);
        }
        catch(Exception exception)
        {
            new Dialogo_de_error("Acerca no encontrado");
            return;
        }
        jeditorpane.setEditable(false);
        JScrollPane jscrollpane = new JScrollPane();
        jscrollpane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLoweredBevelBorder()));
        javax.swing.JViewport jviewport = jscrollpane.getViewport();
        jviewport.add(jeditorpane);
        super.jp.add(jscrollpane, "Center");
        JPanel jpanel = new JPanel();
        Boton_Libreria boton_libreria;
        jpanel.add(boton_libreria = new Boton_Libreria(CONTINUAR));
        boton_libreria.addActionListener(this);
        jpanel.setBorder(BorderFactory.createEtchedBorder());
        super.jp.add(jpanel, "South");
        setSize((2 * Graficador3D.screensize.width) / 3, (2 * Graficador3D.screensize.height) / 3);
        Dimension dimension = getSize();
        setLocation((Graficador3D.screensize.width - dimension.width) / 2, (Graficador3D.screensize.height - dimension.height) / 2);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        cerrar_Dialogo();
    }

    
}