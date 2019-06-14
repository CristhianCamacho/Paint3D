
package com.cc.paint3D.Graficador3D.Utiles;

import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import java.awt.*;
import java.awt.image.BufferedImage;
import sun.audio.*;//pal sonido
import java.io.*;

import java.awt.event.*;

public class Ventana_de_espera_G3D extends Window implements Runnable, ActionListener{
 
 	Color color_de_fondo=Color.ORANGE;
 	Color color_de_texto=new Color(31,63,127);
 	Color color_de_borde=Color.red;
 	
 	int tiempo_de_espera=40;
 	
 	boolean[] choque={false,false};//1
 	
 	int[] radio_fragmentos={1,1};//2
 	
 	
 	int[] actual_x={DIM_X/2-TAM_CUAD/2,DIM_X/2-TAM_CUAD/2};            
 	int[] actual_y={DIM_Y-TAM_CUAD/2,0};//3
 	
 	static int DIM_X = 800;//dimension de la pantalla    
    static int DIM_Y = 600;//dimension de la pantalla 
    
       
    
    double[] explota_Y={ DIM_Y/2-TAM_CUAD/2 , DIM_Y/2-TAM_CUAD/2 };//4
    
    static int TAM_CUAD = 150;//tama�o de la figura
    int[] tam ={ 150 , 150 };//5
    
    Thread runner;
    Image imagen;
    BufferedImage bufferedImage;
        
    boolean figuras;
    boolean textos;
    String txt="no null";
    int pos=0;
        
    Font font_titlulo;//= new Font("Adler", Font.TYPE1_FONT , 75);
    Font font_nombre;//= new Font("sansserif.bold", Font.TYPE1_FONT , 20);
    
    
	public Font[] todas_las_fuentes ;
	int[] tamanios_fuentes;
	int[] pos_X_fuentes;
	int[] pos_Y_fuentes;
	//String[] nombres_de_las_fuentes; 
    
    Thread thread;
    
    int pos_y_Producto;
    int pos_y_Autor;
    
    public Ventana_de_espera_G3D(Dimension dimension,Dimension dim_espera)
    {
        super(new Frame());
        textos = true;
        figuras = true; 
        
         DIM_X=dim_espera.width;
         DIM_Y=dim_espera.height;              
         cargar_valores_iniciales();	
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] temp = ge.getAllFonts();//169
		int n=30;
		todas_las_fuentes=new Font[n];
		tamanios_fuentes=new int[n];
		pos_X_fuentes=new int[n];
		pos_Y_fuentes=new int[n];
		for(int i=0;i<n;i++)
		{
			todas_las_fuentes[i]=temp[i];
			tamanios_fuentes[i]=(int)( Math.random()*(int)( Math.pow( DIM_X*DIM_Y , 0.5 )/15) );
			pos_X_fuentes[i]=(int)(Math.random()*DIM_X);
			pos_Y_fuentes[i]=(int)(Math.random()*DIM_Y);
		}
		
		
        
        setSize(DIM_X, DIM_Y);/*/*//*/*//*/*/
        
        setLocation((int)(dimension.width - DIM_X) / 2, (int)(dimension.height - DIM_Y) / 2);
        
        setVisible(true);
        
        bufferedImage = new BufferedImage( (int)(TAM_CUAD), (int)(TAM_CUAD) , BufferedImage.TYPE_3BYTE_BGR);//tama�o de los cubos
        disenio_de_cubo(bufferedImage,TAM_CUAD,TAM_CUAD);
        
        
        
        thread = new Thread(this);
        thread.setPriority(10);
        thread.start();
        
        cargar_AudioStream();    	
    }
    
    
    
    public void cargar_valores_iniciales()
    {
    	if(DIM_X > DIM_Y)
    	{
    	TAM_CUAD = (int)(DIM_Y/4);   	
    	font_titlulo = new Font("Adler", Font.TYPE1_FONT , (int)(DIM_Y/15));
    	font_nombre = new Font("sansserif,bold", Font.TYPE1_FONT , (int)(DIM_Y/20) );
    	}
    	else
    	{
    	TAM_CUAD = (int)(DIM_X/4);   
    	font_titlulo = new Font("Adler", Font.TYPE1_FONT , (int)(DIM_X/9));
    	font_nombre = new Font("sansserif,bold", Font.TYPE1_FONT , (int)(DIM_X/20) );
    	}
    	
    	int x0=(int)(DIM_X/2-TAM_CUAD/2);int x1=(int)(DIM_X/2-TAM_CUAD/2);
    	actual_x[0]=x0;
    	actual_x[1]=x1;    	
    	x0=(int)(DIM_Y-TAM_CUAD/2);x1=0;            
 		actual_y[0]=x0;
    	actual_y[1]=x1;
    	
    	explota_Y[0]=(int)(DIM_Y/2-TAM_CUAD/2);
    	explota_Y[1]=(int)(DIM_Y/2-TAM_CUAD/2);//4   	
    	
        pos_y_Producto=(int)(3*DIM_Y/10);
    	pos_y_Autor=(int)(DIM_Y-30);
        
    }
    
    public void disenio_de_cubo(BufferedImage b,int ancho,int alto)
    {
    	Graphics2D graphics2d = b.createGraphics();
        setHints(graphics2d);
        
        //GradientPaint gradientpaint = new GradientPaint(0.0F, (float)(ancho), Color.black, (float)(alto), 0.0F, Color.red);
        GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0f , Color.black, (float)(alto), (float)(ancho) , Color.red);
        
        
        graphics2d.setPaint(gradientpaint);
        graphics2d.fillRect( 1, 1, ancho-1, alto-1 );//tama�o de los cuadrados en el relleno
        graphics2d.setColor(new Color((int)(Math.random()*255),
        							  (int)(Math.random()*255),
        							  (int)(Math.random()*255) ));        							  
        							  
        graphics2d.drawRect(0, 0, ancho, alto);//borde 
        graphics2d.setColor(Color.green);
        graphics2d.drawRect(2, 2, ancho-2, alto-2);
    }
    
    public void disenio_de_esfera(BufferedImage b,int ancho,int alto)
    {
    	Graphics2D graphics2d = b.createGraphics();
        setHints(graphics2d);
        
        //GradientPaint gradientpaint = new GradientPaint(0.0F, (float)(ancho), Color.black, (float)(alto), 0.0F, Color.red);
        GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0f , Color.BLACK, (float)(alto), (float)(ancho) , Color.orange);
                
        graphics2d.setPaint(gradientpaint);
        graphics2d.fillOval( 1, 1, ancho-1, alto-1 );//tama�o de los cuadrados en el relleno
        graphics2d.setColor(new Color((int)(Math.random()*255),
        							  (int)(Math.random()*255),
        							  (int)(Math.random()*255) ));        							  
        							  
        graphics2d.drawOval(0, 0, ancho, alto);//borde 
        graphics2d.setColor(Color.green);
        graphics2d.drawOval(2, 2, ancho-2, alto-2);
    }
    
    

    public void update(Graphics g)
    {
        paint(g);
    }

	int vez=0;
	
    public void paint(Graphics g)
    {
        vez++;
        
        if(imagen == null)
            imagen = createImage(DIM_X, DIM_Y);/*/*//*/*/
        Graphics g1 = imagen.getGraphics();
        Graphics2D graphics2d = (Graphics2D)g1;
        setHints(graphics2d);
			
		if(vez%5==0)        
        {
        graphics2d.setColor(color_de_fondo);//color del fondo
        graphics2d.fillRect(0, 0 , DIM_X, DIM_Y);        
    	}
    	else
    	{
    	graphics2d.clearRect(10,10,60,12);	
    	graphics2d.drawString("VEZ : "+vez ,10,20);	
    	}
        
        if(textos && txt != null)
        {
            dibujar_texto(graphics2d,pos);            
        }        
                
        if(figuras)
        {
            dibujar_escena_de_espera(graphics2d);            
        }                
        dibujar_borde(graphics2d);  
        
         
        g.drawImage(imagen, 0, 0, null);
        g1.dispose();
        //System.out.println("paint");
    }
    
    public void dibujar_borde(Graphics2D graphics2d)
    {
    	graphics2d.setColor(color_de_borde);
    	
    	graphics2d.drawRect(0,0,DIM_X-1,DIM_Y-1);
    	
    	//graphics2d.setColor(color_de_borde.darker());
    	graphics2d.drawRect(1,1,DIM_X-3,DIM_Y-3);
    	
    	//graphics2d.setColor(color_de_borde);    	
    	graphics2d.drawRect(2,2,DIM_X-5,DIM_Y-5);    	
    	
    	
    }	
    
    public void dibujar_escena_de_espera(Graphics2D graphics2d)
    {
    	graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP , 0.5F));    	
    	    	
    for(int a=0;a<radio_fragmentos.length;a++)
    {
    	if(choque[a])
    	{
    	int nro_fragmentos=(TAM_CUAD/radio_fragmentos[a]);
    	double salto_angulo=2*Math.PI/nro_fragmentos;
    		for(int i=0;i<nro_fragmentos;i++)
    		{
    		double cos=Math.cos(i*salto_angulo);	
    		double sen=Math.sin(i*salto_angulo);
    		tam[a]=(int)3*TAM_CUAD/(radio_fragmentos[a]);
    		BufferedImage b = new BufferedImage( tam[a] , tam[a] , BufferedImage.TYPE_3BYTE_BGR);	
    		disenio_de_cubo(b,tam[a],tam[a]);
    		graphics2d.drawImage(b, (int)(actual_x[a]+cos*radio_fragmentos[a]*10+TAM_CUAD/2),
    								(int)(actual_y[a]+sen*radio_fragmentos[a]*10+TAM_CUAD/2 ), null);	
    		
    		/*dise�o_de_esfera(b,tam[a],tam[a]);
    		graphics2d.drawImage(b, (int)(actual_x[a]+cos*radio_fragmentos[a]*10+TAM_CUAD/2),
    								(int)(actual_y[a]+sen*radio_fragmentos[a]*10+TAM_CUAD/2 ), null);	
    		*/
    		}
    	//radio_fragmentos+=1;    	
    	}
    	else
    	{
///////////////////////////////////////////////////////////////////////////////    		
    		
    		if(a==0)    		
    		{
    		if(actual_y[a]>explota_Y[a])
    		{graphics2d.drawImage(bufferedImage, (int)(actual_x[a]),(int)(actual_y[a]), null);
    		 /*actual_y-=10;*/}
    		else {choque[a]=true;
    				
    				sonar();
    				
    		      actual_y[a]+=10;
    			  graphics2d.drawImage(bufferedImage, (int)(actual_x[a]),(int)(actual_y[a]), null);
    		 	 }
    		 }
    		 if(a==1)    		
    		{
    		if(actual_y[a]<explota_Y[a])
    		{graphics2d.drawImage(bufferedImage, (int)(actual_x[a]),(int)(actual_y[a]), null);
    		 /*actual_y-=10;*/}
    		else {choque[a]=true;
    			  sonar();
    			  graphics2d.drawImage(bufferedImage, (int)(actual_x[a]),(int)(actual_y[a]), null);
    		 	 }
    		 }
///////////////////////////////////////////////////////////////////////////////    		
    		 
    		     		
    	}
	}
	    		
    }

    void setHints(Graphics2D graphics2d)
    {
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

	
	

    public void dibujar_texto(Graphics2D graphics2d,int p)
    {
       
        String s = ES_Utiles.PRODUCTO;
        //int i = s.length();
        //try
        //{
        //    text = true;
            //for(int j = 0; j <= i; j++)
            //{
                //Thread.sleep(35L);
        txt = s.substring(0, p);
        //System.out.println(".dibujar_texto:txt="+txt);
        //System.out.println(".dibujar_texto:todas_las_fuentes.length="+todas_las_fuentes.length);//169
        for (int i = 0; i < todas_las_fuentes.length; i++)
        {
        	
        	//String text_temp=s.substring(0, (p+ (int)(Math.random()*ESUtils.PRODUCT.length() ))%ESUtils.PRODUCT.length() );
        	String text_temp=s.substring(0, (p) );
        	
        	
        	Font font = new Font(todas_las_fuentes[i].getName(),
        						 Font.BOLD,	tamanios_fuentes[i] );
			
			graphics2d.setFont(font);
			graphics2d.setColor(new Color( (int)(Math.random()*255),
										   (int)(Math.random()*255),
										   (int)(Math.random()*255)) );
										  
			
			graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP , (float)( Math.random() )));  
			graphics2d.drawString(text_temp, (int)(Math.random()*DIM_X) ,//pos_X_fuentes[i]
											 (int)(Math.random()*DIM_Y) );//pos_Y_fuentes[i]							  
			        
        }      //repaint();
            //}

        //}
       // catch(InterruptedException interruptedexception) { }
        
        graphics2d.setFont(font_titlulo);
        graphics2d.setColor( color_de_texto.darker() );        
        graphics2d.drawString(txt, 30 , pos_y_Producto );        
        graphics2d.setColor(color_de_texto);
        graphics2d.drawString(ES_Utiles.PRODUCTO, 20 , pos_y_Producto+10 );
            
        graphics2d.setColor(Color.white);
        graphics2d.setFont(font_nombre);
        graphics2d.drawString(ES_Utiles.AUTOR, 30 , pos_y_Autor );
        
    }
         
    public void run()
    {
    	figuras = true;
    	textos =true;
    	
    	//if(radio_fragmentos<40)
    for(int a=0;a<radio_fragmentos.length;a++)
       	while(radio_fragmentos[a]<50)
        {
    	
    	if(choque[a])
    	{    	
    	radio_fragmentos[a]+=1;    	
    	}
    	else
    	{
    		
    		
    		
///////////////////////////////////////////////////////////////////////////////    		
    		if(a==0)
    		{
    		if(actual_y[a]>explota_Y[a])
    		{
    		 actual_y[a]-=5;
    		}    		
    		else {
    				choque[a]=true;
    				sonar();
    				actual_y[a]+=5;
    		 	 }  
    		}
    		
    		if(a==1)
    		{
    		if(actual_y[a]<explota_Y[a])
    		{
    		 actual_y[a]+=5;
    		}    		
    		else {
    				choque[a]=true;
    				sonar();
    		 	 }  
    		}
///////////////////////////////////////////////////////////////////////////////    		
    		 	 
    		 	 
    		 	   		
    	}
    	   String s = ES_Utiles.PRODUCTO;
        	int t = s.length();
            if( (pos+1)<t  )
            pos+=1;
            else if( (pos+1) == t )
            	 pos=1;	
                
    	esperar(tiempo_de_espera);
    	repaint();
    	}
    	
    	  	
    }
    
    
    
    
    
    
    
    AudioStream as;
    public void cargar_AudioStream()
    {
    try {
	//System.out.println((new File(".")).getAbsolutePath());
    as = new AudioStream(new FileInputStream("./out/production/Paint3D/com/cc/paint3D/Graficador3D/sound/sound_harp.au"));
    	}
    catch (IOException e) {System.err.println("no se puede abrir el flujo de audio");
		}
    }
    
    public void sonar()
    {
    	/*************************************************/
    				javax.swing.Timer t = new javax.swing.Timer(1000,this);
    				//new javax.swing.Timer(1000,null);
    				t.start();
					try {
	    			AudioPlayer.player.start(as);
	    			t.wait(1000);
	    			AudioPlayer.player.stop(as);
	    			t.stop();     
					} catch (Exception e) {
	 			   System.err.println("no se puede abrir el flujo de audio");
					}
					as=null;
					cargar_AudioStream();
		/*************************************************/
    }
    public void actionPerformed(ActionEvent e) {
      
      //(e.getSource() == tiempo) {
      
   }
    
    
    public void esperar(int tiempo)
    {        
        try
        {         
            Thread.sleep(tiempo);
        }
        catch(InterruptedException interruptedexception) {System.out.println("Error esperar:"+interruptedexception);}
    }
}