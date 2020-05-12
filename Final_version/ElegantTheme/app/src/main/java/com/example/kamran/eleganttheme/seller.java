package com.example.kamran.eleganttheme;


//import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.rey.material.widget.Button;

import java.io.IOException;

import me.drakeet.materialdialog.MaterialDialog;

public class seller extends AppCompatActivity {
    EditText length,width;
    Button btnPickPhoto,upload_btn;
    ImageView imgvPhoto;
    private final int SELECT_PHOTO = 101;
    private final int CAPTURE_PHOTO = 102;
    final private int REQUEST_CODE_WRITE_STORAGE = 1;
    static Uri uri;
    String l;
    int height_n;
    Bitmap yourBitmap ;


    public seller() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller);

        initViews();

    }


    public void initViews(){
        btnPickPhoto = (Button)findViewById(R.id.btn_pick_photo);
        imgvPhoto = (ImageView)findViewById(R.id.imgv_photo);
        upload_btn = (Button)findViewById(R.id.upload);
        length = (EditText) findViewById(R.id.len);/*length */
        width = (EditText) findViewById(R.id.height);/* width */
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"WrongConstant", "ResourceType"})
            @Override
            public void onClick(View v) {
                /**************** Resize photo with proper scale to put it on plan*******************/
                Uri path = Uri.parse("android.resource:// com.example.kamran.eleganttheme/" + R.drawable.ic_face);
                String imgPath = path.toString();
                String url = "C:/Users/15005/AndroidStudioProjects/version1/ElegantTheme/app/src/main/res/drawable/ic_face.xml";
                //float x = imgvPhoto.getScaleX();
                //float y = imgvPhoto.getScaleY();
               l = length.getText().toString();
               int x;
                x = Integer.parseInt(l); // Length in meter token from user input then multiply with 100 to convert to pixels

                Bitmap resized = Bitmap. createScaledBitmap ( yourBitmap , x*50 , 150 , true ) ;
                imgvPhoto .setImageBitmap(resized);
                //imgvPhoto.setScaleX((float) (x + 2));
                //imgvPhoto.setScaleY((float) (y + 0));
                /*Picasso.get()
                        .load(imgPath)
                        .resize(5000, 200)
                        //.resizeDimen(R.dimen.image_size, R.dimen.image_size)
                        //.onlyScaleDown()
                        //.fit()
                        .centerCrop()
                        .into(imgvPhoto);*/
            }
        });
        /*********** select or capture photo from SELECT IMAGE button ***********/
        btnPickPhoto.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                int hasWriteStoragePermission = 0;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hasWriteStoragePermission = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

                if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_WRITE_STORAGE);
                    }
                    //return;
                }

                listDialogue();
            }
        });

    }

    public void listDialogue(){
        final ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        arrayAdapter.add("Take Photo");
        arrayAdapter.add("Select Gallery");

        ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(0);
        listView.setAdapter(arrayAdapter);

        final MaterialDialog alert = new MaterialDialog(this).setContentView(listView);

        alert.setPositiveButton("Cancel", new View.OnClickListener() {
            @Override public void onClick(View v) {
                alert.dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                    alert.dismiss();
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //Uri uri  = Uri.parse("file:///sdcard/photo.jpg");
                    String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "propic.jpg";
                    uri = Uri.parse(root);
                    //i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(i, CAPTURE_PHOTO);

                }else {

                    alert.dismiss();
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);

                }
            }
        });

        alert.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {

                    //Uri imageUri = imageReturnedIntent.getData();
                    //String selectedImagePath = getPath(imageUri);
                    //File f = new File(selectedImagePath);
                    //Bitmap bmp = Compressor.getDefault(this).compressToBitmap(f);

                    //imgvPhoto.setImageURI(imageUri);
                    Uri imageUri = imageReturnedIntent.getData() ;
                    try {
                        yourBitmap = MediaStore.Images.Media. getBitmap ( this .getContentResolver() , imageUri) ;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imgvPhoto .setImageBitmap( yourBitmap ) ;

                }
                break;

            case CAPTURE_PHOTO:
                if (resultCode == RESULT_OK) {

                    Bitmap bmp = imageReturnedIntent.getExtras().getParcelable("data");



                    imgvPhoto.setImageBitmap(bmp);

                }

                break;
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null,
                null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

}