package org.emdev.common.xml;

import org.ebookdroid.app.EBookDroid;
import org.sufficientlysecure.viewer.R;

import org.emdev.utils.enums.ResourceConstant;

public enum XmlParsers implements ResourceConstant {

    /**
     *
     */
    VTDEx(R.string.pref_fb2_xmlparser_vtd_ex),
    /**
     *
     */
    Duckbill(R.string.pref_fb2_xmlparser_duckbill);

    private final String resValue;

    private XmlParsers(final int resId) {
        this.resValue = EBookDroid.context.getString(resId);
    }

    @Override
    public String getResValue() {
        return resValue;
    }

}
