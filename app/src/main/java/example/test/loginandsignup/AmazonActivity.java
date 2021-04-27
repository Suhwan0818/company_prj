//package example.test.loginandsignup;
//
//import android.renderscript.ScriptGroup;
//
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.SdkClientException;
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.CopyObjectRequest;
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//
//import java.io.File;
//import java.io.InputStream;
//
//public class AmazonActivity {
//    //아마존의 업로드 기능만 구현한 것입니다.
//    //원래는 PHOTO ACTIVITY와 연결하려 했었습니다.
//    private AmazonS3 s3Client;
//    final private String accessKey = "AKIA2PF5ZEK77N5KPI56";
//    final private String secretKey = "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";
//    private Regions clientRegion = Regions.CA_CENTRAL_1;//캐나다 채널
//    private String bucket = "mysnapbook";//저희가 쓸 버킷 이름입니다.
//
//    private AmazonActivity() {
//        createS3Client();
//    }
//
//    static private AmazonActivity instance = null;
//
//    public static AmazonActivity getInstance() {
//        if (instance == null){
//            return new AmazonActivity();
//        } else {
//            return instance;
//        }
//    }
//
//    private void createS3Client() {
//        AWSCredentials credentials = new BasicAWSCredentials("AKIA2PF5ZEK77N5KPI56", "cYaPQHAUqhbQaxnSP/rli8HoMFJDEji6KZPLgUBS");
//        this.s3Client = AmazonS3ClientBuilder
//                .standard()
//                //AWS예시에서는 CREDENTIALS로 가져오는 걸로 되있었는데 상황에 따라 바꾸는게 맞다고 봅니다.
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(clientRegion)
//                .build();
//    }
//
//    public void upload(File file, String key){
//        uploadToS3(new PutObjectRequest(this.bucket, key/*키가 파일 위치를 말하는 겁니다.*/, file/*파일 이름 어떻게 할지*/));
//    }
//
//    public void upload(ScriptGroup.Input is, String key, String contentType, long contentLength){
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentType(contentType);
//        objectMetadata.setContentLength(contentLength);
//
//        uploadToS3(new PutObjectRequest(this.bucket, key,/*input stream을 넣어야 하는데 오류가 뜹니다 해결부탁드립니다 ㅠ*/  , objectMetadata));
//    }
//
//    private void uploadToS3(PutObjectRequest putObjectRequest){
//        try{
//            this.s3Client.putObject(putObjectRequest);
//            System.out.println(String.format("[%s] upload complete", putObjectRequest.getKey()));
//        } catch (AmazonServiceException e) {
//            e.printStackTrace();
//        } catch (SdkClientException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void copy(String orgKey, String copyKey){
//        try{
//            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(
//                    this.bucket,
//                    orgKey,
//                    this.bucket,
//                    copyKey
//            );
//            this.s3Client.copyObject(copyObjectRequest);
//            System.out.println(String.format("Finish copying [%s] to [%s]", orgKey, copyKey));
//        } catch(AmazonServiceException e) {
//            e.printStackTrace();
//        } catch (SdkClientException e) {
//            e.printStackTrace();
//        }
//    }
//    public void delete(String key){
//        try{
//            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket, key);
//            this.s3Client.deleteObject(deleteObjectRequest);
//            System.out.println(String.format("[%s] deletion complete", key));
//        } catch (AmazonServiceException e) {
//            e.printStackTrace();
//        } catch (SdkClientException e) {
//            e.printStackTrace();
//        }
//    }
//}