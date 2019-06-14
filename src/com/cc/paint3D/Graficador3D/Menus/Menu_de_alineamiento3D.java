
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Seleccion_multiple;
import com.cc.paint3D.Graficador3D.interfaces.Props;



public class Menu_de_alineamiento3D extends Menu_de_seleccion_multiple
    implements Seleccion_multiple, Props
{
	static String opciones[] = {
        Graficador3D.RIGHT , Graficador3D.DOWN , Graficador3D.UP , Graficador3D.LEFT
    };

    public Menu_de_alineamiento3D()
    {
        super(Graficador3D.ALIGMENT, opciones, null);
        set_Seleccion_multiple(this);
        Integer integer = (Integer)Escena.get_actual().get_Prop(Props.ALINEAMIENTO);
        if(integer != null)
            set_estado(integer.intValue());
    }

    public void llamada_multiple(Menu_de_seleccion_multiple menu_de_seleccion_multiple, int i)
    {
        Escena.get_actual().set_Prop(Props.ALINEAMIENTO, new Integer(i));
    }

    

}