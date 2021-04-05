from _ast import If
from _thread import *
from operator import eq
import os
import socket

S_HOST = '192.168.137.106'
S_PORT = 7779

def threaded(client_socket, addr): 

    print('Connected by :', addr[0], ':', addr[1])

    try:
        data = client_socket.recv(1024)

        print('Get Message ' + data.decode())

        client_socket.send(data)

    except:
        print('Exception by ' + addr[0],':',addr[1])

    client_socket.close()

def main():
    global S_HOST, S_PORT

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind((S_HOST, S_PORT))
    server_socket.listen()

    print('server start')

    while True:

        print('wait')

        client_socket, addr = server_socket.accept()
        start_new_thread(threaded, (client_socket, addr))

    server_socket.close()

main()