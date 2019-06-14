
package com.cc.paint3D.Graficador3D.Botones;

import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import javax.swing.*;

public class Boton_Flash extends JButton
    implements ActionListener
{
    private class GOF extends RGBImageFilter
    {

        public int filterRGB(int i, int j, int k)
        {
            int l = (k & 0xff0000) >> 16;
            int i1 = (k & 0xff00) >> 8;
            int j1 = k & 0xff;
            l = (l + i1 + j1) / 3;
            return 0xff000000 + (l << 16) + (l << 8) + l;
        }

        public GOF()
        {
            super.canFilterIndexColorModel = true;
        }
    }


    static RGBImageFilter filter;
    Timer timer;
    Dimension prefdimension;
    boolean raton_up;
    boolean entro;
    boolean repetir;
    Image imagen;
    Image imagen_alternativa;
    boolean cargado;
    int x;
    int y;
    Color normal=getBackground();
    
    public Boton_Flash(Image image, String s, Dimension dimension, ActionListener actionlistener, String s1)
    {
        if(filter == null)
            filter = new GOF();
                   
        //enableEvents(16);
        imagen = image;
        imagen_alternativa = get_image_alternativa(image);
        prefdimension = dimension;
        setPreferredSize(dimension);
        if(s != null)
            setToolTipText(s);
        addActionListener(actionlistener);
        if(s1 != null)
            setActionCommand(s1);
        setBorderPainted(false);
    }

    public void setRepeat(boolean flag)
    {
        repetir = flag;
    }

    public Image get_image_alternativa(Image image)
    {
        Image image1 = createImage(new FilteredImageSource(image.getSource(), filter));
        prepareImage(image1, this);
        return ES_Utiles.cargar_Image(image1);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(raton_up)
            return;
        doClick();
        if(timer.getDelay() == 300)
            timer.setDelay(30);
        timer.start();
    }

    public void paintComponent(Graphics g)
    {
        if(imagen == null)
            return;
        if(!cargado)
        {
            x = (prefdimension.width - imagen.getWidth(null)) / 2;
            y = (prefdimension.height - imagen.getHeight(null)) / 2;
            cargado = true;
        }
        g.setColor(getBackground());
        g.fillRect(0, 0, prefdimension.width, prefdimension.height);
        if(!isEnabled())
            g.drawImage(imagen_alternativa, x, y, null);
        else
        if(raton_up)
            g.drawImage(imagen, x, y, null);
        else
            g.drawImage(imagen, x + 1, y + 1, null);
        if(entro)
        {
            g.setColor(Color.black);
            g.drawRect(0, 0, prefdimension.width - 1, prefdimension.height - 1);
            g.setColor(Color.lightGray);
            g.draw3DRect(1, 1, prefdimension.width - 3, prefdimension.height - 3, raton_up);
        }
    }	
	 
    public void processMouseEvent(MouseEvent mouseevent)
    {
        if(!isEnabled())
            return;
        switch(mouseevent.getID())
        {
        case Event.MOUSE_MOVE:
        default:
            break;

        case Event.MOUSE_ENTER: 
            raton_up = true;
            entro = true;           
            setBackground(normal.darker());
            repaint();
            break;

        case Event.MOUSE_EXIT: 
            raton_up = true;
            setBackground(normal);
            repaint();
            entro = false;
            break;

        case Event.MOUSE_DOWN: 
            raton_up = false;
            repaint();
            if(!repetir)
                break;
            if(timer == null)
                timer = new Timer(300, this);
            else
                timer.setDelay(300);
            timer.setRepeats(false);
            timer.start();
            break;

        case Event.MOUSE_UP: 
            raton_up = true;
            repaint();
            if(!contains(mouseevent.getX(), mouseevent.getY()))
                return;
            break;
        }
        super.processMouseEvent(mouseevent);
    }

    
}