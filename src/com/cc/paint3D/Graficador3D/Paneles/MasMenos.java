
package com.cc.paint3D.Graficador3D.Paneles;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_masmenos;
import com.cc.paint3D.Graficador3D.Botones.Boton_Flash;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.Paneles.Barra_de_herramientas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.AbstractButton;
import javax.swing.JPanel;

public class MasMenos extends JPanel
    implements ActionListener
{
	static Dimension DIM = new Dimension(32, 32);
    Ajustador_de_masmenos ajustador_de_masmenos;
    Boton_Flash mas;
    Boton_Flash menos;
    int incremento;

    public MasMenos(Ajustador_de_masmenos ajustador)
    {
        this();
        ajustador_de_masmenos = ajustador;
    }

    public MasMenos()
    {
        setLayout(new FlowLayout(1, 5, 3));
        java.awt.Image image = ES_Utiles.get_System_Image(Graficador3D.MINUS_ICON_1);
        add(menos = new Boton_Flash(image, null, Barra_de_herramientas.DIM, this, null));
        menos.setRepeat(true);
        image = ES_Utiles.get_System_Image(Graficador3D.PLUS_ICON_1);
        add(mas = new Boton_Flash(image, null, Barra_de_herramientas.DIM, this, null));
        mas.setRepeat(true);
        incremento = 1;
    }

    public void setRepeat(boolean flag)
    {
        mas.setRepeat(flag);
        menos.setRepeat(flag);
    }

    public void set_incremento(int i)
    {
        incremento = i;
    }

    public void setAdjuster(Ajustador_de_masmenos ajustador)
    {
        ajustador_de_masmenos = ajustador;
    }

    public void off()
    {
        if(mas.isEnabled())
        {
            mas.setEnabled(false);
            menos.setEnabled(false);
        }
    }

    public void on()
    {
        if(!mas.isEnabled())
        {
            mas.setEnabled(true);
            menos.setEnabled(true);
        }
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if((Boton_Flash)actionevent.getSource() == mas)
            ajustador_de_masmenos.ajustar(this, incremento);
        else
            ajustador_de_masmenos.ajustar(this, -incremento);
    }

    
}