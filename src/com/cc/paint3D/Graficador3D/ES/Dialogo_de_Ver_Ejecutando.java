
package com.cc.paint3D.Graficador3D.ES;

import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.Barra;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;
import com.cc.paint3D.Graficador3D.G3D.Datos_utiles;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.media.j3d.Canvas3D;
import javax.swing.*;


public class Dialogo_de_Ver_Ejecutando extends ES_Dialogo
    implements ActionListener
{
	String CANCELAR;

    public Dialogo_de_Ver_Ejecutando(Canvas3D canvas3d)
    {
        super("Ejecutando la distribucion actual");
        CANCELAR = "Cerrar";
        super.jp.setLayout(new BorderLayout());
        
        
        
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new FlowLayout());
        jpanel.setBorder(BorderFactory.createEtchedBorder());
        Boton_Libreria boton_libreria;
        jpanel.add(boton_libreria = new Boton_Libreria(CANCELAR));
        boton_libreria.addActionListener(this);
        
        super.jp.add(jpanel, "North");
        super.jp.add(canvas3d, "Center");
        
        setLocation(Escena.handle.getLocation().x,Escena.handle.getLocation().y);
        setSize( Escena.handle.getSize().width , Escena.handle.getSize().height );        
        
        setVisible(true);
        //dimensionar_y_mostrar();
        canvas3d.requestFocus();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(CANCELAR == actionevent.getActionCommand())
        {
            System.gc();
            dispose();
            cerrar_Dialogo();             
        }
    }

    public static void ejecutar()
    {
        Canvas3D canvas3d = null;
        try
        {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
                        
            Datos_utiles.escribir_todo(dataoutputstream);
            
            byte abyte0[] = bytearrayoutputstream.toByteArray();
            ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
            DataInputStream datainputstream = new DataInputStream(bytearrayinputstream);
            int i = datainputstream.readInt();
            int j = datainputstream.readInt();

            canvas3d = Datos_utiles.getCanvas(datainputstream, new Dimension(i, j));
            canvas3d.setSize(i, j);
        }
        catch(Exception exception)
        {
            new Dialogo_de_error("Error al cargar la distribucion - " + exception.getMessage());
        }
        if(canvas3d != null)
            new Dialogo_de_Ver_Ejecutando(canvas3d);
        System.gc();
    }


}