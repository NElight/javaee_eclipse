package main

import (
	"fmt"
	"net"
	
)


func startNetServer(){
	fmt.Println("Starting net server")
	listener, err := net.Listen("tcp", "localhost:50000")
	if err != nil {
		fmt.Println("error when listen address : ", err.Error())
		return
	}
	
	for {
		conn, err := listener.Accept()
		if err != nil{
			fmt.Println("error when connect : %s", err.Error())
			return
		}
		go doServerStuff(conn)
	}
}

func doServerStuff(conn net.Conn) {
	for {
		buf := make([]byte, 512)
		len, err :=conn.Read(buf)
		if err != nil {
			fmt.Println("error when read buf :%s", err.Error())
			return 
		}
		fmt.Printf("received string : %v", string(buf[:len]))
	}
}
