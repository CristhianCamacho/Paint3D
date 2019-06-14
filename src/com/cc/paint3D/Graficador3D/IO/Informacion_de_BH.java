
package com.cc.paint3D.Graficador3D.IO;

//import com.cc.paint3D.Graficador3D.ES.Informacion_de_rotacion;

import java.io.*;

public abstract class Informacion_de_BH
{
	public static final int ROTACION = 1;
	public static final int TRASLACION = 2;
	public static final int TRASLACION_PUNTOS = 3;
    public int tipo;
    public static boolean cambio;
	
    public Informacion_de_BH()
    {
    }

    public static Informacion_de_BH get_BH(int tip,int i)
    {        
        Informacion_de_BH informacion_de_BH=null;
System.out.println("Informacion_de_BH:get_BH:(tip,i)=("+tip+","+i+")");        
        //if(i==1)
        informacion_de_BH = createBH(tip);
System.out.println("Informacion_de_BH:get_BH:informacion_de_BH.tipo="+informacion_de_BH.tipo);         
        //else
        //if(i==0)
        //return null;
        
        if(informacion_de_BH.cambiar_rotacion() || informacion_de_BH.cambiar_traslacion())
            return informacion_de_BH;
        else
            return null;
    }

    public static Informacion_de_BH createBH(int i)
    {
        switch(i)
        {
        case ROTACION: 
            return new Informacion_de_rotacion();
        case TRASLACION: 
            return new Informacion_de_traslacion();
        case TRASLACION_PUNTOS: 
            return new Informacion_de_traslacion_puntos();            
        }
        return null;
    }

    public static Informacion_de_BH leer_BH(DataInputStream datainputstream)
        throws IOException
    {
        int tipp=datainputstream.readInt();
        Informacion_de_BH informacion_de_BH = createBH(tipp);
System.out.println("Informacion_de_BH:leer_BH:tipo= "+tipp);           
        informacion_de_BH.leer_SubBH(datainputstream);
        return informacion_de_BH;
    }

    public void escribir_BH(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeBoolean(true);        
        dataoutputstream.writeInt(tipo);
System.out.println("Informacion_de_BH:escribir_BH:tipo= "+tipo);        
        escribir_SubBH(dataoutputstream);
    }
	
	/*public void set_tipo(int t)
	{
		tipo=t;		
	}*/	
	
    public abstract boolean cambiar_rotacion();
	public abstract boolean cambiar_traslacion();
	
    public abstract void escribir_SubBH(DataOutputStream dataoutputstream)
        throws IOException;

    public abstract void leer_SubBH(DataInputStream datainputstream)
        throws IOException;

    
}