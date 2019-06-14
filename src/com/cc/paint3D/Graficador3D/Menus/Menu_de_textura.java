
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Seleccion_multiple;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.IO.Tipo_de_texto;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos;


public class Menu_de_textura extends Menu_de_seleccion_multiple
    implements Seleccion_multiple, Props
{
	static String opciones[] = {
        Graficador3D.NO_TEXTURE, Graficador3D.MODULATE, Graficador3D.BLEND, Graficador3D.REPLACE, Graficador3D.DECAL
    };
	
    public Menu_de_textura()
    {
        super(Graficador3D.TEXTURE, opciones, null);//"Texture"
        set_Seleccion_multiple(this);
        Tipo_de_texto tipo_de_texto = (Tipo_de_texto)Escena.get_actual().get_Prop(Props.TEXTURA);//6
        if(tipo_de_texto != null)
            set_estado(tipo_de_texto.tipo);
            
        
    }

    public void llamada_multiple(Menu_de_seleccion_multiple menu_de_seleccion_multiple, int i)
    {
        Tipo_componente tipo_componente = Escena.get_actual();
        if(i != 0)
        {
            if(super.anterior == 0)
            {
                java.io.File file = Manejador_de_archivos.get_buscador_de_imagen(Graficador3D.CHOOSE_TEXTURE_IMAGE);
                if(file == null)
                {
                    set_estado_anterior();
                    return;
                }
                tipo_componente.set_Prop(Props.TEXTURA, new Tipo_de_texto(file, i));//6
            } else
            {
                Tipo_de_texto tipo_de_texto = (Tipo_de_texto)tipo_componente.get_Prop(Props.TEXTURA);//6
                tipo_de_texto.tipo = i;
                tipo_componente.set_Prop(Props.TEXTURA, tipo_de_texto);//6
            }
        } else
        {
            tipo_componente.set_Prop(Props.TEXTURA, null);//6
        }
    }

    

}