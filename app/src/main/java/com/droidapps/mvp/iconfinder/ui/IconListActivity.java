package com.droidapps.mvp.iconfinder.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.droidapps.mvp.iconfinder.IconListApplication;
import com.droidapps.mvp.iconfinder.IconListPresenter;
import com.droidapps.mvp.iconfinder.R;
import com.droidapps.mvp.iconfinder.di.IconComponent;
import com.droidapps.mvp.iconfinder.pojo.IconData;
import com.droidapps.mvp.iconfinder.ui.adapter.DownloadListener;
import com.droidapps.mvp.iconfinder.ui.adapter.IconListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IconListActivity extends AppCompatActivity implements IconListView, DownloadListener {

    @Inject
    IconListPresenter presenter;

    @Inject
    IconListAdapter adapter;

    @Inject
    GridLayoutManager layoutManager;

    @BindView(R.id.root)
    View rootView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.loader)
    ProgressBar progressBar;

    @BindView(R.id.paging_loader)
    ProgressBar pagingProgressBar;

    @BindView(R.id.error_tv)
    TextView errorTextView;

    @BindView(R.id.download_progress_bar)
    View downloadProgressBar;

    private String iconDownloadUrl;

    private boolean isDownloading = false;

    private String lastIconId = "";
    private boolean isLoading = false;
    private static final int PERMISSION_CODE = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_list);
        ButterKnife.bind(this);

        setUpInjection();

        setUpAdapter();
        presenter.fetchIconList(lastIconId);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {

                            isLoading = true;
                            presenter.fetchIconList(lastIconId);
                        }
                    }
                }
            }
        });

    }

    private void setUpInjection() {
        IconListApplication application = (IconListApplication) getApplication();
        IconComponent component = application.getIconComponent(this, this, this);
        component.inject(this);
    }

    private void setUpAdapter() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPagingLoader() {
        pagingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePagingLoader() {
        pagingProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showDownloadProgress() {
        downloadProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDownloadProgress() {
        downloadProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        errorTextView.setText(error);
        hideList();
        hideLoader();
        hidePagingLoader();
    }

    @Override
    public void onDownloadProcessDone(int messageRes) {
        isDownloading = false;
        hideDownloadProgress();
        showSnakeBar(getString(messageRes));
    }

    @Override
    public void onDataFetched(List<IconData> iconList) {
        isLoading = false;

        if (iconList != null && iconList.size() > 0)
            lastIconId = iconList.get(iconList.size() - 1).getIconId();

        adapter.setIconList(iconList);
    }

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isDownloading = true;
            presenter.downloadIcon(iconDownloadUrl);
        } else {
            showSnakeBar("Storage permission not granted.");
        }
    }

    private void showSnakeBar(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDownload(String url) {
        iconDownloadUrl = url;
        isDownloading = true;
        if (isWriteStoragePermissionGranted()) presenter.downloadIcon(iconDownloadUrl);
    }

    @Override
    public void onBackPressed() {
        if (isDownloading) {
            isDownloading = false;
            downloadProgressBar.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}
