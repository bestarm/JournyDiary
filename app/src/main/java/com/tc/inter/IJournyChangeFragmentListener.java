package com.tc.inter;

import entity.Journy;

/**
 * Created by ThanhCong on 01/04/2017.
 */

public interface IJournyChangeFragmentListener {
    void showJournyListFragment();
    void showAddJournyFragment(Journy journy);
    void showDiaryActivity(int position);
    void updateJournyListFragment();
}
