
package com.cc.paint3D.Graficador3D.ES;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

public class Error_NO_java3D extends Window implements KeyListener
{
	String URL;
	
    public Error_NO_java3D(Dimension dimension)
    {
        super(new Frame());
        URL = "http://java.sun.com/products/java-media/3D/index.html";
        
        Color color_de_fondo=Color.RED;
        
        setBackground(color_de_fondo);
        setForeground(Color.lightGray);
                
        setLayout(new GridLayout(4, 1));
        Font font = new Font("Dialog", Font.BOLD , 20);
        Label label;
        
                
        add(label = new Label("Este programa necesita de la extension Java3D.(NO INSTALADA)"));
        label.setFont(font);
        add(label = new Label("Java3D se puede descargar gratis de :" ));
        label.setFont(font);
        add(label = new Label("'" + URL + "'"));
        label.setFont(font);
        
        
        Button button = new Button("Cerrar");
        //button.setForeground( Color.blue.brighter() );        
        button.setBackground( Color.blue.brighter() );   
        
        Panel panel_de_boton=new Panel();
        panel_de_boton.setLayout( new GridLayout(1,3) );
        	Panel panel_falso_1=new Panel();panel_falso_1.setBackground( color_de_fondo );
        	Panel panel_falso_2=new Panel();panel_falso_2.setBackground( color_de_fondo );
        
        panel_de_boton.add(panel_falso_1);
        panel_de_boton.add(button);
        panel_de_boton.add(panel_falso_2);
          
                
        add(panel_de_boton);
        
        
        button.setFont(font);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                llamada_final();
            }

        });
        button.addKeyListener(this);
        button.requestFocus();
        pack();
        
        setSize(new Dimension(700,300));
        Dimension dimension1 = getSize();
        setLocation((dimension.width - dimension1.width) / 2, (dimension.height - dimension1.height) / 2 + 15);
        setVisible(true);
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyPressed(KeyEvent keyevent)
    {
        if(keyevent.getKeyCode() == 10)
            llamada_final();
    }

    void llamada_final()
    {
        System.exit(0);
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void paint(Graphics g)
    {
        Dimension dimension = getSize();
        g.drawRect(0, 0, dimension.width - 1, dimension.height - 1);
    }
  
}