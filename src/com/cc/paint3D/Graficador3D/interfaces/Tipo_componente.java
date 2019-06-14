
package com.cc.paint3D.Graficador3D.interfaces;

import java.io.*;
import javax.media.j3d.TransformGroup;
import javax.swing.JPopupMenu;

public interface Tipo_componente
{

    public abstract void agregar_este_a(TransformGroup transformgroup);

    public abstract void separar_este();

    public abstract void final_agregar_nodo(Object obj);

    public abstract Object get_Prop(int i);

    public abstract void leer_Objeto(DataInputStream datainputstream)
        throws IOException;

    public abstract void set_Menu(JPopupMenu jpopupmenu);

    public abstract void set_Prop(int i, Object obj);

    public abstract void escribir_Objeto(DataOutputStream dataoutputstream)
        throws IOException;
}