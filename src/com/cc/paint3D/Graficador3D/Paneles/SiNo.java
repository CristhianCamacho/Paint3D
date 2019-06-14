
package com.cc.paint3D.Graficador3D.Paneles;

import java.awt.Container;
import javax.swing.*;

public class SiNo extends JPanel
{
	
	JRadioButton si;
    JRadioButton no;
	
    public SiNo(String s, String s1, String s2)
    {
        this(s, s1, s2, false);
    }

    public SiNo(String s, String s1, String s2, boolean flag)
    {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), s));
        ButtonGroup buttongroup = new ButtonGroup();
        no = new JRadioButton(s1);
        buttongroup.add(no);
        add(no);
        no.setSelected(true);
        si = new JRadioButton(s2);
        buttongroup.add(si);
        add(si);
        set_Si(flag);
    }

    public SiNo(String s)
    {
        this(s, "No", "Si");
    }

    public void set_Si(boolean flag)
    {
        if(flag)
            si.setSelected(true);
        else
            no.setSelected(false);
    }

    public boolean es_Si()
    {
        return si.isSelected();
    }

    
}