package com.example.simulateuser;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;
import java.util.Random;


public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "TwitterAccessibilityService";
    int flag;

    boolean isComment = false;
    boolean isRetweet = false;
    boolean isTweet = false;

    String lastContent = null;

    String[] labels = {"president", "impeachment", "democrats", "trump", "american", "senate", "america", "democrat", "pelosi", "hoax", "republican",
                       "military", "congress", "election", "fake", "partisan", "political", "republicans", "china", "administration"};

    String[] commonStatement = {"thriven and thro", "gradely", "eximious", "jelly", "topgallant", "prestantious", "gallows", "budgeree", "supernacular",
                                "jam", "boss", "fizzing", "bad", "deevy", "bosker", "v.g", "jake", "bodacious"};

    Random randomIndex = new Random(commonStatement.length);

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        // 服务开启时，调用
        // setServiceInfo();这个方法同样可以实现xml中的配置信息
        // 可以做一些开启时的操作，比如点两下返回
        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_CLICKED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(config);
        flag = 0;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // 关闭此服务时，调用
        // 如果有资源记得释放
        return super.onUnbind(intent);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) throws NullPointerException{
        // 使用最多的方法，这里会有大量的逻辑操作
        // 通过对event的判断执行不同的操作
        // 当窗口发生的事件是我们配置监听的事件，会回调此方法
        try {
            int eventType = accessibilityEvent.getEventType();
            String className = accessibilityEvent.getClassName().toString();
            Log.d(TAG, "当前事件类型: " + eventType);
            Log.d(TAG, "当前执行类名: " + className);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        AccessibilityNodeInfo nodeInfo = accessibilityEvent.getSource();
        // 界面变化事件
        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED){
            ComponentName componentName = null;
            try {
                componentName = new ComponentName(accessibilityEvent.getPackageName().toString(), accessibilityEvent.getClassName().toString());
            } catch (NullPointerException e){
                e.printStackTrace();
                return;
            }

            ActivityInfo activityInfo = tryGetActivity(componentName);
            boolean isActivity = activityInfo != null;
            if (isActivity){
                Log.i(TAG, componentName.flattenToShortString());
                // 格式为：（包名/.+当前Activity所在包的类名）
                // 如果是模拟程序的操作界面
                if (componentName.flattenToShortString().equals("com.example.simulateexample/.MainActivity")){
                    List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.example.simulateexample:id/btn_click");
                    if (list != null && list.size() > 0){
                        list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                        forceClick(list.get(0));
                    }
                } else if (componentName.flattenToShortString().equals("com.twitter.android/.LoginActivity")){
                    Log.i("Herrrb", "进入Twitter");
                    try {
                        List<AccessibilityNodeInfo> identifier = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/login_identifier");
                        List<AccessibilityNodeInfo> password = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/login_password");
                        if (identifier != null && identifier.size() > 0){
                            Bundle userName = new Bundle();
                            userName.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "18135456775");
                            identifier.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, userName);
                            Log.i(TAG, "用户名已设置");
                        }
                        if (password != null && password.size() > 0){
                            Bundle passWord = new Bundle();
                            passWord.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "Whb990124");
                            password.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, passWord);
                            Log.i(TAG, "密码也已设置");
                        }
                        List<AccessibilityNodeInfo> login = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/login_login");
                        if (login != null && login.size() > 0){
                            Log.i(TAG, "对登录键进行点击");
                            login.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    } catch (NullPointerException e){
                        e.printStackTrace();
                        Log.e(TAG, "LoginActivity 已经结束");
                    }
                    Log.i(TAG, "=============================================");
                    Log.i(TAG, "登陆阶段结束");
                }else if (componentName.flattenToShortString().equals("com.twitter.android/com.twitter.app.onboarding.signup.SignUpSplashActivity") || componentName.flattenToShortString().equals("com.twitter.android/com.twitter.app.onboarding.signupsplash.SignUpSplashActivity")){
                    Log.i(TAG, "进入Twitter Splash界面1");
                    List<AccessibilityNodeInfo> signin1 = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/sign_in_text");
                    if (signin1 != null && signin1.size()>0){
                        signin1.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }else if (componentName.flattenToShortString().equals("com.twitter.android/.onboarding.common.CtaSubtaskActivity")){
                    Log.i(TAG, "进入Twitter Splash界面2");
                    List<AccessibilityNodeInfo> signin = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/detail_text");
                    if (signin != null && signin.size()>0){
                        Log.i("Herrrb", signin.toString());
                        signin.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }else if (componentName.flattenToShortString().equals("com.twitter.android/.LoginChallengeActivity")){
                    Log.i(TAG, "进入Twitter登录验证阶段");
                    List<AccessibilityNodeInfo> phone_number = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/challenge_response");
                    List<AccessibilityNodeInfo> verify_buttom = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/email_challenge_submit");
                    if (phone_number != null && phone_number.size() > 0){
                        Bundle pNumber = new Bundle();
                        pNumber.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "18829033587");
                        phone_number.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, pNumber);
                    } else {
                        Log.i(TAG, "获取电话号码输入控件失败");
                    }

                    if (verify_buttom != null && verify_buttom.size() > 0){
                        verify_buttom.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    } else {
                        Log.i(TAG, "获取验证按钮控件失败");
                    }
                }else if(componentName.flattenToShortString().equals("com.twitter.android/com.twitter.app.main.MainActivity")){
                    // 在这里就是正常的操作流程了，浏览点赞评论转发
                    Log.i(TAG, "进入主页开始浏览");
                    Log.i(TAG, "当前线程号：" + Thread.currentThread());

                    sleep(1);

                    List<AccessibilityNodeInfo> lists;

                    // 这里可能会出现无指针错误，
                    try {
                        lists = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/list");
                    } catch (NullPointerException e){
                        e.printStackTrace();
                        try {
                            nodeInfo.refresh();
                            lists = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/list");
                        } catch (NullPointerException e1){
                            e.printStackTrace();
                            return;
                        }
                    }

                    if (Math.random() < 0.1){
                        // 这里用来发送推文

                        sleep(2);
                        List<AccessibilityNodeInfo> composerWrite = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/composer_write");
                        if (composerWrite != null && composerWrite.size() != 0){
                            Log.i(TAG, "点击发推键");
                            composerWrite.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            isTweet = true;
                        }
                    } else {
                        // 进入浏览模式
                        List<AccessibilityNodeInfo> layoutRow = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/outer_layout_row_view_tweet");
                        Log.i(TAG, "当前页面内容数量：" + layoutRow.size());
                        if (layoutRow.size() != 0) {
                            if (flag == layoutRow.size()) {
                                flag = 0;
                                lists.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                            } else {
                                String currentString = layoutRow.get(flag).getChild(0).getContentDescription().toString().substring(0, 20);
                                if (currentString.equals(lastContent)) {
                                    flag += 2;
                                    layoutRow.get(flag - 1).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                } else {
                                    lastContent = currentString;
                                    flag++;
                                    layoutRow.get(flag - 1).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                }
                            }
                        }
                    }
                }else if (componentName.flattenToShortString().equals("com.twitter.android/.TweetDetailActivity")){
                    Log.i(TAG, "进入详情页面");
                    sleep(1);

                    // 如果从评论页面跳转回来，就直接退出
                    if (isComment){
                        isComment = false;
                        Log.i(TAG, "已完成评论");
                        performGlobalAction(GLOBAL_ACTION_BACK);
                    }

                    if (isRetweet){
                        isRetweet = false;
                        Log.i(TAG, "已完成转推");
                        performGlobalAction(GLOBAL_ACTION_BACK);
                    }

                    boolean ifLike = false;
                    List<AccessibilityNodeInfo> contentText = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/content_text");
                    if (contentText != null && contentText.size() != 0){
                        String content = contentText.get(0).getText().toString().toLowerCase();
                        for (String label: labels){
                            int startIndex = content.indexOf(label);
                            if (startIndex != -1){
                                ifLike = true;
                                break;
                            }
                        }
                    }

                    if (ifLike){
                        Log.i(TAG, "存在指定内容，进行点赞");
                        List<AccessibilityNodeInfo> likeButton = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/favorite");
                        if (likeButton != null && likeButton.size() > 0){
                            Log.i(TAG, "点赞");
                            if (likeButton.get(0).getContentDescription().toString().equals("喜欢（喜欢了）")){
                                Log.i(TAG, "已经点过赞了");
                            } else {
                                likeButton.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            }
                        }
                        double proportion = Math.random();
                        if (proportion >= 0.5344){
                            List<AccessibilityNodeInfo> commentButton = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/reply");
                            List<AccessibilityNodeInfo> retweetButton = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/retweet");
                            if (Math.random() > 0.5){
                                if (commentButton != null && commentButton.size() != 0){
                                    sleep(2);
                                    commentButton.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                }
                            } else {
                                if (retweetButton != null && retweetButton.size() != 0){
                                    if (retweetButton.get(0).getContentDescription().toString().equals("转推 (已转推)")){
                                        sleep(2);
                                        Log.i(TAG, "已转推");
                                    } else {
                                        sleep(2);
                                        retweetButton.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                    }
                                }
                            }
                        }
                    }

                    performGlobalAction(GLOBAL_ACTION_BACK);
                }else if (componentName.flattenToShortString().equals("com.twitter.android/com.twitter.composer.ComposerActivity") && !isTweet){
                    Log.i(TAG, "进入评论页面");
                    sleep(1);
                    List<AccessibilityNodeInfo> tweetText = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/tweet_text");
                    if (tweetText != null && tweetText.size() != 0){
                        int random = randomIndex.nextInt(commonStatement.length);
                        String tweetContent = commonStatement[random];
                        Bundle tweetCont = new Bundle();
                        tweetCont.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, tweetContent);
                        tweetText.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, tweetCont);
                        Log.i(TAG, "已设置回复内容：" + tweetContent);
                    }

                    List<AccessibilityNodeInfo> tweetButton = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/button_tweet");
                    if (tweetButton != null && tweetButton.size() != 0){
                        AccessibilityNodeInfo tweetBtn = tweetButton.get(0);
                        if (tweetBtn.getContentDescription() != null){
                            sleep(2);
                            performGlobalAction(GLOBAL_ACTION_BACK);
                        } else {
                            isComment = true;
                            sleep(2);
                            tweetBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                } else if (componentName.flattenToShortString().equals("com.twitter.android/com.twitter.composer.ComposerActivity") && isTweet){
                    // 进入发推页面
                    boolean isText = false;
                    sleep(1);
                    List<AccessibilityNodeInfo> tweetText = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/tweet_text");
                    if (tweetText != null && tweetText.size() != 0){
                        int randomT = randomIndex.nextInt(commonStatement.length);
                        String TweetSentence = commonStatement[randomT];
                        Bundle tweetSentence = new Bundle();
                        tweetSentence.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, TweetSentence);
                        tweetText.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, tweetSentence);
                        Log.i(TAG, "已设置发推内容");
                        isText = true;
                    }

                    if (isText){
                        List<AccessibilityNodeInfo> TweetButton = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/button_tweet");
                        if (TweetButton != null && TweetButton.size() != 0){
                            AccessibilityNodeInfo tweetBtn = TweetButton.get(0);
                            if (tweetBtn.getContentDescription() == null){
                                // 当Content Description有值的时候，说明并没有设置推文
                                Log.i(TAG, "点击发推");
                                isTweet = false;
                                sleep(2);
                                tweetBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            }
                        }
                    }


                }
            } else {
                if (componentName.getClassName().equals("com.google.android.material.bottomsheet.a")){
                    List<AccessibilityNodeInfo> retweetBtn = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/action_sheet_item_title");
                    if (retweetBtn != null && retweetBtn.size() != 0){
                        Log.i(TAG, "点击了转推");
                        isRetweet = true;
                        sleep(2);
                        retweetBtn.get(0).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }

//        if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED){
//            if (isRetweet){
//                Log.i(TAG, "点击弹出的转推按钮");
//                sleep(2);
//                List<AccessibilityNodeInfo> test1 = nodeInfo.findAccessibilityNodeInfosByViewId("com.twitter.android:id/action_sheet_item_title");
//                if (test1 != null && test1.size() != 0){
//                    isRetweet = false;
//                    test1.get(0).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                    Log.i(TAG, "点击转推");
//                }
//            }
//        }
    }

    private ActivityInfo tryGetActivity(ComponentName componentName){
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onInterrupt() {
        // 当服务要被中断时调用，会被调用多次
    }

    private void sleep(int sec){
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

//    private void forceClick(AccessibilityNodeInfo nodeInfo){
//        Rect rect = new Rect();
//        nodeInfo.getBoundsInScreen(rect);
//        Log.i(TAG, "forceClick: " + rect.left + " " + rect.top + " " + rect.right + " " + rect.bottom);
//        int x = (rect.left + rect.right) / 2;
//        int y = (rect.top + rect.bottom) / 2;
//        String cmd = "input tap " + x + " " + y;
//
//        Process process = null;
//        DataOutputStream os = null;
//        try {
//            process = Runtime.getRuntime().exec("su");
//            os = new DataOutputStream(process.getOutputStream());
//            os.writeBytes(cmd + "\n");
//            Log.i(TAG, "执行命令：" + cmd);
//            os.writeBytes("exit\n");
//            os.flush();
//        } catch (Exception e){
//            e.printStackTrace();
//        } finally {
//            try{
//                if (os != null){
//                    os.close();
//                }
//                process.destroy();
//            } catch (Exception e){
//            }
//        }
//    }

}
