package com.example.mysqlapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CommunicationHelper {

    private CompletableFuture socketFuture;

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private BufferedReader bufferedReader;
    final String endOfMessage = "_EOM";
    byte[] msgBuffer = new byte[1024];
    int messageLen = 0;

    private static CommunicationHelper INSTANCE;

    private CommunicationHelper() {


        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        createSocket();
    }

    public String searchData(String id) {

        try {

            if (socketFuture != null) {

                socketFuture.join();
            }

            dataOutputStream.writeBytes("SEARCH");
            dataOutputStream.writeBytes(id);
            dataOutputStream.writeBytes("_EOM");
            dataOutputStream.flush();

            return readStream(bufferedReader);

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        } finally {

            createSocket();
        }
    }

    public boolean insertData(String id, String name, String surname, String major, String gpa) {

        try {

            if (socketFuture != null) {

                socketFuture.join();
            }

            dataOutputStream.writeBytes("INSERT");
            dataOutputStream.writeBytes(id);
            dataOutputStream.writeBytes("^^");
            dataOutputStream.writeBytes(name);
            dataOutputStream.writeBytes("^^");
            dataOutputStream.writeBytes(surname);
            dataOutputStream.writeBytes("^^");
            dataOutputStream.writeBytes(major);
            dataOutputStream.writeBytes("^^");
            dataOutputStream.writeBytes(gpa);
            dataOutputStream.writeBytes("_EOM");
            dataOutputStream.flush();

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        } finally {

            createSocket();
        }

        return true;
    }

    public String[] fetchData() {

        try {

            if (socketFuture != null) {

                socketFuture.join();
            }

            dataOutputStream.writeBytes("FETCH");
            dataOutputStream.writeBytes("_EOM");
            dataOutputStream.flush();

            String data = readStream(bufferedReader);
            System.out.println(data);
            String[] params = data.split("\\^\\^");

            return params;

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        } finally {

            createSocket();
        }
    }

    public User loginToSql(String id, String password) {

        try {

            if (socketFuture != null) {

                socketFuture.join();
            }

            User loginInfo;

            dataOutputStream.writeBytes("LOGIN");
            dataOutputStream.writeBytes(id);
            dataOutputStream.writeBytes("^^");
            dataOutputStream.writeBytes(password);
            dataOutputStream.writeBytes("_EOM");
            dataOutputStream.flush();


            String message = readStream(bufferedReader);
            String[] params = message.split("\\^\\^");

            boolean isAdmin = 1 == Integer.parseInt(params[1]) ? true : false;

            if (isAdmin) {

                loginInfo = new User();

                loginInfo.setId(Integer.parseInt(params[0]));
                loginInfo.setAdmin(true);

            } else {

                loginInfo = new Student();
                loginInfo.setId(Integer.parseInt(params[0]));
            }

            return loginInfo;

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        } finally {

            createSocket();
        }
    }

    public static CommunicationHelper getInstance() {

        if (INSTANCE == null) {

            INSTANCE = new CommunicationHelper();
        }

        return INSTANCE;
    }


    private void createSocket() {

        socketFuture = CompletableFuture.runAsync(() -> {

            try {

                Log.i("NETWORK", "CREATING SOCKET");
                socket = new Socket("192.168.1.67", 1269);

                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.i("NETWORK", "SOCKET CREATED");

            } catch (IOException e) {

                e.printStackTrace();
            }
        });
    }

    private String readStream(BufferedReader dataInputStream) {

        String message = "";

        try {

            while (true) {

                int result = dataInputStream.read();

                if (result == -1) {

                    break;

                } else {

                    msgBuffer[messageLen++] = (byte) result;

                    byte[] arr = Arrays.copyOfRange(msgBuffer, 0, messageLen);

                    message = new String(arr);

                    if (message.endsWith(endOfMessage)) {

                        message = message.substring(0, messageLen - 4);

                        break;
                    }
                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            Arrays.fill(msgBuffer, (byte) 0);
            messageLen = 0;

            return message;
        }
    }
}
