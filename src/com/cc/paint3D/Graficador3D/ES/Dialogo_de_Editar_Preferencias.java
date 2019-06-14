
package com.cc.paint3D.Graficador3D.ES;

import com.cc.paint3D.Graficador3D.ES.ES_Dialogo;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.IO.Prefs;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos_temporales;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;
import com.cc.paint3D.Graficador3D.Paneles.SiNo;

import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;
import com.cc.paint3D.Graficador3D.Paneles.SiNo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.text.JTextComponent;


public class Dialogo_de_Editar_Preferencias extends ES_Dialogo
    implements ActionListener
{
    String CANCELAR;
    String APLICAR;
    JRadioButton pagina_con_color;
    JRadioButton pagina_sin_color;
    JTextField browser;
    JTabbedPane jtb;
    SiNo componente_en_el_centro;	
    	
    class HTML extends JPanel
    {

        HTML()
        {
            setLayout(new GridLayout(2, 3));
            add(componente_en_el_centro = new SiNo("Posicion del applet en la pagina", "Defecto", "Centro"));
            componente_en_el_centro.set_Si(Prefs.getBoolean("posicion_en_el_applet"));
            JPanel jpanel = new JPanel();
            jpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "fondo del HTML"));
            ButtonGroup buttongroup = new ButtonGroup();
            jpanel.add(pagina_sin_color = new JRadioButton("pagina web sin color"));
            buttongroup.add(pagina_sin_color);
            jpanel.add(pagina_con_color = new JRadioButton("Igual al applet"));
            buttongroup.add(pagina_con_color);
            if(Prefs.getBoolean("html_con_color_de_background"))
                pagina_con_color.setSelected(true);
            else
                pagina_sin_color.setSelected(true);
            add(jpanel);
        }
    }


    public Dialogo_de_Editar_Preferencias()
    {
        super("Editar preferencias");
        CANCELAR = "Cancelar";
        APLICAR = "Aplicar";
        Prefs.get("width");
        super.jp.setLayout(new BorderLayout());
        jtb = new JTabbedPane();
        jtb.setFont(Manejador_de_archivos_temporales.MSG_FONT);
        super.jp.add(jtb, "Center");
        jtb.addTab("HTML", new HTML());
        
        JPanel jpanel = new JPanel();
        jpanel.setBorder(BorderFactory.createEtchedBorder());
        Boton_Libreria boton_libreria;
        jpanel.add(boton_libreria = new Boton_Libreria(APLICAR));
        boton_libreria.addActionListener(this);
        jpanel.add(boton_libreria = new Boton_Libreria(CANCELAR));
        boton_libreria.addActionListener(this);
        super.jp.add(jpanel, "South");
        dimensionar_y_mostrar();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        cerrar_Dialogo();
        if(actionevent.getActionCommand() == CANCELAR)
        {
            return;
        } else
        {
            Prefs.set("browser", browser.getText());
            Prefs.setBoolean("posicion_en_el_applet", componente_en_el_centro.es_Si());
            Prefs.setBoolean("html_con_color_de_background", pagina_con_color.isSelected());
            Prefs.escribir_Prefs();
            return;
        }
    }

    public Insets getInsets()
    {
        return new Insets(30, 5, 5, 5);
    }

    
}