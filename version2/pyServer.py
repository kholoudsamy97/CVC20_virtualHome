import socket
import struct
# import threading

host, = "192.168.1.3",
port = 1234
client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


def recv():
    try:
        client.bind((host, port))
    finally:
        pass
    client.listen(10)  # how many connections can it receive at one time
    print("Start Listening...")

    while True:
        conn, addr = client.accept()
        print("client with address: ", addr, " is connected.")
        data = conn.recv(1024)
        print(data)
        data1 = conn.recv(1024)
        print(data1)
        if data == "Correct" or data1 == "Correct":
            reply = "Success"
            conn.send(reply.encode("utf-8"))
            conn.close()
            print("-----------------------------")
        elif data == "Disconnect" or data1 == "Disconnect":
            reply = "Disconnected and the listen has Stopped"
            conn.send(reply.encode("utf-8"))
            conn.close()
            break
        else:
            reply = "Failed"
            conn.send(reply.encode("utf-8"))
            conn.close()
            print("-----------------------------")

        buf = ''
        while len(buf) < 4:
            buf += client.recv(4 - len(buf))
        size = struct.unpack('!i', buf)
        print("receiving %s bytes" ) % size


        with open('tst.jpg', 'wb') as img:
            while True:
                data = client.recv(1024)
                if not data:
                    break
                img.write(data)
        print('received, yay!')


    client.close()


# thread = threading.Thread(target = recvFromAndroid, args = ())
# thread.start()
recv()
# print "completed"