import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

//import com.sun.j3d.utils.behaviors.keyboard.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.behaviors.picking.*;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.media.j3d.*;
import javax.media.j3d.PositionInterpolator;
import com.sun.j3d.utils.behaviors.interpolators.*;

import javax.vecmath.*;
import java.util.Vector;

public class Graficador3D_applet extends Applet implements ActionListener
{
	/*
	String S_START="Iniciar";
    private Button start = new Button(S_START);
    String S_STOP="Detener";
    private Button stop = new Button(S_STOP);
    */    
    /*
    public Panel paneles()
    {
	Panel p = new Panel();
	p.add("East",start);
	start.addActionListener(this);
	p.add("East",stop);
	stop.addActionListener(this);
	return p;
    }    
    */
    public final int TEXTURAS[] = {2, 4, 5, 3};
    
    Bounds bnds;
    byte buf[];
    
    ByteArrayOutputStream baos;
    
    //para fijar la textura a un componente
    MediaTracker tracker;
    
    //propio   
    public Vector vector_de_BH_spline= new Vector();
    public Vector vector_de_BH_traslacion= new Vector();
    public Vector vector_de_BH_rotacion= new Vector();
    
    //Figura_luz
    public static final int DIRECCIONAL = 0;
    public static final int SPOT = 1;
    public static final int PUNTUAL = 2;
        
    //Figura
    public final int ESFERA = 0;
    public final int CUBO = 1;
    public final int CAJA = 2;
    public final int CONO = 3;
    public final int CILINDRO = 4;
    public final int TEXTO_3D = 5;
    public final int TEXTO_2D = 6;
    public final int TETRAEDRO = 7;
    
    //Menu_de_fondo
    public final int NINGUNO = 0;
    public final int SENCILLA = 1;
    public final int ESCALADA = 2;
    public final int MOSAICO = 3;
    
    //Informacion_de_BH
    public final int ROTACION=1;
    public final int TRASLACION=2;
    public final int TRASLACION_PUNTOS=3;
    	
	
    public Graficador3D_applet()
    {
		//vector_de_BH_spline= new Vector();
    	//vector_de_BH_traslacion= new Vector();
    	//vector_de_BH_rotacion= new Vector();
    }
	//Datos_utiles
    void agregar_BH(DataInputStream datainputstream, TransformGroup transformgroup, TransformGroup transformgroup1, Node node)
        throws IOException
    {
        
        boolean bo=datainputstream.readBoolean();
System.out.println("agregar_BH:bo="+bo);         
        if(!bo)
        {
            transformgroup.addChild(transformgroup1);
            transformgroup1.addChild(node);
System.out.println("agregar_BH:agregamos node 1");            
            return;
        }
        TransformGroup transformgroup2 = new TransformGroup();
        transformgroup2.setCapability(transformgroup.ALLOW_TRANSFORM_WRITE);//18
        
        switch(datainputstream.readInt())
        {
        case ROTACION:
            {
            int i = datainputstream.readInt();
System.out.println("agregar_BH:i="+i);
			bo=datainputstream.readBoolean();
System.out.println("agregar_BH:bo="+bo); 			            
            if(!bo)
            {
                transformgroup.addChild(transformgroup1);
                transformgroup1.addChild(transformgroup2);
                transformgroup2.addChild(node);
System.out.println("agregar_BH:agregamos node 2");                   
            } else
            {
                transformgroup.addChild(transformgroup2);
                transformgroup2.addChild(transformgroup1);
                transformgroup1.addChild(node);
System.out.println("agregar_BH:agregamos node 3");                   
            }
            
            Alpha alpha = new Alpha(-1, datainputstream.readInt() * 1000);
            Transform3D transform3d = new Transform3D();            
            //para que roten los otros dos ejes
            switch(i)
            {
            case 1: 
                transform3d.rotZ(Math.PI/2.0);
                break;

            case 2: 
                transform3d.rotX(Math.PI/2.0);
                break;
            }
                                    
            RotationInterpolator rotationinterpolator = new RotationInterpolator(alpha, transformgroup2, transform3d, 0.0F, (float) Math.PI*2.0f );//6.283185F=2*PI Rango:{0;2*PI}
            rotationinterpolator.setSchedulingBounds(bnds);
            transformgroup.addChild(rotationinterpolator);
            //vector_de_BH_rotacion.add(rotationinterpolator);
            
            rotationinterpolator.setEnable(b_start_Interpolator);
System.out.println("agregar_BH:agregamos rotationinterpolator");
            
            //vector_de_BH_rotacion.add(rotationinterpolator);   
            break;
        	}
        case TRASLACION:    
        	{
        	int i = datainputstream.readInt();
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
            
            switch(i)
            {
            case 0: 
                transform3d.rotZ(Math.PI/2.0);
                break;
                
            case 1: 
                transform3d.rotX(Math.PI/2.0);
                break;

            case 2: 
                transform3d.rotY(Math.PI/2.0);
                break;
            }
            
			PositionInterpolator translator = new PositionInterpolator(alpha,transformgroup2,transform3d,2.0f, 3.5f);
			translator.setSchedulingBounds(bnds);
			transformgroup.addChild(translator);
			//vector_de_BH_traslacion.add(translator);
            			
            translator.setEnable(b_start_Interpolator);
            
            
            break;
            }
         case TRASLACION_PUNTOS:
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
        		puntos[i1]=leerPoint3f(datainputstream);	
        	}        
        	
        	if(datainputstream.readInt()==0)//spline
        	{	
        		Alpha alpha = new Alpha(-1, datainputstream.readInt() * 1000);
        		Transform3D transform3d = new Transform3D();
         		
	  			KBKeyFrame[] splineKeyFrames = new KBKeyFrame[puntos.length];
      			float salto=(float)(1.0/puntos.length);
System.out.println("Datos_utiles:agregar_BH:TRASLACION_PUNTOS:salto= "+salto);      
     			for(int i1=0;i1<puntos.length-1;i1++)
      			{	      		
      				float head  = (float)((Math.pow(-1,i1))*Math.PI/2.0f*Math.cos(Math.PI/2*i1) );                          	
      				float pitch = (float)((Math.pow(-1,i1))*Math.PI/2.0f*Math.sin(Math.PI/2*i1) );                            	
      				float bank  = (float)((Math.pow(-1,i1))*Math.PI/2.0f*Math.cos(Math.PI/2*i1) );                           	
      				Point3f s = new Point3f(1.0f, 1.0f, 1.0f);   
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
      			splineInterpolator.setSchedulingBounds(bnds);      
      			transformgroup.addChild(splineInterpolator);      			
      			//vector_de_BH_spline.add(splineInterpolator);
      			
      			splineInterpolator.setEnable(b_start_Interpolator);
      			
      		//	
         	}
         	else //lineal
         	{	
        		Alpha alpha = new Alpha(-1, datainputstream.readInt() * 1000);
        		Transform3D transform3d = new Transform3D();
         		
	  			KBKeyFrame[] linearKeyFrames = new KBKeyFrame[puntos.length];
      			float salto=(float)(1.0/puntos.length);
System.out.println("Datos_utiles:agregar_BH:TRASLACION_PUNTOS:salto= "+salto);      
     			for(int i1=0;i1<puntos.length-1;i1++)
      			{	      		
      				float head  = 0.0f;                          	
      				float pitch = 0.0f;                          	
      				float bank  = 0.0f;;                          	
      				Point3f s = new Point3f(1.0f, 1.0f, 1.0f);   
      				linearKeyFrames[i1] = 
        			 new KBKeyFrame((float)(i1*salto), 1, puntos[i1], head, pitch, bank, s, 0.0f, 0.0f, 0.0f);     	
      			}
      			{
      	
      				float head  = 0;                          
      				float pitch = 0;                          
      				float bank  = 0;;                          
      				Point3f s = new Point3f(0.5f, 0.5f, 0.5f);   
      				linearKeyFrames[puntos.length-1] = 
         			new KBKeyFrame(1.0f, 1, puntos[puntos.length-1], head, pitch, bank, s, 0.0f, 0.0f, 0.0f);     	
      			}      	
      			KBRotPosScaleSplinePathInterpolator linearInterpolator =
         		new KBRotPosScaleSplinePathInterpolator(alpha, transformgroup2,
                                                  transform3d, linearKeyFrames); 
      			linearInterpolator.setSchedulingBounds(bnds);
      
      			transformgroup.addChild(linearInterpolator);
      			linearInterpolator.setEnable(b_start_Interpolator);
      			
      		}
         		
         	break;	
         	}          
            
        }
    }
	
	boolean b_start_Interpolator=true;
	
	
	//Figura_luz
    void agregarFigura_luz(DataInputStream datainputstream, TransformGroup transformgroup)
        throws IOException
    {
        Object obj = null;
        int i = datainputstream.readInt();
        Color3f color3f = getColor3f(datainputstream.readInt());
        TransformGroup transformgroup1 = leerTransformGroup(datainputstream);
        switch(i)
        {
        case DIRECCIONAL: 
            obj = new DirectionalLight();
            break;

        case PUNTUAL: 
            PointLight pointlight = new PointLight();
            obj = pointlight;
            pointlight.setAttenuation(leerPoint3f(datainputstream));
            break;

        case SPOT: 
            SpotLight spotlight = new SpotLight();
            obj = spotlight;
            spotlight.setAttenuation(leerPoint3f(datainputstream));
            spotlight.setSpreadAngle(datainputstream.readFloat());
            spotlight.setConcentration(datainputstream.readFloat());
            spotlight.setDirection(leerVector3f(datainputstream));
            break;
        }
        ((SceneGraphObject) (obj)).setCapability(Light.ALLOW_COLOR_WRITE);
        ((Light) (obj)).setColor(color3f);
        ((Light) (obj)).setInfluencingBounds(bnds);
        transformgroup1.addChild(((Node) (obj)));
        transformgroup.addChild(transformgroup1);
    }
	
	//Datos_utiles
    void agregar_Primitiva(DataInputStream datainputstream, TransformGroup transformgroup, Node node, Appearance appearance)
        throws IOException
    {
        
System.out.println("agregar_Primitiva:");        
        TransformGroup transformgroup1 = leerTransformGroup(datainputstream);
        Material material = appearance.getMaterial();
        int i;
        i= datainputstream.readInt();
System.out.println("agregar_Primitiva:i="+i);         
        if( i>= 0)
        {
            float f = (float)i / 100F;
            appearance.setTransparencyAttributes(new TransparencyAttributes(2, f));
        }
        i= datainputstream.readInt();
System.out.println("agregar_Primitiva:i="+i);        
        if( i>= 0)
            material.setShininess(i);
        i= datainputstream.readInt();
System.out.println("agregar_Primitiva:i="+i);    
        if( i!= 0)
            material.setDiffuseColor(getColor3f(i));
		i= datainputstream.readInt();
System.out.println("agregar_Primitiva:i="+i);            
        if( i!= 0)
            material.setSpecularColor(getColor3f(i));
		i= datainputstream.readInt();
System.out.println("agregar_Primitiva:i="+i);            
        if( i!= 0)
            material.setEmissiveColor(getColor3f(i));
        i= datainputstream.readInt();
System.out.println("agregar_Primitiva:i="+i);    
        if( i!= 0)
            material.setAmbientColor(getColor3f(i));
            
        String s = datainputstream.readUTF();
System.out.println("agregar_Primitiva:s="+s);        
        if(!s.equals(""))
        {
            BufferedImage bufferedimage = getBufferedImage(s);
System.out.println("agregar_Primitiva:bufferedimage="+bufferedimage);             
            TextureLoader textureloader = new TextureLoader(bufferedimage);
            javax.media.j3d.Texture texture = textureloader.getTexture();
            TextureAttributes textureattributes = new TextureAttributes();
            
            int inn=datainputstream.readInt();
System.out.println("agregar_Primitiva:inn="+inn);            
            textureattributes.setTextureMode(TEXTURAS[ inn- 1]);
            appearance.setTexture(texture);
            appearance.setTextureAttributes(textureattributes);
        }
    
System.out.println("agregar_Primitiva:agregar_BH");         
        agregar_BH(datainputstream, transformgroup, transformgroup1, node);
    }
    
    
	//Escena
    void agregarComponente(DataInputStream datainputstream, int i, TransformGroup transformgroup)
        throws IOException
    {
        Appearance appearance = new Appearance();
        appearance.setMaterial(new Material());
        
                
        switch(i)
        {
        default:
            break;

        case ESFERA: 
            Sphere sphere = new Sphere(datainputstream.readFloat(), Sphere.ALLOW_BOUNDS_READ, datainputstream.readInt(), appearance);
            agregar_Primitiva(datainputstream, transformgroup, sphere, appearance);
System.out.println("Graficador3D_applet.agregarComponente.switch.i= "+i+" SPHERE");
            break;

        case CUBO: 
            float f = datainputstream.readFloat();
            Box box = new Box(f, f, f, Box.ALLOW_BOUNDS_READ, appearance);
            agregar_Primitiva(datainputstream, transformgroup, box, appearance);
System.out.println("Graficador3D_applet.agregarComponente.switch.i= "+i+" CUBE");
            break;

        case CAJA:
            Box box1 = new Box(datainputstream.readFloat(), datainputstream.readFloat(), datainputstream.readFloat(), Box.ALLOW_BOUNDS_READ, appearance);
            agregar_Primitiva(datainputstream, transformgroup, box1, appearance);
System.out.println("Graficador3D_applet.agregarComponente.switch.i= "+i+" RECTAGLE");
            break;

        case CONO: 
            Cone cone = new Cone(datainputstream.readFloat(), datainputstream.readFloat(), Cone.ALLOW_BOUNDS_READ, datainputstream.readInt(), datainputstream.readInt(), appearance);
            agregar_Primitiva(datainputstream, transformgroup, cone, appearance);
System.out.println("Graficador3D_applet.agregarComponente.switch.i= "+i+" CONE");
            break;

        case CILINDRO: 
            Cylinder cylinder = new Cylinder(datainputstream.readFloat(), datainputstream.readFloat(), Cylinder.ALLOW_BOUNDS_READ, datainputstream.readInt(), datainputstream.readInt(), appearance);
            agregar_Primitiva(datainputstream, transformgroup, cylinder, appearance);
System.out.println("Graficador3D_applet.agregarComponente.switch.i= "+i+" CYLINDER");
            break;

        case TEXTO_3D:
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
System.out.println("Graficador3D_applet.agregarComponente.switch.i= "+i+" TEXT3D");
            break;

        case TEXTO_2D: 
            TransformGroup transformgroup1 = leerTransformGroup(datainputstream);
            Text2D text2d = new Text2D(datainputstream.readUTF(), getColor3f(datainputstream.readInt()), datainputstream.readUTF(), datainputstream.readInt(), datainputstream.readInt());
            if(datainputstream.readInt() == 1)
            {
                PolygonAttributes polygonattributes = new PolygonAttributes();
                polygonattributes.setCullFace(PolygonAttributes.CULL_NONE);
                polygonattributes.setBackFaceNormalFlip(true);
                text2d.getAppearance().setPolygonAttributes(polygonattributes);
            }
            agregar_BH(datainputstream, transformgroup, transformgroup1, text2d);
             break;
            
        case TETRAEDRO:
System.out.println("Graficador3D_applet:agregarComponente:tetraedro" );        
        	Shape3D tetraedro = createTetraedro(datainputstream.readFloat(), appearance);
            agregar_Primitiva(datainputstream, transformgroup, tetraedro, appearance);
System.out.println("Graficador3D_applet:agregado:tetraedro" );              
            break;
        	       
           
          
    	}
    }

	//Figura_Tetra
	public Shape3D createTetraedro(float lado_, Appearance appearance)
	{
	Shape3D s3d_tetra_=new Shape3D();	
		
	int i;   
    
    float sqrt3 = (float) Math.sqrt(3.0)/2*lado_;
    float sqrt3_3 = sqrt3 / 3.0f;
    float sqrt24_3 = (float) Math.sqrt( 3*2*Math.pow(lado_,2) ) / 3.0f;

    float ycenter = 0.5f * sqrt24_3;
    float zcenter = -sqrt3_3;

    Point3f p1 = new Point3f(-(float)(lado_/2), -ycenter, -zcenter);
    Point3f p2 = new Point3f((float)(lado_/2), -ycenter, -zcenter);
    Point3f p3 = new Point3f(0.0f, -ycenter, -sqrt3 - zcenter);
    Point3f p4 = new Point3f(0.0f, sqrt24_3 - ycenter, 0.0f);

    Point3f[] verts = {		p1, p2, p4,	// front face
							p1, p4, p3,	// left, back face
							p2, p3, p4,	// right, back face
							p1, p3, p2,	// bottom face
    				  };

    TexCoord2f texCoord[] = {       new TexCoord2f(0.0f, 0.0f),
									new TexCoord2f(1.0f, 0.0f),
        							new TexCoord2f(0.5f, sqrt3 / 2.0f),
    						};
    
    TriangleArray tetra = new TriangleArray(12, TriangleArray.COORDINATES |
		TriangleArray.NORMALS | TriangleArray.TEXTURE_COORDINATE_2);

	tetra.setCoordinates(0, verts);
        for (i = 0; i < 12; i++)
        {
            tetra.setTextureCoordinate(0, i, texCoord[i%3]);
        }

	int face;
	Vector3f normal = new Vector3f();
	Vector3f v1 = new Vector3f();
	Vector3f v2 = new Vector3f();
	Point3f [] pts = new Point3f[3];
	for (i = 0; i < 3; i++) pts[i] = new Point3f();

	for (face = 0; face < 4; face++) {
	    tetra.getCoordinates(face*3, pts);
	    v1.sub(pts[1], pts[0]);
	    v2.sub(pts[2], pts[0]);
	    normal.cross(v1, v2);
	    normal.normalize();
	    for (i = 0; i < 3; i++) {
		tetra.setNormal((face * 3 + i), normal);
	    }
	}

	tetra.setCapability(Geometry.ALLOW_INTERSECT);
	
	s3d_tetra_.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
			
	s3d_tetra_.setGeometry(tetra);
	
	s3d_tetra_.setAppearance(appearance);
	
	return s3d_tetra_;	
	}
	
	//Datos_utiles
    BufferedImage getBufferedImage(String s)
    {
        File file = new File(s);
        String s1 = file.getName();
        if(buf == null)
        {
            buf = new byte[512];
            baos = new ByteArrayOutputStream(512);
        } else
        {
            baos.reset();
        }
        if(tracker == null)
            tracker = new MediaTracker(this);
        BufferedImage bufferedimage = null;
        Image image = null;
        try
        {
            InputStream inputstream = getClass().getResourceAsStream(s1);
            int i;
            while((i = inputstream.read(buf, 0, 512)) > -1) 
                baos.write(buf, 0, i);
            inputstream.close();
            image = Toolkit.getDefaultToolkit().createImage(baos.toByteArray());
            tracker.addImage(image, 0);
            tracker.checkID(0, true);
            tracker.waitForAll();
        }
        catch(Exception _ex)
        {
            return null;
        }
        bufferedimage = new BufferedImage(image.getWidth(null), image.getHeight(null), 1);
        java.awt.Graphics2D graphics2d = bufferedimage.createGraphics();
        graphics2d.drawImage(image, 0, 0, null);
        return bufferedimage;
    }
	//Datos_utiles
    Canvas3D getCanvas(DataInputStream datainputstream)
        throws IOException
    {
        
int dim_1=datainputstream.readInt();
System.out.println("Graficador_Applet:getCanvas:dim_1="+dim_1);
int dim_2=datainputstream.readInt();
System.out.println("Graficador_Applet:getCanvas:dim_2="+dim_2);     
        Dimension dimension = new Dimension(dim_1, dim_2);
        
        bnds = new BoundingSphere(new Point3d(0.0D, 0.0D, 0.0D), 200);
        java.awt.GraphicsConfiguration graphicsconfiguration = SimpleUniverse.getPreferredConfiguration();
        
        
        Canvas3D canvas3d = new Canvas3D(graphicsconfiguration);
        SimpleUniverse simpleuniverse = new SimpleUniverse(canvas3d);
        simpleuniverse.getViewingPlatform().setNominalViewingTransform();
        
        BranchGroup branchgroup = new BranchGroup();
        branchgroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);//para poder dar movimiento a la escena
        
        TransformGroup transformgroup = new TransformGroup();   
        
        Transform3D transform3d = new Transform3D();
//        float f1=datainputstream.readFloat();
//System.out.println("Graficador_Applet:getCanvas:f1="+f1);
//        float f2=datainputstream.readFloat();
//System.out.println("Graficador_Applet:getCanvas:f2="+f2);
//        float f3=datainputstream.readFloat();
//System.out.println("Graficador_Applet:getCanvas:f3="+f3);        
        
//        Vector3d v=new Vector3d(f1,f2,f3);
        transform3d.setScale( leerVector3d(datainputstream) );
//transform3d.setScale( v );
        transformgroup.setTransform(transform3d);        
                
        branchgroup.addChild(transformgroup);
        
		//manejo de teclado	
		transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		M_KAction keyNav = new M_KAction(transformgroup, 1.0f, 5.0f);
        M_KAction_funcionamiento keyNavBeh = new M_KAction_funcionamiento(keyNav);
        keyNavBeh.setSchedulingBounds(bnds);
        transformgroup.addChild(keyNavBeh);
		  
        
        //////**********************************************************////////
               
        // Maneja el Teclado
      // los cambios afectan a toda la escena
//TransformGroup tg2 = simpleuniverse.getViewingPlatform().getViewPlatformTransform();
      // las teclas KeyNavigatorBehavior afecta al TG
//KeyNavigatorBehavior teclado = new KeyNavigatorBehavior(tg2);
//teclado.setSchedulingBounds(new BoundingSphere (new Point3d(0,0,0),200));
      
      // Añadimos el manejo de teclado a la escena
//branchgroup.addChild(teclado);

      //////**********************************************************////////
      //esto hace que sea tan pesado que la maquina no lo soporta
      
      //Manejo del Mouse
      TransformGroup tg3 = simpleuniverse.getViewingPlatform().getViewPlatformTransform();
      // El mouse afecta al TG
      MouseRotate mouse = new MouseRotate(tg3);
      mouse.setSchedulingBounds(new BoundingSphere (new Point3d(0,0,0),200));
      //System.out.println("----mouse.getXFactor()"+mouse.getXFactor() );//0.03
      //System.out.println("----mouse.getYFactor()"+mouse.getYFactor() );//0.03
      
      mouse.setFactor(0.001,0.001);
      //
      //Añadimos el manejo de mouse a la escena
      branchgroup.addChild(mouse);
        
      ////***************************************************/////////////////  
        
      	leerTodo(datainputstream, branchgroup, transformgroup, dimension);       
        
      	branchgroup.compile();        
      	simpleuniverse.addBranchGraph(branchgroup);
                
      	return canvas3d;
    }

    Color3f getColor3f(int i)
    {
        Color color = new Color(i);
        float f = color.getRed() != 0 ? (float)color.getRed() / 255F : 0.0F;
        float f1 = color.getGreen() != 0 ? (float)color.getGreen() / 255F : 0.0F;
        float f2 = color.getBlue() != 0 ? (float)color.getBlue() / 255F : 0.0F;
        return new Color3f(f, f1, f2);
    }

    void leerTodo(DataInputStream datainputstream, BranchGroup branchgroup, TransformGroup transformgroup, Dimension dimension)
        throws IOException
    {                
        int i = 0;
        i = datainputstream.readInt();
System.out.println("leerTodo:i="+i);

        if(i != 0)
        {
            Background background = new Background(getColor3f(i));
            background.setApplicationBounds(bnds);
            branchgroup.addChild(background);
        }
        i = datainputstream.readInt();
System.out.println("leerTodo:i="+i);
        if(i != 0)
        {
            BufferedImage bufferedimage = getBufferedImage(datainputstream.readUTF());
            Object obj = null;
            Background background1 = null;
            if(bufferedimage != null)
            {
                switch(i)
                {
                default:
                    break;

                case SENCILLA: 
                    TextureLoader textureloader = new TextureLoader(bufferedimage);
                    background1 = new Background(textureloader.getImage());
                    break;

                case ESCALADA: 
                    TextureLoader textureloader1 = new TextureLoader(bufferedimage);
                    background1 = new Background(textureloader1.getScaledImage(dimension.width, dimension.height));
                    break;

                case MOSAICO: 
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
                background1.setApplicationBounds(bnds);
                branchgroup.addChild(background1);
            }
        }
        i = datainputstream.readInt();
System.out.println("leerTodo:i="+i);

        if(i != 0)
        {
            AmbientLight ambientlight = new AmbientLight(getColor3f(i));
            ambientlight.setInfluencingBounds(bnds);
            branchgroup.addChild(ambientlight);
        }
        i = datainputstream.readInt();
System.out.println("leerTodo:i="+i);

        if(i != 0)
        {
            int j = datainputstream.readInt();
            float f = 0.0F;
            if(j > 0)
                f = (float)j / 100F;
            ExponentialFog exponentialfog = new ExponentialFog(getColor3f(i), f);
            exponentialfog.setInfluencingBounds(bnds);
            branchgroup.addChild(exponentialfog);
        }
        i = datainputstream.readInt();
System.out.println("leerTodo:i="+i);

        for(int k = 0; k < i; k++)
            agregarFigura_luz(datainputstream, transformgroup);

        i = datainputstream.readInt();
System.out.println("leerTodo:i="+i);

        for(int l = 0; l < i; l++)
        {
System.out.println("leerTodo:dataI...="+datainputstream.readUTF() );
System.out.println("leerTodo:data_i..="+datainputstream.readUTF() );
            agregarComponente(datainputstream, datainputstream.readInt(), transformgroup);
        }

    }

    Point3f leerPoint3f(DataInputStream datainputstream)
        throws IOException
    {
        return new Point3f(datainputstream.readFloat(), datainputstream.readFloat(), datainputstream.readFloat());
    }
	//Datos_utiles
    TransformGroup leerTransformGroup(DataInputStream datainputstream)
        throws IOException
    {
        TransformGroup transformgroup = new TransformGroup();
        float af[] = new float[16];
        for(int i = 0; i < 16; i++)
            af[i] = datainputstream.readFloat();

        Transform3D transform3d = new Transform3D(af);
        transformgroup.setTransform(transform3d);
        return transformgroup;
    }

    Vector3f leerVector3f(DataInputStream datainputstream)
        throws IOException
    {
        return new Vector3f(datainputstream.readFloat(), datainputstream.readFloat(), datainputstream.readFloat());
    }

	Vector3d leerVector3d(DataInputStream datainputstream)
        throws IOException
    {
        return new Vector3d(datainputstream.readFloat(), datainputstream.readFloat(), datainputstream.readFloat());
    }
    
    void mostrarMsg(String s)
    {
        Graphics g = getGraphics();
        g.drawString(s, 3, 14);
        g.dispose();
    }

    public void start()
    {
        setLayout(new BorderLayout());
        String s;
        
        try
        {        
        s= getParameter("datos") + ".t";
    	}
////////////////////////////////////////////////////////////////////////////////    	
        catch(Exception exception)
        {
        s="A61487136046.t";//"A61094023562"+".t";//A61094023562//A61083573531
    	}
////////////////////////////////////////////////////////////////////////////////        
        try
        {
            DataInputStream datainputstream = new DataInputStream(getClass().getResourceAsStream(s));
            
System.out.println("start:datainputstream=" + datainputstream.readInt() );

System.out.println("start:=Cylinder.ALLOW_AUTO_COMPUTE_BOUNDS_READ " + Cylinder.ALLOW_AUTO_COMPUTE_BOUNDS_READ );                        
System.out.println("start:=Cylinder.ENABLE_APPEARANCE_MODIFY " + Cylinder.ENABLE_APPEARANCE_MODIFY );            
System.out.println("start:=Cylinder.ENABLE_COLLISION_REPORTING " + Cylinder.ENABLE_COLLISION_REPORTING );            
System.out.println("start:=Cylinder.GENERATE_NORMALS " + Cylinder.GENERATE_NORMALS );
System.out.println("start:=Cylinder.ENABLE_GEOMETRY_PICKING " + Cylinder.ENABLE_GEOMETRY_PICKING );            
System.out.println("start:=Cylinder.ENABLE_PICK_REPORTING " + Cylinder.ENABLE_PICK_REPORTING );            
System.out.println("start:=Cylinder.GENERATE_NORMALS_INWARD " + Cylinder.GENERATE_NORMALS_INWARD );            
System.out.println("start:=Cylinder.GENERATE_TEXTURE_COORDS " + Cylinder.GENERATE_TEXTURE_COORDS );            
System.out.println("start:=Cylinder.GEOMETRY_NOT_SHARED " + Cylinder.GEOMETRY_NOT_SHARED );
System.out.println("start:=Cylinder.BODY " + Cylinder.BODY );
System.out.println("start:=Cylinder.BOTTOM " + Cylinder.BOTTOM );
System.out.println("start:=Cylinder.TOP " + Cylinder.TOP );
                      
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_AUTO_COMPUTE_BOUNDS_WRITE ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_BOUNDS_READ ); //3
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_BOUNDS_WRITE ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_CHILDREN_EXTEND ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_CHILDREN_READ ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_CHILDREN_WRITE ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_COLLIDABLE_READ ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_COLLIDABLE_READ ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_COLLIDABLE_WRITE ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_COLLISION_BOUNDS_READ ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_COLLISION_BOUNDS_WRITE ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_COLLISION_BOUNDS_WRITE ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_LOCAL_TO_VWORLD_READ ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_PICKABLE_READ ); 
System.out.println("start:=Cylinder. " + Cylinder.ALLOW_PICKABLE_WRITE ); 
System.out.println("start:=Cylinder. " + Cylinder.BODY ); 
System.out.println("start:=Cylinder. " + Cylinder.BOTTOM );
                  





            
            Canvas3D c3d=getCanvas(datainputstream);
System.out.println( "c3d="+c3d.getSize().getHeight() );
            add( c3d , "Center" );
System.out.println("close()"); 
            datainputstream.close();
            
            
            //add("South",paneles());
        }
        catch(Exception exception)
        {            
  System.out.println("start:Error: " + exception);         
        }        

    }
	
    public void stop()
    {
        removeAll();
        System.gc();
    }
    public static void main(String[] args)
    {
      
      Graficador3D_applet ga=new Graficador3D_applet();
      Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
      int an=dimension.width;
      int al=dimension.height;
      ga.start();
      MainFrame mf= new MainFrame(ga, an-400, al-300);
      mf.setLocation((int)( an/2-mf.getWidth()/2 ), (int)( al/2-mf.getHeight()/2 ) );
      
    }
    
    
    //Canvas3D c3d;
    public void actionPerformed(ActionEvent e)
    {
    	/*if(e.getSource().equals(start) )
    	{ 
    		b_start_Interpolator=true;
    		//iniciar_interpolators();
    		//start();
    	}
    	if(e.getSource().equals(stop) )    	
    	{ 
    		b_start_Interpolator=false;
    		//iniciar_interpolators();
    		//remove(c3d);
    		//c3d=null;
    		//stop();
    		//start();
    		        	
        	//System.gc();        	
    	}*/  
    }
    
    /*public void iniciar_interpolators()
    {
    	try
    	{
    	
    		int i=0;
    		if(vector_de_BH_spline.size()!=0)
    		{
    			while(i<vector_de_BH_spline.size())
    			{
    				( (KBRotPosScaleSplinePathInterpolator)vector_de_BH_spline.get(i) ).setEnable(b_start_Interpolator);	
    				i++;
    			};
    		}
    		
    			i=0;
    		if(vector_de_BH_traslacion.size()!=0)
    		{    		
    			while(i<vector_de_BH_traslacion.size())
    			{
    				( (PositionInterpolator)vector_de_BH_traslacion.get(i) ).setEnable(b_start_Interpolator);	
    			}
    		}
    			i=0;
    		if(vector_de_BH_rotacion.size()!=0)
    		{
    			while(i<vector_de_BH_rotacion.size())    
    			{
    				( (RotationInterpolator)vector_de_BH_rotacion.get(i) ).setEnable(b_start_Interpolator);	
    				i++;
    			}	
    		}
    	} 
    	catch(Exception e)
    	{
    		System.out.println("Error "+e);
    	}
    }*/
        
}