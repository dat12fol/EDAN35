package android.ahaonline.com.edan35.Objects;


import android.ahaonline.com.edan35.data.VertexBuffer;
import android.ahaonline.com.edan35.programs.ShaderLightProgram;

import static android.opengl.GLES30.*;

import static android.ahaonline.com.edan35.util.Constants.COORDS_PER_VERTEX;


/**
 * Created by Felix on 2016-12-04.
 */

public class Light extends transformController {

    private VertexBuffer vertexBuffer;
    private ShaderLightProgram shader;

    public Light() {
        super();
        vertexBuffer = new VertexBuffer(lightCoords);
    }

    static float lightCoords[] = {
            -1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f
    };

    public void bindShader(ShaderLightProgram shaderLightProgram) {
        this.shader = shaderLightProgram;
        vertexBuffer.setVertAttrib(shaderLightProgram.getPositionAttributeLocation(), COORDS_PER_VERTEX, 0, 0
        );
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, 36);
        glDisableVertexAttribArray(shader.getPositionAttributeLocation());
    }
}
