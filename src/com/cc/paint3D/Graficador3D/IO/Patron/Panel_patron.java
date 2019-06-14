package com.cc.paint3D.Graficador3D.IO.Patron;

import com.cc.paint3D.Graficador3D.Graficador3D;
import com.cc.paint3D.Graficador3D.ES.ES_Dialogo;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;
import com.cc.paint3D.Graficador3D.Botones.Boton_Libreria;

import java.io.*;
import java.util.*;
import java.lang.Thread;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JInternalFrame.JDesktopIcon;

import java.awt.image.BufferedImage;

import javax.vecmath.Point3f;

public class Panel_patron extends ES_Dialogo implements ChangeListener{
	
	JInternalFrame panel_de_controles;		
	public JPanel panel_de_dibujo;
			
	public JTextArea jta_estado;
	
	public JComboBox jcb_tipo_de_trayectoria;
	public JComboBox jcb_tiempo;
	public JCheckBox jchb_antialiasing;
	public JCheckBox jchb_pintar_ejes;
	public JCheckBox jchb_escribir_puntos;	
	
	public boolean mas_puntos=false;
	public boolean mover_puntos=false;
	public boolean borrar_puntos=false;
	public boolean borrar_todo=false;
	
	Point2D mSelectedPoint;
	Point2D mGeometryCenter;
	int i_mSelectedPoint;
	Color color_punto_seleccionado=Color.red.darker();
	Color color_punto_normal=Color.blue;
	Color color_lineas=Color.red;	
		
	int ancho,alto;
	
	public Vector v_puntos=new Vector();	
	public Vector v_puntos_temporal=new Vector();
	
	public Panel_patron(int an,int al)
	{	
		super("Colocar los Puntos");
		
		ancho=an;
		alto=al+30;
		        
        JPanel jpanel = new JPanel();
        jpanel.setLayout( new GridLayout(1,2) );
        jpanel.setBorder(BorderFactory.createEtchedBorder());
        jpanel.setDoubleBuffered(true);
        
        super.jp.add(jpanel, "Center");
        
		createUI(jpanel);	
		
		setBackground(Color.white);
				
	}
	
				
	public void createUI(JPanel jpanel)
	{	
		
	panel_de_controles=new JInternalFrame("panel_de_controles");
	panel_de_controles.setLayout(new GridLayout(7,1));
	panel_de_controles.setSize(ancho,alto);
	
	
	panel_de_controles.setVisible(true);
		
		
	JPanel panel_estado_antialiasing=new JPanel();
		JPanel pane_estado=new JPanel();
		pane_estado.setLayout(new GridLayout(1,2));
			JLabel L_1=new JLabel("Estado");L_1.setFont(ES_Utiles.FONT_DE_MENSAJES);	
			jta_estado=new JTextArea("INICIO");jta_estado.setFont(ES_Utiles.FONT_DE_MENSAJES);
			jta_estado.setColumns(10);	
		pane_estado.add(L_1);
		//pane_estado.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Estado"));
		pane_estado.add(jta_estado);			
	panel_estado_antialiasing.add(pane_estado);
panel_de_controles.add(panel_estado_antialiasing);
	
	JPanel pane=new JPanel();
	pane.setLayout(new GridLayout(1,6));
		jchb_antialiasing=new JCheckBox();
		jchb_antialiasing.setSelected(true);
		jchb_antialiasing.addChangeListener(this);
		JLabel L_1a=new JLabel("borroso");L_1a.setFont(ES_Utiles.FONT_DE_MENSAJES);	
	pane.add(L_1a);
	
	pane.add(jchb_antialiasing);
	
		jchb_pintar_ejes=new JCheckBox();
		jchb_pintar_ejes.setSelected(true);
		jchb_pintar_ejes.addChangeListener(this);
		JLabel L_1b=new JLabel("Ejes");L_1b.setFont(ES_Utiles.FONT_DE_MENSAJES);	
	pane.add(L_1b);
	pane.add(jchb_pintar_ejes);
	
		jchb_escribir_puntos=new JCheckBox();
		jchb_escribir_puntos.setSelected(false);
		jchb_escribir_puntos.addChangeListener(this);
		JLabel L_1c=new JLabel("Puntos");L_1c.setFont(ES_Utiles.FONT_DE_MENSAJES);
	
	pane.add(L_1c);
	pane.add(jchb_escribir_puntos);
	
	panel_de_controles.add(pane);
	
////////////////////////////////////////////////////////////////////////////////
	JPanel panel_de_botones=new JPanel();
	panel_de_botones.setLayout( new GridLayout(1,2) );	
		
		Boton_Libreria colocar_puntos=new Boton_Libreria("colocar_puntos",new Eventos_patron(this));
		colocar_puntos.setForeground(color_punto_normal);	
		panel_de_botones.add(colocar_puntos);	
	
		Boton_Libreria mover_puntos=new Boton_Libreria("mover_puntos",new Eventos_patron(this));
		mover_puntos.setForeground(color_punto_seleccionado);	
		panel_de_botones.add(mover_puntos);
	panel_de_controles.add(panel_de_botones);
	
	JPanel panel_de_botones_2=new JPanel();
	panel_de_botones_2.setLayout( new GridLayout(1,2) );	
		
		Boton_Libreria borrar_puntos=new Boton_Libreria("borrar_puntos",new Eventos_patron(this));
		borrar_puntos.setForeground(color_lineas);	
		panel_de_botones_2.add(borrar_puntos);
	
		
		Boton_Libreria borrar_todo=new Boton_Libreria("borrar_todo",new Eventos_patron(this));
		panel_de_botones_2.add(borrar_todo);
		
	panel_de_controles.add(panel_de_botones_2);
	
	
	
	String[] opciones={"Spline","Lineal"};
	JPanel paneee=new JPanel();
	paneee.setLayout(new GridLayout(1,3));
		jcb_tipo_de_trayectoria=new JComboBox(opciones);
		jcb_tipo_de_trayectoria.setSelectedIndex(1);
		jcb_tipo_de_trayectoria.setFont(ES_Utiles.FONT_DE_MENSAJES);
		JLabel jl_tipo=new JLabel("Tipo de trayectoria");
		jl_tipo.setFont(ES_Utiles.FONT_DE_MENSAJES);		
	paneee.add(jl_tipo);
	paneee.add(new JPanel());
	paneee.add(jcb_tipo_de_trayectoria);		
	panel_de_controles.add(paneee);
		
	String[] opc;
	int n=100;
	opc=new String[n];
	for(int i=0;i<n;i++)
	{
		opc[i]=""+(i+1);	
	}		
	/*JComboBox*/ jcb_tiempo=new JComboBox(opc);jcb_tiempo.setFont(ES_Utiles.FONT_DE_MENSAJES);
	JPanel panee=new JPanel();
	panee.setLayout(new GridLayout(1,3));
	jcb_tiempo.setSelectedIndex(9);
	
	JLabel jl_tiempo=new JLabel("TIEMPO");jl_tiempo.setFont(ES_Utiles.FONT_DE_MENSAJES);		
	panee.add(jl_tiempo);
	panee.add(new JPanel());
	panee.add(jcb_tiempo);		
	panel_de_controles.add(panee);
	
	JPanel panel_tray_cerrar=new JPanel();	
	panel_tray_cerrar.setLayout(new GridLayout(1,3));
	
	Boton_Libreria aplicar_persecucion_de_puntos=new Boton_Libreria("Persec.",new Eventos_patron(this));
	panel_tray_cerrar.add(aplicar_persecucion_de_puntos);	
	
	Boton_Libreria aplicar_persecucion_de_puntos_asimetrica=new Boton_Libreria("asim.",new Eventos_patron(this));
	panel_tray_cerrar.add(aplicar_persecucion_de_puntos_asimetrica);	
	
	
	Boton_Libreria cerrar_Dialogo=new Boton_Libreria("Aceptar",new Eventos_patron(this));
	panel_tray_cerrar.add(cerrar_Dialogo);	
	
	panel_de_controles.add(panel_tray_cerrar);
			
	//panel_de_dibujo=new JInternalFrame("panel_de_dibujo");
	panel_de_dibujo=new JPanel();
	panel_de_dibujo.setLayout( new GridLayout(1,1) );
	panel_de_dibujo.setDoubleBuffered(true);
	
	panel_de_dibujo.setPreferredSize(new Dimension(ancho,alto));
	panel_de_dibujo.addMouseListener(new Eventos_I(this));
	panel_de_dibujo.addMouseMotionListener(new Eventos_I(this));
	//panel_de_dibujo.setFrameIcon(null /*new FrameIcon(ES_Utiles.get_System_Image(Graficador3D.SYS_ICON) ) */);
         
	//panel_de_dibujo.addInternalFrameListener(new Eventos_I(this));	
	//panel_de_dibujo.setDesktopIcon(new JDesktopIcon(ES_Utiles.get_System_Image(Graficador3D.SYS_ICON)));
	panel_de_dibujo.setVisible(true);	
	
	//panel_de_controles.setFrameIcon(null/*()*/  );		
	
	
	jpanel.add(panel_de_controles);	
	jpanel.add(panel_de_dibujo);			
	}
			
	public void dibujar_punto(double x ,double y)
	{
		Graphics2D g2=(Graphics2D)panel_de_dibujo.getGraphics();
		
		Point2D punto;				
		punto= new Point2D.Double( x , y );
		
			if(mas_puntos)
			{
			mSelectedPoint=null;	
				
			//Shape z=null;
			try{
				/*z = get_punto_de_control((Point2D)v_puntos.get(0));
					if( z.contains(punto) )
					{						
					v_puntos.add((Point2D)v_puntos.get(0));	
					}
					else*/
					{
					////////////////////////////////////////////////////
					v_puntos.add(punto);				
					fijar_texto("mas_puntos");					
					////////////////////////////////////////////////////				
					}
				}
			catch(Exception e){System.out.println("exception"+e);
			////////////////////////////////////////////////////
			v_puntos.add(punto);				
			fijar_texto("mas_puntos");			
			////////////////////////////////////////////////////			
			 }	
			
			}
			else
				if(mover_puntos)
				{
					fijar_texto("mover_puntos");
					
					for (int i = 0; i < v_puntos.size(); i++)
					{
												
						Shape s = get_punto_de_control((Point2D)v_puntos.get(i));
						if (s.contains(punto))
						{
						mSelectedPoint = (Point2D)v_puntos.get(i);
						i_mSelectedPoint=i;
						break;
						}						
					}	
					
				}
				else
				if(borrar_puntos)
				{
					mSelectedPoint=null;
					
					fijar_texto("borrar_puntos");
					
					for (int i = 0; i < v_puntos.size(); i++)
					{
												
						Shape s = get_punto_de_control((Point2D)v_puntos.get(i));
						if (s.contains(punto))
						{
						mSelectedPoint = (Point2D)v_puntos.get(i);
						i_mSelectedPoint=i;
						
						borrar_punto();
						
						break;
						}
						
						
					}
				}
				else
				if(borrar_todo)
				{
					mSelectedPoint=null;
					
					fijar_texto("borrar_todo");
					v_puntos=new Vector();
					v_puntos_temporal=new Vector();					
				}
		
	pintar_puntos(v_puntos);	
	}
	
	public void mover_punto(double x ,double y)
	{
		Point2D punto;				
		punto= new Point2D.Double( x , y );
	
		if (mSelectedPoint != null) 
		{
		mSelectedPoint.setLocation(punto);
		
		v_puntos.setElementAt(mSelectedPoint,i_mSelectedPoint);		
		
		pintar_puntos(v_puntos);
		}	
	}
	
	public void fijar_texto(String texto)
	{
		jta_estado.setText(texto);
	}
	
	public void borrar_punto()
	{
		Vector v=new Vector(); 
		
		if (mSelectedPoint != null) 
		{
			for(int i=0;i<i_mSelectedPoint;i++)
			{
				v.add((Point2D)v_puntos.get(i));	
			}
						
			for(int i=i_mSelectedPoint+1;i<v_puntos.size();i++)
			{
				v.add((Point2D)v_puntos.get(i));
			}
		
		v_puntos=v;
				
		}	
	}

int tam=6;

public Shape get_punto_de_control(Point2D p)
{
	
	int lado=tam;
	//return new Rectangle2D.Double( p.getX()-lado/2 , p.getY()-lado/2 , lado , lado );	
	
	return new java.awt.geom.Ellipse2D.Double( p.getX()-lado/2 , p.getY()-lado/2 , lado , lado );	
}

public void stateChanged(ChangeEvent ce)
{
	//Graphics2D g2=(Graphics2D)panel_de_dibujo.getGraphics();		
	pintar_puntos(v_puntos);
}

BufferedImage bi;
boolean firstTime=true;
Graphics2D g2=null;
Rectangle area=null;
public void pintar_puntos(Vector v_puntos_)
{					
				
	//Graphics2D g2=(Graphics2D)panel_de_dibujo.getGraphics();
		
	if(firstTime)
	{
	Dimension dim = getSize();
	int w = dim.width;
	int h = dim.height;
	area = new Rectangle(dim);
	bi = (BufferedImage)createImage(w, h);
	g2 = bi.createGraphics();
	firstTime = false;
	}
		
	g2.clearRect(0,0,area.width,area.height);
	//panel_de_dibujo.getGraphics().clearRect(0,0,area.width,area.height);
	//g2.clearRect(4,10,panel_de_dibujo.size().width-8,panel_de_dibujo.size().height-10);
		
		
	if(jchb_antialiasing.isSelected())
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
	else
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_OFF);
	
	
	
	
	if( jchb_pintar_ejes.isSelected() )
	pintar_ejes(g2);
				
				for(int i=0;i<v_puntos_.size();i++)	
				{
				
				Point2D punto_temp_1 =(Point2D)v_puntos_.get(i);
						
				if(punto_temp_1==mSelectedPoint)	g2.setPaint(color_punto_seleccionado);
				else								g2.setPaint(color_punto_normal);
								
				g2.setFont(fuente_punto);
				
					if(jchb_escribir_puntos.isSelected())
					{
					
					if(punto_temp_1==mSelectedPoint)	
					{
						
						Point2D punto_temp_3 = new Point2D.Double(punto_temp_1.getX()+tam/2,punto_temp_1.getY()+tam/2); 
						
						g2.setPaint(Color.black); 
						g2.fill( get_punto_de_control( punto_temp_3) );
						g2.setPaint(color_punto_seleccionado);
					}
					
					g2.fill( get_punto_de_control(punto_temp_1) );
						
					}
											
				}
				g2.setPaint(color_lineas);
				if(v_puntos_.size()>1)
				g2.draw( crear_LINEA(v_puntos_) );
	/*			
	int[] x=new int[v_puntos.size()];
	int[] y=new int[v_puntos.size()];
				
	for(int i=0;i<v_puntos.size();i++)	
	{
		x[i]=(int)((Point2D)v_puntos.get(i)).getX();
		y[i]=(int)((Point2D)v_puntos.get(i)).getY();
	}
				
	if(v_puntos.size()>0)
	g2.drawPolyline(x,y,5);
	*/
	
	panel_de_dibujo.getGraphics().drawImage(bi, 0, 0, this);
	//panel_de_dibujo.paint((Graphics)g2);				
}

protected Shape crear_LINEA(Vector v_puntos_)
	{
	
	int n=v_puntos_.size();
	float[][] mPoints=new float[2][n];
		
	Iterator<Point2D> it = v_puntos_.iterator();
    Point2D aux;
    int cont=0;
    	while(it.hasNext())
    	{
    		aux = it.next();
    		if(aux instanceof Point2D)
    		{    			
    			mPoints[0][cont]=(float)aux.getX();
    			mPoints[1][cont]=(float)aux.getY();
    		}
    	cont++;	    		
    	}
	
	GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
	mPoints.length);
	
			
	path.moveTo(mPoints[0][0], mPoints[1][0]);
	
	//Figura.dibujar_lineas , Panel_patron.crear_LINEA
	/////////////////////cola de flecha
			float x0 = mPoints[0][1] - mPoints[0][0];
			float y0 = mPoints[1][1] - mPoints[1][0];
	
			float x0u = (float) ( x0/Math.pow( Math.pow(x0,2) + Math.pow(y0,2) , 0.5 ) );
			float y0u = (float) ( y0/Math.pow( Math.pow(x0,2) + Math.pow(y0,2) , 0.5 ) );
		
			float salto0=6f;
			
			float p02_x=mPoints[0][0]-salto0*(y0u)-salto0*(x0u);
			float p02_y=mPoints[1][0]-salto0*(-x0u)-salto0*(y0u);
			
			float p03_x=mPoints[0][0]-salto0*(-y0u)-salto0*(x0u);
			float p03_y=mPoints[1][0]-salto0*(x0u)-salto0*(y0u);
	
	path.lineTo(p02_x, p02_y);
	path.lineTo(p03_x, p03_y);
	path.lineTo(mPoints[0][0], mPoints[1][0]);	
	//////////////////////////////////////////		
					
	for (int i = 1 ; i < n ; i += 1)
	{
	path.lineTo(mPoints[0][i], mPoints[1][i]);				
	}
		//Figura.dibujar_lineas , Panel_patron.crear_LINEA
	///////////////////// punta de flecha	
			float xn = mPoints[0][n-1] - mPoints[0][n-2];
			float yn = mPoints[1][n-1] - mPoints[1][n-2];
	
			float xnu = (float) ( xn/Math.pow( Math.pow(xn,2) + Math.pow(yn,2) , 0.5 ) );
			float ynu = (float) ( yn/Math.pow( Math.pow(xn,2) + Math.pow(yn,2) , 0.5 ) );
		
			float salto=6f;
		
			float pn2_x=mPoints[0][n-1]+salto*(ynu);
			float pn2_y=mPoints[1][n-1]+salto*(-xnu);
		
			float pn3_x=mPoints[0][n-1]+salto*(xnu);
			float pn3_y=mPoints[1][n-1]+salto*(ynu);
		
			float pn4_x=mPoints[0][n-1]+salto*(-ynu);
			float pn4_y=mPoints[1][n-1]+salto*(xnu);
			
			path.lineTo(pn2_x, pn2_y);	
			path.lineTo(pn3_x, pn3_y);
			path.lineTo(pn4_x, pn4_y);
			
			path.lineTo( mPoints[0][n-1] , mPoints[1][n-1] );
	////////////////////////////////////////////////////////////////////////	
		
	return path;
	}

/*
public void pintar_todo_blanco(Graphics2D g2)
{
	for(int i=0;i<v_puntos.size();i++)	
				if(i==i_mSelectedPoint||i==(i_mSelectedPoint-1))
				{
				
				Point2D punto_temp_1 =(Point2D)v_puntos.get(i);
						
				if(punto_temp_1==mSelectedPoint)	g2.setPaint(Color.WHITE);
				else								g2.setPaint(Color.WHITE);
					
				g2.fill( get_punto_de_control(punto_temp_1) );
				g2.setFont(fuente_punto);
				
					if(jchb_escribir_puntos.isSelected())
					{
					Point2D aux_temp=transformar( escala , punto_temp_1 );			
					g2.drawString("("+redondear(aux_temp.getX(),2)+","+redondear(aux_temp.getY(),2)+")",(int)punto_temp_1.getX(),(int)punto_temp_1.getY());
					}
				
					if ( i < (v_puntos.size()-1) )
					{
						Point2D punto_temp_2=(Point2D)v_puntos.get(i+1);
						g2.setPaint(Color.WHITE);
					//g2.fill3DRect((int)punto_temp_1.getX(),(int)punto_temp_1.getY(),
					//			(int)punto_temp_2.getX(),(int)punto_temp_2.getY() , true);
								
						//g2.drawLine((int)punto_temp_1.getX(),(int)punto_temp_1.getY(),
						//			(int)punto_temp_2.getX(),(int)punto_temp_2.getY());
						
						g2.drawLine((int)punto_temp_1.getX()-1,(int)punto_temp_1.getY()-1,
									(int)punto_temp_2.getX()-1,(int)punto_temp_2.getY()-1);
									
						g2.drawLine((int)punto_temp_1.getX()-2,(int)punto_temp_1.getY()-2,
									(int)punto_temp_2.getX()-2,(int)punto_temp_2.getY()-2);
						
						g2.drawLine((int)punto_temp_1.getX()+1,(int)punto_temp_1.getY()+1,
									(int)punto_temp_2.getX()+1,(int)punto_temp_2.getY()+1);
									
						g2.drawLine((int)punto_temp_1.getX()+2,(int)punto_temp_1.getY()+2,
									(int)punto_temp_2.getX()+2,(int)punto_temp_2.getY()+2);
									
					
						if(jchb_escribir_puntos.isSelected())
						{	
							float distancia=(float)punto_temp_1.distance(punto_temp_2)/escala;
							distancia=(float)redondear(distancia,2);
					
							int x2=(int)(punto_temp_1.getX()+(punto_temp_2.getX()-punto_temp_1.getX())/2);
							int y2=(int)(punto_temp_1.getY()+(punto_temp_2.getY()-punto_temp_1.getY())/2);					
										
							g2.setFont(fuente_distancia);
							g2.drawString("d="+distancia,x2-1,y2-1);
							g2.drawString("d="+distancia,x2+1,y2+1);
							g2.drawString("d="+distancia,x2+2,y2+2);
							g2.drawString("d="+distancia,x2-2,y2-2);				
						}	
					}			
				}
}	
*/
public void pintar_ejes(Graphics2D g2d)
{
	
	int n_f=(int)((ancho/2)/escala);
	
	g2d.setColor(Color.LIGHT_GRAY);
	g2d.setFont(new Font("Helvetica",Font.PLAIN,6));
	
	for(int i=0;i<=n_f;i++)
	{
	g2d.drawLine( (int)(ancho/2-i*escala),(int)(0),(int)(ancho/2-i*escala),(int)(alto) );	
	}
	for(int i=0;i<=n_f;i++)
	{
	g2d.drawLine( (int)(ancho/2+i*escala),(int)(0),(int)(ancho/2+i*escala),(int)(alto) );	
	}
	
	int n_c=(int)((alto/2)/escala);
	
	for(int i=0;i<=n_c;i++)
	{
	g2d.drawLine( (int)(0),(int)(alto/2-i*escala),(int)(ancho),(int)(alto/2-i*escala) );
	}
	for(int i=0;i<=n_c;i++)
	{
	g2d.drawLine( (int)(0),(int)(alto/2+i*escala),(int)(ancho),(int)(alto/2+i*escala) );
	}
	
		{
			Stroke stroke_actual=g2d.getStroke();
		
			Stroke stroke = new BasicStroke(1, BasicStroke.CAP_SQUARE,
			BasicStroke.JOIN_MITER, 2,
			new float[] { 2, 5 }, 5);
			g2d.setStroke(stroke);
		
		for(int i=0;i<=2*n_f;i++)
		{
		g2d.drawLine( (int)(ancho/2-i*escala/2),(int)(0),(int)(ancho/2-i*escala/2),(int)(alto) );	
		}
		for(int i=0;i<=2*n_f;i++)
		{
		g2d.drawLine( (int)(ancho/2+i*escala/2),(int)(0),(int)(ancho/2+i*escala/2),(int)(alto) );	
		}
		for(int i=0;i<=2*n_c;i++)
		{
		g2d.drawLine( (int)(0),(int)(alto/2-i*escala/2),(int)(ancho),(int)(alto/2-i*escala/2) );
		}
		for(int i=0;i<=2*n_c;i++)
		{
		g2d.drawLine( (int)(0),(int)(alto/2+i*escala/2),(int)(ancho),(int)(alto/2+i*escala/2) );
		}
	
		for(int i=0;i<=2*n_c+4;i++)
		{			
			if(i%2==1)
			g2d.draw(new java.awt.geom.Ellipse2D.Double(ancho/2-i*escala/2,alto/2-i*escala/2,i*escala,i*escala) );
		}		
			
			g2d.setStroke(stroke_actual);
		
		for(int i=0;i<=n_c+2;i++)
		{			
			g2d.draw(new java.awt.geom.Ellipse2D.Double(ancho/2-i*escala,alto/2-i*escala,2*i*escala,2*i*escala) );
		}		
			
			
		}
		
	g2d.setColor(Color.BLACK);
	//eje y
	g2d.drawLine( (int)(ancho/2),(int)(2),(int)(ancho/2),(int)(alto-2) );
	//eje x
	g2d.drawLine( (int)(2),(int)(alto/2),(int)(ancho-2),(int)(alto/2) );
		
}

Font fuente_distancia=new Font("TimesRoman",Font.BOLD,9);
Font fuente_punto=new Font("Arial",Font.BOLD,9);

	public float redondear(double valor , int posiciones)
	{
		return (float)( ((int)( valor*Math.pow(10,posiciones) ))/Math.pow(10,posiciones) );
	}
	
	public Point2D[] get_Point2D()
	{
		Point2D[] aux=new Point2D[v_puntos.size()];
		
		for (int i=0;i<aux.length;i++)
		aux[i]=(Point2D)v_puntos.get(i);
		
		aux=transformar( escala , aux );
		
		return aux;		
	}
	int escala=40;
	public Point2D[] set_Point2D(Point3f[] pun)
	{
	v_puntos.removeAllElements();				
		Point2D[] aux=new Point2D[pun.length];
		
		for (int i=0;i<aux.length;i++)
		{
			aux[i]=new Point2D.Double(pun[i].x,pun[i].y);
		}
	aux=transformar_entrada( escala , aux );
		for (int i=0;i<aux.length;i++)
		{
			v_puntos.add(aux[i]);			
		}
				
		return aux;		
	}	
	
	public Point2D[] transformar( float scale , Point2D[] pun )
	{
		  	Point2D[] p_salida=new Point2D[pun.length];
		
		for(int i=0;i<pun.length;i++)
		{	
			double x=(float)/*redondear(*/ ( pun[i].getX()-(ancho/2) )/scale ;//, 2);
			double y=(float)/*redondear(*/ -( pun[i].getY()-(alto/2) )/scale ;//, 2);
			p_salida[i]= new Point2D.Double( x , y );	
		}		
		return p_salida;	
  	}
  	public Point2D transformar( float scale , Point2D pun )
	{
		Point2D p_salida;//=new Point2D[pun.length];
		
		//for(int i=0;i<pun.length;i++)
		{	
			double x=(float)/*redondear(*/ ( pun.getX()-(ancho/2) )/scale ;//, 2);
			double y=(float)/*redondear(*/ -( pun.getY()-(alto/2) )/scale ;//, 2);
			p_salida= new Point2D.Double( x , y );	
		}		
		return p_salida;	
  	}
  	
  	public Point2D[] transformar_entrada( float scale , Point2D[] pun )
	{
		  	Point2D[] p_salida=new Point2D[pun.length];
		
		for(int i=0;i<pun.length;i++)
		{	
			double x=(float)/*redondear(*/ pun[i].getX()*scale+(ancho/2) ;//, 2);
			double y=(float)/*redondear(*/ -pun[i].getY()*scale+(alto/2) ;//, 2);
			p_salida[i]= new Point2D.Double( x , y );	
		}		
		return p_salida;	
  	}
  	
  	public void trayectorias_de_persecucion_simetrica()
  	{
  		//v_puntos=null;
  		//v_puntos=new Vector();
  		
  		
  		int nro_particulas=jcb_tiempo.getSelectedIndex()+1;
  		int particula_actual;
  		if(ES_Utiles.particula_actual<10)
  		particula_actual=ES_Utiles.particula_actual;//(int)(1+Math.random()*(nro_particulas-1));
  		else
		{
		ES_Utiles.particula_actual=0;	
		particula_actual=ES_Utiles.particula_actual;
		}
		ES_Utiles.particula_actual++;  		
  		int nroAra=nro_particulas;
  		int DireccionSimetrica=-1;
  		int radio=100;
  		double salto=0.1;
  		int x[]=new int[nro_particulas];
  		int y[]=new int[nro_particulas];
  		double xa[]=new double[nro_particulas];
  		double ya[]=new double[nro_particulas];
  		int h=(int)(ancho/2);
  		int k=(int)(alto/2);
  		
  		Vector[] v_vectores_pa_pintar=new Vector[nroAra];
  		
  		for (int i=0;i<nroAra;i++)
		{
		//Graphics2D g2=(Graphics2D)panel_de_dibujo.getGraphics();
			
		x[i]=(int)( radio*(Math.cos(((i-1)%nroAra)*(2*Math.PI/nroAra))+(Math.pow((-1.0),(double)DireccionSimetrica))*Math.sin(((i-1)%nroAra)*(2*Math.PI/nroAra)))+(double)h) ;//izq	
		y[i]=(int)( radio*(Math.cos(((i-1)%nroAra)*(2*Math.PI/nroAra))-(Math.pow((-1.0),(double)DireccionSimetrica))*Math.sin(((i-1)%nroAra)*(2*Math.PI/nroAra)))+(double)k) ;
				
		xa[i]=radio*(Math.cos(((i-1)%nroAra)*(2*Math.PI/nroAra))-(Math.pow((-1.0),(double)DireccionSimetrica))*Math.sin(((i-1)%nroAra)*(2*Math.PI/nroAra)))+(double)h;	//der
		ya[i]=radio*(Math.cos(((i-1)%nroAra)*(2*Math.PI/nroAra))+(Math.pow((-1.0),(double)DireccionSimetrica))*Math.sin(((i-1)%nroAra)*(2*Math.PI/nroAra)))+(double)k;
		
		v_vectores_pa_pintar[i]=new Vector();
		//Point2D punto=new Point2D.Double(xa[i],ya[i]);
		//g2.fill( get_punto_de_control(punto) );		
		}
  		
  		boolean condicion=true;
  		int contador=0;
  		int tolerancia=0;
  		  		
  		while(condicion && tolerancia<20000)
  		{
  			for(int i=0;i<nro_particulas;i++)
  			{
  				double auxx=Math.pow((xa[(i+1)%xa.length]-xa[i]),2)+Math.pow((ya[(i+1)%ya.length]-ya[i]),2);
  			
  				double xu=(xa[(i+1)%xa.length]-xa[i])/(Math.pow(auxx,0.5));
				double yu=(ya[(i+1)%ya.length]-ya[i])/(Math.pow(auxx,0.5));
				
				//double xtransito=(xa[i]+xu*salto);
				//double ytransito=(ya[i]+yu*salto);
						
				//xa[i]=xtransito;
				//ya[i]=ytransito;
				xa[i]=xa[i]+xu*salto;
				ya[i]=ya[i]+yu*salto;
			
				
				x[i]=(int)xa[i];
				y[i]=(int)ya[i];
				
			if(i==particula_actual)
			{
				if( (contador%nro_particulas)==particula_actual )
				{
				Point2D punto = new Point2D.Double( xa[i] , ya[i] );
				v_puntos_temporal.add(punto);
				}
				//dibujar_punto((double)x[i],(double)y[i]);	
			}
			else
			{
				if( (contador%nro_particulas)==particula_actual )
				{
				Point2D punto = new Point2D.Double( xa[i] , ya[i] );
				v_vectores_pa_pintar[i].add(punto);
				}
			}
			
			if((x[i]==x[(i+1)%x.length])&&(y[i]==y[(i+1)%y.length]))
				{
					tolerancia++;
					//condicion=false;
				}				
			}
		contador++;
		}	
		pintar_puntos(v_puntos_temporal);
		
		for (int i=0;i<nroAra;i++)
		{
		//Graphics2D g2=(Graphics2D)panel_de_dibujo.getGraphics();
						
		xa[i]=radio*(Math.cos(((i-1)%nroAra)*(2*Math.PI/nroAra))-(Math.pow((-1.0),(double)DireccionSimetrica))*Math.sin(((i-1)%nroAra)*(2*Math.PI/nroAra)))+(double)h;	//der
		ya[i]=radio*(Math.cos(((i-1)%nroAra)*(2*Math.PI/nroAra))+(Math.pow((-1.0),(double)DireccionSimetrica))*Math.sin(((i-1)%nroAra)*(2*Math.PI/nroAra)))+(double)k;
		
		Point2D punto=new Point2D.Double(xa[i],ya[i]);
		g2.fill( get_punto_de_control(punto) );		
		}
		
		
		////////////////////////////////////////////////////////////////////////
		float salto_color=(int)(100/v_vectores_pa_pintar.length);
  		float rojo=(int)(0);  		  		
  		float verde=(float)(Math.random() );
  		float azul=(float)(Math.random() );
  		//if(azul!=0||verde!=0)
  		rojo=(float)(Math.random() );
  		
  		for(int i=0;i<v_vectores_pa_pintar.length;i++)
  		{
  			if(i!=particula_actual)
  			{
  				g2.setPaint(new Color( 55+2*(int)(rojo*salto_color*i) ,
  									   155+(int)(verde*salto_color*i) ,
  									   155+(int)(azul*salto_color*i) ) );
  									   
  				pintar_puntos_sin_clearRect(v_vectores_pa_pintar[i]);
  			}
  		}
  		////////////////////////////////////////////////////////////////////////
  					  		  		  		  		
  	}
  	
  	public void trayectorias_de_persecucion_asimetrica()
  	{
  		if(v_puntos.size()==0) return;
  		v_puntos_temporal=new Vector(); 
  		Vector[] v_vectores_pa_pintar=new Vector[v_puntos.size()];
  		
  		Point2D[] puntos2D = new Point2D[v_puntos.size()];
  		  		
  		for(int i=0;i<puntos2D.length;i++)
  		{
  			puntos2D[i]=(Point2D)v_puntos.get(i);
  			
  			v_vectores_pa_pintar[i]=new Vector();
  		
  		}
  		  		
  		int nro_particulas=puntos2D.length;;
  		int particula_actual;
  		if(ES_Utiles.particula_actual<nro_particulas)
  		particula_actual=ES_Utiles.particula_actual;//(int)(1+Math.random()*(nro_particulas-1));
  		else
		{
		ES_Utiles.particula_actual=0;	
		particula_actual=ES_Utiles.particula_actual;
		}
		ES_Utiles.particula_actual++;  
  		
  		double salto=0.1;
  		int x[]=new int[nro_particulas];
  		int y[]=new int[nro_particulas];
  		double xa[]=new double[nro_particulas];
  		double ya[]=new double[nro_particulas];
  		int h=(int)(ancho/2);
  		int k=(int)(alto/2);
  			 		
  		
  		for(int i=0;i<puntos2D.length;i++)
  		{
  			x[i]=(int)puntos2D[i].getX();
  			y[i]=(int)puntos2D[i].getY();
  			
  			xa[i]=puntos2D[i].getX();
  			ya[i]=puntos2D[i].getY();
  		}
  		  		
  		boolean condicion=true;
  		int contador=0;
  		int tolerancia=0;
  		  		
  		while(condicion && tolerancia<200)
  		{
  			for(int i=0;i<nro_particulas;i++)
  			{
  				double auxx=Math.pow((xa[(i+1)%xa.length]-xa[i]),2)+Math.pow((ya[(i+1)%ya.length]-ya[i]),2);
  			
  				double xu=(xa[(i+1)%xa.length]-xa[i])/(Math.pow(auxx,0.5));
				double yu=(ya[(i+1)%ya.length]-ya[i])/(Math.pow(auxx,0.5));
				
				//double xtransito=(xa[i]+xu*salto);
				//double ytransito=(ya[i]+yu*salto);
						
				//xa[i]=xtransito;
				//ya[i]=ytransito;
				xa[i]=xa[i]+xu*salto;
				ya[i]=ya[i]+yu*salto;
			
				
				x[i]=(int)xa[i];
				y[i]=(int)ya[i];
				
			if(i==particula_actual)
			{
				if( (contador%nro_particulas)==particula_actual )
				{
				Point2D punto = new Point2D.Double( xa[i] , ya[i] );
				v_puntos_temporal.add(punto);
				}
				
				//dibujar_punto((double)x[i],(double)y[i]);	
			}
			else
			{
				if( (contador%nro_particulas)==particula_actual )
				{
				Point2D punto = new Point2D.Double( xa[i] , ya[i] );
				v_vectores_pa_pintar[i].add(punto);
				}
			}
			
			if((x[i]==x[(i+1)%x.length])&&(y[i]==y[(i+1)%y.length]))
				{
					tolerancia++;
					//condicion=false;
				}				
			}
		contador++;
		}	
		pintar_puntos(v_puntos_temporal);
		
		//double acum_x=0,acum_y=0;
		for (int i=0;i<v_puntos.size();i++)
		{
		//Graphics2D g2=(Graphics2D)panel_de_dibujo.getGraphics();
		//acum_x+=((Point2D)(v_puntos.get(i))).getX();
		//acum_y+=((Point2D)(v_puntos.get(i))).getY();
						
		Point2D punto=new Point2D.Double( ((Point2D)(v_puntos.get(i))).getX(),
		   								  ((Point2D)(v_puntos.get(i))).getY() );
		g2.fill( get_punto_de_control(punto) );		
		}
		
		//Point2D punto=new Point2D.Double( acum_x/v_puntos.size(),
		//   								  acum_y/v_puntos.size() );
		//g2.fill( get_punto_de_control(punto) );
		
		////////////////////////////////////////////////////////////////////////
		float salto_color=(int)(100/v_vectores_pa_pintar.length);
  		int rojo=(int)(0);  		  		
  		int verde=(int)(Math.random()+0.5 );
  		int azul=(int)(Math.random()+0.5 );
  		if(azul!=0||verde!=0)
  		rojo=(int)(Math.random()+0.5 );
  		
  		for(int i=0;i<v_vectores_pa_pintar.length;i++)
  		{
  			if(i!=particula_actual)
  			{
  				g2.setPaint(new Color( 55+2*(int)(rojo*salto_color*i) ,
  									   155+(int)(verde*salto_color*i) ,
  									   155+(int)(azul*salto_color*i) ) );
  									   
  				pintar_puntos_sin_clearRect(v_vectores_pa_pintar[i]);
  			}
  		}
  		////////////////////////////////////////////////////////////////////////
  		
  	}	
  	
  	public void pintar_puntos_sin_clearRect(Vector v)
  	{
  		//g2.setPaint(Color.BLUE);
				if(v.size()>1)
				g2.draw( crear_LINEA(v) );
		
	panel_de_dibujo.getGraphics().drawImage(bi, 0, 0, this);
  	}
  	
  	/*
  	public void calcular_puntos()
  	{
  		JDialog Dialogo_de_datos=new JDialog();
  			 Dialogo_de_datos.setLayout(new GridLayout(4,1));
  			 Dialogo_de_datos.setLocation(200,200);
  			 Dialogo_de_datos.setSize(400,400);
  		
  		JPanel pane_0=new JPanel();pane_0.setLayout( new GridLayout(1,2) );
  		JLabel L_0=new JLabel("X(t)=");
  		JTextArea funcion_x=new JTextArea("");
  		pane_0.add(L_0);
  		pane_0.add(funcion_x);
  		
  		Dialogo_de_datos.add(pane_0); 
  		
  		JPanel pane_1=new JPanel();pane_1.setLayout( new GridLayout(1,2) );
  		JLabel L_1=new JLabel("Y(t)=");
  		JTextArea funcion_y=new JTextArea("");
  		pane_1.add(L_1);
  		pane_1.add(funcion_y);
  		
  		Dialogo_de_datos.add(pane_1);  		  		
  		
  		JPanel pane_2=new JPanel();pane_2.setLayout( new GridLayout(1,2) );
  		int n=20;
  		String[] op=new String[n];
  		for(int i=0;i<n;i++)op[i]=""+i;
  		JComboBox salto=new JComboBox(op);
  		JLabel L_2=new JLabel("salto=");
  		pane_2.add(L_2);
  		pane_2.add(salto);
  		
  		Dialogo_de_datos.add(pane_2);
  		
  		JButton aplicar=new JButton("aplicar");
  		//aplicar.    		
  		Dialogo_de_datos.add(aplicar);
  		  		
  		Dialogo_de_datos.setVisible(true);
  		
  		
  	}
  	*/
  	public void mostrar()
  	{
  		//panel_de_dibujo.setVisible(true);
  		
  		//try{pintar_puntos();}catch(Exception e){}
  		dimensionar_y_mostrar();
  		  		
  		//panel_de_dibujo.setVisible(false);
  		//pintar_puntos();
  		//panel_de_dibujo.setFocusable(true);
  		//panel_de_dibujo.setVisible(true); 
  		//pintar_puntos();
  		//dibujar_punto(0,0);
  		
  	}
  	
  	/*public void paint(Graphics g)
  	{
  		pintar_puntos();
  	}*/
////////////////////////////////////////////////////////////////////////////////	
}		