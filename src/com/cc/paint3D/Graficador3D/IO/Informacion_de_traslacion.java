
package com.cc.paint3D.Graficador3D.IO;

import com.cc.paint3D.Graficador3D.ES.ES_Dialogo;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;
import com.cc.paint3D.Graficador3D.IO.Informacion_de_BH;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_opciones_multiples;
import com.cc.paint3D.Graficador3D.Paneles.SiNo;
import com.cc.paint3D.Graficador3D.Paneles.Caja_de_masmenos;



import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.io.*;


public class Informacion_de_traslacion extends Informacion_de_BH
{
    class Dialogo_de_traslacion extends ES_Dialogo
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            String s = actionevent.getActionCommand();
            if(s == CANCELAR)
            {
                Informacion_de_BH.cambio = false;
                cerrar_Dialogo();
                return;
            } else
            {
                Informacion_de_BH.cambio = true;
                
                direccion = pom_ejes.get_opcion();
                trasladar_5_o_10 = rel.es_Si();
                tiempo = tm.actual;
                
                cerrar_Dialogo();
                return;
            }
        }

        String APLICAR;
        String CANCELAR;
        String ejes[] = {
            "Eje: Y ", "Eje: X ", "Eje: Z "
        };
        Panel_de_opciones_multiples pom_ejes;
        SiNo rel;
        Caja_de_masmenos tm;

        Dialogo_de_traslacion()
        {
            super("Trasladar");
            APLICAR = "Aplicar";
            CANCELAR = "Cancelar";
            tipo = TRASLACION;
            super.jp.setLayout(new GridLayout(4, 1));
            super.jp.add(pom_ejes = new Panel_de_opciones_multiples("Siguiendo de cual eje", ejes, null, direccion));
            pom_ejes.set_opcion(direccion);
            super.jp.add(rel = new SiNo("trasladar:", "5 unidades", "10 unidades"));
            rel.set_Si(trasladar_5_o_10);
            super.jp.add(tm = new Caja_de_masmenos("Tiempo de Traslacion (segundos)", null, tiempo, 1, 1000));
            Container container = new Container();
            container.setLayout(new FlowLayout());
            super.jp.add(container);
            container.add(new Boton_Libreria(APLICAR, this));
            container.add(new Boton_Libreria(CANCELAR, this));
            dimensionar_y_mostrar();
        }
    }

	int direccion;
    boolean trasladar_5_o_10;
    int tiempo;
	
    public Informacion_de_traslacion()
    {
        tiempo = 4;
        super.tipo = TRASLACION;
    }

    public void escribir_SubBH(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeInt(direccion);
        dataoutputstream.writeBoolean(trasladar_5_o_10);
        dataoutputstream.writeInt(tiempo);
    }

    public void leer_SubBH(DataInputStream datainputstream)
        throws IOException
    {
        direccion = datainputstream.readInt();
        trasladar_5_o_10 = datainputstream.readBoolean();
        tiempo = datainputstream.readInt();
    }

    public boolean cambiar_traslacion()
    {                
        new Dialogo_de_traslacion();
        return Informacion_de_BH.cambio;
    }
	
	public boolean cambiar_rotacion()
	{
		return false;	
	}
    
}