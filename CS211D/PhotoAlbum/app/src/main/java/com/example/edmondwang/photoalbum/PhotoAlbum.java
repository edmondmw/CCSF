//Author: Edmond Wang
//Date: 10/3/16
//Homework Assignment: 4
//This project allows us to iterate through a list of images by
// clicking next or prev.
package com.example.edmondwang.photoalbum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PhotoAlbum extends AppCompatActivity
{
    Integer imageIDs[] = {R.drawable.id1, R.drawable.id2, R
            .drawable.id3, R.drawable.id4, R.drawable.id5, R
            .drawable.id6, R.drawable.id7, R.drawable.id8};
    int arrayIndex = 0;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);
        iv = (ImageView)findViewById(R.id.image);
    }

    //****************buttonHandler()*****************************
    public void buttonHandler(View v)
    {
        switch(v.getId())
        {
            case R.id.prevButton:
                arrayIndex--;
                if(arrayIndex < 0)
                {
                    arrayIndex = imageIDs.length - 1;
                }
                break;
            case R.id.nextButton:
                arrayIndex++;
                if(arrayIndex >= imageIDs.length)
                {
                    arrayIndex = 0;
                }
                break;
            default:
                break;
        }

        iv.setImageResource(imageIDs[arrayIndex]);
    }

}
