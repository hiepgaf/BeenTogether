package net.kenit.hieptran.beentogether.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.kenit.hieptran.beentogether.R;
import net.kenit.hieptran.beentogether.model.Lover;
import net.kenit.hieptran.beentogether.utils.Utils;
import net.kenit.hieptran.beentogether.utils.cropimage.FileUtils;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hiepthb on 04/07/2016.
 */
public class SetUpActivity extends AppCompatActivity {
    private static final int REQUEST_GALLERY = 21;
    private static final int REQUEST_CAMERA = 20;
    private static final int REQUEST_CROP = 22;
    private static final int REQUEST_GALLERY_FEMALE = 23;
    private static final int REQUEST_CAMERA_FEMALE = 24;
    private static final int REQUEST_CROP_FEMALE = 25;
    private String mCurrentPhotoPath;
    private CircleImageView maleImage, femaleImage;
    private EditText maleEdt, femaleEdt;
    private AppCompatButton mBtnSetCover, mBtnSetDate;
    private AppCompatTextView mTxtCover, mTxtDate;
    private Gson mGson;
    private Lover mMaleLover, mFemaleLover;
    private String mAvataMale, mAvataFemale;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        maleImage = (CircleImageView) findViewById(R.id.profile_image_male);
        femaleImage = (CircleImageView) findViewById(R.id.profile_image_female);
        maleEdt = (EditText) findViewById(R.id.name_male);
        femaleEdt = (EditText) findViewById(R.id.name_female);
        mBtnSetCover = (AppCompatButton) findViewById(R.id.btn_set_cover);
        mBtnSetDate = (AppCompatButton) findViewById(R.id.btn_set_date);
        mTxtCover = (AppCompatTextView) findViewById(R.id.txt_cover_info);
        mTxtDate = (AppCompatTextView) findViewById(R.id.txt_date_info);
        maleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog(Utils.LOVER_TYPE.MALE);
            }
        });
        femaleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog(Utils.LOVER_TYPE.FEMALE);
            }
        });
        mMaleLover = new Lover();
        mFemaleLover = new Lover();
        mGson = new GsonBuilder().create();
    }

    private void showPhotoDialog(final Utils.LOVER_TYPE type) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setItems(new String[]{"Chụp ảnh", "Chọn từ thư viện", "Hủy"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == 0) {
                    dispatchTakePictureIntent(type);
                } else if (which == 1) {
                    startGalleryIntent(type);
                }

            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK)
                    goCrop(Uri.parse(mCurrentPhotoPath), Utils.LOVER_TYPE.MALE);
                break;
            case REQUEST_CAMERA_FEMALE:
                if (resultCode == RESULT_OK)
                    goCrop(Uri.parse(mCurrentPhotoPath), Utils.LOVER_TYPE.FEMALE);
                break;
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK)
                    goCrop(data.getData(), Utils.LOVER_TYPE.MALE);
                break;
            case REQUEST_GALLERY_FEMALE:
                if (resultCode == RESULT_OK)
                    goCrop(data.getData(), Utils.LOVER_TYPE.FEMALE);
                break;
            case REQUEST_CROP:
                if (resultCode == RESULT_OK)
                    maleImage.setImageURI(data.getData());
                mAvataMale = Utils.saveAvatar(Utils.LOVER_TYPE.MALE, maleImage);
                break;
            case REQUEST_CROP_FEMALE:
                if (resultCode == RESULT_OK)
                    femaleImage.setImageURI(data.getData());
                mAvataFemale = Utils.saveAvatar(Utils.LOVER_TYPE.MALE, maleImage);
                break;
        }
        /*if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            goCrop(Uri.parse(mCurrentPhotoPath));
        } else if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            goCrop(data.getData());
        } else if (requestCode == REQUEST_CROP && resultCode == RESULT_OK) {
            maleImage.setImageURI(data.getData());
        }*/
    }

    private void goCrop(Uri sourUri, Utils.LOVER_TYPE type) {
        Intent intent = new Intent(SetUpActivity.this, CropActivity.class);
        intent.setData(sourUri);
        switch (type) {
            case MALE:
                startActivityForResult(intent, REQUEST_CROP);

                break;
            case FEMALE:
                startActivityForResult(intent, REQUEST_CROP_FEMALE);

                break;
        }
    }

    private void startGalleryIntent(Utils.LOVER_TYPE type) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        switch (type) {
            case MALE:
                startActivityForResult(intent, REQUEST_GALLERY);

                break;
            case FEMALE:
                startActivityForResult(intent, REQUEST_GALLERY_FEMALE);

                break;
        }
    }

    private void dispatchTakePictureIntent(Utils.LOVER_TYPE type) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile;
            photoFile = FileUtils.getOutputMediaFileUri();
            mCurrentPhotoPath = "file:" + photoFile.getAbsolutePath();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                switch (type) {
                    case MALE:
                        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                        break;
                    case FEMALE:
                        startActivityForResult(takePictureIntent, REQUEST_CAMERA_FEMALE);
                        break;
                }
            }
        }
    }
}
