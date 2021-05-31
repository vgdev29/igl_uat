package com.fieldmobility.igl.Fragment;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fieldmobility.igl.Helper.ServiceUtility;
import com.fieldmobility.igl.MataData.Pdf_Item;
import com.fieldmobility.igl.MataData.StaticValue;
import com.fieldmobility.igl.R;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static android.os.Environment.getExternalStorageDirectory;


public class BePartnerStepThreeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "fullname";
    private static final String ARG_PARAM2 = "father_husbandname";

    // TODO: Rename and change types of parameters
    private static String mParam1;
    private static String mParam2;
    private PdfWriter pdfWriter;
    String reportName;
    String orderId;
    File pdfFile;
    String mPath;
    Bitmap bitmap;
    Button clear, save;
    SignatureView signatureView;
    String path ,firstname;
    ImageView image_signature;
    String fullname,father_husbandname;
    private static final String IMAGE_DIRECTORY = "/signdemo";
    private static ArrayList<Pdf_Item> arrayListRProductModel = new ArrayList<Pdf_Item>();
    private OnStepThreeListener mListener;
    static String name,email_id,father_husband_name,mobile_no,contact_no,aadhaar_no,proof_of_residence,type_ownership;
    public BePartnerStepThreeFragment() {

    }

    public static BePartnerStepThreeFragment newInstance(String param1, String param2,String param3) {
        BePartnerStepThreeFragment fragment = new BePartnerStepThreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM2, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            Log.e("mParam11",mParam1);
            Log.e("mParam22",mParam2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_be_partner_step_three, container, false);
        /*Bundle args1 = getArguments();
        if (args1  != null){
            firstname = args1.getString("position");
        }*/

    }

    private Button backBT;
    private Button nextBT;
    private ImageView image;
    private Button pdf_button,signature_button;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         fullname = getArguments().getString("fullname");
         father_husbandname = getArguments().getString("father_husbandname");
        Log.e("mParam22",fullname);
        backBT=view.findViewById(R.id.backBT);
        nextBT=view.findViewById(R.id.nextBT);
        pdf_button=view.findViewById(R.id.pdf_button);
        image=view.findViewById(R.id.image);
        signature_button=view.findViewById(R.id.signature_button);
        image_signature=view.findViewById(R.id.image_signature);
        name=BePartnerStepOneFragment.fullname_edit;
        father_husband_name=BePartnerStepOneFragment.father_husband_name_edit;
        email_id=BePartnerStepOneFragment.email_id_edit;
        mobile_no=BePartnerStepOneFragment.mobile_no_edit;
        contact_no=BePartnerStepOneFragment.contect_no_edit;
        aadhaar_no=BePartnerStepOneFragment.aadhaar_no_edit;
        type_ownership=BePartnerStepOneFragment.type_ownership;
        proof_of_residence=BePartnerStepOneFragment.proof_of_residence;
        Log.e("name",name);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openPdf();
            }
        });
        signature_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signature_Method();
            }
        });
    }

    private void openPdf() {

        File file = new File(mPath);
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            Log.e("uri++",uri.toString());
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(getActivity(), "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        backBT.setOnClickListener(this);
        nextBT.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        backBT.setOnClickListener(null);
        nextBT.setOnClickListener(null);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.backBT:
                if (mListener != null)
                    mListener.onBackPressed(this);
                break;

            case R.id.nextBT:

                if (mListener != null)
                    mListener.onNextPressed(this);
               MyPdf();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepThreeListener) {
            mListener = (OnStepThreeListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        backBT=null;
        nextBT=null;
    }
    public interface OnStepThreeListener {
        void onBackPressed(Fragment fragment);
        void onNextPressed(Fragment fragment);
    }

    private void MyPdf() {
        Integer randomNum = ServiceUtility.randInt(0, 9999999);
        orderId=randomNum.toString();
        reportName="Android"+orderId;
        Log.e("reportName",reportName);
        createPDF(reportName);
    }


    private void Signature_Method() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.signature_dialog_box);
        dialog.setTitle("Signature");
        dialog.setCancelable(false);

        signatureView = (SignatureView) dialog.findViewById(R.id.signature_view);
        clear = (Button) dialog.findViewById(R.id.clear);
        save = (Button) dialog.findViewById(R.id.save);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                path = saveImage(bitmap);
                image_signature.setImageBitmap(bitmap);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("Signature_Page++", wallpaperDirectory.toString());
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }

    public boolean createPDF(String reportName) {

        try {
            //creating a directory in SD card
            File mydir = new File(Environment.getExternalStorageDirectory()
                    + StaticValue.PATH_PRODUCT_REPORT); //PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
            if (!mydir.exists()) {
                mydir.mkdirs();
            }

            //getting the full path of the PDF report name
             mPath = Environment.getExternalStorageDirectory().toString()
                    + StaticValue.PATH_PRODUCT_REPORT //PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
                    + reportName + ".pdf"; //reportName could be any name

            //constructing the PDF file
             pdfFile = new File(mPath);
            Log.e("pdfFile",pdfFile.toString());

            //Creating a Document with size A4. Document class is available at  com.itextpdf.text.Document
            Document document = new Document(PageSize.A4);

            //assigning a PdfWriter instance to pdfWriter
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            Log.e("pdfWriter",pdfWriter.toString());
            //PageFooter is an inner class of this class which is responsible to create Header and Footer
            PageHeaderFooter event = new PageHeaderFooter();
            pdfWriter.setPageEvent(event);

            //Before writing anything to a document it should be opened first
            document.open();

            //Adding meta-data to the document
            addMetaData(document);
            //Adding Title(s) of the document
            addTitlePage(document);
            //Adding main contents of the document
            addContent(document);
            //Closing the document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
        return true;
    }

    private static void addMetaData(Document document) {
        document.addTitle("All Product Names");
        document.addSubject("Android");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("SIAS ERP");
        document.addCreator(StaticValue.USER_MODEL);
    }



    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph paragraph = new Paragraph();

        // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
        Paragraph childParagraph = new Paragraph("INDRAPRASTHA GAS LIMITED", StaticValue.FONT_TITLE); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_CENTER);

        paragraph.add(childParagraph);

        childParagraph = new Paragraph("Product List", StaticValue.FONT_SUBTITLE); //public static Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);

        childParagraph = new Paragraph("Report generated on: 17.12.2015" , StaticValue.FONT_SUBTITLE);
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);

        addEmptyLine(paragraph, 2);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        //End of adding several titles

    }

    /**
     * In this method the main contents of the documents are added
     * @param document
     * @throws DocumentException
     */

    private static void addContent(Document document) throws DocumentException {

        Paragraph reportBody = new Paragraph();
        reportBody.setFont(StaticValue.FONT_BODY); //public static Font FONT_BODY = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL);

        // Creating a table
        createTable(reportBody);

        // now add all this to the document
        document.add(reportBody);

    }

    /**
     * This method is responsible to add table using iText
     * @param reportBody
     * @throws BadElementException
     */
    private static void createTable(Paragraph reportBody)
            throws BadElementException {

        float[] columnWidths = {7,7}; //total 4 columns and their width. The first three columns will take the same width and the fourth one will be 5/2.
        PdfPTable table = new PdfPTable(columnWidths);

        table.setWidthPercentage(100); //set table with 100% (full page)
        table.getDefaultCell().setUseAscender(true);


        //Adding table headers
        PdfPCell cell = new PdfPCell(new Phrase("Name", //Table Header
                StaticValue.FONT_TABLE_HEADER)); //Public static Font FONT_TABLE_HEADER = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //alignment
        cell.setBackgroundColor(new GrayColor(0.75f)); //cell background color
        cell.setFixedHeight(30); //cell height
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Value",
                StaticValue.FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);



        //End of adding table headers

        //This method will generate some static data for the table
        generateTableData();

        //Adding data into table
        for (int i = 0; i < arrayListRProductModel.size(); i++) { //
            cell = new PdfPCell(new Phrase(arrayListRProductModel.get(i).getName()));
            cell.setFixedHeight(28);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(arrayListRProductModel.get(i).getUnitName()));
            cell.setFixedHeight(28);
            table.addCell(cell);


        }

        reportBody.add(table);

    }

    /**
     * This method is used to add empty lines in the document
     * @param paragraph
     * @param number
     */
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    /**
     * This is an inner class which is used to create header and footer
     * @author XYZ
     *
     */

    class PageHeaderFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);

        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Phrase footer_poweredBy = new Phrase("Powered By IGL", StaticValue.FONT_HEADER_FOOTER); //public static Font FONT_HEADER_FOOTER = new Font(Font.FontFamily.UNDEFINED, 7, Font.ITALIC);
            Phrase footer_pageNumber = new Phrase("Page " + document.getPageNumber(), StaticValue.FONT_HEADER_FOOTER);

            // Header
            // ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, header,
            // (document.getPageSize().getWidth()-10),
            // document.top() + 10, 0);

            // footer: show page number in the bottom right corner of each age
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                    footer_pageNumber,
                    (document.getPageSize().getWidth() - 10),
                    document.bottom() - 10, 0);
//			// footer: show page number in the bottom right corner of each age
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer_poweredBy, (document.right() - document.left()) / 2
                            + document.leftMargin(), document.bottom() - 10, 0);
        }
    }

    private static void generateTableData(){
        Pdf_Item productModel = new Pdf_Item("Name", name);
        arrayListRProductModel.add(productModel);

        productModel = new Pdf_Item("Email Id", email_id);
        arrayListRProductModel.add(productModel);

        productModel = new Pdf_Item("Mobile No", mobile_no);
        arrayListRProductModel.add(productModel);

        productModel = new Pdf_Item("Contact No", contact_no);
        arrayListRProductModel.add(productModel);
        productModel = new Pdf_Item("Father/Husband Name", father_husband_name);
        arrayListRProductModel.add(productModel);
        productModel = new Pdf_Item("Aadhaar No", aadhaar_no);
        arrayListRProductModel.add(productModel);
        productModel = new Pdf_Item("Address", "Noida");
        arrayListRProductModel.add(productModel);

        productModel = new Pdf_Item("Type of OwnerShip", type_ownership);
        arrayListRProductModel.add(productModel);

        productModel = new Pdf_Item("Proof of Residence", proof_of_residence);
        arrayListRProductModel.add(productModel);
    }

}