
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.interfaces.Selector;

public class Llamando_al_Menu_de_seleccion extends Menu_de_seleccion_doble
{	
	Selector selector;

    public Llamando_al_Menu_de_seleccion(String s, String s1, String s2, Selector selector1)
    {
        super(s, s1, s2);
        selector = selector1;
    }

    public Llamando_al_Menu_de_seleccion(String s, String s1, String s2)
    {
        super(s, s1, s2);
    }

    public void setSelector(Selector selector1)
    {
        selector = selector1;
    }

    public void hacer_llamada()
    {
        selector.llamada(this);
    }

    
}