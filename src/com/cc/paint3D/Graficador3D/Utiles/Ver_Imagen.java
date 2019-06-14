
package com.cc.paint3D.Graficador3D.Utiles;

import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.*;
import java.io.File;
//lo usa Dialogo_de_busqueda_de_Archivos

public class Ver_Imagen extends Component
{
	Color background;
    Image imagen;
    int th;
    int tw;
    int iw;
    int ih;
    int nw;
    int nh;
    int ah;
    int aw;
    boolean cargada;


    public Ver_Imagen()
    {
        this(100, 200);
    }

    public Ver_Imagen(int i, int j)
    {
        tw = i;
        th = j;
    }

    public void ver_imagen(File file)
    {
        if(file != null)
        {
            imagen = ES_Utiles.get_Image(file);
            if(imagen == null)
                return;
            iw = imagen.getWidth(null);
            ih = imagen.getHeight(null);
            float f = iw;
            float f1 = ih;
            float f2 = f / f1;
            if(iw > aw)
            {
                nw = aw;
                nh = (int)((float)nw / f2);
                if(nh > ah)
                {
                    nh = ah;
                    nw = (int)(f2 * (float)nh);
                }
            } else
            if(ih > ah)
            {
                nh = ah;
                nw = (int)(f2 * (float)nh);
                if(nw > aw)
                {
                    nw = aw;
                    nh = (int)((float)nw / f2);
                }
            } else
            {
                nw = iw;
                nh = ih;
            }
        } else
        {
            imagen = null;
        }
        System.gc();
        if(isShowing())
            repaint();
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(tw, th);
    }

    public void paint(Graphics g)
    {
        if(!cargada)
        {
            Dimension dimension = getSize();
            aw = dimension.width;
            ah = dimension.height;
            background = getParent().getBackground();
            cargada = true;
        }
        g.setColor(background);
        g.fillRect(0, 0, aw, ah);
        if(imagen != null)
        {
            int i = (aw - nw) / 2;
            int j = (ah - nh) / 2;
            g.drawImage(imagen, i, j, nw, nh, this);
            g.setColor(Color.black);
            g.drawString("Ancho:" + iw + " Alto:" + ih, 2, 10);
        }
    }

    
}