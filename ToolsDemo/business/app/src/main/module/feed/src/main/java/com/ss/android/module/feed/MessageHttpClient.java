package com.ss.android.module.feed;

import android.content.Context;

import org.apache.http.impl.client.DefaultHttpClient;

public class MessageHttpClient extends DefaultHttpClient {
    private static final String COOKIE_PREF_NAME = "SSMessageCookiePrefsFile";
    private Context mContext;
    private static MessageHttpClient sMessageHttpClient;

}
