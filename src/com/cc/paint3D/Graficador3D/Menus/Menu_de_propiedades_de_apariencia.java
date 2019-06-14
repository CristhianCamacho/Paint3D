
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPopupMenu;


public class Menu_de_propiedades_de_apariencia extends JPopupMenu
    implements ActionListener
{
	public String COLOR;
    public static Menu_de_propiedades_de_apariencia handle = new Menu_de_propiedades_de_apariencia();
    public static Menu_de_textura tm;

    public Menu_de_propiedades_de_apariencia()
    {
        COLOR = Graficador3D.SET_APPEARANCE_COLOR;
	}
    public void actionPerformed(ActionEvent actionevent)
    {
    }

    public static void set()
    {
        handle.removeAll();
        Escena.get_actual().set_Menu(handle);
    }

    

}