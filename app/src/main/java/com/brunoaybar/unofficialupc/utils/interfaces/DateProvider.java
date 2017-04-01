package com.brunoaybar.unofficialupc.utils.interfaces;

import java.text.DateFormat;
import java.util.Date;

/**
 * Provides dates
 */

public interface DateProvider {
    Date getNow();
    Date getNever();
    DateFormat getLocalFormatter(String pattern);
}
