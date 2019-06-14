
package com.cc.paint3D.Graficador3D.Paneles;

import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos_temporales;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.JTextComponent;

public class Panel_de_texto_estado extends JPanel
    implements Props
{
	static String msg_antiguo;
    static StringBuffer sb;
    static JTextField jtf_estatus;    
    
    static Color foreground_normal=new Color(220,220,220);
    static Color background_normal=new Color(100,150,200);
    static Color foreground_selected=new Color(250,75,50);
    static Color background_selected=new Color(25,25,25); 
	
    public Panel_de_texto_estado()
    {
        super(false);
        setBorder(new BevelBorder(1));
        sb = new StringBuffer(26);
        setLayout(new GridLayout(1, 1));
        jtf_estatus = new JTextField() {

            public boolean isFocusTraversable()
            {
                return false;
            }

        };
        jtf_estatus.setEditable(false);
        jtf_estatus.setBackground(Color.white);
        jtf_estatus.setFont(Manejador_de_archivos_temporales.MSG_FONT);
        jtf_estatus.setColumns(30);
        set_inicial();
        add(jtf_estatus);
                
    }

    public static void set_inicial()
    {        
    	set(" No hay Figura seleccionada",foreground_normal,background_normal);
    }

    public static void set(String s, Color color, Color color1)
    {
        jtf_estatus.setForeground(color);
        jtf_estatus.setBackground(color1);
        jtf_estatus.setText(" " + s);
    }

    public static void set_Componente(Tipo_componente tipo_componente)
    {        
    	String text=" Seleccionamos: " + (String)tipo_componente.get_Prop(Props.NOMBRE) + " ";
    	set(text,foreground_selected,background_selected);
    }
	//Menu_iluminacion
    public static void set_texto(String s, Color color, Color color1)
    {
        foreground_normal = jtf_estatus.getForeground();
        background_normal = jtf_estatus.getBackground();
        
        msg_antiguo = jtf_estatus.getText();        
        
    	set(" "+s,color,color1);
    }
	
    public static void set_antiguo()
    {
        jtf_estatus.setBackground(background_normal);
        jtf_estatus.setForeground(foreground_normal);
        jtf_estatus.setText(msg_antiguo);
    }
    
}