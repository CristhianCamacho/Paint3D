
package com.cc.paint3D.Graficador3D.Botones;

import javax.swing.JRadioButtonMenuItem;

public class RBMI extends JRadioButtonMenuItem
{

    public RBMI(int i, String s)
    {
        super(s);
        pos = i;
    }

    public int pos;
}