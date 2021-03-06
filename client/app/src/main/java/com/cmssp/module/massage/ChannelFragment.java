package com.cmssp.module.massage;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.cmssp.Const;
import com.cmssp.R;
import com.cmssp.model.entity.ChannelEntity;
import com.cmssp.model.entity.ChannelListEntity;
import com.cmssp.model.entity.PointEntity;
import com.cmssp.model.repository.ChannelRepo;
import com.ox.adapter.Adapter;
import com.ox.adapter.AdapterHelper;
import com.ox.adapter.RecyclerAdapter;
import com.ox.adapter.RecyclerAdapterHelper;
import com.ox.base.BaseEntity;
import com.ox.base.BaseFragment;
import com.ox.data.retrofit.DefaultSubscriber;
import com.ox.utils.LayoutUtil;
import com.ox.utils.ToastUtils;
import com.ox.widgets.pullToRefreshView.OXRecyclerView;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/11/10.
 */
public class ChannelFragment extends BaseFragment{

    @BindView(R.id.content_view)
    OXRecyclerView contentView;

    @BindView(R.id.title)
    TextView title;

    private int mCurrentPage = 0;

    private RecyclerAdapter<ChannelEntity> mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.layout_fragment_point;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        contentView.setLayoutManager(LayoutUtil.getLinearLayout(mContext));
        contentView.setLoadingListener(this);

        title.setText(R.string.massagePoint);

        mAdapter = new RecyclerAdapter<ChannelEntity>(mContext , R.layout.layout_channel_item) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, ChannelEntity item) {

                Adapter<PointEntity> mGridAdapter = new Adapter<PointEntity>(mContext , R.layout.layout_channel_point) {
                    @Override
                    protected void convert(AdapterHelper helper, PointEntity item) {
                        helper.setText(R.id.pointName ,item.name);
                        helper.setText(R.id.pointTone ,item.tone);
                    }
                };

                helper.setText(R.id.channelName , item.name);
                helper.setText(R.id.channelTime , item.getShowTime());
                helper.setImageUrl(R.id.channelImage , item.pic , false);
                GridView gridView = helper.getView(R.id.points);

//                double itemHeight = helper.getItemView().getMeasuredHeight();
//                double rows = Math.ceil(item.points.size()/gridView.getNumColumns());
                mGridAdapter.addAll(item.points);

                gridView.setAdapter(mGridAdapter);
            }
        };

        contentView.setAdapter(mAdapter);
        contentView.addItemDecoration(LayoutUtil.getHorizontalDivider_6(mContext));
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mCurrentPage = 0;
        loadDataFromServer(mCurrentPage++);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        loadDataFromServer(mCurrentPage++);
    }

    @Override
    protected void initData() {
        super.initData();
        loadDataFromServer(mCurrentPage++);
    }

    void loadDataFromServer(int pageNumber){
        ChannelRepo.instance.getChannelList(pageNumber)
//                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new DefaultSubscriber<BaseEntity<ChannelListEntity>>() {
                    @Override
                    protected void _onNext(BaseEntity<ChannelListEntity> result) {
                        if (isRefresh){
                            mAdapter.clear();
                        }
                        if (result.ret == Const.ERROR_OK){
                            ChannelListEntity listEntity = result.data;
                            if(!listEntity.channelEntityList.isEmpty()){
                                mAdapter.addAll(listEntity.channelEntityList);
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();

                        contentView.refreshComplete();
                        contentView.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        contentView.refreshComplete();
                        contentView.loadMoreComplete();

                        ToastUtils.show(mContext , "刷新失败");
                    }
                });
    }
}
