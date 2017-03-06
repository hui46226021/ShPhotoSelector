package com.sh.zsh.code.shphotoselectorlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhush on 2016/9/29.
 * E-mail zhush@jerei.com
 * 相册 选择器弹框
 */
public class AlbumDialog implements AdapterView.OnItemClickListener {
    /**
     * 得到自定义的progressDialog
     * @param context
     * @param msg
     * @return
     */


    public  Dialog dialog;
    ListView listview1;

    BitmapUtil bitmapUtil;
    PhotoFileListAdapter listAdapter;
    List<DireInfo> locallist;


    SelectedCall selectedCall;

    public AlbumDialog(SelectedCall selectedCall) {
        this.selectedCall = selectedCall;
    }



    public  Dialog createLoadingDialog(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.imgfilelist, null);// 得到加载view

         listview1 = (ListView) v.findViewById(R.id.listView1);
        bitmapUtil =new BitmapUtil(context);
        locallist= bitmapUtil.LocalImgFileList();
        List<HashMap<String, String>> listdata=new ArrayList<HashMap<String,String>>();
        Bitmap bitmap[] = null;
        if (locallist!=null) {
            bitmap=new Bitmap[locallist.size()];
            for (int i = 0; i < locallist.size(); i++) {
                HashMap<String, String> map=new HashMap<String, String>();
                map.put("filecount", locallist.get(i).photoContent.size()+"张");
                map.put("imgpath", locallist.get(i).photoContent.get(0)==null?null:(locallist.get(i).photoContent.get(0)));
                map.put("imageDire", locallist.get(i).imageDire);
                listdata.add(map);
            }
        }
        listAdapter=new PhotoFileListAdapter(context, listdata);
        listview1.setAdapter(listAdapter);
        listview1.setOnItemClickListener(this);

        dialog = new Dialog(context, R.style.spnner_dialog);

        dialog.setContentView(v, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y =( (Activity)context).getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        WindowManager m = ((Activity)context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用

        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围消失
        dialog.setCanceledOnTouchOutside(true);

        return dialog;

    }


    public  void dismiss(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    public void show(){
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        selectedCall.selectedCall(locallist.get(i));
        dismiss();
    }


    /**
     * 弹出下拉框选中监听
     */
    public static interface SelectedCall{
        public void selectedCall(DireInfo direInfo);
    }
}
