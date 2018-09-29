# !/user/bin/env python
# -coding:utf-8 -

import socket
import time

s=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(('127.0.0.1', 8080))

for i in range(0,1000):
    print(i)
    s.send(b'GET /efg_web_test/hello/world?param=easfun HTTP/1.1\r\nHost: 127.0.0.1:8080\r\nConnection: keep-alive\r\nuser-agent: SocketHttpClient easyfun\r\n\r\n')
    buffer = []
    while True:
        # 每次最多接收1k字节:
        debug=[]
        d = s.recv(1)
        if d:
            buffer.append(d)

            if len(buffer) > 4:
                tail = b''.join(buffer[-4:])
                # print(tail)
                if tail == b'\r\n\r\n':
                    break
        else:
            break
    data = b''.join(buffer)
    print(data)

    time.sleep(1)

s.close()

