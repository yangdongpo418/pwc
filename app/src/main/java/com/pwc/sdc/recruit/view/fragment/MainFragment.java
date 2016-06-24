package com.pwc.sdc.recruit.view.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pwc.sdc.recruit.R;
import com.pwc.sdc.recruit.base.BaseFragment;
import com.pwc.sdc.recruit.base.BaseRecycleAdapter;
import com.pwc.sdc.recruit.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author:dongpo 创建时间: 6/23/2016
 * 描述:
 * 修改:
 */
public class MainFragment extends BaseFragment {
   @Bind(R.id.base_ptr_view_content)
   RecyclerView rvContent;

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        List<String> list = new ArrayList<>();
        list.add("你好啊");
        list.add("你好啊1");
        list.add("你好啊2");
        list.add("你好啊3");
        list.add("你好啊4");
        list.add("你好啊5");


        RecycleAdapter adapter = new RecycleAdapter(list,R.layout.grid_item);
        GridLayoutManager layout = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        rvContent.setLayoutManager(layout);
        rvContent.setAdapter(adapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    public class RecycleAdapter extends BaseRecycleAdapter<String>{
        public RecycleAdapter(List<String> data, int layoutId) {
            super(data, layoutId);
        }

        @Override
        protected void setItemData(ViewHolder holder, int position, int viewType) {
            holder.setText(R.id.descriptionTv, (String) getData(R.layout.grid_item).get(position));
        }
    }

}
