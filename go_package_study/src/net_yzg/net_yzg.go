package net_yzg

import (
//	"fmt"
	"net/http"
	"log"
	"time"
)

func TestNet(method string){
	switch method {
	case "netHttpServerv1":
		testStartHttpServerv1()
	case "netHttpServerv2":
		testStartHttpServerv2()
	case "netHttpServerv3":
		testStartHttpServerv3()
	case "testHttpTemplate":
		testHttpTemplate()	
	}
}

func testStartHttpServerv1(){
	//v1
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
	     w.Write([]byte("httpserver v1"))
       })
	http.HandleFunc("/bye", sayGoodbye)
	log.Println("start v1 server")
	log.Fatal(http.ListenAndServe(":8088", nil))
	
}

func testStartHttpServerv2(){
	mux := http.NewServeMux()
	mux.Handle("/", &myHandle1{})
	mux.HandleFunc("/bye", sayGoodbye2)
	log.Println("start v2 server")
	log.Fatal(http.ListenAndServe(":8089", mux))
	
}

func testStartHttpServerv3(){
	mux := http.NewServeMux()
	mux.Handle("/", &myHandle2{})
	mux.HandleFunc("/bye", sayGoodbye3)
	
	server := http.Server{
		Addr : ":8090",
		WriteTimeout : time.Second * 3,
		Handler : mux,
	}
	log.Println("start v3 server")
	log.Fatal(server.ListenAndServe())
}

type myHandle1 struct{}
func (me *myHandle1) ServeHTTP(w http.ResponseWriter, r *http.Request){
	w.Write([]byte("this is version v2"))
}

type myHandle2 struct{}
func (me *myHandle2) ServeHTTP(w http.ResponseWriter, r *http.Request){
	w.Write([]byte("this is version v3"))
}

func sayGoodbye(w http.ResponseWriter, r *http.Request){
	w.Write([]byte("bye bye, this is server v1"))
}

func sayGoodbye2(w http.ResponseWriter, r *http.Request){
	w.Write([]byte("bye bye, this is server v2"))
}

func sayGoodbye3(w http.ResponseWriter, r *http.Request){
	w.Write([]byte("bye bye, this is server v3"))
}
