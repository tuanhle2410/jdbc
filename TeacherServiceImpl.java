package vn.edu.topica.edumall.api.lms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.api.lms.dto.TeacherDetailDTO;
import vn.edu.topica.edumall.api.lms.dto.TeacherLegalInfoUpdateDTO;
import vn.edu.topica.edumall.api.lms.dto.TeacherPersonalInfoUpdateDTO;
import vn.edu.topica.edumall.api.lms.service.TeacherService;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Value("${kelley.url.save-course}")
    String kelleyUrl;

    @Value("${kelley.url.update-teacher}")
    String kelleyUpdateTeacherUrl;


    @Override
    public TeacherDetailDTO getTeacherDetail(Authentication authentication) {

        String email = ((UserPrincipal) authentication.getCredentials()).getEmail();
        try {
            URIBuilder uriBuilder = new URIBuilder(kelleyUrl);
            uriBuilder.setParameter("email", email);
            HttpGet requestGet = new HttpGet(uriBuilder.build());

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(requestGet);
            if (response.getStatusLine().getStatusCode() == 200) {

                String json = EntityUtils.toString(response.getEntity());

                //todo map with kelley
                ObjectMapper objectMapper = new ObjectMapper();
                TeacherDetailDTO teacherDetail = objectMapper.readValue(json, TeacherDetailDTO.class);

                return teacherDetail;
            }
        } catch (URISyntaxException e) {
            log.error(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "", null);
    }

    @Override
    public boolean updateTeacherDetail(TeacherPersonalInfoUpdateDTO teacherPersonalInfoUpdateDTO) {
        try {
            URIBuilder uriBuilder = new URIBuilder(kelleyUpdateTeacherUrl);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut clientPut = new HttpPut(uriBuilder.build());

            ObjectMapper objectMapper = new ObjectMapper();
            String teacherJson = objectMapper.writeValueAsString(teacherPersonalInfoUpdateDTO);
            StringEntity entity = new StringEntity(teacherJson, "UTF-8");
            clientPut.setEntity(entity);
            clientPut.setHeader("Accept", "application/json");
            clientPut.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(clientPut);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return true;
            } else {
                String strResponse = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject jsonResponse = new JSONObject(strResponse);
                String message = null;
                if (!jsonResponse.isEmpty()) {
                    message = jsonResponse.get("message").toString();
                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message, null);
            }
        } catch (URISyntaxException e) {
            log.error(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "", null);
    }

    @Override
    public boolean updateTeacherLegalDetail(TeacherLegalInfoUpdateDTO teacherLegalInfoUpdateDTO) {
        try {
            URIBuilder uriBuilder = new URIBuilder(kelleyUpdateTeacherUrl);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut clientPut = new HttpPut(uriBuilder.build());

            ObjectMapper objectMapper = new ObjectMapper();
            String teacherJson = objectMapper.writeValueAsString(teacherLegalInfoUpdateDTO);
            StringEntity entity = new StringEntity(teacherJson, "UTF-8");
            clientPut.setEntity(entity);
            clientPut.setHeader("Accept", "application/json");
            clientPut.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpClient.execute(clientPut);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return true;
            } else {
                String strResponse = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject jsonResponse = new JSONObject(strResponse);
                String message = null;
                if (!jsonResponse.isEmpty()) {
                    message = jsonResponse.get("message").toString();
                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message, null);
            }
        } catch (URISyntaxException e) {
            log.error(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "", null);
    }


}
