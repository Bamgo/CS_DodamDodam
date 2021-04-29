import serial
import time
import socket

cmd = 'temp'


def temperature():
    seri = serial.Serial("COM3", 115200)

    obj = seri.readline()
    str = obj[:-2].decode()

    return (str)


while True:
    print(temperature())
