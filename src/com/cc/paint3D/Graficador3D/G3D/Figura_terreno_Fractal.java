
package com.cc.paint3D.Graficador3D.G3D;

import com.cc.paint3D.Graficador3D.Menus.JMI_Ajustador_de_real;
import com.cc.paint3D.Graficador3D.interfaces.Props;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Stripifier;
import com.sun.j3d.utils.geometry.Triangulator;

import java.io.*;
import java.awt.geom.*;
import java.util.Vector;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.util.*;

import javax.swing.JPopupMenu;


public class Figura_terreno_Fractal extends Figura
{
	float lado;
	public int numero_de_lados;	
	public int divisiones;
	
	Appearance app;
	Shape3D s3d_fractal;
	
		
    public Figura_terreno_Fractal()
    {
        lado=1.0f;
        numero_de_lados=6;        
    }

    public void agregar_al_Menu(JPopupMenu jpopupmenu)
    {
        jpopupmenu.add(new JMI_Ajustador_de_real(Props.HEIGHT, "lado"));        
        jpopupmenu.add(new JMI_Ajustador_de_real(Props.WIDTH, "nivel"));        
    }

    public Node get_Node(Appearance appearance)
    {
    app=appearance;
    
    s3d_fractal=createFractal(lado ,app);   
    
    return s3d_fractal;            
    }
	
	public static Shape3D createFractal(float lado_, Appearance appearance)
	{
	Shape3D s3d_tetra=new Shape3D();	
		
	//int i;
	int divisiones=10;   
    int numero_de_lados=4;
    
    int nro_particulas=numero_de_lados;
    double radio=lado_;
    double salto=0.05;
    int DireccionSimetrica=2;
    
    double x[]=new double[nro_particulas];
  	double y[]=new double[nro_particulas];
  	double xa[]=new double[nro_particulas];
  	double ya[]=new double[nro_particulas];
  	Vector v_de_particulas[]=new Vector[nro_particulas];
  	
  	int h=0;
  	int k=0;
  	
  	double xc[]=new double[nro_particulas];
  	double yc[]=new double[nro_particulas];
  	Vector v_de_centros[]=new Vector[nro_particulas];
  	
  		
  	    
    //vertices
    for (int i=0;i<nro_particulas;i++)
	{
    x[i]=( radio*(Math.cos(((i-1)%nro_particulas)*(2*Math.PI/nro_particulas))+(Math.pow((-1.0),(double)DireccionSimetrica))*Math.sin(((i-1)%nro_particulas)*(2*Math.PI/nro_particulas)))+(double)h) ;//izq	
	y[i]=( radio*(Math.cos(((i-1)%nro_particulas)*(2*Math.PI/nro_particulas))-(Math.pow((-1.0),(double)DireccionSimetrica))*Math.sin(((i-1)%nro_particulas)*(2*Math.PI/nro_particulas)))+(double)k) ;
				
	xa[i]=radio*(Math.cos(((i-1)%nro_particulas)*(2*Math.PI/nro_particulas))-(Math.pow((-1.0),(double)DireccionSimetrica))*Math.sin(((i-1)%nro_particulas)*(2*Math.PI/nro_particulas)))+(double)h;	//der
	ya[i]=radio*(Math.cos(((i-1)%nro_particulas)*(2*Math.PI/nro_particulas))+(Math.pow((-1.0),(double)DireccionSimetrica))*Math.sin(((i-1)%nro_particulas)*(2*Math.PI/nro_particulas)))+(double)k;
	
	v_de_particulas[i]=new Vector();
	v_de_particulas[i].add(new Point2D.Double( xa[i] , ya[i] ));
	
	xc[i]=x[i]+(0.5*(x[(i+1)%nro_particulas]-x[i]));
	yc[i]=y[i]+(0.5*(y[(i+1)%nro_particulas]-y[i]));
	
	v_de_centros[i]=new Vector();
	v_de_centros[i].add(new Point2D.Double( xc[i] , yc[i] ));		
	}
    
    
	//algoritmo de persecucion
	boolean condicion=true;
	while(condicion)
  	{
  			for(int i=0;i<nro_particulas;i++)
  			{
  				double auxx=Math.pow((xa[(i+1)%xa.length]-xa[i]),2)+Math.pow((ya[(i+1)%ya.length]-ya[i]),2);
  			
  				double xu=(xa[(i+1)%xa.length]-xa[i])/(Math.pow(auxx,0.5));
				double yu=(ya[(i+1)%ya.length]-ya[i])/(Math.pow(auxx,0.5));
				
				
				xa[i]=xa[i]+xu*salto;
				ya[i]=ya[i]+yu*salto;
				
				Point2D punto = new Point2D.Double( xa[i] , ya[i] );
				v_de_particulas[i].add(punto);
				
	xc[i]=xa[i]+(0.5*(xa[(i+1)%nro_particulas]-xa[i]));
	yc[i]=ya[i]+(0.5*(ya[(i+1)%nro_particulas]-ya[i]));
	Point2D punto1 = new Point2D.Double( xc[i] , yc[i] );
	v_de_centros[i].add(punto1);
										
			if( (xa[i]==xa[(i+1)%xa.length])&&(ya[i]==ya[(i+1)%ya.length]) )//|| xa[i]<0.001 )
				{
					condicion=false;					
				}				
			}							
  	}	
////////////////////////////////////////////////////////////////////////////////  	
  /*	int n=v_de_particulas.length*v_de_particulas[0].size();
  	
  	int[] lineas = { n };
    LineStripArray lineas3D = new LineStripArray(n,LineStripArray.COORDINATES|LineStripArray.COLOR_3,lineas);
	
	int cont=0;
	for(int i=0;i<v_de_particulas.length;i++)
	{
	for(int j=0;j<v_de_particulas[i].size();j++)
	{
		//Point2D punto = (Point2D)(v_de_particulas[i].get(j));
				
		Point2D punto = (Point2D)(v_de_centros[i].get(j));		
				
		Point3f p=new Point3f((float)punto.getX(),(float)punto.getY(),0f); 
		
		lineas3D.setCoordinate(cont, p);
		lineas3D.setColor(cont,new Color3f(1.0f,0.0f,0.0f));
		
		cont++;
	}
	}*/	
////////////////////////////////////////////////////////////////////////////////  	



 	
 
	double salto_PI=2*Math.PI/divisiones;
	
	Vector v_de_todos_puntos=new Vector();
	
	for(int i=0;i<nro_particulas;i++)//-nro_particulas+1
  	{  		
  		//Enumeration e=v_de_particulas[i].elements();
  		//Enumeration e1=v_de_centros[i].elements();
  		
  		//while (e.hasMoreElements())
        for(int l=5;l<v_de_particulas[i].size()-5;l+=1)
        {
        	//Point2D punto = (Point2D)e.nextElement();	
        	//Point2D punto1 = (Point2D)e1.nextElement();
        	Point2D punto = (Point2D)v_de_particulas[i].get(l);
        	Point2D punto1 = (Point2D)v_de_centros[i].get(l);
        	
        	Point2D punto2 = (Point2D)v_de_particulas[i].get((l+1)%v_de_particulas[i].size());
        	Point2D punto3 = (Point2D)v_de_centros[i].get((l+1)%v_de_particulas[i].size());
        	
        	Point2D punto4 = (Point2D)v_de_particulas[i].get((l+2)%v_de_particulas[i].size());
        	Point2D punto5 = (Point2D)v_de_centros[i].get((l+2)%v_de_particulas[i].size());
        	
        	//Point2D punto6 = (Point2D)v_de_particulas[i].get((l+3)%v_de_particulas[i].size());
        	//Point2D punto7 = (Point2D)v_de_centros[i].get((l+3)%v_de_particulas[i].size());
        	        	
        	//double d=punto.distance(punto1);
        	
        	for (int j=0;j<divisiones;j++)
        		{        			        			
        			
        			double d=punto.distance(punto1);
        			
        			double z_temp=punto1.getX()+d*Math.cos((j%divisiones)*salto_PI);
        			double y_temp=punto1.getY()+d*Math.sin((j%divisiones)*salto_PI);
        			
        			v_de_todos_puntos.add( new Point3f( (float)punto1.getX(),(float)y_temp,(float)z_temp) );
        			
        			double z_temp1=punto1.getX()+d*Math.cos(((j+1)%divisiones)*salto_PI);
        			double y_temp1=punto1.getY()+d*Math.sin(((j+1)%divisiones)*salto_PI);
        			
        			v_de_todos_puntos.add( new Point3f( (float)punto1.getX(),(float)y_temp1,(float)z_temp1) );
        			//v_de_todos_puntos.add( new Point3f( (float)punto1.getX(), (float)punto1.getY() ,0f) );
        			//////////////////////////////////////////////////////////// 
        			/*z_temp=punto1.getX()+d*Math.cos(((j+2)%divisiones)*salto_PI);
        			y_temp=punto1.getY()+d*Math.sin(((j+2)%divisiones)*salto_PI);
        			
        			v_de_todos_puntos.add( new Point3f( (float)punto1.getX(),(float)y_temp,(float)z_temp) );
        			
        			z_temp1=punto1.getX()+d*Math.cos(((j+1)%divisiones)*salto_PI);
        			y_temp1=punto1.getY()+d*Math.sin(((j+1)%divisiones)*salto_PI);
        			
        			v_de_todos_puntos.add( new Point3f( (float)punto1.getX(),(float)y_temp1,(float)z_temp1) );
        			*/
        			//////////////////////////////////////////////////////////// 
        			        			        			  			
        			double d1=punto2.distance(punto3);
        			
        			double z_temp2=punto3.getX()+d1*Math.cos(((j)%divisiones)*salto_PI);
        			double y_temp2=punto3.getY()+d1*Math.sin(((j)%divisiones)*salto_PI);
        			 
        			v_de_todos_puntos.add( new Point3f( (float)punto3.getX(),(float)y_temp2,(float)z_temp2) );
        			
        			double z_temp3=punto3.getX()+d1*Math.cos(((j+1)%divisiones)*salto_PI);
        			double y_temp3=punto3.getY()+d1*Math.sin(((j+1)%divisiones)*salto_PI);
        			        			       			        				        			
					v_de_todos_puntos.add( new Point3f( (float)punto3.getX(),(float)y_temp3,(float)z_temp3) );
					//v_de_todos_puntos.add( new Point3f( (float)punto3.getX(), (float)punto3.getY() ,0f) );
        			////////////////////////////////////////////////////////////        			
        			/*
        			d1=punto2.distance(punto3);
        			
        			z_temp2=punto3.getX()+d1*Math.cos(((j+2)%divisiones)*salto_PI);
        			y_temp2=punto3.getY()+d1*Math.sin(((j+2)%divisiones)*salto_PI);
        			 
        			v_de_todos_puntos.add( new Point3f( (float)punto3.getX(),(float)y_temp2,(float)z_temp2) );
        			
        			z_temp3=punto3.getX()+d1*Math.cos(((j+1)%divisiones)*salto_PI);
        			y_temp3=punto3.getY()+d1*Math.sin(((j+1)%divisiones)*salto_PI);
        			        			       			        				        			
					v_de_todos_puntos.add( new Point3f( (float)punto3.getX(),(float)y_temp3,(float)z_temp3) );
					*/
        			////////////////////////////////////////////////////////////        			
        			
        			double d2=punto4.distance(punto5);
        			
        			double z_temp4=punto5.getX()+d2*Math.cos(((j)%divisiones)*salto_PI);
        			double y_temp4=punto5.getY()+d2*Math.sin(((j)%divisiones)*salto_PI);
        			 
        			v_de_todos_puntos.add( new Point3f( (float)punto5.getX(),(float)y_temp4,(float)z_temp4) );
        			
        			double z_temp5=punto5.getX()+d2*Math.cos(((j+1)%divisiones)*salto_PI);
        			double y_temp5=punto5.getY()+d2*Math.sin(((j+1)%divisiones)*salto_PI);
        			        			       			        				        			
					v_de_todos_puntos.add( new Point3f( (float)punto5.getX(),(float)y_temp5,(float)z_temp5) );
					//v_de_todos_puntos.add( new Point3f( (float)punto5.getX(), (float)punto5.getY() ,0f) );
        			////////////////////////////////////////////////////////////        			
        			/*
        			z_temp4=punto5.getX()+d2*Math.cos(((j+2)%divisiones)*salto_PI);
        			y_temp4=punto5.getY()+d2*Math.sin(((j+2)%divisiones)*salto_PI);
        			 
        			v_de_todos_puntos.add( new Point3f( (float)punto5.getX(),(float)y_temp4,(float)z_temp4) );
        			
        			z_temp5=punto5.getX()+d2*Math.cos(((j+1)%divisiones)*salto_PI);
        			y_temp5=punto5.getY()+d2*Math.sin(((j+1)%divisiones)*salto_PI);
        			        			       			        				        			
					v_de_todos_puntos.add( new Point3f( (float)punto5.getX(),(float)y_temp5,(float)z_temp5) );
					*/
					////////////////////////////////////////////////////////////
        			 			
        			/*
        			double d3=punto6.distance(punto7);
        			
        			double z_temp6=punto6.getX()+d3*Math.cos(((j)%divisiones)*salto_PI);
        			double y_temp6=punto6.getY()+d3*Math.sin(((j)%divisiones)*salto_PI);
        			 
        			v_de_todos_puntos.add( new Point3f( (float)punto7.getX(),(float)y_temp6,(float)z_temp6) );
        			
        			double z_temp7=punto6.getX()+d3*Math.cos(((j+1)%divisiones)*salto_PI);
        			double y_temp7=punto6.getY()+d3*Math.sin(((j+1)%divisiones)*salto_PI);
        			        			       			        				        			
					v_de_todos_puntos.add( new Point3f( (float)punto7.getX(),(float)y_temp7,(float)z_temp7) );
					//v_de_todos_puntos.add( new Point3f( (float)punto7.getX(), (float)punto7.getY() ,0f) );
        			*/        		
        		}        		
        }	
  	}	
	
	
	   
    
    	    	
    	TexCoord2f texCoord[] = {       new TexCoord2f(0.0f, 0.0f),
										new TexCoord2f(1.0f, 0.0f),
        								new TexCoord2f(0.5f, 1.0f)        							
    							};
    
    	System.out.println( "Figura_Fractal------------->"+v_de_todos_puntos.size() );
    	
    	//javax.media.j3d.QuadArray
    	
    	javax.media.j3d.QuadArray tetra=new QuadArray(2*v_de_todos_puntos.size(), QuadArray.COORDINATES |
		QuadArray.NORMALS | QuadArray.TEXTURE_COORDINATE_2);
		
    	/*
    	TriangleArray tetra = new TriangleArray(2*v_de_todos_puntos.size(), TriangleArray.COORDINATES |
		TriangleArray.NORMALS | TriangleArray.TEXTURE_COORDINATE_2);
		*/
		for (int i = 0; i < (v_de_todos_puntos.size()/(3)) ; i++)
        {
        	Point3f[] point3f_=new Point3f[3];
				
			point3f_[0] = (Point3f) v_de_todos_puntos.get(3*i+0); 
			point3f_[1] = (Point3f) v_de_todos_puntos.get(3*i+1); 
			point3f_[2] = (Point3f) v_de_todos_puntos.get(3*i+2);
        tetra.setCoordinates(2*i, point3f_);		
        	/*	{
         		Vector3f normal = new Vector3f();
         		Vector3f v1 = new Vector3f();
		 		Vector3f v2 = new Vector3f();
		 		v1.sub(point3f_[0], point3f_[1]);
	     		v2.sub(point3f_[0], point3f_[2]);			
				normal.cross(v1, v2);
	    		normal.normalize();
	    		tetra.setNormal(3*i+0, normal);
	    		tetra.setNormal(3*i+1, normal);	
	    		tetra.setNormal(3*i+2, normal);		
	    		}
	    	�*/	
        ///tetra.setCoordinates(i, point3f_);	
		
			
			Point3f[] point3f_1=new Point3f[3];
			
			point3f_1[2] = (Point3f) v_de_todos_puntos.get(3*i+0); 
			point3f_1[1] = (Point3f) v_de_todos_puntos.get(3*i+1); 
			point3f_1[0] = (Point3f) v_de_todos_puntos.get(3*i+2);
        tetra.setCoordinates(2*i+1, point3f_1);	
        	/*	
        		{
        		Vector3f normal = new Vector3f();
         		Vector3f v1 = new Vector3f();
		 		Vector3f v2 = new Vector3f();
		 		v1.sub(point3f_1[0], point3f_1[1]);
	     		v2.sub(point3f_1[0], point3f_1[2]);			
				normal.cross(v1, v2);
	    		normal.normalize();
	    		tetra.setNormal(3*(i)+0, normal);
	    		tetra.setNormal(3*(i)+1, normal);	
	    		tetra.setNormal(3*(i)+2, normal);
        		}
			*/
			
		}
		
        for (int i = 0; i < v_de_todos_puntos.size(); i++)
        {
            tetra.setTextureCoordinate(0, i, texCoord[i%3]);
        }
		
		

		
		int face;
		Vector3f normal = new Vector3f();
		Vector3f v1 = new Vector3f();
		Vector3f v2 = new Vector3f();
		Point3f [] pts = new Point3f[3];
		for (int i = 0; i < 3; i++) pts[i] = new Point3f();

		for (face = 0; face < (v_de_todos_puntos.size()/3); face++)
			{
	    		tetra.getCoordinates(face*3, pts);
	    		v1.sub(pts[0], pts[1]);
	    		v2.sub(pts[1], pts[2]);
	    		normal.cross(v1, v2);
	    		normal.normalize();
	    		for (int i = 0; i < 3; i++)
	    		{
				tetra.setNormal((face * 3 + i), normal);
	    		}
	    		
			}
		
	
	tetra.setCapability(Geometry.ALLOW_INTERSECT);
//	lineas3D.setCapability(Geometry.ALLOW_INTERSECT);
		
	s3d_tetra.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);	
	
	s3d_tetra.setGeometry(tetra);
	//s3d_tetra.setGeometry(lineas3D);
	
	s3d_tetra.setAppearance(appearance);
	
	return s3d_tetra;	
	}
	
    public Object get_Prop(int i)
    {
        switch(i)
        {
        case Props.APPEARANCE: 
            return app;

        case Props.HEIGHT:
            return new Float(lado);

        case Props.WIDTH:             

        case Props.LENGTH:             

        case Props.ESCALA_ESCENA_X: 
        
        case Props.TRANSPARENCIA: 
        case Props.X_DIVISIONES: 
        case Props.Y_DIVISIONES: 
        default:
            return super.get_Prop(i);
        }
    }

    public void leer_SubObjeto(DataInputStream datainputstream)
        throws IOException
    {
        lado = datainputstream.readFloat();        
        addChild(get_Node(Color_utiles.iniciar_Appearance()));
    }

    public void set_Prop(int i, Object obj)
    {
        switch(i)
        {
        case Props.HEIGHT: 
            Float float1 = (Float)obj;
            lado = float1.floatValue();
            Datos_utiles.reemplazar_actual(this);
            break;

        case Props.WIDTH:
            break;

        case Props.LENGTH:            
            break;
		
		case Props.APPEARANCE:      
            
            s3d_fractal.setAppearance( (Appearance)obj );
            
            
            break;		
        		
        case Props.X_DIVISIONES: 
        case Props.Y_DIVISIONES: 
        default:
            super.set_Prop(i, obj);
            break;
        }
    }

    public void escribir_SubObjeto(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeInt(TERRENO_FRACTAL);
        dataoutputstream.writeFloat(lado);
        
    }
    
}