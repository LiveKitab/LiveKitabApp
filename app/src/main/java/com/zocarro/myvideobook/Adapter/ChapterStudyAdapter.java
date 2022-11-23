package com.zocarro.myvideobook.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.zocarro.myvideobook.Activity.StudymaterialshowActivity;
import com.zocarro.myvideobook.Model.chaptermaterialmodel;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.VideoCourse.ViewCourseVideo;

import java.io.File;
import java.util.ArrayList;

public class ChapterStudyAdapter extends RecyclerView.Adapter<ChapterStudyAdapter.VideoCourseHolder>
{
    Context ctx;
    ArrayList<chaptermaterialmodel> videoViewModelArrayList;
    String video_id;
    private ProgressDialog pDialog;
    String dwnload_file_path,file,fileName;


    boolean isPurchased = false;

    public ChapterStudyAdapter(Context ctx, ArrayList<chaptermaterialmodel> videoViewModelArrayList)
    {
        this.ctx = ctx;
        this.videoViewModelArrayList = videoViewModelArrayList;
        this.video_id = "UNKOWN";
        this.isPurchased = true;
    }
    @NonNull
    @Override
    public VideoCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_study_material_video_rows,null);
        return new VideoCourseHolder(myview);
    }
    @Override
    public void onBindViewHolder(@NonNull VideoCourseHolder holder, int position)
    {
        final chaptermaterialmodel videoViewModel=videoViewModelArrayList.get(position);
        VideoCourseHolder container=(VideoCourseHolder) holder;
        container.txtvideoTitle.setText(videoViewModel.getV_title());
//        container.txtChapName.setText(videoViewModel.getCh_name());
        container.videodescription.setText(videoViewModel.getV_des());
        container.sequence.setText(videoViewModel.getSequence());
//        img_url="http://img.youtube.com/vi/" + videolink + "/0.jpg";
        String img_url = "http://img.youtube.com/vi/" + videoViewModel.getV_link() +"/0.jpg";
        Log.d("image_url1",img_url);
        Glide.with(ctx).load(img_url).into(container.imageView);
        container.cardvideoview.setOnClickListener(v ->
        {


            if (isPurchased)
            {
                Intent intent=new Intent(ctx, ViewCourseVideo.class);
                intent.putExtra("c_id", videoViewModel.getC_id());
                intent.putExtra("courseTitle",videoViewModel.getV_title());
                intent.putExtra("courseDesc", videoViewModel.getV_des());
                intent.putExtra("sub_id", videoViewModel.getSub_id());
                intent.putExtra("v_id", videoViewModel.getV_id());
                intent.putExtra("v_link", videoViewModel.getV_link());
                intent.putExtra("subject_name","UNKNOWN");
                intent.putExtra("subject_code","UNKNOWN");
                intent.putExtra("chap_id",videoViewModel.getChap_id());
                intent.putExtra("chap_name",videoViewModel.getChap_name());
                intent.putExtra("file",videoViewModel.getFile());
                Log.d("filessss",videoViewModel.getFile());

                Log.d("v_link123",videoViewModel.getV_link());
                Log.d("v_id123",videoViewModel.getV_id());
                intent.putExtra("pos",position);
                ctx.startActivity(intent);
                ((Activity)ctx).finish();
            }
            else {
                Toast.makeText(ctx, "Please enroll in this course to continue",Toast.LENGTH_SHORT).show();
            }


//                Intent intent=new Intent(ctx, StudymaterialshowActivity.class);
//                intent.putExtra("file",videoViewModel.getFile());
//                intent.putExtra("chap_name", videoViewModel.getChap_name());
//                ctx.startActivity(intent);
//                ((Activity)ctx).finish();
        });
        container.downloadmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("TAG", "onClick: " + "called");
                dwnload_file_path = videoViewModel.getFile();
                String url = null;
                url =  videoViewModel.getFile();
                String filename1 = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));
                String fileExtension = url.substring(url.lastIndexOf("."));
                file = filename1 + fileExtension;
                Log.d("files", file);
                Log.d("files", videoViewModel.getFile());
                pDialog = new ProgressDialog(ctx);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                downloadPDF();
            }
        });
    }
    public void downloadPDF()
    {
        new DownloadFile().execute(dwnload_file_path, file);
    }

    public void viewPDF(String fileName)
    {
        Log.d("file", fileName);
        String filess =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;
        File pdfFile = new File(filess);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            ctx.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ctx, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showpDialog();
        }

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];

            fileName = strings[1];
//            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//            File folder = new File(extStorageDirectory);
//            folder.mkdir();
//
//            File pdfFile = new File(folder, fileName);
//
//            try{
//                pdfFile.createNewFile();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            FileDownloader.downloadFile(fileUrl, pdfFile);

            if(ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                // this will request for permission when user has not granted permission for the app
                ActivityCompat.requestPermissions((AppCompatActivity)ctx, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            else{
                //Download Script
                DownloadManager downloadManager = (DownloadManager)ctx.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(fileUrl);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setVisibleInDownloadsUi(true);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                File folder = new File(Environment.getExternalStorageDirectory() +
//                        File.separator + "Videobook");
//                boolean success = true;
//                if (!folder.exists()) {
//                    success = folder.mkdirs();
//                }
//                if (success)
//                {
//                    // Do something on success
//                } else {
//                    // Do something else on failure
//                }
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());
//                request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory() + "/videobook", uri.getLastPathSegment());
                downloadManager.enqueue(request);
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            hidepDialog();
            Toast.makeText(ctx, "Download PDf successfully", Toast.LENGTH_SHORT).show();
//            viewPDF(fileName);

            Log.d("Download complete", "----------");
        }
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public int getItemCount()
    {
        return videoViewModelArrayList.size();
    }

    class VideoCourseHolder extends RecyclerView.ViewHolder
    {
        TextView txtvideoTitle,txtChapName,videodescription,sequence;
        ImageView imageView;
        MaterialCardView cardvideoview;
        RelativeLayout relCard;
        ImageButton downloadmaterial;
        public VideoCourseHolder(@NonNull View itemView)
        {
            super(itemView);
            txtvideoTitle=itemView.findViewById(R.id.videotitle);
//            txtvideoName=itemView.findViewById(R.id.txtvideoName);
//            txtChapName=itemView.findViewById(R.id.txtChapName);
            imageView=itemView.findViewById(R.id.imageView);
            cardvideoview=itemView.findViewById(R.id.cardvideoview);
            relCard=itemView.findViewById(R.id.relCard);
            videodescription = itemView.findViewById(R.id.videodescription);
            downloadmaterial = itemView.findViewById(R.id.downloadmaterial);
            sequence = itemView.findViewById(R.id.sequence);


        }
    }
}
