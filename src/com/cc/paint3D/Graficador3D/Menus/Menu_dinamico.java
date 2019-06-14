package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_pregunta;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;

import com.cc.paint3D.Graficador3D.G3D.Figura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;
import javax.swing.JDialog;

import java.util.EventObject;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.*;

public class Menu_dinamico extends JWindow
implements ActionListener, Props{
    
    public JPopupMenu jpm_todo=new JPopupMenu("");
    
    JMenuItem jmi_cortar;
    JMenuItem jmi_copiar;
    JMenuItem jmi_pegar;
    JMenuItem jmi_eliminar;
    JMenuItem jmi_propiedades;
    
        
    String CORTAR="cortar";
    String COPIAR="copiar";
    String PEGAR="pegar";
    String ELIMINAR="eliminar";
    String PROPIEDADES="Propiedades"; 
    
    String[] textos={CORTAR,COPIAR,PEGAR,ELIMINAR,PROPIEDADES};
    
    public Menu_dinamico()
    {
    	
    	
    	jmi_cortar = ES_Utiles.get_MI(textos[0], this);           
        jpm_todo.add(jmi_cortar);
    	
    	jmi_copiar = ES_Utiles.get_MI(textos[1], this);           
        jpm_todo.add(jmi_copiar);
    	
    	jmi_pegar = ES_Utiles.get_MI(textos[2], this);           
        jpm_todo.add(jmi_pegar);
    	
    	jmi_eliminar = ES_Utiles.get_MI(textos[3], this);           
        jpm_todo.add(jmi_eliminar);
    	
    	jpm_todo.addSeparator();
    	
    	jmi_propiedades = ES_Utiles.get_MI(textos[4], this);           
        jpm_todo.add(jmi_propiedades);
    	
    	    	
        getContentPane().add(jpm_todo);
        
        verificar_estado();
    	  	  		
    }
    
	public void mostrar(int x, int y)
	{
		verificar_estado();
		
		jpm_todo.setLocation(x,y);
		
		jpm_todo.setVisible(true);
        try{
        jpm_todo.show((Component)getContentPane(), x, y ); 		
		}catch(Exception e){}
		jpm_todo.setVisible(true);
		
			
	}
	
	public void verificar_estado()
	{
		if(Graficador3D.escena.get_actual()==null)
		{
			jmi_copiar.setEnabled(false);
			jmi_cortar.setEnabled(false);
			
			if(comp_cortado==null) jmi_pegar.setEnabled(false);
			else jmi_pegar.setEnabled(true);
			
			jmi_eliminar.setEnabled(false);
			jmi_propiedades.setEnabled(false);
		}
		else
		{	      
			jmi_copiar.setEnabled(true);
			jmi_cortar.setEnabled(true);
			
			if(comp_cortado==null) jmi_pegar.setEnabled(false);
			else jmi_pegar.setEnabled(true);
						
			jmi_eliminar.setEnabled(true);
			jmi_propiedades.setEnabled(true);				
		}
		
		
		
	}
	
	public void ocultar()
	{
		jpm_todo.setVisible(false);	
	}
    Tipo_componente comp_cortado=null;
    public void actionPerformed(ActionEvent actionevent)
    {
        JMenuItem jmenuitem = (JMenuItem)actionevent.getSource();
        String s = actionevent.getActionCommand();
        System.out.println("1:Menu_dinamico:actionPerformed:s= "+s);
    
    if( s.equalsIgnoreCase(CORTAR) )
    {
     comp_cortado=Graficador3D.escena.get_actual();
     Graficador3D.escena.borrar_actual();         
    }
        
    if( s.equalsIgnoreCase(COPIAR) )
    {
     comp_cortado=Graficador3D.escena.get_actual();              
    }
    
    if( s.equalsIgnoreCase(PEGAR) )
    {                 	     
     //Graficador3D.escena.addObject( comp_cortado.get_copia_de_este() , true ); 
     System.out.println("Menu_dinamico:actionPerformed:comp_cortado\n"+comp_cortado.getClass().getCanonicalName() );
     
     //Object obj = null;
        //try
        //{obj = Class.forName( comp_cortado.getClass().getCanonicalName() ).newInstance();}//"Graficador_3D.G3D." + s
        //catch(Exception exception){
        //    new Dialogo_de_error("Un error ocurrio tratando de cargar el componente seleccionado.\n"+
        //    					  " "+comp_cortado.getClass().getCanonicalName());
        //    return;
        //}
        //if(obj == null)
        //    return;
        //Tipo_componente tipo_componente = (Tipo_componente)obj;
        //tipo_componente.set_Prop( Props.NOMBRE, comp_cortado.get_Prop(Props.NOMBRE) );//22
     	
     	/*
     	try{
     	if(comp_cortado.get_Prop(Props.TEXTURA)!=null) tipo_componente.set_Prop( Props.TEXTURA, comp_cortado.get_Prop(Props.TEXTURA) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.MATERIAL)!=null) tipo_componente.set_Prop( Props.MATERIAL, comp_cortado.get_Prop(Props.MATERIAL) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.BEHAVIOUR)!=null) tipo_componente.set_Prop( Props.BEHAVIOUR, comp_cortado.get_Prop(Props.BEHAVIOUR) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.DIFUSO)!=null) tipo_componente.set_Prop( Props.DIFUSO, comp_cortado.get_Prop(Props.DIFUSO) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.ESPECULAR)!=null) tipo_componente.set_Prop( Props.ESPECULAR, comp_cortado.get_Prop(Props.ESPECULAR) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.EMISIVO)!=null) tipo_componente.set_Prop( Props.EMISIVO, comp_cortado.get_Prop(Props.EMISIVO) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.AMBIENTE)!=null) tipo_componente.set_Prop( Props.AMBIENTE, comp_cortado.get_Prop(Props.AMBIENTE) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.TEXTO)!=null) tipo_componente.set_Prop( Props.TEXTO, comp_cortado.get_Prop(Props.TEXTO) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.COLOR)!=null) tipo_componente.set_Prop( Props.COLOR, comp_cortado.get_Prop(Props.COLOR) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.APPEARANCE)!=null) tipo_componente.set_Prop( Props.APPEARANCE, comp_cortado.get_Prop(Props.APPEARANCE) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.TRANSPARENCIA)!=null) tipo_componente.set_Prop( Props.TRANSPARENCIA, comp_cortado.get_Prop(Props.TRANSPARENCIA) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.HEIGHT)!=null) tipo_componente.set_Prop( Props.HEIGHT, comp_cortado.get_Prop(Props.HEIGHT) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.WIDTH)!=null) tipo_componente.set_Prop( Props.WIDTH, comp_cortado.get_Prop(Props.WIDTH) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.X_DIVISIONES)!=null) tipo_componente.set_Prop( Props.X_DIVISIONES, comp_cortado.get_Prop(Props.X_DIVISIONES) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.Y_DIVISIONES)!=null) tipo_componente.set_Prop( Props.Y_DIVISIONES, comp_cortado.get_Prop(Props.Y_DIVISIONES) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.LENGTH)!=null) tipo_componente.set_Prop( Props.LENGTH, comp_cortado.get_Prop(Props.LENGTH) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.FONT_NOMBRE)!=null) tipo_componente.set_Prop( Props.FONT_NOMBRE, comp_cortado.get_Prop(Props.FONT_NOMBRE) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.FONT_ESTILO)!=null) tipo_componente.set_Prop( Props.FONT_ESTILO, comp_cortado.get_Prop(Props.FONT_ESTILO) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.FONT_TAMA�O)!=null) tipo_componente.set_Prop( Props.FONT_TAMA�O, comp_cortado.get_Prop(Props.FONT_TAMA�O) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.DENSIDAD)!=null) tipo_componente.set_Prop( Props.DENSIDAD, comp_cortado.get_Prop(Props.DENSIDAD) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.ALINEAMIENTO)!=null) tipo_componente.set_Prop( Props.ALINEAMIENTO, comp_cortado.get_Prop(Props.ALINEAMIENTO) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.BEHAVIOUR)!=null) tipo_componente.set_Prop( Props.BEHAVIOUR, comp_cortado.get_Prop(Props.BEHAVIOUR) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.MATERIAL)!=null) tipo_componente.set_Prop( Props.MATERIAL, comp_cortado.get_Prop(Props.MATERIAL) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.NECESITA_TEXTO)!=null) tipo_componente.set_Prop( Props.NECESITA_TEXTO, comp_cortado.get_Prop(Props.NECESITA_TEXTO) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	     	    	
     	try{
     	if(comp_cortado.get_Prop(Props.BRILLO)!=null) tipo_componente.set_Prop( Props.BRILLO, comp_cortado.get_Prop(Props.BRILLO) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	try{
     	if(comp_cortado.get_Prop(Props.FILES)!=null) tipo_componente.set_Prop( Props.FILES, comp_cortado.get_Prop(Props.FILES) );
     	}catch(Exception e){System.out.println("Menu_dinamico:actionPerformed:Exception= "+e);}
     	*/
     	
     	/*
     	tipo_componente.set_Prop( Props.NAME, comp_cortado.get_Prop(Props.NAME) );
     	tipo_componente.set_Prop( Props.NAME, comp_cortado.get_Prop(Props.NAME) );
     	tipo_componente.set_Prop( Props.NAME, comp_cortado.get_Prop(Props.NAME) );
     	tipo_componente.set_Prop( Props.NAME, comp_cortado.get_Prop(Props.NAME) );
     	tipo_componente.set_Prop( Props.NAME, comp_cortado.get_Prop(Props.NAME) );
     	tipo_componente.set_Prop( Props.NAME, comp_cortado.get_Prop(Props.NAME) );
     	tipo_componente.set_Prop( Props.NAME, comp_cortado.get_Prop(Props.NAME) );
     	*/
        //tipo_componente.final_agregar_nodo((Integer)tipo_componente.get_Prop(Props.NECESITA_TEXTO));
        
        
//        Escena.agregar_objeto( ((Figura)comp_cortado).bg.cloneTree(), true);     
    }
    
    if( s.equalsIgnoreCase(ELIMINAR) )
    {
    Graficador3D.escena.borrar_actual();  	
    } 
    
    if( s.equalsIgnoreCase(PROPIEDADES) )
    {
    Tipo_componente tipo_componente = (Tipo_componente)Graficador3D.escena.get_actual();	    
    //JDialog dialogo=new JDialog();
    //JTextField jtf=new JTextField();	
    	//dialogo.getContentPane().add(jtf);
    	String temp="";
    	temp+="Propiedades de :"+(String)tipo_componente.get_Prop(Props.NOMBRE)+"\n";
    	temp+="escala de componente x : "+(String)tipo_componente.get_Prop(Props.ESCALA_COMPONENTE_X)+"\n";
    	temp+="escala de componente y : "+(String)tipo_componente.get_Prop(Props.ESCALA_COMPONENTE_Y)+"\n";
    	temp+="escala de componente z : "+(String)tipo_componente.get_Prop(Props.ESCALA_COMPONENTE_Z)+"\n";
    	
    	//jtf.setText(temp);
    	//jtf.setPreferredSize( new Dimension(300,300));
    //dialogo.setVisible(true);
    Dialogo_de_pregunta dialogo=new Dialogo_de_pregunta(temp,"","");
    //dialogo.setUndecorated(true);
    dialogo.setBackground( Color.BLUE.darker() );   
    //dialogo.setVisible(false);
    dialogo.setVisible(true);  	
    }
             
               
    }
    
}
