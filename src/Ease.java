
public class Ease {

	public static float linear(float t, float b, float c, float d) {
		return (c*(t/d)) + b;
	}
	
	public static float quadIn(float t, float b, float c, float d) {
		t /= d;
		return (c*t*t) + b;
	}
	
	public static float quadOut(float t, float b, float c, float d) {
		t /= d;
		return ((-c) * (t*(t-2.0f))) + b;
	}
	
	public static float quadInOut(float t, float b, float c, float d) {
		t /= (d/2.0f);
		if (t < 1.0f) return ((c/2.0f)*t*t) + b;
		t--;
		return ((-c/2.0f) * (t*(t-2.0f) - 1.0f)) + b;
	}

	public static float cubeIn(float t, float b, float c, float d) {
		t /= d;
		return (c*t*t*t) + b;
	}

	public static float cubeOut(float t, float b, float c, float d) {
		t /= d;
		t--;
		return (c*((t*t*t) + 1.0f)) + b;
	}
	
	public static float cubeInOut(float t, float b, float c, float d) {
		t /= (d/2.0f);
		if (t < 1.0f) return ((c/2.0f)*t*t*t) + b;
		t -= 2.0f;
		return ((c/2.0f)*((t*t*t) + 2.0f)) + b;
	}
	
	public static float quartIn(float t, float b, float c, float d) {
		t /= d;
		return (c*t*t*t*t) + b;
	}
	
	public static float quartOut(float t, float b, float c, float d) {
		t /= d;
		t--;
		return ((-c) * ((t*t*t*t) - 1.0f)) + b;
	}
	
	public static float quartInOut(float t, float b, float c, float d) {
		t /= (d/2.0f);
		if (t < 1.0f) return ((c/2.0f)*t*t*t*t) + b;
		t -= 2.0f;
		return ((-c/2.0f) * ((t*t*t*t) - 2.0f)) + b;
	}
	
	public static float quintIn(float t, float b, float c, float d) {
		t /= d;
		return (c*t*t*t*t*t) + b;
	}
	
	public static float quintOut(float t, float b, float c, float d) {
		t /= d;
		t--;
		return (c*((t*t*t*t*t) + 1.0f)) + b;
	}
	
	public static float quintInOut(float t, float b, float c, float d) {
		t /= (d/2.0f);
		if (t < 1.0f) return ((c/2.0f)*t*t*t*t*t) + b;
		t -= 2.0f;
		return ((c/2.0f)*((t*t*t*t*t) + 2.0f)) + b;
	}
	
	public static float sineIn(float t, float b, float c, float d) {
		return (float)(((-c) * Math.cos((t/d) * (Math.PI / 2.0f))) + c + b);
	}
	
	public static float sineOut(float t, float b, float c, float d) {
		return (float)(((c) * Math.sin((t/d) * (Math.PI/2.0f))) + b);
	}
	
	public static float sineInOut(float t, float b, float c, float d) {
		return (float)(((-c/2.0f) * (Math.cos(Math.PI*t/d) - 1.0f)) + b);
	}
	
	public static float expoIn(float t, float b, float c, float d) {
		return (float)((c * Math.pow(2.0f, 10.0f * ((t/d) - 1.0f))) + b);
	}
	
	public static float expoOut(float t, float b, float c, float d) {
		return (float)((c * (-Math.pow(2.0f, -10.0f * (t/d)) + 1.0f)) + b);
	}
	
	public static float expoInOut(float t, float b, float c, float d) {
		t /= (d/2.0f);
		if (t < 1.0f) return (float)(((c/2.0f) * Math.pow(2.0f, 10.0f * (t - 1.0f))) + b);
		t--;
		return (float)(((c/2.0f) * (-Math.pow(2.0f, -10.0f * t) + 2.0f)) + b);
	}
	
	public static float circIn(float t, float b, float c, float d) {
		t /= d;
		return (float)(((-c) * (Math.sqrt(1.0f - (t*t)) - 1.0f)) + b);
	}
	
	public static float circOut(float t, float b, float c, float d) {
		t /= d;
		t--;
		return (float)((c * Math.sqrt(1.0f - (t*t))) + b);
	}
	
	public static float circInOut(float t, float b, float c, float d) {
		t /= (d/2.0f);
		if (t < 1.0f) return (float)(((-c/2.0f) * (Math.sqrt(1.0f - (t*t)) - 1.0f)) + b);
		t -= 2.0f;
		return (float)(((c/2.0f) * (Math.sqrt(1.0f - (t*t)) + 1.0f)) + b);
	}
}
