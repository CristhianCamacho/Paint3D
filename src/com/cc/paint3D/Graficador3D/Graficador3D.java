package com.cc.paint3D.Graficador3D;

import com.cc.paint3D.Graficador3D.ES.Error_NO_java3D;
import com.cc.paint3D.Graficador3D.IO.Archivos_recientes;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos_temporales;
import com.cc.paint3D.Graficador3D.IO.Props_de_sistema;
import com.cc.paint3D.Graficador3D.Paneles.Panel_Principal;
import com.cc.paint3D.Graficador3D.Utiles.Ventana_de_espera_G3D;

import javax.swing.*;
import java.awt.*;

public class Graficador3D extends Valores_idioma
{

    public static Dimension screensize;
    public static Barra barra;
    public static Escena escena;

    public static Ventana_de_espera_G3D ventana_de_espera_G3D;//publico para las fuentes de Menu_de_fuentes

    public Graficador3D()
    {
        screensize = Toolkit.getDefaultToolkit().getScreenSize();
        /*Ventana_de_espera_G3D*/ ventana_de_espera_G3D = new Ventana_de_espera_G3D(screensize,new Dimension( 500+(int)(Math.random()*200) ,300+(int)(Math.random()*400) ) );

        try
		{
            //UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		    //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        }
		catch (Exception e)
		{
		    JOptionPane.showMessageDialog(null,"Error al intentar cargar L&F");
		}

        Class class1;
        try
        {
            class1 = Class.forName("javax.media.j3d.Transform3D");
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            new Error_NO_java3D(screensize);
            return;
        }

        Props_de_sistema.set_Directorios();
        if(!Props_de_sistema.existe())
            iniciar_todo();
        else
        if(!Props_de_sistema.set_Propiedades())
        {
            iniciar_todo();
        } else
        {
            barra = new Barra();
            escena = new Escena();

            barra.setLocation(Props_de_sistema.get_location_Barra());
            barra.setVisible(true);
            escena.setLocation(Props_de_sistema.get_location_Escena());
            escena.pack();
            Escena.checkSize();
            escena.setVisible(true);
            Escena.setOrig();
            Panel_Principal.prefdim = null;
            Archivos_recientes.cargar_Archivos_recientes();
        }
        Barra.set_sin_titulo();
        Manejador_de_archivos_temporales.borrar_archivos_temporales();
        ventana_de_espera_G3D.setVisible(false);
    }

    static void iniciar_todo()
    {
        barra = new Barra();

        Manejador_de_archivos.directorio_escojido = Props_de_sistema.DIR_ACTUAL;
        Manejador_de_archivos.directorio_applet = Props_de_sistema.DIR_DE_APPLETS;

        System.out.println("LD Library Path:" + System.getProperty("java.library.path"));

        Panel_Principal.prefdim = new Dimension( barra.getSize().width , (2 * screensize.height) / 3);
        escena = new Escena();
        escena.pack();
        Panel_Principal.prefdim = null;

        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        barra.setLocation( (int) ((dimension.width-barra.getSize().width)/2) ,
                (int) ((dimension.height-(barra.getSize().height+escena.getSize().height))/2) );

        escena.setVisible(true);
        barra.setVisible(true);


        escena.setLocation(barra.getLocation().x, barra.getLocation().y + barra.getSize().height );

        Archivos_recientes.cargar_Archivos_recientes();
    }

    public static void main(String args[])
    {
        new Graficador3D();
    }

    public static void cerrar_Programa()
    {
        Props_de_sistema.escribir_Props();
        Manejador_de_archivos_temporales.borrar_archivos_temporales();
        System.exit(0);
    }

}