package top.legend.accessibility.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.legend.accessibility.R;
import top.legend.accessibility.model.Items;

/**
 * author：hcqi .
 * des:
 * email:hechuanqi.top@gmail.com
 * date: 2018/1/27
 */
public class ItemsAdapter extends BaseQuickAdapter<Items, BaseViewHolder> {
    public ItemsAdapter(@Nullable List<Items> data) {
        super(R.layout.itmes, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Items item) {
        List<String> items = item.getItems();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            String str = items.get(i);
            stringBuilder.append(str);
            if (i != items.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        helper.setText(R.id.tv_title, item.getTitle()).setText(R.id.tv_item, stringBuilder.toString());


    }
}
