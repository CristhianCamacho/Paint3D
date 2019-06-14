
package com.cc.paint3D.Graficador3D.IO;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Menus.Barra_de_Menus;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.G3D.Archivo_generado;
import com.cc.paint3D.Graficador3D.HTML_ayuda.Creador_de_HTML;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;
import javax.swing.*;


public class Archivos_recientes
    implements ActionListener
{
	
	public static final int MAX = 6;
    public static File LISTA_DE_FILES_RECIENTES = new File(Props_de_sistema.DIR_DE_PROPS, "recientes.g3d");;
    public static String SALIR = Graficador3D.EXIT;//"Exit"
    public static Archivos_recientes handle;
    public static Vector lista_de_archivos_recientes;
    public static JMenu file;


    public Archivos_recientes()
    {
    }

    public static void cargar_Archivos_recientes()
    {
        lista_de_archivos_recientes = new Vector();
        file = Barra_de_Menus.file;
        handle = new Archivos_recientes();
        if(LISTA_DE_FILES_RECIENTES.exists())
        {
            file.addSeparator();
            try
            {
                BufferedReader bufferedreader = new BufferedReader(new FileReader(LISTA_DE_FILES_RECIENTES));
                String s;
                while((s = bufferedreader.readLine()) != null) 
                {
                    JMenuItem jmenuitem;
                    file.add(jmenuitem = new JMenuItem(s));
                    jmenuitem.addActionListener(handle);
                    lista_de_archivos_recientes.addElement(s);
                }
                bufferedreader.close();
            }
            catch(Exception exception)
            {
                new Dialogo_de_error("No se puede leer de la lista de archivos recientes.");//"Unable to read the recent file list."
            }
        }
        agregar_MenuItem_Salir();
    }

    public static void agregar_MenuItem_Salir()
    {
        file.addSeparator();
        JMenuItem jmenuitem;
        file.add(jmenuitem = new JMenuItem(SALIR));
        jmenuitem.addActionListener(handle);
    }

    public static void agregar_Archivo_reciente(File file1)
    {
        String s = file1.getAbsolutePath();
        JMenuItem jmenuitem = new JMenuItem(s);
        jmenuitem.addActionListener(handle);
        int l = lista_de_archivos_recientes.size();
        int i1 = l;
        int i = 0;
        if(l > 0)
        {
            for(; i < l; i++)
                if(s.equals((String)lista_de_archivos_recientes.elementAt(i)))
                    break;

            if(i < l)
            {
                if(i > 0)
                {
                    Object obj = lista_de_archivos_recientes.elementAt(i);
                    lista_de_archivos_recientes.removeElementAt(i);
                    lista_de_archivos_recientes.insertElementAt(obj, 0);
                }
            } else
            if(l == MAX)
            {
                lista_de_archivos_recientes.removeElementAt(5);
                lista_de_archivos_recientes.insertElementAt(s, 0);
            } else
            {
                lista_de_archivos_recientes.insertElementAt(s, 0);
                i1++;
            }
        } else
        {
            lista_de_archivos_recientes.addElement(s);
            i1++;
        }
        int j1 = file.getItemCount() - 1;
        if(l == 0)
        {
            file.remove(j1);
        } else
        {
            int k1 = l + 2;
            for(int j = 0; j < k1; j++)
                file.remove(j1--);

        }
        try
        {
            BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(LISTA_DE_FILES_RECIENTES));
            for(int k = 0; k < i1; k++)
            {
                String s1 = (String)lista_de_archivos_recientes.elementAt(k);
                bufferedwriter.write(s1, 0, s1.length());
                bufferedwriter.newLine();
                JMenuItem jmenuitem1;
                file.add(jmenuitem1 = new JMenuItem(s1));
                jmenuitem1.addActionListener(handle);
            }

            bufferedwriter.close();
            agregar_MenuItem_Salir();
        }
        catch(Exception exception)
        {
            new Dialogo_de_error("No se puede agregar a la lista de archivos recientes.");//"Unable to write recent file list."
        }
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        if(s == SALIR)
        {
            Graficador3D.cerrar_Programa();
        } else
        {
            File file1 = new File(s);
            Archivo_generado archivo_generado = Creador_de_HTML.get_primer_applet(file1);
            if(archivo_generado == null)
                return;
            if(archivo_generado != null)
                Distribuidor_de_Archivos.abrir_Distribucion(archivo_generado);
        }
    }

    
    
}