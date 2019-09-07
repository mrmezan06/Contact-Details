package com.mezan.contactdetails;

;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Adapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public Adapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Contacts ContactF = new Contacts();
                return ContactF;
            case 1:
                Call_logs Call_logsF = new Call_logs();
                return Call_logsF;
            case 2:
                Message MessageF = new Message();
                return MessageF;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
