
package com.cc.paint3D.Graficador3D.HTML_ayuda;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.Barra;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.JTextComponent;
import java.io.IOException;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultTreeCellRenderer;

public class Ventana_de_Ayuda extends JFrame
    implements ActionListener, HyperlinkListener
{
			
    static final Cursor HAND = new Cursor(Cursor.HAND_CURSOR);
    static Dimension DIM = new Dimension(25, 25);
    static String EXCEPTION = "Exception al cargar la pagina";
    static String CERRAR = "Cerramos la ayuda";
    static Boton_Libreria atras;
    static Boton_Libreria adelante;
    static Boton_Libreria index;
    static Vector paginas;
    static int pagina_actual;
    static String[] todas_las_paginas={ "index.html", "basico.html", "componentes.html",
    								    "propiedades.html", "luces.html", "fondo.html",
    								    "vista.html","desplegar.html","ayuda.html",
    								    "preferencias.html","soporte.html"};
    static JEditorPane jEditorPane_html;
    
    static Font TOOLTIP_FONT = new Font("SansSerif", Font.BOLD, 16);

	private static boolean DEBUG = false;
	
	
	

    public Ventana_de_Ayuda()
    {		
        Barra.handle.repaint();
        setTitle(ES_Utiles.PRODUCTO + Graficador3D.DOCUMENTATION);
        setIconImage(ES_Utiles.get_System_Image(Graficador3D.SYS_ICON));
        JPanel jpanel = new JPanel();
        jpanel.setOpaque(true);
        getContentPane().setLayout(new BorderLayout());
                
        getContentPane().add(jpanel, "Center");
                               
        jpanel.setLayout(new BorderLayout());
        paginas = new Vector(1);
        JPanel jpanel1 = new JPanel();
        jpanel1.setLayout(new BorderLayout());
        jpanel1.setBackground(Color.lightGray);
        JPanel jpanel2 = new JPanel();
        jpanel2.setLayout(new FlowLayout(1, 5, 0));
        jpanel2.setBackground(Color.lightGray);
        jpanel2.add(atras = new Boton_Libreria(Graficador3D.BACK, Graficador3D.LEFT_HTML_ICON));
        atras.setEnabled(false);
        atras.addActionListener(this);
        jpanel2.add(index = new Boton_Libreria(Graficador3D.CONTENTS, Graficador3D.INDEX_ICON));
        index.setEnabled(false);
        index.addActionListener(this);
        jpanel2.add(adelante = new Boton_Libreria(Graficador3D.FORWARD, Graficador3D.RIGHT_HTML_ICON));
        adelante.setEnabled(false);
        jpanel1.add(jpanel2, "West");
        Boton_Libreria boton_libreria;
        jpanel1.add(boton_libreria = new Boton_Libreria(CERRAR), "East");
        boton_libreria.addActionListener(this);
        jpanel1.setBorder(BorderFactory.createEtchedBorder());
        jpanel.add(jpanel1, "North");
        
        //URL url = getClass().getResource("ayuda/index.html");
        URL url = getClass().getResource("ayuda/"+todas_las_paginas[0]);
        paginas.addElement(url);
        try
        {
            jEditorPane_html = new JEditorPane(url);
        }
        catch(Exception exception)
        {
            new Dialogo_de_error("No se encontro la ayuda");
            return;
        }
        jEditorPane_html.setEditable(false);
        jEditorPane_html.addHyperlinkListener(this);
        
        
        //////////////////////////////////////////////////////////////////////////
              
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Ayuda");
        createNodes(top);
        JTree tree = new JTree(top);
        tree.setFont(new Font("Serif",(Font.ITALIC),12));
        tree.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        

        renderer.setLeafIcon(new ImageIcon("/out/production/Paint3D/com/cc/paint3D/Graficador3D/HTML_ayuda/ayuda/bullet.gif"));

        tree.setCellRenderer(renderer);
        tree.addTreeSelectionListener(new TreeSelectionListener() 
        {
            public void valueChanged(TreeSelectionEvent e) 
            {
            	//pagina_actual=-1;//
            	index.setEnabled(true);
            	
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)(e.getPath().getLastPathComponent());
                Object nodeInfo = node.getUserObject();
                if (node.isLeaf()) 
                {
                 BookInfo book = (BookInfo)nodeInfo;
                 
                 //System.out.println("Ventana_de_Ayuda:valueChanged:132 "+ book.bookURL.getFile() );
                 System.out.println("Ventana_de_Ayuda:valueChanged:132 "+ book.direccionRelativa );
                 for(int i=0;i<todas_las_paginas.length;i++)
                 	{	
                 		String direccionRelativa=book.direccionRelativa;
                 		if(direccionRelativa.equalsIgnoreCase("out/production/Paint3D/com/cc/paint3D/Graficador3D/HTML_ayuda/ayuda/"+todas_las_paginas[i]))
                 		pagina_actual=i;
                 	}
                 	
                 displayURL(book.bookURL);
                 if (DEBUG) 
                 {
                  System.out.print(book.bookURL + ": \n");
                 }
                }
                else 
                {
                 System.out.println("Mostrando helpURL");	
                }
                if (DEBUG) 
                {
                 System.out.println(nodeInfo.toString());
                }
            }
        });
        
        JScrollPane treeView = new JScrollPane(tree);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(treeView);
        
        splitPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLoweredBevelBorder()));
        jpanel.add(splitPane, "West");
        
        setVisible(true);
             
        
/////////////////////////////////////////////////////////////////////////
        JScrollPane jscrollpane = new JScrollPane();
        jscrollpane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLoweredBevelBorder()));
        javax.swing.JViewport jviewport = jscrollpane.getViewport();
        jviewport.add(jEditorPane_html);
        
        jpanel.add(jscrollpane, "Center");
        
        setSize(new Dimension(Graficador3D.barra.getSize().width , ( Graficador3D.escena.getSize().height ) ) );
        Dimension dimension = getSize();
        setLocation( (int)(Graficador3D.barra.getLocation().x) , (int)(Graficador3D.barra.getLocation().y+Graficador3D.barra.getSize().height) );
        
        addWindowListener(new java.awt.event.WindowAdapter() 
        {
          public void windowClosing(java.awt.event.WindowEvent evt) 
          {
           setVisible(false);         	
           dispose();
           System.gc();
          }
        });       
        
        setVisible(true);
    }

    public void hyperlinkUpdate(HyperlinkEvent hyperlinkevent)
    {
        if(hyperlinkevent.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED)
        {
            setCursor(Cursor.getDefaultCursor());
            URL url = hyperlinkevent.getURL();
            try
            {
                jEditorPane_html.setPage(url);
            }
            catch(Exception exception)
            {
                setVisible(false);
                new Dialogo_de_error(EXCEPTION);
                return;
            }
            atras.setEnabled(true);
            index.setEnabled(true);
            if(paginas.size() == 1)
            {
                paginas.addElement(url);
                pagina_actual++;
            } else
            if(pagina_actual + 1 == paginas.size())
            {
                paginas.addElement(url);
                pagina_actual++;
            } else
            if(url.equals((URL)paginas.elementAt(pagina_actual + 1)))
            {
                pagina_actual++;
                if(pagina_actual + 1 == paginas.size())
                    adelante.setEnabled(false);
            } else
            {
                for(int i = paginas.size() - 1; i > pagina_actual; i--)
                    paginas.removeElementAt(i);

                paginas.addElement(url);
                pagina_actual++;
                adelante.setEnabled(false);
            }
        } else
        if(hyperlinkevent.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ENTERED)
            setCursor(HAND);
        else
        if(hyperlinkevent.getEventType() == javax.swing.event.HyperlinkEvent.EventType.EXITED)
            setCursor(Cursor.getDefaultCursor());
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getActionCommand() == CERRAR)
        {
            setVisible(false);
            return;
        }
        Boton_Libreria boton_libreria = (Boton_Libreria)actionevent.getSource();
        if(boton_libreria == index)
        {
                                    
            if(pagina_actual == 0)
                return;
            try
            {
                jEditorPane_html.setPage((URL)paginas.elementAt(0));
                pagina_actual = 0;
            }
            catch(Exception exception)
            {
                setVisible(false);
                new Dialogo_de_error(EXCEPTION);
                return;
            }
            atras.setEnabled(false);
            adelante.setEnabled(true);
            
            index.setEnabled(false);
        } else
        {
            if(paginas.size() <= 1)
                return;
            if(boton_libreria == atras)
            {
                pagina_actual--;
                try
                {
                    jEditorPane_html.setPage((URL)paginas.elementAt(pagina_actual));
                }
                catch(Exception exception1)
                {
                    setVisible(false);
                    new Dialogo_de_error(EXCEPTION);
                    return;
                }
                adelante.setEnabled(true);
                if(pagina_actual == 0)
                {
                    atras.setEnabled(false);
                    index.setEnabled(false);
                }
            } else
            if(boton_libreria == adelante)
            {
                pagina_actual++;
                index.setEnabled(true);
                try
                {
                    jEditorPane_html.setPage((URL)paginas.elementAt(pagina_actual));
                }
                catch(Exception exception2)
                {
                    setVisible(false);
                    new Dialogo_de_error(EXCEPTION);
                    return;
                }
                atras.setEnabled(true);
                if(pagina_actual + 1 == paginas.size())
                    adelante.setEnabled(false);
            }else
            if(boton_libreria == index)
            {
            	pagina_actual=0;
            }
        }
    }
    
    private class BookInfo 
    {
      public String bookName;
      public String direccionRelativa;
      public URL bookURL;
      public String prefix = "file:"+System.getProperty("user.dir")+System.getProperty("file.separator");
   
      public BookInfo(String book,String filename) 
      {
        bookName = book;
         try 
         {
          //System.out.println("Ventana_de_Ayuda:BookInfo:prefix= "+prefix);
          direccionRelativa=filename;
          bookURL = new URL(prefix + filename);
          //System.out.println( "Ventana_de_Ayuda:BookInfo:bookURL= "+bookURL.getFile() );
         }
         catch (java.net.MalformedURLException exc) 
         {
          //System.err.println("Error con el bookURL: "+bookURL);
          bookURL = null;
         }
      }

        public String toString() 
        {
         return bookName;
        }
    }
    
    private void createNodes(DefaultMutableTreeNode top) 
    {    
    	
      String DIR="/out/production/Paint3D/com/cc/paint3D/Graficador3D/HTML_ayuda/ayuda/";
      
      DefaultMutableTreeNode category = null;
      DefaultMutableTreeNode book = null;
      
      
      category = new DefaultMutableTreeNode(new BookInfo("INDICE",DIR+"index.html"));   
      top.add(category);
      
      category = new DefaultMutableTreeNode(new BookInfo(" "," "));   
      top.add(category);
            
      book = new DefaultMutableTreeNode(new BookInfo("Lo Basico para el Uso",DIR+"basico.html"));
      top.add(book);
      book = new DefaultMutableTreeNode(new BookInfo("Los Componentes o Figuras",DIR+"componentes.html"));
      top.add(book);
      book = new DefaultMutableTreeNode(new BookInfo("Cambiando las Propiedades ",DIR+"propiedades.html"));
      top.add(book);	
      book = new DefaultMutableTreeNode(new BookInfo("Iluminacion de la escena",DIR+"luces.html"));
      top.add(book);
      book = new DefaultMutableTreeNode(new BookInfo("Propiedades del Fondo",DIR+"fondo.html"));
      top.add(book);	
      book = new DefaultMutableTreeNode(new BookInfo("Menu de opciones de la Vista",DIR+"vista.html"));
      top.add(book);	
      book = new DefaultMutableTreeNode(new BookInfo("Desplegar opciones",DIR+"desplegar.html"));
      top.add(book);	
      book = new DefaultMutableTreeNode(new BookInfo("Menu de opciones de la Ayuda",DIR+"ayuda.html"));
      top.add(book);
      book = new DefaultMutableTreeNode(new BookInfo("Las Preferencias del sistema ",DIR+"preferencias.html"));
      top.add(book);	
      book = new DefaultMutableTreeNode(new BookInfo("Soporte tecnico",DIR+"soporte.html"));
      top.add(book);	
            		    
    }
    
    private void displayURL(URL url) 
    {
                
        try 
        {
         jEditorPane_html.setPage(url);
         System.out.println((jEditorPane_html.getPage()));
        }
        catch (IOException e) 
        {
         System.err.println("Error con el URL: "+url);
        }
    }

    
    public static void main(String[] args) 
    {
     (new Ventana_de_Ayuda()).show();
    }

}