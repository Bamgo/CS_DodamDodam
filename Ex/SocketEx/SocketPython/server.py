from socket import *
from select import *

HOST = ''
PORT = 3000
BUFSIZE = 1024
ADDR = (HOST, PORT)

serverSocket = socket(AF_INET, SOCK_STREAM)  # 소켓 생성

serverSocket.bind(ADDR)  # 소켓 주소 정보 할당
print('bind')

serverSocket.listen(100)  # 연결 수신 대기 상태
print('listen')

clientSocket, addr_info = serverSocket.accept()  # 연결 수락
print('accept')
print('--client information--')
print(clientSocket)

while True:  # 클라이언트로부터 메시지를 가져옴
    data = clientSocket.recv(65535)
    print('recieve data : ', data.decode())
    msg = data.decode()
    if msg == 'exit':  # exit라는 메세지를 받으면 종료
        break;

clientSocket.close()  # 소켓 종료
serverSocket.close()
print('close')