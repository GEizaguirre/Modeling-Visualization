package cat.urv.miv.mivandroid3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

public class FontAtlas {

    private final int max_characters = 200;
    private XMLBitmapFontManager xbf_mng;
    private Context context;
    private List<FontData> font_info;
    private Square[] font_string = new Square [max_characters];
    private int image_width;
    private int image_height;
    private Map<Character, float[]> letter_coordinates;
    private Map<Character, FontData> letter_data;
    private int started_squares = 0;
    private Texture fonts_texture;
    private GL10 gl;


    public FontAtlas (Context context, GL10 gl, int text_id, int image_id){
        this.context=context;
        this.gl = gl;
        xbf_mng = new XMLBitmapFontManager(context);
        // Read coordinates' xml
        this.font_info = xbf_mng.parse(text_id);

        InputStream is = context.getResources().openRawResource(image_id);
        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        image_height = mBitmap.getHeight();
        image_width = mBitmap.getWidth();

        letter_coordinates = new HashMap<>();
        letter_data = new HashMap<>();

        // Save each character's coordinates
        for (FontData fd : font_info) {
            float coordinates[] = new float[]{
                    fd.getX() / (float) image_width, (fd.getY() + fd.getHeight()) / (float) image_height,
                    fd.getX() / (float) image_width, (fd.getY()) / (float) image_height,
                    (fd.getX() + fd.getWidth()) / (float) image_width, (fd.getY()) / (float) image_height,
                    (fd.getX() + fd.getWidth()) / (float) image_width, (fd.getY() + fd.getHeight()) / (float) image_height
            };
            letter_coordinates.put(fd.getCharacter(), coordinates);
            letter_data.put(fd.getCharacter(), fd);
        }
        fonts_texture = new Texture (gl, context, image_id);

    }

    public void drawString(String phrase, float scx, float scy){

        // Draw string character by character.
        if (phrase.length()>max_characters) System.out.println("Error writing String.");
        else {
            gl.glPushMatrix();
            gl.glScalef(scx, scy, 0);
            for (int i = 0; i < phrase.length(); i++) {
                if (i >= started_squares) {
                    started_squares++;
                    font_string[i] = new Square();
                }
                font_string[i].setTexture(fonts_texture, letter_coordinates.get(phrase.charAt(i)));
                gl.glTranslatef(2f, 0, 0);
                font_string[i].draw(gl);
            }
            gl.glPopMatrix();
        }

    }
}
