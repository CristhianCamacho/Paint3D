
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.Utiles.Icono_Cuadrado;
import com.cc.paint3D.Graficador3D.Paneles.Barra_de_herramientas;
import com.cc.paint3D.Graficador3D.IO.Distribuidor_de_Archivos;

import com.cc.paint3D.Graficador3D.Barra;
import com.cc.paint3D.Graficador3D.Escena;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.*;

import java.awt.Font;

import javax.swing.*;
import javax.swing.border.*;


public class Barra_de_Menus extends JMenuBar
    implements ActionListener, MouseListener ,MouseMotionListener
{
	
	Barra barra;
	
	
	static String colores[] = {
        Graficador3D.BLACK, Graficador3D.DARK_GRAY, Graficador3D.LIGHT_GRAY, Graficador3D.DARK_BLUE, Graficador3D.BLUE, Graficador3D.CYAN, Graficador3D.MAGENTA, Graficador3D.DARK_RED, Graficador3D.RED, Graficador3D.DARK_GREEN, 
        Graficador3D.GREEN, Graficador3D.DARK_YELLOW, Graficador3D.YELLOW, Graficador3D.ORANGE, Graficador3D.PINK, Graficador3D.WHITE
    };
    //{
    //    "Black", "Dark gray", "Light Gray", "Dark blue", "Blue", "Cyan", "Magenta", "Dark red", "Red", "Dark green", 
    //    "Green", "Dark yellow", "Yellow", "Orange", "Pink", "White"
    //};
    static String NUEVA_DISTRIBUCION = Graficador3D.CREATE_NEW_LAYOUT;//"Create new layout";
    static String ABRIR = Graficador3D.OPEN_EXISTING_LAYOUT;//"Open existing layout"
    static String GUARDAR = Graficador3D.SAVE;//"Save";
    static String GUARDAR_COMO = Graficador3D.SAVE_AS;//"Save as..."
    //static String SAVE_AS_IMAGE = Graficador3D.SAVE_AS_IMAGE;//"Save as image"
    public static JMenu file;
    static JPopupMenu properties;
    static Color darkRed = new Color(204, 0, 102);
    static Color darkGreen = new Color(0, 153, 51);
    static Color darkBlue = new Color(51, 0, 102);
    static Color lightGray = new Color(204, 204, 204);
    static Color darkGray = new Color(102, 102, 102);
    static Color darkYellow = new Color(204, 153, 51);
    static Color orange = new Color(255, 204, 102);
    static Color pink = new Color(255, 153, 153);
    
    
    
    Font font_seleccionado = new Font("Arial", Font.BOLD, 14);
    Color color_font_seleccionado=Color.black;
    Font font_normal = new Font("Arial", Font.TYPE1_FONT, 14);;
    Color color_font_normal=Color.GRAY;
    
    Menu_de_ver_HTML menu_de_ver_HTML;
    Menu_de_fondo menu_de_fondo;
    Menu_de_iluminacion menu_de_iluminacion;
    Menu_de_opciones_de_escala menu_de_opciones_de_escala;
    Menu_de_ayuda menu_de_ayuda;
    
    ////////////////////MASCARAS///////////////////////////
	/*
	private static String MASCARA_1 = "Mascaras/Skin-LF-aqua.theme";
	private static String MASCARA_2 = "Mascaras/Skin-LF-beos.theme";
	private static String MASCARA_3 = "Mascaras/Skin-LF-cellshaded.theme";
	private static String MASCARA_4 = "Mascaras/Skin-LF-macos.theme";
	private static String MASCARA_5 = "Mascaras/Skin-LF-modern.theme";
	private static String MASCARA_6 = "Mascaras/Skin-LF-matrix.theme";
	private static String MASCARA_7 = "Mascaras/Skin-LF-toxic.theme";
	private static String MASCARA_8 = "Mascaras/Skin-LF-whistler.theme";
	private static String MASCARA_9 = "Mascaras/Skin-LF-xpluna.theme";
	public static String MASCARA_DEFECTO = MASCARA_7;
	
	JMenuItem jmi_MASCARA_1,jmi_MASCARA_2,jmi_MASCARA_3,jmi_MASCARA_4,jmi_MASCARA_5,jmi_MASCARA_6,jmi_MASCARA_7,jmi_MASCARA_8,jmi_MASCARA_9,jmi_MASCARA_DEFECTO;
	*/
	////////////////////////////////////////////////////////
	
	
    public Barra_de_Menus(Barra b)
    {
        barra=b;
        crearInicito();
        
        file = new JMenu(Graficador3D.FILE);//"File   "
        file.add(ES_Utiles.get_MI(NUEVA_DISTRIBUCION, this));
        file.add(ES_Utiles.get_MI(ABRIR, this));
        file.add(ES_Utiles.get_MI(GUARDAR, this));
        file.add(ES_Utiles.get_MI(GUARDAR_COMO, this));
        
        file.addActionListener(this);
        file.addMouseListener(this);file.setFont(font_normal);file.setForeground(color_font_normal);
        file.addMouseMotionListener(this);
////////////////////////////////////////////        
        //EtchedBorder c= new EtchedBorder();
       	//file.setBorder(c);
        add(file);
////////////////////////////////////////////        
        add(menu_de_ver_HTML = new Menu_de_ver_HTML());/*viewmenu.setBorder(c);*/menu_de_ver_HTML.addMouseListener(this);menu_de_ver_HTML.setFont(font_normal);menu_de_ver_HTML.setForeground(color_font_normal);
        add(menu_de_fondo = new Menu_de_fondo());/*backgroundmenu.setBorder(c);*/menu_de_fondo.addMouseListener(this);menu_de_fondo.setFont(font_normal);menu_de_fondo.setForeground(color_font_normal);
        add(menu_de_iluminacion = new Menu_de_iluminacion());/*lightsmenu.setBorder(c);*/menu_de_iluminacion.addMouseListener(this);menu_de_iluminacion.setFont(font_normal);menu_de_iluminacion.setForeground(color_font_normal);
        /*
        add(menu_de_configuracion = new Menu_de_configuracion());menu_de_configuracion.addMouseListener(this);menu_de_configuracion.setFont(font_normal);menu_de_configuracion.setForeground(color_font_normal);
        */
        add(menu_de_opciones_de_escala = new Menu_de_opciones_de_escala());/*optionsmenu.setBorder(c);*/menu_de_opciones_de_escala.addMouseListener(this);menu_de_opciones_de_escala.setFont(font_normal);menu_de_opciones_de_escala.setForeground(color_font_normal);
        add(menu_de_ayuda = new Menu_de_ayuda());/*helpmenu.setBorder(c);*/menu_de_ayuda.addMouseListener(this);menu_de_ayuda.setFont(font_normal);menu_de_ayuda.setForeground(color_font_normal);
        
        
        
        
        
        barra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//setLookAndFeel(MASCARA_DEFECTO);
		
		barra.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

		/*
		JMenu m4= new JMenu("Mascaras");

		jmi_MASCARA_1 = new JMenuItem(" Aqua ");
		jmi_MASCARA_2 = new JMenuItem(" Beigeazul ");
		jmi_MASCARA_3 = new JMenuItem(" Cellshaded ");
		jmi_MASCARA_4 = new JMenuItem(" Macos ");
		jmi_MASCARA_5 = new JMenuItem(" Modern ");
		jmi_MASCARA_6 = new JMenuItem(" Matrix ");
		jmi_MASCARA_7 = new JMenuItem(" Toxic ");
		jmi_MASCARA_8 = new JMenuItem(" Whistler ");
		jmi_MASCARA_9 = new JMenuItem(" XP-luna ");
		
		jmi_MASCARA_1 .addActionListener(this);
		jmi_MASCARA_2 .addActionListener(this);
		jmi_MASCARA_3 .addActionListener(this);
		jmi_MASCARA_4 .addActionListener(this);
		jmi_MASCARA_5 .addActionListener(this);
		jmi_MASCARA_6 .addActionListener(this);
		jmi_MASCARA_7 .addActionListener(this);
		jmi_MASCARA_8 .addActionListener(this);
		jmi_MASCARA_9 .addActionListener(this);
		
		m4.add(jmi_MASCARA_1);
		m4.add(jmi_MASCARA_2);
		m4.add(jmi_MASCARA_3);
		m4.add(jmi_MASCARA_4);
		m4.add(jmi_MASCARA_5);
		m4.add(jmi_MASCARA_6);
		m4.add(jmi_MASCARA_7);
		m4.add(jmi_MASCARA_8);
		m4.add(jmi_MASCARA_9);
		
		add(m4);
        */
    }
	
	public void crearInicito()
    {
    	EtchedBorder c= new EtchedBorder();
    	JMenu inicito=new JMenu("");
    	inicito.setBorder(c);
    	inicito.setEnabled(false);
    	add(inicito);
    }	
	//Menu_de_color_de_fondo
    public static void agregar_Colores(JMenu jmenu)
    {
        int i = colores.length;
        for(int j = 0; j < i; j++)
        {
            JMenuItem jmenuitem = new JMenuItem(colores[j]);
            
                                    
            jmenuitem.setIcon(new Icono_Cuadrado(get_Color(String.valueOf(j))));
            jmenuitem.addActionListener((ActionListener)jmenu);
            jmenuitem.setActionCommand(String.valueOf(j));
            jmenu.add(jmenuitem);
        }

    }

    public static Color get_Color(String s)
    {
        switch(Integer.parseInt(s))
        {
        case 0: // '\0'
            return Color.black;

        case 1: // '\001'
            return darkGray;

        case 2: // '\002'
            return lightGray;

        case 3: // '\003'
            return darkBlue;

        case 4: // '\004'
            return Color.blue;

        case 5: // '\005'
            return Color.cyan;

        case 6: // '\006'
            return Color.magenta;

        case 7: // '\007'
            return darkRed;

        case 8: // '\b'
            return Color.red;

        case 9: // '\t'
            return darkGreen;

        case 10: // '\n'
            return Color.green;

        case 11: // '\013'
            return darkYellow;

        case 12: // '\f'
            return Color.yellow;

        case 13: // '\r'
            return orange;

        case 14: // '\016'
            return pink;

        case 15: // '\017'
            return Color.white;
        }
        return null;
    }
//ActionListener
    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        Barra_de_herramientas.set_no_editable();
        if(s == NUEVA_DISTRIBUCION)
            Barra_de_herramientas.nueva_distribucion();
        else
        if(s == ABRIR)
            Distribuidor_de_Archivos.abrir_HTML();
        else
        if(s == GUARDAR)
            Distribuidor_de_Archivos.guardar_todo(false);//false
        else
        if(s == GUARDAR_COMO)
            Distribuidor_de_Archivos.guardar_todo(true);
       //else
        //if(s == SAVE_AS_IMAGE)
            //{
            //JMenu temporal=new JMenu();
            //jm_save_as_image.add(temporal);
            //Utiles_de_Imagen iu=new Utiles_de_Imagen( jm_save_as_image );	
            //iu.run();//LayoutFile.saveAll(true);//en construccion    
        	//}
        	
        	
       //////////////////////////////////////////////////////////////////////// 	
       ActionEvent e=actionevent;
        	
       Object oo = e.getSource();

       /*
		if( oo == jmi_MASCARA_1)	{setLookAndFeel(MASCARA_1);}
		if( oo == jmi_MASCARA_2)	{setLookAndFeel(MASCARA_2);}
		if( oo == jmi_MASCARA_3)	{setLookAndFeel(MASCARA_3);}
		if( oo == jmi_MASCARA_4)	{setLookAndFeel(MASCARA_4);}
		if( oo == jmi_MASCARA_5)	{setLookAndFeel(MASCARA_5);}
		if( oo == jmi_MASCARA_6)	{setLookAndFeel(MASCARA_6);}
		if( oo == jmi_MASCARA_7)	{setLookAndFeel(MASCARA_7);}
		if( oo == jmi_MASCARA_8)	{setLookAndFeel(MASCARA_8);}
		if( oo == jmi_MASCARA_9)	{setLookAndFeel(MASCARA_9);}
		*/
		 	
    }

	public void setLookAndFeel(String SKIN)
	{
		
		try{
			barra.handle.setLookAndFeel(SKIN);
			Escena.handle.setLookAndFeel(SKIN);
		
			
		}catch(Exception e){ 
			System.err.println(e);
		}
		
		
		try{
			//Skin skin = SkinLookAndFeel.loadThemePack(SKIN);
			//SkinLookAndFeel.setSkin(skin);
			//SkinLookAndFeel.enable();
			
		}catch(Exception e){ 
			System.err.println(e);
		}
		
		SwingUtilities.updateComponentTreeUI(this);

	}
//MouseListener
	public void mouseClicked(MouseEvent e)
	{
		//System.out.println ( "mouseClicked \n   "+e.getSource() );
		//file.setForeground(Color.blue);
	}
	
	public void mouseEntered(MouseEvent e)
	{
	//	System.out.println ( "mouseEntered \n   "+ e.getSource() );
		//EtchedBorder c= new EtchedBorder();       	
		
		if( e.getSource().equals(file) )
		{
			file.setForeground(color_font_seleccionado);
			file.setFont(font_seleccionado);
			//file.setBorder(null);
			
			//file.setBorder(c);
		}
		if( e.getSource().equals(menu_de_ver_HTML) )
		{
			menu_de_ver_HTML.setForeground(color_font_seleccionado);
			menu_de_ver_HTML.setFont(font_seleccionado);
			//viewmenu.setBorder(null);
			
			//menu_de_ver_HTML.setBorder(c);
		}
		if( e.getSource().equals(menu_de_fondo) )
		{
			menu_de_fondo.setForeground(color_font_seleccionado);
			menu_de_fondo.setFont(font_seleccionado);
			//backgroundmenu.setBorder(null);
			
			//menu_de_fondo.setBorder(c);
		}
		if( e.getSource().equals(menu_de_iluminacion) )
		{
			menu_de_iluminacion.setForeground(color_font_seleccionado);
			menu_de_iluminacion.setFont(font_seleccionado);
			//lightsmenu.setBorder(null);
			
			//menu_de_iluminacion.setBorder(c);
		}
		
		if( e.getSource().equals(menu_de_opciones_de_escala) )
		{
			menu_de_opciones_de_escala.setForeground(color_font_seleccionado);
			menu_de_opciones_de_escala.setFont(font_seleccionado);
			//optionsmenu.setBorder(null);
			
			//optionsmenu.setBorder(c);
		}
		if( e.getSource().equals(menu_de_ayuda) ) 
		{
			menu_de_ayuda.setForeground(color_font_seleccionado);
			menu_de_ayuda.setFont(font_seleccionado);
			//helpmenu.setBorder(null);
			
			//menu_de_ayuda.setBorder(c);
		} 
		 
		 
		 
			
	}
	public void mouseExited(MouseEvent e)
	{
		if( e.getSource().equals(file) )
		{		
			file.setForeground(color_font_normal);
			file.setFont(font_normal);
			
			//file.setBorder(null);
		}
		if( e.getSource().equals(menu_de_ver_HTML) )
		{
			menu_de_ver_HTML.setForeground(color_font_normal);
			menu_de_ver_HTML.setFont(font_normal);
			
			//menu_de_ver_HTML.setBorder(null);
		}
		
		if( e.getSource().equals(menu_de_fondo) )
		{
			menu_de_fondo.setForeground(color_font_normal);
			menu_de_fondo.setFont(font_normal);
			
			//menu_de_fondo.setBorder(null);
		}
		if( e.getSource().equals(menu_de_iluminacion) )
		{
			menu_de_iluminacion.setForeground(color_font_normal);
			menu_de_iluminacion.setFont(font_normal);
			
			//menu_de_iluminacion.setBorder(null);
		}
	
		if( e.getSource().equals(menu_de_opciones_de_escala) )
		{
			menu_de_opciones_de_escala.setForeground(color_font_normal);
			menu_de_opciones_de_escala.setFont(font_normal);
			
			//optionsmenu.setBorder(null);
		}
		if( e.getSource().equals(menu_de_ayuda) ) 
		{
			menu_de_ayuda.setForeground(color_font_normal);
			menu_de_ayuda.setFont(font_normal);
			
			//menu_de_ayuda.setBorder(null);
		} 
		
		//System.out.println ( "mouseExited \n   "+ e.getSource() );
			
	}
    public void mousePressed(MouseEvent e)
    {    	
    	//System.out.println ( "mousePressed \n   "+ e.getSource() );
    }
    
    public void mouseReleased(MouseEvent e)
    {
    	//System.out.println ( "mouseReleased \n   "+ e.getSource() );
    }
    
	//MouseMotionListener
	public void mouseMoved(MouseEvent e)
	{
		//System.out.println ( "mouseMoved \n   "+ e.getSource() );
	}
	
	public void mouseDragged(MouseEvent e)
	{
		//System.out.println ( "mouseDragged \n   "+ e.getSource() );
	}








    

}