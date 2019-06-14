
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.IO.Distribuidor_de_Archivos;
import com.cc.paint3D.Graficador3D.G3D.Archivo_generado;
import com.cc.paint3D.Graficador3D.IO.Prefs;
import com.cc.paint3D.Graficador3D.HTML_ayuda.Ver_codigo_HTML;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenu;


class Menu_de_ver_HTML extends JMenu
    implements ActionListener
{
	String VISTA_HTML_FUENTE;    
	
    Menu_de_ver_HTML()
    {
        super(Graficador3D.VIEW);
        VISTA_HTML_FUENTE = Graficador3D.HTML_SOURCE;
        
        add(ES_Utiles.get_MI(VISTA_HTML_FUENTE, this));
        
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        if(s == VISTA_HTML_FUENTE)
        {
            if(Distribuidor_de_Archivos.actual_archivo_generado != null)
                new Ver_codigo_HTML();
            else
                new Dialogo_de_error(Graficador3D.OPEN_A_SAVED_LAYOUT_FILE_FIRST);//"Open a saved layout file first."
        }
        
    }

    
}