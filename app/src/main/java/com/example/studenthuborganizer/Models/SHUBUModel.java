package com.example.studenthuborganizer.Models;

import com.example.studenthuborganizer.Presenters.SHUBOPresenter;

public class SHUBUModel {
    SHUBOPresenter mPresenterContext;
    SHUBOCalContract calContract;

    // Constructor to receive the Activity context and allow association
    public SHUBUModel(SHUBOPresenter context) {
            this.mPresenterContext = context;
            calContract = new SHUBOCalContract(context.GetAppContext());
        }


}
