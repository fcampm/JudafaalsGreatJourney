package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.xml.soap.Text;

import mx.itesm.jgj.EstadoSalto;

/**
 * Created by jomai on 2/20/2018.
 */

class Personaje {
    private static final float G=99.81f;
    private static final float VY = 400;
    private Animation animacion;
    private float x,y;
    private float timerAnimacion;

    private EstadoSalto estadoSalto;
    private final float MAX_ALTURA=150;
    private float yOriginal;

    public Personaje(Texture textura){
        TextureRegion region=new TextureRegion(textura);
        TextureRegion[][] frames=region.split(50,66);
        animacion=new Animation(0.2f,frames[0][3],frames[0][2],frames[0][1]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        x=Pantalla.ANCHO/5;
        y=Pantalla.ALTO/2;
        estadoSalto=EstadoSalto.EN_PISO;
    }
    public void render(SpriteBatch batch){
        timerAnimacion+= Gdx.graphics.getDeltaTime();
        TextureRegion frame=(TextureRegion) animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame,x,y);
    }
    public void actualizar(float delta){

        if(estadoSalto==EstadoSalto.SUBIENDO){
            setY(y+VY*delta-0.5f*G*delta*delta);
            if(y>=yOriginal+MAX_ALTURA){
                estadoSalto=EstadoSalto.BAJANDO;

            }}
        else if(estadoSalto==EstadoSalto.BAJANDO){
            setY(y-VY*delta);
            if(y<=yOriginal){
                y=yOriginal;
                estadoSalto=EstadoSalto.EN_PISO;
            }
        }
    }

    public void setY(float y){
        this.y=y;
    }
    public void saltar(){
        estadoSalto=EstadoSalto.SUBIENDO;
        yOriginal=y;
    }
    public float getX(){
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return ((TextureRegion) animacion.getKeyFrame(0)).getRegionWidth();
    }

    public float getHeight() {
        return ((TextureRegion) animacion.getKeyFrame(0)).getRegionHeight();
    }
}
