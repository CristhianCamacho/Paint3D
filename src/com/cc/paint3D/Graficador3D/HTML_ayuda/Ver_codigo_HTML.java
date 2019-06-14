
package com.cc.paint3D.Graficador3D.HTML_ayuda;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.ES.ES_Dialogo;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;
import com.cc.paint3D.Graficador3D.IO.Distribuidor_de_Archivos;
import com.cc.paint3D.Graficador3D.G3D.Archivo_generado;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.*;
import java.net.URL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.JTextComponent;



public class Ver_codigo_HTML extends ES_Dialogo
    implements ActionListener
{
    public String DESHACER_CAMBIOS;
    public String SALVAR;
    public String CANCELAR;
    public JEditorPane ta=null;
    public String html_actual;
    
     
    public Archivo_generado archivo_generado;    

    public Ver_codigo_HTML()
    {
        super("Contenido del Archivo  "+"'" + Distribuidor_de_Archivos.actual_archivo_generado.file.getName() + "'");
        
        
        DESHACER_CAMBIOS = "Deshacer Cambios";
        SALVAR = "Salvar y Salir";
        CANCELAR = "Cancelar";
        archivo_generado = Distribuidor_de_Archivos.actual_archivo_generado;
        if(archivo_generado == null)
        {
            new Dialogo_de_error("Guarde y Nombre esta distribucion primero.");
            return;
        }
        if(!archivo_generado.file.exists())
        {
            new Dialogo_de_error("El Archivo " + archivo_generado.file.getName() + " no se encuentra");
            return;
        } else
        {
            int i = (int)archivo_generado.file.length();
            SimpleDateFormat simpledateformat = new SimpleDateFormat("MMM dd, yyyy");
            String s = simpledateformat.format(new Date(archivo_generado.file.lastModified()));
            setTitle(getTitle() + "     Ultima modificacion en " + s + '.');
            super.jp.setLayout(new BorderLayout());
            
            try{
            
            String SourceCodeloading="<html><body bgcolor=\"#ffffff\">cargando y formateando el codigo fuente, porfavor espere...</body></html>";
			ta = new JEditorPane("text/html", SourceCodeloading );
			
			ta.setPreferredSize( new Dimension(Graficador3D.barra.getSize().width , ( Graficador3D.escena.getSize().height ) ) );
				            	
            html_actual=ES_Utiles.get_File_como_String(archivo_generado.file);
        	
        	ta.setEditable(true);
        	ta.setText( loadSourceCode() );
        	
        	
        	}catch(Exception e){System.out.println("Ver_codigo_HTML:Ver_codigo_HTML:Exception= "+e);}
        	        	
            ta.setFont(new Font("Monospaced", 0, 12));
            JScrollPane jscrollpane = new JScrollPane();
            jscrollpane.getViewport().add(ta);
            super.jp.add("Center", jscrollpane);
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new FlowLayout());
            jpanel.setBorder(BorderFactory.createEtchedBorder());
            Boton_Libreria boton_libreria = new Boton_Libreria(DESHACER_CAMBIOS);
            boton_libreria.addActionListener(this);
            jpanel.add(boton_libreria);
            boton_libreria = new Boton_Libreria(SALVAR);
            boton_libreria.addActionListener(this);
            jpanel.add(boton_libreria);
            boton_libreria = new Boton_Libreria(CANCELAR);
            boton_libreria.addActionListener(this);
            jpanel.add(boton_libreria);
            super.jp.add("North", jpanel);
            dimensionar_y_mostrar();
            ta.requestFocus();
            
        }
        
        
    }
    
    public String loadSourceCode()
    {
    	String sourceCode="";
    	
	if(getResourceName() != null)
	{
	    String filename = "applets_creados/" + getResourceName() + ".html";
	    sourceCode = new String("<html><body bgcolor=\"#ffffff\"><pre>");
	    InputStream is;
	    InputStreamReader isr;
	    CodeViewer cv = new CodeViewer();
	    URL url;
	    
	    try {

		url = archivo_generado.file.toURL();
		is = url.openStream();
		isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		
		
		String line = reader.readLine();
		while(line != null) {
		    sourceCode += cv.syntaxHighlight(line) + " \n ";
		    line = reader.readLine();
		}
		sourceCode += new String("</pre></body></html>");
            } catch (Exception ex) {
                sourceCode = "no se puede cargar el Archivo: " + filename;
            }
	}

	return sourceCode;
    }
    
    public String getResourceName() {
	return archivo_generado.nombre;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(CANCELAR == actionevent.getActionCommand())
            cerrar_Dialogo();
        else
        if(SALVAR == actionevent.getActionCommand())
        {            
            cerrar_Dialogo();
        } else
        {
            ta.setText( loadSourceCode() );
            
        }
    }

}