package com.fmanda.autopartdashboard.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.fmanda.autopartdashboard.helper.DBHelper;
import com.fmanda.autopartdashboard.helper.GsonRequest;
import com.fmanda.autopartdashboard.model.ModelAPAging;
import com.fmanda.autopartdashboard.model.ModelCashFlow;
import com.fmanda.autopartdashboard.model.ModelInventory;
import com.fmanda.autopartdashboard.model.ModelProfitLoss;
import com.fmanda.autopartdashboard.model.ModelProject;
import com.fmanda.autopartdashboard.model.ModelSalesPeriod;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        void onError(String msg); //beware using this on asynctask, u will get exception
        void onProgress(String msg);
    }

    private AsyncTaskListener asyncTaskListener;

    public interface AsyncTaskListener {
        void onProgressUpdate(String...msg);
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

    public String url_profitloss(){
        return base_url() + "profitloss";
    }

    public String url_cashflow(){
        return base_url() + "cashflow";
    }


    public String url_salesperiod(){
        return base_url() + "salesperiod";
    }

    private void log(String...s) {
        if (asyncTaskListener != null)
            asyncTaskListener.onProgressUpdate(s);
    }

    private void SaveProject(ModelProject[] projects){
        try {
            db.getWritableDatabase().execSQL(new ModelProject().generateSQLDelete("")); //always delete all record projects
            for (ModelProject project : projects) {
//                project.setIDFromUID(db.getReadableDatabase(), project.getUid());
                project.setId(0);
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

    public boolean DownloadProject(Boolean async){
        if (async) {
            GsonRequest<ModelProject[]> gsonReq = new GsonRequest<>(url_project(), ModelProject[].class,
                    new Response.Listener<ModelProject[]>() {
                        @Override
                        public void onResponse(ModelProject[] response) {
                            SaveProject(response);
                            listener.onSuccess("");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            log(error.toString());
                            listener.onError(error.toString());
                        }
                    }
            );
            this.controllerRequest.addToRequestQueue(gsonReq, url_project());
            return true;
        }else {
            RequestFuture<ModelProject[]> future = RequestFuture.newFuture();
            GsonRequest<ModelProject[]> gsonReq = new GsonRequest<>(url_project(), ModelProject[].class, future, future);
            this.controllerRequest.addToRequestQueue(gsonReq, url_project());

            try {
                ModelProject[] response = future.get(10, TimeUnit.SECONDS);
                SaveProject(response);
            } catch (InterruptedException|ExecutionException| TimeoutException e) {
                log(e.getMessage(),"error");
                return false;
            }
            return true;
        }
    }

    private void SaveProfitLoss(ModelProfitLoss[] profits, int monthperiod, int yearperiod){
        try {
            db.getWritableDatabase().execSQL(new ModelProfitLoss().generateSQLDelete
                    ("where monthperiod = " + String.valueOf(monthperiod)
                    + " and yearperiod = " + String.valueOf(yearperiod))); //always delete all record projects

            for (ModelProfitLoss profit : profits) {
                profit.setId(0);
                profit.saveToDB(db.getWritableDatabase());
                if (profit.getId() == 0) {
                    log("projectcode : " + profit.getProjectcode() + " " + profit.getReportname() + " inserted");
                }
            }
        }catch(Exception e){
            log(e.toString());
        }
    }


    public boolean DownloadProfitLoss(Boolean async, final int monthperiod, final int yearperiod){
        String url = url_profitloss() + "/" + String.valueOf(yearperiod) + "/" + String.valueOf(monthperiod);
        if (async) {
            GsonRequest<ModelProfitLoss[]> gsonReq = new GsonRequest<>(url, ModelProfitLoss[].class,
                    new Response.Listener<ModelProfitLoss[]>() {
                        @Override
                        public void onResponse(ModelProfitLoss[] response) {
                            SaveProfitLoss(response, monthperiod, yearperiod);
                            listener.onSuccess("");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            log(error.toString());
                            listener.onError(error.toString());
                        }
                    }
            );
            this.controllerRequest.addToRequestQueue(gsonReq, url_project());
            return true;
        }else {
            RequestFuture<ModelProfitLoss[]> future = RequestFuture.newFuture();
            GsonRequest<ModelProfitLoss[]> gsonReq = new GsonRequest<>(url, ModelProfitLoss[].class, future, future);
            this.controllerRequest.addToRequestQueue(gsonReq, url_project());

            try {
                ModelProfitLoss[] response = future.get(10, TimeUnit.SECONDS);
                SaveProfitLoss(response, monthperiod, yearperiod);
            } catch (InterruptedException|ExecutionException| TimeoutException e) {
                log(e.getMessage(),"error");
                return false;
            }
            return true;
        }
    }

    public boolean DownloadSalesPeriod(final Date startDate, final Date endDate){
        String dt1 = new SimpleDateFormat("yyyy-MM-dd").format(startDate.getTime());
        String dt2 = new SimpleDateFormat("yyyy-MM-dd").format(endDate.getTime());
        String url = url_salesperiod() + "/" + dt1 + "/" + dt2;

        GsonRequest<ModelSalesPeriod[]> gsonReq = new GsonRequest<>(url, ModelSalesPeriod[].class,
                new Response.Listener<ModelSalesPeriod[]>() {
                    @Override
                    public void onResponse(ModelSalesPeriod[] response) {
                        SaveSalesPeriods(response, startDate, endDate);
                        listener.onSuccess("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        log(error.toString());
                        listener.onError(error.toString());
                    }
                }
        );
        this.controllerRequest.addToRequestQueue(gsonReq, url_project());
        return true;
    }

    private void SaveSalesPeriods(ModelSalesPeriod[] salesPeriods, Date startdate, Date enddate){
        try {
            db.getWritableDatabase().execSQL(new ModelSalesPeriod().generateSQLDelete
                    ("where transdate between '" + String.valueOf(startdate.getTime())
                            + "' and '" + String.valueOf(enddate.getTime()) + "'")
                    ); //always delete all record projects

            for (ModelSalesPeriod salesPeriod : salesPeriods) {
                salesPeriod.setId(0);
                salesPeriod.saveToDB(db.getWritableDatabase());
                if (salesPeriod.getId() == 0) {
                    log("salesPeriod : " + salesPeriod.getTransdate().toString() + " inserted");
                }
            }
        }catch(Exception e){
            log(e.toString());
        }
    }

    public boolean DownloadAgingAP(){
        String url = base_url() + "apaging";

        GsonRequest<ModelAPAging[]> gsonReq = new GsonRequest<>(url, ModelAPAging[].class,
                new Response.Listener<ModelAPAging[]>() {
                    @Override
                    public void onResponse(ModelAPAging[] response) {
                        SaveAgingAP(response);
                        listener.onSuccess("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                }
        );
        this.controllerRequest.addToRequestQueue(gsonReq, url_project());
        return true;
    }

    private void SaveAgingAP(ModelAPAging[] agings){
        try {
            db.getWritableDatabase().execSQL(new ModelAPAging().generateSQLDelete("")); //always delete all record projects

            for (ModelAPAging aging : agings) {
                aging.setId(0);
                aging.saveToDB(db.getWritableDatabase());
            }
        }catch(Exception e){
            log(e.toString());
        }
    }

    public boolean DownloadInventory(){
        String url = base_url() + "inventory";

        GsonRequest<ModelInventory[]> gsonReq = new GsonRequest<>(url, ModelInventory[].class,
                new Response.Listener<ModelInventory[]>() {
                    @Override
                    public void onResponse(ModelInventory[] response) {
                        SaveInventory(response);
                        listener.onSuccess("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                }
        );
        this.controllerRequest.addToRequestQueue(gsonReq, url_project());
        return true;
    }

    private void SaveInventory(ModelInventory[] inventories){
        try {
            db.getWritableDatabase().execSQL(new ModelInventory().generateSQLDelete("")); //always delete all record projects

            for (ModelInventory inventory : inventories) {
                inventory.setId(0);
                inventory.saveToDB(db.getWritableDatabase());
            }
        }catch(Exception e){
            log(e.toString());
        }
    }

    private void SaveCashFlow(ModelCashFlow[] cashFlows, int monthperiod, int yearperiod){
        try {
            db.getWritableDatabase().execSQL(new ModelCashFlow().generateSQLDelete
                    ("where monthperiod = " + String.valueOf(monthperiod)
                            + " and yearperiod = " + String.valueOf(yearperiod))); //always delete all record projects

            for (ModelCashFlow cashFlow : cashFlows) {
                cashFlow.setId(0);
                cashFlow.saveToDB(db.getWritableDatabase());
            }
        }catch(Exception e){
            log(e.toString());
        }
    }
    public boolean DownloadCashFlow(final int monthperiod, final int yearperiod){
        String url = url_cashflow() + "/" + String.valueOf(yearperiod) + "/" + String.valueOf(monthperiod);
        GsonRequest<ModelCashFlow[]> gsonReq = new GsonRequest<>(url, ModelCashFlow[].class,
                new Response.Listener<ModelCashFlow[]>() {
                    @Override
                    public void onResponse(ModelCashFlow[] response) {
                        SaveCashFlow(response, monthperiod, yearperiod);
                        listener.onSuccess("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                }
        );
        this.controllerRequest.addToRequestQueue(gsonReq, url_project());
        return true;
    }


    public void SyncData( final Boolean async){
        try {
            AsyncRestRunner runner = new AsyncRestRunner(this, 1, 2020);
            runner.execute(async);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SyncProfitLoss(int monthperiod, int yearperiod){
        try {
            AsyncRestRunner runner = new AsyncRestRunner(this, monthperiod, yearperiod);
//            runner.syncProject = Boolean.TRUE;
            runner.syncProfitLoss = Boolean.TRUE;
            runner.execute(Boolean.FALSE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    public void SyncProjects(int monthperiod, int yearperiod){
//        AsyncRestRunner runner = new AsyncRestRunner(this, monthperiod, yearperiod);
//        runner.syncProject = Boolean.FALSE;
//        runner.execute(Boolean.FALSE);
//    }
}

//next ganti ke java.util.concurrent
class AsyncRestRunner extends AsyncTask<Boolean, String, Void> {
    private ControllerRest controllerRest;
//    public Boolean syncProject = Boolean.FALSE;
    public Boolean syncProfitLoss = Boolean.FALSE;
    private int monthperiod;
    private int yearperiod;

    AsyncRestRunner(ControllerRest controllerRest, int monthperiod, int yearperiod) {
        this.monthperiod = monthperiod;
        this.yearperiod = yearperiod;
        this.controllerRest = controllerRest;
        this.controllerRest.setAsyncTaskListenerListener(new ControllerRest.AsyncTaskListener() {
            @Override
            public void onProgressUpdate(String...msg) {
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
        if (values.length>1) {
            if (values[1] == "error") {
                controllerRest.listener.onError(values[0]);
            }
        }else{
            controllerRest.listener.onProgress(values[0]);
        }
    }

    @Override
    protected Void doInBackground(Boolean... booleans) {
        try{
            boolean async = booleans[0];
            publishProgress("Connecting to Rest API : " + controllerRest.base_url());
            boolean result;
//            if (syncProject) {
//                publishProgress("Download Project");
//                if (!controllerRest.DownloadProject(async)) return null;
//            }

            if (syncProfitLoss) {
                publishProgress("Download Profit Loss");
                if (!controllerRest.DownloadProfitLoss(async, this.monthperiod, this.yearperiod)) return null;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
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