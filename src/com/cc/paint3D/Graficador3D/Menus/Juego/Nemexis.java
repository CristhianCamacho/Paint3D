package com.cc.paint3D.Graficador3D.Menus.Juego;

import com.sun.j3d.utils.applet.MainFrame;


// Fichero Nemexis.java
import java.applet.*;
import java.awt.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.applet.Applet;
import java.lang.InterruptedException;
import java.awt.event.*;
import java.awt.image.*;

//****************************************************************************************
//****************************************************************************************
//****************************************************************************************
//****************************************************************************************
//  Clase Nemexis
//   Juego de naves, scroll horizontal, enemigos especiales, y con enemigo final (espero).
// Todo un insulto al original de MSX.
//****************************************************************************************
//****************************************************************************************
//****************************************************************************************
public class Nemexis extends Applet implements KeyListener {

  //****************************************************************************
  //****************************************************************************
  // Clase Coordenadas
  //****************************************************************************
  //****************************************************************************
  class Coordenadas { 
    int x,y; 
	
    //------------------------------------------------------
    //Constructor 1
    Coordenadas(int sel_x,int sel_y) { x=sel_x; y=sel_y; };
 
    //------------------------------------------------------
    //Constructor 2
    Coordenadas(String buf_par) 
    { x=(new Integer(buf_par.substring(0,buf_par.indexOf(",")))).intValue();
      y=(new Integer(buf_par.substring(buf_par.indexOf(",")+1,buf_par.length()))).intValue();
    }

    //------------------------------------------------------
    //Constructor de copia
    Coordenadas(Coordenadas obj) { x=obj.x; y=obj.y; };

    //------------------------------------------------------
    //Constructor de asignacion
    void copia(Coordenadas obj) { x=obj.x; y=obj.y; };

    //------------------------------------------------------    
    public boolean equals(Coordenadas obj)  
    { return ((x==obj.x)&&(y==obj.y)); }

    //------------------------------------------------------    
    public void show()  
    {  System.out.print("("+x+","+y+")");  }  

    //------------------------------------------------------
    public double diferencia_absoluta(Coordenadas obj)
    {  return(Math.sqrt(Math.pow(x-obj.x,2)+Math.pow(y-obj.y,2))); }

    //------------------------------------------------------    
    public String getPaquete()  
    {  return(x+","+y);  }  

  } // Fi clase Coordenadas
 //****************************************************************************
 





 //****************************************************************************
 //****************************************************************************
 // Clase Coordenadas_Double
 //****************************************************************************
 //****************************************************************************
 // Clase Coordenadas_Double
 class Coordenadas_Double{ 
    double x,y; 
  	
    //------------------------------------------------------
    // Constructor 1
    Coordenadas_Double(double sel_x,double sel_y) { x=sel_x; y=sel_y; };
 
    //------------------------------------------------------
    // Constructor 2
    Coordenadas_Double(double sel_x,double sel_y,int color_par) { x=sel_x; y=sel_y; }
 
    //------------------------------------------------------
    //Constructor de copia
    Coordenadas_Double(Coordenadas_Double obj) { x=obj.x; y=obj.y;  };

    //------------------------------------------------------
    // Constructor de asignacion
    void copia(Coordenadas_Double obj) { x=obj.x; y=obj.y; };

    //------------------------------------------------------    
    public boolean equals(Coordenadas_Double obj)  
    { return ((x==obj.x)&&(y==obj.y)); }

    //------------------------------------------------------    
    public void show()  
    {  System.out.print("("+x+","+y+")");  }  

    //------------------------------------------------------    
    public String get_Paquete()
    {  return ("("+x+","+y+")"); }
    
    //------------------------------------------------------    
    public void set_Paquete(String buf)
    {  x=(new Double(buf.substring(buf.indexOf("(")+1,buf.indexOf(",")))).doubleValue();
       y=(new Double(buf.substring(buf.indexOf(",")+1,buf.indexOf(")")))).doubleValue();
    }

    //------------------------------------------------------
    public double diferencia_absoluta(Coordenadas_Double obj)
    {  return(Math.sqrt(Math.pow(x-obj.x,2)+Math.pow(y-obj.y,2))); }

  }// Fi clase Coordenadas_Double
 //****************************************************************************
 

  //-----------------------------------------------------------------------------
  // Funcion reorientar_Angulo(double orientacion_par, double rang_inf, double rang_sup)
  //   Realizara el mapeo inverso entre los dos extremos
  double reorientar_Angulo(double orientacion_par, double rang_inf, double rang_sup)
  {
   return (-orientacion_par+rang_sup+rang_inf);
  }


  //---------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------
  // Funcion reorienta_Angulo
  //   Reorientamos el angulo de lanzamiento
  //---------------------------------------------------------------------------------------------
  double reorienta_Angulo(double orientacion_lanzamiento) 
  {
    // Modificamos la orientacion
    if ((orientacion_lanzamiento>=0)&&(orientacion_lanzamiento<(Math.PI/2)))
    {  
      orientacion_lanzamiento=reorientar_Angulo(orientacion_lanzamiento, 0, Math.PI/2);
      orientacion_lanzamiento=((orientacion_lanzamiento)-Math.PI); 
     }
     else
    if ((orientacion_lanzamiento>=(Math.PI/2))&&(orientacion_lanzamiento<Math.PI))
    {     
       orientacion_lanzamiento=reorientar_Angulo(orientacion_lanzamiento, Math.PI/2, Math.PI);
       orientacion_lanzamiento=orientacion_lanzamiento;
     }
     else
     if ((orientacion_lanzamiento>=-Math.PI)&&(orientacion_lanzamiento<-(Math.PI/2)))
     {
       orientacion_lanzamiento=-(reorientar_Angulo(-orientacion_lanzamiento, Math.PI/2, Math.PI));
       orientacion_lanzamiento=(orientacion_lanzamiento)+Math.PI; 
     }
     else
     if ((orientacion_lanzamiento>=-(Math.PI/2))&&(orientacion_lanzamiento<0))
     {
      orientacion_lanzamiento=-reorientar_Angulo(-orientacion_lanzamiento, 0, Math.PI/2);     
      orientacion_lanzamiento=orientacion_lanzamiento;
     }
     return (orientacion_lanzamiento);
  }


  //---------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------
  // Funcion obtener_Angulo_Lanzamiento
  //   Funcion que calculara el angulo efectivo entre la posicion inicial y la final
  //---------------------------------------------------------------------------------------------
  double obtener_Angulo_Lanzamiento(Coordenadas_Double pos_ini_par, Coordenadas_Double pos_fin_par)
  {
     // Calculamos la nueva orientacion
     double orientacion_lanzamiento=Math.atan2((pos_fin_par.x-pos_ini_par.x),(pos_fin_par.y-pos_ini_par.y));

     // Reorientamos el angulo de lanzamiento
     orientacion_lanzamiento=reorienta_Angulo(orientacion_lanzamiento);

     return (orientacion_lanzamiento); 
   }









  
//***************************************************************************************
//***************************************************************************************
//***************************************************************************************
//***************************************************************************************
// Clase Pantalla_Foto
//  Panel en que mostrem la foto final
//***************************************************************************************
//***************************************************************************************
//***************************************************************************************
class Pantalla_Foto extends Panel implements Runnable {
     Image imatge_final;          
     String mensaje_informativo="";
     int iteraciones=10;

     // Variables para hacer el doble buffering
     Dimension offDimension;
     Image offImage;
     Graphics2D offGraphics;

     Thread hilo_foto;

     // Constructor---------------------------------
     Pantalla_Foto(Image imag_par,String mensaje_par)
     { imatge_final=imag_par;
       mensaje_informativo=new String(mensaje_par);

       hilo_foto=new Thread(this);
       hilo_foto.start();
     }

     //--------------------------------------------
     // Procedimiento start()
     public void start() {}
 
     //--------------------------------------------
     // Procedimiento run()
     public void run()
     {
      iteraciones=40;

      while (iteraciones>0) 
      {
        repaint();
        try { hilo_foto.sleep(20); } catch (InterruptedException e) {System.out.println(e.toString());};
        iteraciones--;
      }
     
     }


     //-----------------------------------------------------------
     // Procedimiento captura_Dimension
     //  Procedimiento que capturara la dimension para realizar el doble buffering
     void captura_Dimension()
     {try{
       Dimension d = getSize();
     
       // Calculamos las dimensiones de la pantalla pintada
       int ancho_pantalla=d.width;
       int alto_pantalla=d.height;

       // Si no estan instanciadas las variables de dimension, imagen y graficos
       if ((offGraphics == null) || (ancho_pantalla!=offDimension.width) || (alto_pantalla!=offDimension.height))
       { 
         // Capturamos la dimension
         offDimension=new Dimension(ancho_pantalla,alto_pantalla);     

         // Creamos una imagen
         offImage=createImage(ancho_pantalla,alto_pantalla);
 
         // Asociamos la imagen a los graficos
         offGraphics=(Graphics2D)offImage.getGraphics();
        } 
       } catch (Exception ed) { System.out.println("Dimension error"); }
      }

      // Pintamos el estado-------------------
      public void repaint(Graphics g) 
      {  update(g);
         paint(g);
       }


      // Pintamos la foto final-------------------
      public void update(Graphics g) 
      {  
        // Capturem la dimension
        captura_Dimension();

         // Mostramos el mensaje por la pantalla
         if (iteraciones>0)
         {  
    int x_temp=dimension_pantalla.width;
    int y_temp=dimension_pantalla.height;     	
    
    int ancho_mensaje=x_temp-200; 
    int alto_mensaje=y_temp-200; 
    
         	offGraphics.setColor(Color.darkGray);
         	offGraphics.fillRect(75,75,ancho_mensaje,alto_mensaje);
         	
         	offGraphics.setColor(Color.orange);
            offGraphics.fillRect(50,50,ancho_mensaje,alto_mensaje);
    ancho_mensaje-=0;
    alto_mensaje-=0;

offGraphics.drawImage(image_fondo, 0, 0,(int)(x_temp), (int)(y_temp), this);
            
    offGraphics.setColor(Color.red);
    offGraphics.drawRect(59,59,ancho_mensaje-2*9,alto_mensaje-2*9);
    offGraphics.drawRect(58,58,ancho_mensaje-2*8,alto_mensaje-2*8);
    offGraphics.drawRect(57,57,ancho_mensaje-2*7,alto_mensaje-2*7);
    offGraphics.setColor(Color.green);
    offGraphics.drawRect(56,56,ancho_mensaje-2*6,alto_mensaje-2*6);
    offGraphics.drawRect(55,55,ancho_mensaje-2*5,alto_mensaje-2*5);
    offGraphics.drawRect(54,54,ancho_mensaje-2*4,alto_mensaje-2*4);
    offGraphics.setColor(Color.yellow);
    offGraphics.drawRect(53,53,ancho_mensaje-2*3,alto_mensaje-2*3);
    offGraphics.drawRect(52,52,ancho_mensaje-2*2,alto_mensaje-2*2);
    offGraphics.drawRect(51,51,ancho_mensaje-2*1,alto_mensaje-2*1);        
                        
            offGraphics.setColor(Color.black);
            offGraphics.setFont( new Font("Tahoma",Font.BOLD,10) );
            offGraphics.drawString(mensaje_informativo,100,100);
            
            offGraphics.setColor(Color.red.darker());
            offGraphics.setFont( new Font("Tahoma",Font.BOLD,12) );
            offGraphics.drawString(mensaje_informativo,100,110);
                                    
            offGraphics.setColor(Color.red);
            offGraphics.setFont( new Font("Tahoma",Font.BOLD,14) );
            offGraphics.drawString(mensaje_informativo,100,120);
                                   
            offGraphics.setColor(Color.yellow.darker());
            offGraphics.setFont( new Font("Tahoma",Font.BOLD,16) );
            offGraphics.drawString(mensaje_informativo,100,130);
                        
            offGraphics.setColor(Color.yellow);
            offGraphics.setFont( new Font("Tahoma",Font.BOLD,18) );
            offGraphics.drawString(mensaje_informativo,100,140);
            
            offGraphics.setColor(Color.green.darker());
            offGraphics.setFont( new Font("Tahoma",Font.BOLD,20) );
            offGraphics.drawString(mensaje_informativo,100,150);
            
            offGraphics.setColor(Color.green);
            offGraphics.setFont( new Font("Tahoma",Font.BOLD,22) );
            offGraphics.drawString(mensaje_informativo,100,160);
            
         offGraphics.drawImage(imagen_ladrillos,10,10,this);
         
         offGraphics.drawImage(imatge_final,200,200,this);   
                        
         }

        // Escribimos el buffer en pantalla
        g.drawImage(offImage,10,10,this); 
        offImage.flush();     
       }
  
  } // Fi Clase Pantalla_Foto
 //***************************************************************************************































//***************************************************************************************
//***************************************************************************************
//***************************************************************************************
//***************************************************************************************
//***************************************************************************************
// Clase Pantalla
//   Esta clase se encargara de pintar al protagonista, la region donde se encuentra y
// los elementos que se encuentran dentro de esa region.
//***************************************************************************************
//***************************************************************************************
//***************************************************************************************
class Pantalla extends Panel {
      
      // Variables para hacer el doble buffering
       Dimension offDimension;
       Image offImage;
       Graphics offGraphics;

      // Variable que nos determinara el crecimiento del juego
       double krecer;

      // Constantes que determinan la region del mapa globaL que pintaremos
       private int ANCHO_REGION; 
       private int ALTO_REGION; 

       // ---------------------------------------------------------------------------------
       // Constructor 
       Pantalla(double krecer_par)
       {        
         // Establecemos el tamanyo del tablero
         krecer=krecer_par;

         // Establecemos el rango de visualizacion del personaje
         ANCHO_REGION = (int)(ANCHO_CAMPO/20);   // = 400
         ALTO_REGION  = (int)(ALTO_CAMPO);       // = 200
             
         // Establecemos el tamanyo inicial
         setSize((int)(ANCHO_REGION*krecer), (int)(ALTO_REGION*krecer)); 
       }    
       

      //-----------------------------------------------------------
      // Procedimiento captura_Dimension
      //  Procedimiento que capturara la dimension para realizar el doble buffering
      void captura_Dimension()
      {try{
        Dimension d = getSize();
     
        // Calculamos las dimensiones de la pantalla pintada
        int ancho_pantalla=d.width;
        int alto_pantalla=d.height;

        // Si no estan instanciadas las variables de dimension, imagen y graficos
        if ((offGraphics == null) || (ancho_pantalla!=offDimension.width) || (alto_pantalla!=offDimension.height))
        { 
          // Capturamos la dimension
          offDimension=new Dimension(ancho_pantalla,alto_pantalla);     

          // Creamos una imagen
          offImage=createImage(ancho_pantalla,alto_pantalla);
 
          // Asociamos la imagen a los graficos
          offGraphics=offImage.getGraphics();
        } 
       } catch (Exception ed) { System.out.println("Dimension error"); }
      }

       // ---------------------------------------------------------------------------------
       // Procedimiento paint
       public void paint(Graphics g)    {               } // Fi paint
    
       // ---------------------------------------------------------------------------------
       // Procedimiento repaint
       public void repaint(Graphics g)  {  update(g);   } // Fi repaint


       // ---------------------------------------------------------------------------------
       //  Procedimiento obtener_Punto_Pintar
       //    Dado un punto calcularemos el punto de la pantalla donde tenemos que pintarlo
       Coordenadas obtener_Punto_Pintar(Coordenadas pos_par, Coordenadas referencia_par)
       { Coordenadas pos_salida=new Coordenadas(pos_par);
          
         pos_salida.x-=referencia_par.x;
         pos_salida.y-=referencia_par.y; 

         pos_salida.x+=(ANCHO_REGION/2);
         pos_salida.y+=(ALTO_REGION/2);

         return (pos_salida);
       }

          
       // ---------------------------------------------------------------------------------
       //  Procedimiento update
       //    Este procedimiento pintara la region donde se encuentra el personaje
       // y los elementos que se encuentre dentro de la region (rectangulos,personajes,malos,revistas)
       public void update(Graphics g) 
       { 
       try
        {        
         // Capturem la dimension
         captura_Dimension();

         // Limpiamos
         // offGraphics.setColor(Color.blue);
         // offGraphics.fillRect(0, 0, (2*ANCHO_REGION*krecer), (int)(1.25*ALTO_REGION*krecer));  
         offGraphics.drawImage(image_fondo, 0, 0,(int)(ANCHO_REGION*krecer), (int)(1.25*ALTO_REGION*krecer), this);

         // Dibujamos el marco
         offGraphics.setColor(Color.black);
         offGraphics.drawRect(0, 0, (int)(2*ANCHO_REGION*krecer), (int)(1.25*ALTO_REGION*krecer));  
   
         // Capturamos la posicion del centro del tablero
         Coordenadas pos_centro=new Coordenadas(tabla.posicion_centro);                               

         // Pintamos el conjunto de rectangulos
         int contador_rectangulos=1;
         Enumeration erect=tabla.lista_rectangulos.elements();         
         while (erect.hasMoreElements())
         { Vector svect_rect=(Vector)erect.nextElement();
           Coordenadas scord=(Coordenadas)svect_rect.elementAt(0);
 
           // Comprobamos si el rectangulo se encuentra dentro del AMBITO_ACTIVACION
           if (Math.abs(scord.x-pos_centro.x)<AMBITO_ACTIVACION)
           {   
            // Pintamos el rectangulo
            Coordenadas ex1=obtener_Punto_Pintar((Coordenadas)svect_rect.elementAt(0),pos_centro);
            Coordenadas ex2=obtener_Punto_Pintar((Coordenadas)svect_rect.elementAt(1),pos_centro);
 
            // Establecemos el rectangulo de color verde
            int x1=(int)(ex1.x*krecer);
            int y1=(int)((ALTO_REGION+25-ex1.y)*krecer);
            int x2=(int)(Math.abs(ex1.x-ex2.x)*krecer);
            int y2=(int)(Math.abs(ex1.y-ex2.y)*krecer);

            offGraphics.setColor(Color.green);          
            // offGraphics.fillRect(x1,y1,x2,y2);
            offGraphics.drawImage(imagen_ladrillos,x1,y1,x2,y2,this);
            offGraphics.setColor(Color.black);
//offGraphics.drawRect(x1,y1,x2,y2);
            offGraphics.drawString("Plataforma "+contador_rectangulos, x1+5,y1+10);
           } 

           // Si pasamos el limite, no comprobamos mas rectangulos y salimos del flujo
           // if (scord.x-pos_centro.x>AMBITO_ACTIVACION) break;
           
           // Incrementamos el numero de rectangulos
           contador_rectangulos++;
          } // Fi while


        // Pintamos los jugadores 
        Enumeration ejug=lista_jugadores.keys();
        while (ejug.hasMoreElements()) 
        { String snom=(String)ejug.nextElement();
          Jugador sjug=(Jugador)lista_jugadores.get(snom);

          // Obtenemos el punto en la pantalla para el personaje
          Coordenadas pos_jug_pintar=obtener_Punto_Pintar(sjug.posicion,pos_centro);
        //Pintamos el personaje y su nombre
        //offGraphics.drawImage(sjug.determina_Imagen_Jugador(), (int)((pos_jug_pintar.x-15)*krecer), (int)((ALTO_REGION-pos_jug_pintar.y+5)*krecer), this);
        
        if( (sjug.determina_Imagen_Jugador()==imagen_nave) )
        {
          	offGraphics.drawImage(sjug.determina_Imagen_Jugador(), (int)((pos_jug_pintar.x-15)*krecer), (int)((ALTO_REGION-pos_jug_pintar.y+5)*krecer),40,40,this);
        }
        else
        if( (sjug.determina_Imagen_Jugador()==imagen_nave_inmune) )
        {
          	//offGraphics.drawImage(imagen_nave, (int)((pos_jug_pintar.x-15)*krecer), (int)((ALTO_REGION-pos_jug_pintar.y+5)*krecer),60,50,this);
           	offGraphics.drawImage(sjug.determina_Imagen_Jugador(), (int)((pos_jug_pintar.x-50)*krecer), (int)((ALTO_REGION-pos_jug_pintar.y-10)*krecer),this);
        	offGraphics.drawImage(imagen_explosion, (int)((pos_jug_pintar.x-50)*krecer), (int)((ALTO_REGION-pos_jug_pintar.y-10)*krecer),this);
        	offGraphics.drawImage(imagen_nave, (int)((pos_jug_pintar.x-15)*krecer), (int)((ALTO_REGION-pos_jug_pintar.y+5)*krecer),40,40,this);
        }
        else  
        if( (sjug.determina_Imagen_Jugador()==imagen_explosion) )
        {
        	offGraphics.drawImage(sjug.determina_Imagen_Jugador(), (int)((pos_jug_pintar.x-50)*krecer), (int)((ALTO_REGION-pos_jug_pintar.y-10)*krecer),this);
        }
        //offGraphics.setColor( new Color(255,200,230) );
        //offGraphics.fillOval((int)((pos_jug_pintar.x-15)*krecer), (int)((ALTO_REGION-pos_jug_pintar.y+5)*krecer), Gestor_Enemigos.RADIO_ENEMIGO , Gestor_Enemigos.RADIO_ENEMIGO );
          	
		//offGraphics.drawString(sjug.nombre, (int)((pos_jug_pintar.x-5)*krecer), (int)((ALTO_REGION-pos_jug_pintar.y-5)*krecer));
			
			
          // Pintamos las balas
          Enumeration ebal=sjug.gestor_balas.lista_balas.elements();
          while (ebal.hasMoreElements())
          { Coordenadas scordbala=(Coordenadas)ebal.nextElement();
 
            // Obtenemos el punto en la pantalla para el personaje
            Coordenadas pos_bal_pintar=obtener_Punto_Pintar(scordbala,pos_centro);
            
            // Establecemos las balas de color amarillo
            offGraphics.setColor(Color.yellow);
            offGraphics.fillOval((int)(pos_bal_pintar.x*krecer), (int)((ALTO_REGION-pos_bal_pintar.y)*krecer)+50, (int)(Gestor_Balas.ANCHO_BALA*krecer), (int)(Gestor_Balas.ALTO_BALA*krecer));
            //offGraphics.fillRect((int)(pos_bal_pintar.x*krecer), (int)((ALTO_REGION-pos_bal_pintar.y)*krecer)+50, (int)(Gestor_Balas.ANCHO_BALA*krecer), (int)(Gestor_Balas.ALTO_BALA*krecer));
            offGraphics.setColor(Color.red);
            //offGraphics.drawRect((int)(pos_bal_pintar.x*krecer), (int)((ALTO_REGION-pos_bal_pintar.y)*krecer)+50, (int)(Gestor_Balas.ANCHO_BALA*krecer), (int)(Gestor_Balas.ALTO_BALA*krecer));            
			offGraphics.drawOval((int)(pos_bal_pintar.x*krecer), (int)((ALTO_REGION-pos_bal_pintar.y)*krecer)+50, (int)(Gestor_Balas.ANCHO_BALA*krecer), (int)(Gestor_Balas.ALTO_BALA*krecer));
            
            //offGraphics.fillOval((int)(pos_bal_pintar.x*krecer+2), (int)((ALTO_REGION-pos_bal_pintar.y)*krecer+2)+50, (int)(Gestor_Balas.ANCHO_BALA*krecer-5), (int)(Gestor_Balas.ALTO_BALA*krecer-5));
            
			
          } // Fi while balas

        } // Fi while jugadores


        // Pintamos los enemigos
        Enumeration e=gestor_enemigos.lista_enemigos.elements();
        while (e.hasMoreElements())
        { Enemigo sene=(Enemigo)e.nextElement();
          
          // Obtenemos el punto en la pantalla para el enemigo
          Coordenadas pos_ene_pintar=obtener_Punto_Pintar(sene.posicion,pos_centro);

          // Pintamos el personaje y su nombre
       //offGraphics.setColor( new Color(255,200,230) );
       //offGraphics.fillOval((int)((pos_ene_pintar.x+10)*krecer), (int)((ALTO_REGION-pos_ene_pintar.y+15)*krecer), Gestor_Enemigos.RADIO_ENEMIGO , Gestor_Enemigos.RADIO_ENEMIGO );
          
          if( sene.determina_Imagen_Enemigo()==imagen_pikachu )
          {
          offGraphics.drawImage(sene.determina_Imagen_Enemigo(), (int)((pos_ene_pintar.x+10)*krecer), (int)((ALTO_REGION-pos_ene_pintar.y+15)*krecer),50,20, this);
 		  }
          else
          offGraphics.drawImage(sene.determina_Imagen_Enemigo(), (int)((pos_ene_pintar.x+10)*krecer), (int)((ALTO_REGION-pos_ene_pintar.y+15)*krecer), this);
 			
         } // Fi while enemigos

       } catch (Exception egg) {};

       // Escribimos el buffer en pantalla
       g.drawImage(offImage, 0, 0, this);
       paint(g);
     }
  }  // Fi clase Pantalla
 //****************************************************************************

























  //****************************************************************************************
  //****************************************************************************************
  //****************************************************************************************
  //****************************************************************************************
  //****************************************************************************************
  // Clase Reloj_Nemexis
  //  Panel que se encargara de mostrar un reloj por pantalla y llevar el tiempo
  //****************************************************************************************
  //****************************************************************************************
  //****************************************************************************************
  class Reloj_Nemexis extends Panel implements Runnable {
       int tiempo;
       Label etiqueta_tiempo;
       Thread t;

       // Constructor---------------------------
       Reloj_Nemexis()
       { tiempo=0;
         etiqueta_tiempo=new Label("Tiempo = "+tiempo);
         add(etiqueta_tiempo);      
  
         t=new Thread(this); 
         t.start();
       }

      // Start ---------------------------
      public void start() { }         

      //----------------------------------------
      // Procedimiento run()
      //  Se encarga la etiqueta del reloj
      public void run() 
      { 
        while(tiempo>=0)
        { 
         // Damos el hilo a los otros
         try { Thread.currentThread().sleep(1000); }
         catch (InterruptedException e) {}   
         tiempo++;
         etiqueta_tiempo.setText("Tiempo = "+tiempo);
        }      
      }
  }   

































 





 //*****************************************************************************************
 //*****************************************************************************************
 // Clase Tablero
 //   Esta clase sera el contenedor del mapeado del juego. Ofrecera :
 //      -Funciones para consultar las posiciones dentro de ese mapeado.
 //      -Se encargara de mover a una velocidad constante el centro de la pantalla.
 //      -(Monoplayer): Creara y lanzara las rafagas de enemigos almacenadas en checkpoints
 //****************************************************************************************
 //****************************************************************************************
 class Tablero implements Runnable {
   
   // Posicion del centro del juego
   Coordenadas posicion_centro;

   // Lista de rectangulos que representaran el suelo del juego
   Vector lista_rectangulos=new Vector();   // Lista de (content=Vector)

   // Lista de checkpoint desde donde se lanzaran las rafagas de enemigos
   Hashtable lista_checkpoints=new Hashtable();

   // Variable booleana que determina el flujo de vida del juego
   boolean salir_tablero=false;

   // Variable que determinara si hemos lanzado el enemigo final
   boolean enemigo_final_lanzado=false;
  
   public final static int paso=200;   // Es el tamanyo del paso en el eje X de la pantalla

   // Constantes que nos definen el tamanyo del tablero
   public int ANCHO_TABLERO;
   public int ALTO_TABLERO;

   // Constante que determina la velocidad de desplazamiento
   public final static int VELOCIDAD_DESPLAZAMIENTO_CENTRO = 25;//50;
 
   // Constante que determina el desplazamiento del centro
   public final static int DISTANCIA_DESPLAZAMIENTO_CENTRO = 2;

   Thread hilo;  // Hilo de ejecucion

   //-------------------------------------------------------------------------
   // Constructor: Inicializara todos los elementos del juego
   //     A-Inicializamos las coordenadas del centro
   //     A-Construira la lista de rectangulos del mapeado
   //     B-Inicializara el conjunto de checkpoints de rafagas de enemigos
   //     C-Arrancara el proceso
   Tablero()
   {
    // Incializamos el ancho y alto del tablero
    ANCHO_TABLERO =(int)(ANCHO_CAMPO/20);
    ALTO_TABLERO = (int)(ALTO_CAMPO);

    // Inicializamos la posicion inicial del juego
    posicion_centro=new Coordenadas((int)(ANCHO_TABLERO/2),(int)(ALTO_TABLERO/2));
    

    // Construimo el mapa de rectangulos 
    construir_Mapa_Rectangulos();

    // Incializamos la lista de checkpoints de las rafagas de enemigos
    incializar_Lista_CheckPoint();
    
    // Arrancamos el hilo de ejecucion
    hilo=new Thread(this);
    hilo.start();
   }


   //-------------------------------------------------------------------------
   // Funcion crear_Rectangulo
   //   Funcion que construira un vector que representara un rectangulo de plataforma
   Vector crear_Rectangulo(int x1, int y1, int x2, int y2)
   { Vector salida=new Vector();
     
     salida.addElement(new Coordenadas(x1, y1));  // Construimos el punto de inicio
     salida.addElement(new Coordenadas(x2, y2));  // Construimos el punto de fin
    
     return (salida); 
   }


   //-------------------------------------------------------------------------
   // Procedimiento construir_Mapa_Rectangulos()
   //   Construiremos el mapeado del juego a partir de la construccion de rectangulos
   // los cuales representaran las plataformas.
   void construir_Mapa_Rectangulos()
   {    
    // Rect 1
    lista_rectangulos.addElement(crear_Rectangulo(4*paso,20,7*paso,0));
    // Rect 2
    lista_rectangulos.addElement(crear_Rectangulo(5*paso,200,9*paso,180));

    // Rect 3
    lista_rectangulos.addElement(crear_Rectangulo(7*paso,60,12*paso,0));

    // Rect 4
    lista_rectangulos.addElement(crear_Rectangulo(9*paso,200,11*paso,180));
    // Rect 4.1
    lista_rectangulos.addElement(crear_Rectangulo(11*paso,200,14*paso,180));
    // Rect 4.2
    lista_rectangulos.addElement(crear_Rectangulo(14*paso,200,15*paso,180));

    // Rect 5 
    lista_rectangulos.addElement(crear_Rectangulo(12*paso,20,17*paso,0));

    // Rect 6
    lista_rectangulos.addElement(crear_Rectangulo(19*paso,120,23*paso,80));

    // Rect 7.0
    // lista_rectangulos.addElement(crear_Rectangulo(25*paso,200,28*paso,160));
    // Rect 7.1
    // lista_rectangulos.addElement(crear_Rectangulo(28*paso,200,31*paso,160));
    // Rect 7.2
    // lista_rectangulos.addElement(crear_Rectangulo(31*paso,200,35*paso,160));

    // Rect 8.0
    // lista_rectangulos.addElement(crear_Rectangulo(25*paso,40,28*paso,0));
    // Rect 8.1
    // lista_rectangulos.addElement(crear_Rectangulo(28*paso,40,31*paso,0));
    // Rect 8.2
    // lista_rectangulos.addElement(crear_Rectangulo(31*paso,40,35*paso,0));

    // Rect 9
    lista_rectangulos.addElement(crear_Rectangulo(26*paso,140,29*paso,120));
    // Rect 10
    lista_rectangulos.addElement(crear_Rectangulo(26*paso,80,29*paso,60));
    // Rect 11
    lista_rectangulos.addElement(crear_Rectangulo(30*paso,120,34*paso,80));

   }

   //-------------------------------------------------------------------------
   // Procedimiento incializar_Lista_CheckPoint()
   //   Inicializaremos la lista de rafagas de enemigos
   void incializar_Lista_CheckPoint()
   {
     lista_checkpoints.put(new Coordenadas(aleatorio(2*paso,(2*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(2*paso,(2*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(3*paso,(3*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio((int)(3.5*paso),(int)(3.5*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(4*paso,(4*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio((int)(4.5*paso),(int)(4.5*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(5*paso,(5*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(6*paso,(6*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(7*paso,(7*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(7*paso,(7*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(8*paso,(8*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(9*paso,(9*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio((int)(9.5*paso),(int)(9.5*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(10*paso,(10*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio((int)(10.5*paso),(int)(10.5*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(11*paso,(11*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(12*paso,(12*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(13*paso,(13*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(13*paso,(13*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(14*paso,(14*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(15*paso,(15*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(15*paso,(15*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(15*paso,(15*paso)+10),-1),new Integer(aleatorio(0,8)));




     lista_checkpoints.put(new Coordenadas(aleatorio(17*paso,(17*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(18*paso,(18*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(19*paso,(19*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio((int)(19.5*paso),(int)(19.5*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(20*paso,(20*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio((int)(20.5*paso),(int)(20.5*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(22*paso,(22*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(23*paso,(23*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio((int)(23.5*paso),(int)(23.5*paso)+10),-1),new Integer(aleatorio(0,3)));

     lista_checkpoints.put(new Coordenadas(aleatorio(24*paso,(24*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio((int)(24.5*paso),(int)(24.5*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(26*paso,(26*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(28*paso,(28*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(29*paso,(29*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(30*paso,(30*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(30*paso,(30*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(31*paso,(31*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(31*paso,(31*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(31*paso,(31*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(32*paso,(32*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(34*paso,(34*paso)+10),-1),new Integer(aleatorio(0,8)));

     lista_checkpoints.put(new Coordenadas(aleatorio(34*paso,(34*paso)+10),-1),new Integer(aleatorio(0,8)));
   }


   //-------------------------------------------------------------------------
   // Funcion esta_Dentro
   //   Esta funcion determinara si la posicion se encuentra dentro del rectangulo 
   // especificado.
   boolean esta_Dentro(Coordenadas posicion_par, Coordenadas ex1, Coordenadas ex2)   
   {
     // Comprobamos en el eje X
     if ((ex1.x<=posicion_par.x)&&(ex2.x>=posicion_par.x))
     { // Comprobamos en el eje Y
       if ((ex1.y>=posicion_par.y)&&(ex2.y<=posicion_par.y))
         return (true);
     } 
    return (false);
   }

   //-------------------------------------------------------------------------
   // Funcion esta_Ocupado
   //  Esta funcion comprobara si la posicion esta libre o ocupada
   boolean esta_Ocupado(Coordenadas posicion_par)
   { // Comprobamos las frontera del tablero
     if (posicion_par.x<=50) return (true);

     // Comprobamos las frontera del tablero
     if (posicion_par.x>=ANCHO_CAMPO-50) return (true);

     // Recorremos el mapa de rectangulos hasta encontrar los rectangulos mas cercanos
     Enumeration e=lista_rectangulos.elements();
     while (e.hasMoreElements())
     { Vector slisrect=(Vector)e.nextElement();
       Coordenadas ex1=(Coordenadas)slisrect.elementAt(0);
       
       // Comprobamos si el punto se encuentra dentro del ambito
       if (Math.abs(posicion_par.x-ex1.x)<AMBITO_ACTIVACION)
       { 
          // Capturamos las coordenadas de fin del rectangulo
          Coordenadas ex2=(Coordenadas)slisrect.elementAt(1);
   
          // Comprobamos si el punto esta dentro del rectangulo
          if (esta_Dentro(posicion_par,ex1,ex2)) return (true); 
       }
     
       // Comprobamos el rectangulo esta alejado si estamos lejos y no comprobamos mas
       if ((ex1.x-posicion_par.x)>AMBITO_ACTIVACION) break;
     }
 
     return (false);
   }

   //-------------------------------------------------------------------------
   // Funcion capturar_Rafaga_Checkpoint
   //   Esta funcion delvolvera el tipo de rafaga de enemigos asociado al checkpoint mas
   // cercano a la posicion por parametro
   int capturar_Rafaga_Checkpoint(Coordenadas pos_par)
   { int rafaga_seleccionada=-1; 
     Coordenadas coordenadas_seleccionada=null;

     // Por todos los checkpoints
     Enumeration e=lista_checkpoints.keys();
     while (e.hasMoreElements())
     { Coordenadas scord=(Coordenadas)e.nextElement();

       // Comprobamos si estamos cerca
       if (Math.abs(scord.x-pos_par.x)<=DISTANCIA_DESPLAZAMIENTO_CENTRO*20)
       {  rafaga_seleccionada=((Integer)lista_checkpoints.get(scord)).intValue();
          lista_checkpoints.remove(scord);
          break; 
       }

     } // Fi while

     return (rafaga_seleccionada);

   } // Fi capturar_Rafaga_Checkpoint
        

   //-------------------------------------------------------------------------
   // Procedimiento lanzar_Rafaga_Enemigos
   //  Este procedimiento lanzara un conjunto de enemigos segun la seleccion
   void lanzar_Rafaga_Enemigos(int rafaga_par, Coordenadas pos_par)
   { Coordenadas planzar=new Coordenadas(pos_par.x+(int)(ANCHO_TABLERO/(1.5)),pos_par.y);

     // Segun la rafaga seleccionada
     switch (rafaga_par) {
       case 0: gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x,planzar.y+50),Enemigo.ESTRELLA);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+40,planzar.y+40),Enemigo.ESTRELLA);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+80,planzar.y+30),Enemigo.ESTRELLA);
               break;
       case 1: gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x,planzar.y-50),Enemigo.JARJAR);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+40,planzar.y-40),Enemigo.JARJAR);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+80,planzar.y-30),Enemigo.JARJAR);
               break;
       case 2: gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x,planzar.y),Enemigo.PIKACHU);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+40,planzar.y-50),Enemigo.PIKACHU);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+80,planzar.y+50),Enemigo.PIKACHU);
               break;
       case 3: gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x,planzar.y),Enemigo.PRIN);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+40,planzar.y+25),Enemigo.PRIN);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+80,planzar.y+50),Enemigo.PRIN);
               break;
       case 4: gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x,planzar.y),Enemigo.PELUSA);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+40,planzar.y-25),Enemigo.PELUSA);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+80,planzar.y+25),Enemigo.PELUSA);
               break;
       case 5: gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x,planzar.y),Enemigo.XBOX);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+40,planzar.y-50),Enemigo.XBOX);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+80,planzar.y+50),Enemigo.XBOX);
               break;
       case 6: gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x,planzar.y),Enemigo.BANDERA);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+40,planzar.y+50),Enemigo.BANDERA);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+80,planzar.y-50),Enemigo.BANDERA);
               break;
       case 7: gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x,planzar.y),Enemigo.MENEO);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+40,planzar.y-50),Enemigo.MENEO);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+80,planzar.y-25),Enemigo.MENEO);
               break;
       default:gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x,planzar.y-20),Enemigo.ESTRELLA);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+40,planzar.y),Enemigo.ESTRELLA);
               gestor_enemigos.lanzar_Enemigo(new Coordenadas(planzar.x+80,planzar.y+20),Enemigo.ESTRELLA);
               break;
     }
   } // Fi lanzar_Rafaga_Enemigos
     

   //-------------------------------------------------------------------------
   // Procedimiento run
   //   Ejecutara periodicamente el desplazamiento del centro de la tabla y
   // comprobara si para esa posicion ha de activar alguna rafaga de enemigos
   public void run() 
   { int rafaga_activada=-1;

     // Mientras el proceso este vivo
     while (salir_tablero==false) 
     { 
       // Dormimos el intervalo de movimiento
       try { hilo.sleep(VELOCIDAD_DESPLAZAMIENTO_CENTRO); } catch (Exception egt) {};

       // Comprobamos si llegamos a la posicion final de pantalla
       if (posicion_centro.x<=ANCHO_CAMPO-500) 
       {
         // Desplazamos el centro sobre el eje X
         posicion_centro.x+=DISTANCIA_DESPLAZAMIENTO_CENTRO;

         // Comprobamos si activamos alguna rafaga de enemigos
         rafaga_activada=capturar_Rafaga_Checkpoint(posicion_centro);
         if (rafaga_activada!=-1)
         { System.out.println("Rafaga encontrada en ="+posicion_centro.getPaquete());
           lanzar_Rafaga_Enemigos(rafaga_activada,posicion_centro);
         }
       } // Fi if
       else // Si llegamos al enemigo final
       { 
          // Lanzamos el enemigo final
          if (!enemigo_final_lanzado)
          {
            enemigo_final_lanzado=true;
            gestor_enemigos.lanzar_Enemigo(new Coordenadas(posicion_centro.x+80,posicion_centro.y),Enemigo.MONSTRUO_FINAL);
          }

          // Lanzamos rafagas de enemigos
          if (aleatorio(0,100)<1)
             lanzar_Rafaga_Enemigos(aleatorio(0,5),posicion_centro);          
       }
     } // Fi while
   }
       


 }  // Fi clase Tablero
 //****************************************************************************


























 //****************************************************************************
 //****************************************************************************
 // Clase Enemigo
 //****************************************************************************
 //****************************************************************************
 class Enemigo  {
   Coordenadas posicion;        // Posicion del Enemigo
   int direccion;               // Direccion del Enemigo (IZQUIERDA, DERECHA)
   int tipo;                    // Nos indica el tipo del Enemigo
   int vidas;                   // Nos indica de cuantas vidas tiene el enemigo

   int sentido=1;               // Sentido vertical del desplazamiento
   
   double incremento_velocidad=0; // Incremento gradual de velocidad

   Coordenadas_Double posicion_bala; // Posicion del enemigo bala
   double angulo_bala=-1;            // Representa el angulo de desplazamiento de la bala

   // Constantes que representan el tipo de los malos
   public static final int ESTRELLA   = 0;
   public static final int JARJAR     = 1;
   public static final int PIKACHU    = 2;
   public static final int PRIN       = 3;
   public static final int PELUSA     = 4;
   public static final int XBOX       = 5;
   public static final int BANDERA       = 6;
   public static final int MENEO      = 7;

   public static final int BALA       = 10;
   //public static final int BALA_1       = 11;
   
   public static final int MONSTRUO_FINAL = 12;

   // Constantes del personaje
   private static final int DESPLAZAMIENTO_MALO = 2;

   //-------------------------------------------------------------------------
   // Constructor
   //   Inicializamos el personaje enemigo, su posicion y su tipo 
   Enemigo(Coordenadas pos_par, int tipo_par) 
   { posicion=new Coordenadas(pos_par);
     direccion=IZQUIERDA;
     tipo=tipo_par;    
    
     // Asignamos el numero de vidas segun el tipo
     switch (tipo) {
         case  ESTRELLA: vidas=3; break;
         case  JARJAR:   vidas=3; break;
         case  PIKACHU:  vidas=2; break;
         case  PRIN:     vidas=1; break;
         case  PELUSA:   vidas=1; break;
         case  XBOX:     vidas=1; break;
         case  BANDERA:     vidas=3; break;
         case  MENEO:    vidas=3; break;
         case  MONSTRUO_FINAL: vidas=VIDA_ENEMIGO_FINAL; 
         					   vida_enemigo_final=vidas; break;
         default:  vidas=1; break;
     }

   }

   //-------------------------------------------------------------------------
   // Constructor
   //   Inicializamos el malo correspondiente a la bala
   Enemigo(Coordenadas pos_ini_par, Coordenadas pos_fin_par)
   { posicion=new Coordenadas(pos_ini_par);
     direccion=IZQUIERDA;
     tipo=BALA;
     vidas=5;
     posicion_bala=new Coordenadas_Double(pos_fin_par.x,pos_fin_par.y);
          
     // Calculamos el angulo de desplazamiento de la bala
     angulo_bala=obtener_Angulo_Lanzamiento(new Coordenadas_Double(pos_ini_par.x,pos_ini_par.y), new Coordenadas_Double(pos_fin_par.x,pos_fin_par.y));
   }

   //-------------------------------------------------------------------------
   // Constructor
   //   Inicializamos el malo correspondiente a la bala
   Enemigo(Enemigo enemigo_par)
   { posicion=new Coordenadas(enemigo_par.posicion);
     direccion=enemigo_par.direccion;
     tipo=enemigo_par.tipo;
     vidas=enemigo_par.vidas;
     posicion_bala=new Coordenadas_Double(enemigo_par.posicion_bala.x,enemigo_par.posicion_bala.y);
          
     // Calculamos el angulo de desplazamiento de la bala
     angulo_bala=enemigo_par.angulo_bala;
   }


   //-------------------------------------------------------------------------
   // Procedimiento determina_Imagen_Enemigo()
   //   Devolvera la imagen del Enemigo que se debe pintar
   Image determina_Imagen_Enemigo()
   { 
     switch (tipo) {
         case  ESTRELLA: return (imagen_estrella);
         case  JARJAR:   return (imagen_jarjar);
         case  PIKACHU:  return (imagen_pikachu);
         case  PRIN:     return (imagen_prin);
         case  PELUSA:   return (imagen_pelusa);
         case  XBOX:     return (imagen_xbox);
         case  BANDERA:     return (imagen_bandera);
         case  MENEO:    return (imagen_meneo);

         case  BALA:     
         				if(Math.random()<0.5f)
         				return (imagen_bala_1);         				
         				else
         				return (imagen_bala);
         //case  BALA_1:     return (imagen_bala_1);
         case  MONSTRUO_FINAL: return (imagen_monstruo_final);

         default:  return (imagen_monstruo); 
     }
   }

   //-------------------------------------------------------------------------
   // Procedimiento velocidad_Malo
   //   Procedimiento que determina la velocidad del malo segun su tipo
   int velocidad_Malo(int tipo_par)
   { switch (tipo_par) {
         case  ESTRELLA: return (50);
         case  JARJAR:   return (50);
         case  PIKACHU:  return (50);
         case  PRIN:     return (50);
         case  PELUSA:   return (50);
         case  XBOX:     return (50);
         case  BANDERA:     return (50);
         case  MENEO:    return (50);
         default:         return (60); 
     }
   }   

   //----------------------------------------------------------------------------- 
   // Funcio mover_Direccion
   //  Retornara la nueva posicion segun la direccion elegida
   Coordenadas mover_Direccion(Coordenadas posicion_par,int direccion_par)
   { 
     // Segun el tipo de monstruo
     switch (tipo) {
        case  ESTRELLA: return (mover_Monstruo1(posicion_par));
        case  JARJAR:   return (mover_Monstruo2(posicion_par));
        case  PIKACHU:  return (mover_Monstruo3(posicion_par));
        case  PRIN:     return (mover_Monstruo4(posicion_par));
        case  PELUSA:   return (mover_Monstruo5(posicion_par));
        case  XBOX:     return (mover_Monstruo6(posicion_par));
        case  BANDERA:     return (mover_Monstruo7(posicion_par));
        case  MENEO:    return (mover_Monstruo8(posicion_par));
        case  BALA:     return (mover_Monstruo_Bala(posicion_par));
        case  MONSTRUO_FINAL: return (mover_Monstruo_Final(posicion_par));
        default:  return(mover_Monstruo2(posicion_par)); 
     }
  }

   //----------------------------------------------------------------------------- 
   // Procedimiento mover_Monstruo1: Se desplazara en linea recta (ESTRELLA)
   Coordenadas mover_Monstruo1(Coordenadas posicion_par) 
   { Coordenadas coord_sort=new Coordenadas(posicion_par);

     // Se desplazara sobre la horizontal
     coord_sort.x=posicion_par.x-DESPLAZAMIENTO_MALO;

     return (coord_sort);
   }

   //----------------------------------------------------------------------------- 
   // Procedimiento mover_Monstruo2: (JARJAR)
   Coordenadas mover_Monstruo2(Coordenadas posicion_par) 
   { Coordenadas coord_sort=new Coordenadas(posicion_par);
 
     // Se desplazara sobre la horizontal
     coord_sort.x=posicion_par.x-DESPLAZAMIENTO_MALO;

     // Desplazamos sobre la vertical
     int nueva_y=posicion_par.y+(sentido*(DESPLAZAMIENTO_MALO));

     // Comprobamos margenes inferior
     if (nueva_y<0)
     {
        sentido=1;
        return (coord_sort); 
     }

     // Comprobamos margenes superior
     if (nueva_y>tabla.ALTO_TABLERO)
     {
       sentido=-1;
       return (coord_sort); 
     }

     // Avanzamos en las y
     coord_sort.y=nueva_y;

     return (coord_sort);
   }


   //----------------------------------------------------------------------------- 
   // Procedimiento mover_Monstruo3: Se desplazara en una sinusoidal (PIKACHU)
   Coordenadas mover_Monstruo3(Coordenadas posicion_par) 
   { Coordenadas coord_sort=new Coordenadas(posicion_par);
 
     // Se desplazara sobre la horizontal
     coord_sort.x=posicion_par.x-DESPLAZAMIENTO_MALO;

     // Desplazamos sobre la vertical
     int desplaza_y=(int)(16*Math.sin(posicion_par.x/10));

     // Desplazamos sinusoidalmente en las y
     coord_sort.y+=desplaza_y;

     return (coord_sort);
   }

   //----------------------------------------------------------------------------- 
   // Procedimiento mover_Monstruo4: (PRIN)
   Coordenadas mover_Monstruo4(Coordenadas posicion_par) 
   { Coordenadas coord_sort=new Coordenadas(posicion_par);

     // Se desplazara sobre la horizontal
     coord_sort.x=posicion_par.x-DESPLAZAMIENTO_MALO+(int)(DESPLAZAMIENTO_MALO*Math.sin(posicion_par.x/10));

     // Desplazamos sobre la vertical
     int nueva_y=posicion_par.y+(sentido*(2*DESPLAZAMIENTO_MALO));

     // Comprobamos margenes inferior
     if (nueva_y<0)
     {
        sentido=1;
        return (coord_sort); 
     }

     // Comprobamos margenes superior
     if (nueva_y>tabla.ALTO_TABLERO)
     {
       sentido=-1;
       return (coord_sort); 
     }

     // Avanzamos en las y
     coord_sort.y=nueva_y;

     return (coord_sort);
   }

   //----------------------------------------------------------------------------- 
   // Procedimiento mover_Monstruo5: (PELUSA)
   Coordenadas mover_Monstruo5(Coordenadas posicion_par) 
   { Coordenadas coord_sort=new Coordenadas(posicion_par);

     // Se desplazara sobre la horizontal
     coord_sort.x=posicion_par.x-DESPLAZAMIENTO_MALO+((int)(DESPLAZAMIENTO_MALO*Math.sin(posicion_par.x/10)));

     // Desplazamos sobre la vertical
     coord_sort.y=posicion_par.y+(int)(5*Math.sin(posicion_par.x/20));

     // Comprobamos margenes inferior
     if (coord_sort.y<0)
     {
        coord_sort.y=tabla.ALTO_TABLERO;
        return (coord_sort); 
     }

     // Comprobamos margenes superior
     if (coord_sort.y>tabla.ALTO_TABLERO)
     {
       coord_sort.y=0;
       return (coord_sort); 
     }

     return (coord_sort);
   }


   //----------------------------------------------------------------------------- 
   // Procedimiento mover_Monstruo6: (XBOX)
   Coordenadas mover_Monstruo6(Coordenadas posicion_par) 
   { Coordenadas coord_sort=new Coordenadas(posicion_par);

     // Incrementamos la velocidad con la variable "sentido"
     incremento_velocidad+=0.2;

     // Se desplazara sobre la horizontal
     coord_sort.x=posicion_par.x-DESPLAZAMIENTO_MALO-(int)incremento_velocidad;

     return (coord_sort);
   }

   //----------------------------------------------------------------------------- 
   // Procedimiento mover_Monstruo7: (CULO)
   Coordenadas mover_Monstruo7(Coordenadas posicion_par) 
   { Coordenadas coord_sort=new Coordenadas(posicion_par);

     // Se desplazara sobre la horizontal
     coord_sort.x=posicion_par.x-DESPLAZAMIENTO_MALO;

     return (coord_sort);
   }

   //----------------------------------------------------------------------------- 
   // Procedimiento mover_Monstruo8: (MENEO)
   Coordenadas mover_Monstruo8(Coordenadas posicion_par) 
   { Coordenadas coord_sort=new Coordenadas(posicion_par);

     // Se desplazara sobre la horizontal
     coord_sort.x=posicion_par.x-DESPLAZAMIENTO_MALO;

     return (coord_sort);
   }


   //----------------------------------------------------------------------------- 
   // Procedimiento mover_Monstruo_Bala
   Coordenadas mover_Monstruo_Bala(Coordenadas posicion_par) 
   { Coordenadas coord_sort=new Coordenadas(posicion_par);

     posicion_bala.x+=(DESPLAZAMIENTO_MALO+1.75)*Math.cos(angulo_bala);
     posicion_bala.y+=(DESPLAZAMIENTO_MALO+1.75)*Math.sin(angulo_bala);
    
     coord_sort.x=(int)posicion_bala.x;
     coord_sort.y=(int)posicion_bala.y;

     return (coord_sort);
   }


   //----------------------------------------------------------------------------- 
   // Procedimiento mover_Monstruo_Final
   Coordenadas mover_Monstruo_Final(Coordenadas posicion_par) 
   { Coordenadas coord_sort=new Coordenadas(posicion_par);

     // Desplazamos sobre la vertical
     int nueva_y=posicion_par.y+(sentido*(2*DESPLAZAMIENTO_MALO));

     // Comprobamos margenes inferior
     if (nueva_y<0)
     {
        sentido=1;
        return (coord_sort); 
     }

     // Comprobamos margenes superior
     if (nueva_y>tabla.ALTO_TABLERO)
     {
       sentido=-1;
       return (coord_sort); 
     }

     // Avanzamos en las y
     coord_sort.y=nueva_y;

     return (coord_sort);
   }


   //-------------------------------------------------------------------------
   // Procedimiento mover
   //   El personaje malo se movera (IZQUIERDA, DERECHA) y solo cambiara de direccion cuando
   // encuentre un precipicio
   void mover()
   {
     Coordenadas nueva_posicion=mover_Direccion(posicion,direccion);
     
     // Actualizamos la posicion del personaje
     posicion.copia(nueva_posicion);

   } // Fi void mover


 }  // Fi clase Enemigo
 //****************************************************************************


































 //*******************************************************************************************
 //*******************************************************************************************
 // Clase Gestor_Enemigos
 //   Esta clase gestionara una lista de enemigos. Cada enemigo ofrecera su funcion de 
 // desplazamiento para realizar su desplazamiento. Tambien se controlara cuando el 
 // enemigo salga de la pantalla.
 //*******************************************************************************************
 //*******************************************************************************************
 class Gestor_Enemigos implements Runnable {

    Vector lista_enemigos=new Vector();  // En esta lista se encuentran los enemigos activos
    Vector lista_introducir_enemigos=new Vector(); // Lista de enemigos que se introduciran en la siguiente generacion
    Vector lista_eliminar_enemigos=new Vector();   // Lista de enemigos que se eliminara en la siguiente generacion

    boolean salir_gestor_enemigos=false;   // Determina el flujo del gestor de enemigos

    // Constates de enemigo
    public static final int RADIO_ENEMIGO = 12;       // Definicion del tamanyo del enemigo
    public static final int VELOCIDAD_ENEMIGOS = 40;//(int)(Tablero.VELOCIDAD_DESPLAZAMIENTO_CENTRO*1.5);//40;  // Definicion de la velocidad de los enemigos

    Thread hilo;  // Hilo de ejecucion

    //---------------------------------------------------------------------------------------
    // Constructor: Inicializaremos el gestor de enemigos
    Gestor_Enemigos()  
    {
     // Arrancamos el hilo de ejecucion
     hilo=new Thread(this);
     hilo.start();
    }

    //---------------------------------------------------------------------------------------
    // Procedimiento lanzar_Enemigos
    //  Introduciremos un nuevo tipo de enemigo
    void lanzar_Enemigo(Coordenadas pos_par, int tipo_par)
    {
      // Lo introducimos en la lista para introducir enemigos
      lista_introducir_enemigos.addElement(new Enemigo(pos_par,tipo_par));
    }

    //---------------------------------------------------------------------------------------
    // Procedimiento lanzar_Enemigos
    //  Introduciremos un nuevo tipo de enemigo BALA
    void lanzar_Enemigo(Coordenadas pos_bala_par, Coordenadas pos_prota_par)
    {
      // Lo introducimos en la lista para introducir enemigos
      lista_introducir_enemigos.addElement(new Enemigo(pos_bala_par,pos_prota_par));
    }


    //---------------------------------------------------------------------------------------
    // Procedimiento introducir_Enemigos
    //   Este procedimiento introducir los enemigos encontrados en la lista a introducir
    void introducir_Enemigos()
    {
     try 
     {
      // Recorremos la lista de eliminar
      Enumeration e=lista_introducir_enemigos.elements();
      while (e.hasMoreElements())
      { Enemigo snuevo_enemigo=(Enemigo)e.nextElement();

        // Introducimos el enemigo de la lista
        if (snuevo_enemigo.tipo!=Enemigo.BALA)
        {
         lista_enemigos.addElement(new Enemigo(snuevo_enemigo.posicion,snuevo_enemigo.tipo)); 
        }
        else 
        { 
         lista_enemigos.addElement(new Enemigo(snuevo_enemigo));  
        }
      }
     } catch (Exception est)  {};

     // Vaciamos la lista de los objetos a introducir
     lista_introducir_enemigos.removeAllElements();
    }

    //---------------------------------------------------------------------------------------
    // Procedimiento eliminar_Enemigos
    //   Este procedimiento eleiminara los enemigos encontrados en la lista a eliminar
    void eliminar_Enemigos()
    {
     try 
     {
      // Recorremos la lista de eliminar
      Enumeration e=lista_eliminar_enemigos.elements();
      while (e.hasMoreElements())
      { int ieli=((Integer)e.nextElement()).intValue();
         
        // Eliminamos el enemigo de la lista
        lista_enemigos.removeElementAt(ieli);
      }
     } catch (Exception est)  {};

     // Vaciamos la lista de los objetos a eliminar
     lista_eliminar_enemigos.removeAllElements();
    }


    //---------------------------------------------------------------------------------------
    // Procedimiento capturar_Enemigo
    //   Esta funcion capturara al enemigo mas cercano a la posicion por parametro y que 
    // sea inferior al tamanyo del enemigo
    int capturar_Enemigo(Coordenadas pos_par)
    { int contador_enemigo=0;

      // Controlamos la lista vacia
      if (lista_enemigos.size()==0) return (-1);

      // Recorremos la lista de enemigos
      Enumeration e=lista_enemigos.elements();
      while (e.hasMoreElements())
      { Enemigo sene=(Enemigo)e.nextElement();
         
        // Comprobamos si esta lo sificientemente cerca
        if (Math.abs(sene.posicion.x-pos_par.x)<RADIO_ENEMIGO)
          if (Math.abs(sene.posicion.y-pos_par.y)<RADIO_ENEMIGO)
          { 
            // Si no tiene mas vidas eliminamos el enemigo
            if (sene.vidas<=0)
            {            
              // Comprobamos si hemos eliminado al monstruo final
              try 
              { if (sene.tipo==Enemigo.MONSTRUO_FINAL)
                  mostrar_Foto_Final_Partida(true,"Bien Hecho �GANASTE! tus Puntos="+protagonista.puntos); 
               } catch (Exception egg) {};
             return (contador_enemigo);  
            }
            else 
            {
              sene.vidas--;
              //if(sene.tipo==sene.MONSTRUO_FINAL)
              {
              	vida_enemigo_final=sene.vidas;
              	label_vida_enemigo_final.setText("Enemigo = "+vida_enemigo_final);
              }
              return (-2); 
            }
          }
 
       // Incrementamos el indice del enemigo
       contador_enemigo++;
      }
   
      return (-1);
    } // Fi void capturar_Enemigo

   
    //---------------------------------------------------------------------------------------
    // Procedimiento mover_Enemigo
    //  Este procedimiento movera el i-esimo enemigo segun su funcion de desplazamiento
    // y comprobara si la posicion de este a salido de la pantalla para eliminarlo
    void mover_Enemigo(int indice_enemigo_par) 
    {
     // Capturamos el enemigo seleccionado
     Enemigo selene=(Enemigo)lista_enemigos.elementAt(indice_enemigo_par);
   
     // Ejecutamos la funcion de desplazamiento del enemigo
     selene.mover();

     // Comprobamos si se trata de del enemigo final para dispara contra el protagonista
     switch (selene.tipo){
          case Enemigo.MONSTRUO_FINAL:
           if (aleatorio(0,20)<1)
              lanzar_Enemigo(protagonista.posicion ,selene.posicion);
           break;
         case Enemigo.ESTRELLA:
          if (aleatorio(0,80)<1)
             lanzar_Enemigo(protagonista.posicion ,selene.posicion);
           break;
         case Enemigo.BANDERA:
          if (aleatorio(0,70)<1)
             lanzar_Enemigo(protagonista.posicion ,selene.posicion);
           break;
         case Enemigo.MENEO:
          if (aleatorio(0,70)<1)
             lanzar_Enemigo(protagonista.posicion ,selene.posicion);
           break;
     }
 
     // Comprobamos la posicion del enemigo para determinar si lo eliminamos
     int mitad_ancho=(int)(tabla.ANCHO_TABLERO*2/3);
     if (selene.posicion.x<tabla.posicion_centro.x-mitad_ancho)
       lista_eliminar_enemigos.addElement(new Integer(indice_enemigo_par));
    }


    //---------------------------------------------------------------------------------------
    // Procedimiento run
    //   Ejecutara periodicamente eliminar,mover la lista de enemigos
    public void run()
    {
      // Mientras el flujo de gestor de enemigos este vivo
      while (salir_gestor_enemigos==false)
      {
       try 
       {
          // Dormimos el intervalo
          try { hilo.sleep(VELOCIDAD_ENEMIGOS); } catch (InterruptedException est) {};

          // Introducimos los enemigos
          if (lista_introducir_enemigos.size()>0) introducir_Enemigos();

          // Eliminamos los enemigos
          if (lista_eliminar_enemigos.size()>0) eliminar_Enemigos();

          // Movemos los enemigos
          for (int i=0;i<lista_enemigos.size();i++)
            mover_Enemigo(i);

        } catch (Exception egg) {};
       }// Fi while

     } // Fi void run
     
 } // Fi clase Gestor_Enemigos
     
    
































































 //*******************************************************************************************
 //*******************************************************************************************
 // Clase Gestor_Balas
 //   Esta clase se encargara de gestionar/managejar el movimiento de las balas
 // Controlaremos: 
 //   - El m�ximo numero de balas permitidas
 //   - La distancia entre dos balas sea de un minimo
 //   - La desaparicion de las balas (colision con enemigo o pared, o salir del tablero)
 //*******************************************************************************************
 //*******************************************************************************************
 class Gestor_Balas implements Runnable {

    Vector lista_balas=new Vector();  // Array donde se guardaran las balas
    int numero_balas_vivas=0;         // Numero de balas vivas
    int posicion_x_minima_bala=ANCHO_CAMPO;     // Posicion x de la bala mas cercana (la mas recientemente creada)
    boolean salir_gestor_balas=false;    // Determina el flujo de vida del gestor de balas
 
    Vector lista_introducir_balas=new Vector();  // Lista de las balas que debemos introducir
    Vector lista_eliminar_balas=new Vector();    // Lista de las balas que debemos eliminar

    Jugador jugador_local=null;                  // Referencia al jugador

    // Constante que define el ancho de la bala
    public static final int ANCHO_BALA     = 4+0+0;
    public static final int ALTO_BALA      = 2+0+0;

    // Constante que define la distancia de desplazamiento de la bala
    public static final int DISTANCIA_DESPLAZAMIENTO_BALA = 2;

    // Constante que define la velocidad de desplazamiento de las balas
    public static final int VELOCIDAD_BALAS = 4;//(int)(Tablero.VELOCIDAD_DESPLAZAMIENTO_CENTRO/6);//4;
 

    // Hilo de ejecucion
    Thread hilo;

    //------------------------------------------------------------------------------
    // Constructor: Inicializamos el gestor de las balas
    Gestor_Balas(Jugador jugador_par)
    {  numero_balas_vivas=0;
       posicion_x_minima_bala=ANCHO_CAMPO;
   
       // Referenciamos el jugador
       jugador_local=jugador_par;

       // Arrancamos el proceso
       hilo=new Thread(this);
       hilo.start();
    }

    //------------------------------------------------------------------------------
    // Procedimiento disparar_Bala
    //   Este procedimiento disparara una nueva bala si se cumplen las condiciones:
    //     -El numero de balas no ha llegado al maximo
    //     -La distancia con las otra balas a de ser superior a una constante
    void disparar_Bala(Coordenadas pos_par)
    {
      // Comprobamos el numero de balas
      if (numero_balas_vivas>=10) return;
     
      // Comprobamos que se cumpla la distancia minima
      if (pos_par.x+(4*ANCHO_BALA)>posicion_x_minima_bala) return;

      // Lanzamos una nueva bala en la lista de balas a introducir
      lista_introducir_balas.addElement(new Coordenadas(pos_par));
     }


    //------------------------------------------------------------------------------
    // Procedimiento introducir_Lista_Balas
    //   Insertamos la lista de balas de la lista transitoria a la lista final y vaciaremos
    // dicha lista
    void introducir_Lista_Balas()
    {
     try 
     {
      // Por todas las balas a introducir
      Enumeration e=lista_introducir_balas.elements();
      while (e.hasMoreElements())
      {  
        Coordenadas snuevabala=(Coordenadas)e.nextElement();
         
        // Insertamos en la lista de balas
        lista_balas.addElement(new Coordenadas(snuevabala));

        // Establecemos la nueva poscion x minima
        posicion_x_minima_bala=snuevabala.x;

        numero_balas_vivas++;
      }
     } catch (Exception est) {};
   
      // Eliminamos el contenido
      lista_introducir_balas.removeAllElements(); 
    }


    //------------------------------------------------------------------------------
    // Procedimiento eliminar_Lista_Balas
    //   Eliminamos de la lista de balas las balas seleccionadas
    void eliminar_Lista_Balas()
    {
     try 
     {
      // Por todas las balas a introducir
      Enumeration e=lista_eliminar_balas.elements();
      while (e.hasMoreElements())
      {  
        int sbalaeli=((Integer)e.nextElement()).intValue();
         
        // Eliminamos la bala situada en la posicion
        lista_balas.removeElementAt(sbalaeli);

        numero_balas_vivas--;
       }
      } catch (Exception est) {};

      // Eliminamos el contenido
      lista_eliminar_balas.removeAllElements(); 
    }

             
    //------------------------------------------------------------------------------
    // Procedimiento mover_Bala
    //   Este procedimiento se encargara de realizar el desplazamiento de la bala
    // considerando:
    //        -Salir del tablero (desaparece)
    //        -Colision con la pared (desaparece)
    //        -Colision con un enemigo (enemigo y la bala desparecen
    //  Devolvemos la nueva posicion x de la bala
    int mover_Bala(int indice_bala_par) 
    {
      // Capturamos la bala seleccionada
      Coordenadas sbala=(Coordenadas)lista_balas.elementAt(indice_bala_par);
        
      // Despalazamos la bala en el eje
      Coordenadas nueva_bala=new Coordenadas(sbala.x+DISTANCIA_DESPLAZAMIENTO_BALA,sbala.y);
         
      // Comprobamos si salimos del tablero
      int mitad_ancho=(int)(tabla.ANCHO_TABLERO/2);
      if (nueva_bala.x>tabla.posicion_centro.x+mitad_ancho)
      {
       lista_eliminar_balas.addElement(new Integer(indice_bala_par)); // Eliminamos la bala
       return (ANCHO_CAMPO);
      }
  
     // Comprobamos si nos chocamos con alguna pared, entonces ....
     if (tabla.esta_Ocupado(nueva_bala))
     {
      lista_eliminar_balas.addElement(new Integer(indice_bala_par)); // Eliminamos la bala
      return (ANCHO_CAMPO);
     }
 
     // Comprobamos si chocamos con algun enemigo, entonces ....
     int enemigo_interseccion=gestor_enemigos.capturar_Enemigo(nueva_bala);
     if (enemigo_interseccion!=-1)
     {
      lista_eliminar_balas.addElement(new Integer(indice_bala_par)); // Eliminamos la bala
   
      // Eliminamos al enemigo si no le quedan mas vidas
      if (enemigo_interseccion!=-2)
      {
        gestor_enemigos.lista_eliminar_enemigos.addElement(new Integer(enemigo_interseccion));
        jugador_local.puntos++;
        
        // Si el jugador es protagonista refrescamos el marcador
        if (jugador_local.es_protagonista)
        { 
         label_Puntuacion.setText("Puntos="+jugador_local.puntos);
         label_Puntuacion.repaint();
        }
      }

      return (ANCHO_CAMPO);
     }
      
     // Si todo es correcto entonces completamos el movimiento
     sbala.copia(nueva_bala);       

     return (sbala.x);

   } // Fi int mover_Bala
 

   //------------------------------------------------------------------------------
   // Procedimiento run
   //   Este procedimiento ejecutara:
   //    - Eliminacion de las balas
   //    - Insercion de las nuevas balas
   //    - Movimiento de las nuevas balas 
   public void run()
   {
    // Mientras el proceso siga vivo
    while (salir_gestor_balas==false) 
    {
     try 
     {
        // Dormimos el intervalo del disparo
        try { hilo.sleep(VELOCIDAD_BALAS);  } catch (InterruptedException est) {};

        // Eliminamos balas
        if (lista_eliminar_balas.size()>0) eliminar_Lista_Balas();

        // Introducimos balas
        if (lista_introducir_balas.size()>0) introducir_Lista_Balas();
 
        // Desplazamos la balas
        int minimo_x_tmp=ANCHO_CAMPO;
        for (int i=0;i<lista_balas.size();i++)
        { // Movemos la bala y obtenemos su x
          int tmp_x_bal=mover_Bala(i);
          if (tmp_x_bal<minimo_x_tmp) minimo_x_tmp=tmp_x_bal; 
        }
        // Establecemos el nuevo minimo
        posicion_x_minima_bala=minimo_x_tmp;    
      } 
      catch (Exception egg) {};

    } // Fi while

   } // Fi void run



} // Fi Gestor_Balas
































 //****************************************************************************
 //****************************************************************************
 // Clase Jugador
 //   Esta clase representara a un jugador del juego. Su funcion principal es 
 // representar la nave. La nave se podra desplazarse por el mapeado (con ciertos limites
 // respecto a la posicion central) y disparar (o no) balas.
 //****************************************************************************
 //****************************************************************************
 class Jugador implements Runnable {
   String nombre;               // Nombre del personaje
   int estado;                  // Estado del personaje (NORMAL, EXPLOSION, INMUNE)
   int estado_disparo;          // Indica si el jugador dispara o no (MODO_PAZ,MODO_DISPARO)
   int direccion;               // Direccion de desplazamiento del personaje (8 direcciones + NINGUNA=posicion_centro+CTE)
   Coordenadas posicion;        // Posicion del personaje
   int puntos;                  // Puntos del personajes
   int vidas;                   // Vidas del personaje
   boolean es_protagonista;     // Variable que informara si el personaje es el protagonista
   int unidades_explosion=0;    // Determina las unidades que estara explotando
   int unidades_inmune=0;       // Determina las unidades que estara inmune

   double DESPLAZAMIENTO_NINGUNO=-1;  // Desplazamiento en direccion NINGUNA

   Gestor_Balas gestor_balas;   // Es es gestor de disparos del jugador

   boolean salir_jugador=false; // Control de ciclo del personaje

   Thread hilo;                 // Hilo de ejecucion del personaje

   // Constantes de estado del jugador
   public static final int NORMAL    = 0;
   public static final int EXPLOSION = 1;
   public static final int INMUNE    = 2;

   // Constantes de estado del disparo
   public static final int MODO_PAZ     = 0;
   public static final int MODO_DISPARO = 1;

   // Constantes del jugador
   public static final int DISTANCIA_DESPLAZAMIENTO_JUGADOR   = 5;
   public static final int VELOCIDAD_JUGADOR                  = 30;//(int)(Tablero.VELOCIDAD_DESPLAZAMIENTO_CENTRO/2);//30;

   //-------------------------------------------------------------------------
   // Constructor: Inicializamos los miembros del jugador
   Jugador(String nombre_par, boolean es_protagonista_par, Coordenadas posicion_par) 
   { nombre=new String(nombre_par);
     posicion=new Coordenadas(posicion_par);
     estado=NORMAL;
     estado_disparo=MODO_PAZ;
     direccion=NINGUNA;
     es_protagonista=es_protagonista_par;
     unidades_explosion=0;
     unidades_inmune=0;
     puntos=0;
     vidas=VIDA_PROTAGONISTA;

     // Creamos el gestor de balas
     gestor_balas=new Gestor_Balas(this);

     // Establecemos el desplazamiento ninguno
     DESPLAZAMIENTO_NINGUNO=(double)((double)(Tablero.DISTANCIA_DESPLAZAMIENTO_CENTRO)*((double)VELOCIDAD_JUGADOR/(double)Tablero.VELOCIDAD_DESPLAZAMIENTO_CENTRO));
     System.out.println("Desplazamiento NINGUNO="+DESPLAZAMIENTO_NINGUNO);

     // Arrancamos el hilo de ejecucion
     hilo=new Thread(this);
     hilo.start();
   }


   //-------------------------------------------------------------------------
   // Procedimiento determina_Imagen_Jugador()
   //   Devolvera la imagen del Jugador que se debe pintar
   Image determina_Imagen_Jugador()
   {
     // Determinamos si corremos o saltamos
     switch (estado) 
     { 
       case NORMAL:    return (imagen_nave);
       case EXPLOSION: return (imagen_explosion);
       case INMUNE:    return (imagen_nave_inmune);
     }
     return (null);
   } // Fi determina_Imagen_Jugador


   //-------------------------------------------------------------------------
   // Procedimiento actualizar_Personaje(String buf)
   //  Actualizamos los datos del personaje
   void actualizar_Personaje(String buf)
   {  // Comprobamos que no se trate del protagonista
      if (es_protagonista) return;
   }

   //-------------------------------------------------------------------------
   // Procedimiento cambiar_Direccion
   //  Determinara si existe un cambio de direccion para comunicarlo al resto de los jugadores
   void cambiar_Direccion(int direccion_par)
   {  
     // Comprobamos si hemos cambiado de direccion
     if (direccion_par!=direccion)
     {  
       direccion=direccion_par;   // Actualizamos la direccion        
     } 
    }


   //-------------------------------------------------------------------------
   // Procedimiento cambiar_Disparo
   //  Determinara si existe un cambio en el modo de disparo para comunicarlo al restor de los jugadores
   void cambiar_Disparo(int estado_disparo_par)
   {  
     // Comprobamos si hemos cambiado de estado del disparo
     if (estado_disparo!=estado_disparo_par)
     {  
       estado_disparo=estado_disparo_par;   // Actualizamos el estado del disparo
     }
   }


   //----------------------------------------------------------------------------- 
   // Funcio mover_Direccion
   //  Retornara la nueva posicion segun la direccion elegida
   Coordenadas mover_Direccion(Coordenadas posicion_par,int direccion_par, int desplazamiento_par)
   { Coordenadas coord_sort=new Coordenadas(posicion_par);
       
     // Segun la direccion elegida
     switch (direccion_par) { 
         case ABAJO:     coord_sort.y=posicion_par.y-desplazamiento_par; 
                         coord_sort.x=posicion_par.x+(int)(Math.rint(DESPLAZAMIENTO_NINGUNO)+1); 
                         break;
         case ARRIBA:    coord_sort.y=posicion_par.y+desplazamiento_par; 
                         coord_sort.x=posicion_par.x+(int)(Math.rint(DESPLAZAMIENTO_NINGUNO)+1); 
                         break;
         case IZQUIERDA: coord_sort.x=posicion_par.x-desplazamiento_par+(int)(Math.rint(DESPLAZAMIENTO_NINGUNO)+1); 
                         break;
         case DERECHA:   coord_sort.x=posicion_par.x+desplazamiento_par+(int)(Math.rint(DESPLAZAMIENTO_NINGUNO)+1);
                         break;
         case ABAJO_IZQUIERDA:
                         coord_sort.y=posicion_par.y-desplazamiento_par; 
                         coord_sort.x=posicion_par.x-desplazamiento_par+(int)(Math.rint(DESPLAZAMIENTO_NINGUNO)+1);
                         break;
         case ABAJO_DERECHA:
                         coord_sort.y=posicion_par.y-desplazamiento_par; 
                         coord_sort.x=posicion_par.x+desplazamiento_par+(int)(Math.rint(DESPLAZAMIENTO_NINGUNO)+1);
                         break;
         case ARRIBA_IZQUIERDA:
                         coord_sort.y=posicion_par.y+desplazamiento_par; 
                         coord_sort.x=posicion_par.x-desplazamiento_par+(int)(Math.rint(DESPLAZAMIENTO_NINGUNO)+1);
                         break;
         case ARRIBA_DERECHA:
                         coord_sort.y=posicion_par.y+desplazamiento_par;
                         coord_sort.x=posicion_par.x+desplazamiento_par+(int)(Math.rint(DESPLAZAMIENTO_NINGUNO)+1);
                         break;

         case NINGUNA:   coord_sort.x=posicion_par.x+(int)(Math.rint(DESPLAZAMIENTO_NINGUNO)+1); 
                         break;
      }
     return (coord_sort);
   }



   //-------------------------------------------------------------------------
   // Procedimiento mover()
   //  Ejecutara un movimiento del jugador(si es posible) y calculara las consecuencias
   public void mover()
   { Coordenadas nueva_posicion=null;
  
     // Comprobamos si hemos llegado al final de la pantalla
     if (tabla.enemigo_final_lanzado) DESPLAZAMIENTO_NINGUNO=-1;

     // Nos desplazamos segun la direccion
     nueva_posicion=mover_Direccion(posicion,direccion,DISTANCIA_DESPLAZAMIENTO_JUGADOR);

     // Comprobamos que la frontera horizontal inferior
     int mitad_ancho=(int)(tabla.ANCHO_TABLERO/2);
     if (nueva_posicion.x<tabla.posicion_centro.x-mitad_ancho)
     {
       posicion.x=tabla.posicion_centro.x-mitad_ancho;
       return; 
     }

     // Comprobamos que la frontera horizontal superior
     if (nueva_posicion.x>tabla.posicion_centro.x+mitad_ancho)
        return;
  
     // Comprobamos los margenes verticales
     if ((nueva_posicion.y<5)||(nueva_posicion.y>(tabla.ALTO_TABLERO-5)))
        return;
     
     // Comprobamos si nos chocamos con alguna pared, entonces ....
     if ((estado==NORMAL)&&(tabla.esta_Ocupado(nueva_posicion)))
     { // ... explotamos
       estado=EXPLOSION;
       unidades_explosion=50;
       vidas--;

       // Si el jugador es protagonista refrescamos las vidas
       if (es_protagonista)
       { 
        label_Vidas.setText("Vidas="+vidas);
        label_Vidas.repaint();
       }

       // Comprobamos si hemos consumido todas las vidas
       if (vidas<=0)
          mostrar_Foto_Final_Partida(false,"GAME OVER");

       return;
     }
 
     // Comprobamos si chocamos con algun enemigo, entonces ....
     int enemigo_interseccion=gestor_enemigos.capturar_Enemigo(nueva_posicion);
     if ((estado==NORMAL)&&(enemigo_interseccion!=-1))
     {  // ... volvemos a explotar
        estado=EXPLOSION;
        unidades_explosion=50;
        vidas--;

        // Si el jugador es protagonista refrescamos las vidas
        if (es_protagonista)
        { 
         label_Vidas.setText("Vidas="+vidas);
         label_Vidas.repaint();
        }

        // Comprobamos si hemos consumido todas las vidas
        if (vidas<=0)
           mostrar_Foto_Final_Partida(false,"GAME OVER");

        return;
     }
      
     // Si todo es correcto entonces completamos el movimiento
     posicion.copia(nueva_posicion);       

   } // Fi void mover()


   //-------------------------------------------------------------------------
   // Procedimiento run
   //   Este procedimiento ejecutara segun la velocidad del jugador el movimiento
   // correspondiente, solo si esta activo
   public void run() 
   {
    // Ejecutaremos ciclicamente el movimiento si el personaje esta activo
    while (salir_jugador==false) 
    {  
     try 
     {
      // Dormimos para establecer la velocidad del personaje
       try { hilo.sleep(VELOCIDAD_JUGADOR); } catch (InterruptedException e) {};

       // Miramos si estamos disparando
       if (estado_disparo==MODO_DISPARO) gestor_balas.disparar_Bala(posicion);
       
       // Segun el estado del jugador
       switch (estado) {
          case NORMAL:    break;
          case EXPLOSION: if (unidades_explosion<0) 
                          { unidades_inmune=100;
                            estado=INMUNE; 
                          }
                          else
                          {
                            unidades_explosion--; 
                          }
                          break;
          case INMUNE: if (unidades_inmune<0)
                           estado=NORMAL;
                       else
                       {
                          unidades_inmune--;
                         
                       }
                       break; 
         } // Fi switch 

       // Ejecutamos el desplazamiento
       mover();          

       // Si esta activado realizamos el movimiento
       if (es_protagonista)
       { 
         pantalla.repaint();
         repaint();
         requestFocus();
       }
     } catch (Exception egg) { egg.printStackTrace(); };
    } // Fi while 
   } // Fi void run()


 } // Fi clase Jugador
 //****************************************************************************






 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************
 //*****************************************************************************************

  String nombre_jugador_applet;                   // Es el nombre del jugador
  Tablero tabla=null;                             // Representa la tabla del juego
  Jugador protagonista=null;                      // Representa al protagonista del juego
  Pantalla pantalla=null;                         // Representa la pantalla del juego    
  Reloj_Nemexis reloj_nemexis=null;               // Representa el reloj del juego
  Label label_Puntuacion=null;                    // Representa la puntuacion del personaje
  Label label_Vidas=null;                         // Esta etiqueta muestra las vidas restantes del personaje  
  boolean es_partida_finalizada=false;            // Nos informa de si la partida ha finalizado
  String nombre_Ganador_Partida="";               // Nos informa de quien ha finalizado la partida

  Gestor_Enemigos gestor_enemigos=null;           // Representa al gestor de enemigos del juego

  // Lista de jugadores de la partida
  Hashtable lista_jugadores=new Hashtable();

  // Lista que contine las teclas pulsadas por el jugador
  Hashtable teclas_pulsadas=new Hashtable();

  Image imagen_nave=null;                    // Imagen del personaje (TEMPORAL)
  Image imagen_explosion = null; 
  Image imagen_nave_inmune = null;
  Image imagen_monstruo=null;             // Imagenes de los malos
  Image imagen_estrella=null;
  Image imagen_jarjar=null;
  Image imagen_pikachu=null;
  Image imagen_prin=null;
  Image imagen_pelusa=null;
  Image imagen_xbox=null;
  Image imagen_bandera=null;
  Image imagen_meneo=null;
  Image imagen_bala=null;
  Image imagen_bala_1=null;
  Image imagen_monstruo_final=null;

  Image imagen_ladrillos=null;                     // Imagen de un ladrillo  

  // Imagenes para ganadores y perdedores
  Image image_ganador=null; 
  Image image_perdedor=null; 
  Image image_fondo=null;

  Random aleatorio_nemexis;                       // Numero utilizado para generacion de aleatorios

  // Constantes del tamanyo del campo del juego
  private static final int ANCHO_CAMPO = 8000;
  private static final int ALTO_CAMPO  = 200;    

  // Constantes del programa
  private static final int AMBITO_ACTIVACION = 1200;  // Define el ambito de activacion del personaje protagonista
  private static final int DISTANCIA_CAPTURA = 20;    // Define la distancia con la cual colisionamos con los elementos  
 
  // Constantes de direccion del personaje
  private static final int IZQUIERDA         = 0;
  private static final int DERECHA           = 1;
  private static final int ARRIBA            = 2;
  private static final int ABAJO             = 3;
  private static final int ARRIBA_IZQUIERDA  = 4;
  private static final int ABAJO_IZQUIERDA   = 5;
  private static final int ARRIBA_DERECHA    = 6;
  private static final int ABAJO_DERECHA     = 7;
  private static final int NINGUNA           = 8;

  // Constantes del teclado
  private static final int TECLA_ARRIBA    = 38;
  private static final int TECLA_ABAJO     = 40;
  private static final int TECLA_RIGHT     = 37;
  private static final int TECLA_LEFT      = 39;

  private static final int TECLA_ESPACIO   = 32;


  //-------------------------------------------------------------------------
  // Procedimiento inicializar_Grupo_Personajes(String lista_nombres_par)
  //   Creamos la lista de los otros personajes humnanos con los que competiremos
  void inicializar_Grupo_Jugadores(String lista_nombres_par)
  { String buf=new String(lista_nombres_par);
    while (!buf.equals(""))
    {  String snom=buf.substring(buf.indexOf(":iNJug:")+7,buf.indexOf(":fNJug:"));
       // if (!snom.equals(protagonista.nombre))
       //    lista_personajes.put(new String(snom),new Personaje(snom,false,new Coordenadas(100,101)));
       buf=buf.substring(buf.indexOf(":fNJug:")+7,buf.length()); 
    }
  }


  //-----------------------------------------------------------------------------------------
  // Funcion aleatorio(int rini,int rfin)
  //  Esta funcion generara aleatoriamente un numero entre el rango seleccionado de int
  int aleatorio(int rini,int rfin)
  { int valor_salida=0;

    // Generem el numero aleatori entre 0 y 1
    double alea_generat=aleatorio_nemexis.nextDouble();

    // Escalem el numero entre el rang escollit
    return ((int)((alea_generat*(rfin-rini))+rini));
  }


   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   // Leemos las imagenes asociadas a la prueba
   void Leer_Imagenes_Local()
   {
     // Seleccion aleatoria de las imagenes
     int igan_img=aleatorio(1,8);
     int iper_img=aleatorio(1,8);

     // Liberamos las imagenes
     try  { 
           image_ganador.flush(); 
           image_perdedor.flush(); 
          } catch (Exception egg) {}; 
      
      // Cargando imagenes desde fichero++++++++++++++++++++++++++++++++++++
         Toolkit new_tool=Toolkit.getDefaultToolkit();
      
         try {
           	String base="./out/production/Paint3D/com/cc/paint3D/Graficador3D/Menus/Juego/";
System.out.println( "Nemexis:cargando image" );
           	          	
             image_fondo = new_tool.getImage(base+"images_nemexis/fondo.jpg");
             
             imagen_nave = new_tool.getImage(base+"images_nemexis/nave_squeleton.gif");//jugador.gif
             
             imagen_explosion = new_tool.getImage(base+"images_nemexis/explosion.gif");
             
             imagen_nave_inmune = new_tool.getImage(base+"images_nemexis/nave_inmune.gif");///jugador_inmune.gif
             
             imagen_monstruo=new_tool.getImage(base+"images_nemexis/malo_monstruo.gif");
             imagen_estrella=new_tool.getImage(base+"images_nemexis/malo_estrella.gif");
             imagen_jarjar=new_tool.getImage(base+"images_nemexis/malo_canyones.gif");
             imagen_pikachu=new_tool.getImage(base+"images_nemexis/malo_pikachu.gif");
             imagen_prin=new_tool.getImage(base+"images_nemexis/malo_prin.gif");
             imagen_pelusa=new_tool.getImage(base+"images_nemexis/malo_pelusa.gif");
             imagen_xbox= new_tool.getImage(base+"images_nemexis/nave_inmune.gif");
             imagen_bandera= new_tool.getImage(base+"images_nemexis/malo_bandera.gif");
             imagen_meneo=new_tool.getImage(base+"images_nemexis/malo_esfera.gif");

             imagen_bala=new_tool.getImage(base+"images_nemexis/malo_bala.gif");
             imagen_bala_1=new_tool.getImage(base+"images_nemexis/malo_bala_1.gif");
             
             imagen_monstruo_final=new_tool.getImage(base+"images_nemexis/malo_monstruo_final.gif");

             imagen_ladrillos= new_tool.getImage(base+"images_nemexis/ladrillos.gif"); //ladrillos.jpg");
             
           
           image_ganador=imagen_nave;            
           image_perdedor=imagen_monstruo_final;
           
           
           }
            catch (Exception e) 
           { System.out.println("Load_Imagenes: Cargando desde fuera de applet"); 
             // e.printStackTrace();
           }

         
           try 
           {
           	String base="./out/production/Paint3D/com/cc/paint3D/Graficador3D/Menus/Juego/";
System.out.println( "Nemexis:cargando image" );           	
                         
             /*image_fondo = new_tool.getImage(base+"images_nemexis/fondo.jpg");
             
             imagen_nave = new_tool.getImage(base+"images_nemexis/nave_squeleton.gif");//jugador.gif
             
             imagen_explosion = new_tool.getImage(base+"images_nemexis/explosion.gif");
             
             imagen_nave_inmune = new_tool.getImage(base+"images_nemexis/explosion.gif");///jugador_inmune.gif
             
             imagen_monstruo=new_tool.getImage(base+"images_nemexis/malo_monstruo.gif");
             imagen_estrella=new_tool.getImage(base+"images_nemexis/malo_estrella.gif");
             imagen_jarjar=new_tool.getImage(base+"images_nemexis/malo_canyones.gif");
             imagen_pikachu=new_tool.getImage(base+"images_nemexis/malo_pikachu.gif");
             imagen_prin=new_tool.getImage(base+"images_nemexis/malo_prin.gif");
             imagen_pelusa=new_tool.getImage(base+"images_nemexis/malo_pelusa.gif");
             imagen_xbox= new_tool.getImage(base+"images_nemexis/malo_xbox.gif");
             imagen_bandera= new_tool.getImage(base+"images_nemexis/malo_bandera.gif");
             imagen_meneo=new_tool.getImage(base+"images_nemexis/malo_esfera.gif");

             imagen_bala=new_tool.getImage(base+"images_nemexis/malo_bala.gif");
             imagen_monstruo_final=new_tool.getImage(base+"images_nemexis/malo_monstruo_final.gif");

             imagen_ladrillos= new_tool.getImage(base+"images_nemexis/ladrillos_no.gif"); //ladrillos.jpg");
             */
           }
            catch (Exception e) 
             { System.out.println("Error al cargar imagenes");  }       
 
        // Esperamos a capturarlas con el mediatracker++++++++++++++++++++++++++
        MediaTracker tracker_atle=new MediaTracker(this);
        tracker_atle.addImage(image_fondo,0);
        tracker_atle.addImage(imagen_nave,1);
        tracker_atle.addImage(imagen_explosion,2);
        tracker_atle.addImage(imagen_nave_inmune,3);
        //tracker_atle.addImage(imagen_ladrillos,6);
        
        
        	 tracker_atle.addImage(imagen_monstruo,6);
             tracker_atle.addImage(imagen_estrella,6);
             tracker_atle.addImage(imagen_jarjar,6);
             tracker_atle.addImage(imagen_pikachu,6);
             tracker_atle.addImage(imagen_prin,6);
             tracker_atle.addImage(imagen_pelusa,6);
             tracker_atle.addImage(imagen_xbox,6);
             tracker_atle.addImage(imagen_bandera,6);
             tracker_atle.addImage(imagen_meneo,6);
             tracker_atle.addImage(imagen_bala,6);
             tracker_atle.addImage(imagen_bala_1,6);
             tracker_atle.addImage(imagen_monstruo_final,6);
             tracker_atle.addImage(imagen_ladrillos,6);
        
        
        
        try { tracker_atle.waitForAll();  } catch (InterruptedException e) {}
    }

  //----------------------------------------------------------------------------
  // Constructor
  public Nemexis()
  { 
    // Inicializamos la funcion aleatorio
    try { 
     Calendar ahora=Calendar.getInstance();
     aleatorio_nemexis=new Random(ahora.getTime().hashCode());             
    } catch (Exception ed) {  ed.printStackTrace();  }

     // Creamos al protagonista
     protagonista=new Jugador("Marciano", true, new Coordenadas(50,100));
     nombre_jugador_applet="Marciano";

     // Introducimos el jugador en la lista de jugadores
     lista_jugadores.put(new String(nombre_jugador_applet),protagonista);
 
     // Creacion del tablero del juego de billar, con la coleccion de bolas
     tabla=new Tablero();
    
     // Creamos el gestor de enemigos
     gestor_enemigos=new Gestor_Enemigos();
    
     // Creamos la pantalla del juego
     setLayout(new BorderLayout());
     pantalla=new Pantalla(1.8);
     add(pantalla,BorderLayout.CENTER);
     Panel panel_inferior=new Panel(new GridLayout(1,5));
     panel_inferior.add(new Label(nombre_jugador_applet));
     label_Puntuacion=new Label("Puntos = "+protagonista.puntos);
     panel_inferior.add(label_Puntuacion);
     label_Vidas=new Label("Vidas = "+protagonista.vidas);
     panel_inferior.add(label_Vidas);
     
     label_vida_enemigo_final=new Label("Enemigo = "+vida_enemigo_final);
     panel_inferior.add(label_vida_enemigo_final);
     
     reloj_nemexis=new Reloj_Nemexis();
     panel_inferior.add(reloj_nemexis);
     add(panel_inferior, BorderLayout.SOUTH);
     // Anaydimos los listeners
     pantalla.addKeyListener(this);
     addKeyListener(this);

     // Pedimos el foco
     pantalla.requestFocus();

     // Validamos y refrescamos
     validate();
     doLayout();
     repaint();
  }

  //-----------------------------------------------------------------------------------------
  //  Funcion mostrar_Foto_Final_Partida(boolean es_ganador,String texto_mensaje_par)
  //   Esta funcion mostrara a pantalla completa la foto correspondiente ganadora o perdedora
  // mas un texto por pantalla.
  public void mostrar_Foto_Final_Partida(boolean es_ganador,String texto_mensaje_par)
  {  Pantalla_Foto panta_foto=null;
     removeAll();
     setLayout(new GridLayout(1,1));
     
     protagonista.estado_disparo=Jugador.MODO_PAZ;
      
     // Desconectamos
     desconexion_procesos();

     // Mostramos imagen correspondiente
     if (es_ganador)
     {  
       panta_foto=new Pantalla_Foto(image_ganador,texto_mensaje_par);       
     }      
     else 
     {
       panta_foto=new Pantalla_Foto(image_perdedor,texto_mensaje_par);
     }

     add(panta_foto);            
      
     validate(); 
     doLayout();
     repaint(); 
   }


  //------------------------------------------------------------------------------
  // Procedimiento init()
  public void init() 
  {
   // Leemos las imagenes
   Leer_Imagenes_Local();
  }

  //------------------------------------------------------------------------------
  // Procedimiento desconexion_procesos
  //  Procedimiento que finalizara con todos los procesos y los recursos
  void desconexion_procesos()
  { 
     // Nos desconectamos de la comunicacion
     desconexion_procesos_comunicacion();
     
     // Liberamos las cosas
     try {       
       // Desconectamos los jugadores
       Enumeration e=lista_jugadores.keys();
       while(e.hasMoreElements())
       { String snom=(String)e.nextElement();
         Jugador sjug=(Jugador)lista_jugadores.get(snom);
 
         // Desconectamos el jugador y sus balas
         sjug.salir_jugador=true;
         sjug.gestor_balas.salir_gestor_balas=true;
          
         // Enviamos al garbage collector
         try { sjug.gestor_balas=null; } catch (Exception exgestbal) {};
         try { sjug=null;} catch (Exception exjug) {};
       }

       // Detenemos y eliminamos el tablero
       tabla.salir_tablero=true;
       try {  tabla=null; } catch (Exception extabla) {};

       // Detenemos y eliminamos los enemigos
       gestor_enemigos.salir_gestor_enemigos=true;
       try { gestor_enemigos=null; } catch (Exception exgestene) {};

       // Eliminamos la pantalla
       try { pantalla=null; } catch (Exception expant) {};
      } catch (Exception e) {};     
  }
 
  //------------------------------------------------------------------------------
  // Procedimiento desconexion_procesos_comunicacion
  //  Procedimiento que finalizara con todos los procesos y los recursos de comunicacion
  void desconexion_procesos_comunicacion()
  {
    // Liberamos el recurso de comunicacion
    try {
    //  Comunicador_Nemexis.Enviar_Paquete_Nemexis(Comunicador_Nemexis.DESCONECTAR,"");

    // Paramos el flujo de comunicacion
  //  Comunicador_Nemexis.escucha_comunica.salir_comunicacion_tcp=true;
  //  Comunicador_Nemexis.salir_comunicacion=true;

  //  Comunicador_Nemexis.escucha_comunica.comunica_hear.client.close();

  //  Comunicador_Nemexis.escucha_comunica=null;
  //  Comunicador_Nemexis=null;
   } catch (Exception ed) {}
  }


   //---------------------------------------------------------------------
   // Procedimiento stop
   //   Enviamos un paquete de comunicacion de abandono de partida y cerrado
   public void stop()
   { 
     try { desconexion_procesos(); } catch (Exception e) {};
   }


   //---------------------------------------------------------------------
   // Procedimiento destroy
   //   Enviamos un paquete de comunicacion de abandono de partida y cerrado
   public void destroy()
   { 
     try { desconexion_procesos(); } catch (Exception e) {};
   }

  
  //------------------------------------------------------------------------------
  // Procedimiento modificar_Lista_Teclas(Integer direccion_par, boolean accion_par)
  //   Este procedimiento insertara/eliminara entradas de la lista de teclas
  void modificar_Lista_Teclas(Integer direccion_par, boolean accion_par)
  { 
     // Dependiendo del tipo de accion ((accion_par==true-> insertar)((accion_par==false-> eliminar))
     if (accion_par)
     {  if ((Integer)teclas_pulsadas.get(direccion_par)==null)
        teclas_pulsadas.put(direccion_par,new Integer(direccion_par.intValue()));
     }
     else // Eliminar
     {
       if ((Integer)teclas_pulsadas.get(direccion_par)!=null)
         teclas_pulsadas.remove(direccion_par);
     }
  }

   //------------------------------------------------------------------------------
   // Funciones asociadas al teclado

   //------------------------------------------------------------------------------
   // Procedimiento keyPressed
   //   Este procedimiento anyadira (sin repetir) las direcciones de movimiento de la lista
   // y determinaremos la nueva direccion del jugador
   public void keyPressed( KeyEvent e ) 
   {  
     // Segun el codigo de tecla
     switch (e.getKeyCode())
     {  case TECLA_ARRIBA:    modificar_Lista_Teclas(new Integer(ARRIBA),true);    break;
        case TECLA_ABAJO:     modificar_Lista_Teclas(new Integer(ABAJO),true);     break;
        case TECLA_RIGHT:     modificar_Lista_Teclas(new Integer(DERECHA),true);   break;
        case TECLA_LEFT:      modificar_Lista_Teclas(new Integer(IZQUIERDA),true); break;
        case TECLA_ESPACIO:   protagonista.cambiar_Disparo(Jugador.MODO_DISPARO);  break;
     }

     // Determinamos la nueva direccion entre las 8 posibles
     int nueva_direccion=NINGUNA;
     switch (teclas_pulsadas.size())
     {  case 1:  // Determinamos entre 4 direcciones
          if ((Integer)teclas_pulsadas.get(new Integer(ARRIBA))!=null)
          {    nueva_direccion=ARRIBA; break; }
          if ((Integer)teclas_pulsadas.get(new Integer(ABAJO))!=null)
          {    nueva_direccion=ABAJO; break; }
          if ((Integer)teclas_pulsadas.get(new Integer(DERECHA))!=null)
          {    nueva_direccion=IZQUIERDA; break; }
          if ((Integer)teclas_pulsadas.get(new Integer(IZQUIERDA))!=null)
          {    nueva_direccion=DERECHA; break; }
          break;
        case 2:
           if (((Integer)teclas_pulsadas.get(new Integer(ARRIBA))!=null)&&((Integer)teclas_pulsadas.get(new Integer(IZQUIERDA))!=null))
           {    nueva_direccion=ARRIBA_DERECHA;   break; }
           if (((Integer)teclas_pulsadas.get(new Integer(ARRIBA))!=null)&&((Integer)teclas_pulsadas.get(new Integer(DERECHA))!=null))
           {    nueva_direccion=ARRIBA_IZQUIERDA; break; }
           if (((Integer)teclas_pulsadas.get(new Integer(ABAJO))!=null)&&((Integer)teclas_pulsadas.get(new Integer(IZQUIERDA))!=null))
           {    nueva_direccion=ABAJO_DERECHA;    break; }
           if (((Integer)teclas_pulsadas.get(new Integer(ABAJO))!=null)&&((Integer)teclas_pulsadas.get(new Integer(DERECHA))!=null))
           {    nueva_direccion=ABAJO_IZQUIERDA;  break; }
           break;
       }

       // Realizamos el cambio de direccion
       protagonista.cambiar_Direccion(nueva_direccion);
   }

   public void keyTyped( KeyEvent e ) {   }

   //------------------------------------------------------------------------------
   // Procedimiento keyPressed
   //   Este procedimiento eliminara la tecla pulsada de la lista
   public void keyReleased( KeyEvent e ) 
   {
     // Segun el codigo de tecla
     switch (e.getKeyCode())
     {  case TECLA_ARRIBA:    modificar_Lista_Teclas(new Integer(ARRIBA),false);    break;
        case TECLA_ABAJO:     modificar_Lista_Teclas(new Integer(ABAJO),false);     break;
        case TECLA_RIGHT:     modificar_Lista_Teclas(new Integer(DERECHA),false);   break;
        case TECLA_LEFT:      modificar_Lista_Teclas(new Integer(IZQUIERDA),false); break;     
        case TECLA_ESPACIO:   protagonista.cambiar_Disparo(Jugador.MODO_PAZ);       break;
      }

     // Controlamos la lista vacia
     if (teclas_pulsadas.size()==0) protagonista.cambiar_Direccion(NINGUNA);
   }
  

  //************************************************************************************
  //************************************************************************************
  //************************************************************************************
  //************************************************************************************
  //************************************************************************************
  //************************************************************************************
  // Procedimiento principal 
  // Invocat nomes quan s'executa com a aplicacio
   public static void main(String[] args) 
  {
        nemexis= new Nemexis();
        
        MainFrame mf= new MainFrame(nemexis , 750, 550);      
  		mf.addWindowListener( new WindowListener()
  								{
  									public void windowDeactivated(java.awt.event.WindowEvent we)
  									{
  										//nemexis.stop();	
  									}
  									
  									public void windowActivated(java.awt.event.WindowEvent we)
  									{
  										
  									}
  									public void windowDeiconified(java.awt.event.WindowEvent we)
  									{
  										
  									}
  									public void windowIconified(java.awt.event.WindowEvent we)
  									{
  										
  									}
  									public void windowClosed(java.awt.event.WindowEvent we)
  									{
  										
  									}
  									public void windowClosing(java.awt.event.WindowEvent we)
  									{
  										nemexis.stop();
  										System.gc();	
  									}
  									public void windowOpened(java.awt.event.WindowEvent we)
  									{
  										
  									}		
  								}
  							);
  							
  }
  
    public void set_dimension_pantalla(Dimension d)
  {
  	dimension_pantalla=d;
  }
  
  
  
  private Dimension dimension_pantalla=null;
  public static Nemexis nemexis;
  int vida_enemigo_final=0;
  public static int VIDA_ENEMIGO_FINAL=50;
  public static int VIDA_PROTAGONISTA=77;
  Label label_vida_enemigo_final=null;// Esta etiqueta muestra las vidas restantes del Enemigo Final  
  	
 
}

