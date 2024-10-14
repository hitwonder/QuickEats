package com.app.kiosk.ui;

import com.app.kiosk.model.Item;

public interface OnItemClickListener {
    void onItemClick(Item item);
    void toggleNoResultsMessage(boolean isEmpty);

}

