package com.zl.app.data;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.baidu.mapapi.map.Text;
import com.google.gson.reflect.TypeToken;
import com.zl.app.data.model.customer.YyMobileCompany;
import com.zl.app.data.model.customer.YyMobilePeriod;
import com.zl.app.data.model.customer.YyMobilePeriodBbs;
import com.zl.app.data.model.customer.YyMobilePeriodStudent;
import com.zl.app.data.model.user.YyMobileStudent;
import com.zl.app.util.AppManager;
import com.zl.app.util.GsonUtil;
import com.zl.app.util.RequestURL;
import com.zl.app.util.net.BaseResponse;
import com.zl.app.util.net.DefaultResponseListener;
import com.zl.app.util.net.GsonRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengxiang on 2016/4/20.
 */
public class CourseService {

    public String API_GET_TEACHER_ORG ="http://www.ziluedu.cn/mobilePeriod/tcompany.html";

    public String API_GET_PARENT_ORG  = "http://www.ziluedu.cn/mobilePeriod/pcompany.html";

    //精确搜索 家长的孩子列表接口
    public String API_GET_CHILDREN  = "http://www.ziluedu.cn/mobilePeriod/pstudent.html";


    public interface CourseStatusP {
        //        1 请假 2 正常 3补假 4已上课
        int QINGJIA = 1;
        int ZHENGCHANG = 2;
        int BUJIA = 3;
        int YISHANGKE = 4;
    }

    public interface CourseStatusT {
        //课程状态 2 正常 3取消 4已上课
        int ZHENGCHANG = 2;
        int QUXIAO = 3;
        int YISHANGKE = 4;
    }


    /**
     * 精确搜索 老师的机构列表接口
     * @param uid
     * @param listener
     */
    public void getCourseTeacherOrg(String uid,DefaultResponseListener<BaseResponse<List<YyMobileCompany>>> listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        GsonRequest request = new GsonRequest(Request.Method.POST,
                API_GET_TEACHER_ORG, params, null,
                new TypeToken<BaseResponse<List<YyMobileCompany>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    /**
     * 精确搜索 家长的机构列表接口
     * @param uid
     * @param listener
     */
    public void getCourseParentOrg(String uid,
       DefaultResponseListener<BaseResponse<List<YyMobileCompany>>> listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        GsonRequest request = new GsonRequest(Request.Method.POST,
                API_GET_PARENT_ORG, params, null,
                new TypeToken<BaseResponse<List<YyMobileCompany>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    /**
     * 精确搜索 家长的孩子列表接口
     * @param uid
     * @param listener
     */
    public void getParentChildren(String uid,
              DefaultResponseListener<BaseResponse<List<YyMobileStudent>>> listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        GsonRequest request = new GsonRequest(Request.Method.POST,
                API_GET_CHILDREN, params, null,
                new TypeToken<BaseResponse<List<YyMobileStudent>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }


    /**
     * 获取课程评论列表
     * @param uid
     * @param periodId
     * @param listener
     */
    public void commentList(String uid,int periodId,DefaultResponseListener<BaseResponse<List<YyMobilePeriodBbs>>> listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("period.periodId", periodId + "");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_COURSE_MSG_LIST, params, null,
                new TypeToken<BaseResponse<List<YyMobilePeriodBbs>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);

    }
    /**
     * 发送课程评论
     *
     * @param uid
     * @param periodId
     * @param content
     * @param yyuserId
     * @param image1
     * @param image2
     * @param image3
     * @param image4
     * @param listener
     */
    public void sendCourseMessage(String uid, int periodId, String content, int yyuserId,
                                  String image1, String image2, String image3, String image4
            , DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("period.periodId", periodId + "");
        if(!TextUtils.isEmpty(content)){
            params.put("content", content);
        }
        if (yyuserId != 0) {
            params.put("yyuserId", yyuserId + "");
        }
        if (!TextUtils.isEmpty(image1)) {
            params.put("image1", image1);
        }
        if (!TextUtils.isEmpty(image2)) {
            params.put("image2", image2);
        }
        if (!TextUtils.isEmpty(image3)) {
            params.put("image3", image3);
        }
        if (!TextUtils.isEmpty(image4)) {
            params.put("image4", image4);
        }
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_COURSE_MSG_SEND, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    /**
     * 教师审核学生动态
     *
     * @param uid
     * @param relationId
     * @param type       确认 传totype值，已上课 传 2
     * @param listener
     */
    public void teacherChecked(String uid, int relationId, int type, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("relationId", relationId + "");
        params.put("type", type + "");
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_TEACHER_CHECKED, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    //提交课程学生动态
    public void submitCourseStudentsDT(String uid, String relationId, String totype, String content, DefaultResponseListener<BaseResponse> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("relationId", relationId);
        params.put("totype", totype);
        params.put("content", content);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_SUBMIT_COURSE_STUDENT_DT, params, null,
                new TypeToken<BaseResponse>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    //课程学生动态
    public void getCourseStudentsDT(String uid, String courseId, DefaultResponseListener<BaseResponse<List<YyMobilePeriodStudent>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("period.periodId", courseId);
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_COURSE_STUDENT_DT, params, null,
                new TypeToken<BaseResponse<List<YyMobilePeriodStudent>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }


    public void getCoursePList(String uid, String startDate, String endDate, Integer type, Integer studentId, Integer companyId
            , DefaultResponseListener<BaseResponse<List<YyMobilePeriod>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("type", type == null ? "" : type + "");
        params.put("student.studentId", studentId == null ? "" : studentId + "");
        params.put("companyId", companyId == null ? "" : companyId + "");
        Log.e("getCoursePList", GsonUtil.gson.toJson(params));
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_COURSE_PLIST, params, null,
                new TypeToken<BaseResponse<List<YyMobilePeriod>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }

    public void getCourseTList(String uid, String startDate, String endDate, Integer status, Integer companyId
            , DefaultResponseListener<BaseResponse<List<YyMobilePeriod>>> listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("status", status == null ? "" : status + "");
        params.put("companyId", companyId == null ? "" : companyId + "");
        Log.e("getCourseTList", GsonUtil.gson.toJson(params));
        GsonRequest request = new GsonRequest(Request.Method.POST, RequestURL.API_COURSE_TLIST, params, null,
                new TypeToken<BaseResponse<List<YyMobilePeriod>>>() {
                },
                listener, listener);
        AppManager.getRequestQueue().add(request);
    }
}
