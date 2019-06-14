
package com.cc.paint3D.Graficador3D.G3D;

import com.cc.paint3D.Graficador3D.Escena;
import com.cc.paint3D.Graficador3D.interfaces.Props;
import com.cc.paint3D.Graficador3D.interfaces.Tipo_componente;
import com.cc.paint3D.Graficador3D.ES.Dialogo_de_error;
import com.cc.paint3D.Graficador3D.Paneles.Panel_Principal;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_opciones_de_escala;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_de_fondo;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_fondo;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_ambiente;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_color_de_niebla;
import com.cc.paint3D.Graficador3D.Menus.Menu_de_iluminacion;
import com.cc.paint3D.Graficador3D.ES.ES_Utiles;

import com.cc.paint3D.Graficador3D.IO.Informacion_de_BH;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Vector;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.interpolators.*;

public class Datos_utiles
    implements Props
{
	public static final int TEXTURAS[] = {2, 4, 5, 3};	
    public static BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0D, 0.0D, 0.0D), 200);
    
    //public static BoundingSphere boundingSphere_big = new BoundingSphere(new Point3d(5000.0D, 5000.0D, 5000.0D), 10000);
        
    public static Vector files;
	
	public static float traslacion_inicial=5.0f;
	
    public Datos_utiles()
    {
    }

    public static void reemplazar_actual(Tipo_componente tipo_componente)
    {
        tipo_componente.separar_este();
        Integer integer = (Integer)tipo_componente.get_Prop(Props.POS);//7
        int i = integer.intValue();
        try
        {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
            tipo_componente.escribir_Objeto(dataoutputstream);
            byte abyte0[] = bytearrayoutputstream.toByteArray();
            ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
            DataInputStream datainputstream = new DataInputStream(bytearrayinputstream);
            
            Object obj = null;
            String cual=datainputstream.readUTF();
            System.out.println("Datos_utiles:reemplazar_actual:cual= "+cual);
            
            
            obj = Class.forName("com.cc.paint3D.Graficador3D.G3D." + cual).newInstance();//Graficador_3D.G3D.
            Tipo_componente tipo_componente1 = (Tipo_componente)obj;
            tipo_componente1.set_Prop(Props.NOMBRE, datainputstream.readUTF());//22
            datainputstream.readInt();
            tipo_componente1.leer_Objeto(datainputstream);
            Escena.reemplazar_componente(i, tipo_componente1);
            tipo_componente1.set_Prop(Props.POS, i );//7
        }
        catch(Exception exception)
        {
            new Dialogo_de_error("Error al reemplazar el componente " + exception.getMessage());//"Error changing component "
        }
        System.gc();
    }

    public static void escribir_todo(DataOutputStream dataoutputstream)
        throws IOException
    {
        if(files == null)
            files = new Vector();
        else
            files.removeAllElements();
        
        Dimension dimension = Panel_Principal.handle.getSize();
        dataoutputstream.writeInt(dimension.width);
        dataoutputstream.writeInt(dimension.height);
        dataoutputstream.writeFloat(Escena.scale_actual_x);
        dataoutputstream.writeFloat(Escena.scale_actual_y);
        dataoutputstream.writeFloat(Escena.scale_actual_z);

        Menu_de_color_de_fondo.escribir_Background(dataoutputstream);
        int i = Menu_de_fondo.menu_de_seleccion_multiple_image.get_estado();
System.out.println("Datos_utiles:escribir_todo:i= "+i);        
        dataoutputstream.writeInt(i);
        if(i != 0)
        {
            dataoutputstream.writeUTF(Menu_de_fondo.path.getAbsolutePath());
            files.addElement(Menu_de_fondo.path);
        }
        Menu_de_color_ambiente.escribir_Ambient(dataoutputstream);
        Menu_de_color_de_niebla.escribir_niebla(dataoutputstream);
        int j = 0;
        if(Menu_de_iluminacion.figura_luz_Direccional != null)
            j++;
        j += Menu_de_iluminacion.get_numero_de_luces_Puntuales();
        j += Menu_de_iluminacion.get_numero_de_luces_Spot();
        dataoutputstream.writeInt(j);
        if(Menu_de_iluminacion.figura_luz_Direccional != null)
            Menu_de_iluminacion.figura_luz_Direccional.escribir_Figura_luz(dataoutputstream);
        int k = Menu_de_iluminacion.vector_de_figura_luz_Puntual.size();
        for(int l = 0; l < k; l++)
        {
            Object obj = Menu_de_iluminacion.vector_de_figura_luz_Puntual.elementAt(l);
            if(obj != null)
                ((Figura_luz)obj).escribir_Figura_luz(dataoutputstream);
        }

        k = Menu_de_iluminacion.vector_de_figura_luz_Spot.size();
        for(int i1 = 0; i1 < k; i1++)
        {
            Object obj1 = Menu_de_iluminacion.vector_de_figura_luz_Spot.elementAt(i1);
            if(obj1 != null)
                ((Figura_luz)obj1).escribir_Figura_luz(dataoutputstream);
        }

        Vector vector = Escena.vector_de_Componentes;
        int j1 = vector.size();
        int k1 = 0;
        for(int l1 = 0; l1 < j1; l1++)
        {
            Object obj2 = vector.elementAt(l1);
            if(obj2 != null)
                k1++;
        }

        dataoutputstream.writeInt(k1);
        for(int i2 = 0; i2 < j1; i2++)
        {
            Object obj3 = vector.elementAt(i2);
            if(obj3 != null)
            {
                Tipo_componente tipo_componente = (Tipo_componente)obj3;
System.out.println("Datos_utiles:escribir_todo:__ tipo_componente.escribir_Objeto__:");                
                tipo_componente.escribir_Objeto(dataoutputstream);
                tipo_componente.set_Prop(Props.FILES, files);
            }
        }

    }

    public static Canvas3D getCanvas(DataInputStream datainputstream, Dimension dimension)
        throws IOException
    {
        java.awt.GraphicsConfiguration graphicsconfiguration = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3d = new Canvas3D(graphicsconfiguration);
        SimpleUniverse simpleuniverse = new SimpleUniverse(canvas3d);
        simpleuniverse.getViewingPlatform().setNominalViewingTransform();
        BranchGroup branchgroup = new BranchGroup();
        TransformGroup transformgroup = new TransformGroup();
        Transform3D transform3d = new Transform3D();
        float x=datainputstream.readFloat();
        float y=datainputstream.readFloat();
        float z=datainputstream.readFloat();
        transform3d.setScale( new Vector3d(x,y,z) );
        transformgroup.setTransform(transform3d);
        branchgroup.addChild(transformgroup);
                
        /*
        {                        									       
            transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            
            KeyNavigatorBehavior keynavigatorbehavior = new KeyNavigatorBehavior(transformgroup);
            keynavigatorbehavior.setSchedulingBounds(boundingSphere);
            transformgroup.addChild(keynavigatorbehavior);
        }*/
        
                        
        leer_todo(datainputstream, branchgroup, transformgroup, dimension);
        branchgroup.compile();
        
        simpleuniverse.addBranchGraph(branchgroup);        
        
        return canvas3d;
    }

    public static BufferedImage get_BufferedImage(String s)
    {
        return ES_Utiles.get_BufferedImage(new File(s));
    }

    public static void leer_todo(DataInputStream datainputstream, BranchGroup branchgroup, TransformGroup transformgroup, Dimension dimension)
        throws IOException
    {
        int i = 0;
        i = datainputstream.readInt();
        System.out.println("Datos_utiles:leer_todo:background:i= "+i);
        if(i != 0)
        {
            Background background = new Background(get_Color3f(i));
            background.setApplicationBounds(boundingSphere);
            branchgroup.addChild(background);
        }
        i = datainputstream.readInt();
        System.out.println("Datos_utiles:leer_todo:Background:bufferedimage:i= "+i);       
        if(i != 0)
        {
            
            BufferedImage bufferedimage = get_BufferedImage(datainputstream.readUTF());
            Object obj = null;
            Background background1 = null;
            if(bufferedimage != null)
            {
                switch(i)
                {
                default:
                    break;

                case Menu_de_fondo.SENCILLA:
                    TextureLoader textureloader = new TextureLoader(bufferedimage);
                    background1 = new Background(textureloader.getImage());
                    break;

                case Menu_de_fondo.ESCALADA:
                    TextureLoader textureloader1 = new TextureLoader(bufferedimage);
                    background1 = new Background(textureloader1.getScaledImage(dimension.width, dimension.height));
                    break;

                case Menu_de_fondo.MOSAICO: // '3'
                    BufferedImage bufferedimage1 = new BufferedImage(dimension.width, dimension.height, 1);
                    int i1 = bufferedimage.getHeight();
                    int j1 = bufferedimage.getWidth();
                    java.awt.Graphics2D graphics2d = bufferedimage1.createGraphics();
                    for(int k1 = 0; k1 < dimension.height; k1 += i1)
                    {
                        for(int l1 = 0; l1 < dimension.width; l1 += j1)
                            graphics2d.drawImage(bufferedimage, l1, k1, null);

                    }

                    bufferedimage = bufferedimage1;
                    TextureLoader textureloader2 = new TextureLoader(bufferedimage);
                    background1 = new Background(textureloader2.getImage());
                    break;
                }
                background1.setApplicationBounds(boundingSphere);
                branchgroup.addChild(background1);
            }
        }
        i = datainputstream.readInt();
    
        if(i != 0)
        {
            AmbientLight ambientlight = new AmbientLight(get_Color3f(i));
            ambientlight.setInfluencingBounds(boundingSphere);
            branchgroup.addChild(ambientlight);
        }
        i = datainputstream.readInt();
        System.out.println("Datos_utiles:leer_todo:ExponentialFog:i= "+i);    
        if(i != 0)
        {
            int j = datainputstream.readInt();
            float f = 0.0F;
            if(j > 0)
                f = (float)j / 100F;
            ExponentialFog exponentialfog = new ExponentialFog(get_Color3f(i), f);
            exponentialfog.setInfluencingBounds(boundingSphere);
            branchgroup.addChild(exponentialfog);
        }
        i = datainputstream.readInt();
        System.out.println("Datos_utiles:leer_todo:addLight:i= "+i);        
        for(int k = 0; k < i; k++)
            agregar_luz(datainputstream, transformgroup);

        i = datainputstream.readInt();
        for(int l = 0; l < i; l++)
        {
            datainputstream.readUTF();
            datainputstream.readUTF();
            agregar_Figura(datainputstream, datainputstream.readInt(), transformgroup);
        }

    }

    public static void agregar_luz(DataInputStream datainputstream, TransformGroup transformgroup)
        throws IOException
    {
        Object obj = null;
        int i = datainputstream.readInt();
        Color3f color3f = get_Color3f(datainputstream.readInt());
        TransformGroup transformgroup1 = leer_TransformGroup(datainputstream);
        switch(i)
        {
        case Figura_luz.DIRECCIONAL: 
            obj = new DirectionalLight();
            break;

        case Figura_luz.PUNTUAL: 
            PointLight pointlight = new PointLight();
            obj = pointlight;
            pointlight.setAttenuation(leer_Point3f(datainputstream));
            break;

        case Figura_luz.SPOT: 
            SpotLight spotlight = new SpotLight();
            obj = spotlight;
            spotlight.setAttenuation(leer_Point3f(datainputstream));
            spotlight.setSpreadAngle(datainputstream.readFloat());
            spotlight.setConcentration(datainputstream.readFloat());
            spotlight.setDirection(leer_Vector3f(datainputstream));
            break;
        }
        ((SceneGraphObject) (obj)).setCapability(TransformGroup.ALLOW_COLLISION_BOUNDS_READ);
        ((Light) (obj)).setColor(color3f);
        ((Light) (obj)).setInfluencingBounds(boundingSphere);
        transformgroup1.addChild(((Node) (obj)));
        transformgroup.addChild(transformgroup1);
    }

    public static Point3f leer_Point3f(DataInputStream datainputstream)
        throws IOException
    {
        return new Point3f(datainputstream.readFloat(), datainputstream.readFloat(), datainputstream.readFloat());
    }

    public static Vector3f leer_Vector3f(DataInputStream datainputstream)
        throws IOException
    {
        return new Vector3f(datainputstream.readFloat(), datainputstream.readFloat(), datainputstream.readFloat());
    }

    public static TransformGroup leer_TransformGroup(DataInputStream datainputstream)
        throws IOException
    {
        System.out.println("Datos_utiles:leer_TransformGroup:");
        
        TransformGroup transformgroup = new TransformGroup();
        float af[] = new float[16];
        for(int i = 0; i < 16; i++)
            af[i] = datainputstream.readFloat();

        Transform3D transform3d = new Transform3D(af);
        transformgroup.setTransform(transform3d);
        
        System.out.println("Datos_utiles:leer_TransformGroup:return");
        return transformgroup;
    }

    public static void agregar_Figura(DataInputStream datainputstream, int i, TransformGroup transformgroup)
        throws IOException
    {
    	System.out.println("Datos_utiles:agregar_Figura:");
    	
        Appearance appearance = new Appearance();
        appearance.setMaterial(new Material());
        switch(i)
        {
        default:
            break;

        case Figura.ESFERA: 
            Sphere sphere = new Sphere(datainputstream.readFloat(), 3, datainputstream.readInt(), appearance);
            agregar_Primitiva(datainputstream, transformgroup, sphere, appearance);
            break;

        case Figura.CUBO: 
            float f = datainputstream.readFloat();
            Box box = new Box(f, f, f, 3, appearance);
            agregar_Primitiva(datainputstream, transformgroup, box, appearance);
            break;

        case Figura.CAJA: 
            Box box1 = new Box(datainputstream.readFloat(), datainputstream.readFloat(), datainputstream.readFloat(), 3, appearance);
            agregar_Primitiva(datainputstream, transformgroup, box1, appearance);
            break;

        case Figura.CONO: 
            Cone cone = new Cone(datainputstream.readFloat(), datainputstream.readFloat(), 3, datainputstream.readInt(), datainputstream.readInt(), appearance);
            agregar_Primitiva(datainputstream, transformgroup, cone, appearance);
            break;

        case Figura.CILINDRO: 
            Cylinder cylinder = new Cylinder(datainputstream.readFloat(), datainputstream.readFloat(), 3, datainputstream.readInt(), datainputstream.readInt(), appearance);
            agregar_Primitiva(datainputstream, transformgroup, cylinder, appearance);
            break;
	
		case Figura.TETRAEDRO: 
            Shape3D shape3D = Figura_tetra.createTetraedro(datainputstream.readFloat(), appearance);
            agregar_Primitiva(datainputstream, transformgroup, shape3D, appearance);
            break;
        
       case Figura.TERRENO_FRACTAL: 
            Shape3D shape3D1 = Figura_terreno_Fractal.createFractal(datainputstream.readFloat(), appearance);
            agregar_Primitiva(datainputstream, transformgroup, shape3D1, appearance);
            break;     
		
        case Figura.TEXTO_3D: 
            FontExtrusion fontextrusion = new FontExtrusion();
            Font3D font3d = new Font3D(new Font(datainputstream.readUTF(), datainputstream.readInt(), datainputstream.readInt()), fontextrusion);
            Text3D text3d = new Text3D(font3d, datainputstream.readUTF(), new Point3f(0.0F, 0.0F, 0.0F), 0, datainputstream.readInt());
            datainputstream.readInt();
            Shape3D shape3d = new Shape3D();
            shape3d.setGeometry(text3d);
            shape3d.setAppearance(appearance);
            TexCoordGeneration texcoordgeneration = new TexCoordGeneration();
            texcoordgeneration.setEnable(true);
            appearance.setTexCoordGeneration(texcoordgeneration);
            agregar_Primitiva(datainputstream, transformgroup, shape3d, appearance);
            break;

        case Figura.TEXTO_2D: 
            TransformGroup transformgroup1 = leer_TransformGroup(datainputstream);
            Text2D text2d = new Text2D(datainputstream.readUTF(), get_Color3f(datainputstream.readInt()), datainputstream.readUTF(), datainputstream.readInt(), datainputstream.readInt());
            if(datainputstream.readInt() == 1)
            {
                PolygonAttributes polygonattributes = new PolygonAttributes();
                polygonattributes.setCullFace(PolygonAttributes.CULL_NONE);
                polygonattributes.setBackFaceNormalFlip(true);
                text2d.getAppearance().setPolygonAttributes(polygonattributes);
            }
            agregar_BH(datainputstream, transformgroup, transformgroup1, text2d);
            break;
        }
    }

    public static void agregar_Primitiva(DataInputStream datainputstream, TransformGroup transformgroup, Node node, Appearance appearance)
        throws IOException
    {
        TransformGroup transformgroup1 = leer_TransformGroup(datainputstream);
        Material material = appearance.getMaterial();
        int i;
        if((i = datainputstream.readInt()) >= 0)
        {
            float f = (float)i / 100F;
            appearance.setTransparencyAttributes(new TransparencyAttributes(2, f));
        }
        if((i = datainputstream.readInt()) >= 0)
            material.setShininess(i);
        if((i = datainputstream.readInt()) != 0)
            material.setDiffuseColor(get_Color3f(i));
        if((i = datainputstream.readInt()) != 0)
            material.setSpecularColor(get_Color3f(i));
        if((i = datainputstream.readInt()) != 0)
            material.setEmissiveColor(get_Color3f(i));
        if((i = datainputstream.readInt()) != 0)
            material.setAmbientColor(get_Color3f(i));
        String s = datainputstream.readUTF();
        if(!s.equals(""))
        {
            BufferedImage bufferedimage = get_BufferedImage(s);
            TextureLoader textureloader = new TextureLoader(bufferedimage);
            javax.media.j3d.Texture texture = textureloader.getTexture();
            TextureAttributes textureattributes = new TextureAttributes();
            textureattributes.setTextureMode(TEXTURAS[datainputstream.readInt() - 1]);
            appearance.setTexture(texture);
            appearance.setTextureAttributes(textureattributes);
        }
        agregar_BH(datainputstream, transformgroup, transformgroup1, node);
    }

    public static void agregar_BH(DataInputStream datainputstream, TransformGroup transformgroup, TransformGroup transformgroup1, Node node)
        throws IOException
    {
        //no hay BH
        if(!datainputstream.readBoolean())
        {
            transformgroup.addChild(transformgroup1);
            transformgroup1.addChild(node);
            return;
        }
        TransformGroup transformgroup2 = new TransformGroup();
        transformgroup2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        int a=datainputstream.readInt();
System.out.println("******************************");        
System.out.println("Datos_utiles:agregar_BH:a= "+a);
System.out.println("******************************");        
        switch(a)
        {
        case Informacion_de_BH.ROTACION:            
            {
System.out.println("Datos_utiles:agregar_BH:ROTACION");            	
            int i = datainputstream.readInt();
System.out.println("Datos_utiles:agregar_BH:ROTACION:i= "+i);             
            if(!datainputstream.readBoolean())
            {
                transformgroup.addChild(transformgroup1);
                transformgroup1.addChild(transformgroup2);
                transformgroup2.addChild(node);
            } else
            {
                transformgroup.addChild(transformgroup2);
                transformgroup2.addChild(transformgroup1);
                transformgroup1.addChild(node);
            }
            Alpha alpha = new Alpha(-1, datainputstream.readInt() * 1000);//tiempo en segundos
            Transform3D transform3d = new Transform3D();
            //para que roten los otros dos ejes
            switch(i)
            {
            case 0: 
                transform3d.rotY(Math.PI/2.0);
                break;
                
            case 1: 
                transform3d.rotZ(Math.PI/2.0);
                break;

            case 2: 
                transform3d.rotX(Math.PI/2.0);
                break;
            }
            RotationInterpolator rotationinterpolator = new RotationInterpolator(alpha, transformgroup2, transform3d, 0.0F, 6.283185F);//2*PI
            rotationinterpolator.setSchedulingBounds(boundingSphere);
            transformgroup.addChild(rotationinterpolator);
            break;
        	}
        case Informacion_de_BH.TRASLACION:
        	{
System.out.println("Datos_utiles:agregar_BH:TRASLACION:");         		
        	int i = datainputstream.readInt();
System.out.println("Datos_utiles:agregar_BH:TRASLACION:i= "+i);         	
////////////////////////////////////////////////////////////////////////////////
				transformgroup.addChild(transformgroup1);
                transformgroup1.addChild(transformgroup2);
                transformgroup2.addChild(node);
////////////////////////////////////////////////////////////////////////////////           
            if(!datainputstream.readBoolean())
            {
                //transformgroup.addChild(transformgroup1);
                //transformgroup1.addChild(transformgroup2);
                //transformgroup2.addChild(node);
                traslacion_inicial=5F;
            } else
            {
                //transformgroup.addChild(transformgroup2);
                //transformgroup2.addChild(transformgroup1);
                //transformgroup1.addChild(node);
                traslacion_inicial=10F;
            }
            Alpha alpha = new Alpha(-1, datainputstream.readInt() * 1000);//tiempo en segundos
            Transform3D transform3d = new Transform3D();
            
            switch(i)
            {
            case 0: 
                transform3d.rotZ(Math.PI/2.0);//Z
                break;
                
            case 1: 
                transform3d.rotX(Math.PI/2.0);//X
                break;

            case 2: 
                transform3d.rotY(Math.PI/2.0);//Y
                break;
            }
            
			PositionInterpolator translator = new PositionInterpolator(alpha,transformgroup2,transform3d,0.0f, traslacion_inicial);
			translator.setSchedulingBounds(boundingSphere);
			transformgroup.addChild(translator);
            
            break;
            }
         case Informacion_de_BH.TRASLACION_PUNTOS:
         	{
         		
         	transformgroup.addChild(transformgroup1);
            transformgroup1.addChild(transformgroup2);
            transformgroup2.addChild(node);	
         		
System.out.println("Datos_utiles:agregar_BH:TRASLACION_PUNTOS:");          		
         	int i = datainputstream.readInt();
System.out.println("Datos_utiles:agregar_BH:TRASLACION_PUNTOS:i= "+i);         	
         	Point3f[] puntos=new Point3f[i];
        	for(int i1=0;i1<puntos.length;i1++)
        	{
        		puntos[i1]=leer_Point3f(datainputstream);	
        	}        
        	//float escala=datainputstream.readFloat();
        	if(datainputstream.readInt()==0)//spline
        	{	
        		Alpha alpha = new Alpha(-1, datainputstream.readInt() * 1000);
        		Transform3D transform3d = new Transform3D();
         		
	  			KBKeyFrame[] splineKeyFrames = new KBKeyFrame[puntos.length];
      			
      			//float[] salto=createJumps(puntos);
      			float salto=(float)(1.0/(puntos.length-1));
System.out.println("Datos_utiles:agregar_BH:TRASLACION_PUNTOS:salto= "+salto);      
     			for(int i1=0;i1<puntos.length-1;i1++)
      			{	      		
      				float head  = (float)((Math.pow(-1,i1))*Math.PI/2.0f*Math.cos(Math.PI/2*i1) );                          	
      				float pitch = (float)((Math.pow(-1,i1))*Math.PI/2.0f*Math.sin(Math.PI/2*i1) );                            	
      				float bank  = (float)((Math.pow(-1,i1))*Math.PI/2.0f*Math.cos(Math.PI/2*i1) );                           	
      				Point3f s = new Point3f(1.0f, 1.0f, 1.0f);//s=ctte   
      				splineKeyFrames[i1] = 
        			 new KBKeyFrame((float)(i1*salto), 0, puntos[i1], head, pitch, bank, s, 0.0f, 0.0f, 0.0f);     	
      			}
      			{
      	
      				float head  = (float)((Math.pow(-1,puntos.length-1))*Math.PI/2.0f*Math.cos(Math.PI/2*puntos.length-1) );                          
      				float pitch = (float)((Math.pow(-1,puntos.length-1))*Math.PI/2.0f*Math.sin(Math.PI/2*puntos.length-1) );                          
      				float bank  = (float)((Math.pow(-1,puntos.length-1))*Math.PI/2.0f*Math.cos(Math.PI/2*puntos.length-1) );                          
      				Point3f s = new Point3f(1.0f, 1.0f, 1.0f);   
      				splineKeyFrames[puntos.length-1] = 
         			new KBKeyFrame(1.0f, 0, puntos[puntos.length-1], head, pitch, bank, s, 0.0f, 0.0f, 0.0f);     	
      			}      	
      			KBRotPosScaleSplinePathInterpolator splineInterpolator =
         		new KBRotPosScaleSplinePathInterpolator(alpha, transformgroup2,
                                                  transform3d, splineKeyFrames); 
      			splineInterpolator.setSchedulingBounds(boundingSphere);
      
      			transformgroup.addChild(splineInterpolator);
      			splineInterpolator.setEnable(true);
         	}
         	else//lineal
         	{	
        		Alpha alpha = new Alpha(-1, datainputstream.readInt() * 1000);
        		Transform3D transform3d = new Transform3D();
         		
	  			KBKeyFrame[] linearKeyFrames = new KBKeyFrame[puntos.length];
      			//float[] salto=createJumps(puntos);
      			float salto=(float)(1.0/(puntos.length-1));
System.out.println("Datos_utiles:agregar_BH:TRASLACION_PUNTOS:salto= "+salto);      
     			for(int i1=0;i1<puntos.length-1;i1++)
      			{	      		
      				float head  = 0.0f;                          	
      				float pitch = 0.0f;                          	
      				float bank  = 0.0f;;                          	
      				Point3f s = new Point3f(1.0f, 1.0f, 1.0f);//s=ctte   
      				linearKeyFrames[i1] = 
        			 new KBKeyFrame((float)(i1*salto), 1, puntos[i1], head, pitch, bank, s, 0.0f, 0.0f, 0.0f);     	
      			}
      			{
      	
      				float head  = 0;                          
      				float pitch = 0;                          
      				float bank  = 0;;                          
      				Point3f s = new Point3f(1.0f, 1.0f, 1.0f);   
      				linearKeyFrames[puntos.length-1] = 
         			new KBKeyFrame(1.0f, 1, puntos[puntos.length-1], head, pitch, bank, s, 0.0f, 0.0f, 0.0f);     	
      			}      	
      			KBRotPosScaleSplinePathInterpolator linearInterpolator =
         		new KBRotPosScaleSplinePathInterpolator(alpha, transformgroup2,
                                                  transform3d, linearKeyFrames); 
      			linearInterpolator.setSchedulingBounds(boundingSphere);
      
      			transformgroup.addChild(linearInterpolator);
      			linearInterpolator.setEnable(true);
         	}
         		
         	break;	
         	}     
        
        }
    }
    
    /*public static float[] createJumps(Point3f[] p3f)
    {
    float[] f_salida=new float[p3f.length-1];
    
    double distancia_total=0;
    float[] distancia_actual=new float[p3f.length-1];
    
    for(int i=0; i<p3f.length-1;i++)
    {
    	//float dist=Math.pow( (p3f[i].x-p3f[i+1].x) ,2) +
    	//		   Math.pow( (p3f[i].y-p3f[i+1].y) ,2) +
		//		   Math.pow( (p3f[i].z-p3f[i+1].z) ,2) ;
				   	    			   
    	//float distancia_actual=Math.pow( dist ,0.5f);
    	distancia_actual[i]=p3f[i].distance(p3f[i+1]);	
    	distancia_total+=distancia_actual[i];
    }
    
    for(int i=0; i<p3f.length-1;i++)
    {
    	f_salida[i]=(float)(distancia_actual[i]/distancia_total);
    	System.out.println("Informacion_de_traslacion f_salida[i]="+f_salida[i]);		
    }
    
    return f_salida;    	
    }*/

    public static Color3f get_Color3f(int i)
    {
        Color color = new Color(i);
        float f = color.getRed() != 0 ? (float)color.getRed() / 255F : 0.0F;
        float f1 = color.getGreen() != 0 ? (float)color.getGreen() / 255F : 0.0F;
        float f2 = color.getBlue() != 0 ? (float)color.getBlue() / 255F : 0.0F;
        return new Color3f(f, f1, f2);
    }

    

}