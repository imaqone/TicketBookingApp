package com.example.ordersystem;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class rFragmentPaperAdapter extends FragmentPagerAdapter {
    private List<Fragment>listFragment;
    private FragmentManager fragmentManager;
    public rFragmentPaperAdapter(FragmentManager fragmentManager, List<Fragment>list){
        super(fragmentManager);
        this.fragmentManager=fragmentManager;
        this.listFragment=list;
    }
    @Override
    public Fragment getItem(int arg){
        return listFragment.get(arg);
    }
    @Override
    public int getCount(){
        return listFragment.size();
    }
}
