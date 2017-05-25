package com.example.katarzyna.affective.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.example.katarzyna.affective.R;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by katarzyna on 09.05.17.
 */

public class VideoFragment extends Fragment  implements Detector.ImageListener, CameraDetector.CameraEventListener{

    private static String TAG = VideoFragment.class.getSimpleName();


    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.videoTitle)
    TextView videoTitle;
    @BindView(R.id.describtion)
    TextView describtion;
    @BindView(R.id.actionButton)
    Button actionButton;

    SurfaceView cameraPreview;

    boolean isCameraBack = false;
    boolean isSDKStarted = false;

    RelativeLayout mainLayout;

    CameraDetector detector;

    int previewWidth = 0;
    int previewHeight = 0;
//todo json


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public VideoFragment(){

    }

    public static VideoFragment newInstance(String videoTitle) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView Called");

        View v = inflater.inflate(R.layout.watchvideo_fragment_layout, container, false);
        ButterKnife.bind(v);

        videoView.setVideoURI(Uri.parse("http://www.semanticdevlab.com/abc.mp4"));
        videoView.setMediaController(new MediaController(getContext()));
        videoView.requestFocus();
        videoView.start();
        describtion.setText("kokoko");

        cameraPreview = new SurfaceView(getContext()) {
            @Override
            public void onMeasure(int widthSpec, int heightSpec) {
                int measureWidth = MeasureSpec.getSize(widthSpec);
                int measureHeight = MeasureSpec.getSize(heightSpec);
                int width;
                int height;
                if (previewHeight == 0 || previewWidth == 0) {
                    width = measureWidth;
                    height = measureHeight;
                } else {
                    float viewAspectRatio = (float) measureWidth / measureHeight;
                    float cameraPreviewAspectRatio = (float) previewWidth / previewHeight;

                    if (cameraPreviewAspectRatio > viewAspectRatio) {
                        width = measureWidth;
                        height = (int) (measureWidth / cameraPreviewAspectRatio);
                    } else {
                        width = (int) (measureHeight * cameraPreviewAspectRatio);
                        height = measureHeight;
                    }
                }
                setMeasuredDimension(width, height);
            }
        };
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        detector = new CameraDetector(getContext(), CameraDetector.CameraType.CAMERA_BACK, videoView);
        detector.setDetectSmile(true);
        detector.setImageListener(this);
        detector.setOnCameraEventListener(this);


        return v;
    }

    @OnClick(R.id.actionButton)
    public void startEmocionRecognition() {
        Log.d(TAG, "pushed");
        if (isSDKStarted) {
            isSDKStarted = false;
            stopDetector();
            describtion.setText("Stop Camera");
        } else {
            isSDKStarted = true;
            startDetector();
            describtion.setText("Start Camera");
        }
    }



    void startDetector() {
        if (!detector.isRunning()) {
            detector.start();
        }
    }

    void stopDetector() {
        if (detector.isRunning()) {
            detector.stop();
        }
    }

    void switchCamera(CameraDetector.CameraType type) {
        detector.setCameraType(type);
    }

    @Override
    public void onImageResults(List<Face> list, Frame frame, float v) {
        if (list == null || list.size() ==0)
            return;
         else {
            Face face = list.get(0);
            describtion.setText(String.format("SMILE\n%.2f", face.expressions.getSmile()));
            Log.d(TAG, String.format("SMILE\n%.2f", face.expressions.getSmile()));


        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void onCameraSizeSelected(int width, int height, Frame.ROTATE rotate) {
        if (rotate == Frame.ROTATE.BY_90_CCW || rotate == Frame.ROTATE.BY_90_CW) {
            previewWidth = height;
            previewHeight = width;
        } else {
            previewHeight = height;
            previewWidth = width;
        }
        cameraPreview.requestLayout();
    }
}

