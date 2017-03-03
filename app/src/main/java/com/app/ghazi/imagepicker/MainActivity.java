package com.app.ghazi.imagepicker;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vlk.multimager.activities.GalleryActivity;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Params;
import com.vlk.multimager.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView mImage;
    private TextView mImageName;
    private TextView mImageSize;
    private TextView mImageMime;
    private Button mSelectImage;
    private Button mUploadImage;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;


    public void initUI(){
        //mImage = (ImageView) findViewById(R.id.imageView1);
        mImageName = (TextView) findViewById(R.id.textView1);
        mImageSize = (TextView) findViewById(R.id.textView2);
        mImageMime = (TextView) findViewById(R.id.textView3);
        mSelectImage = (Button) findViewById(R.id.button1);
        mUploadImage = (Button) findViewById(R.id.button2);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    ApiService service;

    ArrayList<Image> imagesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();


        service = ApiClient.getClient().create(ApiService.class);


        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateMultiPicker();
            }
        });

        mUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.TYPE_MULTI_PICKER:
                imagesList = intent.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
                break;
        }

        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(5,1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        mAdapter = new RecyclerList(getApplicationContext(), imagesList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initiateMultiPicker() {
        Intent intent = new Intent(this, GalleryActivity.class);
        Params params = new Params();
        params.setCaptureLimit(10);
        params.setPickerLimit(10);
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent, Constants.TYPE_MULTI_PICKER);
    }

    private void uploadImage(){
        for(int i=0;i<imagesList.size();i++){
            System.out.println(imagesList.get(i).imagePath);

            File file = new File(imagesList.get(i).imagePath);

            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            Call<ResponseBody> call = service.uploadImage2(fileToUpload, name);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
