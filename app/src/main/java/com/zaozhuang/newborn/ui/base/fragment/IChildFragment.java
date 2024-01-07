package com.zaozhuang.newborn.ui.base.fragment;


public interface IChildFragment {
    void onSelected();

    default boolean canNext() {
        return false;
    }
}
