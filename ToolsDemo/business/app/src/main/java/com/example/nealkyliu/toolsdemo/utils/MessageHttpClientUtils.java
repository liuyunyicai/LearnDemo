package com.example.nealkyliu.toolsdemo.utils;

import android.content.Context;

import org.apache.http.impl.client.DefaultHttpClient;

public class MessageHttpClientUtils extends DefaultHttpClient {
    private static final String COOKIE_PREF_NAME = "SSMessageCookiePrefsFile";
    private Context mContext;
    private static MessageHttpClientUtils sMessageHttpClient;

}
