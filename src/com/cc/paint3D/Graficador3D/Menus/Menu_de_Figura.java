
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_obtener_texto;
import com.cc.paint3D.Graficador3D.G3D.Figura_caja;
import com.cc.paint3D.Graficador3D.G3D.Figura_cono;
import com.cc.paint3D.Graficador3D.G3D.Figura_cubo;
import com.cc.paint3D.Graficador3D.G3D.Figura_cilindro;
import com.cc.paint3D.Graficador3D.G3D.Figura_terreno_Fractal;

import com.cc.paint3D.Graficador3D.G3D.Figura_tetra;

import com.cc.paint3D.Graficador3D.G3D.Figura;
import com.cc.paint3D.Graficador3D.G3D.Figura_esfera;
import com.cc.paint3D.Graficador3D.G3D.Figura_texto2D;
import com.cc.paint3D.Graficador3D.G3D.Figura_texto3D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.StringTokenizer;
import javax.swing.*;


public class Menu_de_Figura extends JPopupMenu
    implements ActionListener, Props
{
	
	static final int NECESITA_NADA = 0;
    static final int NECESITA_IMAGE = 1;
    static final int NECESITA_FILE = 2;
    static final int NECESITA_BMP = 3;
    static final int NECESITA_INGRESAR_TEXTO = 4;
    public static Menu_de_Figura handle;
	
    public Menu_de_Figura()
    {
        handle = this;
        JMenuItem jmenuitem = null;
        String s1 = ES_Utiles.get_SystemFile_como_String("Menus/componentes/componentes.g3d");
        if(s1 == null)
            return;
        
        for(StringTokenizer stringtokenizer = new StringTokenizer(s1, "\n:"); stringtokenizer.hasMoreElements();)
        {
            String s = stringtokenizer.nextToken();
            //if(s.equals("SEPARATOR"))
            //    addSeparator();
            //else
            if(s.equals("MSEP"))
                {
                addSeparator();
                }
            else
            if(s.equals("MENU"))
            {
                jmenuitem = new JMenu(stringtokenizer.nextToken());
                add(jmenuitem);
            } else
            {
                JMenuItem jmenuitem1 = new JMenuItem(s);
                jmenuitem1.setActionCommand(stringtokenizer.nextToken());
                jmenuitem1.addActionListener(this);
                if(jmenuitem != null)
                    jmenuitem.add(jmenuitem1);
                else
                    add(jmenuitem1);
            }
        }

    }

    public void actionPerformed(ActionEvent actionevent)
    {
        JMenuItem jmenuitem = (JMenuItem)actionevent.getSource();
        String s = actionevent.getActionCommand();
        createComponent(s, jmenuitem.getText());
    }

    public static void createComponent(String s, String s1)
    {
        Object obj = null;
        try
        {
            obj = Class.forName("com.cc.paint3D.Graficador3D.G3D." + s).newInstance();
        }
        catch(Exception exception)
        {
            new Dialogo_de_error("Un error ocurrio tratando de cargar el componente seleccionado.\n"+
            					  " "+s);
            return;
        }
        if(obj == null)
            return;
            
        //obj.setBounds();
            
        Tipo_componente tipo_componente = (Tipo_componente)obj;
        tipo_componente.set_Prop(Props.NOMBRE, s1);
        String s2 = null;
        Integer integer = (Integer)tipo_componente.get_Prop(Props.NECESITA_TEXTO);
        switch(integer.intValue())
        {
        case NECESITA_INGRESAR_TEXTO: 
            String s3 = Dialogo_de_obtener_texto.preguntando("Ingrese el texto");
            if(s3 == null)
                return;
            s2 = s3;
            break;
        }
        tipo_componente.final_agregar_nodo(s2);
        Escena.agregar_objeto(obj, true);
    }

    
}