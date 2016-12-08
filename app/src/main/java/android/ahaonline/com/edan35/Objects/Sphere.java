package android.ahaonline.com.edan35.Objects;

import android.ahaonline.com.edan35.Constants;
import android.ahaonline.com.edan35.data.VertexBuffer;
import android.ahaonline.com.edan35.programs.TextureShaderProgram;
import android.content.Context;
import static android.opengl.GLES30.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import static android.ahaonline.com.edan35.Constants.COORDS_PER_VERTEX;



/**
 * Created by felix on 22/11/2016.
 * Test class - texture coordinates does not work properly!
 */
public class Sphere extends transformController {

    private float[] vertices;
    private float[] normals;
    private float[] texcoords;
    private int[] indices;

    private VertexBuffer vertexBuffer;
    private VertexBuffer vertexBufferColor;
    private VertexBuffer vertexBufferTexture;

    private Context context;

    private int itemSize;
    private int numItems;

    private final int vertexCount;
    private final int buffers[];

    private IntBuffer indexArray;
    //private final IndexBuffer indexArray;


    public Sphere(float radius, int latitudeBands, int longitudeBands, Context context) {

        vertices = new float[((latitudeBands + 1) * (longitudeBands + 1) * 3 * 2)];
        normals = new float[((latitudeBands + 1) * (longitudeBands + 1) * 3)];
        texcoords = new float[((latitudeBands + 1) * (longitudeBands + 1) * 2)];

        vertexCount = vertices.length / COORDS_PER_VERTEX;

        int indexT = 0;
        int indexV = 0;
        int indexN = 0;

        for (int latNumber  = 0; latNumber  <= latitudeBands; latNumber ++) {

            float theta = (float) (latNumber * Math.PI / latitudeBands);
            float sinTheta = (float) Math.sin(theta);
            float cosTheta = (float) Math.cos(theta);

            for (int longNumber = 0; longNumber <= longitudeBands; longNumber++) {
                float phi = (float) (longNumber * 2 * Math.PI / longitudeBands);
                float sinPhi = (float) Math.sin(phi);
                float cosPhi = (float) Math.cos(phi);

                float x = cosPhi * sinTheta;
                float y = cosTheta;
                float z = sinPhi * sinTheta;
                float u = (longNumber / longitudeBands);
                float v = 1.0f - (latNumber / latitudeBands);

                normals[indexN++] = x;
                normals[indexN++] = y;
                normals[indexN++] = z;

                vertices[indexV++] = radius * x;
                vertices[indexV++] = radius * y;
                vertices[indexV++] = radius * z;

                texcoords[indexT++] = u;
                texcoords[indexT++] = v;





            }
        }

        indices = new int[((latitudeBands + 1) * (longitudeBands + 1) * 6)];
        int indexI = 0;
        for (int latNumber = 0; latNumber <= latitudeBands ; latNumber++) {
            for (int longNumber = 0; longNumber <= longitudeBands - 1 ; longNumber++) {

                short first = (short)((latNumber * (longitudeBands + 1)) + longNumber);
                short second = (short)(first + longitudeBands + 1);

                indices[indexI++] = first;
                indices[indexI++] = second;
                indices[indexI++] = first + 1;

                indices[indexI++] = second;
                indices[indexI++] = second + 1;
                indices[indexI++] = first + 1;

            }
        }

        this.context = context;

        vertexBuffer = new VertexBuffer(vertices);
        vertexBufferColor = new VertexBuffer(vertices);
        vertexBufferTexture = new VertexBuffer(texcoords);

       buffers= new int[1];
        glGenBuffers(buffers.length, buffers, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffers[0]);

        indexArray = ByteBuffer
                .allocateDirect(indices.length * 32 )
                .order(ByteOrder.nativeOrder())
                .asIntBuffer()
                .put(indices);
        indexArray.position(0);

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexArray.capacity()
                * Constants.BYTES_PER_SHORT, indexArray, GL_STREAM_DRAW);


        itemSize = 1;
        numItems = indices.length;

        System.out.println(vertices.length/3);
        System.out.println(texcoords.length/2);

        //indexArray = IntBuffer.allocate(indices.length).put(indices);

        //indexArray.position(0);

       /* short inShort[] = new short[indices.length];

        for(int i = 0; i < indices.length; i++)
        {
            inShort[i] = (short)indices[i];
        }

        indexArray = new IndexBuffer(inShort);*/
        //indexArray.numItems = indices.length;


    }

   /* public void bindShader(ShaderTestProgram shaderTestProgram) {
        vertexBuffer.setVertexAttribPointer(0,
                shaderTestProgram.getPositionAttributeLocation(),
                COORDS_PER_VERTEX, 0);

        vertexBufferColor.setVertexAttribPointer(0,
                shaderTestProgram.getColorAttributeLocation(),
                COORDS_PER_VERTEX, 0);


    }*/

    public void bindShader(TextureShaderProgram shaderTestProgram) {
        vertexBuffer.setVertexAttribPointer(0,
                shaderTestProgram.getPositionAttributeLocation(),
                3, 0);

        vertexBufferTexture.setVertexAttribPointer(0,
                shaderTestProgram.getTextureCoordinatesAttributeLocation(),
                2, 0);


    }

    public void draw() {

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffers[0]);

        glDrawElements(GL_TRIANGLES, numItems, GL_UNSIGNED_INT, 0);
    }


}

