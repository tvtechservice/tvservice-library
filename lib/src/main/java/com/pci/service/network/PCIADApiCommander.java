package com.pci.service.network;

import android.os.Build;


import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.pci.beacon.BuildConfig;
import com.pci.beacon.R;
import com.pci.service.redux.core.PCIStore;
import com.pci.service.redux.state.PCIState;

import java.io.*;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;
import com.pci.beacon.C;
import com.pci.beacon.PCI;
import com.pci.service.util.PCILog;
import javax.net.ssl.HttpsURLConnection;


class PCIADApiCommander implements C {

    private static PCIADApiCommander INSTANCE;

    private static final String USER_AGENT_FORMAT_PCIAD =
            "PCIAD(STBType/Genie;STBModel/CT1100;STBFirmware/1.15.01;STBPCIApp/18.01.01)";

    public static PCIADApiCommander getInstance() {
        if (INSTANCE == null) {
            synchronized (PCIStore.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PCIADApiCommander();
                }
            }
        }
        return INSTANCE;
    }

    private PCIADApiCommander() {}


    public <T> PCIADApiResponse<T> send(final PCIADApiRequest request, final Class<T> responseDataClass) throws PCIADNetworkException {
        return send(request, responseDataClass, 3);
    }


    private <T> PCIADApiResponse<T> send(final PCIADApiRequest request, Class<T> responseDataClass, final int tryCount) throws PCIADNetworkException {

        int errorCount = 0;
        PCIADNetworkException exception = new PCIADNetworkException("알 수 없는 오류", -1);

        while (errorCount < tryCount) {
            try {
                PCIADApiResponse<T> response = sendRequest(request, responseDataClass);

                if (response.isSuccessful()) {
                    PCILog.i(request.getTarget().toString() + " 요청 성공");
                    if (response.getData() != null) {
                        PCILog.d(gson.toJson(response.getData()));
                    }

                    return response;
                } else {
                    errorCount++;
                    if (errorCount == tryCount) {
                        PCILog.e(gson.toJson(response));
                    }
                    exception = new PCIADNetworkException(response.getMessage(), response.getCode());
                }
            } catch (SocketTimeoutException timeoutException) {
                errorCount++;
                exception = new PCIADNetworkException("응답시간 초과", 408);
            } catch (IOException ioException) {
                errorCount++;
                String message = "알 수 없는 오류";
                if (ioException.getMessage() != null) {
                    message = ioException.getMessage();
                }
                exception = new PCIADNetworkException(message, -2);
            }
        }

        throw exception;
    }


    private String getUserAgent( PCIState state) {
        return String.format(USER_AGENT_FORMAT_PCIAD,
                Build.VERSION.RELEASE,
                PCI.VERSION,
                state.getAdid(),
                state.getPackageName(),
                BuildConfig.DEBUG ? "DEBUG" : "RELEASE");
    }

    private String getUserAgent_pciad() {
        return String.format(USER_AGENT_FORMAT_PCIAD);
    }



    private String convertJsonToUrlParam(String json) {
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> paramsMap = gson.fromJson(json, type);

        StringBuilder params = new StringBuilder();

        for (String key : paramsMap.keySet()) {
            params.append(key).append("=").append(paramsMap.get(key)).append("&");
        }

        if (params.toString().endsWith("&")) {
            params.deleteCharAt(params.length() - 1);
        }

        return params.toString();
    }

    //test
    //    private void trustAllHosts() {
//        // Create a trust manager that does not validate certificate chains
//        TrustManager[] trustAllCerts = new TrustManager[]
//                {
//                        new X509TrustManager() {
//                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                        return new java.security.cert.X509Certificate[] {};
//                    }
//                    @Override
//                       public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                    }
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] chain,String authType) throws CertificateException {
//                    }}};// Install the all-trusting trust manager
//        try {
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }






    private <T> PCIADApiResponse<T> sendRequest(final PCIADApiRequest request, final Class<T> responseDataClass) throws IOException {

        try {
            URL url = request.getTarget().url();

            if (PCIADApiMethod.GET.equals(request.getTarget().method()) && request.getData() != null) {
                String urlString = url.toString();
                urlString += "?" + convertJsonToUrlParam(gson.toJson(request.getData()));
                url = new URL(urlString);
            }

            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setConnectTimeout(REQUEST_TIMEOUT);
            con.setReadTimeout(REQUEST_TIMEOUT * 2);

            //add request header
            con.setRequestMethod(request.getTarget().method().value);
            con.setRequestProperty("User-Agent", getUserAgent_pciad());
            con.setRequestProperty("Content-Type", "application/json");

            PCILog.d("Request URL          : %s", con.getURL());
            PCILog.d("Request Method       : %s", con.getRequestMethod());
            PCILog.d("Request User-Agent   : %s", con.getRequestProperty("User-Agent"));
            PCILog.d("Request Content-Type : %s", con.getRequestProperty("Content-Type"));

            // Send post request
            if (request.getTarget().method().equals(PCIADApiMethod.POST)) {
                con.setDoOutput(true);

                DataOutputStream os = new DataOutputStream(con.getOutputStream());

                PCILog.d("Request Payload      : %s", gson.toJson(request.getData()));
                if (request.getData() != null) os.writeBytes(gson.toJson(request.getData()));
                else os.writeBytes("");

                os.flush();
                os.close();
            }

            InputStream inputStream = null;

            if (con.getResponseCode() / 100 == 2) {
                inputStream = con.getInputStream();
            } else {
                inputStream = con.getErrorStream();
            }

            PCIADApiResponse<T> responseEntity = null;

            if (inputStream != null) {
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String responseString = response.toString();
                PCILog.d("Response String      : %s", responseString);
                PCIADApiResponseHolder responseHolder = null;
//                JSONObject json = null;

                try {

//                    responseHolder = new JSONObject(responseString);
                    responseHolder = gson.fromJson(responseString, PCIADApiResponseHolder.class);

                } catch (JsonSyntaxException e) {
                    PCILog.e(e);
                    responseHolder = new PCIADApiResponseHolder();
                    responseHolder.setCode(-1);
                    responseHolder.setMessage(e.getMessage());
                }

                responseEntity = new PCIADApiResponse<T>();
                responseEntity.setCode(responseHolder.getCode());
                responseEntity.setData(responseHolder.getData(responseDataClass));
                responseEntity.setMessage(responseHolder.getMessage());
                PCILog.d("Response Payload     : %s", gson.toJson(responseEntity.getData()));
            } else {
                responseEntity = new PCIADApiResponse<T>();
                responseEntity.setCode(con.getResponseCode());
            }

            return responseEntity;
        } catch (IOException ioException) {
            //ioException.printStackTrace();
            PCILog.e(request.getTarget().toString() + " 요청실패");
            throw ioException;
        }
    }

    private <T> PCIADApiResponse<T> sendRequest_pciad(final PCIADApiRequest request, final Class<T> responseDataClass) throws IOException {

        try {
            URL url = request.getTarget().url();

            if (PCIADApiMethod.GET.equals(request.getTarget().method()) && request.getData() != null) {
                String urlString = url.toString();
                urlString += "?" + convertJsonToUrlParam(gson.toJson(request.getData()));
                url = new URL(urlString);
            }

            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setConnectTimeout(REQUEST_TIMEOUT);
            con.setReadTimeout(REQUEST_TIMEOUT * 2);

            //add request header
            con.setRequestMethod(request.getTarget().method().value);
            con.setRequestProperty("User-Agent", getUserAgent_pciad());
            con.setRequestProperty("Content-Type", "application/json");

            PCILog.d("Request URL          : %s", con.getURL());
            PCILog.d("Request Method       : %s", con.getRequestMethod());
            PCILog.d("Request User-Agent   : %s", con.getRequestProperty("User-Agent"));
            PCILog.d("Request Content-Type : %s", con.getRequestProperty("Content-Type"));

            // Send post request
            if (request.getTarget().method().equals(PCIADApiMethod.POST)) {
                con.setDoOutput(true);

                DataOutputStream os = new DataOutputStream(con.getOutputStream());

                PCILog.d("Request Payload      : %s", gson.toJson(request.getData()));
                if (request.getData() != null) os.writeBytes(gson.toJson(request.getData()));
                else os.writeBytes("");

                os.flush();
                os.close();
            }

            InputStream inputStream = null;

            if (con.getResponseCode() / 100 == 2) {
                inputStream = con.getInputStream();
            } else {
                inputStream = con.getErrorStream();
            }

            PCIADApiResponse<T> responseEntity = null;

            if (inputStream != null) {
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String responseString = response.toString();
                PCILog.d("Response String      : %s", responseString);
                PCIADApiResponseHolder responseHolder = null;

                try {
                    responseHolder = gson.fromJson(responseString, PCIADApiResponseHolder.class);
                } catch (JsonSyntaxException e) {
                    PCILog.e(e);
                    responseHolder = new PCIADApiResponseHolder();
                    responseHolder.setCode(-1);
                    responseHolder.setMessage(e.getMessage());
                }

                responseEntity = new PCIADApiResponse<T>();
                responseEntity.setCode(responseHolder.getCode());
//                responseEntity.setData(responseHolder.getData(responseDataClass));
                responseEntity.setMessage(responseHolder.getMessage());
                PCILog.d("Response Payload     : %s", gson.toJson(responseEntity.getData()));
            } else {
                responseEntity = new PCIADApiResponse<T>();
                responseEntity.setCode(con.getResponseCode());
            }

            return responseEntity;
        } catch (IOException ioException) {
            //ioException.printStackTrace();
            PCILog.e(request.getTarget().toString() + " 요청실패");
            throw ioException;
        }
    }


}
