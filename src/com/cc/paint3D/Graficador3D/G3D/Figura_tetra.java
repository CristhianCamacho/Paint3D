
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
import javax.media.j3d.*;
import javax.vecmath.*;

import javax.swing.JPopupMenu;


public class Figura_tetra extends Figura
{
	float lado;	
	
	Appearance app;
	Shape3D s3d_tetra;
	
		
    public Figura_tetra()
    {
        lado=0.8f;        
    }

    public void agregar_al_Menu(JPopupMenu jpopupmenu)
    {
        jpopupmenu.add(new JMI_Ajustador_de_real(Props.HEIGHT, "lado"));        
    }

    public Node get_Node(Appearance appearance)
    {
    app=appearance;
    
    s3d_tetra=createTetraedro(lado ,app);   
    
    return s3d_tetra;            
    }
	
	public static Shape3D createTetraedro(float lado_, Appearance appearance)
	{
	Shape3D s3d_tetra_=new Shape3D();	
		
	int i;   
    
    float alto_parcial = (float) Math.sqrt(3.0)/2*lado_;
    float alto_parcial_3 = alto_parcial / 3.0f;
    float alto_total = (float) Math.sqrt( 3*2*Math.pow(lado_,2) ) / 3.0f;

    float ycenter = 0.5f * alto_total;
    float zcenter = alto_parcial_3;

    Point3f p1 = new Point3f(-(float)(lado_/2), -ycenter, +zcenter);
    Point3f p2 = new Point3f((float)(lado_/2), -ycenter, +zcenter);
    Point3f p3 = new Point3f(0.0f, -ycenter, -alto_parcial + zcenter);
    Point3f p4 = new Point3f(0.0f, alto_total - ycenter, 0.0f);

    Point3f[] verts = {		p1, p2, p4,	// front face
							p1, p4, p3,	// left, back face
							p2, p3, p4,	// right, back face
							p1, p3, p2,	// bottom face
    				  };

    TexCoord2f texCoord[] = {       new TexCoord2f(0.0f, 0.0f),
									new TexCoord2f(1.0f, 0.0f),
        							new TexCoord2f(0.5f, 1.0f),
        							
        							/*new TexCoord2f(1.0f, 1.0f),
									new TexCoord2f(0.0f, 1.0f),
        							new TexCoord2f(0.0f, 0.0f)*/
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
            
            s3d_tetra.setAppearance( (Appearance)obj );
            
            
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
        dataoutputstream.writeInt(TETRAEDRO);
        dataoutputstream.writeFloat(lado);
        
    }
    
}