package tech.pacia.tonx2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

enum Direction {LEFT, RIGHT}

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private Texture image;
    private float myX = 0;
    private Direction direction = Direction.RIGHT;

    PolygonSprite firstPolygonSprite;
    PolygonSprite secondPolygonSprite;
    PolygonSpriteBatch polygonSpriteBatch;

    class MyInputProcessor extends InputAdapter {


        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            System.out.println("screenX + \", screenY = \" + screenY = " + screenX + ", screenY = " + screenY);
            float x = screenX - (secondPolygonSprite.getBoundingRectangle().width / 2);
            float y = Gdx.graphics.getHeight() - 1 - screenY - (secondPolygonSprite.getBoundingRectangle().height / 2);
            secondPolygonSprite.setPosition(x, y);
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            float x = screenX - (secondPolygonSprite.getBoundingRectangle().width / 2);
            float y = Gdx.graphics.getHeight() - 1 - screenY - (secondPolygonSprite.getBoundingRectangle().height / 2);
            secondPolygonSprite.setPosition(x, y);
            return false;
        }
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        image = new Texture("libgdx.png");

        polygonSpriteBatch = new PolygonSpriteBatch();
        {
            // Creating the color filling (but textures would work the same way)
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(0xDEADBEFF); // DE is red, AD is green, and BE is blue.
            pixmap.fill();
            Texture textureSolid = new Texture(pixmap);
            PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(textureSolid), new float[]{      // Four vertices
                0, 0,            // Vertex 0         3--2
                100, 0,          // Vertex 1         | /|
                100, 100,        // Vertex 2         |/ |
                0, 100           // Vertex 3         0--1
            }, new short[]{0, 1, 2,         // Two triangles using vertex indices.
                0, 2, 3          // Take care of the counter-clockwise direction.
            });
            firstPolygonSprite = new PolygonSprite(polygonRegion);
            firstPolygonSprite.setOrigin(25, 25);
        }

        {
            // Creating the color filling (but textures would work the same way)
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(0xDEADBEFF); // DE is red, AD is green, and BE is blue.
            pixmap.fill();
            Texture textureSolid = new Texture(pixmap);
            PolygonRegion polygonRegion = new PolygonRegion(new TextureRegion(textureSolid), new float[]{      // Four vertices
                0, 0,            // Vertex 0         3--2
                100, 0,          // Vertex 1         | /|
                100, 100,        // Vertex 2         |/ |
                0, 100           // Vertex 3         0--1
            }, new short[]{0, 1, 2,         // Two triangles using vertex indices.
                0, 2, 3          // Take care of the counter-clockwise direction.
            });
            secondPolygonSprite = new PolygonSprite(polygonRegion);
            // secondPolygonSprite.setOrigin(0, 0);
        }

        Gdx.input.setInputProcessor(new MyInputProcessor());
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        if (myX >= (Gdx.graphics.getWidth() - image.getWidth())) {
            direction = Direction.LEFT;
        } else if (myX <= 0) {
            direction = Direction.RIGHT;
        }

        if (direction == Direction.RIGHT) {
            myX += 1;
        } else {
            myX -= 1;
        }

        spriteBatch.begin();
        spriteBatch.draw(image, myX, 210);
        spriteBatch.end();

        polygonSpriteBatch.begin();
        firstPolygonSprite.draw(polygonSpriteBatch);
        secondPolygonSprite.draw(polygonSpriteBatch);
        polygonSpriteBatch.end();
        firstPolygonSprite.rotate(1.1f);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        polygonSpriteBatch.dispose();
        image.dispose();
    }
}
