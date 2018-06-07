package com.inu.tmi.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.api.ServerRequestBody;
import com.inu.tmi.api.TMIServer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "TMI.TMIRegisterActivity";
    private static int duplicateCheck = 0;
    private static int GET_PICTURE_URI = 0011;

    EditText ID;
    EditText PW;
    EditText NAME;
    ImageView imageView;
    Bitmap bitmap;
    private static ServerRequestBody requestBody;
    private File user_img = null;
    private String filePath;
    private Uri uri;
    private FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ID = (EditText)findViewById(R.id.inputEmail);
        PW = (EditText)findViewById(R.id.inputPasswd);
        NAME = (EditText)findViewById(R.id.inputName);
        imageView = (ImageView)findViewById(R.id.imageView);


        findViewById(R.id.authorBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ID.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "이메일 입력해라", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.i(TAG, ID.getText().toString());

                    TMIServer.getInstance().emailCheck(ID.getText().toString(), new Callback<ServerRequestBody>() {

                        @Override
                        public void onResponse(Call<ServerRequestBody> call, Response<ServerRequestBody> response) {
                            if(response.body() != null){
                                Log.i(TAG,"응답");
                                String requestBody = response.body().getMsg();
                                Log.i(TAG,"emailCheckBody : "+ requestBody);
                                if(requestBody.equals("duplicate email")){
                                    Toast.makeText(getApplicationContext(),"중복 이메일 입니다.", Toast.LENGTH_SHORT).show();
                                }
                                else if(requestBody.equals("email available")){
                                    Toast.makeText(getApplicationContext(),"사용 가능한 이메일 입니다.", Toast.LENGTH_SHORT).show();
                                    duplicateCheck = 1;
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"잘못된 이메일 입니다." + requestBody, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ServerRequestBody> call, Throwable t) {
                            Log.i(TAG,"실패");
                            Log.i(TAG,t.toString());

                        }
                    });

                    Log.i(TAG,"emailCheck end");
                }
            }
        });


        findViewById(R.id.registerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ID.getText().equals(null) )
                {
                    Toast.makeText(getApplicationContext(),"ID 입력해라", Toast.LENGTH_SHORT).show();
                }
                else if(PW.getText().equals(null))
                {
                    Toast.makeText(getApplicationContext(),"PW 입력해라", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //중복 체크 안했을 때
                    if(duplicateCheck != 1){
                        Toast.makeText(getApplicationContext(),"이메일 중복 체크 하세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //File creating from selected URL
                        if (user_img == null){
                            //분기처리

                        }

                        user_img = new File(filePath);

                        // create RequestBody instance from file
                        File file = createTempFile(bitmap);
                        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("cycle", file.getName(), reqFile);


                        TMIServer.getInstance().signUp(ID.getText().toString(), PW.getText().toString(), NAME.getText().toString(), file, new Callback<ServerRequestBody>() {
                            @Override
                            public void onResponse(Call<ServerRequestBody> call, Response<ServerRequestBody> response) {
                                if(response.body() != null){
                                    Log.i(TAG, " : signup success - ");
                                    ServerRequestBody requestBody = response.body();

                                    if(requestBody.getMsg().equals("success")){
                                        //로그인 성공 시 메인 액티비티로 intent
                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"로그인 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ServerRequestBody> call, Throwable t) {
                                Log.i(TAG, " : register fail - " + t.toString());

                            }
                        });
                    }
                }
            }
        });


        //사진 등록하기
        findViewById(R.id.imageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionCheck();

                //사진가져오기
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_PICTURE_URI);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GET_PICTURE_URI) {

            if (resultCode == Activity.RESULT_OK) {
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    //bitmap = (Bitmap) data.getExtras().get("data");
                    uri = data.getData();

                    filePath = getRealPathFromURIPath(uri, RegisterActivity.this);

                    Log.d(TAG, "Filename " + bitmap);









/*                    //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                    RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());*/

//                   배치해놓은 ImageView에 이미지를 넣어봅시다.
                    imageView.setImageBitmap(rotate(bitmap,0));

                } catch (Exception e) {
                    Log.e("test", e.getMessage());
                }
            }
        }

/*        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, (Activity) getApplicationContext(), new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Toast.makeText(get(), "onImagePickError", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                //Toast.makeText(getContext(), "onImagePicked", Toast.LENGTH_SHORT).show();
                user_img = (File) imageFiles.get(0);
            }


            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Toast.makeText(getContext(), "onCanceld", Toast.LENGTH_SHORT).show();
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getBaseContext());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });*/

    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() +"_image.webp");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.WEBP,0, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public Bitmap rotate(Bitmap bitmap, int degrees)
    {
        if(degrees != 0 && bitmap != null)
        {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);

            try
            {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if(bitmap != converted)
                {
                    bitmap.recycle();
                    bitmap = converted;
                }
            }
            catch(OutOfMemoryError ex)
            {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return bitmap;
    }

   /* private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }
*/
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public void permissionCheck() {
        int permFineLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permCoaLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permAlbum = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permFineLoc != PackageManager.PERMISSION_GRANTED && permCoaLoc != PackageManager.PERMISSION_GRANTED && permAlbum != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }


}
