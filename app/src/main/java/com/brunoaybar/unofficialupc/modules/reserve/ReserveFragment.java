package com.brunoaybar.unofficialupc.modules.reserve;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.modules.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

public class ReserveFragment extends BaseFragment {

    public static ReserveFragment newInstance(){
        return new ReserveFragment();
    }

    public ReserveFragment() {
        setFragmentTitle(R.string.option_reserve);
    }


    private ReserveViewModel viewModel;
    private ReserveFiltersAdapter adapter;

    @BindView(R.id.rviReserve) RecyclerView rviReserve;
    @BindView(R.id.btnSearch) Button btnSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reserve,container,false);
        ButterKnife.bind(this,view);
        rviReserve.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReserveFiltersAdapter(getContext());
        rviReserve.setAdapter(adapter);
        viewModel = new ReserveViewModel();

        bind();

        return view;
    }


    public void bind(){
        mSubscription.add(viewModel.getReserveEntries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showEntries,this::displayError));

        mSubscription.add(viewModel.getReserveEnabledStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateReserveEnabled,this::displayError));

        adapter.setListener(viewModel::updated);

        viewModel.load();
    }

    private void showEntries(List<DisplayableReserveFilter> entries){
        adapter.update(entries);
    }

    private void updateReserveEnabled(boolean enabled){
        btnSearch.setEnabled(enabled);
    }

}
