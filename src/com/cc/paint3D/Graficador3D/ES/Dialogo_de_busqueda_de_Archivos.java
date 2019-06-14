
package com.cc.paint3D.Graficador3D.ES;

import com.cc.paint3D.Graficador3D.ES.ES_Dialogo;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;
import com.cc.paint3D.Graficador3D.Utiles.Ver_Imagen;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_pregunta;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;


public class Dialogo_de_busqueda_de_Archivos extends ES_Dialogo
    implements ActionListener, ListSelectionListener
{
    class FFRenderer extends DefaultListCellRenderer
    {

        public Component getListCellRendererComponent(JList jlist, Object obj, int i, boolean flag, boolean flag1)
        {
            File file = (File)obj;
            Component component = super.getListCellRendererComponent(jlist, file.getName(), i, flag, flag1);
            if(file.isDirectory())
                setIcon(Dialogo_de_busqueda_de_Archivos.imageIcon_dir);
            else
                setIcon(Dialogo_de_busqueda_de_Archivos.imageIcon_doc);
            return component;
        }

        FFRenderer(JList jlist)
        {
        }
    }

    class LCRenderer extends Component
        implements ListCellRenderer
    {

        public Component getListCellRendererComponent(JList jlist, Object obj, int i, boolean flag, boolean flag1)
        {
            File file = (File)obj;
            if(get_Profundidad(file) > 0)
            {
                isdisk = false;
                txt = file.getName();
            } else
            {
                isdisk = true;
                txt = file.getAbsolutePath();
            }
            if(i < 0)
                indent = 0;
            else
                indent = 1 + get_Profundidad(file) * 8;
            selected = flag;
            return this;
        }

        public void update(Graphics g)
        {
            paint(g);
        }

        public void paint(Graphics g)
        {
            if(back == null)
                back = getParent().getBackground();
            g.setColor(selected ? Dialogo_de_busqueda_de_Archivos.ALTCOL : back);
            g.fillRect(0, 0, DIM.width + 30, DIM.height);
            if(isdisk)
                g.drawImage(Dialogo_de_busqueda_de_Archivos.image_disk, indent, Image.SCALE_REPLICATE, null);
            else
                g.drawImage(Dialogo_de_busqueda_de_Archivos.image_directorio, indent, Image.SCALE_DEFAULT, null);
            g.setFont(ES_Utiles.FONT_DE_MENSAJES);
            g.setColor(Color.black);
            g.drawString(txt, indent + 21, 15);
        }

        public Dimension getPreferredSize()
        {
            return DIM;
        }

        boolean isdisk;
        Color back;
        String txt;
        int indent;
        boolean selected;

        public LCRenderer()
        {
        }
    }

	public static final int BUSQUEDA = 0;
    public static final int BUSQUEDA_IMAGE = 1;
    public static final int GUARDAR = 2;
    static Color ALTCOL = new Color(153, 153, 255);
    Dimension DIMENSION_BOTON_LIBRERIA;
    Dimension DIM;
    JList jlist;
    DefaultListModel vals;
    JScrollPane sp;
    JComboBox jcb_Buscar_en;
    static File directorio_actual;
    static File file_actual;
    File directorio_home;
    static Image image_directorio;
    static Image image_disk;
    static ImageIcon imageIcon_dir;
    static ImageIcon imageIcon_disk;
    static ImageIcon imageIcon_doc;
    Boton_Libreria bl_subir_un_nivel;
    Boton_Libreria bl_ir_a_home;
    
    Boton_Libreria bl_nuevo_directorio;    
        
    Boton_Libreria bl_seleccionar;
    Boton_Libreria bl_cancelar;
    boolean ignorar;
    Ver_Imagen il;
    int type;
    static JTextField fn;
    String suffix[];
    int lens[];
    int index_actual;
    int slen;


    public Dialogo_de_busqueda_de_Archivos(File file, String s, int i, String s1, String as[])
    {
        super(s);
        DIMENSION_BOTON_LIBRERIA = new Dimension(26, 26);
        DIM = new Dimension(250, 20);
        type = i;
        suffix = as;
        if(as == null)
            slen = 0;
        else
            slen = as.length;
        if(slen > 0)
        {
            lens = new int[slen];
            for(int j = 0; j < slen; j++)
                lens[j] = as[j].length();

        }
        directorio_home = directorio_actual = file;
        if(image_directorio == null)
        {
            image_directorio = ES_Utiles.get_System_Image("dir.gif");
            imageIcon_dir = new ImageIcon(image_directorio);
            imageIcon_doc = new ImageIcon(ES_Utiles.get_System_Image("doc.gif"));
            image_disk = ES_Utiles.get_System_Image("disk.gif");
            imageIcon_disk = new ImageIcon(image_disk);
        }
        super.jp.setLayout(new BorderLayout());
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new FlowLayout(0, 6, 5));
        jpanel.add(new JLabel("Buscar en:"));//"Look in:"
        jpanel.add(jcb_Buscar_en = new JComboBox());
        jcb_Buscar_en.setRenderer(new LCRenderer());
        
        agregar_Raices_a_Buscar_en();
        
        jpanel.add(bl_subir_un_nivel = new Boton_Libreria(null, "updir.gif"));
        bl_subir_un_nivel.addActionListener(this);
        bl_subir_un_nivel.setPreferredSize(DIMENSION_BOTON_LIBRERIA);
        bl_subir_un_nivel.setToolTipText("subir un nivel en el arbol de directorios");//"Go up a directory level"
        jpanel.add(bl_ir_a_home = new Boton_Libreria(null, "home.gif"));
        bl_ir_a_home.addActionListener(this);
        bl_ir_a_home.setPreferredSize(DIMENSION_BOTON_LIBRERIA);
        bl_ir_a_home.setToolTipText("ir al directorio inicial");//"Go to the home directory"
        
        
        jpanel.add(bl_nuevo_directorio = new Boton_Libreria(null, "new.gif"));
        bl_nuevo_directorio.addActionListener(this);
        bl_nuevo_directorio.setPreferredSize(DIMENSION_BOTON_LIBRERIA);
        bl_nuevo_directorio.setToolTipText("nuevo directorio");//"Go to the home directory"
        
        
        
        
        super.jp.add(jpanel, "North");
        JPanel jpanel1 = new JPanel();
        jpanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jpanel1.setLayout(new BorderLayout());
        sp = new JScrollPane();
        vals = new DefaultListModel();
        jlist = new JList();
        jlist.setSelectionMode(0);
        jlist.setCellRenderer(new FFRenderer(jlist));
        jlist.setModel(vals);
        sp.getViewport().setView(jlist);
        jpanel1.add(sp, "Center");
        if(i == 1)
            jpanel1.add(il = new Ver_Imagen(), "East");
        super.jp.add(jpanel1, "Center");
        jcb_Buscar_en.addActionListener(this);
        jlist.addListSelectionListener(this);
        JPanel jpanel2 = new JPanel();
        JPanel jpanel3 = new JPanel();
        jpanel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), s1));
        jpanel3.add(fn = new JTextField(25));
        fn.setFont(ES_Utiles.FONT_DE_MENSAJES);
        jpanel2.add(jpanel3);
        if(i < GUARDAR)
            fn.setEditable(false);
        JPanel jpanel4 = new JPanel();
        jpanel4.setLayout(new BorderLayout());
        String s2;
        if(i < GUARDAR)
            s2 = "Abrir  ";
        else
            s2 = "Guardar   ";
        jpanel4.add(bl_seleccionar = new Boton_Libreria(s2), "North");
        bl_seleccionar.addActionListener(this);
        if(i < GUARDAR)
            bl_seleccionar.setEnabled(false);
        jpanel4.add(bl_cancelar = new Boton_Libreria("Cancelar"), "South");
        bl_cancelar.addActionListener(this);
        jpanel2.add(jpanel4);
        super.jp.add(jpanel2, "South");
        set_Directorio(directorio_actual);
        set_ir_a_home();
        dimensionar_y_mostrar();
        if(i == GUARDAR)
            fn.requestFocus();
    }

    public void set_ir_a_home()
    {
        ignorar = true;        
        int tam_jcb = jcb_Buscar_en.getItemCount();
        
        String s = directorio_actual.getAbsolutePath();
        
        int j = 0;
        for(j = 0; j < tam_jcb; j++)
        {
            File file = (File)jcb_Buscar_en.getItemAt(j);
            if(s.startsWith(file.getAbsolutePath()))
                break;
        }

        int k = get_Profundidad(directorio_actual);
                
        if(k == 0)
        {
            jcb_Buscar_en.setSelectedIndex(j);
            ignorar = false;
            return;
        }
        if(j == tam_jcb)
        {
            new Dialogo_de_error("Un dispositivo esperado esta perdido - El programa no funcionara apropiadamente.");
            return;
        }
        j++;
        File file1 = directorio_actual;
        do
        {
            if(j == tam_jcb)
                jcb_Buscar_en.addItem(file1);
            else
                jcb_Buscar_en.insertItemAt(file1, j);
            file1 = file1.getParentFile();
            if(file1.getParentFile() != null)
            {
                tam_jcb = -1;
            } else
            {
                jcb_Buscar_en.setSelectedItem(directorio_actual);
                ignorar = false;
                return;
            }
        } while(true);
    }

    public void set_Directorio(File file)
    {
        if(get_Profundidad(file) == 0)
            bl_subir_un_nivel.setEnabled(false);
        else
            bl_subir_un_nivel.setEnabled(true);
        vals.removeAllElements();
        File afile[] = file.listFiles();
        if(afile == null)
            return;
        int i = afile.length;
        File afile1[] = new File[i];
        int j = 0;
        for(int k = 0; k < i; k++)
        {
            File file1 = afile[k];
            if(file1.isDirectory())
            {
                afile1[j] = file1;
                j++;
            }
        }

        fileBubbleSort(afile1, j);
        for(int l = 0; l < j; l++)
            vals.addElement(afile1[l]);

        j = 0;
        boolean flag = false;
        for(int l1 = 0; l1 < i; l1++)
        {
            File file2 = afile[l1];
            if(!file2.isDirectory())
                if(slen == 0)
                {
                    afile1[j] = file2;
                    j++;
                } else
                {
                    String s = file2.getName();
                    int j1 = s.length();
                    int k1;
                    for(k1 = 0; k1 < slen; k1++)
                    {
                        int i1 = lens[k1];
                        if(s.regionMatches(true, j1 - i1, suffix[k1], 0, i1))
                            break;
                    }

                    if(k1 < slen)
                    {
                        afile1[j] = file2;
                        j++;
                    }
                }
        }

        if(j > 0)
        {
            fileBubbleSort(afile1, j);
            for(int i2 = 0; i2 < j; i2++)
                vals.addElement(afile1[i2]);

        }
        file_actual = null;
        if(type == 0)
            bl_seleccionar.setEnabled(false);
        fn.setText("");
        directorio_actual = file;
        index_actual = -2;
    }

	//ListSelectionListener
    public void valueChanged(ListSelectionEvent listselectionevent)
    {
        int i = jlist.getSelectedIndex();
        if(i < 0)
            return;
        if(index_actual == i)
            return;
        index_actual = i;
        File file = (File)vals.getElementAt(i);
        if(file.isDirectory())
        {
            set_Directorio(file);
            int j = jcb_Buscar_en.getItemCount();
            String s = file.getAbsolutePath();
            int k = 0;
            for(k = 0; k < j; k++)
            {
                File file1 = (File)jcb_Buscar_en.getItemAt(k);
                if(s.startsWith(file1.getAbsolutePath()))
                    break;
            }

            if(k == j - 1)
            {
            	jcb_Buscar_en.addItem(file);
                jcb_Buscar_en.setSelectedIndex(j);
            } else
            {
                int l = 0;
                for(l = k; l < j; l++)
                {
                    File file2 = (File)jcb_Buscar_en.getItemAt(l);
                    if(!s.startsWith(file2.getAbsolutePath()))
                        break;
                }

                jcb_Buscar_en.insertItemAt(file, l);
                ignorar = true;
                jcb_Buscar_en.setSelectedItem(file);
                ignorar = false;
            }
        } else
        if(type < 2)
        {
            file_actual = file;
            bl_seleccionar.setEnabled(true);
            fn.setText(file.getName());
            if(type == 1)
                il.ver_imagen(file);
        } else
        if(type == 2)
        {
            file_actual = file;
            fn.setText(file.getName());
        }
    }

    public int get_Profundidad(File file)
    {
        int i = 0;
        do
        {
            file = file.getParentFile();
            if(file != null)
                i++;
            else
                return i;
        } while(true);
    }

    public void agregar_Raices_a_Buscar_en()
    {
        File afile[] = File.listRoots();
        int i = afile.length;
        ignorar = true;
        for(int j = 0; j < i; j++)
            jcb_Buscar_en.addItem(afile[j]);

        ignorar = false;
    }

    public void borrar_Buscar_en(File file)
    {
        Vector vector = new Vector();
        int i = jcb_Buscar_en.getItemCount();
        String s = file.getAbsolutePath();
        for(int j = 0; j < i; j++)
        {
            File file1 = (File)jcb_Buscar_en.getItemAt(j);
            String s1 = file1.getAbsolutePath();
            if(s1.startsWith(s) && !s1.equals(s))
                vector.addElement(file1);
        }

        i = vector.size();
        ignorar = true;
        for(int k = 0; k < i; k++)
            jcb_Buscar_en.removeItem(vector.elementAt(k));

        ignorar = false;
    }
	//ActionListener
    public void actionPerformed(ActionEvent actionevent)
    {
        Object obj = actionevent.getSource();
        if(obj instanceof JComboBox)
        {
            if(!ignorar)
            {
                File file = (File)jcb_Buscar_en.getSelectedItem();
                File afile[] = file.listFiles();
                if(afile == null)
                {
                    new Dialogo_de_error("El dispositivo '" + file.getAbsolutePath() + "' no es accessible.");//The device //is not accessible
                    ignorar = true;
                    jcb_Buscar_en.setSelectedItem(directorio_actual);
                    ignorar = false;
                    return;
                }
                borrar_Buscar_en(file);
                set_Directorio(file);
            }
        } else
        if(obj instanceof Boton_Libreria)
        {
            Boton_Libreria boton_libreria = (Boton_Libreria)obj;
            if(boton_libreria == bl_subir_un_nivel)
            {
                if(get_Profundidad(directorio_actual) == 0)
                    return;
                File file1 = directorio_actual.getParentFile();
                set_Directorio(file1);
                borrar_Buscar_en(file1);
            } else
            if(boton_libreria == bl_ir_a_home)
            {
                set_Directorio(directorio_home);
                Vector vector = new Vector();
                int i = jcb_Buscar_en.getItemCount();
                for(int j = 0; j < i; j++)
                {
                    File file2 = (File)jcb_Buscar_en.getItemAt(j);
                    if(get_Profundidad(file2) > 0)
                        vector.addElement(file2);
                }

                i = vector.size();
                ignorar = true;
                for(int k = 0; k < i; k++)
                    jcb_Buscar_en.removeItem(vector.elementAt(k));

                ignorar = false;
                set_ir_a_home();
            } else
            if(boton_libreria == bl_nuevo_directorio)
            {
            	ignorar=true;            	
//System.out.println("Dialogo_de_Busqueda_de_Archivo:actionPerformed:file_actual.getAbsolutePath()"+directorio_actual.getAbsolutePath() );
            	Dialogo_de_obtener_texto dot=new Dialogo_de_obtener_texto("Ingrese el nombre del nuevo DIRECTORIO");
            	String nuevo_dir_temp=dot.tf.getText();
//System.out.println("Dialogo_de_Busqueda_de_Archivo:actionPerformed:file_actual.getAbsolutePath()+\"/\"+nuevo_dir_temp"+directorio_actual.getAbsolutePath()+"/"+nuevo_dir_temp );
            	File archivo=new File(directorio_actual.getAbsolutePath()+"/"+nuevo_dir_temp );
            	if(!vals.contains(archivo) )
            	{	
            		archivo.mkdir();      	
            		vals.addElement(archivo);
            	}
            	else
            	if(!nuevo_dir_temp.equals(""))
            	{
            		new Dialogo_de_error("El directorio  ' "+nuevo_dir_temp+" '  ya existe");	
            	}            	            	
            }
            if(boton_libreria == bl_cancelar)
            {
                fn.setText("");
                file_actual = null;
                cerrar_Dialogo();
            } else
            if(boton_libreria == bl_seleccionar)
                cerrar_Dialogo();
        }
    }

    public void ultima_llamada_de_Dialogo()
    {
        file_actual = null;
        directorio_actual = null;
    }
	//Manejador de archivos
    public static File preguntar(File file, String s, int i, String s1, String as[])
    {
        new Dialogo_de_busqueda_de_Archivos(file, s, i, s1, as);
        if(i == GUARDAR)
        {
            if(fn.getText().equals(""))
                return null;
            File file1 = new File(directorio_actual, fn.getText());
            if(file1.exists() && !Dialogo_de_pregunta.preguntando("Sobreescribir el archivo existente con el mismo nombre?", "Sobrescribir", "Cancelar"))
                return null;
            else
                return file1;
        } else
        {
            return file_actual;
        }
    }

    public static void fileBubbleSort(File afile[], int i)
    {
        int l = i - 1;
        boolean flag = false;
        do
        {
            for(int j = 0; j < l; j++)
            {
                int k = j + 1;
                if(afile[j].getName().compareToIgnoreCase(afile[k].getName()) > 0)
                {
                    File file = afile[k];
                    afile[k] = afile[j];
                    afile[j] = file;
                    flag = true;
                }
            }

            if(flag)
            {
                flag = false;
                l--;
            } else
            {
                return;
            }
        } while(true);
    }

    

}