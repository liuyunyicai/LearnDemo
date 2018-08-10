package com.example.nealkyliu.toolsdemo.bussiness.test.job;

import android.content.Context;
import android.net.Uri;

import com.example.nealkyliu.toolsdemo.BuildConfig;
import com.example.nealkyliu.toolsdemo.LiveDataApplication;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.IInjectClass;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectClass2;
import com.example.nealkyliu.toolsdemo.bussiness.test.classed.InjectClass3;
import com.example.nealkyliu.toolsdemo.bussiness.test.compontent.DaggerInjectComponent;
import com.example.nealkyliu.toolsdemo.bussiness.test.compontent.InjectComponent;
import com.example.nealkyliu.toolsdemo.bussiness.test.compontent.InjectSubComponent;
import com.example.nealkyliu.toolsdemo.bussiness.test.scope.ContextLife;
import com.example.nealkyliu.toolsdemo.utils.LogUtils;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/6
 **/
public class TestJobImpl {
//    @Inject
//    IInjectClass mClassC;
//
    @Inject
    @Named("name1")
    IInjectClass mClass1;

    @Inject
    @Named("name2")
    IInjectClass mClass12;

    @Inject
    Lazy<InjectClass2> mInjectClass2Lazy;

    @ContextLife("Activity")
    @Inject
    Context mContext;

    @ContextLife("Application")
    @Inject
    Context mAppContext;

//
//    @Inject
//    InjectClass3 mClass3;
//
//    @Inject
//    IInjectClass mClassCCCCl;
//
    public TestJobImpl() {
//        InjectComponent component = DaggerInjectComponent.builder()
//                .build();
//        component.inject(this);

        String tempUrl = getOneLinkReferrer();
        tempUrl = "tempschema://temphost?" + tempUrl;
        Uri uri = Uri.parse(tempUrl);
        uri.getQueryParameter("group_id");
        uri.getQueryParameter("schema");
    }

    public static String getOneLinkReferrer() {
        String testReferrer = "af_tranid=gbtaHnp_i6UtRCYIFKCguA&pid=sharing_install&af_sub2=6581263215244607754&c=sys&schema=snssdk" +
                ":\\/\\/detail?group_id=6581263215244607754&item_id=6581263202875605509&campaign_group_id=6581263215244607754&app_launch_by=Share+Page+Link";
        return testReferrer;
    }



//
    public void testMain() {
        InjectComponent component = DaggerInjectComponent.builder().injectSubComponent(new InjectSubComponent() {
            @Override
            public InjectClass3 getInjectClass3() {
                return new InjectClass3();
            }
        }).build();
        component.inject(this);
        LogUtils.d(this, "testMain isSame == " + (component.getInjectClass() == component.getInjectClass()));

//        mClass1.test();
//        mClass12.test();
//        mInjectClass2Lazy.get().test();

        LiveDataApplication.getInst().test();
//        mClass1.test();
//        mClassCCCCl.test();
    }
}
