package main

import (
	"fmt"
	"time"
//	"log"
)

func chan_test(){
	var ch1 chan int
	ch1 = make(chan int)
	
	go send_int(ch1)
	go receive_int(ch1)
	
	time.Sleep(1e9)
	
}

func send_int(ch chan int){
	for i:=0;;i++{
		ch <- i
	}
}

func receive_int(ch chan int){
	for {
		fmt.Println(<-ch)
	}
}

//func safelyWork(work *int){
//	defer func(){
//		if err := recover();err!=nil{
//			log.Printf("error %s in %v", err, work)
//		}
//	}
//}
