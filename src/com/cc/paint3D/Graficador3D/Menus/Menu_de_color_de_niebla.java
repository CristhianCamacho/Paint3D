
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Barra;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Fijable;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_porcentaje;
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


public class Menu_de_color_de_niebla extends JMenu
    implements ActionListener, Fijable, Props
{
	String AGREGAR_NIEBLA;
    String BORRAR_NIEBLA;
    public static Menu_de_color_de_niebla handle;
    static BranchGroup bg;
    static Ajustador_de_porcentaje 	ajustador_de_porcentaje;
    static ExponentialFog exponentialFog;
    static Color color_de_niebla= Color.black;
    static int densidad = 100;
    static JMenuItem jmi_borrar_niebla;

    	
    public Menu_de_color_de_niebla()
    {
        super(Graficador3D.FOG);
        AGREGAR_NIEBLA = Graficador3D.ADD_EXPONENTIAL_FOG;
        BORRAR_NIEBLA = Graficador3D.REMOVE_FOG;
        handle = this;
        add(ES_Utiles.get_MI(AGREGAR_NIEBLA, this));
        add(ajustador_de_porcentaje = new Ajustador_de_porcentaje(Props.DENSIDAD, Graficador3D.DENSITY, this));
        ajustador_de_porcentaje.setEnabled(false);
        add(jmi_borrar_niebla = ES_Utiles.get_MI(BORRAR_NIEBLA, this));
        jmi_borrar_niebla.setEnabled(false);
    }
	//Datos_utiles
    public static void escribir_niebla(DataOutputStream dataoutputstream)
        throws IOException
    {
        if(bg == null)
        {
            dataoutputstream.writeInt(0);
        } else
        {
            dataoutputstream.writeInt(color_de_niebla.getRGB());
            dataoutputstream.writeInt(densidad);
        }
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Object obj = null;
        String s = actionevent.getActionCommand();
        if(s == BORRAR_NIEBLA)
            borrar_este();
        else
        if(s == AGREGAR_NIEBLA)
        {
            Color color = JColorChooser.showDialog(Escena.handle, AGREGAR_NIEBLA, color_de_niebla);
            Barra.handle.repaint();
            if(color == null)
                return;
            agregar_niebla(color);
        }
    }

    public static void agregar_niebla(Color color)
    {
        jmi_borrar_niebla.setEnabled(true);
        color_de_niebla = color;
        javax.vecmath.Color3f color3f = Manejador_de_archivos_temporales.getColor3f(color);
        if(bg != null)
            bg.detach();
        exponentialFog = new ExponentialFog(color3f);
        exponentialFog.setCapability(ExponentialFog.ALLOW_DENSITY_WRITE);
        exponentialFog.setInfluencingBounds(Datos_utiles.boundingSphere);
        bg = new BranchGroup();
        bg.setCapability(ExponentialFog.ALLOW_DENSITY_WRITE);
        bg.addChild(exponentialFog);
        Escena.bg.addChild(bg);
        ajustador_de_porcentaje.setEnabled(true);
    }

    public static void borrar_este()
    {
        if(bg != null)
        {
            bg.detach();
            bg = null;
            exponentialFog = null;
        }
        ajustador_de_porcentaje.setEnabled(false);
        jmi_borrar_niebla.setEnabled(false);
    }

    public Object get_Prop(int i)
    {
        switch(i)
        {
        case Props.DENSIDAD: 
            return new Integer(densidad);
        }
        return null;
    }

    public void set_Prop(int i, Object obj)
    {
        switch(i)
        {
        case Props.DENSIDAD: 
            Integer integer = (Integer)obj;
            densidad = integer.intValue();
            if(densidad == 0)
                exponentialFog.setDensity(0.0F);
            else
                exponentialFog.setDensity((float)densidad / 100F);
            break;
        }
    }

    
}