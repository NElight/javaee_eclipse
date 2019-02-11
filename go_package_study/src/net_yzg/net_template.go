package net_yzg

import (
	"net/http"
	"html/template"
	"log"
)

type info struct{
	Title string
	Name string
	Site string
}

func testHttpTemplate(){
	http.HandleFunc("/", sayHello)
	log.Println("start http server for template test")
	err := http.ListenAndServe(":8091", nil)
	if err != nil {
		log.Println(err)
	}
	
}

func sayHello(w http.ResponseWriter, r *http.Request){
	message := info{Title:"templage", Name:"yzg", Site:"www.aaaabbbbccc.com"}
	log.Println("aaaaa")
	temp, _ := template.ParseFiles("src/net_yzg/template/home.html")
	log.Println(temp)
	temp.Execute(w, message)
}