package org.ebookdroid.common.settings.types;

import org.ebookdroid.app.EBookDroid;
import org.sufficientlysecure.viewer.R;

import org.emdev.utils.enums.ResourceConstant;

public enum BookRotationType implements ResourceConstant {

    /**
    *
    */
    UNSPECIFIED(R.string.pref_rotation_unspecified, null),
    /**
    *
    */
    LANDSCAPE(R.string.pref_rotation_land, RotationType.LANDSCAPE),
    /**
    *
    */
    PORTRAIT(R.string.pref_rotation_port, RotationType.PORTRAIT),
    /**
     *
     */
    AUTOMATIC(R.string.pref_rotation_auto, RotationType.AUTOMATIC);
    private final String resValue;

    private final RotationType orientation;

    private BookRotationType(final int resId, final RotationType orientation) {
        this.resValue = EBookDroid.context.getString(resId);
        this.orientation = orientation;
    }

    @Override
    public String getResValue() {
        return resValue;
    }

    public int getOrientation(final RotationType defRotation) {
        return orientation != null ? orientation.getOrientation() : defRotation.getOrientation();
    }
}
