package com.rdb.style.widget;

import com.rdb.style.helper.ViewHelper;

public interface StyleView<T extends ViewHelper> {

    T onCreateHelper();
}
