
package com.cc.paint3D.Graficador3D.IO;

import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.IO.Patron.Panel_patron;

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

import javax.vecmath.Point3f;
import java.awt.geom.Point2D;

public class Informacion_de_traslacion_puntos extends Informacion_de_BH
{
    /*class Dialogo_de_puntos extends ES_Dialogo
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
            tipo = ROTACION;
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
    }*/

	//int direccion;
    //boolean trasladar_5_o_10;
    
    
    Panel_patron panel_patron;
    
    public Point3f[] puntos;
//    float escala=-1;
    int tipo_Spline_Lineal=-1;
    int tiempo=-1;
    	
    public Informacion_de_traslacion_puntos()
    {
        tiempo = 10;
        super.tipo = TRASLACION_PUNTOS;
    }

    public void escribir_SubBH(DataOutputStream dataoutputstream)
        throws IOException
    {
        //dataoutputstream.writeInt(direccion);
        //dataoutputstream.writeBoolean(trasladar_5_o_10);
        dataoutputstream.writeInt(puntos.length);
        for(int i=0;i<puntos.length;i++)
        {
        	escribir_Point3f(dataoutputstream,puntos[i]);	
        }
//        dataoutputstream.writeFloat(escala);
        dataoutputstream.writeInt(tipo_Spline_Lineal);
        dataoutputstream.writeInt(tiempo);
    }
    public void escribir_Point3f(DataOutputStream dataoutputstream, Point3f point3f) throws IOException
    {
    	dataoutputstream.writeFloat(point3f.x);
    	dataoutputstream.writeFloat(point3f.y);
    	dataoutputstream.writeFloat(point3f.z);
	}
	
    public void leer_SubBH(DataInputStream datainputstream)
        throws IOException
    {
        //direccion = datainputstream.readInt();
        //trasladar_5_o_10 = datainputstream.readBoolean();
        
        puntos=new Point3f[datainputstream.readInt()];
        for(int i=0;i<puntos.length;i++)
        {
        	puntos[i]=leer_Point3f(datainputstream);	
        }
//      escala=datainputstream.readFloat();
        tipo_Spline_Lineal=datainputstream.readInt();
         
        tiempo = datainputstream.readInt();
        if(panel_patron!=null)
        panel_patron.jcb_tiempo.setSelectedIndex(tiempo-1);        
    }
    public Point3f leer_Point3f(DataInputStream datainputstream) throws IOException
    {
    	Point3f p3f_salida=new Point3f(datainputstream.readFloat(),
    								   datainputstream.readFloat(),
    								   datainputstream.readFloat());
    	return p3f_salida;    	
	}
    

    public boolean cambiar_traslacion()
    {                
        panel_patron = new Panel_patron( (int)(Escena.canvas.getSize().width/2) , (int)(Escena.canvas.getSize().height/2) );
        if(puntos!=null)        
        panel_patron.set_Point2D(puntos);
        if(tipo_Spline_Lineal!=-1)
        panel_patron.jcb_tipo_de_trayectoria.setSelectedIndex(tipo_Spline_Lineal);
        if(tiempo!=-1)
        panel_patron.jcb_tiempo.setSelectedIndex(tiempo-1);       
//      if(escala!=-1) 
//      panel_patron.set_escala(escala);        
        Informacion_de_BH.cambio = true;
        
        panel_patron.mostrar();
        
        //panel_patron.pintar_puntos();
        //fijamos el punto inicial
        /*{
			p3f_salida[0]=new Point3f( (float)(Escena.x_actual) ,
									   (float)(Escena.y_actual) , 0.0f);
			
		}*/
        
        //panel_patron.tipo_de_trayectoria.setSelectedIndex(tipo_Spline_Lineal);
//        escala=panel_patron.get_escala();
        puntos=get_Point3f_(panel_patron.get_Point2D());
        tipo_Spline_Lineal=panel_patron.jcb_tipo_de_trayectoria.getSelectedIndex();      
        tiempo=panel_patron.jcb_tiempo.getSelectedIndex()+1;        
        
        return Informacion_de_BH.cambio;
    }
	
	public boolean cambiar_rotacion()
	{
		return false;	
	}
	/*public boolean cambiar_traslacion()
	{
		return false;	
	}*/
	
	public Point3f[] get_Point3f_(Point2D[] point2d)
	{
		int n=point2d.length;
		Point3f[] p3f_salida=new Point3f[n];
		
		//		
		for(int i=0;i<n;i++)
		{
			p3f_salida[i]=new Point3f( (float)(point2d[i].getX()) ,
									   (float)(point2d[i].getY()) , 0.0f);			
		}
		
		return p3f_salida;		
	}
	
	
    
}