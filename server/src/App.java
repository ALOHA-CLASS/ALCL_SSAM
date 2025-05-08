import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) {
        try {
            // 8888 포트에서 클라이언트 연결 대기
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("서버가 시작되었습니다. 클라이언트를 기다리는 중...");

            while (true) {  // 연결을 계속 유지하도록 무한 루프 사용
                // 클라이언트 연결 수락
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 연결되었습니다.");

                // 클라이언트와 데이터 송수신을 위한 스트림 설정
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                // 클라이언트로부터 메시지 받기
                String clientMessage;
                while ((clientMessage = input.readLine()) != null) {  // 클라이언트가 종료할 때까지 메시지 받기
                    System.out.println("클라이언트로부터 받은 메시지: " + clientMessage);
                    
                    // 클라이언트에게 응답 보내기
                    output.println("서버로부터의 응답: " + clientMessage);
                }

                // 클라이언트가 연결을 종료하면 연결 종료
                System.out.println("클라이언트 연결 종료.");
                input.close();
                output.close();
                clientSocket.close();
            }

            // 서버 종료
            // serverSocket.close(); // 서버 종료를 원할 때 주석 해제 (무한 루프라서 종료 안됨)

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
