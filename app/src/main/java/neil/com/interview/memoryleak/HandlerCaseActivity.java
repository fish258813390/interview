package neil.com.interview.memoryleak;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * 由Handler 造成的内存泄漏
 * 非静态内部类默认会持有外部类的引用
 * Created by neil on 2018/4/4 0004.
 */
public class HandlerCaseActivity extends Activity {

    /**
     * 造成泄漏的写法
     * 匿名内部类会持有外部类的引用，即myHandler 实例会持有HandlerCaseActivity的引用
     * 当HandlerCaseActivity要销毁时，myHandler 会持有引用导致无法销毁，造成内存泄漏
     */
    /*private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    };*/

    private MyHandler mHandler = new MyHandler(this);
    /**
     * 正确写法 修复内存泄漏
     */
    private static class MyHandler extends Handler {

        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerCaseActivity activity = (HandlerCaseActivity) reference.get();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void loadData() {
        Message message = Message.obtain();
        mHandler.sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
