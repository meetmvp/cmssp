package com.ox.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * ResourceUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-5-26
 */
public class ResourceUtils {

    private ResourceUtils() {
        throw new AssertionError();
    }

    /**
     * get an asset using ACCESS_STREAMING mode. This provides access to files that have been bundled with an
     * application as assets -- that is, files placed in to the "assets" directory.
     * 
     * @param context
     * @param fileName The name of the asset to open. This name can be hierarchical.
     * @return
     */
    public static String geFileFromAssets(Context context, String fileName) {
        if (context == null || StringUtils.isEmpty(fileName)) {
            return null;
        }

        StringBuilder s = new StringBuilder("");
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get content from a raw resource. This can only be used with resources whose value is the name of an asset files
     * -- that is, it can be used to open drawable, sound, and raw resources; it will fail on string and color
     * resources.
     * 
     * @param context
     * @param resId The resource identifier to open, as generated by the appt tool.
     * @return
     */
    public static String geFileFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * same to {@link ResourceUtils#geFileFromAssets(Context, String)}, but return type is List<String>
     * 
     * @param context
     * @param fileName
     * @return
     */
    public static List<String> geFileToListFromAssets(Context context, String fileName) {
        if (context == null || StringUtils.isEmpty(fileName)) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * same to {@link ResourceUtils#geFileFromRaw(Context, int)}, but return type is List<String>
     * 
     * @param context
     * @param resId
     * @return
     */
    public static List<String> geFileToListFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            reader = new BufferedReader(in);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到动画id
     * @param context 上下文对象
     * @param name 字符串
     * @return
     */
    public static int getAnimId(Context context,String name){
        return context.getResources().getIdentifier(name, "anim",
                context.getPackageName());
    }

    /**
     * 得到属性id
     * @param context 上下文对象
     * @param name 字符串
     * @return
     */
    public static int getAttrId(Context context,String name){
        return context.getResources().getIdentifier(name, "attr",
                context.getPackageName());
    }

    /**
     * 得到布局id
     * @param context 上下文对象
     * @param name 字符串
     * @return
     */
    public static int getLayoutId(Context context, String name) {
        return context.getResources().getIdentifier(name, "layout",
                context.getPackageName());
    }

    /**
     * 得到字符串id
     * @param context 上下文对象
     * @param name 字符串
     * @return
     */
    public static int getStringId(Context context, String name) {
        return context.getResources().getIdentifier(name, "string",
                context.getPackageName());
    }

    /**
     * 得到图片id
     * @param context 上下文对象
     * @param name 字符串
     * @return
     */
    public static int getDrawableId(Context context, String name) {
        return context.getResources().getIdentifier(name,
                "drawable", context.getPackageName());
    }

    /**
     * 得到资源样式数组
     * @param context 上下文对象
     * @param name 字符串
     * @return
     */
    public static final int[] getResourceDeclareStyleableIntArray( Context context, String name ){
        try {
            //use reflection to access the resource class
            Field[] fields2 = Class.forName( context.getPackageName() + ".R$styleable" ).getFields();
            //browse all fields
            for ( Field f : fields2 ) {
                //pick matching field
                if ( f.getName().equals( name ) ) {
                    //return as int array
                    int[] ret = (int[])f.get( null );
                    return ret;
                }
            }
        } catch ( Throwable t ) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * 得到样式id
     * @param context 上下文对象
     * @param name 字符串
     * @return
     */
    public static int getStyleableId(Context context, String name){
        return context.getResources().getIdentifier(name, "styleable",
                context.getPackageName());
    }

    /**
     * 得到样式id
     * @param context 上下文对象
     * @param name 字符串
     * @return
     */
    public static int getStyleId(Context context, String name) {
        return context.getResources().getIdentifier(name, "style",
                context.getPackageName());
    }

    /**
     * 得到id
     * @param context 上下文对象
     * @param name 字符串
     * @return
     */
    public static int getId(Context context, String name) {
        return context.getResources().getIdentifier(name, "id",
                context.getPackageName());
    }

    /**
     * 得到颜色id
     * @param context 上下文对象
     * @param name 字符串
     * @return
     */
    public static int getColorId(Context context, String name) {
        return context.getResources().getIdentifier(name, "color",
                context.getPackageName());
    }

    /**
     * 得到原生id
     * @param context 上下文对象
     * @param name 字符串
     * @return
     */
    public static int getRawId(Context context, String name) {
        return context.getResources().getIdentifier(name, "raw",
                context.getPackageName());
    }

    /**
     * 获取字符串
     *
     * @param ctx
     * @param resName
     * @return
     */
    public static String getString(Context ctx, String resName) {
        return ctx.getString(ID.getString(ctx, resName));
    }

    /**
     * 获取字符串
     *
     * @param ctx
     * @param resName
     * @param formatArgs
     * @return
     */
    public static String getString(Context ctx, String resName, Object...formatArgs) {
        return ctx.getString(ID.getString(ctx, resName), formatArgs);
    }

    /**
     * 获取颜色
     *
     * @param ctx
     * @param resName
     * @return
     */
    public static int getColor(Context ctx, String resName) {
        return ctx.getResources().getColor(ID.getColor(ctx, resName));
    }
}