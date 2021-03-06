package de.dominikwieners.working;

/**
 * Created by dominikwieners on 17.03.18.
 */

public class Config {
    //Persistence
    public static final String WELCOME_DONE = "WELCOME_DONE";

    //Intent extras
    public static final String CURRENT_MONTH = "CURRENT_MONTH";
    public static final String CURRENT_PAGER_POS = "CURRENT_PAGER_POS";
    public static final String SELECTED_YEAR = "SELECTED_YEAR";

    //Bundles
    public static final String WORK_ITEM_LIST = "WORK_ITEM_LIST";

    //state of next button in option menu
    public static final int HIDE_MENU = 0;
    public static final int SHOW_MENU = 1;

    //After this timer limit, it's possible to add data
    public static final int TIMER_MINUTES_ON_ENABLE_SAVE = 1;

    public static final int TIMER_DAY_LIMIT_HOURS = 23;
    public static final int TIMER_DAY_LIMIT_MINUTES = 59;
    public static final int TIMER_DAY_LIMIT_SECONDS = 00;

    public static final String MY_WEBSITE = "http://dominikwieners.de/";
    public static final String MY_EMAIL_ADDRESS = "kontakt@dominikwieners.de";
    public static final String MY_GITHUB = "https://github.com/domw94";
}
