package com.fmanda.autopartdashboard.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.fmanda.autopartdashboard.helper.DBHelper;
import com.fmanda.autopartdashboard.helper.GsonRequest;
import com.fmanda.autopartdashboard.model.ModelProject;
import com.fmanda.autopartdashboard.model.ModelSetting;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;



public class ControllerRest {
    private Context context;
    private DBHelper db;
    private ControllerSetting controllerSetting;

//    public String base_url;

//    private static ControllerRest mInstance;
//
//    public static synchronized ControllerRest getInstance(Context context) {
//        if (mInstance == null) {
//            mInstance = new ControllerRest(context.getApplicationContext());
//        }
//        return mInstance;
//    }

    private int user_id;

    private ControllerRequest controllerRequest;
    protected Listener listener;


    public interface Listener {
        void onSuccess(String msg);
        void onError(String msg);
    }

    private AsyncTaskListener asyncTaskListener;

    public interface AsyncTaskListener {
        void onProgressUpdate(String msg);
    }


    public void setListener(ControllerRest.Listener listener) {
        this.listener = listener;
    }

    public void setAsyncTaskListenerListener(ControllerRest.AsyncTaskListener listener) {
        this.asyncTaskListener = listener;
    }

    public ControllerRest(Context context) {
        this.context = context;
        this.db = DBHelper.getInstance(context);
        this.controllerRequest = ControllerRequest.getInstance(context);
        this.controllerSetting = new ControllerSetting((this.context));
        this.user_id = controllerSetting.getUserID();

//        this.base_url = "http://" + controllerSetting.getSettingStr("rest_url") + "/";
    }

    protected String base_url(){
        return "http://" + controllerSetting.getSettingStr("rest_url") + "/";
    }

    public String url_project(){
        return base_url() + "project";
    }

    private void log(String s) {
        asyncTaskListener.onProgressUpdate(s);
    }

    private void SaveProject(ModelProject[] projects){
        db.getWritableDatabase().execSQL(new ModelProject().generateSQLDelete("")); //always delete all record projects
        try {
            for (ModelProject project : projects) {
//                project.setIDFromUID(db.getReadableDatabase(), project.getUid());
                project.saveToDB(db.getWritableDatabase());
                if (project.getId() == 0) {
                    log("[project] " + project.getProjectname() + " inserted");
                }else{
                    log("[project] " + project.getProjectname() + " updated");
                }
            }
        }catch(Exception e){
            log(e.toString());
        }
    }

    public void DownloadProject(Boolean async){
        if (async) {
            GsonRequest<ModelProject[]> gsonReq = new GsonRequest<>(url_project(), ModelProject[].class,
                    new Response.Listener<ModelProject[]>() {
                        @Override
                        public void onResponse(ModelProject[] response) {
                            SaveProject(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            log(error.toString());
                        }
                    }
            );
            this.controllerRequest.addToRequestQueue(gsonReq, url_project());
        }else {
            RequestFuture<ModelProject[]> future = RequestFuture.newFuture();
            GsonRequest<ModelProject[]> gsonReq = new GsonRequest<>(url_project(), ModelProject[].class, future, future);
            this.controllerRequest.addToRequestQueue(gsonReq, url_project());

            try {
                ModelProject[] response = future.get(10, TimeUnit.SECONDS);
                SaveProject(response);
            } catch (InterruptedException|ExecutionException| TimeoutException e) {
                log(e.getMessage());
            }
        }
    }

    public void SyncData(final Boolean async){
        AsyncRestRunner runner = new AsyncRestRunner(this);
        runner.execute(async);
    }
}


class AsyncRestRunner extends AsyncTask<Boolean, String, Void> {
    private ControllerRest controllerRest;

    AsyncRestRunner(ControllerRest controllerRest) {
        this.controllerRest = controllerRest;
        this.controllerRest.setAsyncTaskListenerListener(new ControllerRest.AsyncTaskListener() {
            @Override
            public void onProgressUpdate(String msg) {
                publishProgress(msg);
            }
        });
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        controllerRest.listener.onSuccess("FINISH");
    }

    @Override
    protected void onProgressUpdate(String... values) {
        controllerRest.listener.onSuccess(values[0]);
    }

    @Override
    protected Void doInBackground(Boolean... booleans) {
        boolean async = booleans[0];
        publishProgress("Connecting to Rest API : " + controllerRest.base_url());
//        publishProgress("Upload Detail Order");
//        controllerRest.UploadDetailOrder(async);

//        publishProgress("Download Project");
        controllerRest.DownloadProject(async);
//        controllerRest.DownloadMaterial(async);
//        controllerRest.DownloadProduct(async);
//        controllerRest.DownloadCustomer(async);
//        publishProgress("Download Order");
//        controllerRest.DownloadOrder(async);
        return null;
    }
}




//PRODUCT
//    public void DownloadProducts(){
//        GsonRequest<ModelProduct[]> gsonReq = new GsonRequest<ModelProduct[]>(product_get_url(), ModelProduct[].class,
//                new Response.Listener<ModelProduct[]>() {
//                    @Override
//                    public void onResponse(ModelProduct[] response) {
//                        try {
//                            ImageHelper img = new ImageHelper(context);
//                            for (ModelProduct prod : response) {
//                                prod.setIDFromUID(db.getReadableDatabase(), prod.getUid());
//
//                                ModelProduct oldProduct = new ModelProduct();
//                                oldProduct.loadFromDB(db.getReadableDatabase(), prod.getId());
//
//                                //delete old modifier
//                                Boolean isFound = false;
//                                for (ModelModifier oldmodifier : oldProduct.modifiers){
//                                    isFound = false;
//                                    for (ModelModifier newmodifier : prod.modifiers) {
//                                        if (newmodifier.getUid() == oldmodifier.getUid()){
//                                            isFound = true;
//                                            continue;
//                                        }
//                                    }
//                                    if (!isFound) oldmodifier.deleteFromDB(db.getWritableDatabase());
//                                }
//
//                                prod.setImg(prod.getImg().replace(".jpg","")); //server contain .jpg
//                                prod.saveToDB(db.getWritableDatabase());
//
//                                for (ModelModifier modelModifier : prod.modifiers){
//                                    modelModifier.setIDFromUID(db.getReadableDatabase(), modelModifier.getUid());
//                                    modelModifier.setProduct_id(prod.getId());
//                                    modelModifier.saveToDB(db.getWritableDatabase());
//                                }
//
//
//                                //replace format to png
//                                if (!prod.getImg().equals("")) {
//                                    img.setFileName(prod.getImg());
//                                    if (!img.checFileExist()) {
//                                        DownloadProductImage(prod.getImg());
//                                    }
//                                }
//                                listener.onSuccess(prod.getName() + " updated");
//                            }
//                        }catch(Exception e){
//                            listener.onError(e.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        listener.onError(error.toString());
//                    }
//                }
//        );
//        this.controllerRequest.addToRequestQueue(gsonReq,product_get_url());
//    }


//    public void UploadOrders(){
//        ControllerOrder controllerorder = new ControllerOrder(this.context);
//        List<ModelOrder> orders = controllerorder.getModifiedOrders();
//        final SQLiteDatabase db = DBHelper.getInstance(this.context).getReadableDatabase();
//        final SQLiteDatabase dbw = DBHelper.getInstance(this.context).getWritableDatabase();
//
//        //tambahkan yg diedit saja
//
////        GsonBuilder gsonBuilder;
////        Gson gson;
////        gsonBuilder = new GsonBuilder().setDateFormat("yyyy-mm-dd hh:mm:ss");
////        gson = gsonBuilder.create();
//
//        for (final ModelOrder modelOrder : orders) {
//            modelOrder.prepareUpload(db);
//            modelOrder.setCompany_id(this.company_id);
//            modelOrder.setUnit_id(this.unit_id);
////            Toast.makeText(context, debug, Toast.LENGTH_SHORT).show();
//
//            GsonRequest<ModelOrder> gsonReq = new GsonRequest<ModelOrder>(order_post_url(), modelOrder,
//                    new Response.Listener<ModelOrder>() {
//                        @Override
//                        public void onResponse(ModelOrder response) {
//                            //update ID
//                            try{
//                                modelOrder.setuid(response.getuid());
//                                modelOrder.setUploaded(1);
//                                modelOrder.saveToDB(dbw);
//                                listener.onSuccess(response.getOrderno() + " updated");
//                            }catch (Exception e){
//                                listener.onError(e.toString());
//                            }
//
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            listener.onError(error.toString());
//                        }
//                    }
//            );
//            this.controllerRequest.addToRequestQueue(gsonReq,customer_post_url());
//        }
//
//        //test
//
//
//    }
//
//
//




//    public void DownloadProductImage(final String img_name){
//        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
//        String get_url = image_get_url(img_name);
//
//        // Initialize a new ImageRequest
//        ImageRequest imageRequest = new ImageRequest(
//                get_url, // Image URL
//                new Response.Listener<Bitmap>() { // Bitmap listener
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        // Do something with response
//                        ImageHelper img = new ImageHelper(context);
//                        img.setFileName(img_name);
//                        img.save(response);
//                    }
//                },
//                0, // Image width
//                0, // Image height
//                CENTER_CROP, // Image scale type
//                Bitmap.Config.RGB_565, //Image decode configuration
//                new Response.ErrorListener() { // Error listener
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Do something with error response
//                        listener.onError(error.toString());
////                        error.printStackTrace();
//                    }
//                }
//        );
//
//        this.controllerRequest.addToRequestQueue(imageRequest,image_get_url(img_name));
//    }