package com.brunoaybar.unofficialupc.modules.reserve.filters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.modules.base.BaseFragment;
import com.brunoaybar.unofficialupc.modules.reserve.DisplayableReserveFilter;
import com.brunoaybar.unofficialupc.modules.reserve.ReserveViewModel;
import com.brunoaybar.unofficialupc.modules.reserve.options.ReserveOptionsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

public class ReserveFiltersFragment extends BaseFragment {

    public static ReserveFiltersFragment newInstance(){
        return new ReserveFiltersFragment();
    }

    public ReserveFiltersFragment() {
        setFragmentTitle(R.string.title_reserve);
    }


    private ReserveViewModel viewModel;
    private ReserveFiltersAdapter adapter;

    @BindView(R.id.rviReserve) RecyclerView rviReserve;
    @BindView(R.id.btnSearch) Button btnSearch;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reserve_filters,container,false);
        ButterKnife.bind(this,view);
        viewModel = new ReserveViewModel();
        setupToolbar();
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView(){
        rviReserve.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReserveFiltersAdapter(getContext());
        rviReserve.setAdapter(adapter);
    }

    @Override
    protected void bind() {
        super.bind();

        mSubscription.add(viewModel.getReserveEntries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showEntries,this::displayError));

        mSubscription.add(viewModel.getReserveEnabledStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateReserveEnabled,this::displayError));

        adapter.setListener(new ReserveFiltersAdapter.Callback() {
            @Override
            public void selectedFilterValue(List<DisplayableReserveFilter> entries, int position, int selectedValue) {
                viewModel.selectedFilterValue(entries,position,selectedValue);
            }
            @Override
            public void touchedFilterHeader(List<DisplayableReserveFilter> entries, int position) {
                viewModel.updatedEntry(entries,position);
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        viewModel.load();
    }

    private void showEntries(List<DisplayableReserveFilter> entries){
        adapter.update(entries);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayError(Throwable error) {
        super.displayError(error);
        progressBar.setVisibility(View.GONE);
    }

    private void updateReserveEnabled(boolean enabled){
        btnSearch.setSelected(enabled);
    }

    private boolean isSearching = false;
    @OnClick(R.id.btnSearch)
    public void searchClicked(View v){
        if(isSearching){
            return;
        }

        if(v.isSelected()){
            isSearching = true;
            progressBar.setVisibility(View.VISIBLE);
            viewModel.applyFilters(adapter.getData())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( data -> {
                        Intent i = new Intent(getActivity(),ReserveOptionsActivity.class);
                        i.putExtra(ReserveOptionsActivity.getDataParam(), data);
                        startActivity(i);
                        isSearching = false;
                        progressBar.setVisibility(View.GONE);
                    },this::displayError);
        }else{
            displayMessage(R.string.text_reserve_missing_filters);
            isSearching = false;
        }
    }



    private void setupToolbar(){
        if(getActivity() != null && getActivity() instanceof AppCompatActivity){
            AppCompatActivity appCompatActivity = ((AppCompatActivity) getActivity());
            appCompatActivity.setSupportActionBar(toolbar);
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


}
