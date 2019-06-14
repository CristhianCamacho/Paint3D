
package com.cc.paint3D.Graficador3D.G3D;

import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.IO.Tipo_de_texto;
import com.cc.paint3D.Graficador3D.IO.Informacion_de_BH;
import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos_temporales;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_de_componente;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_escala_de_componente;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_textura;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_porcentaje;
import com.cc.paint3D.Graficador3D.interfaces.Ajustador_de_Entero;

import java.awt.Color;
import java.io.*;
import java.util.Vector;
import javax.media.j3d.*;
import javax.swing.JPopupMenu;
import javax.vecmath.*;

public abstract class Figura extends TransformGroup
    implements Tipo_componente, Props
{
	
	public static final int ESFERA = 0;
    public static final int CUBO = 1;
    public static final int CAJA = 2;
    public static final int CONO = 3;
    public static final int CILINDRO = 4;
    public static final int TEXTO_3D = 5;
    public static final int TEXTO_2D = 6;
    public static final int TETRAEDRO = 7;
    public static final int TERRENO_FRACTAL = 8;
            
    public BranchGroup bg;
    String nombre;
    int transparencia;
    int brillo;
    Tipo_de_texto textura;
    Color color_difuso;
    Color color_especular;
    Color color_emisivo;
    Color color_ambiente;
    int pos;
    Informacion_de_BH bh;
    
    Menu_de_escala_de_componente menu_de_escala_de_componente;
    
    public float escala_x=1f;
    public float escala_y=1f;
    public float escala_z=1f;

    public Figura()
    {
        transparencia = Props.NINGUNO;
        brillo = Props.NINGUNO;
        setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        
        setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        
        /*
        ////////////////////////////////////////////////////////////////////////       
        setCapability(TransformGroup.ALLOW_AUTO_COMPUTE_BOUNDS_READ);
        setCapability(TransformGroup.ALLOW_AUTO_COMPUTE_BOUNDS_WRITE);
        setCapability(TransformGroup.ALLOW_BOUNDS_READ);
        setCapability(TransformGroup.ALLOW_BOUNDS_WRITE);
        setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        setCapability(TransformGroup.ALLOW_COLLIDABLE_READ);
        setCapability(TransformGroup.ALLOW_COLLIDABLE_WRITE);
        setCapability(TransformGroup.ALLOW_COLLISION_BOUNDS_READ);
        setCapability(TransformGroup.ALLOW_COLLISION_BOUNDS_WRITE);
        setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
        setCapability(TransformGroup.ALLOW_PICKABLE_READ);
        setCapability(TransformGroup.ALLOW_PICKABLE_WRITE);
        setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        setCapability(TransformGroup.ENABLE_COLLISION_REPORTING);
        setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        ////////////////////////////////////////////////////////////////////////   
        */
        
        bg = new BranchGroup();
        bg.setCapability(BranchGroup.ALLOW_DETACH);
        /*
        bg.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        bg.setCapability(BranchGroup.ALLOW_BOUNDS_WRITE);
        bg.setCapability(BranchGroup.ALLOW_COLLIDABLE_WRITE);
        bg.setCapability(BranchGroup.ALLOW_COLLISION_BOUNDS_WRITE);
        bg.setCapability(BranchGroup.ALLOW_PICKABLE_WRITE);
        */
bg.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        //bg.setCapability(BranchGroup.);
        //bg.setCapability(BranchGroup.);
                
        /*bg.setCapability(10);
        bg.setCapability(3);
        bg.setCapability(4);
        bg.setCapability(14);
        bg.setCapability(12);
        bg.setCapability(13);
        bg.setCapability(7);
        bg.setCapability(15);
        bg.setCapability(17);
        bg.setCapability(11);
        bg.setCapability(5);
        bg.setCapability(6);
        bg.setCapability(0);
        bg.setCapability(1);
        */
        bg.addChild(this);
    }

    public void agregar_este_a(TransformGroup transformgroup)
    {
        transformgroup.addChild(bg);
    }

    public abstract void agregar_al_Menu(JPopupMenu jpopupmenu);

    public void separar_este()
    {
        bg.detach();
    }

    public void final_agregar_nodo(Object obj)
    {
        addChild(get_Node(Color_utiles.iniciar_Appearance()));
    }

    public abstract Node get_Node(Appearance appearance);

    public Object get_Prop(int i)
    {
        switch(i)
        {
        case Props.NECESITA_TEXTO: 
            return new Integer(-1);

        case Props.NOMBRE: 
            {	System.out.println("Figura:get_Prop:nombre= "+nombre);
            	
            	return nombre;
            }
        case Props.POS: 
            return new Integer(pos);

        case Props.TRANSPARENCIA: 
            if(transparencia >= 0)
                return new Integer(transparencia);
            else
                return new Integer(0);

        case Props.TEXTURA: 
            return textura;

        case Props.BEHAVIOUR: 
            return bh;

        case Props.MATERIAL: 
            Appearance appearance = (Appearance)get_Prop(Props.APPEARANCE);
            return appearance.getMaterial();

        case Props.BRILLO: 
            int j = (int)((Material)get_Prop(Props.MATERIAL)).getShininess();
            return new Integer(j);

        case Props.APPEARANCE: 
        case Props.ESCALA_ESCENA_X: 
        
        case Props.HEIGHT: 
        case Props.WIDTH: 
        case Props.X_DIVISIONES: 
        case Props.Y_DIVISIONES: 
        case Props.LENGTH: 
        case Props.FONT_NOMBRE: 
        case Props.FONT_ESTILO: 
        case Props.FONT_TAMANIO:
        case Props.DENSIDAD: 
        case Props.ALINEAMIENTO: 
        default:
            return null;
        }
    }

    public void leer_Objeto(DataInputStream datainputstream)
        throws IOException
    {
        leer_SubObjeto(datainputstream);
        Color_utiles.leer_Transform3D(datainputstream, this);
        if((transparencia = datainputstream.readInt()) >= 0)
            set_Prop(Props.TRANSPARENCIA, new Integer(transparencia));
        if((brillo = datainputstream.readInt()) >= 0)
            set_Prop(Props.BRILLO, new Integer(brillo));
        if((color_difuso = Color_utiles.leer_Color(datainputstream)) != null)
            set_Prop(Props.DIFUSO, color_difuso);
        if((color_especular = Color_utiles.leer_Color(datainputstream)) != null)
            set_Prop(Props.ESPECULAR, color_especular);
        if((color_emisivo = Color_utiles.leer_Color(datainputstream)) != null)
            set_Prop(Props.EMISIVO, color_emisivo);
        if((color_ambiente = Color_utiles.leer_Color(datainputstream)) != null)
            set_Prop(Props.AMBIENTE, color_ambiente);
        String s = datainputstream.readUTF();
        if(!s.equals(""))
            set_Prop(Props.TEXTURA, new Tipo_de_texto(s, datainputstream.readInt()));
        if(datainputstream.readBoolean())
          {
          	bh = Informacion_de_BH.leer_BH(datainputstream);
System.out.println("Figura:leer_Objeto:bh.tipo= "+bh.tipo);          	
          }
    }

    abstract void leer_SubObjeto(DataInputStream datainputstream)
        throws IOException;

    public void set_Menu(JPopupMenu jpopupmenu)
    {
        jpopupmenu.add(new Menu_de_color_de_componente());
        jpopupmenu.add(menu_de_escala_de_componente = new Menu_de_escala_de_componente());
        jpopupmenu.add(new Menu_de_textura());
        jpopupmenu.add(new Ajustador_de_porcentaje(Props.TRANSPARENCIA, "Transparencia"));
        jpopupmenu.add(new Ajustador_de_Entero(Props.BRILLO, "Brillo", 1, 128));
        jpopupmenu.addSeparator();
        agregar_al_Menu(jpopupmenu);
    }

    public void set_Prop(int i, Object obj)
    {
        switch(i)
        {
        case Props.TEXTO: 
        case Props.COLOR: 
        case Props.APPEARANCE: 
        case Props.ESCALA_ESCENA_X:         
        case Props.HEIGHT: 
        case Props.WIDTH: 
        case Props.X_DIVISIONES: 
        case Props.Y_DIVISIONES: 
        case Props.LENGTH: 
        case Props.FONT_NOMBRE: 
        case Props.FONT_ESTILO: 
        case Props.FONT_TAMANIO:
        case Props.DENSIDAD: 
        case Props.ALINEAMIENTO: 
        case Props.MATERIAL: 
        case Props.NECESITA_TEXTO: 
        default:
            break;

        case Props.NOMBRE: 
            nombre = (String)obj;
            break;

        case Props.POS: 
            Integer integer = (Integer)obj;
            pos = integer.intValue();
            break;

        case Props.DIFUSO: 
            color_difuso = (Color)obj;
            ((Material)get_Prop(Props.MATERIAL)).setDiffuseColor(Manejador_de_archivos_temporales.getColor3f(color_difuso));
            break;

        case Props.ESPECULAR: 
            color_especular = (Color)obj;
            ((Material)get_Prop(Props.MATERIAL)).setSpecularColor(Manejador_de_archivos_temporales.getColor3f(color_especular));
            break;

        case Props.EMISIVO: 
            color_emisivo = (Color)obj;
            ((Material)get_Prop(Props.MATERIAL)).setEmissiveColor(Manejador_de_archivos_temporales.getColor3f(color_emisivo));
            break;

        case Props.AMBIENTE: 
            color_ambiente = (Color)obj;
            ((Material)get_Prop(Props.MATERIAL)).setAmbientColor(Manejador_de_archivos_temporales.getColor3f(color_ambiente));
            break;
		
/*		case Props.ENMALLADO:
			if(((boolean)(obj)))
			{
				PolygonAttributes pa = new PolygonAttributes();
				pa.setPolygonMode(pa.POLYGON_LINE);
				pa.setCullFace(PolygonAttributes.CULL_NONE);
						
				((Appearance)(get_Prop(Props.APPEAREANCE))).setPolygonAttributes(pa);	
			}
			else
			{
			PolygonAttributes pa = new PolygonAttributes();
			pa.setPolygonMode(pa.POLYGON_FILL);
			pa.setCullFace(PolygonAttributes.CULL_NONE);
						
			((Appearance)(get_Prop(Props.APPEAREANCE))).setPolygonAttributes(pa);	
			}
*/			
        case Props.TEXTURA: 
            textura = Color_utiles.set_TEXTURAS((Tipo_de_texto)obj, (Appearance)get_Prop(Props.APPEARANCE));
            break;

        case Props.TRANSPARENCIA: 
            transparencia = Color_utiles.get_Transparencia(obj, (Appearance)get_Prop(Props.APPEARANCE));
            break;

        case Props.BRILLO: 
            brillo = ((Integer)obj).intValue();
            ((Material)get_Prop(Props.MATERIAL)).setShininess(brillo);
            break;

        case Props.BEHAVIOUR: 
            bh = (Informacion_de_BH)obj;
            break;

        case Props.FILES: 
            if(textura != null)
            {
                Vector vector = (Vector)obj;
                vector.addElement(textura.path);
            }
            break;
        }
    }

    public void escribir_Objeto(DataOutputStream dataoutputstream)
        throws IOException
    {
System.out.println("Figura:escribir_Objeto:");
         
        dataoutputstream.writeUTF(getClass().getName().substring( "com.cc.paint3D.Graficador3D.G3D.".length() ));
        dataoutputstream.writeUTF(nombre);
        
        escribir_SubObjeto(dataoutputstream);
        Color_utiles.escribir_Transform3D(dataoutputstream, this);
        dataoutputstream.writeInt(transparencia);
        dataoutputstream.writeInt(brillo);
        Color_utiles.escribir_Color(dataoutputstream, color_difuso);
        Color_utiles.escribir_Color(dataoutputstream, color_especular);
        Color_utiles.escribir_Color(dataoutputstream, color_emisivo);
        Color_utiles.escribir_Color(dataoutputstream, color_ambiente);
        if(textura == null)
            dataoutputstream.writeUTF("");
        else
            textura.escribir_texto(dataoutputstream);
        if(bh == null)
            dataoutputstream.writeBoolean(false);
        else
            {
            	
System.out.println("Figura:escribir_Objeto:bh.tipo= "+	bh.tipo);
System.out.println("**************/bh.escribir_BH/*****************");            	
            	bh.escribir_BH(dataoutputstream);
        	}
    }

    public abstract void escribir_SubObjeto(DataOutputStream dataoutputstream)
        throws IOException;

    
    
    
    
    
    BranchGroup bg_linea=null;
    public void dibujar_lineas(Point3f[] puntos)
    {
System.out.println("Escena:dibujar_lineas ");                	
    	
    	if(bg_linea!=null)
		removeChild(bg_linea);
		
		if(puntos==null)
		return;
    			
		if(puntos.length==0)
    	return;
    	
    	int[] lineas = { puntos.length +4 +3 };
      	LineStripArray lineas3D = new LineStripArray(puntos.length + 4 +3,LineStripArray.COORDINATES|LineStripArray.COLOR_3,lineas);
		
		//Figura.dibujar_lineas , Panel_patron.crear_LINEA
		///////////////////cola de flecha//////////////////////////////////////
			float x0 = puntos[1].x - puntos[0].x;
			float y0 = puntos[1].y - puntos[0].y;
	
			float x0u = (float) ( x0/Math.pow( Math.pow(x0,2) + Math.pow(y0,2) , 0.5 ) );
			float y0u = (float) ( y0/Math.pow( Math.pow(x0,2) + Math.pow(y0,2) , 0.5 ) );
		
			float salto0=0.08f;
			
			float p02_x=puntos[0].x-salto0*(y0u)-salto0*(x0u);
			float p02_y=puntos[0].y-salto0*(-x0u)-salto0*(y0u);
			
			float p03_x=puntos[0].x-salto0*(-y0u)-salto0*(x0u);
			float p03_y=puntos[0].y-salto0*(x0u)-salto0*(y0u);
			
			lineas3D.setCoordinate(0, puntos[0]);
			lineas3D.setColor(0 , new Color3f(1.0f,0.0f,0.0f));
			
			lineas3D.setCoordinate(1, new Point3f(p02_x,p02_y,0f));
			lineas3D.setColor(1 , new Color3f(1.0f,0.0f,0.0f));
			
			lineas3D.setCoordinate(2, new Point3f(p03_x,p03_y,0f));
			lineas3D.setColor(2 , new Color3f(1.0f,0.0f,0.0f));
				
		///////////////////////////////////////////////////////////////////////
				
		for(int i=0;i<puntos.length;i++)
		{
		lineas3D.setCoordinate(i+3, puntos[i]);
		lineas3D.setColor(i+3,new Color3f(1.0f,0.0f,0.0f));
		}
		//Figura.dibujar_lineas , Panel_patron.crear_LINEA
		///////////////////punta de flecha//////////////////////////////////////
		
			float xn = puntos[puntos.length-1].x - puntos[puntos.length-2].x;
			float yn = puntos[puntos.length-1].y - puntos[puntos.length-2].y;
		
			float xnu = (float) ( xn/Math.pow( Math.pow(xn,2) + Math.pow(yn,2) , 0.5 ) );
			float ynu = (float) ( yn/Math.pow( Math.pow(xn,2) + Math.pow(yn,2) , 0.5 ) );
		
			float salto=0.08f;
		
			float p2_x=puntos[puntos.length-1].x+salto*(ynu);
			float p2_y=puntos[puntos.length-1].y+salto*(-xnu);
		
			float p3_x=puntos[puntos.length-1].x+salto*(xnu);
			float p3_y=puntos[puntos.length-1].y+salto*(ynu);
		
			float p4_x=puntos[puntos.length-1].x+salto*(-ynu);
			float p4_y=puntos[puntos.length-1].y+salto*(xnu);
		
		lineas3D.setCoordinate( 3+puntos.length , new Point3f(p2_x,p2_y,0f) );
		lineas3D.setColor(3+puntos.length , new Color3f(1.0f,0.0f,0.0f));
		
		lineas3D.setCoordinate( 3+puntos.length+1 , new Point3f(p3_x,p3_y,0f) );
		lineas3D.setColor(3+puntos.length+1 , new Color3f(1.0f,0.0f,0.0f));
		
		lineas3D.setCoordinate( 3+puntos.length+2 , new Point3f(p4_x,p4_y,0f) );
		lineas3D.setColor(3+puntos.length+2 , new Color3f(1.0f,0.0f,0.0f));
		
		lineas3D.setCoordinate( 3+puntos.length+3 , puntos[puntos.length-1] );
		lineas3D.setColor(3+puntos.length+3 , new Color3f(1.0f,0.0f,0.0f));		
				
		////////////////////////////////////////////////////////////////////////
		
		//lineas3D.setCapability(LineStripArray.ALLOW_INTERSECT);	
		/*
		TransformGroup tg_linea= new TransformGroup();
		tg_linea.addChild(new Shape3D(lineas3D));		
		tg_linea.setPickable(false);
		tg_linea
		*/
		//( (TransformGroup) bg.getParent()).addChild(tg_linea);
						
		//((Graficador_3D.G3D.Figura)get_actual()).bg.addChild(tg_linea); 
		
		 
		bg_linea = null;
		bg_linea = new BranchGroup();
		bg_linea.setCapability(BranchGroup.ALLOW_DETACH);
		bg_linea.setPickable(false);
		
		bg_linea.addChild(new Shape3D(lineas3D));
		
		int[] linea_eje_y = { 2 + 4 };
      	LineStripArray linea3D_eje_y = new LineStripArray( 2 + 4 ,LineStripArray.COORDINATES|LineStripArray.COLOR_3,linea_eje_y);
		linea3D_eje_y.setCoordinate( 0 , new Point3f(0f,-1.5f,0f) );
		linea3D_eje_y.setColor(0 , new Color3f(0.0f,0.0f,0.0f));		
		linea3D_eje_y.setCoordinate( 1 , new Point3f(0f,1.5f,0f) );
		linea3D_eje_y.setColor(1 , new Color3f(0.0f,0.0f,0.0f));
		//punta de flecha
		linea3D_eje_y.setCoordinate( 2 , new Point3f(-salto,1.5f,0f) );
		linea3D_eje_y.setColor(2 , new Color3f(0.0f,0.0f,0.0f));
		linea3D_eje_y.setCoordinate( 3 , new Point3f(0f,1.5f+salto,0f) );
		linea3D_eje_y.setColor(3 , new Color3f(0.0f,0.0f,0.0f));
		linea3D_eje_y.setCoordinate( 4 , new Point3f(salto,1.5f,0f) );
		linea3D_eje_y.setColor(4 , new Color3f(0.0f,0.0f,0.0f));
		linea3D_eje_y.setCoordinate( 5 , new Point3f(0f,1.5f,0f) );
		linea3D_eje_y.setColor(5 , new Color3f(0.0f,0.0f,0.0f));
		//////////////////				
		bg_linea.addChild(new Shape3D(linea3D_eje_y));
		
		int[] linea_eje_x = { 2 + 4 };
      	LineStripArray linea3D_eje_x = new LineStripArray( 2 + 4 ,LineStripArray.COORDINATES|LineStripArray.COLOR_3,linea_eje_x);
		linea3D_eje_x.setCoordinate( 0 , new Point3f(-2f,0f,0f) );
		linea3D_eje_x.setColor(0 , new Color3f(0.0f,0.0f,0.0f));		
		linea3D_eje_x.setCoordinate( 1 , new Point3f(2f,0f,0f) );
		linea3D_eje_x.setColor(1 , new Color3f(0.0f,0.0f,0.0f));		
		//punta de flecha
		linea3D_eje_x.setCoordinate( 2 , new Point3f(2f,salto,0f) );
		linea3D_eje_x.setColor(2 , new Color3f(0.0f,0.0f,0.0f));
		linea3D_eje_x.setCoordinate( 3 , new Point3f(2f+salto,0f,0f) );
		linea3D_eje_x.setColor(3 , new Color3f(0.0f,0.0f,0.0f));
		linea3D_eje_x.setCoordinate( 4 , new Point3f(2f,-salto,0f) );
		linea3D_eje_x.setColor(4 , new Color3f(0.0f,0.0f,0.0f));
		linea3D_eje_x.setCoordinate( 5 , new Point3f(2f,0f,0f) );
		linea3D_eje_x.setColor(5 , new Color3f(0.0f,0.0f,0.0f));
		//////////////////
		bg_linea.addChild(new Shape3D(linea3D_eje_x));
		
      	//bg.addChild(tg_linea);
      	//bg.addChild(bg_linea);
      	
      	addChild(bg_linea);  
    }
    
}