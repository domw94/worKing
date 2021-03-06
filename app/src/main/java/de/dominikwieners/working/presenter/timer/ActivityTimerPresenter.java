package de.dominikwieners.working.presenter.timer;

import android.content.Context;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import de.dominikwieners.working.data.room.Type;
import de.dominikwieners.working.data.room.Work;
import de.dominikwieners.working.repository.WorkingDatabase;
import de.dominikwieners.working.ui.view.timer.ActivityTimerView;

/**
 * Created by dominikwieners on 25.03.18.
 */

public class ActivityTimerPresenter extends MvpBasePresenter<ActivityTimerView> {
    /**
     * Load Type data from Room db
     *
     * @param context
     * @return
     */
    public List<Type> loadTypeData(Context context) {
        return WorkingDatabase
                .getInstance(context)
                .getTypeDao()
                .getAll();
    }

    /**
     * Get String array of all types
     *
     * @param context
     * @return
     */
    public String[] getTypeArray(Context context) {
        List<Type> typeList = loadTypeData(context);
        String[] types = new String[typeList.size()];
        for (int i = 0; i < typeList.size(); i++) {
            types[i] = typeList.get(i).getType();
        }
        return types;
    }


    /**
     * Inset Work into db
     *
     * @param context
     * @param work
     */
    public void insertWorkData(Context context, Work work) {
        WorkingDatabase
                .getInstance(context)
                .getWorkDao()
                .insertAll(work);
    }
}
