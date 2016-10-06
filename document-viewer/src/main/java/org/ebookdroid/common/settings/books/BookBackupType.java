package org.ebookdroid.common.settings.books;

import org.ebookdroid.app.EBookDroid;
import org.sufficientlysecure.viewer.R;

import org.emdev.utils.enums.ResourceConstant;

public enum BookBackupType implements ResourceConstant {

    /**
     *
     */
    NONE(R.string.pref_bookbackuptype_none),
    /**
     *
     */
    RECENT(R.string.pref_bookbackuptype_recent),
    /**
     *
     */
    ALL(R.string.pref_bookbackuptype_all);

    public final String resValue;

    private BookBackupType(final int resId) {
        this.resValue = EBookDroid.context.getString(resId);
    }

    @Override
    public String getResValue() {
        return resValue;
    }
}
