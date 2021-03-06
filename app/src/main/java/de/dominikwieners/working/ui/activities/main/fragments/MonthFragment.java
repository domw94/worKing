package de.dominikwieners.working.ui.activities.main.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.dominikwieners.working.Config;
import de.dominikwieners.working.R;
import de.dominikwieners.working.data.room.Work;
import de.dominikwieners.working.presenter.main.FragmentMonthPresenter;
import de.dominikwieners.working.ui.activities.main.adapter.WorkAdapter;
import de.dominikwieners.working.ui.view.main.FragmentMonthView;

/**
 * Created by dominikwieners on 17.03.18.
 */

public class MonthFragment extends MvpFragment<FragmentMonthView, FragmentMonthPresenter> {

    @BindView(R.id.main_month_recycler)
    RecyclerView recycler;

    public static MonthFragment newInstance(List<Work> workList) {
        MonthFragment monthFragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putSerializable(Config.WORK_ITEM_LIST, (ArrayList<Work>) workList);
        monthFragment.setArguments(args);
        return monthFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_month, container, false);
        ButterKnife.bind(this, view);
        recycler.setAdapter(new WorkAdapter(presenter, getActivity().getApplicationContext(), getArguments()));
        recycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recycler.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        return view;
    }

    @Override
    public FragmentMonthPresenter createPresenter() {
        return new FragmentMonthPresenter();
    }

    public RecyclerView getRecycler() {
        return recycler;
    }
}
