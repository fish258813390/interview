package neil.com.interview.memoryleak;

import android.content.Context;

/**
 * 由于Context 的使用造成内存泄漏
 * 推荐使用Application 的 context实例，
 * 单例对象的生命周期和App保持一致
 * Created by neil on 2018/4/4 0004.
 */
public class ContextCase {

    /**
     * 有内存泄漏的问题
     * context 应该使用Application 的context
     */
    /*private static ContextCase mInstance;
    private Context mContext;
    private ContextCase(Context context){
        this.mContext = context;
    }
    private static ContextCase getInstance(Context context){
        if(mInstance == null){
            mInstance = new ContextCase(context);
        }
        return mInstance;
    }*/

    /**
     * 修复内存泄漏的方法
     */
    private static ContextCase mInstance;
    private Context mContext;

    private ContextCase(Context context) {
        this.mContext = context.getApplicationContext(); // 使用Applicaion的context
    }

    public static ContextCase getInstance(Context context){
        if(mInstance == null){
            mInstance = new ContextCase(context);
        }
        return mInstance;
    }
}
