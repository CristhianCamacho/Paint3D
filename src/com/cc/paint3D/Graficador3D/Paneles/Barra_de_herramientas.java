
package com.cc.paint3D.Graficador3D.Paneles;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.Barra;
import com.cc.paint3D.Graficador3D.Botones.Boton_Flash;

import com.cc.paint3D.Graficador3D.IO.Props_de_sistema;
import com.cc.paint3D.Graficador3D.IO.Distribuidor_de_Archivos;

import com.cc.paint3D.Graficador3D.Menus.Menu_de_Figura;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_propiedades_de_apariencia;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_propiedades_de_comportamiento;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_borrar;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_Ver_Ejecutando;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_Editar_Preferencias;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_iluminacion;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_de_niebla;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_ambiente;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_de_fondo;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_fondo;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.*;


public class Barra_de_herramientas extends JPanel
    implements ActionListener
{
	
	static Dimension DIM = new Dimension(32, 32);
    String NUEVO;
    String ABRIR;
    String GUARDAR;
    String COMPONENTE;
    String PROPS_APARIENCIA;
    String PROPS_COMPORTAMIENTO;
    
    String BORRAR;
    String EJECUTAR;
    String PREFS;
    static Barra_de_herramientas handle;
    static Boton_Flash prop_apariencia;
    static Boton_Flash prop_comportamiento;
    static Boton_Flash ejecutar;
    
    static JPopupMenu menu_actual;
	
    public Barra_de_herramientas()
    {
        NUEVO = Graficador3D.NEW_ICON;
        ABRIR = Graficador3D.OPEN_ICON;
        GUARDAR = Graficador3D.SAVE_ICON;
        COMPONENTE = Graficador3D.COMPONENT_ICON;
        PROPS_APARIENCIA = Graficador3D.PROPS_ICON;
        PROPS_COMPORTAMIENTO = Graficador3D.PROPS2_ICON;
        BORRAR = Graficador3D.DELETE_ICON;
        EJECUTAR = Graficador3D.RUN_ICON;
        PREFS = Graficador3D.PREFS_ICON;
        

        handle = this;
        setLayout(new FlowLayout(0, 0, 0));
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new FlowLayout(0, 3, 0));

        agregar_Boton_Flash(NUEVO, "Iniciando una nueva distribucion", jpanel);
        agregar_Boton_Flash(ABRIR, "Abriendo una distribucion existente", jpanel);
        agregar_Boton_Flash(GUARDAR, "Salvando la distribucion actual", jpanel);
        agregar_Boton_Flash(COMPONENTE, "Seleccione un nuevo componente", jpanel);
        jpanel.setBorder(BorderFactory.createEtchedBorder());
        add(jpanel);
        JPanel jpanel1 = new JPanel();
        jpanel1.setLayout(new FlowLayout(0, 3, 0));
        jpanel1.setBorder(BorderFactory.createEtchedBorder());
        prop_apariencia = agregar_Boton_Flash(PROPS_APARIENCIA, "fijando las propiedades del componente", jpanel1);
        prop_comportamiento = agregar_Boton_Flash(PROPS_COMPORTAMIENTO, "fijando el comportamiento", jpanel1);
        add(jpanel1);
        JPanel jpanel2 = new JPanel();
        jpanel2.setLayout(new FlowLayout(0, 3, 0));
        jpanel2.setBorder(BorderFactory.createEtchedBorder());
        ejecutar = agregar_Boton_Flash(EJECUTAR, "Ver la distribucion actual", jpanel2);
        ejecutar.setRepeat(true);
        agregar_Boton_Flash(BORRAR, "Borrar componente", jpanel2);
        agregar_Boton_Flash(PREFS, "Editar preferencias", jpanel2);
        add(jpanel2);

        
        set_no_editable();
    }

    public static void set_no_editable()
    {
        prop_apariencia.setEnabled(false);
        prop_comportamiento.setEnabled(false);
    }

    public static void set_si_editable()
    {
        prop_apariencia.setEnabled(true);
        prop_comportamiento.setEnabled(true);
    }

    public static void esconder_menu()
    {
        if(menu_actual != null)
            menu_actual.setVisible(false);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        esconder_menu();
        if(s == NUEVO)
            nueva_distribucion();
        else
        if(s == ABRIR)
            Distribuidor_de_Archivos.abrir_HTML();
        else
        if(s == GUARDAR)
            Distribuidor_de_Archivos.guardar_todo(false);//true
        else
        if(s == COMPONENTE)
            showPopup(actionevent, Menu_de_Figura.handle != null ? ((JPopupMenu) (Menu_de_Figura.handle)) : ((JPopupMenu) (new Menu_de_Figura())));
        else
        if(s == PROPS_APARIENCIA)
            showPopup(actionevent, Menu_de_propiedades_de_apariencia.handle);
        else
        if(s == PROPS_COMPORTAMIENTO)
            showPopup(actionevent, Menu_de_propiedades_de_comportamiento.handle);
        else
        if(s == BORRAR)
            showPopup(actionevent, Menu_de_borrar.handle != null ? ((JPopupMenu) (Menu_de_borrar.handle)) : ((JPopupMenu) (new Menu_de_borrar())));
        else
        if(s == EJECUTAR)
            Dialogo_de_Ver_Ejecutando.ejecutar();
        else
        if(s == PREFS)
            new Dialogo_de_Editar_Preferencias();
    }

    public static void nueva_distribucion()
    {
        Escena.borrar_todo();
        Menu_de_iluminacion.borrar_todas_las_luces();
        Menu_de_color_de_niebla.borrar_este();
        Menu_de_color_ambiente.borrar_este();
        Menu_de_color_de_fondo.borrar_este();
        Menu_de_fondo.borrar_fondo();
        Menu_de_color_de_fondo.agregar_Color( new Color(200,200,200) );//color inicial del fondo
        Barra.set_sin_titulo();
        Distribuidor_de_Archivos.actual_archivo_generado = null;
        System.gc();
    }

    public void showPopup(ActionEvent actionevent, JPopupMenu jpopupmenu)
    {
        if(jpopupmenu.isVisible())
            return;
        menu_actual = jpopupmenu;
        Component component = (Component)actionevent.getSource();
        Container container = component.getParent();
        if(container.isAncestorOf(jpopupmenu))
        {
            jpopupmenu.setVisible(true);
        } else
        {
            Point point = component.getLocation();
            Dimension dimension = component.getSize();
            container.add(jpopupmenu);
            jpopupmenu.show(container, point.x, point.y + dimension.height);
        }
    }

    public Boton_Flash agregar_Boton_Flash(String s, String s1, JPanel jpanel)
    {
        java.awt.Image image = ES_Utiles.get_System_Image(s);
        Boton_Flash boton_flash = new Boton_Flash(image, s1, DIM, this, s);
        jpanel.add(boton_flash);
        return boton_flash;
    }

    

}