
package com.cc.paint3D.Graficador3D.Menus;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.Paneles.Barra_de_herramientas;
import com.cc.paint3D.Graficador3D.IO.Distribuidor_de_Archivos;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_pregunta;
import com.cc.paint3D.Graficador3D.G3D.Archivo_generado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JPopupMenu;

public class Menu_de_borrar extends JPopupMenu
    implements ActionListener
{
	String BORRAR_COMPONENTE;
    String BORRAR_TODO;
    String BORRAR_ARCHIVOS;
    public static Menu_de_borrar handle;
	
    public Menu_de_borrar()
    {
        BORRAR_COMPONENTE = Graficador3D.REMOVE_CURRENT_COMPONENT;
        BORRAR_TODO = Graficador3D.REMOVE_ALL_COMPONENTS_IN_LAYOUT;
        BORRAR_ARCHIVOS = Graficador3D.DELETE_SAVED_LAYOUT_FILE;
        handle = this;
        add(ES_Utiles.get_MI(BORRAR_COMPONENTE, this));
        add(ES_Utiles.get_MI(BORRAR_TODO, this));
        addSeparator();
        add(ES_Utiles.get_MI(BORRAR_ARCHIVOS, this));
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        Barra_de_herramientas.set_no_editable();
        if(s == BORRAR_COMPONENTE)
            Escena.borrar_actual();
        else
        if(s == BORRAR_TODO)
            Escena.borrar_todo();
        else
        if(s == BORRAR_ARCHIVOS)
        {
            if(Distribuidor_de_Archivos.actual_archivo_generado == null)
            {
                new Dialogo_de_error(Graficador3D.FIRST_OPEN_THE_HTML_FILE_TO_DELETE);
                return;
            }
            Archivo_generado archivo_generado = Distribuidor_de_Archivos.actual_archivo_generado;
            if(!Dialogo_de_pregunta.preguntando(Graficador3D.DO_YOU_WANT_TO_DELETE_THE_FILE+"'" + archivo_generado.file.getName() + "' ?", Graficador3D.DELETE, Graficador3D.CANCEL))
                return;
            File file = new File(archivo_generado.file.getParent(), archivo_generado.nombre + ".jar");
            if(file.exists())
                file.delete();
            if(archivo_generado.file.exists())
                archivo_generado.file.delete();
            Barra_de_herramientas.nueva_distribucion();
        }
    }

    
}