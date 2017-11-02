package com.framgia.soundcloud.screen.detail;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
import com.framgia.soundcloud.R;

/**
 * Created by anh on 31/10/2017.
 */

public class TrackDownloadManager {
    public static final String CAN_NOT_DOWNLOAD = "cant download this track";

    private final String mAudioType = ".mp3";
    private long downloadReference;
    private Context mContext;
    private String mFileName;
    private String mUrl;

    public TrackDownloadManager(Context context, String fileName, String url) {
        mContext = context;
        mFileName = fileName;
        mUrl = url;
    }

    public void startDownload() {
        DownloadManager downloadManager =
                (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(mUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(mFileName);
        request.setDescription(mContext.getResources().getString(R.string.downloading));
        request.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                mFileName + mAudioType);
        downloadReference = downloadManager.enqueue(request);
        DownloadManager.Query myDownloadQuery = new DownloadManager.Query();
        myDownloadQuery.setFilterById(downloadReference);
        Cursor cursor = downloadManager.query(myDownloadQuery);
        if (cursor.moveToFirst()) {
            checkStatus(cursor);
        }
    }

    private void checkStatus(Cursor cursor) {
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);
        String reasonText = "";
        switch (reason) {
            case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                reasonText = "ERROR_FILE_ALREADY_EXISTS";
                break;
            case DownloadManager.ERROR_FILE_ERROR:
                reasonText = "ERROR_FILE_ERROR";
                break;
            case DownloadManager.ERROR_HTTP_DATA_ERROR:
                reasonText = "ERROR_HTTP_DATA_ERROR";
                break;
            case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                reasonText = "ERROR_INSUFFICIENT_SPACE";
                break;
            case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                break;
            case DownloadManager.ERROR_UNKNOWN:
                reasonText = "ERROR_UNKNOWN";
                break;
            case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                reasonText = "ERROR_DEVICE_NOT_FOUND";
                break;
            default:
                reasonText = CAN_NOT_DOWNLOAD;
                break;
        }
        Toast.makeText(mContext, reasonText, Toast.LENGTH_LONG).show();
    }
}
