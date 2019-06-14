
package com.cc.paint3D.Graficador3D.IO;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import javax.vecmath.Color3f;
import javax.vecmath.Tuple3f;

public class Manejador_de_archivos_temporales
{
	public static Font MSG_FONT = new Font("Helvetica", Font.BOLD, 12);
	
    public Manejador_de_archivos_temporales()
    {
    }

    public static Color3f getColor3f(Color color)
    {
        float f = color.getRed() != 0 ? (float)color.getRed() / 255F : 0.0F;
        float f1 = color.getGreen() != 0 ? (float)color.getGreen() / 255F : 0.0F;
        float f2 = color.getBlue() != 0 ? (float)color.getBlue() / 255F : 0.0F;
        return new Color3f(f, f1, f2);
    }

    public static Color getColor(Color3f color3f)
    {
        return new Color( (int)(((Tuple3f) (color3f)).x * 255F),
        				  (int)(((Tuple3f) (color3f)).y * 255F),
        				  (int)(((Tuple3f) (color3f)).z * 255F) );
    }

    public static void borrar_archivos_temporales()
    {
        String as[] = Props_de_sistema.DIR_TEMPORAL.list();
        int i = as.length;
        for(int j = 0; j < i; j++)
            (new File(Props_de_sistema.DIR_TEMPORAL, as[j])).delete();

    }
   
}