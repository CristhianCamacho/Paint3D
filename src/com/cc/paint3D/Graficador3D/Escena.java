package com.cc.paint3D.Graficador3D;

import com.cc.paint3D.Graficador3D.Menus.Menu_de_iluminacion;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_propiedades_de_apariencia;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_propiedades_de_comportamiento;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_de_fondo;
import com.cc.paint3D.Graficador3D.G3D.Figura_luz;
import com.cc.paint3D.Graficador3D.G3D.Datos_utiles;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.Paneles.Panel_Principal;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_texto_estado;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;

import com.cc.paint3D.Graficador3D.Paneles.Barra_de_herramientas;
import com.cc.paint3D.Graficador3D.Paneles.Panel_de_mas_menos;

import com.cc.paint3D.Graficador3D.Eventos_teclado.Mi_KeyAction_Navegador_funcionamiento;
import com.cc.paint3D.Graficador3D.Eventos_teclado.Mi_KeyAction_navegador;

import com.sun.j3d.utils.behaviors.picking.*;
import com.sun.j3d.utils.behaviors.keyboard.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.media.j3d.*;
import javax.swing.JDialog;
import javax.vecmath.*;

import javax.swing.SwingUtilities;
//import com.l2fprod.gui.plaf.skin.Skin;
//import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;


public class Escena extends JDialog
    implements WindowListener, ComponentListener, Props
{
	public static StringBuffer sb = new StringBuffer(60);
    public static Escena handle;
    public static Panel_Principal canvas;
    public static BoundingSphere bounds;
    public static BranchGroup bg;
    public static TransformGroup tg;
    public static Vector vector_de_Componentes;
    public static PickObject po;
    public static int actual;
    public static float scale_actual_x = 0.2F;
    public static float scale_actual_y = 0.2F;
    public static float scale_actual_z = 0.2F;

    public Escena()
    {
        super(Barra.handle, "", false);
        handle = this;
        
        //setDefaultLookAndFeelDecorated(false);
        //setUndecorated(true);
        
        //setLookAndFeel(Barra_de_Menus.MASCARA_DEFECTO);
        
        //setIconImage(ES_Utiles.get_System_Image(Graficador3D.SYS_ICON));
        
        //setResizable(true);
        
        vector_de_Componentes = new Vector();
        actual = Props.NINGUNO;
        getContentPane().setLayout(new BorderLayout());
        bounds = Datos_utiles.boundingSphere;
        java.awt.GraphicsConfiguration graphicsconfiguration = SimpleUniverse.getPreferredConfiguration();
        canvas = new Panel_Principal(graphicsconfiguration);
        getContentPane().add(canvas);
        SimpleUniverse simpleuniverse = new SimpleUniverse(canvas);
        
        simpleuniverse.getViewingPlatform().setNominalViewingTransform();
        
        bg = new BranchGroup();
        
        bg.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);//14
        bg.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);//13
        
        tg = new TransformGroup();
        Transform3D transform3d = new Transform3D();
        transform3d.setScale( new Vector3d(scale_actual_x,scale_actual_y,scale_actual_z) );
        tg.setTransform(transform3d);
        bg.addChild(tg);
        
        po = new PickObject(canvas, bg);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//18
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);//17
        tg.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);//14
        tg.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);//13
        
        //dibujar_ejes( tg );
        
        Mi_KeyAction_navegador keyNav = new Mi_KeyAction_navegador(tg, 1.0f, 5.0f);
        Mi_KeyAction_Navegador_funcionamiento keyNavBeh = new Mi_KeyAction_Navegador_funcionamiento(keyNav);
        keyNavBeh.setSchedulingBounds(bounds);
        tg.addChild(keyNavBeh);
		
		                
        PickRotateBehavior pickrotatebehavior = new PickRotateBehavior(bg, canvas, bounds);
        tg.addChild(pickrotatebehavior);
        PickZoomBehavior pickzoombehavior = new PickZoomBehavior(bg, canvas, bounds);
        tg.addChild(pickzoombehavior);
        PickTranslateBehavior picktranslatebehavior = new PickTranslateBehavior(bg, canvas, bounds);
        tg.addChild(picktranslatebehavior);
        		
		
		Menu_de_color_de_fondo.agregar_Color( new Color(200,200,200) );//color inicial del fondo
		      
        
        simpleuniverse.addBranchGraph(bg);
        
        
        addWindowListener(this);
        addComponentListener(this);
    }

    public static Tipo_componente get_actual()
    {
        System.out.println("Escena:get_actual:actual= "+actual);
        if(actual < 0)
            return null;
        else
            return (Tipo_componente)vector_de_Componentes.elementAt(actual);
    }

    public static void agregar_objeto(Object obj, boolean flag)
    {
        Tipo_componente tipo_componente = (Tipo_componente)obj;
        
System.out.println("Escena:agregar_objeto:tipo_componente= "+tipo_componente);
        
        tipo_componente.agregar_este_a(tg);
        int i = vector_de_Componentes.size();
        tipo_componente.set_Prop(Props.POS, new Integer(i));
        vector_de_Componentes.addElement(obj);
        if(flag)
        {
            actual = i;
            set_actual();
            Panel_de_texto_estado.set_Componente(tipo_componente);
        }
    }

    public static void reemplazar_componente(int i, Tipo_componente tipo_componente)
    {
        if(vector_de_Componentes.size() <= i)
            vector_de_Componentes.addElement(tipo_componente);
        else
            vector_de_Componentes.setElementAt(tipo_componente, i);
        tipo_componente.agregar_este_a(tg);
    }

    public static void borrar_actual()
    {
        if(actual < 0)
        {
            new Dialogo_de_error("No hay objeto seleccionado actualmente");//"No object currently selected"
            return;
        }
        Object obj = vector_de_Componentes.elementAt(actual);
        if(obj == null)
        {
            return;
        } else
        {
            Tipo_componente tipo_componente = (Tipo_componente)obj;
            tipo_componente.separar_este();
            vector_de_Componentes.setElementAt(null, actual);
            des_seleccionar();
            System.gc();
            return;
        }
    }

    public static void borrar_todo()
    {
        int i = vector_de_Componentes.size();
        if(i == 0)
            return;
        for(int j = 0; j < i; j++)
        {
            Object obj = vector_de_Componentes.elementAt(j);
            if(obj != null)
            {
                Tipo_componente tipo_componente = (Tipo_componente)obj;
                tipo_componente.separar_este();
            }
        }

        vector_de_Componentes.removeAllElements();
        des_seleccionar();
        System.gc();
    }
	
	//public static int x_actual=0;	
	//public static int y_actual=0;
		
    public static void seleccionar_Componente(int i, int j)
    {
//        x_actual=i;
//        y_actual=j;
        Panel_Principal.handle.setCursor(Panel_Principal.handle.CROSSHAIR);
        
        Barra_de_herramientas.esconder_menu();
        SceneGraphPath scenegraphpath = po.pickClosest(i, j);//po.pickAny(// po.pickAny(i,j);//
        if(scenegraphpath == null)
        {
            des_seleccionar();
            Panel_de_texto_estado.set_inicial();
            Panel_Principal.handle.setCursor(Panel_Principal.handle.HAND);
            return;
        }
        
        //int n=scenegraphpath.nodeCount();
        //System.out.println("hay "+n+" nodos");
        //for(int i1=0;i1<n;i1++)
        //{
        javax.media.j3d.Node node = scenegraphpath.getNode(0);
        //System.out.println("el nodo "+i1+" es "+node.toString());
        //node=node.cloneNode(true);
                
        if(node instanceof Tipo_componente)
        {
            //System.out.println("node.getBounds"+ null );
            //node.getBounds().set(null);
            
            Tipo_componente tipo_componente = (Tipo_componente)node;
            Integer integer = (Integer)tipo_componente.get_Prop(Props.POS);

            int k = integer.intValue();
            if(k == actual)
                return;
            actual = k;
            set_actual();
            Panel_de_texto_estado.set_Componente(tipo_componente);
            System.gc();
        } else
        if(node instanceof Figura_luz)
            Menu_de_iluminacion.seleccionar_Figura_luz((Figura_luz)node);
		//}    
    }

    public static void set_actual()
    {
        Barra_de_herramientas.set_si_editable();
        Menu_de_propiedades_de_apariencia.set();
        Menu_de_propiedades_de_comportamiento.set();
        Panel_de_mas_menos.off();
    }

    public static void des_seleccionar()
    {
        actual = Props.NINGUNO;
        Barra_de_herramientas.set_no_editable();
        Panel_de_mas_menos.off();
        Panel_de_texto_estado.set_inicial();
    }

    public static void setDimension(Dimension dimension)
    {
        canvas.setSize(dimension);
        Panel_Principal.prefdim = dimension;
        handle.pack();
        checkSize();
        Panel_Principal.prefdim = null;
        handle.repaint();
    }

    public static void checkSize()
    {
        Point point = handle.getLocation();
        Dimension dimension = handle.getSize();
        boolean flag = false;
        if(point.y + dimension.height > Graficador3D.screensize.height)
        {
            dimension.height = Graficador3D.screensize.height - point.y;
            flag = true;
        }
        if(point.x + dimension.width > Graficador3D.screensize.width)
        {
            dimension.width = Graficador3D.screensize.width - point.x;
            flag = true;
        }
        if(flag)
        {
            handle.setSize(dimension);
            handle.invalidate();
            handle.validate();
        }
    }

    public static void setOrig()
    {
        Dimension dimension = canvas.getSize();
        sb.setLength(0);
        sb.append("ancho:").append(dimension.width).append(" * alto:").append(dimension.height);
        handle.setTitle(sb.toString());
    }

    public static void setPos(int i, int j)
    {
        sb.setLength(0);
        sb.append("X: ").append(i + 1).append("  Y: ").append(j + 1);
        handle.setTitle(sb.toString());
    }

    public static void setPos(int i, int j, int k, int l)
    {
        sb.setLength(0);
        sb.append(" X: ").append(i).append(" Y: ").append(j).append("  Ancho: ");
        sb.append(k).append(" Alto: ").append(l);
        handle.setTitle(sb.toString());
    }

    public void componentResized(ComponentEvent componentevent)
    {
        invalidate();
        validate();
        setOrig();
    }

    public void componentMoved(ComponentEvent componentevent)
    {
    }

    public void componentShown(ComponentEvent componentevent)
    {
    }

    public void componentHidden(ComponentEvent componentevent)
    {
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
        Graficador3D.cerrar_Programa();
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowOpened(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }
    
    
    
    public void dibujar_ejes(TransformGroup tg_entrada)
	{
		////////////////////////////////////////////////////////////////////////        
        float lado=0.3f;
        //los ejes plano XY
        Point3f vertices_XY[] = new Point3f[4];
      	vertices_XY[0] = new Point3f (0.0f, 0.0f, 0.0f);
      	vertices_XY[1] = new Point3f (lado, 0.0f, 0.0f);
      	vertices_XY[2] = new Point3f (lado, lado, 0.0f);
      	vertices_XY[3] = new Point3f (0.0f, lado, 0.0f);

		int[] lineas = { 5 };
      	LineStripArray cuadrado_XY = new LineStripArray(5,LineStripArray.COORDINATES|LineStripArray.COLOR_3,lineas);

      	cuadrado_XY.setCoordinate(0, vertices_XY[0]);
      	cuadrado_XY.setCoordinate(1, vertices_XY[1]);
     	cuadrado_XY.setCoordinate(2, vertices_XY[2]);
      	cuadrado_XY.setCoordinate(3, vertices_XY[3]);
      	cuadrado_XY.setCoordinate(4, vertices_XY[0]);
      
      	cuadrado_XY.setColor(0,new Color3f(1.0f,0.0f,0.0f));
      	cuadrado_XY.setColor(1,new Color3f(1.0f,0.0f,0.0f));
      	cuadrado_XY.setColor(2,new Color3f(1.0f,0.0f,0.0f));
      	cuadrado_XY.setColor(3,new Color3f(1.0f,0.0f,0.0f));
		cuadrado_XY.setCapability(LineStripArray.ALLOW_INTERSECT);
      
      	TransformGroup tg_XY = new TransformGroup();
      	tg_XY.addChild(new Shape3D(cuadrado_XY)); 
      	tg_entrada.addChild(tg_XY);
        
        //los ejes plano XZ
        Point3f vertices_XZ[] = new Point3f[4];
      	vertices_XZ[0] = new Point3f (0.0f, 0.0f, 0.0f);
      	vertices_XZ[1] = new Point3f (0.0f, 0.0f, lado);
      	vertices_XZ[2] = new Point3f (lado, 0.0f, lado);
      	vertices_XZ[3] = new Point3f (lado, 0.0f, 0.0f);

		//int[] lineas = { 5 };
      	LineStripArray cuadrado_XZ = new LineStripArray(5,LineStripArray.COORDINATES|LineStripArray.COLOR_3,lineas);

      	cuadrado_XZ.setCoordinate(0, vertices_XZ[0]);
      	cuadrado_XZ.setCoordinate(1, vertices_XZ[1]);
     	cuadrado_XZ.setCoordinate(2, vertices_XZ[2]);
      	cuadrado_XZ.setCoordinate(3, vertices_XZ[3]);
      	cuadrado_XZ.setCoordinate(4, vertices_XZ[0]);
      
      	cuadrado_XZ.setColor(0,new Color3f(0.0f,1.0f,0.0f));
      	cuadrado_XZ.setColor(1,new Color3f(0.0f,1.0f,0.0f));
      	cuadrado_XZ.setColor(2,new Color3f(0.0f,1.0f,0.0f));
      	cuadrado_XZ.setColor(3,new Color3f(0.0f,1.0f,0.0f));
		cuadrado_XZ.setCapability(LineStripArray.ALLOW_INTERSECT);
      
      	TransformGroup tg_XZ = new TransformGroup();
      	tg_XZ.addChild(new Shape3D(cuadrado_XZ)); 
      	tg_entrada.addChild(tg_XZ);
        
        //los ejes plano YZ
        Point3f vertices_YZ[] = new Point3f[4];
      	vertices_YZ[0] = new Point3f (0.0f, 0.0f, 0.0f);
      	vertices_YZ[1] = new Point3f (0.0f, 0.0f, lado);
      	vertices_YZ[2] = new Point3f (0.0f, lado, lado);
      	vertices_YZ[3] = new Point3f (0.0f, lado, 0.0f);

		//int[] lineas = { 5 };
      	LineStripArray cuadrado_YZ = new LineStripArray(5,LineStripArray.COORDINATES|LineStripArray.COLOR_3,lineas);

      	cuadrado_YZ.setCoordinate(0, vertices_YZ[0]);
      	cuadrado_YZ.setCoordinate(1, vertices_YZ[1]);
     	cuadrado_YZ.setCoordinate(2, vertices_YZ[2]);
      	cuadrado_YZ.setCoordinate(3, vertices_YZ[3]);
      	cuadrado_YZ.setCoordinate(4, vertices_YZ[0]);
      
      	cuadrado_YZ.setColor(0,new Color3f(0.0f,0.0f,1.0f));
      	cuadrado_YZ.setColor(1,new Color3f(0.0f,0.0f,1.0f));
      	cuadrado_YZ.setColor(2,new Color3f(0.0f,0.0f,1.0f));
      	cuadrado_YZ.setColor(3,new Color3f(0.0f,0.0f,1.0f));
		cuadrado_YZ.setCapability(LineStripArray.ALLOW_INTERSECT);
      
      	TransformGroup tg_YZ = new TransformGroup();
      	tg_YZ.addChild(new Shape3D(cuadrado_YZ)); 
      	tg_entrada.addChild(tg_YZ);
        
        ////////////////////////////////////////////////////////////////////////	
	}
	
    
	public void setLookAndFeel(String SKIN){
		
		try{
			//Skin skin = SkinLookAndFeel.loadThemePack(SKIN);
			//SkinLookAndFeel.setSkin(skin);
			//SkinLookAndFeel.enable();
			
		}catch(Exception e){ 
			System.err.println(e);
		}
		
		SwingUtilities.updateComponentTreeUI(this);
	}
}