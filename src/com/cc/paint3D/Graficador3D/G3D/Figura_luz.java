
package com.cc.paint3D.Graficador3D.G3D;

import com.cc.paint3D.Graficador3D.IO.Manejador_de_archivos_temporales;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import java.awt.Color;
import java.io.*;
import javax.media.j3d.*;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;


public class Figura_luz extends TransformGroup
{
	public static final int DIRECCIONAL = 0;
    public static final int SPOT = 1;
    public static final int PUNTUAL = 2;
    public BranchGroup bg;
    Color color;
    public Light light;
    Sphere esfera;
    int tipo;
    public int pos;
	
    public Figura_luz(int i, Color color1, DataInputStream datainputstream)
    {
        this(i, color1);
        try
        {
            Color_utiles.leer_Transform3D(datainputstream, this);
            switch(i)
            {
            case PUNTUAL: 
                ((PointLight)light).setAttenuation(Color_utiles.leer_Point3f(datainputstream));
                break;

            case SPOT: 
                SpotLight spotlight = (SpotLight)light;
                spotlight.setAttenuation(Color_utiles.leer_Point3f(datainputstream));
                spotlight.setSpreadAngle(datainputstream.readFloat());
                spotlight.setConcentration(datainputstream.readFloat());
                spotlight.setDirection(Color_utiles.leer_Vector3f(datainputstream));
                break;
            }
        }
        catch(Exception exception) { }
    }

    public Figura_luz(int i, Color color1)
    {
        tipo = i;
        color = color1;
        javax.vecmath.Color3f color3f = Manejador_de_archivos_temporales.getColor3f(color1);
        setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        setCapability(TransformGroup.ENABLE_PICK_REPORTING);
                        
        bg = new BranchGroup();
        bg.setCapability(BranchGroup.ALLOW_DETACH);
        Appearance appearance = new Appearance();
        if(i != 0)
        {
            ColoringAttributes coloringattributes = new ColoringAttributes(color3f, 2);
            appearance.setColoringAttributes(coloringattributes);
            appearance.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
            appearance.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
            TransparencyAttributes transparencyattributes = new TransparencyAttributes(TransparencyAttributes.ALLOW_MODE_READ, 0.0F);
            transparencyattributes.setCapability(TransparencyAttributes.SCREEN_DOOR);
            appearance.setTransparencyAttributes(transparencyattributes);
                        
            addChild(esfera = new Sphere(0.15F, Sphere.GENERATE_NORMALS, appearance));
        
        }
        bg.addChild(this);
        switch(i)
        {
        case DIRECCIONAL: 
            light = new DirectionalLight();
            break;

        case PUNTUAL: 
            PointLight pointlight = new PointLight();
            light = pointlight;
            pointlight.setCapability(PointLight.ALLOW_ATTENUATION_READ);
            pointlight.setCapability(PointLight.ALLOW_ATTENUATION_WRITE );
            break;

        case SPOT: 
            SpotLight spotlight = new SpotLight();
            light = spotlight;
            spotlight.setCapability(SpotLight.ALLOW_ATTENUATION_READ);
            spotlight.setCapability(SpotLight.ALLOW_ATTENUATION_WRITE);
            spotlight.setCapability(SpotLight.ALLOW_DIRECTION_READ);
            spotlight.setCapability(SpotLight.ALLOW_DIRECTION_WRITE);
            spotlight.setCapability(SpotLight.ALLOW_SPREAD_ANGLE_READ);
            spotlight.setCapability(SpotLight.ALLOW_SPREAD_ANGLE_WRITE);
            spotlight.setCapability(SpotLight.ALLOW_CONCENTRATION_READ);
            spotlight.setCapability(SpotLight.ALLOW_CONCENTRATION_WRITE);
            break;
        }
        light.setCapability(Light.ALLOW_COLOR_WRITE);
        light.setColor(color3f);
        light.setInfluencingBounds(Datos_utiles.boundingSphere);
        addChild(light);
    }

    public void escribir_Figura_luz(DataOutputStream dataoutputstream)
        throws IOException
    {
        dataoutputstream.writeInt(tipo);
        dataoutputstream.writeInt(color.getRGB());
        Color_utiles.escribir_Transform3D(dataoutputstream, this);
        switch(tipo)
        {
        case PUNTUAL: 
            PointLight pointlight = (PointLight)light;
            Point3f point3f = new Point3f();
            pointlight.getAttenuation(point3f);
            Color_utiles.escribir_Point3f(dataoutputstream, point3f);
            break;

        case SPOT: 
            SpotLight spotlight = (SpotLight)light;
            Point3f point3f1 = new Point3f();
            spotlight.getAttenuation(point3f1);
            Color_utiles.escribir_Point3f(dataoutputstream, point3f1);
            dataoutputstream.writeFloat(spotlight.getSpreadAngle());
            dataoutputstream.writeFloat(spotlight.getConcentration());
            Vector3f vector3f = new Vector3f();
            spotlight.getDirection(vector3f);
            Color_utiles.escribir_Vector3f(dataoutputstream, vector3f);
            break;
        }
    }

    public void encender(boolean on)
    {
        TransparencyAttributes transparencyattributes = esfera.getAppearance().getTransparencyAttributes();
        if(!on)
            transparencyattributes.setTransparency(1.0F);
        else
            transparencyattributes.setTransparency(0.0F);
    }

    public void addChild_a(TransformGroup transformgroup)
    {
        transformgroup.addChild(bg);
    }

    
}