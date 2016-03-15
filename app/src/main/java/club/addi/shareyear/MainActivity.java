package club.addi.shareyear;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MainActivity extends Activity {
    private static final int THUMB_SIZE = 150;
    private ImageView mImage;
    private EditText mEdit;
    private Button mBtn;
    private IWXAPI iwxapi;
    private Bitmap bitmap =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //微信api注册
        iwxapi = WXAPIFactory.createWXAPI(this,"wx89fcc63b6a002387");
        iwxapi.registerApp("wx89fcc63b6a002387");
        initView();
        mEdit.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/test.ttf"));
    }

    /**
     * 初始化控件，添加监听事件
     */
    private void initView() {
        mImage = (ImageView) findViewById(R.id.img_image);
        mEdit = (EditText) findViewById(R.id.edi_text);
        mBtn = (Button) findViewById(R.id.btn_share);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    shareToWechat();
               // mImage.setImageBitmap(getScreenrShot());
                   mBtn.setVisibility(View.VISIBLE);
            }
        });
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 100);
            }
        });

    }

    //定义分享到微信方法
    private void shareToWechat(){
        WXImageObject imgObj  = new WXImageObject(getScreenrShot());
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        //构造一个req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        iwxapi.sendReq(req);
    }


    //定义获取图片方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100 && resultCode==RESULT_OK){
            if(data!=null){
                mImage.setImageURI(data.getData());
            }
        }
    }


    //获取截图
    private Bitmap getScreenrShot(){
        mBtn.setVisibility(View.INVISIBLE);
        View view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();   //建立缓存截图
        return view.getDrawingCache();
    }



}
