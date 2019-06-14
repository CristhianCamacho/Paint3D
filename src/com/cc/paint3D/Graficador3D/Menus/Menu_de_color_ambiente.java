
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


public class Menu_de_color_ambiente extends JMenu
    implements ActionListener
{
	String CONFIGURAR;
    String BORRAR;
    static BranchGroup bg;
    static Color amb;
    static JMenuItem jmi_borrar_color_ambiente;

    static 
    {
        amb = Color.black;
    }

    public Menu_de_color_ambiente()
    {
        
        super(Graficador3D.AMBIENT_COLOR);
        CONFIGURAR = Graficador3D.SET_AMBIENT_COLOR;
        BORRAR = Graficador3D.REMOVE_AMBIENT_COLOR;
        add(ES_Utiles.get_MI(CONFIGURAR, this));
        add(jmi_borrar_color_ambiente = ES_Utiles.get_MI(BORRAR, this));
        jmi_borrar_color_ambiente.setEnabled(false);
    }

    public static void escribir_Ambient(DataOutputStream dataoutputstream)
        throws IOException
    {
        if(bg == null)
            dataoutputstream.writeInt(0);
        else
            dataoutputstream.writeInt(amb.getRGB());
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Object obj = null;
        String s = actionevent.getActionCommand();
        if(s == BORRAR)
        {
            borrar_este();
        } else
        {
            Color color = JColorChooser.showDialog(Escena.handle, CONFIGURAR, amb);
            Barra.handle.repaint();
            if(color == null)
                return;
            agregar_Ambient(color);
        }
    }

    public static void agregar_Ambient(Color color)
    {
        jmi_borrar_color_ambiente.setEnabled(true);
        amb = color;
        javax.vecmath.Color3f color3f = Manejador_de_archivos_temporales.getColor3f(color);
        if(bg != null)
            bg.detach();
        AmbientLight ambientlight = new AmbientLight(color3f);
        ambientlight.setInfluencingBounds(Datos_utiles.boundingSphere);
        bg = new BranchGroup();
        bg.setCapability(BranchGroup.ALLOW_DETACH);
        bg.addChild(ambientlight);
        Escena.bg.addChild(bg);
    }

    public static void borrar_este()
    {
        if(bg != null)
        {
            bg.detach();
            bg = null;
        }
        jmi_borrar_color_ambiente.setEnabled(false);
    }

    
}