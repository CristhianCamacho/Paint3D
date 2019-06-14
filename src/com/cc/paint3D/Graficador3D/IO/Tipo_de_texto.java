
package com.cc.paint3D.Graficador3D.IO;

import java.io.*;

public class Tipo_de_texto
{
	public File path;
    public int tipo;
	
    public Tipo_de_texto(File file, int i)
    {
        path = file;
        tipo = i;
    }

    public Tipo_de_texto(String s, int i)
    {
        path = new File(s);
        tipo = i;
    }

    public void escribir_texto(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeUTF(path.getAbsolutePath());
        dataoutputstream.writeInt(tipo);
    }

    
}