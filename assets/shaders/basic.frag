precision mediump float;

uniform vec3 u_LightPosition;

varying vec3 v_Position;
varying vec4 v_Color;
varying vec3 v_Normal;

void main()
{
    float distance = length(u_LightPosition - v_Position);
    vec3 lightVector = normalize(u_LightPosition - v_Position);

    float diffuse = max(dot(v_Normal, lightVector), 0.1);
    diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));

    gl_FragColor = v_Color * diffuse;
}