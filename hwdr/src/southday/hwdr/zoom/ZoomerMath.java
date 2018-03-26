package southday.hwdr.zoom;

public class ZoomerMath {
    /**
     * 基于三角取值的插值核函数
     * <p> R(x) = </p>
     * <p> x + 1 , -1 <= x <= 0 </p>
     * <p> 1 - x , 0 < x <= 1 </p>
     * @param x 属于 (-2.0, 2.0)
     * @return
     */
    public static double triangleInterp(double x) {
        /*
         * x = (u + 1) 或  (u + 0) 或  (u - 1) 或  (u - 2)
         * u 的取值范围是(0,1)，那么 x 的取值范围是(-2,2)
         * 所以通过 x = x / 2.0 来将x映射到(-1, 1)区间上
         */
        x = x / 2.0;
        return x <= 0.0 ? x + 1.0 : 1.0 - x;
    }
    
    /**
     * 基于Bell分布采样的插值核函数
     * <p> R(x) = </p>
     * <p> 0.5(x + 1.5)^2 , -1.5 <= x <= -0.5 </p>
     * <p> 0.75 - (x)^2 , -0.5 < x <= 0.5 </p>
     * <p> 0.5(x - 1.5)^2 , 0.5 < x <= 1.5 </p>
     * @param x 属于 (-2.0, 2.0)
     * @return
     */
    public static double bellInterp(double x) {
        // x 的取值范围是(-2.0,2.0)，要映射到(-1.5, 1.5)
        x = (x / 2.0) * 1.5;
        
        if (x >= -1.5 && x <= 0.5) {
            return 0.5 * Math.pow(x + 1.5, 2.0);
        } else if (x > -0.5 && x <= 0.5) {
            return 0.75 - x * x;
        } else if (x > 0.5 && x <= 1.5) {
            return 0.5 * Math.pow(x - 1.5, 2.0);
        }
        return 0.0;
    }
    
    /**
     * 基于B样条曲线采样的插值核函数
     * <p> R(x) = </p>
     * <p> (2/3) + 0.5|x|^3 - (x)^2 , 0 <= |x| <= 1 </p>
     * <p> (1/6)(2 - |x|)^3 , 1 < |x| <= 2 </p>
     * @param x 属于 (-2.0, 2.0)
     * @return
     */
    public static double bsplineInterp(double x) {
        if (x < 0.0) x = -x; // 取|x|
        
        if (x >= 0.0 && x <= 1.0) {
            return (2.0 / 3.0) + 0.5 * (x * x * x) - (x * x);
        } else if (x > 1.0 && x <= 2.0) {
            return (1.0 / 6.0) * Math.pow(2.0 - x, 3.0);
        }
        return 1.0;
    }
    
    /**
     * 基于catmull-Rom样条曲线的插值核函数
     * <p> R(x) = </p>
     * <p> (1/6)(9|x|^3 - 15|x|^2 + 6) , |x| <= 1 </p>
     * <p> (1/6)(-3|x|^3 + 15|x|^2 - 24|x| + 12) , 1 < |x| <= 2 </p>
     * <p> 0 , |x| > 2 </p>
     * @param x 
     * @return
     */
    public static double catmullRomInterp(double x) {
        if (x < 0.0) x = -x;
        
        if (x <= 1.0) {
            return (9.0 * x * x * x - 15.0 * x * x + 6.0) / 6.0;
        } else if (x > 1.0 && x <= 2.0) {
            return (-3.0 * x * x * x + 15.0 * x * x - 24.0 * x + 12.0) / 6.0;
        } else {
            return 0.0;
        }
    }
    
    /**
     * 基于sin(x*Pi)/x 函数逼近的插值核函数
     * <p> R(x) = </p>
     * <p> 1 - 2|x|^2 + |x|^3 , |x| <= 1 </p>
     * <p> 4 - 8|x| + 5|x|^2 - |x|^3 , 1 < |x| < 2 </p>
     * <p> 0 , |x| >= 2 </p>
     * @param x 属于 (-2.0, 2.0)
     * @return
     */
    public static double sinCloserInterp(double x) {
        if (x < 0.0) x = -x;
        
        if (x <= 1.0) {
            return 1.0 - 2.0 * (x * x) + (x * x * x);
        } else if (x > 1.0 && x < 2.0) {
            return 4.0 - 8.0 * x + 5.0 * (x * x) - (x * x * x);
        } else {
            return 0.0;
        }
    }
    
    /**
     * 处理边缘情况
     * @param value
     * @param max
     * @param min
     * @return
     */
    public static int clip(int value, int max, int min) {
        return value > max ? max : value < min ? min : value;
    }
}
