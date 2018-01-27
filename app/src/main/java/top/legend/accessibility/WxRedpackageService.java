package top.legend.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author：hcqi .
 * des:
 * email:hechuanqi.top@gmail.com
 * date: 2018/1/27
 */
public class WxRedpackageService extends AccessibilityService {
    private static final String TAG = "WxRedpackageService";
    
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        switch (accessibilityEvent.getEventType()) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                if (accessibilityEvent.getClassName().equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")) {
                    //红包详情
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        detail(accessibilityEvent);
                    }
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void detail(AccessibilityEvent accessibilityEvent) {
        List<Map> items = new ArrayList<>();
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow().getChild(0).getChild(0);
        if (nodeInfo.getClassName().equals("android.widget.ListView")) {
            AccessibilityNodeInfo textview = nodeInfo.getChild(0).getChild(2);
            String text = textview.getText().toString();
//            String count = text.substring(0, text.indexOf("个"));
//            String money = text.substring(text.indexOf("共") + 1, text.indexOf("元"));
//            String sumTime = text.substring(text.indexOf(",") + 1, text.indexOf("分"));
            Log.d(TAG, "拿到红包个数以及金额 : " + text);
            HashMap<Object, Object> titleMap = new HashMap<>();
//            titleMap.put("count", count);
            titleMap.put("string", text);
//            titleMap.put("sumMoney", money);
//            titleMap.put("sumTime", sumTime);
            items.add(titleMap);
            for (int i = 1; i < nodeInfo.getChildCount() - 1; i++) {
                AccessibilityNodeInfo itemNode = nodeInfo.getChild(i).getChild(1);
                AccessibilityNodeInfo itemNameNode = nodeInfo.getChild(i).getChild(0);
                CharSequence itemName = itemNameNode.getText();
                CharSequence itemTime = itemNode.getText();

                CharSequence itemMoney = nodeInfo.getChild(i).getChild(2).getText();
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("itemName", itemName);
                objectObjectHashMap.put("itemTime", itemTime);
                objectObjectHashMap.put("itemMoney", itemMoney);

                items.add(objectObjectHashMap);
//                AccessibilityNodeInfo child = nodeInfo.getChild(i);
//                for (int j = 0; j < child.getChildCount(); j++) {
//                    AccessibilityNodeInfo childLevel1 = child.getChild(j);
//                }
            }
        }
        Log.d(TAG, "拿到所有红包数据 : " + items.toString());
        Toast.makeText(this, "已检测到红包数据 请前往accessibility界面查看", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(items);

//        if (nodeInfo != null) {
//            List<AccessibilityNodeInfo> infoList;
//            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
//                AccessibilityNodeInfo child = nodeInfo.getChild(i);
//                for (int j = 0; j < child.getChildCount(); j++) {
//                    AccessibilityNodeInfo child_level2 = child = child.getChild(j);
//                    infoList = child_level2.findAccessibilityNodeInfosByViewId("@id/bze");
//                    for (int a = 0; a < child_level2.getChildCount(); a++) {
//                        AccessibilityNodeInfo child_level3 = child_level2.getChild(a);
//                        infoList = child_level3.findAccessibilityNodeInfosByViewId("@id/bze");
//                    }
//                }
//                infoList = child.findAccessibilityNodeInfosByViewId("@id/bze");
//                if (!infoList.isEmpty()) {
//                    break;
//                }
//            }
//
//            //为示,直接查看了红包控件的id
//            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("@id/bze");
//            nodeInfo.recycle();
//            for (AccessibilityNodeInfo item : list) {
//            }
//        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt: ");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected: ");
    }
}
