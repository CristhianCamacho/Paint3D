
package com.cc.paint3D.Graficador3D.Utiles;

import java.awt.*;
import javax.swing.Icon;

public class Icono_Cuadrado implements Icon
{

	static final int DIM = 14;    
    Color color;

    public Icono_Cuadrado(Color col)
    {
        color = col;
    }

    public int getIconHeight()
    {
        return DIM;
    }

    public int getIconWidth()
    {
        return DIM;
    }

    public void paintIcon(Component component, Graphics g, int i, int j)
    {
        int k = j;
        g.setColor(color);
        g.fillRect(10, k, DIM, DIM);
        g.setColor(color.darker());
        g.drawRect(10, k, DIM-1, DIM-1);
    }

    
}