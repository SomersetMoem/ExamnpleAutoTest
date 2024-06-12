package api.swaggerTests;

import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import test.org.listeners.CustomTpl;
import test.org.services.FileService;

import java.io.File;

import static test.org.assertions.conditions.Conditions.hasStatusCode;

public class FileTests {
    private static FileService fileService;

    @BeforeEach
    public static void setup() {
        RestAssured.baseURI = "http://85.192.34.140:8080/";
        RestAssured.filters(new ResponseLoggingFilter(), new RequestLoggingFilter(),
                CustomTpl.customLogFilter().withCustomTemplate());

        fileService = new FileService();
    }

    @Test
    public void positiveDownloadTest() {
        byte[] file = fileService.downloadBaseImage()
                .asResponse()
                .asByteArray();

        attachFile(file);

        File expectedFile = new File("src/test/resources/threadqa.jpeg");

        Assert.assertEquals(expectedFile.length(), file.length);
    }

    @Test
    public void positiveUploadTest() {
        File expectedFile = new File("src/test/resources/threadqa.jpeg");
        fileService.uploadFile(expectedFile)
                .should(hasStatusCode(200));

        byte[] actualFile = fileService.downloadLastFile().asResponse()
                .asByteArray();

        attachFile(actualFile);

        Assert.assertTrue(actualFile.length != 0);
        Assert.assertEquals(expectedFile.length(), actualFile.length);
    }

    @Attachment(value = "downloaded", type = "image/png")
    private byte[] attachFile(byte[] bytes) {
        return bytes;
    }
}
