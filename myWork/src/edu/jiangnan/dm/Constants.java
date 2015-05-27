package edu.jiangnan.dm;

/**
 * Created by Administrator on 2015/3/19.
 */
public class Constants {
    public static final String USERINFO_TABLE_NAME="userInfo";
    public static final String CONTACT_TABLE_NAME="tcontact";

    public final static int STATE_NO_DATA = 0;
    public final static int STATE_OK = 200;
    public final static int STATE_NAME_ERRO = 1;
    public final static int STATE_JOB_ERRO = 2;
    public final static int STATE_TELEPHONE_ERRO = 3;
    public final static int STATE_EMAIL_ERRO = 4;
    public final static int STATE_ORGANIZATION_ERRO = 5;
    public final static int STATE_ADDRESS_ERRO = 6;
    public final static int STATE_CARDID_ERRO = 7;
    public final static int STATE_HAD_EXISTED = 8;

    public final static int CODE_ADDED = 2;
    public final static int CODE_SCAN_TO_ADD = 22;

    /**
     * 名片样式编号
     */
    public final static String CARD_ONE = "card1";
    public final static String CARD_TWO = "card2";
    public final static String CARD_THREE = "card3";
    public final static String CARD_FOUR = "card4";

    /**
     * 广播
     */
    public final static String ACTION_ICON_CHANGED = "change_icon";
    public final static String ACTION_GOTO_CARD = "goto_card";

    /**
     * DES_KEY
     */
    public final static String KEY_ENCODE = "key_zhouguodong";
}
