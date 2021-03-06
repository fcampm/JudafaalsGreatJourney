package mx.itesm.jgj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static mx.itesm.jgj.MenuJudafaals.ALTO;
import static mx.itesm.jgj.MenuJudafaals.ANCHO;


class MenuNiveles extends Pantalla {


    private JudafaalsGreatAdventure jga;

    // Texturas
    private Texture fondoMenuNiveles;
    private Texture naveFondo;
    private Texture texturaPrimerNivel;
    private Texture texturaSegundoNivel;
    private Texture texturaTecerNivel;
    private Texture texturaBack;
    private TextureRegionDrawable imagenPrimerNivel;
    private TextureRegionDrawable imagenSegundoNivel;
    private TextureRegionDrawable imagenTecerNivel;
    private TextureRegionDrawable imagenBack;

    private AssetManager assetManager;

    // Escenas
    private Stage escenaMenuNivel;

    // Preferencias de los niveles.
    private Preferences levelPreferences= Gdx.app.getPreferences("usersPreferences");

    public MenuNiveles(JudafaalsGreatAdventure judafaalsGreatAdventure) {
        this.jga = judafaalsGreatAdventure;
        assetManager = jga.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearMenu();
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(escenaMenuNivel);
    }

    private void crearMenu() {
        escenaMenuNivel = new Stage(vista);

        // Botón primer nivel.
        ImageButton btnPrimerNivel = new ImageButton(imagenPrimerNivel);
        btnPrimerNivel.setPosition(250, ALTO / 3);
        btnPrimerNivel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                jga.setScreen(new PantallaCargando(jga, Pantalla.PRIMERNIVEL));
            }
        });
        escenaMenuNivel.addActor(btnPrimerNivel);

        // Botón nivel dos.
        ImageButton btnSegundoNivel = new ImageButton(imagenSegundoNivel);
        btnSegundoNivel.setPosition(ANCHO / 2 - btnSegundoNivel.getWidth() / 2, ALTO / 2 - (btnSegundoNivel.getHeight() * 2));
        btnSegundoNivel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(levelPreferences.getBoolean("firstLevelPassed")) {
                    jga.setScreen(new PantallaCargando(jga, Pantalla.SEGUNDONIVEL));
                }
            }
        });
        escenaMenuNivel.addActor(btnSegundoNivel);

        // Botón nivel tres.
        ImageButton btnTercerNivel = new ImageButton(imagenTecerNivel);
        btnTercerNivel.setPosition(850, ALTO / 2 - (btnTercerNivel.getHeight() * 3));
        btnTercerNivel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(levelPreferences.getBoolean("secondLevelPassed")) {
                    jga.setScreen(new PantallaCargando(jga, Pantalla.TERCERNIVEL));
                }
            }
        });
        escenaMenuNivel.addActor(btnTercerNivel);

        // Botón home que va al menú principal.
        ImageButton btnBack = new ImageButton(imagenBack);
        btnBack.setPosition(0, ALTO - btnBack.getHeight());
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                jga.setScreen(new PantallaCargando(jga, Pantalla.MENU));
            }
        });
        escenaMenuNivel.addActor(btnBack);
    }

    private void cargarTexturas() {
        fondoMenuNiveles = assetManager.get("Fondos/Pantalla principal.jpg");
        naveFondo = assetManager.get("Naveinicio.png");
        texturaPrimerNivel = assetManager.get("Botones/BotonNivelUno.png");
        if(levelPreferences.getBoolean("firstLevelPassed")) {
            texturaSegundoNivel = assetManager.get("Botones/BotonNivelDos.png");
        }
        else{
            texturaSegundoNivel = assetManager.get("Botones/NoBotonNivelDos.png");
        }
        if(levelPreferences.getBoolean("secondLevelPassed")){
            texturaTecerNivel = assetManager.get("Botones/BotonNivelTres.png");
        }
        else{
            texturaTecerNivel = assetManager.get("Botones/NoBotonNivelTres.png");
        }
        texturaBack = assetManager.get("Botones/FlechaAtras.png");
        imagenPrimerNivel = new TextureRegionDrawable(new TextureRegion(texturaPrimerNivel));
        imagenSegundoNivel = new TextureRegionDrawable(new TextureRegion(texturaSegundoNivel));
        imagenTecerNivel = new TextureRegionDrawable(new TextureRegion(texturaTecerNivel));
        imagenBack = new TextureRegionDrawable(new TextureRegion(texturaBack));
    }

    @Override
    public void render(float delta) {
        borrarPantalla();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondoMenuNiveles, 0, 0);
        batch.draw(naveFondo, ANCHO / 2 - naveFondo.getWidth() / 2, ALTO / 2 - naveFondo.getHeight() / 2 - 174);
        batch.end();
        escenaMenuNivel.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            jga.setScreen(new PantallaCargando(jga, Pantalla.MENU));
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        escenaMenuNivel.dispose();
        batch.dispose();
        assetManager.unload("Fondos/Pantalla principal.jpg");
        assetManager.unload("Naveinicio.png");
        assetManager.unload("Botones/BotonNivelUno.png");
        assetManager.unload("Botones/BotonNivelDos.png");
        assetManager.unload("Botones/BotonNivelTres.png");
        assetManager.unload("Botones/FlechaAtras.png");
    }
}
