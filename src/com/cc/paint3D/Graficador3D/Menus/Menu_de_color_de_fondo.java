
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.Barra;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos_temporales;
import com.cc.paint3D.Graficador3D.G3D.Datos_utiles;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.media.j3d.*;
import javax.swing.*;



public class Menu_de_color_de_fondo extends JMenu
    implements ActionListener
{
	public static String CONFIGURAR = "colores";
    public String BORRAR;
    public static Color color_de_fondo;
    public static BranchGroup bg;
    public static JMenuItem jmi_borrar_color;

    static 
    {
        color_de_fondo = Color.black;
    }
	
    public Menu_de_color_de_fondo()
    {
        super(Graficador3D.BACKGROUND_COLOR);
        BORRAR =Graficador3D.REMOVE_CURRENT ;
        add(ES_Utiles.get_MI(CONFIGURAR, this));
        jmi_borrar_color = add(ES_Utiles.get_MI(BORRAR, this));
        jmi_borrar_color.setEnabled(false);
        addSeparator();
        Barra_de_Menus.agregar_Colores(this);
    }

    public static void escribir_Background(DataOutputStream dataoutputstream)
        throws IOException
    {
        if(bg == null)
            dataoutputstream.writeInt(0);
        else
            dataoutputstream.writeInt(color_de_fondo.getRGB());
    }

    public static void borrar_este()
    {
        if(bg != null)
            bg.detach();
        bg = null;
        jmi_borrar_color.setEnabled(false);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Color color = null;
        String s = actionevent.getActionCommand();
        if(s == BORRAR)
        {
            borrar_este();
            return;
        }
        if(s == CONFIGURAR)
        {
            color = JColorChooser.showDialog(Escena.handle, CONFIGURAR, color_de_fondo);
            Barra.handle.repaint();
        } else
        {
            color = Barra_de_Menus.get_Color(s);
        }
        if(color == null)
        {
            return;
        } else
        {
            agregar_Color(color);
            return;
        }
    }

    public static void agregar_Color(Color color)
    {
        jmi_borrar_color.setEnabled(true);
        color_de_fondo = color;
        javax.vecmath.Color3f color3f = Manejador_de_archivos_temporales.getColor3f(color);
        borrar_este();
        Menu_de_fondo.borrar_fondo();
        Background background = new Background(color3f);
        background.setApplicationBounds(Datos_utiles.boundingSphere);
        bg = new BranchGroup();
        bg.setCapability(BranchGroup.ALLOW_DETACH);
        bg.addChild(background);
        Escena.bg.addChild(bg);
    }

    
}