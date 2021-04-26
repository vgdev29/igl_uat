package com.example.igl.Helper;

public class Constants {

   /* LIVE URL*/
 //public static String BASE_URL="http://49.50.65.107:8081/";

   /* UAT URL*/
   public static String BASE_URL="http://49.50.118.112:8080/";

    static String  localhost = "http://192.168.31.250:8081/";
    public static String START_CLICKED="false";
    //public static final String Logout=BASE_URL+"/logout";
    public static final int LOCATION_INTERVAL = 10000;
    public static final int FASTEST_LOCATION_INTERVAL = 5000;
    public static final String Auth_User = BASE_URL + "loginapi/authenticate";
    public static final String Login_User = BASE_URL + "loginapi/userdetails";
    public static final String SignUp_User = BASE_URL + "register";
    public static final String BP_Creation = BASE_URL + "bpcreation/registration/bp_creation";
    public static final String NG_UPDATE = BASE_URL+"api/jmr/";
    public static final String BP_Images = BASE_URL + "bpcreation/registration/bp_images";
    public static final String UPLOAD_IMAGE = BASE_URL+"api/jmr/update?jmr=36655";

    public static final String BP_No_Get_Listing = BASE_URL + "ekyc/bp_details/listing";
    public static final String NEW_BP_No_Get_Listing = BASE_URL + "ekyc/bp_details/new_registration_listing";
 public static final String Documen_Resubmission_Listing = BASE_URL + "ekyc/bp_details/documentResubmissionListing";
    public static final String Document_POST = BASE_URL + "bpcreation/registration/documents/";

    public static final String BP_No_Post = BASE_URL + "ekyc/bp_details/update/";
    public static final String Attendance_Signin_Post = BASE_URL + "ekyc/bp_details/attendence_sign_in/";
    public static final String Attendance_Signout_Post = BASE_URL + "ekyc/bp_details/attendence_sign_out/";
    public static final String City_List = BASE_URL + "ekyc/bp_details/listing_of_city";
    public static final String Arealist_reason_Socity = BASE_URL + "ekyc/bp_details/address";
    public static final String Socity_List = BASE_URL + "ekyc/bp_details/society/";
    public static final String Arealist_Customar_Type = BASE_URL + "ekyc/bp_details/customer_type";
    public static final String Get_TODOCATEGORY = BASE_URL + "ekyc/bp_details/getTodoCategory";
    public static final String TODO_CREATION = BASE_URL + "ekyc/bp_details/todoCreate?";
    public static final String TODO_UPDATE = BASE_URL + "ekyc/bp_details/reschedule/";
    public static final String GET_TODO_LIST = BASE_URL + "ekyc/bp_details/gettodoList/"; //56
    public static final String GET_LEAVE_LIST = BASE_URL + "ekyc/bp_details/getLeave_listing/"; //56
    public static final String GET_ATTENDENCE_LIST = BASE_URL + "ekyc/bp_details/get_attendence_listing/";
    public static final String APPLY_LEAVE = BASE_URL + "ekyc/bp_details/applyLeave/";
    public static final String  TYPE_MASTER_STATUS = BASE_URL + "ekyc/bp_details/statusDropdown/";
    public static final String FESABILITY_ADD = BASE_URL + "ekyc/bp_details/fesabilityAdd";
    public static final String FESABILITY_ADD_Declined = BASE_URL + "ekyc/bp_details/fesabilityAddDeclined";
    public static final String TYPE_SUBMASTER_STATUS = BASE_URL + "ekyc/bp_details/substatusDropdown/";
    public static final String PIPELINE = BASE_URL + "ekyc/bp_details/pipeline";
    public static final String TPI_LISTING_GET = BASE_URL + "ekyc/bp_details/new_registration_listing_TPI/";
    public static final String RFC_LISTING_GET = BASE_URL + "ekyc/bp_details/RFCListing/";
    public static final String RFC_LISTING_TPI_GET = BASE_URL + "ekyc/bp_details/RFCListingTPI/";
    public static final String RFC_CONNECTION_POST = BASE_URL + "ekyc/bp_details/RFCMobile/";
    public static final String RFCADD = BASE_URL + "ekyc/bp_details/RFCAdd";
    public static final String RFCApproval = BASE_URL + "ekyc/bp_details/RFCApproval";
    public static final String RFCDetails = BASE_URL + "ekyc/bp_details/RFCDetails";
    public static final String RFCAddDeclined = BASE_URL + "ekyc/bp_details/RFCAddDeclined";
    public static final String MeterType = BASE_URL + "ekyc/bp_details/meterType";
    public static final String Manufacture_Make = BASE_URL + "ekyc/bp_details/manufactureMake";
    public static final String MeterNo = BASE_URL + "ekyc/bp_details/meterNo";
    public static final String RFCtodo = BASE_URL + "ekyc/bp_details/RFCtodo";
    public static final String DeleteTODO = BASE_URL + "ekyc/bp_details/deleteTODO";  //todo_id=85
    public static final String Forgot_Password= BASE_URL + "ekyc/bp_details/deleteTODO";  //todo_id=85
    public static final String Update_Password = BASE_URL + "ekyc/bp_details/deleteTODO";  //todo_id=85
    public static final String ClaimUnclaim = BASE_URL + "ekyc/bp_details/ClaimUnclaim/";
    public static final String JobStart = BASE_URL + "ekyc/bp_details/JobStart/";  //todo_id=85
    public static final String Learning = BASE_URL + "ekyc/bp_details/learning";  //todo_id=85
    public static final String UserTracking = BASE_URL + "ekyc/bp_details/userTracking";

    public static final String RFCApprovalMultipart = BASE_URL + "ekyc/bp_details/RFCApprovalMultipart";

    //public static final String RFCApprovalMultipart =  localhost+ "/bp_details/RFCApprovalMultipart";

    public static final String TPI_FEASIBILITY_PENDING = BASE_URL + "ekyc/bp_details/feasibility_pending_Listing/";
    public static final String TPI_RFC_PENDING = BASE_URL + "ekyc/bp_details/rfc_pending_Listing/";

    public static final String TPI_RFC_APPROVAl = BASE_URL + "ekyc/bp_details/rfc_onHold_Listing/";
    public static final String TPI_RFCDONE_APPROVAl_Data = BASE_URL + "ekyc/bp_details/Tpi_Listing_Done?bpno=";

    public static final String TPI_RFC_APPROVAl_DECLINE_CASE1 = BASE_URL + "ekyc/bp_details/TPI_ApprovalDecline_CASE1";
    public static final String TPI_RFC_HOLD_APPROVAl_DECLINE_CASE2 = BASE_URL + "ekyc/bp_details/TPI_ApprovalDecline_CASE2";

    public static final String TPI_RFCHOLD_APPROVAl_Data = BASE_URL + "ekyc/bp_details/Tpi_Listing_Hold?bpno=";

    public static final String TPI_DECLINE = BASE_URL + "ekyc/bp_details/TPI_Decline";



}
