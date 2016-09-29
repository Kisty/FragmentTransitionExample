package uk.co.imagitech.fragmenttransitionexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageFragment extends Fragment {
    private static final String ARG_WIDTH = "width";
    private static final String ARG_HEIGHT = "height";
    private static final String ARG_LEFT = "left";
    private static final String ARG_TOP = "top";

    private int mThumbWidth;
    private int mThumbHeight;
    private int mThumbLeft;
    private int mThumbTop;


    public ImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param width
     * @param height
     * @param left
     * @param top
     * @return A new instance of fragment ImageFragment.
     */
    public static ImageFragment newInstance(int width, int height, int left, int top) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_WIDTH, width);
        args.putInt(ARG_HEIGHT, height);
        args.putInt(ARG_LEFT, left);
        args.putInt(ARG_TOP, top);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mThumbWidth = getArguments().getInt(ARG_WIDTH);
            mThumbHeight = getArguments().getInt(ARG_HEIGHT);
            mThumbLeft = getArguments().getInt(ARG_LEFT);
            mThumbTop = getArguments().getInt(ARG_TOP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        if (savedInstanceState == null) {
            final View imageView = view.findViewById(R.id.image);
            imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                    int[] location = new int[2];
                    imageView.getLocationOnScreen(location);
                    int left = location[0];
                    int top = location[1];
                    int leftDelta = mThumbLeft - left;
                    int topDelta = mThumbTop - top;

                    int width = imageView.getWidth();
                    int height = imageView.getHeight();

                    String tag = ImageFragment.class.getSimpleName();
                    Log.d(tag, String.format("tos: width: %d, height: %d, left: %d, top: %d", width, height, left, top));
                    float widthScale = (float) mThumbWidth / width;
                    float heightScale = (float) mThumbHeight / height;

                    Log.d(tag, String.format("widthScale: %f, heightScale: %f, leftDelta: %d, topDelta: %d", widthScale, heightScale, leftDelta, topDelta));

                    //Run animation
                    imageView.setPivotX(0);
                    imageView.setPivotY(0);
                    imageView.setScaleX(widthScale);
                    imageView.setScaleY(heightScale);
                    imageView.setTranslationX(leftDelta);
                    imageView.setTranslationY(topDelta);

                    imageView.animate()
                            .scaleX(1)
                            .scaleY(1)
                            .translationX(0)
                            .translationY(0)
                            .setInterpolator(new DecelerateInterpolator());

                    return true;
                }
            });
        }
        return view;
    }

}
