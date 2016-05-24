package com.takeiteasy.vip.pagerextramargins;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);

        ViewCompat.offsetTopAndBottom(pager, 20);

        ViewCompat.setOnApplyWindowInsetsListener(pager, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                Log.d(TAG, "onApplyWindowInsets: ");
                Log.d(TAG, "onApplyWindowInsets: stable " + insets.hasStableInsets());
                Log.d(TAG, "onApplyWindowInsets: system " + insets.hasSystemWindowInsets());
                Log.d(TAG, "onApplyWindowInsets: system left - " + insets.getSystemWindowInsetLeft());
                Log.d(TAG, "onApplyWindowInsets: system top - " + insets.getSystemWindowInsetTop());
                Log.d(TAG, "onApplyWindowInsets: system right - " + insets.getSystemWindowInsetRight());
                Log.d(TAG, "onApplyWindowInsets: system bottom - " + insets.getSystemWindowInsetBottom());
                WindowInsetsCompat buf = insets.replaceSystemWindowInsets(
                        insets.getSystemWindowInsetLeft(),
                        insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(),
                        insets.getSystemWindowInsetBottom() + insets.getSystemWindowInsetTop());
                Log.d(TAG, "onApplyWindowInsets: system bottom - " + buf.getSystemWindowInsetBottom());

                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) pager.getLayoutParams();
                lp.setMargins(0, 0, 0, insets.getSystemWindowInsetTop());
                pager.setLayoutParams(lp);
                return insets;
            }
        });

        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return new MyFragment();
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

    public static class MyFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment, container, false);

            RecyclerView recyclerView = (RecyclerView) v;

            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(manager);

            recyclerView.setAdapter(new MyAdapter());

            return v;
        }

        public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false));
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                holder.textView.setText("ITEM - " + position);
            }

            @Override
            public int getItemCount() {
                return 5;
            }

            public class ViewHolder extends RecyclerView.ViewHolder {

                private TextView textView;

                public ViewHolder(View itemView) {
                    super(itemView);

                    textView = (TextView) itemView.findViewById(R.id.text);
                }
            }
        }
    }
}
