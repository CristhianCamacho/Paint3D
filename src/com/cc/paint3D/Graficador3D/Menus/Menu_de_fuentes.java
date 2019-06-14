
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Seleccion_multiple;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_Entero;

import javax.swing.JMenu;
import java.awt.GraphicsEnvironment;
import java.awt.Font;

public class Menu_de_fuentes extends JMenu
    implements Seleccion_multiple, Props
{
	public String names[]= {
        "Helvetica", "TimesRoman", "Courier" ,
        //"Adler" , "Alexei" , "Angerthas Moria" ,
        //"AngloSaxon Runes" , "AnneBoleynSH" , "Arabic 11 BT" ,
        //"Arial" , "Arial Black" , "Arial Black Italic"
    };
    
    Font[] todas_las_fuentes=Graficador3D.ventana_de_espera_G3D.todas_las_fuentes;
    
    String styles[] = {
        "Plain", "Bold", "Italic", "Bold/Italic" 
    };
    int styleint[] = {
        0, 1, 2, 3
    };
    Menu_de_seleccion_multiple fname;
    Menu_de_seleccion_multiple fstyle;

    public Menu_de_fuentes()
    {
        super(Graficador3D.FONT);
        
        names=llenar_nombres_de_fonts();
        
        Tipo_componente tipo_componente = Escena.get_actual();
        add(fname = new Menu_de_seleccion_multiple(Graficador3D.FONT_NAME, names, this));
        
       
        fname.set_estado(ES_Utiles.get_Pos_Menu_de_fuentes(names, (String)tipo_componente.get_Prop(Props.FONT_NOMBRE)));
        add(fstyle = new Menu_de_seleccion_multiple(Graficador3D.STYLE, styles, this));
        fstyle.set_estado(ES_Utiles.get_Pos_Menu_de_fuentes(styleint, (Integer)tipo_componente.get_Prop(Props.FONT_ESTILO)));
        add(new Ajustador_de_Entero(Props.FONT_TAMANIO, Graficador3D.FONT_SIZE, 1, 1000));
    }
	
	public String[] llenar_nombres_de_fonts()
	{
	
	int n=todas_las_fuentes.length+3;	
	String[] names_temp=new String[n];
			 names_temp[0]=names[0];
			 names_temp[1]=names[1];
			 names_temp[2]=names[2];
		for (int i = 3; i<n ; i++)
		{
			names_temp[i]=todas_las_fuentes[i-3].getName();	
			System.out.println("Menu_de_fuentes:llenar_nombres_de_fonts:=names_temp["+i+"]= "+names_temp[i]);
		}
	return names_temp;	
	}
	
    public void llamada_multiple(Menu_de_seleccion_multiple menu_de_seleccion_multiple, int i)
    {
        Tipo_componente tipo_componente = Escena.get_actual();
        if(menu_de_seleccion_multiple == fname)
            tipo_componente.set_Prop(Props.FONT_NOMBRE, names[fname.get_estado()]);
        else
        if(menu_de_seleccion_multiple == fstyle)
            tipo_componente.set_Prop(Props.FONT_ESTILO, new Integer(styleint[fstyle.get_estado()]));
    }

    
}