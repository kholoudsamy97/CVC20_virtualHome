import socket

# import threading

host, = "192.168.1.6",
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

    client.close()


# thread = threading.Thread(target = recvFromAndroid, args = ())
# thread.start()
recv()
# print "completed"