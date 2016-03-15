package club.addi.shareyear.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by addi on 2016/3/15 0015.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private static final String APPID = "wx89fcc63b6a002387";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        api  = WXAPIFactory.createWXAPI(this,"wx89fcc63b6a002387",false);
        api.handleIntent(getIntent(),this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

    }
}
