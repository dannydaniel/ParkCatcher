/*
    Park Catcher Montréal
    Find a free parking in the nearest residential street when driving in
    Montréal. A Montréal Open Data project.

    Copyright (C) 2012 Mudar Noufal <mn@mudar.ca>

    This file is part of Park Catcher Montréal.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ca.mudar.parkcatcher.ui.fragments;

import ca.mudar.parkcatcher.Const;
import ca.mudar.parkcatcher.Const.HelpPages;
import ca.mudar.parkcatcher.R;

import com.actionbarsherlock.app.SherlockFragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class HelpFragment extends SherlockFragment {
    private static final String TAG = "HelpFragment";

    public static HelpFragment newInstance(int index) {
        HelpFragment fragment = new HelpFragment();

        fragment.index = index;

        return fragment;
    }

    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null)
                && savedInstanceState.containsKey(Const.KEY_BUNDLE_HELP_INDEX)) {
            index = savedInstanceState.getInt(Const.KEY_BUNDLE_HELP_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int res;
        switch (index) {
            case HelpPages.RULES:
                res = R.layout.fragment_help_rules;
                break;
            case HelpPages.STOPPING:
                res = R.layout.fragment_help_stopping;
                break;
            case HelpPages.PARKING:
                res = R.layout.fragment_help_parking;
                break;
            case HelpPages.RESTRICTED:
                res = R.layout.fragment_help_restricted;
                break;
            case HelpPages.SRRR:
                res = R.layout.fragment_help_srrr;
                break;
            case HelpPages.CELL:
                res = R.layout.fragment_help_cell;
                break;
            case HelpPages.ARROW:
                res = R.layout.fragment_help_arrow;
                break;
            case HelpPages.PRIORITY:
                res = R.layout.fragment_help_priority;
                break;
            // case HelpPages.APP:
            default:
                res = R.layout.fragment_help_app;
                break;
        }

        return inflater.inflate(res, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Const.KEY_BUNDLE_HELP_INDEX, index);
    }

    public void startLoadingImages(View view, Resources res) {

        loadBitmap(R.drawable.help_arrow_sw_left,
                (ImageView) view.findViewById(R.id.help_arrow_sw_left), res);
        loadBitmap(R.drawable.help_arrow_ne_left,
                (ImageView) view.findViewById(R.id.help_arrow_ne_left), res);
        loadBitmap(R.drawable.help_arrow_se_right,
                (ImageView) view.findViewById(R.id.help_arrow_se_right), res);
        loadBitmap(R.drawable.help_arrow_nw_right,
                (ImageView) view.findViewById(R.id.help_arrow_nw_right), res);

        loadBitmap(R.drawable.help_arrow_ne_right,
                (ImageView) view.findViewById(R.id.help_arrow_ne_right), res);
        loadBitmap(R.drawable.help_arrow_sw_right,
                (ImageView) view.findViewById(R.id.help_arrow_sw_right), res);
        loadBitmap(R.drawable.help_arrow_nw_left,
                (ImageView) view.findViewById(R.id.help_arrow_nw_left), res);
        loadBitmap(R.drawable.help_arrow_se_left,
                (ImageView) view.findViewById(R.id.help_arrow_se_left), res);
    }

    public void loadBitmap(int resId, ImageView imageView, Resources res) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, res);
        task.execute(resId);
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;
        private Resources res;

        public BitmapWorkerTask(ImageView imageView, Resources resources) {
            // Use a WeakReference to ensure the ImageView can be garbage
            // collected
            imageViewReference = new WeakReference<ImageView>(imageView);
            res = resources;
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            return BitmapFactory.decodeResource(res, data);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
