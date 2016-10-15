package com.brunoaybar.unofficalupc.modules.general;


import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

/**
 * Base fragment class for fragments implemented on this application
 */

public class BaseFragment extends Fragment {

    private @StringRes int mTitle;

    public String getTitle(Context context) {
        return context.getString(mTitle);
    }

    public void setFragmentTitle(@StringRes int title) {
        this.mTitle = title;
    }
}
