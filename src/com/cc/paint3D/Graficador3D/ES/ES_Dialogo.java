
package com.cc.paint3D.Graficador3D.ES;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Barra;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;


public class ES_Dialogo extends JDialog
{
	    
    public JPanel jp;    
    public static Dimension dimension_por_defecto;
	
    public ES_Dialogo(String s)
    {
        super(Barra.handle, s, true);
        Barra.handle.repaint();
        jp = new JPanel();
        jp.setOpaque(true);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jp, "Center");
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent)
            {
                ultima_llamada_de_Dialogo();
                cerrar_Dialogo();
            }

        });
    }

    public void dimensionar_y_mostrar()
    {
        pack();
        Dimension dimension = getSize();
        Dimension dimension1 = Barra.handle.getSize();
        Dimension dimension2 = Graficador3D.screensize;
        Point point = Barra.handle.getLocation();
        if(dimension.width < dimension1.width && dimension.height + point.y < Graficador3D.screensize.height)
        {
            setLocation(point.x + (dimension1.width - dimension.width) / 2, point.y);
        } else
        {
            if(dimension.width > dimension2.width)
            {
                point.x = 0;
                dimension.width = dimension2.width;
            } else
            {
                point.x = (dimension2.width - dimension.width) / 2;
            }
            if(dimension.height > dimension2.height)
            {
                point.y = 0;
                dimension.height = dimension2.height;
            } else
            {
                point.y = (dimension2.height - dimension.height) / 2;
            }
            setLocation(point);
            setSize(dimension);

        }
        setVisible(true);
    }

    public void cerrar_Dialogo()
    {
        jp=null;
        this.dispose();
        setVisible(false);
    }
	
	public void ultima_llamada_de_Dialogo()
	{		
	}
}