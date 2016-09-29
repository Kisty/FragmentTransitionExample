package uk.co.imagitech.fragmenttransitionexample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showThumbnail(View view) {
        View image = findViewById(R.id.image);
        int[] location = new int[2];
        image.getLocationOnScreen(location);

        int width = image.getWidth();
        int height = image.getHeight();
        int left = location[0];
        int top = location[1];
        String tag = MainActivity.class.getSimpleName();
        Log.d(tag, String.format("froms: width: %d, height: %d, left: %d, top: %d", width, height, left, top));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack("image", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .add(R.id.activity_main, ImageFragment.newInstance(width, height, left, top))
                .addToBackStack("image")
                .commit();
    }
}
