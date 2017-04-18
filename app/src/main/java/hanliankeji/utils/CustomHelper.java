package hanliankeji.utils;

import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

/**
 * 拍照辅助工具类，可以在这里进行参数设置，具体可以参考注释
 */
public class CustomHelper {
    private View rootView;

    public static CustomHelper of(View rootView) {
        return new CustomHelper(rootView);
    }

    private CustomHelper(View rootView) {
        this.rootView = rootView;
    }

    /**
     * 拍照  选择图片的操作
     *
     * @param takePhoto
     * @param takeType  0 - 拍照    1- 从相册中选取
     * @param limit     最多选择几张照片
     */
    public void onClick(TakePhoto takePhoto, int takeType, int limit) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        switch (takeType) {
            case 0:
                //调用相机进行拍照
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                break;
            case 1:
                if (limit > 1) {
                    //默认设置为可以剪裁
                    takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
                    return;
                }
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                break;

            default:
                break;
        }
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);//不使用系统的相册
        builder.setCorrectImage(true);//纠正拍照的照片旋转角度
        takePhoto.setTakePhotoOptions(builder.create());

    }

    /**
     * 压缩图片
     *
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(102400)
                .setMaxPixel(800)
                .enableReserveRaw(true)
                .create();
        //设置为不显示压缩对话框
        takePhoto.onEnableCompress(config, false);

    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(800).setAspectY(800);
        builder.setWithOwnCrop(false);
        return builder.create();
    }
}
