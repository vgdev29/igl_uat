package com.fieldmobility.igl.Helper;

public class Constants {
    public static final boolean isDebugBuild=true; // make it FALSE while giving app to others
    //LIVE URL
     // public static String BASE_URL="http://49.50.65.107:8081/";
     // public static  String BASE_URL_PYTHON = "http://49.50.65.107:8000/"; //PYTHON SERVER


    //  UAT URL
      public static String BASE_URL = "http://49.50.118.112:8080/";
     public static String BASE_URL_PYTHON = "http://49.50.68.239:8080/";

    //local
          static String localhost = "http://192.168.31.29:8081/";

    public static final int LOCATION_INTERVAL = 100000000;
    public static final int FASTEST_LOCATION_INTERVAL = 5000000;

    //added extra 's' at end point - incorporated on 2 OCT - prod
 // public static final String Auth_User = BASE_URL + "loginapi/authenticates";

    //for uat /testing ('s' is not updated at uat environment
    public static final String Auth_User = BASE_URL + "loginapi/authenticate";

   //  public static final String Auth_User = localhost + "/authenticates";

    public static final String Login_User = BASE_URL + "loginapi/userdetails";
    public static final String SignUp_User = BASE_URL + "register";
    public static final String UPLOAD_IMAGE = BASE_URL + "api/jmr/update?jmr=36655";
    public static final String BP_No_Get_Listing = BASE_URL + "ekyc/bp_details/listing";
    public static final String NEW_BP_No_Get_Listing = BASE_URL + "ekyc/bp_details/new_registration_listing";
    public static final String Documen_Resubmission_Listing = BASE_URL + "ekyc/bp_details/documentResubmissionListing";
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
    public static final String TYPE_MASTER_STATUS = BASE_URL + "ekyc/bp_details/statusDropdown/";
    public static final String  FEAS_STATUS_DROPDOWN = BASE_URL + "ekyc/bp_details/feas_statusDropdown/";
    public static final String FESABILITY_ADD = BASE_URL + "ekyc/bp_details/fesabilityAdd";
    public static final String FESABILITY_ADD_Declined = BASE_URL + "ekyc/bp_details/fesabilityAddDeclined";
    public static final String TYPE_SUBMASTER_STATUS = BASE_URL + "ekyc/bp_details/substatusDropdown/";
    public static final String PIPELINE = BASE_URL + "ekyc/bp_details/pipeline";
    public static final String TPI_LISTING_GET = BASE_URL + "ekyc/bp_details/new_registration_listing_TPI/";
    //Api chang by adding s in end point
    public static final String RFC_LISTING_GET = BASE_URL + "ekyc/bp_details/RFCListings/";
    public static final String RFC_LISTING_TPI_GET = BASE_URL + "ekyc/bp_details/RFCListingTPI/";
    public static final String RFC_CONNECTION_POST = BASE_URL + "ekyc/bp_details/RFCMobile/";
    public static final String RFCADD = BASE_URL + "ekyc/bp_details/RFCAdd";
    public static final String RFCApproval = BASE_URL + "ekyc/bp_details/RFCApproval";
    public static final String RFCDetails = BASE_URL + "ekyc/bp_details/RFCDetails";
    public static final String MITDDetails = BASE_URL + "ekyc/bp_details/mitd_update";
    public static final String MITD_DONE = BASE_URL + "ekyc/bp_details/mitd_done";
    public static final String RFCAddDeclined = BASE_URL + "ekyc/bp_details/RFCAddDeclined";
    public static final String MeterType = BASE_URL + "ekyc/bp_details/meterType";
    public static final String Manufacture_Make = BASE_URL + "ekyc/bp_details/manufactureMake";
    public static final String MeterNo = BASE_URL + "ekyc/bp_details/meterNo";
    public static final String RFCtodo = BASE_URL + "ekyc/bp_details/RFCtodo";
    public static final String DeleteTODO = BASE_URL + "ekyc/bp_details/deleteTODO";  //todo_id=85
    public static final String Forgot_Password = BASE_URL + "ekyc/bp_details/deleteTODO";  //todo_id=85
    public static final String Update_Password = BASE_URL + "ekyc/bp_details/deleteTODO";  //todo_id=85
    public static final String ClaimUnclaim = BASE_URL + "ekyc/bp_details/ClaimUnclaim/";
    public static final String JobStart = BASE_URL + "ekyc/bp_details/JobStart/";  //todo_id=85
    public static final String Learning = BASE_URL + "ekyc/bp_details/learning";  //todo_id=85
    public static final String UserTracking = BASE_URL + "ekyc/bp_details/userTracking";
    public static final String RFCApprovalMultipart = BASE_URL + "ekyc/bp_details/RFCApprovalMultipart";

    //public static final String RFCApprovalMultipart = localhost + "/bp_details/RFCApprovalMultipart";


    public static final String TPI_FEASIBILITY_PENDING = BASE_URL + "ekyc/bp_details/feasibility_pending_Listing/";
    //api change by adding s to end
    public static final String TPI_RFC_PENDING = BASE_URL + "ekyc/bp_details/rfc_pending_Listings/?zone=";
    //api change by adding s to end
    public static final String TPI_RFC_APPROVAl = BASE_URL + "ekyc/bp_details/rfc_onHold_Listings/";
    public static final String TPI_RFCDONE_APPROVAl_Data = BASE_URL + "ekyc/bp_details/Tpi_Listing_Done?bpno=";
    public static final String TPI_RFC_APPROVAl_DECLINE_CASE1 = BASE_URL + "ekyc/bp_details/TPI_ApprovalDecline_CASE1";
    public static final String TPI_RFC_HOLD_APPROVAl_DECLINE_CASE2 = BASE_URL + "ekyc/bp_details/TPI_ApprovalDecline_CASE2";
    public static final String TPI_RFCHOLD_APPROVAl_Data = BASE_URL + "ekyc/bp_details/Tpi_Listing_Hold?bpno=";
    public static final String TPI_DECLINE = BASE_URL + "ekyc/bp_details/TPI_Decline";
    public static final String CON_SUP_DETAILS = BASE_URL + "ekyc/bp_details/userDetails?";
    public static final String MITD_UPDATE = BASE_URL + "ekyc/bp_details/mitd_update/";
    public static final String BP_Creation = BASE_URL + "bpcreation/registration/bp_creation";
    public static final String BP_Images = BASE_URL + "bpcreation/registration/bp_images";
    public static final String Document_POST = BASE_URL + "bpcreation/registration/documents";
    public static final String BP_No_Listing = BASE_URL + "bpcreation/registration/new_registration_listing";
    public static final String findbpbymob = BASE_URL + "bpcreation/registration/findbpbymob/";
    public static final String BP_CITY_LISTING = BASE_URL + "bpcreation/registration/city/";
    public static final String BP_No_Resubmition_Listing = BASE_URL + "bpcreation/registration/documentResubmissionListing/";
    //NI User
    public static final String ni_user_submit = BASE_URL + "bpcreation/registration/nic";
    public static final String ni_user_listing = BASE_URL + "bpcreation/registration/nic_list/";

    // RISER MODULE
  //  public static final String RISER_LISTING = localhost + "/bp_details/RiserListings";
   public static final String RISER_LISTING = BASE_URL + "ekyc/bp_details/RiserListings";
    public static final String RISER_TPIPendingLISTING = BASE_URL + "ekyc/bp_details/RiserTpiPendingListings/";
  //  public static final String RISER_TPIPendingLISTING = localhost + "/bp_details/RiserTpiPendingListings/";
    public static final String RISER_TPIPendingClaim = BASE_URL + "ekyc/bp_details/RiserClaim/";
   // public static final String RISER_TPIPendingClaim = localhost + "/bp_details/RiserClaim/";
    public static final String RISER_TPI_APPROVAL_LISTING = BASE_URL + "ekyc/bp_details/RiserApprovalList";
    public static final String RISER__APPROVAL_DECLINE = BASE_URL + "ekyc/bp_details/RiserApproveDecline";
    public static final String RISER__PROJECT_REPORT = BASE_URL + "ekyc/bp_details/createriser";
 //   public static final String RISER__PROJECT_REPORT = localhost + "/bp_details/createriser";
    public static final String RISER__SEARCH_BP = BASE_URL + "ekyc/bp_details/searchbp/";

    //Ekyc new Api
    public static final String EKYC_DATA_UPDATE = BASE_URL + "ekyc/bp_details/updatekycData/";
    public static final String EKYC_ID_IMAGEUPDATE = BASE_URL + "ekyc/bp_details/updateIdProof/";
    public static final String EKYC_ADDRESS_IMAGEUPDATE = BASE_URL + "ekyc/bp_details/updateAddressProof/";
    public static final String EKYC_SIGNATURE_IMAGEUPDATE = BASE_URL + "ekyc/bp_details/updateSignature/";


    //NG API
    public static final String REFRESH_NG = BASE_URL + "ekyc/bp_details/Refreshng/";
  public static final String REFRESH_RFC = BASE_URL + "ekyc/bp_details/Refreshrfc/";
   public static final String REFRESH_FEAS = BASE_URL + "ekyc/bp_details/Refreshfeas/";

    //RFC API
     public static final String TPI_DATA = BASE_URL + "ekyc/bp_details/tpi_data?zone=";
      public static final String TPI_RFCPENSEARCH = BASE_URL + "ekyc/bp_details/tpirfcpen_search?zone=";
     public static final String RFC_MOB_UPDATE = BASE_URL + "ekyc/bp_details/updatemobemail_rfc?bp=";

    public static final String MITD_OWN_LISTING_GET = BASE_URL + "ekyc/bp_details/MITDownListings/";
   // public static final String MITD_OWN_LISTING_GET = localhost + "/bp_details/MITDownListings/";

  public static final String MITD_OTHER_LISTING_GET = BASE_URL + "ekyc/bp_details/MITDotherListings/";
 //public static final String MITD_OTHER_LISTING_GET = localhost + "/bp_details/MITDotherListings/";

    public static final String TPI_MITD_PENDING = BASE_URL + "ekyc/bp_details/mitd_pending_Listings/?zone=";
   // public static final String TPI_MITD_PENDING = localhost + "/bp_details/mitd_pending_Listings/?zone=";

     public static final String TPI_MITD_APPROVAL = BASE_URL + "ekyc/bp_details/mitd_approval_Listings/?id=";
    //public static final String TPI_MITD_APPROVAL = localhost + "/bp_details/mitd_approval_Listings/?id=";

     public static final String TPI_CONT_DETAIL = BASE_URL + "ekyc/bp_details/mitd_cont_details";
    //public static final String TPI_CONT_DETAIL = localhost + "/bp_details/mitd_cont_details";

    public static final String MITDClaimUnclaim = BASE_URL + "ekyc/bp_details/MITDClaimUnclaim/";
  //  public static final String MITDClaimUnclaim = localhost + "/bp_details/MITDClaimUnclaim/";

    public static final String TPI_MITDPENSEARCH = BASE_URL + "ekyc/bp_details/tpimitdpen_search?zone=";
   // public static final String TPI_MITDPENSEARCH = localhost + "/bp_details/tpimitdpen_search?zone=";

    public static final String RFCMobDetails = BASE_URL + "ekyc/bp_details/RFCMobDetails";
  //  public static final String RFCMobDetails = localhost + "/bp_details/RFCMobDetails";

    public static final String FINDRISER = BASE_URL + "ekyc/bp_details/FindRiser/";
    // public static final String FINDRISER = localhost + "/bp_details/FindRiser/";


    public static final String SUBMITMITD = BASE_URL + "ekyc/bp_details/submitMitd/";
    //public static final String SUBMITMITD = localhost + "/bp_details/submitMitd/";

      public static final String SUBMITMITDAPPROVAL = BASE_URL + "ekyc/bp_details/approveMitd/";
   // public static final String SUBMITMITDAPPROVAL = localhost + "/bp_details/approveMitd/";

    //MDPE
    //public static final String MDPELIST_SUP = BASE_URL + "mdpe/suballo/";
     public static final String MDPELIST_SUP = localhost + "mdpe/suballo/";






}
