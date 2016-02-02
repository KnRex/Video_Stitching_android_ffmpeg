package com.genie.videostitchingexample;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.genie.core.FfmpegManager;
import com.genie.listeners.CompletionListener;
import com.genie.utils.VideoStitchingRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AddVideoActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    private static final int PICK_VIDEO_FROM_GALLERY = 102;
    ArrayList<String> inputArrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        listView = (ListView) findViewById(R.id.add_listview);
        View view=((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer,null,false);
        Button btn= (Button) view.findViewById(R.id.add_video);
        btn.setOnClickListener(this);
        listView.addFooterView(view);
        progressDialog=new ProgressDialog(this);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, inputArrayList);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else{
            progressDialog.setMessage("Progressing...");
            progressDialog.show();
            stitchVideo();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(
                    Intent.createChooser(
                            intent, "Complete action using"),
                    PICK_VIDEO_FROM_GALLERY);
        } else {

            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("video/*");
            intent.putExtra("return-data", true);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            List<ResolveInfo> list = getApplicationContext()
                    .getPackageManager().queryIntentActivities(
                            intent,
                            PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() <= 0) {
                return;
            }
            startActivityForResult(intent, PICK_VIDEO_FROM_GALLERY);
        }

    }


    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri originalUri = null;


            if (requestCode == PICK_VIDEO_FROM_GALLERY) {
                Uri uri = data.getData();
                String path = Utils.getPath(AddVideoActivity.this, uri);
                inputArrayList.add(path);
                adapter.notifyDataSetChanged();

            }

        }
    }


    private void stitchVideo() {
        String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ffmpeg_videos";
        File dir = new File(videoPath);
        dir.mkdirs();
        final File file = new File(dir, "stitched_video_" + System.currentTimeMillis() + ".mp4");
        Log.d("path", file.getAbsolutePath());

        VideoStitchingRequest videoStitchingRequest = new VideoStitchingRequest.Builder().inputVideoFilePath(inputArrayList).outputPath(file.getAbsolutePath()).build();
        FfmpegManager manager = FfmpegManager.getInstance();
        manager.stitchVideos(AddVideoActivity.this, videoStitchingRequest, new CompletionListener() {
            @Override
            public void onProcessCompleted(String message) {
                Log.d("message", message);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(file.getAbsolutePath()));
                intent.setDataAndType(Uri.parse(file.getAbsolutePath()), "video/mp4");
                startActivity(intent);
                progressDialog.cancel();
            }
        });
    }


}
